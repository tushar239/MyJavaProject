package algorithms._1array_stack_queue.geeksforgeeks.pointers_sorting_stack_queue_string.stack.medium;

import java.util.Stack;

import static algorithms.utils.ArrayUtils.printArray;

/*
  Print next greater number of Q queries

  https://www.geeksforgeeks.org/print-next-greater-number-q-queries/

  Rephrasing above problem statement:
  For each element, find total number of greater elements in increasing order starting from next element.

  Input : a[] = {3, 4, 2, 7, 5, 8, 10, 6}
      q = 2
      index = 0,
      index = 5

      Output: 4
              1

  Explanation: The next greater elements to the right of 3(index 0) are 4, 7, 8, 10.
               The next greater elements to the right of 8(index 5) are 10.


  This problem is same as FindFirstGreaterElementFromNextElementForEveryElement.java with some enhancement.
 */
public class _1FindNumberOfGreaterElementsInIncreasingOrderForEachElement {

    public static void main(String[] args) {
        int[] A = {3, 4, 2, 7, 5, 8, 10, 6};

        int[] numberOfNextGreaterElementsForEachElement = findNumberOfGreaterElementsForEachElement(A);

        System.out.println();

        System.out.println("Array containing total number next greater elements in increasing order from the next element for each element");
        printArray(numberOfNextGreaterElementsForEachElement, () -> "%d -> %d");
    }

    /*

    This method gets input as follows.

          array element index   next greater element's index

            0               ->      1
            1               ->      3
            2               ->      3
            3               ->      5
            4               ->      5
            5               ->      6
            6               ->      -1
            7               ->      -1


    - loop this input array from last to first index

      if(value == -1) {

        result[i] = 0;

      } else {

        result[i] = 1 + result[ input[i] ];
      }

    This method returns following 'result' array

        array element index     total number of greater elements in increasing order to the right of the element

        0                   ->          4
        1                   ->          3
        2                   ->          3
        3                   ->          2
        4                   ->          2
        5                   ->          1
        6                   ->          0
        7                   ->          0

    */
    private static int[] findNumberOfGreaterElementsForEachElement(int[] A) {
        if (A == null || A.length == 0) return new int[0];

        int[] finalResult = new int[A.length];

        // this array contains total number of greater elements in increasing order for each element index.
        int[] intermediateResult = findNextGreaterElementForEachElement(A);

        System.out.println("Array containing next greater element's index for each element index");
        printArray(intermediateResult, () -> "%d -> %d");


        for (int i = intermediateResult.length - 1; i >= 0; i--) {

//            System.out.println(i + "->" + intermediateResult[i]);

            if (intermediateResult[i] == -1) {
                finalResult[i] = 0;
            } else {
                finalResult[i] = 1 + finalResult[intermediateResult[i]];
            }
        }

//        System.out.println();

        return finalResult;
    }

    /*
        This method returns an array containing an index of next greater element for each element index

        e.g.

          array element index   next greater element's index

            0               ->      1
            1               ->      3
            2               ->      3
            3               ->      5
            4               ->      5
            5               ->      6
            6               ->      -1
            7               ->      -1
     */
    private static int[] findNextGreaterElementForEachElement(int[] A) {
        if (A == null || A.length == 0) return new int[0];

        int[] nextGreaterElementIndices = new int[A.length];

        Stack<Element> stack = new Stack<>();

        for (int i = 0; i < A.length; i++) {
            int element = A[i];

            if (stack.isEmpty()) {
                stack.push(new Element(i, element));
            } else {

                while (!stack.isEmpty() && stack.peek().getValue() < element) {
                    Element poppedElement = stack.pop();
                    nextGreaterElementIndices[poppedElement.getIndex()] = i;
                }

                stack.push(new Element(i, element));
            }
        }

        while (!stack.isEmpty()) {
            Element poppedElement = stack.pop();
            nextGreaterElementIndices[poppedElement.getIndex()] = -1;
        }

        return nextGreaterElementIndices;
    }

    private static class Element {
        int index;
        int value;

        public Element(int index, int value) {
            this.index = index;
            this.value = value;
        }

        public int getIndex() {
            return index;
        }

        public int getValue() {
            return value;
        }
    }
}
