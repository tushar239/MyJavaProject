package java8.functionalprograming.functionalprogramminginjavabook.chapter15.properties;

import java8.functionalprograming.functionalprogramminginjavabook.chapter7.Result;

import java.util.Optional;

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
    public static Optional<Integer> getAsIntegerReturningOptional(String str) {
        try {
            return Optional.ofNullable(Integer.parseInt(str));
        } catch (NumberFormatException nfe) {
            return Optional.empty(); // Optional doesn't have something like  Result.failure(...). If you want to throw an exception you need to return "() -> throw new RuntimeException(nfe);"
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
