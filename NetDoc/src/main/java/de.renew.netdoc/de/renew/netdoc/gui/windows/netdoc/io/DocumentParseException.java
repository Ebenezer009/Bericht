package de.renew.netdoc.io;

import java.io.IOException;


/**
 * This class is a custom exception class that extends IOException and
 * is designed to handle exceptions related to document parsing.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public class DocumentParseException extends IOException {

    /**
     * Creates a new DocumentParseException with an empty detail message.
     */
    public DocumentParseException() {
        this("", null);
    }

    /**
     * Creates a new DocumentParseException with the specified detail message.
     * @param message  the detail message of the new exception.
     */
    public DocumentParseException(String message) {
        this(message, null);
    }

    /**
     * Creates a new DocumentParseException with an empty detail message and
     * the specified cause.
     * @param cause  the cause of the new exception.
     */
    public DocumentParseException(Throwable cause) {
        this("", cause);
    }

    /**
     * Creates a new DocumentParseException with the specified detail message
     * and cause.
     * @param message  the detail message of the new exception.
     * @param cause  the cause of the new exception.
     */
    public DocumentParseException(String message, Throwable cause) {
        super(message);

        this.initCause(cause);
    }


    /**
     * Serial Version UID of this class.
     */
    private static final long serialVersionUID = 1L;
}