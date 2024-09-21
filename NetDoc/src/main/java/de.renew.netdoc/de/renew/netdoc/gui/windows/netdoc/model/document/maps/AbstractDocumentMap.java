package de.renew.netdoc.model.document.maps;

import de.renew.netdoc.model.doctarget.DocTarget;
import de.renew.netdoc.model.document.Document;
import de.renew.netdoc.model.document.DocumentMap;
import de.renew.netdoc.renew.hotdraw.DrawingTarget;

import java.util.Collection;
import java.util.Iterator;

/**
 * This class includes the Abstract NetDoc document map.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public abstract class AbstractDocumentMap implements DocumentMap {
    /**
     * empty constructor
     */
    public AbstractDocumentMap(){

    }

    /**
     * <p>Determines whether this map contains the specified NetDoc document.
     * </p>
     * <p>To override implement {@link #containsDocumentImpl(Document)}.</p>
     *
     * @param document the document to be verified.
     * @return {@code true}, if this map contains the specified document;<br>
     * {@code false} otherwise.
     * @de.renew.require (document != null)
     */
    @Override
    public final boolean containsDocument(Document document) {
        assert (document != null) : "Precondition violated: (document != null)";

        return this.containsDocumentImpl(document);
    }

    /**
     * <p>Determines whether this map contains the specified documentation
     * target.</p>
     * <p>To override implement {@link #containsTargetImpl(DocTarget)}.</p>
     *
     * @param target the documentation target to be verified.
     * @return {@code true}, if this map contains the specified target;<br>
     * {@code false} otherwise.
     * @de.renew.require (target != null)
     */
    @Override
    public final boolean containsTarget(DocTarget target) {
        assert (target != null) : "Precondition violated: (target != null)";

        return this.containsTargetImpl(target);
    }

    /**
     * <p>Returns all NetDoc documents currently contained in this map.</p>
     * <p>To override implement {@link #getDocumentsImpl()}.</p>
     *
     * @return a collection containing {@link Document} instances.
     * @de.renew.ensure (returnValue != null)
     * @de.renew.ensure ! returnValue.contains(null)
     */
    @Override
    public final Collection<Document> getDocuments() {
        Collection<Document> returnValue = this.getDocumentsImpl();
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";
        assert !returnValue.contains(null) : "Postcondition violated: ! returnValue.contains(null)";

        return returnValue;
    }

    /**
     * <p>Returns all documentation targets currently contained in this map.
     * </p>
     * <p>To override implement {@link #getTargetsImpl()}.</p>
     *
     * @return a collection containing {@link DocTarget} instances.
     * @de.renew.ensure (returnValue != null)
     * @de.renew.ensure ! returnValue.contains(null)
     */
    @Override
    public final Collection<DrawingTarget> getTargets() {
        Collection<DrawingTarget> returnValue = this.getTargetsImpl();
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";
        assert !returnValue.contains(null) : "Postcondition violated: ! returnValue.contains(null)";

        return returnValue;
    }

    /**
     * <p>Returns the NetDoc document corresponding to the specified
     * documentation target.</p>
     * <p>To override implement {@link #getDocumentImpl(DocTarget)}.</p>
     *
     * @param target the documentation target specifying  the NetDoc
     * document to be returned.
     * @return the NetDoc document corresponding to the specified
     * documentation target;<br>
     * or {@code null}, if there was no corresponding document.
     * @de.renew.require (target != null)
     */
    @Override
    public final Document getDocument(DocTarget target) {
        assert (target != null) : "Precondition violated: (target != null)";

        return this.getDocumentImpl(target);
    }

    /**
     * Determines whether this map contains the specified NetDoc document.
     *
     * @param document the document to be verified.
     * @return {@code true}, if this map contains the specified document;<br>
     * {@code false} otherwise.
     * @de.renew.require (document != null)
     */
    protected boolean containsDocumentImpl(Document document) {
        return this.getDocuments().contains(document);
    }

    /**
     * Determines whether this map contains the specified documentation target.
     *
     * @param target the documentation target to be verified.
     * @return {@code true}, if this map contains the specified target;<br>
     * {@code false} otherwise.
     * @de.renew.require (target != null)
     */
    protected boolean containsTargetImpl(DocTarget target) {
        return this.getTargets().contains(target);
    }

    /**
     * Returns the NetDoc document corresponding to the specified
     * documentation target.
     *
     * @param target the documentation target specifying w the NetDoc
     * document to be returned.
     * @return the NetDoc document corresponding to the specified
     * documentation target;<br>
     * or {@code null}, if there was no corresponding document.
     * @de.renew.require (target != null)
     */
    protected Document getDocumentImpl(DocTarget target) {
        Iterator<Document> documentIterator = this.getDocuments().iterator();
        while (documentIterator.hasNext()) {
            Document current = documentIterator.next();
            if (current.getTarget() == target) {
                return current;
            }
        }
        return null;
    }

    /**
     * Returns all NetDoc documents currently contained in this map.
     *
     * @return a collection containing {@link Document} instances.
     * @de.renew.ensure (returnValue != null)
     * @de.renew.ensure ! returnValue.contains(null)
     */
    protected abstract Collection<Document> getDocumentsImpl();

    /**
     * Returns all documentation targets currently contained in this map.
     *
     * @return a collection containing {@link DocTarget} instances.
     * @de.renew.ensure (returnValue != null)
     * @de.renew.ensure ! returnValue.contains(null)
     */
    protected abstract Collection<DrawingTarget> getTargetsImpl();
}