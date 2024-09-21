package de.renew.netdoc.model.document.parts.linear.tex;

import de.renew.netdoc.model.document.DocumentPart;


/**
 * TeX document part whose text is specified by the name of a documentation
 * target provided by another document part.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public class TexTargetNamePart extends TexDefinitionPart {

    /**
     * Creates a new TexTargetNamePart using the specified name and document
     * part.
     * @param name the name of the new document part.
     * @param documentPart the document part assigned to the target whose name
     * is specifying the text of the new document part.
     * @de.renew.require (name != null)
     * @de.renew.require (documentPart != null)
     */
    public TexTargetNamePart(String name, DocumentPart documentPart) {
        super(name);

        this._documentPart = documentPart;
    }

    /**
     * Returns the document part assigned to the target specfying the text of
     * this document part.
     * <p>To override implement {@link #getDocumentPartImpl()}.</p>
     * @return the document part.
     * @de.renew.ensure (returnValue != null)
     */
    public final DocumentPart getDocumentPart() {
        DocumentPart returnValue = this.getDocumentPartImpl();
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * @inheritDoc
     */
    @Override
    protected DocumentPart cloneBase() {
        return new TexTargetNamePart(this.getName(), this.getDocumentPart());
    }

    /**
     * Returns the document part assigned to the target specfying the text of
     * this document part.
     * @return the document part.
     * @de.renew.ensure (returnValue != null)
     */
    protected DocumentPart getDocumentPartImpl() {
        return this._documentPart;
    }

    /**
     * @inheritDoc
     */
    @Override
    protected final String getTextImpl() {
        try {
            return this.getDocumentPart().getTarget().getName();
        } catch (NullPointerException e) {
            return "";
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    protected final void setTextImpl(String text) {
        throw new IllegalArgumentException("Unmodifiable");
    }

    /**
     * The document part assigned to the target specfying the text of this
     * document part.
     */
    private DocumentPart _documentPart;

    /**
     * Serial Version UID of this class.
     */
    private static final long serialVersionUID = 1L;
}