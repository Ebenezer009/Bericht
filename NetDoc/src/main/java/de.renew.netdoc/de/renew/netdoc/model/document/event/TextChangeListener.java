package de.renew.netdoc.model.document.event;


/**
 * Observer listening to document part text change events.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public interface TextChangeListener {

    /**
     * Invoked when the text of a document part has changed.
     * @param event the corresponding change event.
     * @de.renew.require (event != null)
     */
    public void textChanged(TextChangeEvent event);
}