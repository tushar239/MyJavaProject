package algorithms.crackingcodinginterviewbook._5recursion_and_dynamic_programming;

/*
pg 351 of Cracking Coding Interview book

Recursive Multiply:
Write a recursive function to multiply two numbers without using the * operator or( / operator).
You can use addition, subtraction and bit shifting, but you should minimize the number of those operations.

Important Recursion concept:
Sometimes it is obvious to reduce the problem by half like Binary Search.
But sometimes, it is challenging to think about reducing the problem by half instead of 1.
This algorithm teaches you the advantage of dividing the problem into half instead of 1.
If you can divide the problem into half, then time complexity can be O(log(n)) instead of O(n).
*/
public class _5RecursiveMultiply {

    public static void main(String[] args) {
        int smaller = 3;
        int bigger = 5;
        {
            int result = simplest_most_inefficient_multiplication_that_uses_n_times_addition_operator(smaller, bigger);
            System.out.println("Most inefficient way that uses + operator n times where n is number of smaller number: " + result);
        }
        {
            int result = simplest_and_efficient_it_doesnt_require_memoization(smaller, bigger);
            System.out.println("Simplest and efficient way that uses / operator and + operator log(smaller) times: " + result);
        }

        {
            int result = efficient_that_requires_memoization(smaller, bigger);
        }
    }

    // see book
    private static int efficient_that_requires_memoization(int smaller, int bigger) {
        return 0;
    }

    private static int simplest_most_inefficient_multiplication_that_uses_n_times_addition_operator(int smaller, int bigger) {
        if (smaller == 0) return 0;
        if (smaller == 1) return bigger;

        return bigger + simplest_most_inefficient_multiplication_that_uses_n_times_addition_operator(smaller - 1, bigger);
    }

    /*
    This solution doesn't require any memoization. you will realize it if your draw recursion tree.

                    smaller=15

                    m(15)
                    |
                    m(7)
                    |
                    m(3)
                    |
                    m(1)

     Just like Binary Search algorithm, problem is divided into two halves. One half is used, another is not.
     Height of the tree is log(smaller). At every node of the tree, constant time operation is happening.
     So, time complexity is O(log(smaller))

     '/' operator is used only log(smaller) times. '+' operator is used only log(smaller) times, if smaller is an even number, otherwise 2log(smaller) times.

     Time complexity = O(log(smaller))
     */
    private static int simplest_and_efficient_it_doesnt_require_memoization(int smaller, int bigger) {
        if (smaller == 0) return 0;
        if (smaller == 1) return bigger;

        int halfOfSmaller = smaller / 2;

        int resultOfHalf = simplest_and_efficient_it_doesnt_require_memoization(halfOfSmaller, bigger);

        if (smaller % 2 == 1) {
            return resultOfHalf + resultOfHalf + bigger;
        }
        return resultOfHalf + resultOfHalf;
    }
}
