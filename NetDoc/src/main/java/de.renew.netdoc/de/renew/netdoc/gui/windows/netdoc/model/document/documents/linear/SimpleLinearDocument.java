package de.renew.netdoc.model.document.documents.linear;

import de.renew.netdoc.model.doctarget.DocTarget;
import de.renew.netdoc.model.document.DocumentPart;
import de.renew.netdoc.model.document.DocumentParts;
import de.renew.netdoc.model.document.documents.LinearDocument;
import de.renew.netdoc.model.document.event.DocumentChangeEvent;
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
 * Standard implementation of a Linear NetDoc document.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public class SimpleLinearDocument extends LinearDocument {

    /**
     * Creates a new SimpleLinearDocument initially not assigned to a target.
     */
    public SimpleLinearDocument() {
        this(null);
    }

    /**
     * Creates a new SimpleLinearDocument assigned to the specified target.
     *
     * @param target the documentation target to assign to the new document.
     */
    public SimpleLinearDocument(DocTarget target) {
        this(target, DocumentParts.NETDOC_TEX_UNIT_VERSION_PREFIX + DocumentParts.NETDOC_TEX_UNIT_VERSION_NUMBER);

        this._parts = new ArrayList<DocumentPart>();
    }

    /**
     * Creates a new SimpleLinearDocument assigned to the specified target
     * using the specified version string.
     *
     * @param target the documentation target to assign to the new document.
     * @param version the version string of the new document.
     * @de.renew.require (version != null)
     */
    public SimpleLinearDocument(DocTarget target, String version) {
        super(target);
        assert (version != null) : "Precondition violated: (version != null)";

        this._parts = new ArrayList<DocumentPart>();
        this._version = version;
    }

    /**
     * <p>Sets the version string of this document to the specified string.</p>
     * <p>To override implement {@link #setVersionImpl(String)}.</p>
     *
     * @param version the new version string to be used.
     * @de.renew.require (version != null)
     */
    public final void setVersion(String version) {
        assert (version != null) : "Precondition violated: (version != null)";

        this.setVersionImpl(version);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected int getPartCountImpl() {
        return this.getInternalPartList().size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void addPartAtImpl(int index, DocumentPart partToAdd) throws IllegalArgumentException {
        this.addSubPartStructureChangeListener(partToAdd);
        this.addSubPartTextChangeListener(partToAdd);
        this.getInternalPartList().add(index, partToAdd);
        this.setModified(true);
        StructureChangeEvent event = new StructureChangeEvent(StructureChangeEvent.PART_ADDED, this, partToAdd, index);
        this.fireStructureChangeEvent(event);
        this.fireDocumentChangeEvent(new DocumentChangeEvent(this, event));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected DocumentPart getPartAtImpl(int index) {
        return this.getInternalPartList().get(index);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void removePartAtImpl(int index) throws IllegalArgumentException {
        this.removeSubPartStructureChangeListener(this.getPartAt(index));
        this.removeSubPartTextChangeListener(this.getPartAt(index));
        this.getInternalPartList().remove(index);
        this.setModified(true);
        StructureChangeEvent event =
                new StructureChangeEvent(StructureChangeEvent.PART_REMOVED, this, this.getPartAt(index), index);
        this.fireStructureChangeEvent(event);
        this.fireDocumentChangeEvent(new DocumentChangeEvent(this, event));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected int indexOfPartImpl(String partName) {
        int index = 0;
        Iterator<DocumentPart> partIterator = this.getInternalPartList().iterator();
        while (partIterator.hasNext()) {
            if ((partIterator.next()).getName().equals(partName)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void setPartAtImpl(int index, DocumentPart part) throws IllegalArgumentException {
        this.removeSubPartStructureChangeListener(this.getPartAt(index));
        this.removeSubPartTextChangeListener(this.getPartAt(index));
        this.addSubPartStructureChangeListener(part);
        this.addSubPartTextChangeListener(part);
        this.getInternalPartList().set(index, part);
        this.setModified(true);

        StructureChangeEvent addedEvent =
                new StructureChangeEvent(StructureChangeEvent.PART_REMOVED, this, this.getPartAt(index), index);
        this.fireStructureChangeEvent(addedEvent);
        this.fireDocumentChangeEvent(new DocumentChangeEvent(this, addedEvent));

        StructureChangeEvent removedEvent =
                new StructureChangeEvent(StructureChangeEvent.PART_ADDED, this, part, index);
        this.fireStructureChangeEvent(removedEvent);
        this.fireDocumentChangeEvent(new DocumentChangeEvent(this, removedEvent));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected List<DocumentPart> getPartListImpl() {
        return Collections.unmodifiableList(this.getInternalPartList());
    }

    /**
     * Returns the internal list of direct sub parts of this document.
     *
     * @return a list containing {@link DocumentPart} instances.
     * @de.renew.ensure (returnValue != null)
     * @de.renew.ensure ! returnValue.contains(null)
     */
    protected List<DocumentPart> getInternalPartList() {
        return this._parts;
    }

    /**
     * Returns the sub part structure change listener used by this document.
     *
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
     *
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
     * Adds the sub part structure change listener used by this document to the
     * specified document part. Verifies whether the listener is already
     * contained. If so, the listener will not be added again.
     *
     * @param partToObserve the part to be observed by this document.
     * @de.renew.require (partToObserve != null)
     */
    protected void addSubPartStructureChangeListener(DocumentPart partToObserve) {
        Enumeration<StructureChangeListener> listenerEnum = partToObserve.getStructureChangeListeners();
        while (listenerEnum.hasMoreElements()) {
            if (listenerEnum.nextElement() == this.getSubPartStructureChangeListener()) {
                return;
            }
        }
        partToObserve.addStructureChangeListener(this.getSubPartStructureChangeListener());
    }

    /**
     * Removes the sub part structure change listener used by this document
     * from the specified document part.
     *
     * @param partToModify the part where to remove the listener.
     * @de.renew.require (partToModify != null)
     */
    protected void removeSubPartStructureChangeListener(DocumentPart partToModify) {
        partToModify.removeStructureChangeListener(this.getSubPartStructureChangeListener());
    }

    /**
     * Adds the sub part text change listener used by this document to the
     * specified document part. Verifies whether the listener is already
     * contained. If so, the listener will not be added again.
     *
     * @param partToObserve the part to be observed by this document.
     * @de.renew.require (partToObserve != null)
     */
    protected void addSubPartTextChangeListener(DocumentPart partToObserve) {
        Enumeration<TextChangeListener> listenerEnum = partToObserve.getTextChangeListeners();
        while (listenerEnum.hasMoreElements()) {
            if (listenerEnum.nextElement() == this.getSubPartTextChangeListener()) {
                return;
            }
        }
        partToObserve.addTextChangeListener(this.getSubPartTextChangeListener());
    }

    /**
     * Removes the sub part text change listener used by this document from the
     * specified document part.
     *
     * @param partToModify the part where to remove the listener.
     * @de.renew.require (partToModify != null)
     */
    protected void removeSubPartTextChangeListener(DocumentPart partToModify) {
        partToModify.removeTextChangeListener(this.getSubPartTextChangeListener());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getVersionImpl() {
        return this._version;
    }

    /**
     * <p>Sets the version string of this document to the specified string.</p>
     * <p>Implements {@link #setVersion(String)}.</p>
     *
     * @param version the new version string to be used.
     * @de.renew.require (version != null)
     */
    protected void setVersionImpl(String version) {
        this._version = version;
    }

    /**
     * Reads an object and adds listeners to the object.
     *
     * @param stream Parameter for object
     * @throws IOException Signals that an I/O exception to some sort has occurred.
     * @throws ClassNotFoundException if class is not found an exception is thrown
     *
     */
    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        Iterator<DocumentPart> partIterator = this.getPartList().iterator();
        while (partIterator.hasNext()) {
            this.addSubPartStructureChangeListener(partIterator.next());
            this.addSubPartTextChangeListener(partIterator.next());
        }
    }

    /**
     * Writes an object and throws an exception if needed.
     *
     * @param stream Parameter for object
     * @throws IOException Signals that an I/O exception to some sort has occurred.
     *
     */
    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
    }

    /**
     * The sub parts of this document.
     */
    private List<DocumentPart> _parts;

    /**
     * The version string associated with this document.
     */
    private String _version;

    /**
     * The sub part structure change listener used by this document.
     */
    private transient SubPartStructureChangeListener _subPartStructureChangeListener;

    /**
     * The sub part text change listener used by this document.
     */
    private transient SubPartTextChangeListener _subPartTextChangeListener;

    /**
     * Observer listening to structure change events of the direct sub parts of
     * this document.
     */
    protected class SubPartStructureChangeListener extends StructureChangeAdapter {

        /**
         * Creates a new SubPartStructureChangeListener.
         */
        public SubPartStructureChangeListener() {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected void partAddedImpl(StructureChangeEvent event) {
            if (event.getOriginator().isModified()) {
                SimpleLinearDocument.this.setModified(true);
            }
            SimpleLinearDocument.this.fireStructureChangeEvent(event);
            SimpleLinearDocument.this.fireDocumentChangeEvent(
                    new DocumentChangeEvent(SimpleLinearDocument.this, event));
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected void partRemovedImpl(StructureChangeEvent event) {
            if (event.getOriginator().isModified()) {
                SimpleLinearDocument.this.setModified(true);
            }
            SimpleLinearDocument.this.fireStructureChangeEvent(event);
            SimpleLinearDocument.this.fireDocumentChangeEvent(
                    new DocumentChangeEvent(SimpleLinearDocument.this, event));
        }
    }

    /**
     * Observer listening to text change events of the direct sub parts of
     * this document.
     */
    protected class SubPartTextChangeListener extends TextChangeAdapter {

        /**
         * Creates a new SubPartTextChangeListener.
         */
        public SubPartTextChangeListener() {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected void textChangedImpl(TextChangeEvent event) {
            if (event.getOriginator().isModified()) {
                SimpleLinearDocument.this.setModified(true);
            }
            SimpleLinearDocument.this.fireTextChangeEvent(event);
            SimpleLinearDocument.this.fireDocumentChangeEvent(
                    new DocumentChangeEvent(SimpleLinearDocument.this, event));
        }
    }

    /**
     * Serial Version UID of this class.
     */
    private static final long serialVersionUID = 1L;
}