What is XSLT?
-------------
It is a template to transform xml to html/txt/xml etc.

XSLT processors are required to interpret and execute the instructions found in XSLT stylesheets.

What is JAXP?
-------------
JAXP is Java API for XML Processing, which provides a platform for us to Parse the XML Files with the DOM, SAX, Stax etc. Parsers.

e.g. XSLTTest.java, it provides Transformer.
Transformer transformer = factory.newTransformer(xslt document);
transformer.transform(any Source, any Result);

Source is an interface. So, you can pass any of its subclasses : StreamSource, DomSource, SaxSource. 
If you use SaxSource, xml will parsed using Sax parser. You can write a custom SaxSource and provide custom XMLReader inside it. XMLReader requires ContentHandler. You can customize DefaultHandler that extends ContentHandler to override parsing methods like startDocument, startElement, characters, endElement, endDocument etc.
DefaultHandler provides low level Sax API methods that you can override to intercept the elements being parsed.

What is JAXB?
-------------
JAXB (Java API for XML Binding) is a specific API (the stuff under javax.xml.bind) that uses annotations to bind XML documents to a java object model.
JAXB is mainly use for marshaling and unmarshaling (converting xml document to java object and vice-versa). 

catalog2.xsl
-----------

<xsl:template match="/"> --- match attribute contains XPath

It means start parsing an xml from root element (<catalog>)
It can be started from any element, not necessarily root element aloways.


<xsl:apply-templates/> --- Apply all the templates
<xsl:apply-templates select="catalog/cd" /> --- select clause uses XPath. It says start parsing cd elements under catalog element and apply all necessary templates to it.


The <xsl:apply-templates> element tells the XSLT processor to begin a new search for elements in the source XML
document that match the "catalog/cd" pattern and to instantiate an additional template that matches. 

Simply including one or more <xsl:template> elements in a stylesheet does not mean that they will be instantiated.

While parsing catalog/cd elements, xslt processor looks for the template that provides BEST match (Not necessarily exact match) to the parsed element.
e.g.  
<xsl:template match="title">
		...
</xsl:template>

When catalog/cd elements are being parsed, xslt processor hits <title> element under <cd>. It tries to find BEST match for <title> element.
As exact template with match="title" is available, that template will be applied. Let's say you have

<xsl:template match="title">
		...
</xsl:template>

<xsl:template match="cd/title">
		...
</xsl:template>

Which one will be applied? -> second one with match="cd/title" will be applied because that is a better match than another one with match="title"

More about XSLT
---------------
http://www.w3schools.com/xsl/