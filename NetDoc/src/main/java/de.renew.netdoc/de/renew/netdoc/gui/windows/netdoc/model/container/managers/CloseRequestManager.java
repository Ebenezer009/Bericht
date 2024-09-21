package de.renew.netdoc.model.container.managers;

import de.renew.netdoc.model.container.ContainerException;
import de.renew.netdoc.model.container.DocumentContainer;
import de.renew.netdoc.model.container.event.CloseRequestEvent;
import de.renew.netdoc.model.container.event.CloseRequestListener;
import de.renew.netdoc.model.container.event.DocumentContainerAdapter;
import de.renew.netdoc.model.container.event.DocumentContainerEvent;
import de.renew.netdoc.model.document.Document;
import de.renew.netdoc.model.document.event.DocumentChangeEvent;
import de.renew.netdoc.model.document.event.DocumentChangeListener;

import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;

/**
 * This class includes the  manager for the Close-Request container.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public abstract class CloseRequestManager extends AbstractContainerManager implements CloseRequestListener {

    /**
     * Creates a new CloseRequestContainerManager.
     */
    protected CloseRequestManager() {
        super();

        this._closeRequestListeners = new CloseRequestListener[0];
    }

    /**
     * <p>Determines whether the originator of the specified close event should
     * be allowed to close.</p>
     * <p>To override implement {@link #allowClosingImpl(CloseRequestEvent)}.</p>
     *
     * @param event the close request event to be verified.
     * @return {@code true}, if the originator of the specified event should be
     * allowed to close;<br>
     * {@code false} otherwise.
     * @de.renew.require (event != null)
     */
    @Override
    public final boolean allowClosing(CloseRequestEvent event) {
        assert (event != null) : "Precondition violated: (event != null)";

        return this.allowClosingImpl(event);
    }

    /**
     * Determines whether the originator of the specified close event should be
     * allowed to close.
     *
     * @param event the close request event to be verified.
     * @return {@code true}, if the originator of the specified event should be
     * allowed to close;<br>
     * {@code false} otherwise.
     * @de.renew.require (event != null)
     */
    protected boolean allowClosingImpl(CloseRequestEvent event) {
        Enumeration<CloseRequestListener> listenerEnum = this.getCloseRequestListeners();
        while (listenerEnum.hasMoreElements()) {
            if (!(listenerEnum.nextElement()).allowClosing(event)) {
                return false;
            }
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close(boolean forceClose) throws ContainerException {
        // close document containers
        DocumentContainer[] documentContainers = this.getDocumentContainers().toArray(new DocumentContainer[0]);
        for (int index = 0; index < documentContainers.length; index++) {
            if (forceClose) {
                documentContainers[index].close(true);
            } else {
                if (!this.allowClosing(new CloseRequestEvent(documentContainers[index]))) {
                    return;
                }
                documentContainers[index].close(forceClose);
                if (this.getDocumentContainers().contains(documentContainers[index])) {
                    return;
                }
            }
        }

        // close manager
        if (forceClose || this.allowClosing(new CloseRequestEvent(this))) {
            this.close();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void addCloseRequestListenerImpl(CloseRequestListener listenerToAdd) {
        CloseRequestListener[] oldListeners = this._closeRequestListeners;
        CloseRequestListener[] newListeners = new CloseRequestListener[oldListeners.length + 1];
        System.arraycopy(oldListeners, 0, newListeners, 0, oldListeners.length);
        newListeners[oldListeners.length] = listenerToAdd;
        this._closeRequestListeners = newListeners;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void removeCloseRequestListenerImpl(CloseRequestListener listenerToRemove) {
        CloseRequestListener[] oldListeners = this._closeRequestListeners;
        int index = Arrays.asList(oldListeners).indexOf(listenerToRemove);
        if (index >= 0) {
            CloseRequestListener[] newListeners = new CloseRequestListener[oldListeners.length - 1];
            System.arraycopy(oldListeners, 0, newListeners, 0, index);
            System.arraycopy(oldListeners, index + 1, newListeners, index, newListeners.length - index);
            this._closeRequestListeners = newListeners;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Enumeration<CloseRequestListener> getCloseRequestListenersImpl() {
        return Collections.enumeration(Arrays.asList(this._closeRequestListeners));
    }

    /**
     * Adds the internal listeners of this manager to the specified document
     * container.
     *
     * @param containerToObserve the document container to be observed.
     * @de.renew.require (containerToObserve != null)
     */
    protected void addListeners(DocumentContainer containerToObserve) {
        containerToObserve.addDocumentChangeListener(this.getDocumentListener());
        containerToObserve.addDocumentContainerListener(this.getDocumentListener());
    }

    /**
     * Removes the internal listeners of this manager from the specified
     * document container.
     *
     * @param containerToUpdate the document container to be updated.
     * @de.renew.require (containerToUpdate != null)
     */
    protected void removeListeners(DocumentContainer containerToUpdate) {
        containerToUpdate.removeDocumentChangeListener(this.getDocumentListener());
        containerToUpdate.removeDocumentContainerListener(this.getDocumentListener());
    }

    /**
     * Closes the specified document without further request.
     *
     * @param documentToClose the document to be closed.
     * @throws ContainerException if the document could not be closed.
     * @de.renew.require (documentToClose != null)
     */
    protected void closeDocument(Document documentToClose) throws ContainerException {
        DocumentContainer container = this.getDocumentContainer(documentToClose);
        if (container != null) {
            container.closeDocument(documentToClose, true);
        }
    }

    /**
     * Closes the specified document container without further request.
     *
     * @param containerToClose the document container to be closed.
     * @throws ContainerException if the container could not be closed.
     * @de.renew.require (containerToClose != null)
     */
    protected void closeDocumentContainer(DocumentContainer containerToClose) throws ContainerException {
        containerToClose.close(true);
    }

    /**
     * Returns the document listener used by this manager.
     *
     * @return the document listener used by this manager.
     * @de.renew.ensure (returnValue != null)
     */
    protected DocumentListener getDocumentListener() {
        if (this._documentListener == null) {
            this._documentListener = this.new DocumentListener();
        }
        return this._documentListener;
    }

    /**
     * The close request listeners registered to this manager.
     */
    private CloseRequestListener[] _closeRequestListeners;

    /**
     * The document listener of this manager.
     */
    private DocumentListener _documentListener;

    /**
     * The document container listener used by this manager to handle drawing
     * close events and to delegate the events to all document listeners
     * registered to this manager. This listener should be added to all
     * document containers controlled by this manager.
     */
    protected class DocumentListener extends DocumentContainerAdapter implements DocumentChangeListener {

        /**
         * Creates a new DocumentListener.
         */
        public DocumentListener() {
        }

        /**
         * <p>Invoked when a document has changed.</p>
         * <p>To override implement {@link #documentChangedImpl(DocumentChangeEvent)}.</p>
         *
         * @param event the corresponding change event.
         * @de.renew.require (event != null)
         */
        @Override
        public final void documentChanged(DocumentChangeEvent event) {
            assert (event != null) : "Precondition violated: (event != null)";

            this.documentChangedImpl(event);
        }

        /**
         * Invoked when a document has changed.
         *
         * @param event the corresponding change event.
         * @de.renew.require (event != null)
         */
        public void documentChangedImpl(DocumentChangeEvent event) {
            CloseRequestManager.this.fireDocumentChangeEvent(event);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected void containerClosedImpl(DocumentContainerEvent event) {
            CloseRequestManager.this.removeListeners(event.getOriginator());
            CloseRequestManager.this.fireDocumentContainerEvent(event);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected void containerClosingImpl(DocumentContainerEvent event) {
            if (CloseRequestManager.this.allowClosing(new CloseRequestEvent(event.getOriginator()))) {
                CloseRequestManager.this.fireDocumentContainerEvent(event);
                try {
                    CloseRequestManager.this.closeDocumentContainer(event.getOriginator());
                } catch (ContainerException e) {
                }
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected void containerOpenedImpl(DocumentContainerEvent event) {
            CloseRequestManager.this.fireDocumentContainerEvent(event);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected void documentClosedImpl(DocumentContainerEvent event) {
            CloseRequestManager.this.fireDocumentContainerEvent(event);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected void documentClosingImpl(DocumentContainerEvent event) {
            if (CloseRequestManager.this.allowClosing(new CloseRequestEvent(event.getInvolvedDocument()))) {
                CloseRequestManager.this.fireDocumentContainerEvent(event);
                try {
                    CloseRequestManager.this.closeDocument(event.getInvolvedDocument());
                } catch (ContainerException e) {
                }
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected void documentOpenedImpl(DocumentContainerEvent event) {
            CloseRequestManager.this.fireDocumentContainerEvent(event);
        }
    }
}