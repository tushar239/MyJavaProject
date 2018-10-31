package algorithms._1array_stack_queue.geeksforgeeks.divide_and_concur.binary_search_way.medium;

/*
    Find the Rotation Count in Rotated Sorted array

    https://www.geeksforgeeks.org/find-rotation-count-rotated-sorted-array/

    example:

    Input : arr[] = {15, 18, 2, 3, 6, 12}
    Output: 2 if rotation happened to right side. 4 if rotation happened to left side
    Explanation : Initial array must be {2, 3, 6, 12, 15, 18}.
    We get the given array after rotating the initial array twice.

    Input: arr[] = {7, 9, 11, 12, 15};
    Output: 0


    Above example as assumed that rotation happened to right side.
    My algorithm assumes that rotation happened to left side.


    Brute-Force approach of finding an element that is < than prev one takes O(n).
    To reduce the time complexity, you can use divide-and-concur Binary Search style that takes O(log n).

    Important:
        This algorithm is easy to implement, but trickier part is to ask an interviewer whether rotation happened to right side or left side.

    Important:
        This algorithm teaches you some important concepts to remember when you are using Binary Search style divide and concur.
        Read ArrayFundamentals.java Divide and Concur section.

*/
public class _1FindRotationCountInRotatedSortedArray {

    public static void main(String[] args) {
        {
            {
                int arr[] = {7, 9, 11, 12, 5};
                int numberOfRotations = find(arr, 0, arr.length - 1);
                System.out.println(numberOfRotations);//1
            }
            {
                //int arr[] = {15, 18, 2, 3, 4, 5};//2
                int arr[] = {14, 15, 18, 1, 2, 3, 5};//3
                //int arr[] = {1, 2, 3, 5, 14, 15, 18};//0
                //int arr[] = {18, 1, 2, 3, 5};//1
                //int arr[] = {1};//0
                //int arr[] = {};//0
                int numberOfRotations = find_better_way(arr, 0, arr.length - 1);
                System.out.println(numberOfRotations);
            }

        }
        {
            int arr[] = {9, 11, 12, 5, 7};
            int numberOfRotations = find(arr, 0, arr.length - 1);
            System.out.println(numberOfRotations);//2
        }
        {
            int arr[] = {7, 9, 11, 12, 15};
            int numberOfRotations = find(arr, 0, arr.length - 1);
            System.out.println(numberOfRotations);//-1
        }
    }

    private static int find(int[] arr, int start, int end) {
        if (start > end) return -1;

        int mid = (start + end) / 2;

        // It is an important exit condition.
        // you are using arr[mid+1] later in the code. It can throw ArrayIndexOutOfBoundException. To avoid that, mid+1 has to be <= end (mid has to be < end). So, you must have an exit condition checking mid == end.
        if (mid == end) return -1;

        //if (mid < end) {
        if (arr[mid] > arr[mid + 1]) {
            return end - mid;
        } else if (arr[mid] <= arr[mid + 1]) {
            return find(arr, mid + 1, end);
        }
        //}

        return find(arr, start, mid - 1);
    }

    /*

         Before you do this algorithm, read BitonicPoint.java


         int A[] = {14, 15, 18, 1, 2, 3, 5}

         You need 3 rotations to make int A[] = {1, 2, 3, 5, 14, 15, 18}


         Here, main scenario is mid=18. You need to check 18 with next number 1.
         To check A[mid+1], you need to have a condition (mid < A.length-1).

         if (mid < A.length - 1)
            if (A[mid] > A[mid + 1]) // mid=18 scenario
                total number of rotations = mid+1
            else
                if(A[mid] > A[A.length-1]) // mid = 15 or 14 scenario. check mid with 5.
                    find(A, mid+1, end)
                else                       // mid = 2 scenario
                    find(A, start, mid-1)
         else                              // mid=5 scenario (mid will become 5 only in the last recursion call)
            return 0
    */
    private static int find_better_way(int[] A, int start, int end) {
        if (start > end) return 0;

        int mid = (start + end) / 2;


        if (mid < A.length - 1) {
            if (A[mid + 1] < A[mid]) {          //mid=18 or 14 scenario where 18 or 14 > 1
                return mid + 1;
            } else {
                if (A[mid] > A[A.length - 1]) { //mid=15 scenario, where 15 > 5
                    return find_better_way(A, mid + 1, end);
                }
                else {                          //mid=1 scenario, where 1 < 5
                    return find_better_way(A, start, mid - 1);
                }
            }
        }

        //if (mid == A.length - 1) { // mid=5 scenario
        return 0;
        //}

    }


}
