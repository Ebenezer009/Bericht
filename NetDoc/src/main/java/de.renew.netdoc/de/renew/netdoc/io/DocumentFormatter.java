package de.renew.netdoc.io;

import de.renew.netdoc.model.document.DocumentPart;


/**
 * NetDoc document formatter.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public interface DocumentFormatter {

    /**
     * Formats the specified document part.
     * @param targetPart  the document part to be formatted.
     * @throws DocumentFormatException  if the specified document part or a
     * needed sub part was unmodifiable or not manageable.
     * @de.renew.require (targetPart != null)
     */
    public void format(DocumentPart targetPart) throws DocumentFormatException;
}