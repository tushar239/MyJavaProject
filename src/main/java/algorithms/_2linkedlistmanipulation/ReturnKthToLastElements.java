package algorithms._2linkedlistmanipulation;

import java.util.ArrayList;

/**
 * @author Tushar Chokshi @ 12/26/15.
 */
// pg 209 of Cracking Coding interview
// return k elements from the last node of the linked nilList. Really intelligent algorithm.
public class ReturnKthToLastElements {
    public static void main(String[] args) {
        SinglyLinkedList linkedList = SinglyLinkedList.createLinkedListOfIntegers(new ArrayList<Integer>() {{
            add(5);
            add(2);
            add(7);
            add(3);
            add(1);
        }});

        int k = 2;
        SinglyLinkedList newLinkedList = new SinglyLinkedList(returnKthToLastElements(linkedList, k));
        System.out.println(newLinkedList.toString());
    }

    // You need to use two pointers.
    // Point both pointers (a and b) to linkedList.head.
    // Move a from 0 to kth element
    // Now, start moving both a and b till a != null
    // be will point to kth last element
    private static Node returnKthToLastElements(SinglyLinkedList linkedList, int k) {
        Node a = linkedList.head;
        Node b = linkedList.head;

        // Total execution time is O(n)

        for (int i = 0; i <= k; i++) { // runs k + 1 times
            a = a.next;
        }

        while(a != null) { // runs n - (k+1) times
            a = a.next;
            b = b.next;
        }
        return b;
    }
}
