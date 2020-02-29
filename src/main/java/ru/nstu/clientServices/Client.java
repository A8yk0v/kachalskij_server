package ru.nstu.clientServices;

import java.net.Socket;
import java.net.SocketException;

public class Client {
    private String description;
    private Socket socket;

    public Client(Socket socket) {
        this.socket = socket;
        // Таймаут для сокета в две секунды
        try {
            this.socket.setSoTimeout(2*1000);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        description = "Noname client";
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    @Override
    public String toString() {
        return "{" +
                "description: " + description +
                ", socket: " + socket +
                '}';
    }
}
