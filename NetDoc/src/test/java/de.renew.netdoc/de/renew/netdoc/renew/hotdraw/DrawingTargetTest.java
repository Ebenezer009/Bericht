package de.renew.netdoc.renew.hotdraw;

import CH.ifa.draw.framework.Drawing;
import CH.ifa.draw.standard.FigureEnumerator;
import de.renew.netdoc.io.URLs;
import de.renew.netdoc.model.doctarget.DocTarget;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;

import java.io.File;
import java.net.URL;
import java.util.Collection;
import java.util.Vector;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class DrawingTargetTest extends AbstractTargetTest {

    private DrawingTarget drawingTarget;
    private File file;
    @Mock
    private Drawing drawing;

    @Override
    @BeforeEach
    protected void setUp() {
        super.setUp();
        drawingTarget = DrawingTarget.instance(drawing);
        file = new File("test.txt");
        setupDrawing();
    }

    @AfterEach
    void tearDown() throws Exception  {
        closeable.close();
    }

    @Test
    void testInstance() {
        //when
        DrawingTarget target = DrawingTarget.instance(drawing);
        //then
        assertNotNull(target);
    }

    @Test
    void testGetDrawing() {
        //when
        Drawing actualDrawing = drawingTarget.getDrawing();
        //then
        assertEquals(drawing, actualDrawing);
    }

    @Test
    void testGetResource() throws Exception{
        //when
        URL url = drawingTarget.getResource();
        URL expected = URLs.create(file);
        //then
        assertEquals(expected, url);
    }

    @Test
    void testGetSubTargets() {
        //when
        Collection<FigureTarget> actual = drawingTarget.getSubTargets();
        //then
        assertThat(actual).contains(expectedFigures.toArray(new FigureTarget[0]));
    }

    @Test
    void testGetSubTargetsWithName() {
        // when
        Collection<FigureTarget> subTargets = drawingTarget.getSubTargets();
        setSubTargetsName(subTargets);
        Collection<DocTarget> actualTargets = drawingTarget.getSubTargets(TARGET_NAME + 1);
        //then
        assertThat(actualTargets).containsExactly(figureTarget);
    }

    @Test
    void testGetSubTargetWithName() {
        //when
        Collection<FigureTarget> subTargets = drawingTarget.getSubTargets();
        setSubTargetsName(subTargets);
        FigureTarget actual = (FigureTarget) drawingTarget.getSubTarget(TARGET_NAME + 1);
        FigureTarget actualNotFound = (FigureTarget) drawingTarget.getSubTarget(TARGET_NAME);
        //then
        assertEquals(figureTarget, actual);
        assertThat(expectedFigures).doesNotContain(actualNotFound);
    }

    private void setupDrawing() {
        when(drawing.getFilename()).thenReturn(file);
        // enumerator needs to be created in place
        when(drawing.figures()).thenReturn(new FigureEnumerator(new Vector<>(figures)), new FigureEnumerator(new Vector<>(figures)));
    }
}