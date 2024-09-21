package de.renew.netdoc.gui.swing.gui.event;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * This class provides the window listener delegating the events to a list of wrapped window listeners.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public class WindowListenerWrapper implements WindowListener {

    /**
     * Creates a new listener wrapping the specified window listeners.
     *
     * @param listenersToWrap the listeners to be wrapped by the new listener.
     * @de.renew.require (listenersToWrap != null)
     * @de.renew.require !java.util.Arrays.asList(listenersToWrap).contains(null)
     */
    public WindowListenerWrapper(WindowListener[] listenersToWrap) {
        assert (listenersToWrap != null) : "Precondition violated: (listenersToWrap != null)";
        assert !java.util.Arrays.asList(listenersToWrap).contains(null) :
                "Precondition violated: !java.util.Arrays.asList(listenersToWrap).contains(null)";

        this._wrappedListeners = listenersToWrap.clone();
    }

    /**
     * <p>Returns the window listeners wrapped by this listener.</p>
     * <p>To override implement {@link #getWrappedListenersImpl()}.</p>
     *
     * @return the window listeners wrapped by this listener.
     * @de.renew.ensure (returnValue != null)
     * @de.renew.ensure !java.util.Arrays.asList(returnValue).contains(null)
     */
    public final WindowListener[] getWrappedListeners() {
        WindowListener[] returnValue = this.getWrappedListenersImpl();
        assert (returnValue != null) : "Precondition violated: (returnValue != null)";
        assert !java.util.Arrays.asList(returnValue).contains(null) :
                "Precondition violated: !java.util.Arrays.asList(returnValue).contains(null)";

        return returnValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void windowOpened(WindowEvent event) {
        this.fireWindowOpened(event);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void windowClosing(WindowEvent event) {
        this.fireWindowClosing(event);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void windowClosed(WindowEvent event) {
        this.fireWindowClosed(event);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void windowIconified(WindowEvent event) {
        this.fireWindowIconified(event);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void windowDeiconified(WindowEvent event) {
        this.fireWindowDeiconified(event);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void windowActivated(WindowEvent event) {
        this.fireWindowActivated(event);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void windowDeactivated(WindowEvent event) {
        this.fireWindowDeactivated(event);
    }

    /**
     * Fires the specified window opened event to all listeners controlled by
     * this listener.
     *
     * @param event the window event to be fired.
     * @de.renew.require (event != null)
     */
    protected void fireWindowOpened(WindowEvent event) {
        WindowListener[] listeners = this.getInternalListeners();
        for (int index = 0; index < listeners.length; index++) {
            listeners[index].windowOpened(event);
        }
    }

    /**
     * Fires the specified window closing event to all listeners controlled by
     * this listener.
     *
     * @param event the window event to be fired.
     * @de.renew.require (event != null)
     */
    protected void fireWindowClosing(WindowEvent event) {
        WindowListener[] listeners = this.getInternalListeners();
        for (int index = 0; index < listeners.length; index++) {
            listeners[index].windowClosing(event);
        }
    }

    /**
     * Fires the specified window closed event to all listeners controlled by
     * this listener.
     *
     * @param event the window event to be fired.
     * @de.renew.require (event != null)
     */
    protected void fireWindowClosed(WindowEvent event) {
        WindowListener[] listeners = this.getInternalListeners();
        for (int index = 0; index < listeners.length; index++) {
            listeners[index].windowClosed(event);
        }
    }

    /**
     * Fires the specified window iconified event to all listeners controlled
     * by this listener.
     *
     * @param event the window event to be fired.
     * @de.renew.require (event != null)
     */
    protected void fireWindowIconified(WindowEvent event) {
        WindowListener[] listeners = this.getInternalListeners();
        for (int index = 0; index < listeners.length; index++) {
            listeners[index].windowIconified(event);
        }
    }

    /**
     * Fires the specified window deiconified event to all listeners controlled
     * by this listener.
     *
     * @param event the window event to be fired.
     * @de.renew.require (event != null)
     */
    protected void fireWindowDeiconified(WindowEvent event) {
        WindowListener[] listeners = this.getInternalListeners();
        for (int index = 0; index < listeners.length; index++) {
            listeners[index].windowDeiconified(event);
        }
    }

    /**
     * Fires the specified window activated event to all listeners controlled
     * by this listener.
     *
     * @param event the window event to be fired.
     * @de.renew.require (event != null)
     */
    protected void fireWindowActivated(WindowEvent event) {
        WindowListener[] listeners = this.getInternalListeners();
        for (int index = 0; index < listeners.length; index++) {
            listeners[index].windowActivated(event);
        }
    }

    /**
     * Fires the specified window deactivated event to all listeners controlled
     * by this listener.
     *
     * @param event the window event to be fired.
     * @de.renew.require (event != null)
     */
    protected void fireWindowDeactivated(WindowEvent event) {
        WindowListener[] listeners = this.getInternalListeners();
        for (int index = 0; index < listeners.length; index++) {
            listeners[index].windowDeactivated(event);
        }
    }

    /**
     * <p>Returns the window listeners wrapped by this listener.</p>
     * <p>Implements {@link #getWrappedListeners()}.</p>
     *
     * @return the window listeners wrapped by this listener.
     * @de.renew.ensure (returnValue != null)
     * @de.renew.ensure !java.util.Arrays.asList(returnValue).contains(null)
     */
    protected WindowListener[] getWrappedListenersImpl() {
        return this.getInternalListeners().clone();
    }

    /**
     * Returns the internal array of the listeners wrapped by this listener.
     *
     * @return the internal array of the listeners wrapped by this listener.
     * @de.renew.ensure (returnValue != null)
     * @de.renew.ensure !java.util.Arrays.asList(returnValue).contains(null)
     */
    protected WindowListener[] getInternalListeners() {
        return this._wrappedListeners;
    }

    /**
     * The window listeners to delegate the events to.
     */
    private WindowListener[] _wrappedListeners;
}