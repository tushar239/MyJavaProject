package algorithms._1array.geeksforgeeks.pointers_and_sorting.sort.hard;

import java.util.LinkedList;
import java.util.List;

import static algorithms.utils.ArrayUtils.exchange;

/*
Sort a nearly sorted (or K sorted) array

https://www.geeksforgeeks.org/nearly-sorted-algorithm/

*/
public class _1SortKSortedArray {

    public static void main(String[] args) {
        int[] A = {2, 6, 3, 12, 56, 8};
        int k = 3;

        // testingMinHeap(A);

        // Create a Min Heap of size k+1 and inserting first k+1 elements of an array in it. Because .... TODO:
        MIN_BH minHeap = new MIN_BH(k + 1);
        for (int i = 0; i < k + 1; i++) {
            minHeap.insert(A[i]);
        }

        List<Integer> elementsInSortedOrder = new LinkedList<>();

        // removing one element from Min Heap and inserting one from starting from K+1 pos of array
        for (int i = k + 1; i < A.length; i++) {
            int removedElementFromHeap = minHeap.deleteAndInsert(A[i]);
            elementsInSortedOrder.add(removedElementFromHeap);
        }

        // when all elements of an array are inserted in Min Heap, make Min Heap empty
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

    static class MIN_BH {

        private int[] PQ;//priority queue uses Binary Heap solution, so this array is named as PQ.
        int n = 0; // total elements inserted in array. Don't make a mistake of initializing it by 1, instead of use ++n in insert() method and n-- in delMax() method.

        public MIN_BH(int capacity) {
            this.PQ = new int[capacity + 1];
        }

        public void insert(int element) {
            n++;
            PQ[n] = element;
            swimUp(n / 2);
        }

        private void swimUp(int parentElementPos) {
            if (parentElementPos >= 1) {
                int leftChildElementPos = 2 * parentElementPos;
                int rightChildElementPos = 2 * parentElementPos + 1;

                if (rightChildElementPos <= n) {// if right child exists
                    if ((PQ[leftChildElementPos] < PQ[rightChildElementPos]) &&
                            (PQ[parentElementPos] > PQ[leftChildElementPos])) {
                        exchange(PQ, leftChildElementPos, parentElementPos);
                    } else if ((PQ[rightChildElementPos] < PQ[leftChildElementPos]) &&
                            (PQ[parentElementPos] > PQ[rightChildElementPos])) {
                        exchange(PQ, rightChildElementPos, parentElementPos);
                    }
                } else {
                    if (PQ[parentElementPos] > PQ[leftChildElementPos]) {
                        exchange(PQ, leftChildElementPos, parentElementPos);
                    }
                }

                swimUp(parentElementPos / 2);
            }
        }


        private int delete() {
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