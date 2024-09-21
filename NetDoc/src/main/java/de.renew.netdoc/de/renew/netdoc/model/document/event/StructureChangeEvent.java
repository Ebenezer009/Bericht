package de.renew.netdoc.model.document.event;

import de.renew.netdoc.model.document.DocumentPart;


/**
 * Structure change event.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public class StructureChangeEvent {

    /**
     * The part-added event type.
     */
    public static final Object PART_ADDED = Integer.valueOf(1);

    /**
     * The part-removed event type.
     */
    public static final Object PART_REMOVED = Integer.valueOf(2);


    /**
     * Creates a new StructureChangeEvent using the specified event type and
     * originator part.
     * @param eventType the event type of the new event.
     * @param originator the document part that caused the new event.
     * @de.renew.require (eventType != null)
     * @de.renew.require (originator != null)
     */
    public StructureChangeEvent(Object eventType, DocumentPart originator) {
        this(eventType, originator, null);
    }

    /**
     * Creates a new StructureChangeEvent using the specified event type,
     * originator part and involved part.
     * @param eventType the event type of the new event.
     * @param originator the document part that caused the new event.
     * @param involvedPart the document part involved in the new event (e.g.
     * has been added/removed); or {@code null}, if no part was involved or the
     * part was not known.
     * @de.renew.require (eventType != null)
     * @de.renew.require (originator != null)
     */
    public StructureChangeEvent(Object eventType, DocumentPart originator,
                                DocumentPart involvedPart) {
        this(eventType, originator, involvedPart, -1);
    }

    /**
     * Creates a new StructureChangeEvent using the specified event type,
     * originator part and change index.
     * @param eventType the event type of the new event.
     * @param originator the document part that caused the new event.
     * @param changeIndex the index within the sub part list of the originator
     * part where the change has been made that caused this event;<br>
     * or {@code -1}, if unknown.
     * @de.renew.require (eventType != null)
     * @de.renew.require (originator != null)
     * @de.renew.require (changeIndex >= -1)
     */
    public StructureChangeEvent(Object eventType, DocumentPart originator,
                                int changeIndex) {
        this(eventType, originator, null, changeIndex);
    }

    /**
     * Creates a new StructureChangeEvent using the specified event type,
     * originator part, involved part and change index.
     * @param eventType the event type of the new event.
     * @param originator the document part that caused the new event.
     * @param involvedPart the document part involved in the new event (e.g.
     * has been added/removed); or {@code null}, if no part was involved or the
     * part was not known.
     * @param changeIndex the index within the sub part list of the originator
     * part where the change has been made that caused this event;<br>
     * or {@code -1}, if unknown.
     * @de.renew.require (eventType != null)
     * @de.renew.require (originator != null)
     * @de.renew.require (changeIndex >= -1)
     */
    public StructureChangeEvent(Object eventType, DocumentPart originator,
                                DocumentPart involvedPart, int changeIndex) {
        assert (eventType != null) : "Precondition violated: (eventType != null)";
        assert (originator != null) : "Precondition violated: (originator != null)";
        assert (changeIndex >= -1) : "Precondition violated: (changeIndex >= -1)";

        this._type = eventType;
        this._originator = originator;
        this._involvedPart = involvedPart;
        this._changeIndex = changeIndex;
    }

    /**
     * <p>Returns the index within the sub part list of the originator part
     * where the change has been made that caused this event.</p>
     * <p>To override implement {@link #getChangeIndexImpl()}.</p>
     * @return the index where the change has been;<br>
     * or {@code -1}, if unknown.
     * @de.renew.ensure (returnValue >= -1)
     */
    public final int getChangeIndex() {
        int returnValue = this.getChangeIndexImpl();
        assert (returnValue >= -1) : "Postcondition violated: (returnValue >= -1)";

        return returnValue;
    }

    /**
     * Returns the document part involved in the this event (e.g. that has been
     * added/removed).
     * @return the document part involved in the this event);<br>
     * or {@code null}, if no part was involved or the part was not known.
     */
    public DocumentPart getInvolvedPart() {
        return this._involvedPart;
    }

    /**
     * <p>Returns the document part that caused this event.</p>
     * <p>To override implement {@link #getOriginatorImpl()}.</p>
     * @return the document part that caused this event.
     * @de.renew.ensure (returnValue != null)
     */
    public final DocumentPart getOriginator() {
        DocumentPart returnValue = this.getOriginatorImpl();
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * <p>Returns the type of this event.</p>
     * <p>To override implement {@link #getTypeImpl()}.</p>
     * @return the type of this event.
     * @de.renew.ensure (returnValue != null)
     */
    public final Object getType() {
        Object returnValue = this.getTypeImpl();
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * Returns the index within the sub part list of the originator part where
     * the change has been made that caused this event.
     * @return the index where the change has been;<br>
     * or {@code -1}, if unknown.
     * @de.renew.ensure (returnValue >= -1)
     */
    protected int getChangeIndexImpl() {
        return this._changeIndex;
    }

    /**
     * Returns the document part that caused this event.
     * @return the document part that caused this event.
     * @de.renew.ensure (returnValue != null)
     */
    protected DocumentPart getOriginatorImpl() {
        return this._originator;
    }

    /**
     * Returns the type of this event.
     * @return the type of this event.
     * @de.renew.ensure (returnValue != null)
     */
    protected Object getTypeImpl() {
        return this._type;
    }

    /**
     * The type of this event.
     */
    private Object _type;

    /**
     * The document part that caused this event.
     */
    private DocumentPart _originator;

    /**
     * The document part involved this event.
     */
    private DocumentPart _involvedPart;

    /**
     * The index within the list of sub parts of the originator part where the
     * change has been made that caused this event; or {@code -1}, if unknown.
     */
    private int _changeIndex;
}