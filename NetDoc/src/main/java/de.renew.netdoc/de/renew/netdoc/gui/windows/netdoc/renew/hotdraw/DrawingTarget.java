package de.renew.netdoc.renew.hotdraw;

import CH.ifa.draw.framework.Drawing;
import CH.ifa.draw.framework.Figure;
import CH.ifa.draw.framework.FigureEnumeration;
import de.renew.netdoc.io.URLs;
import de.renew.netdoc.model.doctarget.targets.AbstractDocTarget;
import de.renew.netdoc.model.doctarget.targets.ResourceTarget;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * This class provides the documentation target for the HotDraw drawing.
 * <p>
 * TODO: Weak reference value map
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public class DrawingTarget extends AbstractDocTarget implements ResourceTarget {

    /**
     * Returns a DrawingTarget instance using the specified drawing.
     *
     * @param drawing the drawing to be used by the target.
     * @return a DrawingTarget instance using the specified drawing.
     * @de.renew.require (drawing != null)
     * @de.renew.ensure (returnValue != null)
     */
    public static DrawingTarget instance(Drawing drawing) {
        assert (drawing != null) : "Precondition violated: (drawing != null)";

        DrawingTarget returnValue = null;
        synchronized (DrawingTarget.instances) {
            returnValue = DrawingTarget.instances.get(drawing);
            if (returnValue == null) {
                returnValue = new DrawingTarget(drawing);
                DrawingTarget.instances.put(drawing, returnValue);
            }
        }
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * The DrawingTarget instances.
     */
    private static Map<Drawing, DrawingTarget> instances =
            Collections.synchronizedMap(new HashMap<Drawing, DrawingTarget>());

    /**
     * Creates a new target using the specified drawing.
     *
     * @param drawing the drawing to be used by the new target.
     * @de.renew.require (drawing != null)
     */
    private DrawingTarget(Drawing drawing) {
        this._drawing = drawing;
    }

    /**
     * Returns the drawing used by this target.
     *
     * @return the drawing used by this target.
     * @de.renew.ensure (returnValue != null)
     */
    public Drawing getDrawing() {
        Drawing returnValue = this._drawing;
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public URL getResource() {
        try {
            return URLs.create(this.getDrawing().getFilename());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getNameImpl() {
        String name = this.getDrawing().getName();
        return (name == null) ? "" : name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Collection<FigureTarget> getSubTargetsImpl() {
        Collection<FigureTarget> targets = new ArrayList<FigureTarget>();
        FigureEnumeration figures = this.getDrawing().figures();
        while (figures.hasMoreElements()) {
            Figure current = figures.nextFigure();
            if (current != null) {
                targets.add(FigureTarget.instance(current));
            }
        }
        return targets;
    }

    /**
     * The drawing used by this target.
     */
    private Drawing _drawing;
}