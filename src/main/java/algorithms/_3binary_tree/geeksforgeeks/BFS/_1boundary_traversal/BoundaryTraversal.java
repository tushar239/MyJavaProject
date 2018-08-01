package algorithms._3binary_tree.geeksforgeeks.BFS._1boundary_traversal;

import algorithms.crackingcodinginterviewbook._4tree_and_graph.tree.baseclasses.TreeNode;
import algorithms.utils.TreeUtils;

/*
    Boundary Traversal of Binary Tree

    Watch 'Boundary Traversal of Binary Tree.mp4'

    Boundary Traversal = Left Boundary Traversal +
                         Right Boundary Traversal +
                         Leaf Nodes Traversal

    When you traverse a boundary of a binary tree, there will be some duplications of the nodes.
    e.g. root node will be same during both left and right boundaries traversals.
         there will be duplications of leaf nodes between all three types of traversals.

    Input Binary Tree:

                1
        2               3
      4     5       6       7
       8   9 10   11 12   13


    left boundary: 1 2 4 8
    right boundary: 1 3 7 13
    leaf boundary: 8 9 10 11 12 13

*/
public class BoundaryTraversal {

    public static void main(String[] args) {
        TreeNode one = new TreeNode(1);
        TreeNode two = new TreeNode(2);
        TreeNode three = new TreeNode(3);
        TreeNode four = new TreeNode(4);
        TreeNode five = new TreeNode(5);
        TreeNode six = new TreeNode(6);
        TreeNode seven = new TreeNode(7);
        TreeNode eight = new TreeNode(8);
        TreeNode nine = new TreeNode(9);
        TreeNode ten = new TreeNode(10);
        TreeNode eleven = new TreeNode(11);
        TreeNode twelve = new TreeNode(12);
        TreeNode thirteen = new TreeNode(13);

        one.left = two;
        one.right = three;

        two.left = four;
        two.right = five;

        three.left = six;
        three.right = seven;

        four.right = eight;

        five.left = nine;
        five.right = ten;

        six.left = eleven;
        six.right = twelve;

        seven.right = thirteen;

        System.out.print("Input Binary Tree:");
        TreeUtils.printPreety(one);

        System.out.println();
        System.out.print("Left Boundary:");
        leftBoundaryTraversal(one);// 1,2,4,8

        System.out.println();
        System.out.print("Right Boundary:");
        rightBoundaryTraversal(one);// 1,3,7,13


        System.out.println();
        System.out.print("Leaf Boundary:");
        leafNodesTraversal(one); // 8,9,10,11,12,13

    }

    private static void leftBoundaryTraversal(TreeNode root) {

        if (root == null) return;

        System.out.print(root.data + ",");

        if (root.left != null) {
            leftBoundaryTraversal(root.left);
        } else if (root.right != null) {
            leftBoundaryTraversal(root.right);
        }
    }

    private static void rightBoundaryTraversal(TreeNode root) {

        if (root == null) return;

        System.out.print(root.data + ",");

        if (root.right != null) {
            rightBoundaryTraversal(root.right);
        } else if (root.left != null) {
            rightBoundaryTraversal(root.left);
        }
    }

    private static void leafNodesTraversal(TreeNode root) {

        if (root == null) return;

        if (root.left == null && root.right == null) {
            System.out.print(root.data + ",");
        }
        leafNodesTraversal(root.left);
        leafNodesTraversal(root.right);

    }
}
