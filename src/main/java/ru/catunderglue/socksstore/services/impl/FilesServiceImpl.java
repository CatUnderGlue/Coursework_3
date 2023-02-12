package ru.catunderglue.socksstore.services.impl;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.catunderglue.socksstore.services.FilesService;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FilesServiceImpl implements FilesService {

    @Value(value = "${data.files.path}")
    private String dataFilesPath;
    @Value(value = "${socks.data.file.name}")
    private String socksDataFileName;
    @Value(value = "${operations.data.file.name}")
    private String operationsDataFileName;

    @Override
    public File getSocksDataFileInfo(){
        return new File(dataFilesPath + "/" + socksDataFileName);
    }
    @Override
    public File getOperationsDataFileInfo(){
        return new File(dataFilesPath + "/" + operationsDataFileName);
    }

    @Override
    public boolean saveSocksToFile(String json) {
        return saveToFile(json, socksDataFileName);
    }

    @Override
    public String readSocksFromFile() {
        return readFromFile(socksDataFileName);
    }

    @Override
    public boolean saveOperationsToFile(String json) {
        return saveToFile(json, operationsDataFileName);
    }

    @Override
    public String readOperationsFromFile() {
        return readFromFile(operationsDataFileName);
    }

    @Override
    public void importSocksDataFile(MultipartFile file) throws IOException {
        cleanDataFile(socksDataFileName);
        File dataFile = getSocksDataFileInfo();

        try (FileOutputStream fos = new FileOutputStream(dataFile)) {
            IOUtils.copy(file.getInputStream(), fos);
        } catch (IOException e) {
            throw new IOException();
        }
    }
    @Override
    public void importOperationsDataFile(MultipartFile file) throws IOException {
        cleanDataFile(operationsDataFileName);
        File dataFile = getOperationsDataFileInfo();

        try (FileOutputStream fos = new FileOutputStream(dataFile)) {
            IOUtils.copy(file.getInputStream(), fos);
        } catch (IOException e) {
            throw new IOException();
        }
    }

    public boolean cleanDataFile(String dataFileName) {
        Path path = Path.of(dataFilesPath, dataFileName);
        try {
            Files.deleteIfExists(path);
            Files.createFile(path);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String readFromFile(String dataFileName) {
        try {
            return Files.readString(Path.of(dataFilesPath, dataFileName));
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public boolean saveToFile(String json, String dataFileName) {
        try {
            cleanDataFile(dataFileName);
            Files.writeString(Path.of(dataFilesPath, dataFileName), json);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @PostConstruct
    private void init() {
        try {
            if (Files.notExists(Path.of(dataFilesPath, socksDataFileName))) {
                Files.createFile(Path.of(dataFilesPath, socksDataFileName));
            }
            if (Files.notExists(Path.of(dataFilesPath, operationsDataFileName))) {
                Files.createFile(Path.of(dataFilesPath, operationsDataFileName));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
