package algorithms.crackingcodinginterviewbook._4tree_and_graph.tree.baseclasses;

import java.util.Arrays;

/*

Binary Heap Video:
    https://www.youtube.com/watch?v=EuUBxM_E03E


pg. 103 of Cracking Coding Algorithm Book

Binary Heap is used by Heap Sort and Heap Sort is used by Priority Queue (BH->HS->PQ).

Among all of these data structures, Binary Heap provides optimal performance.

    to insert 1 element, it takes O(log n). (IMP) to insert n elements, it takes O(n), not O(n log n) --- from https://www.geeksforgeeks.org/time-complexity-of-building-a-heap/
    to find min/max element, it takes (O(1))
    to delete 1 element min/max, it takes O(log n)

    Inserting n elements takes O(n). deleting n element takes O(n log n). So, creating sorted array from it takes O(n) + O(n log n)

Priority Queue uses Heap Sort mechanism because it needs to maintain order(priority).

There are two versions of Binary Heap (Max Binary Heap and Min Binary Heap).
In Max Binary Heap, root is max and all its children are smaller than it. Max Binary Heap is good, if you have a requirement of retrieving max in O(1) likewise Min Binary Heap.

(IMP)
If you have a requirement where from 3-4 different queues/arrays, you want to keep elements in one data structure and want to find min/max element in O(1) time then use Priority Queue.
If you have infinite stream of elements and at any point, you want to find out min/max, then use binary heap.


Binary Heap
- Binary Heap is not a tree, it is an array but can be visualized as a tree.
- uses BALANCED (or almost Balanced) Tree. It doesn't have to be symmetric like BST.
  Almost balanced tree is also called Complete Tree(pg 102 of Cracking Coding Interview Book)
- all children are smaller than parent in a tree.
- It is a in-place sorting, but it doesn't sort original array. It requires an Auxiliary array.
  This Aux array will not be in sorted form. After this Aux array is created, you need to iterate it removing Max element from it and fill your original array back.
  So, inserting/deleting an element from Binary Heap takes O(log n), but creating sorted array from it takes O(n log n)
- Even though structure is like tree, it actually doesn't use tree. Unlike to BST, we do not all its elements as nodes, rather we call keys because node has link to left and right children nodes, whereas children of binary heap key is identified by index in aux array.
    - Aux array always start from index 1 (not 0). It makes it easier to find children nodes and leaf nodes. If you see the formula of finding a parent of a node at k'th position, it is k/2. if you start from 0th element in array, it will result in exception.
      Create Aux array with always (orig array size + 1) capacity.
      Aux array looks like [blank,1,2,3,4,5]
    - As it is a balanced tree, height of a tree will be (log n) and so search takes only O(log n) in worst case.
    - left and right children of a key(node) at position k is calculated by 2k and 2k+1.
    - Parent key(node) of any child key(node) at position k can be found at the position of k/2.
    - if height of a tree is h (which is log n) then max number of keys(nodes) in a tree can be 2^h+1 - 1.
    - if number of keys(nodes) in a tree are n, then leaf keys(nodes) in a tree exist from n/2 +1 to n positions.

    In Ternary tree, replace 2 by 3 in all these equations.
    - It uses special methods like swim and sink. swim is used while inserting a new element whereas sink is used while deleting a min/max(root element) element.
        For inserting an element in the tree, add it at the end of a tree and swim it up.
        For deleting an element from tree, remove a top most element from a tree and put last element of a tree to top position and then sink it down the tree.

Worst BST
    1
     |
      2
       |
        3
It may reserve o(n) if you want to find max.
But in Max Binary Heap, it takes O(1) and as Binary Heap is almost balanced, traversal time is never more than O(log n).
Max depth of Binary Heap is log n.

Max Binary Heap will always be like

         10
      |      |
      4      5
    |   |   |
    2   1   3
It does not have to be symmetric though, but it is always almost balanced. So, insert or delete_min_or_max does not reserve more than O(log n).


(IMP) Time Complexity to create a Binary Heap from an array
-----------------------------------------------------------
To insert/delete an element in/from a Binary Heap takes O(log n).
So, you must be thinking that creating a Binary Heap for n elements takes O(n log n), but that is not true.
see https://www.geeksforgeeks.org/?p=12580
It takes close to O(n) only.

Usage of Binary Heap
--------------------
 - Find min/max in O(1) time
 - Creating Priority Queue (BH -> HS -> PQ)
 - When data is flowing from multiple streams and you want to find min/max element at any given point in time.
   As data comes, keep inserting them in min/max heap. At any given point in time, you can find min/max element.
 - Sorting k-sorted array (when every element in an array is k positions away from its actual position in sorted array)
   For k-sorted array, insertion sort takes O(nk), while heap sort takes O(n log k)

   e.g. SortKSortedArray.java, MergeKSortedArray.java
*/

