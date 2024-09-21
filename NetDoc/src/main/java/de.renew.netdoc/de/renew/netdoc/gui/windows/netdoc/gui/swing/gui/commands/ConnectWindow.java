package de.renew.netdoc.gui.swing.gui.commands;

import de.renew.netdoc.gui.swing.gui.Window;
import de.renew.netdoc.gui.swing.gui.event.WindowConnectionListener;
import de.renew.netdoc.model.command.CommandException;
import de.renew.netdoc.model.command.commands.AbstractCommand;

import javax.swing.JFrame;


/**
 * This class creates a Connect-Window via Command.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public class ConnectWindow extends AbstractCommand {

    /**
     * The name of this command.
     */
    public static final String NAME = "Connect Window";

    /**
     * Connect-Bottom parameter.
     */
    public static final String DIRECTION_BOTTOM = "bottom";

    /**
     * Connect-Left parameter.
     */
    public static final String DIRECTION_LEFT = "left";

    /**
     * Connect-None parameter.
     */
    public static final String DIRECTION_NONE = "none";

    /**
     * Connect-Right parameter.
     */
    public static final String DIRECTION_RIGHT = "right";

    /**
     * Connect-Top parameter.
     */
    public static final String DIRECTION_TOP = "top";

    /**
     * Determines whether the specified object is a valid Connect-Window
     * direction.
     *
     * @param direction Parameter for an object direction
     * @return <code>true</code>, if the specified object is a valid
     * Connect-Window direction;<br>
     * <code>false</code> otherwise.
     */
    public static boolean isValidDirection(Object direction) {
        return (direction != null) && ((direction.equals(ConnectWindow.DIRECTION_BOTTOM)) ||
                (direction.equals(ConnectWindow.DIRECTION_LEFT)) || (direction.equals(ConnectWindow.DIRECTION_NONE)) ||
                (direction.equals(ConnectWindow.DIRECTION_RIGHT)) || (direction.equals(ConnectWindow.DIRECTION_TOP)));
    }

    /**
     * Creates a new Connect-Window Command using the specified window, opposite
     * window and connect direction.
     *
     * {@link #isValidDirection(Object)}
     *
     * @param windowToConnect  the window to be connected to another window.
     * @param connectDirection the connect direction to be used.
     * @de.renew.require (windowToConnect != null)
     * @de.renew.require ConnectWindow.isValidDirection(connectDirection)
     *
     */
    public ConnectWindow(Window windowToConnect, Object connectDirection) {
        assert (windowToConnect != null) : "Precondition violated: (windowToConnect != null)";
        assert ConnectWindow.isValidDirection(connectDirection) :
                "Precondition violated: " + "ConnectWindow.isValidDirection(connectDirection)";

        this._windowToConnect = windowToConnect;
        this._connectDirection = connectDirection;
    }

    /**
     * <p>Returns the connect direction used by this command.</p>
     * <p>To override implement {@link #getConnectDirectionImpl()}.</p>
     *
     * {@link #isValidDirection(Object)}
     *
     * @return the connect direction used by this command.
     * @de.renew.ensure ConnectWindow.isValidDirection(returnValue)
     *
     */
    public final Object getConnectDirection() {
        Object returnValue = this.getConnectDirectionImpl();
        assert ConnectWindow.isValidDirection(returnValue) :
                "Postcondition violated: " + "ConnectWindow.isValidDirection(returnValue)";

        return returnValue;
    }

    /**
     * <p>Returns the window to be connected by this command.</p>
     * <p>To override implement {@link #getWindowToConnectImpl()}.</p>
     *
     * @return the window to be connected by this command.
     * @de.renew.ensure (returnValue != null)
     */
    public final Window getWindowToConnect() {
        Window returnValue = this.getWindowToConnectImpl();
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isExecutable() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void executeImpl() throws CommandException {
        Object connectionState = this.getConnectionState(this.getConnectDirection());
        JFrame frame = this.getWindowToConnect().getJavaFrame();
        WindowConnectionListener listener = WindowConnectionListener.getConnectionListener(frame);
        if (listener == null) {
            throw new CommandException(this, "Unknown target window type");
        }
        listener.setConnectionState(connectionState);
        listener.update();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getNameImpl() {
        return ConnectWindow.NAME;
    }

    /**
     * Maps the specified connect direction to its corresponding connection
     * state.
     *
     * {@link #isValidDirection(Object)}
     *
     * @param connectDirection the connect direction to be mapped.
     * @return the corresponding connection state.
     * @de.renew.require ConnectWindow.isValidDirection(connectDirection)
     * @de.renew.ensure (returnValue != null)
     *
     */
    protected Object getConnectionState(Object connectDirection) {
        if (connectDirection.equals(ConnectWindow.DIRECTION_BOTTOM)) {
            return WindowConnectionListener.STATE_CONNECTED_BOTTOM;
        } else if (connectDirection.equals(ConnectWindow.DIRECTION_LEFT)) {
            return WindowConnectionListener.STATE_CONNECTED_LEFT;
        } else if (connectDirection.equals(ConnectWindow.DIRECTION_RIGHT)) {
            return WindowConnectionListener.STATE_CONNECTED_RIGHT;
        } else if (connectDirection.equals(ConnectWindow.DIRECTION_TOP)) {
            return WindowConnectionListener.STATE_CONNECTED_TOP;
        } else if (connectDirection.equals(ConnectWindow.DIRECTION_NONE)) {
            return WindowConnectionListener.STATE_NOT_CONNECTED;
        } else {
            return null;
        }
    }

    /**
     * Returns the connect direction used by this command.
     *
     * {@link #isValidDirection(Object)}
     *
     * @return the connect direction used by this command.
     * @de.renew.ensure ConnectWindow.isValidDirection(returnValue)
     *
     */
    protected Object getConnectDirectionImpl() {
        return this._connectDirection;
    }

    /**
     * Returns the window to be connected by this command.
     *
     * @return the window to be connected by this command.
     * @de.renew.ensure (returnValue != null)
     */
    protected Window getWindowToConnectImpl() {
        return this._windowToConnect;
    }

    /**
     * The window to be connected by this command.
     */
    private Window _windowToConnect;

    /**
     * The connect direction used by this command.
     */
    private Object _connectDirection;
}