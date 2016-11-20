package java8.functionalprograming.functionalprogramminginjavabook.chapter10;

import java8.functionalprograming.functionalprogramminginjavabook.chapter5and8.List;

import static java8.functionalprograming.functionalprogramminginjavabook.chapter10.Tree.empty;
import static java8.functionalprograming.functionalprogramminginjavabook.chapter10.Tree.foldLeft_InOrder;
import static java8.functionalprograming.functionalprogramminginjavabook.chapter10.Tree.foldLeft_PreOrder;
import static java8.functionalprograming.functionalprogramminginjavabook.chapter10.Tree.height;
import static java8.functionalprograming.functionalprogramminginjavabook.chapter10.Tree.rotateLeft;

/*
 * Balancing trees using the Day-Stout-Warren (DSW) algorithm.
 *
 * pg 310
 *
 * I am not able to understand book's code. So, I got some idea from 'https://xuyuanguo.wordpress.com/2013/02/06/dsw-algorithm-balancing-binary-search-tree/'
 * and wrote my own code.
 *
 * In this algorithm,
 * - make the tree totally imbalanced
 *      - by converting tree into linked list using pre-order traversal
 *      - and converting that linked list into tree by adding each element of a linked list to right of a tree
 * - apply rotateLeft on right nodes till the Balance Factor of a tree is 1 or 0.
 *   Balance Factor=Math.abs(left subtree height - right subtree height).
 *
 * DSW algorithm is useful to make provided tree balanced.
 *
 * But if you are creating a new tree, then you can keep balancing a tree as you keep adding new nodes to it using the same rotation algorithms.
 * AVL and Red-Black trees are build like that.
 * They are also called Automatically/Self-Balancing Trees.
 * These two trees are Almost Balanced.
 *
 * If you want totally balanced tree, then use B-Tree.
 *
 * Instead of using rotation algorithm, which is a key algorithm for DSW, you can use another technique called 'CreateMinimalBST' from ordered linked list. This is coded in CreateMinimalBST.java.
 *
 *  DSW algorithm is ok, if tree needs to be balanced few times, but it is not ok if it needs to balanced on each insert/delete of a node. it causes performance issues.
 *  There re two solutions of real time tree balancing.
 *  - AVL tree, Red-Black Tree, B-Tree etc are self-balancing (automatically balanced) trees, in which, tree is balanced on insertion/deletion of the node to a tree.
 *    OR
 *  - you can say that you will use DSW algorithm, when Balance Factor (Math.abs(height of left subtree - height of right subtree) > some number (may be 10-20 instead of 1).
 *
 */
public class DSWAlgorithm {


        /*
            Initial Tree

            height of left subtree = 2
            height of right subtree = 3
            which is almost balanced
                5
                |
            _____________
            |           |
            3           8
            |           |
        --------    ---------
        |       |   |       |
        1       10  6      11
    -----               ---------
    |                   |       |
    0               ________
                    |       |
                    10      14
                             |
                          ________
                          |      |
                          13     15




        After making initial tree imbalanced using makeTreeImBalanced method.

        height of left subtree = 4
        height of right subtree = 1
        which is imbalanced

                0
                |
            _____________
                        |
                        1
                        |
                    ---------
                            |
                            3
                            |
                         ---------
                                 |
                                 5
                                 |
                             ---------
                                     |
                                     6
                                     |
                                 --------
                                        |
                                        8
                                        |
                                     --------
                                            |
                                            10
                                            |
                                         ________
                                                |
                                                11
                                                |
                                             _______
                                                   |
                                                   13
                                                   |
                                                _______
                                                      |
                                                      14
                                                      |
                                                   ________
                                                          |
                                                          15



            After making imbalanced tree balanced.

            height of left subtree = 2
            height of right subtree = 3
            which is almost balanced
                5
                |
            _____________
            |           |
            3           8
            |           |
        --------    ---------
        |       |   |       |
        1       10  6      11
    -----               ---------
    |                   |       |
    0               ________
                    |       |
                    10      14
                             |
                          ________
                          |      |
                          13     15

         */

