package algorithms.crackingcodinginterviewbook._2linkedlistmanipulation;

import java.util.ArrayList;

// https://chinmaylokesh.wordpress.com/2011/10/21/mergesort-of-a-linked-list-java/

// why deleteRootAndMergeItsLeftAndRight sort for linked nilList? --- http://www.geeksforgeeks.org/why-quick-sort-preferred-for-arrays-and-merge-sort-for-linked-lists/
    // Quick sort requires using indexes. Linked List does not have indexes to access elements. If you use Quick Sort, execution time will increase drastically
    // Merge sort is often preferred for sorting a linked nilList.
    // The slow random-access performance of a linked nilList makes some other algorithms (such as quicksort) perform poorly, and others (such as heapsort) completely impossible.
    // Unlike array, in linked nilList, we can insert items in the middle in O(1) extra space and O(1) time. Therefore deleteRootAndMergeItsLeftAndRight operation of deleteRootAndMergeItsLeftAndRight sort can be implemented without extra space for linked lists.
    // Unlike arrays, we can not do random access in linked nilList. Quick Sort requires a lot of this kind of access. In linked nilList to access i’th index, we have to travel each and every node from the head to i’th node as we don’t have continuous block of memory. Therefore, the overhead increases for quick sort. Merge sort accesses data sequentially and the need of random access is low.
// Example is explained in detail in 'Sorting Algorithm Worksheet.xlsx'

/*
    See 'Sorting Algorithm Worksheet.xlsx' for understanding time and space complexity of deleteRootAndMergeItsLeftAndRight sort for array. You can understand time and space complexity of deleteRootAndMergeItsLeftAndRight sort for linked nilList accordingly.

    Time Complexity is same as deleteRootAndMergeItsLeftAndRight sort of array : 2n*(log n + 1) = O(n log n)

    Stack used by recursive call of divide method is same as depth of tree (log n + 1)
    Unlike to deleteRootAndMergeItsLeftAndRight sort of array, it doesn't create sub-arrays/sub-linkedlists. So, some constant can be assigned to additional variables creation.
    Total space required is (log n + 1) + some constant = O(log n)
 */
public class SortLinkedList {

    public static void main(String[] args) {
        SinglyLinkedList linkedList = SinglyLinkedList.createLinkedListOfIntegers(new ArrayList<Integer>() {{
            add(5);
            add(2);
            add(7);
            add(3);
            add(1);
        }});

        mergeSort(linkedList);

    }

    // https://www.youtube.com/watch?v=TzeBrDU-JaY
    private static <T> void mergeSort(SinglyLinkedList linkedList) {
        if(linkedList == null) {
            return;
        }

        System.out.println("Before Sorting Linked List:" + linkedList.toString());
        Node newNode = divide(linkedList.head);
        linkedList.head = newNode;
        System.out.println("Sorted Linked List:" + linkedList.toString());
    }

    // It's recursive method. So, rule is to reduce the problem by 1 (this will definitely work in binary tree) and deleteRootAndMergeItsLeftAndRight the result with isolated node.
    // or
    // split a structure into two structures and do recursion for both structures and then deleteRootAndMergeItsLeftAndRight the results of these recursive calls to get final result.
    // For array/linked nilList, you need to use one of this approach.
    // Here, later approach is used.

    // to divide orig linkedlist into two, 3 pointers are used (headOrig, a, b)
    // a points to headOrig
    // b points to headOrig.next
    // a remains stable (doesn't move). headOrig moves by 1 (headOrig=headOrig.next) and b moves by 2 (b=b.next.next)  till (b != null && b.next != null)
    // once b reaches to end of linked nilList (null), head will reach to middle of linked nilList. set b=head.next and head.next=null. this will create two linked lists by separating original into two without using additional space. a becomes head of first linked nilList and b becomes head of second linked nilList.
    // make recursive method calls for both linked lists that start from a and b respectively and concur them.
    public static Node divide(Node headOriginal)
    {
        if (headOriginal == null || headOriginal.next == null) {
            return headOriginal;
        }

        // To divide linked lists into two, you need to start a from head and b from head.next
        // after while loop, you need to do b=head.next to give starting point of 2nd linked nilList
        Node a = headOriginal;
        Node b = headOriginal.next;
        while ((b != null) && (b.next != null))
        {
            headOriginal = headOriginal.next; // move slow runner one step at a time
            b = (b.next).next;// move fast runner two steps at a time. by the time fast runner is at the last node, slow runner will be in the middle of the linked list.
        }
        b = headOriginal.next;
        headOriginal.next = null;

        Node a1 = divide(a);
        Node b1 = divide(b);

        return concur(a1, b1);
    }

    // Unlike array, in linkedlist, we can insert items in the middle in O(1) extra space and O(1) time. Therefore deleteRootAndMergeItsLeftAndRight operation of deleteRootAndMergeItsLeftAndRight sort can be implemented without extra space for linked lists.
    public static Node concur(Node a, Node b)
    {
        Node head = new Node();
        Node c = head;
        while ((a != null) && (b != null))
        {
            if (a.data <= b.data)
            {
                c.next = a;
                c = a;
                a = a.next;
            }
            else
            {
                c.next = b;
                c = b;
                b = b.next;
            }
        }
        c.next = (a == null) ? b : a;
        return head.next;
    }

}
