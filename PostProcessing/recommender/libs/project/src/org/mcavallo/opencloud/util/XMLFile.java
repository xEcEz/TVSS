//@MC
package org.mcavallo.opencloud.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Class for reading local or remote an XML file, InputStream or DOM.
 * The content is accessed using the XPath syntax.
 *
 */
public class XMLFile {

	/** Object to read the file through the XPath syntax */ 
	private XPath xpath;
	
	/** XML namespace used by the XPath expression */
	private NamespaceContext namespaceContext;
	
	/** DOM document object */
	private Document document = null;
	
	/** Node from witch the XPath expression starts */  
	private Node rootNode = null;

	/** Proxy server for the http connection */
	private Proxy proxy = null;
	
	/** Timeout (in milliseconds) for establishing the HTTP connection */ 
	private int connectTimeout = 10000;
	
	/** Timeout (in milliseconds) for reading the URL */
	private int readTimeout = 10000;
	
	public XMLFile() throws ParserConfigurationException {
		inizialize();
	}

    private void inizialize() throws ParserConfigurationException {
		xpath = XPathFactory.newInstance().newXPath();
        
        // Create an empty DOM.
        DocumentBuilder builder;
        builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        document = builder.newDocument();
	}

	/**
	 * Open and parse a local XML file
	 * @param filePath Path of the file
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 */
	public void readFile(String filePath) throws IOException, ParserConfigurationException, SAXException {
		InputStream in = new FileInputStream(filePath);
        readInputStream(in);
	}
	
    /**
	 * Open and parse a String
	 * @param string String to parse
     * @throws IOException 
     * @throws SAXException 
     * @throws ParserConfigurationException 
	 */
	public void readString(String string) throws ParserConfigurationException, SAXException, IOException {
		InputStream in = new ByteArrayInputStream(string.getBytes());
        readInputStream(in);
	}
	
	/**
	 * Open and parse a remote XML file
	 * @param url Url of the file to parse
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 */
	public void readURL(URL url) throws IOException, ParserConfigurationException, SAXException {
		URLConnection connection;
		
		if (proxy == null) {
			connection = url.openConnection();
		} else {
			connection = url.openConnection(proxy);
		}
		connection.setConnectTimeout(connectTimeout);
		connection.setReadTimeout(readTimeout);
		connection.connect();
		
		InputStream in = connection.getInputStream();
        readInputStream(in);
	}

    /**
     * Open and parse an XML InputStream
     * @param in InputStream to parse
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    public void readInputStream(InputStream in) throws ParserConfigurationException, SAXException, IOException {
		if (in == null)
			return;
		
		// Parse the XML as a W3C document.
        DocumentBuilder builder;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        //factory.setValidating(false);
		builder = factory.newDocumentBuilder();
		document = builder.parse(in);
	}

    /**
     * Read and parse a DOM Document
     * @param document Document to parse
     */
    public void readDocument(Document document)
    {
        this.document = document;
    }

	/**
	 * @return The proxy object 
	 */
	public Proxy getProxy() {
		return proxy;
	}

	/**
	 * Sets the proxy to access the net
	 * @param proxy The proxy object
	 */
	public void setProxy(final Proxy proxy) {
		this.proxy = proxy;

		ProxySelector.setDefault(new ProxySelector () {

			@Override
			public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {
				ioe.printStackTrace();
			}

			@Override
			public List<Proxy> select(URI uri) {
				List<Proxy> result = new ArrayList<Proxy>();
				
				result.add(proxy);
				
				return result;
			}
			
		});
	}

