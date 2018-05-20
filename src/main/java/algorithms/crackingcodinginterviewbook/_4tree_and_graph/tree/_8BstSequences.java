package algorithms.crackingcodinginterviewbook._4tree_and_graph.tree;

import algorithms.crackingcodinginterviewbook._4tree_and_graph.tree.baseclasses.BST;
import algorithms.crackingcodinginterviewbook._4tree_and_graph.tree.baseclasses.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static algorithms.utils.TreeUtils.printPreety;


// http://wdxtub.com/interview/14520607214254.html
/*
BST Sequences:
A binary search tree was created by traversing through an array from left to right and inserting each elememtn.
Given a binary search tree with distinct elements, print all possible arrays that could have led to this tree.

e.g.
    2
  1    3

  Output: {2,1,3}, {2,3,1}


I could not understand the weaving logic.
 */
public class _8BstSequences {

    public static void main(String[] args) {
        BST bst = BST.createBST();
        printPreety(bst.root);

        List<LinkedList<Integer>> linkedLists = allSequences(bst.root);
        System.out.println(linkedLists);
    }

    private static List<LinkedList<Integer>> allSequences(TreeNode node) {
        List<LinkedList<Integer>> result = new ArrayList<>();

        if (node == null){
            result.add(new LinkedList<>());
            return result;
        }

        LinkedList<Integer> prefix = new LinkedList<Integer>();
        prefix.add(node.data);

        List<LinkedList<Integer>> leftSeq = allSequences(node.left);
        List<LinkedList<Integer>> rightSeq = allSequences(node.right);

        // weave
        for (LinkedList<Integer> left : leftSeq) {
            for (LinkedList<Integer> right : rightSeq) {
                ArrayList<LinkedList<Integer>> weaved = new ArrayList<LinkedList<Integer>>();
                weaveLists(left, right, weaved, prefix);
                result.addAll(weaved);
            }
        }
        return result;
    }

    private static void weaveLists(LinkedList<Integer> first, LinkedList<Integer> second,
                    ArrayList<LinkedList<Integer>> results, LinkedList<Integer> prefix) {
        // one list is empty. Add remainder to [a cloned] prefix and store result.
        if (first.size() == 0 || second.size() == 0){
            LinkedList<Integer> result = (LinkedList<Integer>) prefix.clone();
            result.addAll(first);
            result.addAll(second);
            results.add(result);
            return ;
        }

        // Recurse with head of first added to the prefix. Removing the head will
        // damage first, so we'll need to put it back where we found it afterwards.
        int headFirst = first.removeFirst();
        prefix.addLast(headFirst);
        weaveLists(first, second, results, prefix);
        prefix.removeLast();
        first.addFirst(headFirst);

        // Do the same thing with second, damaging and then restoring the list
        int headSecond = second.removeFirst();
        prefix.addLast(headSecond);
        weaveLists(first, second, results, prefix);
        prefix.removeLast();
        second.addFirst(headSecond);
    }
}
