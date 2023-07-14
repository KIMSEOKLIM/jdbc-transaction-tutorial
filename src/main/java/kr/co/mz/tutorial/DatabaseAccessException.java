package kr.co.mz.tutorial;

public class DatabaseAccessException extends RuntimeException {
    public DatabaseAccessException(String message) {
        super(message);
    }

    public DatabaseAccessException(String message, Throwable t) {
        super(message, t);
    }
}
