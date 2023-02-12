package ru.catunderglue.socksstore.models.enums;

public enum OperationType {
    ADD("Приёмка"), REMOVE("Выдача");
    private final String translate;

    OperationType(String translate) {
        this.translate = translate;
    }

    public String getTranslate() {
        return translate;
    }
}
