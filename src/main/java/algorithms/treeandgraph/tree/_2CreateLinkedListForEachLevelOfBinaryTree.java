package algorithms.treeandgraph.tree;

import algorithms._2linkedlistmanipulation.Node;
import algorithms._2linkedlistmanipulation.SinglyLinkedList;
import algorithms.treeandgraph.tree.baseclasses.BST;
import algorithms.treeandgraph.tree.baseclasses.TreeNode;

import java.util.ArrayList;
import java.util.List;

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
        bst.printPreety();

        List<SinglyLinkedList> lists = new ArrayList<>();

        createLinkedListAnotherWay(0, bst.root, lists);

        for (SinglyLinkedList list : lists) {
            System.out.println("linkedlist:");
            list.traverse();
            System.out.println();
        }
    }

    /*
    In this algorithm, instead of thinking of creating a linkedlist at level 0, to minimize a problem by one (for recursive method), you can think to create linkedlist at level 1.
    Here, as per other traditional recursive algorithms, you dont have to deleteRootAndMergeItsLeftAndRight the resulting linkedlist of level 1 with level 0. So, DO NOT WAIT for creating level 1 linkedlist to create level 0 linkedlist.
    Use Pre-order traversal

    Means do not do following (Do not use post-order traversal)

        if(root == null) {
            return null;
        }

        Node left = createLinkedListOfIntegers(level + 1, root.left, lists);
        Node right = createLinkedListOfIntegers(level + 1, root.right, lists);


        SinglyLinkedList singlyLinkedList = null;

        if(lists.size() == level) {
            singlyLinkedList = new SinglyLinkedList();
            lists.add(singlyLinkedList) ;
        } else {
            singlyLinkedList = lists.get(level);
        }

        singlyLinkedList.addToTail(root.data);

        return singlyLinkedList.head;

     */
    private static Node createLinkedList(int level, final TreeNode root, List<SinglyLinkedList> lists) {

        if (root == null) {
            return null;
        }

        SinglyLinkedList singlyLinkedList = null;

        if (lists.size() == level) {
            singlyLinkedList = new SinglyLinkedList();
            lists.add(singlyLinkedList);
        } else {
            singlyLinkedList = lists.get(level);
        }

        singlyLinkedList.addToTail(root.data);

        Node left = createLinkedList(level + 1, root.left, lists); // As parent node is not merged with the result by traversing left and right children, there is no need to return anything
        Node right = createLinkedList(level + 1, root.right, lists);

        return singlyLinkedList.head;
    }

    /*
        Merge each low level linkedlist with its parent level linkedlist

        5,4,3,2,6,1,0
        4,3,2,6,1,0
        3,2,1,0

        This is actually pre-order traversing. If you need level-order traversing, you need to use different algorithm.
     */
    private static SinglyLinkedList createLinkedListAnotherWay(int level, final TreeNode root, List<SinglyLinkedList> lists) {

        if (root == null) {
            return null;
        }


        SinglyLinkedList singlyLinkedList = null;

        if (lists.size() == level) {
            singlyLinkedList = new SinglyLinkedList();
            lists.add(singlyLinkedList);
        } else {
            singlyLinkedList = lists.get(level);
        }

        singlyLinkedList.addToTail(root.data);

        for (int i = 0; i <= level - 1; i++) {
            lists.get(i).addToTail(root.data);
        }


        int nextLevel = level + 1;
        createLinkedListAnotherWay(nextLevel, root.left, lists);
        createLinkedListAnotherWay(nextLevel, root.right, lists);
        // same as
//        createLinkedListAnotherWay(++level, root.left, lists);
//        createLinkedListAnotherWay(level, root.right, lists);
//        System.out.println(level);

        return singlyLinkedList;
    }

}
