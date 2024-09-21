package de.renew.netdoc.gui.swing;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.KeyStroke;
import javax.swing.text.Document;

class JUndoTextAreaTest {

    private JUndoTextArea jUndoTextArea;

    @BeforeEach
    public void setup() {
        jUndoTextArea = new JUndoTextArea();
    }

    @Test
    void testSetRedoKeyStroke() {
        //given
        KeyStroke keyStroke = KeyStroke.getKeyStroke('a');
        //when
        jUndoTextArea.setRedoKeyStroke(keyStroke);
        //then
        assertEquals(keyStroke, jUndoTextArea.getRedoKeyStroke());
    }

    @Test
    void testSetUndoKeyStroke() {
        //given
        KeyStroke keyStroke = KeyStroke.getKeyStroke('a');
        //when
        jUndoTextArea.setUndoKeyStroke(keyStroke);
        //then
        assertEquals(keyStroke, jUndoTextArea.getUndoKeyStroke());
    }

    @Test
    void testSetDocument() {
        //given
        Document document = jUndoTextArea.getDocument();
        Document document2 = jUndoTextArea.getDocument();
        //when
        jUndoTextArea.setDocument(document);
        jUndoTextArea.setDocument(document2);
        Document actualDocument = jUndoTextArea.getDocument();
        //then
        assertEquals(document2, actualDocument);

    }
}