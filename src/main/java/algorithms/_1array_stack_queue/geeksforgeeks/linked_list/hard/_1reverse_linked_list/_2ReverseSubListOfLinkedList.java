package algorithms._1array_stack_queue.geeksforgeeks.linked_list.hard._1reverse_linked_list;

import algorithms.crackingcodinginterviewbook._2linkedlistmanipulation.Node;
import algorithms.crackingcodinginterviewbook._2linkedlistmanipulation.SinglyLinkedList;
import com.google.common.collect.Lists;

/*
    Reverse a sublist of linked list

    https://www.geeksforgeeks.org/reverse-sublist-linked-list/
*/
public class _2ReverseSubListOfLinkedList {

    public static void main(String[] args) {
        SinglyLinkedList list = SinglyLinkedList.createLinkedListOfIntegers(Lists.newArrayList(1, 2, 3, 4, 5));
        int start = 1;
        int end = 3;

        //Node newHead = reverse(list.head, start, end);

        Node newHead = reverseSubListOfLinkedList(list.head, start, end);

        System.out.println(newHead);// 1-> 4-> 3 -> 2 -> 5
    }

    private static Node reverseSubListOfLinkedList(Node head, int m, int n) {

        // you need to have 4 pointers
        // 1                    ->                  2 ->                    3 ->                    4 ->                    5
        // prevOfHeadOfSubList                  headOfSubList                                   endOfSubList            nextOfEndOfSubList
        Node headOfSubList = head;
        Node endOfSubList = headOfSubList;

        Node prevOfHeadOfSubList = null;
        Node nextOfEndOfSubList = null;


        // finding out first two pointers
        for (int i = 0; i < m; i++) {
            prevOfHeadOfSubList = headOfSubList;
            headOfSubList = headOfSubList.next;
        }

        // finding out last two pointers
        for (int i = m; i < n; i++) {
            endOfSubList = endOfSubList.next;
            nextOfEndOfSubList = endOfSubList.next;
        }


        // reversing a sublist
        Node newHeadOfSubList = reverseLinkedListIteratively(headOfSubList, endOfSubList);

        Node endNodeOfReversedSubList = newHeadOfSubList;
        while (endNodeOfReversedSubList.next != null) {
            endNodeOfReversedSubList = endNodeOfReversedSubList.next;
        }

        endNodeOfReversedSubList.next = nextOfEndOfSubList; // attaching end of reversed sublist to original list

        // attaching start of reversed sublist to original list
        if (prevOfHeadOfSubList == null) {
            return newHeadOfSubList;
        } else {
            prevOfHeadOfSubList.next = newHeadOfSubList;
            return head;
        }
    }

    // This algorithm is same as ReverseLinkedList.java. The only difference is that exit condition of while loop changes from nextNext == null to nextNext == end.next
    private static Node reverseLinkedListIteratively(Node head, Node end) {

        if (head == null || end == null || head == end) return head;

        Node start = head;
        Node next = start.next;
        Node nextNext = next.next;

        while (true) {

            if (nextNext == end.next) {
                // reverse a link between two adjacent elements
                if (start.next == next) { // tricky condition
                    start.next = null;
                }
                next.next = start;

                // new head of reversed linked list
                return next;
            }

            // reverse a link between two adjacent elements
            if (start.next == next) { // tricky condition
                start.next = null;
            }
            next.next = start;

            // Move all 3 pointers by one step
            start = next;
            next = nextNext;
            nextNext = nextNext.next;
        }

    }


    /*
        Don't try recursion on reversing a sublist. It will be painful.
    */
    private static Node reverse(Node head, int start, int end) {
        Node prevOfStartNode = null;
        Node startIndexHead = head;

        for (int i = 0; i < start; i++) {
            if (startIndexHead.next == null)
                throw new RuntimeException("start index is beyond the size of a linked list");
            prevOfStartNode = startIndexHead;
            startIndexHead = startIndexHead.next;
        }

        Node endIndexHead = startIndexHead;

        for (int i = start; i < end; i++) {
            if (endIndexHead.next == null) throw new RuntimeException("end index is beyond the size of a linked list");
            endIndexHead = endIndexHead.next;
        }

        reverseRecursively(head, startIndexHead, prevOfStartNode, endIndexHead);
        return head;

    }

    private static Node reverseRecursively(Node head, Node headOfSubList, Node prevOfHeadOfSubList, Node endOfSubList) {
        if (headOfSubList == endOfSubList) return headOfSubList;

        Node newHeadOfRemainingSubList = reverseRecursively(head, headOfSubList.next, headOfSubList, endOfSubList);

        headOfSubList.next = endOfSubList.next;
        endOfSubList.next = headOfSubList;

        if (prevOfHeadOfSubList != null) {
            prevOfHeadOfSubList.next = newHeadOfRemainingSubList;
        } else {
            head = newHeadOfRemainingSubList;
        }

        endOfSubList = headOfSubList;// this will have no effect. .........
//        headOfSubList = newHeadOfRemainingSubList;

        return newHeadOfRemainingSubList;

    }

}