    private static <A extends Comparable<A>> Tree<A> balance(Tree<A> tree) {
        Tree<A> imBalancedTree = makeTreeImBalanced(tree);

        System.out.println("ImBalanced Tree......");
        printTree(imBalancedTree);

        Tree<A> balancedTree = create_BalancedTree_From_ImBalancedTree(imBalancedTree);
        return balancedTree;
    }


/*
    public static int log2nlz(int n) {
        return n == 0
                ? 0
                : 31 - Integer.numberOfLeadingZeros(n);
    }
*/

    /**
     * Time complexity: log(n)
     */
/*
    private static int greatestPowerOf2LessThanN(int n) {
        int x = MSB(n);//MSB
        return (1 << x);//2^x
    }
*/

    /**
     * Time complexity: log(n)
     * return the index of most significant set bit: index of
     * least significant bit is 0
     */
/*
    public static int MSB(int n) {
        int ndx = 0;
        while (1 < n) {
            n = (n >> 1);
            ndx++;
        }
        return ndx;
    }
*/

    private static <A extends Comparable<A>> Tree<A> makeTreeImBalanced(Tree<A> tree) {
        List<A> listFromTree = Tree.foldLeft_InOrder_Using_One_Function(tree, List.nilList(), leftSubTreeResult_List -> treeValue -> rightSubTreeResult_List ->  List.concat(List.concat(leftSubTreeResult_List, List.consList(treeValue)), rightSubTreeResult_List));
        Tree<A> imbalancedTree = List.foldLeftTailRecursive(listFromTree.reverse(), Tree.empty(), listElement -> tree1 -> new Tree.DefaultTree<A>(listElement, Tree.empty(), tree1));
        return imbalancedTree;
    }

    private static <A extends Comparable<A>> Tree<A> create_BalancedTree_From_ImBalancedTree(Tree<A> imbalacedTree) {
        // finding height of a tree every time is not an optimal way. There is another approach given in the book using log2nlz method, but I am not able to understand it.
        // There is another approach given at 'http://www.geekviewpoint.com/java/bst/dsw_algorithm' also using greatestPowerOf2LessThanN method, but I am not able understand it.
        boolean balanceFactorOfTree = Math.abs(height(imbalacedTree.left()) - height(imbalacedTree.right())) <= 1;
        if (balanceFactorOfTree) {
            return imbalacedTree;
        }

        return create_BalancedTree_From_ImBalancedTree(rotateLeft(imbalacedTree));

/*
        // or imperative style
        while(Math.abs(height(imbalacedTree.left()) - height(imbalacedTree.right())) > 1 ) {
            imbalacedTree = rotateLeft(imbalacedTree);
        }

        Tree<A> balancedTree = imbalacedTree;
        return balancedTree;
*/
    }

    public static void main(String[] args) {
        Tree<Integer> tree = empty();
        tree = tree.insert(tree, 11);
        tree = tree.insert(tree, 8);
        tree = tree.insert(tree, 14);
        tree = tree.insert(tree, 5);
        tree = tree.insert(tree, 10);
        tree = tree.insert(tree, 13);
        tree = tree.insert(tree, 15);
        tree = tree.insert(tree, 3);
        tree = tree.insert(tree, 6);
        tree = tree.insert(tree, 1);
        tree = tree.insert(tree, 0);

        System.out.println("Initial Tree......");
        printTree(tree);

        Tree<Integer> balancedTree = balance(tree);
        System.out.println("Balanced Tree......");
        printTree(balancedTree);

    }

    private static <A extends Comparable<A>> void printTree(Tree<A> tree) {
        System.out.println();
        System.out.println("Pre-Order Traversal : ");
        System.out.println(foldLeft_PreOrder(tree));
        System.out.println("In-Order Traversal: ");
        System.out.println(foldLeft_InOrder(tree));
        System.out.println("Height of a tree: " + height(tree));
        System.out.println("Height of left subtree: " + height(tree.left()) + ", Height of right subtree: " + height(tree.right()));
        System.out.println();
    }

}
