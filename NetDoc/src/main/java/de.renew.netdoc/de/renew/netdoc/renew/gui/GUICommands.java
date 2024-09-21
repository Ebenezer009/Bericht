package de.renew.netdoc.renew.gui;

import de.renew.netdoc.gui.Window;
import de.renew.netdoc.gui.commands.ConnectWindow;
import de.renew.netdoc.model.command.Command;
import de.renew.netdoc.model.command.CommandException;
import de.renew.netdoc.model.container.ContainerManager;
import de.renew.netdoc.model.container.DocumentContainer;
import de.renew.netdoc.model.container.commands.manager.AllDocumentsCommand;
import de.renew.netdoc.model.container.commands.manager.CurrentDocumentCommand;
import de.renew.netdoc.model.container.commands.manager.SingleDocumentCommand;
import de.renew.netdoc.model.document.Document;


/**
 * Factory providing ContainerManager window commands.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public abstract class GUICommands {

    /**
     * Returns a Connect-Current-Document-Window command for the specified
     * container manager and connect direction.
     * @param containerManager the container manager to be used by the command.
     * @param connectDirection the connect direction to be used by the command.
     * @return the command.
     * @de.renew.require (containerManager != null)
     * @de.renew.require ConnectWindow.isValidDirection(connectDirection)
     * @de.renew.ensure (returnValue != null)
     * @see ConnectWindow#isValidDirection(Object)
     */
    public static Command getConnectCurrentDocumentWindow(ContainerManager containerManager,
                                                          Object connectDirection) {
        assert (containerManager != null) : "Precondition violated: (containerManager != null)";
        assert ConnectWindow.isValidDirection(
                        connectDirection) : "Precondition violated: "
                                        + "ConnectWindow.isValidDirection(connectDirection)";

        final ContainerManager iContainerManager = containerManager;
        final Object iConnectDirection = connectDirection;
        Command returnValue = new CurrentDocumentCommand(containerManager) {
            @Override
            protected Command createCommand(Document document) {
                return new ConnectDocumentWindow(iContainerManager, document,
                                iConnectDirection);
            }

            @Override
            protected String getNameImpl() {
                return GUICommands.getConnectDocumentWindowName(
                                iConnectDirection);
            }
        };
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * Returns a Connect-All-Document-Windows command for the specified
     * container manager and connect direction.
     * @param containerManager the container manager to be used by the command.
     * @param connectDirection the connect direction to be used by the command.
     * @return the command.
     * @de.renew.require (containerManager != null)
     * @de.renew.require ConnectWindow.isValidDirection(connectDirection)
     * @de.renew.ensure (returnValue != null)
     * @see ConnectWindow#isValidDirection(Object)
     */
    public static Command getConnectAllDocumentWindows(ContainerManager containerManager,
                                                       Object connectDirection) {
        assert (containerManager != null) : "Precondition violated: (containerManager != null)";
        assert ConnectWindow.isValidDirection(
                        connectDirection) : "Precondition violated: "
                                        + "ConnectWindow.isValidDirection(connectDirection)";

        final ContainerManager iContainerManager = containerManager;
        final Object iConnectDirection = connectDirection;
        Command returnValue = new AllDocumentsCommand(containerManager) {
            @Override
            protected Command createCommand(Document document) {
                return new ConnectDocumentWindow(iContainerManager, document,
                                iConnectDirection);
            }

            @Override
            protected String getNameImpl() {
                return GUICommands.getConnectDocumentWindowName(
                                iConnectDirection);
            }
        };
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }


    /**
     * Determines the name of a Connect-Document-Window command for the
     * specified connect direction.
     * @param connectDirection the connect direction to be used.
     * @return the command name.
     * @de.renew.require ConnectWindow.isValidDirection(connectDirection)
     * @de.renew.ensure (returnValue != null)
     * @see ConnectWindow#isValidDirection(Object)
     */
    public static String getConnectDocumentWindowName(Object connectDirection) {
        return connectDirection.toString();
    }

    /**
     * Connect-Document-Window Command.
     *
     * @author Christian Bracker,
     * <a href="mailto:1bracker@informatik.uni-hamburg.de">
     * 1bracker@informatik.uni-hamburg.de</a>
     */
    private static class ConnectDocumentWindow extends SingleDocumentCommand {

        /**
         * The name of this command.
         */
        public static final String NAME = "Connect-Document-Window";

        /**
         * Creates a new Connect-Document-Window Command using the specified
         * container manager, document and connect direction.
         * @param containerManager the container manager to execute the command on.
         * @param documentToConnect  the document whose window is* to be connected
         * @param connectDirection  the connect direction to be used.
         * @de.renew.require (containerManager != null)
         * @de.renew.require (documentToConnect != null)
         * @de.renew.require ConnectWindow.isValidDirection(connectDirection)
         * @see ConnectWindow#isValidDirection(Object)
         */
        public ConnectDocumentWindow(ContainerManager containerManager,
                                     Document documentToConnect,
                                     Object connectDirection) {
            super(containerManager, documentToConnect);
            assert ConnectWindow.isValidDirection(
                            connectDirection) : "Precondition violated: ConnectWindow.isValidDirection(connectDirection)";

            this._connectDirection = connectDirection;
        }

        /**
         * Returns the connect direction used by this command.
         * @return the connect direction used by this command.
         * @de.renew.ensure ConnectWindow.isValidDirection(returnValue)
         * @see ConnectWindow#isValidDirection(Object)
         */
        public Object getConnectDirection() {
            Object returnValue = this._connectDirection;
            assert ConnectWindow.isValidDirection(
                            returnValue) : "Postcondition violated: ConnectWindow.isValidDirection(returnValue)";

            return returnValue;
        }

        /**
         * @inheritDoc
         */
        @Override
        protected void executeImpl() throws CommandException {
            DocumentContainer container = this.getContainerManager()
                            .getDocumentContainer(this.getDocument());

            if (container == null) {
                throw new CommandException(this, "Document window not open");
            }
            if (!(container instanceof Window)) {
                throw new CommandException(this,
                                "Document container is not a window");
            }

            new ConnectWindow((Window) container, this.getConnectDirection())
                            .execute();
        }

        /**
         * @inheritDoc
         */
        @Override
        protected String getNameImpl() {
            return ConnectDocumentWindow.NAME;
        }

        /**
         * The connect direction used by this command.
         */
        private Object _connectDirection;
    }
}