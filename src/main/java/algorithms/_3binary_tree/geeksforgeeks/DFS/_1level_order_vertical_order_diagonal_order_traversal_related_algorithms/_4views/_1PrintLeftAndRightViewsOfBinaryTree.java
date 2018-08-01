package algorithms._3binary_tree.geeksforgeeks.DFS._1level_order_vertical_order_diagonal_order_traversal_related_algorithms._4views;

import algorithms.crackingcodinginterviewbook._4tree_and_graph.tree.baseclasses.TreeNode;
import algorithms.utils.TreeUtils;

/*
    Print left and right views of a binary tree

    https://www.youtube.com/watch?v=eBdKNoW3VJg

    https://www.geeksforgeeks.org/print-left-view-binary-tree/
    https://www.geeksforgeeks.org/print-right-view-binary-tree-2/

                    a
            b               c
        d      e        f       g
      h      i            j         k
        l                              m
                                         n

    left view = a b d h l n

    right view = a c g k m n
*/
public class _1PrintLeftAndRightViewsOfBinaryTree {

    public static void main(String[] args) {
        TreeNode one = new TreeNode(1);
        TreeNode two = new TreeNode(2);
        TreeNode three = new TreeNode(3);
        TreeNode four = new TreeNode(4);
        TreeNode five = new TreeNode(5);
        TreeNode six = new TreeNode(6);
        TreeNode seven = new TreeNode(7);

        one.left = two;
        one.right = three;
        two.left = four;
        two.right = five;
        three.left = six;
        six.left = seven;

        /*
                1
            2       3
          4   5   6
                 7

         */
        TreeUtils.printPreety(one);

        System.out.println("Printing left view of binary tree");
        leftView(one, 1); // 1,2,4,7

        System.out.println();

        System.out.println("Printing right view of binary tree");
        rightView(one, 1); // 1,3,6,7
    }

    static int max_level_for_left_view = 0;

    private static void leftView(TreeNode root, int level) {
        if (root == null) return;

        if (max_level_for_left_view < level) {
            System.out.print(root.data + ",");
            max_level_for_left_view = level;
        }

        leftView(root.left, level + 1);
        leftView(root.right, level + 1);
    }

    static int max_level_for_right_view = 0;

    private static void rightView(TreeNode root, int level) {
        if (root == null) return;

        if (max_level_for_right_view < level) {
            System.out.print(root.data + ",");
            max_level_for_right_view = level;
        }

        rightView(root.right, level + 1);
        rightView(root.left, level + 1);
    }
}
