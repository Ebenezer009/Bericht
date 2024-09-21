package de.renew.netdoc.model.document.parts.linear.tex;

import de.renew.netdoc.model.doctarget.targets.ResourceTarget;
import de.renew.netdoc.model.document.DocumentPart;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TexTargetPackagePartTest {

    private static final String NAME = "name";
    private TexTargetPackagePart targetPackagePart;
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private DocumentPart part;
    private AutoCloseable closeable;

    @BeforeEach
    void setUp() throws MalformedURLException {
        closeable = MockitoAnnotations.openMocks(this);
        targetPackagePart = new TexTargetPackagePart(NAME, part);
        setupMock();
    }

    @AfterEach
    void tearDown() throws  Exception{
        closeable.close();
    }

    private void setupMock() throws MalformedURLException {
        when(part.getTarget().getName()).thenReturn(NAME);
        ResourceTarget resourceTarget = mock(ResourceTarget.class);
        when(resourceTarget.getResource()).thenReturn(new URL("file:src/test/resources/"));
        when(part.getTarget()).thenReturn(resourceTarget);
    }

    @Test
    void testGetDocumentPart() {
        //when
        DocumentPart actualPart = targetPackagePart.getDocumentPart();
        //then
        assertEquals(part, actualPart);
    }

    @Test
    void testGetText() {
        //when
        String text = targetPackagePart.getText();
        //then
        assertEquals("src.test.resources", text);
    }

    @Test
    void testSetText() {
        //then
        assertThrows(IllegalArgumentException.class, () -> targetPackagePart.setText(NAME));
    }

}