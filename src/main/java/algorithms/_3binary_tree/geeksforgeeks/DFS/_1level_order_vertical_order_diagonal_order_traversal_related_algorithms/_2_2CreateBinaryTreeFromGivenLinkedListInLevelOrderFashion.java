package algorithms._3binary_tree.geeksforgeeks.DFS._1level_order_vertical_order_diagonal_order_traversal_related_algorithms;

import algorithms.crackingcodinginterviewbook._2linkedlistmanipulation.Node;
import algorithms.crackingcodinginterviewbook._4tree_and_graph.tree.baseclasses.TreeNode;
import algorithms.utils.TreeUtils;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/*
        Construct Complete Binary Tree from its Linked List Representation

        https://www.geeksforgeeks.org/given-linked-list-representation-of-complete-tree-convert-it-to-linked-representation/
        https://www.youtube.com/watch?v=dGJtKxBpP00

        This is a tricky algorithm.

        create a tree root from the head of the list and push the root to a queue.

        while(!queue.isEmpty() {

            TreeNode root = queue.poll();

            next two list nodes should be left and right children of the root.

            push left and right children of root to a queue.
        }

*/
public class _2_2CreateBinaryTreeFromGivenLinkedListInLevelOrderFashion {

    public static void main(String[] args) {
        Node one = new Node(1);
        Node two = new Node(2);
        Node three = new Node(3);
        Node four = new Node(4);
        Node five = new Node(5);
        Node six = new Node(6);

        one.next = two;
        two.next = three;
        three.next = four;
        four.next = five;
        five.next = six;

        TreeNode root = createBinaryTree(one);
        TreeUtils.printPreety(root);
    }

    private static TreeNode createBinaryTree(Node head) {
        Node runner = head;

        if (runner != null) {
            TreeNode root = new TreeNode(runner.data);
            Queue<TreeNode> queue = new LinkedBlockingQueue();
            queue.add(root);
            createBinaryTree(runner, queue);
            return root;
        }
        return null;
    }

    private static void createBinaryTree(Node runner, Queue<TreeNode> queue) {

        while (!queue.isEmpty()) {
            TreeNode treeNode = queue.poll();

            if (runner.next != null) {
                treeNode.left = new TreeNode(runner.next.data);
                queue.add(treeNode.left);
                runner = runner.next;

                if (runner.next != null) {
                    treeNode.right = new TreeNode(runner.next.data);
                    queue.add(treeNode.right);
                    runner = runner.next;

                }
            }
        }
    }
}
