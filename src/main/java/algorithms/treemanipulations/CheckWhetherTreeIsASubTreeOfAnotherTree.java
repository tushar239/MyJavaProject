package algorithms.treemanipulations;

import algorithms.treemanipulations.baseclasses.BST;
import algorithms.treemanipulations.baseclasses.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tushar Chokshi @ 9/7/15.
 */
public class CheckWhetherTreeIsASubTreeOfAnotherTree {
    public static void main(String[] args) {
        BST bst1 = BST.createBST();
        {
            BST bst2 = BST.createSubBST();

            TreeNode bst1Node = find(bst1, bst2.root);
            System.out.println("Testing subtree:");
            System.out.println(match(bst1Node, bst2.root));
            System.out.println("Testing subtree using in-order and pre-order:");
            System.out.println(usingInOrderPreOrder(bst1, bst2));
        }
        System.out.println();
        {
            BST bst2 = BST.createNonSubBST();

            TreeNode bst1Node = find(bst1, bst2.root);
            System.out.println("Testing non-subtree:");
            System.out.println(match(bst1Node, bst2.root));
            System.out.println("Testing non-subtree using in-order and pre-order:");
            System.out.println(usingInOrderPreOrder(bst1, bst2));
        }


    }

    private static TreeNode find(BST bst1, TreeNode subTreeRoot) {
        return bst1.get(subTreeRoot.data);
    }

    /*
    Time Complexity: Time worst case complexity of above solution is O(mn) where m and n are number of nodes in given two trees.
    If number of nodes in both trees are same (n), then time complexity is O(N^2). Space complexity will be O(log n)
     */
    private static boolean match(TreeNode bst1Node, TreeNode subTreeRoot) {
        if(bst1Node == null && subTreeRoot == null) return true;
        if((bst1Node != null && subTreeRoot == null) || (bst1Node == null && subTreeRoot != null)) return false;
        if(bst1Node.data != subTreeRoot.data) return false;

        return match(bst1Node.left, subTreeRoot.left) && match(bst1Node.right, subTreeRoot.right);

    }

    /*
    Time complexity can be reduced to O(n).

    http://www.geeksforgeeks.org/if-you-are-given-two-traversal-sequences-can-you-construct-the-binary-tree/
    http://www.geeksforgeeks.org/check-binary-tree-subtree-another-binary-tree-set-2/

    The idea is based on the fact that inorder and preorder/postorder uniquely identify a binary tree.
    Tree S is a subtree of T if both inorder and preorder traversals of S are substrings of inorder and preorder traversals of T respectively.

    We can also use postorder traversal in place of preorder in the above algorithm.

    Following are detailed steps.

    1) Find inorder and preorder traversals of T, store them in two auxiliary arrays inT[] and preT[].

    2) Find inorder and preorder traversals of S, store them in two auxiliary arrays inS[] and preS[].

    3) If inS[] is a subarray of inT[] and preS[] is a subarray preT[], then S is a subtree of T. Else not.

    The above algorithm doesn't work for cases where a tree is present in another tree, but not as a subtree. Consider the following example.

            Tree1
              x
            /    \
          a       b
         /
        c


            Tree2
              x
            /    \
          a       b
         /         \
        c           d

     */


    private static boolean usingInOrderPreOrder(BST T, BST S) {
        if (S == null)  return true;
        if (T == null)  return false;

        List<Integer> T_IOT = new ArrayList<>();
        T.inOrderTraversalConsideringNull(T.root, T_IOT);

        List<Integer> S_IOT = new ArrayList<>();
        S.inOrderTraversalConsideringNull(S.root, S_IOT);

        if(isContains(T_IOT, S_IOT)) {

            List<Integer> T_POT = new ArrayList<>();
            T.preOrderTraversalConsideringNull(T.root, T_POT);

            List<Integer> S_POT = new ArrayList<>();
            S.preOrderTraversalConsideringNull(S.root, S_POT);

            return isContains(T_POT, S_POT);
        }

        return false;
    }

    private static boolean isContains(List<Integer> list, List<Integer> subList) {
        // e.g. nilList = [null, 2, null, 3, null, 4, null, 5, null, 8, null, 9, null, 10, null]
        // subList = [null, 2, null, 3, null, 4, null]
        // then we need to remove parenthesis before we check for contains
        return list.toString().substring(1, list.toString().length() - 1).contains(subList.toString().substring(1, subList.toString().length() - 1));
    }
}
