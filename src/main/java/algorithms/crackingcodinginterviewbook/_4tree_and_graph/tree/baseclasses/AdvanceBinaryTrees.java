package algorithms.crackingcodinginterviewbook._4tree_and_graph.tree.baseclasses;

/**
 * @author Tushar Chokshi @ 8/7/15.
 */
public class AdvanceBinaryTrees {
    public static void main(String[] args) {

        // Binary Search Tree (BST)
        {
            System.out.println("Creating Binary Search Tree (BST)");
            /* With this example, tree will will be created as a worst case tree, So not taking this example and taking some real time example.
            Integer[] numbers = {7, 2, 1, 6, 8, 5, 3, 4};*/

            BST<Person, Company> bst = new BST<>();
            {
                bst.put(new Person("tushar"), new Company("cdk"));
                bst.put(new Person("miral"), new Company("bio-red"));
                bst.put(new Person("srikant"), new Company("srs"));
                bst.put(new Person("manjari"), new Company("house"));
                bst.put(new Person("jagdish"), new Company("office"));
                bst.put(new Person("nargis"), new Company("zoo"));
            }
            System.out.println("Tree Structure will be:");
            System.out.println("                            tushar(left child=miral)");
            System.out.println();
            System.out.println("                     miral(parent=tushar, left child=manjari, right child=srikant)");
            System.out.println();
            System.out.println("        manjari(left child=jagdish)                 srikant(left child=nargis) ");
            System.out.println();
            System.out.println("jagdish(leaf)                           nargis(leaf)");
            System.out.println();

            System.out.println();
            {
                Company company = bst.get(new Person("jagdish"));
                System.out.println("Value of key jagdish:" + company.getName());
            }
            System.out.println();
            {
                Company company = bst.get(new Person("srikant"));
                System.out.println("Value of key srikant:" + company.getName());
            }
            System.out.println();
            {
                Company company = bst.get(new Person("nargis"));
                System.out.println("Value of key nargis:" + company.getName());
            }
            System.out.println();
            {
                Company company = bst.get(new Person("abc"));
                if (company == null) {
                    System.out.println("key abc not found");
                }

            }
            System.out.println();
            {
                System.out.println("size of tree:" + bst.getSize());
                Node node = bst.remove(new Person("nargis"));
                if (node != null) {
                    System.out.println("node with key nargis is removed");
                }
                System.out.println("size of tree:" + bst.getSize());
            }
            System.out.println();
            {
                Company company = bst.get(new Person("nargis"));
                if (company == null) {
                    System.out.println("Value of key nargis not found");
                }
            }
            System.out.println();
            {
                System.out.println("Traversing Tree");
                bst.traverse();
            }
            System.out.println();
        }

        // LLRB tree ??????????? not able to make it work
        {
            {
                System.out.println("Creating Left Leaning Red-Black Tree (LLRB)");

                Integer[] numbers = {1, 7, 9, 5, 8, 2, 3, 10};

                BST<Integer, Integer> LLRB = new BST<>();
                Node parentNode = null;
                for (int i = 0; i <= numbers.length - 1; i++) {
                    LLRB.put_in_LLRB(numbers[i], i);
                }
                {
                    System.out.println("Traversing Tree");
                    LLRB.traverse();
                }
                System.out.println();

            }
        }
    }


