package example;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;

public class ConvertXmlToCsv {
    public static void main(String args[]) throws Exception {
//        File stylesheet = new File("src/main/resources/style.xsl");
        File xmlSource = new File("src/main/java/example/AllOffers_Nissan.xml");

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(xmlSource);

//        StreamSource stylesource = new StreamSource(stylesheet);
        StreamSource stylesource = new StreamSource();
        //Transformer transformer = TransformerFactory.newInstance().newTransformer(stylesource);
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        Source source = new DOMSource(document);
        Result outputTarget = new StreamResult(new File("src/main/java/example/AllOffers_Nissan.csv"));
        transformer.transform(source, outputTarget);
    }
}
