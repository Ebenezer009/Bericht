package de.renew.netdoc.model.container.containers;

import de.renew.netdoc.model.container.event.DocumentContainerEvent;
import de.renew.netdoc.model.container.event.DocumentContainerListener;
import de.renew.netdoc.model.document.Document;
import de.renew.netdoc.model.document.event.DocumentChangeEvent;
import de.renew.netdoc.model.document.event.DocumentChangeListener;
import de.renew.netdoc.model.container.ContainerException;
import de.renew.netdoc.model.container.DocumentContainer;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;


/**
 * Abstract NetDoc document container.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public abstract class AbstractDocumentContainer implements DocumentContainer {

    /**
     * Creates a new AbstractDocumentContainer.
     */
    public AbstractDocumentContainer() {
        this._documentChangeListeners = new DocumentChangeListener[0];
        this._documentContainerListeners = new DocumentContainerListener[0];
    }

    /**
     * <p>Adds the specified document change listener to the list of listeners
     * being notified on changes of documents contained in this container.</p>
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
     * listeners being notified on changes of documents contained in this
     * container.</p>
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
     * <p>Returns the document change listeners registered to this container.
     * </p>
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
     * listeners being notified on changes of documents contained in this
     * container.</p>
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
     * listeners being notified on changes of documents contained in this
     * container.</p>
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
     * <p>Returns the document container listeners registered to this
     * container.</p>
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
     * <p>Closes the specified document.</p>
     * <p>To override implement {@link
     * #closeDocumentImpl(Document,boolean)}.</p>
     * @param documentToClose  the document to be closed.
     * @param forceClose specifies whether the document should be forced to
     * close. If {@code true}, forces the document to close sending a close
     * event to all container listeners. If {@code false}, the document will
     * not be closed and a closing event will be send instead.
     * @de.renew.require (documentToClose != null)
     */
    @Override
    public final void closeDocument(Document documentToClose,
                                    boolean forceClose) {
        assert (documentToClose != null) : "Precondition violated: (documentToClose != null)";

        this.closeDocumentImpl(documentToClose, forceClose);
    }

    /**
     * <p>Determines whether the specified document is contained in this
     * container.</p>
     * <p>To override implement {@link #containsDocumentImpl(Document)}.
     * </p>
     * @param document  the document to be verified.
     * @return <code>true</code>, if the specified document is contained;<br>
     * <code>false</code> otherwise.
     * @de.renew.require (document != null)
     */
    @Override
    public final boolean containsDocument(Document document) {
        assert (document != null) : "Precondition violated: (document != null)";

        return this.containsDocumentImpl(document);
    }

    /**
     * <p>Sends the focus to the specified document.</p>
     * <p>To override implement {@link #focusOnDocumentImpl(Document)}.
     * </p>
     * @param document  the document to focus on.
     * @de.renew.require (document != null)
     */
    @Override
    public final void focusOnDocument(Document document) {
        assert (document != null) : "Precondition violated: (document != null)";

        this.focusOnDocumentImpl(document);
    }

    /**
     * <p>Opens the specified document in this container.</p>
     * <p>To override implement {@link #openDocumentImpl(Document)}.</p>
     * @param documentToOpen  the document to be opened.
     * @throws ContainerException if the document could not be opened.
     * @de.renew.require (documentToOpen != null)
     */
    @Override
    public final void openDocument(Document documentToOpen)
                    throws ContainerException {
        assert (documentToOpen != null) : "Precondition violated: (documentToOpen != null)";

        this.openDocumentImpl(documentToOpen);
    }

    /**
     * <p>Returns a collection of all documents contained in this container.
     * </p>
     * <p>To override implement {@link #getDocumentsImpl()}.</p>
     * @return a collection of {@link
     * Document} instances.
     * @de.renew.ensure (returnValue != null)
     * @de.renew.ensure ! returnValue.contains(null);
     */
    @Override
    public final Collection<Document> getDocuments() {
        Collection<Document> returnValue = this.getDocumentsImpl();
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";
        assert !returnValue.contains(
                        null) : "Postcondition violated: ! returnValue.contains(null)";

        return returnValue;
    }


    /**
     * Notifies all document change listeners registered to this container
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
     * Adds the specified document change listener to the list of listeners
     * being notified on changes of documents contained in this container.
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
     * listeners being notified on changes of documents contained in this
     * container.
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
     * Returns the document change listeners registered to this container.
     * @return an enumeration of the document change listeners.
     * @de.renew.ensure (returnValue != null)
     */
    protected Enumeration<DocumentChangeListener> getDocumentChangeListenersImpl() {
        return Collections.enumeration(
                        Arrays.asList(this._documentChangeListeners));
    }

    /**
     * Adds the specified document container listener to the list of listeners
     * being notified on changes of documents contained in this container.
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
     * listeners being notified on changes of documents contained in this
     * container.
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
     * Returns the document container listeners registered to this container.
     * @return an enumeration of the document container listeners.
     * @de.renew.ensure (returnValue != null)
     */
    protected Enumeration<DocumentContainerListener> getDocumentContainerListenersImpl() {
        return Collections.enumeration(
                        Arrays.asList(this._documentContainerListeners));
    }

    /**
     * Closes the specified document.
     * @param documentToClose  the document to be closed.
     * @param forceClose specifies whether the document should be forced to
     * close. If {@code true}, forces the document to close sending a close
     * event to all container listeners. If {@code false}, the document will
     * not be closed and a closing event will be send instead.
     * @de.renew.require (documentToClose != null)
     */
    protected abstract void closeDocumentImpl(Document documentToClose,
                                              boolean forceClose);

    /**
     * Determines whether the specified document is contained in this
     * container.
     * @param document  the document to be verified.
     * @return <code>true</code>, if the specified document is contained;<br>
     * <code>false</code> otherwise.
     * @de.renew.require (document != null)
     */
    protected boolean containsDocumentImpl(Document document) {
        return this.getDocuments().contains(document);
    }

    /**
     * Sends the focus to the specified document.
     * @param document  the document to focus on.
     * @de.renew.require (document != null)
     */
    protected abstract void focusOnDocumentImpl(Document document);

    /**
     * Opens the specified document in this container.
     * @param documentToOpen  the document to be opened.
     * @throws ContainerException if the document could not be opened.
     * @de.renew.require (documentToOpen != null)
     */
    protected void openDocumentImpl(Document documentToOpen)
                    throws ContainerException {
        throw new ContainerException("Unsupported operation");
    }

    /**
     * Returns a collection of all documents contained in this container.
     * @return a collection of {@link
     * Document} instances.
     * @de.renew.ensure (returnValue != null)
     * @de.renew.ensure ! returnValue.contains(null);
     */
    protected abstract Collection<Document> getDocumentsImpl();

    /**
     * The document change listeners registered to this container.
     */
    private DocumentChangeListener[] _documentChangeListeners;

    /**
     * The document container listeners registered to this container.
     */
    private DocumentContainerListener[] _documentContainerListeners;
}