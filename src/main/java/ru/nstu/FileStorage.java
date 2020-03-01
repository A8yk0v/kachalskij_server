package ru.nstu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.nstu.clientServices.ClientService;
import ru.nstu.exceptions.FilesUploadException;

import java.io.File;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;

@Component
public class FileStorage {
    private File algorithm;
    private File data;
    private File dataManipulationAlgorithm;

    @Autowired
    private ClientService clientService;

    public void addFile(MultipartFile data) {
        try {
            this.data = new File("data");
            Files.deleteIfExists(this.data.toPath());
            Files.copy(data.getInputStream(), this.data.toPath() );
        } catch (FileAlreadyExistsException e) {
            System.out.println(e.toString());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addFiles(MultipartFile alg, MultipartFile data, MultipartFile dataAlg) throws FilesUploadException {
        try {
            algorithm = new File("algorithm");
            this.data = new File("data");
            dataManipulationAlgorithm = new File("dataManipulationAlgorithm");

            Files.deleteIfExists(algorithm.toPath());
            Files.deleteIfExists(this.data.toPath());
            Files.deleteIfExists(dataManipulationAlgorithm.toPath());

            Files.copy(alg.getInputStream(), algorithm.toPath() );
            Files.copy(data.getInputStream(), this.data.toPath() );
            Files.copy(dataAlg.getInputStream(), dataManipulationAlgorithm.toPath() );

        } catch (FileAlreadyExistsException e) {
            System.out.println(e.toString());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final File getAlgorithm() {
        return algorithm;
    }

    public final File getData() {
        return data;
    }

    public final File getDataManipulationAlgorithm() {
        return dataManipulationAlgorithm;
    }
}
