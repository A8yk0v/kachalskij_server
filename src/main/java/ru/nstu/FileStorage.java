package ru.nstu;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.nstu.exceptions.FilesUploadException;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class FileStorage {
    private File algorithm;
    private File Data;
    private File DataManipulationAlgorithm;

    public void addFiles(MultipartFile alg, MultipartFile data, MultipartFile dataAlg) throws FilesUploadException {
        try {
            algorithm = new File(alg.getOriginalFilename());
            Data = new File(data.getOriginalFilename());
            DataManipulationAlgorithm = new File(dataAlg.getOriginalFilename());

            Files.copy(alg.getInputStream(), Paths.get(algorithm.getPath()) );
            Files.copy(data.getInputStream(), Paths.get(Data.getPath()) );
            Files.copy(dataAlg.getInputStream(), Paths.get(DataManipulationAlgorithm.getPath()) );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final File getAlgorithm() {
        return algorithm;
    }

    public final File getData() {
        return Data;
    }

    public final File getDataManipulationAlgorithm() {
        return DataManipulationAlgorithm;
    }
}
