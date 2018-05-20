package algorithms.crackingcodinginterviewbook._4tree_and_graph.tree;

import algorithms.crackingcodinginterviewbook._4tree_and_graph.tree.baseclasses.BST;
import algorithms.crackingcodinginterviewbook._4tree_and_graph.tree.baseclasses.TreeNode;

/**
 * @author Tushar Chokshi @ 9/3/15.
 */
// Cracking Coding Interview Book p.g. 248
// http://www.geeksforgeeks.org/inorder-successor-in-binary-search-tree/
/*
Find In-Order Successor in BST:
Write an algorithm to find the "next" node (i.e.,in-order successor) of a given node in a binary search tree.
You may assume that each node has a link to its parent.

e.g. in-order successor of 5 in following in-order array of bst is 6.

{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15}
*/
public class _5FindInOrderSuccessor {

    public static void main(String[] args) {

        //BST bst = new BST();
        //bst.root = CreateMinimalBST.createMinimalBST(new int[] {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15});
        BST bst = BST.createBST();

        {
            int data = 4;
            System.out.println("Finding successor of " + data);
            //TreeNode inOrderSuccessor = findInOrderSuccessor(bst, data);

            TreeNode inOrderSuccessor = findInOrderSuccessorOtherWay(bst, data);
            if (inOrderSuccessor != null) {
                System.out.println(inOrderSuccessor.data);
            } else {
                System.out.println("successor not found");
            }

        }
        System.out.println();
        {
            int data = 9;
            System.out.println("Finding successor of " + data);
            //TreeNode inOrderSuccessor = findInOrderSuccessor(bst, data);
            TreeNode inOrderSuccessor = findInOrderSuccessorOtherWay(bst, data);
            if (inOrderSuccessor != null) {
                System.out.println(inOrderSuccessor.data);
            } else {
                System.out.println("successor not found");
            }

        }
        System.out.println();
        {
            int data = 10;
            System.out.println("Finding successor of " + data);
            //TreeNode inOrderSuccessor = findInOrderSuccessor(bst, data);
            TreeNode inOrderSuccessor = findInOrderSuccessorOtherWay(bst, data);
            if (inOrderSuccessor != null) {
                System.out.println(inOrderSuccessor.data);
            } else {
                System.out.println("successor not found");
            }

        }
    }

    /*
    REMEMBER:
    You have to have a link to parent nodes for this algorithm.

    You need to consider following scenarios

    'node' is a node for which we need to find a next successor.

    - node has right subtree    (Simple case)
      then find left most node in its right subtree
    - node doesn't have right subtree
        - node is a left child of its parent
          then node's parent node becomes a next successor of that node.
        - node is a right child of its parent
          then go up the tree till you find a parent who is left child of its parent.
          this parent's parent's parent's..... parent becomes next successor of a node.
     */

    private static TreeNode findInOrderSuccessor(BST bst, int data) {
        TreeNode treeNode = bst.get(data);

        if (treeNode != null) {

            if (treeNode.right != null) {
                TreeNode node = treeNode.right;
                while (node.left != null) {
                    node = node.left;
                }
                return node;
            } else {
                TreeNode node1 = treeNode.parent;
                TreeNode node2 = treeNode;
                while (node2 != node1.left) {
                    node2 = node1;
                    node1 = node1.parent;
                    if (node1 == null) {
                        return null; // passed treeNode is the right most leaf node. So, its successor is not possible.
                    }
                }
                return node1;
            }
        }
        return null;
    }

    private static TreeNode findInOrderSuccessorOtherWay(BST bst, int data) {
        TreeNode treeNode = bst.get(data);

        if(treeNode != null) {
            if (treeNode.hasRight()) {
                return findMinFromRightSubTree(treeNode.right);
            } else {
                return findBiggerParent(treeNode, treeNode.parent);
            }
        }
        return null;

    }

    private static TreeNode findBiggerParent(TreeNode treeNode, TreeNode parentNode) {
        if(parentNode == null) return null;
        if(parentNode.data > treeNode.data) return parentNode;
        return findBiggerParent(treeNode, parentNode.parent);
    }

    private static TreeNode findMinFromRightSubTree(TreeNode treeNode) {
        if(!treeNode.hasLeft()) {
            return treeNode;
        }
        return findMinFromRightSubTree(treeNode.left);
    }
}
