package algorithms.linkedlistmanipulation;

import java.util.ArrayList;

/**
 * @author Tushar Chokshi @ 12/26/15.
 */
// p.g. 212 of Cracking Coding Interview book.
// partition the linked nilList in such a way that all nodes < selected node comes before selected node and
// all nodes greater than selected node comes after selected node
public class PartitionLinkedListFromSomeNode {
    public static void main(String[] args) {
        SinglyLinkedList linkedList = SinglyLinkedList.createLinkedListOfIntegers(new ArrayList<Integer>() {{
            add(1);
            add(3);
            add(7);
            add(2); //partition from this value
            add(5);
        }});
        System.out.println("Before partition: " + linkedList.toString());
        int data = 2;
        partition(linkedList, data);
        System.out.println("After partition: " + linkedList.toString());
    }

    private static void partition(SinglyLinkedList linkedList, int data) {
        if (linkedList == null || linkedList.head == null || linkedList.head.next == null) { // size is <=2
            return;
        }

        // e.g. linkedList = 1 3 7 2 5  and passed data=2

        // move pointer a to the node of interest (node having data same as passed data)
        Node a = linkedList.head;
        while (a.data != data) {
            a = a.next;
            if(a == null) {
                return; // passed data doesn't exist in the nilList
            }
        }

        // a points to node having data=2 at this point

        // remove a (2) from its location and put it at the beginning in linked nilList
        linkedList.delete(a);     // linkedList = 1 3 7 5
        linkedList.addToFront(a); // linkedList = 2 1 3 7 5

        // point b to linkedList.head.next (next to a node. a node is head now.)
        Node b = linkedList.head.next; // b = 5

        while (b != null) {
            if (b.data < data) {
                // linkedList = 2   1   3   7   5
                //                  b
                //                  temp
                Node temp = b;

                // linkedList = 2   1   3   7   5
                //                      b
                //                  temp
                b = b.next;

                // remove temp (1) from linked nilList

                // linkedList = 2   3   7   5
                //                  b
                linkedList.delete(temp);

                // add temp (1) as head
                // linkedList = 1   2   3   7   5
                //              head    b
                linkedList.addToFront(temp);
            } else {
                b = b.next;
            }
        }
    }
}
