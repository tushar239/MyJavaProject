package algorithms._1array_stack_queue.geeksforgeeks.pointers_sorting_stack_queue_string.stack.easy;

import java.util.Stack;

/*
    Sorting array using Stacks

    https://www.geeksforgeeks.org/sorting-array-using-stacks/


    Input :  8 5 7 1 9 12 10
    Output : 1 5 7 8 9 10 12
    Explanation : Output is sorted element set

    Input :  7 4 10 20 2 5 9 1
    Output : 1 2 4 5 7 9 10 20

*/
public class _1SortArrayUsingStacks {


    public static void main(String[] args) {
        int[] A = {8, 5, 7, 1, 9, 12, 10};
        sort(A);
    }

    private static void sort(int[] A) {
        if (A == null || A.length == 0 || A.length == 1) {
            System.out.println("Array is already sorted");
        }

        Stack<Integer> finalStack = new Stack<>();

        Stack<Integer> stagingStack = new Stack<>();

        finalStack.push(A[0]); // pushing first element to finalStack

        for (int i = 1; i < A.length; i++) { // starting from 2nd element

            int elementToPushToFinalStack = A[i];

            if (elementToPushToFinalStack >= finalStack.peek()) {

                /* if elementToPush >= finalStack's top element && > stagingStack's top element,
                        transfer elements from staging to final stack till stagingStack's top element becomes greater.
                */
                if (!stagingStack.isEmpty() && elementToPushToFinalStack > stagingStack.peek()) {
                    while (!stagingStack.isEmpty() && stagingStack.peek() <= elementToPushToFinalStack) {
                        finalStack.push(stagingStack.pop());
                    }
                }

                finalStack.push(elementToPushToFinalStack);

            }
            /*
                if elementToPush < finalStack's top element
                    transfer elements from finalStack to stagingStack till finalStack's top element becomes lesser or equal.
            */
            else {
                while (!finalStack.isEmpty() && elementToPushToFinalStack < finalStack.peek()) {
                    stagingStack.push(finalStack.pop()); // pop elements from finalStack and put into staging stack
                }
                finalStack.push(elementToPushToFinalStack);
            }
        }

        while (!stagingStack.isEmpty()) {
            finalStack.push(stagingStack.pop());
        }

        for (Integer num : finalStack) {
            System.out.print(num + ",");
        }
        System.out.println();
    }
}
