package algorithms._3binary_tree.geeksforgeeks.DFS._1level_order_vertical_order_diagonal_order_traversal_related_algorithms;

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
    ------------------------------------

        https://www.youtube.com/watch?v=NjdOhYKjFrU
        Watch "Level Order Traversal.mp4"

        There are two ways of Level Order Traversal
        - Using Queue
        - Using Level + Map<level, List<TreeNode>>
          Important:
          Even though, this approach satisfies the need of collecting the nodes of the tree as per their levels,
          it is not doing Level-Order traversal (BFS). It is still doing Post-Order traversal (DFS).
          Remember it.


        In Level-Order Traversal, nodes are considered at same level when they are Horizontally on the same level.

                     1          ----- level 1
                2        3      ----- level 2
             4     5  6     7   ----- level 3


    Vertical Order Binary Tree Traversal
    ------------------------------------

        https://www.youtube.com/watch?v=PQKkr036wRc
        Watch "Vertical Order Traversal.mp4"

        Vertical Order Traversal is an extension of Level Order Traversal.
        It gives the nodes in the same Vertical Order(It's like Vertical Level Order).

        There are two ways of Vertical Order Traversal
        - Using Queue for Queuing QItem(hd, node), Map<Horizontal Distance, List<TreeNode>>
        - Using Horizontal Distance + Map<level, List<TreeNode>>

        IMPORTANT:
          Even though, this method is correct for vertical order, it won't work for printing top view of binary tree in level-order format(PrintTopAndBottomViewsOfBinaryTree.java).
          So, always use first technique


        In Vertical-Order Traversal, nodes are considered at same level when they are Vertically on the same level.

        |
        |                       1 (hd=0)
        |              2 (hd=-1)              3 (hd=1)
        |       4 (hd=-2)        5 (hd=0)  6 (hd=0)     7 (hd=2)
        |
        ---------------------------------------------------------
        hd=     -2      -1        0           1           2


        Nodes at same hd(horizontal distance) are considered to be on the same Vertical-Order.



    Diagonal Order Binary Tree Traversal
    ------------------------------------

    https://www.youtube.com/watch?v=e9ZGxH1y_PE



                             1 (dd=0)
                    2 (dd=-1)                   3 (dd=0)
             4 (dd=-2)        5 (dd=-1)  6 (dd=-1)           7 (dd=0)
      

        1,3,7 are on the same Diagonal (Diagonal Distance is same)
        2,5,6 are on another Diagonal
        4 is on another Diagonal

        This is right side inclining diagonal

            During Level order traversal, enqueue a node using dd (diagonal distance).
            dd for right child node should be same as parent node
            and
            dd for left child node should be 1 less than dd of parent node.

        You can also write an algorithm in a bit different way to find the nodes using left side inclining diagonal

                             1 (dd=0)
                    2 (dd=0)                   3 (dd=1)
             4 (dd=0)        5 (dd=1)  6 (dd=1)           7 (dd=2)

        1,2,4 are on the same Diagonal (Diagonal Distance is same)
        3,1,5 are on another Diagonal
        7 is on another Diagonal

        This is right side inclining diagonal

            During Level order traversal, enqueue a node using dd (diagonal distance).
            dd for right child node should be 1 less than dd of parent node.
            and
            dd for left child node should be same as parent node.



    These algorithms are based on BFS (Breath First Search) algorithm.

    PreOrder, InOrder, PostOrder, Boundary traversals are based on DFS (Depth First Search) algorithm.


*/
@SuppressWarnings("Duplicates")
public class _0_1LevelOrderAndVerticalOrderAndDiagonalOrderTraversal {

    public static void main(String[] args) {
        List<Integer> list = Lists.newArrayList(3, 6, 4, 7, 1, 5, 2);
        BinaryTree tree = new BinaryTree(createBinaryTree(list, 0, list.size() - 1));

        System.out.println("Created Tree");
        TreeUtils.printPreety(tree.getRoot());

        System.out.println();

        // ----------------------- Level Order Traversal ------------------

        System.out.println("Level Order Traversal using Queue");
        printNodesInLevelOrder(tree.getRoot());

        System.out.println();

        System.out.println("Level Order Traversal using Level + Map<Level, List<TreeNode>>");
        printNodesInLevelOrder_2(tree.getRoot());

        System.out.println();

        // ----------------------- Vertical Order Traversal ------------------
        System.out.println("Vertical Order Traversal using Queue for QItem(hd, node) + Map<HD, List<TreeNodes>>");
        printNodesInVerticalOrder(tree.getRoot());

        System.out.println();

        System.out.println("Vertical Order Traversal using Horizontal_Distance + Map<HD, List<TreeNodes>>");
        printNodesInVerticalOrder_2(tree.getRoot());

        System.out.println();

        // ----------------------- Right Side Diagonal Order Traversal ------------------
        System.out.println("Right Side Diagonal Order Traversal using Queue for QItem(dd, node) + Map<DD, List<TreeNodes>>");
        printNodesInRightSideDiagonalOrder(tree.getRoot());

        System.out.println();

        System.out.println("Left Side Diagonal Order Traversal using Queue for QItem(dd, node) + Map<DD, List<TreeNodes>>");
        printNodesInLeftSideDiagonalOrder(tree.getRoot());

        System.out.println();

    }

    /*
    1) Create empty queue and push root node to it.
    2) Do the following when queue is not empty
              Pop a node from queue and examine it. if it is a node that you are looking for then return it, otherwise
              Push left child of popped node to queue if not null
              Push right child of popped node to queue if not null

     */
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

    /*
        Important:

        Even though, this approach satisfies the need of collecting the nodes of the tree as per their levels,
        it is not doing Level-Order traversal (BFS). It is still doing Post-Order traversal (DFS).
        So, remember it.
     */
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


//---------------------------- Vertical Order Traversal ----------------------------------------------------------

    private static void printNodesInVerticalOrder(TreeNode root) {
        if (root == null) return;


        LinkedBlockingQueue<QItem> queue = new LinkedBlockingQueue<>();
        queue.add(new QItem(root, 0));// Horizontal Distance of root is 0

        Map<Integer, List<TreeNode>> verticalOrderMap = new HashMap<>();
        verticalOrderTraversal(queue, verticalOrderMap);

        for (Integer hd : verticalOrderMap.keySet()) {
            System.out.print(hd + " : ");

            List<TreeNode> nodes = verticalOrderMap.get(hd);
            for (TreeNode node : nodes) {
                System.out.print(node.data + ",");
            }
            System.out.println();
        }
    }

    private static void verticalOrderTraversal(Queue<QItem> queue, Map<Integer, List<TreeNode>> verticalOrderMap) {

        if (queue.isEmpty()) return;

        QItem qItem = queue.poll();

        int hd = qItem.distance;
        TreeNode node = qItem.node;

        if (verticalOrderMap.containsKey(hd)) {
            verticalOrderMap.get(hd).add(node);
        } else {
            verticalOrderMap.put(hd, Lists.newArrayList(node));
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
        private int distance;

        public QItem(TreeNode node, int distance) {
            this.node = node;
            this.distance = distance;
        }
    }

    /*
        Even though, this method is correct for vertical order, it won't work for printing top view of binary tree in level-order format(PrintTopAndBottomViewsOfBinaryTree.java).
        So, always use Queuing QItem(hd+node) technique.
    */
    private static void printNodesInVerticalOrder_2(TreeNode root) {
        if (root == null) return;

        HashMap<Integer, List<TreeNode>> verticalOrderMap = new HashMap<>();
        verticalOrderTraversal_2(root, 0, verticalOrderMap);

        for (Integer hd : verticalOrderMap.keySet()) {
            System.out.print(hd + " : ");

            List<TreeNode> nodes = verticalOrderMap.get(hd);
            for (TreeNode node : nodes) {
                System.out.print(node.data + ",");
            }
            System.out.println();
        }
    }

    private static void verticalOrderTraversal_2(TreeNode root, int hd, Map<Integer, List<TreeNode>> verticalOrderMap) {// hd stands for Horizontal Distance
        if (root == null) return;

        if (verticalOrderMap.containsKey(hd)) {
            verticalOrderMap.get(hd).add(root);
        } else {
            List<TreeNode> list = new LinkedList<>();
            list.add(root);
            verticalOrderMap.put(hd, list);
        }

        verticalOrderTraversal_2(root.left, hd - 1, verticalOrderMap);
        verticalOrderTraversal_2(root.right, hd + 1, verticalOrderMap);
    }

//---------------------------- Diagonal Order Traversal ----------------------------------------------------------

    private static void printNodesInRightSideDiagonalOrder(TreeNode root) {
        if (root == null) return;


        LinkedBlockingQueue<QItem> queue = new LinkedBlockingQueue<>();
        queue.add(new QItem(root, 0));// Horizontal Distance of root is 0

        Map<Integer, List<TreeNode>> diagonalOrderMap = new HashMap<>();
        rightSideDiagonalOrderTraversal(queue, diagonalOrderMap);

        for (Integer dd : diagonalOrderMap.keySet()) {
            System.out.print(dd + " : ");

            List<TreeNode> nodes = diagonalOrderMap.get(dd);
            for (TreeNode node : nodes) {
                System.out.print(node.data + ",");
            }
            System.out.println();
        }
    }
    private static void rightSideDiagonalOrderTraversal(Queue<QItem> queue, Map<Integer, List<TreeNode>> diagonalOrderMap) {

        if (queue.isEmpty()) return;

        QItem qItem = queue.poll();

        int dd = qItem.distance; // diagonal distance
        TreeNode node = qItem.node;

        if (diagonalOrderMap.containsKey(dd)) {
            diagonalOrderMap.get(dd).add(node);
        } else {
            diagonalOrderMap.put(dd, Lists.newArrayList(node));
        }

        if (node.left != null) {
            queue.add(new QItem(node.left, dd - 1));
        }

        if (node.right != null) {
            queue.add(new QItem(node.right, dd));
        }

        rightSideDiagonalOrderTraversal(queue, diagonalOrderMap);
    }


    private static void printNodesInLeftSideDiagonalOrder(TreeNode root) {
        if (root == null) return;


        LinkedBlockingQueue<QItem> queue = new LinkedBlockingQueue<>();
        queue.add(new QItem(root, 0));// Horizontal Distance of root is 0

        Map<Integer, List<TreeNode>> diagonalOrderMap = new HashMap<>();
        leftSideDiagonalOrderTraversal(queue, diagonalOrderMap);

        for (Integer dd : diagonalOrderMap.keySet()) {
            System.out.print(dd + " : ");

            List<TreeNode> nodes = diagonalOrderMap.get(dd);
            for (TreeNode node : nodes) {
                System.out.print(node.data + ",");
            }
            System.out.println();
        }
    }
    private static void leftSideDiagonalOrderTraversal(Queue<QItem> queue, Map<Integer, List<TreeNode>> diagonalOrderMap) {

        if (queue.isEmpty()) return;

        QItem qItem = queue.poll();

        int dd = qItem.distance; // diagonal distance
        TreeNode node = qItem.node;

        if (diagonalOrderMap.containsKey(dd)) {
            diagonalOrderMap.get(dd).add(node);
        } else {
            diagonalOrderMap.put(dd, Lists.newArrayList(node));
        }

        if (node.left != null) {
            queue.add(new QItem(node.left, dd));
        }

        if (node.right != null) {
            queue.add(new QItem(node.right, dd+1));
        }

        leftSideDiagonalOrderTraversal(queue, diagonalOrderMap);
    }

}
