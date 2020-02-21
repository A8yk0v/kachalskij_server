package ru.nstu.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class HelloController {

    @RequestMapping("/")
    public String index() {
        System.out.println("index");
        return "index.html";
    }

    @PostMapping("/")
    public String uploadFiles(@RequestParam("file1") MultipartFile file1,
                              @RequestParam("file2") MultipartFile file2,
                              @RequestParam("file3") MultipartFile file3) {
        System.out.println("test");
        return "{id: 'ok'}";
    }

}

