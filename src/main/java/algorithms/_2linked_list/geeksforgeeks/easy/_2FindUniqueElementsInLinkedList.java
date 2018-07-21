package algorithms._2linked_list.geeksforgeeks.easy;

/*
    Find unique elements in linked list

    Input : 1 -> 4 -> 4 -> 2 -> 3 -> 5 -> 3 -> 4 -> 5
    Output :1 2

    Simple approach:
    Two loops taking O(n^2)

    Better approach:
    Merge sort taking O(n log n)

    Best approach:
    Hashing.

    Traverse the link list from head to end. For every newly encountered element, we put it in the hash table after that we again traverse list and Print those elements whose frequency is 1
*/
public class _2FindUniqueElementsInLinkedList {
}
