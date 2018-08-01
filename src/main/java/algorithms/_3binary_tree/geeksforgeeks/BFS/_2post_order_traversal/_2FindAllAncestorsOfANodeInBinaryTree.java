package algorithms._3binary_tree.geeksforgeeks.BFS._2post_order_traversal;

import algorithms._3binary_tree.geeksforgeeks.BinaryTree;
import algorithms.crackingcodinginterviewbook._4tree_and_graph.tree.baseclasses.TreeNode;
import algorithms.utils.TreeUtils;
import com.google.common.collect.Lists;

import java.util.List;

/*
    Print ancestors of a node in Binary tree

    https://www.youtube.com/watch?v=qjD-CmuC0MQ


    This is the perfect example of Post-Order Traversal.

    Why Post-Order Traversal?
    Unless the node of interest is found in left or right subtree, you are not sure whether current node can be an ancestor.


    e.g.
        Input Binary Tree:

            4
          2   6
         1 3 5 7


                                   isNodeFound=find(root=4, node=1)
                                                |   ^
                                                V   |
root=4, node=1   exit conditions   isNodeFound=find(2, 1)   if(isNodeFound) print(root)
                                                |   ^
                                                V   |
root=2, node=1   exit conditions   isNodeFound=find(1, 1)   if(isNodeFound) print(root)
                                                |   ^
                                                V   |
root=1, node=1   exit condition matched, so return true

*/
public class _2FindAllAncestorsOfANodeInBinaryTree {

    public static void main(String[] args) {
        List<Integer> list1 = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7);
        BinaryTree binaryTree1 = new BinaryTree(TreeUtils.createBinaryTree(list1, 0, list1.size() - 1));

        System.out.println("Input Binary Tree:");
        TreeUtils.printPreety(binaryTree1.getRoot());

        {
            TreeNode node = new TreeNode(1);
            System.out.println("Ancestors of TreeNode(" + node.data + ")");
            boolean nodeFound = findAllAncestors(binaryTree1.getRoot(), node);
            System.out.println("node found?: " + nodeFound);
        }

        System.out.println();

        {
            TreeNode node = new TreeNode(10);
            System.out.println("Ancestors of TreeNode(" + node.data + ")");
            boolean nodeFound = findAllAncestors(binaryTree1.getRoot(), node);
            System.out.println("node found?: " + nodeFound);
        }

    }

    private static boolean findAllAncestors(TreeNode root, TreeNode node) {
        if (root == null && node == null) return true;

        if (root == null && node != null) return false;

        if (root.data == node.data) return true;

        if(findAllAncestors(root.left, node) || findAllAncestors(root.right, node)) {
            System.out.println(root.data);
            return true;
        }
        return false;

/*        boolean isNodeFound = findAllAncestors(root.left, node);
        if (isNodeFound) {
            System.out.println(root.data);
            return true;
        } else {
            isNodeFound = findAllAncestors(root.right, node);
            if (isNodeFound) {
                System.out.println(root.data);
                return true;
            }
        }
        return false;*/
    }
}
