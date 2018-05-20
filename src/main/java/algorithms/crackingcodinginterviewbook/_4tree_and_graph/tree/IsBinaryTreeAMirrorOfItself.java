package algorithms.crackingcodinginterviewbook._4tree_and_graph.tree;

import algorithms.crackingcodinginterviewbook._4tree_and_graph.tree.baseclasses.BST;
import algorithms.crackingcodinginterviewbook._4tree_and_graph.tree.baseclasses.TreeNode;

/**
 * @author Tushar Chokshi @ 11/8/15.
 */
// http://www.geeksforgeeks.org/symmetric-tree-tree-which-is-mirror-image-of-itself/
public class IsBinaryTreeAMirrorOfItself {

    public static void main(String[] args) {
        {
            count=0;
            System.out.println("Testing Non-Mirror BST");
            BST bst = BST.createBST();
            System.out.println("Is Mirror: " + isMirror(bst.root));
            System.out.println(count);
        }
        {
            count=0;
            System.out.println("Testing Mirror BST");
            BST bst = BST.createMirrorBST();
            System.out.println("Is Mirror: " + isMirror(bst.root));
            System.out.println(count);
        }
    }

    private static boolean isMirror(TreeNode root) {
        if (root == null) {
            return true;
        }
        if (!root.hasLeft() && !root.hasRight()) {
            return true;
        }
        return isMirror(root.left, root.right);
    }
    private static int count=0;
    /*
    Time complexity
       isMirror(n) = c + isMirror(n/2)+ isMirror(n/2)
       isMirror(n) = c + 2isMirror(n/2)

       Master's theorem
       T(n)=aT(n/b) + O(n^d) = O(n^d log n)

       So here isMirror(n)=O(log n)

    Space complexity
        Like Merge Sort, no sub array's are created. So no extra space other than stack for recursive calls, which will be O(log n)
     */
    private static boolean isMirror(TreeNode leftRoot, TreeNode rightRoot) {
        if (leftRoot == null && rightRoot == null) {
            return true;
        }
        if ((leftRoot == null && rightRoot != null) || (leftRoot != null && rightRoot == null)) {
            return false;
        }
        count++;
        /*if (leftRoot.data != rightRoot.data) {
            return false;
        }
        return isMirror(leftRoot.left, rightRoot.left) && isMirror(leftRoot.right, rightRoot.right);*/
        //OR
        return (leftRoot.data == rightRoot.data) && isMirror(leftRoot.left, rightRoot.left) && isMirror(leftRoot.right, rightRoot.right);
    }

}
