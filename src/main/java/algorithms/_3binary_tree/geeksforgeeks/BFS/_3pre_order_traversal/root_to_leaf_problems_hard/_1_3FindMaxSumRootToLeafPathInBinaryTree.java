package algorithms._3binary_tree.geeksforgeeks.BFS._3pre_order_traversal.root_to_leaf_problems_hard;

import algorithms.crackingcodinginterviewbook._4tree_and_graph.tree.baseclasses.TreeNode;
import algorithms.utils.TreeUtils;

/*
    Find the maximum sum leaf to root path in a Binary Tree

    https://www.geeksforgeeks.org/find-the-maximum-sum-path-in-a-binary-tree/
*/
public class _1_3FindMaxSumRootToLeafPathInBinaryTree {

    public static void main(String[] args) {
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

        {
            findMaxPathSum(ten, 0);
            System.out.println("Max Sum:" + maxSum);
        }
        {
            int maxPathSum = findMaxPathSum_1(ten, 0, Integer.MIN_VALUE);
            System.out.println("Max Sum:" + maxPathSum);
        }
    }

    private static int maxSum = Integer.MIN_VALUE;

    private static void findMaxPathSum(TreeNode root, int initialSum) {

        if (root == null) {
            maxSum = Math.max(maxSum, initialSum);
            return;
        }

        if (isLeaf(root)) {
            maxSum = Math.max(maxSum, initialSum + root.data);
            return;
        }

        findMaxPathSum(root.left, initialSum + root.data);
        findMaxPathSum(root.right, initialSum + root.data);
    }

    public static int findMaxPathSum_1(TreeNode root, int initialSum, int maxSum) {

        if (root == null) {
            return Math.max(maxSum, initialSum);
        }

        if (isLeaf(root)) {
            return Math.max(maxSum, initialSum + root.data);
        }

        int maxPathSumGoingLeft = findMaxPathSum_1(root.left, initialSum + root.data, maxSum);
        int maxPathSumGoingRight = findMaxPathSum_1(root.right, initialSum + root.data, maxSum);

        return Math.max(maxPathSumGoingLeft, maxPathSumGoingRight);
    }


    private static boolean isLeaf(TreeNode root) {
        return root != null && root.left == null && root.right == null;
    }
}
