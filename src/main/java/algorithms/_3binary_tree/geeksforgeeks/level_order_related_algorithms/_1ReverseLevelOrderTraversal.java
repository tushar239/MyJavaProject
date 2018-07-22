package algorithms._3binary_tree.geeksforgeeks.level_order_related_algorithms;

import algorithms._3binary_tree.geeksforgeeks.BinaryTree;
import algorithms.crackingcodinginterviewbook._4tree_and_graph.tree.baseclasses.TreeNode;
import algorithms.utils.TreeUtils;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Stack;

import static algorithms.utils.TreeUtils.createBinaryTree;

/*
    Reverse level order traversal binary tree

    https://www.youtube.com/watch?v=D2bIbWGgvzI

    e.g.

        7
      6   5
     3 4 1 2

    O/P:
    3,4,1,2,6,5,7


    Remember that for Level Order Traversal, you need a Queue.
                      Level Order Traversal in reverse, you need a Stack.

                      By seeing expected the output, you can can say that right child needs to be pushed first in the stack and then left child.
                      And unlike to normal level order traversal, you are not polling the item from the stack. You just keep filling up the stack.
                      When you finish the traversal of a tree, you just poll all items from stack. This items will be polled in reverse level order.

*/
public class _1ReverseLevelOrderTraversal {

    public static void main(String[] args) {
        List<Integer> list = Lists.newArrayList(3, 6, 4, 7, 1, 5, 2);
        BinaryTree tree = new BinaryTree(createBinaryTree(list, 0, list.size() - 1));
        TreeUtils.printPreety(tree.getRoot());
        reverseStart(new Stack<>(), tree.getRoot());
    }

    private static void reverseStart(Stack<Integer> st, TreeNode root) {
        st.push(root.data);
        reverse(st, root);
//        System.out.println(st);
        while(!st.isEmpty()) {
            System.out.print(st.pop()+",");
        }
    }
    private static void reverse(Stack<Integer> st, TreeNode root) {

        if(root.right != null) {
            st.push(root.right.data);
        }

        if(root.left != null) {
            st.add(root.left.data);
        }

        if(root.right != null) {
            reverse(st, root.right);
        }

        if(root.left != null) {
            reverse(st, root.left);
        }

    }
}
