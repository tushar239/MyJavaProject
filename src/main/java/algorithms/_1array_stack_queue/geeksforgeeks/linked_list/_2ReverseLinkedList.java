package algorithms._1array_stack_queue.geeksforgeeks.linked_list;

import algorithms.crackingcodinginterviewbook._2linkedlistmanipulation.Node;
import algorithms.crackingcodinginterviewbook._2linkedlistmanipulation.SinglyLinkedList;
import com.google.common.collect.Lists;

/*
    It is is easy to think recursive when you want to reverse a Linked List.

    1---->2---->3

    Reducing the problem by 1.

  head    newHeadNode
    1       3--------->2
    |                  ^
    |                  |
    --------------------

    after recursive call, just do

    head.next.next = head
    head.next = null
    head = newHeadNode


*/
public class _2ReverseLinkedList {

    public static void main(String[] args) {
        SinglyLinkedList list = SinglyLinkedList.createLinkedListOfIntegers(Lists.newArrayList(1, 2, 3));
        Node newHead = reverse(list.head);
        System.out.println(newHead.toString());
    }

    private static Node reverse(Node head) {
        if (head == null) return head;

        if (head.next == null) return head;

        Node newHead = reverse(head.next);

        head.next.next = head;
        head.next = null;

        head = newHead;

        return head;
    }
}
