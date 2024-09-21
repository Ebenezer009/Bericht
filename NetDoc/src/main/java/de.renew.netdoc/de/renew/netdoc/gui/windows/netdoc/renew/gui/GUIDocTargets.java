package de.renew.netdoc.renew.gui;

import CH.ifa.draw.framework.Drawing;
import de.renew.gui.GuiPlugin;
import de.renew.netdoc.model.container.ContainerException;
import de.renew.netdoc.model.doctarget.DocTarget;
import de.renew.netdoc.model.document.DocumentPart;
import de.renew.netdoc.model.document.Documents;
import de.renew.netdoc.renew.hotdraw.DrawingTarget;

import javax.swing.JFrame;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;

/**
 * This class is a Toolkit providing Renew GUI documentation targets.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public abstract class GUIDocTargets {
    /**
     * empty constructor
     */
    public GUIDocTargets(){

    }

    /**
     * Creates a new default document part for the specified documentation
     * target.
     *
     * @param docTarget the documentation target specifying the type of the
     * document part to be created.
     * @return the new document part.
     * @throws ContainerException if an error occurred while creating the
     * document part.
     * @de.renew.require (docTarget ! = null)
     * @de.renew.ensure (returnValue ! = null)
     */
    public static DocumentPart createDocumentPart(DocTarget docTarget) throws ContainerException {
        assert (docTarget != null) : "Precondition violated: (docTarget != null)";

        DocumentPart returnValue = null;

        if (docTarget instanceof DrawingTarget) {
            returnValue = Documents.getDefaultTex();
        } else {
            throw new ContainerException("Unknown target type");
        }

        returnValue.setTarget(docTarget);
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * Determines the current documentation target of the specified Renew GUI
     * plugin.
     *
     * @param renewGUIPlugin the Renew GUI Plugin to process.
     * @return the current documentation target of the specified plugin;<br>
     * or {@code null}, if there was no current target.
     * @de.renew.require (renewGUIPlugin ! = null)
     */
    public static DocTarget getCurrentTarget(GuiPlugin renewGUIPlugin) {
        assert (renewGUIPlugin != null) : "Precondition violated: (renewGUIPlugin != null)";

        return renewGUIPlugin.getGui().drawings().hasMoreElements() ?
                DrawingTarget.instance(renewGUIPlugin.getGui().drawing()) : null;
    }

    /**
     * Determines the documentation targets provided by the specified Renew GUI
     * plugin.
     *
     * @param renewGUIPlugin the Renew GUI Plugin to process.
     * @return a collection containing {@link DocTarget} instances.
     * @de.renew.require (renewGUIPlugin != null)
     * @de.renew.ensure (returnValue != null)
     * @de.renew.ensure !returnValue.contains(null)
     */
    public static Collection<DrawingTarget> getTargets(GuiPlugin renewGUIPlugin) {
        assert (renewGUIPlugin != null) : "Precondition violated: (renewGUIPlugin != null)";

        Collection<DrawingTarget> returnValue = new ArrayList<DrawingTarget>();

        Enumeration<Drawing> drawings = renewGUIPlugin.getGui().drawings();
        while (drawings.hasMoreElements()) {
            Drawing current = drawings.nextElement();
            if (current != null) {
                returnValue.add(DrawingTarget.instance(current));
            }
        }
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";
        assert !returnValue.contains(null) : "Postcondition violated: !returnValue.contains(null)";

        return returnValue;
    }

    /**
     * Returns the frame of the specified documentation target (if any)
     * provided by the specified Renew GUI Plugin.
     *
     * @param renewGUIPlugin the Renew GUI Plugin to process.
     * @param docTarget the documentation target whose frame is to be returned.
     * @return the frame of the specified documentation target;<br>
     * or {@code null}, if the no frame for that target is known.
     * @de.renew.require (renewGUIPlugin != null)
     * @de.renew.require (docTarget != null)
     */
    public static JFrame getTargetFrame(GuiPlugin renewGUIPlugin, DocTarget docTarget) {
        assert (renewGUIPlugin != null) : "Precondition violated: (renewGUIPlugin != null)";
        assert (docTarget != null) : "Precondition violated: (docTarget != null)";

        if (docTarget instanceof DrawingTarget) {
            try {
                return renewGUIPlugin.getGui().getFrame();
            } catch (Exception e) {
            }
        }

        return null;
    }
}