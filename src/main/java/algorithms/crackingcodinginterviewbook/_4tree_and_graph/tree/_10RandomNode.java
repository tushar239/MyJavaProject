package algorithms.crackingcodinginterviewbook._4tree_and_graph.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
pg 267 of Cracking Coding Interview Book

Random Node:
you are implementing a binary tree class from scratch which, in addition to insert,find and delete, has a method getRandomNode() which returns a
random node from the tree. All nodes should be equally likely to be chosen.
Design and implement an algorithm for getRandomNode and explain how you would implement the rest of the methods.


IMPORTANT:
Remember that you need to have 'size' variable for each node in a tree.
Increase size on each insert of new node.

Remember the logic of finding a random node.
 */
public class _10RandomNode {

    public static void main(String[] args) {
        BTNode root = new BTNode(9);
        BinaryTree binaryTree = new BinaryTree(root);
        binaryTree.insert(6);
        binaryTree.insert(4);
        binaryTree.insert(8);
        binaryTree.insert(13);
        binaryTree.insert(10);
        binaryTree.insert(17);

        binaryTree.printPretty();

        System.out.println(binaryTree.getRandomNode());

    }

    static class BinaryTree {

        private BTNode root;

        public BinaryTree(BTNode root) {
            this.root = root;
        }

        public BTNode getRandomNode() {
            return getRandomNode(root);
        }

        /*
        Important:
        for this algorithm, it is required that each node in a tree has a size.
        size can be incremented for nodes when you insert a new node.

        generate a random number
        if random number <= root.left.size, then fetch a random node from left subtree
        if random number > root.left.size, then fetch a random node from right subtree
        else return root;

        Runtime complexity = O(log n)
         */
        private BTNode getRandomNode(BTNode root) {
            if (root == null) return root;
            if (root.left == null && root.right == null) return root;

            int random = new Random().nextInt(root.size + 1);
            System.out.println("Generated random number: " + random);

            if (random == root.size) return root;

            int leftSize = root.left == null ? 0 : root.left.size;
            if (random <= leftSize) return getRandomNode(root.left);

            return getRandomNode(root.right);
        }

        public void insert(int data) {
            if (root == null) {
                root = new BTNode(data);
                return;
            }
            root.insert(root, data);
        }

        public void printPretty() {
            List<BTNode> list = new ArrayList<>();
            list.add(root);
            printTree(list, getHeight(root));
        }


        public int getHeight(BTNode root) {
            if (root == null) {
                return 0;
            } else {
                return 1 + Math.max(getHeight(root.left), getHeight(root.right));
            }
        }

        private void printTree(List<BTNode> levelNodes, int level) {

            List<BTNode> nodes = new ArrayList<>();

            //indentation for first node in given level
            printIndentForLevel(level);

            for (BTNode BTNode : levelNodes) {

                //print node data
                System.out.print(BTNode == null ? " " : BTNode.data + "(" + BTNode.size + ")");

                //spacing between nodes
                printSpacingBetweenNodes(level);

                //if its not a leaf node
                if (level > 1) {
                    nodes.add(BTNode == null ? null : BTNode.left);
                    nodes.add(BTNode == null ? null : BTNode.right);
                }
            }
            System.out.println();

            if (level > 1) {
                printTree(nodes, level - 1);
            }
        }

        private void printIndentForLevel(int level) {
            for (int i = (int) (Math.pow(2, level - 1)); i > 0; i--) {
                System.out.print("   ");
            }
        }

        private void printSpacingBetweenNodes(int level) {
            //spacing between nodes
            for (int i = (int) ((Math.pow(2, level - 1)) * 2) - 1; i > 0; i--) {
                System.out.print("   ");
            }
        }


    }

    static class BTNode {
        private int data;
        private BTNode left;
        private BTNode right;
        private int size = 1;

        public BTNode(int data) {
            this.data = data;
        }

        public BTNode getLeft() {
            return left;
        }

        public void setLeft(BTNode left) {
            this.left = left;
        }

        public BTNode getRight() {
            return right;
        }

        public void setRight(BTNode right) {
            this.right = right;
        }

        public int getData() {
            return data;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public void insert(BTNode root, int data) {
            if (data < root.data) {
                root.size++;
                if (root.left == null) {
                    root.left = new BTNode(data);
                    return;
                }
                insert(root.left, data);
            } else if (data > root.data) {
                root.size++;
                if (root.right == null) {
                    root.right = new BTNode(data);
                    return;
                }
                insert(root.right, data);
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            BTNode btNode = (BTNode) o;

            if (data != btNode.data) return false;
            if (size != btNode.size) return false;
            if (left != null ? !left.equals(btNode.left) : btNode.left != null) return false;
            return right != null ? right.equals(btNode.right) : btNode.right == null;
        }

        @Override
        public int hashCode() {
            int result = data;
            result = 31 * result + (left != null ? left.hashCode() : 0);
            result = 31 * result + (right != null ? right.hashCode() : 0);
            result = 31 * result + size;
            return result;
        }

        @Override
        public String toString() {
            return "BTNode{" +
                    "data=" + data +
                    '}';
        }


    }
}
