package algorithms._0Fundamentals.Tree;
/*

Tree:

    How many nodes are there in balanced Binary Tree?

    Height Of Balanced Binary Tree will be log2 n, where n is the number of nodes in a tree.

    Height of Balanced Ternary Tree will be log3 n.


    Totally Unbalanced BST:

    Height of BST can be n in worst case. where all nodes are in direction

            10
           9
          8
         7
        6
       ...

*/

/*
    Inserting a node in Binary Tree
    -------------------------------
    It's easy. You can just add it as a leaf node


    Inserting a node in BST
    -----------------------
    if(root.data == newNode.data) don't do anything and return.
    else if(newNode.data == root.data) go to left subtree
    else go to right subtree and try to insert a node there.

    New node will be added as a leaf node either in left or right subtree.

    Basically, you start comparing a new node from the root node of a tree. This is reverse of Binary Heap.

    Inserting an element in Binary Heap
    -------------------------------
    Insert an element at the end of PQ (as the last leaf node) and then swim up.
    This is reverse of BST.



    Deleting a node from Binary Tree
    --------------------------------
    Deleting a node from Binary Tree is easier.

    Deleting a node from BST
    ------------------------
    BST.java
    When you want to delete a node from BST, you need to consider three situations.

    Node to be deleted
    - is a leaf node
      Just detach node to be deleted from a parent node based on whether it's a left or right node of its parent node.

    - has only one child node
      Put its child node of a node to be deleted in place of node to be deleted.

    - has two children nodes (this is the most challenging)
      IMPORTANT:
      find min value node from right most tree of a node to be deleted and put it in place of a node to be deleted.

    Deleting an element from Binary Heap
    ------------------------------------
    BinaryHeap.java

    You need to first insert an element in PQ at the end (insert a node as a last leaf node) and then swim up.
*/

/*
    Traversal of Binary Tree
    ------------------------

    Any Binary Tree (not just Binary Search Tree) can be traversed 4 ways

    See 'Binary Tree Traversal Types.doc'
    See 'PreOrder,InOrder,PostOrder Traversal tricks.mp4'

    https://www.youtube.com/watch?v=9RHO6jU--GU

    Breadth-First Traversal (BFS)

        Level-Order Traversal (https://www.youtube.com/watch?v=86g8jAQug04, http://www.java2blog.com/2014/07/binary-tree-level-order-traversal-in.html)
        You need to use a Queue for BFS. It is same concept as BFS in graph. See BST.java.

        -	Level-order traversal (Horizontal Level-Order Traversal)
        -	Vertical-Order traversal (Vertical Level-Order Traversal) (internally uses Level-Order traversal along with Horizontal Distance applied to each node)


    Depth-First Traversal (DFS) (https://www.youtube.com/watch?v=gm8DUJJhmY4)

        DFS traverses the tree while giving priority to the depth(height) of the tree. It is called Depth-First instead of more logical 'Height-First'.

        -   Pre-Order Traversal
        -   In-Order Traversal  ----- Always traverses nodes of BST(Binary Search Tree which is symmetric) in ASCENDING order
        -   Post-Order Traversal

        One additional type of traversal

        -	Boundary traversal (BoundaryTraversal.java)

        See 'PreOrder,InOrder,PostOrder Traversal tricks.mp4'.

        See BST.java.


    When to use Pre-Order, In-order or Post-Order?

        Watch 'PreOrder,InOrder,PostOrder Traversal tricks.mp4'

        https://stackoverflow.com/questions/9456937/when-to-use-preorder-postorder-and-inorder-binary-search-tree-traversal-strate

        In-Order traversal
            If you know that the tree has an inherent sequence in the nodes and if you want to flatten the tree back into its original sequence, then an in-order traversal should be used. The tree would be flattened in the same way it was created. A pre-order or post-order traversal might not unwind the tree back into the sequence which was used to create it.

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


    Searching a node involves traversing a tree. There are different types of traversals.

        - Pre Order
        - In Order
        - Post Order
        - Level Order

        See BST.java

    When you traverse a

    -   Normal Binary Tree,
        To search an element, you may need to go both left and right, if element is not same as root node.

        Here, is a sample code to find an element recursively in a Binary Tree.

        if(element == root.data) {
            return true;
        }

        boolean foundInLeftSubTree = find(root.left, element);

        boolean foundInRightSubTree = false;

        if (!foundInLeftSubTree) {
            foundInRightSubTree = find(root.left, element);
        }

        e.g. ReverseBinaryTreePath.java

    -   Binary Search Tree(BST),

        You need to apply 'Binary Search' concept.
        If node that you are searching is less than root node, then traverse left subtree, otherwise traverse left search tree.

        Here, is a sample code to find an element recursively in a Binary Search Tree (BST).

        if(element == root.data) {
            return true;
        }
        if(element < root.data) {
            return find(root.left, element);
        }
        return find(root.right, element);

*/


