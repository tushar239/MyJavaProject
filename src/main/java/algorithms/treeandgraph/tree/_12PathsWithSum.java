package algorithms.treeandgraph.tree;

import algorithms.treeandgraph.tree.baseclasses.BST;
import algorithms.treeandgraph.tree.baseclasses.TreeNode;

public class _12PathsWithSum {

    public static void main(String[] args) {
        TreeNode node10 = new TreeNode(10);
        TreeNode node5 = new TreeNode(5);
        TreeNode node_3 = new TreeNode(-3);
        TreeNode node11 = new TreeNode(11);
        TreeNode node2 = new TreeNode(2);
        TreeNode node1 = new TreeNode(1);
        TreeNode node3 = new TreeNode(3);
        TreeNode nodeAnother3 = new TreeNode(3);
        TreeNode node_2 = new TreeNode(-2);

        node10.left = node5;
        node10.right = node_3;
        node_3.right = node11;
        node5.left = node3;
        node5.right = node2;
        node2.right = node1;
        node3.left = nodeAnother3;
        node3.right = node_2;

        BST bst = new BST();
        bst.root = node10;
        bst.printPreety();

        //System.out.println(countPathsWithSum(node10, 8, 0, 0));;

        System.out.println();

//        int totalPathsOfSum = countPathsWithSum(node10, 8);
//        System.out.println("totalPathsOfSum: " + totalPathsOfSum);

        int totalPathsOfSum_ = countPathsWithSum_(node10, 8);
        System.out.println("totalPathsOfSum: " + totalPathsOfSum_);

    }

    /*
    Create separate output variables from root processing, left subtree processing and right subtree processing and then merge them as needed.
    Don’t take a chance of using the same output variable between these 3 processing.


     */
    private static int countPathsWithSum_(TreeNode root, int expectedSum) {
        if (root == null) return 0;

        int totalPathsFromRoot = countPathsWithSum_(root, expectedSum, 0);

        int totalPathsFromLeft = countPathsWithSum_(root.left, expectedSum);
        int totalPathsFromRight = countPathsWithSum_(root.right, expectedSum);

        return totalPathsFromRoot + totalPathsFromLeft + totalPathsFromRight;
    }


    /*
    Important:
    ßyou started hardcoding currentSum (that is not the expected output of the method) to root.data. As soon as you saw that, you realized that it is shared between recursive method calls. So, it should be passed as method parameter.

    private static int countPathsWithSum_(TreeNode root, int expectedSum) {
        if (root == null) return 0;

        int totalPaths = 0;
        currentSum = root.data;
        if (expectedSum == currentSum) {
            totalPaths++;
        }
        ...
    }
     */
    private static int countPathsWithSum_(TreeNode root, int expectedSum, int currentSum) {
        if (root == null) return 0;

        int totalPaths = 0;
        currentSum += root.data;// Important: you started hardcoding currentSum (that is not the expected output of the method) to root.data. As soon as you saw that, you realized that it is shared between recursive method calls. So, it should be passed as method parameter.
        if (expectedSum == currentSum) {
            totalPaths++;
        }

        int totalPathsFromLeft = countPathsWithSum_(root.left, expectedSum, currentSum);
        int totalPathsFromRight = countPathsWithSum_(root.right, expectedSum, currentSum);

        return totalPaths + totalPathsFromLeft + totalPathsFromRight;
    }

/*
    private static int countPathsWithSum(TreeNode root, int expectedSum) {
        if (root == null) return 0;

        int totalPaths = 0;

        totalPaths += countPathsWithSum(root, expectedSum, 0);

        totalPaths = totalPaths + countPathsWithSum(root.left, expectedSum);
        totalPaths = totalPaths + countPathsWithSum(root.right, expectedSum);

        return totalPaths;
    }

    private static int countPathsWithSum(TreeNode root, int expectedSum, int currentSum) {
        if (root == null) return 0;

        int totalPaths = 0;

        currentSum += root.data;
        if (expectedSum == currentSum) {
            totalPaths++;
        }

        totalPaths = totalPaths + countPathsWithSum(root.left, expectedSum, currentSum);
        totalPaths = totalPaths + countPathsWithSum(root.right, expectedSum, currentSum);

        return totalPaths;
    }*/

}
