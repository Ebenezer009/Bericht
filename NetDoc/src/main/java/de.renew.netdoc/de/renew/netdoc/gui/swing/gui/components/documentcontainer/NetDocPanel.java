package de.renew.netdoc.gui.swing.gui.components.documentcontainer;

import de.renew.netdoc.gui.swing.gui.Component;
import de.renew.netdoc.model.container.ContainerException;
import de.renew.netdoc.model.container.event.DocumentContainerAdapter;
import de.renew.netdoc.model.container.event.DocumentContainerEvent;
import de.renew.netdoc.model.document.Document;
import de.renew.netdoc.model.document.event.DocumentChangeEvent;
import de.renew.netdoc.model.document.event.DocumentChangeListener;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.util.Collection;
import java.util.Collections;

/**
 * This class is the main panel of the NetDoc GUI.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public class NetDocPanel extends SingleDocumentContainerComponent {

    /**
     * Creates a new NetDocPanel.
     */
    public NetDocPanel() {
        this(null);
    }

    /**
     * Creates a new NetDocPanel with the specified parent.
     *
     * @param parent the parent of the new panel.
     */
    public NetDocPanel(Component parent) {
        super(parent);

        this._panel = new JPanel();
        this._editor = new DocumentEditor(this);

        this.getPanel().setLayout(new BorderLayout());
        this.getPanel().add(this.getEditor().getJavaComponent(), BorderLayout.CENTER);

        this.getEditor().addDocumentChangeListener(this.getContainerListener());
        this.getEditor().addDocumentContainerListener(this.getContainerListener());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Document getDocument() {
        return this.getEditor().getDocument();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected java.awt.Component getJavaComponentImpl() {
        return this.getPanel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Collection<?> getChildrenImpl() {
        return Collections.singletonList(this.getEditor());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void openDocumentImpl(Document documentToOpen) throws ContainerException {
        this.getEditor().openDocument(documentToOpen);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void focusOnDocumentImpl(Document document) {
        super.focusOnDocumentImpl(document);
        this.getEditor().focusOnDocument(document);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void closeDocumentImpl(Document documentToClose, boolean forceClose) {
        this.getEditor().closeDocument(documentToClose, forceClose);
    }

    /**
     * Returns the document editor contained in this panel.
     *
     * @return the document editor contained in this panel.
     * @de.renew.ensure (returnValue != null)
     */
    protected DocumentEditor getEditor() {
        return this._editor;
    }

    /**
     * Returns the java panel wrapped by this panel.
     *
     * @return the java panel wrapped by this panel.
     * @de.renew.ensure (returnValue != null)
     */
    protected JPanel getPanel() {
        return this._panel;
    }

    /**
     * Returns the document container listener used by this component.
     *
     * @return the document container listener used by this component.
     * @de.renew.ensure (returnValue != null)
     */
    protected ContainerListener getContainerListener() {
        if (this._containerListener == null) {
            this._containerListener = new ContainerListener();
        }
        return this._containerListener;
    }

    /**
     * The document editor contained in this panel.
     */
    private DocumentEditor _editor;

    /**
     * The main panel of this panel.
     */
    private JPanel _panel;

    /**
     * The document container listener used by this component.
     */
    private ContainerListener _containerListener;

    /**
     * Observer listening to document change events of the editor component.
     */
    protected class ContainerListener extends DocumentContainerAdapter implements DocumentChangeListener {

        /**
         * Creates a new ContainerListener.
         */
        public ContainerListener() {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void documentChanged(DocumentChangeEvent event) {
            NetDocPanel.this.fireDocumentChangeEvent(event);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected void containerClosedImpl(DocumentContainerEvent event) {
            NetDocPanel.this.fireDocumentContainerEvent(
                    new DocumentContainerEvent(event.getType(), NetDocPanel.this, event.getInvolvedDocument()));
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected void containerClosingImpl(DocumentContainerEvent event) {
            NetDocPanel.this.fireDocumentContainerEvent(
                    new DocumentContainerEvent(event.getType(), NetDocPanel.this, event.getInvolvedDocument()));
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected void containerOpenedImpl(DocumentContainerEvent event) {
            NetDocPanel.this.fireDocumentContainerEvent(
                    new DocumentContainerEvent(event.getType(), NetDocPanel.this, event.getInvolvedDocument()));
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected void documentClosedImpl(DocumentContainerEvent event) {
            NetDocPanel.this.fireDocumentContainerEvent(
                    new DocumentContainerEvent(event.getType(), NetDocPanel.this, event.getInvolvedDocument()));
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected void documentClosingImpl(DocumentContainerEvent event) {
            NetDocPanel.this.fireDocumentContainerEvent(
                    new DocumentContainerEvent(event.getType(), NetDocPanel.this, event.getInvolvedDocument()));
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected void documentOpenedImpl(DocumentContainerEvent event) {
            NetDocPanel.this.fireDocumentContainerEvent(
                    new DocumentContainerEvent(event.getType(), NetDocPanel.this, event.getInvolvedDocument()));
        }
    }
}