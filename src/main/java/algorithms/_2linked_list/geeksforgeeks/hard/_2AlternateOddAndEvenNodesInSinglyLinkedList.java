package algorithms._2linked_list.geeksforgeeks.hard;

import algorithms.crackingcodinginterviewbook._2linkedlistmanipulation.Node;
import algorithms.crackingcodinginterviewbook._2linkedlistmanipulation.SinglyLinkedList;

import java.util.Arrays;

/*
    Alternate Odd and Even Nodes in a Singly Linked List

    https://www.geeksforgeeks.org/alternate-odd-even-nodes-singly-linked-list/

    Input : 11 -> 20 -> 40 -> 55 -> 77 -> 80 -> NULL
    Output : 11 -> 20 -> 55 -> 40 -> 77 -> 80 -> NULL
    20, 40, 80 occur in even positions and 11, 55, 77 occur in odd positions.


    1 -> 3 -> 5 -> 2


    Simple Solution:

        It takes O(n) time and O(n) space.

        Traverse entire linkedlist. Put odd elements in one sack and even in another.
        Then merge both as required.

        Effective Solution:

            Traverse entire linkedlist. When any odd element comes, add to the head of linked list. Put odd elements at the beginning of the linked list and even at the end.

            Then keep below pointers, one that points to head(odd element) and another that points to first even element.

    oddPrev odd    oddNext   evenPrev  even   evenNext
             1 ->   3 ->         5 ->   2 ->     4      -> 6 -> 8 -> 10 -> null

        IMPORTANT:
        You need to keep prev and next pointers when you need to remove/add the node from the middle of the singly linked list.

        Remove the even element and insert it after first odd element.
        Jump odd element pointer by 2 steps, so that it points to next odd element.

        1 -> 2 -> 3 -> 5 -> 4 -> 6 -> 8 -> 10 -> null
                  odd      even

        1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 8 -> 10 -> null
                           odd   even

        Now, odd's next is even, so stop here.

*/
public class _2AlternateOddAndEvenNodesInSinglyLinkedList {

    public static void main(String[] args) {
        SinglyLinkedList list = SinglyLinkedList.createLinkedListOfIntegers(Arrays.asList(1, 2, 3, 4, 5));
        Node newHead = alternate(list.head);
        System.out.println(newHead);
    }

    private static Node alternate(Node head) {
        // you need min 3 elements in linked list
        if (head == null || head.next == null || head.next.next == null) return head;

        Node newHead = putOddsAtTheBeginningOfList(head);// O(n)

        if (newHead.data % 2 == 0) { // all elements are even
            return newHead;
        }

        // starting element of odd elements
        Node odd = newHead;
        Node oddNext = odd.next;

        // finding starting element of even elements
        Node even = newHead;
        Node evenPrev = null;
        while (even.next != null) { //O(n)
            if (even.data % 2 != 0) {
                evenPrev = even;
                even = even.next;
            } else {
                break;
            }

        }
        Node evenNext = even.next;

        // traversing entire list and putting even element after odd element
        while (oddNext != even && evenNext != null) { // O(n)
            // removing even element and putting it after odd element
            evenPrev.next = evenNext;
            even.next = oddNext;
            odd.next = even;

            // moving required pointer
            odd = oddNext;
            oddNext = odd.next;
            even = evenNext;
            evenNext = even.next;
        }

        return newHead;
    }

    private static Node putOddsAtTheBeginningOfList(Node head) {
        Node runnerPrev = head;
        Node runner = runnerPrev.next;

        while (runner != null) {

            if (runner.data % 2 != 0) {//is odd
                runnerPrev.next = runner.next;
                runner.next = head;
                head = runner;

                runner = runnerPrev.next;
            } else {
                runnerPrev = runner;
                runner = runner.next;
            }

        }

        return head;
    }
}
