package java8.functionalprograming.functionalprogramminginjavabook.chapter15.imperative_to_functional;

import java8.functionalprograming.functionalprogramminginjavabook.chapter5.List;
import java8.functionalprograming.functionalprogramminginjavabook.chapter7.Result;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * 15.3.3 Implementing the functions
 *
 * Below example is directly from Book (pg 448).
 *
 * ReadXMLFile.java is built by myself.
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
        return String.format(format, element.getChildText("firstname"),
                element.getChildText("lastname"),
                element.getChildText("email"),
                element.getChildText("salary"));
    }

    private static <T> void processList(List<T> list) {
        list.forEach(System.out::println);
    }

    public static void main(String[] args) {
        readFile2String(ReadXMLFile.FILE_LOCATION)
                .flatMap(xmlStr -> readDocument("staff", xmlStr))
                .map(staffChildren -> toStringList(staffChildren, "FirstName: %s, LastName: %s, Email: %s, Salary: %s"))
                .forEach(finalResult -> processList(finalResult));


    }

}
