package de.renew.netdoc.renew.hotdraw.figures;

import CH.ifa.draw.util.StorableInput;
import de.renew.netdoc.io.documentparsers.TexDocumentParser;
import de.renew.netdoc.model.document.DocumentPart;

import java.io.IOException;
import java.io.StringReader;

/**
 * This class includes a JHotDraw figure providing a NetDoc TeX document part.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public class TexDocumentPartFigure extends DocumentPartFigure {

    /**
     * Creates a new figure initially not providing a document part.
     */
    public TexDocumentPartFigure() {
        this(null);
    }

    /**
     * Creates a new figure providing the specified document part.
     *
     * @param documentPart the document part to be provided by the new
     * figure;<br>
     * or {@code null}, if the new figure should initially not provide a
     * document part.
     */
    public TexDocumentPartFigure(DocumentPart documentPart) {
        super(documentPart);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void readImpl(StorableInput stream) throws IOException {
        String documentString = stream.readString();
        if (!documentString.equals("")) {
            StringReader reader = new StringReader(documentString);
            try {
                new TexDocumentParser().parseFrom(reader);
            } finally {
                reader.close();
            }
        }
    }

    /**
     * Serial Version UID of this class.
     */
    private static final long serialVersionUID = 1L;
}