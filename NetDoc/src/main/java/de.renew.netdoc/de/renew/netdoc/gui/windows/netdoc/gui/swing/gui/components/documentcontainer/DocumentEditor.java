package de.renew.netdoc.gui.swing.gui.components.documentcontainer;

import de.renew.netdoc.gui.swing.gui.Component;
import de.renew.netdoc.gui.swing.gui.swing.JUndoTextArea;
import de.renew.netdoc.gui.swing.gui.swing.JUndoTextField;
import de.renew.netdoc.model.container.ContainerException;
import de.renew.netdoc.model.container.event.DocumentContainerEvent;
import de.renew.netdoc.model.document.Document;
import de.renew.netdoc.model.document.DocumentPart;
import de.renew.netdoc.model.document.DocumentParts;
import de.renew.netdoc.model.document.event.DocumentChangeEvent;
import de.renew.netdoc.model.document.event.StructureChangeAdapter;
import de.renew.netdoc.model.document.event.StructureChangeEvent;
import de.renew.netdoc.model.document.event.TextChangeEvent;
import de.renew.netdoc.model.document.event.TextChangeListener;
import de.renew.netdoc.model.document.parts.DocumentPartWrapper;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;
import java.awt.BorderLayout;
import java.awt.GridLayout;

/**
 * This class holds the component for the NetDoc document editor.
 *
 * @author Christian Bracker, <a
 * href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public class DocumentEditor extends SingleDocumentContainerComponent {

    /**
     * Creates a new DocumentEditor.
     */
    public DocumentEditor() {
        this(null);
    }

    /**
     * Creates a new DocumentEditor with the specified parent.
     *
     * @param parent the parent of the new editor.
     */
    public DocumentEditor(Component parent) {
        super(parent);

        this._document = null;
        this._mainPanel = new JPanel();
        this._titleField = new JUndoTextField();
        this._authorField = new JUndoTextField();
        this._descriptionArea = new JUndoTextArea();
        this._descriptionArea.setLineWrap(true);

        // update the editor components
        this.setDocument(this._document);

        // create title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BorderLayout(3, 0));
        titlePanel.add(new JLabel("Title:"), BorderLayout.WEST);
        titlePanel.add(this._titleField, BorderLayout.CENTER);

        // create author panel
        JPanel authorPanel = new JPanel();
        authorPanel.setLayout(new BorderLayout(3, 0));
        authorPanel.add(new JLabel("Author:"), BorderLayout.WEST);
        authorPanel.add(this._authorField, BorderLayout.CENTER);

        // create text fields panel
        JPanel fieldPanel = new JPanel();
        fieldPanel.setLayout(new GridLayout(1, 2, 6, 0));
        fieldPanel.add(titlePanel);
        fieldPanel.add(authorPanel);

        // create description area panel
        JScrollPane descriptionAreaPanel = new JScrollPane(this._descriptionArea);
        descriptionAreaPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));

        // layout main panel
        this._mainPanel.setLayout(new BorderLayout(0, 4));
        this._mainPanel.add(fieldPanel, BorderLayout.NORTH);
        this._mainPanel.add(descriptionAreaPanel, BorderLayout.CENTER);
    }

    /**
     * <p>
     * Returns the author text of the document this editor is working on.
     * </p>
     * <p>
     * <b>Programmers Note:</b> This method is finalised to ensure assertions.
     * To modify the behaviour of this method override the corresponding
     * internal method {@link #getAuthorImpl()}.
     * </p>
     *
     * @return the author text of the document this editor is working on.
     * @de.renew.ensure (returnValue != null)
     */
    public final String getAuthor() {
        String returnValue = this.getAuthorImpl();
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * <p>
     * Returns the title text of the document this editor is working on.
     * </p>
     * <p>
     * <b>Programmers Note:</b> This method is finalised to ensure assertions.
     * To modify the behaviour of this method override the corresponding
     * internal method {@link #getTitleImpl()}.
     * </p>
     *
     * @return the title text of the document this editor is working on.
     * @de.renew.ensure (returnValue != null)
     */
    public final String getTitle() {
        String returnValue = this.getTitleImpl();
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * <p>
     * Returns the description of the document this editor is working on.
     * </p>
     * <p>
     * <b>Programmers Note:</b> This method is finalised to ensure assertions.
     * To modify the behaviour of this method override the corresponding
     * internal method {@link #getDescriptionImpl()}.
     * </p>
     *
     * @return the main text of the document this editor is working on.
     * @de.renew.ensure (returnValue != null)
     */
    public final String getDescription() {
        String returnValue = this.getDescriptionImpl();
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * <p>
     * Sets the author text of the document this editor is working on to the
     * specified text.
     * </p>
     * <p>
     * <b>Programmers Note:</b> This method is finalised to ensure assertions.
     * To modify the behaviour of this method override the corresponding
     * internal method {@link #setAuthorImpl(String)}.
     * </p>
     *
     * @param authorText the new author text.
     * @de.renew.require (authorText != null)
     */
    public final void setAuthor(String authorText) {
        assert (authorText != null) : "Precondition violated: (authorText != null)";

        this.setAuthorImpl(authorText);
    }

    /**
     * <p>
     * Sets the title text of the document this editor is working on to the
     * specified text.
     * </p>
     * <p>
     * <b>Programmers Note:</b> This method is finalised to ensure assertions.
     * To modify the behaviour of this method override the corresponding
     * internal method {@link #setTitleImpl(String)}.
     * </p>
     *
     * @param titleText the new title text.
     * @de.renew.require (titleText != null)
     */
    public final void setTitle(String titleText) {
        assert (titleText != null) : "Precondition violated: (titleText != null)";

        this.setTitleImpl(titleText);
    }

    /**
     * <p>
     * Sets the description of the document this editor is working on to the
     * specified description.
     * </p>
     * <p>
     * <b>Programmers Note:</b> This method is finalised to ensure assertions.
     * To modify the behaviour of this method override the corresponding
     * internal method {@link #setDescriptionImpl(String)}.
     * </p>
     *
     * @param description the new description.
     * @de.renew.require (description != null)
     */
    public final void setDescription(String description) {
        assert (description != null) : "Precondition violated: (description != null)";

        this.setDescriptionImpl(description);
    }

    /**
     * Returns the author text of the document this editor is working on.
     *
     * @return the author text of the document this editor is working on.
     * @de.renew.ensure (returnValue != null)
     */
    protected String getAuthorImpl() {
        return this.getAuthorTextField().getText();
    }

    /**
     * Returns the title text of the document this editor is working on.
     *
     * @return the title text of the document this editor is working on.
     * @de.renew.ensure (returnValue != null)
     */
    protected String getTitleImpl() {
        return this.getTitleTextField().getText();
    }

    /**
     * Returns the description of the document this editor is working on.
     *
     * @return the description of the document this editor is working on.
     * @de.renew.ensure (returnValue != null)
     */
    protected String getDescriptionImpl() {
        return this.getDescriptionArea().getText();
    }

    /**
     * Sets the author text of the document this editor is working on to the
     * specified text.
     *
     * @param authorText the new author text.
     * @de.renew.require (authorText != null)
     */
    protected void setAuthorImpl(String authorText) {
        this.getAuthorTextField().setText(authorText);
    }

    /**
     * Sets the title text of the document this editor is working on to the
     * specified text.
     *
     * @param titleText the new title text.
     * @de.renew.require (titleText != null)
     */
    protected void setTitleImpl(String titleText) {
        this.getAuthorTextField().setText(titleText);
    }

    /**
     * Sets the description of the document this editor is working on to the
     * specified description.
     *
     * @param description the new description.
     * @de.renew.require (description != null)
     */
    protected void setDescriptionImpl(String description) {
        this.getDescriptionArea().setText(description);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Document getDocument() {
        return this._document;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void closeDocumentImpl(Document documentToClose, boolean forceClose) {
        if (documentToClose == this.getDocument()) {
            if (forceClose) {
                this.setDocument(null);
                this.fireDocumentContainerEvent(
                        new DocumentContainerEvent(DocumentContainerEvent.DOCUMENT_CLOSED, this, documentToClose));
            } else {
                this.fireDocumentContainerEvent(
                        new DocumentContainerEvent(DocumentContainerEvent.DOCUMENT_CLOSING, this, documentToClose));
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void openDocumentImpl(Document documentToOpen) throws ContainerException {
        if (this.getDocument() != null) {
            this.closeDocument(this.getDocument(), false);
            if (this.getDocument() != null) {
                throw new ContainerException("Unable to close current document");
            }
        }
        this.setDocument(documentToOpen);
        this.fireDocumentContainerEvent(
                new DocumentContainerEvent(DocumentContainerEvent.DOCUMENT_OPENED, this, documentToOpen));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected java.awt.Component getJavaComponentImpl() {
        return this.getMainPanel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void focusOnDocumentImpl(Document document) {
        super.focusOnDocumentImpl(document);
        this.getDescriptionArea().requestFocusInWindow();
    }

    /**
     * Sets the document controlled by this editor.
     *
     * @param document the new document to be edited;<br>
     * or {@code null}, if the editor is to be disabled.
     */
    protected void setDocument(Document document) {
        if (this._document != null) {
            this.removeListeners(this._document);
            this.removeEditorParts(this._document);
        }
        this.updateComponents(document);
        if (document != null) {
            this.addEditorParts(document);
            this.addListeners(document);
        }
        this._document = document;
    }

    /**
     * Returns the main panel of this editor.
     *
     * @return the main panel of this editor.
     * @de.renew.ensure (returnValue != null)
     */
    protected JPanel getMainPanel() {
        return this._mainPanel;
    }

    /**
     * Returns the author text field of this editor.
     *
     * @return the author text field of this editor.
     * @de.renew.ensure (returnValue != null)
     */
    protected JTextField getAuthorTextField() {
        return this._authorField;
    }

    /**
     * Returns the title text field of this editor.
     *
     * @return the title text field of this editor.
     * @de.renew.ensure (returnValue != null)
     */
    protected JTextField getTitleTextField() {
        return this._titleField;
    }

    /**
     * Returns the description area of this editor.
     *
     * @return the description area of this editor.
     * @de.renew.ensure (returnValue != null)
     */
    protected JTextArea getDescriptionArea() {
        return this._descriptionArea;
    }

    /**
     * Adds the editor document parts to the specified document.
     *
     * @param document the document to process.
     * @de.renew.require (document != null)
     */
    protected void addEditorParts(Document document) {
        document.setPart(this.new TextDocumentPart(document.getPart(DocumentParts.NAME_NETDOC_AUTHOR),
                this.getAuthorTextField()));
        document.setPart(
                this.new TextDocumentPart(document.getPart(DocumentParts.NAME_NETDOC_TITLE), this.getTitleTextField()));
        document.setPart(this.new TextDocumentPart(document.getPart(DocumentParts.NAME_NETDOC_DESCRIPTION),
                this.getDescriptionArea()));
    }

    /**
     * Removes the editor document parts from the specified document.
     *
     * @param document the document to process.
     * @de.renew.require (document != null)
     */
    protected void removeEditorParts(Document document) {
        document.setPart(((TextDocumentPart) document.getPart(
                DocumentParts.NAME_NETDOC_AUTHOR)).getWrappedPart());
        document.setPart(
                ((TextDocumentPart) document.getPart(DocumentParts.NAME_NETDOC_TITLE)).getWrappedPart());
        document.setPart(((TextDocumentPart) document.getPart(
                DocumentParts.NAME_NETDOC_DESCRIPTION)).getWrappedPart());
    }

    /**
     * Adds the text property change listeners given by the specified document
     * to the text components of this editor.
     *
     * @param document the document to process.
     * @de.renew.require (document != null)
     */
    protected void addListeners(Document document) {
        this.getAuthorTextField().getDocument()
                .addDocumentListener((DocumentListener) document.getPart(DocumentParts.NAME_NETDOC_AUTHOR));
        this.getTitleTextField().getDocument()
                .addDocumentListener((DocumentListener) document.getPart(DocumentParts.NAME_NETDOC_TITLE));
        this.getDescriptionArea().getDocument()
                .addDocumentListener((DocumentListener) document.getPart(DocumentParts.NAME_NETDOC_DESCRIPTION));

        document.addStructureChangeListener(this.getChangeListener());
        document.addTextChangeListener(this.getChangeListener());
    }

    /**
     * Removes the text property change listeners given by the specified
     * document from the text components used by this editor.
     *
     * @param document the document to be used.
     * @de.renew.require (document != null)
     */
    protected void removeListeners(Document document) {
        document.removeStructureChangeListener(this.getChangeListener());
        document.removeTextChangeListener(this.getChangeListener());

        this.getAuthorTextField().getDocument()
                .removeDocumentListener((DocumentListener) document.getPart(DocumentParts.NAME_NETDOC_AUTHOR));
        this.getTitleTextField().getDocument()
                .removeDocumentListener((DocumentListener) document.getPart(DocumentParts.NAME_NETDOC_TITLE));
        this.getDescriptionArea().getDocument()
                .removeDocumentListener((DocumentListener) document.getPart(DocumentParts.NAME_NETDOC_DESCRIPTION));
    }

    /**
     * Updates the specified components using the specified document.
     *
     * @param document the document to be used;<br>
     * or {@code null}, if the components are to be disabled.
     */
    protected void updateComponents(Document document) {
        if (document != null) {
            this.getTitleTextField().setEnabled(true);
            this.getAuthorTextField().setEnabled(true);
            this.getDescriptionArea().setEnabled(true);
            this.getTitleTextField().setText(document.getPart(DocumentParts.NAME_NETDOC_TITLE).getText());
            this.getAuthorTextField().setText(document.getPart(DocumentParts.NAME_NETDOC_AUTHOR).getText());
            this.getDescriptionArea().setText(document.getPart(DocumentParts.NAME_NETDOC_DESCRIPTION).getText());
        } else {
            this.getTitleTextField().setText("");
            this.getAuthorTextField().setText("");
            this.getDescriptionArea().setText("");
            this.getTitleTextField().setEnabled(false);
            this.getAuthorTextField().setEnabled(false);
            this.getDescriptionArea().setEnabled(false);
        }
    }

    /**
     * Returns the document text and structure change listener used by this
     * component.
     *
     * @return the document text and structure change listener used by this
     * component.
     * @de.renew.ensure (returnValue != null)
     */
    protected ChangeListener getChangeListener() {
        if (this._changeListener == null) {
            this._changeListener = new ChangeListener();
        }
        return this._changeListener;
    }

    /**
     * The document this editor is working on.
     */
    private Document _document;

    /**
     * The main panel of this editor.
     */
    private JPanel _mainPanel;

    /**
     * The author text field used by this editor.
     */
    private JTextField _authorField;

    /**
     * The title text field used by this editor.
     */
    private JTextField _titleField;

    /**
     * The description area used by this editor.
     */
    private JTextArea _descriptionArea;

    /**
     * The document text and structure change listener used by this component.
     */
    private ChangeListener _changeListener;

    /**
     * The document part used by this editor replacing the original part while
     * editing.
     */
    protected class TextDocumentPart extends DocumentPartWrapper implements DocumentListener {

        /**
         * Creates a new document part wrapping the specified document part.
         *
         * @param partToWrap the document part to be wrapped by the new part.
         * @param associatedComponent the text component associated with the
         * new part.
         * @de.renew.require (partToWrap != null)
         * @de.renew.require (associatedComponent != null)
         */
        public TextDocumentPart(DocumentPart partToWrap, JTextComponent associatedComponent) {
            super(partToWrap);
            assert (associatedComponent != null) : "Precondition violated: (associatedComponent != null)";

            this._associatedComponent = associatedComponent;
        }

        /**
         * Returns the text component associated with this part.
         *
         * @return the text component associated with this part.
         * @de.renew.ensure (returnValue != null)
         */
        public JTextComponent getAssociatedComponent() {
            JTextComponent returnValue = this._associatedComponent;
            assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

            return returnValue;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void changedUpdate(DocumentEvent event) {
            this.update(event.getDocument());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void insertUpdate(DocumentEvent event) {
            this.update(event.getDocument());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void removeUpdate(DocumentEvent event) {
            this.update(event.getDocument());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void setText(String text) {
            this.getAssociatedComponent().setText(text);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected Object cloneImpl() {
            return new TextDocumentPart(this.getWrappedPart(), this.getAssociatedComponent());
        }

        /**
         * Updates the NetDoc document part using the text of the specified
         * document.
         *
         * @param document the document specifying the new text.
         */
        protected void update(javax.swing.text.Document document) {
            try {
                this.getWrappedPart().setText(document.getText(0, document.getLength()));
            } catch (Exception e) {
            }
        }

        /**
         * The text component associated with this part.
         */
        private JTextComponent _associatedComponent;
    }

    /**
     * Observer listening to structure and text change events of the document
     * this editor is working on. Notifies all document change listener of this
     * editor about document changes.
     */
    protected class ChangeListener extends StructureChangeAdapter implements TextChangeListener {

        /**
         * Creates a new ChangeListener.
         */
        public ChangeListener() {
        }

        /**
         * <p>
         * Invoked when the text of a document part has changed.
         * </p>
         * <p>
         * To override implement {@link #textChangedImpl(TextChangeEvent)}.
         * </p>
         *
         * @param event the corresponding change event.
         * @de.renew.require (event != null)
         */
        @Override
        public final void textChanged(TextChangeEvent event) {
            assert (event != null) : "Precondition violated: (event != null)";

            this.textChangedImpl(event);
        }

        /**
         * Invoked when the text of a document part has changed.
         *
         * @param event the corresponding change event.
         * @de.renew.require (event != null)
         */
        protected void textChangedImpl(TextChangeEvent event) {
            DocumentEditor.this.fireDocumentChangeEvent(
                    new DocumentChangeEvent(DocumentEditor.this.getDocument(), event));
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected void partAddedImpl(StructureChangeEvent event) {
            DocumentEditor.this.fireDocumentChangeEvent(
                    new DocumentChangeEvent(DocumentEditor.this.getDocument(), event));
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected void partRemovedImpl(StructureChangeEvent event) {
            DocumentEditor.this.fireDocumentChangeEvent(
                    new DocumentChangeEvent(DocumentEditor.this.getDocument(), event));
        }
    }
}