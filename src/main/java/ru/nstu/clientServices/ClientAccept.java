package ru.nstu.clientServices;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class ClientAccept implements Runnable {

    private ServerSocket serverSocket;
    private List<Client> clients;

    public ClientAccept(ServerSocket serverSocket, List<Client> clients) {
        this.serverSocket = serverSocket;
        this.clients = clients;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();

                Client client = new Client(clientSocket);
                clients.add(client);

                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String description = in.readLine();
                client.setDescription(description);

                System.out.println("New Client! " + client.getDescription());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
