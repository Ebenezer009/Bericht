package de.renew.netdoc.model.document.event;


/**
 * Standard implementation of the DocumentChangeListener interface providing
 * empty method bodies for each event.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public class DocumentChangeAdapter implements DocumentChangeListener {

    /**
     * Creates a new DocumentChangeAdapter.
     */
    public DocumentChangeAdapter() {
    }

    /**
     * <p>Invoked when a document has changed.</p>
     * <p>To override implement {@link #documentChangedImpl(DocumentChangeEvent)}.</p>
     * @param event the corresponding change event.
     * @de.renew.require (event != null)
     */
    @Override
    public final void documentChanged(DocumentChangeEvent event) {
        assert (event != null) : "Precondition violated: (event != null)";

        this.documentChangedImpl(event);
    }

    /**
     * Invoked when a document has changed.
     * @param event the corresponding change event.
     * @de.renew.require (event != null)
     */
    protected void documentChangedImpl(DocumentChangeEvent event) {
    }
}