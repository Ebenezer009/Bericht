package de.renew.netdoc.gui.swing;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.KeyStroke;
import javax.swing.text.Document;

class JUndoTextFieldTest {

    private JUndoTextField jUndoTextField;

    @BeforeEach
    public void setup() {
        jUndoTextField = new JUndoTextField();
    }

    @Test
    void testSetRedoKeyStroke() {
        //given
        KeyStroke keyStroke = KeyStroke.getKeyStroke('a');
        //when
        jUndoTextField.setRedoKeyStroke(keyStroke);
        //then
        assertEquals(keyStroke, jUndoTextField.getRedoKeyStroke());
    }

    @Test
    void testSetUndoKeyStroke() {
        //given
        KeyStroke keyStroke = KeyStroke.getKeyStroke('a');
        //when
        jUndoTextField.setUndoKeyStroke(keyStroke);
        //then
        assertEquals(keyStroke, jUndoTextField.getUndoKeyStroke());
    }

    @Test
    void testSetDocument() {
        //given
        Document document = jUndoTextField.getDocument();
        Document document2 = jUndoTextField.getDocument();
        //when
        jUndoTextField.setDocument(document);
        jUndoTextField.setDocument(document2);
        Document actualDocument = jUndoTextField.getDocument();
        //then
        assertEquals(document2, actualDocument);
    }
}