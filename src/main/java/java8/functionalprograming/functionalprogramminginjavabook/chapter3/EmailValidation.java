package java8.functionalprograming.functionalprogramminginjavabook.chapter3;

import java8.functionalprograming.functionalprogramminginjavabook.chapter13.outputtingdata.Effect;
import java8.functionalprograming.functionalprogramminginjavabook.chapter7.Result;

import java.util.function.Function;
import java.util.regex.Pattern;

/**
 * @author Tushar Chokshi @ 8/29/16.
 */
public class EmailValidation {
    static Pattern emailPattern =
            Pattern.compile("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$");
    static Effect<String> success = s ->
            System.out.println("Mail sent to " + s);
    static Effect<String> failure = s ->
            System.err.println("Error message logged: " + s);

    /*
    static Function<String, Result<String>> emailChecker = s -> {
        if (s == null) {
          return Result.failure("email must not be null");
        } else if (s.length() == 0) {
          return Result.failure("email must not be empty");
        } else if (emailPattern.matcher(s).matches()) {
          return Result.success(s);
        } else {
          return Result.failure("email " + s + " is invalid.");
        }
    };
     */
    // Replacing above if-else conditions using Matchers.
    // Each Matcher can take predicate and supplier/consumer as arguments based on requirement
    // e.g. Case.mcase(predicate, supplier/consumer)
    // here, at this point in the book, we don't know anything about predicate, so supplier is used.
    static Function<String, Result<String>> emailChecker =
            (s) -> Case.match(
                    Case.mcase(() -> Result.success(s)),
                    Case.mcase(() -> s == null, () -> Result.failure("email must not be null")),
                    Case.mcase(() -> s.length() == 0, () ->
                            Result.failure("email must not be empty")),
                    Case.mcase(() -> !emailPattern.matcher(s).matches(), () -> Result.failure("email " + s + " is invalid."))
            );

    public static void main(String... args) {
        emailChecker.apply("this.is@my.email").bind(success, failure);
        emailChecker.apply(null).bind(success, failure);
        emailChecker.apply("").bind(success, failure);
        emailChecker.apply("john.doe@acme.com").bind(success, failure);
    }
}