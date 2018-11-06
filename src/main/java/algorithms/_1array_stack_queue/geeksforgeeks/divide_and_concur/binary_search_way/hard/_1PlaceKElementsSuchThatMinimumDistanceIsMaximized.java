package algorithms._1array_stack_queue.geeksforgeeks.divide_and_concur.binary_search_way.hard;

import java.util.Arrays;

/*
    Place k elements such that minimum distance is maximized

    https://www.geeksforgeeks.org/place-k-elements-such-that-minimum-distance-is-maximized/

    This problem is harder to understand.
    It says that place K objects on K array elements in such a way that distance between any two objects can be maximized.

    1. sort an array, so that it is easier to place the objects and find the distance between them.
    2. decide distance between two objects as mid element (  left=A[start], right=A[end], distance=(left+right)/2  )
    3. iterate entire array.
        place 1st object on 1st element always because that's the smallest possible element and one object should always be placed on it.
        find second element in remaining array whose distance from 1st object is >= mid
        then third  element, whose distance from 2nd object is >= mid
        and so on till you place K objects.

        If you can't place all K objects with distance from each other >= mid, then you need to reduce the distance (right=mid) and start from step 2 again.
        If you can place all K objects with distance from each other >= mid, then you need to increase the distance (left=mid+1) and start from step 2 again.

    example:

    points on a line : [1   2   4   8   9], place 3 objects on a line in such a way that distance between adjacent objects can be maximized.

        distance = mid = (1+9)/2 = 5
        first object can be placed on 1, second object on 8, there is no place for 9
        so, reduce the distance (right=5)

        distance = mid = (1+5)/2 = 3
        first object can be placed on 1, second object on 4, third object can be placed on 8
        Now, try with higher distance (left=3+1=4)

        distance = mid = (4+5)/2 = 4
        first object can be placed on 1, second object on 8, there is no place for 9

        So, max distance between any two adjacent objects out of K objects can be 3


*/
public class _1PlaceKElementsSuchThatMinimumDistanceIsMaximized {

    public static void main(String[] args) {
        int arr[] = {1, 2, 8, 4, 9};
        int n = arr.length;
        int KObjects = 3;
        System.out.print(largestMinDist(arr, 0,n-1, KObjects));
    }

    // Returns largest minimum distance for K Objects elements in arr[0..end-1].
    // If elements can't be placed, returns -1.
    static int largestMinDist(int arr[], int start, int end, int KObjects) {
        // Sort the positions
        Arrays.sort(arr);

        // Initialize result.
        int maxPossibleDistance = -1;

        // Consider the maximum possible distance
        int left = arr[start], right = arr[end];

        // Do binary search for largest minimum distance
        while (left < right) {
            int mid = (left + right) / 2;// min distance between two objects

            // If it is possible to place K Objects elements with minimum distance mid, search for higher distance.
            if (isFeasible(mid, arr, start, end, KObjects)) {
                // Change value of variable max to mid if all elements can be successfully placed
                maxPossibleDistance = Math.max(maxPossibleDistance, mid);
                left = mid + 1;
            }

            // If not possible to place K Objects elements, search for lower distance
            else
                right = mid;
        }

        return maxPossibleDistance;
    }

    // Returns true if it is possible to arrange K Objects elements of arr[0..end-1] with minimum distance given as mid.
    static boolean isFeasible(int mid, int arr[], int start, int end, int KObjects) {
        // always put 1st object on first point of the line
        int pos = arr[start];

        // Initialize count of elements placed.
        int elements = 1;

        // Try placing KObjects elements with minimum distance mid.
        for (int i = start+1; i <= end; i++) {
            if (arr[i] - pos >= mid) {
                // Place next element if its
                // distance from the previously
                // placed element is greater or equal to current mid
                pos = arr[i];
                elements++;

                // Return if all elements are placed successfully
                if (elements == KObjects)
                    return true;
            }
        }
        return false;
    }

}
