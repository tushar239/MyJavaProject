package algorithms._1array.geeksforgeeks.pointers_sorting_stack.stack;

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

   Now, to insert 3 in sorted stack, you need to iterate through entire rest of the stack. You can use staging stack during this process.
*/
public class _5SortStackUsingRecursion {

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