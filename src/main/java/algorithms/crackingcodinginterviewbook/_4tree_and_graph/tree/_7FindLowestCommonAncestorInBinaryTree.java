package algorithms.crackingcodinginterviewbook._4tree_and_graph.tree;

import algorithms.crackingcodinginterviewbook._4tree_and_graph.tree.baseclasses.BST;
import algorithms.crackingcodinginterviewbook._4tree_and_graph.tree.baseclasses.TreeNode;

import static algorithms.utils.TreeUtils.printPreety;

/**
 * @author Tushar Chokshi @ 9/7/15.
 */

/*
http://www.geeksforgeeks.org/lowest-common-ancestor-binary-tree-set-1/

If Tree is
                 5

         3              9

      2     4        8     10

If you want to find lowest common ancestor of 3 and 2, then it will be 3.
If you want to find lowest common ancestor of 3 and 4, then it will be 3.
If you want to find lowest common ancestor of 2 and 4, then it will be 3.
If you want to find lowest common ancestor of 9 and 4, then it will be 5.
If you want to find lowest common ancestor of 2 and 10, then it will be 5.

If you want to find lowest common ancestor of 2 and 16, then it will be null.  --- It is impossible to return null. so returning 2. To avoid this situation, you need to traver entire tree to make sure that both 2 and 16 exist.



 */
public class _7FindLowestCommonAncestorInBinaryTree {
    public static void main(String[] args) {
        BST bst = BST.createBST();
        printPreety(bst.root);

        System.out.println("Assuming Parent link is NOT available for all nodes in a tree");
        System.out.println("Case -1, finding ancestor of 2 and 3:   " + CA(bst.root, new TreeNode(2), new TreeNode(3))); // O/P: 3
        System.out.println("Case -2, finding ancestor of 2 and 4:   " + CA(bst.root, new TreeNode(2), new TreeNode(4))); // O/P: 3
        System.out.println("Case -3, finding ancestor of 2 and 9:   " + CA(bst.root, new TreeNode(2), new TreeNode(9)));// O/P: 5
        System.out.println("Case -4, finding ancestor of 2 and 16:   " + CA(bst.root, new TreeNode(2), new TreeNode(16)));// O/P: null

        System.out.println("Case -1, finding ancestor of 2 and 3:   " + CA_Harder_Way(bst.root, new TreeNode(2), new TreeNode(3))); // O/P: 3
        System.out.println("Case -2, finding ancestor of 2 and 4:   " + CA_Harder_Way(bst.root, new TreeNode(2), new TreeNode(4))); // O/P: 3
        System.out.println("Case -3, finding ancestor of 2 and 9:   " + CA_Harder_Way(bst.root, new TreeNode(2), new TreeNode(9)));// O/P: 5
        System.out.println("Case -4, finding ancestor of 2 and 16:   " + CA_Harder_Way(bst.root, new TreeNode(2), new TreeNode(16)));// O/P: null


        System.out.println();

        System.out.println("Assuming Parent link is available for all nodes in a tree");
//        System.out.println("Case -1, finding ancestor of 2 and 3:   " + findLowestCommonAncestorWithParentLink(bst.get(2), bst.get(3)));// O/P: 3
//        System.out.println("Case -2, finding ancestor of 2 and 4:   " + findLowestCommonAncestorWithParentLink(bst.get(2), bst.get(4)));// O/P: 3
//        System.out.println("Case -3, finding ancestor of 2 and 9:   " + findLowestCommonAncestorWithParentLink(bst.get(2), bst.get(9)));// O/P: 5

        System.out.println("Case -1, finding ancestor of 2 and 3:   " + CA_With_Parent_Link(bst.root, bst.get(2), bst.get(3))); // O/P: 3
        System.out.println("Case -2, finding ancestor of 2 and 4:   " + CA_With_Parent_Link(bst.root, bst.get(2), bst.get(4))); // O/P: 3
        System.out.println("Case -3, finding ancestor of 2 and 9:   " + CA_With_Parent_Link(bst.root, bst.get(2), bst.get(9)));// O/P: 5


    }

/*
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

   Let's look at FindLowestCommonAncestorInBinaryTree.java algorithm
          CA(5,2,9)
             CAL=CA(3,2,9)  --- CAL=CA(2,2,9)   ---  CAL=(null,2,9)
                                                     CAR=(null,2,9)
                                CAR=CA(4,2,9)
                                                ---  CAL=(null,2,9)
                                                     CAR=(null,2,9)

             CAR=CA(9,2,9)  --- CAL=CA(8,2,9)   --- ...
                                CAR=CA(10,2,9)  --- ...

    When you are tracing a call stack on paper, you can do it in tree form.


        CA(5,2,9) {
            exit_condition_on_entry
            CAL=CA(3,2,9)
                exit_condition_on_entry
                CAL=CA(2,2,9)
                    ...
                CAR=CA(4,2,9)
                    ...
                exit_condition_on_exit
            CAR=CA(9,2,9)
                exit_condition_on_entry
                    ...
                exit_condition_on_exit
            exit_condition_on_exit
        }

    If value is returned from exit_condition_on_entry  or exit_condition_on_exit of
    - CA(3,2,9) call, then it is assigned to CAL of CA(5,2,9)
    - CA(9,2,9) call, then it is assigned to CAR of CA(5,2,9)


Analysis of call stack
----------------------

Tree :

    5
  3   9
 2 4 8 10

Inputs for this algorithm :
    - startNode=5
    - n1=2
    - n2=9

So, you want to find common ancestor of nodes 2 and 9.
expected common ancestor is 5.

Call Stack :


    CA(5,2,9)
        exit_cond_on_entry
        CAL = CA(3,2,9) --------------------------------------------------------
        opt_cond                                                                |
        CAR = CA(9,2,9)---------------------------------                        |
        exit_cond_on_exit -- met with return value=9   |                    exit_cond_on_entry
                                                       |                    CAL = CA(2,2,9)-------------------------------------------------------------------------------------------
                                                       |                    opt_cond                                                                                                 |
                                                       |                    CAR = CA(4,2,9)---------------------------------------                                                   |
                                                       |                    exit_cond_on_exit - met with return value=3          |                                                   |
                                                       |                                                                exit_cond_on_entry                              exit_cond_on_entry - met with return value=2
                                                       |                                                                CAL = CA(null,2,9) ------------------------------------------------------------------
                                                       |                                                                opt_cond                                                                            |
                                                       |                                                                CAR = CA(null,2,9) ------------------------------------------------             exit_cond_on_entry - met with return value=null
                                                       |                                                                exit_cond_on_exit ----- met with return value=null                |
                                                       |                                                                                                                            exit_cond_on_entry - met with return value=null
                                                exit_cond_on_entry - met with return value=9


    CA(3,2,9)'s CAL=2 and CAR=null
    So, CA(5,2,9)'s CAL=2

    CA(9,2,9)'s return value is 9
    So, CA(5,2,9)'s CAR =9

    CA(5,2,9)'s exit_cond_on_exit will detect that CAL and CAR are not null. So, final value is 9.



Analysis of the case where OPTIMIZATION CONDITION will be utilized to reduce the time complexity of this algorithm
------------------------------------------------------------------------------------------------------------------

Tree :

    5
  3   9
 2 4 8 10

Inputs for this algorithm :
    - startNode=5
    - n1=2
    - n2=4

So, you want to find common ancestor of nodes 2 and 4.
expected common ancestor is 3.

Call Stack :


    CA(5,2,4)
        exit_cond_on_entry
        CAL = CA(3,2,4) --------------------------------------------------------
        opt_cond -  met with return value=3                                     |
        CAR = CA(9,2,4)                                                         |
        exit_cond_on_exit                                                   exit_cond_on_entry
                                                                            CAL = CA(2,2,4)-------------------------------------------------------------------------------------------
                                                                            opt_cond                                                                                                 |
                                                                            CAR = CA(4,2,4)---------------------------------------                                                   |
                                                                            exit_cond_on_exit - met with return value=3          |                                                   |
                                                                                                                        exit_cond_on_entry - met with return value=4        exit_cond_on_entry - met with return value=2

        CA(3,2,4)'s CAL=2 and CAR=4
        CA(5,2,4)'s optimization condition will be met because it's CAL is other than 2 and 4. So, CA(9,2,4) won't be called. This will save the traversal time of half of the tree.
        If you don't have optimization condition, time complexity of this algorithm will be O(n) in worst case.


*/

