package de.renew.netdoc.model.document.documents;

import de.renew.netdoc.model.doctarget.DocTarget;
import de.renew.netdoc.model.document.Document;
import de.renew.netdoc.model.document.DocumentPart;
import de.renew.netdoc.model.document.event.DocumentChangeEvent;
import de.renew.netdoc.model.document.event.DocumentChangeListener;
import de.renew.netdoc.model.document.parts.AbstractDocumentPart;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;

/**
 * This class includes the Abstract NetDoc document.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public abstract class AbstractDocument extends AbstractDocumentPart implements Document {

    /**
     * Creates a new AbstractDocument initially not assigned to a target.
     */
    protected AbstractDocument() {
        this(null);
    }

    /**
     * Creates a new AbstractDocument assigned to the specified target.
     *
     * @param target the documentation target to assign to the new document.
     */
    protected AbstractDocument(DocTarget target) {
        super(target);
        this.initialise();
    }

    /**
     * <p>Adds the specified document change listener to the list of listeners
     * being notified on changes of this document.</p>
     * <p>To override implement {@link #addDocumentChangeListenerImpl(DocumentChangeListener)}.</p>
     *
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
     * listeners being notified on changes of this document.</p>
     * <p>To override implement {@link #removeDocumentChangeListenerImpl(DocumentChangeListener)}.</p>
     *
     * @param listenerToRemove the listener to be removed.
     * @de.renew.require (listenerToRemove != null)
     */
    @Override
    public final void removeDocumentChangeListener(DocumentChangeListener listenerToRemove) {
        assert (listenerToRemove != null) : "Precondition violated: (listenerToRemove != null)";

        this.removeDocumentChangeListenerImpl(listenerToRemove);
    }

    /**
     * <p>Returns the document change listeners registered to this document.</p>
     * <p>To override implement {@link #getDocumentChangeListenersImpl()}.</p>
     *
     * @return an enumeration of the document change listeners.
     * @de.renew.ensure (returnValue != null)
     */
    @Override
    public final Enumeration<DocumentChangeListener> getDocumentChangeListeners() {
        Enumeration<DocumentChangeListener> returnValue = this.getDocumentChangeListenersImpl();
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected DocumentPart cloneBase() {
        Object[] parameters = new Object[0];
        Constructor<?> constructor = this.getConstructor(new Class[]{DocTarget.class, String.class});
        if (constructor != null) {
            parameters = new Object[]{this.getTarget(), this.getVersion()};
        } else {
            constructor = this.getConstructor(new Class[]{DocTarget.class});
            if (constructor == null) {
                throw new Error(
                        "Could not find a default constructor. " + "cloneBase() must be overridden by sub class. " +
                                "Class: " + this.getClass().getName());
            }
            parameters = new Object[]{this.getTarget()};
        }

        Document returnValue = null;

        try {
            returnValue = (Document) constructor.newInstance(parameters);

            if (!returnValue.getText().equals(this.getText())) {
                returnValue.setText(this.getText());
            }
        } catch (Exception e) {
            throw new Error("Error while creating clone using default constructor. " +
                    "cloneBase() must be overridden by sub class. " + "Class: " + this.getClass().getName(), e);
        }

        return returnValue;
    }

    /**
     * Notifies all document change listeners registered to this manager
     * that a document has changed using the specified event.
     *
     * @param event the document change event to be fired.
     * @de.renew.require (event != null)
     */
    protected void fireDocumentChangeEvent(DocumentChangeEvent event) {
        Enumeration<DocumentChangeListener> listenerEnum = this.getDocumentChangeListeners();

        while (listenerEnum.hasMoreElements()) {
            (listenerEnum.nextElement()).documentChanged(event);
        }
    }

    /**
     * Adds the specified document change listener to the list of listeners
     * being notified on changes of this document.
     *
     * @param listenerToAdd the listener to be added.
     * @de.renew.require (listenerToAdd != null)
     */
    protected void addDocumentChangeListenerImpl(DocumentChangeListener listenerToAdd) {
        DocumentChangeListener[] oldListeners = this._documentChangeListeners;
        DocumentChangeListener[] newListeners = new DocumentChangeListener[oldListeners.length + 1];
        System.arraycopy(oldListeners, 0, newListeners, 0, oldListeners.length);
        newListeners[oldListeners.length] = listenerToAdd;
        this._documentChangeListeners = newListeners;
    }

    /**
     * Removes the specified document change listener from the list of
     * listeners being notified on changes of this document.
     *
     * @param listenerToRemove the listener to be removed.
     * @de.renew.require (listenerToRemove != null)
     */
    protected void removeDocumentChangeListenerImpl(DocumentChangeListener listenerToRemove) {
        DocumentChangeListener[] oldListeners = this._documentChangeListeners;
        int index = Arrays.asList(oldListeners).indexOf(listenerToRemove);
        if (index >= 0) {
            DocumentChangeListener[] newListeners = new DocumentChangeListener[oldListeners.length - 1];
            System.arraycopy(oldListeners, 0, newListeners, 0, index);
            System.arraycopy(oldListeners, index + 1, newListeners, index, newListeners.length - index);
            this._documentChangeListeners = newListeners;
        }
    }

    /**
     * Returns the document change listeners registered to this document.
     *
     * @return an enumeration of the document change listeners.
     * @de.renew.ensure (returnValue != null)
     */
    protected Enumeration<DocumentChangeListener> getDocumentChangeListenersImpl() {
        return Collections.enumeration(Arrays.asList(this._documentChangeListeners));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected final String getNameImpl() {
        try {
            return this.getTarget().getName().toString();
        } catch (NullPointerException e) {
            return "";
        }
    }

    /**
     * Initialises this document part.
     */
    private void initialise() {
        this._documentChangeListeners = new DocumentChangeListener[0];
    }

    /**
     * Returns the constructor of this document part matching the specified
     * parameter types.
     *
     * @param parameterTypes the types of the parameters.
     * @return the constructor; or {@code null}, if this document part has no
     * constructor matching the specified parameter types.
     */
    private Constructor<?> getConstructor(Class<?>[] parameterTypes) {
        try {
            return this.getClass().getConstructor(parameterTypes);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Reads an object and initialises it.
     *
     * @param stream Parameter stream for object
     * @throws IOException Signals that an I/O exception to some sort has occurred
     * @throws ClassNotFoundException if class is not found an exception is thrown
     *
     */
    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        this.initialise();
    }

    /**
     * Writes an object and throws IOException if needed.
     *
     * @param stream Parameter stream for object
     * @throws IOException Signals that an I/O exception to some sort has occurred
     *
     */
    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
    }

    /**
     * The document change listeners registered to this document.
     */
    private transient DocumentChangeListener[] _documentChangeListeners;
}