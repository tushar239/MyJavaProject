package algorithms._3binary_tree.geeksforgeeks.BFS._3pre_order_traversal.root_to_leaf_problems_hard;

import algorithms.crackingcodinginterviewbook._4tree_and_graph.tree.baseclasses.TreeNode;
import algorithms.utils.TreeUtils;

/*
    Find the maximum path sum between two leaves of a binary tree

    https://www.youtube.com/watch?v=3dVCRMw6Eyk

*/
public class _1_4FindMaxPathSumBetweenTwoLeavesOfBinaryTree {

    public static void main(String[] args) {
        {
            /*
            Input Tree:

                 10
              8     2
            3   5
            */

            TreeNode ten = new TreeNode(10);
            TreeNode eight = new TreeNode(8);
            TreeNode two = new TreeNode(2);
            TreeNode three = new TreeNode(3);
            TreeNode five = new TreeNode(5);

            ten.left = eight;
            ten.right = two;
            eight.left = three;
            eight.right = five;

            System.out.println("Input Binary Tree:");
            TreeUtils.printPreety(ten);

            _1_4FindMaxPathSumBetweenTwoLeavesOfBinaryTree obj = new _1_4FindMaxPathSumBetweenTwoLeavesOfBinaryTree();

            int maxPathSum = obj.findMaxPathSum_1(ten);
            System.out.println("Output:");
            System.out.println(maxPathSum);//25 (5+8+10+2)
        }

        System.out.println();

        {
            /*
            Input Tree:

                -10
              8    2
            3   5
            */

            TreeNode ten = new TreeNode(-10);
            TreeNode eight = new TreeNode(8);
            TreeNode two = new TreeNode(2);
            TreeNode three = new TreeNode(3);
            TreeNode five = new TreeNode(5);

            ten.left = eight;
            ten.right = two;
            eight.left = three;
            eight.right = five;

            System.out.println("Input Binary Tree:");
            TreeUtils.printPreety(ten);

            _1_4FindMaxPathSumBetweenTwoLeavesOfBinaryTree obj = new _1_4FindMaxPathSumBetweenTwoLeavesOfBinaryTree();

            int maxPathSum = obj.findMaxPathSum_1(ten);
            System.out.println("Output:");
            System.out.println(maxPathSum);//16 (3+8+5)
        }
    }

    // not correct
    private static int findMaxPathSum(TreeNode root) {
        if (root == null) return 0;
        if (isLeafNode(root)) return root.data;

        int maxPathSumFromLeftSubTree = findMaxPathSum(root.left);
        int maxPathSumFromRightSubTree = findMaxPathSum(root.right);

        int maxPathSumIncludingRoot = Math.max(Math.max(root.data + maxPathSumFromLeftSubTree, root.data + maxPathSumFromRightSubTree), root.data + maxPathSumFromLeftSubTree + maxPathSumFromRightSubTree);

        return Math.max(Math.max(maxPathSumIncludingRoot, maxPathSumFromLeftSubTree), maxPathSumFromRightSubTree);

    }

    private int max = Integer.MIN_VALUE;

    private int findMaxPathSum_1(TreeNode root) {
        if (root == null) return 0;
        if (isLeafNode(root)) return root.data;

        if (root.left != null && root.right != null) {

            int maxPathSumInLeftSubTree = _1_3FindMaxSumRootToLeafPathInBinaryTree.findMaxPathSum_1(root.left, 0, Integer.MIN_VALUE);
            int maxPathSumInRightSubTree = _1_3FindMaxSumRootToLeafPathInBinaryTree.findMaxPathSum_1(root.right, 0, Integer.MIN_VALUE);

            max = Math.max(max, maxPathSumInLeftSubTree + root.data + maxPathSumInRightSubTree);

            int maxPathSumFromLeftSubTree = findMaxPathSum_1(root.left);
            int maxPathSumFromRightSubTree = findMaxPathSum_1(root.right);

            max = Math.max(Math.max(max, maxPathSumFromLeftSubTree), maxPathSumFromRightSubTree);
        } else if (root.left != null) {
            int maxPathSumFromLeftSubTree = findMaxPathSum_1(root.left);
            max = Math.max(max, maxPathSumFromLeftSubTree);
        } else {
            int maxPathSumFromRightSubTree = findMaxPathSum_1(root.right);
            max = Math.max(max, maxPathSumFromRightSubTree);
        }
        return max;
    }

    private static boolean isLeafNode(TreeNode node) {
        return node != null && node.left == null && node.right == null;
    }
}
