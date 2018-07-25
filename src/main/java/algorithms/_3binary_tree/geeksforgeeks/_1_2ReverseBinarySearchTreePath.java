package algorithms._3binary_tree.geeksforgeeks;

import algorithms.crackingcodinginterviewbook._4tree_and_graph.tree.baseclasses.TreeNode;
import algorithms.utils.TreeUtils;
import com.google.common.collect.Lists;

import java.util.LinkedList;
import java.util.List;

import static algorithms.utils.TreeUtils.createBinaryTree;

/*
    Reverse a path in BST using queue

    https://www.geeksforgeeks.org/reverse-path-bst-using-queue/

    e.g. Input BST:

                        11
              7                     12
        3          8           5           9

        Find a node 3 in a tree and when it is found, reverse its path from root to that node.

    Output Binary Tee:

                        3
              7                     12
        11          8           5           9


    Solution:
    Solution of this algorithm is same as ReverseBinaryTreePath.java with a small difference.
    Searching an element in Binary Tree is a bit different than Binary Search Tree(BST).

    In below algorithm, I have used a list to keep visited elements.
    You can use a queue also because unlike to ReverseBinaryTreePath.java, this algorithm doesn't require you to remove a tail element from 'visited'.
*/
public class _1_2ReverseBinarySearchTreePath {

    public static void main(String[] args) {
        List<Integer> list = Lists.newArrayList(3, 7, 8, 11, 5, 12, 9);
        BinaryTree tree = new BinaryTree(createBinaryTree(list, 0, list.size() - 1));

        reverseTreePath(tree);
    }

    private static void reverseTreePath(BinaryTree tree) {

        System.out.println("Input Tree");
        TreeUtils.printPreety(tree.root);

        reverseTreePath(tree.root, new LinkedList<>(), 3);

        System.out.println("Output Tree With Reversed Path");
        TreeUtils.printPreety(tree.root);
    }

    private static boolean reverseTreePath(TreeNode root, List<Integer> visited, Integer element) {
        if (root == null) {
            return false;
        }

        visited.add(root.data);

        // Typical algorithm to find an element in BST
        if (root.data == element) {

            root.data = visited.remove(0);

            return true;

        } else if (element < root.data) {

            boolean foundInLeftSubTree = reverseTreePath(root.left, visited, element);

            if (foundInLeftSubTree) {
                root.data = visited.remove(0);
                return true;
            }

            return false;
        }

        boolean foundInRightSubTree = reverseTreePath(root.left, visited, element);

        if (foundInRightSubTree) {
            root.data = visited.remove(0);
            return true;
        }

        //visited.remove(visited.size() - 1); // not needed in case of BST. It is needed for Binary Tree (ReverseBinaryTreePath.java)
        return false;

    }


    private static class BinaryTree {
        private TreeNode root;

        public BinaryTree(TreeNode root) {
            this.root = root;
        }
    }
}
