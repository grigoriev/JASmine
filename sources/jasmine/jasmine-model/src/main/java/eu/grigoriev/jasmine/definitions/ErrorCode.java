package eu.grigoriev.jasmine.definitions;

public enum ErrorCode {
    NOT_FOUND(2001),
    ALREADY_EXISTS(2002);

    private int value;

    ErrorCode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
