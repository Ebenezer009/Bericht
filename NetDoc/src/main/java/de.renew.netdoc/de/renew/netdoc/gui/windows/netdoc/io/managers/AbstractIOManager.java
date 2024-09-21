package de.renew.netdoc.io.managers;

import de.renew.netdoc.io.IOManager;
import de.renew.netdoc.io.URLs;
import de.renew.netdoc.model.doctarget.DocTarget;
import de.renew.netdoc.model.doctarget.targets.ResourceTarget;
import de.renew.netdoc.model.document.Document;
import de.renew.netdoc.model.document.DocumentPart;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;


/**
 * This class provides a flexible framework for managing different aspects
 * of document parts, allowing for customization in concrete subclasses.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public abstract class AbstractIOManager implements IOManager {

    /**
     * Creates a new AbstractInputOutputManager.
     */
    protected AbstractIOManager() {
    }

    /**
     * <p>Determines whether a document part for the specified documentation
     * target already exists.</p>
     * <p>To override implement {@link #documentPartExistsImpl(DocTarget)}.</p>
     *
     * @param docTarget the documentation target specifying the document part.
     * @return {@code true}, if a document part for the specified target
     * already exists;<br>
     * {@code false} otherwise.
     * @de.renew.require (docTarget != null)
     */
    @Override
    public final boolean documentPartExists(DocTarget docTarget) {
        assert (docTarget != null) : "Precondition violated: (docTarget != null)";

        return this.documentPartExistsImpl(docTarget);
    }

    /**
     * <p>Loads the NetDoc document part correspoding to the specified
     * documentation target.</p>
     * <p>To override implement {@link #loadDocumentPartImpl(DocTarget)}.</p>
     *
     * @param correspondingTarget the documentation target specifying the
     * NetDoc document part to be loaded.
     * @return the loaded document part.
     * @throws IOException if an I/O error occurred while loading the document
     * part.
     * @de.renew.require (correspondingTarget != null)
     * @de.renew.ensure (returnValue != null)
     */
    @Override
    public final DocumentPart loadDocumentPart(DocTarget correspondingTarget)
            throws IOException {
        assert (correspondingTarget != null) : "Precondition violated: (correspondingTarget != null)";

        DocumentPart returnValue = this
                .loadDocumentPartImpl(correspondingTarget);
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * <p>Saves the specified NetDoc document part to its default location.</p>
     * <p>To override implement {@link #saveDocumentPartImpl(DocumentPart)}.</p>
     *
     * @param partToSave the NetDoc document part to be saved.
     * @throws IOException if an I/O error occurred while saving the document
     * part.
     * @de.renew.require (partToSave != null)
     */
    @Override
    public final void saveDocumentPart(DocumentPart partToSave)
            throws IOException {
        assert (partToSave != null) : "Precondition violated: (partToSave != null)";

        this.saveDocumentPartImpl(partToSave);
    }

    /**
     * <p>Imports a NetDoc document part from the specified input stream.</p>
     * <p>To override implement {@link #importDocumentPartFromImpl(Reader)}.</p>
     *
     * @param inputStream the input stream to parse the document part from.
     * @return the parsed document part.
     * @throws IOException if an I/O error occurred while importing the document
     * part.
     * @de.renew.require (inputStream != null)
     * @de.renew.ensure (returnValue != null)
     */
    @Override
    public final DocumentPart importDocumentPartFrom(Reader inputStream)
            throws IOException {
        assert (inputStream != null) : "Precondition violated: (inputStream != null)";

        DocumentPart returnValue = this.importDocumentPartFromImpl(inputStream);
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * <p>Exports the specified NetDoc document part to the default export
     * location.</p>
     * <p>To override implement {@link #exportDocumentPartImpl(DocumentPart)}.</p>
     *
     * @param partToExport the NetDoc document part to be exported.
     * @throws IOException if an I/O error occurred while exporting the document
     * part.
     * @de.renew.require (documentToExport != null)
     */
    @Override
    public final void exportDocumentPart(DocumentPart partToExport)
            throws IOException {
        assert (partToExport != null) : "Precondition violated: (partToExport != null)";

        this.exportDocumentPartImpl(partToExport);
    }

    /**
     * <p>Exports the specified NetDoc document part to the specified output
     * stream.</p>
     * <p>To override implement {@link #exportDocumentPartToImpl(DocumentPart, Writer)}.</p>
     *
     * @param partToExport the NetDoc document part to be exported.
     * @param outputStream the output stream the document part will be written
     * to.
     * @throws IOException if an I/O error occurred while exporting the document
     * part.
     * @de.renew.require (partToExport != null)
     * @de.renew.require (outputStream != null)
     */
    @Override
    public final void exportDocumentPartTo(DocumentPart partToExport,
                                           Writer outputStream)
            throws IOException {
        assert (partToExport != null) : "Precondition violated: (partToExport != null)";
        assert (outputStream != null) : "Precondition violated: (outputStream != null)";

        this.exportDocumentPartToImpl(partToExport, outputStream);
    }

    /**
     * Determines whether a document part for the specified documentation
     * target already exists.
     * @param docTarget the documentation target specifying the document part.
     * @return {@code true}, if a document part for the specified target
     * already exists;<br>
     * {@code false} otherwise.
     * @de.renew.require (docTarget != null)
     */
    protected boolean documentPartExistsImpl(DocTarget docTarget) {
        URL path = this.getDefaultDocumentLocation(docTarget);
        return (path != null) && URLs.exists(path);
    }

    /**
     * Loads the NetDoc document part correspoding to the specified
     * documentation target.
     * @param correspondingTarget the documentation target specifying the
     * NetDoc document part to be loaded.
     * @return the loaded document part.
     * @throws IOException if an I/O error occurred while loading the document
     * part.
     * @de.renew.require (correspondingTarget != null)
     * @de.renew.ensure (returnValue != null)
     */
    protected DocumentPart loadDocumentPartImpl(DocTarget correspondingTarget)
            throws IOException {
        URL path = this.getDefaultDocumentLocation(correspondingTarget);
        if (path == null) {
            throw new IOException("No default import location specified");
        }

        InputStream stream = URLs.openInputStream(path);
        try {
            InputStreamReader reader = new InputStreamReader(stream);
            try {
                DocumentPart part = this.importDocumentPartFrom(reader);
                part.setTarget(correspondingTarget);
                return part;
            } finally {
                reader.close();
            }
        } finally {
            stream.close();
        }
    }

    /**
     * Saves the specified NetDoc document part to its default location.
     * @param partToSave the NetDoc document part to be saved.
     * @throws IOException if an I/O error occurred while saving the document
     * part.
     * @de.renew.require (partToSave != null)
     */
    protected void saveDocumentPartImpl(DocumentPart partToSave)
            throws IOException {
        this.exportDocumentPart(partToSave);
    }

    /**
     * Imports a NetDoc document part from the specified input stream.
     * @param inputStream the input stream to parse the document part from.
     * @return the parsed document part.
     * @throws IOException if an I/O error occured while importing the document
     * part.
     * @de.renew.require (inputStream != null)
     * @de.renew.ensure (returnValue != null)
     */
    protected DocumentPart importDocumentPartFromImpl(Reader inputStream)
            throws IOException {
        if (this.getDocumentParser() == null) {
            throw new IOException("No parser available");
        }
        DocumentPart returnValue = this.getDocumentParser()
                .parseFrom(inputStream);
        if (this.getDocumentInputFormatter() != null) {
            this.getDocumentInputFormatter().format(returnValue);
        }
        return returnValue;
    }

    /**
     * Exports the specified NetDoc document part to the default export
     * location.
     * @param partToExport the NetDoc document part to be exported.
     * @throws IOException if an I/O error occurred while exporting the document
     * part.
     * @de.renew.require (partToExport != null)
     */
    protected void exportDocumentPartImpl(DocumentPart partToExport)
            throws IOException {
        URL path = this.getDefaultDocumentLocation(partToExport);
        if (path == null) {
            throw new IOException("No default export location specified");
        }

        OutputStream stream = URLs.openOutputStream(path);
        try {
            OutputStreamWriter writer = new OutputStreamWriter(stream);
            try {
                this.exportDocumentPartTo(partToExport, writer);
            } finally {
                writer.close();
            }
        } finally {
            stream.close();
        }
    }

    /**
     * Exports the specified NetDoc document part to the specified output
     * stream.
     * @param partToExport the NetDoc document part to be exported.
     * @param outputStream the output stream the document part will be written
     * to.
     * @throws IOException if an I/O error occured while exporting the document
     * part.
     * @de.renew.require (partToExport != null)
     * @de.renew.require (outputStream != null)
     */
    protected void exportDocumentPartToImpl(DocumentPart partToExport,
                                            Writer outputStream)
            throws IOException {
        if (this.getDocumentOutputFormatter() != null) {
            this.getDocumentOutputFormatter().format(partToExport);
        }
        if (this.getDocumentExportFormatter() != null) {
            partToExport = (Document) partToExport.clone();
            this.getDocumentExportFormatter().format(partToExport);
        }
        partToExport.writeTo(outputStream);
    }

    /**
     * Returns the default stream location of the specified document part.
     * @param documentPart the document part to be processed.
     * @return the default stream location;<br>
     * or {@code null}, if no default location is specified for that document
     * part.
     * @de.renew.require (documentPart != null)
     */
    protected URL getDefaultDocumentLocation(DocumentPart documentPart) {
        if (documentPart.getTarget() != null) {
            return this.getDefaultDocumentLocation(documentPart.getTarget());
        } else {
            return null;
        }
    }

    /**
     * Returns the default stream location of the document part corresponding
     * to the specified documentation target.
     * @param docTarget the documentation target to be processed.
     * @return the default stream location;<br>
     * or {@code null}, if no default location is specified for that document
     * part.
     * @de.renew.require (docTarget != null)
     */
    protected URL getDefaultDocumentLocation(DocTarget docTarget) {
        try {
            return URLs.exchangeExtension(
                    ((ResourceTarget) docTarget).getResource(),
                    this.getDocumentFilenameExtension(docTarget));
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Returns the default filename extension for the specified document part.
     * @param documentPart the document part to be processed.
     * @return the default filename extension.
     * @de.renew.require (documentPart != null)
     * @de.renew.ensure (returnValue != null)
     */
    protected String getDocumentFilenameExtension(DocumentPart documentPart) {
        if (documentPart.getTarget() != null) {
            return this.getDocumentFilenameExtension(documentPart.getTarget());
        } else {
            return "";
        }
    }

    /**
     * Returns the default filename extension for the document corresponding to
     * the specified documentation target.
     * @param docTarget the documentation target to be processed.
     * @return the default filename extension.
     * @de.renew.require (docTarget != null)
     * @de.renew.ensure (returnValue != null)
     */
    protected String getDocumentFilenameExtension(DocTarget docTarget) {
        return "";
    }
}