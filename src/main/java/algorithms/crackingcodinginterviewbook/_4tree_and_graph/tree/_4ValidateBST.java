package algorithms.crackingcodinginterviewbook._4tree_and_graph.tree;

import algorithms.crackingcodinginterviewbook._4tree_and_graph.tree.baseclasses.BST;
import algorithms.crackingcodinginterviewbook._4tree_and_graph.tree.baseclasses.TreeNode;

import static algorithms.utils.TreeUtils.printPreety;

// http://www.programcreek.com/2012/12/leetcode-validate-binary-search-tree-java/
// http://codereview.stackexchange.com/questions/67928/validating-a-binary-search-tree

/*

DON'T READ the solution of this class. Read 'CheckIfBinaryTreeIsBinarySearchTree.java'.


Validate BST:
Implement a function to check if a binary tree is a binary search tree.

    5
  3   9
 2 4 8 10


Is BST: true

    5
  4   7
 2 6 8 10


Is BST: false  because left side of 5 has greater value 6

NOTE:
Just checking whether left and right subtrees are BST to decide entire tree is BST will not be enough.
You need to compare max value of left subtree and min value of right subtree with root value. This makes this algorithm trickier.
e.g.

    4
2      6

is a BST, but

    5
  4   7
 2 6 8 10

is not a BST
 */
public class _4ValidateBST {

    public static void main(String[] args) {
        {
            BST bst = BST.createBST();

            printPreety(bst.root);

            System.out.println("Is BST: " + checkBST(bst.root));

            Result result = checkBST_Another_Harder_Approach(bst.root);
            System.out.println("Is BST: " + result.isBst+", min: "+result.min+", max: "+result.max);// true, 2, 10

        }
        System.out.println();
        {
            BST bst = BST.createNonBST();

            printPreety(bst.root);

            System.out.println("Is BST: " + checkBST(bst.root));

            Result result = checkBST_Another_Harder_Approach(bst.root);
            System.out.println("Is BST: " + result.isBst+", min: "+result.min+", max: "+result.max);// false, null, null

        }


    }


    private static boolean checkBST(TreeNode root) {
        if(root == null) return true;
        //return checkBST(root, null, null);
        return checkBST(root, root.data, root.data);
    }

    /*

     Approach shown in the book (pg 248)

        IMPORTANT:
        This is easier to think and code compared to another approach of returning max and min from Left and Right Subtrees respectively to compare with root.
        Remember, whenever you see a need to returning more than one values from a recursive method, then think that these values shared between recursive calls.
        If answer is yes, then pass them as input parameters. Your code and logic will be easier and less error prone.

        checkBST method should return true or false.
        but root's data should be compared with max from left subtree and min from right subtree.
        min and max should be shared between recursive method calls.
        So, passing them as parameters.

        Remember, don't initialize literal values by 0 or some default value. Initialize them by null.
        If you initialize them by 0, algorithm will be error prone.

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

        //if ((min != null && root.data <= min) || (max != null && root.data > max)) return false;
        if ((min != null && root.data < min) || (max != null && root.data > max)) return false;

        //if (!checkBST(root.left, min, root.data) || !checkBST(root.right, root.data, max)) return false;
        if (!checkBST(root.left, null, root.data) || !checkBST(root.right, root.data, null)) return false;

        return true;
    }


    /*
    This is HARDER APPROACH because you are returning multiple values from recursive method.
    Above approach is easier.

    Here, I started thinking that how can I do recursion first and then try to compare root's value with values
    returned by left and right subtrees.
    Left and Right subtrees should return max and min values respectively. So, instead of just returning
    isBst, I decided to return max and min values also from Left and Right subtrees.
     */
    private static Result checkBST_Another_Harder_Approach(TreeNode root) {
        if (root == null) {
            return new Result(true, null, null);
        }

        boolean isBst = false;
        Integer max = root.data;
        Integer min = root.data;

        if (root.left == null && root.right == null) {
            isBst = true;
        } else if (root.left == null) {
            if (root.right.data > root.data) {
                isBst = true;
            }
        } else {
            if (root.left.data < root.data) {
                isBst = true;
            }
        }


        if (isBst) {

            // get isBst and max from left subtree
            Result resultFromLeft = checkBST_Another_Harder_Approach(root.left);// (true, null, null) will be returned from null nodes

            if (resultFromLeft.isBst && (resultFromLeft.max == null || resultFromLeft.max < max)) {
                Integer min1 = resultFromLeft.min;
                if (min1 == null) min1 = min;

                // get isBst and min from right subtree
                Result resultFromRight = checkBST_Another_Harder_Approach(root.right);// (true, null, null) will be returned from null nodes

                if (resultFromRight.isBst && (resultFromRight.min == null || resultFromRight.min > min)) {

                    Integer max1 = resultFromRight.max;
                    if (max1 == null) max1 = max;

                    return new Result(isBst, min1, max1);
                }
            }
        }

        return new Result(false, null, null); // i don't care about min and max values as actual value isBst=false
    }

    static class Result {
        private boolean isBst;
        private Integer min;
        private Integer max;

        public Result(boolean isBst, Integer min, Integer max) {
            this.isBst = isBst;
            this.min = min;
            this.max = max;
        }

        public Result() {
        }

        public void setBst(boolean bst) {
            isBst = bst;
        }

        public void setMin(Integer min) {
            this.min = min;
        }

        public void setMax(Integer max) {
            this.max = max;
        }

        public boolean isBst() {
            return isBst;
        }

        public Integer isMin() {
            return min;
        }

        public Integer isMax() {
            return max;
        }
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
