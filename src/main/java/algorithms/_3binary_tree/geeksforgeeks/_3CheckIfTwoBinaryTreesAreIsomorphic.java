package algorithms._3binary_tree.geeksforgeeks;

import algorithms.crackingcodinginterviewbook._4tree_and_graph.tree.baseclasses.TreeNode;
import algorithms.utils.TreeUtils;
import com.google.common.collect.Lists;

import java.util.List;

/*
    Check if two binary trees are Isomorphic

    https://www.youtube.com/watch?v=9Eo42meRcrY

         a           a
      b     c     b      c

        or

        a           a
     b      c    c      b        ----- this type of isomorphic trees are also called mirrors of each other (CheckMirrorOfBinaryTree.java)

     are called isomorphic trees.

     Two trees are isomorphic, if their roots are same and their subtrees are isomorphic.

*/
public class _3CheckIfTwoBinaryTreesAreIsomorphic {

    public static void main(String[] args) {
        List<Integer> list1 = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7);
        BinaryTree binaryTree1 = new BinaryTree(TreeUtils.createBinaryTree(list1, 0, list1.size() - 1));
        TreeUtils.printPreety(binaryTree1.getRoot());

        List<Integer> list2 = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7);
        BinaryTree binaryTree2 = new BinaryTree(TreeUtils.createBinaryTree(list2, 0, list2.size() - 1));
        TreeUtils.printPreety(binaryTree2.getRoot());

        List<Integer> list3 = Lists.newArrayList(5, 6, 7, 4, 1, 2, 3);
        BinaryTree binaryTree3 = new BinaryTree(TreeUtils.createBinaryTree(list3, 0, list3.size() - 1));
        TreeUtils.printPreety(binaryTree3.getRoot());

        List<Integer> list4 = Lists.newArrayList(5, 2, 7, 4, 1, 6, 3);
        BinaryTree binaryTree4 = new BinaryTree(TreeUtils.createBinaryTree(list4, 0, list4.size() - 1));
        TreeUtils.printPreety(binaryTree4.getRoot());


        System.out.println("Same Isomorphic: " + areIsomorphic(binaryTree1.getRoot(), binaryTree2.getRoot()));
        System.out.println("Cross Isomorphic: " + areIsomorphic(binaryTree1.getRoot(), binaryTree3.getRoot()));
        System.out.println("Not Isomorphic: " + areIsomorphic(binaryTree1.getRoot(), binaryTree4.getRoot()));

    }

    private static boolean areIsomorphic(TreeNode root1, TreeNode root2) {
        if (root1 == null && root2 == null) return true;

        if ((root1 == null && root2 != null) || (root1 != null && root2 == null)) return false;

        if (root1.data == root2.data) {
            if ((areIsomorphic(root1.left, root2.left) && areIsomorphic(root1.right, root2.right))
                    || (areIsomorphic(root1.left, root2.right) && areIsomorphic(root1.right, root2.left))) {
                return true;
            }
        }
        return false;
    }
}
