package de.renew.netdoc.model.container.event;

/**
 * This class documents event changes, especially the closing request.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public class CloseRequestEvent {

    /**
     * Creates a new CloseRequestEvent using the specified originator.
     *
     * @param originator the object requesting a close operation.
     * @de.renew.require (originator != null)
     */
    public CloseRequestEvent(Object originator) {
        assert (originator != null) : "Precondition violated: (originator != null)";

        this._originator = originator;
    }

    /**
     * <p>Returns the originator of this event.</p>
     * <p>To override implement {@link #getOriginatorImpl()}.</p>
     *
     * @return the originator of this event.
     * @de.renew.ensure (returnValue != null)
     */
    public Object getOriginator() {
        Object returnValue = this.getOriginatorImpl();
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * Returns the originator of this event.
     *
     * @return the originator of this event.
     * @de.renew.ensure (returnValue != null)
     */
    protected Object getOriginatorImpl() {
        return this._originator;
    }

    /**
     * The object requesting a close operation.
     */
    private Object _originator;
}