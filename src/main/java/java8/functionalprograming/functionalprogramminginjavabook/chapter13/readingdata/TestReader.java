package java8.functionalprograming.functionalprogramminginjavabook.chapter13.readingdata;

import java8.functionalprograming.functionalprogramminginjavabook.chapter12.Tuple;
import java8.functionalprograming.functionalprogramminginjavabook.chapter7.Result;

/**
 * @author Tushar Chokshi @ 8/28/16.
 */
public class TestReader {
    public static void main(String[] args) {
        // First, the reader is created.
        Input input = ConsoleReader.consoleReader();

        // The readString method is called (with a user prompt) and returns a Result<Tuple<String, Input>>.
        // This result is then mapped to produce a Result<String>.

        // Why are you returning a Tuple<String, Input> ? why not just String?
        // If you see carefully, readString is calling Input class' reader.readLine(), so it is changing the state of reader and so the state of Input.
        // So, here we are doing the same thing as we saw Chapter 12's Generator.java's testInteger() method, which returns Random instance also along with the actual output
        Result<Tuple<String, Input>> tupleResult = input.readString("Enter your name: ");
        Result<String> rString = tupleResult.map(t -> t._1);

        // This line represents the business part of the program. This part may be functionally pure.
        Result<String> result = rString.map(s -> String.format("Hello, %s!", s));

        // Output either the result or an error message
        result.forEachOrFail(System.out::println).forEach(System.out::println);
    }
}
