package algorithms._1array_stack_queue.geeksforgeeks.pointers_sorting_stack_queue.sort.hard;

import java.util.LinkedList;
import java.util.List;

import static algorithms.utils.ArrayUtils.exchange;

/*
Sort a nearly sorted (or K sorted) array in O(n log k)

https://www.geeksforgeeks.org/nearly-sorted-algorithm/

Watch 'https://www.youtube.com/watch?v=7Ks76fPIQzU'.

Insertion Sort vs Heap Sort
---------------------------

Normally, when you see that an array is almost sorted, then you think of using INSERTION SORT algorithm to completely sort it.
But Insertion Sort takes O(nk) to sorted K sorted array.

If you want to sort K sorted array in O(n log k), then you need to use heap sort.

Time complexity analysis of below algorithm
-------------------------------------------

k+1 time for creating a Binary Heap of first k+1 element
    +
(n-k)(log k) time for deleting one element and inserting a new one from k+2 to n elements of an array in Binary Heap.
    +
log k to remove remaining elements from Binary Heap


O(k) + O((n-k) (log k)) + O(log k)
= O(k) + O(n log k) - O(k log k) + O(log k)
= O(n log k)


In this algorithm, I have created my own Min Heap. Instead, you can use Java's PriorityQueue.
See MergeSortedArray.java

*/
public class _1SortKSortedArray {

    public static void main(String[] args) {
        int[] A = {2, 6, 3, 12, 56, 8};
        int k = 3;

        // testingMinHeap(A);

        // Create a Min Heap of size k+1 and inserting first k+1 elements of an array in it.
        // why?
        // Because any element has to be moved only up to only k positions in Binary Heap to bring it at right pos in sorted array, 1st element of sorted array will be found at the most at k+1 pos.
        MIN_BH minHeap = new MIN_BH(k + 1);
        for (int i = 0; i < k + 1; i++) {
            minHeap.insert(A[i]);
        }

        List<Integer> elementsInSortedOrder = new LinkedList<>();

        // Removing one element from Min Heap and inserting one starting from K+1 to last pos of array
        // IMP:
        // Deleting and Inserting an element together.
        // This is a different thing happening in this algorithm compared to a normal Binary Heap algorithm (BinaryHeap.java)
        for (int i = k + 1; i < A.length; i++) {
            int removedElementFromHeap = minHeap.deleteAndInsert(A[i]);
            elementsInSortedOrder.add(removedElementFromHeap);
        }

        // When all elements of an array are inserted in Min Heap, make Min Heap empty
        while (true) {
            int removedElementFromHeap = minHeap.delete();
            if (removedElementFromHeap == Integer.MIN_VALUE) break;
            elementsInSortedOrder.add(removedElementFromHeap);
        }
        System.out.println(elementsInSortedOrder);

    }

    private static void testingMinHeap(int[] A) {
        MIN_BH minHeap = new MIN_BH(A.length);

        // inserting all elements in Min Heap
        for (int num : A) {
            minHeap.insert(num);
        }

        // retrieving all elements from Min Heap. They will come in sorted order.
        List<Integer> elementsInSortedOrder = new LinkedList<>();
        while (true) {
            int removedElementFromHeap = minHeap.delete();
            if (removedElementFromHeap == Integer.MIN_VALUE) break;
            elementsInSortedOrder.add(removedElementFromHeap);
        }
        System.out.println(elementsInSortedOrder);
    }

    /*
        Important concepts to remember for Binary Heap:

        - Binary Heap looks like a Almost Balanced Binary Tree, but it is just an Aux array (PQ).

        - child elements of any element is found at 2*elementPos and 2*elementPos + 1 positions in PQ array.

        - parent element of any element is found at elementPos/2

        - total number of nodes in Binary Heap = 2^h+1 - 1 where h is the height of a tree. h ~= log n.

        - Leaf nodes are found at n/2 + 1 to n positions of PQ.

    */
    static class MIN_BH {

