package de.renew.netdoc.model.document.parts;

import de.renew.netdoc.model.document.event.StructureChangeListener;
import de.renew.netdoc.model.document.event.TextChangeListener;
import de.renew.netdoc.model.doctarget.DocTarget;
import de.renew.netdoc.model.document.DocumentPart;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.Enumeration;


public class DocumentPartWrapper implements DocumentPart {

    /**
     * Creates a new document part wrapping the specified document part.
     * @param partToWrap the document part to be wrapped by the new part.
     * @de.renew.require (partToWrap != null)
     */
    public DocumentPartWrapper(DocumentPart partToWrap) {
        assert (partToWrap != null) : "Precondition violated: (partToWrap != null)";

        this._wrappedPart = partToWrap;
    }

    /**
     * <p>Returns a clone of this document part.</p>
     * <p>To override implement {@link #cloneImpl()}.</p>
     * @return a clone of this document part.
     * @de.renew.ensure (returnValue != null)
     * @de.renew.ensure (returnValue != this)
     * @de.renew.ensure (returnValue.getClass() == this.getClass())
     */
    @Override
    public final Object clone() {
        Object returnValue = this.cloneImpl();
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";
        assert (returnValue != this) : "Postcondition violated: (returnValue != this)";
        assert (returnValue.getClass() == this
                        .getClass()) : "Postcondition violated: (returnValue.getClass() == this.getClass())";

        return returnValue;
    }

    /**
     * <p>Returns the document part wrapped by this part.</p>
     * <p>To override implement {@link #getWrappedPartImpl()}.</p>
     * @return the document part wrapped by this part.
     * @de.renew.ensure (returnValue != null)
     */
    public final DocumentPart getWrappedPart() {
        DocumentPart returnValue = this.getWrappedPartImpl();
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * @inheritDoc
     */
    @Override
    public DocTarget getTarget() {
        return this.getWrappedPart().getTarget();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setTarget(DocTarget newTarget) {
        this.getWrappedPart().setTarget(newTarget);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void addStructureChangeListener(StructureChangeListener listenerToAdd) {
        this.getWrappedPart().addStructureChangeListener(listenerToAdd);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void removeStructureChangeListener(StructureChangeListener listenerToRemove) {
        this.getWrappedPart().removeStructureChangeListener(listenerToRemove);
    }

    /**
     * @inheritDoc
     */
    @Override
    public Enumeration<StructureChangeListener> getStructureChangeListeners() {
        return this.getWrappedPart().getStructureChangeListeners();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void addTextChangeListener(TextChangeListener listenerToAdd) {
        this.getWrappedPart().addTextChangeListener(listenerToAdd);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void removeTextChangeListener(TextChangeListener listenerToRemove) {
        this.getWrappedPart().removeTextChangeListener(listenerToRemove);
    }

    /**
     * @inheritDoc
     */
    @Override
    public Enumeration<TextChangeListener> getTextChangeListeners() {
        return this.getWrappedPart().getTextChangeListeners();
    }

    /**
     * @inheritDoc
     */
    @Override
    public String getName() {
        return this.getWrappedPart().getName();
    }

    /**
     * @inheritDoc
     */
    @Override
    public String getText() {
        return this.getWrappedPart().getText();
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean isModified() {
        return this.getWrappedPart().isModified();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setModified(boolean isModified) {
        this.getWrappedPart().setModified(isModified);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setText(String text) throws IllegalArgumentException {
        this.getWrappedPart().setText(text);
    }

    /**
     * @inheritDoc
     */
    @Override
    public DocumentPart getPart(String partName) {
        return this.getWrappedPart().getPart(partName);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void removePart(String partName) throws IllegalArgumentException {
        this.getWrappedPart().removePart(partName);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setPart(DocumentPart part) throws IllegalArgumentException {
        this.getWrappedPart().setPart(part);
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean containsPart(String partName) {
        return this.getWrappedPart().containsPart(partName);
    }

    /**
     * @inheritDoc
     */
    @Override
    public int getPartCount() {
        return this.getWrappedPart().getPartCount();
    }

    /**
     * @inheritDoc
     */
    @Override
    public Collection<DocumentPart> getParts() {
        return this.getWrappedPart().getParts();
    }

    /**
     * @inheritDoc
     */
    @Override
    public Collection<DocumentPart> getParts(String partName) {
        return this.getWrappedPart().getParts(partName);
    }

    /**
     * @inheritDoc
     */
    @Override
    public String getVersion() {
        return this.getWrappedPart().getVersion();
    }

    /**
     * @inheritDoc
     */
    @Override
    public String toString() {
        return this.getWrappedPart().toString();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void writeTo(Writer stream) throws IOException {
        this.getWrappedPart().writeTo(stream);
    }

    /**
     * <p>Returns a clone of this document part.</p>
     * <p>Implements {@link #clone()}.</p>
     * @return a clone of this document part.
     * @de.renew.ensure (returnValue != null)
     * @de.renew.ensure (returnValue != this)
     * @de.renew.ensure (returnValue.getClass() == this.getClass())
     */
    protected Object cloneImpl() {
        Constructor<?> constructor = this
                        .getConstructor(new Class[] { DocumentPart.class });
        Object[] parameters = new Object[] { this.getWrappedPart() };
        if (constructor == null) {
            throw new Error("Could not find a default constructor. "
                            + "cloneImpl() must be overridden by sub class. "
                            + "Class: " + this.getClass().getName());
        }

        DocumentPart returnValue = null;

        try {
            returnValue = (DocumentPart) constructor.newInstance(parameters);
        } catch (Exception e) {
            throw new Error("Error while creating clone using default constructor. "
                            + "cloneImpl() must be overridden by sub class. "
                            + "Class: " + this.getClass().getName(), e);
        }

        return returnValue;
    }

    /**
     * <p>Returns the document part wrapped by this part.</p>
     * <p>Implements {@link #getWrappedPart()}.</p>
     * @return the document part wrapped by this part.
     * @de.renew.ensure (returnValue != null)
     */
    protected DocumentPart getWrappedPartImpl() {
        return this._wrappedPart;
    }

    /**
     * Returns the constructor of this document part matching the specified
     * parameter types.
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
     * The document part wrapped by this part.
     */
    private DocumentPart _wrappedPart;
}