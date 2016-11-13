package java8.functionalprograming.functionalprogramminginjavabook.chapter15.properties;

import java8.functionalprograming.functionalprogramminginjavabook.chapter7.Result;

/**
 * @author Tushar Chokshi @ 11/11/16.
 */
public class Util {
    public static Result<Integer> getAsInteger(String str) {
        try {
            return Result.success(Integer.parseInt(str));
        } catch (NumberFormatException nfe) {
            return Result.failure("Non-number string cannot be converted to number");
        }
    }

    public static Result<String> getAsString(Object obj) {
        try {
            return Result.success(String.valueOf(obj));
        } catch (NumberFormatException nfe) {
            return Result.failure("Non-string object cannot be converted to string");
        }
    }
}
