package de.renew.netdoc.renew.plugin;

import de.renew.netdoc.gui.commands.ConnectWindow;
import de.renew.netdoc.model.command.Command;
import de.renew.netdoc.model.container.commands.ManagerCommands;
import de.renew.netdoc.renew.gui.GUICommands;

/**
 * This class is a factory providing NetDoc Renew plugin commands.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public abstract class PluginCommands {

    /**
     * Name of the Close-Current-NetDoc command.
     */
    public static final String NAME_CLOSE_CURRENT_NETDOC = "Close NetDoc";

    /**
     * Name of the Open-Current-NetDoc command.
     */
    public static final String NAME_OPEN_CURRENT_NETDOC = "Open NetDoc";

    /**
     * Name of the Save-Current-NetDoc command.
     */
    public static final String NAME_SAVE_CURRENT_NETDOC = "Save NetDoc";

    /**
     * Name of the Close-All-NetDocs command.
     */
    public static final String NAME_CLOSE_ALL_NETDOCS = "Close All NetDocs";

    /**
     * Name of the Open-All-NetDocs command.
     */
    public static final String NAME_OPEN_ALL_NETDOCS = "Open All NetDocs";

    /**
     * Name of the Save-All-NetDocs command.
     */
    public static final String NAME_SAVE_ALL_NETDOCS = "Save All NetDocs";

    /**
     * This class cannot be instantiated.
     */
    private PluginCommands() {
    }

    /**
     * Returns a Close-Current-NetDoc command for the specified NetDoc plugin.
     *
     * @param plugin the NetDoc plugin to execute the command on
     * @return the command.
     * @de.renew.require (plugin != null)
     * @de.renew.ensure (returnValue != null)
     */
    public static PluginCommand getCloseCurrentNetDoc(NetDocPlugin plugin) {
        assert (plugin != null) : "Precondition violated: (plugin != null)";

        PluginCommand returnValue = new PluginCommand(plugin, PluginCommands.NAME_CLOSE_CURRENT_NETDOC) {
            @Override
            protected Command createInnerCommand() {
                return ManagerCommands.getCloseCurrentDocument(this.getPlugin().getContainerManager());
            }
        };
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * Returns a Connect-Current-NetDoc command for the specified NetDoc plugin
     * and connect direction.
     *
     * {@link ConnectWindow#isValidDirection(Object)}
     *
     * @param plugin           the NetDoc plugin to execute the command on
     * @param connectDirection the connect direction to be used by the command.
     * @return the command.
     * @de.renew.require (plugin != null)
     * @de.renew.require ConnectWindow.isValidDirection(connectDirection)
     * @de.renew.ensure (returnValue != null)
     *
     */
    public static PluginCommand getConnectCurrentNetDoc(NetDocPlugin plugin, Object connectDirection) {
        assert (plugin != null) : "Precondition violated: (plugin != null)";
        assert ConnectWindow.isValidDirection(connectDirection) :
                "Precondition violated: ConnectWindow.isValidDirection(connectDirection)";

        final Object iConnectDirection = connectDirection;

        PluginCommand returnValue =
                new PluginCommand(plugin, GUICommands.getConnectDocumentWindowName(connectDirection)) {
                    @Override
                    protected Command createInnerCommand() {
                        return GUICommands.getConnectCurrentDocumentWindow(this.getPlugin().getContainerManager(),
                                iConnectDirection);
                    }
                };
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * Returns an Open-Current-NetDoc command for the specified NetDoc plugin.
     *
     * @param plugin the NetDoc plugin to execute the command on
     * @return the command.
     * @de.renew.require (plugin != null)
     * @de.renew.ensure (returnValue != null)
     */
    public static PluginCommand getOpenCurrentNetDoc(NetDocPlugin plugin) {
        assert (plugin != null) : "Precondition violated: (plugin != null)";

        PluginCommand returnValue = new PluginCommand(plugin, PluginCommands.NAME_OPEN_CURRENT_NETDOC) {
            @Override
            protected Command createInnerCommand() {
                return ManagerCommands.getOpenCurrentDocument(this.getPlugin().getContainerManager());
            }
        };
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * Returns a Save-Current-NetDoc command for the specified NetDoc plugin.
     *
     * @param plugin the NetDoc plugin to execute the command on
     * @return the command.
     * @de.renew.require (plugin != null)
     * @de.renew.ensure (returnValue != null)
     */
    public static PluginCommand getSaveCurrentNetDoc(NetDocPlugin plugin) {
        assert (plugin != null) : "Precondition violated: (plugin != null)";

        PluginCommand returnValue = new PluginCommand(plugin, PluginCommands.NAME_SAVE_CURRENT_NETDOC) {
            @Override
            protected Command createInnerCommand() {
                return ManagerCommands.getSaveCurrentDocument(this.getPlugin().getContainerManager());
            }
        };
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * Returns a Close-All-NetDocs command for the specified NetDoc plugin.
     *
     * @param plugin the NetDoc plugin to execute the command on
     * @return the command.
     * @de.renew.require (plugin != null)
     * @de.renew.ensure (returnValue != null)
     */
    public static PluginCommand getCloseAllNetDocs(NetDocPlugin plugin) {
        assert (plugin != null) : "Precondition violated: (plugin != null)";

        PluginCommand returnValue = new PluginCommand(plugin, PluginCommands.NAME_CLOSE_ALL_NETDOCS) {
            @Override
            protected Command createInnerCommand() {
                return ManagerCommands.getCloseAllDocuments(this.getPlugin().getContainerManager());
            }
        };
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * Returns a Connect-All-NetDocs command for the specified NetDoc plugin
     * and connect direction.
     *
     * {@link ConnectWindow#isValidDirection(Object)}
     *
     * @param plugin           the NetDoc plugin to execute the command on
     * @param connectDirection the connect direction to be used by the command.
     * @return the command.
     * @de.renew.require (plugin != null)
     * @de.renew.require ConnectWindow.isValidDirection(connectDirection)
     * @de.renew.ensure (returnValue != null)
     *
     */
    public static PluginCommand getConnectAllNetDocs(NetDocPlugin plugin, Object connectDirection) {
        assert (plugin != null) : "Precondition violated: (plugin != null)";
        assert ConnectWindow.isValidDirection(connectDirection) :
                "Precondition violated: ConnectWindow.isValidDirection(connectDirection)";

        final Object iConnectDirection = connectDirection;

        PluginCommand returnValue =
                new PluginCommand(plugin, GUICommands.getConnectDocumentWindowName(connectDirection)) {
                    @Override
                    protected Command createInnerCommand() {
                        return GUICommands.getConnectAllDocumentWindows(this.getPlugin().getContainerManager(),
                                iConnectDirection);
                    }
                };
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * Returns an Open-All-NetDocs command for the specified NetDoc plugin.
     *
     * @param plugin the NetDoc plugin to execute the command on
     * @return the command.
     * @de.renew.require (plugin != null)
     * @de.renew.ensure (returnValue != null)
     */
    public static PluginCommand getOpenAllNetDocs(NetDocPlugin plugin) {
        assert (plugin != null) : "Precondition violated: (plugin != null)";

        PluginCommand returnValue = new PluginCommand(plugin, PluginCommands.NAME_OPEN_ALL_NETDOCS) {
            @Override
            protected Command createInnerCommand() {
                return ManagerCommands.getOpenAllDocuments(this.getPlugin().getContainerManager());
            }
        };
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * Returns a Save-All-NetDocs command for the specified NetDoc plugin.
     *
     * @param plugin the NetDoc plugin to execute the command on
     * @return the command.
     * @de.renew.require (plugin != null)
     * @de.renew.ensure (returnValue != null)
     */
    public static PluginCommand getSaveAllNetDocs(NetDocPlugin plugin) {
        assert (plugin != null) : "Precondition violated: (plugin != null)";

        PluginCommand returnValue = new PluginCommand(plugin, PluginCommands.NAME_SAVE_ALL_NETDOCS) {
            @Override
            protected Command createInnerCommand() {
                return ManagerCommands.getSaveAllDocuments(this.getPlugin().getContainerManager());

            }
        };
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }
}