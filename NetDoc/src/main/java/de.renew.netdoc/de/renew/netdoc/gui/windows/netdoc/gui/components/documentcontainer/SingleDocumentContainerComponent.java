package de.renew.netdoc.gui.components.documentcontainer;

import de.renew.netdoc.gui.Component;
import de.renew.netdoc.gui.components.DocumentContainerComponent;
import de.renew.netdoc.model.document.Document;

import java.util.ArrayList;
import java.util.Collection;

/**
 * This class provides the NetDoc DocumentContainer GUI component containing a single document.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public abstract class SingleDocumentContainerComponent extends DocumentContainerComponent {

    /**
     * Creates a new SingleDocumentContainerComponent.
     */
    protected SingleDocumentContainerComponent() {
        this(null);
    }

    /**
     * Creates a new SingleDocumentContainerComponent with the specified
     * parent.
     *
     * @param parent the parent of the new component.
     */
    protected SingleDocumentContainerComponent(Component parent) {
        super(parent);
    }

    /**
     * Returns the document controlled by this component.
     *
     * @return the document controlled by this component;<br>
     * or {@code null}, if this container did not contain a document.
     */
    public abstract Document getDocument();

    /**
     * {@inheritDoc}
     */
    @Override
    public final Document getCurrentDocument() {
        return this.getDocument();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Collection<Document> getDocumentsImpl() {
        Document document = this.getDocument();
        ArrayList<Document> documents = new ArrayList<Document>();
        if (document != null) {
            documents.add(document);
        }
        return documents;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close(boolean forceClose) {
        Document document = this.getDocument();
        if (document != null) {
            this.closeDocument(document, forceClose);
        }
    }
}