package java8.functionalprograming.functionalprogramminginjavabook.chapter15.imperative_to_functional;

import java8.functionalprograming.functionalprogramminginjavabook.chapter3.Executable;
import java8.functionalprograming.functionalprogramminginjavabook.chapter7.Result;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/*
15.3 Converting an imperative program: the XML reader
 */
public class ReadXMLFile {
    public static void main(String[] args) {
        String fileLocation = "MyJavaProject/src/main/java/java8/functionalprograming/functionalprogramminginjavabook/chapter15/imperative_to_functional/file.xml";
        /*
          The first problem we may have is that no part of the program can be reused.
          Of course, it is only an example, but even as an example, it should be written in a reusable way, at least to be testable.
          Here, the only way to test the program is to look at the console, which will display either the expected result or an error message.
        */

        imperativeMethod(fileLocation);

        /*
         Converting above imperative method to a functional method.
        */
        Executable executable = functionalMethod_step1(fileLocation);
        executable.execute();

        /*
         Making above method (functionalMethod_step1()) more functional by breaking down the code in multiple testable functional methods;
         */
        functionalMethod_step2(fileLocation);
    }

    protected static void imperativeMethod(String fileLocation) {
        SAXBuilder builder = new SAXBuilder();
        File xmlFile = new File(fileLocation);

        try {

            Document document = (Document) builder.build(xmlFile);
            Element rootNode = document.getRootElement();
            List list = rootNode.getChildren("staff");

            for (int i = 0; i < list.size(); i++) {

                Element node = (Element) list.get(i);

                System.out.println("First Name : " + node.getChildText("firstname")); // not functional because creating side effect
                System.out.println("Last Name : " + node.getChildText("lastname"));
                System.out.println("Nick Name : " + node.getChildText("nickname"));
                System.out.println("Salary : " + node.getChildText("salary"));

            }

        } catch (IOException | JDOMException e) {
            System.out.println(e.getMessage()); // not functional because creating side effect
        }
    }

    protected static Executable functionalMethod_step1(String fileLocation) {
        SAXBuilder builder = new SAXBuilder();

        File xmlFile = new File(fileLocation);

        Result<Document> document = null;
        try {
            document = Result.success(builder.build(xmlFile));
        } catch (JDOMException e) {
            Result.failure(e.getMessage());
        } catch (IOException e) {
            Result.failure(e.getMessage());
        }

        // 'staff' is a root element
        Result<Element> rootElement = document.flatMap(doc -> {

            try {
                return Result.success(doc.getRootElement());
            } catch (Exception e) {
                return Result.failure(e.getMessage());
            }

        });

        // List of 'staff' elements
        Result<List<Element>> staffs = rootElement.map(re -> re.getChildren());


        Result<List<String>> finalResult = staffs.map(stfs -> stfs.stream().map(staff -> {
            String firstName = staff.getChild("firstname").getText();
            String lastName = staff.getChild("lastname").getText();
            String email = staff.getChild("email").getText();
            String salary = staff.getChild("salary").getText();

            return String.format("FirstName: %s, LastName: %s, Email: %s, Salary: %s", firstName, lastName, email, salary);

        }).collect(Collectors.toList()));


        return () -> finalResult.forEach(fr -> fr.stream().forEach(str -> System.out.println(str)));
    }


    // You can even make above method (functionalMethod_step1()) more functional and testable by breaking it down into multiple methods.
    protected static void functionalMethod_step2(String fileLocation) {
        Result<Document> document = getDocument(fileLocation);
        Result<Element> rootElement = getRootElement(document);
        Result<List<Element>> staffElements = getStaffElements(rootElement);
        Result<List<String>> finalResult = getFinalResult(staffElements);
        processFinalResult(finalResult);
    }

    private static Result<Document> getDocument(String fileLocation) {
        SAXBuilder builder = new SAXBuilder();
        File xmlFile = new File(fileLocation);

        try {
            return Result.success(builder.build(xmlFile));
        } catch (JDOMException e) {
            return Result.failure(e.getMessage());
        } catch (IOException e) {
            return Result.failure(e.getMessage());
        }
    }

    private static Result<Element> getRootElement(Result<Document> document) {
        return document.flatMap(doc -> {

            try {
                return Result.success(doc.getRootElement());
            } catch (Exception e) {
                return Result.failure(e.getMessage());
            }

        });
    }

    private static Result<List<Element>> getStaffElements(Result<Element> rootElement) {
        return rootElement.map(re -> re.getChildren());
    }

    private static Result<List<String>> getFinalResult(Result<List<Element>> staffs) {
        Result<List<String>> finalResult = staffs.map(stfs -> stfs.stream().map(staff -> {
            String firstName = staff.getChild("firstname").getText();
            String lastName = staff.getChild("lastname").getText();
            String email = staff.getChild("email").getText();
            String salary = staff.getChild("salary").getText();

            return String.format("FirstName: %s, LastName: %s, Email: %s, Salary: %s", firstName, lastName, email, salary);

        }).collect(Collectors.toList()));

        return finalResult;

    }
    private static Executable processFinalResult(Result<List<String>> finalResult) {
        return () -> finalResult.forEach(fr -> fr.stream().forEach(str -> System.out.println(str)));
    }
}
