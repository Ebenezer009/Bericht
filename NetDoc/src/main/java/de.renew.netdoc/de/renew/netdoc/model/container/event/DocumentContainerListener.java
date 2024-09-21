package de.renew.netdoc.model.container.event;


/**
 * Observer listening to document container events.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public interface DocumentContainerListener {

    /**
     * Invoked when a container has been closed.
     * @param event the corresponding container event.
     * @de.renew.require (event != null)
     */
    public void containerClosed(DocumentContainerEvent event);

    /**
     * Invoked when a container is about to be closed.
     * @param event the corresponding container event.
     * @de.renew.require (event != null)
     */
    public void containerClosing(DocumentContainerEvent event);

    /**
     * Invoked when a container has been opened.
     * @param event the corresponding container event.
     * @de.renew.require (event != null)
     */
    public void containerOpened(DocumentContainerEvent event);

    /**
     * Invoked when a document has been closed.
     * @param event the corresponding container event.
     * @de.renew.require (event != null)
     */
    public void documentClosed(DocumentContainerEvent event);

    /**
     * Invoked when a document is about to be closed.
     * @param event the corresponding container event.
     * @de.renew.require (event != null)
     */
    public void documentClosing(DocumentContainerEvent event);

    /**
     * Invoked when a document has been opened.
     * @param event the corresponding container event.
     * @de.renew.require (event != null)
     */
    public void documentOpened(DocumentContainerEvent event);
}