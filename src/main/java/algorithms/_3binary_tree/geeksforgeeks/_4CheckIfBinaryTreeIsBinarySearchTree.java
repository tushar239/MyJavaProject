package algorithms._3binary_tree.geeksforgeeks;

import algorithms.crackingcodinginterviewbook._4tree_and_graph.tree.baseclasses.TreeNode;
import algorithms.utils.TreeUtils;
import com.google.common.collect.Lists;

import java.util.List;

/*
    Check if Binary Tree is Binary Search Tree

    https://www.youtube.com/watch?v=MILxfAbIhrE

    DON'T READ ValidateBST.java, READ this solution of this class.


    If you just check that left node < root node < right node, then all the scenarios won't work.

    e.g.
    following case will work

        5
      3   9
     2 4 8 10

    but, following case will not work

        5
      4   7
     2 6 8 10

   In BST, all nodes in left subtree should be less than a parent node.
   6 is greater than 4, but it is greater than 5 also.

   Approach 1:

       In BST, every node has a lower bound and upper bound.

       e.g.
            root node's lowerbound = Integer.MIN_VALUE and upperbound = Integer.MAX_VALUE
            4's lowerbound = Integer.MIN_VALUE and upperbound = 5
            7's lowerbound = 5 and upperbound = Integer.MAX_VALUE
            6's lowerbound = 4 and upperbound = 5

            When you traverse left subtree, its lowerbound is same as parent node's lowerbound its upperbound is same as parent node's value.
            When you traverse right subtree, its lowerbound is same as parent node's value and its upperbound is same as parent node's upperbound.

        This approach takes O(n).

    Approach 2:

         Find Max node from left subtree

         If(this max value > root value)
            return false
         else
            Find Min node from right subtree

            If(this min value < root value)
                return false

         return true

         In this approach, on every level of the tree, you have to traverse entire subtrees. It will take O(height of tree * n)
*/
public class _4CheckIfBinaryTreeIsBinarySearchTree {

    public static void main(String[] args) {

        {
        /*
                5
              3   9
             2 4 8 10

            Is BST
         */
            List<Integer> list = Lists.newArrayList(2, 3, 4, 5, 8, 9, 10);
            BinaryTree binaryTree = new BinaryTree(TreeUtils.createBinaryTree(list, 0, list.size() - 1));

            System.out.println("Input Binary Tree:");
            TreeUtils.printPreety(binaryTree.getRoot());

            System.out.println("Is BST?:");
            System.out.println(checkBST(binaryTree.getRoot(), Integer.MIN_VALUE, Integer.MAX_VALUE));// true
        }
        {
        /*
                5
              4   7
             2 6 8 10

            Not a BST because 6 > 5
         */
            List<Integer> list = Lists.newArrayList(2, 4, 6, 5, 8, 7, 10);
            BinaryTree binaryTree = new BinaryTree(TreeUtils.createBinaryTree(list, 0, list.size() - 1));

            System.out.println("Input Binary Tree:");
            TreeUtils.printPreety(binaryTree.getRoot());

            System.out.println("Is BST?:");
            System.out.println(checkBST(binaryTree.getRoot(), Integer.MIN_VALUE, Integer.MAX_VALUE));// false
        }

    }

    private static boolean checkBST(TreeNode root, int lowerBound, int upperBound) {
        if (root == null) return true;

        if (root.data < lowerBound || root.data > upperBound) return false;

        return checkBST(root.left, lowerBound, root.data) && checkBST(root.right, root.data, upperBound);
    }
}
