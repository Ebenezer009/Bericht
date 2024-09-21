package de.renew.netdoc.model.container.commands.manager;

import de.renew.netdoc.model.command.Command;
import de.renew.netdoc.model.command.CommandException;
import de.renew.netdoc.model.command.exceptions.CommandCancelException;
import de.renew.netdoc.model.container.ContainerManager;
import de.renew.netdoc.model.container.commands.ManagerCommand;
import de.renew.netdoc.model.doctarget.DocTarget;

/**
 * This class creates a Command for the  All-Targets .
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public abstract class AllTargetsCommand extends ManagerCommand {

    /**
     * Creates a new All-Targets Command using the specified container
     * manager.
     *
     * @param containerManager the container manager holding the targets.
     * @de.renew.require (containerManager != null)
     */
    protected AllTargetsCommand(ContainerManager containerManager) {
        super(containerManager);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void executeImpl() throws CommandException {
        DocTarget[] targets = this.getContainerManager().getDocumentMap().getTargets().toArray(new DocTarget[0]);

        try {
            for (int index = 0; index < targets.length; index++) {
                this.createCommand(targets[index]).execute();
            }
        } catch (CommandCancelException e) {
            // do not throw exception on command cancel
        }
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