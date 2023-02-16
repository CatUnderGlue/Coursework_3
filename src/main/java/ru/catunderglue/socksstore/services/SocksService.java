package ru.catunderglue.socksstore.services;

import ru.catunderglue.socksstore.models.enums.Color;
import ru.catunderglue.socksstore.models.enums.Size;
import ru.catunderglue.socksstore.models.Socks;

import java.util.List;

public interface SocksService {
    List<Socks> getAllSocks();

    Socks addSocks(Socks socks);
    int getSocks(Color color, Size size, int cottonMin, int cottonMax);
    boolean updateSocks(Socks socks);
    boolean removeSocks(Socks socks);
}
