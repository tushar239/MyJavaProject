package algorithms.crackingcodinginterviewbook._4tree_and_graph.tree;

import algorithms.crackingcodinginterviewbook._4tree_and_graph.tree.baseclasses.BST;
import algorithms.crackingcodinginterviewbook._4tree_and_graph.tree.baseclasses.TreeNode;

/**
 * @author Tushar Chokshi @ 11/6/15.
 */

// http://www.geeksforgeeks.org/find-minimum-depth-of-a-binary-tree/
public class FindMinimumHeightOfATree {

    public static void main(String[] args) {
        BST bst = BST.createBST();
        System.out.println("Using PostOrder Traversal :"+ findMinHeight(bst.root));
        System.out.println("Using PreOrder Traversal :" + findMinHeightAnotherWay(bst.root, 0));
    }

    // PostOrder Traversal
    private static int findMinHeight(TreeNode root) {

        if(root == null) {
            return 0;
        }
        if(!root.hasLeft() && !root.hasRight()) {
            return 1;
        }

        int heightOfLeftSubTree = findMinHeight(root.left);
        int heightOfRightSubTree = findMinHeight(root.right);

        heightOfLeftSubTree += 1; // adding root node's height to total height of left sub tree
        heightOfRightSubTree += 1; // adding root node's height to total height of right sub tree

        if(heightOfLeftSubTree <= heightOfRightSubTree) {
            return heightOfLeftSubTree;
        }
        else {
            return heightOfRightSubTree;
        }
    }

    // PreOrder Traversal
    private static int findMinHeightAnotherWay(TreeNode root, int height) {

        if(root == null) {
            return height;
        }
        if(!root.hasLeft() && !root.hasRight()) {
            return height + 1;
        }

        height = height + 1; // adding root node's height

        int heightOfLeftSubTree = findMinHeightAnotherWay(root.left, height);
        int heightOfRightSubTree = findMinHeightAnotherWay(root.right, height);


        if(heightOfLeftSubTree <= heightOfRightSubTree) {
            return heightOfLeftSubTree;
        }
        else {
            return heightOfRightSubTree;
        }
    }
}
