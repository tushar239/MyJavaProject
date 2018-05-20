package algorithms.crackingcodinginterviewbook._4tree_and_graph.tree;

import algorithms.crackingcodinginterviewbook._4tree_and_graph.tree.baseclasses.BST;
import algorithms.crackingcodinginterviewbook._4tree_and_graph.tree.baseclasses.TreeNode;
import org.apache.commons.lang.StringUtils;

import java.util.LinkedList;
import java.util.List;

import static algorithms.utils.TreeUtils.printPreety;

/**
 * @author Tushar Chokshi @ 9/7/15.
 */
public class _11CheckWhetherTreeIsASubTreeOfAnotherTree {
    public static void main(String[] args) {
        BST bst1 = BST.createBST();
        printPreety(bst1.root);
        {
            BST bst2 = BST.createSubBST();
            printPreety(bst2.root);

            TreeNode bst1Node = find(bst1, bst2.root);
            System.out.println("Testing subtree:");
            //System.out.println(match(bst1Node, bst2.root));
            System.out.println(isSubtree(bst1.root, bst2.root));

            System.out.println("Testing subtree using in-order and pre-order:");
            //System.out.println(usingInOrderPreOrder(bst1, bst2));
            System.out.println(isSubtree_easyWay(bst1.root, bst2.root));
        }
        System.out.println();
        {
            BST bst2 = BST.createNonSubBST();
            printPreety(bst2.root);

            TreeNode bst1Node = find(bst1, bst2.root);
            System.out.println("Testing non-subtree:");
            //System.out.println(match(bst1Node, bst2.root));
            System.out.println(isSubtree(bst1Node, bst2.root));

            System.out.println("Testing non-subtree using in-order and pre-order:");
            //System.out.println(usingInOrderPreOrder(bst1, bst2));
            System.out.println(isSubtree_easyWay(bst1.root, bst2.root));
        }

    }

    /*
    Runtime Complexity:
                            s(n, m)
                            match(n,m)

            s(n/2,m)                        s(n/2,m)
            match(n/2,m)                    match(n/2,m)

    s(n/4,m)        s(n/4,m)        s(n/4,m)        s(n/4,m)
    match(n/4,m)    match(n/4,m)    match(n/4,m)    match(n/4,m)

    At every node of above recursive tree, m comparisons are made.
    So, runtime complexity is O(nm), if m<n.

    If number of nodes in both trees are same (n), then time complexity is O(n^2). Space complexity will be O(log n).

    Memory complexity:
    utilized stack O(log m)+O(log n)
    on each call of isSubtree, match will acquire O(log m) stack and that stack memory will be released before next isSubtree is called.
    So, by the time, isSubtree acquires O(log n) stack, match will acquire only O(log m).


    Even though, isSubtree_easyWay shows lesser runtime complexity, I would say this approach is still better.
    Because, O(nm) is worst case when you assume that every node of T1 matches the root of T2.
    In reality, only 1-2 nodes might match. If you have a tree with distinct values, then runtime complexity will be reduced to O(n+km) where k is the number of times T2's root appear in T1.

     */
    private static boolean isSubtree(TreeNode T1, TreeNode T2) {
        if (T1 == null && T2 == null) return true;
        if (T1 != null && T2 == null) return true;
        if (T1 == null && T2 != null) return false;

        boolean match = match_(T1, T2);
        if (match) return true;
        return isSubtree(T1.left, T2) || isSubtree(T1.right, T2);
    }

    private static boolean match_(TreeNode T1, TreeNode T2) {
        if (T1 == null && T2 == null) return true;
        if (T1 != null && T2 == null) return true;
        if (T1 == null && T2 != null) return false;

        if (T1.data == T2.data) return match_(T1.left, T2.left) && match_(T1.right, T2.right);
        return false;
    }

/*
    Time complexity can be reduced to O(n+m).

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

    // memory complexity is O(n+m)
    private static boolean isSubtree_easyWay(TreeNode T1, TreeNode T2) {
        List<Integer> t1List = new LinkedList<>();
        inOrderTraversal(T1, t1List); // O(n)

        List<Integer> t2List = new LinkedList<>();
        inOrderTraversal(T2, t2List); // O(m)

        String t1Str = StringUtils.join(t1List, "");
        String t2Str = StringUtils.join(t2List, "");

        if (t1Str.contains(t2Str)) { // O(nm) --- if you ignore time complexity of string comparisons, then overall time complexity of this algorithm is O(n)
            t1List = new LinkedList<>();
            preOrderTraversal(T1, t1List);// O(n)

            t2List = new LinkedList<>();
            preOrderTraversal(T2, t2List);// O(m)

            t1Str = StringUtils.join(t1List, "");
            t2Str = StringUtils.join(t2List, "");

            return t1Str.contains(t2Str);// O(nm)
        }
        return false;
    }

    private static void inOrderTraversal(TreeNode treeNode, List<Integer> list) {
        if (treeNode == null) {
            list.add(null);
            return;
        }

        inOrderTraversal(treeNode.left, list);
        visitTreeNode(treeNode, list);
        inOrderTraversal(treeNode.right, list);
    }

    private static void preOrderTraversal(TreeNode treeNode, List<Integer> list) {
        if (treeNode == null) {
            list.add(null);
            return;
        }

        visitTreeNode(treeNode, list);
        preOrderTraversal(treeNode.left, list);
        preOrderTraversal(treeNode.right, list);
    }


    private static void visitTreeNode(TreeNode treeNode, List<Integer> list) {
        list.add(treeNode.data);
    }


    private static TreeNode find(BST bst1, TreeNode subTreeRoot) {
        return bst1.get(subTreeRoot.data);
    }



    /*
    private static boolean match(TreeNode bst1Node, TreeNode subTreeRoot) {
        if (bst1Node == null && subTreeRoot == null) return true;
        if ((bst1Node != null && subTreeRoot == null) || (bst1Node == null && subTreeRoot != null)) return false;
        if (bst1Node.data != subTreeRoot.data) return false;

        return match(bst1Node.left, subTreeRoot.left) && match(bst1Node.right, subTreeRoot.right);

    }


    private static boolean usingInOrderPreOrder(BST T, BST S) {
        if (S == null) return true;
        if (T == null) return false;

        List<Integer> T_IOT = new ArrayList<>();
        T.inOrderTraversalConsideringNull(T.root, T_IOT);

        List<Integer> S_IOT = new ArrayList<>();
        S.inOrderTraversalConsideringNull(S.root, S_IOT);

        if (isContains(T_IOT, S_IOT)) {

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
    }*/
}
