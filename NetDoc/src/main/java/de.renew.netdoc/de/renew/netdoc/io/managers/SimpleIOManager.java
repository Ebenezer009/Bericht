package de.renew.netdoc.io.managers;

import de.renew.netdoc.io.DocumentFormatter;
import de.renew.netdoc.io.DocumentParser;
import de.renew.netdoc.model.doctarget.DocTarget;


/**
 * Abstract Input/Output manager.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public class SimpleIOManager extends AbstractIOManager {

    /**
     * Creates a new SimpleInputOutputManager initially providing not any
     * formatter or parser. Uses an empty document filename extension.
     */
    public SimpleIOManager() {
        this(null, null, null, null, "");
    }

    /**
     * Creates a new SimpleInputOutputManager using the specified document
     * export, input and output formatters and the specified document parser.
     * @param documentExportFormatter the document export formatter to be used
     * by the new manager; or {@code null}, if initially no export formatter
     * should be used.
     * @param documentInputFormatter the document input formatter to be used by
     * the new manager; or {@code null}, if initially no input formatter should
     * be used.
     * @param documentOutputFormatter the document output formatter to be used
     * by the new manager; or {@code null}, if initially no output formatter
     * should be used.
     * @param documentParser the document parser to be used by the new manager;
     * or {@code null}, if initially no parser should be used.
     * @param documentFilenameExtension the default document filename extension
     * to be used by the new manager.
     * @de.renew.require (documentFilenameExtension != null)
     */
    public SimpleIOManager(DocumentFormatter documentExportFormatter,
                           DocumentFormatter documentInputFormatter,
                           DocumentFormatter documentOutputFormatter,
                           DocumentParser documentParser,
                           String documentFilenameExtension) {
        super();

        this._documentExportFormatter = documentExportFormatter;
        this._documentInputFormatter = documentInputFormatter;
        this._documentOutputFormatter = documentOutputFormatter;
        this._documentParser = documentParser;
        this._documentFilenameExtension = documentFilenameExtension;
    }

    /**
     * @inheritDoc
     */
    @Override
    public DocumentFormatter getDocumentExportFormatter() {
        return this._documentExportFormatter;
    }

    /**
     * @inheritDoc
     */
    @Override
    public DocumentFormatter getDocumentInputFormatter() {
        return this._documentInputFormatter;
    }

    /**
     * @inheritDoc
     */
    @Override
    public DocumentFormatter getDocumentOutputFormatter() {
        return this._documentOutputFormatter;
    }

    /**
     * @inheritDoc
     */
    @Override
    public DocumentParser getDocumentParser() {
        return this._documentParser;
    }

    /**
     * <p>Returns the default document filename extension used by this manager.
     * </p>
     * <p>To override implement {@link #getDocumentFilenameExtensionImpl()}.
     * </p>
     * @return the default document filename extension used by this manager.
     * @de.renew.ensure (returnValue != null)
     */
    public final String getDocumentFilenameExtension() {
        String returnValue = this.getDocumentFilenameExtensionImpl();
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * <p>Sets the default document filename extension used by this manager.
     * </p>
     * <p>To override implement {@link
     * #setDocumentFilenameExtensionImpl(String)}.</p>
     * @param newExtension the new default document filename extension to be
     * used by this manager.
     * @de.renew.require (newExtension != null)
     */
    public final void setDocumentFilenameExtension(String newExtension) {
        assert (newExtension != null) : "Precondition violated: (newExtension != null)";

        this.setDocumentFilenameExtensionImpl(newExtension);
    }

    /**
     * Sets the document export formatter used by this manager to the specified
     * formatter.
     * @param newExportFormatter the new document export formatter to be used
     * by this manager; or {@code null}, if no export formatter is to be used.
     */
    public void setDocumentExportFormatter(DocumentFormatter newExportFormatter) {
        this._documentExportFormatter = newExportFormatter;
    }

    /**
     * Sets the document input formatter used by this manager to the specified
     * formatter.
     * @param newInputFormatter the new document input formatter to be used by
     * this manager; or {@code null}, if no input formatter is to be used.
     */
    public void setDocumentInputFormatter(DocumentFormatter newInputFormatter) {
        this._documentInputFormatter = newInputFormatter;
    }

    /**
     * Sets the document output formatter used by this manager to the specified
     * formatter.
     * @param newOutputFormatter the new document output formatter to be used
     * by this manager; or {@code null}, if no output formatter is to be used.
     */
    public void setDocumentOutputFormatter(DocumentFormatter newOutputFormatter) {
        this._documentOutputFormatter = newOutputFormatter;
    }

    /**
     * Sets the document parser used by this manager to the specified parser.
     * @param newParser the new document parser to be used by this manager; or
     * {@code null}, if no parser is to be used.
     */
    public void setDocumentParser(DocumentParser newParser) {
        this._documentParser = newParser;
    }

    /**
     * @inheritDoc
     */
    @Override
    protected String getDocumentFilenameExtension(DocTarget docTarget) {
        return this.getDocumentFilenameExtension();
    }

    /**
     * Returns the default document filename extension used by this manager.
     * @return the default document filename extension used by this manager.
     * @de.renew.ensure (returnValue != null)
     */
    protected String getDocumentFilenameExtensionImpl() {
        return this._documentFilenameExtension;
    }

    /**
     * Sets the default document filename extension used by this manager.
     * @param newExtension the new default document filename extension to be
     * used by this manager.
     * @de.renew.require (newExtension != null)
     */
    protected void setDocumentFilenameExtensionImpl(String newExtension) {
        this._documentFilenameExtension = newExtension;
    }

    /**
     * The default document filename extension used by this manager.
     */
    private String _documentFilenameExtension;

    /**
     * The document export formatter used by this manager.
     */
    private DocumentFormatter _documentExportFormatter;

    /**
     * The document input formatter used by this manager.
     */
    private DocumentFormatter _documentInputFormatter;

    /**
     * The document output formatter used by this manager.
     */
    private DocumentFormatter _documentOutputFormatter;

    /**
     * The document parser used by this manager.
     */
    private DocumentParser _documentParser;
}