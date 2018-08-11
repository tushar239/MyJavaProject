package algorithms._3binary_tree.geeksforgeeks;

import algorithms.crackingcodinginterviewbook._4tree_and_graph.tree.baseclasses.TreeNode;
import algorithms.utils.TreeUtils;

/*
    Lowest common ancestor of two nodes in Binary Tree Algorithm

    https://www.youtube.com/watch?v=NBcqBddFbZw

                    10
                8           2
            3       5
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
            obj.findLCA(ten, new TreeNode(3), new TreeNode((2))/*, new HashMap<>()*/);//10
            System.out.println(obj.count);
        }
        System.out.println("LCA of 3 and 5");
        {
            _6FindLowestCommonAncestorOfTwoNodesInBinaryTree obj = new _6FindLowestCommonAncestorOfTwoNodesInBinaryTree();
            obj.findLCA(ten, new TreeNode(3), new TreeNode((5))/*, new HashMap<>()*/);//8
            System.out.println(obj.count);
        }
        System.out.println("LCA of 8 and 3");
        {
            _6FindLowestCommonAncestorOfTwoNodesInBinaryTree obj = new _6FindLowestCommonAncestorOfTwoNodesInBinaryTree();
            obj.findLCA(ten, new TreeNode(8), new TreeNode((3))/*, new HashMap<>()*/);//8
            System.out.println(obj.count);
        }
        System.out.println("LCA of 8 and 2");
        {
            _6FindLowestCommonAncestorOfTwoNodesInBinaryTree obj = new _6FindLowestCommonAncestorOfTwoNodesInBinaryTree();
            obj.findLCA(ten, new TreeNode(8), new TreeNode((2))/*, new HashMap<>()*/);//10
            System.out.println(obj.count);
        }
        System.out.println("LCA of 5 and 2");
        {
            _6FindLowestCommonAncestorOfTwoNodesInBinaryTree obj = new _6FindLowestCommonAncestorOfTwoNodesInBinaryTree();
            obj.findLCA(ten, new TreeNode(5), new TreeNode((2))/*, new HashMap<>()*/);//10
            System.out.println(obj.count);
        }

        System.out.println("LCA of 18 and 3");
        {
            _6FindLowestCommonAncestorOfTwoNodesInBinaryTree obj = new _6FindLowestCommonAncestorOfTwoNodesInBinaryTree();
            obj.findLCA(ten, new TreeNode(18), new TreeNode((3))/*, new HashMap<>()*/);//3
            System.out.println(obj.count);
        }
        System.out.println("LCA of 10 and 10");
        {
            _6FindLowestCommonAncestorOfTwoNodesInBinaryTree obj = new _6FindLowestCommonAncestorOfTwoNodesInBinaryTree();
            obj.findLCA(ten, new TreeNode(10), new TreeNode((10))/*, new HashMap<>()*/);//10
            System.out.println(obj.count);
        }
        System.out.println("LCA of 11 and 12");
        {
            _6FindLowestCommonAncestorOfTwoNodesInBinaryTree obj = new _6FindLowestCommonAncestorOfTwoNodesInBinaryTree();
            obj.findLCA(ten, new TreeNode(11), new TreeNode((12))/*, new HashMap<>()*/);//Not found
            System.out.println(obj.count);
        }

        //---------------------------------------- Find LCA in another way -------------------
        System.out.println("Find LCA in Better Way..................");

        System.out.println("LCA of 3 and 2");
        {
            _6FindLowestCommonAncestorOfTwoNodesInBinaryTree obj = new _6FindLowestCommonAncestorOfTwoNodesInBinaryTree();
            TreeNode lca = obj.findLCA_Better_Way(ten, new TreeNode(3), new TreeNode((2)));
            System.out.println(lca.data);// 10
        }

        System.out.println("LCA of 3 and 5");
        {
            _6FindLowestCommonAncestorOfTwoNodesInBinaryTree obj = new _6FindLowestCommonAncestorOfTwoNodesInBinaryTree();
            TreeNode lca = obj.findLCA_Better_Way(ten, new TreeNode(3), new TreeNode((5)));
            System.out.println(lca.data);//8
        }

        System.out.println("LCA of 3 and 11");
        {
            _6FindLowestCommonAncestorOfTwoNodesInBinaryTree obj = new _6FindLowestCommonAncestorOfTwoNodesInBinaryTree();
            TreeNode lca = obj.findLCA_Better_Way(ten, new TreeNode(3), new TreeNode((11)));
            System.out.println(lca.data);// 3
        }

    }

    private int count = 0;

    /*
        Below algorithm works fine, but time complexity is very high.

                                1
                        2               3
                    4       5
                         7      8

         find LCA of node1=4 and node2=8. Answer should be 2

         find LCA of node1=1 and node2=4. Answer should be 1

         find LCA of node1=1 and node2=9. Answer should be 1  (9 doesn't exist)

         find LCA of node1=9 and node2=10. Answer should be 'LCA can't be found'



         void findLCA(root, node1, node2) {

              if root == null

                    LCA is not possible to find because root is null

              if root == node1 or node2, then

                    LCA of node1 and node2 is root

              else if (node1 is found in left subtree && node2 is found in right subtree)
                        OR
                      (node1 is found in right subtree && node2 is found in left subtree), then

                      LCA of node1 and node2 is root

              else if(both node1 and node2 are found in left subtree), then

                    findLCA(root.left, node1, node2)

              else if(both node1 and node2 are found in right subtree), then

                    findLCA(root.right, node1, node2)

              else if(node1 is found in either left or right subtrees, but node2 is not found), then

                    LCA is node1;

              else if(node2 is found in either left or right subtrees, but node1 is not found), then

                    LCA is node2;

              else // both node1 and node2 are not found

                    LCA is not possible to find because both node1 and node2 do not exist

          }


    */
    private void findLCA(TreeNode root, TreeNode node1, TreeNode node2/*, Map<TreeNode, Boolean> nodeFound*/) {

        if (root == null) return;

        if (root.data == node1.data || root.data == node2.data) {
            System.out.println(root.data);
            return;
        }

        boolean foundNode1InLeftSubtree = nodeFound(root.left, node1/*, nodeFound*/);
        boolean foundNode1InRightSubtree = false;

        if (!foundNode1InLeftSubtree) {
            foundNode1InRightSubtree = nodeFound(root.right, node1/*, nodeFound*/);
        }

        boolean foundNode2InLeftSubtree = nodeFound(root.left, node2/*, nodeFound*/);
        boolean foundNode2InRightSubtree = false;
        if (!foundNode2InLeftSubtree) {
            foundNode2InRightSubtree = nodeFound(root.right, node2/*, nodeFound*/);
        }

        if (foundNode1InLeftSubtree && foundNode2InLeftSubtree) {
            findLCA(root.left, node1, node2/*, nodeFound*/);
        } else if (foundNode1InRightSubtree && foundNode2InRightSubtree) {
            findLCA(root.right, node1, node2/*, nodeFound*/);
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

    private boolean nodeFound(TreeNode root, TreeNode node/*, Map<TreeNode, Boolean> nodeFound*/) {

        if (root == null) {
            //nodeFound.put(node, false);
            return false;
        }

        if (root.data == node.data) {
            //nodeFound.put(node, true);
            return true;
        }

        count++;

        return nodeFound(root.left, node/*, nodeFound*/) || nodeFound(root.right, node/*, nodeFound*/);
    }

    /*
            Better approach that takes O(n)

            TreeNode findLCA(root, node1, node2) {

                if(root == null) return null;

                if(root=node1 or node2) return root;

                TreeNode node1or2FromLeft = findLCA(root.left, node1,node2)

                // extra condition to avoid traversal of right subtree. This you can understand only when you draw a recursive tree diagram.
                // In case of node1=4 and node2=8, node1or2FromLeft will be 2. So, LCA is already found. In this case, you don't need traverse right subtree.
                if(node1or2FromLeft != null && node1or2FromLeft != node1 && node1or2FromLeft != node2)
                    return node1or2FromLeft;

                TreeNode node1or2FromRight = findLCA(root.right, node1,node2)

                if(node1or2FromLeft != null && node1or2FromRight != null)
                    return root;

                if(node1or2FromLeft != null)
                    return node1or2FromRight;

                return node1or2FromLeft;

            }



                                1
                        2               3
                    4       5
                         7      8


                                                                                    LCA(1, 4,8)
                                                                                       |
                                                -----------------------------------------------------------------------------------------------------------
                                                |                                                                                                         |
                        root=1              LCA(2, 4,8)=2     if(returned value is diff than 4 or 8), then do not traverse right subtree             LCA(3, 4,8)
                                                |                                                                                                         |
                                    --------------------------                                                                                      ---------------------
                                    |                        |                                                                                      |                   |
                        root=2  LCA(4, 4,8)=4           LCA(5, 4,8)=8    if(both 4,8 are found) return root=2                           root=3  LCA(null, 4,8)  LCA(null, 4,8)
                                                             |
                                    |                       -----------------                                                                           .........
                                                            |               |
                        root=4 exit cond matched    root=5 LCA(8, 4,8) LCA(8, 4,8)
                               do return root=4         .........

    */
    private TreeNode findLCA_Better_Way(TreeNode root, TreeNode node1, TreeNode node2) {

        if (root == null) {
            return null;
        }

        // Important condition
        if (root.equals(node1) || root.equals(node2)) {
            return root;
        }

        TreeNode left = findLCA_Better_Way(root.left, node1, node2);// left

        // extra condition that avoids traversing a right subtree, if possible. If you forget this condition, code will still work.
        if (left != null && !left.equals(node1) && !left.equals(node2)) return left;

        TreeNode right = findLCA_Better_Way(root.right, node1, node2);// right

        if (left != null && right != null) {
            return root;
        }

        if (left == null) {
            return right;
        }

        return left;
    }


}
