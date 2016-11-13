package java8.functionalprograming;

/**
 * @author Tushar Chokshi @ 5/15/16.
 */
// Now interface can have static methods and non-static(default) methods with implementations.
// default methods can be overridden.

public interface MyInterface {
    void myMethod();

    default void myAnotherMethod() {
        System.out.println("Inside myAnotherMethod");
    }

    static void myStaticMethod() {
        System.out.println("Inside myStaticMethod");
    }
    // It returns a new instance of MyInterface that calls this instance's myMethod() and after instance's myMethod()
    // This helps chaining
    default MyInterface andThen(MyInterface after) {
        return () -> {
            myMethod();
            after.myMethod();
        };
    }
}
