package algorithms._3binary_tree.geeksforgeeks.DFS._1level_order_vertical_order_diagonal_order_traversal_related_algorithms._4views;

import algorithms.crackingcodinginterviewbook._4tree_and_graph.tree.baseclasses.TreeNode;
import algorithms.utils.TreeUtils;
import com.google.common.collect.Lists;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.TreeSet;
import java.util.concurrent.LinkedBlockingQueue;

/*
    Print Nodes in Top View of Binary Tree

        https://www.geeksforgeeks.org/print-nodes-top-view-binary-tree/
        https://www.youtube.com/watch?v=c3SAvcjWb1E

    Print Nodes in Bottom View of Binary Tree

        https://www.geeksforgeeks.org/bottom-view-binary-tree/
        https://www.youtube.com/watch?v=V7alrvgS5AI


    Create a Vertical Order Map<HD, List<TreeNode>> just by using  Vertical Order Traversal Technique mentioned in LevelOrderAndVerticalOrderTraversal.java
    Sort this map by HD.
    Top View has first element from the list for each HD.
    Bottom View has last element from the list for each HD.

*/
@SuppressWarnings("Duplicates")
public class _2PrintTopAndBottomViewsOfBinaryTree {

    public static void main(String[] args) {
        TreeNode one = new TreeNode(1);
        TreeNode two = new TreeNode(2);
        TreeNode three = new TreeNode(3);
        TreeNode four = new TreeNode(4);
        TreeNode five = new TreeNode(5);
        TreeNode six = new TreeNode(6);
        TreeNode seven = new TreeNode(7);

        one.left = two;
        one.right = three;
        two.left = four;
        two.right = five;
        three.left = six;
        six.left = seven;

        /*
                1
            2       3
          4   5   6
                 7

         */
        TreeUtils.printPreety(one);

        System.out.println("Printing top view of binary tree");
        Queue<QItem> queue = new LinkedBlockingQueue<>();
        queue.add(new QItem(one, 0));

        Map<Integer, List<TreeNode>> verticalOrderMap = new HashMap<>();
        verticalOrderTraversal(queue, verticalOrderMap); // Instead of 4,2,1,3, it prints top view in level order 1,2,3,4. This is how even geeksforgeeks code does.


        // Top View is the first element for each HD in verticalOrderMap
        // Bottom View is the last element for each HD in verticalOrderMap

        List<Integer> topViewList = new LinkedList<>();
        List<Integer> bottomViewList = new LinkedList<>();


        TreeSet<Integer> orderedHds = new TreeSet<>(verticalOrderMap.keySet());
        for (Integer hd : orderedHds) {
            topViewList.add(verticalOrderMap.get(hd).get(0).data);
            bottomViewList.add(verticalOrderMap.get(hd).get(verticalOrderMap.get(hd).size()-1).data);
        }
        System.out.println(topViewList);
        System.out.println(bottomViewList);

//        System.out.println();
//        printTopView(one);
    }

    // This code is same as Vertical Order Traversal technique mentioned in LevelOrderAndVerticalOrderTraversal
    private static void verticalOrderTraversal(Queue<QItem> queue, Map<Integer, List<TreeNode>> verticalOrderMap) {

        if (queue.isEmpty()) return;

        QItem qItem = queue.poll();

        int hd = qItem.hd;
        TreeNode node = qItem.node;

        if (verticalOrderMap.containsKey(hd)) {
            // For top view, you just need Map<Integer, TreeNode> and just add the first value because you just need the first node for an HD.
            // For bottom view, you just need Map<Integer, TreeNode> and just keep overwriting the value because you just need the last node for an HD.
            verticalOrderMap.get(hd).add(node);
        } else {
            verticalOrderMap.put(hd, Lists.newArrayList(node));
//            System.out.print(node.data + ",");
        }

        if (node.left != null) {
            queue.add(new QItem(node.left, hd - 1));
        }

        if (node.right != null) {
            queue.add(new QItem(node.right, hd + 1));
        }

        verticalOrderTraversal(queue, verticalOrderMap);
    }

    private static class QItem {
        private TreeNode node;
        private int hd;

        public QItem(TreeNode n, int h) {
            node = n;
            hd = h;
        }
    }

    /* from geeksforgeeks site
    private static void printTopView(TreeNode root)
    {
        // base case
        if (root == null) {  return;  }

        Queue<QItem> queue = new LinkedBlockingQueue<>();

        // Create a queue and add root to it
        queue.add(new QItem(root, 0)); // Horizontal distance of root is 0

        Set<Integer> set = new HashSet<>();

        // Standard BFS or level order traversal loop
        while (!queue.isEmpty())
        {
            // Remove the front item and get its details
            QItem qi = queue.remove();
            int hd = qi.hd;
            TreeNode node = qi.node;

            // If this is the first node at its horizontal distance,
            // then this node is in top view
            if (!set.contains(hd))
            {
                set.add(hd);
                System.out.print(node.data + ",");
            }

            // Enqueue left and right children of current node
            if (node.left != null)
                queue.add(new QItem(node.left, hd-1));
            if (node.right != null)
                queue.add(new QItem(node.right, hd+1));
        }
    }*/


}
