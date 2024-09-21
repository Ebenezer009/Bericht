package de.renew.netdoc.gui.swing;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;
import javax.swing.undo.UndoManager;


/**
 * JTextArea providing the possibility to undo/redo the last text changes using
 * defined keystrokes.
 *
 * @author Christian Bracker, <a
 *         href="mailto:1bracker@informatik.uni-hamburg.de">
 *         1bracker@informatik.uni-hamburg.de</a>
 *
 * @see javax.swing.undo.UndoManager#getLimit()
 */
public class JUndoTextArea extends JTextArea {

    /**
     * The action-key for the redo action.
     */
    private static final Object ACTION_KEY_REDO = "redo";

    /**
     * The action-key for the undo action.
     */
    private static final Object ACTION_KEY_UNDO = "undo";

    /**
     * The default redo-keystroke.
     */
    private static final KeyStroke DEFAULT_REDO_KEYSTROKE = KeyStroke
                    .getKeyStroke("ctrl Y");

    /**
     * The default undo-keystroke.
     */
    private static final KeyStroke DEFAULT_UNDO_KEYSTROKE = KeyStroke
                    .getKeyStroke("ctrl Z");

    /**
     * Constructs a new text area.
     *
     * @see JTextArea#JTextArea()
     */
    public JUndoTextArea() {
        this(null, null, 0, 0);
    }

    /**
     * Constructs a new text area initialised with the specified storage model.
     *
     * @param storageModel the text storage to be used; if {@code null}, a
     *            default one will be created.
     * @see JTextArea#JTextArea(Document)
     */
    public JUndoTextArea(Document storageModel) {
        this(storageModel, null, 0, 0);
    }

    /**
     * Constructs a new text area initialised with the specified text.
     *
     * @param text the initial text of the new component; or {@code null}.
     * @see JTextArea#JTextArea(String)
     */
    public JUndoTextArea(String text) {
        this(null, text, 0, 0);
    }

    /**
     * Constructs a new text area initialised with the specified number of rows
     * and columns.
     *
     * @param rows the number of rows - not negative.
     * @param columns the number of columns - not negative.
     * @see JTextArea#JTextArea(int,int)
     */
    public JUndoTextArea(int rows, int columns) {
        this(null, null, rows, columns);
    }

    /**
     * Constructs a new text area initialised with the specified text and the
     * specified number of rows and columns.
     *
     * @param text the initial text of the new component; or {@code null}.
     * @param rows the number of rows - not negative.
     * @param columns the number of columns - not negative.
     * @see JTextArea#JTextArea(String,int,int)
     */
    public JUndoTextArea(String text, int rows, int columns) {
        this(null, text, rows, columns);
    }

    /**
     * Constructs a new text area initialised with the specified storage model
     * and text and the specified number of rows and columns.
     *
     * @param storageModel the text storage to be used; if {@code null}, a
     *            default one will be created.
     * @param text the initial text of the new component; or {@code null}.
     * @param rows the number of rows - not negative.
     * @param columns the number of columns - not negative.
     * @see JTextArea#JTextArea(Document,String,int,int)
     */
    public JUndoTextArea(Document storageModel, String text, int rows,
                         int columns) {
        super(storageModel, text, rows, columns);

        if (storageModel == null) {
            super.setDocument(this.createDefaultDocument());
            if (text != null) {
                this.setText(text);
            }
        }

        this.initialise();
    }

    /**
     * Returns the redo-keystroke registered to this text area.
     *
     * @return the redo-keystroke registered to this text area;<br>
     *         or {@code null}, if redo via keystroke is disabled.
     */
    public KeyStroke getRedoKeyStroke() {
        return this._redoKeyStroke;
    }

    /**
     * Registers the specified keystroke as the redo-keystroke of this text area
     * and unregisters the current.
     *
     * @param newRedoKeyStroke the new redo-keystroke to register;<br>
     *            or {@code null}, if redo via keystroke is to be disabled.
     */
    public void setRedoKeyStroke(KeyStroke newRedoKeyStroke) {
        if (this._redoKeyStroke != null) {
            this.getInputMap().remove(this._redoKeyStroke);
        }
        this._redoKeyStroke = newRedoKeyStroke;
        this.getInputMap().put(this._redoKeyStroke,
                        JUndoTextArea.ACTION_KEY_REDO);
    }

