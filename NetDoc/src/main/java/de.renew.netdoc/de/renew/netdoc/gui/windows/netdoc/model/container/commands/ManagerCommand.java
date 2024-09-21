package de.renew.netdoc.model.container.commands;

import de.renew.netdoc.model.command.commands.AbstractCommand;
import de.renew.netdoc.model.container.ContainerManager;

/**
 * This class creates a Command for the ContainerManager.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public abstract class ManagerCommand extends AbstractCommand {

    /**
     * Creates a new ContainerManager Command using the specified container
     * manager.
     *
     * @param containerManager the container manager to be used by the new
     *                         command.
     * @de.renew.require (containerManager != null)
     */
    protected ManagerCommand(ContainerManager containerManager) {
        assert (containerManager != null) : "Precondition violated: (containerManager != null)";

        this._containerManager = containerManager;
    }

    /**
     * <p>Returns the container manager used by this command.</p>
     * <p>To override implement {@link #getContainerManagerImpl()}.</p>
     *
     * @return the container manager used by this command.
     * @de.renew.ensure (returnValue != null)
     */
    public final ContainerManager getContainerManager() {
        ContainerManager returnValue = this.getContainerManagerImpl();
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * Returns the container manager used by this command.
     *
     * @return the container manager used by this command.
     * @de.renew.ensure (returnValue != null)
     */
    protected ContainerManager getContainerManagerImpl() {
        return this._containerManager;
    }

    /**
     * The container manager used by this command.
     */
    private ContainerManager _containerManager;
}