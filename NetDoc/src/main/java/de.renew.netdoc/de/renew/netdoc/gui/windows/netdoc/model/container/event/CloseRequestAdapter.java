package de.renew.netdoc.model.container.event;


/**
 * Standard implementation of the CloseRequestListener interface providing
 * empty method bodies for each event.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public class CloseRequestAdapter implements CloseRequestListener {

    /**
     * Creates a new CloseRequestAdapter.
     */
    public CloseRequestAdapter() {
    }

    /**
     * <p>Invoked when an object is requesting a close operation. The return
     * value specifies whether the object should be allowed to perform the
     * operation.</p>
     * <p>To override implement {@link #allowClosingImpl(CloseRequestEvent)}.</p>
     * @param event the corresponding close request event.
     * @return {@code true}, if the object requesting the close operation
     * should be allowed to close;<br>
     * {@code false} otherwise.
     * @de.renew.require (event != null)
     */
    @Override
    public final boolean allowClosing(CloseRequestEvent event) {
        assert (event != null) : "Precondition violated: (event != null)";

        return this.allowClosingImpl(event);
    }

    /**
     * Invoked when an object is requesting a close operation. The return value
     * specifies whether the object should be allowed to perform the operation.
     * @param event the corresponding close request event.
     * @return {@code true}, if the object requesting the close operation
     * should be allowed to close;<br>
     * {@code false} otherwise.
     * @de.renew.require (event != null)
     */
    protected boolean allowClosingImpl(CloseRequestEvent event) {
        return true;
    }
}