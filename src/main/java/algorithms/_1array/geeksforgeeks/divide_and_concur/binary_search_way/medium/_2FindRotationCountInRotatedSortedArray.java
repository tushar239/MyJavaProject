package algorithms._1array.geeksforgeeks.divide_and_concur.binary_search_way.medium;

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
public class _2FindRotationCountInRotatedSortedArray {

    public static void main(String[] args) {
        {
            int arr[] = {7, 9, 11, 12, 5};
            int numberOfRotations = binarySearch(arr, 0, arr.length - 1);
            System.out.println(numberOfRotations);//1
        }
        {
            int arr[] = {9, 11, 12, 5, 7};
            int numberOfRotations = binarySearch(arr, 0, arr.length - 1);
            System.out.println(numberOfRotations);//2
        }
        {
            int arr[] = {7, 9, 11, 12, 15};
            int numberOfRotations = binarySearch(arr, 0, arr.length - 1);
            System.out.println(numberOfRotations);//-1
        }
    }

    private static int binarySearch(int[] arr, int start, int end) {
        if (start > end) return -1;

        int mid = (start + end) / 2;

        // It is an important exit condition.
        // you are using arr[mid+1] later in the code. It can throw ArrayIndexOutOfBoundException. To avoid that, mid+1 has to be <= end (mid has to be < end). So, you must have an exit condition checking mid == end.
        if (mid == end) return -1;

        //if (mid < end) {
        if (arr[mid] > arr[mid + 1]) {
            return end - mid;
        } else if (arr[mid] <= arr[mid + 1]) {
            return binarySearch(arr, mid + 1, end);
        }
        //}

        return binarySearch(arr, start, mid - 1);
    }
}
