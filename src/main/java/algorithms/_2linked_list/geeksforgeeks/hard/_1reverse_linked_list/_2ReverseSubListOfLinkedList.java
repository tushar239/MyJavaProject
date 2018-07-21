package algorithms._2linked_list.geeksforgeeks.hard._1reverse_linked_list;

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

        /*
           - you need to have 4 pointers
             1                    ->                  2 ->                    3 ->                    4 ->                    5
           prevOfStartOfSubList                  startOfSubList                                   endOfSubList            nextOfEndOfSubList

           - now send reverse(startOfSubList, endOfSubList)
                                                                 startOfSubList
                   result =         1                   4   ->  3   ->  2           5
                            prevOfStartOfSubList   newStartOfSubList           nextOfEndOfSubList


            - point original startOfSubList to nextOfEndOfSubList(2 to 5)

            startOfSubList.next = nextOfEndOfSubList;

            - point prevOfStartOfSubList to newStartOfSubList (1 to 4)

            if (prevOfStartOfSubList != null)
                prevOfStartOfSubList.next = newStartOfSubList;
                return head;
            else
                return newStartOfSubList; // new head of entire linkedlist

        */

        Node startOfSubList = head;
        Node endOfSubList = startOfSubList;

        Node prevOfHeadOfSubList = null;
        Node nextOfEndOfSubList = null;


        // finding out first two pointers
        for (int i = 0; i < m; i++) {
            prevOfHeadOfSubList = startOfSubList;
            startOfSubList = startOfSubList.next;
        }

        // finding out last two pointers
        for (int i = m; i <= n; i++) {
            endOfSubList = endOfSubList.next;
            nextOfEndOfSubList = endOfSubList.next;
        }


        // reversing a sublist
        Node newHeadOfSubList = recurse(startOfSubList, endOfSubList, nextOfEndOfSubList);

        startOfSubList.next = nextOfEndOfSubList;
        // attaching start of reversed sublist to original list
        if (prevOfHeadOfSubList == null) {
            return newHeadOfSubList;
        } else {
            prevOfHeadOfSubList.next = newHeadOfSubList;
            return head;
        }

    }

    /*
    Remember:

    list = 1 -> 2 -> 3 -> 4 -> 5
                                                                          originalStart
    if you reverse 2 -> 3 -> 4, reversal algorithm can return you 4 -> 3 -> 2 -> null, then you need to attach this reversed sublist to main list.
                                                                 newStart
    1 -> 4 -> 3 -> 2 -> 5

    */
    private static Node recurse(Node start, Node end, Node nextOfEnd) {

        if (start == null || end == null || start == end) return start;

        Node prev = null;
        Node current = start;
        Node next = null;

        while (current != nextOfEnd) {// this condition is slightly different than original reverse algorithm (ReverseLinkedList.java)
            next = current.next;

            current.next = prev;

            prev = current;
            current = next;
        }

        return prev;
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