package de.renew.netdoc.model.document.parts.linear;

import de.renew.netdoc.model.doctarget.DocTarget;
import de.renew.netdoc.model.document.DocumentPart;
import de.renew.netdoc.model.document.parts.AbstractDocumentPart;
import de.renew.netdoc.model.document.parts.LinearDocumentPart;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


/**
 * Abstract Linear NetDoc document part.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public abstract class AbstractLinearDocumentPart extends AbstractDocumentPart
                implements LinearDocumentPart {

    /**
     * Creates a new document part initially not assigned to a target.
     */
    protected AbstractLinearDocumentPart() {
        this(null);
    }

    /**
     * Creates a new document part assigned to the specified target.
     * @param target the documentation target to assign to the new part.
     */
    protected AbstractLinearDocumentPart(DocTarget target) {
        super(target);
    }

    /**
     * <p>Adds the specified sub part at the specified index. The sub part
     * currently at that index and all following parts will be displaced by one
     * position (insertion). If the index was equal to the part count, the part
     * will be appended.</p>
     * <p>To override implement {@link
     * #addPartAtImpl(int,DocumentPart)}.</p>
     * @param index  the index where to add the part.
     * @param partToAdd  the sub part to be added.
     * @throws IllegalArgumentException  if this part was unmodifiable or the
     * specified part was invalid.
     * @de.renew.require (index >= 0)
     * @de.renew.require (index <= this.getPartCount())
     * @de.renew.require (partToAdd != null)
     */
    @Override
    public final void addPartAt(int index, DocumentPart partToAdd)
                    throws IllegalArgumentException {
        assert (index >= 0) : "Precondition violated: (index >= 0)";
        assert (index <= this
                        .getPartCount()) : "Precondition violated: (index <= this.getPartCount())";
        assert (partToAdd != null) : "Precondition violated: (partToAdd != null)";

        this.addPartAtImpl(index, partToAdd);
    }

    /**
     * <p>Appends the specified part to the end of the sub part list.</p>
     * <p>To override implement {@link #appendPartImpl(DocumentPart)}.
     * </p>
     * @param partToAppend  the sub part to be appended.
     * @throws IllegalArgumentException  if the part could not be appended.
     * @de.renew.require (partToAppend != null)
     */
    @Override
    public void appendPart(DocumentPart partToAppend)
                    throws IllegalArgumentException {
        assert (partToAppend != null) : "Precondition violated: (partToAppend != null)";

        this.appendPartImpl(partToAppend);
    }

    /**
     * <p>Returns the sub part at the specified index.</p>
     * <p>To override implement {@link #getPartAtImpl(int)}.</p>
     * @param index  the index of the part to be returned.
     * @return the sub part at the specified index.
     * @de.renew.require (index >= 0)
     * @de.renew.require (index < this.getPartCount())
     * @de.renew.ensure (returnValue != null)
     */
    @Override
    public final DocumentPart getPartAt(int index) {
        assert (index >= 0) : "Precondition violated: (index >= 0)";
        assert (index < this
                        .getPartCount()) : "Precondition violated: (index < this.getPartCount())";

        DocumentPart returnValue = this.getPartAtImpl(index);
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * <p>Removes the sub part at the specified index.</p>
     * <p>To override implement {@link #removePartAtImpl(int)}.</p>
     * @param index  the index of the part to be removed.
     * @throws IllegalArgumentException  if this part was unmodifiable.
     * @de.renew.require (index >= 0)
     * @de.renew.require (index < this.getPartCount())
     */
    @Override
    public final void removePartAt(int index) throws IllegalArgumentException {
        assert (index >= 0) : "Precondition violated: (index >= 0)";
        assert (index < this
                        .getPartCount()) : "Precondition violated: (index < this.getPartCount())";

        this.removePartAtImpl(index);
    }

    /**
     * <p>Sets the sub part at the specified index to the specified part.</p>
     * <p>To override implement {@link
     * #setPartAtImpl(int,DocumentPart)}.</p>
     * @param index  the index where to set the part.
     * @param part  the new sub part.
     * @throws IllegalArgumentException  if this part was unmodifiable or the
     * specified part was invalid.
     * @de.renew.require (index >= 0)
     * @de.renew.require (index < this.getPartCount())
     * @de.renew.require (part != null)
     */
    @Override
    public final void setPartAt(int index, DocumentPart part)
                    throws IllegalArgumentException {
        assert (index >= 0) : "Precondition violated: (index >= 0)";
        assert (index < this
                        .getPartCount()) : "Precondition violated: (index < this.getPartCount())";
        assert (part != null) : "Precondition violated: (part != null)";

        this.setPartAtImpl(index, part);
    }

    /**
     * <p>Determines the index of the direct sub part with the specified name.
     * </p>
     * <p>To override implement {@link #indexOfPartImpl(String)}.</p>
     * @param partName  the name of the direct sub part whose index is to be
     * determined.
     * @return the index of the direct sub part;<br>
     * or <code>-1</code>, if this part did not contain a sub part with the
     * specified name.
     * @de.renew.require (partName != null)
     * @de.renew.ensure (returnValue >= -1)
     */
    @Override
    public final int indexOfPart(String partName) {
        assert (partName != null) : "Precondition violated: (partName != null)";

        int returnValue = this.indexOfPartImpl(partName);
        assert (returnValue >= -1) : "Postcondition violated: (returnValue >= -1)";

        return returnValue;
    }

    /**
     * <p>Returns a list of the direct sub parts of this document part.</p>
     * <p>To override implement {@link #getPartListImpl()}.</p>
     * @return a list containing {@link
     * DocumentPart} instances.
     * @de.renew.ensure (returnValue != null)
     * @de.renew.ensure ! returnValue.contains(null)
     */
    @Override
    public final List<DocumentPart> getPartList() {
        List<DocumentPart> returnValue = this.getPartListImpl();
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";
        assert !returnValue.contains(
                        null) : "Postcondition violated: ! returnValue.contains(null)";

        return returnValue;
    }


    /**
     * <p>Returns the direct sub parts of this document part.</p>
     * <p>This method is finalised to ensure that its result is identical to
     * the result of {@link #getPartList()}.</p>
     * @return a list containing {@link
     * DocumentPart} instances.
     * @de.renew.ensure (returnValue != null)
     * @de.renew.ensure ! returnValue.contains(null)
     */
    @Override
    protected final Collection<DocumentPart> getPartsImpl() {
        return this.getPartList();
    }


    /**
     * @inheritDoc
     */
    @Override
    protected Object cloneImpl() {
        LinearDocumentPart returnValue = (LinearDocumentPart) this.cloneBase();

        Iterator<DocumentPart> parts = this.getPartList().iterator();
        while (parts.hasNext()) {
            returnValue.appendPart((DocumentPart) (parts.next()).clone());
        }

        return returnValue;
    }


    /**
     * @inheritDoc
     */
    @Override
    protected String toStringImpl() {
        StringBuffer buffer = new StringBuffer();
        Iterator<DocumentPart> partIterator = this.getPartList().iterator();
        while (partIterator.hasNext()) {
            buffer.append(partIterator.next().toString());
        }
        buffer.append(this.getText());
        return buffer.toString();
    }


    /**
     * Appends the specified part to the end of the sub part list.
     * @param partToAppend  the sub part to be appended.
     * @throws IllegalArgumentException  if the part could not be appended.
     * @de.renew.require (partToAppend != null)
     */
    protected void appendPartImpl(DocumentPart partToAppend)
                    throws IllegalArgumentException {
        this.addPartAt(this.getPartCount(), partToAppend);
    }

    /**
     * Adds the specified sub part at the specified index. The sub part
     * currently at that index and all following parts will be displaced by one
     * position (insertion). If the index was equal to the part count, the part
     * will be appended.
     * @param index  the index where to add the part.
     * @param partToAdd  the sub part to be added.
     * @throws IllegalArgumentException  if this part was unmodifiable or the
     * specified part was invalid.
     * @de.renew.require (index >= 0)
     * @de.renew.require (index <= this.getPartCount())
     * @de.renew.require (partToAdd != null)
     */
    protected void addPartAtImpl(int index, DocumentPart partToAdd)
                    throws IllegalArgumentException {
        throw new IllegalArgumentException("Unmodifiable");
    }

    /**
     * Returns the sub part at the specified index.
     * @param index  the index of the part to be returned.
     * @return the sub part at the specified index.
     * @de.renew.require (index >= 0)
     * @de.renew.require (index < this.getPartCount())
     * @de.renew.ensure (returnValue != null)
     */
    protected DocumentPart getPartAtImpl(int index) {
        return this.getPartList().get(index);
    }

    /**
     * Removes the sub part at the specified index.
     * @param index  the index of the part to be removed.
     * @throws IllegalArgumentException  if this part was unmodifiable.
     * @de.renew.require (index >= 0)
     * @de.renew.require (index < this.getPartCount())
     */
    protected void removePartAtImpl(int index) throws IllegalArgumentException {
        throw new IllegalArgumentException("Unmodifiable");
    }

    /**
     * Sets the sub part at the specified index to the specified part.
     * @param index  the index where to set the part.
     * @param part  the new sub part.
     * @throws IllegalArgumentException  if this part was unmodifiable or the
     * specified part was invalid.
     * @de.renew.require (index >= 0)
     * @de.renew.require (index < this.getPartCount())
     * @de.renew.require (part != null)
     */
    protected void setPartAtImpl(int index, DocumentPart part)
                    throws IllegalArgumentException {
        throw new IllegalArgumentException("Unmodifiable");
    }

    /**
     * Determines the index of the direct sub part with the specified name.
     * @param partName  the name of the direct sub part whose index is to be
     * determined.
     * @return the index of the direct sub part;<br>
     * or <code>-1</code>, if this part did not contain a sub part with the
     * specified name.
     * @de.renew.require (partName != null)
     * @de.renew.ensure (returnValue >= -1)
     */
    protected int indexOfPartImpl(String partName) {
        ListIterator<DocumentPart> partIterator = this.getPartList()
                        .listIterator();
        while (partIterator.hasNext()) {
            if ((partIterator.next()).getName().equals(partName)) {
                return partIterator.previousIndex();
            }
        }
        return -1;
    }

    /**
     * Returns a list of the names of the direct sub parts of this document
     * part.
     * @return a list containing {@link java.lang.String} instances.
     * @de.renew.ensure (returnValue != null)
     * @de.renew.ensure ! returnValue.contains(null)
     */
    protected List<String> getPartNameListImpl() {
        List<String> names = new ArrayList<String>();
        Iterator<DocumentPart> partIterator = this.getPartList().iterator();
        while (partIterator.hasNext()) {
            names.add((partIterator.next()).getName());
        }
        return names;
    }

    /**
     * Returns a list of the direct sub parts of this document part.
     * @return a list containing {@link
     * DocumentPart} instances.
     * @de.renew.ensure (returnValue != null)
     * @de.renew.ensure ! returnValue.contains(null)
     */
    protected List<DocumentPart> getPartListImpl() {
        return Collections.emptyList();
    }

    /**
     * Returns the first occurrence of a direct sub part of this part matching
     * the specified name.
     * @param partName the name of the part to return.
     * @return the first occurrence of a direct sub part of this part matching
     * the specified name;<br>
     * or {@code null}, if this part did not contain a direct sub part matching
     * that name.
     * @de.renew.require (partName != null)
     */
    @Override
    protected DocumentPart getLocalPart(String partName) {
        int index = this.indexOfPart(partName);
        return (index >= 0) ? this.getPartAt(index) : null;
    }

    /**
     * Removes the first occurrence of a direct sub part of this part matching
     * the specified name.
     * @param partName the name of the part to remove.
     * @throws IllegalArgumentException if this part was unmodifiable.
     * @de.renew.require (partName != null)
     */
    @Override
    protected void removeLocalPart(String partName) {
        int index = this.indexOfPart(partName);
        if (index >= 0) {
            this.removePartAt(index);
        }
    }

    /**
     * Sets the first occurrence of a direct sub part of this part matching
     * the name of the specified part. If this part did not contain a sub part
     * with that name, the specified part will be added.
     * @param partToSet the document part to set/add.
     * @throws IllegalArgumentException if this part was unmodifiable or the
     * specified part was invalid.
     * @de.renew.require (partToSet != null)
     */
    @Override
    protected void setLocalPart(DocumentPart partToSet) {
        int index = this.indexOfPart(partToSet.getName());
        if (index >= 0) {
            this.setPartAt(index, partToSet);
        } else {
            this.appendPart(partToSet);
        }
    }
}