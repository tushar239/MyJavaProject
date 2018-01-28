package java8.functionalprograming.functionalprogramminginjavabook.chapter3;

import java8.functionalprograming.functionalprogramminginjavabook.chapter13.outputtingdata.Effect;
import java8.functionalprograming.functionalprogramminginjavabook.chapter7.Result;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.regex.Pattern;

/**
 * @author Tushar Chokshi @ 8/29/16.
 */
// This class shows how can we replace if-elseif-else blocks using Matchers
// And how can we use Consumer to avoid functional method creating a side-effect.
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


    public static void main(String... args) {
        // Replacing above if-else conditions using Matchers.
        // Each Matcher can reserve predicate and supplier/consumer as arguments based on requirement
        // e.g. Case.mcase(predicate, supplier/consumer)
        // here, at this point in the book, we don't know anything about predicate, so supplier is used.
        // To see how to use Java 8 Predicate, see next example.
        Function<String, Result<String>> emailChecker =
                (s) -> Case.match(
                        Case.mcase(() -> Result.success(s)),
                        Case.mcase(() -> s == null, () -> Result.failure("email must not be null")),
                        Case.mcase(() -> s.length() == 0, () ->
                                Result.failure("email must not be empty")),
                        Case.mcase(() -> !emailPattern.matcher(s).matches(), () -> Result.failure("email " + s + " is invalid."))
                );
        emailChecker.apply("this.is@my.email").bind(success, failure);
        emailChecker.apply(null).bind(success, failure);
        emailChecker.apply("").bind(success, failure);
        emailChecker.apply("john.doe@acme.com").bind(success, failure);


        // Java 8 style replacing if-else conditions using Matchers.
        String email = "abcgmail.com";

        // see, I am using Consumer as a result of a Matcher to cover up the side-effect.
        Matcher<String, Consumer<String>> matcher1 = new Matcher<>(email1 -> email1 == null, (email1) -> System.out.println("email must not be null"));
        Matcher<String, Consumer<String>> matcher2 = new Matcher<>(email1 -> email1.length() == 0, (email1) -> System.out.println("email must not be empty"));
        Matcher<String, Consumer<String>> matcher3 = new Matcher<>(email1 -> !emailPattern.matcher(email1).matches(), (email1) -> System.out.println("email " + email1 + " is invalid."));
        List<Matcher<String, Consumer<String>>> matchers = new LinkedList<>();
        matchers.add(matcher1);
        matchers.add(matcher2);
        matchers.add(matcher3);

        CompositeMatchers<Consumer<String>> compositeMatchers = new CompositeMatchers(matchers);
        Consumer<String> finalResult = compositeMatchers.execute(email, (email1) -> System.out.println("email is valid"));
        finalResult.accept(email);

    }
}