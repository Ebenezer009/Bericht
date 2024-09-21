package de.renew.netdoc.gui.event;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

/**
 * Test the correct delegation of the {@link WindowListenerWrapper} methods.
 *
 * @author 2slack
 */
class WindowListenerWrapperTest {

    private AutoCloseable closeable;
    private WindowListenerWrapper wrapper;
    @Mock
    private WindowListener listener;
    @Mock
    private WindowEvent event;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        WindowListener[] listeners = new WindowListener[]{listener};
        wrapper = new WindowListenerWrapper(listeners);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void testGetWrappedListeners() {
        //when
        WindowListener[] actual = wrapper.getWrappedListeners();
        //then
        assertThat(actual).containsExactly(listener);
    }

    @Test
    void testWindowOpened() {
        //when
        wrapper.windowOpened(event);
        //then
        verify(listener).windowOpened(event);
    }

    @Test
    void testWindowClosing() {
        //when
        wrapper.windowClosing(event);
        //then
        verify(listener).windowClosing(event);
    }

    @Test
    void testWindowClosed() {
        //when
        wrapper.windowClosed(event);
        //then
        verify(listener).windowClosed(event);
    }

    @Test
    void testWindowIconified() {
        //when
        wrapper.windowIconified(event);
        //then
        verify(listener).windowIconified(event);
    }

    @Test
    void testWindowDeiconified() {
        //when
        wrapper.windowDeiconified(event);
        //then
        verify(listener).windowDeiconified(event);
    }

    @Test
    void testWindowActivated() {
        //when
        wrapper.windowActivated(event);
        //then
        verify(listener).windowActivated(event);
    }

    @Test
    void testWindowDeactivated() {
        //when
        wrapper.windowDeactivated(event);
        //then
        verify(listener).windowDeactivated(event);
    }
}