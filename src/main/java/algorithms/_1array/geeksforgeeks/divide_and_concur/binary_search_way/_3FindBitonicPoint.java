package algorithms._1array.geeksforgeeks.divide_and_concur.binary_search_way;

/*
    Find bitonic point in given bitonic sequence

    https://www.geeksforgeeks.org/find-bitonic-point-given-bitonic-sequence/

    A Bitonic Sequence is a sequence of numbers which is first strictly increasing then after a point strictly decreasing.

    A Bitonic Point is a point in bitonic sequence before which elements are strictly increasing and after which elements are strictly decreasing.
    A Bitonic point doesn't exist if array is only decreasing or only increasing.

    Input : arr[] = {6, 7, 8, 11, 9, 5, 2, 1}
    Output: 11
    All elements before 11 are smaller and all
    elements after 11 are greater.

    This is a classic Binary Search problem.
*/
public class _3FindBitonicPoint {
    public static void main(String[] args) {
        {
            int arr[] = {6, 7, 8, 11, 9, 5, 2, 1};
            int index = binarySearch(arr, 0, arr.length - 1);
            System.out.println("index: " + index);//3
        }
        {
            int arr[] = {6, 11, 10, 9, 5, 2, 1};
            int index = binarySearch(arr, 0, arr.length - 1);
            System.out.println("index: " + index);//1
        }
        {
            int arr[] = {6, 7, 8, 11, 12, 13, 14, 15};
            int index = binarySearch(arr, 0, arr.length - 1);
            System.out.println("index: " + index);//-1
        }
        {
            int arr[] = {6, 5, 4, 3, 2, 1};
            int index = binarySearch(arr, 0, arr.length - 1);
            System.out.println("index: " + index);//-1
        }

    }

    private static int binarySearch(int arr[], int start, int end) {
        if (start > end) return -1;

        int mid = (start + end) / 2;

        if (mid > start && mid < end) {

            // base condition to check if arr[mid] is bitonic point or not
            if (arr[mid - 1] < arr[mid] && arr[mid] > arr[mid + 1]) {
                return mid;
            }

            if (arr[mid + 1] < arr[mid]) {
                return binarySearch(arr, start, mid - 1);
            } else {
                return binarySearch(arr, mid + 1, end);
            }
        }

        return -1;

    }
}
