package de.renew.netdoc.model.container.event;


/**
 * Observer listening to close request events.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public interface CloseRequestListener {

    /**
     * Invoked when an object is requesting a close operation. The return value
     * specifies whether the object should be allowed to perform the operation.
     * @param event the corresponding close request event.
     * @return {@code true}, if the object requesting the close operation
     * should be allowed to close;<br>
     * {@code false} otherwise.
     * @de.renew.require (event != null)
     */
    public boolean allowClosing(CloseRequestEvent event);
}