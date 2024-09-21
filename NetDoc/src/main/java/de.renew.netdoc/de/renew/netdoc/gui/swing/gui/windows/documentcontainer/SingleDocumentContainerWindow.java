package de.renew.netdoc.gui.swing.gui.windows.documentcontainer;

import de.renew.netdoc.gui.swing.gui.windows.DocumentContainerWindow;
import de.renew.netdoc.model.container.event.DocumentContainerEvent;
import de.renew.netdoc.model.document.Document;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collection;

/**
 * This class provides the NetDoc DocumentContainer window containing a single document.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public abstract class SingleDocumentContainerWindow extends DocumentContainerWindow {

    /**
     * Creates a new SingleDocumentContainerWindow.
     */
    protected SingleDocumentContainerWindow() {
        super();
    }

    /**
     * Returns the document controlled by this window.
     *
     * @return the document controlled by this window;<br>
     * or {@code null}, if this window did not contain a document.
     */
    public abstract Document getDocument();

    /**
     * {@inheritDoc}
     */
    @Override
    public void close(boolean forceClose) {
        Document document = this.getDocument();
        if (document != null) {
            this.closeDocument(document, forceClose);
        }

        if (forceClose) {
            this.getJavaFrame().dispose();
            this.fireDocumentContainerEvent(new DocumentContainerEvent(DocumentContainerEvent.CONTAINER_CLOSED, this));
        } else {
            if (this.getDocument() == null) {
                this.fireDocumentContainerEvent(
                        new DocumentContainerEvent(DocumentContainerEvent.CONTAINER_CLOSING, this));
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Document getCurrentDocument() {
        return this.getDocument();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Collection<Document> getDocumentsImpl() {
        Document document = this.getDocument();
        ArrayList<Document> documents = new ArrayList<Document>();
        if (document != null) {
            documents.add(document);
        }
        return documents;
    }

    /**
     * Observer listening to window events of the frame used to display the
     * document.
     */
    protected class FrameListener extends WindowAdapter {

        /**
         * Creates a new FrameListener.
         */
        public FrameListener() {
        }

        /**
         * Invoked when a window is about to be closed.
         *
         * @param event the corresponding window event.
         */
        @Override
        public void windowClosing(WindowEvent event) {
            SingleDocumentContainerWindow.this.close(false);
        }

        /**
         * Invoked the first time a window is made visible.
         *
         * @param event the corresponding window event.
         */
        @Override
        public void windowOpened(WindowEvent event) {
            SingleDocumentContainerWindow.this.fireDocumentContainerEvent(
                    new DocumentContainerEvent(DocumentContainerEvent.CONTAINER_OPENED,
                            SingleDocumentContainerWindow.this));
        }
    }
}