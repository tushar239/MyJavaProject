package algorithms._0Fundamentals;

/*

Stack, Queue and Dequeue
------------------------

    Stack and Queue
    ---------------
     - Core concepts from Cracking Coding Interview Book


         Stack and Queue are created using linkedlist. They are forms for Singly-LinkedList.

         Important:
             Stack is a LinkedList where items are added and removed to/from head(top). 'head' in Stack is called 'top'.
             Queue is a LinkedList where items are added at tail and removed from head.
             Stack is useful for recursions. Method calls and their local variables are stored in stack.
             Queue is useful for BFS (Breadth First Search) and for implementing a Cache.


            In Java, Stack extends Vector. Vector extends AbstractList.
                     Vector is a synchronized List.
                     You can provide initial capacity for a Vector, but can't do the same for Stack.

                     Queue extends Collection.

        class MyStack<T> {
            Node<T> top;

            public T pop(){…}
            public T peek(){…}
            public T push(T item){…}
            public boolean isEmpty(){…}
        }

        class MyQueue<T> {
            Node<T> first;
            Node<T> last;

            public T remove(){…}
            public T peek(){…}
            public T add(T item){…}
            public boolean isEmpty(){…}
        }

         Stack is LIFO and Queue is FIFO.
         LinkedList doesn't create an array to store elements. It maintains references between two nodes of elements.

         Popping activity is same in both in stack and queue, first element is popped and new first element is set as old first element's next

         Important thing is base class for LinkedList. If you remember Node class, then Stack and Queue algorithms are easy to create.

         Why can't we use Array instead of LinkedList?
         Because Array has to be declared with fixed size and if you don't know how many elements you are dealing with then it's very hard to use Array.
         You can use Resizable Array instead of Array. Read document for more details.

         java.util.Stack extends Vector which is based on Resizable Array
         java.util.Queue has many forms BlockingQueue, ArrayBlockingQueue, LinkedBlockingQueue etc. It provides client a choice to use Fixed size Array or LinkedList.

         Important Stack methods:

             pop() - Removes the top item from the stack.
             push(item) - Add an item to the top of the stack.
             peek() - Return the top of the stack (does not remove an item like pop())
             isEmpty() - Returns tru if and only if the stack is empty.

         Important Queue methods:

            add(item) - Add an item to the end of the list.
            remove() - remove the first item in the list.
            peek() - Return the top of the stack.(does not remove an item like remove())
            isEmpty() - Return true if and only if the stack is empty.

    -   When you use Stack/Queue, it's better to pass stack/queue size as method parameter

    -   Sorting, Reversing, Deleting

        -   Sorting

            See SortingFundamentals.java

        -   Reverse a stack/queue
            It is easier to to reverse a stack using recursion (ReverseStackUsingRecursion.java).
            Reversing a queue is very easy (ReverseAQueue.java)

        -   Deleting a middle element in a stack/queue
            It can be done using recursion or using additional stack (DeleteMiddleElementOfAStackWithoutUsingAnyAdditionalDataStructure.java)

Dequeue (Double Ended Queue)
----------------------------
    https://www.geeksforgeeks.org/implement-stack-queue-using-deque/

    Dequeue is a double-ended queue. It is basically a Doubly-LinkedList in which elements can be inserted/removed from both ends.

    Dequeue extends Queue in java.

    Deque<Integer> deque = new LinkedBlockingDeque<>();// uses linked list
                         = new ArrayDequeue();// uses array


    push(item) ---- Unlike other types of Queues, it adds an item on the top(head of doubly linked list)
    pop() --- Unlike other types of queues, it removes an item from tail

    Dequeue has below important methods:

    isEmpty()

    putFirst()
    removeFirst()

    putLast()
    removeLast()

    So, DeQueue can be used as both Stack and normal Queue.
    See 'ImplementStackAndQueueUsingDequeue.java'
*/

import java.util.Iterator;

public class StackAndQueueFundamentals {
    public static void main(String[] args) {
        int array[] = {1, 2, 3, 4, 5, 6, 7};

        System.out.println("------ Stack -------");
        // Stack
        // NOTE: You could use self created SinglyLinkedList.java to create Stack and Queue
        {
            {
                System.out.println("Creating a Stack using LinkedList Another Way - by looping a data array in reverse way");
                Node headNodeInStack = createStackUsingLinkedList(array);
                System.out.println("Popping items from Stack");
                popItems(headNodeInStack);
            }

            System.out.println();
            System.out.println();

            {
                System.out.println("Creating a Stack using LinkedList");
                Node headNodeInStack = createStackUsingLinkedListAnotherWay(array);
                System.out.println("Popping items from Stack");
                popItems(headNodeInStack);
            }

            System.out.println();
            System.out.println();

            {
                Node headNodeInStack = createStackUsingLinkedList(array);
                System.out.println("Popping items from Stack using Iterator");
                Stack stack = new Stack(headNodeInStack);
                stack.popItems();
            }

            System.out.println();
            System.out.println();

            {
                // Stack Using fixed size Array. You can use Resizable Array also.
                System.out.println("Creating an Stack using Fixed size Array");
                Integer[] stackArray = createStackUsingArray(array);
                System.out.println("Popping items from Stack");
                popItemsFromArray(stackArray);
            }

        }

        System.out.println();
        System.out.println("------ Queue -------");

        // Queue
        {
            System.out.println("Creating a Queue using LinkedList");
            Node headNodeInQueue = createQueueUsingLinkedList(array);
            System.out.println("Popping items from Queue");
            popItems(headNodeInQueue);
        }

        System.out.println();
        System.out.println();

        {
            System.out.println("Creating a Queue using Array");
            Integer[] queueArray = createQueueUsingArray(array);
            System.out.println("Popping items from Queue");
            popItemsFromArray(queueArray);

        }

    }

