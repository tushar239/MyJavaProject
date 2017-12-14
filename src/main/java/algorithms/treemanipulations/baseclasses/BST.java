package algorithms.treemanipulations.baseclasses;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/*
  It is very important to understand Recursion for Tree.
  It is very hard to work with a Tree using iterative approach. So, recursion works best with trees. That's why books introduce recursion from Trees onwards.
  With String, LinkedList, Stack & Queue, you can still use iterative approach easily, but with Trees it is hard. Later on in this document, you will see the reason.
 
  Recursion
  ---------
 
  is a concept of reduce the problem by 1.
  step 1 - write exit condition on root node
  step 2 - recurse the method by passing left node of root node. Merge the returned result of recursive method with root node, if not done in step 1 (e.g. In deleteNode method, merging is done in exit condition only, where as in put method, it is not done in exit condition, so it needs to be done after recursive call)
  step 3 - recurse the method by passing right node of root node. Merge the returned result of recursive method with root node, if not done in step 1 (e.g. In deleteNode method, merging is done in exit condition only, where as in put method, it is not done in exit condition, so it needs to be done after recursive call)
 
  Most probably, you will need to surround step 2 and 3 by some condition because if you don't then it will traverse both left and right subtrees even though it is not required. Sometimes, it can give wrong results also.
 
 
  Binary Tree
  -----------
  Tree is a binary tree, if node in a tree can have at the most 2 child nodes.
 
  class BinaryTree {
      Node root;
  }
 
  class Node {
      Node leftChild;
      Node rightChild;
  }
 
  10-ary tree can have at the max 10 children
 
  class Node {
      Node[] children
  }
 
  Height of the binary tree can be determined in logarithm by log2 n = log of n with base 2
  Height of the 10-ary tree can be determined in logarithm by log10 n = log of n with base 10
 
  Symmetric vs Complete vs Full vs Perfect Binary Trees
  -----------------------------------------------------
  Symmetric Binary Tree:
    e.g. BST
    Left subtree of any node contains smaller elements and right subtree contains bigger elements

                 9
          6              15
       4     7        13    17
                              21
                                25

     See, BST doesn't have to be a Complete/Perfect tree.

  Complete Binary Tree:
  (Almost Balanced Tree): e.g. BH (Binary Heap). BH is not a tree, but array can be worked on as binary tree.

      Every level of the tree is fully filled, except for perhaps the last level. To the extent that the last level is filled, it is filled left to right.

            Complete
                 o
              /     \
             o       o
           /   \    /
          o     o  o


          Not-Complete
                 o
              /     \
             o       o
           /   \      \
          o     o      o


  Perfect Binary Tree:
  (Totally Balanced)  e.g. RBT and AVL

                 o
              /     \
             o       o
           /   \    / \
          o     o  o   o

  Full Binary Tree:
    Every node has either 0 or 2 children

  BST, BH, RBT and AVL
  --------------------

  BST - Binary Search Tree - It is a SYMMETRIC Tree. Symmetric mean left tree contains smaller elements and right tree contains bigger elements compared to parent element.
                             It doesn't need to be a Balanced Tree.
                             If you try to create BST from ordered array without using intelligence, you will end up in totally unbalanced tree. Search time will be O(n).
                             You can use 'Create Minimal BST' algorithm to create somewhat balanced tree from an ordered array.
                             REMEMBER:
                               Binary Search vs Binary Search Tree
                               Binary Search is useful to search an element in ordered(sorted) array.
                               If you are given an unordered array, you should first create BST and try to search an element. It will have O(n log n) insertion time and O(log n) search time.

  BH - Binary Heap - It is used by Heap Sort and Heap Sort is used by Priority Queue.
                     It is not a tree, but it's an array that is treated like a tree by keeping track of indices of parent-child elements in an array.
                     It is ALMOST BALANCED (COMPLETE) Tree.

  RBT - Red-Black Tree - It is a PERFECT (TOTALLY BALANCED) tree.
  AVL - Adelson, Velski & Landis Tree - It is also a PERFECT Tree


 
  NOTE: Perfect trees are rare in interviews and in real life, as a perfect tree must have 2^K - 1 nodes (where k is a number of levels).
        In an interview, don't assume that binary tree is perfect.
 
        perfect binary tree's height is always log2 n.
        log2 n = k
        2^k = n
 
        number of nodes (n) ~= 2^k. To be specific, it is 2^k - 1.
 
        You can make a binary tree perfect with any number of nodes using some complex algorithms like RBT.
 */

/*
Next thing to read:
  BinaryHeap.java
 */

