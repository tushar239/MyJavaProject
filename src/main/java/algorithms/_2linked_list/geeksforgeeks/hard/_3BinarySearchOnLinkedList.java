package algorithms._2linked_list.geeksforgeeks.hard;

import algorithms.crackingcodinginterviewbook._2linkedlistmanipulation.Node;
import algorithms.crackingcodinginterviewbook._2linkedlistmanipulation.SinglyLinkedList;

import java.util.Arrays;

/*
    Binary Search on Singly Linked List

    https://www.geeksforgeeks.org/binary-search-on-singly-linked-list/

    BS(1-2-3-4-5) --- mid finding algorithm takes O(n/2)
     |
    BS(1-2)  --- mid finding algorithm takes O(n/4)
     |
    BS(1)  --- mid finding algorithm takes O(n/6)

    so total time taken is n/2 + n/4 + n/6 +....... = n (1/2 + 1/4 + 1/6 + ...) = O(n)


    Binary Search on Linked List is a bad idea. When you do binary search on an array, finding a mid takes O(1), whereas finding a mid in linkedlist takes n/2+n/4+n/6 +... = O(n).
    Binary Search on array takes O(log n), whereas it is on linked list takes O(n).
    So, binary search does not give any advantage over simple search in case of linked list.

    If you are asked to do Binary Search on Singly Linked List, then remember that mid finding algorithm should return mid and midPrev.

*/
public class _3BinarySearchOnLinkedList {

    public static void main(String[] args) {
        SinglyLinkedList list = SinglyLinkedList.createLinkedListOfIntegers(Arrays.asList(1, 2, 3));

        Node runner = list.head;
        while (runner.next != null) {
            runner = runner.next;
        }

        {
            Node foundNode = binarySearch(list.head, runner, 1);
            System.out.println(foundNode.data);
        }

        {
            Node foundNode = binarySearch(list.head, runner, 3);
            System.out.println(foundNode.data);
        }

        {
            Node foundNode = binarySearch(list.head, runner, 2);
            System.out.println(foundNode.data);
        }
        {
            Node foundNode = binarySearch(list.head, runner, 15);
            System.out.println(foundNode);
        }

    }

    private static Node binarySearch(Node head, Node tail, int dataToFind) {
        if (head == null) return null;

/*
        // IMPORTANT: in case of binary search on linked list, it is avery important condition.
        if (head.next == null || head == tail) {
            if (head.data == dataToFind) {
                return head;
            }
            return null;
        }
*/

        // IMPORTANT: When you do binary search on linked list, mid finding algorithm should return mid and midPrev
        MidAndMidPrev midAndMidPrev = findMid(head, tail);

        Node mid = midAndMidPrev.mid;
        Node midPrev = midAndMidPrev.midPrev;

        if (mid.data == dataToFind) {
            return mid;
        } else if (dataToFind < mid.data) {
            tail = midPrev; // IMPORTANT: do not use mid as tail, otherwise at some point findMid will find the same mid again and again and binarySearch method will go in infinite loop.
        } else {
            head = mid.next;
        }

        return binarySearch(head, tail, dataToFind);
    }

    // using slow and fast runners. slow moves by one step, fast moves two steps.
    // when fast==null || fast.next==null, slow is at the middle of the list.
    // Same concept is used to find whether linked list size is odd or even.
    private static MidAndMidPrev findMid(Node head, Node tail) {
        Node slowRunnerPrev = null;

        Node slowRunner = head;
        Node fastRunner = head;

        while (fastRunner != tail && fastRunner != tail.next) {// important condition to find a mid
            slowRunnerPrev = slowRunner;
            slowRunner = slowRunner.next;
            fastRunner = fastRunner.next.next;
        }

        Node mid = slowRunner;
        return new MidAndMidPrev(mid, slowRunnerPrev);
    }

    private static class MidAndMidPrev {
        private Node mid, midPrev;

        public MidAndMidPrev(Node mid, Node midPrev) {
            this.mid = mid;
            this.midPrev = midPrev;
        }

        public Node getMid() {
            return mid;
        }

        public Node getMidPrev() {
            return midPrev;
        }
    }
}
