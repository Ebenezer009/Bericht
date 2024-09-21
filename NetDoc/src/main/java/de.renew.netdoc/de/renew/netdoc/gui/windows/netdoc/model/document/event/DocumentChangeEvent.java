package de.renew.netdoc.model.document.event;

import de.renew.netdoc.model.document.Document;

/**
 * This class shows the Documents change event.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public class DocumentChangeEvent {

    /**
     * Creates a new DocumentChangeEvent using the specified originator and
     * document part structure change event.
     *
     * @param originator           the document causing the new event.
     * @param structureChangeEvent the document part structure change event
     *                             causing the new event.
     * @de.renew.require (originator != null)
     * @de.renew.require (structureChangeEvent != null)
     */
    public DocumentChangeEvent(Document originator, StructureChangeEvent structureChangeEvent) {
        assert (originator != null) : "Precondition violated: (originator != null)";
        assert (structureChangeEvent != null) : "Precondition violated: (structureChangeEvent != null)";

        this._originator = originator;
        this._structureChangeEvent = structureChangeEvent;
        this._textChangeEvent = null;
    }

    /**
     * Creates a new DocumentChangeEvent using the specified originator and
     * document part text change event.
     *
     * @param originator      the document causing the new event.
     * @param textChangeEvent the document part text change event
     *                        causing the new event.
     * @de.renew.require (originator != null)
     * @de.renew.require (textChangeEvent != null)
     */
    public DocumentChangeEvent(Document originator, TextChangeEvent textChangeEvent) {
        assert (originator != null) : "Precondition violated: (originator != null)";
        assert (textChangeEvent != null) : "Precondition violated: (textChangeEvent != null)";

        this._originator = originator;
        this._structureChangeEvent = null;
        this._textChangeEvent = textChangeEvent;
    }

    /**
     * Creates a new DocumentChangeEvent using the specified originator and
     * the specified document part structure and text change events.
     *
     * @param originator           the document causing the new event.
     * @param structureChangeEvent the document part structure change event
     *                             causing the new event.
     * @param textChangeEvent      the document part text change event
     *                             causing the new event.
     * @de.renew.require (originator != null)
     * @de.renew.require (structureChangeEvent != null)
     * @de.renew.require (textChangeEvent != null)
     */
    public DocumentChangeEvent(Document originator, StructureChangeEvent structureChangeEvent,
                               TextChangeEvent textChangeEvent) {
        assert (originator != null) : "Precondition violated: (originator != null)";
        assert (structureChangeEvent != null) : "Precondition violated: (structureChangeEvent != null)";
        assert (textChangeEvent != null) : "Precondition violated: (textChangeEvent != null)";

        this._originator = originator;
        this._structureChangeEvent = structureChangeEvent;
        this._textChangeEvent = textChangeEvent;
    }

    /**
     * Determines whether this event has been caused by a structure change.
     * This is required for receiving the corresponding structure change event
     * via the {@link #getStructureChangeEvent()} method.
     *
     * @return {@code true}, if this event has been caused by a structure
     * change;<br>
     * {@code false} otherwise.
     */
    public boolean hasStructureChanged() {
        return (this._structureChangeEvent != null);
    }

    /**
     * Determines whether this event has been caused by a text change.
     * This is required for receiving the corresponding text change event
     * via the {@link #getTextChangeEvent()} method.
     *
     * @return {@code true}, if this event has been caused by a text
     * change;<br>
     * {@code false} otherwise.
     */
    public boolean hasTextChanged() {
        return (this._textChangeEvent != null);
    }

    /**
     * <p>Returns the document that caused this event.</p>
     * <p>To override implement {@link #getOriginatorImpl()}.</p>
     *
     * @return the document that caused this event.
     * @de.renew.ensure (returnValue != null)
     */
    public final Document getOriginator() {
        Document returnValue = this.getOriginatorImpl();
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * <p>Returns the document part structure change event that caused this
     * event.</p>
     * <p>To override implement {@link #getStructureChangeEventImpl()}.
     * </p>
     *
     * {@link #hasStructureChanged()}
     *
     * @return the document part structure change event that caused this event.
     * @de.renew.require this.hasStructureChanged()
     * @de.renew.ensure (returnValue != null)
     *
     */
    public final StructureChangeEvent getStructureChangeEvent() {
        assert this.hasStructureChanged() : "Postcondition violated: this.hasStructureChanged()";

        StructureChangeEvent returnValue = this.getStructureChangeEventImpl();
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * <p>Returns the document part text change event that caused this event.
     * </p>
     * <p>To override implement {@link #getTextChangeEventImpl()}.</p>
     *
     * {@link #hasTextChanged()}
     *
     * @return the document part text change event that caused this event.
     * @de.renew.require this.hasTextChanged()
     * @de.renew.ensure (returnValue != null)
     *
     */
    public final TextChangeEvent getTextChangeEvent() {
        assert this.hasTextChanged() : "Postcondition violated: this.hasTextChanged()";

        TextChangeEvent returnValue = this.getTextChangeEventImpl();
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * Returns the document that caused this event.
     *
     * @return the document that caused this event.
     * @de.renew.ensure (returnValue != null)
     */
    protected Document getOriginatorImpl() {
        return this._originator;
    }

    /**
     * Returns the document part structure change event that caused this event.
     *
     * @return the document part structure change event that caused this event.
     * @de.renew.require this.hasStructureChanged()
     * @de.renew.ensure (returnValue != null)
     */
    protected StructureChangeEvent getStructureChangeEventImpl() {
        return this._structureChangeEvent;
    }

    /**
     * Returns the document part text change event that caused this event.
     *
     * @return the document part text change event that caused this event.
     * @de.renew.require this.hasTextChanged()
     * @de.renew.ensure (returnValue != null)
     */
    protected TextChangeEvent getTextChangeEventImpl() {
        return this._textChangeEvent;
    }

    /**
     * The document that caused this event.
     */
    private Document _originator;

    /**
     * The document part structure change event that caused this event.
     */
    private StructureChangeEvent _structureChangeEvent;

    /**
     * The document part text change event that caused this event.
     */
    private TextChangeEvent _textChangeEvent;
}