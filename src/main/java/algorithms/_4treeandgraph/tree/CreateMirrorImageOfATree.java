package algorithms._4treeandgraph.tree;

import algorithms._4treeandgraph.tree.baseclasses.BST;
import algorithms._4treeandgraph.tree.baseclasses.TreeNode;

/**
 * @author Tushar Chokshi @ 11/8/15.
 */

// Below link shows how to create a mirror image of n-ary tree.
// http://www.geeksforgeeks.org/mirror-of-n-ary-tree/
// From this I got an idea to do it for binary tree.

// look at FindWhetherTreeIsBalanced.java first to understand the approach.
public class CreateMirrorImageOfATree {

    public static void main(String[] args) {
        System.out.println("One Way (Better Way)");
        {
            BST bst = BST.createBST();
            System.out.println("Before");
            bst.printPreety();
            createMirrorImageOfATree(bst.root);
            System.out.println("After");
            bst.printPreety();
        }

        System.out.println("Another Way");
        {
            BST bst = BST.createBST();
            System.out.println("Before");
            bst.printPreety();
            createMirrorImageOfATreeAnotherWay(bst.root);
            System.out.println("After");
            bst.printPreety();
        }

    }

    private static void createMirrorImageOfATree(TreeNode root) {
        if(root == null) {
            return;
        }
        if(!root.hasLeft() && !root.hasRight()) {
            return;
        }

        // you can do it here as preOrder Traversal or put it after calling create*** recursively as postOrder traversal
        // because traversing parent node first does not have any effect of children nodes and vice-versa
        reverseChildren(root);

        createMirrorImageOfATree(root.left);
        createMirrorImageOfATree(root.right);

    }

    private static void reverseChildren(TreeNode node) {
        if(node == null) {
            return;
        }
        if(!node.hasLeft() && !node.hasRight()) {
            return;
        }
        TreeNode tempNode = node.left;
        node.left = node.right;
        node.right = tempNode;
    }

    private static void createMirrorImageOfATreeAnotherWay(TreeNode root) {
        if(root == null) {
            return;
        }
        if(!root.hasLeft() && !root.hasRight()) {
            return;
        }

        reverseChildrenAnotherWay(root);

    }

    private static void reverseChildrenAnotherWay(TreeNode root) {
        if(root == null) {
            return;
        }
        if(!root.hasLeft() && !root.hasRight()) {
            return;
        }
        TreeNode tempNode = root.left;
        root.left = root.right;
        root.right = tempNode;

        reverseChildrenAnotherWay(root.left);
        reverseChildrenAnotherWay(root.right);
    }
}
