package de.renew.netdoc.model.doctarget;

import de.renew.netdoc.renew.hotdraw.FigureTarget;

import java.util.Collection;

/**
 * Interface for model of a NetDoc documentation target.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public interface DocTarget {

    /**
     * Returns the name of this target.
     *
     * @return the name of this target.
     * @de.renew.ensure (returnValue ! = null)
     */
    public String getName();

    /**
     * Returns the first occurrence of a sub target with the specified name.
     *
     * @param targetName the name of the sub target to be returned.
     * @return the first occurrence of a sub target with the specified name;<br>
     * or {@code null}, if this target did not contain a sub target with that
     * name.
     * @de.renew.require (targetName ! = null)
     */
    public DocTarget getSubTarget(String targetName);

    /**
     * Returns all sub targets contained in this target.
     *
     * @return a collection containing {@link
     * DocTarget} instances.
     * @de.renew.ensure (returnValue ! = null)
     * @de.renew.ensure ! returnValue.contains(null)
     */
    public Collection<FigureTarget> getSubTargets();

    /**
     * Returns all sub targets with the specified name.
     *
     * @param targetName the name of the sub targets to be returned.
     * @return a collection containing {@link
     * DocTarget} instances.
     * @de.renew.ensure (returnValue ! = null)
     * @de.renew.ensure ! returnValue.contains(null)
     */
    public Collection<DocTarget> getSubTargets(String targetName);
}