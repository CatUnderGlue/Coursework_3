package ru.catunderglue.socksstore.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import ru.catunderglue.socksstore.models.enums.Color;
import ru.catunderglue.socksstore.models.enums.OperationType;
import ru.catunderglue.socksstore.models.enums.Size;
import ru.catunderglue.socksstore.models.Socks;
import ru.catunderglue.socksstore.services.FilesService;
import ru.catunderglue.socksstore.services.OperationsService;
import ru.catunderglue.socksstore.services.SocksService;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class SocksServiceImpl implements SocksService {
    private List<Socks> socksList = new ArrayList<>();
    private final FilesService filesService;
    private final OperationsService operationsService;

    public SocksServiceImpl(FilesService filesService, OperationsService operationsService) {
        this.filesService = filesService;
        this.operationsService = operationsService;
    }

    @Override
    public List<Socks> getAllSocks(){
        init();
        return socksList;
    }

    @Override
    public Socks addSocks(Socks socks) {
        boolean flag = false;
        for (Socks socks_temp : socksList) {
            if (socks.getColor().equals(socks_temp.getColor()) && socks.getSize().equals(socks_temp.getSize()) && socks.getCottonRel() == socks_temp.getCottonRel()) {
                int index = socksList.indexOf(socks_temp);
                socks.setQuantity(socks.getQuantity() + socks_temp.getQuantity());
                socksList.set(index, socks);
                flag = true;
            }
        }
        if (!flag) {
            socksList.add(socks);
        }
        operationsService.addOperation(OperationType.ADD, socks);
        saveToFile();
        return socks;
    }

    @Override
    public int getSocks(Color color, Size size, int cottonMin, int cottonMax) {
        init();
        int quantity = 0;
        for (Socks socks : socksList) {
            if (socks.getColor().equals(color) && socks.getSize().equals(size) && socks.getCottonRel() >= cottonMin && socks.getCottonRel() <= cottonMax) {
                quantity += socks.getQuantity();
            }
        }
        return quantity;
    }

    @Override
    public boolean updateSocks(Color color, Size size, int cottonMin, int cottonMax) {
        Socks socks = new Socks(color, size, cottonMin, cottonMax);
        for (Socks socks_temp : socksList) {
            if (socks.getColor().equals(socks_temp.getColor()) && socks.getSize().equals(socks_temp.getSize())
                    && socks.getCottonRel() == socks_temp.getCottonRel() && (socks_temp.getQuantity() - socks.getQuantity() >= 0)){
                int index = socksList.indexOf(socks_temp);
                operationsService.addOperation(OperationType.REMOVE, socks);
                socks.setQuantity(socks_temp.getQuantity() - socks.getQuantity());
                socksList.set(index, socks);
                saveToFile();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean removeSocks(Socks socks) {
        boolean flag = false;
        if (socksList.contains(socks)){
            operationsService.addOperation(OperationType.REMOVE, socks);
            flag = socksList.remove(socks);
            saveToFile();
        }
        return flag;
    }

    private void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(socksList);
            filesService.saveSocksToFile(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void readFromFile() {
        String json = filesService.readSocksFromFile();
        try {
            if (!json.isBlank()) {
                socksList = new ObjectMapper().readValue(json, new TypeReference<List<Socks>>() {});
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @PostConstruct
    public void init(){
        readFromFile();
    }
}
