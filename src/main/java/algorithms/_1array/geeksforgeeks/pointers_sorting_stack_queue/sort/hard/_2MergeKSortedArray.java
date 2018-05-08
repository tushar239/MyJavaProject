package algorithms._1array.geeksforgeeks.pointers_sorting_stack_queue.sort.hard;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/*
    Merge k sorted arrays

    https://www.geeksforgeeks.org/merge-k-sorted-arrays/
    https://www.youtube.com/watch?v=6bvnZzwiKzs

    e.g.
        n=4 and k=3

        int[] A1 = {2, 6, 12, 34}
        int[] A2 = {1, 9, 20, 1000}
        int[] A3 = {23, 34, 90, 2000}

        output: {1,2,6,9,12,20,23,34,34,90,1000,2000}


    Simple Solution:

    Create an output array of size n*k and one by one copy all arrays to it.
    Finally, sort the output array using any O(nLogn) sorting algorithm. This approach takes O(nk log nk) time.

    Better Solution:

    It takes O(nk log k)

    - Create PriorityQueue of size k.
    - Add first element from each array to PQ. --- O(k)
    - Poll an element from PQ and add an element to PQ from an array to which that polled element belongs to.
      Repeat this step until all arrays elements are in PQ. --- (nk log k)
    - Poll remaining elements from PQ. --- O(log k)


*/
public class _2MergeKSortedArray {

    public static void main(String[] args) {
        int[] A1 = {2, 6, 12, 34};
        int[] A2 = {1, 9, 20, 1000};
        int[] A3 = {23, 34, 90, 2000};

        int[][] A = {A1, A2, A3};
        merge(A, A1.length, A.length);
    }

    private static void merge(int[][] A, int n, int k) {


        PriorityQueue<PQNode> pq = new PriorityQueue<>(k);

        // add first element from each array to pq
        for (int i = 0; i < k; i++) {
            pq.add(new PQNode(A[i][0], i));
        }

        // set pointers of all array to second element because first element from each array is inserted in PQ
        int[] arrayPointers = new int[k];
        for (int i = 0; i < k; i++) {
            arrayPointers[i] = 1;
        }

        List<Integer> merged = new LinkedList<>();


        boolean elementsFromAllArraysAdded = false;

        while (!pq.isEmpty()) {

            PQNode minElement = pq.poll();
            merged.add(minElement.value);
            //System.out.println(minElement.value);

            if (!elementsFromAllArraysAdded) {

                boolean added = false;

                int arrayNumber = minElement.arrayNumber;

                if (arrayPointers[arrayNumber] < n) {
                    pq.add(new PQNode(A[arrayNumber][arrayPointers[arrayNumber]], arrayNumber));
                    arrayPointers[arrayNumber]++;
                    added = true;
                } else {
                    for (int i = 0; i < arrayPointers.length; i++) {
                        if (arrayPointers[i] < n) {
                            pq.add(new PQNode(A[i][arrayPointers[i]], i));
                            arrayPointers[i]++;
                            added = true;
                            break;
                        }
                    }
                }
                if (!added) elementsFromAllArraysAdded = true;
            }

        }

        System.out.println(merged);
    }

    private static class PQNode implements Comparable<PQNode> {
        int value;
        int arrayNumber;

        public PQNode(int value, int arrayNumber) {
            this.value = value;
            this.arrayNumber = arrayNumber;
        }

        public int getValue() {
            return value;
        }

        public int getArrayNumber() {
            return arrayNumber;
        }

        @Override
        public int compareTo(PQNode o) {
            if (this.value < o.value) return -1;
            if (this.value > o.value) return 1;
            return 0;
        }
    }
}
