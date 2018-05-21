package algorithms.crackingcodinginterviewbook._4tree_and_graph.tree.baseclasses;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Tushar Chokshi @ 8/30/15.
 */
public class BSTTest {
    public static void main(String[] args) {
        {
            BST bst = BST.createBST();

            System.out.println("Finding a node in BST...");
            int data = 4;
            TreeNode node = bst.get(data);
            if (node != null) {
                System.out.println("\tFound node:" + node.getData());
            } else {
                System.out.println("\tNot Found:" + data);
            }
        }
        System.out.println();

        System.out.println("Removing a node from BST...");
        {
            {
                BST bst = BST.createBST();
                int data = 3;
                System.out.println("\tRemoving a node from BST: "+ data);
                removeNode(bst, data);

            }
            System.out.println();
            System.out.println();
            {
                BST bst = BST.createBST();
                int data = 9;
                System.out.println("\tRemoving a node from BST: "+ data);
                removeNode(bst, data);
            }
            System.out.println();
            System.out.println();
            {
                BST bst = BST.createBST();
                int data = 4;
                System.out.println("\tRemoving a node from BST: "+ data);
                removeNode(bst, data);
            }

        }

        System.out.println();
        System.out.println();


        //http://stackoverflow.com/questions/9456937/when-to-use-preorder-postorder-and-inorder-binary-search-tree-traversal-strate
        {
            final BST bst = BST.createBST();

            System.out.println("PreOrder Traversal of BST...");
            bst.preOrderTraversal(bst.root);
            System.out.println();
            System.out.println();

            System.out.println("InOrder Traversal of BST...");
            bst.inOrderTraversal(bst.root);
            System.out.println();

            System.out.println("InOrder Traversal of BST Considering null...");
            List<Integer> list = new ArrayList<>();
            bst.inOrderTraversalConsideringNull(bst.root, list);
            System.out.println("\t"+list);

            System.out.println();

            System.out.println("PostOrder Traversal of BST...");
            bst.postOrderTraversal(bst.root);
            System.out.println();
            System.out.println();

            System.out.println("LevelOrder Recursive Traversal of BST...");
            LinkedBlockingQueue<TreeNode> queue1 = new LinkedBlockingQueue<>();
            queue1.add(bst.root);
            bst.levelOrderTraversalRecursively(queue1);
            System.out.println();

            System.out.println("LevelOrder Recursive Traversal of BST - another way...");
            bst.levelOrderTraversalRecursively(new ArrayList<TreeNode>(1) {{
                add(bst.root);
            }}, new LinkedBlockingQueue<>());
            System.out.println();



            System.out.println("LevelOrder Recursive Traversal Another Way of BST...");
            if (bst.root != null) {
                LinkedBlockingQueue<TreeNode> queue = new LinkedBlockingQueue<>();
                queue.add(bst.root);

                ArrayList<TreeNode> treeNodes = new ArrayList<TreeNode>(1) {{
                    add(bst.root);
                }};

                bst.levelOrderTraversalAnotherWay(treeNodes, queue);
            }
            System.out.println();


            System.out.println("LevelOrder Iterative Traversal of BST...");
            bst.levelOrderTraversalIteratively(bst.root);
            System.out.println();

        }
    }

    protected static void removeNode(BST bst, int data) {
        System.out.print("\tBefore removing:  ");
        bst.preOrderTraversal(bst.root);
        TreeNode deleteNode = bst.deleteNode(data);
        System.out.print("\n\tAfter removing: ");
        bst.preOrderTraversal(bst.root);
    }
}
