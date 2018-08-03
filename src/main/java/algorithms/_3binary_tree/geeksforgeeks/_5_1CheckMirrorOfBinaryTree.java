package algorithms._3binary_tree.geeksforgeeks;

import algorithms.crackingcodinginterviewbook._4tree_and_graph.tree.baseclasses.TreeNode;
import algorithms.utils.TreeUtils;
import com.google.common.collect.Lists;

import java.util.List;

/*
    Check Mirror of Binary Tree

    https://www.youtube.com/watch?v=9jH2L2Ysxko

        a               a
      b    c         c      b

     these two trees are mirrors of each other

     Mirror trees are one type of isomorphic trees (CheckIfTwoBinaryTreesAreIsomorphic.java)
*/
public class _5_1CheckMirrorOfBinaryTree {

    public static void main(String[] args) {
        /*
                5
              4   7
             2 6 8 10
        */
        List<Integer> list1 = Lists.newArrayList(2, 4, 6, 5, 8, 7, 10);
        BinaryTree binaryTree1 = new BinaryTree(TreeUtils.createBinaryTree(list1, 0, list1.size() - 1));

        System.out.println("Input Binary Tree1:");
        TreeUtils.printPreety(binaryTree1.getRoot());

        /*
                5
              7    4
             10 8 6 2
        */
        List<Integer> list2 = Lists.newArrayList(10, 7, 8, 5, 6, 4, 2);
        BinaryTree binaryTree2 = new BinaryTree(TreeUtils.createBinaryTree(list2, 0, list2.size() - 1));

        System.out.println("Input Binary Tree2:");
        TreeUtils.printPreety(binaryTree2.getRoot());

        {
            boolean isMirror = isMirror(binaryTree1.getRoot(), binaryTree2.getRoot());
            System.out.println("Result: " + isMirror);// true
        }

        List<Integer> list3 = Lists.newArrayList(8, 7, 10, 5, 6, 4, 2);
        BinaryTree binaryTree3 = new BinaryTree(TreeUtils.createBinaryTree(list3, 0, list3.size() - 1));

        System.out.println("Input Binary Tree3:");
        TreeUtils.printPreety(binaryTree3.getRoot());

        {
            boolean isMirror = isMirror(binaryTree1.getRoot(), binaryTree3.getRoot());
            System.out.println("Result: " + isMirror);// false
        }

    }

    private static boolean isMirror(TreeNode root1, TreeNode root2) {
        if (root1 == null && root2 == null) return true;

        if ((root1 != null && root2 == null) || (root1 == null && root2 != null)) return false;

        if (root1.data != root2.data) return false;

        return isMirror(root1.left, root2.right) && isMirror(root1.right, root2.left);
    }
}
