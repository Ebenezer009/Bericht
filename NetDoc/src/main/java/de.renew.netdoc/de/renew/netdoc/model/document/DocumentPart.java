package de.renew.netdoc.model.document;

import de.renew.netdoc.model.document.event.StructureChangeListener;
import de.renew.netdoc.model.document.event.TextChangeListener;
import de.renew.netdoc.model.doctarget.DocTarget;

import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.Enumeration;


/**
 * Model of a NetDoc document part.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public interface DocumentPart extends Cloneable {

    /**
     * Returns a clone of this document part.
     * @return a clone of this document part.
     * @de.renew.ensure (returnValue != null)
     * @de.renew.ensure (returnValue != this)
     * @de.renew.ensure (returnValue.getClass() == this.getClass())
     */
    public Object clone();

    /**
     * Returns the documentation target assigned to this document part.
     * @return the documentation target assigned to this document part;<br>
     * or {@code null}, if this document part is currently not assigned to a
     * target.
     */
    public DocTarget getTarget();

    /**
     * Assigns this document part to the specified documentation target.
     * @param newTarget the new documentation target to assign to this document
     * part;<br>
     * or {@code null}, if the assignment is to be repealed.
     * @throws IllegalArgumentException if this document part did not allow
     * resetting of the target.
     */
    public void setTarget(DocTarget newTarget);

    /**
     * Adds the specified structure change listener to the list of listeners
     * being notified on structure changes of this document part.
     * @param listenerToAdd the listener to be added.
     * @de.renew.require (listenerToAdd != null)
     */
    public void addStructureChangeListener(StructureChangeListener listenerToAdd);

    /**
     * Removes the specified structure change listener from the list of
     * listeners being notified on structure changes of this document part.
     * @param listenerToRemove the listener to be removed.
     * @de.renew.require (listenerToRemove != null)
     */
    public void removeStructureChangeListener(StructureChangeListener listenerToRemove);

    /**
     * Returns the structure change listeners registered to this part.
     * @return an enumeration of the structure change listeners.
     * @de.renew.ensure (returnValue != null)
     */
    public Enumeration<StructureChangeListener> getStructureChangeListeners();

    /**
     * Adds the specified text change listener to the list of listeners being
     * notified on text changes of this document part.
     * @param listenerToAdd the listener to be added.
     * @de.renew.require (listenerToAdd != null)
     */
    public void addTextChangeListener(TextChangeListener listenerToAdd);

    /**
     * Removes the specified text change listener from the list of listeners
     * being notified on text changes of this document part.
     * @param listenerToRemove the listener to be removed.
     * @de.renew.require (listenerToRemove != null)
     */
    public void removeTextChangeListener(TextChangeListener listenerToRemove);

    /**
     * Returns the text change listeners registered to this part.
     * @return an enumeration of the text change listeners.
     * @de.renew.ensure (returnValue != null)
     */
    public Enumeration<TextChangeListener> getTextChangeListeners();

    /**
     * Returns the name of this document part.
     * @return the name of this document part.
     * @de.renew.ensure (returnValue != null)
     */
    public String getName();

    /**
     * Returns the main text of this document part.
     * @return the main text of this document part.
     * @de.renew.ensure (returnValue != null)
     */
    public String getText();

    /**
     * Determines whether this document part has been modified. A part should
     * be marked as modified, if its text has changed, a sub part has been
     * modified or, if a sub part has been added/removed/set.
     * @return {@code true}, if this document part has been modified;<br>
     * {@code false} otherwise.
     */
    public boolean isModified();

    /**
     * Sets the is-modified-property of this document part. A part should be
     * marked as modified, if its text has changed, a sub part has been
     * modified or, if a sub part has been added/removed/set.
     * @param isModified the new value of the is-modified-property of this
     * document part. If {@code true}, this document part will be marked as
     * modified. Surrounding managers can use this as a hint that this
     * document needs to be saved. If {@code false}, this document part will be
     * marked as unmodified.
     */
    public void setModified(boolean isModified);

    /**
     * Sets the main text of this document part to the specified text.
     * @param text  the new main text.
     * @throws IllegalArgumentException  if this part was unmodifiable or the
     * specified text was invalid.
     * @de.renew.require (text != null)
     */
    public void setText(String text) throws IllegalArgumentException;

    /**
     * Returns the first occurrence of a sub part with the specified name.
     * @param partName the name of the sub part to be returned.
     * @return the sub part with the specified name;<br>
     * or {@code null}, if this document part did not contain a sub part with
     * the specified name.
     * @de.renew.require (partName != null)
     */
    public DocumentPart getPart(String partName);

    /**
     * Removes the first occurrence of a sub part with the specified name.
     * @param partName  the name of the direct sub part to be removed.
     * @throws IllegalArgumentException  if the sub part could not be removed.
     * @de.renew.require (partName != null)
     */
    public void removePart(String partName) throws IllegalArgumentException;

    /**
     * Sets the sub part specified by the name of the given part. If
     * this document part did not contain a sub part with the given name, the
     * specified part will be added.
     * @param part  the new sub part.
     * @throws IllegalArgumentException  if this part was unmodifiable or the
     * specified part was invalid.
     * @de.renew.require (part != null)
     */
    public void setPart(DocumentPart part) throws IllegalArgumentException;

    /**
     * Determines whether this document part contains a sub part with the
     * specified name.
     * @param partName  the name of the direct sub part to be verified.
     * @return {@code true}, if this document part contains a direct sub
     * part with the specified name;<br>
     * {@code false} otherwise.
     * @de.renew.require (partName != null)
     */
    public boolean containsPart(String partName);

    /**
     * Returns the number of direct sub parts contained in this document part.
     * @return the number of direct sub parts contained in this document part.
     * @de.renew.ensure (returnValue >= 0)
     */
    public int getPartCount();

    /**
     * Returns the direct sub parts of this document part.
     * @return a collection containing {@link
     * DocumentPart} instances.
     * @de.renew.ensure (returnValue != null)
     * @de.renew.ensure ! returnValue.contains(null)
     */
    public Collection<DocumentPart> getParts();

    /**
     * Returns the all sub parts with the specified name.
     * @param partName the name of the sub parts to be returned.
     * @return a collection containing {@link
     * DocumentPart} instances.
     * @de.renew.require (partName != null)
     * @de.renew.ensure (returnValue != null)
     * @de.renew.ensure ! returnValue.contains(null)
     */
    public Collection<DocumentPart> getParts(String partName);

    /**
     * Returns the version of this document part.
     * @return the version of this document part.
     * @de.renew.ensure (returnValue != null)
     */
    public String getVersion();

    /**
     * Returns the string representation of this document part.
     * @return the string representation of this document part.
     * @de.renew.ensure (returnValue != null)
     */
    @Override
    public String toString();

    /**
     * Writes this document part to the specified character output stream.
     * @param stream  the character output stream to write this part to.
     * @throws IOException  if an I/O error occurs
     * @de.renew.require (stream != null)
     */
    public void writeTo(Writer stream) throws IOException;
}