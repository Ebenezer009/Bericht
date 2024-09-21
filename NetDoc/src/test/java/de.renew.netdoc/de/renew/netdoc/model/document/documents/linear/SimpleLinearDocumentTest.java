package de.renew.netdoc.model.document.documents.linear;

import de.renew.netdoc.model.doctarget.DocTarget;
import de.renew.netdoc.model.document.DocumentPart;
import de.renew.netdoc.model.document.event.DocumentChangeListener;
import de.renew.netdoc.model.document.event.StructureChangeListener;
import de.renew.netdoc.model.document.event.TextChangeListener;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.Enumeration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SimpleLinearDocumentTest {

    private static final String PART_NAME = "part";
    private SimpleLinearDocument document;
    private DocumentPart part;
    @Mock
    private DocTarget target;
    private StructureChangeListener[] structureChangeListeners;
    private TextChangeListener[] textChangeListeners;
    private DocumentChangeListener[] documentChangeListeners;
    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        document = new SimpleLinearDocument(target);
        setupListeners();
        part = mockPart(PART_NAME);
    }

    @AfterEach
    void teardown() throws Exception {
        closeable.close();
    }

    @Test
    void testConstructor() {
        //when
        List<DocumentPart> internalParts = document.getInternalPartList();
        //then
        assertNotNull(internalParts);
    }

    @Test
    void testConstructorWithVersion() {
        //given
        SimpleLinearDocument docWithVersion = new SimpleLinearDocument(target, "1");
        //when
        List<DocumentPart> internalPartsWithVersion = document.getInternalPartList();
        String version = docWithVersion.getVersion();
        //then
        assertNotNull(internalPartsWithVersion);
        assertEquals("1", version);
    }

    @Test
    void testGetPartCount() {
        //when
        document.addPartAt(0, part);
        document.addPartAt(1, part);
        int partCount = document.getPartCount();
        //then
        assertEquals(2, partCount);
    }

    @Test
    void testAddPartAt() {
        assertEquals(document.getPartCountImpl(), 0);
        //when
        document.addPartAt(0, part);
        document.addPartAt(1, mockPart(PART_NAME));
        DocumentPart actualPartAtFirstPosition = document.getPartAt(0);
        DocumentPart actualPartAtSecondPosition = document.getPartAt(1);
        boolean modified = document.isModified();
        //then
        assertEquals(part, actualPartAtFirstPosition);
        assertNotEquals(part, actualPartAtSecondPosition);
        assertTrue(modified);
    }

    @Test
    void testAppendPart() {
        //when
        document.appendPart(part);
        DocumentPart actualPart = document.getPartAt(document.getPartCount() - 1);
        //then
        assertEquals(part, actualPart);
    }

    @Test
    void testRemovePartAt() {
        assertEquals(document.getPartCountImpl(), 0);
        //when
        document.addPartAt(0, part);
        boolean modified = document.isModified();
        //then
        assertTrue(modified);
    }

    @Test
    void testIndexOfPart() {
        //when
        document.addPartAt(0, part);
        int index = document.indexOfPart(PART_NAME);
        //then
        assertEquals(0, index);
    }

    @Test
    void testSetPartAt() {
        //when
        document.addPartAt(0, part);
        DocumentPart mock = mockPart("part2");
        document.setPartAt(0, mock);
        DocumentPart actualPart = document.getPartAt(0);
        boolean modified = document.isModified();
        //then
        assertEquals(mock, actualPart);
        assertTrue(modified);
    }

    @Test
    void testSetPart() {
        //when
        document.addPartAt(0, part);
        DocumentPart otherPart = mockPart(PART_NAME);
        document.setPart(otherPart);
        DocumentPart actual = document.getPart(PART_NAME);
        //then
        assertEquals(otherPart, actual);
    }

    @Test
    void testSetPartWhenNotFound() {
        //when
        document.setPart(part);
        DocumentPart actual = document.getPart(PART_NAME);
        //then
        assertEquals(part, actual);
    }

    @Test
    void testGetPartList() {
        //when
        DocumentPart mock = mockPart("part2");
        document.addPartAt(0, mock);
        document.addPartAt(1, part);
        List<DocumentPart> actual = document.getPartList();
        boolean modified = document.isModified();
        //then
        assertThat(actual).containsExactly(mock, part);
        assertTrue(modified);
    }

    @Test
    void testRemoveStructureChangeListener() {
        //when
        document.addStructureChangeListener(structureChangeListeners[0]);
        document.addStructureChangeListener(structureChangeListeners[1]);
        document.removeStructureChangeListener(structureChangeListeners[0]);
        Enumeration<StructureChangeListener> actual = document.getStructureChangeListeners();
        StructureChangeListener actualListener = actual.nextElement();
        //then
        assertEquals(structureChangeListeners[1], actualListener);
    }

    @Test
    void testRemoveTextChangedListener() {
        //when
        document.addTextChangeListener(textChangeListeners[0]);
        document.addTextChangeListener(textChangeListeners[1]);
        document.removeTextChangeListener(textChangeListeners[0]);
        Enumeration<TextChangeListener> actual = document.getTextChangeListeners();
        TextChangeListener actualListener = actual.nextElement();
        //then
        assertEquals(textChangeListeners[1], actualListener);
    }

    @Test
    void testRemoveDocumentChangedListener() {
        //when
        document.addDocumentChangeListener(documentChangeListeners[0]);
        document.addDocumentChangeListener(documentChangeListeners[1]);
        document.removeDocumentChangeListener(documentChangeListeners[0]);
        Enumeration<DocumentChangeListener> actual = document.getDocumentChangeListeners();
        DocumentChangeListener actualListener = actual.nextElement();
        //then
        assertEquals(documentChangeListeners[1], actualListener);
    }

    private void setupListeners() {
        structureChangeListeners = new StructureChangeListener[] {mock(StructureChangeListener.class), mock(StructureChangeListener.class)};
        documentChangeListeners = new DocumentChangeListener[] {mock(DocumentChangeListener.class), mock(DocumentChangeListener.class)};
        textChangeListeners = new TextChangeListener[] {mock(TextChangeListener.class), mock(TextChangeListener.class)};
    }

    private DocumentPart mockPart(String name) {
        DocumentPart part = mock(DocumentPart.class);
        // avoid NullPointerException
        when(part.getStructureChangeListeners()).thenReturn((Enumeration<StructureChangeListener>) mock(Enumeration.class));
        when(part.getTextChangeListeners()).thenReturn((Enumeration<TextChangeListener>) mock(Enumeration.class));
        when(part.getName()).thenReturn(name);

        return part;
    }
}