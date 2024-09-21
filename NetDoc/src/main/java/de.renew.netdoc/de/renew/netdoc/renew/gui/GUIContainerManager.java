package de.renew.netdoc.renew.gui;

import de.renew.netdoc.gui.Window;
import de.renew.netdoc.gui.event.CloseRequestListener;
import de.renew.netdoc.gui.event.WindowConnectionListener;
import de.renew.netdoc.gui.windows.documentcontainer.NetDocWindow;
import de.renew.netdoc.io.IOManager;
import de.renew.netdoc.io.documentformatters.TexExportFormatter;
import de.renew.netdoc.io.documentparsers.TexDocumentParser;
import de.renew.netdoc.io.managers.SimpleIOManager;
import de.renew.netdoc.model.container.ContainerException;
import de.renew.netdoc.model.container.DocumentContainer;
import de.renew.netdoc.model.container.event.DocumentContainerEvent;
import de.renew.netdoc.model.container.managers.UserRequestManager;
import de.renew.netdoc.model.doctarget.DocTarget;
import de.renew.netdoc.model.document.Document;
import de.renew.netdoc.model.document.DocumentPart;
import de.renew.gui.GuiPlugin;
import de.renew.netdoc.renew.hotdraw.DrawingTarget;

import java.awt.Dimension;
import java.awt.event.ComponentListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;


