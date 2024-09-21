package de.renew.netdoc.model.container.commands.manager;

import de.renew.netdoc.model.container.ContainerManager;
import de.renew.netdoc.model.container.commands.ManagerCommand;
import de.renew.netdoc.model.doctarget.DocTarget;

/**
 * This class creates a Command for the Single-DocTarget .
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public abstract class SingleTargetCommand extends ManagerCommand {

    /**
     * Creates a new Single-Target Command using the specified container
     * manager and documentation target.
     *
     * @param containerManager the container manager holding the targets.
     * @param target the target to execute the command on.
     * @de.renew.require (containerManager != null)
     * @de.renew.require (target != null)
     */
    protected SingleTargetCommand(ContainerManager containerManager, DocTarget target) {
        super(containerManager);
        assert (target != null) : "Precondition violated: (target != null)";

        this._target = target;
    }

    /**
     * <p>Returns the target used by this command.</p>
     * <p>To override implement {@link #getTargetImpl()}.
     * </p>
     *
     * @return the target used by this command.
     * @de.renew.ensure (returnValue != null)
     */
    public final DocTarget getTarget() {
        DocTarget returnValue = this.getTargetImpl();
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * Returns the target used by this command.
     *
     * @return the target used by this command.
     * @de.renew.ensure (returnValue != null)
     */
    protected DocTarget getTargetImpl() {
        return this._target;
    }

    /**
     * The target to execute this command on.
     */
    private DocTarget _target;
}