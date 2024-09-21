package de.renew.netdoc.model.command.commands;

import de.renew.netdoc.model.command.Command;
import de.renew.netdoc.model.command.CommandException;


/**
 * Abstract NetDoc Command.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public abstract class AbstractCommand implements Command {

    /**
     * <p>Executes this command.</p>
     * <p>To override implement {@link #executeImpl()}.</p>
     * @throws CommandException  if an error occured while executing the
     * command.
     * @de.renew.require this.isExecutable()
     */
    @Override
    public final void execute() throws CommandException {
        assert this.isExecutable() : "Precondition violated: this.isExecutable()";

        this.executeImpl();
    }

    /**
     * <p>Returns the name of this command.</p>
     * <p>To override implement {@link #getNameImpl()}.</p>
     * @return the name of this command.
     * @de.renew.ensure (returnValue != null)
     */
    @Override
    public final String getName() {
        String returnValue = this.getNameImpl();
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean isExecutable() {
        return true;
    }

    /**
     * Returns the name of this command.
     * @return the name of this command.
     * @de.renew.ensure (returnValue != null)
     */
    protected String getNameImpl() {
        return "";
    }

    /**
     * Executes this command.
     * @throws CommandException  if an error occured while executing the
     * command.
     * @de.renew.require this.isExecutable()
     */
    protected abstract void executeImpl() throws CommandException;
}