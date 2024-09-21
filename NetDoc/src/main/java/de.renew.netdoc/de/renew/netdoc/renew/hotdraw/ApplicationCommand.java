package de.renew.netdoc.renew.hotdraw;

import CH.ifa.draw.application.DrawApplication;

import de.renew.netdoc.model.command.commands.AbstractCommand;


/**
 * HotDraw DrawApplication Command.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public abstract class ApplicationCommand extends AbstractCommand {

    /**
     * Creates a new HotDraw ApplicationCommand using the specified draw
     * application.
     * @param application the draw application to be used by the new command.
     * @de.renew.require (application != null)
     */
    protected ApplicationCommand(DrawApplication application) {
        assert (application != null) : "Precondition violated: (application != null)";

        this._application = application;
    }

    /**
     * <p>Returns the draw application used by this command.</p>
     * <p>To override implement {@link #getDrawApplicationImpl()}.</p>
     * @return the draw application used by this command.
     * @de.renew.ensure (returnValue != null)
     */
    public final DrawApplication getDrawApplication() {
        DrawApplication returnValue = this.getDrawApplicationImpl();
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * Returns the draw application used by this command.
     * @return the draw application used by this command.
     * @de.renew.ensure (returnValue != null)
     */
    protected DrawApplication getDrawApplicationImpl() {
        return this._application;
    }

    /**
     * The draw application used by this command.
     */
    private DrawApplication _application;
}