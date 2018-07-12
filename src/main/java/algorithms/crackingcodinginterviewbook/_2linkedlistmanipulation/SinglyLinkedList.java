package algorithms.crackingcodinginterviewbook._2linkedlistmanipulation;

import java.util.List;

/*

    There are some important points to remember for a LinkedList.

    - LinkedList class is just a wrapper of Head node.

        public class LinkedList {
            private Node head;

            public Node addToTail(Node newNode) {...}

            public Node addAsHead(Node newNode) {...}

            public Node delete(Node node) {...}

            // peek just reads the head node and returns it. It doesn't remove the head node
            public Node peek() {...}

            // pop just reads the head node, removes it and returns it.
            public Node pop() {...}
        }

        public class Node {
            private int data;
            private Node next;

            public Node(int data) {
                this.data =  data;
            }
        }

    - Runner Node(s)

        Use Runner to traverse through a LinkedList. Don't do head=head.next. You will end up moving head pointer to some other node in the LinkedList.

            head
            runner
              |
              v
            --------------------
            | 1 | 2 | 3 | 4 | 5 |
            --------------------

            Whenever you need to iterate through a linked list, always create a runner node.
            Do not iterate by moving head=head.next, otherwise you will end up moving head pointer to somewhere else in the linked list.
            You should do
            Node runner = head;
            while(...) runner = runner.next;


        When to use prev and next runners?
        When you need to remove/add the element from/in the middle of the list.
        See AlternateOddAndEvenNodesInSinglyLinkedList.java

        More than one Runner Technique: (IMPORTANT)

            The runner technique means that you iterate through the linked list with two pointers simultaneously, with one ahead of the other.
            The "fast" node might be ahead by a fixed steps, or it might be hopping multiple nodes for each one node that the "slow" node iterates through.

            For example, suppose you had a linked list a1->a2->.....->an->b1->b2->.....->bn and you wanted to arrange it into
            a1->b1->a2->b2->...->an->bn. You do not need to know the length of the linked list(but you do know that the length is an even number).

            You could have one pointer p1(the fast pointer) move every two elements for every one move that p2 makes.
            start moving p2 by one step and p1 by two steps.
            when p1==null or p1.next==null, p2 will be at the beginning of second linked list.

   - Recursion (IMPORTANT)

        You can write a normal Iterative traversal also, but if you want to use the recursion, then you can do following.

        public Node search(int data) {
            return search(head, data)
        }
        public Node search(Node runner, int data) {
            if(runner == null || runner.data == data) return runner;
            return(runner.next, data);
        }


        (IMPORTANT)
        Reverse a linked list - This is a very important algorithm to understand recursion in LinkedList.
                                Memorize the recursion approach of ReverseLinkedList.java.
                                But for the actual algorithm, it's better to memorize iterative approach because iterative approach is easier for other algorithms that are based on reversing (e.g. ReverseSubListOfLinkedList.java, ReverseLinkedListInGroupsOfGivenSize.java)


   - Using extra buffer for linkedlist algorithms?

        Using map or set as extra buffer

            Many times when you traverse a linkedlist using runners (pointers), you may end up with runtime complexity O(n^2).

            (IMP) Ask interviewer, are you allowed to use extra buffer?
            If he says yes, you can use map/set as extra buffer.

            NOTE: Set internally uses Map. So, searching anything in Set will reserve O(1) only.

            e.g. Remove Duplicates from LinkedList algorithm (RemoveDups.java)

        Using stack extra buffer (IMPORTANT)

            e.g. PalindromeLinkedList.java, ReturnKthToLastElement.java

            NOTE:
            In case of String’ Palindrome StringPalindrome.java, you don’t need any complexity
            because String provides you indexed charArray using str.toCharArray().
            It’s easy to iterate indexed array compared to a LinkedList.

    - Reverse a Linked List

      This is a very important algorithm. Memorize both iterative and recursive approaches. Use iterative approach because it useful for other problems using reversing algorithm (e.g. ReverseSubListOfLinkedList.java).
      Recursive algorithm will explain you basics of how to think recursive in case of linked list.

      ReverseLinkedList.java

      Algorithms using reversal algorithm:
      - ReverseSubListOfLinkedList.java
      -

    - Binary Search on Singly Linked List

      Binary Search on Linked List is a bad idea. When you do binary search on an array, finding a mid takes O(1), whereas finding a mid in linkedlist takes n/2+n/4+n/6 +... = O(n).
      Binary Search on array takes O(log n), whereas it is on linked list takes O(n).
      So, binary search does not give any advantage over simple search in case of linked list.

      If you are asked to do Binary Search on Singly Linked List, then remember that mid finding algorithm should return mid and midPrev.

      See BinarySearchOnLinkedList.java

    - Do Not modify an object sent as parameter

        e.g. DeleteMiddleNode.java

        private static void delete(Node head, Node nodeToBeDeleted) {
            ...
            head = R.next; // This doesn't work
            ...
        }

        In above code, you are trying to manipulate sent object (head), but you are forgetting that
        When caller calls a method, situation is like below

            sent head from caller   -----|
                                                | -> 5
            param head-------------------|

        When you modify incoming parameter, situation will be as follows:

            sent head from caller   --------> 5
            param head----------------------> 2

        It won't change the actual ‘head’ object sent by a caller

        Solutions:

        1)  Whenever you need to do that, you wrap that param with some other class and send that class object as a param.
            e.g. SinglyLinkedList
            private static void delete(SinglyLinkedList LL, Node nodeToBeDeleted) {

            Now, when you do LL.head = 2. It will actually update the content sent LL object.

        2) Node delete(Node head, Node nodeToBeDeleted) {
                ...
                Node newHead = 2;
                ...

                return newHead;
           }


    -  How to check whether LinkedList has odd or even size?

        1 -> 2 -> 3 -> 4 -> null
        a

        move runner ‘a’ two steps at a time till (a==null or a.next==null).
        If(a==null) then it’s a even size.
        If(a.next==null) then it’s odd size

*/
public class SinglyLinkedList {
    public Node head;

