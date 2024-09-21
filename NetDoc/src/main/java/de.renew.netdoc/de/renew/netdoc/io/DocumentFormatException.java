package de.renew.netdoc.io;

import java.io.IOException;


/**
 * NetDoc document format exception.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public class DocumentFormatException extends IOException {

    /**
     * Creates a new DocumentFormatException with an empty detail message.
     */
    public DocumentFormatException() {
        this("", null);
    }

    /**
     * Creates a new DocumentFormatException with the specified detail message.
     * @param message  the detail message of the new exception.
     */
    public DocumentFormatException(String message) {
        this(message, null);
    }

    /**
     * Creates a new DocumentFormatException with an empty detail message and
     * the specified cause.
     * @param cause  the cause of the new exception.
     */
    public DocumentFormatException(Throwable cause) {
        this("", cause);
    }

    /**
     * Creates a new DocumentFormatException with the specified detail message
     * and cause.
     * @param message  the detail message of the new exception.
     * @param cause  the cause of the new exception.
     */
    public DocumentFormatException(String message, Throwable cause) {
        super(message);

        this.initCause(cause);
    }


    /**
     * Serial Version UID of this class.
     */
    private static final long serialVersionUID = 1L;
}