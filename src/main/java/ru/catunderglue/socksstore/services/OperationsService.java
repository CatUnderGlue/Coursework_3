package ru.catunderglue.socksstore.services;

import ru.catunderglue.socksstore.models.enums.OperationType;
import ru.catunderglue.socksstore.models.Socks;

public interface OperationsService {
    void addOperation(OperationType type, Socks socks);
}
