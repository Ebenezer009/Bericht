package de.renew.netdoc.model.document.event;

/**
 * Interface for the observer listening to document change events.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public interface DocumentChangeListener {

    /**
     * Invoked when a document has changed.
     *
     * @param event the corresponding change event.
     * @de.renew.require (event ! = null)
     */
    public void documentChanged(DocumentChangeEvent event);
}