    private static TreeNode CA(TreeNode root, TreeNode n1, TreeNode n2) {

        // exit_condition_on_entry
        if (root == null) return null;
        if (n1 == null && n2 != null) return null;
        if (n2 == null && n1 != null) return null;
        if (root.equals(n1) || root.equals(n2)) return root;

        // recursive call to left subtree
        TreeNode CAL = CA(root.left, n1, n2);

        // optimization condition that decides whether to traverse right subtree or not --- Important to reduce time complexity. it will not change the result.
        if (CAL != null && (!CAL.equals(n1) && !CAL.equals(n2))) {
            return CAL;
        }

        // recursive call to right subtree
        TreeNode CAR = CA(root.right, n1, n2);

        // exit_condition_on_exit
        if (CAL != null && CAR != null) {
            return root;
        }
        return CAL == null ? CAR : CAL;
    }


    private static TreeNode CA_Harder_Way(TreeNode root, TreeNode n1, TreeNode n2) {
        if (root == null) return null;
        if (n1 == null && n2 != null) return null;
        if (n2 == null && n1 != null) return null;
        if (root.equals(n1) && root.equals(n2)) return root;
        if (root.equals(n1) && (n2.equals(root.left) || n2.equals(root.right))) {
            return n1;
        }
        if (root.equals(n2) && (n1.equals(root.left) || n1.equals(root.right))) {
            return n2;
        }
        if ((n1.equals(root.left) && n2.equals(root.right)) || (n2.equals(root.left) && n1.equals(root.right))) {
            return root;
        }
        if (root.equals(n1)) {
            return n1;
        }
        if (root.equals(n2)) {
            return n2;
        }

        TreeNode left = CA_Harder_Way(root.left, n1, n2);

        if (left != null && !left.equals(n1) && !left.equals(n2)) {// condition of n1=2 and n2=4 is satisfied that has ancestor=3
            return left; // found the final result
        }
        TreeNode right = CA_Harder_Way(root.right, n1, n2);

        if (right != null && !right.equals(n1) && !right.equals(n2)) {// condition of n1=8 and n2=10 is satisfied that has ancestor=9
            return right; // found the final result
        }

        if (left != null && right != null && (n1.equals(left) || n2.equals(left)) && (n1.equals(right) || n2.equals(right))) {// condition of n1=2 and n2=9 is satisfied that has ancestor=5
            return root;
        }

        return left != null ? left : right; // either n1 or n2 exist in a tree, other one doesn't. condition of n1=2 and n2=16 is satisfied who doesn't have common ancestor, but can't return null. If I return null, then traversal of left subtree will return null instead of 2. So, I am returning 2.
    }

