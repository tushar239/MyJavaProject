package algorithms.crackingcodinginterviewbook._4tree_and_graph.tree;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

// http://www.geeksforgeeks.org/mirror-of-n-ary-tree/
/*
                         10
               2    34      56      100
                    1             7  8  9


            Mirror Image

                         10
               100    56      34      2
                    1             9  8  7

 */
public class CreateMirrorImageOfNaryTree {
    public static void main(String[] args) {
        NryTree nryTree = NryTree.createTree();
        System.out.println("Before creating a Mirror: ");
        System.out.println(nryTree);
        createMirrorImage(nryTree.root);
        System.out.println("After creating a Mirror: ");
        System.out.println(nryTree);

    }

    private static void createMirrorImage(NryTreeNode node) {
        if(node == null || !node.hasChildren()) return;

        Collections.reverse(node.getChildren());
        for (NryTreeNode child : node.getChildren()) {
            createMirrorImage(child);
        }
    }
    static class NryTree {
        public NryTreeNode root;

        public NryTree(NryTreeNode root) {
            this.root = root;
        }
        /*
                         10
               2    34      56      100
                    1             7  8  9
         */
        public static NryTree createTree() {
            return new NryTree(new NryTreeNode(10, new LinkedList<NryTreeNode>() {{
                add(new NryTreeNode(2, null));
                add(new NryTreeNode(34, new LinkedList<NryTreeNode>() {{
                    add(new NryTreeNode(1, null));
                }}));
                add(new NryTreeNode(56, null));
                add(new NryTreeNode(100, new LinkedList<NryTreeNode>() {{
                    add(new NryTreeNode(7, null));
                    add(new NryTreeNode(8, null));
                    add(new NryTreeNode(9, null));
                }}));

            }}));
        }

        @Override
        public String toString() {
            return "NryTree{" +
                    "root=" + root +
                    '}';
        }
    }

    static class NryTreeNode {
        public int data;
        public List<NryTreeNode> children;

        public NryTreeNode(int data, List<NryTreeNode> children) {
            this.data = data;
            this.children = children;
        }

        public int getData() {
            return data;
        }

        public List<NryTreeNode> getChildren() {
            return children;
        }

        public boolean hasChildren() {
            return getChildren() != null && getChildren().size() > 0;
        }

        @Override
        public String toString() {
            return "NryTreeNode{" +
                    "data=" + data +
                    ", children=" + children +
                    '}';
        }
    }

}
