package de.renew.netdoc.model.document.parts;

import de.renew.netdoc.model.doctarget.DocTarget;
import de.renew.netdoc.model.document.DocumentPart;
import de.renew.netdoc.model.document.DocumentParts;
import de.renew.netdoc.model.document.event.StructureChangeEvent;
import de.renew.netdoc.model.document.event.StructureChangeListener;
import de.renew.netdoc.model.document.event.TextChangeEvent;
import de.renew.netdoc.model.document.event.TextChangeListener;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.Writer;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;

/**
 * This class includes the Abstract NetDoc document part.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public abstract class AbstractDocumentPart implements DocumentPart, Serializable {

    /**
     * Creates a new AbstractDocumentPart initially not assigned to a target.
     */
    protected AbstractDocumentPart() {
        this(null);
    }

    /**
     * Creates a new AbstractDocumentPart assigned to the specified target.
     *
     * @param target the documentation target to assign to the new part.
     */
    protected AbstractDocumentPart(DocTarget target) {
        this._target = target;
        this.initialise();
    }

    /**
     * <p>Returns a clone of this document part.</p>
     * <p>To override implement {@link #cloneImpl()}.</p>
     *
     * @return a clone of this document part.
     * @de.renew.ensure (returnValue != null)
     * @de.renew.ensure (returnValue != this)
     * @de.renew.ensure (returnValue.getClass () == this.getClass())
     */
    @Override
    public final Object clone() {
        Object returnValue = this.cloneImpl();
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";
        assert (returnValue != this) : "Postcondition violated: (returnValue != this)";
        assert (returnValue.getClass() == this.getClass()) :
                "Postcondition violated: (returnValue.getClass() == this.getClass())";

        return returnValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DocTarget getTarget() {
        return this._target;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTarget(DocTarget newTarget) {
        this._target = newTarget;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isModified() {
        return this._isModified;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setModified(boolean isModified) {
        this._isModified = isModified;

        if (!isModified) {
            DocumentPart[] subParts = this.getParts().toArray(new DocumentPart[0]);
            for (int index = 0; index < subParts.length; index++) {
                subParts[index].setModified(false);
            }
        }
    }

    /**
     * <p>Adds the specified structure change listener to the list of listeners
     * being notified on structure changes of this document part.</p>
     * <p>To override implement {@link #addStructureChangeListenerImpl(StructureChangeListener)}.</p>
     *
     * @param listenerToAdd the listener to be added.
     * @de.renew.require (listenerToAdd != null)
     */
    @Override
    public final void addStructureChangeListener(StructureChangeListener listenerToAdd) {
        assert (listenerToAdd != null) : "Precondition violated: (listenerToAdd != null)";

        this.addStructureChangeListenerImpl(listenerToAdd);
    }

    /**
     * <p>Removes the specified structure change listener from the list of
     * listeners being notified on structure changes of this document part.</p>
     * <p>To override implement {@link #removeStructureChangeListenerImpl(StructureChangeListener)}.</p>
     *
     * @param listenerToRemove the listener to be removed.
     * @de.renew.require (listenerToRemove != null)
     */
    @Override
    public final void removeStructureChangeListener(StructureChangeListener listenerToRemove) {
        assert (listenerToRemove != null) : "Precondition violated: (listenerToRemove != null)";

        this.removeStructureChangeListenerImpl(listenerToRemove);
    }

    /**
     * <p>Returns the structure change listeners registered to this part.</p>
     * <p>To override implement {@link #getStructureChangeListenersImpl()}.</p>
     *
     * @return an enumeration of the structure change listeners.
     * @de.renew.ensure (returnValue != null)
     */
    @Override
    public final Enumeration<StructureChangeListener> getStructureChangeListeners() {
        Enumeration<StructureChangeListener> returnValue = this.getStructureChangeListenersImpl();
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * <p>Adds the specified text change listener to the list of listeners
     * being notified on text changes of this document part.</p>
     * <p>To override implement {@link #addTextChangeListenerImpl(TextChangeListener)}.</p>
     *
     * @param listenerToAdd the listener to be added.
     * @de.renew.require (listenerToAdd != null)
     */
    @Override
    public final void addTextChangeListener(TextChangeListener listenerToAdd) {
        assert (listenerToAdd != null) : "Precondition violated: (listenerToAdd != null)";

        this.addTextChangeListenerImpl(listenerToAdd);
    }

    /**
     * <p>Removes the specified text change listener from the list of listeners
     * being notified on text changes of this document part.</p>
     * <p>To override implement {@link #removeTextChangeListenerImpl(TextChangeListener)}.</p>
     *
     * @param listenerToRemove the listener to be removed.
     * @de.renew.require (listenerToRemove != null)
     */
    @Override
    public final void removeTextChangeListener(TextChangeListener listenerToRemove) {
        assert (listenerToRemove != null) : "Precondition violated: (listenerToRemove != null)";

        this.removeTextChangeListenerImpl(listenerToRemove);
    }

    /**
     * <p>Returns the text change listeners registered to this part.</p>
     * <p>To override implement {@link #getTextChangeListenersImpl()}.</p>
     *
     * @return an enumeration of the text change listeners.
     * @de.renew.ensure (returnValue != null)
     */
    @Override
    public final Enumeration<TextChangeListener> getTextChangeListeners() {
        Enumeration<TextChangeListener> returnValue = this.getTextChangeListenersImpl();
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * <p>Returns the name of this document part.</p>
     * <p>To override implement {@link #getNameImpl()}.</p>
     *
     * @return the name of this document part.
     * @de.renew.ensure (returnValue != null)
     */
    @Override
    public final String getName() {
        String returnValue = this.getNameImpl();
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * <p>Returns the main text of this document part.</p>
     * <p>To override implement {@link #getTextImpl()}.</p>
     *
     * @return the main text of this document part.
     * @de.renew.ensure (returnValue != null)
     */
    @Override
    public String getText() {
        String returnValue = this.getTextImpl();
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * <p>Sets the main text of this document part to the specified text.</p>
     * <p>To override implement {@link #setTextImpl(String)}.</p>
     *
     * @param text the new main text.
     * @throws IllegalArgumentException if this part was unmodifiable or the
     *                                  specified text was invalid.
     * @de.renew.require (text != null)
     */
    @Override
    public final void setText(String text) throws IllegalArgumentException {
        assert (text != null) : "Precondition violated: (text != null)";

        this.setTextImpl(text);
    }

    /**
     * <p>Returns the first occurrence of a sub part with the specified name.
     * </p>
     * <p>To override implement {@link #getPartImpl(String)}.</p>
     *
     * @param partName the name of the direct sub part to be returned.
     * @return the sub part with the specified name;<br>
     * or {@code null}, if this document part did not contain a sub part
     * with the specified name.
     * @de.renew.require (partName != null)
     */
    @Override
    public final DocumentPart getPart(String partName) {
        assert (partName != null) : "Precondition violated: (partName != null)";

        return this.getPartImpl(partName);
    }

    /**
     * <p>Removes the first occurrence of a sub part with the specified name.
     * </p>
     * <p>To override implement {@link #removePartImpl(String)}.</p>
     *
     * @param partName the name of the direct sub part to be removed.
     * @throws IllegalArgumentException if the sub part could not be removed.
     * @de.renew.require (partName != null)
     */
    @Override
    public final void removePart(String partName) throws IllegalArgumentException {
        assert (partName != null) : "Precondition violated: (partName != null)";

        this.removePartImpl(partName);
    }

    /**
     * <p>Sets the sub part specified by the name of the given part. If this
     * document part did not contain a sub part with the given
     * name, the specified part will be added.</p>
     * <p>To override implement {@link #setPartImpl(DocumentPart)}.</p>
     *
     * @param part the new sub part.
     * @throws IllegalArgumentException if this part was unmodifiable or the
     *                                  specified part was invalid.
     * @de.renew.require (part != null)
     */
    @Override
    public final void setPart(DocumentPart part) throws IllegalArgumentException {
        assert (part != null) : "Precondition violated: (part != null)";

        this.setPartImpl(part);
    }

    /**
     * <p>Determines whether this document part contains a sub part with the
     * specified name.</p>
     * <p>To override implement {@link #containsPartImpl(String)}.</p>
     *
     * @param partName the name of the sub part to be verified.
     * @return {@code true}, if this document part contains a direct sub
     * part with the specified name;<br>
     * {@code false} otherwise.
     * @de.renew.require (partName != null)
     */
    @Override
    public final boolean containsPart(String partName) {
        assert (partName != null) : "Precondition violated: (partName != null)";

        return this.containsPartImpl(partName);
    }

    /**
     * <p>Returns the number of direct sub parts contained in this document
     * part.</p>
     * <p>To override implement {@link #getPartCountImpl()}.</p>
     *
     * @return the number of direct sub parts contained in this document part.
     * @de.renew.ensure (returnValue > = 0)
     */
    @Override
    public final int getPartCount() {
        int returnValue = this.getPartCountImpl();
        assert (returnValue >= 0) : "Postcondition violated: (returnValue >= 0)";

        return returnValue;
    }

    /**
     * <p>Returns the direct sub parts of this document part.</p>
     * <p>To override implement {@link #getPartsImpl()}.</p>
     *
     * @return a collection containing {@link  DocumentPart} instances.
     * @de.renew.ensure (returnValue != null)
     * @de.renew.ensure ! returnValue.contains(null)
     */
    @Override
    public final Collection<DocumentPart> getParts() {
        Collection<DocumentPart> returnValue = this.getPartsImpl();
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";
        assert !returnValue.contains(null) : "Postcondition violated: ! returnValue.contains(null)";

        return returnValue;
    }

    /**
     * Returns the all sub parts with the specified name.
     *
     * @param partName the name of the sub parts to be returned.
     * @return a collection containing {@link DocumentPart} instances.
     * @de.renew.require (partName != null)
     * @de.renew.ensure (returnValue != null)
     * @de.renew.ensure ! returnValue.contains(null)
     */
    @Override
    public final Collection<DocumentPart> getParts(String partName) {
        assert (partName != null) : "Precondition violated: (partName != null)";

        Collection<DocumentPart> returnValue = this.getPartsImpl(partName);
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";
        assert !returnValue.contains(null) : "Postcondition violated: ! returnValue.contains(null)";

        return returnValue;
    }

    /**
     * <p>Returns the version of this document part.</p>
     * <p>To override implement {@link #getVersionImpl()}.</p>
     *
     * @return the version of this document part.
     * @de.renew.ensure (returnValue != null)
     */
    @Override
    public final String getVersion() {
        String returnValue = this.getVersionImpl();
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * <p>Returns the string representation of this document part.</p>
     * <p>To override implement {@link #toStringImpl()}.</p>
     *
     * @return the string representation of this document part.
     * @de.renew.ensure (returnValue != null)
     */
    @Override
    public final String toString() {
        String returnValue = this.toStringImpl();
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * <p>Writes this document part to the specified character output stream.
     * </p>
     * <p>To override implement {@link #writeToImpl(Writer)}.</p>
     *
     * @param stream the character output stream to write this part to.
     * @throws IOException if an I/O error occurs
     * @de.renew.require (stream != null)
     */
    @Override
    public final void writeTo(Writer stream) throws IOException {
        assert (stream != null) : "Precondition violated: (stream != null)";

        this.writeToImpl(stream);
    }

    /**
     * <p>Returns a clone of this document part.</p>
     * <p>Implements {@link #clone()}.</p>
     *
     * @return a clone of this document part.
     * @de.renew.ensure (returnValue != null)
     * @de.renew.ensure (returnValue != this)
     * @de.renew.ensure (returnValue.getClass () == this.getClass())
     */
    protected Object cloneImpl() {
        DocumentPart returnValue = this.cloneBase();

        Iterator<DocumentPart> parts = this.getParts().iterator();
        while (parts.hasNext()) {
            returnValue.setPart((DocumentPart) (parts.next()).clone());
        }

        return returnValue;
    }

    /**
     * Creates a clone of this document part not containing any sub parts.
     *
     * @return a clone of this document part not containing any sub parts.
     * @de.renew.ensure (returnValue != null)
     * @de.renew.ensure (returnValue != this)
     * @de.renew.ensure (returnValue.getClass () == this.getClass())
     */
    protected DocumentPart cloneBase() {
        Object[] parameters = new Object[0];
        Constructor<?> constructor = this.getConstructor(new Class[]{String.class});
        if (constructor != null) {
            parameters = new Object[]{this.getName()};
        } else {
            constructor = this.getConstructor(new Class[0]);
            if (constructor == null) {
                throw new Error(
                        "Could not find a default constructor. " + "cloneBase() must be overridden by sub class. " +
                                "Class: " + this.getClass().getName());
            }
        }

        DocumentPart returnValue = null;

        try {
            returnValue = (DocumentPart) constructor.newInstance(parameters);

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
     * Notifies all structure change listeners registered to this part, that the
     * structure of a part has changed using the specified event.
     *
     * @param event the event used to notify the listeners.
     * @de.renew.require (event != null)
     */
    protected void fireStructureChangeEvent(StructureChangeEvent event) {
        Enumeration<StructureChangeListener> listenerEnum = this.getStructureChangeListeners();

        if (event.getType().equals(StructureChangeEvent.PART_ADDED)) {
            while (listenerEnum.hasMoreElements()) {
                (listenerEnum.nextElement()).partAdded(event);
            }
        } else if (event.getType().equals(StructureChangeEvent.PART_REMOVED)) {
            while (listenerEnum.hasMoreElements()) {
                (listenerEnum.nextElement()).partRemoved(event);
            }
        }
    }

    /**
     * Notifies all text change listeners registered to this part, that the
     * text of a part has changed using the specified event.
     *
     * @param event the event used to notify the listeners.
     * @de.renew.require (event != null)
     */
    protected void fireTextChangeEvent(TextChangeEvent event) {
        Enumeration<TextChangeListener> listenerEnum = this.getTextChangeListeners();

        while (listenerEnum.hasMoreElements()) {
            (listenerEnum.nextElement()).textChanged(event);
        }
    }

    /**
     * Adds the specified structure change listener to the list of listeners
     * being notified on structure changes of this document part.
     *
     * @param listenerToAdd the listener to be added.
     * @de.renew.require (listenerToAdd != null)
     */
    protected void addStructureChangeListenerImpl(StructureChangeListener listenerToAdd) {
        StructureChangeListener[] oldListeners = this._structureChangeListeners;
        StructureChangeListener[] newListeners = new StructureChangeListener[oldListeners.length + 1];
        System.arraycopy(oldListeners, 0, newListeners, 0, oldListeners.length);
        newListeners[oldListeners.length] = listenerToAdd;
        this._structureChangeListeners = newListeners;
    }

    /**
     * Removes the specified structure change listener from the list of
     * listeners being notified on structure changes of this document part.
     *
     * @param listenerToRemove the listener to be removed.
     * @de.renew.require (listenerToRemove != null)
     */
    protected void removeStructureChangeListenerImpl(StructureChangeListener listenerToRemove) {
        StructureChangeListener[] oldListeners = this._structureChangeListeners;
        int index = Arrays.asList(oldListeners).indexOf(listenerToRemove);
        if (index >= 0) {
            StructureChangeListener[] newListeners = new StructureChangeListener[oldListeners.length - 1];
            System.arraycopy(oldListeners, 0, newListeners, 0, index);
            System.arraycopy(oldListeners, index + 1, newListeners, index, newListeners.length - index);
            this._structureChangeListeners = newListeners;
        }
    }

    /**
     * Returns the structure change listeners registered to this part.
     *
     * @return an enumeration of the structure change listeners.
     * @de.renew.ensure (returnValue != null)
     */
    protected Enumeration<StructureChangeListener> getStructureChangeListenersImpl() {
        return Collections.enumeration(Arrays.asList(this._structureChangeListeners));
    }

    /**
     * Adds the specified text change listener to the list of listeners being
     * notified on text changes of this document part.
     *
     * @param listenerToAdd the listener to be added.
     * @de.renew.require (listenerToAdd != null)
     */
    protected void addTextChangeListenerImpl(TextChangeListener listenerToAdd) {
        TextChangeListener[] oldListeners = this._textChangeListeners;
        TextChangeListener[] newListeners = new TextChangeListener[oldListeners.length + 1];
        System.arraycopy(oldListeners, 0, newListeners, 0, oldListeners.length);
        newListeners[oldListeners.length] = listenerToAdd;
        this._textChangeListeners = newListeners;
    }

    /**
     * Removes the specified text change listener from the list of listeners
     * being notified on text changes of this document part.
     *
     * @param listenerToRemove the listener to be removed.
     * @de.renew.require (listenerToRemove != null)
     */
    protected void removeTextChangeListenerImpl(TextChangeListener listenerToRemove) {
        TextChangeListener[] oldListeners = this._textChangeListeners;
        int index = Arrays.asList(oldListeners).indexOf(listenerToRemove);
        if (index >= 0) {
            TextChangeListener[] newListeners = new TextChangeListener[oldListeners.length - 1];
            System.arraycopy(oldListeners, 0, newListeners, 0, index);
            System.arraycopy(oldListeners, index + 1, newListeners, index, newListeners.length - index);
            this._textChangeListeners = newListeners;
        }
    }

    /**
     * Returns the text change listeners contained in this part.
     *
     * @return an enumeration of the text change listeners.
     * @de.renew.ensure (returnValue != null)
     */
    protected Enumeration<TextChangeListener> getTextChangeListenersImpl() {
        return Collections.enumeration(Arrays.asList(this._textChangeListeners));
    }

    /**
     * Returns the main text of this document part.
     *
     * @return the main text of this document part.
     * @de.renew.ensure (returnValue != null)
     */
    protected String getTextImpl() {
        return "";
    }

    /**
     * Sets the main text of this document part to the specified text.
     *
     * @param text the new main text.
     * @throws IllegalArgumentException if this part was unmodifiable or the
     * specified text was invalid.
     * @de.renew.require (text != null)
     */
    protected void setTextImpl(String text) throws IllegalArgumentException {
        throw new IllegalArgumentException("Unmodifiable");
    }

    /**
     * Removes the first occurrence of a sub part with the specified name.
     *
     * @param partName the name of the sub part to be removed.
     * @throws IllegalArgumentException if the sub part could not be removed.
     * @de.renew.require (partName != null)
     */
    protected void removePartImpl(String partName) throws IllegalArgumentException {
        if (this.containsLocalPart(partName)) {
            this.removeLocalPart(partName);
        } else {
            Iterator<DocumentPart> parts = this.getParts().iterator();
            while (parts.hasNext()) {
                DocumentPart current = parts.next();
                if (current.containsPart(partName)) {
                    current.removePart(partName);
                    return;
                }
            }
        }
    }

    /**
     * Sets the sub part specified by the name of the given part. If this
     * document part did not contain a sub part with the given name, the
     * specified part will be added.
     *
     * @param part the new sub part.
     * @throws IllegalArgumentException if this part was unmodifiable or the
     * specified part was invalid.
     * @de.renew.require (part != null)
     */
    protected void setPartImpl(DocumentPart part) throws IllegalArgumentException {
        if (this.containsLocalPart(part.getName())) {
            this.setLocalPart(part);
        } else {
            Iterator<DocumentPart> parts = this.getParts().iterator();
            while (parts.hasNext()) {
                DocumentPart current = parts.next();
                if (current.containsPart(part.getName())) {
                    current.setPart(part);
                    return;
                }
            }
            this.setLocalPart(part);
        }
    }

    /**
     * Determines whether this document part contains a sub part with the
     * specified name.
     *
     * @param partName the name of the sub part to be verified.
     * @return {@code true}, if this document part contains a sub part with the
     * specified name;<br>
     * {@code false} otherwise.
     * @de.renew.require (partName != null)
     */
    protected boolean containsPartImpl(String partName) {
        return (this.getPart(partName) != null);
    }

    /**
     * Returns the number of direct sub parts contained in this document part.
     *
     * @return the number of direct sub parts contained in this document part.
     * @de.renew.ensure (returnValue > = 0)
     */
    protected int getPartCountImpl() {
        return this.getParts().size();
    }

    /**
     * Returns the first occurrence of a sub part with the specified name.
     *
     * @param partName the name of the sub part to be returned.
     * @return the sub part with the specified name;<br>
     * or {@code null}, if this document part did not contain a sub part with
     * the specified name.
     * @de.renew.require (partName != null)
     */
    protected DocumentPart getPartImpl(String partName) {
        Iterator<DocumentPart> partIterator = this.getParts().iterator();
        while (partIterator.hasNext()) {
            DocumentPart current = partIterator.next();
            if (current.getName().equals(partName)) {
                return current;
            }
            DocumentPart subPart = current.getPart(partName);
            if (subPart != null) {
                return subPart;
            }
        }
        return null;
    }

    /**
     * Returns the direct sub parts of this document part.
     *
     * @return a collection containing {@link DocumentPart} instances.
     * @de.renew.ensure (returnValue != null)
     * @de.renew.ensure ! returnValue.contains(null)
     */
    protected Collection<DocumentPart> getPartsImpl() {
        return Collections.emptyList();
    }

    /**
     * Returns the all sub parts with the specified name.
     *
     * @param partName the name of the sub parts to be returned.
     * @return a collection containing {@link DocumentPart} instances.
     * @de.renew.require (partName != null)
     */
    protected Collection<DocumentPart> getPartsImpl(String partName) {
        Collection<DocumentPart> parts = new ArrayList<DocumentPart>();
        Iterator<DocumentPart> partIterator = this.getParts().iterator();
        while (partIterator.hasNext()) {
            DocumentPart current = partIterator.next();
            if (current.getName().equals(partName)) {
                parts.add(current);
            }
            parts.addAll(current.getParts(partName));
        }
        return parts;
    }

    /**
     * <p>Returns the version of this document part.</p>
     * <p>Implements {@link #getVersion()}.</p>
     *
     * @return the version of this document part.
     * @de.renew.ensure (returnValue != null)
     */
    protected String getVersionImpl() {
        return DocumentParts.NETDOC_TEX_UNIT_VERSION_PREFIX + DocumentParts.NETDOC_TEX_UNIT_VERSION_NUMBER;
    }

    /**
     * Writes this document part to the specified character output stream.
     *
     * @param stream the character output stream to write this part to.
     * @throws IOException if an I/O error occurs
     * @de.renew.require (stream != null)
     */
    protected void writeToImpl(Writer stream) throws IOException {
        stream.write(this.toString());
    }

    /**
     * Returns the name of this document part.
     *
     * @return the name of this document part.
     * @de.renew.ensure (returnValue != null)
     */
    protected abstract String getNameImpl();

    /**
     * Returns the string representation of this document part.
     *
     * @return the string representation of this document part.
     * @de.renew.ensure (returnValue != null)
     */
    protected abstract String toStringImpl();

    /**
     * Determines whether this part contains a direct sub part with the
     * specified name.
     *
     * @param partName the name of the part to verify.
     * @return {@code true}, if this part contains a direct sub part with the
     * specified name;<br>
     * {@code false} otherwise.
     * @de.renew.require (partName != null)
     */
    protected boolean containsLocalPart(String partName) {
        return (this.getLocalPart(partName) != null);
    }

    /**
     * Returns the first occurrence of a direct sub part of this part matching
     * the specified name.
     *
     * @param partName the name of the part to return.
     * @return the first occurrence of a direct sub part of this part matching
     * the specified name;<br>
     * or {@code null}, if this part did not contain a direct sub part matching
     * that name.
     * @de.renew.require (partName != null)
     */
    protected DocumentPart getLocalPart(String partName) {
        Iterator<DocumentPart> parts = this.getParts().iterator();
        while (parts.hasNext()) {
            DocumentPart current = parts.next();
            if (current.getName().equals(partName)) {
                return current;
            }
        }
        return null;
    }

    /**
     * Removes the first occurrence of a direct sub part of this part matching
     * the specified name.
     *
     * @param partName the name of the part to remove.
     * @throws IllegalArgumentException if this part was unmodifiable.
     * @de.renew.require (partName != null)
     */
    protected void removeLocalPart(String partName) {
        throw new IllegalArgumentException("Unmodifiable");
    }

    /**
     * Sets the first occurrence of a direct sub part of this part matching
     * the name of the specified part. If this part did not contain a sub part
     * with that name, the specified part will be added.
     *
     * @param partToSet the document part to set/add.
     * @throws IllegalArgumentException if this part was unmodifiable or the
     * specified part was invalid.
     * @de.renew.require (partToSet != null)
     */
    protected void setLocalPart(DocumentPart partToSet) {
        throw new IllegalArgumentException("Unmodifiable");
    }

    /**
     * Initialises this document part.
     */
    private void initialise() {
        this._isModified = false;
        this._structureChangeListeners = new StructureChangeListener[0];
        this._textChangeListeners = new TextChangeListener[0];
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
     * @throws ClassNotFoundException Throws exception if class is not found
     *
     */
    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        this.initialise();
    }

    /**
     * Writes an object and throws an exception if needed.
     *
     * @param stream Parameter for the object
     * @throws IOException Signals that an I/O exception to some sort has occurred
     *
     */
    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
    }

    /**
     * The is-modified-property of this document part.
     */
    private transient boolean _isModified;

    /**
     * The documentation target assigned to this document.
     */
    private transient DocTarget _target;

    /**
     * The list holding the structure change listeners of this document part.
     */
    private transient StructureChangeListener[] _structureChangeListeners;

    /**
     * The list holding the text change listeners of this document part.
     */
    private transient TextChangeListener[] _textChangeListeners;
}