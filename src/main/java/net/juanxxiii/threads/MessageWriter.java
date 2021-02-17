package net.juanxxiii.threads;

import net.juanxxiii.dto.Message;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class MessageWriter extends Thread{
    private Socket client;
    private ObjectOutputStream objectOutputStream;

    public MessageWriter(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        try {
            objectOutputStream = new ObjectOutputStream(client.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(Message message) {
        try {
            objectOutputStream.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
