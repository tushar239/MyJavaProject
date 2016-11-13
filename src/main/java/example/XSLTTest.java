package example;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class XSLTTest {
    public static void main(String[] args) {
        String dataXML = "MyJavaProject/XmlToXslt/catalog.xml";
        String inputXSL = "MyJavaProject/XmlToXslt/catalog2.xsl";

        XSLTTest st = new XSLTTest();
        try {
            st.transform(dataXML, inputXSL);
        } catch (TransformerConfigurationException e) {
            System.err.println("TransformerConfigurationException");
            System.err.println(e);
        } catch (TransformerException e) {
            System.err.println("TransformerException");
            System.err.println(e);
        }
    }

    public void transform(String dataXML, String inputXSL) throws TransformerConfigurationException, TransformerException {
        TransformerFactory factory = TransformerFactory.newInstance();
        StreamSource xslStream = new StreamSource(inputXSL);
        Transformer transformer = factory.newTransformer(xslStream);
        StreamSource in = new StreamSource(dataXML);
        //StreamResult out = new StreamResult(System.out);
        StreamResult out = new StreamResult("MyJavaProject/XmlToXslt/output/output.html");
        transformer.transform(in, out);
    }

}
