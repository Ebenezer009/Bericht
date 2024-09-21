package de.renew.netdoc.renew.hotdraw;

import CH.ifa.draw.framework.Figure;
import CH.ifa.draw.framework.FigureEnumeration;

import de.renew.netdoc.model.doctarget.targets.AbstractDocTarget;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


/**
 * HotDraw figure documentation target.
 *
 * TODO: Weak reference value map
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public class FigureTarget extends AbstractDocTarget {

    /**
     * Returns the FigureTarget instance using the specified figure.
     * @param figure the figure to be used by the target.
     * @return the FigureTarget instance using the specified figure.
     * @de.renew.require (figure != null)
     * @de.renew.ensure (returnValue != null)
     */
    public static FigureTarget instance(Figure figure) {
        assert (figure != null) : "Precondition violated: (figure != null)";

        FigureTarget returnValue = null;
        synchronized (FigureTarget.instances) {
            returnValue = FigureTarget.instances.get(figure);
            if (returnValue == null) {
                returnValue = new FigureTarget(figure);
                FigureTarget.instances.put(figure, returnValue);
            }
        }
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * The FigureTarget instances.
     */
    private static Map<Figure, FigureTarget> instances = Collections
                    .synchronizedMap(new HashMap<Figure, FigureTarget>());

    /**
     * Creates a new target using the specified figure.
     * @param figure the figure to be used by the new target.
     * @de.renew.require (figure != null)
     */
    private FigureTarget(Figure figure) {
        this._figure = figure;
        this._name = "";
    }

    /**
     * Returns the figure used by this target.
     * @return the figure used by this target.
     * @de.renew.ensure (returnValue != null)
     */
    public Figure getFigure() {
        Figure returnValue = this._figure;
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * Sets the name of this target to the specified name.
     * @param newName the new name for this target.
     * @de.renew.require (newName != null)
     */
    public void setName(String newName) {
        assert (newName != null) : "Precondition violated: (newName != null)";

        this._name = newName;
    }

    /**
     * @inheritDoc
     */
    @Override
    protected String getNameImpl() {
        return this._name;
    }

    /**
     * @inheritDoc
     */
    @Override
    protected Collection<FigureTarget> getSubTargetsImpl() {
        Collection<FigureTarget> targets = new ArrayList<FigureTarget>();
        FigureEnumeration figures = this.getFigure().figures();
        while (figures.hasMoreElements()) {
            Figure current = figures.nextFigure();
            if (current != null) {
                targets.add(FigureTarget.instance(current));
            }
        }
        return targets;
    }

    /**
     * The figure used by this target.
     */
    private Figure _figure;

    /**
     * The name of this target.
     */
    private String _name;
}