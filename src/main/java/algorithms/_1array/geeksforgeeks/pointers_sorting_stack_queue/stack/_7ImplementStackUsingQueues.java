package algorithms._1array.geeksforgeeks.pointers_sorting_stack_queue.stack;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/*
    Implement Stack using Queues

    https://www.geeksforgeeks.org/implement-stack-using-queue/

    Solution:

    You can use two queues (Q1, Q2) to make them work like a stack.
    When new element needs to be added, add it to Q2 (empty queue) and then dequeue all elements from Q1 and enqueue them to Q2
    and then exchange Q1 with Q2 and vice-versa.
*/
public class _7ImplementStackUsingQueues {

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
        MyStackUsingQueues myStackUsingQueues = new MyStackUsingQueues();
        myStackUsingQueues.push(1);
        myStackUsingQueues.push(2);
        myStackUsingQueues.push(3);
        System.out.println(myStackUsingQueues.poll());
        System.out.println(myStackUsingQueues.poll());
        System.out.println(myStackUsingQueues.poll());
        System.out.println(myStackUsingQueues.poll());
    }

    private static class MyStackUsingQueues {
        private Queue<Integer> Q1 = new LinkedBlockingQueue<>();
        private Queue<Integer> Q2 = new LinkedBlockingQueue<>();

        public void push(Integer num) {
            Q2.add(num);
            while(!Q1.isEmpty()) {
                Q2.add(Q1.remove());
            }
            // exchange Q1 and Q2
            Queue<Integer> temp = Q1;
            Q1=Q2;
            Q2=temp;
        }

        public Integer poll() {
            if(!Q1.isEmpty()) {
                return Q1.remove();
            }
            return null;
        }
        public Integer peek() {
            if(!Q1.isEmpty()) {
                return Q1.peek();
            }
            return null;
        }
    }
}
