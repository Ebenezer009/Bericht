package de.renew.netdoc.gui.swing;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import javax.swing.text.Document;
import javax.swing.text.PlainDocument;
import javax.swing.undo.UndoManager;


/**
 * JTextField providing the possibility to undo/redo the last text changes using
 * defined keystrokes.
 *
 * @author Christian Bracker, <a
 * href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public class JUndoTextField extends JTextField {

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
     * Constructs a new text field.
     * <p>
     * {@link JTextField#JTextField()}
     */
    public JUndoTextField() {
        this(null, null, 0);
    }

    /**
     * Constructs a new text field initialised with the specified number of
     * columns.
     *
     * {@link JTextField#JTextField(int)}
     *
     * @param columns the number of columns used to calculate the preferred
     *                width; if set to zero, the preferred width will be whatever
     *                naturally results from the component implementation.
     *
     */
    public JUndoTextField(int columns) {
        this(null, null, columns);
    }

    /**
     * Constructs a new text field initialised with the specified text.
     *
     *  {@link JTextField#JTextField(String)}
     *
     * @param text the initial text of the new component; or {@code null}.
     *
     */
    public JUndoTextField(String text) {
        this(null, text, 0);
    }

    /**
     * Constructs a new text field initialised with the specified text and
     * number of columns.
     *
     * {@link JTextField#JTextField(String, int)}
     *
     * @param text    the initial text of the new component; or {@code null}.
     * @param columns the number of columns used to calculate the preferred
     *                width; if set to zero, the preferred width will be whatever
     *                naturally results from the component implementation.
     *
     */
    public JUndoTextField(String text, int columns) {
        this(null, text, columns);
    }

    /**
     * Constructs a new text field initialised with the specified storage model,
     * text and number of columns.
     *
     * {@link JTextField#JTextField(Document, String, int)}
     *
     * @param storageModel the text storage to be used; if {@code null}, a
     *                     default will be provided by calling the {@link JTextField#createDefaultModel()} method.
     * @param text         the initial text of the new component; or {@code null}.
     * @param columns      the number of columns used to calculate the preferred
     *                     width; if set to zero, the preferred width will be whatever
     *                     naturally results from the component implementation.
     *
     */
    public JUndoTextField(Document storageModel, String text, int columns) {
        super(storageModel, text, columns);

        if (storageModel == null) {
            super.setDocument(this.createDefaultDocument());
            if (text != null) {
                this.setText(text);
            }
        }

        this.initialise();
    }

    /**
     * Returns the redo-keystroke registered to this text field.
     *
     * @return the redo-keystroke registered to this text field;<br>
     * or {@code null}, if redo via keystroke is disabled.
     */
    public KeyStroke getRedoKeyStroke() {
        return this._redoKeyStroke;
    }

    /**
     * Registers the specified keystroke as the redo-keystroke of this text
     * field and unregisters the current.
     *
     * @param newRedoKeyStroke the new redo-keystroke to register;<br>
     *                         or {@code null}, if redo via keystroke is to be disabled.
     */
    public void setRedoKeyStroke(KeyStroke newRedoKeyStroke) {
        if (this._redoKeyStroke != null) {
            this.getInputMap().remove(this._redoKeyStroke);
        }
        this._redoKeyStroke = newRedoKeyStroke;
        this.getInputMap().put(this._redoKeyStroke,
                JUndoTextField.ACTION_KEY_REDO);
    }

    /**
     * Returns the undo-keystroke registered to this text field.
     *
     * @return the undo-keystroke registered to this text field;<br>
     * or {@code null}, if undo via keystroke is disabled.
     */
    public KeyStroke getUndoKeyStroke() {
        return this._undoKeyStroke;
    }

    /**
     * Registers the specified keystroke as the undo-keystroke of this text
     * field and unregisters the current.
     *
     * @param newUndoKeyStroke the new undo-keystroke to register;<br>
     *                         or {@code null}, if undo via keystroke is to be disabled.
     */
    public void setUndoKeyStroke(KeyStroke newUndoKeyStroke) {
        if (this._undoKeyStroke != null) {
            this.getInputMap().remove(this._undoKeyStroke);
        }
        this._undoKeyStroke = newUndoKeyStroke;
        this.getInputMap().put(this._undoKeyStroke,
                JUndoTextField.ACTION_KEY_UNDO);
    }

    /**
     * <p>
     * Returns the UndoManager used by this text field.
     * </p>
     * <p>
     * To override implement {@link #getUndoManagerImpl()}.
     * </p>
     *
     * @return the UndoManager used by this text field - not {@code null}.
     */
    public final UndoManager getUndoManager() {
        UndoManager returnValue = this.getUndoManagerImpl();
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * Associates this text field with the specified text document.
     *
     * {@link javax.swing.text.JTextComponent#setDocument(Document)}
     *
     * @param document the new document to display/edit.
     * @throws NullPointerException if document was {@code null}.
     *
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
     * Returns the UndoManager used by this text field.
     * </p>
     * <p>
     * Implements {@link #getUndoManager()}.
     * </p>
     *
     * @return the UndoManager used by this text field - not {@code null}.
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
     * Creates the redo action used by this text field.
     *
     * @return the redo action - not {@code null}.
     */
    protected Action createRedoAction() {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent event) {
                JUndoTextField.this.getUndoManager().redo();
            }

            @Override
            public boolean isEnabled() {
                return JUndoTextField.this.getUndoManager().canRedo();
            }

            private static final long serialVersionUID = 1L;
        };
    }

    /**
     * Creates the undo action used by this text field.
     *
     * @return the undo action - not {@code null}.
     */
    protected Action createUndoAction() {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent event) {
                JUndoTextField.this.getUndoManager().undo();
            }

            @Override
            public boolean isEnabled() {
                return JUndoTextField.this.getUndoManager().canUndo();
            }

            private static final long serialVersionUID = 1L;
        };
    }

    /**
     * Initialises this text field.
     */
    private void initialise() {
        this.getDocument().addUndoableEditListener(this.getUndoManager());
        this.setRedoKeyStroke(JUndoTextField.DEFAULT_REDO_KEYSTROKE);
        this.setUndoKeyStroke(JUndoTextField.DEFAULT_UNDO_KEYSTROKE);
        this.getActionMap().put(JUndoTextField.ACTION_KEY_REDO,
                this.createRedoAction());
        this.getActionMap().put(JUndoTextField.ACTION_KEY_UNDO,
                this.createUndoAction());
    }

    /**
     * The redo-keystroke used by this text field.
     */
    private KeyStroke _redoKeyStroke;

    /**
     * The undo-keystroke used by this text field.
     */
    private KeyStroke _undoKeyStroke;

    /**
     * The UndoManager used by this text field.
     */
    private UndoManager _undoManager;

    /**
     * Serial Version UID of this class.
     */
    private static final long serialVersionUID = 1L;
}