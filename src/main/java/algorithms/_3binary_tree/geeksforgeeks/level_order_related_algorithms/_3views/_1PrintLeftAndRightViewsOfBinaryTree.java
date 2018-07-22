package algorithms._3binary_tree.geeksforgeeks.level_order_related_algorithms._3views;

import algorithms.crackingcodinginterviewbook._4tree_and_graph.tree.baseclasses.TreeNode;

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
        int[] A = {1,2,3,4,5,6,7};

    }

    static int max_level_for_left_view =0;

    private static void leftView(TreeNode root, int level) {
        if(root == null) return;

        if(max_level_for_left_view < level) {
            System.out.println(root.data+",");
            max_level_for_left_view = level;
        }

        leftView(root.left, level+1);
        leftView(root.right, level+1);
    }

    static int max_level_for_right_view =0;

    private static void rightView(TreeNode root, int level) {
        if(root == null) return;

        if(max_level_for_left_view < level) {
            System.out.println(root.data+",");
            max_level_for_left_view = level;
        }

        rightView(root.right, level+1);
        rightView(root.left, level+1);
    }
}
