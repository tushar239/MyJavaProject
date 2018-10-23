package algorithms._1array_stack_queue.geeksforgeeks.pointers_sorting_stack_queue_string.stack.medium;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/*
    Implement Stack using Queues

    https://www.geeksforgeeks.org/implement-stack-using-queue/

    Solution:

    You can use two queues (Q1, Q2) to make them work like a stack.
    When new element needs to be added, add it to Q2 (empty queue) and then dequeue all elements from Q1 and enqueue them to Q2
    and then exchange Q1 with Q2 and vice-versa.


    See ImplementQueueUsingStacks.java first.
*/
public class _3ImplementStackUsingQueues {

    public static void main(String[] args) throws InterruptedException {
/*
        arrayBlockingQueueTest();
        System.out.println("main thread finishes");
*/
        implementStackUsingQueues();
    }

   /* private static void arrayBlockingQueueTest() throws InterruptedException {
        //Queue<Integer> Q1 = new LinkedBlockingQueue<>();

        Queue<Integer> Q1 = new ArrayBlockingQueue<>(3);
        ((ArrayBlockingQueue<Integer>) Q1).put(1);
        ((ArrayBlockingQueue<Integer>) Q1).put(2);
        ((ArrayBlockingQueue<Integer>) Q1).put(3);
//        ((ArrayBlockingQueue<Integer>) Q1).put(4);

    }*/

    private static void implementStackUsingQueues() {
        {
            MyStackUsingQueues myStackUsingQueues = new MyStackUsingQueues();
            myStackUsingQueues.push(1);
            myStackUsingQueues.push(2);
            myStackUsingQueues.push(3);
            System.out.println(myStackUsingQueues.pop());//3
            System.out.println(myStackUsingQueues.pop());//2
            System.out.println(myStackUsingQueues.pop());//1
            System.out.println(myStackUsingQueues.pop());//null
        }
        {
            MyStackUsingQueues_Another stack = new MyStackUsingQueues_Another();
            stack.push(1);
            stack.push(2);
            stack.push(3);
            System.out.println(stack.pop());//3
            System.out.println(stack.pop());//2
            System.out.println(stack.pop());//1
            System.out.println(stack.pop());//null
        }
    }

    private static class MyStackUsingQueues {
        private Queue<Integer> Q1 = new LinkedBlockingQueue<>();
        private Queue<Integer> Q2 = new LinkedBlockingQueue<>();

        public void push(Integer num) {
            Q2.add(num);
            while (!Q1.isEmpty()) {
                Q2.add(Q1.remove());
            }
            // exchange Q1 and Q2
            Queue<Integer> temp = Q1;
            Q1 = Q2;
            Q2 = temp;
        }

        public Integer pop() {
            if (!Q1.isEmpty()) {
                return Q1.remove();
            }
            return null;
        }

        public Integer peek() {
            if (!Q1.isEmpty()) {
                return Q1.peek();
            }
            return null;
        }
    }

    private static class MyStackUsingQueues_Another {
        private Queue<Integer> Q1 = new LinkedBlockingQueue<>();
        private Queue<Integer> Q2 = new LinkedBlockingQueue<>();

        public void push(Integer ele) {
            // if both queues are empty, add element to any stack
            if (Q1.isEmpty() && Q2.isEmpty()) {
                Q1.add(ele);
            }
            // first copy elements from non-empty queue(e.g. Q1) to empty queue (e.g. Q2)
            // add new element to Q1
            // reverse the process of copying (Q2 to Q1)
            else if (!Q1.isEmpty() && Q2.isEmpty()) {
                copy(Q1, Q2);
                Q1.add(ele);
                copy(Q2, Q1);
            } else {
                copy(Q2, Q1);
                Q2.add(ele);
                copy(Q1, Q2);
            }
        }

        private void copy(Queue<Integer> from, Queue<Integer> to) {
            while (!from.isEmpty()) {
                to.add(from.poll());
            }
        }

        public Integer pop() {
            if (!Q1.isEmpty()) return Q1.poll();
            else if (!Q2.isEmpty()) return Q2.poll();
            return null;
        }

        public Integer peek() {
            if (!Q1.isEmpty()) return Q1.peek();
            else if (!Q2.isEmpty()) return Q2.peek();
            return null;
        }
    }
}