    private static TreeNode CA_With_Parent_Link(TreeNode root, TreeNode n1, TreeNode n2) {
        if (root == null) return null;
        if (n1 == null && n2 != null) return null;
        if (n2 == null && n1 != null) return null;
        if ((n1.getParent() != null && n2.getParent() == null) || (n2.getParent() != null && n1.getParent() == null) || (n2.getParent() == null && n1.getParent() == null))
            return null;
        if (root.equals(n1) || root.equals(n2)) return root;
        if (n1.equals(n2.getParent())) return n1;
        if (n2.equals(n1.getParent())) return n2;


        TreeNode ancestorOfN1 = null;
        TreeNode n1Parent = n1.getParent();
        while (true) {
            TreeNode n2Parent = n2.getParent();
            if (n2Parent.equals(n1Parent)) {
                ancestorOfN1 = n1Parent;
                break;
            }
            if (n1Parent.equals(root)) {
                ancestorOfN1 = n1Parent;
                break;
            }
            n1Parent = n1Parent.getParent();
        }

        return ancestorOfN1;

    }

    /*
        Here unless you traverse left and right subtree, you can not find the result, so u can say this algorithm uses post-traversal.
        Only pre-traversal algorithm can be converted to Iterative algorithm. So, this algorithm can't be converted to Iterative algorithm.

        It is actually not a post traversal also because you are actually not doing any processing on root node after visiting root.left and root.right (left and right subtrees)

        IMPORTANT:
        Remember, when recursive method is returning a value, then store returned value of left tree traversal and right tree traversal in different variables and later on use those two variable to reach to some conclusion.
        When you are thinking of a recursion, then reserve the smallest tree of 3 elements as an example like below.

        Take below tree as an example
            4
        3       5

        If you need to find lowest common ancestor of 3 and 4, then it will be 4
        If you need to find lowest common ancestor of 3 and 5, then it will be 4

        If you need to find lowest common ancestor of 3 and 3, then it will be 3
        If you need to find lowest common ancestor of 3 and 6, then it will be 3
        If you need to find lowest common ancestor of 4 and 6, then it will be 4

        When you are thinking of recursion, then each step in a tree of a recursive method call has access to input parameters.
        Here there basically 4 main steps - 1) exit condition 2)left subtree traversal 3) right subtree traversal 4) comparing the result of left and right subtree traversals.
        All these 4 steps are executed as a call to parent method call.
        e.g. fLCA(root=4, node1=3 and node2=5) has above 4 steps and all these steps have access to parent method call's input parameters (root=4, node1=3 and node2=5)

     */
    public static TreeNode findLowestCommonAncestor(TreeNode root, TreeNode node1, TreeNode node2) {

        if (root == null) { //exit cond 1
            return null;
        }

        if (root.data == node1.data || root.data == node2.data) {//exit cond 2  (taking care of situation like - If you need to find lowest common ancestor of 3 and 6, then it will be 3)
            return root;
        }

        TreeNode oneFoundNode = findLowestCommonAncestor(root.left, node1, node2); // node1 or node2 found. Storing into oneFoundNode.
        TreeNode secondFoundNode = findLowestCommonAncestor(root.right, node1, node2); // node1 or node2 found. Storing into secondFoundNode.


        if (oneFoundNode != null && secondFoundNode != null) { // If both nodes are traversed
            return root;
        }
        // else if only one of the nodes are traversed
        return oneFoundNode != null ? oneFoundNode : secondFoundNode;// see BST's postOrderTraversal()'s comment to understand why this condition is required. Also see 'Sorting Algorithm Worksheet.xlsx' for better understanding.
    }


    // If you have a parent link available
    public static TreeNode findLowestCommonAncestorWithParentLink(TreeNode node1, TreeNode node2) {
        if (node1.data == node2.data) return null;

        TreeNode ancestor = node1;

        while (ancestor != null) {
            if (ancestorIsInPathOf(ancestor, node2)) {
                return ancestor;
            }
            ancestor = ancestor.parent;
        }

        return null;
    }

    private static boolean ancestorIsInPathOf(TreeNode ancestor, TreeNode node) {
        while (node != null) {
            if (node.data == ancestor.data) {
                return true;
            }
            node = node.parent;
        }

        return false;
    }
}
