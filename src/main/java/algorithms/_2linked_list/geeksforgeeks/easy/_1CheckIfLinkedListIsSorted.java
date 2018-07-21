package algorithms._2linked_list.geeksforgeeks.easy;

import algorithms.crackingcodinginterviewbook._2linkedlistmanipulation.Node;
import algorithms.crackingcodinginterviewbook._2linkedlistmanipulation.SinglyLinkedList;
import com.google.common.collect.Lists;

/*
    https://www.geeksforgeeks.org/check-linked-list-sorting-order/

    Check if linked list is sorted (Iterative and Recursive)
    Given a Linked List, task is to check whether the Linked List is sorted in Descending order or not?

    Examples :

    Input  : 8 -> 7 -> 5 -> 2 -> 1
    Output : Yes
    Explanation :
    In given linked list, starting from head,
    8 > 7 > 5 > 2 > 1. So, it is sorted in reverse order

    Input  : 24 -> 12 -> 9 -> 11 -> 8 -> 2
    Output : No
*/
public class _1CheckIfLinkedListIsSorted {

    public static void main(String[] args) {
        {
            SinglyLinkedList linkedList = new SinglyLinkedList();
            linkedList.createLinkedListOfIntegers(Lists.newArrayList(1, 2, 3, 4, 5));
            boolean result = check(linkedList.head);
            System.out.println("Result: " + result);// true
        }
        {
            SinglyLinkedList linkedList = SinglyLinkedList.createLinkedListOfIntegers(Lists.newArrayList(1, 3, 2));
            boolean result = check(linkedList.head);
            System.out.println("Result: " + result);// false
        }
    }

    private static boolean check(Node head) {
        if (head == null) return true;

        if (head.next == null) return true;

        boolean remainingList = check(head.next);

        return (remainingList && head.data < head.data);
    }


}
