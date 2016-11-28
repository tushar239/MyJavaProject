package java8.functionalprograming.functionalprogramminginjavabook.chapter13.readingdata.readers;

import java8.functionalprograming.functionalprogramminginjavabook.chapter12.Tuple;
import java8.functionalprograming.functionalprogramminginjavabook.chapter7.Result;

/**
 * @author Tushar Chokshi @ 8/28/16.
 */
// pg 378 - This interface is same as book's 'Input' interface
public interface IReader {
    // readInt and readString will input an integer and a string, respectively.
    Result<Tuple<String, IReader>> readString();

    Result<Tuple<Integer, IReader>> readInt();

    // These methods allow passing a message as a parameter, which can be useful for prompting the user (in child class ConsoleReader).
    // However, the provided default implementations ignore the message.
    default Result<Tuple<String, IReader>> readString(String message) {
        return readString();
    }

    default Result<Tuple<Integer, IReader>> readInt(String message) {
        return readInt();
    }
}