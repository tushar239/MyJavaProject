package algorithms.treeandgraph.tree;

import algorithms.treeandgraph.tree.baseclasses.BST;
import algorithms.treeandgraph.tree.baseclasses.TreeNode;

import java.util.HashMap;
import java.util.Map;
/*
Paths with Sum:
You are given a binary tree in which each node contains an integer value(which might be positive or negative).
Design an algorithm to count the number of paths that sum to a given value.
The path does not need to start or end at the root or a leaf, but must go downwards (traveling only from parent nodes to child nodes).

                    10
            5               -3
      3           2              11
 3       -2           1


 */
public class _12PathsWithSum {

    public static void main(String[] args) {
        TreeNode node10 = new TreeNode(10);
        TreeNode node5 = new TreeNode(5);
        TreeNode node_3 = new TreeNode(-3);
        TreeNode node11 = new TreeNode(11);
        TreeNode node2 = new TreeNode(2);
        TreeNode node1 = new TreeNode(1);
        TreeNode node3 = new TreeNode(3);
        TreeNode nodeAnother3 = new TreeNode(3);
        TreeNode node_2 = new TreeNode(-2);

        node10.left = node5;
        node10.right = node_3;
        node_3.right = node11;
        node5.left = node3;
        node5.right = node2;
        node2.right = node1;
        node3.left = nodeAnother3;
        node3.right = node_2;

        BST bst = new BST();
        bst.root = node10;
        bst.printPreety();

        //System.out.println(countPathsWithSum(node10, 8, 0, 0));;

        System.out.println();

//        int totalPathsOfSum = countPathsWithSum(node10, 8);
//        System.out.println("totalPathsOfSum: " + totalPathsOfSum);

        int totalPathsOfSum_ = countPathsWithSum_(node10, 8);
        System.out.println("totalPathsOfSum: " + totalPathsOfSum_);

    }

    /*
    Create separate output variables from root processing, left subtree processing and right subtree processing and then merge them as needed.
    Don’t take a chance of using the same output variable between these 3 processing.

    This approach is same as FindWhetherTeeIsBalanced.java.
    Every node is processed repeatedly.

    This is called BRUTE FORCE approach:
    In Brute Force approach, we just look at all possible paths.
    To do this, we traverse to each node. At each node, we recursively try all paths downwards, tracking the sum as we go.
    As soon as we hit our target sum, we increment the total.

    Brute Force concept is same for finding substring in a string or finding an element in an array etc.

    On each level of recursion tree, we compare N nodes. If a tree is balanced, then runtime complexity is O(n log n).
    For unbalanced tree, it can be O(n^2).

                        cpws(n, 8)
                        cpws(n,8,0)         --- total n nodes are visited

               cpws(n/2,8)            cpws(n/2,8)
               cpws(n/2,8,0)          cpws(n/2,8,0) --- total n nodes are visited

          cpws(n/4,8)   cpws(n/4,8)         cpws(,/4,8)
          cpws(n/4,8,0) cpws(n/4,8,0)       cpws(n/4,8,0)


    You can optimize it by avoiding Brute Force approach and traversing every node just once.
    This is the same problem as FindWhetherTreeIsBalanced.java
     */
    private static int countPathsWithSum_(TreeNode root, int expectedSum) {
        if (root == null) return 0;

        int totalPathsFromRoot = countPathsWithSum_(root, expectedSum, 0);

        int totalPathsFromLeft = countPathsWithSum_(root.left, expectedSum);
        int totalPathsFromRight = countPathsWithSum_(root.right, expectedSum);

        return totalPathsFromRoot + totalPathsFromLeft + totalPathsFromRight;
    }


    /*
    Important:
    you started hardcoding currentSum (that is not the expected output of the method) to root.data. As soon as you saw that, you realized that it is shared between recursive method calls. So, it should be passed as method parameter.

    private static int countPathsWithSum_(TreeNode root, int expectedSum) {
        if (root == null) return 0;

        int totalPaths = 0;
        currentSum = root.data;
        if (expectedSum == currentSum) {
            totalPaths++;
        }
        ...
    }
     */
    private static int countPathsWithSum_(TreeNode root, int expectedSum, int currentSum) {
        if (root == null) return 0;

        int totalPaths = 0;
        currentSum += root.data;// Important: you started hardcoding currentSum (that is not the expected output of the method) to root.data. As soon as you saw that, you realized that it is shared between recursive method calls. So, it should be passed as method parameter.
        if (expectedSum == currentSum) {
            totalPaths++;
        }

        int totalPathsFromLeft = countPathsWithSum_(root.left, expectedSum, currentSum);
        int totalPathsFromRight = countPathsWithSum_(root.right, expectedSum, currentSum);

        return totalPaths + totalPathsFromLeft + totalPathsFromRight;
    }

/*
    private static int countPathsWithSum(TreeNode root, int expectedSum) {
        if (root == null) return 0;

        int totalPaths = 0;

        totalPaths += countPathsWithSum(root, expectedSum, 0);

        totalPaths = totalPaths + countPathsWithSum(root.left, expectedSum);
        totalPaths = totalPaths + countPathsWithSum(root.right, expectedSum);

        return totalPaths;
    }

    private static int countPathsWithSum(TreeNode root, int expectedSum, int currentSum) {
        if (root == null) return 0;

        int totalPaths = 0;

        currentSum += root.data;
        if (expectedSum == currentSum) {
            totalPaths++;
        }

        totalPaths = totalPaths + countPathsWithSum(root.left, expectedSum, currentSum);
        totalPaths = totalPaths + countPathsWithSum(root.right, expectedSum, currentSum);

        return totalPaths;
    }*/


    /*
    Improved version of previous Brute Force based algorithm.
    It is complex to understand. Runtime complexity is O(n). Space complexity is O(n) due to hash table and O(log n) due to stack used for recursion.

    Read the explanation from Cracking Coding Interview book page 274
     */
    private static int countPathsWithSum_Improved(TreeNode root, int expectedSum) {
        if (root == null) return 0;

        Map<Integer, Integer> pathCount = new HashMap<>();
        incrementHashTable(pathCount, 0 , 1); //Needed if target path starts at root
        return countPathsWithSum_Improved(root, expectedSum, 0, pathCount);
    }

    private static int countPathsWithSum_Improved(TreeNode root, int expectedSum, int runningSum, Map<Integer, Integer> pathCount) {
        if(root == null) return 0;

        runningSum += root.data;
        incrementHashTable(pathCount, runningSum, 1); // Add runningSum

        int sum = runningSum - expectedSum;
        int totalPaths = pathCount.containsKey(sum) ? pathCount.get(sum) : 0;

        totalPaths += countPathsWithSum_Improved(root.left, expectedSum, runningSum, pathCount);
        totalPaths += countPathsWithSum_Improved(root.right, expectedSum, runningSum, pathCount);

        incrementHashTable(pathCount, runningSum, -1); // Remove running Sum
        return totalPaths;
    }

    private static void incrementHashTable(Map<Integer, Integer> hashTable, int key, int delta) {
        if(!hashTable.containsKey(key)) {
            hashTable.put(key, 0);
        }
        hashTable.put(key, hashTable.get(key) + delta);
    }

}
