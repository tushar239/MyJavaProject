package algorithms._3binary_tree.geeksforgeeks.BFS._3pre_order_traversal.root_to_leaf_problems_hard;

import algorithms._3binary_tree.geeksforgeeks.BinaryTree;
import algorithms.crackingcodinginterviewbook._4tree_and_graph.tree.baseclasses.TreeNode;
import algorithms.utils.TreeUtils;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

/*
    Print out all of root-to-leaf paths of a Binary Tree one per line

    https://www.youtube.com/watch?v=JPspskkZn9E

    Input Binary Tree:
        4
      2   6
     1 3 5 7

    All Root to Leaf Paths

    [4, 2, 1]
    [4, 2, 3]
    [4, 2, 6, 5]
    [4, 2, 6, 7]


    When you visit the node, put it in a list
    if that node is a leaf node, print the list and remove the last element of the list
    otherwise go to left subtree and right subtree


    This is a good example of Pre-Order traversal.

    Why Pre-Order Traversal?
    Every node you visit will be a part of Root-To-Leaf path. So, you don't need to wait till left and right subtrees are visited.
    You can just put visiting node to the list right away and then traverse left and right subtrees.
*/
public class _1_1PrintAllRootToLeafPathsOfBinaryTree {

    public static void main(String[] args) {
        List<Integer> list = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7);
        BinaryTree binaryTree = new BinaryTree(TreeUtils.createBinaryTree(list, 0, list.size() - 1));

        System.out.println("Input Binary Tree:");
        TreeUtils.printPreety(binaryTree.getRoot());

        System.out.println("All Root to Leaf Paths");
        printRootToLeafPaths(binaryTree.getRoot(), new ArrayList<>());

        /*System.out.println("All Root to Leaf Paths");
        printRootToLeafPaths_another_way(binaryTree.getRoot(), new ArrayList<>());*/
    }

    private static void printRootToLeafPaths(TreeNode root, List<Integer> list) {
        if (root == null) return;

        list.add(root.data);

        if (isLeaf(root)) {
            System.out.println(list);
            list.remove(list.size() - 1);
            return;
        }

        printRootToLeafPaths(root.left, list);
        printRootToLeafPaths(root.right, list);

    }

    private static boolean isLeaf(TreeNode root) {
        return root != null && root.left == null && root.right == null;
    }

    // wrong
/*    private static void printRootToLeafPaths_another_way(TreeNode root, List<Integer> list) {
        if (root == null) return;

        list.add(root.data);

        printRootToLeafPaths_another_way(root.left, list);

        System.out.println(list);
        list.remove(list.size()-1);

        printRootToLeafPaths_another_way(root.right, list);
    }*/
}
