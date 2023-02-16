package ru.catunderglue.socksstore.models.enums;

public enum Size {
    S(23), M(25), L(27), XL(29), XXL(31), XXXL(33);
    private final int size;

    Size(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }
}
