package java8.functionalprograming.functionalprogramminginjavabook.chapter7;

import java8.functionalprograming.functionalprogramminginjavabook.chapter13.outputtingdata.Effect;

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
        rt1.forEachOrFail((msg) -> System.out.println(msg)).forEach((s) -> ResultTest.log(s));// 0.25
        System.out.print("Inverse of 0: ");
        rt2.forEachOrFail((msg) -> System.out.println(msg)).forEach((s) -> ResultTest.log(s));// Division by 0

        // or

        System.out.print("Inverse of 4: ");
        Result<RuntimeException> runtimeExceptionResult = rt1.forEachOrException(System.out::println);
        runtimeExceptionResult.forEach(exc -> System.out.printf(exc.getMessage()));// 0.25


        System.out.print("Inverse of 0: ");
        runtimeExceptionResult = rt2.forEachOrException((x) -> System.out.println(x));
        runtimeExceptionResult.forEach(exc -> System.out.printf(exc.getMessage()));// Division by 0

    }

    private static void log(String s) {
        System.out.println(s);
    }

}