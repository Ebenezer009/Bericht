package de.renew.netdoc.model.command.exceptions;

import de.renew.netdoc.model.command.Command;
import de.renew.netdoc.model.command.CommandException;

/**
 * This class is a custom exception class that extends CommandException and is
 * designed to handle exceptions related to the NetDoc command.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public class CommandCancelException extends CommandException {

    /**
     * Creates a new CommandException with the specified command.
     *
     * @param command the command that caused this exception.
     * @de.renew.require (command != null)
     */
    public CommandCancelException(Command command) {
        this(command, "");
    }

    /**
     * Creates a new CommandException with the specified command and detail
     * message.
     *
     * @param command the command that caused this exception.
     * @param message the detail message of the new exception.
     * @de.renew.require (command != null)
     */
    public CommandCancelException(Command command, String message) {
        super(command, message);
    }

    /**
     * Serial Version UID of this class.
     */
    private static final long serialVersionUID = 1L;
}