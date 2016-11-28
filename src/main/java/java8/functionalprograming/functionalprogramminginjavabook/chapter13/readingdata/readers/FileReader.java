package java8.functionalprograming.functionalprogramminginjavabook.chapter13.readingdata.readers;

import java8.functionalprograming.functionalprogramminginjavabook.chapter7.Result;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * @author Tushar Chokshi @ 11/27/16.
 */
// pg 383
public class FileReader extends AbstractReader {
    private FileReader(BufferedReader reader) {
        super(reader);
    }

    public static Result<IReader> fileReader(String path) {
        try {
            return Result.success(new FileReader(new BufferedReader(
                    new InputStreamReader(new FileInputStream(new File(path))))));
        } catch (Exception e) {
            return Result.failure(e);
        }
    }
}