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
15.3 Converting an imperative program: the XML reader  (pg 445)

How to convert imperative method to a functional method?
Steps:
1. ReadXMLFile.java - Break down imperative methods in a small methods returning Result (not taking Result as arg).
2. ReadXMLFile.java - Make methods as generic as possible (e.g. processFinalResult method. It could have taken List<String> as an arg, but we made it taking List<T>)
3. ReadXMLFile.java - Use Comprehension pattern to get the result by combining these methods. (This is possible because of step 1)
4. AvoidAssertionNullChecksExceptions.java - Instead of using assertions, null checks, throwing exceptions etc that can create side-effects in a method, use Supplier to return a side-effect.
5. Util.java, PropertyReader.java - Avoid type casting to avoid Exceptions.


You should use Result/Optional as a
- returned value of a method
- constructor argument
- field variables

You should not use Result/Optional as a
- method argument

Important Concept:
Why shouldn't we use Result/Optional as method argument?
see functionalMethod_step2 method

 */
public class ReadXMLFile {
    protected static final String FILE_LOCATION = "MyJavaProject/src/main/java/java8/functionalprograming/functionalprogramminginjavabook/chapter15/imperative_to_functional/file.xml";

    public static void main(String[] args) {
        /*
          The first problem we may have is that no part of the program can be reused.
          Of course, it is only an example, but even as an example, it should be written in a reusable way, at least to be testable.
          Here, the only way to test the program is to look at the console, which will display either the expected result or an error message.
        */

        imperativeMethod(FILE_LOCATION);

        System.out.println();

        /*
         15.3.1 Converting above imperative method to a functional method.
        */
        Executable executable = functionalMethod_step1(FILE_LOCATION);
        executable.execute();

        System.out.println();

        /*
         15.3.2 Composing the functions and applying an effect

         Making above method (functionalMethod_step1()) more functional by breaking down the code in multiple testable functional methods;
         */
        functionalMethod_step2(FILE_LOCATION);

        System.out.println();

//        Result<Element> rootElement = Result.<Element>failure("sdfsdf");
//        rootElement.forEach(ef -> System.out.println(ef.getText()));
    }

    protected static void imperativeMethod(String fileLocation) {
        SAXBuilder builder = new SAXBuilder();
        File xmlFile = new File(fileLocation);

        try {

            Document document = builder.build(xmlFile);
            Element rootNode = document.getRootElement();// we are not checking for null document here. Assume that we are doing it. Then null check can be replaced by wrapping a document with Result.
            List list = rootNode.getChildren("staff");// we are not checking for null rootNode here. Assume that we are doing it. Then null check can be replaced by wrapping a rootNode with Result.

            for (int i = 0; i < list.size(); i++) {

                Element node = (Element) list.get(i);

                System.out.println("First Name : " + node.getChildText("firstname")); // not functional because creating side effect by printing to console
                System.out.println("Last Name : " + node.getChildText("lastname"));
                System.out.println("Nick Name : " + node.getChildText("nickname"));
                System.out.println("Salary : " + node.getChildText("salary"));

            }

        } catch (IOException | JDOMException e) {
            System.out.println(e.getMessage()); // not functional because creating side effect by printing to console
        }
    }

    // Step 1 of converting above imperative method into a functional method
    protected static Executable functionalMethod_step1(String fileLocation) {
        SAXBuilder builder = new SAXBuilder();

        File xmlFile = new File(fileLocation);

        Result<Document> document = null;
        try {
            document = Result.success(builder.build(xmlFile));
        } catch (JDOMException | IOException e) {
            document = Result.failure(e.getMessage());
        }

/*
        try {
            Optional<Document> doc = Optional.ofNullable(builder.build(xmlFile));
        } catch (JDOMException | IOException e ) {
            Optional<String> message = Optional.of(e.getMessage());
            return () -> System.out.println(message.get());
        }
*/

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


        return () -> finalResult
                .forEachOrFail(fr -> fr.stream().forEach(str -> System.out.println(str)))
                .forEach(errorMessage -> System.out.println(errorMessage));
    }


