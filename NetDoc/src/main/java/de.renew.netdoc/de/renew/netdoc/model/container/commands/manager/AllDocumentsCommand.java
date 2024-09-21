package de.renew.netdoc.model.container.commands.manager;

import de.renew.netdoc.model.command.Command;
import de.renew.netdoc.model.command.CommandException;
import de.renew.netdoc.model.command.exceptions.CommandCancelException;
import de.renew.netdoc.model.document.Document;
import de.renew.netdoc.model.container.ContainerManager;
import de.renew.netdoc.model.container.commands.ManagerCommand;


/**
 * All-Documents Command.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public abstract class AllDocumentsCommand extends ManagerCommand {

    /**
     * Creates a new All-Documents Command using the specified container
     * manager.
     * @param containerManager the container manager holding the documents.
     * @de.renew.require (containerManager != null)
     */
    protected AllDocumentsCommand(ContainerManager containerManager) {
        super(containerManager);
    }

    /**
     * @inheritDoc
     */
    @Override
    protected void executeImpl() throws CommandException {
        Document[] documents = this.getContainerManager().getDocumentMap()
                        .getDocuments().toArray(new Document[0]);

        try {
            for (int index = 0; index < documents.length; index++) {
                this.createCommand(documents[index]).execute();
            }
        } catch (CommandCancelException e) {
            // do not throw exception on command cancel
        }
    }

    /**
     * Creates a new command for the specified document.
     * @param document the document to create the new command for.
     * @return the new command.
     * @de.renew.require (document != null)
     * @de.renew.ensure (returnValue != null)
     */
    protected abstract Command createCommand(Document document);
}