public class BinaryHeap {
    public static void main(String[] args) {
        Integer[] A = {7, 2, 8, 1, 6, 5};

        System.out.println(".......Creating Binary Heap Tree (Priority Queue) from original array.......");
        MAX_BH<Integer> bh = new MAX_BH(A.length);
        for (int i = 0; i <= A.length - 1; i++) {
            bh.insert(A, A[i]);
        }

        System.out.println("PQ state after converting original array into Binary Heap Tree (Priority Queue-PQ)");
        bh.display();// [null, 8, 6, 7, 1, 2, 5]

        System.out.println();
        System.out.println(".......Starting removing an element from the top of the tree and putting them in reverse way in original array.......");
        for (int i = A.length; i >=  1; i--) {
            A[i-1]= (Integer)bh.delMax();
            bh.display();

        }
        System.out.println("Sorted Array:"+Arrays.asList(A));

    }

    static class MAX_BH<K extends Comparable> {

        private Comparable<K>[] PQ;//priority queue uses Binary Heap solution, so this array is named as PQ.
        int n = 0; // total elements inserted in array. Don't make a mistake of initializing it by 1, instead of use n++ in insert() method and n-- in delMax() method.

        public MAX_BH(int capacity) {
            // Here we are creating a new array with +1 size because Binary Heap tree (priority queue) always starts from 1.
            // So space complexity is n+1 here.
            PQ = new Comparable[capacity + 1];
        }

        public void insert(Comparable[] A, K key) {
            // Binary Heap tree (priority queue) always starts from 1.
            // If you see the formula of finding a parent of a node at k'th position, it is k/2. if you start from 0th element in array, it will result in exception.
            n++;
            PQ[n] = key; // insert a new key at the end of a tree and then swim that key up.
            swim(n);
        }

        public Comparable<K> delMax() {
            final Comparable<K> elementToBeReturned = PQ[1];
            replace(PQ, 1, n);// replace root key with last key of a tree
            PQ[n] = null; // avoid loitering
            n--;

            System.out.println("Removed "+elementToBeReturned +" from PQ, PQ size is now: " + n);
            System.out.println();
            sink(1); // sink root key after replacing it with last key of a tree.

            return elementToBeReturned;

        }


        // for inserting a key
        public void swim(int k) { // k is a position of key in a tree. swim operation will be executed max O(log n) times. recursive swim calls will acquire stack space of log n (same as the height of a tree)
            if (k <= 1) return;

            if (less(PQ[k / 2], PQ[k])) {// key at k/2 index is a parent key of a key at k index

                exchange(PQ, k / 2, k);
                swim(k / 2);

            }
        }

        // for deleting a key
        public void sink(int k) {
            int LCI = 2 * k; // left child index
            int RCI = (2 * k) + 1; // right child index

            if(LCI <= n) { // if element at LCI exist

                if (RCI <= n) { // if element at LCI exist then only element at RCI can exist
                    if (lessOrEqual(PQ[RCI], PQ[LCI])) {
                        exchange(PQ, k, LCI);
                        sink(LCI);
                    } else {
                        exchange(PQ, k, RCI);
                        sink(RCI);
                    }
                } else {
                    if (less(PQ[k], PQ[LCI])) {
                        exchange(PQ, k, LCI);
                        sink(LCI);
                    }
                }
            }
        }

        private boolean isLeaf(int k) {
            return (k >= ((n /2) +1)); // leaves of a tree are from n/2 + 1 to n indices
        }


        public void display() {
            System.out.println("State of PQ: "+Arrays.asList(PQ));
        }


        private static <T> boolean less(Comparable<T> t1, Comparable<T> t2) {
            return t1.compareTo((T) t2) < 0;
        }
        private static <T> boolean lessOrEqual(Comparable<T> t1, Comparable<T> t2) {
            return t1.compareTo((T) t2) <= 0;
        }

        private static <T> void replace(Comparable<T>[] comparables, int i, int j) {
            comparables[i] = comparables[j];
        }
        private static <T> void exchange(Comparable<T>[] comparables, int i, int j) {
            Comparable<T> comparable = comparables[i];
            comparables[i] = comparables[j];
            comparables[j] = comparable;
        }
    }
}