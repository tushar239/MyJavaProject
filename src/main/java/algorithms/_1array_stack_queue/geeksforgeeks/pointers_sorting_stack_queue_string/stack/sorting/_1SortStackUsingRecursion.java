package algorithms._1array_stack_queue.geeksforgeeks.pointers_sorting_stack_queue_string.stack.sorting;

import java.util.Stack;

/*
    Sort a stack using recursion

    https://www.geeksforgeeks.org/sort-a-stack-using-recursion/

    It's a simple concept of reducing the problem by one.

   ___
   |3|  --- reduce the problem by 1 by popping first element
   ---
   ---
   |1|  --- sort rest of the stack
   |4|
   |2|
   |5|
   ---

   Assuming that rest of the stack will be sorted using recursion

   ___
   |3|  --- reduce the problem by 1 by popping first element
   ---
   ---
   |5|  --- sorted rest of the stack
   |4|
   |2|
   |1|
   ---

   Now, to insert 3 in sorted stack, you need to iterate through the stack and put elements > 3 to staging stack. Then insert 3 to stack. Then move elements from staging stack to original stack.


   NOTE:
   Queue can also be sorted using two additional stacks.

   Queue can also be sorted without using additional data structure or recursion (See SortQueueWithoutUsingAnyExtraSpace.java).
   But Stack can't be sorted without using additional data structure.
*/
public class _1SortStackUsingRecursion {

    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(4);
        stack.push(3);
        stack.push(5);

        sort(stack);

        for (Integer num : stack) {
            System.out.println(num + ",");
        }
//        System.out.println(count);
    }

    //    static int count = 0;
    private static void sort(Stack<Integer> stack) {
//        count++;
        if (stack == null || stack.size() == 0 || stack.size() == 1) return;

        int poppedElement = stack.pop();

        sort(stack);

        if (!stack.isEmpty()) {

            Stack<Integer> stagingStack = new Stack<>(); // geeksforgeeks site solution uses list for staging. I am using stack.

            while (!stack.isEmpty() && stack.peek() > poppedElement) {
                stagingStack.add(stack.pop());
            }

            stack.push(poppedElement);

            for (Integer num : stagingStack) {
                stack.push(num);
            }

        } else {
            stack.push(poppedElement);
        }
    }
}
