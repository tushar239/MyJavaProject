package algorithms.treemanipulations;

import algorithms.treemanipulations.baseclasses.BST;
import algorithms.treemanipulations.baseclasses.TreeNode;

/**
 * @author Tushar Chokshi @ 11/6/15.
 */
// http://www.programcreek.com/2012/12/leetcode-validate-binary-search-tree-java/
// http://codereview.stackexchange.com/questions/67928/validating-a-binary-search-tree
public class ValidateBST {

    public static void main(String[] args) {
        {
            BST bst = BST.createBST();
            System.out.println("Test BST one approach:"+ isBinarySearchTreeAnotherWay(bst.root, null, null));
            System.out.println("Test BST another approach:"+ isBinarySearchTree(bst.root));
        }
        System.out.println();
        {
            BST bst = BST.createNonBST();
            System.out.println("Test Non-BST one approach:"+ isBinarySearchTreeAnotherWay(bst.root, null, null));
            System.out.println("Test Non-BST another approach:"+ isBinarySearchTree(bst.root));
        }
    }

    // I like this approach compare to another one
    private static boolean isBinarySearchTree(TreeNode root) {

        if (root == null) {
            return true;
        }

        if(!root.hasLeft() && !root.hasRight()) {
            return true;
        }

        if(root.hasLeft()) {
            if ( root.left.data.compareTo(root.data) > 0) {
                // node is not after (the same as) than min
                return false;
            }

        }
        if(root.hasRight()) {
            if (root.right.data.compareTo(root.data) <= 0) {
                return false;
            }
        }

        boolean isLeftTreeBst = isBinarySearchTree(root.left);
        boolean isRightTreeBst = isBinarySearchTree(root.right);

        return isLeftTreeBst && isRightTreeBst;
    }

    private static boolean isBinarySearchTreeAnotherWay(TreeNode node, Integer min, Integer max) {

        if (node == null) {
            return true;
        }

        if (min != null && min.compareTo(node.data) > 0) {
            // node is not after (the same as) than min
            return false;
        }
        if (max != null && max.compareTo(node.data) <= 0) {
            return false;
        }

        boolean isLeftTreeBst = isBinarySearchTreeAnotherWay(node.left, min, node.data);
        boolean isRightTreeBst = isBinarySearchTreeAnotherWay(node.right, node.data, max);

        return isLeftTreeBst && isRightTreeBst;
    }
}
