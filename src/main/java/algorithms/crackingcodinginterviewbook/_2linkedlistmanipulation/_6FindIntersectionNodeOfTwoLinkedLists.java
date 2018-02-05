package algorithms.crackingcodinginterviewbook._2linkedlistmanipulation;

import java.util.ArrayList;

// pg 221 of Cracking Coding Interviews book
public class _6FindIntersectionNodeOfTwoLinkedLists {
    public static void main(String[] args) {
        {
        /*
        linked list1 = 3-1-5-9-7-2-1
        linked list2 =     4-6-7-2-1

        Intersecting nodeByValue is 7

        find lengths of both lists.

        When you are finding the lengths of two lists, you need to reach to last node of both lists.
        It last nodes are not equal, then that's an exit condition.

        Use two pointers (a and b). a for linked list1 and b is for linked list2.
        If lengths are different, forward longer linkedlist's pointer by the difference of both the lengths. So, in above example a points to 5 of list1 and b points to 4 of list2.
        keep forwarding both a and b. Stop where both a.data and b.data matches. That's an intersecting nodeByValue.
         */
            SinglyLinkedList linkedList1 = SinglyLinkedList.createLinkedListOfIntegers(new ArrayList<Integer>() {{
                add(3);
                add(1);
                add(5);
                add(9);
                add(7);
                add(2);
                add(1);
            }});
            SinglyLinkedList linkedList2 = SinglyLinkedList.createLinkedListOfIntegers(new ArrayList<Integer>() {{
                add(4);
                add(6);
                add(7);
                add(2);
                add(1);
            }});
            Node nodeByValue = findIntersectingNodeByValue(linkedList1, linkedList2);
            System.out.println("Intersection node by value: " + nodeByValue.data);
            Node nodeByReference = findIntersectingNodeByReference(linkedList1, linkedList2);
            System.out.println("Intersection node by reference: " + (nodeByReference != null?nodeByReference.data:null));
        }
        {
            /*
            linked list1 = 3-1-5-9-|
                                    -7-2-1
            linked list2 =     4-6-|

            Intersecting nodeByReference is 7. We also need to make sure that references for 7 in both lists are same.

            To do that
            find lengths and tails of both lists.
            if tails are same by reference, then basically they is no possibility of having intersecting nodeByReference (by reference).

            use two pointers (a and b). a for linked list1 and b is for linked list2.
            If lengths are different, forward longer linkedlist's pointer by the difference of both the lengths. So, in above example a points to 5 of list1 and b points to 4 of list2.
            keep forwarding both a and b. Stop where both a.data and b.data matches. That's an intersecting nodeByReference.
             */

            Node node1 = new Node(3);
            Node node2 = new Node(1);
            Node node3 = new Node(5);
            Node node4 = new Node(9);
            Node node5 = new Node(7);
            Node node6 = new Node(2);
            Node node7 = new Node(1);
            Node node8 = new Node(4);
            Node node9 = new Node(6);

            /*
            linked list1 = 3-1-5-9-|
                                    -7-2-1
            linked list2 =     4-6-|
             */
            SinglyLinkedList linkedList1 = new SinglyLinkedList();
            linkedList1.addToTail(node1);
            linkedList1.addToTail(node2);
            linkedList1.addToTail(node3);
            linkedList1.addToTail(node4);
            linkedList1.addToTail(node5);
            linkedList1.addToTail(node6);
            linkedList1.addToTail(node7);

            SinglyLinkedList linkedList2 = new SinglyLinkedList();
            linkedList2.addToTail(node8);
            linkedList2.addToTail(node9);
            linkedList2.addToTail(node5);

            Node nodeByReference = findIntersectingNodeByReference(linkedList1, linkedList2);
            System.out.println("Intersection node by reference: " + nodeByReference.data);
        }
    }

    private static Node findIntersectingNodeByValue(SinglyLinkedList linkedList1, SinglyLinkedList linkedList2) {
        if (linkedList1 == null || linkedList2 == null || linkedList1.head == null || linkedList2.head == null) {
            return null;
        }
        int length1 = linkedList1.length();//O(n) traversal
        int length2 = linkedList2.length();//O(m) traversal

        return traverseLists(linkedList1, linkedList2, length1, length2);
    }

    private static Node findIntersectingNodeByReference(SinglyLinkedList linkedList1, SinglyLinkedList linkedList2) {
        if (linkedList1 == null || linkedList2 == null || linkedList1.head == null || linkedList2.head == null) {
            return null;
        }
        SinglyLinkedList.LengthAndTail lengthAndTail1 = linkedList1.lengthAndTail();//O(n) traversal
        SinglyLinkedList.LengthAndTail lengthAndTail2 = linkedList2.lengthAndTail();//O(m) traversal

        // If tails nodes are different then basically two lists are not intersecting each other. Nodes can be same by data but they will never be same by reference in both lists.
        if (lengthAndTail1.getTail() != lengthAndTail2.getTail()) {
            return null;
        }

        int length1 = lengthAndTail1.getLength();
        int length2 = lengthAndTail2.getLength();

        return traverseLists(linkedList1, linkedList2, length1, length2);
    }

    private static Node traverseLists(SinglyLinkedList linkedList1, SinglyLinkedList linkedList2, int length1, int length2) {
        Node a = linkedList1.head;
        Node b = linkedList2.head;

        if (length1 > length2) {
            for (int i = 0; i < length1 - length2; i++) {
                a = a.next;
            }
        } else if (length2 > length1) {
            for (int i = 0; i < length2 - length1; i++) {
                b = b.next;
            }
        }

        while (a != null) {
            if (a.data == b.data) {
                return a;
            }
            a = a.next;
            b = b.next;
        }
        return null;
    }

}
