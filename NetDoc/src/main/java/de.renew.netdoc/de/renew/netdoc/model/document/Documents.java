package de.renew.netdoc.model.document;

import de.renew.netdoc.model.document.documents.LinearDocument;
import de.renew.netdoc.model.document.documents.linear.TexDocument;
import de.renew.netdoc.model.document.parts.LinearDocumentPart;
import de.renew.netdoc.model.document.parts.linear.tex.TexBlockDefinitionPart;
import de.renew.netdoc.model.document.parts.linear.tex.TexDefinitionPart;

import java.util.Date;


/**
 * Factory providing default NetDoc documents.
 *
 * @author Christian Bracker,
 * <a href="mailto:1bracker@informatik.uni-hamburg.de">
 * 1bracker@informatik.uni-hamburg.de</a>
 */
public abstract class Documents {

    /**
     * Default NetDoc document description.
     */
    private static final String DEFAULT_NETDOC_DESCRIPTION = "";


    /**
     * Returns a new default TeX document.
     * @return the new default TeX document.
     * @de.renew.ensure (returnValue != null)
     */
    public static LinearDocument getDefaultTex() {
        // get current date/time string
        String dateString = DocumentParts.getDateString(new Date());

        LinearDocumentPart main = new TexBlockDefinitionPart(
                        DocumentParts.NAME_NETDOC_MAIN);

        main.appendPart(new TexDefinitionPart(DocumentParts.NAME_NETDOC_TITLE));
        main.appendPart(new TexDefinitionPart(
                        DocumentParts.NAME_NETDOC_AUTHOR));
        main.appendPart(new TexDefinitionPart(
                        DocumentParts.NAME_NETDOC_DATE_CREATION, dateString));
        main.appendPart(new TexDefinitionPart(
                        DocumentParts.NAME_NETDOC_DATE_LAST_MODIFIED,
                        dateString));
        main.appendPart(new TexBlockDefinitionPart(
                        DocumentParts.NAME_NETDOC_DESCRIPTION,
                        Documents.DEFAULT_NETDOC_DESCRIPTION));

        LinearDocument returnValue = new TexDocument();
        returnValue.appendPart(main);
        assert (returnValue != null) : "Postcondition violated: (returnValue != null)";

        return returnValue;
    }
}