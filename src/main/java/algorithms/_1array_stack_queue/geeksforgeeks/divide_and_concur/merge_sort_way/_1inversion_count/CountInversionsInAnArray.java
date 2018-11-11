package algorithms._1array_stack_queue.geeksforgeeks.divide_and_concur.merge_sort_way._1inversion_count;

import java.util.Arrays;

/*
    Count Inversions in an array | Set 1 (Using Merge Sort)
    https://www.geeksforgeeks.org/counting-inversions/

    Count inversions in an array | Set 2 (Using Self-Balancing BST)
    https://www.geeksforgeeks.org/count-inversions-in-an-array-set-2-using-self-balancing-bst/


    Two elements A[i] and A[j] form an inversion, if A[i] > A[j] and i < j

    e.g.
    2   4   1   3   5

    Possible inversions = (2,1) (4,1) (4,3)


    Brute-Force approach takes O(n^2)

        for(int i=0; i<A.length; i++)
            for(int j=i+1; j<A.length; j++)
                if(A[i] > A[j])
                    print A[i] and A[j]


    To reduce O(n^2) to O(n), somehow i and j should be moving together. Try it. It won't be possible.

    So, last option is O(n log n). Use divide and concur using one of the sorting algorithms.

    When you think of divide-and-concur, think whether is it possible using quick sort?
    Try 9,5,4,2,1,7. You will miss (2,1) in output.
    When you think of merge sort, draw entire tree before reaching to any conclusion.

                9,5,4,2,1,7
                    |
            (9,5,4)     (2,1,7)
               |           |
          (9,5) (4)     (2,1)  (7)
            |             |
         (9) (5)       (2) (1)

        before concurring (9) and (5), you can say 9 > 5, so one of the output is (9,5)
        then before concurring (5,9) and (4), you can say 5 > 4 and 9 > 4, so possible outputs are (5,4),(9,4)


        before concurring (2) and (1), you can say 2 > 1, so one of the output is (2,1)
        before concurring (1,2) and (7), you can say there is no possible output

        then before concurring (4,5,9) and (1,2,7), you can say possible outputs are (4,1),(4,2),(5,1),(5,2),(9,7)


    I tried Merge Sort, Quick Sort and BST.

    - Quick Sort solution doesn't work
    - BST solution is very root_to_leaf_problems_hard to implement when array has duplicate elements.
      Even though, you make it work, it takes O(n^2) in worst case because it takes O(n^2) to create BST when array is already sorted because created BST will be totally unbalanced.
      You can use Self-Balancing BST (AVL/Red-Black) to get O(n log n).
    - Merge Sort solution works fine.

    Watch "Count inversions in an array.mp4"
*/
public class CountInversionsInAnArray {

    public static void main(String[] args) {
        System.out.println("Trying BST: ");
        {
            int[] A = {2, 4, 1, 3, 5};//3 - (2,1) (1,3) (1,5)
            int totalInversions = countInversions_using_bst(A);
            System.out.println(totalInversions);

        }

        System.out.println("Trying Quick Sort: ");

        {
            int[] A = {2, 4, 1, 3, 5};//3 - (2,1) (1,3) (1,5)
            int[] copiedA = Arrays.copyOf(A, A.length);
            int totalInversions = quickSort(copiedA, 0, A.length - 1);
            System.out.println(totalInversions);
            /*for (int i : A) {
                System.out.print(i + ",");
            }*/

        }

        System.out.println("Trying Quick Sort: ");

        {
            //int[] A = {2, 4, 0, 4, 1, 3, 5};//7 - (2,0) (2,1) (4,0) (4,1) (4,3) (4,1) (4,3)
            int[] A = {5, 4, 0, 4};//4 - (5,4) (5,0) (5,4) (4,0)
            int[] copiedA = Arrays.copyOf(A, A.length);
            int totalInversions = quickSort(copiedA, 0, A.length - 1);
            System.out.println(totalInversions);
            /*for (int i : A) {
                System.out.print(i+",");
            }*/

        }

        System.out.println("Trying Merge Sort: ");
        {
            int[] A = {2, 4, 0, 4, 1, 3, 5};//7 - (2,0) (2,1) (4,0) (4,1) (4,3) (4,1) (4,3)
//            int[] A = {5, 4, 0, 4};//4 - (5,4) (5,0) (5,4) (4,0)
            int totalInversions = mergeSort(A);
            System.out.println(totalInversions);
        }

    }

    /*
                                5

                         3

                  1             4(inversionCount=2)

                       2(inversionCount=1)

     Assumption: There are no duplicates in an array.
                 If there are duplicates (2,5,0,4,1,3,5) or (2,4,0,4,1,3,5), it is root_to_leaf_problems_hard to handle using a Tree solution.

     Inserting all element in BST takes O(n^2) in worst case when BST is not Balanced. So, time complexity of this approach is O(n^2)
     To improve the time complexity to O(n log n), you can use Self-Balancing trees like AVL Tree or RBT (Red-Black Tree) that is total balanced.
     https://www.geeksforgeeks.org/count-inversions-in-an-array-set-2-using-self-balancing-bst/
     */
    private static int countInversions_using_bst(int[] A) {
        if (A == null || A.length == 1) return 0;

        int totalNumberOfInversions = 0;

        BST_For_InversionCount_Algorithm bst = new BST_For_InversionCount_Algorithm(A[A.length - 1]);

        for (int i = A.length - 2; i >= 0; i--) {// important: you need create a tree from end to start of an array
            BSTNode_For_InversionCount_Algorithm insertedNode = bst.insert(A[i]);
            totalNumberOfInversions += insertedNode.getInversionCount();
        }

        return totalNumberOfInversions;
    }