	/**
	 * Sets ip address and port of the proxy required to access the net  
	 * @param ipAddress IP address of the proxy
	 * @param port Port number of the proxy
	 */
	public void setProxy(String ipAddress, int port) {
		final Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ipAddress, port));
		this.proxy = proxy;
		ProxySelector.setDefault(new ProxySelector () {

			@Override
			public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {
				ioe.printStackTrace();
			}

			@Override
			public List<Proxy> select(URI uri) {
				List<Proxy> result = new ArrayList<Proxy>();
				
				result.add(proxy);
				
				return result;
			}
			
		});
	}

	/**
	 * @return The connection timeout
	 */
	public int getConnectTimeout() {
		return connectTimeout;
	}

	/**
	 * Sets the connection timeout
	 * @param connectTimeout The connection timeout in milliseconds
	 */
	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	/**
	 * @return The read timeout
	 */
	public int getReadTimeout() {
		return readTimeout;
	}

	/**
	 * Sets the read timeout
	 * @param readTimeout The read timeout in milliseconds
	 */
	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}

	/**
	 * @return The XML NamespaceContext
	 */
	public NamespaceContext getNamespaceContext() {
		return namespaceContext;
	}

	/**
	 * Sets the XML NamespaceContext
	 * @param nsContext The NamespaceContext
	 */
	public void setNamespaceContext(NamespaceContext nsContext) {
		this.namespaceContext = nsContext;
	}

	/**
	 * Sets the XML NamespaceContext specifing the prefix and a URI
	 * @param prefix Prefix of the namespace
	 * @param uri URI of the namespace
	 */
	public void setNamespaceContext(final String prefix, final String uri) {
		this.namespaceContext = new NamespaceContext () {

		    public String getNamespaceURI(String nsPrefix) {
		        if (nsPrefix == null) {
		            throw new NullPointerException("Null prefix");
		        } else if (nsPrefix.equals(prefix)) {
		            return uri;
		        }
		        
		        return XMLConstants.NULL_NS_URI;
		    }

		    // This method isn't necessary for XPath processing.
		    public String getPrefix(String nsUri) {
		        throw new UnsupportedOperationException();
		    }

		    // This method isn't necessary for XPath processing either.
		    public Iterator getPrefixes(String nsUri) {
		        throw new UnsupportedOperationException();
		    }

		};
	}

    /**
     * Read the DOM node specified by the given XPath expression.
     * If a root node as been set, the expression is relative to the root node.
     * @param xpathExpression XPath expression of the wanted node
     * @return The resulting node
     * @throws XPathExpressionException
     */
	public Node getNode(String xpathExpression) throws XPathExpressionException {
		Node node = null;
		
		if (rootNode != null) {
			node = (Node) xpath.evaluate(xpathExpression, rootNode, XPathConstants.NODE);
		} else if (document != null){
			node = (Node) xpath.evaluate(xpathExpression, document, XPathConstants.NODE);
		}

		return node;
	}

    /**
     * Read the DOM node specified by the given XPath expression.
     * The expression is relative to the node specified as argument.
     * @param xpathExpression XPath expression of the wanted node
     * @param startingNode Starting node for the XPath expression
     * @return The resulting node
     * @throws XPathExpressionException
     */
    public Node getNode(String xpathExpression, Node startingNode) throws XPathExpressionException {
        Node node = null;
        
        if (startingNode != null) {
            node = (Node) xpath.evaluate(xpathExpression, startingNode, XPathConstants.NODE);
        } else if (document != null){
            node = getNode(xpathExpression);
        }

        return node;
    }

    /**
     * Read the DOM nodes specified by the given XPath expression.
     * If a root node as been set, the expression is relative to the root node.
     * @param xpathExpression XPath expression of the wanted nodes
     * @return The resulting node list
     * @throws XPathExpressionException
     */
	public NodeList getNodes(String xpathExpression) throws XPathExpressionException {
		NodeList nodes = null;
		
		if (rootNode != null) {
			nodes = (NodeList) xpath.evaluate(xpathExpression, rootNode, XPathConstants.NODESET);
		} else if (document != null){
			nodes = (NodeList) xpath.evaluate(xpathExpression, document, XPathConstants.NODESET);
		}

		return nodes;
	}

    /**
     * Read the DOM nodes specified by the given XPath expression.
     * The expression is relative to the node specified as argument.
     * @param xpathExpression XPath expression of the wanted nodes
     * @param startingNode Starting node for the XPath expression
     * @return XPath expression of the wanted node list
     * @throws XPathExpressionException
     */
    public NodeList getNodes(String xpathExpression, Node startingNode) throws XPathExpressionException
    {
        NodeList nodes = null;
        
        if (startingNode != null) {
            nodes = (NodeList) xpath.evaluate(xpathExpression, startingNode, XPathConstants.NODESET);
        } else {
            nodes = getNodes(xpathExpression);
        }
        
        return nodes;
    }

    /**
     * @return The DOM tree of the XML File
     */
    public Document getDocument()
    {
        return document;
    }

    /**
     * Writes the XML to a file
     * @param outputFilePath Output file path
     * @throws FileNotFoundException
     * @throws TransformerException
     */
    public void writeToFile(String outputFilePath) throws FileNotFoundException, TransformerException
    {
        writeToStream(new FileOutputStream(outputFilePath));
    }

    /**
     * Writes the XML to a file
     * @param outputFile Output file
     * @throws FileNotFoundException
     * @throws TransformerException
     */
    public void writeToFile(File outputFile) throws FileNotFoundException, TransformerException
    {
        writeToStream(new FileOutputStream(outputFile));
    }

    /**
     * Writes the XML to an OutputStream
     * @param outputStream The OutputStream
     * @throws TransformerException
     */
    public void writeToStream(OutputStream outputStream) throws TransformerException
    {
        // construct the "do nothing" transformation
        Transformer t;
        
        t = TransformerFactory.newInstance().newTransformer();
        t.setOutputProperty("indent", "yes");
        
        // apply the "do nothing" transformation and send the output to a file
        t.transform(new DOMSource(document), new StreamResult(outputStream));
    }

    /**
     * Append the child node to the parent node
     * @param parent Parent node
     * @param child Child node
     */
    public void addNode(Node parent, Node child)
    {
        if (parent == null)
            return;
        
        parent.appendChild(child);
    }
    
    /**
     * Append the given tag to the parent node
     * @param parent Parent node
     * @param tagName Tag name
     */
    public void addNode(Node parent, String tagName)
    {
        if (parent == null)
            return;
        
        Node child = document.createElement(tagName);
        parent.appendChild(child);
    }
    
    /**
     * Append the child node to the parent node specified by the XPath expression
     * @param xpathExpression XPath expression of the parent node
     * @param child Child node
     * @throws XPathExpressionException
     */
    public void addNode(String xpathExpression, Node child) throws XPathExpressionException
    {
        Node parent = getNode(xpathExpression);
        addNode(parent, child);
    }
    
    /**
     * Append the given tag to the parent node specified by the XPath expression
     * @param xpathExpression XPath expression of the parent node
     * @param tagName Tag name
     * @throws XPathExpressionException
     */
    public void addNode(String xpathExpression, String tagName) throws XPathExpressionException
    {
        Node parent = getNode(xpathExpression);
        addNode(parent, tagName);
    }
    
    /**
     * Add an attribute-value pair to a node
     * @param node Target node
     * @param name Attribute name
     * @param value Attribute value
     */
    public void addAttribute(Node node, String name, String value)
    {
        if (node == null)
            return;

        Attr attr = document.createAttribute(name);
        attr.setValue(value);
        node.appendChild(attr);
    }

    /**
     * Add an attribute-value pair to a node specified by an XPath expression
     * @param xpathExpression XPath expression of the target node
     * @param name Attribute name
     * @param value Attribute value
     * @throws XPathExpressionException
     */
    public void addAttribute(String xpathExpression, String name, String value) throws XPathExpressionException
    {
        Node parent = getNode(xpathExpression);
        addAttribute(parent, name, value);
    }
    
    /**
     * Add text content to a node
     * @param node Target node
     * @param textContent Text content
     */
    public void addText(Node node, String textContent)
    {
        if (node == null)
            return;

        node.setTextContent(textContent);
    }
    
    /**
     * Add text content to a node specified by an XPath expression
     * @param xpathExpression XPath expression of the target node
     * @param textContent Text content
     * @throws XPathExpressionException
     */
    public void addText(String xpathExpression, String textContent) throws XPathExpressionException
    {
        Node parent = getNode(xpathExpression);
        addText(parent, textContent);
    }

    /**
     * Sets the XML Document
     * @param document DOM Document
     */
    public void setDocument(Document document)
    {
        this.document = document;
    }

	/**
	 * @return Current root node
	 */
	public Node getRootNode() {
		return rootNode;
	}

	/**
	 * Sets the root node
	 * @param rootNode Root node
	 */
	public void setRootNode(Node rootNode) {
		this.rootNode = rootNode;
	}

	/**
	 * Sets as root node the node specified by an XPath expression
	 * @param xpathExpression XPath expression
	 * @throws XPathExpressionException
	 */
	public void setRootNode(String xpathExpression) throws XPathExpressionException {
		if (xpathExpression == null) {
			this.rootNode = null;
		} else {
			this.rootNode = (Node) xpath.evaluate(xpathExpression, document, XPathConstants.NODE);
		}
	}
}
//@