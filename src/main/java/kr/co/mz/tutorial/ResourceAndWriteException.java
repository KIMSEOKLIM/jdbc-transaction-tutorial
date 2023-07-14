package kr.co.mz.tutorial;

public class ResourceAndWriteException extends RuntimeException {
    public ResourceAndWriteException(String message) {
        super(message);
    }

    public ResourceAndWriteException(String message, Throwable cause) {
        super(message, cause);
    }
}
