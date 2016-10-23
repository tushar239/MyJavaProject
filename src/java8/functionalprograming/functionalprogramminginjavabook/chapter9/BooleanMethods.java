package java8.functionalprograming.functionalprogramminginjavabook.chapter9;

import java.util.function.Supplier;

/**
 * @author Tushar Chokshi @ 9/25/16.
 */
public class BooleanMethods {

    public static void main(String[] args) {
            /*
                Obviously, the or method is not equivalent to the || operator.
                The difference is that the || evaluates its operand lazily, which means the second operand is not evaluated if the first one is true, since it is not necessary for computing the result.
                But the or method evaluates its arguments strictly, which means that the second argument is evaluated even if its value is not needed, so the IllegalStateException is always thrown.

                Implementing laziness in Java is not fully possible, but we can produce a good approximation using the Supplier class.

             */
        System.out.println(getFirst() || getSecond()); // true
        //System.out.println(or(getFirst(), getSecond())); // Exception in thread "main" java.lang.IllegalStateException
        System.out.println(or(() -> getFirst(), () -> getSecond())); // true
    }

    public static boolean getFirst() {
        return true;
    }

    public static boolean getSecond() {
        throw new IllegalStateException();
    }

    public static boolean or(boolean a, boolean b) {
        return a ? true : b ? true : false;
    }

    public static boolean or(Supplier<Boolean> a, Supplier<Boolean> b) {
        return a.get() ? true : b.get() ? true : false;
    }

    public static boolean and(boolean a, boolean b) {
        return a ? b ? true : false : false;
    }
}
