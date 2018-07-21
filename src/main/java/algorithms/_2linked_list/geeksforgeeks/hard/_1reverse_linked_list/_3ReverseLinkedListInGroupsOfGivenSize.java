package algorithms._2linked_list.geeksforgeeks.hard._1reverse_linked_list;

import algorithms.crackingcodinginterviewbook._2linkedlistmanipulation.Node;
import algorithms.crackingcodinginterviewbook._2linkedlistmanipulation.SinglyLinkedList;
import com.google.common.collect.Lists;

/*
    Reverse a Linked List in groups of given size

    https://www.geeksforgeeks.org/reverse-a-list-in-groups-of-given-size/
*/
public class _3ReverseLinkedListInGroupsOfGivenSize {

    public static void main(String[] args) {
        SinglyLinkedList list = SinglyLinkedList.createLinkedListOfIntegers(Lists.newArrayList(1, 2, 3, 4, 5));
        Node newHead = reverse(list.head, 3);
        System.out.println(newHead.toString());
    }

    private static Node reverse(Node head, int k) {
        if (head == null) return head;

        Node prev = null;
        Node current = head;
        Node next = null;

        int count = 0;

        while (count < k && current != null) {// only additional condition to original reverse algorithm (ReverseLinkedList.java) is count < k
            next = current.next;

            current.next = prev;

            prev = current;
            current = next;

            count++;
        }

        head.next = reverse(next, k);

        return prev;

    }
}
