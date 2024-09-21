package de.renew.netdoc.model.document.event;


/**
 * Standard implementation of the StructureChangeListener interface providing
 * empty method bodies for each event.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public class StructureChangeAdapter implements StructureChangeListener {

    /**
     * Creates a new StructureChangeAdapter.
     */
    public StructureChangeAdapter() {
    }

    /**
     * <p>Invoked when a sub part has been added.</p>
     * <p>To override implement {@link
     * #partAddedImpl(StructureChangeEvent)}.</p>
     * @param event the corresponding change event.
     * @de.renew.require (event != null)
     */
    @Override
    public void partAdded(StructureChangeEvent event) {
        assert (event != null) : "Precondition violated: (event != null)";

        this.partAddedImpl(event);
    }

    /**
     * <p>Invoked when a sub part has been removed.</p>
     * <p>To override implement {@link
     * #partRemovedImpl(StructureChangeEvent)}.</p>
     * @param event the corresponding change event.
     * @de.renew.require (event != null)
     */
    @Override
    public void partRemoved(StructureChangeEvent event) {
        assert (event != null) : "Precondition violated: (event != null)";

        this.partRemovedImpl(event);
    }

    /**
     * Invoked when a sub part has been added.
     * @param event the corresponding change event.
     * @de.renew.require (event != null)
     */
    protected void partAddedImpl(StructureChangeEvent event) {
    }

    /**
     * Invoked when a sub part has been removed.
     * @param event the corresponding change event.
     * @de.renew.require (event != null)
     */
    protected void partRemovedImpl(StructureChangeEvent event) {
    }
}