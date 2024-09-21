package de.renew.netdoc.model.document.event;


/**
 * Standard implementation of the TextChangeListener interface providing empty
 * method bodies for each event.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public class TextChangeAdapter implements TextChangeListener {

    /**
     * Creates a new TextChangeAdapter.
     */
    public TextChangeAdapter() {
    }

    /**
     * <p>Invoked when the text of a document part has changed.</p>
     * <p>To override implement {@link #textChangedImpl(TextChangeEvent)}.</p>
     * @param event the corresponding change event.
     * @de.renew.require (event != null)
     */
    @Override
    public final void textChanged(TextChangeEvent event) {
        assert (event != null) : "Precondition violated: (event != null)";

        this.textChangedImpl(event);
    }

    /**
     * Invoked when the text of a document part has changed.
     * @param event the corresponding change event.
     * @de.renew.require (event != null)
     */
    protected void textChangedImpl(TextChangeEvent event) {
    }
}