package net.juanxxiii.view;

import net.juanxxiii.dto.Message;
import net.juanxxiii.threads.MessageListener;
import net.juanxxiii.threads.MessageWriter;

import javax.swing.*;
import java.io.IOException;
import java.net.Socket;
import java.time.LocalTime;

public class MainWindow {

    private JTextField messageTextField;
    private JButton sendButton;
    private JButton connectButton;
    private JScrollPane jScrollPanelMessage;
    private JScrollPane jScrollPanelUsers;
    private JTextPane userListPanel;
    private JTextPane messageListPanel;
    public JPanel panelMain;

    private MessageWriter messageWriter;
    private boolean online = false;
    private String username;

    public MainWindow() {
        messageTextField.setEnabled(false);
        sendButton.setEnabled(false);
        connectButton.addActionListener(e -> {
            if (!online) {
                username = JOptionPane.showInputDialog(panelMain, "¿Cuál es tu nombre de usuario?", null);
                checkName(username);
            } else {
                messageWriter.sentDisconnectMessage();
                System.exit(0);
            }
        });
        sendButton.addActionListener(actionEvent -> {
            Message message = new Message();
            message.setMessage(messageTextField.getText());
            messageTextField.setText("");
            message.setUsername(username);
            message.setTimestamp(LocalTime.now());
            messageWriter.sendMessage(message);
        });
    }

    public void checkName(String username) {
        if (username.equals("")) {
            JOptionPane.showMessageDialog(panelMain, "El campo nombre no puede estar vacío.");
            online = false;
        } else if (username.contains(" ")) {
            JOptionPane.showMessageDialog(panelMain, "El campo nombre tener espacios en blanco.");
            online = false;
        } else {
            online = true;
            messageTextField.setEnabled(true);
            sendButton.setEnabled(true);
            connectButton.setText("Desconectarse");
            try {
                Socket client = new Socket("localhost", 9090);
                messageWriter = new MessageWriter(client, username);
                messageWriter.start();
                MessageListener messageListener = new MessageListener(client, messageListPanel, userListPanel);
                messageListener.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
