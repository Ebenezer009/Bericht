package de.renew.netdoc.renew.hotdraw;

import CH.ifa.draw.framework.Figure;
import CH.ifa.draw.standard.FigureEnumerator;
import de.renew.netdoc.model.doctarget.DocTarget;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Collection;
import java.util.Vector;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FigureTargetTest extends AbstractTargetTest {

    private FigureTarget target;
    @Mock
    private Figure figure;
    @Override
    @BeforeEach
    protected void setUp() {
        super.setUp();
        target = FigureTarget.instance(figure);
        setupFigures();
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void testInstance() {
        //when
        FigureTarget target = FigureTarget.instance(mock(Figure.class));
        //then
        assertNotNull(target);
    }

    @Test
    void testSetName() {
        //when
        target.setName(TARGET_NAME);
        String name = target.getName();
        //then
        assertEquals(TARGET_NAME, name);
    }

    @Test
    void getFigure() {
        //when
        Figure actualFigure = target.getFigure();
        //then
        assertEquals(figure, actualFigure);
    }

    @Test
    void testGetSubTargets() {
        //when
        Collection<FigureTarget> actualFigures = target.getSubTargets();
        //then
        assertThat(expectedFigures).containsExactlyInAnyOrderElementsOf(actualFigures);
    }

    @Test
    void testGetSubTargetsWithName() {
        //when
        Collection<FigureTarget> subTargets = target.getSubTargets();
        setSubTargetsName(subTargets);
        Collection<DocTarget> actualTargets = target.getSubTargets(TARGET_NAME + 1);
        //then
        assertThat(actualTargets).containsExactly(figureTarget);
    }

    @Test
    void testGetSubTargetWithName() {
        //when
        Collection<FigureTarget> subTargets = target.getSubTargets();
        setSubTargetsName(subTargets);
        FigureTarget actualTarget = (FigureTarget) target.getSubTarget(TARGET_NAME + 1);
        FigureTarget actualNotFound = (FigureTarget) target.getSubTarget(TARGET_NAME);
        //then
        assertEquals(figureTarget, actualTarget);
        assertThat(expectedFigures).doesNotContain(actualNotFound);
    }

    void setupFigures() {
        when(figure.figures()).thenReturn(new FigureEnumerator(new Vector<>(figures)), new FigureEnumerator(new Vector<>(figures)));
    }
}