package algorithms._2linkedlistmanipulation;

import java.util.ArrayList;

/**
 * @author Tushar Chokshi @ 12/26/15.
 */
// pg 209 of Cracking Coding interview
// return k elements from the last node of the linked nilList. Really intelligent algorithm.
public class _2ReturnKthToLastElements {
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


        FoundNode foundNode = test(linkedList.head, 3);
        System.out.println(foundNode.getNode().getData());
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


    static class FoundNode {
        private int count;
        private Node node;

        public FoundNode(int count) {
            this.count = count;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public Node getNode() {
            return node;
        }

        public void setNode(Node node) {
            this.node = node;
        }
    }
    private static FoundNode test(Node head, int k) {
        if(head == null) return new FoundNode(0);

        FoundNode foundNode = test(head.next, k);

        foundNode.setCount(foundNode.getCount()+1);
        if(foundNode.getCount() == k) {
            foundNode.setNode(head);
        }

        return foundNode;
    }
}
