package kr.co.mz.tutorial;

public class RuntimeServletException extends RuntimeException {

    public RuntimeServletException(String message) {
        super(message);
    }

    public RuntimeServletException(String message, Throwable cause) {
        super(message, cause);
    }
}
