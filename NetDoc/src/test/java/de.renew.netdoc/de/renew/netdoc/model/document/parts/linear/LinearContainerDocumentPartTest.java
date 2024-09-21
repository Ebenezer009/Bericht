package de.renew.netdoc.model.document.parts.linear;

import de.renew.netdoc.model.document.DocumentPart;
import de.renew.netdoc.model.document.event.StructureChangeListener;
import de.renew.netdoc.model.document.event.TextChangeListener;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LinearContainerDocumentPartTest {
    private static final String PART_NAME = "part";
    private LinearContainerDocumentPart document;
    private DocumentPart part;
    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        document = new LinearContainerDocumentPart("1");
        part = mockPart(PART_NAME);
    }

    @AfterEach
    void teardown() throws Exception {
        closeable.close();
    }

    @Test
    void testConstructor() {
        //then
        assertNotNull(document.getInternalPartList());
        assertEquals("1", document.getNameImpl());
    }

    @Test
    void testGetPartCount() {
        //when
        document.addPartAt(0, part);
        document.addPartAt(1, part);
        //then
        assertEquals(2, document.getPartCount());
    }

    @Test
    void testAddPartAt() {
        //when
        document.addPartAt(0, part);
        document.addPartAt(1, mockPart(PART_NAME));
        //then
        assertEquals(part, document.getPartAt(0));
        assertNotEquals(part, document.getPartAt(1));
        assertTrue(document.isModified());
    }

    @Test
    void testRemovePartAt() {
        //when
        document.addPartAt(0, part);
        document.removePartAt(0);
        //then
        assertEquals(0, document.getPartCount());
        assertTrue(document.isModified());
    }

    @Test
    void testAppendPart() {
        //when
        document.addPartAtImpl(0, part);
        document.addPartAtImpl(0, mockPart("part2"));
        DocumentPart part3 = mockPart("part3");
        document.appendPartImpl(part3);
        //then
        assertEquals(part3, document.getPartAtImpl(2));
    }

    @Test
    void testIndexOfPart() {
        //when
        document.addPartAt(0, part);
        //then
        assertEquals(0, document.indexOfPart(PART_NAME));
    }

    @Test
    void testSetPartAt() {
        //when
        document.addPartAt(0, part);
        DocumentPart mock = mockPart("part2");
        document.setPartAt(0, mock);
        //then
        assertEquals(mock, document.getPartAt(0));
        assertTrue(document.isModified());
    }

    @Test
    void testSetPartList() {
        //when
        List<DocumentPart> expected = Arrays.asList(part, mockPart("part2"));
        for (int i = 0; i < expected.size(); i++) {
            document.addPartAt(i, expected.get(i));
        }
        //then
        assertEquals(expected, document.getParts());
        assertTrue(document.isModified());
    }

    private DocumentPart mockPart(String name) {
        DocumentPart part = Mockito.mock(DocumentPart.class);
        Mockito.when(part.getStructureChangeListeners()).thenReturn((Enumeration<StructureChangeListener>) Mockito.mock(Enumeration.class));
        Mockito.when(part.getTextChangeListeners()).thenReturn((Enumeration<TextChangeListener>) Mockito.mock(Enumeration.class));
        Mockito.when(part.getName()).thenReturn(name);

        return part;
    }

}
