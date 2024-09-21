package de.renew.netdoc.model.document.parts;

import de.renew.netdoc.model.document.DocumentPart;

import java.util.List;

/**
 * Interface for the linear NetDoc document part.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public interface LinearDocumentPart extends DocumentPart {

    /**
     * Adds the specified sub part at the specified index. The sub part
     * currently at that index and all following parts will be displaced by one
     * position (insertion). If the index was equal to the part count, the part
     * will be appended.
     *
     * @param index the index where to add the part.
     * @param partToAdd the sub part to be added.
     * @throws IllegalArgumentException if this part was unmodifiable or the
     * specified part was invalid.
     * @de.renew.require (index > = 0)
     * @de.renew.require (index &lt; = this.getPartCount ())
     * @de.renew.require (partToAdd ! = null)
     */
    public void addPartAt(int index, DocumentPart partToAdd) throws IllegalArgumentException;

    /**
     * Appends the specified part to the end of the sub part list.
     *
     * @param partToAppend the sub part to be appended.
     * @throws IllegalArgumentException if the part could not be appended.
     * @de.renew.require (partToAppend ! = null)
     */
    public void appendPart(DocumentPart partToAppend) throws IllegalArgumentException;

    /**
     * Returns the sub part at the specified index.
     *
     * @param index the index of the part to be returned.
     * @return the sub part at the specified index.
     * @de.renew.require (index > = 0)
     * @de.renew.require (index &lt; this.getPartCount ())
     * @de.renew.ensure (returnValue ! = null)
     */
    public DocumentPart getPartAt(int index);

    /**
     * Removes the sub part at the specified index.
     *
     * @param index the index of the part to be removed.
     * @throws IllegalArgumentException if this part was unmodifiable.
     * @de.renew.require (index > = 0)
     * @de.renew.require (index &lt; this.getPartCount ())
     */
    public void removePartAt(int index) throws IllegalArgumentException;

    /**
     * Sets the sub part at the specified index to the specified part.
     *
     * @param index the index where to set the part.
     * @param part the new sub part.
     * @throws IllegalArgumentException if this part was unmodifiable or the
     * specified part was invalid.
     * @de.renew.require (index > = 0)
     * @de.renew.require (index &lt; this.getPartCount ())
     * @de.renew.require (part ! = null)
     */
    public void setPartAt(int index, DocumentPart part) throws IllegalArgumentException;

    /**
     * Determines the index of the direct sub part with the specified name.
     *
     * @param partName the name of the direct sub part whose index is to be
     * determined.
     * @return the index of the direct sub part;<br>
     * or <code>-1</code>, if this part did not contain a sub part with the
     * specified name.
     * @de.renew.require (partName ! = null)
     * @de.renew.ensure (returnValue > = - 1)
     */
    public int indexOfPart(String partName);

    /**
     * Returns a list of the direct sub parts of this document part.
     *
     * @return a list containing {@link
     * DocumentPart} instances.
     * @de.renew.ensure (returnValue ! = null)
     * @de.renew.ensure ! returnValue.contains(null)
     */
    public List<DocumentPart> getPartList();
}