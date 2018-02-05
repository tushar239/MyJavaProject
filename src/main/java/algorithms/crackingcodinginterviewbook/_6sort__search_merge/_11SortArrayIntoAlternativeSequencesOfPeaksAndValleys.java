package algorithms.crackingcodinginterviewbook._6sort__search_merge;

/*
pg 414 from Cracking Coding Interview book.

Understand PeakAndValleyInUnOrderedArray.java first before doing this algorithm

Peaks and Valleys:
In an array of integers, a "peak" is an element which is greater than of equal to the adjacent integers
and a "valley" is an element which is less than or equal to the adjacent integers.

See PeakAndValleyInUnOrderedArray.java to understand what is peak and what is valley.
*/
public class _11SortArrayIntoAlternativeSequencesOfPeaksAndValleys {

    public static void main(String[] args) {
        {
            int[] A = {9, 1, 0, 4, 8, 7};
            arrangeRecursively(A, 0, A.length - 1);
            for (int a : A) {
                System.out.print(a + ",");
            }
        }

        System.out.println();

        {
            int[] A = {9, 1, 0, 4, 8, 7};
            arrange(A);
            for (int a : A) {
                System.out.print(a + ",");
            }
        }
    }

    private static void arrangeRecursively(int[] A, int start, int end) {

        if (start > end) {
            return;
        }

        // if there are just two elements, nothing to arrange
        if (end <= start + 1) {
            return;
        }

        int firstIndex = start;
        int secondIndex = start + 1;
        int thirdIndex = start + 2;

        int firstElement = A[firstIndex];

        if (secondIndex <= A.length - 1) {

            int secondElement = A[secondIndex];

            if (thirdIndex <= A.length - 1) {

                int thirdElement = A[thirdIndex];

                int max = findMax(firstElement, secondElement, thirdElement);

                if (max == firstElement) {
                    swap(A, start, secondIndex);
                } else if (max == thirdElement) {
                    swap(A, secondIndex, thirdIndex);
                }

            } /*else { // if just two elements are left, nothing to arrange
                int elementBeforeFirstElement = A[start-1];

                int max = findMax(firstElement, secondElement);

                if (firstElement == max) {
                    swap(A, start, start + 1);
                }
            }*/
        }
        arrangeRecursively(A, start + 2, end);// you increment by 2
    }

    private static void arrange(int[] A) {
        if (A.length == 0) return;

        int start = 0;
        int end = A.length - 1;

        if (start > end) {
            return;
        }

        // if there are just two elements, nothing to arrange
        if (end <= 1) {
            return;
        }

        for (int i = 0; i <= end; i = i + 2) {

            int firstIndex = i;
            int secondIndex = i + 1;
            int thirdIndex = i + 2;

            if (firstIndex <= end) {

                int firstElement = A[firstIndex];

                if (secondIndex <= end) {

                    int secondElement = A[secondIndex];

                    if (thirdIndex <= end) {

                        int thirdElement = A[secondIndex];

                        int max = findMax(firstElement, secondElement, thirdElement);

                        if (max == firstElement) {
                            swap(A, firstIndex, secondIndex);

                        } else if (max == thirdElement) {
                            swap(A, secondIndex, thirdIndex);
                        }

                    } else {
                        // nothing needs to be done, if only 2 elements are left
                    }
                } else {
                    // nothing needs to be done, if only 1 element is left
                }
            }

        }
    }

    private static void swap(int[] A, int oneIndex, int secondIndex) {
        int temp = A[oneIndex];
        A[oneIndex] = A[secondIndex];
        A[secondIndex] = temp;

        /*for (int a : A) {
            System.out.print(a + ",");
        }
        System.out.println();*/
    }

    private static int findMax(int a, int b, int c) {
        return Math.max(a, Math.max(b, c));
    }
}
