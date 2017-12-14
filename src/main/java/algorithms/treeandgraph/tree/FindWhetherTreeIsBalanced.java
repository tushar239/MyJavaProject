package algorithms.treeandgraph.tree;

import algorithms.treeandgraph.tree.baseclasses.BST;
import algorithms.treeandgraph.tree.baseclasses.TreeNode;

/**
 * @author Tushar Chokshi @ 11/6/15.
 */

// http://www.programcreek.com/2013/02/leetcode-balanced-binary-tree-java/
// http://www.geeksforgeeks.org/how-to-determine-if-a-binary-tree-is-balanced/
public class FindWhetherTreeIsBalanced {

    public static void main(String[] args) {
        {
            BST bst = BST.createBST();
            System.out.println("Testing Balanced Tree:"+ findWhetherTreeIsBalanced(bst.root));
            System.out.println("Testing Balanced Tree Second Way:"+ findWhetherTreeIsBalancedSecondWay(bst.root));
        }
        {
            BST unBalancedBst = BST.createUnBalancedBST();
            System.out.println("Testing UnBalanced Tree:"+ findWhetherTreeIsBalanced(unBalancedBst.root));
            System.out.println("Testing UnBalanced Tree Second Way:"+ findWhetherTreeIsBalancedSecondWay(unBalancedBst.root));
        }


    }

    // Best way

    /*
        This algorithm is a bit interesting because
        if you think of recursive strategy (reducing problem by 1), after calling recursive method for root.left and root.right you don't really know how to deleteRootAndMergeItsLeftAndRight the result with root
        Actual result is determined by the height of a tree and recursive method is returning boolean.
        So, you need a helper method to get a height of left and right subtrees under root.

        The same approach can be applied to all recursive calls that actually requires some other helper method. So REMEMBER this algorithm very well.
        Similar algorithm is CreateMirrorImageOfATree.java.

    */

    private static boolean findWhetherTreeIsBalanced(TreeNode root) {
        if(root == null) {
            return true;
        }
        if(!root.hasLeft() && !root.hasRight()) {
            return true;
        }

        boolean isLeftTreeBalanced = findWhetherTreeIsBalanced(root.left);
        boolean isRightTreeBalanced = findWhetherTreeIsBalanced(root.right);

        int leftTreeHeight = getHeight(root.left);
        int rightTreeHeight = getHeight(root.right);
        leftTreeHeight++; // adding root's height
        rightTreeHeight++;// adding root's height

        return Math.abs(leftTreeHeight - rightTreeHeight) <= 1 && isLeftTreeBalanced && isRightTreeBalanced;
    }

    private static boolean findWhetherTreeIsBalancedSecondWay(TreeNode root) {

        if(root == null) {
            return true;
        }
        if(!root.hasLeft() && !root.hasRight()) {
            return true;
        }

        int leftHeight = getHeight(root.left);
        int rightHeight = getHeight(root.right);

        leftHeight++; // adding root's height
        rightHeight++;// adding root's height

        //int heightDiff = leftHeight - rightHeight;
        //if(heightDiff == 0 || heightDiff == 1 || heightDiff == -1) {
        return Math.abs(leftHeight - rightHeight) <= 1;

    }

    private static int getHeight(TreeNode parent/*, int height*/) {

        if(parent == null) {
            //return height;
            return 0;
        }
        if(!parent.hasLeft() && !parent.hasRight()) {
            //return height + 1;
            return 1;
        }

        //height = height + 1;

        int leftHeight = getHeight(parent.left/*, height*/);
        int rightHeight = getHeight(parent.right/*, height*/);

        leftHeight++; // adding parent parent's height
        rightHeight++;// adding parent parent's height

        if(leftHeight >= rightHeight) {
            return leftHeight;
        }
        else {
            return rightHeight;
        }

    }


}
