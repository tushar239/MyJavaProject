package algorithms.crackingcodinginterviewbook._4tree_and_graph.tree.baseclasses;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class BST {
    public TreeNode root;

    public void traverse() {
        preOrderTraversal(root);
    }

    /*private void traverse(TreeNode node) {
        System.out.println("data:" + node.getData());
        if (node.hasLeft()) {
            traverse(node.getLeft());
        }
        if (node.hasRight()) {
            traverse(node.getRight());
        }
    }*/

    // put iteratively
    public void putIteratively(Integer value) {
        TreeNode newNode = new TreeNode(value);
        if (root == null) {
            root = newNode;
        } else {
            TreeNode parentNode = root;

            while (true) {
                if (newNode.data.compareTo(parentNode.data) < 0) {
                    if (parentNode.hasLeft()) {
                        parentNode = parentNode.left;
                    } else {
                        parentNode.setLeft(newNode);
                        break;
                    }

                } else if (newNode.data.compareTo(parentNode.data) > 0) {
                    if (parentNode.hasRight()) {
                        parentNode = parentNode.right;
                    } else {
                        parentNode.setRight(newNode);
                        break;
                    }
                } else {
                    break;
                }
            }

        }

    }

    // put recursively
    /*public void put(Integer value) {
        put(root, new TreeNode(value));
    }*/

    /*
        If newNode to insert has value < parent node value then
            if(parentNode.left == null) parentNode.left = newNode;
            else put(parentNode.left, newNode)

        else if newNode to insert has value > parent node value then
            if(parentNode.right == null) parentNode.right = newNode;
            else put(parentNode.right, newNode)


    */

    // put is done recursively
    /*public TreeNode put(TreeNode parentNode, TreeNode newNode) {
        if (parentNode == null) {
            root = newNode;
            return root;
        }

        if (newNode.getData().compareTo(parentNode.getData()) < 0) {
            if (parentNode.hasLeft()) {
                put(parentNode.getLeft(), newNode);
            } else {
                parentNode.setLeft(newNode);
            }

        } else if (newNode.getData().compareTo(parentNode.getData()) > 0) {
            if (parentNode.hasRight()) {
                put(parentNode.getRight(), newNode);
            } else {
                parentNode.setRight(newNode);
            }
        } else {
            parentNode.setData(newNode.getData());
        }
        return newNode;
    }
*/

    public void put(Integer data) {
        root = put(root, data);
    }

    /*    public TreeNode put(TreeNode root, TreeNode newTreeNode) {
            if (root == null) {
                return newTreeNode;
            }

            if (newTreeNode.getData().compareTo(root.getData()) < 0) {
                TreeNode leftNode = put(root.getLeft(), newTreeNode);
                root.left = leftNode;

            } else if (newTreeNode.getData().compareTo(root.getData()) > 0) {
                TreeNode rightNode = put(root.getRight(), newTreeNode);
                root.right = rightNode;
            }

            return root;
        }*/
    public static TreeNode put(TreeNode root, Integer data) {
        if (root == null) {
            return new TreeNode(data);
        }

        if (data.compareTo(root.getData()) < 0) {
            TreeNode newTreeNode = put(root.getLeft(), data);  // reducing problem by 1
            root.left = newTreeNode; // connecting the result of 'reducing problem by 1' with root node

        } else if (data.compareTo(root.getData()) > 0) {
            TreeNode newTreeNode = put(root.getRight(), data);  // reducing problem by 1
            root.right = newTreeNode; // connecting the result of 'reducing problem by 1' with root node
        } else {
            root.setData(data);
        }
        return root;
    }

    public TreeNode get(Integer data) {
        return get(root, data);
    }

    /*
    This will not work

    private TreeNode get(TreeNode node, Integer data) {

        if (data == null || node == null) { // exit cond 1
            return null;
        }

        if (node.data.compareTo(data) == 0) { exit cond 2
            return node;
        }

        node = get(node.left, data); // recursion 1
        node = get(node.right, data); // recursion 2

        return node;
    }

    In this case, all get method for all left nodes will be pushed to stack until match is found.
    As soon as match is found, exit cond 2 will return that left node and while popping that get(node.left,data) method, it will assign a value to foundNode.
    Now, here it should stop and should not go and push get(node.right,data) to stack, otherwise everything will mess up.

    So, when you need that if match is found on one side of the node, then it should not go to other side, then use another variable 'foundNode' to store the result of get(...) method.


    I like getDifferentWay_2 approach better.
    */
    private static TreeNode get(TreeNode node, Integer data) {
        TreeNode foundNode = null;

        if (data == null || node == null) { // exit condition 1
            return null;
        }

        if (node.data.compareTo(data) == 0) { // exit condition 2
            foundNode = node;
        }

        if (foundNode == null) {
            foundNode = get(node.left, data);
        }
        if (foundNode == null) {
            foundNode = get(node.right, data);
        }

        return foundNode;
    }

    public TreeNode getDifferentWay(Integer data) {
        return getDifferentWay(root, data);
    }

    private TreeNode getDifferentWay(TreeNode node, Integer data) {
        TreeNode foundNode = null;

        if (data == null || node == null) { // exit condition
            return null;
        }

        if (node.data.compareTo(data) == 0) { // exit condition
            foundNode = node;
        }

        if (foundNode == null) {
            foundNode = getDifferentWay(node.left, data);
            if (foundNode == null) {
                foundNode = getDifferentWay(node.right, data);
            }
        }
        return foundNode;
    }

    public TreeNode getDifferentWay_2(Integer data) {
        return getDifferentWay_2(root, data);
    }

    private TreeNode getDifferentWay_2(TreeNode node, Integer data) {

        if (data == null || node == null) { // exit condition
            return null;
        }

        if (node.data.compareTo(data) == 0) { // exit condition
            return node;
        }

        TreeNode foundNode = getDifferentWay_2(node.left, data);

        if (foundNode == null) {
            foundNode = getDifferentWay_2(node.right, data);
        }

        return foundNode;
    }

    // Watch https://www.youtube.com/watch?v=gcULXE7ViZw
    public TreeNode deleteNode(Integer data) {
        return deleteNode(root, data);
    }

    /*
        As mentioned in
         http://www.algolist.net/Data_structures/Binary_search_tree/Removal
         https://www.youtube.com/watch?v=gcULXE7ViZw,

        if node toBeDeleted is found in a tree, then
            there are three possibilities for a node that needs to be deleted
            - node is a leaf node           --- just de-link this node from its parent
            - node has only one child node  --- child node becomes toBeDeleted node's parent node's left/right child. In this way, toBeDeleted node will be de-linked.
            - node has two children nodes   --- find a minValueNode from right subtree of a toBeDeleted node.
                                                set the value of toBeDeleted node as this min value.
                                                delete that minValueNode.

            first two cases are easy to handle, third one is a bit complicated.


     */
    private TreeNode deleteNode(TreeNode root, Integer data) {
        if (root == null) return root; // exit condition

        if (data.compareTo(root.data) == 0) { // exit condition
            return deleteRootAndMergeItsLeftAndRight(root);
        }
        if (data.compareTo(root.data) < 0) {
            return deleteNode(root.left, data); // returned result will be same as one of the exit condition's result. If you see, in deleteRootAndMergeItsLeftAndRight method, we already merging the changes with root (passed node). So, here we don't have to merge the result of recursive method with the root.
        } else {
            return deleteNode(root.right, data); // returned result will be same as one of the exit condition's result. If you see, in deleteRootAndMergeItsLeftAndRight method, we already merging the changes with root (passed node). So, here we don't have to merge the result of recursive method with the root.
        }

    }

    // If you see FunctionalProgrammingInJava's Tree.java,
    // we don't modify the current tree. On every modification, we create a new tree.
    // Advantage of that is recursive method's logic becomes very simple. You don't have to think about all these if-elseif conditions that we have implemented here in this method and also you don't need to remember parent.
    private TreeNode deleteRootAndMergeItsLeftAndRight(TreeNode root) {
        if (root.isLeafNode()) { // has no children   - just de-link this node from its parent
            if (root.hasParent()) {
                if (root.amILeftChildOfMyParent()) {
                    root.getParent().setLeft(null);
                } else {
                    root.getParent().setRight(null);
                }
            }
        } else if (root.hasOnlyOneChild()) { // child node becomes toBeDeleted node's parent node's left/right child. In this way, toBeDeleted node will be de-linked.
            if (root.getLeft() != null) {
                root.parent.setLeft(root.getLeft());
            } else {
                root.parent.setRight(root.getRight());
            }
        } else {
            // find a minValueNode from right subtree of a toBeDeleted node.
            TreeNode minValueTreeNode = min(root.getRight());

            // set the value of toBeDeleted node as this min value.
            root.setData(minValueTreeNode.getData());

            // delete that minValueNode.
            deleteNode(minValueTreeNode, minValueTreeNode.getData());

        }
        return root;
    }

    public TreeNode min(TreeNode root) {
        if (root == null) return root;

        if (root.isLeafNode()) return root;

        if (!root.hasLeft()) return root;

        return min(root.getLeft());
    }

    public TreeNode max(TreeNode root) {
        if (root == null) return root;

        if (root.isLeafNode()) return root;

        if (!root.hasRight()) return root;

        return max(root.getRight());
    }

    // http://www.geeksforgeeks.org/merge-two-balanced-binary-search-trees/
    // traverse both BSTs in-order and create two arrays
    // merge both arrays in a new array in such a way that new array is a sorted (in-order) array
    // use CreateMinimalBST approach to create a new tree out of new sorted array
    public static BST merge(BST bst1, BST bst2) {
        return null;
    }

    /*
        It's better to do Binary Tree traversals recursively instead of Iteratively.

        e.g. If you try to do tree traversal iteratively, you literally need to use stack

        So, whenever you need to traverse both left and right sides of the tree, it is better to use recursive approach.
     */
    public void preOrderTraversal(TreeNode treeNode) {
        if (treeNode == null) return; // exit condition

        visitTreeNode(treeNode); // visit current node
        preOrderTraversal(treeNode.left); // go left
        preOrderTraversal(treeNode.right); // go right

        /*
        In reality,
        traversal order depends on the order of combining the results

        public String traversal(TreeNode treeNode) {
            if (treeNode == null) return; // exit condition

            String rootResult = visitTreeNode(treeNode); // visit current node
            String leftSubTreeResult = preOrderTraversal(treeNode.left); // go left
            String rightSubTreeResult = preOrderTraversal(treeNode.right); // go right

            return rootResult + leftSubTreeResult + rightSubTreeResult // Pre-Order Traversal
            //return leftSubTreeResult + rootResult + rightSubTreeResult // In-Order Traversal
            //return leftSubTreeResult + rightSubTreeResult + rootResult + // Post-Order Traversal

        }
        private String visitTreeNode(TreeNode treeNode) {
            return "\t" + treeNode.data + ":" + treeNode.color + ", ";
        }


        */
    }

    public void preOrderTraversalConsideringNull(TreeNode treeNode, List<Integer> list) {
        if (treeNode == null) {
            list.add(null);
            return;
        }
        list.add(treeNode.data);
        preOrderTraversalConsideringNull(treeNode.left, list);
        preOrderTraversalConsideringNull(treeNode.right, list);
    }

    /*
       Post-Order Traversal
       If change in children node makes a difference to parent node, then use post-order traversal.

       Best example of Pre and Post Order Traversals are FindMaximumSumInPathOfBinaryTree.java
    */
    public void postOrderTraversal(TreeNode treeNode) {
        if (treeNode == null) return; // exit condition 1

        // exit condition 2,3...

        // If you put any one or more exit condition here, then it will stop traversing a child tree (left and right) further. it will set the returned value to parent recursive call.
        // In this case it is possible that one of the left or right child tree traversal result can be null. you need to consider that scenario. e.g. FindLowestCommonAncestorInBinaryTree.java

        postOrderTraversal(treeNode.left); // go left --- not same as "treeNode = treeNode.left; postOrderTraversal(treeNode)"
        postOrderTraversal(treeNode.right); // go right --- not same as "treeNode = treeNode.right; postOrderTraversal(treeNode)"
        visitTreeNode(treeNode); // visit current node
    }

    public void postOrderTraversalConsideringNull(TreeNode treeNode, List<Integer> list) {
        if (treeNode == null) {
            list.add(null);
            return;
        }
        postOrderTraversalConsideringNull(treeNode.left, list);
        postOrderTraversalConsideringNull(treeNode.right, list);
        list.add(treeNode.data);
    }

    /*
    In-Order Traversal
    If a tree is BST (symmetric tree), then in-order traversal visits nodes in ascending order.
    This method of traversal is useful, if you want to convert a BST (symmetric binary tree) in an array of elements with ascending order (traverse BST in ascending order)
     */
    public void inOrderTraversal(TreeNode treeNode) {
        if (treeNode == null) return; // exit condition

        inOrderTraversal(treeNode.left); // go left
        visitTreeNode(treeNode); // visit current node
        inOrderTraversal(treeNode.right); // go right
    }

    public void inOrderTraversalConsideringNull(TreeNode treeNode, List<Integer> list) {
        if (treeNode == null) {
            list.add(null);
            return;
        }
        inOrderTraversalConsideringNull(treeNode.left, list);
        list.add(treeNode.data);
        inOrderTraversalConsideringNull(treeNode.right, list);
    }

    private void visitTreeNode(TreeNode treeNode) {
        System.out.print("\t" + treeNode.data + ":" + treeNode.color + ", ");
    }

    /*
      Level Order Traversal OR Breath First Traversal(BFS) Iteratively

      http://www.java2blog.com/2014/07/binary-tree-level-order-traversal-in.html

      We will use Queue for Level Order traversal just like Breadth First Traversal in Graph.

            1) Create empty queue and push root node to it.
            2) Do the following when queue is not empty
                Pop a node from queue and examine it. if it is a node that you are looking for then return it, otherwise
                Push left child of popped node to queue if not null
                Push right child of popped node to queue if not null

                   15        - level 0
               7        11   - level 1
            5    8           - level 2

           O/P: 15 7 11 5 8  ----- order of traversal


     See LevelOrderAndVerticalOrderTraversal.java
     */
    public void levelOrderTraversalIteratively(TreeNode root) {
        if (root == null) return;

        Queue<TreeNode> queue = new LinkedBlockingQueue<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            TreeNode polledTreeNode = queue.poll();
            System.out.print("\t" + polledTreeNode.data + ", ");

            root = polledTreeNode;

            if (root.left != null) {
                queue.add(root.left);
            }
            if (root.right != null) {
                queue.add(root.right);
            }
        }
    }

    public void levelOrderTraversalRecursively(Queue<TreeNode> queue) {
        if (queue.isEmpty()) return;

        TreeNode poppedNode = queue.poll();

        if (poppedNode == null) return;

        System.out.print("\t" + poppedNode.data + ", ");


        TreeNode left = poppedNode.left;
        TreeNode right = poppedNode.right;

        if (left != null) queue.add(left);
        if (right != null) queue.add(right);

        levelOrderTraversalRecursively(queue);
    }

    /*
    Level Order Traversal OR Breath First Traversal(BFS) Recursively
    https://www.youtube.com/watch?v=86g8jAQug04
     */
    public void levelOrderTraversalRecursively(List<TreeNode> treeNodes, Queue<TreeNode> queue) {
        if (treeNodes == null || treeNodes.isEmpty()) return;

        for (TreeNode treeNode : treeNodes) {
            queue.add(treeNode);
        }


        final TreeNode poppedNode = queue.poll();
        System.out.print("\t" + poppedNode.data + ", ");

        levelOrderTraversalRecursively(new ArrayList<TreeNode>(2) {{
            if (poppedNode.left != null) {
                add(poppedNode.left);
            }
            if (poppedNode.right != null) {
                add(poppedNode.right);
            }
        }}, queue);

        if (!queue.isEmpty()) {
            System.out.print("\t" + queue.poll().data + ", ");
        }
    }

    /*
        Level Order Traversal OR Breath First Traversal(BFS) Recursively Another Way - I like this way because exit conditions are clearly mentioned at the beginning of the method.
                 This can be possible by putting root node in queue before coming to this method.
                 Same thinking is applied in Graph's Depth First Search and Breadth First Search.
    */
    public void levelOrderTraversalAnotherWay(List<TreeNode> treeNodes, Queue<TreeNode> queue) {
        if (treeNodes == null || treeNodes.isEmpty()) return;
        if (queue.isEmpty()) return;

        final TreeNode poppedNode = queue.poll();
        System.out.print("\t" + poppedNode.data + ", ");

        levelOrderTraversalRecursively(new ArrayList<TreeNode>(2) {{
            if (poppedNode.left != null) {
                add(poppedNode.left);
            }
            if (poppedNode.right != null) {
                add(poppedNode.right);
            }
        }}, queue);
    }

    public void inOrderTraversal(TreeNode treeNode, List<Integer> dataList) {
        if (treeNode == null || dataList == null) return; // exit condition

        inOrderTraversal(treeNode.left); // go left
        visitTreeNode(treeNode, dataList); // visit current node
        inOrderTraversal(treeNode.right); // go right

    }

    private void visitTreeNode(TreeNode treeNode, List<Integer> dataList) {
        dataList.add(treeNode.data);
    }


    /*
        Created BST (Symmetric tree)

                5

         3              9

      2     4        8     10

     */
    public static BST createBST() {
        BST bst = new BST();
        TreeNode level0Node = null;
        {
            level0Node = new TreeNode(5);
            level0Node.setIsRoot(true);
            bst.root = level0Node;
        }
        TreeNode level1LeftNode, level1RightNode = null;


        {
            level1LeftNode = new TreeNode(3);
            level1RightNode = new TreeNode(9);
            level0Node.left = level1LeftNode;
            level0Node.right = level1RightNode;
            level1LeftNode.setParent(level0Node);
            level1RightNode.setParent(level0Node);
        }

        TreeNode level2LeftNode1, level2RightNode1 = null;
        {
            level2LeftNode1 = new TreeNode(2);
            level2RightNode1 = new TreeNode(4);
            level1LeftNode.left = level2LeftNode1;
            level1LeftNode.right = level2RightNode1;
            level2LeftNode1.setParent(level1LeftNode);
            level2RightNode1.setParent(level1LeftNode);

        }

        TreeNode level2LeftNode2, level2RightNode2 = null;
        {
            level2LeftNode2 = new TreeNode(8);
            level2RightNode2 = new TreeNode(10);
            level1RightNode.left = level2LeftNode2;
            level1RightNode.right = level2RightNode2;
            level2LeftNode2.setParent(level1RightNode);
            level2RightNode2.setParent(level1RightNode);
        }
        return bst;
    }


    /*
        Created BST (Symmetric tree)

                5

         3              3

      2     4        2     4

     */
    public static BST createMirrorBST() {
        BST bst = new BST();
        TreeNode level0Node = null;
        {
            level0Node = new TreeNode(5);
            level0Node.setIsRoot(true);
            bst.root = level0Node;
        }
        TreeNode level1LeftNode, level1RightNode = null;


        {
            level1LeftNode = new TreeNode(3);
            level1RightNode = new TreeNode(3);
            level0Node.left = level1LeftNode;
            level0Node.right = level1RightNode;
            level1LeftNode.setParent(level0Node);
            level1RightNode.setParent(level0Node);
        }

        TreeNode level2LeftNode1, level2RightNode1 = null;
        {
            level2LeftNode1 = new TreeNode(2);
            level2RightNode1 = new TreeNode(4);
            level1LeftNode.left = level2LeftNode1;
            level1LeftNode.right = level2RightNode1;
            level2LeftNode1.setParent(level1LeftNode);
            level2RightNode1.setParent(level1LeftNode);

        }

        TreeNode level2LeftNode2, level2RightNode2 = null;
        {
            level2LeftNode2 = new TreeNode(2);
            level2RightNode2 = new TreeNode(4);
            level1RightNode.left = level2LeftNode2;
            level1RightNode.right = level2RightNode2;
            level2LeftNode2.setParent(level1RightNode);
            level2RightNode2.setParent(level1RightNode);
        }
        return bst;
    }

    /*
            Created Unbalanced BST

                    5

             3

          2     4

         */
    public static BST createUnBalancedBST() {
        BST bst = new BST();
        TreeNode level0Node = null;
        {
            level0Node = new TreeNode(5);
            level0Node.setIsRoot(true);
            bst.root = level0Node;
        }
        TreeNode level1LeftNode, level1RightNode = null;


        {
            level1LeftNode = new TreeNode(3);
            level0Node.left = level1LeftNode;
            level1LeftNode.setParent(level0Node);

        }

        TreeNode level2LeftNode1, level2RightNode1 = null;
        {
            level2LeftNode1 = new TreeNode(2);
            level2RightNode1 = new TreeNode(4);
            level1LeftNode.left = level2LeftNode1;
            level1LeftNode.right = level2RightNode1;
            level2LeftNode1.setParent(level1LeftNode);
            level2RightNode1.setParent(level1LeftNode);

        }


        return bst;
    }

    /*
          Created Unbalanced BST

                  5

           3            9

        2             7

       */
    public static BST createAnotherUnBalancedBST() {
        BST bst = new BST();
        TreeNode level0Node = null;
        {
            level0Node = new TreeNode(5);
            level0Node.setIsRoot(true);
            bst.root = level0Node;
        }
        TreeNode level1LeftNode, level1RightNode = null;


        {
            level1LeftNode = new TreeNode(3);
            level0Node.left = level1LeftNode;
            level1LeftNode.setParent(level0Node);

            level1RightNode = new TreeNode(9);
            level0Node.right = level1RightNode;
            level1RightNode.setParent(level0Node);

        }

        TreeNode level2LeftNode1, level2RightNode1 = null;
        {
            level2LeftNode1 = new TreeNode(2);
            level2RightNode1 = new TreeNode(7);
            level1LeftNode.left = level2LeftNode1;
            level1RightNode.left = level2RightNode1;
            level2LeftNode1.setParent(level1LeftNode);
            level2RightNode1.setParent(level1RightNode);

        }


        return bst;
    }


    /*
            Created Non-BST (Not Symmetric tree)

                    5

             4             7

          2     6        8     10

         */
    public static BST createNonBST() {
        BST bst = new BST();
        TreeNode level0Node = null;
        {
            level0Node = new TreeNode(5);
            level0Node.setIsRoot(true);
            bst.root = level0Node;
        }
        TreeNode level1LeftNode, level1RightNode = null;


        {
            level1LeftNode = new TreeNode(4);
            level1RightNode = new TreeNode(7);
            level0Node.left = level1LeftNode;
            level0Node.right = level1RightNode;
            level1LeftNode.setParent(level0Node);
            level1RightNode.setParent(level0Node);
        }

        TreeNode level2LeftNode1, level2RightNode1 = null;
        {
            level2LeftNode1 = new TreeNode(2);
            level2RightNode1 = new TreeNode(6);
            level1LeftNode.left = level2LeftNode1;
            level1LeftNode.right = level2RightNode1;
            level2LeftNode1.setParent(level1LeftNode);
            level2RightNode1.setParent(level1LeftNode);

        }

        TreeNode level2LeftNode2, level2RightNode2 = null;
        {
            level2LeftNode2 = new TreeNode(8);
            level2RightNode2 = new TreeNode(10);
            level1RightNode.left = level2LeftNode2;
            level1RightNode.right = level2RightNode2;
            level2LeftNode2.setParent(level1RightNode);
            level2RightNode2.setParent(level1RightNode);
        }
        return bst;
    }


    /*
         3

      2     4
    */
    public static BST createSubBST() {
        BST bst = new BST();
        TreeNode level0Node = null;
        {
            level0Node = new TreeNode(3);
            level0Node.setIsRoot(true);
            bst.root = level0Node;
        }
        TreeNode level1LeftNode, level1RightNode = null;


        {
            level1LeftNode = new TreeNode(2);
            level1RightNode = new TreeNode(4);
            level0Node.left = level1LeftNode;
            level0Node.right = level1RightNode;
            level1LeftNode.setParent(level0Node);
            level1RightNode.setParent(level0Node);
        }


        return bst;
    }

    public static BST createNonSubBST() {
        BST bst = new BST();
        TreeNode level0Node = null;
        {
            level0Node = new TreeNode(3);
            level0Node.setIsRoot(true);
            bst.root = level0Node;
        }
        TreeNode level1LeftNode, level1RightNode = null;


        {
            level1LeftNode = new TreeNode(5);
            level1RightNode = new TreeNode(4);
            level0Node.left = level1LeftNode;
            level0Node.right = level1RightNode;
            level1LeftNode.setParent(level0Node);
            level1RightNode.setParent(level0Node);
        }


        return bst;
    }


}
