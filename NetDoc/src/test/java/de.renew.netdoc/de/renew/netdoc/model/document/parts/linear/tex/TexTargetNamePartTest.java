package de.renew.netdoc.model.document.parts.linear.tex;

import de.renew.netdoc.model.document.DocumentPart;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class TexTargetNamePartTest {

    private static final String NAME = "name";
    private TexTargetNamePart namePart;
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private DocumentPart part;
    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        namePart = new TexTargetNamePart(NAME, part);
        setupMock();
    }

    @AfterEach
    void tearDown() throws  Exception{
        closeable.close();
    }

    private void setupMock() {
        when(part.getTarget().getName()).thenReturn(NAME);
    }

    @Test
    void testGetDocumentPart() {
        //when
        DocumentPart actualPart = namePart.getDocumentPart();
        //then
        assertEquals(part, actualPart);
    }

    @Test
    void testGetText() {
        //when
        String text = namePart.getText();
        //then
        assertEquals(NAME, text);
    }

    @Test
    void testSetText() {
        //then
        assertThrows(IllegalArgumentException.class, () -> namePart.setText(NAME));
    }
}