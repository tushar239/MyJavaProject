package algorithms._3binary_tree.geeksforgeeks.DFS._1level_order_vertical_order_diagonal_order_traversal_related_algorithms;

import algorithms.crackingcodinginterviewbook._4tree_and_graph.tree.baseclasses.TreeNode;
import algorithms.utils.TreeUtils;

/*
    Construct a complete binary tree from given array in level order fashion

    https://www.geeksforgeeks.org/construct-complete-binary-tree-given-array/

    If we observe carefully we can see that if parent node is at index i in the array then the left child of that node is at index (2*i + 1) and right child is at index (2*i + 2) in the array.
*/
public class _2_1CreateBinaryTreeFromGivenArrayInLevelOrderFashion {

    public static void main(String[] args) {
        int[] A = {1,2,3,4,5,6,7};

        System.out.println("Approach 1...");
        {
            TreeNode root = createBinaryTree(A);
            TreeUtils.printPreety(root);
        }

        System.out.println("Approach 2: Better approach...");
        {
            TreeNode root = createBinaryTreeBetterWay(A, 0, A.length-1);
            TreeUtils.printPreety(root);
        }


    }

    private static TreeNode createBinaryTree(int[] A) {
        if(A == null || A.length == 0) return null;

        if(A.length==1) return new TreeNode(A[0]);

        TreeNode root = new TreeNode(A[0]);

        createBinaryTree(root, 0, A);

        return root;
    }

    private static void createBinaryTree(TreeNode root, int startIndex, int[] A) {

        int indexOfLeftElement = 2*startIndex +1;
        int indexOfRightElement = 2*startIndex +2;

        if(A.length > indexOfLeftElement) {
            root.left = new TreeNode(A[indexOfLeftElement]);
            createBinaryTree(root.left, indexOfLeftElement, A);
        }

        if(A.length > indexOfRightElement) {
            root.right = new TreeNode(A[indexOfRightElement]);
            createBinaryTree(root.right, indexOfRightElement, A);
        }
    }

    private static TreeNode createBinaryTreeBetterWay(int[] A, int start, int end) {
        if(A == null || A.length == 0) return null;

        if(start == end) return new TreeNode(A[start]);

        if(start > end) return null;

        TreeNode root = new TreeNode(A[start]);

        root.left = createBinaryTreeBetterWay(A, 2*start + 1, end);

        root.right = createBinaryTreeBetterWay(A, 2*start + 2, end);

        return root;
    }
}
