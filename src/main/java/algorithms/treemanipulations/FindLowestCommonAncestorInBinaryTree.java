package algorithms.treemanipulations;

import algorithms.treemanipulations.baseclasses.BST;
import algorithms.treemanipulations.baseclasses.TreeNode;

/**
 * @author Tushar Chokshi @ 9/7/15.
 */

/*
http://www.geeksforgeeks.org/lowest-common-ancestor-binary-tree-set-1/

If Tree is
                 5

         3              9

      2     4        8     10

If you want to find lowest common ancestor of 2 and 4, then it will be 3.
If you want to find lowest common ancestor of 3 and 2, then it will be 3.
If you want to find lowest common ancestor of 3 and 4, then it will be 3.

See tree traversal in 'Sorting Algorithm Worksheet.xlsx'

 */
public class FindLowestCommonAncestorInBinaryTree {
    public static void main(String[] args) {
        BST bst = BST.createBST();
        System.out.println("Assuming Parent link is NOT available for all nodes in a tree");
        System.out.println("Case -1, finding ancestor of 2 and 3:   "+findLowestCommonAncestor(bst.root, new TreeNode(2), new TreeNode(3))); // O/P: 3
        System.out.println("Case -2, finding ancestor of 2 and 4:   "+findLowestCommonAncestor(bst.root, new TreeNode(2), new TreeNode(4)));// O/P: 3

        System.out.println();

        System.out.println("Assuming Parent link is available for all nodes in a tree");
        System.out.println("Case -1, finding ancestor of 2 and 3:   "+findLowestCommonAncestorWithParentLink(bst.get(2),  bst.get(3)));// O/P: 3
        System.out.println("Case -2, finding ancestor of 2 and 4:   "+findLowestCommonAncestorWithParentLink(bst.get(2), bst.get(4)));// O/P: 3
    }

    /*
        Here unless you traverse left and right subtree, you can not find the result, so u can say this algorithm uses post-traversal.
        Only pre-traversal algorithm can be converted to Iterative algorithm. So, this algorithm can't be converted to Iterative algorithm.

        It is actually not a post traversal also because you are actually not doing any processing on root node after visiting root.left and root.right (left and right subtrees)

        IMPORTANT:
        Remember, when recursive method is returning a value, then store returned value of left tree traversal and right tree traversal in different variables and later on use those two variable to reach to some conclusion.
        When you are thinking of a recursion, then take the smallest tree of 3 elements as an example like below.

        Take below tree as an example
            4
        3       5

        If you need to find lowest common ancestor of 3 and 4, then it will be 4
        If you need to find lowest common ancestor of 3 and 5, then it will be 4

        If you need to find lowest common ancestor of 3 and 3, then it will be 3
        If you need to find lowest common ancestor of 3 and 6, then it will be 3
        If you need to find lowest common ancestor of 4 and 6, then it will be 4

        When you are thinking of recursion, then each step in a tree of a recursive method call has access to input parameters.
        Here there basically 4 main steps - 1) exit condition 2)left subtree traversal 3) right subtree traversal 4) comparing the result of left and right subtree traversals.
        All these 4 steps are executed as a call to parent method call.
        e.g. fLCA(root=4, node1=3 and node2=5) has above 4 steps and all these steps have access to parent method call's input parameters (root=4, node1=3 and node2=5)

     */
    public static TreeNode findLowestCommonAncestor(TreeNode root, TreeNode node1, TreeNode node2) {

        if (root == null) { //exit cond 1
            return null;
        }

        if (root.data == node1.data || root.data == node2.data) {//exit cond 2  (taking care of situation like - If you need to find lowest common ancestor of 3 and 6, then it will be 3)
            return root;
        }

        TreeNode oneFoundNode = findLowestCommonAncestor(root.left, node1, node2); // node1 or node2 found. Storing into oneFoundNode.
        TreeNode secondFoundNode = findLowestCommonAncestor(root.right, node1, node2); // node1 or node2 found. Storing into secondFoundNode.


        if (oneFoundNode != null && secondFoundNode != null) { // If both nodes are traversed
            return root;
        }
        // else if only one of the nodes are traversed
        return oneFoundNode != null ? oneFoundNode : secondFoundNode;// see BST's postOrderTraversal()'s comment to understand why this condition is required. Also see 'Sorting Algorithm Worksheet.xlsx' for better understanding.
    }



    // If you have a parent link available
    public static TreeNode findLowestCommonAncestorWithParentLink(TreeNode node1, TreeNode node2) {
        if (node1.data == node2.data) return null;

        TreeNode ancestor = node1;

        while (ancestor != null) {
            if (ancestorIsInPathOf(ancestor, node2)) {
                return ancestor;
            }
            ancestor = ancestor.parent;
        }

        return null;
    }

    private static boolean ancestorIsInPathOf(TreeNode ancestor, TreeNode node) {
        while (node != null) {
            if (node.data == ancestor.data) {
                return true;
            }
            node = node.parent;
        }

        return false;
    }
}
