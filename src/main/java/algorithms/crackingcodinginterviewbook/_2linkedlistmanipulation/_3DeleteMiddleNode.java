package algorithms.crackingcodinginterviewbook._2linkedlistmanipulation;

import java.util.ArrayList;

public class _3DeleteMiddleNode {
    public static void main(String[] args) {
        // delete Middle element
        {
            SinglyLinkedList linkedList = getSinglyLinkedList();

            delete_1(linkedList, new Node(7));
            System.out.println(linkedList);

        }
        // delete 1st element
        {
            SinglyLinkedList linkedList = getSinglyLinkedList();
            delete_1(linkedList, new Node(5));
            System.out.println(linkedList);
        }

        // Recursive Approach: delete Middle Element
        {
            SinglyLinkedList linkedList = getSinglyLinkedList();
            Node newHead = deleteRecursively(linkedList.head, new Node(7), null);
            System.out.println(newHead);
        }
        // Recursive Approach: delete 1st Element
        {
            SinglyLinkedList linkedList = getSinglyLinkedList();
            Node newHead = deleteRecursively(linkedList.head, new Node(5), null);
            System.out.println(newHead);
        }
    }

    private static SinglyLinkedList getSinglyLinkedList() {
        return SinglyLinkedList.createLinkedListOfIntegers(new ArrayList<Integer>() {{
            add(5);
            add(2);
            add(7);
            add(3);
            add(1);
        }});
    }

    /*
    In below code, you are trying to manipulate sent object (head), but you are forgetting that

    When caller calls a method, situation is like below

        sent head from caller   -----|
                                     | -> 5
        param head-------------------|


    When you modify incoming parameter, situation will be as follows:

        sent head from caller   -----|
                                     | -> 5

        param head----------------------> 2

    It won't change the actual object sent by a caller

    Solutions:

    1)  Whenever you need to do that, you wrap that param with some other class and send that class object as a param.
        e.g. SinglyLinkedList

        private static void delete_1(SinglyLinkedList LL, Node nodeToBeDeleted) {

        Now, when you do LL.head = 2. It will actually update the content sent LL object.

    2) Node delete_2(Node head, Node nodeToBeDeleted) {
            ...
            Node newHead = 2;
            ...

            return newHead;
       }


    */
    private static void delete_erroneous_code(Node head, Node nodeToBeDeleted) {
        if (head == null) return;

        Node R = head;
        Node prevOfR = null;
        while (R != null) {
            if (R.equals(nodeToBeDeleted)) {
                if (prevOfR != null) {
                    prevOfR.next = R.next;
                } else {
                    head = R.next; // This doesn't work
                    R.next = null;
                }
                break;
            }
            prevOfR = R;
            R = R.next;

        }
    }


    private static void delete_1(SinglyLinkedList LL, Node nodeToBeDeleted) {
        if (LL.head == null) return;

        Node R = LL.head;
        Node prevOfR = null;
        while (R != null) {
            if (R.equals(nodeToBeDeleted)) {
                if (prevOfR != null) {
                    prevOfR.next = R.next;
                } else {
                    LL.head = R.next;
                    R.next = null;
                }
                break;
            }
            prevOfR = R;
            R = R.next;

        }
    }
    // OR

    private static Node delete_2(Node head, Node nodeToBeDeleted) {
        if (head == null) return head;

        Node R = head;
        Node prevOfR = null;
        Node newHead = head;
        while (R != null) {
            if (R.equals(nodeToBeDeleted)) {
                if (prevOfR != null) {
                    prevOfR.next = R.next;
                } else {
                    newHead = R.next;
                    R.next = null;
                }
                break;
            }
            prevOfR = R;
            R = R.next;
        }
        return newHead;
    }


    private static Node deleteRecursively(Node head, Node nodeToBeDeleted, Node prevNode) {
        if (head == null) return head;

        if (head.equals(nodeToBeDeleted)) {
            Node newHead = head;
            if (prevNode != null) {
                prevNode.next = head.next;
            } else {
                newHead = head.next;
            }
            return newHead;
        }
        return deleteRecursively(head.next, nodeToBeDeleted, head);
    }


}
