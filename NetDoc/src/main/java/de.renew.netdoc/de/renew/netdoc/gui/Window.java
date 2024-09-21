package de.renew.netdoc.gui;

import javax.swing.JFrame;


/**
 * NetDoc GUI window.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public interface Window extends Component {

    /**
     * Returns the javax.swing.JFrame this window is wrapping.
     * @return the javax.swing.JFrame this window is wrapping.
     * @de.renew.ensure (returnValue != null)
     */
    public JFrame getJavaFrame();
}