/*
    Traversal of Binary Tree
    ------------------------

    Any Binary Tree (not just Binary Search Tree) can be traversed 4 ways
    See 'Binary Tree Traversal Types.doc'



    https://www.youtube.com/watch?v=9RHO6jU--GU

    Breadth-First Traversal (BFS)
        Level-Order Traversal (https://www.youtube.com/watch?v=86g8jAQug04, http://www.java2blog.com/2014/07/binary-tree-level-order-traversal-in.html)
    Depth-First Traversal (DFS) (https://www.youtube.com/watch?v=gm8DUJJhmY4)
        DFS traverses the tree while giving priority to the depth(height) of the tree. It is called Depth-First instead of more logical 'Height-First'.
        Pre-Order Traversal
        In-Order Traversal
        Post-Order Traversal


    When to use Pre-Order, In-order or Post-Order?
    https://stackoverflow.com/questions/9456937/when-to-use-preorder-postorder-and-inorder-binary-search-tree-traversal-strate


        In-Order traversal
            If you know that the tree has an inherent sequence in the nodes, and you want to flatten the tree back into its original sequence, than an in-order traversal should be used. The tree would be flattened in the same way it was created. A pre-order or post-order traversal might not unwind the tree back into the sequence which was used to create it.

            If In-Order traversal is applied to BST (symmetric tree), it visits nodes in ascending order.
            'Create Minimal BST' algorithm is used to create BST from a sorted array.
            In-Order traversal is used to convert BST back to sorted array.

        Pre-Order traversal
            If you know you need to explore the roots before inspecting any leaves, you pick pre-order because you will encounter all the roots before all of the leaves.
            e.g. ColorNodes.java

            In other words: If change in parent node makes a difference in children nodes, then use pre-order traversal

        Post-Order traversal;
            If you know you need to explore all the leaves before any nodes, you select post-order because you don't waste any time inspecting roots in search for leaves.
            e.g. FindMaximumSumInPathOfBinaryTree.java


    Why to use Recursive approach instead of Iterative approach for traversing a tree?

        It's better to do Binary Tree traversals recursively instead of Iteratively.

        e.g. If you try to do tree traversal iteratively, you literally need to use stack

        So, whenever you need to traverse both left and right sides of the tree, it is better to use recursive approach.


        PreOrder traversal iteratively:
        http://www.geeksforgeeks.org/iterative-preorder-traversal/

            void iterativePreorder(Node node) {

                    // Base Case
                    if (node == null) {
                        return;
                    }

                    // Create an empty stack and push root to it
                    Stack<Node> stack = new Stack<Node>();
                    stack.push(root);

                    Pop all items one by one. Do following for every popped item
                     a) print it
                     b) push its right child
                     c) push its left child
                     Note that right child is pushed first so that left is processed first

                    while (stack.empty() == false) {

                    // Pop the top item from stack and print it
                    Node mynode = stack.peek();
                    System.out.print(mynode.data + " ");
                    stack.pop();

                    // Push right and left children of the popped node to stack
                    if (mynode.right != null) {
                        stack.push(mynode.right);
                    }
                    if (mynode.left != null) {
                        stack.push(mynode.left);
                    }
                }
            }

    PostOrder traversal iteratively:
    http://www.geeksforgeeks.org/iterative-postorder-traversal-using-stack/

    InOrder traversal iteratively:
    http://www.geeksforgeeks.org/inorder-tree-traversal-without-recursion/



    Very Important concept-

        http://www.geeksforgeeks.org/if-you-are-given-two-traversal-sequences-can-you-construct-the-binary-tree/

        Following combination can uniquely identify a tree.

        InOrder and PreOrder.
        InOrder and PostOrder.
        InOrder and LevelOrder.

        And following do not.
        Postorder and Preorder.
        Preorder and Levelorder.
        Postorder and Levelorder.



    http://stackoverflow.com/questions/14287980/checking-subtrees-using-preorder-and-inorder-strings

    If it is a binary search tree, either only preorder or only postorder can make it unique, while only inorder can not. For example:

      5
     / \
    3   6

    inorder: 3-5-6 however, another binary search tree can have the same inorder:

    3
     \
      5
       \
        6


    Another example
    http://stackoverflow.com/questions/16381294/determine-if-a-binary-tree-is-subtree-of-another-binary-tree-using-pre-order-and

             26
            /   \
          10     3
        /    \     \
      4      6      3
       \
        30
    and the candidate subtrees are

    10
    / \
    4 6
    \
    30
    and

    30
    / \
    4 10
    \
    6
    have the same inorder traversal as 4,30,10,6 but the second one isn't subtree
 */

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
    public TreeNode put(TreeNode root, Integer data) {
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

    */
    private TreeNode get(TreeNode node, Integer data) {
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
        return get(root, data);
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
            Created Non-BST (Not Symmetric tree)

                    5

             9             3

          2     4        8     10

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
            level1LeftNode = new TreeNode(9);
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


    public void printPreety() {
        List<TreeNode> list = new ArrayList<>();
        list.add(root);
        printTree(list, getHeight(root));
    }

    public int getHeight(TreeNode root) {

        if (root == null) {
            return 0;
        } else {
            return 1 + Math.max(getHeight(root.left), getHeight(root.right));
        }
    }

    /**
     * pass head node in nilList and height of the tree
     *
     * @param levelNodes
     * @param level
     */
    private void printTree(List<TreeNode> levelNodes, int level) {

        List<TreeNode> nodes = new ArrayList<TreeNode>();

        //indentation for first node in given level
        printIndentForLevel(level);

        for (TreeNode treeNode : levelNodes) {

            //print node data
            System.out.print(treeNode == null ? " " : treeNode.data);

            //spacing between nodes
            printSpacingBetweenNodes(level);

            //if its not a leaf node
            if (level > 1) {
                nodes.add(treeNode == null ? null : treeNode.left);
                nodes.add(treeNode == null ? null : treeNode.right);
            }
        }
        System.out.println();

        if (level > 1) {
            printTree(nodes, level - 1);
        }
    }

    private void printIndentForLevel(int level) {
        for (int i = (int) (Math.pow(2, level - 1)); i > 0; i--) {
            System.out.print(" ");
        }
    }

    private void printSpacingBetweenNodes(int level) {
        //spacing between nodes
        for (int i = (int) ((Math.pow(2, level - 1)) * 2) - 1; i > 0; i--) {
            System.out.print(" ");
        }
    }


}
