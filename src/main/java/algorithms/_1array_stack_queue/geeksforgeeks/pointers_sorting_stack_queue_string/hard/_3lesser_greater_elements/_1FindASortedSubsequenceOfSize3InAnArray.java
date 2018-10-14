package algorithms._1array_stack_queue.geeksforgeeks.pointers_sorting_stack_queue_string.hard._3lesser_greater_elements;

/*
    Find a sorted subsequence of size 3 in linear time  (Sorted Subsequence Triplet)

    https://www.geeksforgeeks.org/find-a-sorted-subsequence-of-size-3-in-linear-time/

    Given an array of n integers, find the 3 elements such that a[i] < a[j] < a[k] and i < j < k in 0(n) time. If there are multiple such triplets, then print any one of them.

    Examples:

    Input:  arr[] = {12, 11, 10, 5, 6, 2, 30}
    Output: 5, 6, 30

    Input:  arr[] = {1, 2, 3, 4}
    Output: 1, 2, 3 OR 1, 2, 4 OR 2, 3, 4

    Input:  arr[] = {4, 3, 2, 1}
    Output: No such triplet


    This is a tricky problem.

    You want to keep track of one smaller and one greater element for each element in an array.
    So, you need to create two arrays (smaller and greater)

    Remember this trick to find out one smaller and one bigger element for each element of array.


    It is easy to do this algorithm in O(n^2).
    To find smaller element in left side, you can just iterate through all left elements.
    To find greater element in right side, you can just iterate through all right elements.

    Harder to do it in O(n).
    If you find smaller element in left side for any element(i), then for next element, you start from that found smaller element. Do not start from extreme left again.
    If you find greater element in right side for any element(i), then for next element, you start from that found greater element. Do not start from extreme right again.


    index 0   1   2   3  4  5  6
         12, 11, 10,  5, 6, 2, 30
         S   i


    if(A[start] < A[i]) smaller[i]=start
    else start=i, smaller[i] = -1


    If A[start] is smaller than A[i],  then it is possible that A[start] can be smaller than A[i+1]. So, just increment i.
    Otherwise, start=i and increment i.


        smaller[1] = -1

    index 0   1   2   3  4  5  6
         12, 11, 10,  5, 6, 2, 30
             S   i

        smaller[2] = -1

    index 0   1   2   3  4  5  6
         12, 11, 10,  5, 6, 2, 30
                  S   i

        smaller[3] = -1

    index 0   1   2   3  4  5  6
         12, 11, 10,  5, 6, 2, 30
                      S  i

        smaller[4] = 3

    index 0   1   2   3  4  5  6
         12, 11, 10,  5, 6, 2, 30
                      S     i

        smaller[5] = -1

    index 0   1   2   3  4  5  6
         12, 11, 10,  5, 6, 2, 30
                            S  i

        smaller[6] = 2
*/
public class _1FindASortedSubsequenceOfSize3InAnArray {

    public static void main(String[] args) {
        int[] A = {12, 11, 10, 5, 6, 2, 30};// 5,6,30
        find(A);
    }

    private static void find(int[] A) {

        int[] smaller = new int[A.length];
        smaller[0] = -1;

        int start = 0;

        for (int i = 1; i < A.length; i++) {

            if (A[start] < A[i]) {
                smaller[i] = start;
            } else {
                smaller[i] = -1;
                start = i;
            }
        }

        int[] greater = new int[A.length];
        greater[A.length - 1] = -1;
        start = A.length - 1;

        for (int i = A.length - 2; i >= 0; i--) {
            if (A[start] > A[i]) {
                greater[i] = start;
            } else {
                greater[i] = -1;
                start = i;
            }
        }

        for (int i = 0; i < A.length; i++) {
            if (smaller[i] != -1 && greater[i] != -1) {
                System.out.println(A[smaller[i]] + "," + A[i] + "," + A[greater[i]]);
                break;
            }
        }

    }
}
