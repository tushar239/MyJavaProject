package algorithms._1array_stack_queue.geeksforgeeks.pointers_sorting_stack_queue_string.queue;

import java.util.Stack;

/*
    Implement Queue using Stacks

    https://www.geeksforgeeks.org/queue-using-stacks/

    This problem is similar to 'ImplementStackUsingQueues.java'.

    Solution:

    You can use two stacks (S1, S2) to make them work like a queue.
    When new element needs to be added, push it to S2 (empty stack) and then pop all elements from S1 and push them to S2.
    and then exchange S1 with S2 and vice-versa.

    - add(1)

    1
    S1  S2

    - add(2)

    copy(S1,S2)
    S2.add(2)
    copy(S2,S1)

                2       1
         1      1       2
    S1  S2      S2      S1

    - add(3)

    copy(S2,S1)
    S1.add(3)
    copy(S1,S2)


        3       1
        2       2
        1       3
    S1  S2      S1


    and so on...



*/
public class _1ImplementQueueUsingStacks {

    public static void main(String[] args) {
        QueueUsingStacks queue = new QueueUsingStacks();
        queue.add(1);
        queue.add(2);
        queue.add(3);
        queue.add(4);
        queue.add(5);


        System.out.println(queue.poll());//1
        System.out.println(queue.poll());//2
        System.out.println(queue.poll());//3
        System.out.println(queue.poll());//4
        System.out.println(queue.poll());//5
        System.out.println(queue.poll());//null

    }

    private static class QueueUsingStacks {
        Stack<Integer> S1 = new Stack<>();
        Stack<Integer> S2 = new Stack<>();

        // takes O(2 * n^2), so O(n^2)
        private void add(Integer ele) {
            System.out.println("adding ele: " + ele);
            // if both stacks are empty, add element to any stack
            if (S1.isEmpty() && S2.isEmpty()) {
                S1.add(ele);
            }
            // first copy elements from non-empty stack(e.g. S1) to empty stack(e.g. S2)
            // add new element to S2
            // reverse the process of copying (S2 to S1)
            else if (!S1.isEmpty() && S2.isEmpty()) {
                copy(S1, S2);// O(n)
                S2.add(ele);
                copy(S2, S1);// O(n)
            } else {
                copy(S2, S1);
                S1.add(ele);
                copy(S1, S2);
            }
        }

        private void copy(Stack<Integer> from, Stack<Integer> to) {
            //int count =0;
            while (!from.isEmpty()) {
                to.push(from.pop());
                //count++;
            }
            //System.out.println("count:"+count);
        }

        private Integer poll() {
            if (!S1.isEmpty()) return S1.pop();
            else if (!S2.isEmpty()) return S2.pop();
            return null;
        }

        public Integer peek() {
            if (!S1.isEmpty()) return S1.peek();
            else if (!S2.isEmpty()) return S2.peek();
            return null;
        }

    }
}
