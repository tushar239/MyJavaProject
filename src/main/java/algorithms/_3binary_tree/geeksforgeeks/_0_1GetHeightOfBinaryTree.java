package algorithms._3binary_tree.geeksforgeeks;

import algorithms.crackingcodinginterviewbook._4tree_and_graph.tree.baseclasses.TreeNode;
import com.google.common.collect.Lists;

import java.util.List;

import static algorithms.utils.TreeUtils.createBinaryTree;

/*
    Find height of a tree
*/
public class _0_1GetHeightOfBinaryTree {
    public static void main(String[] args) {
        List<Integer> list = Lists.newArrayList(3, 6, 4, 7, 1, 5, 2);
        TreeNode root = createBinaryTree(list, 0, list.size() - 1);

        int height = getHeight(root);

        System.out.println(height);
    }

    private static int getHeight(TreeNode root) {
        if(root == null) return 0;

        int heightOfLeftSubTree = getHeight(root.left);
        int heightOfRightSubTree = getHeight(root.right);

        return Math.max(heightOfLeftSubTree, heightOfRightSubTree) + 1;
    }
}
