package algorithms._3binary_tree.geeksforgeeks;

import algorithms.crackingcodinginterviewbook._4tree_and_graph.tree.baseclasses.TreeNode;
import algorithms.utils.TreeUtils;
import com.google.common.collect.Lists;

import java.util.List;

/*
    Convert Binary Tree to its mirror image

    https://www.youtube.com/watch?v=vdwcCIkLUQI

        a
      b    c
    d   e f  g

  mirror is

        a
     c      b
   g   f  e   d
*/
public class _5_2CreateMirrorOfBinaryTree {

    public static void main(String[] args) {
        List<Integer> list1 = Lists.newArrayList(2, 4, 6, 5, 8, 7, 10);
        BinaryTree binaryTree1 = new BinaryTree(TreeUtils.createBinaryTree(list1, 0, list1.size() - 1));

        System.out.println("Input Binary Tree:");
        TreeUtils.printPreety(binaryTree1.getRoot());

        {
            System.out.println("Mirror of Binary Tree:");
            TreeNode mirrorTree = createMirror(binaryTree1.getRoot());
            TreeUtils.printPreety(mirrorTree);
        }

        {
            System.out.println("Mirror of Binary Tree - In-Place:");
            TreeNode root = binaryTree1.getRoot();
            createMirror_inplace(root);
            TreeUtils.printPreety(root);
        }
    }

    // This approach is creating a new mirror binary tree
    private static TreeNode createMirror(TreeNode root) {
        if (root == null) return null;
        TreeNode mirrorTreeRoot = new TreeNode(root.data);
        createMirror(root, mirrorTreeRoot);
        return mirrorTreeRoot;
    }

    private static void createMirror(TreeNode root1, TreeNode root2) {
        if (root1 == null) return;

        if (root1.left != null) {
            TreeNode left = new TreeNode(root1.left.data);
            root2.right = left;
        }

        if (root1.right != null) {
            TreeNode right = new TreeNode(root1.right.data);
            root2.left = right;
        }

        createMirror(root1.left, root2.right);
        createMirror(root1.right, root2.left);
    }


    private static void createMirror_inplace(TreeNode root) {

        if (root == null) return;

        TreeNode leftSubTree = root.left;
        TreeNode rightSubTree = root.right;

        root.left = rightSubTree;
        root.right = leftSubTree;

        createMirror_inplace(root.left);
        createMirror_inplace(root.right);
    }

}
