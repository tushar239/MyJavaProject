package algorithms._1array_stack_queue.geeksforgeeks.divide_and_concur.binary_search_way.easy;

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
public class _2FindBitonicPoint {
    public static void main(String[] args) {
        {
//            int arr[] = {6, 7, 8, 11, 9, 5, 2, 1};
            int arr[] = {1, 2, 3, 4, 3};
//            int arr[] = {1, 2};

//            int index = binarySearch_doesnt_work(arr, 0, arr.length - 1);
//            System.out.println("index: " + index);//3

            int index = binarySearch_another(arr, 1, arr.length - 2);
            System.out.println("index: " + index);//3

            index = binarySearch_better_way(arr, 0, arr.length - 1);
            System.out.println("index: " + index);//3

        }
        /*{
            int arr[] = {6, 11, 10, 9, 5, 2, 1};

            int index = binarySearch(arr, 0, arr.length - 1);
            System.out.println("index: " + index);//1

            index = binarySearch_another(arr, 1, arr.length - 2);
            System.out.println("index: " + index);//1
        }
        {
            int arr[] = {6, 7, 8, 11, 12, 13, 14, 15};
            int index = binarySearch(arr, 0, arr.length - 1);
            System.out.println("index: " + index);//-1

            index = binarySearch_another(arr, 1, arr.length - 2);
            System.out.println("index: " + index);//-1

        }
        {
            int arr[] = {6, 5, 4, 3, 2, 1};
            int index = binarySearch(arr, 0, arr.length - 1);
            System.out.println("index: " + index);//-1

            index = binarySearch_another(arr, 1, arr.length - 2);
            System.out.println("index: " + index);//-1
        }

        {

            int arr[] = {6, 5, 8, 11, 11, 12, 13, 15};

            int index = binarySearch(arr, 0, arr.length - 1);
            System.out.println("index: " + index);//-1

            index = binarySearch_another(arr, 1, arr.length - 2);
            System.out.println("index: " + index);//-1

        }*/

    }

    private static int binarySearch_doesnt_work(int arr[], int start, int end) {
        if (start > end) return -1;

        int mid = (start + end) / 2;

        if (mid == start || mid == end) return -1;// important exit condition ---- doesn't work for input {1,2,3,4,3}

        // exit condition to check if arr[mid] is bitonic point or not
        if (arr[mid - 1] < arr[mid] && arr[mid] > arr[mid + 1]) {
            return mid;
        }

        if (arr[mid + 1] < arr[mid]) {
            return binarySearch_doesnt_work(arr, start, mid - 1);// search in left half
        }
        return binarySearch_doesnt_work(arr, mid + 1, end);// search in right half

    }

    private static int binarySearch_better_way(int arr[], int start, int end) {
        if (start == end) {
            return -1;
        }

        int mid = (start + end) / 2;

        /*
            IMPORTANT

            When you need to to access
                  A[mid-1], it is must to check whether mid == 0   (not mid == start)
                  A[mid+1], it is must to check whether mid == A.length-1  (not mid == A.length-1)

         */
        if ((mid - 1) < 0 || (mid + 1) >= arr.length) {// Important condition
            return -1;
        }
        /*if (mid == start || mid == end) {// Important condition: This won't work for {1,2,3,4,3}
            return -1;
        }*/

        // exit condition to check if arr[mid] is bitonic point or not
        if (arr[mid - 1] < arr[mid] && arr[mid] > arr[mid + 1]) {
            return mid;
        }

        if (arr[mid + 1] < arr[mid] && arr[mid] < arr[mid - 1]) {
            return binarySearch_better_way(arr, start, mid - 1);
        }
        return binarySearch_better_way(arr, mid + 1, end);

    }

    // IMPORTANT: instead of start=0, send start=1 and instead of sending end=A.length-1, send A.length-2
    // I wouldn't remember this approach. Above one is better.
    private static int binarySearch_another(int arr[], int start, int end) {
        if (start > end) return -1;

        int mid = (start + end) / 2;

        //if (mid == start || mid == end) return -1;// important exit condition

        // exit condition to check if arr[mid] is bitonic point or not
        if (arr[mid - 1] < arr[mid] && arr[mid] > arr[mid + 1]) {
            return mid;
        }

        if (arr[mid + 1] < arr[mid]) {
            return binarySearch_another(arr, start, mid - 1);
        }
        return binarySearch_another(arr, mid + 1, end);

    }
}
