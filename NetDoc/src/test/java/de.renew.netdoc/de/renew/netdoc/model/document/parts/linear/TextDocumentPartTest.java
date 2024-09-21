package de.renew.netdoc.model.document.parts.linear;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class TextDocumentPartTest {
    private static final String NAME = "name";
    private static final String TEXT = "text";
    private TextDocumentPart part;

    @BeforeEach
    void setup() {
        part = new TextDocumentPart(NAME, TEXT);
    }

    @Test
    void testSetText() {
        //when
        String expectedText = "new text";
        part.setText(expectedText);
        String text = part.getText();
        //then
        assertEquals(expectedText, text);
    }

    @Test
    void testSetTextWithSameText() {
        //when
        part.setText(TEXT);
        boolean isDirty = part.isModified();
        //then
        assertFalse(isDirty);
    }

}