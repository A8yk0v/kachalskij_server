package ru.nstu.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.nstu.FileStorage;
import ru.nstu.Result;
import ru.nstu.exceptions.FilesUploadException;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
public class RestFulController {

    @Autowired
    FileStorage fileStorage;

    @PostMapping("/api/file")
    public Result uploadFiles(@RequestParam("file1") MultipartFile file1,
                              @RequestParam("file2") MultipartFile file2,
                              @RequestParam("file3") MultipartFile file3) {

        try {
            if (file1 != null && file1.getOriginalFilename().equals("") ||
                    file2 != null && file2.getOriginalFilename().equals("") ||
                    file3 != null && file3.getOriginalFilename().equals(""))
                return new Result("bad");

            fileStorage.addFiles(file1, file2, file3);
        }
        catch (FilesUploadException e) {
            return new Result(e.toString());
        }
        catch (Exception e) {
            e.printStackTrace();
            return new Result("bad");
        }

        return new Result("ok");
    }
}
