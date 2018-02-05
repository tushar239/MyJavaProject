package algorithms.crackingcodinginterviewbook._3stackandqueue;

// pg 236 of Cracking Coding Interview book

import java.util.Stack;

/*
    Major difference between stack and queue is Stack is LIFO and Queue is FIFO

    You can create a Queue using two stacks.
    e.g. int[] array = {1,2,3,4,5};

    stack1 =
    5
    4
    3
    2
    1

    stack2 = pop from stack1 and push to stack2
    1
    2
    3
    4
    5

    Basically, you are reversing original stack. stack2 will act as a queue

*/
public class _4CreateQueueUsingStacks {
    public static void main(String[] args) {
        int[] array = {1,2,3,4,5};

        Stack<Integer> stack1 = new Stack<>();
        for (int element : array) {
            stack1.push(element);
        }

        System.out.println("Stack1: "+stack1);

        Stack<Integer> stack2 = new Stack<>();
        while(!stack1.isEmpty()) {
            stack2.push(stack1.pop());
        }
        System.out.println("Stack2: "+stack2);


    }

}