    /*
    Each node in a tree is identified by a key (search (get), remove(delete) happens on key and not on value)

    BST has to be a symmetric tree, but doesn't have to be a balanced tree. So, searching a key in a tree is not always O(log n) like binary search.

    Symmetric tree means left child key is smaller and right child key is bigger than its parent node key.
    Symmetric means left hand side nodes are smaller and right hand side nodes are bigger than parent node. Symmetric tree doesn't have to be balanced.

    1
     |
      2
       |
        3

       2
     |   |
     1   3

    In Almost Balanced tree mean both left and right children are smaller than parent node. Binary Heap is based on Almost Balanced tree concept.
         7
     |      |
     5      6
   |   |
   3   4


  Interesting observation:
  The balance of the tree depends upon the order in which the elements are inserted. It is obvious that inserting ordered elements will produce a totally unbalanced tree.
  A set of ten elements can be inserted into a tree in 3,628,800 distinct orders. This will only produce 16,796 distinct trees. These trees will range from perfectly balanced to totally unbalanced. From a more pragmatic point of view, ordered trees (BST) are very efficient for storing and retrieving random data, but they are very bad at storing and retrieving pre-ordered data.

  Solution for BST worst case problem

   BST has a problem. It can reserve O(n) search time in worst case.
   To resolve this this problem, there are two extensions of BST.

    -	AVL tree (Adelson, Velski & Landis Tree) and Red-Black Tree.
        o	They are called self-balancing trees. They self-balance themselves using ROTATION algorithms.
        o	Both of these are Symmetric+ALMOST balanced.

    -	B-Tree.
        o	It’s an extension of M-Way tree. M-Way tree has same problem as BST.
        o	Both M-Way and B-Tree are based on a rule where each node has <=M children node and no of children nodes-1 values.
            So, node can have more than one values.
        o	B-Tree is always Symmetric+TOTALLY Balanced
        o	B-Tree is highly preferable algorithm for DB indexing.

    All of these strategies always reserve O(log n) only for searching/insertion/deletion.

   AVL Tree
    http://www.tutorialspoint.com/data_structures_algorithms/avl_tree_algorithm.htm

    It was the first self-balancing data structure to be invented.

    In an AVL tree, the heights of the two child subtrees of any node differ by at most one; if at any time they differ by more than one, rebalancing is done to restore this property.
    Lookup, insertion, and deletion all reserve O(log n) time in both the average and worst cases, where n is the number of nodes in the tree prior to the operation.


   Red-Black BST
     Red-Black BST is Symmetric+Almost/Totally Balanced
             10
         |      |
         7      11
       |   |   |
       6   8   9

    Almost Balanced means length of each path (from root to leaf) from that of other paths differs by 1 only.
    Totally Balanced means length of each path from root to leaf is same.

    As all operations are recursive. In Worst case scenario, all of them takes O(n).

    Red-Black BST is a combination of
        BST (Binary Search Tree)-Symmetric but doesn't have to Balanced
        and
        BHT (Binary Heap Tree)-Totally Balanced or Almost Balanced. Almost Balanced is also called Almost Complete. Totally Balanced is also called Complete.
    It is Symmetric as well as Balanced (Almost or Totally).

    Red-Black Tree is a self-balancing Binary Search Tree (BST) where every node follows following rules.

        1.	No node has two child red links
        2.	All red links are leaning left
        3.	All null links are blank links
        4.	Every path has same number of blank links

    To see how Red-Black BST works, look at 'Data Structures Self Created Document.doc'

    Red-Black Tree is very completed, so just understanding should be fine. No need to memorize the code.
    Below code doesn't have its remove method because it was just too complicated.

     */

    /*
    Read this class only to understand Red-Black Tree. To understand BST, use another BST.java
     */

    static class BST<K extends Comparable, V> {
        protected static boolean RED = true;
        protected static boolean BLACK = false;

        private Node root;
        private int size;


        public void put(K key, V value) {
            put(root, new Node(key, value));
        }

        private Node put(Node parentNode, Node newNode) {
            if (parentNode == null) {
                root = newNode;
                return root;
            }

            if (newNode.getKey().compareTo(parentNode.getKey()) < 0) {
                if (parentNode.hasLeftChild()) {
                    parentNode = put(parentNode.getLeftChild(), newNode);
                } else {
                    parentNode.setLeftChild(newNode);
                    //newNode.setParentNode(parentNode);
                    size++;
                }

            } else if (newNode.getKey().compareTo(parentNode.getKey()) > 0) {
                if (parentNode.hasRightChild()) {
                    parentNode = put(parentNode.getRightChild(), newNode);
                } else {
                    parentNode.setRightChild(newNode);
                    //newNode.setParentNode(parentNode);
                    size++;
                }
            } else {
                parentNode.setValue(newNode.getValue());
            }
            return newNode;
        }

        public V get(K key) {
            return get(root, key);
        }

        private V get(Node node, K keyToCompare) {
            if (keyToCompare == null) {
                return null;
            }
            if (node == null) { //left and right nodes of a leaf node will be null
                return null;
            }
            if (keyToCompare.compareTo(node.getKey()) < 0) {
                return get(node.getLeftChild(), keyToCompare);
            } else if (keyToCompare.compareTo(node.getKey()) > 0) {
                return get(node.getRightChild(), keyToCompare);
            } else {
                return (V) node.getValue();
            }

        }

        public Node remove(K key) {
            return remove(root, key);
        }

        // This implementation looks wrong. Do not consider this method. See another BST.java.
        private Node remove(Node node, K keyToCompare) {
            if (keyToCompare == null) {
                return null;
            }
            if (node == null) {
                return null;
            }
            if (keyToCompare.compareTo(node.getKey()) < 0) {
                return remove(node.getLeftChild(), keyToCompare);
            } else if (keyToCompare.compareTo(node.getKey()) > 0) {
                return remove(node.getRightChild(), keyToCompare);
            } else {
                if (node.getParentNode().getLeftChild().getKey().compareTo(keyToCompare) == 0) {
                    node.getParentNode().setLeftChild(null);
                } else {
                    node.getParentNode().setRightChild(null);
                }
                size--;
                return node;
            }
        }

        public void traverse() {
            traverse(root);
        }

        private void traverse(Node node) {
            System.out.println("Key:" + node.getKey().toString() + " ,Value:" + node.getValue().toString());
            if (node.hasLeftChild()) {
                traverse(node.getLeftChild());
            }
            if (node.hasRightChild()) {
                traverse(node.getRightChild());
            }
        }

        public int getSize() {
            return size;
        }


        public Node put_in_LLRB(K key, V value) {
            // Always create new Node in LLRB with colorOfLinkWithParentNode=RED
            return put_in_LLRB(root, new Node(key, value, RED));
        }