    /*
        Stack

       | 7 | --- head
       _____
       | 6 |
       _____
       | 5 |
       _____
       | 4 |
       _____
       | 3 |
       _____
       | 2 |
       _____
       | 1 |
       _____

        Queue

        1(head)->2->3->4->5->6->7

     */
    private static Node createStackUsingLinkedList(int[] array) { // you can use self created LinkedList class instead
        // creating a stack using linkedlist.
        Node headNode = null;
        Node lastNode = null;

        for (int i = array.length - 1; i >= 0; i--) { // iterate data array in reverse way (last to first)
            Node<Integer> node = new Node<>();
            node.item = array[i];
            if (headNode == null) {
                headNode = node;
            } else {
                lastNode.next = node;
            }
            lastNode = node;
        }
        return headNode;

    }

    private static Node createQueueUsingLinkedList(int[] array) {

        // creating a queue using linkedlist
        Node headNode = null;
        Node lastNode = null;

        for (int i = 0; i < array.length; i++) { // iterate data array in straight way (first to last)

            Node<Integer> node = new Node<>();
            node.setItem(array[i]);

            if (headNode == null) {
                headNode = node;
            } else {
                lastNode.setNext(node);
            }

            lastNode = node;
        }
        return headNode;
    }

    private static Node createStackUsingLinkedListAnotherWay(int[] array) { // you can use self created LinkedList class instead
        // creating a stack using linkedlist.
        Node headNode = null;
        Node previousNode = null;
        for (int i = 0; i < array.length; i++) {
            Node<Integer> node = new Node<>();
            node.setItem(array[i]);
            node.setNext(previousNode);

            if (i == array.length - 1) {
                headNode = node;
            }
            previousNode = node;
        }
        return headNode;

    }

    private static Integer[] createStackUsingArray(int[] array) {
        Integer[] stackArray = new Integer[array.length];
        int count = 0;
        for (int i = array.length - 1; i >= 0; i--) {
            stackArray[i] = array[count];
            count++;
        }
        return stackArray;
    }

    private static Integer[] createQueueUsingArray(int[] array) {
        Integer[] stackArray = new Integer[array.length];
        for (int i = 0; i < array.length; i++) {
            stackArray[i] = array[i];
        }
        return stackArray;
    }

    private static void popItemsFromArray(Integer[] array) {
        if (array != null) {
            for (int i = 0; i < array.length; i++) {
                System.out.print(array[i] + " ");
                array[i] = null; // Very important to avoid loitering. Object is popped out of the Stack. So, object reference used in that place of the array should be released for garbage collection.
            }
        }
    }


    private static void popItems(Node headNode) {
        if (headNode != null) {
            while (headNode.hasNext()) { // go till the end of queue
                Node poppedNode = headNode;
                System.out.print(poppedNode.getItem() + " ");
                // pop the first element from the queue
                headNode = headNode.getNext(); // set next element as a first element of the queue
            }
            System.out.print(headNode.getItem());
        }

    }

    static class Stack implements Iterable<Node> {
        private Node headNode;

        public Stack(Node headNode) {
            this.headNode = headNode;
        }

        public void popItems() {
            System.out.print(headNode.getItem() + " ");
            Iterator<Node> stackIterator = iterator();
            while (stackIterator.hasNext()) {
                Node nextNode = stackIterator.next();
                System.out.print(nextNode.getItem() + " ");
                stackIterator.remove();
                headNode = nextNode;
            }
        }

        @Override
        public Iterator<Node> iterator() {
            return new StackIterator();
        }

        class StackIterator implements Iterator<Node> {

            @Override
            public boolean hasNext() {
                return headNode.hasNext();
            }

            @Override
            public Node next() {
                return headNode.getNext();
            }

            @Override
            public void remove() {
                headNode.setNext(null);
            }
        }
    }

    static class Node<T> {
        private T item;
        private Node next;

        public T getItem() {
            return item;
        }

        public void setItem(T item) {
            this.item = item;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public boolean hasNext() {
            return getNext() != null;
        }
    }
}
