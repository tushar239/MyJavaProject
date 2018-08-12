package algorithms._3binary_tree.geeksforgeeks;

import algorithms.crackingcodinginterviewbook._4tree_and_graph.tree.baseclasses.TreeNode;
import algorithms.utils.TreeUtils;

/*
    Diameter of a Binary Tree (Max number of nodes(longest path) between any two leaves of a tree)

    https://www.youtube.com/watch?v=ey7DYc9OANo


                             1
                    2                 3
               4            5
           6       7    8       9
                             10   11


    What is diameter of a tree?

    Max number of nodes(longest path) between any two leaves of a tree


    one possibility of the diameter is the one that passes through root (6 to 3)

                             1
                    2                 3
               4
           6

    another possibility is in left subtree (6 to 10)


                    2
               4            5
           6                      9
                               10

    another possibility is in right subtree

                                    3


    Max of 3 becomes a diameter of this tree.
*/
public class _7FindDiameterOfABinaryTree {

    public static void main(String[] args) {
        TreeNode ten = new TreeNode(10);
        TreeNode eight = new TreeNode(8);
        TreeNode two = new TreeNode(2);
        TreeNode three = new TreeNode(3);
        TreeNode five = new TreeNode(5);

        ten.left = eight;
        ten.right = two;
        eight.left = three;
        eight.right = five;

        System.out.println("Input Binary Tree:");
        TreeUtils.printPreety(ten);

        int diameter = findDiameter(ten);
        System.out.println("Diameter:" + diameter);

    }

    /*
         This algorithm takes O(n^2) because of finding the height of a subtree more than once from the same node.

         You can use dynamic programming to memoize the height of a tree from a specific node.
         This will take O(n)
    */
    private static int findDiameter(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int heightOfLeftSubTree = getHeight(root.left);
        int heightOfRightSubTree = getHeight(root.right);

        int diameterThatIncludesRoot = 1 + heightOfLeftSubTree + heightOfRightSubTree;

        int diameterOfLeftSubTree = findDiameter(root.left);
        int diameterOfRightSubTree = findDiameter(root.right);

        /*if ((diameterThatIncludesRoot > diameterOfLeftSubTree) && (diameterThatIncludesRoot > diameterOfRightSubTree)) {
            System.out.println(root.data);
        }*/

        return Math.max(diameterThatIncludesRoot, Math.max(diameterOfLeftSubTree, diameterOfRightSubTree));
    }

    public static int getHeight(TreeNode root) {

        if (root == null) {
            return 0;
        } else {
            System.out.println("Finding height of tree that starts with root: " + root.data);
            return 1 + Math.max(getHeight(root.left), getHeight(root.right));
        }
    }

}