        private Node put_in_LLRB(Node parentNode, Node newNode) {
            // use same put code of BST
            //return put(parentNode, newNode);
            Node h = put(parentNode, newNode).getParentNode();
            //parentNode = h;

            // Use rotateLeft/rotateRight/flipColors as needed
            if (h != null) {
                //Node parent = newlyInsertedNode.getParentNode();

                // Rotate Left, if right link is red and left link is black (null node is considered having black link)
                // after rotation, h node changes from actual h node to its right child node
                if (isRed(h.getRightChild()) && !isRed(h.getLeftChild())) {
                    h = rotateLeft(h);
                }
                // Rotate Right, if left link is red and left-to-left link is red
                // after rotation, h node changes from actual h node to its left child node
                if (isRed(h.getLeftChild()) && isRed(h.getLeftChild().getLeftChild())) {
                    h = rotateRight(h);
                }
                // Flip Colors, if both left and child nodes are linked with red color links
                // make child node links to black and make parent node's link with its parent to red
                if (isRed(h.getLeftChild()) && isRed(h.getRightChild())) {
                    flipColors(h);
                }

                if (!h.hasParentNode()) {
                    root = h;
                }
            }

            return h;
        }

        private Node rotateLeft(Node h) {
            System.out.println("going for rotate left");

            Node x = h.getRightChild();

            h.setRightChild(x.getLeftChild());

            exchangeParents(h, x);
            x.setLeftChild(h);


            x.colorOfLinkWithParentNode = h.colorOfLinkWithParentNode;
            h.colorOfLinkWithParentNode = RED;

            return x;
        }

        private void exchangeParents(Node h, Node x) {
            Node hParentNode = h.getParentNode();

            x.setParentNode(hParentNode);
            if (hParentNode != null) {
                if (hParentNode.hasLeftChild() && hParentNode.getLeftChild() == h) {
                    hParentNode.setLeftChild(x);
                } else if (hParentNode.hasRightChild() && hParentNode.getRightChild() == h) {
                    hParentNode.setRightChild(x);
                }

            }
        }

        private Node rotateRight(Node h) {
            System.out.println("going for rotate right");

            Node x = h.getParentNode();

            exchangeParents(x, h);

            h.colorOfLinkWithParentNode = x.colorOfLinkWithParentNode;

            x.setLeftChild(h.getRightChild());
            h.setRightChild(x);


            x.colorOfLinkWithParentNode = RED;

            return h;
        }

        private void flipColors(Node h) {
            h.colorOfLinkWithParentNode = RED;
            h.getLeftChild().colorOfLinkWithParentNode = BLACK;
            h.getRightChild().colorOfLinkWithParentNode = BLACK;
        }

        private boolean isRed(Node node) {
            if (node == null) return false; // null links are BLACK
            return (node.isColorOfLinkWithParentNode() == RED);
        }


    }

    static class Node<K extends Comparable, V> { // Doubly linked nilList because each node is aware of its parent node(previous node)
        private K key;
        private V value;
        private Node parentNode;
        private Node leftChild;
        private Node rightChild;
        private boolean isMarkedForSwap;

        private boolean colorOfLinkWithParentNode; // For LLRB tree (Red-Black Tree)

        Node(K key, V value) { // For BST tree
            this.key = key;
            this.value = value;
        }

        Node(K key, V value, boolean colorOfLinkWithParentNode) { // For LLRB tree
            this.key = key;
            this.value = value;
            this.colorOfLinkWithParentNode = colorOfLinkWithParentNode;
        }

        public K getKey() {
            return key;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }

        public Node getLeftChild() {
            return leftChild;
        }

        public void setLeftChild(Node leftChild) {
            this.leftChild = leftChild;
            if (this.leftChild != null) {
                this.leftChild.setParentNode(this);// Note this
            }
        }

        public boolean hasLeftChild() {
            return getLeftChild() != null;
        }

        public Node getRightChild() {
            return rightChild;
        }

        public void setRightChild(Node rightChild) {
            this.rightChild = rightChild;
            if (this.rightChild != null) {
                this.rightChild.setParentNode(this); // Note this
            }
        }

        public boolean hasRightChild() {
            return getRightChild() != null;
        }

        public void setParentNode(Node parentNode) {
            this.parentNode = parentNode;
        }

        public Node getParentNode() {
            return parentNode;
        }

        public boolean isColorOfLinkWithParentNode() {
            return colorOfLinkWithParentNode;
        }

        public void setColorOfLinkWithParentNode(boolean colorOfLinkWithParentNode) {
            this.colorOfLinkWithParentNode = colorOfLinkWithParentNode;
        }

        public boolean hasParentNode() {
            return getParentNode() != null;
        }

        public boolean isMarkedForSwap() {
            return isMarkedForSwap;
        }

        public void setIsMarkedForSwap(boolean isMarkedForSwap) {
            this.isMarkedForSwap = isMarkedForSwap;
        }
    }


    static class Person implements Comparable<Person> {
        private String name;

        Person(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public int compareTo(Person person) {
            return this.getName().compareTo(person.getName());
        }

        @Override
        public String toString() {
            return name;
        }
    }

    static class Company {
        private String name;

        private String id;

        Company(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

}

