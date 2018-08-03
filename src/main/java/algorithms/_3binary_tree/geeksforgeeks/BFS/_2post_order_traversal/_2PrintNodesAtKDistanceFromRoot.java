package algorithms._3binary_tree.geeksforgeeks.BFS._2post_order_traversal;

import algorithms._3binary_tree.geeksforgeeks.BinaryTree;
import algorithms.crackingcodinginterviewbook._4tree_and_graph.tree.baseclasses.TreeNode;
import algorithms.utils.TreeUtils;
import com.google.common.collect.Lists;

import java.util.List;

import static algorithms.utils.TreeUtils.createBinaryTree;

/*
    Print nodes at distance k from root

    https://www.youtube.com/watch?v=gciHMsAE5gw


    Solution:
    When it is talking about distance in a tree, it is talking about level.

        7           --- level 1
      6   5         --- level 2
     3 4 1 2        --- level 3

    nodes at distance(k)=2 from the root are 3,4,1,2

    Important:
      Even though, this algorithm passes a level during recursion, don't consider it as a Level-Order Traversal because it is not doing BFS.
      It is doing DFS using Pre-Order traversal.

*/
public class _2PrintNodesAtKDistanceFromRoot {

    public static void main(String[] args) {
        List<Integer> list = Lists.newArrayList(3, 6, 4, 7, 1, 5, 2);
        BinaryTree tree = new BinaryTree(createBinaryTree(list, 0, list.size() - 1));
        int k = 1;

        System.out.println("Created Tree");
        TreeUtils.printPreety(tree.getRoot());

        System.out.println();

        // ----------------------- Level Order Traversal ------------------

        System.out.println("Result:");
        printNodesAtKDistanceFromRoot(tree.getRoot(), 1, k);// 6,5

        System.out.println();

    }

    private static void printNodesAtKDistanceFromRoot(TreeNode root, int level, int k) {
        if(root == null) return;

        if(Math.abs(k) != k) {// if k is a negative value
            return;
        }

        if((level - k) == 1) {
            System.out.println(root.data);
            return;// not mandatory, but it is important because once the required nodes are found, you don't need to traverse remaining levels.
        }

        printNodesAtKDistanceFromRoot(root.left, level+1, k);
        printNodesAtKDistanceFromRoot(root.right, level+1, k);
    }
}
