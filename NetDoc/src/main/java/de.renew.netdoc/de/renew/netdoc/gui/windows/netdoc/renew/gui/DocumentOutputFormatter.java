package de.renew.netdoc.renew.gui;

import de.renew.netdoc.io.DocumentFormatException;
import de.renew.netdoc.io.documentformatters.AbstractDocumentFormatter;
import de.renew.netdoc.model.document.DocumentPart;
import de.renew.netdoc.model.document.DocumentParts;

import java.util.Date;

/**
 * This class provides the document output formatter for the Renew GUI.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public class DocumentOutputFormatter extends AbstractDocumentFormatter {

    /**
     * Creates a new DocumentOutputFormatter.
     */
    public DocumentOutputFormatter() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void formatImpl(DocumentPart targetPart) throws DocumentFormatException {
        DocumentPart lastModifiedPart = targetPart.getPart(DocumentParts.NAME_NETDOC_DATE_LAST_MODIFIED);
        if (lastModifiedPart != null) {
            lastModifiedPart.setText(DocumentParts.getDateString(new Date()));
        }
        targetPart.setModified(false);
    }
}