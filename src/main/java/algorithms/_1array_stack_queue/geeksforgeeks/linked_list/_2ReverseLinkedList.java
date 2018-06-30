package algorithms._1array_stack_queue.geeksforgeeks.linked_list;

import algorithms.crackingcodinginterviewbook._2linkedlistmanipulation.Node;
import algorithms.crackingcodinginterviewbook._2linkedlistmanipulation.SinglyLinkedList;
import com.google.common.collect.Lists;

/*

   Reverse a Linked List:

   https://www.geeksforgeeks.org/reverse-a-linked-list/


    Recursive approach:

       This is very important algorithm to understand the recursion in Linked List.

        It is is easy to think recursive when you want to reverse a Linked List.

        1---->2---->3

        Reducing the problem by 1.

      head    newHeadNode
        1       3--------->2
        |                  ^
        |                  |
        --------------------

        after recursive call (reversing rest of the linked list), just do

        head.next.next = head
        head.next = null
        head = newHeadNode


    Iterative approach:

        1 -> 2 -> 3

        For reversing the linked list, you just need to reverse the arrow direction between two nodes.

        1 <- 2 -> 3
        1 <- 2 <- 3

        At the end, head should eventually be pointed to last element.


*/
public class _2ReverseLinkedList {

    public static void main(String[] args) {
        SinglyLinkedList list = SinglyLinkedList.createLinkedListOfIntegers(Lists.newArrayList(1, 2, 3));
        Node newHead = reverseRecursively(list.head);
        System.out.println(newHead.toString());
    }

    private static Node reverseRecursively(Node head) {
        if (head == null) return head;

        if (head.next == null) return head;

        Node newHead = reverseRecursively(head.next);

        head.next.next = head;
        head.next = null;

        head = newHead;

        return head;
    }
}
