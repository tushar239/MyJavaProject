package java8.functionalprograming.functionalprogramminginjavabook.chapter15.imperative_to_functional;

import java8.functionalprograming.functionalprogramminginjavabook.chapter12.Tuple;
import java8.functionalprograming.functionalprogramminginjavabook.chapter5and8.List;
import java8.functionalprograming.functionalprogramminginjavabook.chapter7.Result;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;

/*
15.3.3 Implementing the functions

    Below example is directly from Book (pg 448).

    ReadXMLFile.java is built by myself.

15.3.4 Making the program even more functional

    improved toStringList and processElement methods

15.3.5 Fixing the argument type problem

    Instead of using Result<String> arguments, we can use Result<FilePath> and Result<ElementName>.
    FilePath and ElementName are just value classes for string values, such as

    public class FilePath {

        public final Result<String> value;

        private FilePath(Result<String> value) {
            this.value = value;
        }

        public static FilePath apply(String value) {
            return new FilePath(Result.of(FilePath::isValidPath, value,
                    "Invalid file path: " + value));
        }

        private static boolean isValidPath(String path) {
            // Replace with validation code
            return true;
        }
    }

    You can add extra logic on your file path like validating it etc.

15.3.6 Making the element processing function a parameter

    Making the functional methods more generic.
    e.g. processList(List<T> list) method. We made it generic instead of 'processList(List<String> list)'

15.3.7 Handling errors on element names
    see processElement method. If you use element.getChildText("element name"), then getChildText can return null and that can throw NullPointerException.
    You can avoid it by having your own getChildText(element, elementName) method as shown below.

*/
public class ReadXMLFile2 {
    private static Result<String> getXmlFilePath() {
        return Result.of("<path_to_file>");
    }

    private static Result<String> getRootElementName() {
        return Result.of("staff");
    }

    public static Result<String> readFile2String(String path) {
        try {
            return Result.success(new String(Files.readAllBytes(Paths.get(path))));
        } catch (IOException e) {
            return Result.failure(String.format("IO error while reading file %s",
                    path), e);
        } catch (Exception e) {
            return Result.failure(String.format("Unexpected error while reading file %s", path), e);
        }
    }

    private static Result<List<Element>> readDocument(String rootElementName,
                                                      String stringDoc) {
        final SAXBuilder builder = new SAXBuilder();
        try {
            final Document document =
                    builder.build(new StringReader(stringDoc));
            final Element rootElement = document.getRootElement();
            return Result.success((List.fromCollection(rootElement.getChildren(rootElementName))));
        } catch (IOException | JDOMException io) {
            return Result.failure(String.format("Invalid root element name '%s' or XML data %s", rootElementName, stringDoc), io);
        } catch (Exception e) {
            return Result.failure(String.format("Unexpected error while reading XML data %s with root element %s", stringDoc, rootElementName), e);
        }
    }

    private static List<String> toStringList(List<Element> list, String format) {
        return list.map(e -> processElement(e, format));
    }

    private static String processElement(Element element, String format) {
        return String.format(format, getChildText(element, "firstname"),
                getChildText(element, "lastname"),
                getChildText(element, "email"),
                getChildText(element, "salary"));
    }

    private static String getChildText(Element element, String name) { // pg 456
        String string = element.getChildText(name);
        return string != null
                ? string : "Element " + name + " not found"; // OR you can return a Result object (Result.success(string) and Result.empty())
    }

    private static <T> void processList(List<T> list) {
        list.forEach(System.out::println);
    }

    // pg 450
    // Better way of implementing toStringList and processElement methods
    // it can reserve different format for different records.
    private static List<String> toStringList(List<Element> list,
                                             Tuple<String, List<String>> format) {
        return list.map(e -> processElement(e, format));
    }
    private static String processElement(Element element,
                                         Tuple<String, List<String>> format) {
        String formatString = format._1;
        List<String> parameters = format._2.map((name) -> element.getChildText(name));
        return String.format(formatString, parameters.toJavaList().toArray());
    }

    public static void main(String[] args) {
        readFile2String(ReadXMLFile.FILE_LOCATION)
                .flatMap(xmlStr -> readDocument("staff", xmlStr))
                .map(staffChildren -> toStringList(staffChildren, "FirstName: %s, LastName: %s, Email: %s, Salary: %s"))
                .forEach(finalResult -> processList(finalResult));
        /*
        O/P:
        FirstName: Mary, LastName: Colson, Email: mary.colson@acme.com, Salary: 200000
        FirstName: Paul, LastName: Smith, Email: paul.smith@acme.com, Salary: 100000
        */

    }



}