/**
 * Renew GUI container manager.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public class GUIContainerManager extends UserRequestManager {

    /**
     * Default document filename extension.
     */
    private static final String FILENAME_EXTENSION = ".doctex";

    /**
     * Creates a new GUIContainerManager using the specified Renew GUI plugin.
     * @param renewGUIPlugin  the Renew GUI plugin to be used by the new
     * manager.
     * @de.renew.require (renewGUIPlugin != null)
     */
    public GUIContainerManager(GuiPlugin renewGUIPlugin) {
        assert (renewGUIPlugin != null) : "Precondition violated: (renewGUIPlugin !=  null)";

        this._guiPlugin = renewGUIPlugin;
        this._containers = new HashMap<Document, DocumentContainer>();
        this._isInitialised = false;

        // wait for GUI frame to appear and then add listener
        new Thread() {
            @Override
            public void run() {
                while (!GUIContainerManager.this.getGUIPlugin()
                                .isGuiPresent()) {
                    Thread.yield();
                }
                JFrame frame = GUIContainerManager.this.getGUIPlugin()
                                .getGuiFrame();
                WindowListener[] listeners = frame.getWindowListeners();
                for (int index = 0; index < listeners.length; index++) {
                    frame.removeWindowListener(listeners[index]);
                }
                frame.addWindowListener(new GUIFrameListener(listeners));
                GUIContainerManager.this._isInitialised = true;
            }
        }.start();
    }


    /**
     * Determines whether this manager is already initialised.
     * @return {@code true}, if this manager is initialised;<br>
     * {@code false} otherwise.
     */
    public boolean isInitialised() {
        return this._isInitialised;
    }

    /**
     * <p>Returns the default window connection state used by this manager.</p>
     * <p>To override implement {@link #getDefaultConnectionStateImpl()}.</p>
     * @return the default window connection state used by this manager.
     * @de.renew.ensure (returnValue != null)
     */
    public Object getDefaultConnectionState() {
        Object returnValue = this.getDefaultConnectionStateImpl();
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * <p>Returns the Renew GUI plugin used by this manager.</p>
     * <p>To override implement {@link #getGUIPluginImpl()}.</p>
     * @return the Renew GUI plugin used by this manager.
     * @de.renew.ensure (returnValue != null)
     */
    public final GuiPlugin getGUIPlugin() {
        GuiPlugin returnValue = this.getGUIPluginImpl();
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * @inheritDoc
     */
    @Override
    public Document getCurrentDocument() {
        DocTarget currentTarget = this.getCurrentTarget();
        if (currentTarget == null) {
            return null;
        } else {
            return this.getDocumentMap().getDocument(currentTarget);
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public DocTarget getCurrentTarget() {
        return GUIDocTargets.getCurrentTarget(this.getGUIPlugin());
    }

    /**
     * @inheritDoc
     */
    @Override
    protected Collection<DrawingTarget> getTargets() {
        return GUIDocTargets.getTargets(this.getGUIPlugin());
    }

    /**
     * @inheritDoc
     */
    @Override
    protected DocumentContainer getDocumentContainerImpl(Document document) {
        return this.getContainerMap().get(document);
    }

    /**
     * @inheritDoc
     */
    @Override
    protected IOManager getIOManagerImpl() {
        if (this._ioManager == null) {
            this._ioManager = this.new GUIIOManager();
        }
        return this._ioManager;
    }

    /**
     * @inheritDoc
     */
    @Override
    protected Collection<DocumentContainer> getDocumentContainersImpl() {
        return Collections.unmodifiableCollection(
                        this.getContainerMap().values());
    }

    /**
     * @inheritDoc
     */
    @Override
    protected void close() throws ContainerException {
        JFrame frame = this.getGUIPlugin().getGuiFrame();
        if (frame != null) {
            WindowListener[] listeners = frame.getWindowListeners();
            for (int index = 0; index < listeners.length; index++) {
                if (listeners[index] instanceof GUIFrameListener) {
                    GUIFrameListener frameListener = ((GUIFrameListener) listeners[index]);
                    frameListener.fireWindowClosing(frame);
                }
            }
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    protected DocumentContainer openDocumentImpl(DocTarget docTarget)
                    throws ContainerException {
        // get document
        Document document = this.getDocumentMap().getDocument(docTarget);

        // if document is missing, try to open
        if (document == null) {
            // create or load document
            document = this.createOrLoadDocument(docTarget);
        }

        // open document
        return this.openDocument(document);
    }

    /**
     * @inheritDoc
     */
    @Override
    protected DocumentContainer openDocumentImpl(Document documentToOpen)
                    throws ContainerException {
        // get document container
        DocumentContainer container = this.getDocumentContainer(documentToOpen);

        // if container is missing, create and add
        if (container == null) {
            container = this.createContainer(documentToOpen);
            this.addContainer(container);
            ((NetDocWindow) container).updateTitle();
        }

        // show container and focus on document
        JFrame documentFrame = ((Window) container).getJavaFrame();
        documentFrame.setVisible(true);
        documentFrame.toFront();
        container.focusOnDocument(documentToOpen);

        return container;
    }

    /**
     * @inheritDoc
     */
    @Override
    protected DocumentListener getDocumentListener() {
        if (this._documentListener == null) {
            this._documentListener = this.new GUIDocumentListener();
        }
        return this._documentListener;
    }

    /**
     * Creates a new document container for the specified document.
     * @param document the document to be used by the new container.
     * @return the new document container.
     * @throws ContainerException if the container could not be created.
     * @de.renew.require (document != null)
     * @de.renew.ensure (returnValue != null)
     */
    protected DocumentContainer createContainer(Document document)
                    throws ContainerException {
        NetDocWindow window = new NetDocWindow();
        window.openDocument(document);
        document.setModified(false);
        return window;
    }

    /**
     * Creates or loads the document for the specifed documentation target.
     * @param docTarget the documentation target specifying the document.
     * @return the document.
     * @throws ContainerException if an error occured while creating or loading
     * the document.
     * @de.renew.require (docTarget != null)
     * @de.renew.ensure (returnValue != null)
     */
    protected Document createOrLoadDocument(DocTarget docTarget)
                    throws ContainerException {
        if (this.getIOManager().documentPartExists(docTarget)) {
            try {
                return (Document) this.getIOManager()
                                .loadDocumentPart(docTarget);
            } catch (IOException e) {
                throw new ContainerException("Unable to load document", e);
            }
        } else {
            return (Document) GUIDocTargets.createDocumentPart(docTarget);
        }
    }

    /**
     * Adds the specified document container to this manager.
     * @param container the document container to be added.
     * @de.renew.require (container != null)
     */
    protected void addContainer(DocumentContainer container)
                    throws ContainerException {
        this.addListeners(container);

        try {
            Document document = container.getCurrentDocument();

            if (document.getTarget() != null) {
                JFrame documentFrame = ((Window) container).getJavaFrame();
                JFrame targetFrame = GUIDocTargets.getTargetFrame(
                                this.getGUIPlugin(), document.getTarget());

                WindowListener[] windowListeners = targetFrame
                                .getWindowListeners();
                for (int index = 0; index < windowListeners.length; index++) {
                    targetFrame.removeWindowListener(windowListeners[index]);
                }

                CloseListener closeListener = this.new CloseListener(
                                windowListeners, document);
                TitleChangeListener titleChangeListener = this.new TitleChangeListener(
                                document);

                targetFrame.addPropertyChangeListener("title",
                                titleChangeListener);
                targetFrame.addWindowListener(closeListener);

                WindowConnectionListener connectionListener = new WindowConnectionListener(
                                documentFrame, targetFrame);

                Dimension size = documentFrame.getPreferredSize();
                Object connectionState = this.getDefaultConnectionState();

                connectionListener.setDefaultHeight(size.height);
                connectionListener.setDefaultWidth(size.width);

                documentFrame.addWindowListener(connectionListener);
                documentFrame.addComponentListener(connectionListener);
                targetFrame.addWindowListener(connectionListener);
                targetFrame.addComponentListener(connectionListener);

                connectionListener.setConnectionState(connectionState);
                connectionListener.update();
            }

            this.getContainerMap().put(document, container);
        } catch (Exception e) {
            throw new ContainerException("Unable to add container", e);
        }

        // add listener to fire a document-opened event AFTER the
        // container-opened event has been fired.
        final DocumentContainer iContainer = container;
        final Document iDocument = iContainer.getCurrentDocument();
        ((Window) container).getJavaFrame()
                        .addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowOpened(WindowEvent event) {
                                GUIContainerManager.this
                                                .fireDocumentContainerEvent(
                                                                new DocumentContainerEvent(
                                                                                DocumentContainerEvent.DOCUMENT_OPENED,
                                                                                iContainer,
                                                                                iDocument));
                                // remove self from listeners
                                event.getWindow().removeWindowListener(this);
                            }
                        });
    }

    /**
     * Removes the listeners from all manager parts controlling the specified
     * document.
     * @param document the document specifying the listeners to be removed.
     * @de.renew.require (document != null)
     */
    protected void removeDocumentListeners(Document document) {
        if (document.getTarget() == null) {
            return;
        }

        DocumentContainer documentContainer = this
                        .getDocumentContainer(document);
        JFrame targetFrame = GUIDocTargets.getTargetFrame(this.getGUIPlugin(),
                        document.getTarget());

        PropertyChangeListener[] propertyListeners = targetFrame
                        .getPropertyChangeListeners();
        for (int index = 0; index < propertyListeners.length; index++) {
            if (propertyListeners[index] instanceof TitleChangeListener) {
                targetFrame.removePropertyChangeListener(
                                propertyListeners[index]);
            }
        }

        WindowListener[] windowListeners = targetFrame.getWindowListeners();
        for (int index = 0; index < windowListeners.length; index++) {
            if (windowListeners[index] instanceof WindowConnectionListener) {
                if (((WindowConnectionListener) windowListeners[index])
                                .getWindow1() == ((Window) documentContainer)
                                                .getJavaFrame()) {
                    targetFrame.removeComponentListener(
                                    (ComponentListener) windowListeners[index]);
                    targetFrame.removeWindowListener(windowListeners[index]);
                }
            } else if (windowListeners[index] instanceof CloseListener) {
                targetFrame.removeWindowListener(windowListeners[index]);
                WindowListener[] wrappedListeners = ((CloseListener) windowListeners[index])
                                .getWrappedListeners();
                for (int index2 = 0; index2 < wrappedListeners.length; index2++) {
                    targetFrame.addWindowListener(wrappedListeners[index2]);
                }
            }
        }
    }

    /**
     * Returns the internal map holding the document containers.
     * @return the internal map holding the document containers.
     * @de.renew.ensure (returnValue != null)
     */
    protected Map<Document, DocumentContainer> getContainerMap() {
        return this._containers;
    }

    /**
     * Returns the default window connection state used by this manager.
     * @return the default window connection state used by this manager.
     * @de.renew.ensure (returnValue != null)
     */
    protected Object getDefaultConnectionStateImpl() {
        return WindowConnectionListener.STATE_CONNECTED_BOTTOM;
    }

    /**
     * Returns the Renew GUI plugin used by this manager.
     * @return the Renew GUI plugin used by this manager.
     * @de.renew.ensure (returnValue != null)
     */
    protected GuiPlugin getGUIPluginImpl() {
        return this._guiPlugin;
    }

    /**
     * The Renew GUI plugin used by this manager.
     */
    private GuiPlugin _guiPlugin;

    /**
     * The Input/Output manager used by this manager.
     */
    private IOManager _ioManager;

    /**
     * The map holding the document containers. Uses documents as keys.
     */
    private Map<Document, DocumentContainer> _containers;

    /**
     * The document listener used by this manager.
     */
    private DocumentListener _documentListener;

    /**
     * Specifies whether this manager is already initialised.
     */
    private boolean _isInitialised;


    /**
     * Observer listening to document events in order to notify all document
     * container and document change listeners registered to this manager.
     */
    protected class GUIDocumentListener extends DocumentListener {

        /**
         * Creates a new GUIDocumentListener.
         */
        public GUIDocumentListener() {
        }

        /**
         * @inheritDoc
         */
        @Override
        protected void documentClosedImpl(DocumentContainerEvent event) {
            DocumentContainer container = GUIContainerManager.this
                            .getDocumentContainer(event.getInvolvedDocument());

            GUIContainerManager.this.removeDocumentListeners(
                            event.getInvolvedDocument());
            GUIContainerManager.this.getContainerMap()
                            .remove(event.getInvolvedDocument());

            super.documentClosedImpl(event);

            container.close(true);
        }
    }

    /**
     * GUI Input/Output manager used by this manager.
     */
    protected class GUIIOManager extends SimpleIOManager {

        /**
         * Creates a new GUI IOManager.
         */
        public GUIIOManager() {
            super(new TexExportFormatter(), new DocumentInputFormatter(),
                            new DocumentOutputFormatter(),
                            new TexDocumentParser(),
                            GUIContainerManager.FILENAME_EXTENSION);
        }

        /**
         * @inheritDoc
         */
        @Override
        protected DocumentPart loadDocumentPartImpl(DocTarget correspondingTarget)
                        throws IOException {
            // TODO: load from documentation target
            return super.loadDocumentPartImpl(correspondingTarget);
        }

        /**
         * @inheritDoc
         */
        @Override
        protected void saveDocumentPartImpl(DocumentPart partToSave)
                        throws IOException {
            // TODO: save to documentation target
            super.saveDocumentPartImpl(partToSave);
        }
    }

    /**
     * Observer listening to close events of a target window.
     */
    private class CloseListener extends CloseRequestListener {

        /**
         * Creates a new listener wrapping the specified window listeners
         * associated to the specified document.
         * @param listenersToWrap the listeners to be wrapped by the new listener.
         * @param document the document associated with the new listener.
         * @de.renew.require (listenersToWrap != null)
         * @de.renew.require !java.util.Arrays.asList(listenersToWrap).contains(null)
         * @de.renew.require (document != null)
         */
        public CloseListener(WindowListener[] listenersToWrap,
                             Document document) {
            super(listenersToWrap);

            this._document = document;
        }

        /**
         * @inheritDoc
         */
        @Override
        protected boolean allowClosing(WindowEvent event) {
            try {
                GUIContainerManager.this.closeDocument(this._document, false);
            } catch (ContainerException e) {
            }
            return (GUIContainerManager.this
                            .getDocumentContainer(this._document) == null);
        }

        /**
         * The document associated with this listener.
         */
        private Document _document;
    }

    /**
     * Observer listening to title property changes of a target window.
     */
    private class TitleChangeListener implements PropertyChangeListener {

        /**
         * Creates a new listener for the window containing the target that
         * corresponds to the specified document.
         * @param document the document specifying the target whose window
         * will be observed by the new listener.
         * @de.renew.require (document != null)
         */
        public TitleChangeListener(Document document) {
            this._document = document;
        }

        /**
         * Invoked when a bound property has changed.
         * @param event the corresponding change event.
         */
        @Override
        public void propertyChange(PropertyChangeEvent event) {
            final String iTitle = (String) event.getNewValue();
            final DocTarget iTarget = this._document.getTarget();
            new Thread() {
                @Override
                public void run() {
                    while (!iTitle.equals(iTarget.getName())) {
                        Thread.yield();
                    }
                    ((NetDocWindow) GUIContainerManager.this
                                    .getDocumentContainer(
                                                    TitleChangeListener.this._document))
                                                                    .updateTitle();
                }
            }.start();
        }

        /**
         * The document used by this listener.
         */
        private Document _document;
    }

    /**
     * The Renew GUI frame listener used by this manager.
     */
    private class GUIFrameListener extends CloseRequestListener {

        /**
         * Creates a new listener wrapping the specified window listeners.
         * @param listenersToWrap the listeners to be wrapped by the new listener.
         * @de.renew.require (listenersToWrap != null)
         * @de.renew.require !java.util.Arrays.asList(listenersToWrap).contains(null)
         */
        public GUIFrameListener(WindowListener[] listenersToWrap) {
            super(listenersToWrap);
        }

        /**
         * @inheritDoc
         */
        @Override
        protected boolean allowClosing(WindowEvent event) {
            try {
                GUIContainerManager.this.close(false);
            } catch (ContainerException e) {
            }
            return false;
        }

        /**
         * Fires a window closing event for the specified window to all
         * listeners controlled by this listener.
         * @param closingWindow the window that is about to close.
         * @de.renew.require (closingWindow != null)
         */
        public void fireWindowClosing(java.awt.Window closingWindow) {
            this.fireWindowClosing(new WindowEvent(closingWindow,
                            WindowEvent.WINDOW_CLOSING));
        }
    }
}