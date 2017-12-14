package algorithms.treeandgraph.tree;

import algorithms.treeandgraph.tree.baseclasses.BST;
import algorithms.treeandgraph.tree.baseclasses.TreeNode;

/**
 * @author Tushar Chokshi @ 9/3/15.
 */
// Cracking Coding Interview Book p.g. 248
// http://www.geeksforgeeks.org/inorder-successor-in-binary-search-tree/
/*
In this method, we assume that every node has parent pointer.

The Algorithm is divided into two cases on the basis of right subtree of the input node being empty or not.

Input: node, root // node is the node whose Inorder successor is needed.
output: succ // succ is Inorder successor of node.

1) If right subtree of node is not NULL, then succ lies in right subtree. Do following.
Go to right subtree and return the node with minimum key value in right subtree.
2) If right sbtree of node is NULL, then succ is one of the ancestors. Do following.
Travel up using the parent pointer until you see a node which is left child of it’s parent. The parent of such a node is the succ.
*/
public class FindInOrderSuccessor {

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
