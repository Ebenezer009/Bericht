package de.renew.netdoc.model.container.managers;

import de.renew.netdoc.model.container.event.CloseRequestEvent;
import de.renew.netdoc.model.document.Document;

import javax.swing.JOptionPane;
import java.io.IOException;

/**
 * This class creates a manager for the  Close-Request container.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public abstract class UserRequestManager extends CloseRequestManager {

    /**
     * Answer given if the user canceled a request.
     */
    public static final Object ANSWER_CANCEL = "cancel";

    /**
     * Answer given if the user denied a request.
     */
    public static final Object ANSWER_NO = "no";

    /**
     * Answer given if the user accepted a request.
     */
    public static final Object ANSWER_YES = "yes";

    /**
     * Creates a new UserRequestContainerManager.
     */
    protected UserRequestManager() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean allowClosingImpl(CloseRequestEvent event) {
        return super.allowClosingImpl(event) && this.requestClosing(event);
    }

    /**
     * Submits a request to the user whether the originator of the specified
     * close event should be allowed to close. The execution of the current
     * thread will be paused until the user replied.
     *
     * @param event the close request event to be verified.
     * @return {@code true}, if the user allowed the originator of the
     * specified event to be closed;<br>
     * {@code false} otherwise.
     * @de.renew.require (event != null)
     */
    protected boolean requestClosing(CloseRequestEvent event) {
        Object originator = event.getOriginator();
        if ((originator instanceof Document) && ((Document) originator).isModified()) {
            Object answer = this.requestSaveDocument((Document) originator);
            if (answer.equals(UserRequestManager.ANSWER_CANCEL)) {
                return false;
            }
            if (answer.equals(UserRequestManager.ANSWER_YES)) {
                try {
                    this.getIOManager().saveDocumentPart((Document) originator);
                } catch (IOException e) {
                    // TODO: notify user
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Submits a request to the user whether the specified document is to be
     * saved before closing it. The execution of the current thread will be
     * paused until the user replied.
     *
     * @param modifiedDocument the document to be saved.
     * @return {@link #ANSWER_YES}, if the user allowed to save the document;<br>
     * {@link #ANSWER_NO}, if the user denied to save the document;<br>
     * {@link #ANSWER_CANCEL}, if the user wants to cancel the close operation.
     * @de.renew.require (modifiedDocument != null)
     * @de.renew.ensure (returnValue != null)
     */
    protected Object requestSaveDocument(Document modifiedDocument) {
        int result = JOptionPane.showOptionDialog(null,
                ">> " + modifiedDocument.getName() + " << has changed! Would you like to save?",
                "Warning: NetDoc not saved", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null,
                // default icon
                new String[]{"Save Document", "Discard Changes", "Cancel"}, "Save Document");
        switch (result) {
            case JOptionPane.YES_OPTION:
                return UserRequestManager.ANSWER_YES;
            case JOptionPane.NO_OPTION:
                return UserRequestManager.ANSWER_NO;
            default:
                return UserRequestManager.ANSWER_CANCEL;
        }
    }
}