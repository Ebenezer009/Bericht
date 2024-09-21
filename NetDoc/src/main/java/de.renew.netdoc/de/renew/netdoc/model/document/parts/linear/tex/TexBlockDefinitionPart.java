package de.renew.netdoc.model.document.parts.linear.tex;

import de.renew.netdoc.model.document.DocumentPart;
import de.renew.netdoc.model.document.parts.linear.LinearContainerDocumentPart;

import java.util.Iterator;


/**
 * TeX block-definition document part.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public class TexBlockDefinitionPart extends LinearContainerDocumentPart {

    /**
     * Creates a new TexBlockDefinitionPart using the specified name and an empty
     * text.
     * @param name  the name of the new document part.
     * @de.renew.require (name != null)
     */
    public TexBlockDefinitionPart(String name) {
        this(name, "");
    }

    /**
     * Creates a new TexBlockDefinitionPart using the specified name and text.
     * @param name  the name of the new document part.
     * @param text  the text of the new document part.
     * @de.renew.require (name != null)
     * @de.renew.require (text != null)
     */
    public TexBlockDefinitionPart(String name, String text) {
        super(name, text);
    }


    /**
     * @inheritDoc
     */
    @Override
    protected String toStringImpl() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("\\begin{");
        buffer.append(this.getName());
        buffer.append("}\n");
        buffer.append(this.getText());
        if ((this.getText().length() != 0) && (this.getText()
                        .charAt(this.getText().length() - 1) != '\n')) {
            buffer.append('\n');
        }
        Iterator<DocumentPart> parts = this.getPartList().iterator();
        while (parts.hasNext()) {
            buffer.append(parts.next());
        }
        buffer.append("\\end{");
        buffer.append(this.getName());
        buffer.append("}\n");
        return buffer.toString();
    }

    /**
     * Serial Version UID of this class.
     */
    private static final long serialVersionUID = 1L;
}