package de.renew.netdoc.model.document.parts.linear.tex;

import de.renew.netdoc.model.document.DocumentPart;
import de.renew.netdoc.model.document.event.StructureChangeListener;
import de.renew.netdoc.model.document.event.TextChangeListener;
import org.junit.jupiter.api.Test;

import java.util.Enumeration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TexBlockDefinitionPartTest {

    private static final String NAME = "name";
    private static final String TEXT = "text";

    @Test
    void testToStringWithText() {
        //given
        TexBlockDefinitionPart part = new TexBlockDefinitionPart(NAME, TEXT);
        //when
        String actual = part.toString();
        //then
        assertEquals("\\begin{name}\ntext\n\\end{name}\n", actual);
    }

    @Test
    void testToStringWithoutText() {
        //given
        TexBlockDefinitionPart part = new TexBlockDefinitionPart(NAME);
        //when
        String actual = part.toString();
        //then
        assertEquals("\\begin{name}\n\\end{name}\n", actual);
    }

    @Test
    void testToStringWithParts() {
        //given
        TexBlockDefinitionPart part = new TexBlockDefinitionPart(NAME);
        part.appendPart(mockPart());
        //when
        String actual = part.toString();
        //then
        assertEquals("\\begin{name}\nname\\end{name}\n", actual);
    }

    private DocumentPart mockPart() {
        DocumentPart part = mock(DocumentPart.class);
        // avoid NullPointerException
        when(part.getStructureChangeListeners()).thenReturn((Enumeration<StructureChangeListener>) mock(Enumeration.class));
        when(part.getTextChangeListeners()).thenReturn((Enumeration<TextChangeListener>) mock(Enumeration.class));
        when(part.toString()).thenReturn(NAME);

        return part;
    }
}