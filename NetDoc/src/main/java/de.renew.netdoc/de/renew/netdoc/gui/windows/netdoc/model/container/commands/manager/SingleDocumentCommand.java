package de.renew.netdoc.model.container.commands.manager;

import de.renew.netdoc.model.container.ContainerManager;
import de.renew.netdoc.model.container.commands.ManagerCommand;
import de.renew.netdoc.model.document.Document;

/**
 * This class creates a Command for the Single-Document .
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public abstract class SingleDocumentCommand extends ManagerCommand {

    /**
     * Creates a new Single-Document Command using the specified container
     * manager and document.
     *
     * @param containerManager the container manager holding the documents.
     * @param document the document to execute the command on.
     * @de.renew.require (containerManager != null)
     * @de.renew.require (document != null)
     */
    protected SingleDocumentCommand(ContainerManager containerManager, Document document) {
        super(containerManager);
        assert (document != null) : "Precondition violated: (document != null)";

        this._document = document;
    }

    /**
     * <p>Returns the document used by this command.</p>
     * <p>To override implement {@link #getDocumentImpl()}.
     * </p>
     *
     * @return the document used by this command.
     * @de.renew.ensure (returnValue != null)
     */
    public final Document getDocument() {
        Document returnValue = this.getDocumentImpl();
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * Returns the document used by this command.
     *
     * @return the document used by this command.
     * @de.renew.ensure (returnValue != null)
     */
    protected Document getDocumentImpl() {
        return this._document;
    }

    /**
     * The document to execute this command on.
     */
    private Document _document;
}