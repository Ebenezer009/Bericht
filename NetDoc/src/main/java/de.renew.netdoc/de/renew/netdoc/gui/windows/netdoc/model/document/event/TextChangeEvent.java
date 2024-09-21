package de.renew.netdoc.model.document.event;

import de.renew.netdoc.model.document.DocumentPart;

/**
 * This class provides the Text change event.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public class TextChangeEvent {

    /**
     * Creates a new TextChangeEvent using the specified originator and the
     * specified old and new text values.
     *
     * @param originator the document part that caused the new event.
     * @param oldText the old text.
     * @param newText the new text.
     * @de.renew.require (originator != null)
     * @de.renew.require (oldText != null)
     * @de.renew.require (newText != null)
     */
    public TextChangeEvent(DocumentPart originator, String oldText, String newText) {
        assert (originator != null) : "Precondition violated: (originator != null)";
        assert (oldText != null) : "Precondition violated: (oldText != null)";
        assert (newText != null) : "Precondition violated: (newText != null)";

        this._originator = originator;
        this._oldText = oldText;
        this._newText = newText;
    }

    /**
     * <p>Returns the document part that caused this event.</p>
     * <p>To override implement {@link #getOriginatorImpl()}.</p>
     *
     * @return the document part that caused the new event.
     * @de.renew.ensure (returnValue != null)
     */
    public final DocumentPart getOriginator() {
        DocumentPart returnValue = this.getOriginatorImpl();
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * <p>Returns the former text of the document part that caused this event.
     * </p>
     * <p>To override implement {@link #getOldTextImpl()}.</p>
     *
     * @return the former text of the document part that caused this event.
     * @de.renew.ensure (returnValue != null)
     */
    public final String getOldText() {
        String returnValue = this.getOldTextImpl();
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * <p>Returns the new text of the document part that caused this event.</p>
     * <p>To override implement {@link #getNewTextImpl()}.</p>
     *
     * @return the new text of the document part that caused this event.
     * @de.renew.ensure (returnValue != null)
     */
    public final String getNewText() {
        String returnValue = this.getNewTextImpl();
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * Returns the document part that caused this event.
     *
     * @return the document part that caused this event.
     * @de.renew.ensure (returnValue != null)
     */
    protected DocumentPart getOriginatorImpl() {
        return this._originator;
    }

    /**
     * Returns the former text of the document part that caused this event.
     *
     * @return the former text of the document part that caused this event.
     * @de.renew.ensure (returnValue != null)
     */
    protected String getOldTextImpl() {
        return this._oldText;
    }

    /**
     * Returns the new text of the document part that caused this event.
     *
     * @return the new text of the document part that caused this event.
     * @de.renew.ensure (returnValue != null)
     */
    protected String getNewTextImpl() {
        return this._newText;
    }

    /**
     * The document part that caused this event.
     */
    private DocumentPart _originator;

    /**
     * The old text value of this event.
     */
    private String _oldText;

    /**
     * The new text value of this event.
     */
    private String _newText;
}