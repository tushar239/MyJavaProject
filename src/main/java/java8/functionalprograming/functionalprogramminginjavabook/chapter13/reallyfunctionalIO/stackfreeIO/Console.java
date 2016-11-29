package java8.functionalprograming.functionalprogramminginjavabook.chapter13.reallyfunctionalIO.stackfreeIO;

import java8.functionalprograming.functionalprogramminginjavabook.chapter13.reallyfunctionalIO.Nothing;
import java8.functionalprograming.functionalprogramminginjavabook.chapter2.Function;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Tushar Chokshi @ 11/29/16.
 */
public class Console {

    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static StackFreeIO<String> readLine(Nothing nothing) {
        return new Suspend<>(() -> {
            try {
                return br.readLine();
            } catch (IOException e) {
                throw new IllegalStateException((e));
            }
        });
    }

    /**
     * A possible implementation of readLine as a function
     */
    public static Function<Nothing, StackFreeIO<String>> readLine_ = x -> new Suspend<>(() -> {
        try {
            return br.readLine();
        } catch (IOException e) {
            throw new IllegalStateException((e));
        }
    });
    /**
     * A simpler implementation of readLine as a function using a method reference
     */
    public static Function<Nothing, StackFreeIO<String>> readLine = (nothing) -> readLine(nothing);

    /**
     * A convenience helper method allowing calling the readLine method without
     * providing a Nothing.
     */
    public static StackFreeIO<String> readLine() {
        return readLine(Nothing.instance);
    }

    public static StackFreeIO<Nothing> printLine(Object s) {
        return new Suspend<>(() -> println(s));
    }

    private static Nothing println(Object s) {
        System.out.println(s);
        return Nothing.instance;
    }

    public static StackFreeIO<Nothing> printLine_(Object s) {
        return new Suspend<>(() -> {
            System.out.println(s);
            return Nothing.instance;
        });
    }

    public static Function<String, StackFreeIO<Nothing>> printLine_ =
            s -> new Suspend<>(() -> {
                System.out.println(s);
                return Nothing.instance;
            });
    public static Function<String, StackFreeIO<Nothing>> printLine = (s) -> printLine(s);
}
