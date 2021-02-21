package net.juanxxiii.threads;

import net.juanxxiii.dto.Message;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class MessageWriter extends Thread{
    private final Socket client;
    private final String username;
    private ObjectOutputStream objectOutputStream;

    public MessageWriter(Socket client, String username) {
        this.client = client;
        this.username = username;
    }

    @Override
    public void run() {
        try {
            objectOutputStream = new ObjectOutputStream(client.getOutputStream());
            Message message = new Message();
            message.setUsername(username);
            sendMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(Message message) {
        try {
            objectOutputStream.writeObject(message);
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sentDisconnectMessage() {
        Message message = new Message();
        message.setUsername("disconnect");
        message.setMessage(username);
        sendMessage(message);
    }
}
