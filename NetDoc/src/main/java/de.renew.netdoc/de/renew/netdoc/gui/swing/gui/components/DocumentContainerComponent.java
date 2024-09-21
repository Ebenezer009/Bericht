package de.renew.netdoc.gui.swing.gui.components;

import de.renew.netdoc.gui.swing.gui.Component;
import de.renew.netdoc.model.container.containers.AbstractDocumentContainer;
import de.renew.netdoc.model.document.Document;

import java.util.ArrayList;
import java.util.Collection;

/**
 * This class includes the component, needed for the NetDoc DocumentContainer GUI.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public abstract class DocumentContainerComponent extends AbstractDocumentContainer implements Component {

    /**
     * Creates a new DocumentContainerComponent.
     */
    protected DocumentContainerComponent() {
        this(null);
    }

    /**
     * Creates a new DocumentContainerComponent with the specified parent.
     *
     * @param parent the parent of the new component.
     */
    protected DocumentContainerComponent(Component parent) {
        super();

        this._parent = parent;
    }

    /**
     * <p>Returns the java.awt.Component this component is wrapping.</p>
     * <p>To override implement {@link #getJavaComponentImpl()}.</p>
     *
     * @return the java.awt.Component this component is wrapping.
     * @de.renew.ensure (returnValue != null)
     */
    @Override
    public final java.awt.Component getJavaComponent() {
        java.awt.Component returnValue = this.getJavaComponentImpl();
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * <p>Returns a collection containing all child components of this
     * component.</p>
     * <p>To override implement {@link #getChildrenImpl()}.</p>
     *
     * @return a collection containing {@link Component}
     * instances.
     * @de.renew.ensure (returnValue != null)
     * @de.renew.ensure ! returnValue.contains(null)
     */
    @Override
    public final Collection<?> getChildren() {
        Collection<?> returnValue = this.getChildrenImpl();
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";
        assert !returnValue.contains(null) : "Postcondition violated: ! returnValue.contains(null)";

        return returnValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Component getParent() {
        return this._parent;
    }

    /**
     * <p>Returns the top-level-component associated with this component. This
     * is either an ancestor or the component itself.</p>
     * <p>To override implement {@link #getTopLevelComponentImpl()}.</p>
     *
     * @return the top-level-component associated with this component.
     * @de.renew.ensure (returnValue != null)
     */
    @Override
    public final Component getTopLevelComponent() {
        Component returnValue = this.getTopLevelComponentImpl();
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setParent(Component parent) {
        this._parent = parent;
    }

    /**
     * Returns the java.awt.Component this component is wrapping.
     *
     * @return the java.awt.Component this component is wrapping.
     * @de.renew.ensure (returnValue != null)
     */
    protected abstract java.awt.Component getJavaComponentImpl();

    /**
     * Returns a collection containing all child components of this component.
     *
     * @return a collection containing {@link Component}
     * instances.
     * @de.renew.ensure (returnValue != null)
     * @de.renew.ensure ! returnValue.contains(null)
     */
    protected Collection<?> getChildrenImpl() {
        return new ArrayList<Component>();
    }

    /**
     * Returns the top-level-component associated with this component. This is
     * either an ancestor or the component itself.
     *
     * @return the top-level-component associated with this component.
     * @de.renew.ensure (returnValue != null)
     */
    protected Component getTopLevelComponentImpl() {
        Component current = this;
        Component parent = this.getParent();
        while (parent != null) {
            current = parent;
            parent = current.getParent();
        }
        return current;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void focusOnDocumentImpl(Document document) {
        this.getJavaComponent().requestFocus();
    }

    /**
     * The parent of this component.
     */
    private Component _parent;
}