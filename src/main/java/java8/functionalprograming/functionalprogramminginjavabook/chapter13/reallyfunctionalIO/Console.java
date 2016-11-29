package java8.functionalprograming.functionalprogramminginjavabook.chapter13.reallyfunctionalIO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Tushar Chokshi @ 8/28/16.
 */
public class Console {

    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static IO<String> readLine(Nothing nothing) {
        return () -> {
            try {
                return br.readLine();
            } catch (IOException e) {
                throw new IllegalStateException((e));
            }
        };
    }

    public static IO<Nothing> printLine(Object o) {
        return () -> { // run() method
            System.out.println(o.toString());
            return Nothing.instance;
        };
    }
}