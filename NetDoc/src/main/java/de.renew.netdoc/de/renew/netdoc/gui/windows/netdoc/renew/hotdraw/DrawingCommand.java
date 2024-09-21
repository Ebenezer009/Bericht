package de.renew.netdoc.renew.hotdraw;

import CH.ifa.draw.framework.Drawing;
import de.renew.netdoc.model.command.commands.AbstractCommand;

/**
 * This class provides the command to the HotDraw Drawing.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public abstract class DrawingCommand extends AbstractCommand {

    /**
     * Creates a new HotDraw DrawingCommand using the specified drawing.
     *
     * @param drawing the drawing to be used by the new command.
     * @de.renew.require (drawing != null)
     */
    protected DrawingCommand(Drawing drawing) {
        assert (drawing != null) : "Precondition violated: (drawing != null)";

        this._drawing = drawing;
    }

    /**
     * <p>Returns the drawing used by this command.</p>
     * <p>To override implement {@link #getDrawingImpl()}.</p>
     *
     * @return the drawing used by this command.
     * @de.renew.ensure (returnValue != null)
     */
    public final Drawing getDrawing() {
        Drawing returnValue = this.getDrawingImpl();
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * Returns the drawing used by this command.
     *
     * @return the drawing used by this command.
     * @de.renew.ensure (returnValue != null)
     */
    protected Drawing getDrawingImpl() {
        return this._drawing;
    }

    /**
     * The drawing used by this command.
     */
    private Drawing _drawing;
}