/*
    Merging two BSTs
    ----------------
    http://www.geeksforgeeks.org/merge-two-balanced-binary-search-trees/

    traverse both BSTs in-order and create two arrays
    merge both arrays in a new array in such a way that new array is a sorted (in-order) array
    use CreateMinimalBST approach to create a new tree out of new sorted array
*/

/*
  It is very important to understand Recursion for Tree.
  It is very root_to_leaf_problems_hard to work with a Tree using iterative approach. So, recursion works best with trees. That's why books introduce recursion from Trees onwards.
  With String, LinkedList, Stack & Queue, you can still use iterative approach easily, but with Trees it is root_to_leaf_problems_hard. Later on in this document, you will see the reason.

  Recursion
  ---------

  is a concept of reduce the problem by 1.
  step 1 - write exit condition on root node
  step 2 - recurse the method by passing left node of root node. Merge the returned result of recursive method with root node, if not done in step 1 (e.g. In deleteNode method, merging is done in exit condition only, where as in put method, it is not done in exit condition, so it needs to be done after recursive call)
  step 3 - recurse the method by passing right node of root node. Merge the returned result of recursive method with root node, if not done in step 1 (e.g. In deleteNode method, merging is done in exit condition only, where as in put method, it is not done in exit condition, so it needs to be done after recursive call)

  Most probably, you will need to surround step 2 and 3 by some condition because if you don't then it will traverse both left and right subtrees even though it is not required. Sometimes, it can give wrong results also.


  How to think of call stacks during recursion?

      See FindLowestCommonAncestorInBinaryTree.java

      Any recursive algorithm is made of one or more of below steps

      - exit condition on entry  (mandatory)
      - optimization condition that decides whether to traverse left subtree or not for better time complexity of the algorithm(optional)
      - recursive call to left subtree (mandatory)
      - optimization condition that decides whether to traverse right subtree or not for better time complexity of the algorithm (optional)
      - recursive call to right subtree (mandatory)
      - exit condition on exit (optional)
        if this one is there, then it shows that you are using post-traversal method to traverse a binary tree.

        Let's look at FindLowestCommonAncestorOfTwoNodesInBinaryTree.java algorithm


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

  BH - Binary Heap - Binary Heap is used by Heap Sort and Heap Sort is used by Priority Queue (BH->HS->PQ)
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
    Binary Heap (Min-Heap and Max-Heap)
    -----------------------------------
    It is used by Heap Sort and Heap Sort is used by Priority Queue.

    BinaryHeap.java

    Parent element of an element at kth position in array can be found at k/2 position.
    Children elements of an element in an array can be found at 2k and 2k+1 positions.
    If there are n elements in an array, leaf nodes are at n/2 + 1 to n positions in an array.

    It uses an Aux array.

    Binary Heap is used by Heap Sort and Heap Sort is used by Priority Queue.
    BH->HS->PQ

    Heap Sort takes O(n log n) to search an element just like Quick Sort, then why it is not used for sorting?
    Heap Sort uses Aux array and Quick Sort doesn't.

    USAGE of Priority Queue - Very Important
    When data is coming from multiple sources and you need to sort those data, you can keep those data in priority queue and extract min by min elements from priority queue.

 */


/*
Tries (Prefix Trees)
It comes up a lot in interview questions, but algorithm books don't spend much time on this data structure.

A trie is a variant of an n-ary tree in which characters are stored at each node. Each path down the tree may represent a word.
The * nodes (called 'null nodes') are often used to indicate complete words. For example, the fact that there is a * node undera MANY indicates that MANY is a complete word.
The existence of MA path indicates there are words tht start with MA.
The actual implementation of these * nodes might be a special type of child(such as a TerminatingTrieNode, which inherits from TrieNode).
Or, we could use just a boolean flag.

A node in a trie could have anywhere from 0 to ALPHABET_SIZE + 1 children.

                   ( )
                /   |   \
              /     |    \
            /       |     \
            M       L      A
           / \      |      |
          A  Y      I      *
         /   |      |
        N    *      E
       /            |
      Y             *
      |
      *

 Trie is specially used to store all the words in the dictionary.
 It is useful when you need to know whether is there any word that has prefix MAN.
 HashTable can tell you whether a word 'MANY' exists or not, but it is very root_to_leaf_problems_hard for it to confirm that MAN is a prefix f MANY.

 Trie is extremely useful for Auto-Completing the word.
 If you type M, it can give you choices MANY, MY.
 If you type M, then A, then N, it can keep passing you a reference of the last searched node. so that it doesn't have to start searching from root when you type a new character.

*/

/*
    AVL, RBT, B-Tree etc
    AdvanceBinaryTrees.java
*/

/*
- sorting

- reversing

    Reversing a Path in Binary Tree

        ReverseBinaryTreePath.java

        There is a small difference between finding an element in Binary Tree and Binary Search Tree(BST).
        Memorize this algorithm.

- deleting


- searching


*/

public class TreeFundamentals {
}
