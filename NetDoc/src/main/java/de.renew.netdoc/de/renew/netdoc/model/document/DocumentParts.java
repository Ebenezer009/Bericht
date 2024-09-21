package de.renew.netdoc.model.document;

import de.renew.netdoc.model.document.documents.LinearDocument;
import de.renew.netdoc.model.document.parts.linear.tex.TexTargetNamePart;
import de.renew.netdoc.model.document.parts.linear.tex.TexTargetPackagePart;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * Factory providing NetDoc document parts and part names.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public abstract class DocumentParts {

    /**
     * NetDoc document part name.
     */
    public static final String NAME_NETDOC_DOCUMENT = "netdocDocument";

    /**
     * NetDoc main part name.
     */
    public static final String NAME_NETDOC_MAIN = "netdocMain";

    /**
     * NetDoc author part name.
     */
    public static final String NAME_NETDOC_AUTHOR = "netdocAuthor";

    /**
     * NetDoc creation date part name.
     */
    public static final String NAME_NETDOC_DATE_CREATION = "netdocDateCreation";

    /**
     * NetDoc last modified date part name.
     */
    public static final String NAME_NETDOC_DATE_LAST_MODIFIED = "netdocDateLastModified";

    /**
     * NetDoc drawing name part name.
     */
    public static final String NAME_NETDOC_TARGET_NAME = "netdocTargetName";

    /**
     * NetDoc drawing package part name.
     */
    public static final String NAME_NETDOC_TARGET_PACKAGE = "netdocTargetPackage";

    /**
     * NetDoc description part name.
     */
    public static final String NAME_NETDOC_DESCRIPTION = "netdocDescription";

    /**
     * NetDoc title part name.
     */
    public static final String NAME_NETDOC_TITLE = "netdocTitle";


    /**
     * Current NetDoc documentation unit version.
     */
    public static final String NETDOC_TEX_UNIT_VERSION_PREFIX = "NetDoc TeX Documentation Unit v";

    /**
     * Current NetDoc documentation unit version.
     */
    public static final String NETDOC_TEX_UNIT_VERSION_NUMBER = "1.0";


    /**
     * NetDoc date/time format pattern.
     */
    private static final String NETDOC_DATETIME_PATTERN = "dd-MMM-yyyy 'at' HH:mm";


    /**
     * Determines the NetDoc string representation of the specified date.
     * @param dateToConvert  the date whose NetDoc string representation is to
     * be determined.
     * @return the NetDoc string representation of the specified date.
     * @de.renew.require (dateToConvert != null)
     * @de.renew.ensure (returnValue != null)
     */
    public static String getDateString(Date dateToConvert) {
        assert (dateToConvert != null) : "Precondition violated: (dateToConvert != null)";

        String returnValue = new SimpleDateFormat(
                        DocumentParts.NETDOC_DATETIME_PATTERN, Locale.ENGLISH)
                                        .format(dateToConvert);
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }

    /**
     * Sets or adds the NetDoc name and package part of the specified document
     * using the TeX drawing name and package parts.
     * @param documentToModify the document to be modified.
     * @de.renew.require (documentToModify != null)
     */
    public static void setTexDrawingParts(LinearDocument documentToModify) {
        assert (documentToModify != null) : "Precondition violated: (documentToModify != null)";

        TexTargetPackagePart packagePart = new TexTargetPackagePart(
                        DocumentParts.NAME_NETDOC_TARGET_PACKAGE,
                        documentToModify);
        if (documentToModify.containsPart(
                        DocumentParts.NAME_NETDOC_TARGET_PACKAGE)) {
            documentToModify.setPart(packagePart);
        } else {
            documentToModify.addPartAt(0, packagePart);
        }

        TexTargetNamePart namePart = new TexTargetNamePart(
                        DocumentParts.NAME_NETDOC_TARGET_NAME,
                        documentToModify);
        if (documentToModify
                        .containsPart(DocumentParts.NAME_NETDOC_TARGET_NAME)) {
            documentToModify.setPart(namePart);
        } else {
            documentToModify.addPartAt(0, namePart);
        }
    }
}