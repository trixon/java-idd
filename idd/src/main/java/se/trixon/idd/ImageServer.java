/*
 * Copyright 2017 Patrik Karlsson.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package se.trixon.idd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import se.trixon.almond.util.SystemHelper;
import se.trixon.almond.util.Xlog;
import se.trixon.idd.db.Db;
import se.trixon.idl.shared.Command;
import se.trixon.idl.shared.IddHelper;
import se.trixon.idl.shared.ImageDescriptor;

/**
 *
 * @author Patrik Karlsson
 */
class ImageServer {

    private static final Logger LOGGER = Logger.getLogger(ImageServer.class.getName());

    private Set<ClientThread> mClientThreads = new HashSet<>();
    private final Config mConfig = Config.getInstance();
    private boolean mDirectKill;
    private boolean mKillInitiated;
    private ServerSocket mServerSocket;
    private boolean mSuccessfulStart;
    private final Db mDb = Db.getInstance();
    private final Querator mQuerator = Querator.getInstance();

    ImageServer() throws IOException {
        intiListeners();
        startServer();

        while (true) {
            try {
                Socket socket = mServerSocket.accept();
                ClientThread clientThread = new ClientThread(socket);
                mClientThreads.add(clientThread);
                clientThread.start();
                clientConnected(socket);
            } catch (IOException e) {
                Xlog.timedErr(e.getMessage());
            }
        }
    }

    private void clientConnected(Socket socket) {
        Xlog.timedOut(String.format("Client connected: %s:%d (%d)",
                socket.getLocalAddress(),
                socket.getLocalPort(),
                socket.getPort()
        ));
    }

    private void clientDisconnected(Socket socket) {
        Xlog.timedOut(String.format("Client disconnected: %s:%d (%d)",
                socket.getLocalAddress(),
                socket.getLocalPort(),
                socket.getPort()
        ));
    }

    private void intiListeners() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (mSuccessfulStart && !mKillInitiated) {
                mDirectKill = true;
                shutdown();
            }
            Xlog.timedOut("shut down!");
        }));
    }

    private String sendFile(String path) {
        String result = null;

//        if (mImageClientCommanders.isEmpty()) {
//            result = "no recievers connected - not sending";
//        } else if (path != null) {
//            result = "recievers connected - try sending file";
//            for (ImageClientCommander clientCommander : mImageClientCommanders) {
//                Thread thread = new Thread(() -> {
//////                        clientCommander.sendFile(remoteInputStreamServer.export());
//                });
//
//                thread.start();
//            }
//        } else {
//            result = "No file to send";
//        }
        return result;
    }

    private synchronized void shutdown() {
        Xlog.timedOut("shutting down...");
        mKillInitiated = true;
        mClientThreads.forEach((clientThread) -> {
            try {
                clientThread.kill();
            } catch (IOException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });

        if (!mDirectKill) {
            IddHelper.exit();
        }
    }

    private void startServer() {
        try {
            final int port = mConfig.getPort();
            String message = String.format("Starting server on port %d", port);
            Xlog.timedOut(message);

            mServerSocket = new ServerSocket(port);
            message = String.format("Listening for connections on port %d", port);
            Xlog.timedOut(message);
            mSuccessfulStart = true;
        } catch (IOException e) {
            Xlog.timedErr(e.getMessage());
            IddHelper.exit();
        }
    }

    class ClientThread extends Thread {

        private static final String OK = "OK";

        private BufferedReader is = null;
        private boolean mKeepReading = true;
        private final Socket mSocket;
        private PrintStream os = null;

        public ClientThread(Socket clientSocket) {
            setName(String.format("%s [%s]", getClass().getCanonicalName(), clientSocket.getInetAddress()));
            mSocket = clientSocket;
        }

        @Override
        public void run() {
            try {
                is = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
                os = new PrintStream(mSocket.getOutputStream());

                os.printf("OK IDD %d\n", IddHelper.PROTOCOL_VERSION);

                while (mKeepReading) {
                    String line = is.readLine();
                    if (line != null) {
                        if (!StringUtils.isBlank(line)) {
                            parseCommand(line);
                        }

//                        synchronized (this) {
//                            for (ClientThread clientThread : mClientThreads) {
//                                if (clientThread.clientName != null) {
//                                    clientThread.os.println("<" + name + "> " + line);
//                                }
//                            }
//                        }
                    }
                }

                clientDisconnected(mSocket);

                synchronized (this) {
                    mClientThreads.remove(this);
                }

                kill();
            } catch (IOException e) {
                Xlog.timedErr(e.getMessage());
            }
        }

        private void parseCommand(String commandString) {
            Xlog.timedOut("parse: " + commandString);
            String[] elements = StringUtils.split(commandString, " ");

            String cmd = elements[0];
            String[] args = ArrayUtils.remove(elements, 0);

            try {
                Command command = Command.valueOf(cmd.toUpperCase(Locale.ROOT));
                System.out.println(command);

                if (command.validateArgs(args)) {
                    String path;
                    switch (command) {
                        case CLOSE:
                            mKeepReading = false;
                            break;

                        case KILL:
                            shutdown();
                            break;

                        case PING:
                            send(OK);
                            break;

                        case RANDOM:
                            path = mQuerator.getRandomPath();
                            sendImage(path);
                            break;

                        case UPDATE:
                            if (args.length > 0) {
                                path = args[0];
                            } else {
                                path = mConfig.getImageDirectory().getPath();
                            }
                            String response = mDb.update(path);
                            send(response);
                            break;

                        case VERSION:
                            send(String.format("idd version: %s\n%s", SystemHelper.getJarVersion(getClass()), OK));
                            break;

                        default:
                            throw new AssertionError();
                    }
                } else {
                    send(String.format("ACK [2@0] {%s} wrong number of arguments for \"%s\"", cmd, cmd));
                }
            } catch (IllegalArgumentException e) {
                send(String.format("ACK [5@0] {} unknown command \"%s\"", cmd));
            }
        }

        private void send(String s) {
            os.println(s);
        }

        private void sendImage(String path) {
            ImageDescriptor imagePacket = new ImageDescriptor();
            imagePacket.setPath(path);
            imagePacket.setBase64FromPath(path);

//            send("IMAGE PACKET");
            send(imagePacket.toJson());
            send(OK);

        }

        void kill() throws IOException {
//            os.println("*** Bye");
            is.close();
            os.close();
            mSocket.close();
        }
    }
}