package algorithms._1array_stack_queue.geeksforgeeks.kadane_algorithm;

/*

    IMPORTANT:
    Kadane's algorithm can be used in algorithms, where you want to find out biggest possible subarray that gives something.
    In this algorithm, it is used to find max sum.
    In FlipElementsInArrayToGetMaximum1s.java, it is used to find max number of continuous 1s.


    1) Largest Sum Contiguous Subarray

    https://www.geeksforgeeks.org/largest-sum-contiguous-subarray/

    Find the contiguous subarray within an array (containing at least one number) which has the largest sum.

    2) Size of The Subarray With Maximum Sum

    https://www.geeksforgeeks.org/size-subarray-maximum-sum/

    3) Maximum Subarray Sum Excluding Certain Elements

    https://www.geeksforgeeks.org/maximum-subarray-sum-excluding-certain-elements/

    For example:

        Given the array [-2, 1, -3, 4, -1, 2, 1, -5, 4],
        the contiguous subarray [4, -1, 2, 1] has the largest sum = 6.


        Solution:
            1) There is a Brute-Force Approach

                Disadvantage :takes O(n^2)
                              There is no place of Dynamic Programming. So, it cannot be improved further.

            2) There is a Kadane's algorithm that takes O(n)

                https://www.youtube.com/watch?v=ohHWQf1HDfU
                See this video by forwarding it till 12:56 minutes.

                Disadvantage : Kadane's Algorithm assumes that there is at least one +ve number in a array.
                Advantage : Takes O(n)

            3) Divide and Conquer (like Quick Sort)

               Advantage: Unlike to Kadane's algorithm, you don't need to have at least one +ve number in an array. It works even though there are all -ve numbers.
                          It takes O(n log n) that is better than Brute-Force

               This approach is NOT WORKING, but it is helpful understand the concept.

               When you have O(n^2) problem, you can try to make it O(n log n) using divide and conquer approach, if possible.
               O(n) can be achieved using some special trick or dynamic algorithm, if possible
*/
public class _1MaxSumContiguousSubarray {
    public static void main(String[] args) {
        /*[-2,1,-3,4,-1,2,1,-5,4]
        the contiguous subarray [4,-1,2,1] has the largest sum = 6.
         */
        int A[] = {-2, 1, -3, 4, -1, 2, 1, -5, 4};// [4, -1, 2, 1]=6
        //int A[] = {-500};// -500
        //int A[] = {-2, -3, 4, -1, -2, 1, 5, -3}; //[4, -1, -2, 1, 5] = 7
        //int A[] = {1, -3, 2, 1, -1};//[2,1]=3
        //int A[] = {6,-1,-4,11,-23};//[6,-1,-4,11] = 12
        //int A[] = {1,-3,2,-5,7};//[7] = 7
        //int A[] = {3, -2, -4, 7};//[7] = 7

        _1MaxSumContiguousSubarray instance = new _1MaxSumContiguousSubarray();

        System.out.println("Recursive O(n^2) approach...");
        int max = instance.maxSumRecursively(A, 0, A.length - 1, Integer.MIN_VALUE);
        System.out.println("max sum: " + max);

        System.out.println();

        System.out.println("Iterative O(n^2) approach...");
        max = instance.maxSumIteratively(A);
        System.out.println("max sum: " + max);

        System.out.println();
        /*int[] memo = new int[A.length];
        for (int i = 0; i < memo.length; i++) {
            memo[i] = Integer.MIN_VALUE;
        }*/

        System.out.println("Kadane's algorithm O(n) approach...(easier to understand)");
        instance.kadane_easier_to_understand(A);

        System.out.println();

        System.out.println("Kadane's Algorithm O(n) approach...");
        max = instance.maxSumUsingKadaneAlgorithm(A);
        System.out.println("max sum: " + max);

        System.out.println();

        System.out.println("Divide and Conquer ---- doesn't work");
        max = instance.maxSumUsingDivideAndConquerAlgorithm(A, 0, A.length - 1);
        System.out.println("max sum: " + max);
    }


