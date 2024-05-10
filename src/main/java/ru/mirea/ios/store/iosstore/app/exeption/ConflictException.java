package ru.mirea.ios.store.iosstore.app.exeption;

public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}
