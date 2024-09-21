package de.renew.netdoc.model.command;


/**
 * NetDoc Command.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public interface Command {

    /**
     * Executes this command.
     * @throws CommandException  if an error occured while executing the
     * command.
     * @de.renew.require this.isExecutable()
     */
    public void execute() throws CommandException;

    /**
     * Returns the name of this command.
     * @return the name of this command.
     * @de.renew.ensure (returnValue != null)
     */
    public String getName();

    /**
     * Determines whether this command is executable.
     * @return <code>true</code>, if this command is executable;<br>
     * <code>false</code> otherwise.
     */
    public boolean isExecutable();
}