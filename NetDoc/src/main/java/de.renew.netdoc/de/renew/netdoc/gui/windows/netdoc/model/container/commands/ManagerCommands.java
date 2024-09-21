package de.renew.netdoc.model.container.commands;

import de.renew.netdoc.model.command.Command;
import de.renew.netdoc.model.command.CommandException;
import de.renew.netdoc.model.command.exceptions.CommandCancelException;
import de.renew.netdoc.model.container.ContainerException;
import de.renew.netdoc.model.container.ContainerManager;
import de.renew.netdoc.model.container.commands.manager.AllDocumentsCommand;
import de.renew.netdoc.model.container.commands.manager.AllTargetsCommand;
import de.renew.netdoc.model.container.commands.manager.CurrentDocumentCommand;
import de.renew.netdoc.model.container.commands.manager.CurrentTargetCommand;
import de.renew.netdoc.model.container.commands.manager.SingleDocumentCommand;
import de.renew.netdoc.model.container.commands.manager.SingleTargetCommand;
import de.renew.netdoc.model.doctarget.DocTarget;
import de.renew.netdoc.model.document.Document;

/**
 * This Factory is providing ContainerManager commands.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public abstract class ManagerCommands {
    /**
     * empty constructor
     */
    public ManagerCommands(){

    }

    /**
     * Name of the Close-Document command.
     */
    private static final String NAME_CLOSE_DOCUMENT = "Close-Document";

    /**
     * Name of the Open-Document command.
     */
    private static final String NAME_OPEN_DOCUMENT = "Open-Document";

    /**
     * Name of the Open-Document command.
     */
    private static final String NAME_OPEN_TARGET = "Open-Document-by-Target";

    /**
     * Name of the Save-Document command.
     */
    private static final String NAME_SAVE_DOCUMENT = "Save-Document";

    /**
     * Name of the Close-Current-Document command.
     */
    private static final String NAME_CLOSE_CURRENT_DOCUMENT = "Close Current Document";

    /**
     * Name of the Open-Current-Document command.
     */
    private static final String NAME_OPEN_CURRENT_DOCUMENT = "Open Current Document";

    /**
     * Name of the Save-Current-Document command.
     */
    private static final String NAME_SAVE_CURRENT_DOCUMENT = "Save Current Document";

    /**
     * Name of the Close-All-Documents command.
     */
    private static final String NAME_CLOSE_ALL_DOCUMENTS = "Close All Documents";

    /**
     * Name of the Open-All-Documents command.
     */
    private static final String NAME_OPEN_ALL_DOCUMENTS = "Open All Documents";

    /**
     * Name of the Save-All-Documents command.
     */
    private static final String NAME_SAVE_ALL_DOCUMENTS = "Save All Documents";

    /**
     * Returns a Close-Current-Document command for the specified container
     * manager.
     *
     * @param containerManager the container manager to be used by the command.
     * @return the command.
     * @de.renew.require (containerManager != null)
     * @de.renew.ensure (returnValue != null)
     */
    public static Command getCloseCurrentDocument(ContainerManager containerManager) {
        assert (containerManager != null) : "Precondition violated: (containerManager != null)";

        final ContainerManager iContainerManager = containerManager;
        Command returnValue = new CurrentDocumentCommand(containerManager) {
            @Override
            protected Command createCommand(Document document) {
                return new CloseDocument(iContainerManager, document);
            }

            @Override
            protected String getNameImpl() {
                return ManagerCommands.NAME_CLOSE_CURRENT_DOCUMENT;
            }
        };
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * Returns an Open-Current-Document command for the specified container
     * manager.
     *
     * @param containerManager the container manager to be used by the command.
     * @return the command.
     * @de.renew.require (containerManager != null)
     * @de.renew.ensure (returnValue != null)
     */
    public static Command getOpenCurrentDocument(ContainerManager containerManager) {
        assert (containerManager != null) : "Precondition violated: (containerManager != null)";

        final ContainerManager iContainerManager = containerManager;
        Command returnValue = new CurrentTargetCommand(containerManager) {
            @Override
            protected Command createCommand(DocTarget target) {
                return new OpenDocumentByTarget(iContainerManager, target);
            }

            @Override
            protected String getNameImpl() {
                return ManagerCommands.NAME_OPEN_CURRENT_DOCUMENT;
            }
        };
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * Returns a Save-Current-Document command for the specified container
     * manager.
     *
     * @param containerManager the container manager to be used by the command.
     * @return the command.
     * @de.renew.require (containerManager != null)
     * @de.renew.ensure (returnValue != null)
     */
    public static Command getSaveCurrentDocument(ContainerManager containerManager) {
        final ContainerManager iContainerManager = containerManager;
        Command returnValue = new CurrentDocumentCommand(containerManager) {
            @Override
            protected Command createCommand(Document document) {
                return new SaveDocument(iContainerManager, document);
            }

            @Override
            protected String getNameImpl() {
                return ManagerCommands.NAME_SAVE_CURRENT_DOCUMENT;
            }
        };
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * Returns a Close-All-Documents command for the specified container
     * manager.
     *
     * @param containerManager the container manager to be used by the command.
     * @return the command.
     * @de.renew.require (containerManager != null)
     * @de.renew.ensure (returnValue != null)
     */
    public static Command getCloseAllDocuments(ContainerManager containerManager) {
        assert (containerManager != null) : "Precondition violated: (containerManager != null)";

        final ContainerManager iContainerManager = containerManager;
        Command returnValue = new AllDocumentsCommand(containerManager) {
            @Override
            protected Command createCommand(Document document) {
                return new CloseDocument(iContainerManager, document);
            }

            @Override
            protected String getNameImpl() {
                return ManagerCommands.NAME_CLOSE_ALL_DOCUMENTS;
            }
        };
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * Returns an Open-All-Documents command for the specified container
     * manager.
     *
     * @param containerManager the container manager to be used by the command.
     * @return the command.
     * @de.renew.require (containerManager != null)
     * @de.renew.ensure (returnValue != null)
     */
    public static Command getOpenAllDocuments(ContainerManager containerManager) {
        assert (containerManager != null) : "Precondition violated: (containerManager != null)";

        final ContainerManager iContainerManager = containerManager;
        Command returnValue = new AllTargetsCommand(containerManager) {
            @Override
            protected Command createCommand(DocTarget target) {
                return new OpenDocumentByTarget(iContainerManager, target);
            }

            @Override
            protected String getNameImpl() {
                return ManagerCommands.NAME_OPEN_ALL_DOCUMENTS;
            }
        };
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * Returns a Save-All-Documents command for the specified container
     * manager.
     *
     * @param containerManager the container manager to be used by the new
     * command.
     * @return the command.
     * @de.renew.require (containerManager != null)
     * @de.renew.ensure (returnValue != null)
     */
    public static Command getSaveAllDocuments(ContainerManager containerManager) {
        final ContainerManager iContainerManager = containerManager;
        Command returnValue = new AllDocumentsCommand(containerManager) {
            @Override
            protected Command createCommand(Document document) {
                return new SaveDocument(iContainerManager, document);
            }

            @Override
            protected String getNameImpl() {
                return ManagerCommands.NAME_SAVE_ALL_DOCUMENTS;
            }
        };
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * Close-Document Command.
     */
    public static class CloseDocument extends SingleDocumentCommand {

        /**
         * Creates a new Close-Document Command using the specified container
         * manager and document.
         *
         * @param containerManager the container manager to execute the command on.
         * @param documentToClose the document to be closed.
         * @de.renew.require (containerManager != null)
         * @de.renew.require (documentToClose != null)
         */
        public CloseDocument(ContainerManager containerManager, Document documentToClose) {
            super(containerManager, documentToClose);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected void executeImpl() throws CommandException {
            try {
                this.getContainerManager().closeDocument(this.getDocument(), false);
            } catch (ContainerException e) {
                throw new CommandException(this, e);
            }

            if (this.getContainerManager().getDocumentContainer(this.getDocument()) != null) {
                throw new CommandCancelException(this);
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected String getNameImpl() {
            return ManagerCommands.NAME_CLOSE_DOCUMENT;
        }
    }

    /**
     * Open-Document Command.
     */
    public static class OpenDocument extends SingleDocumentCommand {

        /**
         * Creates a new Open-Document Command using the specified container
         * manager and document.
         *
         * @param containerManager the container manager to execute the command on.
         * @param documentToOpen the document to be opened.
         * @de.renew.require (containerManager != null)
         * @de.renew.require (documentToOpen != null)
         */
        public OpenDocument(ContainerManager containerManager, Document documentToOpen) {
            super(containerManager, documentToOpen);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected void executeImpl() throws CommandException {
            try {
                this.getContainerManager().openDocument(this.getDocument());
            } catch (ContainerException e) {
                throw new CommandException(this, e);
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected String getNameImpl() {
            return ManagerCommands.NAME_OPEN_DOCUMENT;
        }
    }

    /**
     * Open-Document Command.
     */
    public static class OpenDocumentByTarget extends SingleTargetCommand {

        /**
         * Creates a new Open-Document Command using the specified container
         * manager and document.
         *
         * @param containerManager the container manager to execute the
         * command on.
         * @param docTarget the documentation target specifying the document
         * to be opened.
         * @de.renew.require (containerManager != null)
         * @de.renew.require (docTarget != null)
         */
        public OpenDocumentByTarget(ContainerManager containerManager, DocTarget docTarget) {
            super(containerManager, docTarget);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected void executeImpl() throws CommandException {
            try {
                this.getContainerManager().openDocument(this.getTarget());
            } catch (ContainerException e) {
                throw new CommandException(this, e);
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected String getNameImpl() {
            return ManagerCommands.NAME_OPEN_TARGET;
        }
    }

    /**
     * Save-Document Command.
     */
    public static class SaveDocument extends SingleDocumentCommand {

        /**
         * Creates a new Save-Document Command using the specified container
         * manager and document.
         *
         * @param containerManager the container manager to execute the command on.
         * @param documentToSave the document to be saved.
         * @de.renew.require (containerManager != null)
         * @de.renew.require (documentToSave != null)
         */
        public SaveDocument(ContainerManager containerManager, Document documentToSave) {
            super(containerManager, documentToSave);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected void executeImpl() throws CommandException {
            try {
                this.getContainerManager().getIOManager().saveDocumentPart(this.getDocument());
            } catch (Exception e) {
                throw new CommandException(this, e);
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected String getNameImpl() {
            return ManagerCommands.NAME_SAVE_DOCUMENT;
        }
    }
}