package algorithms._2linked_list.geeksforgeeks.medium;

/*
    Remove duplicates from a sorted linked list using recursion

    https://www.geeksforgeeks.org/remove-duplicates-sorted-linked-list-using-recursion/

    1 -> 1 -> 2 -> 2 -> 3

    reducing problem by 1
    1 ->        1 -> 2 -> 2 -> 3

    Assuming that remaining list has duplicates removed
    1 ->        1 -> 2 -> 3

    so now, first element has to be checked with next element. If they are equal, then remove second element by

    1        1 -x-> 2 -> 3
    |               ^
    |               |
    -----------------

    result: 1 -> 2 -> 3
*/
public class _3RemoveDuplicatesFromSortedLinkedList {
}
