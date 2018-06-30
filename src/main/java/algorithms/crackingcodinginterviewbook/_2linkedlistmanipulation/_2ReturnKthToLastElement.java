package algorithms.crackingcodinginterviewbook._2linkedlistmanipulation;

import java.util.ArrayList;

// pg 209 of Cracking Coding interview
// return k elements from the last node of the linkedlist. Really intelligent algorithm.
public class _2ReturnKthToLastElement {
    public static void main(String[] args) {
        SinglyLinkedList linkedList = SinglyLinkedList.createLinkedListOfIntegers(new ArrayList<Integer>() {{
            add(5);
            add(2);
            add(7);
            add(3);
            add(1);
        }});

        int k = 2;
        {
            Node foundNode = returnKthToLastElements(linkedList, k);
            System.out.println(foundNode.getData());
        }
        {
            FoundNode foundNode = returnKthToLastElements_Taking_Advantage_Of_Stack(linkedList.head, k);
            System.out.println(foundNode.getNode().getData());
        }
    }

    /*
    One Way:
     You need to use two pointers.
     Point both pointers (a and b) to linkedList.head.
     Move a from 0 to k-1th element
     Now, start moving both a and b till a.next != null
     be will point to kth last element

     5 - 2 - 7 - 3 - 1

     find 2nd last element

     head
     5 - 2 - 7 - 3 - 1
     a
     b

     move a k(two) times

     5 - 2 - 7 - 3 - 1
     b       a

    start moving b and a together now till a reaches to a null element

     5 - 2 - 7 - 3 - 1 -> null
                 b         a

    found element is b.

    */
    private static Node returnKthToLastElements(SinglyLinkedList linkedList, int k) {
        Node a = linkedList.head;
        Node b = linkedList.head;

        // Total execution time is O(n)

        for (int i = 0; i < k; i++) { // runs k times
            a = a.next;
        }

        while (a != null) { // runs n - k times
            a = a.next;
            b = b.next;
        }
        return b;
    }


    /*
    Another Way:
    Taking advantage of stack

        Increase a count as you pop method calls from stack.

                                       popping method call from stack

        null
        m(1)                            FoundNode(1, null)
        m(3)                            FoundNode(2, null)
        m(7)                            FoundNode(3, node(7))   --- k = 3. So, update found node.
        m(2)                            FoundNode(4, node(7))
        m(5)                            FoundNode(5, node(7))
    putting method call in stack



        Found node is node(7)
    */
    private static FoundNode returnKthToLastElements_Taking_Advantage_Of_Stack(Node head, int k) {
        if (head == null) return new FoundNode(0);

        FoundNode foundNode = returnKthToLastElements_Taking_Advantage_Of_Stack(head.next, k);

        foundNode.setCount(foundNode.getCount() + 1);
        if (foundNode.getCount() == k) {
            foundNode.setNode(head);
        }

        return foundNode;
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
}
