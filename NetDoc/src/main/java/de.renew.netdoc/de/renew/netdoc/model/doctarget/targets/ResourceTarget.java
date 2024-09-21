package de.renew.netdoc.model.doctarget.targets;

import de.renew.netdoc.model.doctarget.DocTarget;

import java.net.URL;


/**
 * Documentation target specifying a resource.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public interface ResourceTarget extends DocTarget {

    /**
     * Returns the link to the resource (i.e. file, directory or stream
     * location) associated with this target.
     * @return the link to the resource;<br>
     * or {@code null}, if this target is currently not associated with a
     * resource.
     */
    public URL getResource();
}