    // This is a Brute-Force solution
    // O(n^2)
    public int maxSumRecursively(final int[] A, int start, int end, int initialMax) {
        if (A == null) return initialMax;
        if (start > end) return initialMax;

        int startElementOfSubArray = A[start];

        int maxSum = startElementOfSubArray;
        int newSum = maxSum;
        for (int i = start + 1; i < end; i++) {
            newSum = newSum + A[i];
            if (newSum > maxSum) {
                maxSum = newSum;
            }
        }

        int maxSumFromRemainingArray = maxSumRecursively(A, start + 1, end, initialMax);

        return Math.max(maxSum, maxSumFromRemainingArray);

    }

    public int maxSumIteratively(final int[] A) {
        int max = Integer.MIN_VALUE;

        if (A == null) return max;

        int start = 0;
        int end = A.length - 1;

        if (start > end) return max;

        for (int i = start; i <= end; i++) {

            int startElementOfSubArray = A[i];

            int maxSum = startElementOfSubArray;

            int sum = startElementOfSubArray;
            for (int j = i + 1; j <= end; j++) {
                sum = sum + A[j];
                if (sum > maxSum) {
                    maxSum = sum;
                }
            }

            if (maxSum > max) {
                max = maxSum;
            }
        }

        return max;
    }

    /*
    Kadane's Algorithm

    IMPORTANT: Kadane'e Algorithm assumes that there is at least one +ve number in a array.

    Takes O(n), but with above assumption.

    You have to memorize the approach by seeing the video.
    https://www.youtube.com/watch?v=ohHWQf1HDfU
    See this video by forwarding it till 12:56 minutes.


    Solution:

    IMPORTANT:
    find first 0 or +ve number.
    if found, set startIndex and endIndex, finalStartIndex and finalEndIndex to that index.


       i=S
    -2, 1, -3, 4, -1, 2, 1, -5, 4
        E

    S=1
    E=1
    FS=S
    FE=E


        S   i
    -2, 1, -3, 4, -1, 2, 1, -5, 4
        E

    As i is -ve, find first 0 or +ve after that

               i=S
    -2, 1, -3, 4, -1, 2, 1, -5, 4
               E

    and so on.



    int i = startIndex;
    while(i < A.length) {

        sum = sum + A[i];

        if(sum >= 0) {

            if(sum >= maxSum) {

                maxSum = sum;

                endIndex = i;

                finalStartIndex = startIndex;
                finalEndIndex = endIndex;
            }

            i++;
        }
        else  {

              sum=0; // reset
              startIndex=-1; // reset
              endIndex=-1;// reset

              IMPORTANT:
              find first 0 or +ve number from this index
              set i to that +ve number's index

              startIndex = that +ve number's index
              endIndex = that +ve number's index
        }
    }

    */
    private void kadane_easier_to_understand(int[] A) {

        if (A == null) return;
        if (A.length == 0) return;

        int startIndex = -1;// This index shows the starting index of a subarray that gives max sum
        int endIndex = -1;// This index shows the ending index of a subarray that gives max sum

        int finalStartIndex = startIndex;
        int finalEndIndex = endIndex;

        // IMPORTANT:
        // find the first +ve number in an array and start from there because Kadane's algorithm assumes that there is at least one +ve number in an array and so subarray start from +ve number.
        for (int i = 0; i < A.length; i++) {
            if (A[i] >= 0) {
                startIndex = i;
                endIndex = i;

                finalStartIndex = startIndex;
                finalEndIndex = endIndex;
                break;
            }
        }

        // if +ve number is not found
        if (startIndex == -1) {
            return;
        }

        int sum = 0; // as Kadane's algorithm assumes that there is at least one +ve number in an array, sum should never go lower than 0.
        int maxSum = Integer.MIN_VALUE; // and so max sum

        int i = startIndex;
        while(i < A.length) {

            sum = sum + A[i];

            // if sum is 0, then reset the sum reset the start and end indices
            if (sum < 0) {

                // IMPORTANT
                sum = 0;//reset
                startIndex = -1;//reset
                endIndex = -1;//reset

                // IMPORTANT
                // find next +ve number
                for (int j = i+1; j < A.length; j++) {
                    if (A[j] >= 0) {

                        i=j;// reset i to next +ve number index

                        startIndex = j;// reset startIndex to next +ve number index
                        endIndex = j;// reset endIndex to next +ve number index

                        break;
                    }
                }

                // if +ve number is not found
                if (startIndex == -1) {
                    break;
                }

            } else { // if sum >= 0

                if (sum >= maxSum) { // if sum >= 0, check it with maxSum and keep replacing maxSum as necessary
                    maxSum = sum;

                    endIndex=i;

                    finalStartIndex = startIndex;
                    finalEndIndex = endIndex;
                }

                i++;
            }

        }

        System.out.println("maxSum: " + maxSum);
        System.out.println("startIndex: " + finalStartIndex);
        System.out.println("endIndex: " + finalEndIndex);
    }


