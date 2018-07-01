package algorithms._1array_stack_queue.geeksforgeeks.linked_list;

import algorithms.crackingcodinginterviewbook._2linkedlistmanipulation.Node;
import algorithms.crackingcodinginterviewbook._2linkedlistmanipulation.SinglyLinkedList;
import com.google.common.collect.Lists;

/*

   Reverse a Linked List:

   https://www.geeksforgeeks.org/reverse-a-linked-list/


    Recursive approach:

       This is very important algorithm to understand the recursion in Linked List.

        It is is easy to think recursive when you want to reverse a Linked List. It is hard to reverse a sublist using recursion (ReverseSubListOfLinkedList.java). So, memorize iterative approach also along with recursive approach.

        1---->2---->3---->null

        Reducing the problem by 1.

        After recursive call (reversing rest of the linked list), situation will be as shown below

       head    newHeadNode
        1       3--------->2 ----> null
        |                  ^
        |                  |
        --------------------

        Now, just do

        head.next.next = head
        head.next = null
        head = newHeadNode


    Iterative approach:

        It is is easy to think recursive when you want to reverse a Linked List. It is hard to reverse a sublist using recursion (ReverseSubListOfLinkedList.java). So, memorize iterative approach also along with recursive approach.

        1 -> 2 -> 3

        For reversing the linked list, you just need to reverse the arrow direction between two nodes.

        1 <- 2 -> 3
        1 <- 2 <- 3

        At the end, head should eventually be pointed to last element.

        See reverseIteratively method.


*/
public class _2ReverseLinkedList {

    public static void main(String[] args) {
        {
            SinglyLinkedList list = SinglyLinkedList.createLinkedListOfIntegers(Lists.newArrayList(1, 2, 3));
            Node newHead = reverseRecursively(list.head);
            System.out.println(newHead.toString());
        }
        {
            SinglyLinkedList list = SinglyLinkedList.createLinkedListOfIntegers(Lists.newArrayList(1, 2, 3, 4, 5));
            Node newHead = reverseIteratively(list.head);
            System.out.println(newHead.toString());
        }

    }

    public static Node reverseRecursively(Node head) {
        if (head == null) return head;

        if (head.next == null) return head;

        Node newHead = reverseRecursively(head.next);

        head.next.next = head;
        head.next = null;

        head = newHead;

        return head;
    }

    /*
        You need three pointers - Start, Next, NextNext

       start      next   nextnext
        1       -> 2      -> 3     -> null
       head

       reverse the link between start(1) and next(2).

       next.next = start
       if(start.next == next) start.next = null;   This is an important condition. You break the link between start and next, only if next is start's next.

                  start      next   nextnext
        1       <- 2         3     -> null
       head


       next.next = start
       start.next = null is not needed here because start.next != next. If you do that, link will be broken between 2 and 1

                  start      next   nextnext
        1       <- 2       <- 3     -> null
       head


        Now, nextnext == null, so new head of the linked list is 'next'. So, return 'next' as a new head or simply point 'head' to 'next'.


      It's a complicated algorithm, you need to memorize it.
    */
    private static Node reverseIteratively(Node head) {
        if (head == null) return head;

        if (head.next == null) return head;

        Node start = head;
        Node next = start.next;
        Node nextNext = next.next;

        while (true) {

            if (nextNext == null) {
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
}
