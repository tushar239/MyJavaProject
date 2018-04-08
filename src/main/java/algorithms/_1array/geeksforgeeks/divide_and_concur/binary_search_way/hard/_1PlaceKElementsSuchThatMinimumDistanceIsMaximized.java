package algorithms._1array.geeksforgeeks.divide_and_concur.binary_search_way.hard;

import java.util.Arrays;

/*
    Place k elements such that minimum distance is maximized

    https://www.geeksforgeeks.org/place-k-elements-such-that-minimum-distance-is-maximized/

    This problem is harder to understand.
    It says that place K objects on K array elements in such a way that distance between any two objects can be maximized.

*/
public class _1PlaceKElementsSuchThatMinimumDistanceIsMaximized {

    public static void main(String[] args) {
        int arr[] = {1, 2, 8, 4, 9};
        int n = arr.length;
        int k = 3;
        System.out.print(largestMinDist(arr, n, k));
    }

    // Returns largest minimum distance for
    // k elements in arr[0..n-1]. If elements
    // can't be placed, returns -1.
    static int largestMinDist(int arr[], int n,
                              int k) {
        // Sort the positions
        Arrays.sort(arr);

        // Initialize result.
        int res = -1;

        // Consider the maximum possible distance
        int left = arr[0], right = arr[n - 1];

        // Do binary search for largest
        // minimum distance
        while (left < right) {
            int mid = (left + right) / 2;

            // If it is possible to place k
            // elements with minimum distance mid,
            // search for higher distance.
            if (isFeasible(mid, arr, n, k)) {
                // Change value of variable max to
                // mid if all elements can be
                // successfully placed
                res = Math.max(res, mid);
                left = mid + 1;
            }

            // If not possible to place k elements,
            // search for lower distance
            else
                right = mid;
        }

        return res;
    }

    // Returns true if it is possible to
    // arrange k elements of arr[0..n-1]
    // with minimum distance given as mid.
    static boolean isFeasible(int mid, int arr[],
                              int n, int k) {
        // Place first element at arr[0] position
        int pos = arr[0];

        // Initialize count of elements placed.
        int elements = 1;

        // Try placing k elements with minimum
        // distance mid.
        for (int i = 1; i < n; i++) {
            if (arr[i] - pos >= mid) {
                // Place next element if its
                // distance from the previously
                // placed element is greater
                // than current mid
                pos = arr[i];
                elements++;

                // Return if all elements are
                // placed successfully
                if (elements == k)
                    return true;
            }
        }
        return false;
    }

}
