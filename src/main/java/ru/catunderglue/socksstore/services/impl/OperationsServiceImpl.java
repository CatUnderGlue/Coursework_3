package ru.catunderglue.socksstore.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Service;
import ru.catunderglue.socksstore.models.Operation;
import ru.catunderglue.socksstore.models.enums.OperationType;
import ru.catunderglue.socksstore.models.Socks;
import ru.catunderglue.socksstore.services.FilesService;
import ru.catunderglue.socksstore.services.OperationsService;

import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.List;

@Service
public class OperationsServiceImpl implements OperationsService {

    List<Operation> operations = new LinkedList<>();

    private final FilesService filesService;

    public OperationsServiceImpl(FilesService filesService) {
        this.filesService = filesService;
    }

    @Override
    public void addOperation(OperationType type, Socks socks) {
        operations.add(new Operation(type, socks.getQuantity(), socks.getSize(), socks.getCottonRel(), socks.getColor()));
        saveToFile();
    }

    private void saveToFile() {
        try {
            ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
            String json = mapper.writeValueAsString(operations);
            filesService.saveOperationsToFile(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void readFromFile() {
        String json = filesService.readOperationsFromFile();
        try {
            if (!json.isBlank()) {
                ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
                operations = mapper.readValue(json, new TypeReference<List<Operation>>() {
                });
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @PostConstruct
    public void init() {
        readFromFile();
    }
}
