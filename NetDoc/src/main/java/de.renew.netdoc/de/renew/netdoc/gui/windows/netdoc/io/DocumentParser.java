package de.renew.netdoc.io;

import de.renew.netdoc.model.document.DocumentPart;

import java.io.IOException;
import java.io.Reader;


/**
 * This interface provides a contract for classes that implement document parsing
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public interface DocumentParser {

    /**
     * Parses a document part from the specified reader.
     * @param documentReader the reader to parse the document part from.
     * @return the model of the document part been parsed.
     * @throws IOException if an I/O error occurred.
     * @throws DocumentParseException if there was a problem while parsing the
     * document part.
     * @de.renew.require (documentReader != null)
     * @de.renew.ensure (returnValue != null)
     */
    public DocumentPart parseFrom(Reader documentReader)
            throws IOException, DocumentParseException;
}