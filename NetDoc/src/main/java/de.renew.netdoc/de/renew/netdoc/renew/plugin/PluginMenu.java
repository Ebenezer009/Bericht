package de.renew.netdoc.renew.plugin;

import CH.ifa.draw.util.CommandMenu;


/**
 * NetDoc Renew plugin command menu.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public class PluginMenu extends CommandMenu {

    /**
     * Creates a new command menu for the specified NetDoc plugin using the
     * specified display name.
     * @param plugin  the NetDoc plugin the new menu will be registered to.
     * @param name  the display name of the new menu.
     * @de.renew.require (plugin != null)
     * @de.renew.require (name != null)
     */
    public PluginMenu(NetDocPlugin plugin, String name) {
        super(PluginMenu.validate(name));
        assert (plugin != null) : "Precondition violated: (plugin != null)";

        this._plugin = plugin;
    }


    /**
     * <p>Returns the NetDoc plugin registered to this menu.</p>
     * <p>To override implement {@link #getPluginImpl()}.</p>
     * @return the NetDoc plugin registered to this menu.
     * @de.renew.ensure (returnValue != null)
     */
    public final NetDocPlugin getPlugin() {
        NetDocPlugin returnValue = this._plugin;
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * Returns the NetDoc plugin registered to this menu.
     * @return the NetDoc plugin registered to this menu.
     * @de.renew.ensure (returnValue != null)
     */
    protected NetDocPlugin getPluginImpl() {
        return this._plugin;
    }


    /**
     * The NetDoc plugin registered to this menu.
     */
    private NetDocPlugin _plugin;


    /**
     * Validates the specified display name.
     * @param name  the display name to be validated.
     * @return the validated display name.
     */
    private static String validate(String name) {
        assert (name != null) : "Precondition violated: (name != null)";

        return name;
    }


    /**
     * Serial Version UID of this class.
     */
    private static final long serialVersionUID = 1L;
}