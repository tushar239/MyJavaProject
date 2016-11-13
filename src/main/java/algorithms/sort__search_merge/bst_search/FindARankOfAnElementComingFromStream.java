package algorithms.sort__search_merge.bst_search;

// p.g. 412 from Cracking Coding Interview book
/*
You have a stream of numbers coming. Build a datastructure where you can put all these numbers and whenever needed, you can find any element from it with its rank..
Stream of data is NOT in sorted form.

As data is not in sorted form, you can't use Binary Search to find any element and its index.
So, another alternative is BST.
Create BST and run in-order traversal. in-order traversal will visit tree nodes in order. So, when you are visiting nodes to find a node that you need, keep a counter.
That counter will tell you node's order (rank)

 */
public class FindARankOfAnElementComingFromStream {
}
