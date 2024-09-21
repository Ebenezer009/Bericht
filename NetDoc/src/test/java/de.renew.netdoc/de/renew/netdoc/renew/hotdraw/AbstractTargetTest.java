package de.renew.netdoc.renew.hotdraw;

import CH.ifa.draw.framework.Figure;
import CH.ifa.draw.standard.FigureEnumerator;
import de.renew.netdoc.model.doctarget.DocTarget;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Provides test utils for {@link DrawingTargetTest} and {@link FigureTargetTest}.
 *
 * @author 2slack
 */
public class AbstractTargetTest {
    protected static final String TARGET_NAME = "name";
    protected List<Figure> figures;
    protected Collection<DocTarget> expectedFigures;
    protected AutoCloseable closeable;
    protected FigureTarget figureTarget;

    protected void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        figures = new ArrayList<>();
        expectedFigures = new ArrayList<>();
        createListOfFigures();
    }

    protected void createListOfFigures() {
        for (int i = 0; i < 5; i++) {
            Figure figure = mock(Figure.class);
            figures.add(figure);
            when(figure.figures()).thenReturn(new FigureEnumerator(new Vector<>())); // avoid NullPointerException
            FigureTarget target = FigureTarget.instance(figure);
            expectedFigures.add(target);
            if (i == 1) {
                //store this one so we can compare the search result
                figureTarget = target;
            }
        }
    }

    protected void setSubTargetsName(Collection<FigureTarget> subTargets) {
        AtomicInteger counter = new AtomicInteger(0);
        // as the FigureTarget creation is hidden inside of DrawingTarget, we need to set the name like this.
        subTargets.forEach(target -> target.setName(TARGET_NAME + counter.getAndIncrement()));
    }
}
