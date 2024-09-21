package de.renew.netdoc.renew.plugin;

import de.renew.netdoc.model.command.Command;
import de.renew.netdoc.model.command.CommandException;

import org.apache.log4j.Logger;


/**
 * NetDoc Renew plugin command.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public abstract class PluginCommand extends CH.ifa.draw.util.Command
                implements Command {

    /**
     * The Logger for this class.
     */
    private static final Logger logger = Logger.getLogger(PluginCommand.class);

    /**
     * Creates a new PluginCommand for the specified NetDoc plugin using the
     * specified display name.
     * @param plugin the NetDoc plugin related to the new command.
     * @param name the display name of the new command.
     * @de.renew.require (plugin != null)
     * @de.renew.require (name != null)
     */
    public PluginCommand(NetDocPlugin plugin, String name) {
        super(PluginCommand.validate(name));
        assert (plugin != null) : "Precondition violated: (plugin != null)";

        this._plugin = plugin;
        this._innerCommand = null;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void execute() {
        try {
            this.getInnerCommand().execute();
        } catch (CommandException e) {
            logger.error("Error while executing command '" + this.name()
                            + "' using the nested command '"
                            + this.getInnerCommand() + "'", e);
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean isExecutable() {
        return this.getPlugin().isInitialised()
                        && this.getInnerCommand().isExecutable();
    }

    /**
     * @inheritDoc
     */
    @Override
    public String getName() {
        String returnValue = this.name();
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * <p>Returns the inner command to be executed by this command.</p>
     * <p>To override implement {@link #getInnerCommandImpl()}.</p>
     * @return the inner command to be executed by this command.
     * @de.renew.ensure (returnValue != null)
     */
    public final Command getInnerCommand() {
        Command returnValue = this.getInnerCommandImpl();
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * <p>Returns the NetDoc plugin registered to this command.</p>
     * <p>To override implement {@link #getPluginImpl()}.</p>
     * @return the NetDoc plugin registered to this command.
     * @de.renew.ensure (returnValue != null)
     */
    public final NetDocPlugin getPlugin() {
        NetDocPlugin returnValue = this._plugin;
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * Returns the inner command to be executed by this command.
     * @return the inner command to be executed by this command.
     * @de.renew.ensure (returnValue != null)
     */
    protected Command getInnerCommandImpl() {
        if (this._innerCommand == null) {
            this._innerCommand = this.createInnerCommand();
        }
        return this._innerCommand;
    }

    /**
     * Returns the NetDoc plugin registered to this command.
     * @return the NetDoc plugin registered to this command.
     * @de.renew.ensure (returnValue != null)
     */
    protected NetDocPlugin getPluginImpl() {
        return this._plugin;
    }

    /**
     * Creates the inner command used by this command.
     * @return the new inner command.
     * @de.renew.ensure (returnValue != null)
     */
    protected abstract Command createInnerCommand();

    /**
     * The inner command to be executed by this command
     */
    private Command _innerCommand;

    /**
     * The NetDoc plugin registered to this command.
     */
    private NetDocPlugin _plugin;

    /**
     * Validates the specified display name.
     * @param name the display name to be validated.
     * @return the validated display name.
     */
    private static String validate(String name) {
        assert (name != null) : "Precondition violated: (name != null)";

        return name;
    }
}