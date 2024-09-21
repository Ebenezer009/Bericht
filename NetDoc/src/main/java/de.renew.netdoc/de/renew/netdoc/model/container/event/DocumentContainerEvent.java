package de.renew.netdoc.model.container.event;

import de.renew.netdoc.model.document.Document;
import de.renew.netdoc.model.container.DocumentContainer;


/**
 * Document container event.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public class DocumentContainerEvent {

    /**
     * The container-closed event type.
     */
    public static final Object CONTAINER_CLOSED = Integer.valueOf(1);

    /**
     * The container-closing event type.
     */
    public static final Object CONTAINER_CLOSING = Integer.valueOf(2);

    /**
     * The container-opened event type.
     */
    public static final Object CONTAINER_OPENED = Integer.valueOf(3);

    /**
     * The document-closed event type.
     */
    public static final Object DOCUMENT_CLOSED = Integer.valueOf(4);

    /**
     * The document-closing event type.
     */
    public static final Object DOCUMENT_CLOSING = Integer.valueOf(5);

    /**
     * The document-opened event type.
     */
    public static final Object DOCUMENT_OPENED = Integer.valueOf(6);


    /**
     * Creates a new DocumentChangeEvent using the specified event type and
     * originator.
     * @param eventType the event type of the new event.
     * @param originator the document that caused the new event.
     * @de.renew.require (eventType != null)
     * @de.renew.require (originator != null)
     */
    public DocumentContainerEvent(Object eventType,
                                  DocumentContainer originator) {
        this(eventType, originator, null);
    }

    /**
     * Creates a new DocumentChangeEvent using the specified originator and
     * document event.
     * @param eventType the event type of the new event.
     * @param originator the document that caused the new event.
     * @param involvedDocument the document involved in the new event;<br>
     * or {@code null}, if no document was involved.
     * @de.renew.require (eventType != null)
     * @de.renew.require (originator != null)
     */
    public DocumentContainerEvent(Object eventType,
                                  DocumentContainer originator,
                                  Document involvedDocument) {
        assert (eventType != null) : "Precondition violated: (eventType != null)";
        assert (originator != null) : "Precondition violated: (originator != null)";

        this._type = eventType;
        this._originator = originator;
        this._involvedDocument = involvedDocument;
    }

    /**
     * Returns the document involved in this event.
     * @return the document involved in this event;<br>
     * or {@code null}, if there was no document involved.
     */
    public Document getInvolvedDocument() {
        return this._involvedDocument;
    }

    /**
     * <p>Returns the document container that caused this event.</p>
     * <p>To override implement {@link #getOriginatorImpl()}.</p>
     * @return the document container that caused this event.
     * @de.renew.ensure (returnValue != null)
     */
    public final DocumentContainer getOriginator() {
        DocumentContainer returnValue = this.getOriginatorImpl();
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
     * Returns the document container that caused this event.
     * @return the document container that caused this event.
     * @de.renew.ensure (returnValue != null)
     */
    protected DocumentContainer getOriginatorImpl() {
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
     * The document container that caused this event.
     */
    private DocumentContainer _originator;

    /**
     * The document involved in this event or {@code null}.
     */
    private Document _involvedDocument;
}