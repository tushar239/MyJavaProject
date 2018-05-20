package algorithms.crackingcodinginterviewbook._4tree_and_graph.tree;

import algorithms.crackingcodinginterviewbook._4tree_and_graph.tree.baseclasses.BST;
import algorithms.crackingcodinginterviewbook._4tree_and_graph.tree.baseclasses.TreeNode;

import java.util.HashMap;
import java.util.Map;

import static algorithms.utils.TreeUtils.printPreety;

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

    private static int countWithBruteForce = 0;
    private static int countWithTailRecursion = 0;
    private static int countWithBetterApproach = 0;
    private static int countWithMemoization = 0;

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
        printPreety(bst.root);

        System.out.println();

        {
            countWithBruteForce = 0;
            int totalPathsOfSum = countPathsWithSum_BruteForce(node10, 8);
            System.out.println("totalPathsOfSum using Brute Force approach:                                 " + totalPathsOfSum + ", number of subtrees traversed: " + countWithBruteForce);
        }

        {
            countWithTailRecursion = 0;
            int totalPathsOfSum = countPathsWithSum_TailRecursion(node10, 8);
            System.out.println("totalPathsOfSum Using Brute Force approach and Tail-Recursion:              " + totalPathsOfSum + ", number of subtrees traversed: " + countWithTailRecursion);
        }
        {
            countWithBetterApproach = 0;
            int totalPathsOfSum = countPathsWithSum_Improved(node10, 8);
            System.out.println("totalPathsOfSum with Performance Improved approach (not using Brute Force): " + totalPathsOfSum + ", number of subtrees traversed: " + countWithBetterApproach);
        }
        {
            countWithMemoization = 0;
            int totalPathsOfSum = countPathsWithSum_Memoization(node10, 8, new HashMap<>());
            System.out.println("totalPathsOfSum with Performance Improved approach using Memoization:       " + totalPathsOfSum + ", number of subtrees traversed: " + countWithMemoization);
        }

    }

    /*
    Create separate output variables from root processing, left subtree processing and right subtree processing and then merge them as needed.
    Don’t reserve a chance of using the same output variable between these 3 processing.
    You may not see any problem for this algorithm, if you use the same output variable, but it may cause the problem in some very niche conditions for other algorithms.

    This algorithm is same as FindWhetherTeeIsBalanced.java.
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


    You can optimize it by avoiding Brute Force approach and traversing every node just once. See countPathsWithSum_Memoization and countPathsWithSum_Improved.
    It can be sometimes harder to think of a logic like countPathsWithSum_Improved method, but it is very easy to convert any Brute Force into better algorithm using Memoization like countPathsWithSum_Memoization method.


    This is the same problem as FindWhetherTreeIsBalanced.java
     */

    private static int countPathsWithSum_BruteForce(TreeNode root, int expectedSum) {
        if (root == null) return 0;

        int totalPathsFromRoot = countPathsWithSum(root, expectedSum, 0);// separate output variable for root processing
        countWithBruteForce++;

        int totalPathsFromLeft = countPathsWithSum_BruteForce(root.left, expectedSum);// separate output variable for root.left processing
        int totalPathsFromRight = countPathsWithSum_BruteForce(root.right, expectedSum);// separate output variable for root.right processing

        /* try to avoid using same output variable for root + root.left + root.right processing.
        You may not see any problem for this algorithm, if you use the same output variable, but it may cause the problem in some very niche conditions for other algorithms.
        int totalPathsFromRoot = countPathsWithSum(root, expectedSum, 0);
        totalPathsFromRoot += countPathsWithSum(root.left, expectedSum);
        totalPathsFromRoot += countPathsWithSum(root.right, expectedSum);
        return totalPathsFromRoot;
         */

        return totalPathsFromRoot + totalPathsFromLeft + totalPathsFromRight;
    }

    private static int countPathsWithSum_Memoization(TreeNode root, int expectedSum, Map<TreeNode, Integer> nodePathSum) {
        if (root == null) return 0;

        int totalPathsFromRoot = 0;
        if (nodePathSum.containsKey(root)) {
            totalPathsFromRoot = nodePathSum.get(root);
        } else {
            totalPathsFromRoot = countPathsWithSum_Memoization(root, expectedSum, 0, nodePathSum);
            nodePathSum.put(root, totalPathsFromRoot);
        }

        int totalPathsFromLeft = countPathsWithSum_BruteForce(root.left, expectedSum);
        int totalPathsFromRight = countPathsWithSum_BruteForce(root.right, expectedSum);

        return totalPathsFromRoot + totalPathsFromLeft + totalPathsFromRight;
    }

    /*
    Important:
    you started hardcoding currentSum (that is not the expected output of the method) to root.data. As soon as you saw that, you realized that it is shared between recursive method calls. So, it should be passed as method parameter.

    private static int countPathsWithSum(TreeNode root, int expectedSum) {
        if (root == null) return 0;

        int totalPaths = 0;
        currentSum = root.data;
        if (expectedSum == currentSum) {
            totalPaths++;
        }
        ...
    }
     */
    private static int countPathsWithSum(TreeNode root, int expectedSum, int currentSum) {
        if (root == null) return 0;

        countWithBruteForce++;
        countWithTailRecursion++;

        int totalPaths = 0;
        currentSum += root.data;// Important: you started hardcoding currentSum (that is not the expected output of the method) to root.data. As soon as you saw that, you realized that it is shared between recursive method calls. So, it should be passed as method parameter.
        if (expectedSum == currentSum) {
            totalPaths++;
        }

        int totalPathsFromLeft = countPathsWithSum(root.left, expectedSum, currentSum);
        int totalPathsFromRight = countPathsWithSum(root.right, expectedSum, currentSum);

        return totalPaths + totalPathsFromLeft + totalPathsFromRight;
    }

    private static int countPathsWithSum_Memoization(TreeNode root, int expectedSum, int currentSum, Map<TreeNode, Integer> nodPathSumMap) {
        if (root == null) return 0;

        countWithMemoization++;

        int totalPaths = 0;
        currentSum += root.data;// Important: you started hardcoding currentSum (that is not the expected output of the method) to root.data. As soon as you saw that, you realized that it is shared between recursive method calls. So, it should be passed as method parameter.
        if (expectedSum == currentSum) {
            totalPaths++;
        }

        int totalPathsFromLeft = countPathsWithSum_Memoization(root.left, expectedSum, currentSum, nodPathSumMap);
        nodPathSumMap.put(root.left, totalPathsFromLeft);
        int totalPathsFromRight = countPathsWithSum_Memoization(root.right, expectedSum, currentSum, nodPathSumMap);
        nodPathSumMap.put(root.right, totalPathsFromRight);

        return totalPaths + totalPathsFromLeft + totalPathsFromRight;
    }

    private static int countPathsWithSum_TailRecursion(TreeNode root, int expectedSum) {
        if (root == null) return 0;
        //int totalPathsOfRoot = countPathsWithSum(root, expectedSum, 0);
        //or
        int totalPathsOfRoot = 0;
        if (expectedSum == root.data) {
            totalPathsOfRoot++;
        }
        return countPathsWithSum_TailRecursion(root, expectedSum, totalPathsOfRoot);
    }

    // Tail-Recursion:
    // Converted countPathsWithSum_ into Tail-Recursion
    // you need to think the starting result. Here starting result can be the result of root. That would be your extra argument (totalPathsFromRoot)
    // then think the result from left and right of the root with that of root.
    private static int countPathsWithSum_TailRecursion(TreeNode root, int expectedSum, int totalPathsFromRoot) {
        if (root == null) return totalPathsFromRoot;

        int totalPathsFromLeft = countPathsWithSum(root.left, expectedSum, 0);
        int totalPathsFromRight = countPathsWithSum(root.right, expectedSum, 0);
        int totalPaths = totalPathsFromRoot + totalPathsFromLeft + totalPathsFromRight;

        countPathsWithSum_TailRecursion(root.left, expectedSum, totalPaths);
        return countPathsWithSum_TailRecursion(root.right, expectedSum, totalPaths);
    }

    /*
    Improved version of previous Brute Force based algorithm.
    It is complex to understand. Runtime complexity is O(n). Space complexity is O(n) due to hash table and O(log n) due to stack used for recursion.

    Read the explanation from Cracking Coding Interview book page 274
    */
    private static int countPathsWithSum_Improved(TreeNode root, int expectedSum) {
        if (root == null) return 0;

        Map<Integer, Integer> pathCount = new HashMap<>();
        incrementHashTable(pathCount, 0, 1); //Needed if target path starts at root
        return countPathsWithSum_Improved(root, expectedSum, 0, pathCount);
    }

    private static int countPathsWithSum_Improved(TreeNode root, int expectedSum, int runningSum, Map<Integer, Integer> pathCount) {
        if (root == null) return 0;

        countWithBetterApproach++;

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
        if (!hashTable.containsKey(key)) {
            hashTable.put(key, 0);
        }
        hashTable.put(key, hashTable.get(key) + delta);
    }

}
