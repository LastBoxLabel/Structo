package tech.lastbox.structo.model.types;

public enum Sender {
    USER("user"),
    MODEL("model");

    private final String value;

    Sender(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }
}
