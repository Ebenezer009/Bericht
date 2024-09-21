package de.renew.netdoc.gui.windows;

import de.renew.netdoc.gui.Window;
import de.renew.netdoc.gui.components.AbstractComponent;

import javax.swing.JFrame;

/**
 * This class creates an abstract NetDoc GUI window.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public abstract class AbstractWindow extends AbstractComponent implements Window {
    /**
     * empty constructor
     */
    public AbstractWindow(){

    }

    /**
     * <p>Returns the javax.swing.JFrame this window is wrapping.</p>
     * <p>To override implement {@link #getJavaFrameImpl()}.</p>
     *
     * @return the javax.swing.JFrame this window is wrapping.
     * @de.renew.ensure (returnValue != null)
     */
    @Override
    public final JFrame getJavaFrame() {
        JFrame returnValue = this.getJavaFrameImpl();
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected final java.awt.Component getJavaComponentImpl() {
        return this.getJavaFrame();
    }

    /**
     * Returns the javax.swing.JFrame this window is wrapping.
     *
     * @return the javax.swing.JFrame this window is wrapping.
     * @de.renew.ensure (returnValue != null)
     */
    protected abstract JFrame getJavaFrameImpl();
}