package de.renew.netdoc.model.document.parts.linear.tex;

import de.renew.netdoc.io.URLs;
import de.renew.netdoc.model.doctarget.targets.ResourceTarget;
import de.renew.netdoc.model.document.DocumentPart;

import java.net.URL;

/**
 * TeX document part whose text is specified by the package name of a
 * documentation target provided by another document part.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public class TexTargetPackagePart extends TexDefinitionPart {

    /**
     * The filename of the file specifying the root package of the targets.
     */
    private static final String PACKAGE_ROOT_FILENAME = "package-root.doctex";

    /**
     * Creates a new TexTargetPackagePart using the specified name and
     * document part.
     *
     * @param name the name of the new document part.
     * @param documentPart the document part assigned to the target whose
     * package information is specifying the text of the new document part.
     * @de.renew.require (name != null)
     * @de.renew.require (documentPart != null)
     */
    public TexTargetPackagePart(String name, DocumentPart documentPart) {
        super(name);

        this._documentPart = documentPart;
    }

    /**
     * Returns the document part assigned to the target specifying the text of
     * this document part.
     * <p>To override implement {@link #getDocumentPartImpl()}.</p>
     *
     * @return the document part.
     * @de.renew.ensure (returnValue != null)
     */
    public final DocumentPart getDocumentPart() {
        DocumentPart returnValue = this.getDocumentPartImpl();
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected DocumentPart cloneBase() {
        return new TexTargetPackagePart(this.getName(), this.getDocumentPart());
    }

    /**
     * Returns the document part assigned to the target specifying the text of
     * this document part.
     *
     * @return the document part.
     * @de.renew.ensure (returnValue != null)
     */
    protected DocumentPart getDocumentPartImpl() {
        return this._documentPart;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected final String getTextImpl() {
        try {
            URL targetDir = URLs.getParent(((ResourceTarget) this.getDocumentPart().getTarget()).getResource());
            URL packageRoot = targetDir;

            while (!URLs.exists(URLs.append(packageRoot, TexTargetPackagePart.PACKAGE_ROOT_FILENAME))) {
                packageRoot = URLs.getParent(packageRoot);
            }

            return targetDir.getPath().replaceAll("[/\\\\]", ".");
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected final void setTextImpl(String text) {
        throw new IllegalArgumentException("Unmodifiable");
    }

    /**
     * The document part assigned to the target specifying the text of this
     * document part.
     */
    private DocumentPart _documentPart;

    /**
     * Serial Version UID of this class.
     */
    private static final long serialVersionUID = 1L;
}