package algorithms.crackingcodinginterviewbook._5recursion_and_dynamic_programming;

// p.g. 347 of Cracking Coding Interview book
/*
    find a given index as an element in array.
    Consider array as a sorted array.

    If Array is not sorted, consider Brute Force algorithm, in which every single element is compared with a value that needs to be searched.
    https://www.youtube.com/watch?v=Fit5lRYZAm4

    If Array is sorted, then right away you should think of Binary Search algorithm.


    0    1   2   3   4   5   6   7   8   9   10
   -40  -20 -30 -10  4   7   9   10  12  13  15

   your code should be able to find index=4 because a[4]=4.

   Interviewer has said that array is sorted, but what happens if he didn't mention that array doesn't have duplicates.
   If array has duplicates, there will be more than one indices having the same value as index value.

    0    1   2   3   4   5   6   7   8   9   10
   -40  -20 -30 -10  4   7   7   7   9   9   15

   It is very important to clarify from an interviewer that there is no duplicate value in an array.
 */
public class _3FindMagicIndex {
    public static void main(String[] args) {
        int a[] = {-40, -20, -30, -10, 4, 7, 9, 10, 12, 13, 15}; // magic index is 4
        //int a[] = {-40, -20, -30, -10, 1, 2, 7, 10, 12, 13, 15}; // magic index is -1
        int magicIndex = findMagicIndex(a);
        System.out.println("magic index: " + magicIndex);
    }

    private static int findMagicIndex(int a[]) {
        return findMagicIndex(a, 0, a.length - 1);
    }

    private static int findMagicIndex(int[] a, int start, int end) {

        if (start > end) return -1;

        if (start == end && a[start] == start) {
            return start;
        }
        if (start == end && a[start] != start) {
            return -1;
        }

        int mid = (start + end) / 2;

        if (mid == a[mid]) return mid;

        // this is the magic condition
        if (mid < a[mid]) {
            return findMagicIndex(a, start, mid - 1);
        }
        return findMagicIndex(a, mid + 1, end);
    }
}
