package java8.functionalprograming.functionalprogramminginjavabook.chapter15.assertions;

import java8.functionalprograming.functionalprogramminginjavabook.chapter7.Result;

import java.util.Optional;
import java.util.function.Supplier;

/*
    Chapter 15 (15.1 Using assertions to validate)

    Important concept of how to make a method functional that has a side-effect?

    Java suggests one of the below options to avoid division by 0 error.
 */
public class AvoidAssertionNullChecksExceptions {
    double inverse_with_assertion(int x) {  // using assertion. assertion can be disabled at runtime by using 'java -da ...' option.
        assert x != 0; // if x == 0, then it will throw AssertionException, which is a side-effect.
        return 1.0 / x;
    }

    double inverse_throwing_exception(int x) {
        if (x != 0) throw new IllegalArgumentException("div. By 0"); // by adding null check. throwing an exception is a side-effect that makes a method non-functional.
        return 1.0 / x;
    }


    /*
    How will you make above method functional?
    Functional method should not create a side effect like throwing an exception.
    Using Result class, you can wrap an exception easily. Using Java 8's Optional, it is a bit tough. You need to return a Supplier.
    */

    // Using Result
    Result<Double> inverse_functional(int x) {
        return x == 0
                ? Result.failure("div. By 0")
                : Result.success(1.0 / x);
    }

    // Using Optional
    Supplier<Optional<Double>> inverse_Java8(int x) {
        if (x == 0)
            return () -> {throw new RuntimeException("can not divide by 0");};
        return () -> Optional.<Double>ofNullable(1.0 / x); // Instead of throwing an exception from a method, return a Supplier that throws an exception
    }

}
