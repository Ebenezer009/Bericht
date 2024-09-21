package de.renew.netdoc.model.container.event;


/**
 * Standard implementation of the DocumentContainerListener interface providing
 * empty method bodies for each event.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public class DocumentContainerAdapter implements DocumentContainerListener {

    /**
     * Creates a new DocumentContainerAdapter.
     */
    public DocumentContainerAdapter() {
    }

    /**
     * <p>Invoked when a container has been closed.</p>
     * <p>To override implement {@link #containerClosedImpl(DocumentContainerEvent)}.</p>
     * @param event the corresponding container event.
     * @de.renew.require (event != null)
     */
    @Override
    public final void containerClosed(DocumentContainerEvent event) {
        assert (event != null) : "Precondition violated: (event != null)";

        this.containerClosedImpl(event);
    }

    /**
     * <p>Invoked when a container is about to be closed.</p>
     * <p>To override implement {@link #containerClosingImpl(DocumentContainerEvent)}.</p>
     *
     * @param event the corresponding container event.
     * @de.renew.require (event != null)
     */
    @Override
    public final void containerClosing(DocumentContainerEvent event) {
        assert (event != null) : "Precondition violated: (event != null)";

        this.containerClosingImpl(event);
    }

    /**
     * <p>Invoked when a container has been opened.</p>
     * <p>To override implement {@link #containerOpenedImpl(DocumentContainerEvent)}.</p>
     *
     * @param event the corresponding container event.
     * @de.renew.require (event != null)
     */
    @Override
    public final void containerOpened(DocumentContainerEvent event) {
        assert (event != null) : "Precondition violated: (event != null)";

        this.containerOpenedImpl(event);
    }

    /**
     * <p>Invoked when a document has been closed.</p>
     * <p>To override implement {@link #documentClosedImpl(DocumentContainerEvent)}.</p>
     *
     * @param event the corresponding container event.
     * @de.renew.require (event != null)
     */
    @Override
    public final void documentClosed(DocumentContainerEvent event) {
        assert (event != null) : "Precondition violated: (event != null)";

        this.documentClosedImpl(event);
    }

    /**
     * <p>Invoked when a document is about to be closed.</p>
     * <p>To override implement {@link #documentClosingImpl(DocumentContainerEvent)}.</p>
     * @param event the corresponding container event.
     * @de.renew.require (event != null)
     */
    @Override
    public final void documentClosing(DocumentContainerEvent event) {
        assert (event != null) : "Precondition violated: (event != null)";

        this.documentClosingImpl(event);
    }

    /**
     * <p>Invoked when a document has been opened.</p>
     * <p>To override implement {@link #documentOpenedImpl(DocumentContainerEvent)}.</p>
     * @param event the corresponding container event.
     * @de.renew.require (event != null)
     */
    @Override
    public final void documentOpened(DocumentContainerEvent event) {
        assert (event != null) : "Precondition violated: (event != null)";

        this.documentOpenedImpl(event);
    }

    /**
     * Invoked when a container has been closed.
     * @param event the corresponding container event.
     * @de.renew.require (event != null)
     */
    protected void containerClosedImpl(DocumentContainerEvent event) {
    }

    /**
     * Invoked when a container is about to be closed.
     * @param event the corresponding container event.
     * @de.renew.require (event != null)
     */
    protected void containerClosingImpl(DocumentContainerEvent event) {
    }

    /**
     * Invoked when a container has been opened.
     * @param event the corresponding container event.
     * @de.renew.require (event != null)
     */
    protected void containerOpenedImpl(DocumentContainerEvent event) {
    }

    /**
     * Invoked when a document has been closed.
     * @param event the corresponding container event.
     * @de.renew.require (event != null)
     */
    protected void documentClosedImpl(DocumentContainerEvent event) {
    }

    /**
     * Invoked when a document is about to be closed.
     * @param event the corresponding container event.
     * @de.renew.require (event != null)
     */
    protected void documentClosingImpl(DocumentContainerEvent event) {
    }

    /**
     * Invoked when a document has been opened.
     * @param event the corresponding container event.
     * @de.renew.require (event != null)
     */
    protected void documentOpenedImpl(DocumentContainerEvent event) {
    }
}