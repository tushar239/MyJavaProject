package algorithms._1array_stack_queue.geeksforgeeks.pointers_sorting_stack_queue_string.stack.delete;

import java.util.Stack;

/*
    Delete middle element of a stack without using any additional data structure.

    https://www.geeksforgeeks.org/delete-middle-element-stack/

    Given a stack with push(), pop(), empty() operations, delete middle of it without using any additional data structure.
*/
public class _1DeleteMiddleElementOfAStackWithoutUsingAnyAdditionalDataStructure {

    public static void main(String[] args) {
        {
            Stack<Integer> stack = new Stack<>();
            stack.push(1);
            stack.push(2);
            stack.push(3);
            stack.push(4);
            stack.push(5);
            stack.push(6);
            stack.push(7);

            deleteMiddleElement_Using_Additional_DataStructure(stack, stack.size());

            System.out.println(stack);
        }
        System.out.println();
        {
            Stack<Integer> stack = new Stack<>();
            stack.push(1);
            stack.push(2);
            stack.push(3);
            stack.push(4);
            stack.push(5);
            stack.push(6);
            stack.push(7);

            deleteMiddleElement_Recursive_And_Not_Using_Additional_DataStructure(stack, stack.size(), 0);

            System.out.println(stack);
        }
    }

    /*
        This approach uses aux stack (you can use use aux array also).
        This is not increasing memory usage because you are popping elements from input stack and pushing them to aux stack. So, memory usage will remain same.

        There is a better approach that uses recursion and doesn't require aux stack.
    */
    private static void deleteMiddleElement_Using_Additional_DataStructure(Stack<Integer> stack, int stackSize) {

        Stack<Integer> stagingStack = new Stack<>();

        int count = 0;
        while (!stack.isEmpty() || count <= stackSize / 2) {
            if (count == stackSize / 2) {
                stack.pop();
            } else {
                stagingStack.push(stack.pop());
            }
            count++;
        }

        while (!stagingStack.isEmpty()) {
            stack.push(stagingStack.pop());
        }

    }

    /*
        Instead of using aux array, recursive call holds the value of popped element.

        This approach is not reducing the memory usage because holding the variable during recursion also requires memory.
        Memory usage is still same as deleteMiddleElement(...) approach.
        But this recursive approach avoids using any extra data structure.

    */
    private static void deleteMiddleElement_Recursive_And_Not_Using_Additional_DataStructure(Stack<Integer> stack, int stackSize, int count) {

        if (stack.isEmpty()) return;
        if (count > stackSize / 2) return;

        int x = stack.pop();

        deleteMiddleElement_Recursive_And_Not_Using_Additional_DataStructure(stack, stackSize, count + 1);// do not use count++. Either use count+1 or ++count

        if (count != stackSize / 2) {
            stack.push(x);
        }

    }
}
