package de.renew.netdoc.model.document.parts.linear;

import de.renew.netdoc.model.document.event.TextChangeEvent;


/**
 * Text document part.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public class TextDocumentPart extends AbstractLinearDocumentPart {

    /**
     * Creates a new TextDocumentPart using the specified name and an empty
     * text.
     * @param name  the name of the new document part.
     * @de.renew.require (name != null)
     */
    public TextDocumentPart(String name) {
        this(name, "");
    }

    /**
     * Creates a new TextDocumentPart using the specified name and text.
     * @param name  the name of the new document part.
     * @param text  the text of the new document part.
     * @de.renew.require (name != null)
     * @de.renew.require (text != null)
     */
    public TextDocumentPart(String name, String text) {
        assert (name != null) : "Precondition violated: (name != null)";
        assert (text != null) : "Precondition violated: (text != null)";

        this._name = name;
        this._text = text;
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
    protected void setTextImpl(String text) {
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
    protected String toStringImpl() {
        return this.getText();
    }

    /**
     * The name of this document part.
     */
    private String _name;

    /**
     * The text of this document part.
     */
    private String _text;

    /**
     * Serial Version UID of this class.
     */
    private static final long serialVersionUID = 1L;
}