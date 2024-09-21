package de.renew.netdoc.model.command.commands;

import de.renew.netdoc.model.command.Command;
import de.renew.netdoc.model.command.CommandException;

/**
 * This class provides the NetDoc Command delegating the execution to another command.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public class DelegationCommand extends AbstractCommand {

    /**
     * Creates a new DelegationCommand using the specified name and inner
     * command.
     *
     * @param name the name of the new command.
     * @param innerCommand the inner command to be used by the new command.
     * @de.renew.require (name != null)
     * @de.renew.require (innerCommand != null)
     */
    public DelegationCommand(String name, Command innerCommand) {
    }

    /**
     * <p>Returns the inner command to be executed by this command.</p>
     * <p>To override implement {@link #getInnerCommandImpl()}.</p>
     *
     * @return the inner command to be executed by this command.
     * @de.renew.ensure (returnValue != null)
     */
    public final Command getInnerCommand() {
        Command returnValue = this.getInnerCommandImpl();
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void executeImpl() throws CommandException {
        this.getInnerCommand().execute();
    }

    /**
     * Returns the inner command to be executed by this command.
     *
     * @return the inner command to be executed by this command.
     * @de.renew.ensure (returnValue != null)
     */
    protected Command getInnerCommandImpl() {
        return this._innerCommand;
    }

    /**
     * The inner command used by this command.
     */
    private Command _innerCommand;
}