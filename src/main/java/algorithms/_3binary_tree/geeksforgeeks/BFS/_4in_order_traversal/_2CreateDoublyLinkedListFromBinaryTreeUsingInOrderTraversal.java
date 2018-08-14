package algorithms._3binary_tree.geeksforgeeks.BFS._4in_order_traversal;

import algorithms.crackingcodinginterviewbook._4tree_and_graph.tree.baseclasses.TreeNode;
import algorithms.utils.TreeUtils;

/*
    Convert a given Binary Tree to Doubly Linked List | Set 1

    https://www.geeksforgeeks.org/in-place-convert-a-given-binary-tree-to-doubly-linked-list/


    Solution:

        DllNode create(TreeNode root) {

            dllHeadFromLeft = create DoublyLinkedList from left subtree

            dllNodeFromRoot = new DllNode(root.data); // dll node from root

            headOfFinalDll = dllNodeFromRoot;// setting head of final DLL to a DLL node created from root

            if(dllHeadFromLeft != null) {
                   headOfFinalDll = dllHeadFromLeft;// setting head of final DLL to head of DLL created from left subtree

                   iterate entire dll created from left subtree and go to last node
                   and attach dllNode created from root to it
            }

            dllHeadFromRight = create DoublyLinkedList from right subtree

            attach DLL created from right subtree to Dll node created from root

        }

    This solution may take O(n^2)



    There are some other methods also that might take O(n), but I am not able to understand them.

    https://www.geeksforgeeks.org/convert-a-given-binary-tree-to-doubly-linked-list-set-2/
    https://www.geeksforgeeks.org/convert-given-binary-tree-doubly-linked-list-set-3/
    https://www.geeksforgeeks.org/convert-a-given-binary-tree-to-doubly-linked-list-set-4/

*/
public class _2CreateDoublyLinkedListFromBinaryTreeUsingInOrderTraversal {

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

        DllNode dllHead = createDll(ten);
        DllNode runner = dllHead;// 3 8 5 10 2
        while (runner != null) {
            System.out.println(runner + ": prev :" + runner.prev + ": next :" + runner.next);
            runner = runner.next;
        }
    }

    private static DllNode createDll(TreeNode root) {
        if (root == null) return null;

        DllNode dllHeadFromLeftSubTree = createDll(root.left);

        DllNode dllNodeFromRoot = new DllNode(root.data);

        DllNode head = dllNodeFromRoot;
        //DllNode lastNode = dllNodeFromRoot;

        if (dllHeadFromLeftSubTree != null) {

            head = dllHeadFromLeftSubTree;

            DllNode runner = head;
            while (runner.next != null) {
                runner = runner.next;
            }

            runner.next = dllNodeFromRoot;
            dllNodeFromRoot.prev = runner;

        }

        DllNode dllHeadFromRightSubTree = createDll(root.right);

        if (dllHeadFromRightSubTree != null) {
            dllNodeFromRoot.next = dllHeadFromRightSubTree;
            dllHeadFromRightSubTree.prev = dllNodeFromRoot;
        }


        return head;
    }

    static class DllNode {
        public int data;
        public DllNode next;
        public DllNode prev;

        public DllNode(int data) {
            this.data = data;
        }

        public void setNext(DllNode next) {
            this.next = next;
        }

        public void setPrev(DllNode prev) {
            this.prev = prev;
        }

        @Override
        public String toString() {
            return "" + data;
        }
    }
}
