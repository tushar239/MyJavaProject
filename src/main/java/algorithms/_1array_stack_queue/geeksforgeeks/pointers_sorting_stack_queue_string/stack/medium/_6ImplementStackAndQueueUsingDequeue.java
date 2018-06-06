package algorithms._1array_stack_queue.geeksforgeeks.pointers_sorting_stack_queue_string.stack.medium;

/*
    Implementation of Deque using circular array

        https://www.geeksforgeeks.org/implementation-deque-using-circular-array/

    Implement Stack and Queue using Deque

        https://www.geeksforgeeks.org/implement-stack-queue-using-deque/

    What is DeQueue?

        Dequeue is nothing but double ended queue (Doubly Linked List).
        You can add/remove elements to dequeue from the front or back.
        So, DeQueue can be used as both Stack and normal Queue.

        It has a few important methods:

        putFirst(item)
        putLast(item)
        removeFirst()
        removeLast()
        isEmpty()

*/
public class _6ImplementStackAndQueueUsingDequeue {

    public static void main(String[] args) {
        System.out.println("Testing DeQueue");
        {
            MyDequeue<Integer> dequeue = new MyDequeue();
            dequeue.putFirst(1);
            dequeue.putFirst(2);
            dequeue.putFirst(3);
            dequeue.putFirst(4);
            dequeue.putFirst(5);
            dequeue.putLast(6);
            dequeue.putLast(7);
            dequeue.print();

            dequeue.removeFirst();
            dequeue.print();
            dequeue.removeLast();
            dequeue.print();
            dequeue.removeFirst();
            dequeue.print();
            dequeue.removeLast();
            dequeue.print();

            dequeue.removeFirst();
            dequeue.print();
            dequeue.removeLast();
            dequeue.print();

            dequeue.removeFirst();
            dequeue.print();

            dequeue.putLast(9);
            dequeue.print();
        }

        System.out.println("Testing Stack using DeQueue");

        {
            MyStack<Integer> stack = new MyStack<>();
            stack.push(1);
            stack.push(2);
            stack.push(3);

            System.out.println(stack.pop());
            System.out.println(stack.pop());
            System.out.println(stack.pop());
            System.out.println(stack.pop());
        }

        System.out.println("Testing Queue using DeQueue");

        {
            MyQueue<Integer> queue = new MyQueue<>();
            queue.add(1);
            queue.add(2);
            queue.add(3);

            System.out.println(queue.remove());
            System.out.println(queue.remove());
            System.out.println(queue.remove());
            System.out.println(queue.remove());
        }
    }

    static class MyDequeueNode<I> {
        private I item;
        private MyDequeueNode<I> prevNode;
        private MyDequeueNode<I> nextNode;

        public MyDequeueNode(I item) {
            this.item = item;
        }

        public I getItem() {
            return item;
        }

        public void setItem(I item) {
            this.item = item;
        }

        public MyDequeueNode<I> getPrevNode() {
            return prevNode;
        }

        public void setPrevNode(MyDequeueNode<I> prevNode) {
            this.prevNode = prevNode;
        }

        public MyDequeueNode<I> getNextNode() {
            return nextNode;
        }

        public void setNextNode(MyDequeueNode<I> nextNode) {
            this.nextNode = nextNode;
        }
    }

    static class MyDequeue<I> {
        private MyDequeueNode<I> head;
        private MyDequeueNode<I> tail;

        public MyDequeueNode<I> getFirst() {
            return head;
        }

        public void setHead(MyDequeueNode<I> head) {
            this.head = head;
        }

        public MyDequeueNode<I> getTail() {
            return tail;
        }

        public void setTail(MyDequeueNode<I> tail) {
            this.tail = tail;
        }

        public void putFirst(I item) {

            MyDequeueNode<I> newHeadNode = new MyDequeueNode<>(item);

            if (head == null) {
                head = newHeadNode;
                tail = newHeadNode;
                return;
            }

            newHeadNode.nextNode = head;
            head.prevNode = newHeadNode;
            head = newHeadNode;
        }

        public void putLast(I item) {
            MyDequeueNode<I> newTailNode = new MyDequeueNode<>(item);

            if (tail == null) {
                head = newTailNode;
                tail = newTailNode;
                return;
            }

            tail.nextNode = newTailNode;
            newTailNode.prevNode = tail;
            tail = newTailNode;
        }

        public I removeFirst() {
            if (head == null) return null;

            MyDequeueNode<I> headNode = head;

            if (head == tail) {
                head = null;
                tail = null;
                return headNode.item;
            }

            if (head.nextNode != null) {
                head.nextNode.prevNode = null;
            }
            head = head.nextNode;
            headNode.nextNode = null;
            return headNode.item;
        }

        public I removeLast() {
            if (tail == null) return null;

            MyDequeueNode<I> tailNode = tail;

            if (head == tail) {
                head = null;
                tail = null;
                return tailNode.item;
            }

            if (tail.prevNode != null) {
                tail.prevNode.nextNode = null;
            }
            tail = tail.prevNode;
            tailNode.prevNode = null;
            return tailNode.item;
        }

        public boolean isEmpty() {
            return head == null;
        }

        public void print() {
            if (isEmpty()) {
                System.out.println("DeQueue is empty");
                return;
            }

            MyDequeueNode<I> start = head;

            while (start != null) {
                System.out.print(start.item + ",");
                start = start.nextNode;
            }
            System.out.println();
        }
    }

    static class MyStack<I> {
        private MyDequeue<I> dequeue;

        public MyStack() {
            dequeue = new MyDequeue<>();
        }

        public void push(I item) {
            dequeue.putFirst(item);
        }

        public I pop() {
            return dequeue.removeFirst();
        }
    }

    static class MyQueue<I> {
        private MyDequeue<I> dequeue;

        public MyQueue() {
            dequeue = new MyDequeue<>();
        }

        public void add(I item) {
            dequeue.putLast(item);
        }

        public I remove() {
            return dequeue.removeFirst();
        }
    }
}
