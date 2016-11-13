package java8.functionalprograming.functionalprogramminginjavabook.chapter15.imperative_to_functional;

/**
 * @author Tushar Chokshi @ 11/13/16.
 */
public class ReadXMLFile {
    public static void main(String[] args) {

        /*SAXBuilder builder = new SAXBuilder();
        File xmlFile = new File("c:\\file.xml");

        try {

            Document document = (Document) builder.build(xmlFile);
            Element rootNode = document.getRootElement();
            List list = rootNode.getChildren("staff");

            for (int i = 0; i < list.size(); i++) {

                Element node = (Element) list.get(i);

                System.out.println("First Name : " + node.getChildText("firstname"));
                System.out.println("Last Name : " + node.getChildText("lastname"));
                System.out.println("Nick Name : " + node.getChildText("nickname"));
                System.out.println("Salary : " + node.getChildText("salary"));

            }

        } catch (IOException io) {
            System.out.println(io.getMessage());
        } catch (JDOMException jdomex) {
            System.out.println(jdomex.getMessage());
        }*/
    }
}
