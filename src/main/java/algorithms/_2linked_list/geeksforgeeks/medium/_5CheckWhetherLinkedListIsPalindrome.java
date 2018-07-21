package algorithms._2linked_list.geeksforgeeks.medium;

import algorithms._2linked_list.geeksforgeeks.hard._1reverse_linked_list._1ReverseLinkedList;
import algorithms.crackingcodinginterviewbook._2linkedlistmanipulation.Node;
import algorithms.crackingcodinginterviewbook._2linkedlistmanipulation.SinglyLinkedList;
import com.google.common.collect.Lists;

/*
    Check whether a linked list is palindrome

    https://www.youtube.com/watch?v=oZuR2-AKkXE


    Two approaches:

    1) Create two linkedlists from the middle. If Linked List is of odd size, then ignore the middle, otherwise include it.
    Reverse the one of the list and then compare both lists.

    2) Use stack.
    Based on whether the size of linked list even or odd, push elements to stack with/without mid element.
    Do not push elements after mid element to stack.

    linked list = 1   2   3   2   1
                runner
    stack
    2
    1
                            runner
    linked list = 1   2   3   2   1

    Now, pull element from stack and compare with runner.
 */
public class _5CheckWhetherLinkedListIsPalindrome {

    public static void main(String[] args) {
        {
            SinglyLinkedList list = SinglyLinkedList.createLinkedListOfIntegers(Lists.newArrayList(1, 2, 3, 2, 1));

            boolean isPalindrome = check(list.head);

            System.out.println(isPalindrome);// true
        }

        {
            SinglyLinkedList list = SinglyLinkedList.createLinkedListOfIntegers(Lists.newArrayList(1, 2, 3, 4, 1));

            boolean isPalindrome = check(list.head);

            System.out.println(isPalindrome);// false
        }
        {
            SinglyLinkedList list = SinglyLinkedList.createLinkedListOfIntegers(Lists.newArrayList(1, 2, 2, 1));

            boolean isPalindrome = check(list.head);

            System.out.println(isPalindrome);// true
        }

        {
            SinglyLinkedList list = SinglyLinkedList.createLinkedListOfIntegers(Lists.newArrayList(1, 2, 4, 1));

            boolean isPalindrome = check(list.head);

            System.out.println(isPalindrome);// false
        }
    }

    /*
       S,F
        1   2   3   2   1

        If a linkedlist is of odd size, then fast pointer will point to last node.
        If a linkedlist is of even size, then fast pointer will point to null node after last node.

        If list is of odd size, do not include slow pointer(which will point to middle node) in one of the lists that you need to create from one list.
        Otherwise, include a node pointed by slow pointer in first list.
     */
    private static boolean check(Node head) {

        if (head == null || head.next == null) {
            return true;
        }

        Node slow = head;
        Node fast = head;
        Node prevSlow = null;

        boolean isOdd = true;

        while (fast != null && fast.next != null) {
            prevSlow = slow;
            slow = slow.next;
            fast = fast.next.next;

            if (fast == null) {
                isOdd = false;
            }
        }

        Node list1Head = null;
        if (isOdd) {
            list1Head = _4CreateSubListFromLinkedList.createSubList(head, prevSlow);
        } else {
            list1Head = _4CreateSubListFromLinkedList.createSubList(head, prevSlow);
        }

        Node list2Head = null;
        if (isOdd) {
            list2Head = _4CreateSubListFromLinkedList.createSubList(slow.next);
        } else {
            list2Head = _4CreateSubListFromLinkedList.createSubList(slow);
        }

        Node reversedList2Head = _1ReverseLinkedList.reverseIterativelyEasy(list2Head);

        while (list1Head != null) {
            if (list1Head.data != reversedList2Head.data) {
                return false;
            }
            list1Head = list1Head.next;
            reversedList2Head = reversedList2Head.next;
        }

        return true;

    }

}
