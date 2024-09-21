package de.renew.netdoc.model.container.managers;

import de.renew.netdoc.io.IOManager;
import de.renew.netdoc.model.container.event.CloseRequestListener;
import de.renew.netdoc.model.container.event.DocumentContainerEvent;
import de.renew.netdoc.model.container.event.DocumentContainerListener;
import de.renew.netdoc.model.document.Document;
import de.renew.netdoc.model.document.DocumentMap;
import de.renew.netdoc.model.document.event.DocumentChangeEvent;
import de.renew.netdoc.model.document.event.DocumentChangeListener;
import de.renew.netdoc.model.document.maps.AbstractDocumentMap;
import de.renew.netdoc.model.container.ContainerException;
import de.renew.netdoc.model.container.ContainerManager;
import de.renew.netdoc.model.container.DocumentContainer;
import de.renew.netdoc.model.doctarget.DocTarget;
import de.renew.netdoc.renew.hotdraw.DrawingTarget;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;


/**
 * Abstract container manager.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public abstract class AbstractContainerManager implements ContainerManager {

    /**
     * Creates a new AbstractContainerManager.
     */
    protected AbstractContainerManager() {
        this._documentChangeListeners = new DocumentChangeListener[0];
        this._documentContainerListeners = new DocumentContainerListener[0];
    }

    /**
     * <p>Adds the specified close request listener to the list of listeners
     * being notified on close requests of objects controlled by this manager.
     * </p>
     * <p>To override implement {@link
     * #addCloseRequestListenerImpl(CloseRequestListener)}.</p>
     * @param listenerToAdd the listener to be added.
     * @de.renew.require (listenerToAdd != null)
     */
    @Override
    public final void addCloseRequestListener(CloseRequestListener listenerToAdd) {
        assert (listenerToAdd != null) : "Precondition violated: (listenerToAdd != null)";

        this.addCloseRequestListenerImpl(listenerToAdd);
    }

    /**
     * <p>Removes the specified close request listener from the list of
     * listeners being notified on close requests of objects controlled by this
     * manager.</p>
     * <p>To override implement {@link
     * #removeCloseRequestListenerImpl(CloseRequestListener)}.</p>
     * @param listenerToRemove the listener to be removed.
     * @de.renew.require (listenerToRemove != null)
     */
    @Override
    public final void removeCloseRequestListener(CloseRequestListener listenerToRemove) {
        assert (listenerToRemove != null) : "Precondition violated: (listenerToRemove != null)";

        this.removeCloseRequestListenerImpl(listenerToRemove);
    }

    /**
     * <p>Returns the close request listeners registered to this manager.</p>
     * <p>To override implement {@link #getCloseRequestListenersImpl()}.
     * </p>
     * @return an enumeration of the close request listeners.
     * @de.renew.ensure (returnValue != null)
     */
    @Override
    public final Enumeration<CloseRequestListener> getCloseRequestListeners() {
        Enumeration<CloseRequestListener> returnValue = this
                        .getCloseRequestListenersImpl();
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * <p>Adds the specified document change listener to the list of listeners
     * being notified on changes of documents controlled by this manager.</p>
     * <p>To override implement {@link
     * #addDocumentChangeListenerImpl(DocumentChangeListener)}.</p>
     * @param listenerToAdd the listener to be added.
     * @de.renew.require (listenerToAdd != null)
     */
    @Override
    public final void addDocumentChangeListener(DocumentChangeListener listenerToAdd) {
        assert (listenerToAdd != null) : "Precondition violated: (listenerToAdd != null)";

        this.addDocumentChangeListenerImpl(listenerToAdd);
    }

    /**
     * <p>Removes the specified document change listener from the list of
     * listeners being notified on changes of documents controlled by this
     * manager.</p>
     * <p>To override implement {@link
     * #removeDocumentChangeListenerImpl(DocumentChangeListener)}.</p>
     * @param listenerToRemove the listener to be removed.
     * @de.renew.require (listenerToRemove != null)
     */
    @Override
    public final void removeDocumentChangeListener(DocumentChangeListener listenerToRemove) {
        assert (listenerToRemove != null) : "Precondition violated: (listenerToRemove != null)";

        this.removeDocumentChangeListenerImpl(listenerToRemove);
    }

    /**
     * <p>Returns the document change listeners registered to this manager.</p>
     * <p>To override implement {@link
     * #getDocumentChangeListenersImpl()}.</p>
     * @return an enumeration of the document change listeners.
     * @de.renew.ensure (returnValue != null)
     */
    @Override
    public final Enumeration<DocumentChangeListener> getDocumentChangeListeners() {
        Enumeration<DocumentChangeListener> returnValue = this
                        .getDocumentChangeListenersImpl();
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * <p>Adds the specified document container listener to the list of
     * listeners being notified on changes of documents controlled by this
     * manager.</p>
     * <p>To override implement {@link
     * #addDocumentContainerListenerImpl(DocumentContainerListener)}.</p>
     * @param listenerToAdd the listener to be added.
     * @de.renew.require (listenerToAdd != null)
     */
    @Override
    public final void addDocumentContainerListener(DocumentContainerListener listenerToAdd) {
        assert (listenerToAdd != null) : "Precondition violated: (listenerToAdd != null)";

        this.addDocumentContainerListenerImpl(listenerToAdd);
    }

    /**
     * <p>Removes the specified document container listener from the list of
     * listeners being notified on changes of documents controlled by this
     * manager.</p>
     * <p>To override implement {@link
     * #removeDocumentContainerListenerImpl(DocumentContainerListener)}.</p>
     * @param listenerToRemove the listener to be removed.
     * @de.renew.require (listenerToRemove != null)
     */
    @Override
    public final void removeDocumentContainerListener(DocumentContainerListener listenerToRemove) {
        assert (listenerToRemove != null) : "Precondition violated: (listenerToRemove != null)";

        this.removeDocumentContainerListenerImpl(listenerToRemove);
    }

    /**
     * <p>Returns the document container listeners registered to this manager.
     * </p>
     * <p>To override implement {@link
     * #getDocumentContainerListenersImpl()}.</p>
     * @return an enumeration of the document container listeners.
     * @de.renew.ensure (returnValue != null)
     */
    @Override
    public final Enumeration<DocumentContainerListener> getDocumentContainerListeners() {
        Enumeration<DocumentContainerListener> returnValue = this
                        .getDocumentContainerListenersImpl();
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * <p>Returns the Input/Output manager used by this manager.</p>
     * <p>To override implement {@link #getIOManagerImpl()}.</p>
     * @return the Input/Output manager used by this manager.
     * @de.renew.ensure (returnValue != null)
     */
    @Override
    public final IOManager getIOManager() {
        IOManager returnValue = this.getIOManagerImpl();
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * <p>Returns the document map specifying all documents and documentation
     * targets used by this manager.</p>
     * <p>To override implement {@link #getDocumentMapImpl()}.</p>
     * @return the document map.
     * @de.renew.ensure (returnValue != null)
     */
    @Override
    public final DocumentMap getDocumentMap() {
        DocumentMap returnValue = this.getDocumentMapImpl();
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * <p>Returns a collection of all NetDoc document containers currently
     * controlled by this manager.</p>
     * <p>To override implement {@link #getDocumentContainersImpl()}.</p>
     * @return a collection containing {@link
     * DocumentContainer} instances.
     * @de.renew.ensure (returnValue != null)
     * @de.renew.ensure ! returnValue.contains(null)
     */
    @Override
    public final Collection<DocumentContainer> getDocumentContainers() {
        Collection<DocumentContainer> returnValue = this
                        .getDocumentContainersImpl();
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";
        assert !returnValue.contains(
                        null) : "Postcondition violated: ! returnValue.contains(null)";

        return returnValue;
    }

    /**
     * <p>Returns the container holding the specified NetDoc document.</p>
     * <p>To override implement {@link
     * #getDocumentContainerImpl(Document)}.</p>
     * @param document  the NetDoc document whose container is to be returned.
     * @return the container holding the NetDoc document;<br>
     * or <code>null</code>, if the document is currently not held by a
     * container controlled by this manager.
     * @de.renew.require (document != null)
     */
    @Override
    public final DocumentContainer getDocumentContainer(Document document) {
        assert (document != null) : "Precondition violated: (document != null)";

        return this.getDocumentContainerImpl(document);
    }

    /**
     * <p>Closes the specified NetDoc document.</p>
     * <p>To override implement {@link
     * #closeDocumentImpl(Document,boolean)}.</p>
     * @param document  the NetDoc document to be closed.
     * @param forceClose specifies whether the document should be forced to
     * close. If {@code true}, forces the document to close sending a close
     * event to all container listeners. If {@code false}, the document will
     * not be closed and a closing event will be send instead.
     * @throws ContainerException if the document could not be closed.
     * @de.renew.require (document != null)
     */
    @Override
    public final void closeDocument(Document document, boolean forceClose)
                    throws ContainerException {
        assert (document != null) : "Precondition violated: (document != null)";

        this.closeDocumentImpl(document, forceClose);
    }

    /**
     * <p>Opens the specified NetDoc document. If the document was already
     * open, the manager will try to focus on it.</p>
     * <p>To override implement {@link #openDocumentImpl(Document)}.</p>
     * @param documentToOpen the NetDoc document to be opened.
     * @return the container holding the document.
     * @throws ContainerException if the document could not be opened.
     * @de.renew.require (documentToOpen != null)
     * @de.renew.ensure (returnValue != null)
     */
    @Override
    public final DocumentContainer openDocument(Document documentToOpen)
                    throws ContainerException {
        assert (documentToOpen != null) : "Precondition violated: (documentToOpen != null)";

        return this.openDocumentImpl(documentToOpen);
    }

    /**
     * <p>Opens the NetDoc document corresponding to the specified
     * documentation target. If the document was already open, the manager will
     * try to focus on it.</p>
     * <p>To override implement {@link #openDocumentImpl(DocTarget)}.</p>
     * @param docTarget the documentation target specifying the NetDoc
     * document to be opened.
     * @return the container holding the document.
     * @throws ContainerException if the document could not be opened.
     * @de.renew.require (drawing != null)
     * @de.renew.ensure (returnValue != null)
     */
    @Override
    public final DocumentContainer openDocument(DocTarget docTarget)
                    throws ContainerException {
        assert (docTarget != null) : "Precondition violated: (docTarget != null)";

        return this.openDocumentImpl(docTarget);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void close(boolean forceClose) throws ContainerException {
        // close document containers
        DocumentContainer[] documentContainers = this.getDocumentContainers()
                        .toArray(new DocumentContainer[0]);
        for (int index = 0; index < documentContainers.length; index++) {
            documentContainers[index].close(forceClose);
        }

        // close manager
        if (forceClose) {
            this.close();
        }
    }


    /**
     * Returns all documentation targets currently controlled by this manager.
     * @return a collection containing {@link
     * DocTarget} instances.
     * @de.renew.ensure (returnValue != null)
     * @de.renew.ensure ! returnValue.contains(null)
     */
    protected abstract Collection<DrawingTarget> getTargets();

    /**
     * Notifies all document change listeners registered to this manager
     * that a document has changed using the specified event.
     * @param event the document change event to be fired.
     * @de.renew.require (event != null)
     */
    protected void fireDocumentChangeEvent(DocumentChangeEvent event) {
        Enumeration<DocumentChangeListener> listenerEnum = this
                        .getDocumentChangeListeners();

        while (listenerEnum.hasMoreElements()) {
            (listenerEnum.nextElement()).documentChanged(event);
        }
    }

    /**
     * Notifies all document container listeners registered to this container
     * about the specified event.
     * @param event the document container event to be fired.
     * @de.renew.require (event != null)
     */
    protected void fireDocumentContainerEvent(DocumentContainerEvent event) {
        Enumeration<DocumentContainerListener> listenerEnum = this
                        .getDocumentContainerListeners();

        if (event.getType().equals(DocumentContainerEvent.CONTAINER_CLOSED)) {
            while (listenerEnum.hasMoreElements()) {
                (listenerEnum.nextElement()).containerClosed(event);
            }
        } else if (event.getType()
                        .equals(DocumentContainerEvent.CONTAINER_CLOSING)) {
            while (listenerEnum.hasMoreElements()) {
                (listenerEnum.nextElement()).containerClosing(event);
            }
        } else if (event.getType()
                        .equals(DocumentContainerEvent.CONTAINER_OPENED)) {
            while (listenerEnum.hasMoreElements()) {
                (listenerEnum.nextElement()).containerOpened(event);
            }
        } else if (event.getType()
                        .equals(DocumentContainerEvent.DOCUMENT_CLOSED)) {
            while (listenerEnum.hasMoreElements()) {
                (listenerEnum.nextElement()).documentClosed(event);
            }
        } else if (event.getType()
                        .equals(DocumentContainerEvent.DOCUMENT_CLOSING)) {
            while (listenerEnum.hasMoreElements()) {
                (listenerEnum.nextElement()).documentClosing(event);
            }
        } else if (event.getType()
                        .equals(DocumentContainerEvent.DOCUMENT_OPENED)) {
            while (listenerEnum.hasMoreElements()) {
                (listenerEnum.nextElement()).documentOpened(event);
            }
        }
    }

    /**
     * Adds the specified close request listener to the list of listeners
     * being notified on close requests of objects controlled by this manager.
     * @param listenerToAdd the listener to be added.
     * @de.renew.require (listenerToAdd != null)
     */
    protected void addCloseRequestListenerImpl(CloseRequestListener listenerToAdd) {
    }

    /**
     * Removes the specified close request listener from the list of
     * listeners being notified on close requests of objects controlled by this
     * manager.
     * @param listenerToRemove the listener to be removed.
     * @de.renew.require (listenerToRemove != null)
     */
    protected void removeCloseRequestListenerImpl(CloseRequestListener listenerToRemove) {
    }

    /**
     * Returns the close request listeners registered to this manager.
     * @return an enumeration of the close request listeners.
     * @de.renew.ensure (returnValue != null)
     */
    @SuppressWarnings("unchecked")
    //The empty enumeration can obviously be casted to an arbitrary generic type.
    protected Enumeration<CloseRequestListener> getCloseRequestListenersImpl() {
        return Collections.enumeration(Collections.EMPTY_LIST);
    }

    /**
     * Adds the specified document change listener to the list of listeners
     * being notified on changes of documents controlled by this manager.
     * @param listenerToAdd the listener to be added.
     * @de.renew.require (listenerToAdd != null)
     */
    protected void addDocumentChangeListenerImpl(DocumentChangeListener listenerToAdd) {
        DocumentChangeListener[] oldListeners = this._documentChangeListeners;
        DocumentChangeListener[] newListeners = new DocumentChangeListener[oldListeners.length
                        + 1];
        System.arraycopy(oldListeners, 0, newListeners, 0, oldListeners.length);
        newListeners[oldListeners.length] = listenerToAdd;
        this._documentChangeListeners = newListeners;
    }

    /**
     * Removes the specified document change listener from the list of
     * listeners being notified on changes of documents controlled by this
     * manager.
     * @param listenerToRemove the listener to be removed.
     * @de.renew.require (listenerToRemove != null)
     */
    protected void removeDocumentChangeListenerImpl(DocumentChangeListener listenerToRemove) {
        DocumentChangeListener[] oldListeners = this._documentChangeListeners;
        int index = Arrays.asList(oldListeners).indexOf(listenerToRemove);
        if (index >= 0) {
            DocumentChangeListener[] newListeners = new DocumentChangeListener[oldListeners.length
                            - 1];
            System.arraycopy(oldListeners, 0, newListeners, 0, index);
            System.arraycopy(oldListeners, index + 1, newListeners, index,
                            newListeners.length - index);
            this._documentChangeListeners = newListeners;
        }
    }

    /**
     * Returns the document change listeners registered to this manager.
     * @return an enumeration of the document change listeners.
     * @de.renew.ensure (returnValue != null)
     */
    protected Enumeration<DocumentChangeListener> getDocumentChangeListenersImpl() {
        return Collections.enumeration(
                        Arrays.asList(this._documentChangeListeners));
    }

    /**
     * Adds the specified document container listener to the list of listeners
     * being notified on changes of documents controlled by this manager.
     * @param listenerToAdd the listener to be added.
     * @de.renew.require (listenerToAdd != null)
     */
    protected void addDocumentContainerListenerImpl(DocumentContainerListener listenerToAdd) {
        DocumentContainerListener[] oldListeners = this._documentContainerListeners;
        DocumentContainerListener[] newListeners = new DocumentContainerListener[oldListeners.length
                        + 1];
        System.arraycopy(oldListeners, 0, newListeners, 0, oldListeners.length);
        newListeners[oldListeners.length] = listenerToAdd;
        this._documentContainerListeners = newListeners;
    }

    /**
     * Removes the specified document container listener from the list of
     * listeners being notified on changes of documents controlled by this
     * manager.
     * @param listenerToRemove the listener to be removed.
     * @de.renew.require (listenerToRemove != null)
     */
    protected void removeDocumentContainerListenerImpl(DocumentContainerListener listenerToRemove) {
        DocumentContainerListener[] oldListeners = this._documentContainerListeners;
        int index = Arrays.asList(oldListeners).indexOf(listenerToRemove);
        if (index >= 0) {
            DocumentContainerListener[] newListeners = new DocumentContainerListener[oldListeners.length
                            - 1];
            System.arraycopy(oldListeners, 0, newListeners, 0, index);
            System.arraycopy(oldListeners, index + 1, newListeners, index,
                            newListeners.length - index);
            this._documentContainerListeners = newListeners;
        }
    }

    /**
     * Returns the document container listeners registered to this manager.
     * @return an enumeration of the document container listeners.
     * @de.renew.ensure (returnValue != null)
     */
    protected Enumeration<DocumentContainerListener> getDocumentContainerListenersImpl() {
        return Collections.enumeration(
                        Arrays.asList(this._documentContainerListeners));
    }

    /**
     * Returns the Input/Output manager used by this manager.
     * @return the Input/Output manager used by this manager.
     * @de.renew.ensure (returnValue != null)
     */
    protected abstract IOManager getIOManagerImpl();

    /**
     * Returns the document map specifying all documents and documentation
     * targets used by this manager.
     * @return the document map.
     * @de.renew.ensure (returnValue != null)
     */
    protected DocumentMap getDocumentMapImpl() {
        if (this._documentMap == null) {
            this._documentMap = this.new ContainerMap();
        }
        return this._documentMap;
    }

    /**
     * Returns a collection of all NetDoc document containers currently
     * controlled by this manager.
     * @return a collection containing {@link
     * DocumentContainer} instances.
     * @de.renew.ensure (returnValue != null)
     * @de.renew.ensure ! returnValue.contains(null)
     */
    protected abstract Collection<DocumentContainer> getDocumentContainersImpl();

    /**
     * Returns the container holding the specified NetDoc document.
     * @param document  the NetDoc document whose container is to be returned.
     * @return the container holding the NetDoc document;<br>
     * or <code>null</code>, if the document is currently not held by a
     * container controlled by this manager.
     * @de.renew.require (document != null)
     */
    protected DocumentContainer getDocumentContainerImpl(Document document) {
        Iterator<DocumentContainer> containerIterator = this
                        .getDocumentContainers().iterator();
        while (containerIterator.hasNext()) {
            DocumentContainer current = containerIterator.next();
            if (current.containsDocument(document)) {
                return current;
            }
        }
        return null;
    }

    /**
     * Closes the specified NetDoc document.
     * @param document  the NetDoc document to be closed.
     * @param forceClose specifies whether the document should be forced to
     * close. If {@code true}, forces the document to close sending a close
     * event to all container listeners. If {@code false}, the document will
     * not be closed and a closing event will be send instead.
     * @throws ContainerException if the document could not be closed.
     * @de.renew.require (document != null)
     */
    protected void closeDocumentImpl(Document document, boolean forceClose)
                    throws ContainerException {
        DocumentContainer container = this.getDocumentContainer(document);
        if (container != null) {
            container.closeDocument(document, forceClose);
        }
    }

    /**
     * Opens the specified NetDoc document. If the document was already open,
     * the manager will try to focus on it.
     * @param documentToOpen the NetDoc document to be opened.
     * @return the container holding the document.
     * @throws ContainerException if the document could not be opened.
     * @de.renew.require (documentToOpen != null)
     * @de.renew.ensure (returnValue != null)
     */
    protected DocumentContainer openDocumentImpl(Document documentToOpen)
                    throws ContainerException {
        throw new ContainerException("Unsupported operation");
    }

    /**
     * Opens the NetDoc document corresponding to the specified documentation
     * target. If the document was already open, the manager will try to focus
     * on it.
     * @param docTarget the documentation target specifying the NetDoc document
     * to be opened.
     * @return the container holding the document.
     * @throws ContainerException  if the document could not be opened.
     * @de.renew.require (docTarget != null)
     * @de.renew.ensure (returnValue != null)
     */
    protected DocumentContainer openDocumentImpl(DocTarget docTarget)
                    throws ContainerException {
        throw new ContainerException("Unsupported operation");
    }

    /**
     * Closes this manager without further request.
     * @throws ContainerException if the manager could not be closed.
     */
    protected abstract void close() throws ContainerException;

    /**
     * The document change listeners registered to this manager.
     */
    private DocumentChangeListener[] _documentChangeListeners;

    /**
     * The document container listeners registered to this manager.
     */
    private DocumentContainerListener[] _documentContainerListeners;

    /**
     * The document map used by this manager.
     */
    private DocumentMap _documentMap;

    /**
     * Document map providing the documents of all document containers
     */
    protected class ContainerMap extends AbstractDocumentMap {

        /**
         * Creates a new ContainerMap
         */
        public ContainerMap() {
        }

        /**
         * @inheritDoc
         */
        @Override
        protected Collection<Document> getDocumentsImpl() {
            Collection<Document> documents = new ArrayList<Document>();

            DocumentContainer[] containers = AbstractContainerManager.this
                            .getDocumentContainers()
                            .toArray(new DocumentContainer[0]);

            for (int index = 0; index < containers.length; index++) {
                documents.addAll(containers[index].getDocuments());
            }

            return documents;
        }

        /**
         * @inheritDoc
         */
        @Override
        protected Collection<DrawingTarget> getTargetsImpl() {
            return AbstractContainerManager.this.getTargets();
        }
    }
}