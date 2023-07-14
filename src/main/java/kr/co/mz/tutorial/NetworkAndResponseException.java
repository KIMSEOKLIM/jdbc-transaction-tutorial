package kr.co.mz.tutorial;

public class NetworkAndResponseException extends RuntimeException {

    public NetworkAndResponseException(String message) {
        super(message);
    }

    public NetworkAndResponseException(String message, Throwable cause) {
        super(message, cause);
    }
}
