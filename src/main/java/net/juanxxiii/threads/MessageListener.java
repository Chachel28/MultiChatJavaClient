package net.juanxxiii.threads;

import net.juanxxiii.dto.Message;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class MessageListener extends Thread {
    private final Socket client;
    private final JTextPane messageListPanel;
    private final JTextPane userListPanel;

    public MessageListener(Socket client, JTextPane messageListPanel, JTextPane userListPanel) {
        this.client = client;
        this.messageListPanel = messageListPanel;
        this.userListPanel = userListPanel;
    }

    @Override
    public void run() {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(client.getInputStream());
            Message message;
            while ((message = (Message) objectInputStream.readObject()) != null) {
                if (message.getUsername().equals("server")) {
                    userListPanel.setText(message.getMessage());
                    String[] tokens = message.getMessage().split("\n");
                    String fullMessage = "Se ha conectado " + tokens[tokens.length-1] + "\n";
                    messageListPanel.setText(messageListPanel.getText().concat(fullMessage.concat("\n")));
                } else if(message.getUsername().equals("disconnected")){
                    String[] tokens = message.getMessage().split("\n");
                    String fullMessage = "Se ha desconectado " + tokens[0] + "\n";
                    StringBuilder users = new StringBuilder();
                    for (int i = 1; i < tokens.length; i++) {
                        users.append(tokens[i]);
                        users.append("\n");
                    }
                    userListPanel.setText(users.toString());
                    messageListPanel.setText(messageListPanel.getText().concat(fullMessage.concat("\n")));
                }else {
                    String fullMessage = message.getUsername() +
                            "\n\n\t" +
                            message.getMessage() +
                            "\n\n" +
                            message.getTimestamp().toString().substring(0, 9) +
                            "\n";
                    messageListPanel.setText(messageListPanel.getText().concat(fullMessage.concat("\n")));
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
