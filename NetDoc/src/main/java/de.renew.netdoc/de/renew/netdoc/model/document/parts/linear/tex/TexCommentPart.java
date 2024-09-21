package de.renew.netdoc.model.document.parts.linear.tex;

import de.renew.netdoc.model.document.parts.linear.TextDocumentPart;


/**
 * TeX comment definition document part.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public class TexCommentPart extends TextDocumentPart {

    /**
     * TeX comment part name.
     */
    public static final String NAME = "comment";


    /**
     * Creates a new TexCommentPart using an empty text.
     */
    public TexCommentPart() {
        this("");
    }

    /**
     * Creates a new TexCommentPart using the specified text.
     * @param text  the text of the new document part.
     * @de.renew.require (text != null)
     */
    public TexCommentPart(String text) {
        super(TexCommentPart.NAME, text);
    }


    /**
     * @inheritDoc
     */
    @Override
    protected String toStringImpl() {
        return "% " + this.getText() + "\n";
    }

    /**
     * Serial Version UID of this class.
     */
    private static final long serialVersionUID = 1L;
}