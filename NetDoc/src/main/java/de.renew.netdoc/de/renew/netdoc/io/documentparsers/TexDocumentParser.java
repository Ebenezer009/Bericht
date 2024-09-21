package de.renew.netdoc.io.documentparsers;

import de.renew.netdoc.io.DocumentParseException;
import de.renew.netdoc.model.document.DocumentPart;
import de.renew.netdoc.model.document.DocumentParts;
import de.renew.netdoc.model.document.documents.linear.TexDocument;
import de.renew.netdoc.model.document.parts.LinearDocumentPart;
import de.renew.netdoc.model.document.parts.linear.TextDocumentPart;
import de.renew.netdoc.model.document.parts.linear.tex.TexBlockDefinitionPart;
import de.renew.netdoc.model.document.parts.linear.tex.TexDefinitionPart;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * NetDoc TeX document parser.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public class TexDocumentParser extends AbstractDocumentParser {

    /**
     * The Read-Ahead-Limit used for marking the reader.
     */
    private static int READ_AHEAD_LIMIT = 8192;

    /**
     * The regular expression specifying the beginning of a TeX definition.
     */
    private static String DEFINITION_BEGIN_REGEX = "^\\\\(netdoc.*\\{|begin\\{netdoc).*\\}.*";

    /**
     * The regular expression specifying the beginning of a TeX block definition.
     */
    private static String BLOCK_BEGIN_REGEX = "^\\\\begin\\{netdoc.*\\}.*";


    /**
     * Creates a new TexDocumentParser.
     */
    public TexDocumentParser() {
    }

    /**
     * @inheritDoc
     */
    @Override
    protected DocumentPart parseFromImpl(Reader documentReader)
                    throws IOException {
        BufferedReader bufferedReader = null;
        boolean bufferCreated = false;
        if (documentReader instanceof BufferedReader) {
            bufferedReader = (BufferedReader) documentReader;
        } else {
            bufferedReader = new BufferedReader(documentReader);
            bufferCreated = true;
        }

        DocumentPart returnValue = this.parseFrom(bufferedReader);

        if (bufferCreated) {
            bufferedReader.close();
        }

        return returnValue;
    }

    /**
     * Parses a TeX document part from the specified buffered reader.
     * @param documentReader the reader to parse the document part from.
     * @return the model of the document part been parsed.
     * @throws IOException if an I/O error occured.
     * @throws DocumentParseException if there was a problem while parsing the
     * document part.
     * @de.renew.require (documentReader != null)
     * @de.renew.ensure (returnValue != null)
     */
    protected DocumentPart parseFrom(BufferedReader documentReader)
                    throws IOException {
        this.readToDefinitionStart(documentReader);

        documentReader.mark(TexDocumentParser.READ_AHEAD_LIMIT);
        String line = documentReader.readLine();
        documentReader.reset();

        if (line.matches(TexDocumentParser.BLOCK_BEGIN_REGEX)) {
            return this.parseBlockDefinitionFrom(documentReader);
        } else {
            return this.parseDefinitionFrom(documentReader);
        }
    }

    /**
     * Parses a block definition part from the specified reader.
     * @param documentReader  the reader to parse the document part from.
     * @return the model of the document part been parsed.
     * @throws IOException  if an I/O error occured.
     * @throws DocumentParseException  if there was a problem while parsing the
     * document.
     * @de.renew.require (documentReader != null)
     * @de.renew.ensure (returnValue != null)
     */
    private LinearDocumentPart parseBlockDefinitionFrom(BufferedReader documentReader)
                    throws IOException {
        String name = this.parseDefinitionFrom(documentReader).getText();
        List<Object> parts = new ArrayList<Object>();

        StringBuffer buffer = new StringBuffer();
        documentReader.mark(TexDocumentParser.READ_AHEAD_LIMIT);
        String line = documentReader.readLine();

        while (line != null) {
            if (line.matches("^\\\\end\\{" + name + "\\}.*")) {
                parts.add(buffer.toString());
                break;
            } else if (line.matches(TexDocumentParser.DEFINITION_BEGIN_REGEX)) {
                parts.add(buffer.toString());
                buffer = new StringBuffer();
                documentReader.reset();
                if (line.matches(TexDocumentParser.BLOCK_BEGIN_REGEX)) {
                    parts.add(this.parseBlockDefinitionFrom(documentReader));
                } else {
                    parts.add(this.parseDefinitionFrom(documentReader));
                }
            } else {
                buffer.append(line);
                buffer.append('\n');
            }
            documentReader.mark(TexDocumentParser.READ_AHEAD_LIMIT);
            line = documentReader.readLine();
        }

        if (line == null) {
            throw new DocumentParseException(
                            "block definition part not terminated");
        } else {
            LinearDocumentPart definition = new TexBlockDefinitionPart(name);

            if (name.equals(DocumentParts.NAME_NETDOC_DOCUMENT)
                            || (parts.size() > 1)) {
                if (name.equals(DocumentParts.NAME_NETDOC_DOCUMENT)) {
                    definition = new TexDocument();
                }
                Iterator<Object> partIterator = parts.iterator();
                while (partIterator.hasNext()) {
                    Object current = partIterator.next();
                    if (current instanceof String) {
                        if (!current.equals("")) {
                            definition.appendPart(new TextDocumentPart(
                                            (String) current));
                        }
                    } else {
                        definition.appendPart((DocumentPart) current);
                    }
                }
            } else {
                definition.setText(parts.get(0).toString());
            }

            return definition;
        }
    }

    /**
     * Parses a definition part from the specified reader.
     * @param documentReader  the reader to parse the document part from.
     * @return the model of the document part been parsed.
     * @throws IOException  if an I/O error occured.
     * @throws DocumentParseException  if there was a problem while parsing the
     * document.
     * @de.renew.require (documentReader != null)
     * @de.renew.ensure (returnValue != null)
     */
    private DocumentPart parseDefinitionFrom(BufferedReader documentReader)
                    throws IOException {
        documentReader.mark(TexDocumentParser.READ_AHEAD_LIMIT);
        String line = documentReader.readLine();

        if (line == null) {
            throw new IOException();
        }
        int sepIndex = line.indexOf('{');
        if (sepIndex < 0) {
            throw new IOException();
        }

        TexDefinitionPart definition = new TexDefinitionPart(
                        line.substring(1, sepIndex));
        line = line.substring(sepIndex + 1);

        StringBuffer buffer = new StringBuffer();
        while (line != null) {
            if (line.matches("(.*[^\\\\]){0,1}\\}.*")) {
                while (true) {
                    sepIndex = line.indexOf('}');
                    if ((sepIndex > 0) && (line.charAt(sepIndex - 1) == '\\')) {
                        buffer.append(line.substring(0, sepIndex + 1));
                        line = line.substring(sepIndex + 1);
                    } else {
                        buffer.append(line.substring(0, sepIndex));
                        definition.setText(buffer.toString());
                        return definition;
                    }
                }
            } else {
                buffer.append(line);
                buffer.append('\n');
            }
            documentReader.mark(TexDocumentParser.READ_AHEAD_LIMIT);
            line = documentReader.readLine();
        }

        throw new DocumentParseException("definition part not terminated");
    }

    /**
     * Parses the pre-document comments until the beginning of the first
     * definition tag.
     * @param documentReader the reader to parse the comments from.
     * @return the pre-document comments as a single string.
     * @throws IOException if an I/O error occured.
     * @throws DocumentParseException if there was a problem while parsing the
     * comments.
     * @de.renew.require (documentReader != null)
     * @de.renew.ensure (returnValue != null)
     */
    private String readToDefinitionStart(BufferedReader documentReader)
                    throws IOException {
        StringBuffer buffer = new StringBuffer();

        documentReader.mark(TexDocumentParser.READ_AHEAD_LIMIT);
        String line = documentReader.readLine();

        while (line != null) {
            if (line.matches(TexDocumentParser.DEFINITION_BEGIN_REGEX)) {
                documentReader.reset();
                return buffer.toString();
            }
            buffer.append(line);
            buffer.append("\n");
            documentReader.mark(TexDocumentParser.READ_AHEAD_LIMIT);
            line = documentReader.readLine();
        }

        throw new DocumentParseException("definition-begin tag not found");
    }
}