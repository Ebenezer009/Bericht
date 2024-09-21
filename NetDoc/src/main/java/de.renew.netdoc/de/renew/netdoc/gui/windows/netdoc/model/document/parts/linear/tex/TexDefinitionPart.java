package de.renew.netdoc.model.document.parts.linear.tex;

import de.renew.netdoc.model.document.parts.linear.TextDocumentPart;

/**
 * This class provides the TeX definition document part.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public class TexDefinitionPart extends TextDocumentPart {

    /**
     * Creates a new TexDefinitionPart using the specified name and an empty
     * text.
     *
     * @param name the name of the new document part.
     * @de.renew.require (name != null)
     */
    public TexDefinitionPart(String name) {
        this(name, "");
    }

    /**
     * Creates a new TexDefinitionPart using the specified name and text.
     *
     * @param name the name of the new document part.
     * @param text the text of the new document part.
     * @de.renew.require (name != null)
     * @de.renew.require (text != null)
     */
    public TexDefinitionPart(String name, String text) {
        super(name, text);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String toStringImpl() {
        return "\\" + this.getName() + "{" + this.getText() + "}\n";
    }

    /**
     * Serial Version UID of this class.
     */
    private static final long serialVersionUID = 1L;
}