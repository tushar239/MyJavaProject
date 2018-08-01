package algorithms._3binary_tree.geeksforgeeks.DFS._1level_order_vertical_order_diagonal_order_traversal_related_algorithms;

/*
    Find a width of Binary tree

    There are two ways to find a width of Binary Tree

        - Using Vertical Order Traversal

              https://www.youtube.com/watch?v=dwVOR2kh21E

            |
            |                       1 (hd=0)
            |              2 (hd=-1)              3 (hd=1)
            |       4 (hd=-2)       5 (hd=0)  6 (hd=0)     7 (hd=2)
            |
            ---------------------------------------------------------
            hd=     -2      -1        0           1           2

            Width = -(-2) + 2 + 1= 5
            Total number of vertical lines that you draw in a tree is the width of a tree.

            You can see the implementation of Vertical Order traversal in 'LevelOrderAndVerticalOrderAndDiagonalOrderTraversal.java'.
            It creates a Map<HD, List<TreeNode>>
                             -2,  4
                             -1,  2
                             0,   1,5,6
                             1,   3
                             2,   7

            you can find min and max of map keys easily to find the width of a binary tree.

        - Just traverse left nodes and keep increasing the temp1 variable
          Just traverse right nodes and keep increasing the temp2 variable
          width=temp1+temp2+1


          https://www.youtube.com/watch?v=IJEjdjLdAj0


    I like the first approach because I already know Vertical Order Traversal methodology.
*/
public class _5FindWidthOfBinaryTree {
}
