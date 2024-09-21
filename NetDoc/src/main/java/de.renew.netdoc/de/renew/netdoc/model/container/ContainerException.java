package de.renew.netdoc.model.container;


/**
 * NetDoc container exception.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public class ContainerException extends Exception {

    /**
     * Creates a new ContainerException with an empty detail message.
     */
    public ContainerException() {
        this("", null);
    }

    /**
     * Creates a new ContainerException with the specified detail message.
     * @param message  the detail message of the new exception.
     */
    public ContainerException(String message) {
        this(message, null);
    }

    /**
     * Creates a new ContainerException with an empty detail message and the
     * specified cause.
     * @param cause  the cause of the new exception.
     */
    public ContainerException(Throwable cause) {
        this("", cause);
    }

    /**
     * Creates a new ContainerException with the specified detail message and
     * cause.
     * @param message  the detail message of the new exception.
     * @param cause  the cause of the new exception.
     */
    public ContainerException(String message, Throwable cause) {
        super(message, cause);
    }


    /**
     * Serial Version UID of this class.
     */
    private static final long serialVersionUID = 1L;
}