    public SinglyLinkedList() {
    }

    public SinglyLinkedList(Node head) {
        this.head = head;
    }

    public static SinglyLinkedList createLinkedListOfIntegers(List<Integer> dataArray) {
        SinglyLinkedList linkedList = new SinglyLinkedList();
        if (dataArray != null) {
            for (Integer data : dataArray) {
                linkedList.addToTail(new Node(data));
            }
        }
        return linkedList;
    }

    public static SinglyLinkedList createLinkedListOfChars(List<Character> dataArray) {
        SinglyLinkedList linkedList = new SinglyLinkedList();
        if (dataArray != null) {
            for (Character data : dataArray) {
                linkedList.addToTail(new Node(data));
            }
        }
        return linkedList;
    }

    // NOTE: just to find a length, you need to traverse entire linked nilList. That's a disadv compared to Array/Resizable Array(ArrayList)
    public int length() {
        int length = 0;
        Node node = head;
        while (node != null) {
            length++;
            node = node.next;
        }
        return length;
    }

    public LengthAndTail lengthAndTail() {
        int length = 0;
        Node tail = null;

        Node node = head;
        while (node != null) {
            length++;
            if (node.next == null) {
                tail = node;
            }
            node = node.next;
        }
        return new LengthAndTail(length, tail);
    }

    class LengthAndTail {
        private int length;
        private Node tail;

        public LengthAndTail(int length, Node tail) {
            this.length = length;
            this.tail = tail;
        }

        public int getLength() {
            return length;
        }

        public Node getTail() {
            return tail;
        }
    }

    public void traverse() {
        traverseRecursively(head);
        // OR
        // traverseIteratively(head);
    }

