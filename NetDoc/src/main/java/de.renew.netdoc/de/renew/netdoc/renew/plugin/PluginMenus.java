package de.renew.netdoc.renew.plugin;

import CH.ifa.draw.application.MenuManager;
import CH.ifa.draw.util.CommandMenu;

import de.renew.netdoc.gui.commands.ConnectWindow;


/**
 * Factory providing NetDoc Renew plugin menus.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public abstract class PluginMenus {

    /**
     * Display name of the Connect-All-NetDocs menu.
     */
    private static final String NAME_CONNECT_ALL = "Connect All NetDocs";

    /**
     * Display name of the Connect-Current-NetDoc menu.
     */
    private static final String NAME_CONNECT_CURRENT = "Connect NetDoc";

    /**
     * Display name of the main menu.
     */
    private static final String NAME_MAIN = "NetDoc";

    /**
     * This class cannot be instantiated.
     */
    private PluginMenus() {
    }

    /**
     * Returns the Connect-All-NetDocs menu for the specified NetDoc plugin.
     * @param plugin the NetDoc plugin to be used by the menu.
     * @return the menu.
     * @de.renew.require (plugin != null)
     * @de.renew.ensure (returnValue != null)
     */
    public static CommandMenu getConnectAllNetDocs(NetDocPlugin plugin) {
        assert (plugin != null) : "Precondition violated: (plugin != null)";

        CommandMenu returnValue = PluginMenus
                        .createNetDocMenu(PluginMenus.NAME_CONNECT_ALL);
        returnValue.add(PluginCommands.getConnectAllNetDocs(plugin,
                        ConnectWindow.DIRECTION_NONE));
        returnValue.add(PluginCommands.getConnectAllNetDocs(plugin,
                        ConnectWindow.DIRECTION_TOP));
        returnValue.add(PluginCommands.getConnectAllNetDocs(plugin,
                        ConnectWindow.DIRECTION_LEFT));
        returnValue.add(PluginCommands.getConnectAllNetDocs(plugin,
                        ConnectWindow.DIRECTION_RIGHT));
        returnValue.add(PluginCommands.getConnectAllNetDocs(plugin,
                        ConnectWindow.DIRECTION_BOTTOM));
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * Returns the Connect-Current-NetDoc menu for the specified NetDoc plugin.
     * @param plugin the NetDoc plugin to be used by the menu.
     * @return the menu.
     * @de.renew.require (plugin != null)
     * @de.renew.ensure (returnValue != null)
     */
    public static CommandMenu getConnectCurrentNetDoc(NetDocPlugin plugin) {
        assert (plugin != null) : "Precondition violated: (plugin != null)";

        CommandMenu returnValue = PluginMenus
                        .createNetDocMenu(PluginMenus.NAME_CONNECT_CURRENT);
        returnValue.add(PluginCommands.getConnectCurrentNetDoc(plugin,
                        ConnectWindow.DIRECTION_NONE));
        returnValue.add(PluginCommands.getConnectCurrentNetDoc(plugin,
                        ConnectWindow.DIRECTION_TOP));
        returnValue.add(PluginCommands.getConnectCurrentNetDoc(plugin,
                        ConnectWindow.DIRECTION_LEFT));
        returnValue.add(PluginCommands.getConnectCurrentNetDoc(plugin,
                        ConnectWindow.DIRECTION_RIGHT));
        returnValue.add(PluginCommands.getConnectCurrentNetDoc(plugin,
                        ConnectWindow.DIRECTION_BOTTOM));
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * Returns the main menu for the specified NetDoc plugin.
     * @param plugin the NetDoc plugin to be used by the menu.
     * @return the menu.
     * @de.renew.require (plugin != null)
     * @de.renew.ensure (returnValue != null)
     */
    public static CommandMenu getMain(NetDocPlugin plugin) {
        assert (plugin != null) : "Precondition violated: (plugin != null)";

        CommandMenu returnValue = PluginMenus
                        .createNetDocMenu(PluginMenus.NAME_MAIN);
        returnValue.add(PluginCommands.getOpenCurrentNetDoc(plugin));
        returnValue.add(PluginCommands.getCloseCurrentNetDoc(plugin));
        returnValue.add(PluginCommands.getSaveCurrentNetDoc(plugin));
        returnValue.add(PluginMenus.getConnectCurrentNetDoc(plugin));
        returnValue.addSeparator();
        returnValue.add(PluginCommands.getOpenAllNetDocs(plugin));
        returnValue.add(PluginCommands.getCloseAllNetDocs(plugin));
        returnValue.add(PluginCommands.getSaveAllNetDocs(plugin));
        returnValue.add(PluginMenus.getConnectAllNetDocs(plugin));
        returnValue.addSeparator();
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * Creates an empty NetDoc CommandMenu with the specified name.
     * @param name the name of the new menu.
     * @de.renew.require (name != null)
     */
    private static CommandMenu createNetDocMenu(String name) {
        CommandMenu menu = new CommandMenu(name);
        menu.putClientProperty(MenuManager.ID_PROPERTY, "de/renew");
        return menu;
    }
}