package de.renew.netdoc.io.documentformatters;

import de.renew.netdoc.io.DocumentFormatException;
import de.renew.netdoc.model.document.DocumentPart;
import de.renew.netdoc.model.document.DocumentParts;
import de.renew.netdoc.model.document.parts.LinearDocumentPart;
import de.renew.netdoc.model.document.parts.linear.tex.TexTargetNamePart;
import de.renew.netdoc.model.document.parts.linear.tex.TexTargetPackagePart;

/**
 * This class is a TeX-specific document formatter that
 * ensures the presence of certain parts (TexTargetPackagePart and
 * TexTargetNamePart) in the main document part (LinearDocumentPart).
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public class TexExportFormatter extends AbstractDocumentFormatter {

    /**
     * Creates a new TeX document formatter.
     */
    public TexExportFormatter() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void formatImpl(DocumentPart targetPart) throws DocumentFormatException {
        DocumentPart main = targetPart.getPart(DocumentParts.NAME_NETDOC_MAIN);

        if (main == null) {
            throw new DocumentFormatException("Target document part did not contain main-tag");
        }
        if (!(main instanceof LinearDocumentPart)) {
            throw new DocumentFormatException("main-tag of target document part is not linear");
        }

        LinearDocumentPart linearMain = (LinearDocumentPart) main;

        if (!linearMain.containsPart(DocumentParts.NAME_NETDOC_TARGET_PACKAGE)) {
            linearMain.addPartAt(0, new TexTargetPackagePart(DocumentParts.NAME_NETDOC_TARGET_PACKAGE, targetPart));
        }
        if (!linearMain.containsPart(DocumentParts.NAME_NETDOC_TARGET_NAME)) {
            linearMain.addPartAt(0, new TexTargetNamePart(DocumentParts.NAME_NETDOC_TARGET_NAME, targetPart));
        }
    }
}