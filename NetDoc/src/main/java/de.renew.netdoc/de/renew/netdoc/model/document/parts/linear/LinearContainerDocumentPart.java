package de.renew.netdoc.model.document.parts.linear;

import de.renew.netdoc.model.document.DocumentPart;
import de.renew.netdoc.model.document.event.StructureChangeAdapter;
import de.renew.netdoc.model.document.event.StructureChangeEvent;
import de.renew.netdoc.model.document.event.StructureChangeListener;
import de.renew.netdoc.model.document.event.TextChangeAdapter;
import de.renew.netdoc.model.document.event.TextChangeEvent;
import de.renew.netdoc.model.document.event.TextChangeListener;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;


/**
 * Linear NetDoc document part containing other document parts.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public class LinearContainerDocumentPart extends AbstractLinearDocumentPart {

    /**
     * Creates a new LinearContainerDocumentPart with the specified name and an
     * empty text.
     * @param name  the name of the new part.
     * @de.renew.require (name != null)
     */
    public LinearContainerDocumentPart(String name) {
        this(name, "");
    }

    /**
     * Creates a new LinearContainerDocumentPart with the specified name and
     * text.
     * @param name  the name of the new part.
     * @param text  the text of the new part.
     * @de.renew.require (name != null)
     * @de.renew.require (text != null)
     */
    public LinearContainerDocumentPart(String name, String text) {
        assert (name != null) : "Precondition violated: (name != null)";
        assert (text != null) : "Precondition violated: (text != null)";

        this._name = name;
        this._text = text;
        this._parts = new ArrayList<DocumentPart>();
        this._subPartStructureChangeListener = null;
        this._subPartTextChangeListener = null;
    }


    /**
     * @inheritDoc
     */
    @Override
    protected String getNameImpl() {
        return this._name;
    }

    /**
     * @inheritDoc
     */
    @Override
    protected String getTextImpl() {
        return this._text;
    }

    /**
     * @inheritDoc
     */
    @Override
    protected void setTextImpl(String text) throws IllegalArgumentException {
        String oldText = this.getText();
        this._text = text;
        if (!oldText.equals(text)) {
            this.setModified(true);
            this.fireTextChangeEvent(new TextChangeEvent(this, oldText, text));
        }
    }


    /**
     * @inheritDoc
     */
    @Override
    protected int getPartCountImpl() {
        return this.getInternalPartList().size();
    }


    /**
     * @inheritDoc
     */
    @Override
    protected void addPartAtImpl(int index, DocumentPart partToAdd)
                    throws IllegalArgumentException {
        this.addSubPartStructureChangeListener(partToAdd);
        this.addSubPartTextChangeListener(partToAdd);
        this.getInternalPartList().add(index, partToAdd);
        this.setModified(true);
        this.fireStructureChangeEvent(new StructureChangeEvent(
                        StructureChangeEvent.PART_ADDED, this, partToAdd,
                        index));
    }

    /**
     * @inheritDoc
     */
    @Override
    protected DocumentPart getPartAtImpl(int index) {
        return this.getInternalPartList().get(index);
    }

    /**
     * @inheritDoc
     */
    @Override
    protected void removePartAtImpl(int index) throws IllegalArgumentException {
        DocumentPart part = this.getPartAt(index);
        this.removeSubPartStructureChangeListener(part);
        this.removeSubPartTextChangeListener(part);
        this.getInternalPartList().remove(index);
        this.setModified(true);
        this.fireStructureChangeEvent(new StructureChangeEvent(
                        StructureChangeEvent.PART_REMOVED, this,
                part, index));
    }

    /**
     * @inheritDoc
     */
    @Override
    protected int indexOfPartImpl(String partName) {
        int index = 0;
        Iterator<DocumentPart> partIterator = this.getInternalPartList()
                        .iterator();
        while (partIterator.hasNext()) {
            if ((partIterator.next()).getName().equals(partName)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    /**
     * @inheritDoc
     */
    @Override
    protected void setPartAtImpl(int index, DocumentPart part)
                    throws IllegalArgumentException {
        this.removeSubPartStructureChangeListener(this.getPartAt(index));
        this.removeSubPartTextChangeListener(this.getPartAt(index));
        this.addSubPartStructureChangeListener(part);
        this.addSubPartTextChangeListener(part);
        this.getInternalPartList().set(index, part);
        this.setModified(true);
        this.fireStructureChangeEvent(new StructureChangeEvent(
                        StructureChangeEvent.PART_REMOVED, this,
                        this.getPartAt(index), index));
        this.fireStructureChangeEvent(new StructureChangeEvent(
                        StructureChangeEvent.PART_ADDED, this, part, index));
    }

    /**
     * @inheritDoc
     */
    @Override
    protected List<DocumentPart> getPartListImpl() {
        return Collections.unmodifiableList(this.getInternalPartList());
    }


    /**
     * Returns the internal list of direct sub parts of this document part.
     * @return a list containing {@link
     * DocumentPart} instances.
     * @de.renew.ensure (returnValue != null)
     * @de.renew.ensure ! returnValue.contains(null)
     */
    protected List<DocumentPart> getInternalPartList() {
        return this._parts;
    }

    /**
     * Returns the sub part structure change listener used by this document.
     * @return the sub part structure change listener used by this document.
     * @de.renew.ensure (returnValue != null)
     */
    protected SubPartStructureChangeListener getSubPartStructureChangeListener() {
        if (this._subPartStructureChangeListener == null) {
            this._subPartStructureChangeListener = this.new SubPartStructureChangeListener();
        }
        return this._subPartStructureChangeListener;
    }

    /**
     * Returns the sub part text change listener used by this document.
     * @return the sub part text change listener used by this document.
     * @de.renew.ensure (returnValue != null)
     */
    protected SubPartTextChangeListener getSubPartTextChangeListener() {
        if (this._subPartTextChangeListener == null) {
            this._subPartTextChangeListener = this.new SubPartTextChangeListener();
        }
        return this._subPartTextChangeListener;
    }

    /**
     * Adds the sub part structure change listener used by this part to the
     * specified document part. Verifies whether the listener is already
     * contained. If so, the listener will not be added again.
     * @param partToObserve the part to be observed by this document.
     * @de.renew.require (partToObserve != null)
     */
    protected void addSubPartStructureChangeListener(DocumentPart partToObserve) {
        Enumeration<StructureChangeListener> listenerEnum = partToObserve
                        .getStructureChangeListeners();
        while (listenerEnum.hasMoreElements()) {
            if (listenerEnum.nextElement() == this
                            .getSubPartStructureChangeListener()) {
                return;
            }
        }
        partToObserve.addStructureChangeListener(
                        this.getSubPartStructureChangeListener());
    }

    /**
     * Removes the sub part structure change listener used by this part from
     * the specified document part.
     * @param partToModify the part where to remove the listener.
     * @de.renew.require (partToModify != null)
     */
    protected void removeSubPartStructureChangeListener(DocumentPart partToModify) {
        partToModify.removeStructureChangeListener(
                        this.getSubPartStructureChangeListener());
    }

    /**
     * Adds the sub part text change listener used by this part to the
     * specified document part. Verifies whether the listener is already
     * contained. If so, the listener will not be added again.
     * @param partToObserve the part to be observed by this document.
     * @de.renew.require (partToObserve != null)
     */
    protected void addSubPartTextChangeListener(DocumentPart partToObserve) {
        Enumeration<TextChangeListener> listenerEnum = partToObserve
                        .getTextChangeListeners();
        while (listenerEnum.hasMoreElements()) {
            if (listenerEnum.nextElement() == this
                            .getSubPartTextChangeListener()) {
                return;
            }
        }
        partToObserve.addTextChangeListener(
                        this.getSubPartTextChangeListener());
    }

    /**
     * Removes the sub part text change listener used by this part from the
     * specified document part.
     * @param partToModify the part where to remove the listener.
     * @de.renew.require (partToModify != null)
     */
    protected void removeSubPartTextChangeListener(DocumentPart partToModify) {
        partToModify.removeTextChangeListener(
                        this.getSubPartTextChangeListener());
    }

    /**
     * @inheritDoc
     */
    private void readObject(ObjectInputStream stream)
                    throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        Iterator<DocumentPart> partIterator = this.getPartList().iterator();
        while (partIterator.hasNext()) {
            this.addSubPartStructureChangeListener(partIterator.next());
            this.addSubPartTextChangeListener(partIterator.next());
        }
    }

    /**
     * @inheritDoc
     */
    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
    }

    /**
     * The sub parts of this part.
     */
    private List<DocumentPart> _parts;

    /**
     * The name of this part.
     */
    private String _name;

    /**
     * The text of this part.
     */
    private String _text;

    /**
     * The sub part structure change listener used by this part.
     */
    private transient SubPartStructureChangeListener _subPartStructureChangeListener;

    /**
     * The sub part text change listener used by this part.
     */
    private transient SubPartTextChangeListener _subPartTextChangeListener;

    /**
     * Obeserver listening to structure change events of the direct sub parts of
     * this part.
     */
    protected class SubPartStructureChangeListener
                    extends StructureChangeAdapter {

        /**
         * Creates a new SubPartStructureChangeListener.
         */
        public SubPartStructureChangeListener() {
        }

        /**
         * @inheritDoc
         */
        @Override
        protected void partAddedImpl(StructureChangeEvent event) {
            if (event.getOriginator().isModified()) {
                LinearContainerDocumentPart.this.setModified(true);
            }
            LinearContainerDocumentPart.this.fireStructureChangeEvent(event);
        }

        /**
         * @inheritDoc
         */
        @Override
        protected void partRemovedImpl(StructureChangeEvent event) {
            if (event.getOriginator().isModified()) {
                LinearContainerDocumentPart.this.setModified(true);
            }
            LinearContainerDocumentPart.this.fireStructureChangeEvent(event);
        }
    }

    /**
     * Obeserver listening to text change events of the direct sub parts of
     * this part.
     */
    protected class SubPartTextChangeListener extends TextChangeAdapter {

        /**
         * Creates a new SubPartTextChangeListener.
         */
        public SubPartTextChangeListener() {
        }

        /**
         * @inheritDoc
         */
        @Override
        protected void textChangedImpl(TextChangeEvent event) {
            if (event.getOriginator().isModified()) {
                LinearContainerDocumentPart.this.setModified(true);
            }
            LinearContainerDocumentPart.this.fireTextChangeEvent(event);
        }
    }

    /**
     * Serial Version UID of this class.
     */
    private static final long serialVersionUID = 1L;
}