    public int maxSumUsingKadaneAlgorithm(final int[] A) {
        if (A == null) return Integer.MIN_VALUE;
        if (A.length == 0) return Integer.MIN_VALUE;

        int startIndex = -1;// This index shows the starting index of a subarray that gives max sum
        int endIndex = -1;// This index shows the ending index of a subarray that gives max sum

        int finalStartIndex = startIndex;
        int finalEndIndex = endIndex;

        //int prevFinalStartIndex = finalStartIndex;
        //int prevFinalEndIndex = finalEndIndex;

        // find the first 0 or +ve number in an array and start from there because Kadane's algorithm assumes that there is at least one +ve number in an array and so subarray start from +ve number.
        for (int i = 0; i < A.length; i++) {
            if (A[i] >= 0) {
                startIndex = i;
                endIndex = i;
                break;
            }
        }
        if (startIndex == -1) {// no >=0 number found
            return Integer.MIN_VALUE;
        }

        int sum = 0; // as Kadane's algorithm assumes that there is at least one +ve number in an array, sum should never go lower than 0.
        int maxSum = 0; // and so max sum

        for (int i = startIndex; i < A.length; i++) {

            sum = sum + A[i];

            if (startIndex == -1) {
                startIndex = i;
            }
            endIndex = i;

            // if sum is 0, then reset the sum reset the start and end indices
            if (sum < 0) {

                sum = 0;//reset
                startIndex = -1;//reset
                endIndex = -1;//reset

            } else { // if sum >= 0, check it with maxSum and keep replacing maxSum as necessary

                if (sum >= maxSum) {
                    maxSum = sum;
                    finalStartIndex = startIndex;
                    finalEndIndex = endIndex;
                }
            }

        }

        System.out.println("startIndex: " + finalStartIndex);
        System.out.println("endIndex: " + finalEndIndex);
        return maxSum;
    }

    /*
        Divide and Conquer:

        Takes O(n log n)

        https://www.youtube.com/watch?v=ohHWQf1HDfU
        See this video by forwarding it till 7:12 minutes.

        When you need to convert O(n^2) into O(n log n), the only choice you have is Divide and Conquer algorithm.
        To think Divide and Conquer, you need to think how will you write the code, if there are only two elements in an array.
        e.g. [1, -3]
                |
             [1] [-3]

            ans = max(1, -3, 1 + -3)


    NOT WORKING ?????????
    FOR int A[] = {3,-2,-4,7};
    INSTEAD OF 7, IT IS GIVING 10.
     */
    public int maxSumUsingDivideAndConquerAlgorithm(final int[] A, int start, int end) {
        if (A == null) return Integer.MIN_VALUE;

        if (A.length == 0) return Integer.MIN_VALUE;

        if (start == end) return A[start];


        int mid = (start + end) / 2;

        int left_MSS = maxSumUsingDivideAndConquerAlgorithm(A, start, mid);
        int right_MSS = maxSumUsingDivideAndConquerAlgorithm(A, mid + 1, end);

        int sum = 0;

        int leftSum = Integer.MIN_VALUE;
        for (int i = 0; i <= mid; i++) {
            sum += A[i];
            leftSum = Math.max(leftSum, sum);
        }

        sum = 0;

        int rightSum = Integer.MIN_VALUE;
        for (int i = mid + 1; i <= end; i++) {
            sum += A[i];
            rightSum = Math.max(rightSum, sum);
        }

        return Math.max(Math.max(left_MSS, right_MSS), leftSum + rightSum);
    }

}
