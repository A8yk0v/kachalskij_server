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
    public Response uploadFiles(@RequestParam("data") MultipartFile data) {

        try {
            if (data != null && data.getOriginalFilename().equals(""))
                return new Response("bad");

            fileStorage.addFile(data);
        } catch (Exception e) {
            e.printStackTrace();
            return new Response("bad");
        }

        System.out.println("File uploaded");
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
        // Объект отчета
        CalculateResponse response = new CalculateResponse("Ok");

        // Отправляем данные клиентам
        clientService.sendFileDataOnClients();
        // Инициализируем матрицы
        matrixServices.readData();
        // Что вычислять клиентам
        clientService.sendClientsMatrixCalculateParam(matrixServices.getCount());
        // В это время вычисляем сами
        long time = matrixServices.calculate();
        System.out.println("Время вычисления на сервере: " + time/1000.0);
        response.addStrLIne("Время вычисления на сервере: " + time/1000.0);
        // Ожидаем ответы от клиентов
        String str = clientService.clientsResponse();
        response.addStrLIne(str);
        response.addStrLIne("-----------------------------------------------");

        return response;
    }
}
