package algorithms._1array.geeksforgeeks.pointers_sorting_stack.hard._3lesser_greater_elements;

import java.util.Stack;

/*
    Next Greater Element

    https://www.geeksforgeeks.org/next-greater-element/

    Given an array, print the Next Greater Element (NGE) for every element.
    The Next greater Element for an element x is the first greater element on the right side of x in array.
    Elements for which no greater element exist, consider next greater element as -1.

    [11, 14, 13, 21, 3]

    3 -> -1
    21 -> -1
    13 -> 21
    14 -> 21
    11 -> 14

    You might think that this is similar to FindASortedSubsequenceOfSize3InAnArray.java with little bit of improvement.
    But that is not true.


    IMPORTANT:
    This is the perfect example of using stack. It gives you a trick when to use a stack.
    Whenever you need to find 'first possible ...' element in remaining array after current element, then think of using stack.
*/
public class _3FindFirstGreaterElementFromNextElementForEveryElement {

    public static void main(String[] args) {
        {

            int[] A = {11, 14, 13, 21, 3};
            //find_wrong_way(A);
            find_using_stack(A);

        }
        System.out.println();
        {
            int[] A = {14, 12, 13, 15};
            //find_wrong_way(A);
            /*
            Wrong output

                15->-1
                13->15
                12->13
                14->-1

            Expected output

                15->-1
                13->15
                12->13
                14->15

            */

            find_using_stack(A);
        }
    }

    private static void find_wrong_way(int[] A) {

        if (A == null || A.length == 0) return;

        if (A.length == 1) {
            System.out.println(A[A.length - 1] + "->" + "-1");
            return;
        }

        int greaterElementIndex = A.length - 1;
        int possibleGreaterElementIndex = greaterElementIndex;

        System.out.println(A[A.length - 1] + "->" + "-1");

        for (int i = A.length - 2; i >= 0; i--) {
            if (A[i] < A[possibleGreaterElementIndex]) {
                System.out.println(A[i] + "->" + A[possibleGreaterElementIndex]);
                greaterElementIndex = possibleGreaterElementIndex;
            } else if (A[i] < A[greaterElementIndex]) {
                System.out.println(A[i] + "->" + A[greaterElementIndex]);
            } else {
                System.out.println(A[i] + "->" + "-1");
                greaterElementIndex = i;
            }
            possibleGreaterElementIndex = i;
        }
    }

    private static void find_using_stack(int[] A) {
        if (A == null || A.length == 0) return;

        if (A.length == 1) {
            System.out.println(A[A.length - 1] + "->" + "-1");
            return;
        }

        Stack<Integer> stack = new Stack<>();

        stack.push(A[0]);

        for (int i = 1; i < A.length; i++) {
            int possibleGreaterElement = A[i];

            Integer top = stack.peek();

            if (possibleGreaterElement > top) {

                while (possibleGreaterElement > top && !stack.isEmpty()) {
                    top = stack.peek();
                    System.out.println(top + "->" + possibleGreaterElement);
                    stack.pop();
                    // top = stack.peek(); // IMPORTANT: Be careful using stack.peek() after using stack.pop() because after pop(), stack might be empty.
                }
                stack.push(possibleGreaterElement);
            } else {
                stack.push(possibleGreaterElement);
            }
        }

        while (!stack.isEmpty()) {
            Integer popped = stack.pop();
            System.out.println(popped + "->" + -1);
        }

    }
}
