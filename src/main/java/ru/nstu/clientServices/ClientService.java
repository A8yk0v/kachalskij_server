package ru.nstu.clientServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import ru.nstu.FileStorage;
import ru.nstu.IFileSendler;

import javax.annotation.PostConstruct;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class ClientService implements IFileSendler {
    private ServerSocket serverSocket;
    private CopyOnWriteArrayList<Client> clients;
    @Autowired
    private FileStorage fileStorage;
    @Value("${application.ServerPort}")
    private int ServerPort;

    @PostConstruct
    private void doMyInit() {
        try {
            serverSocket = new ServerSocket(ServerPort);
            clients = new CopyOnWriteArrayList<>();

            System.out.println("ServerPort:" + ServerPort);

            Thread thread = new Thread( new ClientAccept(serverSocket, clients) );
            thread.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ClientService() {
        /**
         * Код конструктора перенесен в doMyInit(),
         * так как аннотация @Value "исполняется"
         * после конструктора :/
         */
    }

    public boolean isClients() {
        return clients.size() != 0;
    }

    public void testClientsConnectionn() {
        Iterator<Client> i = clients.iterator();
        while (i.hasNext()) {
            Client item = i.next();
            try {
                int read = item.getSocket().getInputStream().read();
                if (read == -1) { // disconnected ?
                    item.getSocket().close();
                    clients.remove(item);
                }
            } catch (SocketTimeoutException e) {
                // Все хорошо! :)
                System.out.println("SocketTimeoutException");
            }
            catch (SocketException e) {
                clients.remove(item);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void sendFileDataOnClients() {
        for (Client client: clients) {
            try {
                Socket socket = client.getSocket();
                String str = new String( Files.readAllBytes(fileStorage.getData().toPath()) );

                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                out.write(str.length()); out.flush();
                out.write(str); out.flush();
                //out.close(); - так как закрывает соединение

            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void sendAllFiles() {
        for(Client item: clients) {
            try {
                Socket socket = item.getSocket();
                // Отправляем алгоритм
                String str = new String( Files.readAllBytes(fileStorage.getAlgorithm().toPath()) );
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                out.write(str.length()); out.write(str); out.flush();
                // Отправляем даные
                str = new String( Files.readAllBytes(fileStorage.getAlgorithm().toPath()) );
                out.write(str.length()); out.write(str); out.flush();

                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public final List<Client> getClientsList() {
        return clients;
    }

    public String getStringClientsList() {
        StringBuilder str = new StringBuilder("{\"clients\":[");
        boolean flag = false;
        for (Client item: clients) {
            if (flag)
                str.append(',');
            str.append('"' + item.getDescription() + '"');
            flag = true;
        }
        str.append("]}");
        return str.toString();
    }

}
