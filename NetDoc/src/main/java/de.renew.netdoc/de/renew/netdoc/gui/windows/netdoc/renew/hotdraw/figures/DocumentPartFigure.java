package de.renew.netdoc.renew.hotdraw.figures;

import CH.ifa.draw.framework.Handle;
import CH.ifa.draw.standard.AbstractFigure;
import CH.ifa.draw.util.StorableInput;
import CH.ifa.draw.util.StorableOutput;
import de.renew.netdoc.model.document.DocumentPart;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.Vector;

/**
 * This class includes a JHotDraw figure providing a NetDoc document part.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public abstract class DocumentPartFigure extends AbstractFigure {

    /**
     * Creates a new figure initially not providing a document part.
     */
    protected DocumentPartFigure() {
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
    protected DocumentPartFigure(DocumentPart documentPart) {
        this._documentPart = documentPart;
    }

    /**
     * Returns the document part provided by this figure.
     *
     * @return the document part provided by this figure;<br>
     * or {@code null}, if this figure currently does not provide a document
     * part.
     */
    public DocumentPart getDocumentPart() {
        return this._documentPart;
    }

    /**
     * Sets the document part provided by this figure to the specified part.
     *
     * @param documentPart the new document part to be provided by this
     * figure;<br>
     * or {@code null}, if this figure should not provide a document part.
     */
    public void setDocumentPart(DocumentPart documentPart) {
        this._documentPart = documentPart;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void basicMoveBy(int dx, int dy) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void basicDisplayBox(Point origin, Point corner) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Rectangle displayBox() {
        return new Rectangle(0, 0, 0, 0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vector<Handle> handles() {
        return new Vector<Handle>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(Graphics g) {
    }

    /**
     * <p>Reads this figure from the specified storable input.</p>
     * <p>To override implement {@link #readImpl(StorableInput)}.</p>
     *
     * @param stream the storable input to be used.
     * @de.renew.require (stream != null)
     */
    @Override
    public final void read(StorableInput stream) throws IOException {
        assert (stream != null) : "Precondition violated: (stream != null)";

        this.readImpl(stream);
    }

    /**
     * <p>Stores this figure to the specified storable output.</p>
     * <p>To override implement {@link #writeImpl(StorableOutput)}.</p>
     *
     * @param stream the storable output to be used.
     * @de.renew.require (stream != null)
     */
    @Override
    public final void write(StorableOutput stream) {
        assert (stream != null) : "Precondition violated: (stream != null)";

        this.writeImpl(stream);
    }

    /**
     * <p>Stores this figure to the specified storable output.</p>
     * <p>Implements {@link #write(StorableOutput)}.</p>
     *
     * @param stream the storable output to be used.
     * @de.renew.require (stream != null)
     */
    protected void writeImpl(StorableOutput stream) {
        DocumentPart part = this.getDocumentPart();
        stream.writeString((part != null) ? part.toString() : "");
    }

    /**
     * <p>Reads this figure from the specified storable input.</p>
     * <p>Implements {@link #read(StorableInput)}.</p>
     *
     * @param stream the storable input to be used.
     * @throws IOException Signals that an I/O exception of some sort has occurred.
     * @de.renew.require (stream != null)
     */
    protected abstract void readImpl(StorableInput stream) throws IOException;

    /**
     * The document part provided by this figure.
     */
    private DocumentPart _documentPart;
}