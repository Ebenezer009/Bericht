package de.renew.netdoc.model.document.parts;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import de.renew.netdoc.model.document.DocumentPart;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

/**
 * As this test tests the correctness of a wrapper class, whose correctness is tested in a different test, this test
 * only verifies that the correct methods are called.
 *
 * @author 2slack
 */
class DocumentPartWrapperTest {

    private DocumentPartWrapper wrapper;
    @Mock
    private DocumentPart part;
    private AutoCloseable closeable;
    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        wrapper = new DocumentPartWrapper(part);
    }

    @AfterEach
    void tearDown() throws Exception{
        closeable.close();
    }

    @Test
    void testGetName() {
        //when
        wrapper.getName();
        //then
        verify(part).getName();
    }

    @Test
    void testGetText() {
        //when
        wrapper.getText();
        //then
        verify(part).getText();
    }

    @Test
    void testIsModified() {
        //when
        wrapper.isModified();
        //then
        verify(part).isModified();
    }

    @Test
    void testContainsPart() {
        //when
        wrapper.containsPart("part");
        //then
        verify(part).containsPart("part");
    }

    @Test
    void testGetPartCount() {
        //when
        wrapper.getPartCount();
        //then
        verify(part).getPartCount();
    }

    @Test
    void testGetVersion() {
        //when
        wrapper.getVersion();
        //then
        verify(part).getVersion();
    }

    @Test
    public void testEquals() {
        //given
        DocumentPart actual = wrapper.getWrappedPart();
        //when/then
        assertEquals(part, actual);
    }

    @Test
    void testSetText() {
        //given
        String given = "test";
        //when
        wrapper.setText(given);
        //then
        verify(part).setText(given);
    }

    @Test
    void testSetModified() {
        //when
        wrapper.setModified(true);
        //then
        verify(part).setModified(true);
    }

    @Test
    void testRemovePart() {
        //given
        String partName = "part";
        //when
        wrapper.removePart(partName);
        //then
        verify(part).removePart(partName);
    }
}