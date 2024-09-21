package de.renew.netdoc.io.documentformatters;

import de.renew.netdoc.io.DocumentFormatException;
import de.renew.netdoc.io.DocumentFormatter;
import de.renew.netdoc.model.document.DocumentPart;


/**
 * Abstract NetDoc document formatter.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public abstract class AbstractDocumentFormatter implements DocumentFormatter {

    /**
     * <p>Formats the specified document part.</p>
     * <p>To override implement {@link #formatImpl(DocumentPart)}.</p>
     * @param targetPart the document part to be formatted.
     * @throws DocumentFormatException if the specified document part or a
     * needed sub part was unmodifiable or not manageable.
     * @de.renew.require (targetPart != null)
     */
    @Override
    public final void format(DocumentPart targetPart)
                    throws DocumentFormatException {
        assert (targetPart != null) : "Precondition violated: (targetPart != null)";

        this.formatImpl(targetPart);
    }

    /**
     * Formats the specified document part.
     * @param targetPart the document part to be formatted.
     * @throws DocumentFormatException if the specified document part or a
     * needed sub part was unmodifiable or not manageable.
     * @de.renew.require (targetPart != null)
     */
    protected abstract void formatImpl(DocumentPart targetPart)
                    throws DocumentFormatException;
}