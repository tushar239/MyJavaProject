package algorithms.crackingcodinginterviewbook._6sort__search_merge;

// p.g. 412 from Cracking Coding Interview book
/*
Rank from stream:
You have a stream of numbers coming. Build a data structure where you can put all these numbers and whenever needed, you can find any element from it with its rank..
Stream of data is NOT in sorted form.

As data is not in sorted form, you can't use Binary Search to find any element and its index.
So, another alternative is BST.

There are two ways to solve this problem
- Do in-order traversal of created BST and find the index of an element that you are interested. That would be the rank of that element.
  This approach will reserve O(n).

  in-order traversal array: [5,10,13,15,20,23,24,25]

        rank of 5=0
        rank of 30=4
        rank of 23=5
        rank of 24=6
        rank of 25=7


- When you insert an element in BST, insert it with its rank.
  You cannot calculate its exact rank while inserting into BST, but at least a partial one.
  When you insert an element to the left of the node, increment the rank of of the node by one. Don't increase the rank when you add the element to the right of the node.

                                20(4)
                                / \
              15(3)                                 25(2)
              /                                     /
        10(1)                                   23(0)
        /   \                                      \
    5(0)    13(0)                                  24(0)

  When you search an element,
      if the element is on the left of the root node, then
        you already have a rank attached with left node.
      if the element is on the right of the root node, then
        rank of node to search=rank of root node+rank from right tree+1


Fundamentals:

    When to use Binary Search?

        Binary Search works best on already sorted array (SortedSearch.java, SparseSearch.java) or matrix (SortedMatrixSearch.java)

        Binary Search can be used on unsorted array to find peaks and valleys (PeakAndValleyInUnOrderedArray.java)

        Remember, Binary Search needs access by index, so it needs an array as an input, it will perform bad on sorted linkedlist.
        You can create an array from sorted linkedlist first and apply binary search on it.

    When to use Binary Search Tree instead of Binary Search?

        Binary Search works best on already sorted array (SortedSearch.java, SparseSearch.java) or matrix (SortedMatrixSearch.java)

        If you want to search an element in unsorted array, you need to sort it first before you can search. This takes at least O(n log n) for sorting and O(log n) for binary search.

        You can do better by searching an element in BST. Inserting elements will reserve O(n) and searching an element will reserve O(log n) provided created BST is close to balanced.
        BST is worse for sorted array. It will created totally unbalanced tree.

    When to use Min/Max-Heap(Priority Queue) Binary Search or BST?

        When you need to search min/max element in O(1), then you use Min/Max-Heap(Priority Queue) because min element is always on the top of of the min-heap and similary max element is always on the top of max-heap.
        Remember, min/max-heap are not trees. It just keeps track of indices in the array to keep track of min/max element.

*/
public class _10FindARankOfAnElementComingFromStream {

    public static void main(String[] args) {
        int[] nodes = {20, 15, 10, 5, 13, 25, 23, 24};

        BSTWithRank bst = new BSTWithRank();

        for (int i = 0; i < nodes.length; i++) {
            bst.insert(nodes[i]);
        }
        System.out.println(bst);

        int rankOfRootNode = bst.getRank(20);
        System.out.println("Rank of Root Node:" + rankOfRootNode);

        int rankOfLeftMostNode = bst.getRank(5);
        System.out.println("Rank of Left Most Node:" + rankOfLeftMostNode);

        int rankOfImmediateRightNode = bst.getRank(25);
        System.out.println("Rank of Immediate Right Node:" + rankOfImmediateRightNode);

        int rankOfRightMostNode = bst.getRank(24);
        System.out.println("Rank of Right Most Node:" + rankOfRightMostNode);

    }


    static class BSTWithRank {
        private RankNode root;

        private void insert(int data) {
            if (root == null) {
                root = new RankNode(data);
                return;
            }
            insert(root, data);
        }

        private static void insert(RankNode root, int data) {
            if (data < root.data) {
                if (root.left == null) {
                    root.left = new RankNode(data);
                    root.rank++;
                } else {
                    root.rank++;
                    insert(root.left, data);
                }
            } else if (data > root.data) {
                if (root.right == null) {
                    root.right = new RankNode(data);
                } else {
                    insert(root.right, data);
                }
            }
        }

        private int getRank(int data) {
            if (root == null) {
                return -1;
            }
            return getRank(root, data);
        }

        private static int getRank(RankNode root, int data) {
            if (root.data == data) {
                return root.rank;
            }
            if (data < root.data) {
                return getRank(root.left, data);
            }
            return root.rank + getRank(root.right, data) + 1; // +1 is very important
        }

        @Override
        public String toString() {
            return "BSTWithRank{" +
                    "root=" + root +
                    '}';
        }
    }

    static class RankNode {
        private int data;
        private RankNode left;
        private RankNode right;
        private int rank; // left tree size. Whenever you add an element on left side, increase rank by 1

        public RankNode(int data) {
            this.data = data;
        }


        @Override
        public String toString() {
            return "RankNode{" +
                    "data=" + data +
                    ", rank=" + rank +
                    ", left=" + left +
                    ", right=" + right +
                    '}';
        }
    }
}
