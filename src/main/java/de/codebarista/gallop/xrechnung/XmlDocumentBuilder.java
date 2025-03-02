package de.codebarista.gallop.xrechnung;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import java.util.HashMap;
import java.util.Map;

/**
 * A utility class for building XML documents with namespace support.
 */
public class XmlDocumentBuilder {
    private final Document document;
    private final Map<String, String> namespaces = new HashMap<>();
    private Element rootNode;

    /**
     * Creates a new instance of {@code XmlDocumentBuilder}.
     * <p>
     * Initializes a new XML {@link Document} with namespace awareness enabled.
     * </p>
     *
     * @throws ParserConfigurationException if a DocumentBuilder cannot be created
     */
    public XmlDocumentBuilder() throws ParserConfigurationException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        docFactory.setNamespaceAware(true);
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        document = docBuilder.newDocument();
    }

    /**
     * Adds a namespace to be used in the XML document.
     *
     * @param prefix the namespace prefix
     * @param uri    the namespace URI
     */
    public void addNamespace(String prefix, String uri) {
        namespaces.put(uri, prefix);
        setNamespaceAttributes();
    }

    /**
     * Creates an XML element with the specified namespace and element name.
     *
     * @param namespaceUri the namespace URI of the element
     * @param elementName  the name of the element
     * @return the created {@link Element}
     * @throws IllegalArgumentException if the namespace URI is not registered
     */
    public Element createElement(String namespaceUri, String elementName) {
        String prefix = namespaces.get(namespaceUri);
        if (prefix == null) {
            throw new IllegalArgumentException("Unknown namespace URI " + namespaceUri);
        }
        return document.createElementNS(namespaceUri, prefix + ":" + elementName);
    }

    /**
     * Sets the root element of the XML document.
     * <p>
     * This method can only be called once; subsequent calls will throw an exception.
     * </p>
     *
     * @param element the root element to set
     * @throws IllegalStateException if the root element has already been set
     */
    public void setRootElement(Element element) {
        if (document.hasChildNodes()) {
            throw new IllegalStateException("Root node already set");
        }
        document.appendChild(element);
        rootNode = element;
        setNamespaceAttributes();
    }

    /**
     * Updates the root element with all registered namespace attributes.
     */
    private void setNamespaceAttributes() {
        if (rootNode == null) {
            return;
        }
        for (Map.Entry<String, String> entry : namespaces.entrySet()) {
            rootNode.setAttribute("xmlns:" + entry.getValue(), entry.getKey());
        }
    }

    /**
     * Returns a {@link DOMSource} representation of the XML document.
     *
     * @return a {@link DOMSource} containing the XML document
     */
    public DOMSource getDomSource() {
        return new DOMSource(document);
    }
}
