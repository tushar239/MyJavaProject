package algorithms._3binary_tree.geeksforgeeks;

import algorithms.crackingcodinginterviewbook._4tree_and_graph.tree.baseclasses.TreeNode;
import algorithms.utils.TreeUtils;

import java.util.HashMap;
import java.util.Map;

/*
    Lowest common ancestor of two nodes in Binary Tree Algorithm

    https://www.youtube.com/watch?v=F-_1sbnPbWQ


*/
public class _6FindLowestCommonAncestorOfTwoNodesInBinaryTree {

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

        System.out.println("LCA of 3 and 2");
        {
            _6FindLowestCommonAncestorOfTwoNodesInBinaryTree obj = new _6FindLowestCommonAncestorOfTwoNodesInBinaryTree();
            obj.findLCA(ten, new TreeNode(3), new TreeNode((2)), new HashMap<>());//10
            System.out.println(obj.count);
        }
        System.out.println("LCA of 3 and 5");
        {
            _6FindLowestCommonAncestorOfTwoNodesInBinaryTree obj = new _6FindLowestCommonAncestorOfTwoNodesInBinaryTree();
            obj.findLCA(ten, new TreeNode(3), new TreeNode((5)), new HashMap<>());//8
            System.out.println(obj.count);
        }
        System.out.println("LCA of 8 and 3");
        {
            _6FindLowestCommonAncestorOfTwoNodesInBinaryTree obj = new _6FindLowestCommonAncestorOfTwoNodesInBinaryTree();
            obj.findLCA(ten, new TreeNode(8), new TreeNode((3)), new HashMap<>());//8
            System.out.println(obj.count);
        }
        System.out.println("LCA of 8 and 2");
        {
            _6FindLowestCommonAncestorOfTwoNodesInBinaryTree obj = new _6FindLowestCommonAncestorOfTwoNodesInBinaryTree();
            obj.findLCA(ten, new TreeNode(8), new TreeNode((2)), new HashMap<>());//10
            System.out.println(obj.count);
        }
        System.out.println("LCA of 5 and 2");
        {
            _6FindLowestCommonAncestorOfTwoNodesInBinaryTree obj = new _6FindLowestCommonAncestorOfTwoNodesInBinaryTree();
            obj.findLCA(ten, new TreeNode(5), new TreeNode((2)), new HashMap<>());//10
            System.out.println(obj.count);
        }

        System.out.println("LCA of 18 and 3");
        {
            _6FindLowestCommonAncestorOfTwoNodesInBinaryTree obj = new _6FindLowestCommonAncestorOfTwoNodesInBinaryTree();
            obj.findLCA(ten, new TreeNode(18), new TreeNode((3)), new HashMap<>());//3
            System.out.println(obj.count);
        }
        System.out.println("LCA of 10 and 10");
        {
            _6FindLowestCommonAncestorOfTwoNodesInBinaryTree obj = new _6FindLowestCommonAncestorOfTwoNodesInBinaryTree();
            obj.findLCA(ten, new TreeNode(10), new TreeNode((10)), new HashMap<>());//10
            System.out.println(obj.count);
        }
        System.out.println("LCA of 11 and 12");
        {
            _6FindLowestCommonAncestorOfTwoNodesInBinaryTree obj = new _6FindLowestCommonAncestorOfTwoNodesInBinaryTree();
            obj.findLCA(ten, new TreeNode(11), new TreeNode((12)), new HashMap<>());//Not found
            System.out.println(obj.count);
        }

        //---------------------------------------- Find LCA in another way -------------------
        System.out.println("Find LCA in Another Way..................");

