package de.renew.netdoc.gui.swing.gui;

import java.util.Collection;

/**
 * Interface for the component for NetDoc GUI.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public interface Component {

    /**
     * Returns the java.awt.Component this component is wrapping.
     *
     * @return the java.awt.Component this component is wrapping.
     * @de.renew.ensure (returnValue ! = null)
     */
    public java.awt.Component getJavaComponent();

    /**
     * Returns a collection containing all child components of this component.
     *
     * @return a collection containing {@link Component}
     * instances.
     * @de.renew.ensure (returnValue ! = null)
     * @de.renew.ensure ! returnValue.contains(null)
     */
    public Collection<?> getChildren();

    /**
     * Returns the parent of this component.
     *
     * @return the parent of this component;<br>
     * or <code>null</code>, if this component currently has no parent.
     */
    public Component getParent();

    /**
     * Returns the top-level-component associated with this component. This is
     * either an ancestor or the component itself.
     *
     * @return the top-level-component associated with this component.
     * @de.renew.ensure (returnValue ! = null)
     */
    public Component getTopLevelComponent();

    /**
     * Sets the parent of this component to the specified parent.
     *
     * @param parent the new parent of this component.
     */
    public void setParent(Component parent);
}