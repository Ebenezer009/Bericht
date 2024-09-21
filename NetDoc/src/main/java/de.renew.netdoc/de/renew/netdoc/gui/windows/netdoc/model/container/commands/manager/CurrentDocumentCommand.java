package de.renew.netdoc.model.container.commands.manager;

import de.renew.netdoc.model.command.Command;
import de.renew.netdoc.model.command.CommandException;
import de.renew.netdoc.model.container.ContainerManager;
import de.renew.netdoc.model.container.commands.ManagerCommand;
import de.renew.netdoc.model.document.Document;

/**
 * This class creates a Command for the Current-Document .
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public abstract class CurrentDocumentCommand extends ManagerCommand {

    /**
     * Creates a new Current-Document Command using the specified container
     * manager.
     *
     * @param containerManager the container manager holding the documents.
     * @de.renew.require (containerManager != null)
     */
    protected CurrentDocumentCommand(ContainerManager containerManager) {
        super(containerManager);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isExecutable() {
        return (this.getContainerManager().getCurrentDocument() != null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void executeImpl() throws CommandException {
        Document current = this.getContainerManager().getCurrentDocument();

        if (current == null) {
            throw new CommandException(this, "No document focussed");
        }

        this.createCommand(current).execute();
    }

    /**
     * Creates a new command for the specified document.
     *
     * @param document the document to create the new command for.
     * @return the new command.
     * @de.renew.require (document != null)
     * @de.renew.ensure (returnValue != null)
     */
    protected abstract Command createCommand(Document document);
}