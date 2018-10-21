package algorithms._1array_stack_queue.geeksforgeeks.pointers_sorting_stack_queue_string.medium;

/*

Given a sorted array and a number x, find the pair in array whose sum is closest to x

Examples:

Input: arr[] = {10, 22, 28, 29, 30, 40}, x = 54
Output: 22 and 30

Input: arr[] = {1, 3, 4, 7, 10}, x = 15
Output: 4 and 10


closestDiff = Integer.MAX_VALUE;
FS=-1
FE=-1


S                   E
10, 22, 28, 29, 30, 40  , x=54

10+40=50
closestSum=50

50 is < 54, so move S


    S               E
10, 22, 28, 29, 30, 40
22+40=62
closetSum is still 50

62 is > 54, so move E


    S            E
10, 22, 28, 29, 30, 40
22+30=52
closetSum=52

52 < 54, so move S


        S        E
10, 22, 28, 29, 30, 40
28+30=58
closestSum is still 52

58 is > 54, so move E


        S    E
10, 22, 28, 29, 30, 40
28+29=57
closestSum is still 52 that came from 22 and 30


Example 1:

    10 22 28 29 30 40      x=-1
    S               E

    while(S > E) {
        diff = abs(-1 - (10+40))
        diff < closestDiff, so closestDiff=diff, FS=0, FE=5

        (10+40) > x, so reduce E   (otherwise you need to increase S)

        10 22 28 29 30 40
        S           E

        keep doing this
    }

Example 2:

    10 22 28 29 30 40      x=54
    S               E

    while(S > E) {
        diff = abs(-1 - (10+40))
        diff < closestDiff, so closestDiff=diff, FS=0, FE=5

        (10+40) < x, so increase S     (otherwise you need to decrease E)

        10 22 28 29 30 40
           S           E

    }

*/
public class _5FindThePairInSortedArrayWhoseSumIsClosestToX {

    public static void main(String[] args) {
        {
            int A[] = {10, 22, 28, 29, 30, 40};
            int x = 54;
            findPair(A, x);// Closest sum to X is 52 with start index 1 and end index 4
        }

        {
            int A[] = {10, 22, 28, 29, 30, 40};
            int x = 100;
            findPair(A, x);// Closest sum to X is 70 with start index 4 and end index 5
        }

        {
            int A[] = {10, 22, 28, 29, 30, 40};
            int x = -1;
            findPair(A, x);// Closest sum to X is 34 with start index 0 and end index 1
            //printClosest(A, x);
        }


    }

    private static void findPair(int[] A, int x) {
        if (A == null || A.length <= 1) return;

        int start = 0;
        int end = A.length - 1;

        int finalStart = -1;
        int finalEnd = -1;
        int closestDiff = Integer.MAX_VALUE;

        while (start < end) {
            int startElement = A[start];
            int endElement = A[end];

            int sum = startElement + endElement;
            int diff = Math.abs(x - sum);

            if (diff == 0) {
                finalStart = start;
                finalEnd = end;
                closestDiff = diff;
                break;
            }

            if (diff < closestDiff) {
                finalStart = start;
                finalEnd = end;
                closestDiff = diff;
            }

            if (x < sum) {
                end--;
            } else {
                start++;
            }
        }

        if (finalStart != -1 && finalEnd != -1) {
            System.out.println("Closest sum to X is " + Math.abs(x - closestDiff) + " with start index " + finalStart + " and end index " + finalEnd);
        } else {
            System.out.println("Not found");
        }
    }

   /* private static void printClosest(int arr[], int x) {
        int res_l = 0, res_r = 0;  // To store indexes of result pair

        // Initialize left and right indexes and difference between
        // pair sum and x
        int l = 0, r = arr.length - 1, diff = Integer.MAX_VALUE;

        // While there are elements between l and r
        while (r > l) {
            // Check if this pair is closer than the closest pair so far
            if (Math.abs(arr[l] + arr[r] - x) < diff) {
                res_l = l;
                res_r = r;
                diff = Math.abs(arr[l] + arr[r] - x);
            }

            // If this pair has more sum, move to smaller values.
            if (arr[l] + arr[r] > x)
                r--;
            else // Move to larger values
                l++;
        }

        System.out.println(" The closest pair is " + arr[res_l] + " and " + arr[res_r]);
    }*/
}
