package de.renew.netdoc.gui.swing.gui.event;

import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.Semaphore;

/**
 * This class  provides the listener for different types of Window connection.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public class WindowConnectionListener extends WindowAdapter implements ComponentListener {

    /**
     * Connected-bottom state identifier.
     */
    public static final Object STATE_CONNECTED_BOTTOM = "connected-bottom";

    /**
     * Connected-left state identifier.
     */
    public static final Object STATE_CONNECTED_LEFT = "connected-left";

    /**
     * Connected-right state identifier.
     */
    public static final Object STATE_CONNECTED_RIGHT = "connected-right";

    /**
     * Connected-top state identifier.
     */
    public static final Object STATE_CONNECTED_TOP = "connected-top";

    /**
     * Not-connected state identifier.
     */
    public static final Object STATE_NOT_CONNECTED = "not-connected";

    /**
     * Returns the first WindowConnectionListener found within the specified
     * java window.
     *
     * @param connectedWindow the window whose connection listener is to be
     * returned.
     * @return the first connection listener found;<br>
     * or <code>null</code>, if no connection listener was found.
     * @de.renew.require (connectedWindow != null)
     */
    public static WindowConnectionListener getConnectionListener(Window connectedWindow) {
        assert (connectedWindow != null) : "Precondition violated: (connectedWindow != null)";

        ComponentListener[] listeners = connectedWindow.getComponentListeners();
        for (int index = 0; index < listeners.length; index++) {
            if (listeners[index] instanceof WindowConnectionListener) {
                return (WindowConnectionListener) listeners[index];
            }
        }
        return null;
    }

    /**
     * Determines the connection state a given connection listener should use
     * for the second window while using the specified state for the first
     * window.
     *
     * @param state the connection state whose opposite state is to be
     * determined.
     * @return the opposite connection state.
     * @de.renew.require (state != null)
     */
    public static Object getOppositeConnectionState(Object state) {
        assert (state != null) : "Precondition violated: (state != null)";

        Object returnValue = state;
        if (state.equals(WindowConnectionListener.STATE_CONNECTED_BOTTOM)) {
            returnValue = WindowConnectionListener.STATE_CONNECTED_TOP;
        } else if (state.equals(WindowConnectionListener.STATE_CONNECTED_TOP)) {
            returnValue = WindowConnectionListener.STATE_CONNECTED_BOTTOM;
        } else if (state.equals(WindowConnectionListener.STATE_CONNECTED_LEFT)) {
            returnValue = WindowConnectionListener.STATE_CONNECTED_RIGHT;
        } else if (state.equals(WindowConnectionListener.STATE_CONNECTED_RIGHT)) {
            returnValue = WindowConnectionListener.STATE_CONNECTED_LEFT;
        }
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * Creates a new WindowConnectionListener using the specified windows and
     * the not-connected state.
     *
     * @param window1 the first window to connect.
     * @param window2 the second window to connect.
     * @de.renew.require (window1 != null)
     * @de.renew.require (window2 != null)
     */
    public WindowConnectionListener(Window window1, Window window2) {
        this(window1, window2, WindowConnectionListener.STATE_NOT_CONNECTED);
    }

    /**
     * Creates a new WindowConnectionListener using the specified windows and
     * connection state.
     *
     * @param window1 the first window to connect.
     * @param window2 the second window to connect.
     * @param connectionState the initial connection state.
     * @de.renew.require (window1 != null)
     * @de.renew.require (window2 != null)
     * @de.renew.require (connectionState != null)
     */
    public WindowConnectionListener(Window window1, Window window2, Object connectionState) {
        assert (window1 != null) : "Precondition violated: (window1 != null)";
        assert (window2 != null) : "Precondition violated: (window2 != null)";
        assert (connectionState != null) : "Precondition violated: (connectionState != null)";

        this._window1 = window1;
        this._window2 = window2;
        this._connectionState = connectionState;
        this._stateChanged = true;
        this._defaultHeight = 0;
        this._defaultWidth = 0;
        this._oldX1 = Integer.MIN_VALUE;
        this._oldX2 = Integer.MIN_VALUE;
        this._oldY1 = Integer.MIN_VALUE;
        this._oldY2 = Integer.MIN_VALUE;
        this._updateSemaphore = new Semaphore(1, true);
    }

    /**
     * <p>Returns the first window involved in the connection observed by this
     * listener.</p>
     * <p>To override implement {@link #getWindow1Impl()}.</p>
     *
     * @return the first window involved in the connection observed by this
     * listener.
     * @de.renew.ensure (returnValue != null)
     */
    public final Window getWindow1() {
        Window returnValue = this.getWindow1Impl();
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * <p>Returns the second window involved in the connection observed by this
     * listener.</p>
     * <p>To override implement {@link #getWindow2Impl()}.</p>
     *
     * @return the second window involved in the connection observed by this
     * listener.
     * @de.renew.ensure (returnValue != null)
     */
    public final Window getWindow2() {
        Window returnValue = this.getWindow2Impl();
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * <p>Returns the current connection state of this listener.</p>
     * <p>To override implement {@link #getConnectionStateImpl()}.</p>
     *
     * @return the current connection state of this listener.
     * @de.renew.ensure (returnValue != null)
     */
    public final Object getConnectionState() {
        Object returnValue = this.getConnectionStateImpl();
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * Returns the default height of the first window.
     *
     * @return the default height of the first window.
     */
    public int getDefaultHeight() {
        return this._defaultHeight;
    }

    /**
     * Returns the default width of the first window.
     *
     * @return the default width of the first window.
     */
    public int getDefaultWidth() {
        return this._defaultWidth;
    }

    /**
     * <p>Sets the first window involved in the connection observed by this
     * listener to the specified window.</p>
     * <p>To override implement {@link #setWindow1Impl(Window)}.
     * </p>
     *
     * @param window the new first window.
     * @de.renew.require (window != null)
     */
    public final void setWindow1(Window window) {
        assert (window != null) : "Precondition violated: (window != null)";

        this.setWindow1Impl(window);
    }

    /**
     * <p>Sets the second window involved in the connection observed by this
     * listener to the specified window.</p>
     * <p>To override implement {@link #setWindow2Impl(Window)}.
     * </p>
     *
     * @param window the new second window.
     * @de.renew.require (window != null)
     */
    public final void setWindow2(Window window) {
        assert (window != null) : "Precondition violated: (window != null)";

        this.setWindow2Impl(window);
    }

    /**
     * <p>Sets the connection state of this listener to the specified state.
     * </p>
     * <p>To override implement {@link #setConnectionStateImpl(Object)}.
     * </p>
     *
     * @param connectionState the new connection state.
     * @de.renew.require (connectionState != null)
     * @de.renew.require WindowConnectionListener.isValidState(connectionState)
     */
    public final void setConnectionState(Object connectionState) {
        assert (connectionState != null) : "Precondition violated: (connectionState != null)";

        this.setConnectionStateImpl(connectionState);
    }

    /**
     * Sets the default height of the first window to the specified height.
     *
     * @param height the default new height of the first window.
     */
    public void setDefaultHeight(int height) {
        this._defaultHeight = height;
    }

    /**
     * Sets the default width of the first window to the specified width.
     *
     * @param width the new default width of the first window.
     */
    public void setDefaultWidth(int width) {
        this._defaultWidth = width;
    }

    /**
     * Tells this listener to update its connection. This is done by updating
     * the size and position of the first window.
     */
    public void update() {
        this.updateSize(this.getWindow1(), this.getWindow2());
        this.updatePosition(this.getWindow1(), this.getWindow2());
    }

    /**
     * Invoked when the component's position changes.
     *
     * @param event the corresponding event.
     */
    @Override
    public void componentMoved(ComponentEvent event) {
        if (!this.getConnectionState().equals(WindowConnectionListener.STATE_NOT_CONNECTED)) {
            try {
                Window movedWindow = (Window) event.getComponent();
                if (movedWindow != null) {
                    this.updatePosition(this.getOppositeWindow(movedWindow), movedWindow);
                }
            } catch (Exception e) {
            }
        }
    }

    /**
     * Invoked when the component's size changes.
     *
     * @param event the corresponding event.
     */
    @Override
    public void componentResized(ComponentEvent event) {
        if (!this.getConnectionState().equals(WindowConnectionListener.STATE_NOT_CONNECTED)) {
            try {
                Window resizedWindow = (Window) event.getComponent();
                if (resizedWindow != null) {
                    Window oppositeWindow = this.getOppositeWindow(resizedWindow);
                    this.updatePosition(oppositeWindow, resizedWindow);
                    this.updateSize(oppositeWindow, resizedWindow);
                }
            } catch (Exception e) {
            }
        }
    }

    /**
     * Invoked when a window is de-iconified.
     *
     * @param event the corresponding event.
     */
    @Override
    public void windowDeiconified(WindowEvent event) {
        if (!this.getConnectionState().equals(WindowConnectionListener.STATE_NOT_CONNECTED)) {
            try {
                Window window = (Window) event.getComponent();
                if (window != null) {
                    Frame oppositeFrame = (Frame) this.getOppositeWindow(window);
                    if (oppositeFrame.getState() == Frame.ICONIFIED) {
                        oppositeFrame.setState(Frame.NORMAL);
                    }
                }
            } catch (Exception e) {
            }
        }
    }

    /**
     * Invoked when a window is iconified.
     *
     * @param event the corresponding event.
     */
    @Override
    public void windowIconified(WindowEvent event) {
        if (!this.getConnectionState().equals(WindowConnectionListener.STATE_NOT_CONNECTED)) {
            try {
                Window window = (Window) event.getComponent();
                if (window != null) {
                    Frame oppositeFrame = (Frame) this.getOppositeWindow(window);
                    if (oppositeFrame.getState() == Frame.NORMAL) {
                        oppositeFrame.setState(Frame.ICONIFIED);
                    }
                }
            } catch (NullPointerException e) {
            }
        }
    }

    /**
     * Invoked when the component has been made invisible.
     *
     * @param event the corresponding event.
     */
    @Override
    public void componentHidden(ComponentEvent event) {
    }

    /**
     * Invoked when the component has been made visible.
     *
     * @param event the corresponding event.
     */
    @Override
    public void componentShown(ComponentEvent event) {
    }

    /**
     * Determines the connection state for the specified window.
     *
     * @param window the window whose connection state is to determine.
     * @return returns the window
     * @de.renew.require (window != null)
     * @de.renew.require (( window = = this.getWindow1 ()) ||
     * (window == this.getWindow2()))
     * @de.renew.ensure (returnValue != null)
     */
    protected Object getConnectionState(Window window) {
        return (window == this.getWindow1()) ? this.getConnectionState() :
                WindowConnectionListener.getOppositeConnectionState(this.getConnectionState());
    }

    /**
     * Determines the x-position of the specified window after the last update.
     *
     * @param window the window whose former position is to determine.
     * @return the former x-position.
     * @de.renew.require (window != null)
     * @de.renew.require (( window = = this.getWindow1 ()) ||
     * (window == this.getWindow2()))
     */
    protected int getOldX(Window window) {
        return (window == this.getWindow1()) ? this._oldX1 : this._oldX2;
    }

    /**
     * Determines the y-position of the specified window after the last update.
     *
     * @param window the window whose former position is to determine.
     * @return the former y-position.
     * @de.renew.require (window != null)
     * @de.renew.require (( window = = this.getWindow1 ()) ||
     * (window == this.getWindow2()))
     */
    protected int getOldY(Window window) {
        return (window == this.getWindow1()) ? this._oldY1 : this._oldY2;
    }

    /**
     * Determines the opposite window involved in the connection observed by
     * this listener.
     *
     * @param window one of the windows of the connection.
     * @return the opposite window.
     * @de.renew.require (window != null)
     * @de.renew.require (( window = = this.getWindow1 ()) ||
     * (window == this.getWindow2()))
     * @de.renew.ensure (returnValue != null)
     * @de.renew.ensure (( returnValue = = this.getWindow1 ()) ||
     * (returnValue == this.getWindow2()))
     */
    protected Window getOppositeWindow(Window window) {
        return (window == this.getWindow1()) ? this.getWindow2() : this.getWindow1();
    }

    /**
     * Sets the former x-position of the specified window.
     *
     * @param window the window whose former position is to set.
     * @param oldX the new former x-position.
     * @de.renew.require (window != null)
     * @de.renew.require (( window = = this.getWindow1 ()) ||
     * (window == this.getWindow2()))
     */
    protected void setOldX(Window window, int oldX) {
        if (window == this.getWindow1()) {
            this._oldX1 = oldX;
        } else {
            this._oldX2 = oldX;
        }
    }

    /**
     * Sets the former y-position of the specified window.
     *
     * @param window the window whose former position is to set.
     * @param oldY the new former y-position.
     * @de.renew.require (window != null)
     * @de.renew.require (( window = = this.getWindow1 ()) ||
     * (window == this.getWindow2()))
     */
    protected void setOldY(Window window, int oldY) {
        if (window == this.getWindow1()) {
            this._oldY1 = oldY;
        } else {
            this._oldY2 = oldY;
        }
    }

    /**
     * Updates the position of the specified window depending on the position
     * of the specified connected window and the current connection state.
     *
     * @param windowToUpdate the window whose position is to be updated.
     * @param oppositeWindow the connected window.
     * @de.renew.require (windowToUpdate != null)
     * @de.renew.require (oppositeWindow != null)
     */
    protected synchronized void updatePosition(Window windowToUpdate, Window oppositeWindow) {
        try {
            this._updateSemaphore.acquire();
        } catch (InterruptedException e) {
            return;
        }
        try {
            Object connectionState = this.getConnectionState(windowToUpdate);
            if (connectionState.equals(WindowConnectionListener.STATE_NOT_CONNECTED)) {
                return;
            }

            if ((this.getOldX(oppositeWindow) == oppositeWindow.getX()) &&
                    (this.getOldY(oppositeWindow) == oppositeWindow.getY())) {
                this.setOldX(oppositeWindow, Integer.MIN_VALUE);
                this.setOldY(oppositeWindow, Integer.MIN_VALUE);
                return;
            }

            int newX = 0;
            int newY = 0;

            if (connectionState.equals(WindowConnectionListener.STATE_CONNECTED_BOTTOM)) {
                newX = oppositeWindow.getX();
                newY = oppositeWindow.getY() + oppositeWindow.getHeight();
            } else if (connectionState.equals(WindowConnectionListener.STATE_CONNECTED_LEFT)) {
                newX = oppositeWindow.getX() - windowToUpdate.getWidth();
                newY = oppositeWindow.getY();
            } else if (connectionState.equals(WindowConnectionListener.STATE_CONNECTED_RIGHT)) {
                newX = oppositeWindow.getX() + oppositeWindow.getWidth();
                newY = oppositeWindow.getY();
            } else { // if (connectionState.equals(
                //       WindowConnectionListener.STATE_CONNECTED_TOP)) {
                newX = oppositeWindow.getX();
                newY = oppositeWindow.getY() - windowToUpdate.getHeight();
            }

            if ((windowToUpdate.getX() != newX) || (windowToUpdate.getY() != newY)) {
                this.setOldX(windowToUpdate, windowToUpdate.getX());
                this.setOldY(windowToUpdate, windowToUpdate.getY());
                windowToUpdate.setLocation(newX, newY);
            }
        } finally {
            this._updateSemaphore.release();
        }
    }

    /**
     * Updates the size of the specified window depending on the size of the
     * specified connected window and the current connection state.
     *
     * @param windowToUpdate the window whose size is to be updated.
     * @param oppositeWindow the connected window.
     * @de.renew.require (windowToUpdate != null)
     * @de.renew.require (oppositeWindow != null)
     */
    protected synchronized void updateSize(Window windowToUpdate, Window oppositeWindow) {
        try {
            this._updateSemaphore.acquire();
        } catch (InterruptedException e) {
            return;
        }
        try {
            Object connectionState = this.getConnectionState(windowToUpdate);
            if (connectionState.equals(WindowConnectionListener.STATE_NOT_CONNECTED)) {
                return;
            }

            int newHeight = 0;
            int newWidth = 0;

            if (connectionState.equals(WindowConnectionListener.STATE_CONNECTED_BOTTOM) ||
                    connectionState.equals(WindowConnectionListener.STATE_CONNECTED_TOP)) {
                if (this._stateChanged && (windowToUpdate == this.getWindow1())) {
                    newHeight = this.getDefaultHeight();
                    this._stateChanged = false;
                }
                if (newHeight == 0) {
                    newHeight = windowToUpdate.getHeight();
                }
                newWidth = oppositeWindow.getWidth();
            } else {
                if (this._stateChanged && (windowToUpdate == this.getWindow1())) {
                    newWidth = this.getDefaultWidth();
                    this._stateChanged = false;
                }
                if (newWidth == 0) {
                    newWidth = windowToUpdate.getWidth();
                }
                newHeight = oppositeWindow.getHeight();
            }

            if ((windowToUpdate.getHeight() != newHeight) || (windowToUpdate.getWidth() != newWidth)) {
                windowToUpdate.setSize(newWidth, newHeight);
            }
        } finally {
            this._updateSemaphore.release();
        }
    }

    /**
     * Returns the first window involved in the connection observed by this
     * listener.
     *
     * @return the first window involved in the connection observed by this
     * listener.
     * @de.renew.ensure (returnValue != null)
     */
    protected Window getWindow1Impl() {
        return this._window1;
    }

    /**
     * Returns the second window involved in the connection observed by this
     * listener.
     *
     * @return the second window involved in the connection observed by this
     * listener.
     * @de.renew.ensure (returnValue != null)
     */
    protected Window getWindow2Impl() {
        return this._window2;
    }

    /**
     * Returns the current connection state of this listener.
     *
     * @return the current connection state of this listener.
     * @de.renew.ensure (returnValue != null)
     */
    protected Object getConnectionStateImpl() {
        return this._connectionState;
    }

    /**
     * Sets the first window involved in the connection observed by this
     * listener to the specified window.
     *
     * @param window the new first window.
     * @de.renew.require (window != null)
     */
    protected void setWindow1Impl(Window window) {
        this._window1 = window;
    }

    /**
     * Sets the second window involved in the connection observed by this
     * listener to the specified window.
     *
     * @param window the new second window.
     * @de.renew.require (window != null)
     */
    protected void setWindow2Impl(Window window) {
        this._window2 = window;
    }

    /**
     * Sets the connection state of this listener to the specified state.
     *
     * @param connectionState the new connection state.
     * @de.renew.require (connectionState != null)
     */
    protected void setConnectionStateImpl(Object connectionState) {
        if (this._connectionState != connectionState) {
            this._connectionState = connectionState;
            this._stateChanged = true;
        }
    }

    /**
     * The first connected window.
     */
    private Window _window1;

    /**
     * The second connected window.
     */
    private Window _window2;

    /**
     * The current connection state.
     */
    private Object _connectionState;

    /**
     * Specifies whether the connection state has changed recently.
     */
    private boolean _stateChanged;

    /**
     * The default height of the first window.
     */
    private int _defaultHeight;

    /**
     * The default width of the first window.
     */
    private int _defaultWidth;

    /**
     * The x-position of the first window after the last position update.
     */
    private int _oldX1;

    /**
     * The x-position of the second window after the last position update.
     */
    private int _oldX2;

    /**
     * The y-position of the first window after the last position update.
     */
    private int _oldY1;

    /**
     * The y-position of the second window after the last position update.
     */
    private int _oldY2;

    /**
     * Sempahore used for position and size updates.
     */
    private Semaphore _updateSemaphore;
}