package java8.functionalprograming;

/**
 * @author Tushar Chokshi @ 5/15/16.
 */
@FunctionalInterface
public interface MyFunctionalInterface {
    void myMethod();

    boolean equals(Object obj);

    int hashCode();

    String toString();

    // It returns a new instance of MyFunctionalInterface that calls this instance's myMethod() and after instance's myMethod()
    // This helps chaining.
    default MyFunctionalInterface andThen(MyFunctionalInterface another) {
        return () -> {
            myMethod();
            another.myMethod();
        };
    }

}
