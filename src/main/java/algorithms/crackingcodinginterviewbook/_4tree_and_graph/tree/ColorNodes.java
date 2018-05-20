package algorithms.crackingcodinginterviewbook._4tree_and_graph.tree;

import algorithms.crackingcodinginterviewbook._4tree_and_graph.tree.baseclasses.BST;
import algorithms.crackingcodinginterviewbook._4tree_and_graph.tree.baseclasses.TreeNode;

/**
 * @author Tushar Chokshi @ 9/3/15.
 */
/*
If node's color is white, both of its children nodes should get red color and vice-versa.
if node's value is odd, then root should get white color.

Example of pre-order traversal.
You need to process parent node before processing child nodes because you need to know parent node's color first.
This is a perfect example of pre-order traversal.
 */
public class ColorNodes {
    private static final String WHITE = "white";
    private static final String RED = "red";

    public static void main(String[] args) {

        {
            BST bst = BST.createBST();
            colorNodes(bst.root, RED);
            bst.traverse();
        }
        System.out.println();
        {
            BST bst = BST.createBST();
            bst.root.color = WHITE;
            colorNodesDifferentWay(bst.root);
            bst.traverse();
        }

    }

    private static void colorNodes(TreeNode node, String color) {
        if (node == null) return;

        // node has to be visited first because changing its value changes its children's values. So, It is called Pre-Order traversal.
        if (node.isRoot && node.data % 2 == 1) { // odd number
            color = WHITE;
        }
        node.color = color;


        if (WHITE.equals(node.color)) {
            colorNodes(node.left, RED);
            colorNodes(node.right, RED);
        } else if (RED.equals(node.color)) {
            colorNodes(node.left, WHITE);
            colorNodes(node.right, WHITE);
        }
    }

    private static void colorNodesDifferentWay(TreeNode node) {
        if (node == null) return;

        if (node.color.equals(RED)) {
            if (node.hasLeft()) {
                node.left.color = WHITE;
            }
            if (node.hasRight()) {
                node.right.color = WHITE;
            }
        } else if (node.color.equals(WHITE)) {
            if (node.hasLeft()) {
                node.left.color = RED;
            }
            if (node.hasRight()) {
                node.right.color = RED;
            }
        }
        colorNodesDifferentWay(node.left);
        colorNodesDifferentWay(node.right);
    }
}


