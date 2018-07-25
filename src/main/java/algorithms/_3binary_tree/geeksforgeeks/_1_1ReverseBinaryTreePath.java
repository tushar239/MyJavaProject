package algorithms._3binary_tree.geeksforgeeks;

import algorithms.crackingcodinginterviewbook._4tree_and_graph.tree.baseclasses.TreeNode;
import algorithms.utils.TreeUtils;
import com.google.common.collect.Lists;

import java.util.LinkedList;
import java.util.List;

import static algorithms.utils.TreeUtils.createBinaryTree;

/*
    Reverse tree path

    https://www.geeksforgeeks.org/reverse-tree-path/


    e.g. Input Tree:

                        7
              6                     5
        4           3           2       1


    Find a node 4 in a tree and when it is found, reverse its path from root to that node.

    Output Tree:

                        4
              6                     5
        7           3           2       1


    How to think about the solution?

    Take leaf node 4 for considering reduce the problem by 1.

    Assume that when you are at node 4 during tree traversal, you have an array of previously visited nodes [7,6]


    boolean reverseTreePath(root, visited, elementToFind) {

        add 4 to visited [7,6,4]

        if(4 == element to find) {
            replace node 4 by 7
            now visited=[6,4]
            return true;
        }


        // try to find an element in left subtree of node 4
        boolean found = reverseTreePath(4.left, visited, elementToFind)

        // typical binary tree search condition
        if(!found) {

            // try to find an element in right subtree of node 4
            found = reverseTreePath(4.right, visited, elementToFind)
        }

        // if an element is found in either left or right subtree of node 4
        if(found) {
            replace 4 by 7 of visited array
            now visited=[6,4,....]
            return true;
        }

        // IMPORTANT when you search an element in Binary Tree.
        // if element not found in both left and right subtrees of node 4, then
        remove 4 from visited array [7,6]

        return false;
    }
*/
public class _1_1ReverseBinaryTreePath {

    public static void main(String[] args) {
        List<Integer> list = Lists.newArrayList(3, 6, 4, 7, 1, 5, 2);
        BinaryTree tree = new BinaryTree(createBinaryTree(list, 0, list.size() - 1));

        reverseTreePath(tree);
    }

    private static void reverseTreePath(BinaryTree tree) {

        System.out.println("Input Tree");
        TreeUtils.printPreety(tree.root);

        reverseTreePath(tree.root, new LinkedList<>(), 4);

        System.out.println("Output Tree With Reversed Path");
        TreeUtils.printPreety(tree.root);
    }

    private static boolean reverseTreePath(TreeNode root, List<Integer> visited, Integer element) {
        if (root == null) {
            return false;
        }

        visited.add(root.data);

        if (root.data == element) {
            root.data = visited.remove(0);
            return true;
        }

        // Typical code to find an element in Binary Tree. To find an element in BST, you just need to find an element in either left subtree or right subtree because BST is symmetric.
        boolean foundInLeftSubTree = reverseTreePath(root.left, visited, element);
        boolean foundInRightSubTree = false;
        if (!foundInLeftSubTree) {
            foundInRightSubTree = reverseTreePath(root.left, visited, element);
        }

        if (foundInLeftSubTree || foundInRightSubTree) {
            root.data = visited.remove(0);
            return true;
        }

        visited.remove(visited.size() - 1); // because of this you can't use queue instead of list for 'visited'.
        return false;
    }


    private static class BinaryTree {
        private TreeNode root;

        public BinaryTree(TreeNode root) {
            this.root = root;
        }
    }
}
