package algorithms._3binary_tree.geeksforgeeks.level_order_vertical_order_related_algorithms;

import algorithms._3binary_tree.geeksforgeeks.BinaryTree;
import algorithms.crackingcodinginterviewbook._4tree_and_graph.tree.baseclasses.TreeNode;
import algorithms.utils.TreeUtils;
import com.google.common.collect.Lists;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import static algorithms.utils.TreeUtils.createBinaryTree;

/*
    Level Order Binary Tree Traversal
    https://www.youtube.com/watch?v=NjdOhYKjFrU
    Watch "Level Order Traversal.mp4"

    Vertical Order Binary Tree Traversal
    https://www.youtube.com/watch?v=PQKkr036wRc
    Watch "Vertical Order Traversal.mp4"
*/
@SuppressWarnings("Duplicates")
public class _0LevelOrderVerticalOrderTraversal {

    public static void main(String[] args) {
        List<Integer> list = Lists.newArrayList(3, 6, 4, 7, 1, 5, 2);
        BinaryTree tree = new BinaryTree(createBinaryTree(list, 0, list.size() - 1));

        System.out.println("Created Tree");
        TreeUtils.printPreety(tree.getRoot());

        System.out.println();

        System.out.println("Level Order Traversal using Queue");
        printNodesInLevelOrder(tree.getRoot());

        System.out.println();

        System.out.println("Level Order Traversal using Level+Map");
        printNodesInLevelOrder_2(tree.getRoot());

        System.out.println();

        System.out.println("Vertical Order Traversal using Horizontal_Distance+Map");
        printNodesInVerticalOrder(tree.getRoot());

    }

    private static void printNodesInLevelOrder(TreeNode root) {

        if (root == null) return;

        Queue<TreeNode> queue = new LinkedBlockingQueue<>();
        queue.add(root);

        levelOrderTraversal(queue);

        System.out.println();

    }

    private static void levelOrderTraversal(Queue<TreeNode> queue) {
        if (queue.isEmpty()) return;

        TreeNode node = queue.poll();

        System.out.print(node.data + ",");

        TreeNode left = node.left;
        if (left != null) {
            queue.add(left);
        }

        TreeNode right = node.right;
        if (right != null) {
            queue.add(right);
        }

        levelOrderTraversal(queue);
    }

    private static void printNodesInLevelOrder_2(TreeNode root) {

        if (root == null) return;

        HashMap<Integer, List<TreeNode>> levelOrderMap = new HashMap<>();
        levelOrderTraversal_2(root, 0, levelOrderMap);

        for (Integer level : levelOrderMap.keySet()) {
            System.out.print(level + " : ");

            List<TreeNode> nodes = levelOrderMap.get(level);
            for (TreeNode node : nodes) {
                System.out.print(node.data + ",");
            }
            System.out.println();
        }

    }

    private static void levelOrderTraversal_2(TreeNode root, int level, Map<Integer, List<TreeNode>> levelOrderMap) {
        if (root == null) return;

        if (levelOrderMap.containsKey(level)) {
            levelOrderMap.get(level).add(root);
        } else {
            List<TreeNode> list = new LinkedList<>();
            list.add(root);
            levelOrderMap.put(level, list);
        }

        levelOrderTraversal_2(root.left, level + 1, levelOrderMap);
        levelOrderTraversal_2(root.right, level + 1, levelOrderMap);
    }


    private static void printNodesInVerticalOrder(TreeNode root) {
        if (root == null) return;

        HashMap<Integer, List<TreeNode>> verticalOrderMap = new HashMap<>();
        verticalOrderTraversal(root, 0, verticalOrderMap);

        for (Integer hd : verticalOrderMap.keySet()) {
            System.out.print(hd + " : ");

            List<TreeNode> nodes = verticalOrderMap.get(hd);
            for (TreeNode node : nodes) {
                System.out.print(node.data + ",");
            }
            System.out.println();
        }
    }

    private static void verticalOrderTraversal(TreeNode root, int hd, Map<Integer, List<TreeNode>> verticalOrderMap) {// hd stands for Horizontal Distance
        if (root == null) return;

        if (verticalOrderMap.containsKey(hd)) {
            verticalOrderMap.get(hd).add(root);
        } else {
            List<TreeNode> list = new LinkedList<>();
            list.add(root);
            verticalOrderMap.put(hd, list);
        }

        verticalOrderTraversal(root.left, hd - 1, verticalOrderMap);
        verticalOrderTraversal(root.right, hd + 1, verticalOrderMap);
    }

}
