package java8.functionalprograming.functionalprogramminginjavabook.chapter7;

import java8.functionalprograming.functionalprogramminginjavabook.chapter13.Effect;

import java.util.function.Function;
/*
This is a example of how to operate on output of some operation by wrapping it in some context (Result).
Client program doesn't care whether output value (Result) is type of Empty, Success or Failure. It doesn't have to check for null (empty) also.
Client just calls successive methods (flatMap, forEach etc) on the Context (Result) and not on the actual value of the operation.
This is a great feature provided by Functional Programming.
*/

public class ResultTest {
    public static void main(String... args) {
        Result<Integer> ra = Result.success(4);
        Result<Integer> rb = Result.success(0);

        // This example shows how to deal with the output by wrapping it in some context (Result)
        Function<Integer, Result<Double>> inverse = x -> x != 0
                ? Result.success((double) 1 / x)
                : Result.failure("Division by 0");

        Effect<Double> print = System.out::println;

        Result<Double> rt1 = ra.flatMap(inverse);
        Result<Double> rt2 = rb.flatMap(inverse);

        // let that context operate on the output value instead of client program doing that.
        // client program doesn't care whether output value is empty, success or failure.
        System.out.print("Inverse of 4: ");
        rt1.forEachOrFail(System.out::println).forEach((s) -> ResultTest.log(s));
        System.out.print("Inverse of 0: ");
        rt2.forEachOrFail(System.out::println).forEach((s) -> ResultTest.log(s));
    }

    private static void log(String s) {
        System.out.println(s);
    }

    /*
    O/P:
    Inverse of 4: 0.25
    Inverse of 0: Division by 0
     */
}