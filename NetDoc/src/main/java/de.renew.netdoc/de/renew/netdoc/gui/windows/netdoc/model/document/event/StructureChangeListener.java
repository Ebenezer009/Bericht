package de.renew.netdoc.model.document.event;

/**
 * Interface for the observer listening to document part structure change events.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public interface StructureChangeListener {

    /**
     * Invoked when a sub part has been added.
     *
     * @param event the corresponding change event.
     * @de.renew.require (event ! = null)
     */
    public void partAdded(StructureChangeEvent event);

    /**
     * Invoked when a sub part has been removed.
     *
     * @param event the corresponding change event.
     * @de.renew.require (event ! = null)
     */
    public void partRemoved(StructureChangeEvent event);
}