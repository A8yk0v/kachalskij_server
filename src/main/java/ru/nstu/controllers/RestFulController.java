package ru.nstu.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.nstu.FileStorage;
import ru.nstu.matrix.MatrixServices;
import ru.nstu.response.CalculateResponse;
import ru.nstu.response.Response;
import ru.nstu.clientServices.ClientService;
import ru.nstu.exceptions.FilesUploadException;

@RestController
public class RestFulController {

    @Autowired
    FileStorage fileStorage;
    @Autowired
    ClientService clientService;
    @Autowired
    MatrixServices matrixServices;


    @PostMapping("/api/file")
    public Response uploadFiles(@RequestParam("file1") MultipartFile file1,
                                @RequestParam("file2") MultipartFile file2,
                                @RequestParam("file3") MultipartFile file3) {

        try {
            if (file1 != null && file1.getOriginalFilename().equals("") ||
                    file2 != null && file2.getOriginalFilename().equals("") ||
                    file3 != null && file3.getOriginalFilename().equals(""))
                return new Response("bad");

            fileStorage.addFiles(file1, file2, file3);
        }
        catch (FilesUploadException e) {
            return new Response(e.toString());
        }
        catch (Exception e) {
            e.printStackTrace();
            return new Response("bad");
        }

        System.out.println("Files uploaded");
        return new Response("ok");
    }

    /**
     * Проверяем состояния соединения с клиентами
     * И отправляем список подключенных клиентов интерфейсу
     * @return JSON string in response for WebUI
     */
    @PostMapping("/api/refreshClientsList")
    public String refreshClientsList() {
        if ( clientService.isClients() ) {
            clientService.testClientsConnectionn();
            if (clientService.isClients() ) return clientService.getStringClientsList();
        }
        return "{\"clients\":[\"None\"]}";
    }


    /**
     * Инициируем паралельное вычисление матриц
     * принажатии кнопки "calculate" в WebUI
     * @return JSON string in response for WebUI
     */
    @PostMapping("/api/calculate")
    public CalculateResponse calculate() {
        // Отправляем данные клиентам
        clientService.sendFileDataOnClients();
        // TODO В это время вычисляем сами
        long time = matrixServices.calculate();
        System.out.println("Время вычисления на сервере: " + time);

        return new CalculateResponse("{\"status\":[\"Ok\"]}");


        // TODO Ожидаем ответы от клиентов

        // TODO Формируем отчет
    }
}
