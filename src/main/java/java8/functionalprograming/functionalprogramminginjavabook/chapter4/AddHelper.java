package java8.functionalprograming.functionalprogramminginjavabook.chapter4;

import java8.functionalprograming.functionalprogramminginjavabook.chapter2.Function;

/**
 * @author Tushar Chokshi @ 9/3/16.
 */
public class AddHelper {

    // Normal Recursion that uses lot many stack frames
    public static int addRecursion(int x, int y) {
        return y == 0
                ? x
                : addRecursion(x+1, y-1) ;
    }

    public static int addTailRecursion(int x, int y, int result) {
        return y == 0
                ? result
                : addTailRecursion(result = x+1, y-1, result) ;
    }

    // Unlike to Scala, Java doesn't support using only one stack frame, if you use tail recursion.
    // To make it work with Java, use Java 8's Supplier.
    /*
        add(3, 2).eval()
            - returns SupplierContainer(()->add(4,1)), its get()
                - returns SupplierContainer(()->add(5,0)), its get()
                    - returns FinalValueContainer(5), its eval()
                        - returns 5

     */
    public static TailCall<Integer> addTailRecursionUsingJava8(int x, int y) {
        return y == 0
                ? TailCall.getFinalValueContainer(x)
                : TailCall.getSupplierContainer(() -> addTailRecursionUsingJava8(x + 1, y - 1));
    }

    public static void main(String[] args) {
        int x = 3;
        int y = 2;
        // Normal Recursion
        {
            System.out.println("Adding two numbers - Normal Recursion using multiple stack frames: "+addRecursion(x, y));
        }

        // Tail-Recursion
        {
            System.out.println("Adding two numbers - Tail Recursion using multiple stack frames: "+addTailRecursion(x, y,0));
        }
        // Tail-Recursion using Java
        {
            TailCall<Integer> tailCall = addTailRecursionUsingJava8(x, y);
            Integer result = tailCall.eval();
            System.out.println("Adding two numbers - Tail Recursion using single stack frame (using supplier) add.eval(): " + result);
        }

        // Supplying two input numbers using curried functions
        {
            {
                Function<Integer, Function<Integer, TailCall<Integer>>> addTwoNumbersUsingFunction =
                        a -> b -> addTailRecursionUsingJava8(a, b);
                TailCall<Integer> tailCall = addTwoNumbersUsingFunction.apply(x).apply(y);
                Integer result = tailCall.eval();
                System.out.println("Supplying two input numbers using curried functions - Tail Recursion using single stack frame (using supplier) add.eval(): " + result);
            }
            // or
            {
                Function<Integer, Function<Integer, Integer>> addTwoNumbersUsingFunction =
                        a -> b -> {
                            TailCall<Integer> tailCall = addTailRecursionUsingJava8(a, b);
                            return tailCall.eval();
                        };
                Integer result = addTwoNumbersUsingFunction.apply(x).apply(y);
                System.out.println("Supplying two input numbers using curried functions - Tail Recursion using single stack frame (using supplier) add.eval(): " + result);
            }

        }
    }

}
