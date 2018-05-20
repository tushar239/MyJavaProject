package algorithms.crackingcodinginterviewbook._4tree_and_graph.tree;

// http://www.geeksforgeeks.org/binary-tree-to-binary-search-tree-conversion/
/*
    Example 1
Input:
          10
         /  \
        2    7
       / \
      8   4
Output:
          8
         /  \
        4    10
       / \
      2   7


Example 2
Input:
          10
         /  \
        30   15
       /      \
      20       5
Output:
          15
         /  \
       10    20
       /      \
      5        30


    1. do in-order traversal to create an array of binary tree nodes
    2. Sort this array using Arrays.sort(array) -- uses Quick Sort
    3. Again do in-order traversal of binary tree. This time when you visit a node, put an element from sorted array to that node.
 */
public class ConvertBinaryTreeToBinarySearchTree {
    public static void main(String[] args) {

    }
}
