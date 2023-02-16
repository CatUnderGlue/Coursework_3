package ru.catunderglue.socksstore.models.enums;

public enum Color {
    RED("Красный"), GREEN("Зелёный"), BLUE("Синий"), YELLOW("Жёлтый"), ORANGE("Оранжевый"),
    PURPLE("Фиолетовый"), BROWN("Коричневый"), PINK("Розовый"), VIOLET("Лиловый"), GREY("Серый"),
    BLACK("Чёрный"), WHITE("Белый");
    private final String russian;

    Color(String russian) {
        this.russian = russian;
    }

    public String getRussian() {
        return russian;
    }
}
