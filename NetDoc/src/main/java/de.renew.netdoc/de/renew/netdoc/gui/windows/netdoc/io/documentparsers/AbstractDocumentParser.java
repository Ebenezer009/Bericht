package de.renew.netdoc.io.documentparsers;

import de.renew.netdoc.io.DocumentParseException;
import de.renew.netdoc.io.DocumentParser;
import de.renew.netdoc.model.document.DocumentPart;

import java.io.IOException;
import java.io.Reader;


/**
 * This class provides a standardized structure for document parsing,
 * with a specific focus on handling preconditions and post conditions using assertions.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public abstract class AbstractDocumentParser implements DocumentParser {
    /**
     * empty constructor
     */
    public AbstractDocumentParser(){

    }

    /**
     * <p>Parses a document part from the specified reader.</p>
     * <p>To override implement {@link #parseFromImpl(Reader)}.</p>
     *
     * @param documentReader the reader to parse the document part from.
     * @return the model of the document part been parsed.
     * @throws IOException if an I/O error occurred.
     * @throws DocumentParseException if there was a problem while parsing the
     * document part.
     * @de.renew.require (documentReader != null)
     * @de.renew.ensure (returnValue != null)
     */
    @Override
    public final DocumentPart parseFrom(Reader documentReader)
            throws IOException {
        assert (documentReader != null) : "Precondition violated: (documentReader != null)";

        DocumentPart returnValue = this.parseFromImpl(documentReader);
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

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
    protected abstract DocumentPart parseFromImpl(Reader documentReader)
            throws IOException;
}