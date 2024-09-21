package de.renew.netdoc.model.command;


/**
 * NetDoc command exception.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public class CommandException extends Exception {

    /**
     * Creates a new CommandException with the specified command.
     * @param command  the command that caused this exception.
     * @de.renew.require (command != null)
     */
    public CommandException(Command command) {
        this(command, "", null);
    }

    /**
     * Creates a new CommandException with the specified command and detail
     * message.
     * @param command  the command that caused this exception.
     * @param message  the detail message of the new exception.
     * @de.renew.require (command != null)
     */
    public CommandException(Command command, String message) {
        this(command, message, null);
    }

    /**
     * Creates a new CommandException with the specified command and cause.
     * @param command  the command that caused this exception.
     * @param cause  an additional Throwable that caused the new exception.
     * @de.renew.require (command != null)
     */
    public CommandException(Command command, Throwable cause) {
        this(command, "", cause);
    }

    /**
     * Creates a new CommandException with the specified command, detail
     * message and cause.
     * @param command  the command that caused this exception.
     * @param message  the detail message of the new exception.
     * @param cause  an additional Throwable that caused the new exception.
     * @de.renew.require (command != null)
     */
    public CommandException(Command command, String message, Throwable cause) {
        super(message, cause);
        assert (command != null) : "Precondition violated: (command != null)";

        this._command = command;
    }


    /**
     * <p>Returns the command that caused this exception.</p>
     * <p>To override implement {@link #getCommandImpl()}.</p>
     * @return the command that caused this exception.
     * @de.renew.ensure (returnValue != null)
     */
    public final Command getCommand() {
        Command returnValue = this.getCommandImpl();
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * <p>Determines the string representation of this exception.</p>
     * <p>To override implement {@link #toStringImpl()}.</p>
     * @return the string representation of this exception.
     * @de.renew.ensure (returnValue != null)
     */
    @Override
    public final String toString() {
        String returnValue = this.toStringImpl();
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }


    /**
     * Returns the command that caused this exception.
     * @return the command that caused this exception.
     * @de.renew.ensure (returnValue != null)
     */
    protected Command getCommandImpl() {
        return this._command;
    }

    /**
     * Determines the string representation of this exception.
     * @return the string representation of this exception.
     * @de.renew.ensure (returnValue != null)
     */
    protected String toStringImpl() {
        String command = this.getCommand().toString();
        String message = this.getMessage();
        if ((message == null) || message.equals("")) {
            return "(Command: " + command + ")";
        } else {
            return message + " (Command: " + command + ")";
        }
    }


    /**
     * The command that caused this exception.
     */
    private Command _command;


    /**
     * Serial Version UID of this class.
     */
    private static final long serialVersionUID = 1L;
}