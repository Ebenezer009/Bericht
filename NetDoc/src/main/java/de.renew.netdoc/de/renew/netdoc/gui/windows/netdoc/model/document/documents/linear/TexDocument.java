package de.renew.netdoc.model.document.documents.linear;

import de.renew.netdoc.model.doctarget.DocTarget;
import de.renew.netdoc.model.document.DocumentParts;

/**
 * This class creates a Tex NetDoc document.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public class TexDocument extends SimpleLinearDocument {

    /**
     * Creates a new TexDocument initially not assigned to a target.
     */
    public TexDocument() {
        this(null);
    }

    /**
     * Creates a new TexDocument assigned to the specified target.
     *
     * @param target the documentation target to assign to the new document.
     */
    public TexDocument(DocTarget target) {
        super(target);
    }

    /**
     * Creates a new TexDocument assigned to the specified target using the
     * specified version string.
     *
     * @param target the documentation target to assign to the new document.
     * @param version the version string of the new document.
     * @de.renew.require (version != null)
     */
    public TexDocument(DocTarget target, String version) {
        super(target, version);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String toStringImpl() {
        String superString = super.toStringImpl();
        StringBuffer buffer = new StringBuffer();
        buffer.append("% ");
        buffer.append(this.getVersion());
        buffer.append("\n\\begin{");
        buffer.append(DocumentParts.NAME_NETDOC_DOCUMENT);
        buffer.append("}\n");
        buffer.append(superString);
        if ((superString.length() > 0) && (superString.charAt(superString.length() - 1) != '\n')) {
            buffer.append('\n');
        }
        buffer.append("\\end{");
        buffer.append(DocumentParts.NAME_NETDOC_DOCUMENT);
        buffer.append("}\n");
        return buffer.toString();
    }

    /**
     * Serial Version UID of this class.
     */
    private static final long serialVersionUID = 1L;
}