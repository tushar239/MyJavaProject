package algorithms.crackingcodinginterviewbook._4tree_and_graph.tree;

import algorithms.crackingcodinginterviewbook._4tree_and_graph.tree.baseclasses.BST;
import algorithms.crackingcodinginterviewbook._4tree_and_graph.tree.baseclasses.TreeNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static algorithms.utils.TreeUtils.printPreety;

/*
        Tree Structure

                5
       4                6
   3        2       1       0

   Expected output:
   Resulting linked lists
       5
       4,6
       3,2,1,0

 */
public class _2CreateLinkedListForEachLevelOfBinaryTree {

    public static void main(String[] args) {
        BST bst = BST.createBST();
        printPreety(bst.root);

        {
            Map<Integer, List<Integer>> levelOrderedLists = levelOrdering(bst.root, 0);
            for (Integer level : levelOrderedLists.keySet()) {
                System.out.println("level: " + level + ", list: " + levelOrderedLists.get(level));
            }
        }

        System.out.println();
        System.out.println("Improved algorithm.....");
        {
            HashMap<Integer, List<Integer>> levelOrderedLists = new HashMap<>();
            levelOrdering_Improved(bst.root, 0, levelOrderedLists);
            for (Integer level : levelOrderedLists.keySet()) {
                System.out.println("level: " + level + ", list: " + levelOrderedLists.get(level));
            }
        }
    }


    /*
    This algorithm works perfectly fine because your thought process was recursing left and right subtrees and then merging their results with root's result.

    Important:

    You started with

    private static Map<Integer, List<Integer>> levelOrdering(TreeNode root) {
        if(root == null) return null;

        Map<Integer, List<Integer>> rootResult = new HashMap<>();
        List<Integer> list = new ArrayList<>();
        list.add(root.data);
        rootResult.put(0, list); --- root_to_leaf_problems_hard coding level
        ...
    }

    you realized that you are root_to_leaf_problems_hard coding something other than actual return value (Map<Level, List<Integer>>) and that is level=0 for root processing.
    As soon as you saw this, you thought that level is shared between recursive calls. For each recursive calls level should be increased.
    So, you made level as input parameter of this recursive method.

    This algorithm works perfectly fine and it is from the correct thought process of how recursive calls should be coded.

    You can make it a bit better though.
    If you pass Map<Level, List<Integer>> also as a method parameter, you will not need to instantiate a new Map for each recursive call.
    When you see that you will end up merging root processing's result (like map,list etc) with left and right results, replace that merging process by passing that Map or List as a method parameter.
    see 'levelOrdering_Improved' method.
     */
    private static Map<Integer, List<Integer>> levelOrdering(TreeNode root, int level) {
        if (root == null) return null;

        Map<Integer, List<Integer>> rootResult = new HashMap<>();
        List<Integer> list = new ArrayList<>();
        list.add(root.data);
        rootResult.put(level, list);// as you see, I don't like to root_to_leaf_problems_hard code any value for root processing other than actual value that needs to be returned. this creates error prone algorithm. pass level as input parameter.

        Map<Integer, List<Integer>> leftSubTreeResult = levelOrdering(root.left, level + 1);
        Map<Integer, List<Integer>> rightSubTreeResult = levelOrdering(root.right, level + 1);

        if (leftSubTreeResult != null) {
            rootResult.putAll(leftSubTreeResult);
        }
        if (rightSubTreeResult != null) {
            for (Integer levelKey : rightSubTreeResult.keySet()) {
                if (rootResult.containsKey(levelKey)) {
                    rootResult.get(levelKey).addAll(rightSubTreeResult.get(levelKey));
                } else {
                    rootResult.put(levelKey, rightSubTreeResult.get(levelKey));
                }
            }
        }

        return rootResult;
    }


    private static void levelOrdering_Improved(TreeNode root, int level, Map<Integer, List<Integer>> result) {
        if (root == null) return;

        if (result.containsKey(level)) {
            result.get(level).add(root.data);
        } else {
            List<Integer> list = new ArrayList<>();
            list.add(root.data);
            result.put(level, list);
        }

        levelOrdering_Improved(root.left, level + 1, result);
        levelOrdering_Improved(root.right, level + 1, result);

    }

}
