package algorithms._1array_stack_queue.geeksforgeeks.pointers_sorting_stack_queue_string.queue.reverse;

import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingQueue;

/*
    Reverse a queue.

    https://www.geeksforgeeks.org/reversing-a-queue/

    This is very easy.

    Wrong implementation:
    remove and add elements from queue for 0 to queue.size()-2 times.

    Right implementation:
    put all elements of queue in a stack and then from stack to queue.
    OR
    recursion for in-place reversal.
*/
public class _1ReverseAQueue {

    public static void main(String[] args) {
        /*{
            Queue<Integer> queue = new LinkedBlockingQueue<>();
            queue.add(1);
            queue.add(2);
            queue.add(3);

            reverse_wrong_implementation(queue);
        }*/

        {
            Queue<Integer> queue = new LinkedBlockingQueue<>();
            queue.add(1);
            queue.add(2);
            queue.add(3);
            queue.add(4);
            queue.add(5);

            reverse_right_implementation(queue);
        }

        {

            Queue<Integer> queue = new LinkedBlockingQueue<>();
            queue.add(1);
            queue.add(2);
            queue.add(3);
            queue.add(4);
            queue.add(5);

            reverse_using_recursion(queue);

            System.out.println(queue);
        }
    }

    // Wrong implementations
    // queue=1,2,3. This implementation will result 3,1,2 instead of 3,2,1
    private static void reverse_wrong_implementation(Queue<Integer> queue) {
        if (queue == null || queue.size() == 0 || queue.size() == 1) return;

        for(int i=0; i<queue.size()-1; i++) {
            queue.add(queue.poll());
        }

        System.out.println(queue);

    }

    // Right implementation
    private static void reverse_right_implementation(Queue<Integer> queue) {
        if (queue == null || queue.size() == 0 || queue.size() == 1) return;

        Stack<Integer> stack = new Stack<>();

        while(!queue.isEmpty()) {
            stack.push(queue.poll());
        }

        while(!stack.isEmpty()) {
            queue.add(stack.pop());
        }

        System.out.println(queue);

    }

    private static void reverse_using_recursion(Queue<Integer> queue) {
        if (queue == null || queue.size() == 0 || queue.size() == 1) return;

        int ele = queue.poll();

        reverse_using_recursion(queue);

        queue.add(ele);
    }


}
