package de.renew.netdoc.model.document.parts.linear.tex;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TexCommentPartTest {

    private final String TEXT = "text";
    private TexCommentPart commentPart;

    @BeforeEach
    void setUp() {
        commentPart = new TexCommentPart(TEXT);
    }

    @Test
    void testToString() {
        //when
        String text = commentPart.toString();
        String expected = "% " + TEXT + "\n";
        //then
        assertEquals(expected, text);
    }
}