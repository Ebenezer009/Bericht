package de.renew.netdoc.gui.windows.documentcontainer;

import de.renew.netdoc.gui.components.documentcontainer.NetDocPanel;
import de.renew.netdoc.model.container.ContainerException;
import de.renew.netdoc.model.container.event.DocumentContainerAdapter;
import de.renew.netdoc.model.container.event.DocumentContainerEvent;
import de.renew.netdoc.model.document.Document;
import de.renew.netdoc.model.document.event.DocumentChangeEvent;
import de.renew.netdoc.model.document.event.DocumentChangeListener;

import java.awt.Dimension;
import java.util.Collections;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;


/**
 * Multi-Window-Mode NetDoc GUI window.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public class NetDocWindow extends SingleDocumentContainerWindow {

    /**
     * Default width of a NetDocWindow.
     */
    private static final int WIDTH = 400;

    /**
     * Default height of a NetDocWindow.
     */
    private static final int HEIGHT = 200;


    /**
     * Creates a new NetDocWindow.
     */
    public NetDocWindow() {
        this._frame = new NetDocWindow.Frame();
        this._panel = new NetDocPanel(this);

        ((JComponent) this.getPanel().getJavaComponent())
                        .setBorder(new EmptyBorder(4, 2, 2, 2));

        this.getPanel().addDocumentChangeListener(this.getContainerListener());
        this.getPanel().addDocumentContainerListener(
                        this.getContainerListener());

        this.getFrame().addWindowListener(new NetDocWindow.FrameListener());
        this.getFrame().getContentPane()
                        .add(this.getPanel().getJavaComponent());

        this.getFrame().setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.getFrame().pack();
    }


    /**
     * Updates the title of this window.
     */
    public void updateTitle() {
        this.getFrame().updateTitle();
    }

    /**
     * @inheritDoc
     */
    @Override
    public Document getDocument() {
        return this.getPanel().getDocument();
    }

    /**
     * @inheritDoc
     */
    @Override
    protected void closeDocumentImpl(Document documentToClose,
                                     boolean forceClose) {
        this.getPanel().closeDocument(documentToClose, forceClose);
    }

    /**
     * @inheritDoc
     */
    @Override
    protected void openDocumentImpl(Document documentToOpen)
                    throws ContainerException {
        this.getPanel().openDocument(documentToOpen);
    }


    /**
     * @inheritDoc
     */
    @Override
    protected List<NetDocPanel> getChildrenImpl() {
        return Collections.singletonList(this.getPanel());
    }

    /**
     * @inheritDoc
     */
    @Override
    protected void focusOnDocumentImpl(Document document) {
        this.getPanel().focusOnDocument(document);
    }


    /**
     * @inheritDoc
     */
    @Override
    protected final JFrame getJavaFrameImpl() {
        return this.getFrame();
    }

    /**
     * Returns the frame containing the document.
     * @return the frame containing the document.
     * @de.renew.ensure (returnValue != null)
     */
    protected NetDocWindow.Frame getFrame() {
        return this._frame;
    }

    /**
     * Returns the NetDocPanel used by this window.
     * @return the NetDocPanel used by this window.
     * @de.renew.ensure (returnValue != null)
     */
    protected NetDocPanel getPanel() {
        return this._panel;
    }

    /**
     * Returns the document container listener used by this window.
     * @return the document container listener used by this window.
     * @de.renew.ensure (returnValue != null)
     */
    protected ContainerListener getContainerListener() {
        if (this._containerListener == null) {
            this._containerListener = new NetDocWindow.ContainerListener();
        }
        return this._containerListener;
    }


    /**
     * The java frame used by this window.
     */
    private NetDocWindow.Frame _frame;

    /**
     * The NetDocPanel used by this window.
     */
    private NetDocPanel _panel;

    /**
     * The document container listener used by this window.
     */
    private ContainerListener _containerListener;


    /**
     * Observer listening to document change events of the panel component.
     */
    protected class ContainerListener extends DocumentContainerAdapter
                    implements DocumentChangeListener {

        /**
         * Creates a new ContainerListener.
         */
        public ContainerListener() {
        }

        /**
         * @inheritDoc
         */
        @Override
        public void documentChanged(DocumentChangeEvent event) {
            NetDocWindow.this.fireDocumentChangeEvent(event);
        }

        /**
         * @inheritDoc
         */
        @Override
        protected void containerClosedImpl(DocumentContainerEvent event) {
            NetDocWindow.this.fireDocumentContainerEvent(
                            new DocumentContainerEvent(event.getType(),
                                            NetDocWindow.this,
                                            event.getInvolvedDocument()));
        }

        /**
         * @inheritDoc
         */
        @Override
        protected void containerClosingImpl(DocumentContainerEvent event) {
            NetDocWindow.this.fireDocumentContainerEvent(
                            new DocumentContainerEvent(event.getType(),
                                            NetDocWindow.this,
                                            event.getInvolvedDocument()));
        }

        /**
         * @inheritDoc
         */
        @Override
        protected void containerOpenedImpl(DocumentContainerEvent event) {
            NetDocWindow.this.fireDocumentContainerEvent(
                            new DocumentContainerEvent(event.getType(),
                                            NetDocWindow.this,
                                            event.getInvolvedDocument()));
        }

        /**
         * @inheritDoc
         */
        @Override
        protected void documentClosedImpl(DocumentContainerEvent event) {
            NetDocWindow.this.fireDocumentContainerEvent(
                            new DocumentContainerEvent(event.getType(),
                                            NetDocWindow.this,
                                            event.getInvolvedDocument()));
        }

        /**
         * @inheritDoc
         */
        @Override
        protected void documentClosingImpl(DocumentContainerEvent event) {
            NetDocWindow.this.fireDocumentContainerEvent(
                            new DocumentContainerEvent(event.getType(),
                                            NetDocWindow.this,
                                            event.getInvolvedDocument()));
        }

        /**
         * @inheritDoc
         */
        @Override
        protected void documentOpenedImpl(DocumentContainerEvent event) {
            NetDocWindow.this.fireDocumentContainerEvent(
                            new DocumentContainerEvent(event.getType(),
                                            NetDocWindow.this,
                                            event.getInvolvedDocument()));
        }
    }


    /**
     * The internal frame using the default height and width as preferred size
     * and the document name as title.
     */
    protected class Frame extends JFrame {

        /**
         * Creates a new Frame.
         */
        public Frame() {
        }

        /**
         * Returns the preferred size of this frame.
         * @return the preferred size of this frame.
         * @de.renew.ensure (returnValue != null)
         */
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(NetDocWindow.WIDTH, NetDocWindow.HEIGHT);
        }

        /**
         * @inheritDoc
         */
        @Override
        public String getTitle() {
            try {
                return NetDocWindow.this.getDocument().getName() + " - NetDoc";
            } catch (NullPointerException e) {
                return "NetDoc";
            }
        }

        /**
         * Updates the title of this frame.
         */
        public void updateTitle() {
            this.setTitle(this.getTitle());
        }

        /**
         * Serial Version UID of this class.
         */
        private static final long serialVersionUID = 1L;
    }
}