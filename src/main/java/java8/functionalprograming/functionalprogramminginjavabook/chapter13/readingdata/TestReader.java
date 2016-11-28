package java8.functionalprograming.functionalprogramminginjavabook.chapter13.readingdata;

import java8.functionalprograming.functionalprogramminginjavabook.chapter12.Tuple;
import java8.functionalprograming.functionalprogramminginjavabook.chapter13.readingdata.vos.Person;
import java8.functionalprograming.functionalprogramminginjavabook.chapter13.readingdata.readers.ConsoleReader;
import java8.functionalprograming.functionalprogramminginjavabook.chapter13.readingdata.readers.FileReader;
import java8.functionalprograming.functionalprogramminginjavabook.chapter13.readingdata.readers.IReader;
import java8.functionalprograming.functionalprogramminginjavabook.chapter13.readingdata.readers.ScriptReader;
import java8.functionalprograming.functionalprogramminginjavabook.chapter13.readingdata.readers.utils.ReadInput;
import java8.functionalprograming.functionalprogramminginjavabook.chapter7.Result;
import java8.functionalprograming.functionalprogramminginjavabook.chapter9.Stream;

/**
 * @author Tushar Chokshi @ 8/28/16.
 */
public class TestReader {
    public static void main(String[] args) {
        // pg 380
        System.out.println("Reading from Console...");
        readingFromConsole();
        System.out.println();

        // pg 382
        System.out.println("Creating a stream of persons using ConsoleReader and unfold method...");
        consoleReader();
        System.out.println();

        System.out.println("Creating a stream of persons using FileReader and unfold method...");
        fileReader();
        System.out.println();

        System.out.println("Creating a stream of person using ScriptReader and unfold method... Not sure, but it is not working");
        //scriptReader();
    }

    protected static void readingFromConsole() {
        // First, the reader is created.
        IReader reader = ConsoleReader.consoleReader();

        // The readString method is called (with a user prompt) and returns a Result<Tuple<String, Input>>.
        // This result is then mapped to produce a Result<String>.

        // Why are you returning a Tuple<String, Input> ? why not just String?
        // If you see carefully, readString is calling Input class' reader.readLine(), so it is changing the state of reader and so the state of Input.
        // So, here we are doing the same thing as we saw Chapter 12's Generator.java's testInteger() method, which returns Random instance also along with the actual output
        Result<Tuple<String, IReader>> tupleResult = reader.readString("Enter your name: ");
        Result<String> rString = tupleResult.map(t -> t._1);

        // This line represents the business part of the program. This part may be functionally pure.
        Result<String> result = rString.map(s -> String.format("Hello, %s!", s));

        // Output either the result or an error message
        result.forEachOrFail(System.out::println).forEach(System.out::println);
    }

    protected static void consoleReader() {
        IReader reader = ConsoleReader.consoleReader();
        Stream<Person> stream = Stream.unfold(reader, in -> ReadInput.person(in)); // ReadConsole should create Result<Tuple<Person, Input>>
//            Result<Tuple<Person, Input>> personResult = ReadInput.person(input);
//            Result<Person> person = personResult.map(tuple -> tuple._1);
//
//            Stream<Person> stream = Stream.unfold(input, in -> in, in -> person.getOrElse(Person.apply(0, "Unknown", "Unknown"))); // ReadConsole should create Result<Tuple<Person, Input>>
        stream.toList().forEach(System.out::println);
            /*
            Enter your id:  1
            Enter your firstname:  T
            Enter your lastname:  C

            Enter your id:  2
            Enter your firstname:  M
            Enter your lastname:  C

            Enter your id:  3
            Enter your firstname:  S
            Enter your lastname:  C

            Enter your id:  exit

            ID: 1, First name: T, Last name: C
            ID: 2, First name: M, Last name: C
            ID: 3, First name: S, Last name: C
             */
    }

    protected static void fileReader() {
        Result<IReader> rInput = FileReader.fileReader("MyJavaProject/src/main/java/java8/functionalprograming/functionalprogramminginjavabook/chapter13/readingdata/person.txt");
        Result<Stream<Person>> rStream =
                rInput.map(input -> Stream.unfold(input, in -> ReadInput.person(in)));
        Result<String> stringResult =
                rStream.forEachOrFail(
                        stream -> stream.toList()
                                .forEach(person -> System.out.println(person))
                );
        stringResult.forEach(System.out::println);
    }

    protected static void scriptReader() {
        IReader reader = new ScriptReader(
                "0", "Mickey", "Mouse",
                "1", "Minnie", "Mouse",
                "2", "Donald", "Duck",
                "3", "Homer", "Simpson"
        );
        Stream<Person> stream =
                Stream.unfold(reader, in -> ReadInput.person(in));
        stream.toList().forEach(System.out::println);
    }
}
