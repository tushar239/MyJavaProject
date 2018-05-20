package algorithms.utils;

import algorithms.crackingcodinginterviewbook._4tree_and_graph.tree.baseclasses.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class TreeUtils {

    /*
            To create following tree,

                7
              6   5
             4 3 1 2

            you need pass list=(4, 6, 3, 7, 1, 5, 2)


            To create following tree,

                7
              6   5
             4   1

            you need pass list=(4, 6, null, 7, 1, 5, null)


            This algorithm works similar to CreateMinimalBST.java
     */
    public static TreeNode createBinaryTree(List<Integer> list, int start, int end) {

        if (start > end) return null;

        int mid = (start + end) / 2;

        if(list.get(mid) != null) {
            TreeNode root = new TreeNode(list.get(mid));

            root.left = createBinaryTree(list, start, mid - 1);
            root.right = createBinaryTree(list, mid + 1, end);
            return root;
        }

        return null;
    }

    public static void printPreety(TreeNode root) {
        List<TreeNode> list = new ArrayList<>();
        list.add(root);
        printTree(list, getHeight(root));
    }

    public static int getHeight(TreeNode root) {

        if (root == null) {
            return 0;
        } else {
            return 1 + Math.max(getHeight(root.left), getHeight(root.right));
        }
    }

    /**
     * pass head node in nilList and height of the tree
     *
     * @param levelNodes
     * @param level
     */
    public static void printTree(List<TreeNode> levelNodes, int level) {

        List<TreeNode> nodes = new ArrayList<TreeNode>();

        //indentation for first node in given level
        printIndentForLevel(level);

        for (TreeNode treeNode : levelNodes) {

            //print node data
            System.out.print(treeNode == null ? " " : treeNode.data);

            //spacing between nodes
            printSpacingBetweenNodes(level);

            //if its not a leaf node
            if (level > 1) {
                nodes.add(treeNode == null ? null : treeNode.left);
                nodes.add(treeNode == null ? null : treeNode.right);
            }
        }
        System.out.println();

        if (level > 1) {
            printTree(nodes, level - 1);
        }
    }

    private static void printIndentForLevel(int level) {
        for (int i = (int) (Math.pow(2, level - 1)); i > 0; i--) {
            System.out.print(" ");
        }
    }

    private static void printSpacingBetweenNodes(int level) {
        //spacing between nodes
        for (int i = (int) ((Math.pow(2, level - 1)) * 2) - 1; i > 0; i--) {
            System.out.print(" ");
        }
    }
}
