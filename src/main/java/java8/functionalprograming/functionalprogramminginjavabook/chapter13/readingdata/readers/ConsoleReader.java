package java8.functionalprograming.functionalprogramminginjavabook.chapter13.readingdata.readers;

import java8.functionalprograming.functionalprogramminginjavabook.chapter12.Tuple;
import java8.functionalprograming.functionalprogramminginjavabook.chapter7.Result;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author Tushar Chokshi @ 8/28/16.
 */
// pg 380
public class ConsoleReader extends AbstractReader {
    protected ConsoleReader(BufferedReader reader) {
        super(reader);
    }

    // The two default readString and readInt methods are overridden to display the user prompt (System.out.println).
    @Override
    public Result<Tuple<String, IReader>> readString(String message) {
        System.out.print(message + " ");
        return readString();
    }

    @Override
    public Result<Tuple<Integer, IReader>> readInt(String message) {
        System.out.print(message + " ");
        return readInt();
    }

    // The static factory method provides a reader to the underlying abstract class.
    public static ConsoleReader consoleReader() {
        return new ConsoleReader(new BufferedReader(new InputStreamReader(System.in)));
    }
}