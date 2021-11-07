package by.goncharov.epamsound.service;

public class ServiceException extends Exception {
    public ServiceException(final String message, final Throwable cause) {
        super(message, cause);
    }
    public ServiceException(final Throwable cause) {
        super(cause);
    }
    public ServiceException() {
        super();
    }
    public ServiceException(final String message) {
        super(message);
    }
}
