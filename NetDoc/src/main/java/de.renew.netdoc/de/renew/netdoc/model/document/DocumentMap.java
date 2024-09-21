package de.renew.netdoc.model.document;

import de.renew.netdoc.model.doctarget.DocTarget;
import de.renew.netdoc.renew.hotdraw.DrawingTarget;

import java.util.Collection;


/**
 * NetDoc document-target map. Maps a collection of documents to a collection
 * of documentation targets and vice versa.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public interface DocumentMap {

    /**
     * Determines whether this map contains the specified NetDoc document.
     * @param document the document to be verified.
     * @return {@code true}, if this map contains the specified document;<br>
     * {@code false} otherwise.
     * @de.renew.require (document != null)
     */
    public boolean containsDocument(Document document);

    /**
     * Determines whether this map contains the specified documentation target.
     * @param target the documentation target to be verified.
     * @return {@code true}, if this map contains the specified target;<br>
     * {@code false} otherwise.
     * @de.renew.require (target != null)
     */
    public boolean containsTarget(DocTarget target);

    /**
     * Returns all NetDoc documents currently contained in this map.
     * @return a collection containing {@link
     * Document} instances.
     * @de.renew.ensure (returnValue != null)
     * @de.renew.ensure ! returnValue.contains(null)
     */
    public Collection<Document> getDocuments();

    /**
     * Returns all documentation targets currently contained in this map.
     * @return a collection containing {@link
     * DocTarget} instances.
     * @de.renew.ensure (returnValue != null)
     * @de.renew.ensure ! returnValue.contains(null)
     */
    public Collection<DrawingTarget> getTargets();

    /**
     * Returns the NetDoc document corresponding to the specified
     * documentation target.
     * @param target the documentation target specifying the the NetDoc
     * document to be returned.
     * @return the NetDoc document corresponding to the specified
     * documentation target;<br>
     * or {@code null}, if there was no corresponding document.
     * @de.renew.require (target != null)
     */
    public Document getDocument(DocTarget target);
}