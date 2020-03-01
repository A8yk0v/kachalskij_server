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

    /**
     *
     * @param i - кол-во строк в матрице
     */
    public void sendClientsMatrixCalculateParam(int i) {
        int rez = i / clients.size();
        int ost = i % clients.size();

        int fromS = 0;
        for (int j = 0; j < clients.size(); j++) {
            try {
                Socket socket = clients.get(j).getSocket();
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                if (j == clients.size()-1) {
                    out.write(rez + ost);
                    out.write(fromS);
                    fromS += rez + ost;
                }
                else {
                    out.write(rez);
                    out.write(fromS);
                    fromS += rez;
                }
                out.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String clientsResponse() {
        StringBuilder content = new StringBuilder();
        // TODO
        for(Client item: clients) {
            try {
                Socket socket = item.getSocket();

                InputStream is = socket.getInputStream();
                byte[] buffer = new byte[8192];
                // ByteArrayOutputStream используется, как накопитель байт, чтобы
                //   потом превратить в строку все полученные данные.
                //   преобразовывать часть потока в строку опасно, т.к.
                //   если данные идут в многобайтной кодировке, один символ может
                //   быть разрезан между чтениями
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                // InputStream.read( byte[] ) возвращает количество прочитанных байт
                //   и -1, если поток кончился (сервер закрыл соединение)
                //for (int received; (received = is.read(buffer)) != -1; ) {
                    // записываем прочитанное из потока, от 0 до количества считанных
                    int received = is.read(buffer);
                    baos.write(buffer, 0, received);
                //}

                // преобразуем в строку
                content.append( baos.toString("UTF-8") );
                content.append('\n');
            } catch (Exception e) {
                e.printStackTrace();
                content.append(e.toString());
                content.append('\n');
            }

            return content.toString();
        }

        return content.toString();
    }
}
