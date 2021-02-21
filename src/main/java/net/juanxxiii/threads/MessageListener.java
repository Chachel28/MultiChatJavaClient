package net.juanxxiii.threads;

import net.juanxxiii.dto.Message;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Arrays;

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
                if (message.getUsername().equals("connected")) {
                    String[] users = message.getMessage().split("-");
                    String[] connectedUsers;
                    String[] disconnectedUsers = null;
                    if (users.length < 2){
                        connectedUsers = users[0].split("\n");
                    }else{
                        connectedUsers = users[0].split("\n");
                        disconnectedUsers = users[1].split("\n");
                    }
                    StringBuilder fullUserList = new StringBuilder("Usuarios conectados: \n");
                    for (String user:connectedUsers) {
                        fullUserList.append(user).append("\n");
                    }
                    fullUserList.append("\nUsuarios desconectados: \n");
                    if (disconnectedUsers != null){
                        for (String user:disconnectedUsers) {
                            fullUserList.append(user).append("\n");
                        }
                    }
                    userListPanel.setText(fullUserList.toString());
                    String fullMessage = "Se ha conectado " + connectedUsers[connectedUsers.length-1] + "\n";
                    messageListPanel.setText(messageListPanel.getText().concat(fullMessage.concat("\n")));
                } else if(message.getUsername().equals("disconnected")){
                    String[] users = message.getMessage().split("-");
                    String[] connectedUsers;
                    String[] disconnectedUsers = null;
                    if (users.length < 2){
                        connectedUsers = users[0].split("\n");
                    }else{
                        connectedUsers = users[0].split("\n");
                        disconnectedUsers = users[1].split("\n");
                    }
                    StringBuilder fullUserList = new StringBuilder("Usuarios conectados: \n");
                    for (String user:connectedUsers) {
                        fullUserList.append(user).append("\n");
                    }
                    fullUserList.append("\nUsuarios desconectados: \n");
                    if (disconnectedUsers != null){
                        for (String user:disconnectedUsers) {
                            fullUserList.append(user).append("\n");
                        }
                    }
                    userListPanel.setText(fullUserList.toString());
                    String fullMessage = "Se ha desconectado " + disconnectedUsers[disconnectedUsers.length-1] + "\n";
                    messageListPanel.setText(messageListPanel.getText().concat(fullMessage.concat("\n")));
                }else {
                    String fullMessage = message.getUsername() +
                            "\n\n" +
                            message.getMessage() +
                            "\n\n" +
                            message.getTimestamp().toString().substring(0, 8) +
                            "\n\t\t";
                    messageListPanel.setText(messageListPanel.getText().concat(fullMessage.concat("\n")));
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
