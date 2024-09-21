package de.renew.netdoc.model.container;

import de.renew.netdoc.io.IOManager;
import de.renew.netdoc.model.container.event.CloseRequestListener;
import de.renew.netdoc.model.container.event.DocumentContainerListener;
import de.renew.netdoc.model.document.Document;
import de.renew.netdoc.model.document.DocumentMap;
import de.renew.netdoc.model.document.event.DocumentChangeListener;
import de.renew.netdoc.model.doctarget.DocTarget;

import java.util.Collection;
import java.util.Enumeration;


/**
 * NetDoc container manager.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public interface ContainerManager {

    /**
     * Adds the specified close request listener to the list of listeners
     * being notified on close requests of objects controlled by this manager.
     * @param listenerToAdd the listener to be added.
     * @de.renew.require (listenerToAdd != null)
     */
    public void addCloseRequestListener(CloseRequestListener listenerToAdd);

    /**
     * Removes the specified close request listener from the list of
     * listeners being notified on close requests of objects controlled by this
     * manager.
     * @param listenerToRemove the listener to be removed.
     * @de.renew.require (listenerToRemove != null)
     */
    public void removeCloseRequestListener(CloseRequestListener listenerToRemove);

    /**
     * Returns the close request listeners registered to this manager.
     * @return an enumeration of the close request listeners.
     * @de.renew.ensure (returnValue != null)
     */
    public Enumeration<CloseRequestListener> getCloseRequestListeners();

    /**
     * Adds the specified document change listener to the list of listeners
     * being notified on changes of documents controlled by this manager.
     * @param listenerToAdd the listener to be added.
     * @de.renew.require (listenerToAdd != null)
     */
    public void addDocumentChangeListener(DocumentChangeListener listenerToAdd);

    /**
     * Removes the specified document change listener from the list of
     * listeners being notified on changes of documents controlled by this
     * manager.
     * @param listenerToRemove the listener to be removed.
     * @de.renew.require (listenerToRemove != null)
     */
    public void removeDocumentChangeListener(DocumentChangeListener listenerToRemove);

    /**
     * Returns the document change listeners registered to this manager.
     * @return an enumeration of the document change listeners.
     * @de.renew.ensure (returnValue != null)
     */
    public Enumeration<DocumentChangeListener> getDocumentChangeListeners();

    /**
     * Adds the specified document container listener to the list of listeners
     * being notified on changes of documents controlled by this manager.
     * @param listenerToAdd the listener to be added.
     * @de.renew.require (listenerToAdd != null)
     */
    public void addDocumentContainerListener(DocumentContainerListener listenerToAdd);

    /**
     * Removes the specified document container listener from the list of
     * listeners being notified on changes of documents controlled by this
     * manager.
     * @param listenerToRemove the listener to be removed.
     * @de.renew.require (listenerToRemove != null)
     */
    public void removeDocumentContainerListener(DocumentContainerListener listenerToRemove);

    /**
     * Returns the document container listeners registered to this manager.
     * @return an enumeration of the document container listeners.
     * @de.renew.ensure (returnValue != null)
     */
    public Enumeration<DocumentContainerListener> getDocumentContainerListeners();

    /**
     * Returns the document map specifying all documents and documentation
     * targets used by this manager.
     * @return the document map.
     * @de.renew.ensure (returnValue != null)
     */
    public DocumentMap getDocumentMap();

    /**
     * Returns the Input/Output manager used by this manager.
     * @return the Input/Output manager used by this manager.
     * @de.renew.ensure (returnValue != null)
     */
    public IOManager getIOManager();

    /**
     * Returns the NetDoc document currently focussed by this manager.
     * @return the NetDoc document currently focussed by this manager;<br>
     * or {@code null}, if there is currently no document focussed.
     */
    public Document getCurrentDocument();

    /**
     * Returns the documentation target currently focussed by this manager.
     * @return the documentation target currently focussed by this manager;<br>
     * or {@code null}, if there is currently no target focussed.
     */
    public DocTarget getCurrentTarget();

    /**
     * Returns a collection of all NetDoc document containers currently
     * controlled by this manager.
     * @return a collection containing {@link
     * DocumentContainer} instances.
     * @de.renew.ensure (returnValue != null)
     * @de.renew.ensure ! returnValue.contains(null)
     */
    public Collection<DocumentContainer> getDocumentContainers();

    /**
     * Returns the container holding the specified NetDoc document.
     * @param document  the NetDoc document whose container is to be returned.
     * @return the container holding the NetDoc document;<br>
     * or {@code null}, if the document is currently not held by a
     * container controlled by this manager.
     * @de.renew.require (document != null)
     */
    public DocumentContainer getDocumentContainer(Document document);

    /**
     * Closes this manager and all of his controlled containers.
     * @param forceClose specifies whether this manager should be forced to
     * close. If {@code true}, forces this manager and all of his containers to
     * close. If {@code false}, this manager will not be closed and a closing
     * event will be send to all his containers instead.
     * @throws ContainerException if the manager could not be closed.
     */
    public void close(boolean forceClose) throws ContainerException;

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
    public void closeDocument(Document document, boolean forceClose)
                    throws ContainerException;

    /**
     * Opens the specified NetDoc document. If the document was already open,
     * the manager will try to focus on it.
     * @param documentToOpen the NetDoc document to be opened.
     * @return the container holding the document.
     * @throws ContainerException if the document could not be opened.
     * @de.renew.require (documentToOpen != null)
     * @de.renew.ensure (returnValue != null)
     */
    public DocumentContainer openDocument(Document documentToOpen)
                    throws ContainerException;

    /**
     * Opens the NetDoc document corresponding to the specified documentation
     * target. If the document was already open, the manager will try to focus
     * on it.
     * @param docTarget the documentation target specifying the NetDoc document
     * to be opened.
     * @return the container holding the document.
     * @throws ContainerException if the document could not be opened.
     * @de.renew.require (docTarget != null)
     * @de.renew.ensure (returnValue != null)
     */
    public DocumentContainer openDocument(DocTarget docTarget)
                    throws ContainerException;
}