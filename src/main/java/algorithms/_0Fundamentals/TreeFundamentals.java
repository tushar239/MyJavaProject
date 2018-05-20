package algorithms._0Fundamentals;
/*

- sorting

- reversing

    ReverseBinaryTreePath.java

- deleting


- searching

    Searching involves traversing a tree. There are different types of traversals.

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
        You need to apply Binary Search concept.
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
public class TreeFundamentals {
}
