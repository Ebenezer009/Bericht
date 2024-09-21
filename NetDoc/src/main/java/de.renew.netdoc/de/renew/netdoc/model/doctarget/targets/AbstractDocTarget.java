package de.renew.netdoc.model.doctarget.targets;

import de.renew.netdoc.model.doctarget.DocTarget;
import de.renew.netdoc.renew.hotdraw.FigureTarget;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;


/**
 * Abstract documentation target.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public abstract class AbstractDocTarget implements DocTarget {

    /**
     * <p>Returns the name of this target.</p>
     * <p>To override implement {@link #getNameImpl()}.</p>
     * @return the name of this target.
     * @de.renew.ensure (returnValue != null)
     */
    @Override
    public final String getName() {
        String returnValue = this.getNameImpl();
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * <p>Returns the first occurrence of a sub target with the specified name.
     * </p>
     * <p>To override implement {@link #getSubTargetImpl(String)}.</p>
     * @param targetName the name of the sub target to be returned.
     * @return the first occurrence of a sub target with the specified name;<br>
     * or {@code null}, if this target did not contain a sub target with that
     * name.
     * @de.renew.require (targetName != null)
     */
    @Override
    public final DocTarget getSubTarget(String targetName) {
        assert (targetName != null) : "Precondition violated: (targetName != null)";

        return this.getSubTargetImpl(targetName);
    }

    /**
     * <p>Returns all sub targets contained in this target.</p>
     * <p>To override implement {@link #getSubTargetsImpl()}.</p>
     * @return a collection containing {@link
     * DocTarget} instances.
     * @de.renew.ensure (returnValue != null)
     * @de.renew.ensure ! returnValue.contains(null)
     */
    @Override
    public final Collection<FigureTarget> getSubTargets() {
        Collection<FigureTarget> returnValue = this.getSubTargetsImpl();
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";
        assert !returnValue.contains(
                        null) : "Postcondition violated: ! returnValue.contains(null)";

        return returnValue;
    }

    /**
     * <p>Returns all sub targets with the specified name.</p>
     * <p>To override implement {@link #getSubTargetsImpl(String)}.</p>
     * @param targetName the name of the sub targets to be returned.
     * @return a collection containing {@link
     * DocTarget} instances.
     * @de.renew.ensure (returnValue != null)
     * @de.renew.ensure ! returnValue.contains(null)
     */
    @Override
    public final Collection<DocTarget> getSubTargets(String targetName) {
        assert (targetName != null) : "Precondition violated: (targetName != null)";

        Collection<DocTarget> returnValue = this.getSubTargetsImpl(targetName);
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";
        assert !returnValue.contains(
                        null) : "Postcondition violated: ! returnValue.contains(null)";

        return returnValue;
    }

    /**
     * <p>Returns the name of this target.</p>
     * <p>Implements {@link #getName()}.</p>
     * @return the name of this target.
     * @de.renew.ensure (returnValue != null)
     */
    protected abstract String getNameImpl();

    /**
     * <p>Returns the first occurrence of a sub target with the specified name.
     * By default perform a depth-first search.
     * </p>
     * <p>Implements {@link #getSubTarget(String)}.</p>
     * @param targetName the name of the sub target to be returned.
     * @return the first occurrence of a sub target with the specified name;<br>
     * or {@code null}, if this target did not contain a sub target with that
     * name.
     * @de.renew.require (targetName != null)
     */
    protected DocTarget getSubTargetImpl(String targetName) {
        Iterator<FigureTarget> targetIterator = this.getSubTargets().iterator();
        while (targetIterator.hasNext()) {
            DocTarget current = targetIterator.next();
            if (current.getName().equals(targetName)) {
                return current;
            }
            DocTarget subTarget = current.getSubTarget(targetName);
            if (subTarget != null) {
                return subTarget;
            }
        }
        return null;
    }

    /**
     * <p>Returns all sub targets contained in this target.</p>
     * <p>Implements {@link #getSubTargets()}.</p>
     * @return a collection containing {@link
     * DocTarget} instances.
     * @de.renew.ensure (returnValue != null)
     * @de.renew.ensure ! returnValue.contains(null)
     */
    protected abstract Collection<FigureTarget> getSubTargetsImpl();

    /**
     * <p>Returns all sub targets with the specified name. By default perform a
     * depth-first search.</p>
     * <p>Implements {@link #getSubTargets(String)}.</p>
     * @param targetName the name of the sub targets to be returned.
     * @return a collection containing {@link
     * DocTarget} instances.
     * @de.renew.ensure (returnValue != null)
     * @de.renew.ensure ! returnValue.contains(null)
     */
    protected Collection<DocTarget> getSubTargetsImpl(String targetName) {
        Collection<DocTarget> targets = new ArrayList<DocTarget>();
        Iterator<FigureTarget> targetIterator = this.getSubTargets().iterator();
        while (targetIterator.hasNext()) {
            DocTarget current = targetIterator.next();
            if (current.getName().equals(targetName)) {
                targets.add(current);
            }
            targets.addAll(current.getSubTargets(targetName));
        }
        return targets;
    }
}