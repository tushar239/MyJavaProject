package algorithms._3binary_tree.geeksforgeeks.DFS._1level_order_vertical_order_diagonal_order_traversal_related_algorithms;

import algorithms.crackingcodinginterviewbook._4tree_and_graph.tree.baseclasses.TreeNode;
import algorithms.utils.TreeUtils;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/*
    Print Binary Tree levels in sorted order

    https://www.geeksforgeeks.org/print-binary-tree-levels-sorted-order/

    geeksforgeeks site has a solution that uses 1 queue and 2 priority queues.
    I could solve this problem using just 1 queue and 1 priority queue.

    Input Binary Tree:

        7
      6   5
     2 1 4 3

    Output:

    7,
    5,6,
    1,2,3,4,

    Solution:

    while(!queue.isEmpty()) {
        put children of queue's element in priority queue.
        Priority Queue uses min heap. So, all elements will come out from it in sorted form.
    }

    while(!PriorityQueue.isEmpty()) {
        put element from pq to priority queue.
    }


    Queue = 7
    PriorityQueue = empty

    Queue = empty
    PriorityQueue = 5, 6
    Output = 7

    Queue = 5,6
    PriorityQueue = empty

    Queue = empty
    PriorityQueue = 1 2 3 4
    Output = 7,5,6

    Queue = empty
    PriorityQueue = empty
    Output = 7,5,6,1 2 3 4


*/
public class _3PrintBinaryTreeLevelOrderTraversalInSortedOrder {

    public static void main(String[] args) {

        TreeNode seven = new TreeNode(7);
        TreeNode six = new TreeNode(6);
        TreeNode five = new TreeNode(5);
        TreeNode four = new TreeNode(4);
        TreeNode three = new TreeNode(3);
        TreeNode two = new TreeNode(2);
        TreeNode one = new TreeNode(1);

        seven.left= six;
        seven.right=five;

        six.left=two;
        six.right=one;

        five.left=four;
        five.right=three;

        System.out.println("Input Binary Tree:");
        TreeUtils.printPreety(seven);;

        System.out.println("Output:");
        print(seven);
    }

    private static void print(TreeNode root) {
        Queue<TreeNode> queue = new LinkedBlockingQueue<>();
        PriorityQueue<TreeNode> pq = new PriorityQueue<>();

        if(root == null) return;

        queue.add(root);

        print(queue, pq);
    }
    private static void print(Queue<TreeNode> queue, PriorityQueue<TreeNode> pq) {
        if (queue.isEmpty() && pq.isEmpty()) return;

        while (!queue.isEmpty()) {
            TreeNode element = queue.poll();
            System.out.print(element.data+",");

            if(element.left != null) {
                pq.add(element.left);
            }
            if(element.right != null) {
                pq.add(element.right);
            }
        }

        System.out.println();

        while (!pq.isEmpty()) {
            queue.add(pq.poll());
        }

        print(queue, pq);
    }
}
