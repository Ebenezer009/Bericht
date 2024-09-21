package de.renew.netdoc.model.container.commands.manager;

import de.renew.netdoc.model.command.Command;
import de.renew.netdoc.model.command.CommandException;
import de.renew.netdoc.model.container.ContainerManager;
import de.renew.netdoc.model.container.commands.ManagerCommand;
import de.renew.netdoc.model.doctarget.DocTarget;

/**
 * This class creates a Command for the Current-Target .
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public abstract class CurrentTargetCommand extends ManagerCommand {

    /**
     * Creates a new Current-Target Command using the specified container
     * manager.
     *
     * @param containerManager the container manager holding the targets.
     * @de.renew.require (containerManager != null)
     */
    protected CurrentTargetCommand(ContainerManager containerManager) {
        super(containerManager);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isExecutable() {
        return (this.getContainerManager().getCurrentTarget() != null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void executeImpl() throws CommandException {
        DocTarget current = this.getContainerManager().getCurrentTarget();

        if (current == null) {
            throw new CommandException(this, "No target focussed");
        }

        this.createCommand(current).execute();
    }

    /**
     * Creates a new command for the specified target.
     *
     * @param target the target to create the new command for.
     * @return the new command.
     * @de.renew.require (target != null)
     * @de.renew.ensure (returnValue != null)
     */
    protected abstract Command createCommand(DocTarget target);
}