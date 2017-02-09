package tika_poc;

import java.util.Arrays;
import java.util.List;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.sax.ContentHandlerDecorator;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class TestCustomExtract extends ContentHandlerDecorator {
    private Metadata metadata;
    private static final String CUSTOM_DATA = "customdata";
    private StringBuilder stringBuilder;

    /**
     * Creates a decorator for the given SAX event handler and Metadata object.
     *
     * @param handler SAX event handler to be decorated
     */
    public TestCustomExtract(ContentHandler handler, Metadata metadata) {
        super(handler);
        this.metadata = metadata;
        this.stringBuilder = new StringBuilder();
    }

    /**
     * Creates a decorator that by default forwards incoming SAX events to
     * a dummy content handler that simply ignores all the events. Subclasses
     * should use the {@link #setContentHandler(ContentHandler)} method to
     * switch to a more usable underlying content handler.
     * Also creates a dummy Metadata object to store phone numbers in.
     */
    protected TestCustomExtract() {
        this(new DefaultHandler(), new Metadata());
    }

    /**
     * The characters method is called whenever a Parser wants to pass raw...
     * characters to the ContentHandler. But, sometimes, phone numbers are split
     * accross different calls to characters, depending on the specific Parser
     * used. So, we simply add all characters to a StringBuilder and analyze it
     * once the document is finished.
     */
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        try {
            String text = new String(Arrays.copyOfRange(ch, start, start + length));
            stringBuilder.append(text);
            super.characters(ch, start, length);
        } catch (SAXException e) {
            handleException(e);
        }
    }

    /**
     * This method is called whenever the Parser is done parsing the file. So,
     * we check the output for any phone numbers.
     */
    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
        List<String> results = TestClearCustom.extractCustom(stringBuilder.toString());
        for (String result : results) {
            metadata.add(CUSTOM_DATA, result);
        }
    }
}