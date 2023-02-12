package ru.catunderglue.socksstore.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface FilesService {
    File getSocksDataFileInfo();

    File getOperationsDataFileInfo();

    boolean saveSocksToFile(String json);

    String readSocksFromFile();

    boolean saveOperationsToFile(String json);

    String readOperationsFromFile();

    void importSocksDataFile(MultipartFile file) throws IOException;

    void importOperationsDataFile(MultipartFile file) throws IOException;
}
