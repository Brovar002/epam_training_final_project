package by.goncharov.epamsound.service;

/**
 * The type Service exception.
 * @author Goncharov Daniil
 */
public class ServiceException extends Exception {
    /**
     * Instantiates a new Service exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public ServiceException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Service exception.
     *
     * @param cause the cause
     */
    public ServiceException(final Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new Service exception.
     */
    public ServiceException() {
        super();
    }

    /**
     * Instantiates a new Service exception.
     *
     * @param message the message
     */
    public ServiceException(final String message) {
        super(message);
    }
}