    // All recursive calls can be replaced by Iterative excecution. Recursive calls use stack memory, iterative calls do not. Time complexity of both will be same.
    private void traverseRecursively(Node node) {

        // 1 - in recursive call, first statement is always a return condition
        if (node == null) return;

        // 2 - things that you actually want to do.
        // This can be before or after recursive call.
        // You always start coding from this kind of statements and then put a recursive call and then put a return condition
        System.out.println(node.data);

        // 3 - recursive call.
        traverseRecursively(node.next);

    }

    // All recursive calls can be replaced by Iterative excecution. Recursive calls use stack memory, iterative calls do not. Time complexity of both will be same.
    private void traverseIteratively(Node node) {
        while (node != null) {
            System.out.println(node.data);
            node = node.next;
        }
    }

    // NOTE: Adding to tail requires traversing entire linked nilList
    public void addToTail(Node newNode) {

        if (head == null) {
            head = newNode;
            return;
        }

        /*
        head
        runner
          |
          v
        --------------------
        | 1 | 2 | 3 | 4 | 5 |
        --------------------

        Whenever you need to iterate through a linked list, always create a runner node.
        Do not iterate by moving head=head.next, otherwise you will end up moving head pointer to somewhere else in the linked list.

        You should do
        Node runner = head;
        while(...) runner = runner.next;
        */
        Node runner = head; // IMP: this is very important
        while (runner.next != null) {
            runner = runner.next;
        }
        runner.next = newNode;
    }

    // NOTE: Adding to head is easier. It does not require traversing entire linked nilList
    public void addAsHead(Node newNode) {
        if (head == null) {
            head = newNode;
        } else {
            newNode.next = head;
            head = newNode;
        }
    }

    public void addToFront(Node newNode) {
        newNode.next = head;
        head = newNode;
    }

    public void addToTail(int data) { // can be used by queue
        Node newNode = new Node(data);
        addToTail(newNode);
    }

    public void addAsHead(int data) { // can be used by stack
        Node newNode = new Node(data);
        addAsHead(newNode);

    }

    public boolean isEmpty() {
        return head == null;
    }

    // pop actually reads and remove the first node from the LinkedList
    public Node pop() { // can be used by both stack and queue
        Node poppedNode = null;

        if (head != null) {
            poppedNode = head;
            head = head.next;

        }
        return poppedNode;
    }

    // peek just reads the first node. It doesn't remove it from the LinkedList.
    public Node peek() { // can be used by both stack and queue
        return head;
    }


    public Node delete(int data) {

        Node previousNode = null;
        Node currentNode = head;

        while (currentNode != null) {

            if (currentNode.data == data) {
                if (previousNode == null) { // taking care of deletion of first node
                    head = currentNode.next;
                } else {
                    if (currentNode.next == null) { // taking care of deletion of last node
                        previousNode.next = null;
                    } else {
                        previousNode.next = currentNode.next;
                    }

                }
                return currentNode;
            }

            previousNode = currentNode;
            currentNode = currentNode.next;
        }

        return null;
    }

    public Node delete(Node node) {
        return delete(node.data);
    }

    public Node deleteAnotherWay(int data) {

        Node deletedNode = null;

        if (head != null) {
            if (head.data == data) { // taking care of deletion of first node
                deletedNode = head;
                head = head.next;

            } else {
                Node previousNode = head;
                Node currentNode = head.next;

                while (currentNode != null) {

                    if (currentNode.data == data) {
                        deletedNode = currentNode;
                        if (currentNode.next == null) { // taking care of deletion of last node
                            previousNode.next = null;
                        } else {
                            previousNode.next = currentNode.next;
                        }
                        break;
                    }
                    previousNode = currentNode;
                    currentNode = currentNode.next;
                }
            }

        }
        return deletedNode;
    }

    @Override
    public String toString() {
        return "SinglyLinkedList{" +
                "head=" + head +
                '}';
    }
}