    /**
     * Returns the undo-keystroke registered to this text area.
     *
     * @return the undo-keystroke registered to this text area;<br>
     *         or {@code null}, if undo via keystroke is disabled.
     */
    public KeyStroke getUndoKeyStroke() {
        return this._undoKeyStroke;
    }

    /**
     * Registers the specified keystroke as the undo-keystroke of this text area
     * and unregisters the current.
     *
     * @param newUndoKeyStroke the new undo-keystroke to register;<br>
     *            or {@code null}, if undo via keystroke is to be disabled.
     */
    public void setUndoKeyStroke(KeyStroke newUndoKeyStroke) {
        if (this._undoKeyStroke != null) {
            this.getInputMap().remove(this._undoKeyStroke);
        }
        this._undoKeyStroke = newUndoKeyStroke;
        this.getInputMap().put(this._undoKeyStroke,
                        JUndoTextArea.ACTION_KEY_UNDO);
    }

    /**
     * <p>
     * Returns the UndoManager used by this text area.
     * </p>
     * <p>
     * To override implement {@link #getUndoManagerImpl()}.
     * </p>
     *
     * @return the UndoManager used by this text area - not {@code null}.
     */
    public final UndoManager getUndoManager() {
        UndoManager returnValue = this.getUndoManagerImpl();
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * Associates this text area with the specified text document.
     *
     * @param document the new document to display/edit.
     * @throws NullPointerException if document was {@code null}.
     * @see javax.swing.JTextComponent#setDocument(Document)
     */
    @Override
    public void setDocument(Document document) {
        if (this.getDocument() != null) {
            this.getDocument()
                            .removeUndoableEditListener(this.getUndoManager());
            this.getUndoManager().discardAllEdits();
        }
        document.addUndoableEditListener(this.getUndoManager());
        super.setDocument(document);
    }

    /**
     * <p>
     * Returns the UndoManager used by this text area.
     * </p>
     * <p>
     * Implements {@link #getUndoManager()}.
     * </p>
     *
     * @return the UndoManager used by this text area - not {@code null}.
     */
    protected UndoManager getUndoManagerImpl() {
        return (this._undoManager != null) ? this._undoManager
                        : (this._undoManager = new UndoManager());
    }

    /**
     * Creates the default text document.
     *
     * @return the default text document - not {@code null}.
     */
    protected Document createDefaultDocument() {
        return new PlainDocument();
    }

    /**
     * Creates the redo action used by this text area.
     *
     * @return the redo action - not {@code null}.
     */
    protected Action createRedoAction() {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent event) {
                JUndoTextArea.this.getUndoManager().redo();
            }

            @Override
            public boolean isEnabled() {
                return JUndoTextArea.this.getUndoManager().canRedo();
            }

            private static final long serialVersionUID = 1L;
        };
    }

    /**
     * Creates the undo action used by this text area.
     *
     * @return the undo action - not {@code null}.
     */
    protected Action createUndoAction() {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent event) {
                JUndoTextArea.this.getUndoManager().undo();
            }

            @Override
            public boolean isEnabled() {
                return JUndoTextArea.this.getUndoManager().canUndo();
            }

            private static final long serialVersionUID = 1L;
        };
    }

    /**
     * Initialises this text area.
     */
    private void initialise() {
        this.getDocument().addUndoableEditListener(this.getUndoManager());
        this.setRedoKeyStroke(JUndoTextArea.DEFAULT_REDO_KEYSTROKE);
        this.setUndoKeyStroke(JUndoTextArea.DEFAULT_UNDO_KEYSTROKE);
        this.getActionMap().put(JUndoTextArea.ACTION_KEY_REDO,
                        this.createRedoAction());
        this.getActionMap().put(JUndoTextArea.ACTION_KEY_UNDO,
                        this.createUndoAction());
    }

    /**
     * The redo-keystroke used by this text area.
     */
    private KeyStroke _redoKeyStroke;

    /**
     * The undo-keystroke used by this text area.
     */
    private KeyStroke _undoKeyStroke;

    /**
     * The UndoManager used by this text area.
     */
    private UndoManager _undoManager;

    /**
     * Serial Version UID of this class.
     */
    private static final long serialVersionUID = 1L;
}