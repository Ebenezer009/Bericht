package de.renew.netdoc.model.container;

import de.renew.netdoc.model.container.event.DocumentContainerListener;
import de.renew.netdoc.model.document.Document;
import de.renew.netdoc.model.document.event.DocumentChangeListener;

import java.util.Collection;
import java.util.Enumeration;


/**
 * NetDoc document container.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public interface DocumentContainer {

    /**
     * Adds the specified document change listener to the list of listeners
     * being notified on changes of documents contained in this container.
     * @param listenerToAdd the listener to be added.
     * @de.renew.require (listenerToAdd != null)
     */
    public void addDocumentChangeListener(DocumentChangeListener listenerToAdd);

    /**
     * Removes the specified document change listener from the list of
     * listeners being notified on changes of documents contained in this
     * container.
     * @param listenerToRemove the listener to be removed.
     * @de.renew.require (listenerToRemove != null)
     */
    public void removeDocumentChangeListener(DocumentChangeListener listenerToRemove);

    /**
     * Returns the document change listeners registered to this container.
     * @return an enumeration of the document change listeners.
     * @de.renew.ensure (returnValue != null)
     */
    public Enumeration<DocumentChangeListener> getDocumentChangeListeners();

    /**
     * Adds the specified document container listener to the list of listeners
     * being notified on changes of documents contained in this container.
     * @param listenerToAdd the listener to be added.
     * @de.renew.require (listenerToAdd != null)
     */
    public void addDocumentContainerListener(DocumentContainerListener listenerToAdd);

    /**
     * Removes the specified document container listener from the list of
     * listeners being notified on changes of documents contained in this
     * container.
     * @param listenerToRemove the listener to be removed.
     * @de.renew.require (listenerToRemove != null)
     */
    public void removeDocumentContainerListener(DocumentContainerListener listenerToRemove);

    /**
     * Returns the document container listeners registered to this container.
     * @return an enumeration of the document container listeners.
     * @de.renew.ensure (returnValue != null)
     */
    public Enumeration<DocumentContainerListener> getDocumentContainerListeners();

    /**
     * Closes this container.
     * @param forceClose specifies whether this container should be forced to
     * close. If {@code true}, forces this container to close sending a close
     * event to all container listeners. If {@code false}, this container will
     * not be closed and a closing event will be send instead.
     */
    public void close(boolean forceClose);

    /**
     * Closes the specified document.
     * @param documentToClose  the document to be closed.
     * @param forceClose specifies whether the document should be forced to
     * close. If {@code true}, forces the document to close sending a close
     * event to all container listeners. If {@code false}, the document will
     * not be closed and a closing event will be send instead.
     * @de.renew.require (documentToClose != null)
     */
    public void closeDocument(Document documentToClose, boolean forceClose);

    /**
     * Determines whether the specified document is contained in this
     * container.
     * @param document  the document to be verified.
     * @return <code>true</code>, if the specified document is contained;<br>
     * <code>false</code> otherwise.
     * @de.renew.require (document != null)
     */
    public boolean containsDocument(Document document);

    /**
     * Sends the focus to the specified document.
     * @param document  the document to focus on.
     * @de.renew.require (document != null)
     */
    public void focusOnDocument(Document document);

    /**
     * Opens the specified document in this container.
     * @param documentToOpen  the document to be opened.
     * @throws ContainerException if the document could not be opened.
     * @de.renew.require (documentToOpen != null)
     */
    public void openDocument(Document documentToOpen) throws ContainerException;

    /**
     * Returns the document currently focussed by this container.
     * @return the document currently focussed by this container;<br>
     * or <code>null</code>, if there is currently no document focussed.
     */
    public Document getCurrentDocument();

    /**
     * Returns a collection of all documents contained in this container.
     * @return a collection of {@link
     * Document} instances.
     * @de.renew.ensure (returnValue != null)
     * @de.renew.ensure ! returnValue.contains(null);
     */
    public Collection<Document> getDocuments();
}