        private int[] PQ;//priority queue uses Binary Heap solution, so this array is named as PQ.
        int n = 0; // total elements inserted in array. Don't make a mistake of initializing it by 1, instead of use ++n in insert() method and n-- in delMax() method.

        public MIN_BH(int capacity) {
            this.PQ = new int[capacity + 1];// size of PQ should be +1 because Binary Heap always starts from index=1 (not index=0)
        }

        // insert a new element at the end of PQ.
        // n++, PQ[n]=element
        // swimUP that element
        public void insert(int element) {
            n++;
            PQ[n] = element;
            swimUp(n / 2);
        }

        private void swimUp(int parentElementPos) {
            if (parentElementPos == 1) return; // there is no left and and right children

            int leftChildElementPos = 2 * parentElementPos;
            int rightChildElementPos = 2 * parentElementPos + 1;

            if (rightChildElementPos <= n) {// if both left and right child exists
                if ((PQ[leftChildElementPos] < PQ[rightChildElementPos]) &&
                        (PQ[parentElementPos] > PQ[leftChildElementPos])) {
                    exchange(PQ, leftChildElementPos, parentElementPos);
                } else if ((PQ[rightChildElementPos] < PQ[leftChildElementPos]) &&
                        (PQ[parentElementPos] > PQ[rightChildElementPos])) {
                    exchange(PQ, rightChildElementPos, parentElementPos);
                }
            } else {// if only left child exist
                if (PQ[parentElementPos] > PQ[leftChildElementPos]) {
                    exchange(PQ, leftChildElementPos, parentElementPos);
                }
            }

            swimUp(parentElementPos / 2);
        }

        // remove an element from the top PQ[1].
        // put last element PQ[n] at PQ[1]
        // reduce the size of PQ by 1 (n--)
        // sinkDown top element
        public int delete() {
            if (n == 1) {
                n--;
                return PQ[1];
            }
            if (n > 1) {
                int elementToBeRemoved = PQ[1];

                int lastElement = PQ[n];
                PQ[1] = lastElement;
                sinkDown(1);

                n--;
                return elementToBeRemoved;

            }

            return Integer.MIN_VALUE; // if n==0;
        }

        private void sinkDown(int parentElementPos) {
            int leftChildElementPos = 2 * parentElementPos;
            int rightChildElementPos = 2 * parentElementPos + 1;

            if (rightChildElementPos <= n) { // if both left and right child exist
                if ((PQ[leftChildElementPos] < PQ[rightChildElementPos]) &&
                        (PQ[parentElementPos] > PQ[leftChildElementPos])) {

                    exchange(PQ, leftChildElementPos, parentElementPos);
                    sinkDown(leftChildElementPos);

                } else if ((PQ[rightChildElementPos] < PQ[leftChildElementPos]) &&
                        (PQ[parentElementPos] > PQ[rightChildElementPos])) {

                    exchange(PQ, rightChildElementPos, parentElementPos);
                    sinkDown(rightChildElementPos);

                }
            } else if (leftChildElementPos <= n) {// if right child does not exist and only left child exist
                if (PQ[parentElementPos] > PQ[leftChildElementPos]) {

                    exchange(PQ, leftChildElementPos, parentElementPos);
                    sinkDown(leftChildElementPos);

                }
            }
        }

        // This is a different thing happening in this algorithm compared to a normal Binary Heap algorithm (BinaryHeap.java)
        // When both delete and insert needs to happen together, you delete from the top and insert new element at the top and sinkDown (instead of inserting a new element at the end and swimUp)
        private int deleteAndInsert(int element) {
            if (n == 0) {
                insert(element);
                return Integer.MIN_VALUE;
            } else {
                int elementToBeRemoved = PQ[1];
                PQ[1] = element;
                sinkDown(1);
                return elementToBeRemoved;
            }
        }

    }
}