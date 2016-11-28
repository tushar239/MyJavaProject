package java8.functionalprograming.functionalprogramminginjavabook.chapter13.readingdata.readers;

import java8.functionalprograming.functionalprogramminginjavabook.chapter12.Tuple;
import java8.functionalprograming.functionalprogramminginjavabook.chapter7.Result;

import java.io.BufferedReader;

/**
 * @author Tushar Chokshi @ 8/28/16.
 */

/*
The class will be built with a reader, allowing for different sources of input.
*/

public class AbstractReader implements IReader {
    protected final BufferedReader reader;

    protected AbstractReader(BufferedReader reader) {
        this.reader = reader;
    }

    // The readString method will just read a line from the reader and return either a Result.Empty if the line was empty, a Result.Success if some data was obtained, or a Result.Failure if something went wrong.
    @Override
    public Result<Tuple<String, IReader>> readString() {
        try {
            String s = reader.readLine();
            return s.length() == 0
                    ? Result.empty()
                    : Result.success(new Tuple<>(s, this));
        } catch (Exception e) {
            return Result.failure(e);
        }
    }

/*    public Optional<Tuple<String, Input>> readString1() {
        try {
            String s = reader.readLine();
            return s.length() == 0
                    ? Optional.empty()
                    : Optional.of(new Tuple<>(s, this)); // same as Result.success(...)
        } catch (Exception e) {
            return Optional.of(e); // Optional provides support of Empty and Success, but not for failure.
        }
    }*/

    @Override
    public Result<Tuple<Integer, IReader>> readInt() {
        try {
            String s = reader.readLine();
            return s.length() == 0
                    ? Result.empty()
                    : Result.success(new Tuple<>(Integer.parseInt(s), this));
        } catch (Exception e) {
            return Result.failure(e);
        }
    }
}