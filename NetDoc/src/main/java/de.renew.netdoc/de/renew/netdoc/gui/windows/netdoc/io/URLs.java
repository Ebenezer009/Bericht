package de.renew.netdoc.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Toolkit providing advanced URL input/output operations.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public class URLs {

    /**
     * This class cannot be instantiated.
     */
    private URLs() {
    }

    /**
     * Appends the specified relative path to the specified base URL.
     *
     * @param base the base URL to be used.
     * @param relativePathToAppend the relative path to be appended to the
     * given base URL.
     * @return the new URL holding the relative path appended to the base.
     * @throws MalformedURLException if the given relative path was not valid.
     * @de.renew.require (base != null)
     * @de.renew.require (relativePathToAppend != null)
     * @de.renew.ensure (returnValue != null)
     */
    public static URL append(URL base, String relativePathToAppend) throws MalformedURLException {
        assert (base != null) : "Precondition violated: (base != null)";
        assert (relativePathToAppend != null) : "Precondition violated: (relativePathToAppend != null)";

        if (relativePathToAppend.equals("")) {
            return base;
        }

        URL returnValue = null;
        String url = base.toExternalForm();
        if (base.getProtocol().equals("file")) {
            if (url.charAt(url.length()) != File.separatorChar) {
                url = url + File.separatorChar;
            }
            returnValue = new URL(url + relativePathToAppend);
        } else {
            if (url.charAt(url.length()) != '/') {
                url = url + '/';
            }
            returnValue = new URL(url + relativePathToAppend);
        }
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * Creates a URL from the specified abstract path.
     *
     * @param path the abstract path to be used.
     * @return return the URL
     * @throws MalformedURLException if the given path is not valid URL.
     * @de.renew.require (path != null)
     * @de.renew.ensure (returnValue != null)
     */
    public static URL create(File path) throws MalformedURLException {
        return new URL("file://" + path.getPath());
    }

    /**
     * Determines whether the specified resource exists. A resource is meant to
     * be existent, if the link uses the file-protcol and the corresponding
     * file exists or, if the resource is connectable.
     *
     * @param resource the link to the resource to be verified.
     * @return {@code true}, if the specified resource exists;<br>
     * {@code false} otherwise.
     * @de.renew.require (resource != null)
     */
    public static boolean exists(URL resource) {
        assert (resource != null) : "Precondition violated: (resource != null)";

        // if file-protocol
        if (resource.getProtocol().equals("file")) {
            return new File(resource.toString().substring(5)).exists();
        } else {
            try {
                resource.openConnection();
                return true;
            } catch (IOException e) {
                return false;
            }
        }
    }

    /**
     * Returns the filename part of the path of the specified URL.
     *
     * @param url the URL whose filename part is to be returned.
     * @return the filename part of the path of the specified URL.
     * @de.renew.require (url != null)
     * @de.renew.ensure (returnValue != null)
     */
    public static String getFilename(URL url) {
        assert (url != null) : "Precondition violated: (url != null)";

        String returnValue = url.getPath();
        int sepIndex = returnValue.lastIndexOf(URLs.getSeparator(url));
        if (sepIndex >= 0) {
            returnValue = returnValue.substring(sepIndex + 1);
        }
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * Returns the parent URL of the specified URL.
     *
     * @param url the URL whose parent URL is to be returned.
     * @return the parent URL; or {@code null}, if the specified URL has no
     * parent.
     * @de.renew.require (url != null)
     */
    public static URL getParent(URL url) {
        assert (url != null) : "Precondition violated: (url != null)";

        String urlString = url.toExternalForm();
        int sepIndex = urlString.lastIndexOf(URLs.getSeparator(url));

        try {
            return (sepIndex < 0) ? null : new URL(urlString.substring(0, sepIndex));
        } catch (MalformedURLException e) {
            return null;
        }
    }

    /**
     * Returns the separator char used by the specified URL.
     *
     * @param url Parameter for url
     * @return the separator char used by the specified URL.
     * @de.renew.require (url != null)
     */
    public static char getSeparator(URL url) {
        assert (url != null) : "Precondition violated: (url != null)";

        char separator = '/';
        if (url.getProtocol().equals("file")) {
            separator = File.separatorChar;
        }

        return separator;
    }

    /**
     * Adds the specified file extension (e.g. ".txt") to the path of the
     * specified URL.
     *
     * @param url the url to add the file extension to.
     * @param extensionToAdd the file extension to be added.
     * @return the url with the added file extension.
     * @throws MalformedURLException if the specified extension was invalid.
     * @de.renew.require (url != null)
     * @de.renew.require (extensionToAdd != null)
     * @de.renew.ensure (returnValue != null)
     */
    public static URL addExtension(URL url, String extensionToAdd) throws MalformedURLException {
        assert (url != null) : "Precondition violated: (url != null)";
        assert (extensionToAdd != null) : "Precondition violated: (extensionToAdd != null)";

        URL returnValue = new URL(url.toExternalForm() + extensionToAdd);
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * Exchanges the file extension of the path of the specified URL using the
     * specified file extension (e.g. ".txt")
     *
     * @param url the url whose file extension is to be exchanged.
     * @param newExtension the new file extension.
     * @return the url with the exchanged file extension.
     * @throws MalformedURLException if the specified extension was invalid.
     * @de.renew.require (url != null)
     * @de.renew.require (newExtension != null)
     * @de.renew.ensure (returnValue != null)
     */
    public static URL exchangeExtension(URL url, String newExtension) throws MalformedURLException {
        assert (url != null) : "Precondition violated: (url != null)";
        assert (newExtension != null) : "Precondition violated: (newExtension != null)";

        URL returnValue = URLs.addExtension(URLs.removeExtension(url), newExtension);
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * Removes the file extension (e.g. ".txt") from the path of the specified
     * URL.
     *
     * @param url the url to remove the file extension from.
     * @return the url without the file extension.
     * @de.renew.require (url != null)
     * @de.renew.ensure (returnValue != null)
     */
    public static URL removeExtension(URL url) {
        assert (url != null) : "Precondition violated: (url != null)";

        int sepIndex = URLs.getFilename(url).lastIndexOf('.');

        String urlString = url.toExternalForm();
        if (sepIndex >= 0) {
            urlString = urlString.substring(0, urlString.lastIndexOf('.'));
        }

        URL returnValue = null;
        try {
            returnValue = new URL(urlString);
        } catch (MalformedURLException e) {
            returnValue = url;
        }
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * Connects to the specified resource and opens an input stream at this
     * resource. If the specified link used the file-protocol, a {@link
     * FileInputStream} will be opened. Otherwise, a default URL input stream
     * will be opened.
     *
     * @param resource the link to the resource to connect to.
     * @return the input stream just opened.
     * @throws IOException if an I/O error occurred while opening the stream.
     * @de.renew.require (resource != null)
     * @de.renew.ensure (returnValue != null)
     */
    public static InputStream openInputStream(URL resource) throws IOException {
        assert (resource != null) : "Precondition violated: (resource != null)";

        InputStream returnValue = resource.openStream();

        // if file-protocol
        if (resource.getProtocol().equals("file")) {
            // use File-Input-Stream (remove "file:" from path)
            returnValue = new FileInputStream(new File(resource.toExternalForm().substring(5)));
        } else {
            // otherwise use default URL-Input-Stream
            returnValue = resource.openStream();
        }
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * Connects to the specified resource and opens an output stream at this
     * resource. If the specified link used the file-protocol, a {@link
     * FileOutputStream} will be opened. Otherwise, a default URL output stream
     * will be opened.
     * The calling method needs to close this stream after using it.
     *
     * @param resource the link to the resource to connect to.
     * @return the output stream just opened.
     * @throws IOException if an I/O error occurred while opening the stream.
     * @de.renew.require (resource != null)
     * @de.renew.ensure (returnValue != null)
     */
    @SuppressWarnings("resource")
    //The caling methods are responsible for closing this stream.
    //Therefore, the resource leak warning can be suppressed.
    public static OutputStream openOutputStream(URL resource) throws IOException {
        assert (resource != null) : "Precondition violated: (resource != null)";

        OutputStream returnValue = null;

        // if file-protocol
        if (resource.getProtocol().equals("file")) {
            // use File-Output-Stream (remove "file:" from path)
            File file = new File(resource.toExternalForm().substring(5));

            // if file not exists
            if (!file.exists()) {
                // create file
                file.createNewFile();
            }
            returnValue = new FileOutputStream(file);
        } else {
            // otherwise use default URL-Output-Stream
            returnValue = resource.openConnection().getOutputStream();
        }
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }
}