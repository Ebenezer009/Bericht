package de.renew.netdoc.io;

import de.renew.netdoc.model.doctarget.DocTarget;
import de.renew.netdoc.model.document.DocumentPart;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;


/**
 * This interface provides a set of methods for managing
 * the input and output of document parts.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public interface IOManager {

    /**
     * Returns the document export formatter used by this manager.
     * @return the document export formatter used by this manager;<br>
     * or {@code null}, if no export formatter is used.
     */
    public DocumentFormatter getDocumentExportFormatter();

    /**
     * Returns the document input formatter used by this manager.
     * @return the document input formatter used by this manager;<br>
     * or {@code null}, if no input formatter is used.
     */
    public DocumentFormatter getDocumentInputFormatter();

    /**
     * Returns the document output formatter used by this manager.
     * @return the document output formatter used by this manager;<br>
     * or {@code null}, if no output formatter is used.
     */
    public DocumentFormatter getDocumentOutputFormatter();

    /**
     * Returns the document parser used by this manager.
     * @return the document parser used by this manager;<br>
     * or {@code null}, if no parser is used.
     */
    public DocumentParser getDocumentParser();

    /**
     * Determines whether a document part for the specified documentation
     * target already exists.
     * @param docTarget the documentation target specifying the document part.
     * @return {@code true}, if a document part for the specified target
     * already exists;<br>
     * {@code false} otherwise.
     * @de.renew.require (docTarget != null)
     */
    public boolean documentPartExists(DocTarget docTarget);

    /**
     * Loads the NetDoc document part corresponding to the specified
     * documentation target.
     * @param correspondingTarget the documentation target specifying the
     * NetDoc document part to be loaded.
     * @return the loaded document part.
     * @throws IOException if an I/O error occurred while loading the document
     * part.
     * @de.renew.require (correspondingTarget != null)
     * @de.renew.ensure (returnValue != null)
     */
    public DocumentPart loadDocumentPart(DocTarget correspondingTarget)
            throws IOException;

    /**
     * Saves the specified NetDoc document part to its default location.
     * @param partToSave the NetDoc document part to be saved.
     * @throws IOException if an I/O error occurred while saving the document.
     * @de.renew.require (partToSave != null)
     */
    public void saveDocumentPart(DocumentPart partToSave) throws IOException;

    /**
     * Imports a NetDoc document part from the specified input stream.
     * @param inputStream the input stream to parse the document part from.
     * @return the parsed document part.
     * @throws IOException if an I/O error occurred while importing the document
     * part.
     * @de.renew.require (inputStream != null)
     * @de.renew.ensure (returnValue != null)
     */
    public DocumentPart importDocumentPartFrom(Reader inputStream)
            throws IOException;

    /**
     * Exports the specified NetDoc document part to the default export
     * location.
     * @param partToExport the NetDoc document part to be exported.
     * @throws IOException if an I/O error occurred while exporting the document
     * part.
     * @de.renew.require (partToExport != null)
     */
    public void exportDocumentPart(DocumentPart partToExport)
            throws IOException;

    /**
     * Exports the specified NetDoc document part to the specified output
     * stream.
     * @param partToExport the NetDoc document part to be exported.
     * @param outputStream the output stream the document part will be written
     * to.
     * @throws IOException if an I/O error occurred while exporting the document
     * part.
     * @de.renew.require (partToExport != null)
     * @de.renew.require (outputStream != null)
     */
    public void exportDocumentPartTo(DocumentPart partToExport,
                                     Writer outputStream)
            throws IOException;
}