    /*

    Quick Sort doesn't give right result

    Quick Sort doesn't give right result for an input having duplicate elements. It works fine, when there are no duplicates.


    Case 1:

        In Quick Sort, you have to exchange pivot with pIndex and then sort both sides of pIndex.
        When you exchange pivot with pIndex, you have basically changed the end element of right half.

        i
        5   3   1   6   4   2
       pIndex              pivot


                i
        5   3   1   6   4   2
       pIndex              pivot

        now 2 > 1, so exchange i and pIndex and increment both

        At this point, you might think that number of possible inversions are i-pIndex = (5,3), (5,1), (3,1)

                    i
        1   3   5   6  4   2
          pIndex           pivot


                       i
        1   3   5   6  4   2
          pIndex           pivot


                           i
        1   3   5   6  4   2
          pIndex           pivot

        Now, exchange pIndex and pivot. As soon as you do that, you have to change the position of 2 and so possible inversions (6,2) and(6,4) are no more possible.


    Case 2:

        You have to avoid the exchanging elements when both elements are same, otherwise you will get totalInversion=6 for A={5,3,3,1} that is wrong. (5,3),(5,3),(5,1),(3,3),(3,1),(3,1)
    */
    private static int quickSort(int[] A, int start, int end) {
        if (start >= end) return 0; //exit condition

        int pivot = end; // start pivot from end after putting some random number at the end
        int pIndex = start;

        int totalInversions = 0;

        for (int i = start; i < end; i++) {
            if (greater(A[pivot], A[i])) { // if pivot is greater than i, then exchange i with pIndex and increment pIndex
                if (/*i != pIndex &&*/ A[i] != A[pIndex]) { // important
                    int numberToAdd = i - pIndex;
                    totalInversions = totalInversions + Math.abs(i - pIndex);
//                    System.out.println(pIndex + " to " + i + ", " + A[pIndex] + " to " + A[i]);
                }
                exchange(A, i, pIndex);
                pIndex++;
            }
        }
        if (/*pivot != pIndex &&*/ A[pivot] != A[pIndex]) {// important
            totalInversions = totalInversions + Math.abs(pivot - pIndex);
//            System.out.println(pIndex + " to " + pivot + ", " + A[pIndex] + " to " + A[pivot]);
            exchange(A, pIndex, pivot);
        }

        int totalInversionsFromLeft = quickSort(A, start, pIndex - 1);
        int totalInversionsFromRight = quickSort(A, pIndex + 1, end);

        return totalInversions + totalInversionsFromLeft + totalInversionsFromRight;
    }

    private static void exchange(int[] comparables, int i, int j) {
        int comparable = comparables[i];
        comparables[i] = comparables[j];
        comparables[j] = comparable;
    }

    private static boolean greater(int t1, int t2) {
        return t1 > t2;
    }

    private static int mergeSort(int[] A) {
        return divide(A);
    }

    private static int divide(int[] A) {
        if (A.length == 0) return 0;
        if (A.length == 1) return 0;//important

        int mid = A.length / 2;

        int[] L = new int[mid];
        int count = 0;
        for (int i = 0; i < mid; i++) {
            L[count] = A[i];
            count++;
        }

        int[] R = new int[A.length - mid];
        count = 0;
        for (int i = mid; i < A.length; i++) {
            R[count] = A[i];
            count++;
        }

        int inversionCountFromLeft = divide(L);
        int inversionCountFromRight = divide(R);

        int inversionCount = concur(A, L, R);
//        System.out.println(inversionCount);

        return inversionCount + inversionCountFromLeft + inversionCountFromRight;

    }

    private static int concur(int[] A, int[] L, int[] R) {
        //System.out.println("Calling deleteRootAndMergeItsLeftAndRight for "+"L="+Arrays.asList(L)+" ,R="+Arrays.asList(R));
        int i = 0;
        int j = 0;
        int k = 0;

        int inversionCount = 0;
        // concur L and R elements in original array A
        while (i < L.length && j < R.length) {
            if (lessOrEqual(L[i], R[j])) {
                A[k] = L[i];
                i++;
                k++;
            } else {
                inversionCount += (L.length - i); // IMPORTANT
                A[k] = R[j];
                j++;
                k++;
            }
        }
        while (i < L.length) {
            A[k] = L[i];
            i++;
            k++;
        }
        while (j < R.length) {
            A[k] = R[j];
            j++;
            k++;
        }

        return inversionCount;
    }

    private static boolean lessOrEqual(int t1, int t2) {
        return t1 <= t2;
    }


}
