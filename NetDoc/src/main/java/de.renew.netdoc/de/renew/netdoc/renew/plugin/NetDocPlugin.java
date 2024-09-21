package de.renew.netdoc.renew.plugin;

import CH.ifa.draw.DrawPlugin;
import CH.ifa.draw.application.MenuManager;

import de.renew.netdoc.model.container.ContainerManager;
import de.renew.netdoc.model.container.event.DocumentContainerAdapter;
import de.renew.netdoc.model.container.event.DocumentContainerEvent;
import de.renew.netdoc.model.document.event.DocumentChangeEvent;
import de.renew.netdoc.model.document.event.DocumentChangeListener;
import de.renew.netdoc.renew.gui.GUIContainerManager;
import de.renew.gui.GuiPlugin;
import de.renew.plugin.IPlugin;
import de.renew.plugin.PluginAdapter;
import de.renew.plugin.PluginException;
import de.renew.plugin.PluginManager;
import de.renew.plugin.PluginProperties;

import java.net.URL;
import java.util.Iterator;


/**
 * NetDoc Renew-Plugin.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public class NetDocPlugin extends PluginAdapter {

    /**
     * Returns the NetDocPlugin currently registered in the PluginManager.
     * @return the NetDocPlugin currently registered in the PluginManager;<br>
     * or {@code null}, if not any NetDocPlugin is registered.
     */
    public static NetDocPlugin getCurrent() {
        Iterator<IPlugin> it = PluginManager.getInstance()
                        .getPluginsProviding("de/renew").iterator();
        while (it.hasNext()) {
            IPlugin current = it.next();
            if (current instanceof NetDocPlugin) {
                return (NetDocPlugin) current;
            }
        }
        return null;
    }


    /**
     * Creates a new uninitialised NetDocPlugin using the specified properties.
     * @param properties  the properties/configuration of the new plugin.
     */
    public NetDocPlugin(PluginProperties properties) {
        super(properties);

        this._isInitialised = false;
    }

    /**
     * Creates a new uninitialised NetDocPlugin using the configuration at the
     * specified location.
     * @param location  an URL pointing to the jar file or directory
     *                   containing the plugin code and configuration files.
     * @throws PluginException
     *   if an error occurs while loading the plugin or its configuration.
     *   Possible nested exceptions are:
     *   <ul>
     *   <li>{@link java.net.MalformedURLException} if the {@code plugin.cfg}
     *       URL could not be derived from the plugin URL.</li>
     *   <li>{@link java.io.IOException} if the configuration could not be
     *       loaded.</li>
     *   </ul>
     */
    public NetDocPlugin(URL location) throws PluginException {
        super(location);

        this._isInitialised = false;
    }


    /**
     * Initialises this plugin.
     * @throws RuntimeException if the Renew GUI plugin could not be found.
     */
    @Override
    public void init() {
        super.init();

        GuiPlugin plugin = GuiPlugin.getCurrent();
        if (plugin == null) {
            throw new RuntimeException("Renew GUI plugin not found");
        }

        this._containerManager = new GUIContainerManager(plugin);
        this._containerManager
                        .addDocumentChangeListener(this.getDocumentListener());
        this._containerManager.addDocumentContainerListener(
                        this.getDocumentListener());

        MenuManager mm = DrawPlugin.getCurrent().getMenuManager();
        mm.registerMenu(DrawPlugin.PLUGINS_MENU, PluginMenus.getMain(this));

        // wait until GUIContainerManager is initialised
        final GUIContainerManager iManager = (GUIContainerManager) this._containerManager;
        new Thread() {
            @Override
            public void run() {
                while (!iManager.isInitialised()) {
                    Thread.yield();
                }
                NetDocPlugin.this._isInitialised = true;
            }
        }.start();
    }

    /**
     * Determines whether this plugin is already initialised.
     * @return {@code true}, if this plugin is initialised;<br>
     * {@code false} otherwise.
     */
    public boolean isInitialised() {
        return this._isInitialised;
    }

    /**
     * <p>Returns the container manager used by this plugin.</p>
     * <p>To override implement {@link #getContainerManagerImpl()}.</p>
     * @return the container manager used by this plugin.
     * @de.renew.require this.isInitialised()
     * @de.renew.ensure (returnValue != null)
     */
    public final ContainerManager getContainerManager() {
        assert this.isInitialised() : "Precondition violated: this.isInitialised()";

        ContainerManager returnValue = this.getContainerManagerImpl();
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * Updates the command menu used by this plugin.
     */
    public void updateMenu() {
        ((GUIContainerManager) this.getContainerManager()).getGUIPlugin()
                        .getGui().menuStateChanged();
    }

    /**
     * Returns the container manager used by this plugin.
     * @return the container manager used by this plugin.
     * @de.renew.require this.isInitialised()
     * @de.renew.ensure (returnValue != null)
     */
    protected ContainerManager getContainerManagerImpl() {
        return this._containerManager;
    }

    /**
     * Returns the document listener used by this plugin.
     * @return the document listener used by this plugin.
     * @de.renew.ensure (returnValue != null)
     */
    protected DocumentListener getDocumentListener() {
        if (this._documentListener == null) {
            this._documentListener = new NetDocPlugin.DocumentListener();
        }
        return this._documentListener;
    }

    /**
     * The container manager used by this plugin.
     */
    private ContainerManager _containerManager;

    /**
     * Specifies whether this plugin is already initialised.
     */
    private boolean _isInitialised;

    /**
     * The document listener used by this plugin.
     */
    private DocumentListener _documentListener;


    /**
     * The document listener used by this plugin.
     */
    private class DocumentListener extends DocumentContainerAdapter
                    implements DocumentChangeListener {

        /**
         * Creates a new DocumentListener.
         */
        public DocumentListener() {
        }

        /**
         * @inheritDoc
         */
        @Override
        public void documentChanged(DocumentChangeEvent event) {
            //System.out.println("TEST: document changed");
            NetDocPlugin.this.updateMenu();
        }

        /**
         * @inheritDoc
         */
        @Override
        protected void containerClosedImpl(DocumentContainerEvent event) {
            //System.out.println("TEST: document container closed");
            NetDocPlugin.this.updateMenu();
        }

        /**
         * @inheritDoc
         */
        @Override
        protected void containerClosingImpl(DocumentContainerEvent event) {
            //System.out.println("TEST: document container closing");
        }

        /**
         * @inheritDoc
         */
        @Override
        protected void containerOpenedImpl(DocumentContainerEvent event) {
            //System.out.println("TEST: document container opened");
            NetDocPlugin.this.updateMenu();
        }

        /**
         * @inheritDoc
         */
        @Override
        protected void documentClosedImpl(DocumentContainerEvent event) {
            //System.out.println("TEST: document closed");
            NetDocPlugin.this.updateMenu();
        }

        /**
         * @inheritDoc
         */
        @Override
        protected void documentClosingImpl(DocumentContainerEvent event) {
            //System.out.println("TEST: document closing");
        }

        /**
         * @inheritDoc
         */
        @Override
        protected void documentOpenedImpl(DocumentContainerEvent event) {
            //System.out.println("TEST: document opened");
            NetDocPlugin.this.updateMenu();
        }
    }
}