package de.renew.netdoc.renew.gui;

import de.renew.netdoc.io.DocumentFormatException;
import de.renew.netdoc.io.documentformatters.AbstractDocumentFormatter;
import de.renew.netdoc.model.document.DocumentPart;


/**
 * Renew GUI document input formatter.
  *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
*/
public class DocumentInputFormatter extends AbstractDocumentFormatter {

    /**
     * Creates a new DocumentInputFormatter.
     */
    public DocumentInputFormatter() {
    }

    /**
     * @inheritDoc
     */
    @Override
    protected void formatImpl(DocumentPart targetPart)
                    throws DocumentFormatException {
    }
}