/* 
 * Copyright 2018 Patrik Karlström.
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
package se.trixon.idr.ui;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.SystemUtils;
import se.trixon.almond.util.AlmondUI;
import se.trixon.almond.util.Dict;
import se.trixon.almond.util.swing.dialogs.Message;
import se.trixon.idl.Command;
import se.trixon.idl.FrameImageCarrier;
import se.trixon.idl.client.Client;
import se.trixon.idl.client.ClientListener;

/**
 *
 * @author Patrik Karlström
 */
public class MainFrame extends javax.swing.JFrame {

    private static final boolean IS_MAC = SystemUtils.IS_OS_MAC;
    private static final Logger LOGGER = Logger.getLogger(MainFrame.class.getName());
    private final AlmondUI mAlmondUI = AlmondUI.getInstance();
    private final Client mClient = new Client();

    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        initComponents();
        mAlmondUI.addWindowWatcher(this);
        mAlmondUI.initoptions();

        initActions();
        init();

        if (IS_MAC) {
            initMac();
        }

        initMenus();
    }

    private void init() {
        mClient.addClientListener(new ClientListener() {
            @Override
            public void onClientConnect() {
                System.out.println("we did connect");
            }

            @Override
            public void onClientDisconnect() {
                System.out.println("we did disconnect");
            }

            @Override
            public void onClientRegister() {
                System.out.println("we did connect a frame");
            }

            @Override
            public void onClientReceive(FrameImageCarrier frameImageCarrier) {
//            FileUtils.write(new File("/home/pata/base64.txt"), frameImageCarrier.getBase64(), "utf-8");
                imagePanel.setImage(frameImageCarrier.getRotatedBufferedImage());
            }
        });
    }

    private void initActions() {
    }

    private void initMac() {
    }

    private void initMenus() {
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT
     * modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        topPanel = new javax.swing.JPanel();
        connectButton = new javax.swing.JButton();
        disconnectButton = new javax.swing.JButton();
        sendTextField = new javax.swing.JTextField();
        sendButton = new javax.swing.JButton();
        pingButton = new javax.swing.JButton();
        randomButton = new javax.swing.JButton();
        registerButton = new javax.swing.JButton();
        deregisterButton = new javax.swing.JButton();
        imagePanel = new se.trixon.almond.util.swing.ImagePanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("se/trixon/idr/ui/Bundle"); // NOI18N
        setTitle(bundle.getString("MainFrame.title")); // NOI18N
        setAlwaysOnTop(true);

        connectButton.setText(Dict.CONNECT.toString());
        connectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connectButtonActionPerformed(evt);
            }
        });

        disconnectButton.setText(bundle.getString("MainFrame.disconnectButton.text")); // NOI18N
        disconnectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                disconnectButtonActionPerformed(evt);
            }
        });

        sendTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendTextFieldActionPerformed(evt);
            }
        });

        sendButton.setText(bundle.getString("MainFrame.sendButton.text")); // NOI18N
        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendButtonActionPerformed(evt);
            }
        });

        pingButton.setText(bundle.getString("MainFrame.pingButton.text")); // NOI18N
        pingButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pingButtonActionPerformed(evt);
            }
        });

        randomButton.setText(bundle.getString("MainFrame.randomButton.text")); // NOI18N
        randomButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                randomButtonActionPerformed(evt);
            }
        });

        registerButton.setText(bundle.getString("MainFrame.registerButton.text")); // NOI18N
        registerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerButtonActionPerformed(evt);
            }
        });

        deregisterButton.setText(bundle.getString("MainFrame.deregisterButton.text")); // NOI18N
        deregisterButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deregisterButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout topPanelLayout = new javax.swing.GroupLayout(topPanel);
        topPanel.setLayout(topPanelLayout);
        topPanelLayout.setHorizontalGroup(
            topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(topPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(topPanelLayout.createSequentialGroup()
                        .addComponent(sendTextField)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sendButton))
                    .addGroup(topPanelLayout.createSequentialGroup()
                        .addGroup(topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(topPanelLayout.createSequentialGroup()
                                .addComponent(connectButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(disconnectButton))
                            .addGroup(topPanelLayout.createSequentialGroup()
                                .addComponent(pingButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(randomButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(registerButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(deregisterButton)))
                        .addGap(0, 281, Short.MAX_VALUE)))
                .addContainerGap())
        );
        topPanelLayout.setVerticalGroup(
            topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(topPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(connectButton)
                    .addComponent(disconnectButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sendTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sendButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pingButton)
                    .addComponent(randomButton)
                    .addComponent(registerButton)
                    .addComponent(deregisterButton))
                .addContainerGap())
        );

        getContentPane().add(topPanel, java.awt.BorderLayout.PAGE_START);

        imagePanel.setBackground(new java.awt.Color(102, 102, 102));

        javax.swing.GroupLayout imagePanelLayout = new javax.swing.GroupLayout(imagePanel);
        imagePanel.setLayout(imagePanelLayout);
        imagePanelLayout.setHorizontalGroup(
            imagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 640, Short.MAX_VALUE)
        );
        imagePanelLayout.setVerticalGroup(
            imagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 166, Short.MAX_VALUE)
        );

        getContentPane().add(imagePanel, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void connectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_connectButtonActionPerformed
        try {
            mClient.connect();
        } catch (MalformedURLException | SocketException ex) {
            Message.error(this, Dict.Dialog.ERROR.toString(), ex.getMessage());
            LOGGER.log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Message.error(this, Dict.Dialog.ERROR.toString(), ex.getMessage());
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_connectButtonActionPerformed

    private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendButtonActionPerformed
        new Thread(() -> {
            try {
                String result = mClient.send(sendTextField.getText());
                System.out.println(result);
            } catch (IOException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        }).start();
    }//GEN-LAST:event_sendButtonActionPerformed

    private void pingButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pingButtonActionPerformed
        try {
            String result = mClient.send(Command.PING);
            System.out.println(result);

        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_pingButtonActionPerformed

    private void randomButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_randomButtonActionPerformed
        try {
            String result = mClient.send(Command.RANDOM);
            System.out.println(result);
//            lines.removeLast();

//            String json = String.join(" ", lines);
//            System.out.println("RANDOM RESPONSE");
//            System.out.println(json);
//            FrameImageCarrier imageDescriptor = FrameImageCarrier.fromJson(json);
//            FileUtils.write(new File("/home/pata/base64.txt"), imageDescriptor.getBase64(), "utf-8");
////            System.out.println(imageDescriptor.getImage());
//            imagePanel.setImage(imageDescriptor.getRotatedBufferedImage());
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_randomButtonActionPerformed

    private void sendTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendTextFieldActionPerformed
        sendButtonActionPerformed(evt);
    }//GEN-LAST:event_sendTextFieldActionPerformed

    private void registerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerButtonActionPerformed
        try {
            mClient.register();
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_registerButtonActionPerformed

    private void deregisterButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deregisterButtonActionPerformed
        try {
            mClient.deregister();
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_deregisterButtonActionPerformed

    private void disconnectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_disconnectButtonActionPerformed
        mClient.disconnect();
    }//GEN-LAST:event_disconnectButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton connectButton;
    private javax.swing.JButton deregisterButton;
    private javax.swing.JButton disconnectButton;
    private se.trixon.almond.util.swing.ImagePanel imagePanel;
    private javax.swing.JButton pingButton;
    private javax.swing.JButton randomButton;
    private javax.swing.JButton registerButton;
    private javax.swing.JButton sendButton;
    private javax.swing.JTextField sendTextField;
    private javax.swing.JPanel topPanel;
    // End of variables declaration//GEN-END:variables
}
