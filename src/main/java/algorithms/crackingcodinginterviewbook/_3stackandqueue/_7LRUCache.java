package algorithms.crackingcodinginterviewbook._3stackandqueue;

import algorithms.crackingcodinginterviewbook._2linkedlistmanipulation.Node;
import algorithms.crackingcodinginterviewbook._2linkedlistmanipulation.SinglyLinkedList;

// http://www.geeksforgeeks.org/lru-cache-implementation/
/*
    If you remember from the notes in StackAndQueue.java, queue can be used for Cache implementation.
    Here, I have created a queue using LinkedList.

    You can use Java's LinkedBlockingQueue or ArrayBlockingQueue also.
    LinkedBlockingQueue is based on LinkedList whereas ArrayBlockingQueue is based on Array.
    LinkedBlockingQueue is better because on every access operation, you don't need to move all elements of array by one index less, you just need to delink accessed element abd add it to the end of linkedlist.

    BlockingQueue vs BlockingDequeue

        http://tutorials.jenkov.com/java-util-concurrent/blockingqueue.html
        http://tutorials.jenkov.com/java-util-concurrent/blockingdeque.html

        Dequeue means 'Double Ended Queue'. It allows to add/remove elements from both ends.

*/
public class _7LRUCache {
    public static void main(String[] args) {

        // you can use your own LinkedList that restricts the size. Java's LinkedList doesn't do that.
        // I am not using an Array because insertion and deletion to and from array is expensive, if it has to be done at the end and at the beginning respectively.
        // I am using Queue that is implemented using LinkedList because Queue provides functionality like pop and peek from front, add to tail out of the box.
        // If interviewer asks you not to use java's inbuilt data structure, then you need to implement your own queue using LinkedList.
        SinglyLinkedListWithCapacity cache = new SinglyLinkedListWithCapacity(5);

        // populating the cache (queue)
        for (int i = 0; i < 5; i++) {
            try {
                cache.addToTail(new Node(i));
            } catch (CapacityLimitReachedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Cache at the beginning:" + cache);// 0,2,3,4,5
        push(cache, new Node(5)); // 0 should be removed and 5 should be added
        System.out.println("After adding new element :" + cache); // 1,2,3,4,5

        access(cache, new Node(3));
        System.out.println("After accessing an element :" + cache); // 1,2,4,5,3
    }

    // O(n+1)
    private static Node access(SinglyLinkedListWithCapacity cache, Node node) {
        if (cache.isEmpty()) return null;

        Node accessedNode = null;

        Node R = cache.head;
        while (R != null) {
            if (R.equals(node)) accessedNode = R;

            R = R.next;

            // moving just accessed element to the end of queue
            if (accessedNode != null) {
                cache.delete(accessedNode);
                accessedNode.next = null;
                cache.addToTail(accessedNode);
                break;
            }


        }
        return accessedNode;
    }

    // O(n)
    private static void push(SinglyLinkedListWithCapacity cache, Node node) {
        try {
            cache.addToTail(node);
        } catch (CapacityLimitReachedException e) {
            cache.delete(cache.head); // removing LRU element from front of the queue, if capacity limit is reached.
            cache.addToTail(node);
        }
    }

    private static class SinglyLinkedListWithCapacity extends SinglyLinkedList {
        private final int capacity;
        private int length = 0;

        public SinglyLinkedListWithCapacity(int capacity) {
            this.capacity = capacity;
        }

        public int getCapacity() {
            return capacity;
        }

        @Override
        public void addAsHead(Node newNode) {
            if (length == capacity) throw new CapacityLimitReachedException("capacity reached");
            super.addAsHead(newNode);
            length++;
        }

        @Override
        public void addToTail(Node newNode) {
            if (length == capacity) throw new CapacityLimitReachedException("capacity reached");
            super.addToTail(newNode);
            length++;
        }

        @Override
        public Node delete(Node node) {
            Node deletedNode = super.delete(node);
            length--;
            return deletedNode;
        }

    }


}
