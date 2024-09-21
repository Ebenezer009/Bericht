package de.renew.netdoc.model.document;

import de.renew.netdoc.model.document.event.DocumentChangeListener;

import java.util.Enumeration;

/**
 * Interface for the model of a NetDoc document.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public interface Document extends DocumentPart {

    /**
     * Adds the specified document change listener to the list of listeners
     * being notified on changes of this document.
     *
     * @param listenerToAdd the listener to be added.
     * @de.renew.require (listenerToAdd ! = null)
     */
    public void addDocumentChangeListener(DocumentChangeListener listenerToAdd);

    /**
     * Removes the specified document change listener from the list of
     * listeners being notified on changes of this document.
     *
     * @param listenerToRemove the listener to be removed.
     * @de.renew.require (listenerToRemove ! = null)
     */
    public void removeDocumentChangeListener(DocumentChangeListener listenerToRemove);

    /**
     * Returns the document change listeners registered to this document.
     *
     * @return an enumeration of the document change listeners.
     * @de.renew.ensure (returnValue ! = null)
     */
    public Enumeration<DocumentChangeListener> getDocumentChangeListeners();
}