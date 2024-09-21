package de.renew.netdoc.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

class URLsTest {

    private static final String FILENAME = "package-root.doctex";

    @Test
    void testAppend() throws MalformedURLException {
        // given
        URL url1 = new URL("file:PC/downloads");
        URL expectedUrl = new URL("file:PC/downloads/append");

        //when
        URL appendedUrl = URLs.append(url1, "append");
        //then
        assertEquals(expectedUrl, appendedUrl);
    }

    @Test
    void testCreate() throws MalformedURLException {
        // given
        File file = new File("file.txt");
        URL expectedURL = new URL("file://file.txt");
        //when
        URL fileUrl = URLs.create(file);
        // then
        assertEquals(expectedURL, fileUrl);
    }

    @Test
    void testExists() throws MalformedURLException {
        //given
        URL url = new URL("file:src/test/resources/" + FILENAME);
        URL notFoundUrl = new URL("file:thisdoesnotexist.txt");
        //when
        boolean exists = URLs.exists(url);
        boolean doesNotExist = URLs.exists(notFoundUrl);
        //then
        assertTrue(exists);
        assertFalse(doesNotExist);
    }

    @Test
    void testGetFilename() throws MalformedURLException {
        //given
        URL given = new URL("file:src/test/resources/" + FILENAME);
        //when
        String filename = URLs.getFilename(given);
        //then
        assertEquals(FILENAME, filename);
    }

    @Test
    void testGetParent() throws MalformedURLException {
        //given
        URL given = new URL("file:src/test/separator/resources/" + FILENAME);
        URL expectedURL = new URL("file:src/test/separator/resources");
        //when
        URL parent = URLs.getParent(given);
        //then
        assertEquals(expectedURL, parent);
    }

    @Test
    void testAddExtension() throws MalformedURLException {
        //given
        URL url = new URL("file:src/test");
        URL urlExpected = new URL("file:src/test.txt");
        //when
        URL withExtension = URLs.addExtension(url, ".txt");
        //then
        assertEquals(urlExpected, withExtension);
    }

    @Test
    void testExchangeExtension() throws MalformedURLException {
        //given
        URL url = new URL("file:src/test.txt");
        URL urlExpected = new URL("file:src/test.docx");
        //when
        URL withExchangeExtension = URLs.exchangeExtension(url, ".docx");
        //then
        assertEquals(urlExpected, withExchangeExtension);
    }

    @Test
    void testRemoveExtension() throws MalformedURLException {
        //given
        URL url = new URL("file:src/test.txt");
        URL expected = new URL("file:src/test");
        //when
        URL actual = URLs.removeExtension(url);
        //then
        assertEquals(expected, actual);
    }
}