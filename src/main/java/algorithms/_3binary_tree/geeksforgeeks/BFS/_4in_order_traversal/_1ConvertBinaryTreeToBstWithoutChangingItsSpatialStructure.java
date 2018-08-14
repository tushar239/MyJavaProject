package algorithms._3binary_tree.geeksforgeeks.BFS._4in_order_traversal;

import algorithms.crackingcodinginterviewbook._4tree_and_graph.tree.baseclasses.TreeNode;
import algorithms.utils.TreeUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
    Converting Binary Tree to Binary Search Tree without changing spatial structure

    https://www.youtube.com/watch?v=wBFttOncVUc

    Solution that won't work:

        CreateMinimalBST.java is not a solution for this this problem.
        CreateMinimalBST.java creates almost balanced BST.

            1) Traverse a tree using any traversal method and create an array.
            2) Sort an array.
            3) Traverse both sorted array and tree together.
                    Find a mid of sorted array and replace it with root.
                    if tree has left child, recurse with left node and left side of mid of an array
                    if tree has right child, recurse with right node and right side of mid of an array.

            Input Tree:
                4
               5
              3
             1
            2


           In this case, it will create

           [1,2,3,4,5]

                    3
                   1

           It will never go to the right of the mid of an array

    Solution that will work:

        See  ConvertBinaryTreeToBstWithoutChangingItsSpatialStructure.pdf

        It clearly proves that you have to traverse a tree In-Order to change it to BST.

        This algorithm is a bit tricky. Please see practice it properly.
*/
public class _1ConvertBinaryTreeToBstWithoutChangingItsSpatialStructure {

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

        createBstFromBinaryTree(ten);
    }

    private static void createBstFromBinaryTree(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        createArrayFromBinaryTree(root, list);

        Collections.sort(list);

        System.out.println("sortedList: " + list);

        Integer[] sortedArray = list.toArray(new Integer[0]);

        // This won't work
        //createBstFromBinaryTree_In_CreateMinimalBST_Way(root, sortedArray, 0, sortedArray.length - 1);

        //createBstFromBinaryTreeWithoutChangingTheStructureOfOriginalTree(root, sortedArray);

        createBstFromBinaryTreeWithoutChangingTheStructureOfOriginalTree(root, sortedArray, new StartClass(1));

        TreeUtils.printPreety(root);
    }

    private static void createArrayFromBinaryTree(TreeNode root, List<Integer> list) {
        if (root == null) return;

        list.add(root.data);

        createArrayFromBinaryTree(root.left, list);
        createArrayFromBinaryTree(root.right, list);
    }

    /*
        This logic is similar to CreateMinimialBST.java, but it won't work

        Input Tree:
            4
           5
          3
         1
        2


       In this case, it will create

       [1,2,3,4,5]

                3
               1

       It will never go to the right of the mid of an array
    */
    private static void createBstFromBinaryTree_In_CreateMinimalBST_Way(TreeNode root, Integer[] array, int start, int end) {

        if (root == null) return;
        if (start > end) return;

        int mid = (start + end) / 2;

        root.data = array[mid];

        if (root.left != null) {
            createBstFromBinaryTree_In_CreateMinimalBST_Way(root.left, array, start, mid - 1);
        }

        if (root.right != null) {
            createBstFromBinaryTree_In_CreateMinimalBST_Way(root.right, array, mid + 1, end);
        }

    }

    /* This one also works

    private static int start=0;

    private static void createBstFromBinaryTreeWithoutChangingTheStructureOfOriginalTree(TreeNode root, Integer[] array) {
        if (root == null) return;

        createBstFromBinaryTreeWithoutChangingTheStructureOfOriginalTree(root.left, array);

        root.data = array[start];
        start = start+1;

        createBstFromBinaryTreeWithoutChangingTheStructureOfOriginalTree(root.right, array);
    }*/

    private static class StartClass {
        int start;

        public StartClass(int initialValue) {
            this.start = initialValue;
        }

        void incrementStart() {
            start++;
        }

        public int getStart() {
            return start;
        }
    }

    private static void createBstFromBinaryTreeWithoutChangingTheStructureOfOriginalTree(TreeNode root, Integer[] array, StartClass startClass) {
        if (root == null) return;

        createBstFromBinaryTreeWithoutChangingTheStructureOfOriginalTree(root.left, array, startClass);

        root.data = array[startClass.getStart()];
        startClass.incrementStart();

        createBstFromBinaryTreeWithoutChangingTheStructureOfOriginalTree(root.right, array, startClass);
    }
}
