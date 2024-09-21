package de.renew.netdoc.gui.swing.gui.event;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * This class hold the Window listener wrapper requesting each close operation to be allowed.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public abstract class CloseRequestListener extends WindowListenerWrapper {

    /**
     * Creates a new listener wrapping the specified window listeners.
     *
     * @param listenersToWrap the listeners to be wrapped by the new listener.
     * @de.renew.require (listenersToWrap != null)
     * @de.renew.require !java.util.Arrays.asList(listenersToWrap).contains(null)
     */
    public CloseRequestListener(WindowListener[] listenersToWrap) {
        super(listenersToWrap);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void windowClosing(WindowEvent event) {
        if (this.allowClosing(event)) {
            this.fireWindowClosing(event);
        }
    }

    /**
     * Determines whether the specified closing event should be delegated to
     * the wrapped listeners.
     *
     * @param event the closing event to be verified.
     * @return {@code true}, if the specified closing event should be delegated
     * to the wrapped listeners;<br>
     * {@code false} otherwise.
     */
    protected abstract boolean allowClosing(WindowEvent event);
}