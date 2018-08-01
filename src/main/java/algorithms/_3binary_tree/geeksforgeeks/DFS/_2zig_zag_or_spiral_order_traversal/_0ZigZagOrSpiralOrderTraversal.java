package algorithms._3binary_tree.geeksforgeeks.DFS._2zig_zag_or_spiral_order_traversal;

import algorithms.crackingcodinginterviewbook._4tree_and_graph.tree.baseclasses.TreeNode;
import algorithms.utils.TreeUtils;

import java.util.Stack;

/*
    Traverse a binary tree in zig-zag (spiral) order

    Watch 'ZigZag(Spiral) Order Binary Tree Traversal.mp4'
    https://www.youtube.com/watch?v=YsLko6sSKh8

    e.g.

                1
       2                 3
 4          5       6           7


    Expected O/P:  1 3 2 4 5 6 7

    Solution:

        Use two stacks S1 and S2

        Step 1:

            Push '1' in S1

             S1                 S2
             1

        Step 2:

            Pop all the elements one by one from S1 and print them and Push their child from LEFT to RIGHT in S2

             O/P: a

             S1                 S2
                                2
                                3

        Step 3:

            Pop all the elements one by one from S2 and print them and Push their child from RIGHT to LEFT in S1

            O/P: 1 3 2

             S1                 S2
             7
             6
             5
             4

        Step 4:

            Pop all the elements one by one from S1 and print them and Push their child from LEFT to RIGHT in S2

            O/P: 1 3 2 4 5 6 7

            S1                  S2



*/
public class _0ZigZagOrSpiralOrderTraversal {

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

        two.left=four;
        two.right=five;

        three.left = six;
        three.right = seven;

        System.out.println("Input Binary Tree");

        TreeUtils.printPreety(one);

        System.out.println();
        System.out.print("O/P: ");

        spiralOrderTraversal(one);
    }

    private static void spiralOrderTraversal(TreeNode root) {
        Stack<TreeNode> s1 = new Stack<>();
        Stack<TreeNode> s2 = new Stack<>();

        if (root != null) {
            s1.push(root);
            spiralOrderTraversal(s1, s2);
        }
    }

    private static void spiralOrderTraversal(Stack<TreeNode> s1, Stack<TreeNode> s2) {
        if(s1.isEmpty() && s2.isEmpty()) return;

        while(!s1.isEmpty()) {
            TreeNode poppedNode = s1.pop();

            System.out.print(poppedNode.data+",");

            TreeNode left = poppedNode.left;
            if(left != null) {
                s2.push(left);
            }

            TreeNode right = poppedNode.right;
            if(right != null) {
                s2.push(right);
            }
        }

        while(!s2.isEmpty()) {

            TreeNode poppedNode = s2.pop();

            System.out.print(poppedNode.data+",");

            TreeNode right = poppedNode.right;
            if(right != null) {
                s1.push(right);
            }

            TreeNode left = poppedNode.left;
            if(left != null) {
                s1.push(left);
            }
        }

        spiralOrderTraversal(s1, s2);
    }
}
