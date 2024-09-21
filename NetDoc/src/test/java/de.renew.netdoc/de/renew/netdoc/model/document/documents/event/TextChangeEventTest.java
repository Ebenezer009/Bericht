package de.renew.netdoc.model.document.documents.event;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.renew.netdoc.model.document.DocumentPart;
import de.renew.netdoc.model.document.event.TextChangeEvent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.junit.jupiter.api.Test;

public class TextChangeEventTest {
    private final String oldText = "old text";
    private final String newText = "old and new text";
    private TextChangeEvent TCEvent;
    private AutoCloseable closeable;
    @Mock
    private DocumentPart part;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        TCEvent = new TextChangeEvent(part, oldText, newText);
    }
    @AfterEach
    void tearDown() throws Exception{
        closeable.close();
    }

    @Test
    void testGetOriginator() {
        //when
        DocumentPart _testPart = TCEvent.getOriginator();
        //then
        assertEquals(part, _testPart);
    }

    @Test
    void testGetOldText() {
        //when
        String _testOld = TCEvent.getOldText();
        //then
        assertEquals(oldText, _testOld);
    }
}
