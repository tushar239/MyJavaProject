package algorithms._1array_stack_queue.geeksforgeeks.pointers_sorting_stack_queue_string.sort.hard;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/*
    Merge k sorted arrays (k is a number of sorted arrays)

    It is also called K-Way Merge.

    https://www.geeksforgeeks.org/merge-k-sorted-arrays/
    and
    https://www.geeksforgeeks.org/merge-k-sorted-arrays-set-2-different-sized-arrays/

    https://www.youtube.com/watch?v=6bvnZzwiKzs

    e.g.
        n=4 and k=3

        int[] A1 = {2, 6, 12, 34}
        int[] A2 = {1, 9, 20, 1000}
        int[] A3 = {23, 34, 90, 2000}

        output: {1,2,6,9,12,20,23,34,34,90,1000,2000}


    Simple Solution:

    Create an output array of size n*k and one by one copy all arrays to it.
    Finally, sort the output array using any O(n log n) sorting algorithm. This approach takes O(nk log nk) time.

    Better Solution:

    It takes O(nk log k)

    - Create PriorityQueue of size k.
    - Add first element from each array to PQ. --- O(k)
    - Poll an element from PQ and add an element to PQ from an array to which that polled element belongs to.
      Repeat this step until all arrays elements are in PQ. --- (nk log k)
    - Poll remaining elements from PQ. --- O(log k)


    This algorithm is a good example of Comparator and Comparable interfaces.

*/
public class _2MergeSortedArrays_OR_K_Way_Merge {

    public static void main(String[] args) {
//        int[] A1 = {2, 6, 12, 34};
//        int[] A2 = {1, 9, 20, 1000};
//        int[] A3 = {23, 34, 90, 2000};

        int[] A1 = {1,3};
        int[] A2 = {2,4,6};
        int[] A3 = {0,9,10,11};

        int[][] A = {A1, A2, A3};
        merge(A, A1.length, A.length);//[0, 1, 2, 3, 4, 6, 9, 10, 11]
    }

    private static void merge(int[][] A, int n, int k) {

        PriorityQueue<PQNode> pq = new PriorityQueue<>(k);
        // with Comparable implemented by PQNode

        // or

        /* using Comparator

        PriorityQueue<PQNode> pq = new PriorityQueue<>(k, new Comparator<PQNode>() {
            @Override
            public int compare(PQNode o1, PQNode o2) {
                if(o1.value < o2.value) return -1;
                if(o1.value > o2.value) return 1;
                return 0;
            }
        });*/

        // add first element from each array to pq
        for (int i = 0; i < k; i++) {
            // storing ele's value, its array number and position in the array in PQNode.
            pq.add(new PQNode(A[i][0], i, 0));
        }

        List<Integer> merged = new LinkedList<>();

        while(!pq.isEmpty()) {

            // poll an element and add an element from the same array that polled element belong to.
            PQNode ele = pq.poll();

            int value = ele.value;
            int arrayNumber = ele.arrayNumber;
            int posInArray = ele.posInArray;

            merged.add(value);

            if(posInArray < A[arrayNumber].length-1) {
                pq.add(new PQNode(A[arrayNumber][posInArray+1], arrayNumber, posInArray+1));
            }

        }
        /*// set pointers of all array to second element because first element from each array is inserted in PQ
        int[] arrayPointers = new int[k];
        for (int i = 0; i < k; i++) {
            arrayPointers[i] = 1;
        }*/



        /*boolean elementsFromAllArraysAdded = false;

        while (!pq.isEmpty()) {

            PQNode ele = pq.poll();
            merged.add(ele.value);
            //System.out.println(ele.value);

            if (!elementsFromAllArraysAdded) {

                boolean added = false;

                int arrayNumber = ele.arrayNumber;

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

        }*/

        System.out.println(merged);
    }

    private static class PQNode implements Comparable<PQNode> {
        int value;
        int arrayNumber;
        int posInArray;

        public PQNode(int value, int arrayNumber, int posInArray) {
            this.value = value;
            this.arrayNumber = arrayNumber;
            this.posInArray = posInArray;
        }

        public int getValue() {
            return value;
        }

        public int getArrayNumber() {
            return arrayNumber;
        }

        public int getPosInArray() {
            return posInArray;
        }

        @Override
        public int compareTo(PQNode o) {
            if (this.value < o.value) return -1;
            if (this.value > o.value) return 1;
            return 0;
        }
    }
}