    // Step 2 of converting above step1 method into more functional method
    // You can even make above method (functionalMethod_step1()) more functional and testable by breaking it down into multiple methods.
    protected static void functionalMethod_step2(String fileLocation) {
        // In both of the below approaches, methods return a Result.
        // But one takes Result as a parameter and another doesn't.

        // APPROACH 1: Each method takes Result as arg.
        // DisAdvantage: You can't use COMPREHENSION pattern on as shown below.
        //              Inside a method, you need to use result.flatMap/map etc. It adds up more complexity in the method.
        {
            System.out.println("Approach 1: ");
            Result<Document> document = getDocument(fileLocation);
            Result<Element> rootElement = getRootElement(document);
            Result<List<Element>> staffElements = getStaffElements(rootElement);
            Result<List<String>> finalResult = getFinalResult(staffElements);
            processFinalResult(finalResult).execute();
        }
        System.out.println();

        // Approach 2 (BETTER APPROACH):  DO NOT pass Result objects to methods as parameters. Pass normal objects.
        // Advantage: This approach allows you to use COMPREHENSION pattern as below.
        // It also reduces some code inside the method. You don't need to start with resultParam.map/flatMap in the methods.
        {
            System.out.println("Approach 2.1: ");
            {
                // Advantage: You can use Comprehension Pattern, if methods returning Result, but they are not taking Result as arg.
                getDocument(fileLocation)
                        .flatMap(doc -> getRootElement(doc))
                        .flatMap(rootEle -> getStaffElements(rootEle))
                        .map(staffEles -> getFinalResult(staffEles))
                        .forEach(fr -> processFinalResult(fr).execute());
            }
            System.out.println();

            System.out.println("Approach 2.2: I wouldn't choose this approach. Approach 2.1 is simpler and works in all cases.");
            {
                Result<Document> document = getDocument(fileLocation);
                document.forEachOrFail(doc -> getRootElement(doc)
                        .forEachOrFail(rootEle ->
                                getStaffElements(rootEle)
                                        .forEachOrFail(staffEles -> Result.of(getFinalResult(staffEles))
                                                .forEachOrFail(fr -> processFinalResult(fr).execute())
                                        )
                        )
                )
                .forEach(errorMessage -> System.out.println(errorMessage));
            }

            System.out.println();
            {
                // Let's say you got a document object from somewhere.
                Document document = null;

                Result.of(document) // This is fine
                        .flatMap(doc -> getRootElement(doc))
                        .flatMap(rootEle -> getStaffElements(rootEle))
                        .map(staffEles -> getFinalResult(staffEles))
                        .forEachOrFail(fr -> processFinalResult(fr).execute())
                        .forEach(errorMessage -> System.out.println(errorMessage));

                getRootElement(document)// but this is not fine. it can throw NullPointerException. So, don't do this.
                        .flatMap(rootEle -> getStaffElements(rootEle))
                        .map(staffEles -> getFinalResult(staffEles))
                        .forEachOrFail(fr -> processFinalResult(fr).execute())
                        .forEach(errorMessage -> System.out.println(errorMessage));


            }
        }


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

    // Method that takes Result as an arg
    private static Result<Element> getRootElement(Result<Document> document) {
        return document.flatMap(doc -> {
            try {
                return Result.success(doc.getRootElement());
            } catch (Exception e) {
                return Result.failure(e.getMessage());
            }
        });
    }

    // Method that takes normal object (not wrapped by Result) as an arg
    private static Result<Element> getRootElement(Document document) {
        try {
            return Result.success(document.getRootElement());
        } catch (Exception e) {
            return Result.failure(e.getMessage());
        }
    }

    // Method that takes normal object (not wrapped by Result) as an arg
    private static Result<List<Element>> getStaffElements(Result<Element> rootElement) {
        return rootElement.map(re -> re.getChildren());
    }

    // Method that takes normal object (not wrapped by Result) as an arg
    private static Result<List<Element>> getStaffElements(Element rootElement) {
        try {
            return Result.success(rootElement.getChildren());
        } catch (Exception e) {
            return Result.failure(e.getMessage());
        }
    }

    // Method that takes object wrapped by Result as an argument
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

    // Method that takes normal object (not wrapped by Result) as an arg
    private static List<String> getFinalResult(List<Element> staffs) {
        List<String> finalResult = staffs.stream().map(staff -> {
            String firstName = staff.getChild("firstname").getText();
            String lastName = staff.getChild("lastname").getText();
            String email = staff.getChild("email").getText();
            String salary = staff.getChild("salary").getText();

            return String.format("FirstName: %s, LastName: %s, Email: %s, Salary: %s", firstName, lastName, email, salary);

        }).collect(Collectors.toList());


        return finalResult;

    }

    // Method that takes object wrapped by Result as an argument
    private static <T> Executable processFinalResult(Result<List<T>> finalResult) {
        return () -> finalResult.forEach(fr -> fr.stream().forEach(str -> System.out.println(str)));
    }

    // Method that takes normal object (not wrapped by Result) as an arg
    private static <T> Executable processFinalResult(List<T> finalResult) {
        return () -> finalResult.stream().forEach(str -> System.out.println(str));
    }

}
