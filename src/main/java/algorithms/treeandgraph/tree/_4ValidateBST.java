package algorithms.treeandgraph.tree;

import algorithms.treeandgraph.tree.baseclasses.BST;
import algorithms.treeandgraph.tree.baseclasses.TreeNode;

/*
Validate BST:
Implement a function to check if a binary tree is a binary search tree
 */
// http://www.programcreek.com/2012/12/leetcode-validate-binary-search-tree-java/
// http://codereview.stackexchange.com/questions/67928/validating-a-binary-search-tree
public class _4ValidateBST {

    public static void main(String[] args) {
        {
            BST bst = BST.createBST();
//            System.out.println("Test BST approach:"+ isBinarySearchTree(bst.root));
//            System.out.println(count);

            bst.printPreety();

            System.out.println("Is BST: " + checkBST(bst.root));

//            System.out.println(checkBST_1(bst.root));
        }
        System.out.println();
        {
            BST bst = BST.createNonBST();
//           System.out.println("Test Non-BST approach:" + isBinarySearchTree(bst.root));

            bst.printPreety();

            System.out.println("Is BST: " + checkBST(bst.root));


//            System.out.println(checkBST_1(bst.root));
        }
    }


    private static boolean checkBST(TreeNode root) {
        return checkBST(root, null, null);
    }

    /*

    I tried to think returning max from left subtree and min from right subtree and comparing them with root data.
    I could not implement this code.

        boolean checkBST(root) {
            if(root == null) return true;

            try {
                int max = checkLeftBST(root.left, root);
                int min = checkRightBST(root.right, root);

                if(max > root.data) return false;
                if(min < root.data) return false;

            } catch (NotBstException e) {
                return false;
            }
        }

        int checkLeftBST(node, parent) {
            ... couldn't implement correctly....
        }
        int checkRightBST(node, parent) {
            ... couldn't implement correctly....
        }

    Finally, I took the approach shown in the book (pg 248)

        To find max from left subtree, you need to initialize max=root while traversing left subtree
        To find min from right subtree, you need to initialize min=root while traversing right subtree.

        Once you start thinking this way, code will start appearing by itself.

     Runtime Complexity:

                       isBst(n)
            isBst(n/2)             isBst(n/2)
      isBst(n/4)   isBst(n/4)  isBst(n/4)  isBst(n/4)

      On each call of isBst(...), only one node is compared. So, time complexity is O(n).

    */
    private static boolean checkBST(TreeNode root, Integer min, Integer max) {
        if (root == null) return true;

        if ((min != null && root.data <= min) || (max != null && root.data > max)) return false;

        if (!checkBST(root.left, min, root.data) || !checkBST(root.right, root.data, max)) return false;

        return true;
    }



    /* This won't work for all scenarios

    This algorithm will work for

        5
      6   7
     2 4 8 10

   It will detect that this is not BST because 6>5.

    But, it will NOT work for

        5
      4   7
     2 6 8 10

   It will not detect that 6>5.

  This algorithm compares on parent node with child nodes. It doesn't keep track of max and min while traversing entire left and right subtrees.


    */

    private static boolean isBinarySearchTree(TreeNode root) {

        if (root == null) {
            return true;
        }

        if (!root.hasLeft() && !root.hasRight()) {
            return true;
        }


        if (root.hasLeft()) {
            if (root.left.data.compareTo(root.data) > 0) {
                // node is not after (the same as) than min
                return false;
            }

        }
        if (root.hasRight()) {
            if (root.right.data.compareTo(root.data) <= 0) {
                return false;
            }
        }

        boolean isLeftTreeBst = isBinarySearchTree(root.left);
        boolean isRightTreeBst = isBinarySearchTree(root.right);

        return isLeftTreeBst && isRightTreeBst;
    }



/*

    private static boolean checkBST_1(TreeNode root) {
        if(root == null) return true;

        try {
            TreeNode maxNode = checkLeftSubTree(root.left, root);
            if (!maxNode.equals(root)) return false;

            TreeNode minNode = checkRightSubTree(root.right, root);
            if (!minNode.equals(root)) return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    private static TreeNode checkLeftSubTree(TreeNode left, TreeNode parent) throws Exception {
        if(left == null) return parent;

        TreeNode maxNode = checkLeftSubTree(left.left, left);

        if(maxNode != null && maxNode.data > parent.data) throw new Exception("not a bst");

        TreeNode minNode = checkRightSubTree(left.right, left);

        if(minNode != null && parent.data > minNode.data) throw new Exception("not a bst");

       *//* if(minNode == null || parent.data > minNode.data) return parent;
        else*//* return minNode;

    }

    private static TreeNode checkRightSubTree(TreeNode right, TreeNode parent) throws Exception {
        if(right == null) return right;

        TreeNode maxNode = checkLeftSubTree(right.left, right);

        if(maxNode.data > parent.data) throw new Exception("not a bst");

        TreeNode minNode = checkRightSubTree(right.right, right);

        if(minNode != null && parent.data > minNode.data) throw new Exception("not a bst");

        *//*if(minNode == null || parent.data > minNode.data) return minNode;
        else*//* return minNode;
    }*/


}