        System.out.println("LCA of 3 and 2");
        {
            _6FindLowestCommonAncestorOfTwoNodesInBinaryTree obj = new _6FindLowestCommonAncestorOfTwoNodesInBinaryTree();
            TreeNode lca = obj.findLCA_another_way(ten, new TreeNode(3), new TreeNode((2)));//10
            System.out.println(lca.data);
        }
        /*System.out.println("LCA of 3 and 5");
        {
            _6FindLowestCommonAncestorOfTwoNodesInBinaryTree obj = new _6FindLowestCommonAncestorOfTwoNodesInBinaryTree();
            TreeNode lca = obj.findLCA_another_way(ten, new TreeNode(3), new TreeNode((5)));//8
            System.out.println(lca.data);
        }*/
    }

    private int count = 0;

    private void findLCA(TreeNode root, TreeNode node1, TreeNode node2, Map<TreeNode, Boolean> nodeFound) {

        if (root == null) return;

        if (root.data == node1.data || root.data == node2.data) {
            System.out.println(root.data);
            return;
        }

        boolean foundNode1InLeftSubtree = nodeFound(root.left, node1, nodeFound);
        boolean foundNode1InRightSubtree = false;

        if (!foundNode1InLeftSubtree) {
            foundNode1InRightSubtree = nodeFound(root.right, node1, nodeFound);
        }

        boolean foundNode2InLeftSubtree = nodeFound(root.left, node2, nodeFound);
        boolean foundNode2InRightSubtree = false;
        if (!foundNode2InLeftSubtree) {
            foundNode2InRightSubtree = nodeFound(root.right, node2, nodeFound);
        }

        if (foundNode1InLeftSubtree && foundNode2InLeftSubtree) {
            findLCA(root.left, node1, node2, nodeFound);
        } else if (foundNode1InRightSubtree && foundNode2InRightSubtree) {
            findLCA(root.right, node1, node2, nodeFound);
        } else if ((foundNode1InLeftSubtree && foundNode2InRightSubtree) || (foundNode2InLeftSubtree && foundNode1InRightSubtree)) {
            System.out.println(root.data);
        } else if ((foundNode1InLeftSubtree || foundNode1InRightSubtree) && !(foundNode2InLeftSubtree && foundNode2InRightSubtree)) {
            System.out.println(node1.data);
        } else if ((foundNode2InLeftSubtree || foundNode2InRightSubtree) && !(foundNode1InLeftSubtree && foundNode1InRightSubtree)) {
            System.out.println(node2.data);
        } else {
            System.out.println("Not found");
            return;
        }

    }

    // Using Dynamic Programming
    private boolean nodeFound(TreeNode root, TreeNode node, Map<TreeNode, Boolean> nodeFound) {

        if (root == null) {
            nodeFound.put(node, false);
            return false;
        }

        if (root.data == node.data) {
            nodeFound.put(node, true);
            return true;
        }

        count++;

        return nodeFound(root.left, node, nodeFound) || nodeFound(root.right, node, nodeFound);
    }

    // not working as expected. need to relook into it.
    private TreeNode findLCA_another_way(TreeNode root, TreeNode node1, TreeNode node2) {
        if (root == null) return null;
        if (node1 == null && node2 != null) return null;
        if (node2 == null && node1 != null) return null;
        if (root.equals(node1) || root.equals(node2)) {
//            System.out.println(root.data);
            return root;
        }

        TreeNode foundNodeInLeftSubTree = findLCA_another_way(root.left, node1, node2);

        if (foundNodeInLeftSubTree != null) {

            if (node1.data == foundNodeInLeftSubTree.data || node2.data == foundNodeInLeftSubTree.data) {
                TreeNode foundNodeInRightSubTree = findLCA_another_way(root.right, node1, node2);

                if (foundNodeInRightSubTree != null) {
                    if (node1.data == foundNodeInRightSubTree.data || node2.data == foundNodeInRightSubTree.data) {
                        return root;
                    } else {
                        return foundNodeInRightSubTree;
                    }
                } else {
                    return foundNodeInRightSubTree;
                }
            } else {
                return foundNodeInLeftSubTree;
            }

        } else {
            return findLCA_another_way(root.right, node1, node2);
        }

    }


}
