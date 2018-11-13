package algorithms._1array_stack_queue.geeksforgeeks.pointers_sorting_stack_queue_string.medium;

/*
    Number of subarrays whose minimum and maximum are same

    https://www.geeksforgeeks.org/number-subarrays-whose-minimum-maximum/

    Given an array of n integers, find the no of subarrays whose minimal and maximum elements are same.
    Subarray is defined as non-empty sequence of consecutive elements.

    Examples:

        Input: 2 3 1 1
        Output: 5
        Explanation: The subarrays are (2), (3), (1), (1) and (1, 1)

        Input: 2 4 5 3 3 3
        Output: 9
        Explanation: The subarrays are (2), (4),
        (5), (3), (3, 3), (3, 3, 3), (3), (3, 3) and (3)

        The first thing to observe is that only those subarrays whose all elements are same will have the same minimum and maximum. Having different elements clearly means different minimum and maximum. Hence we just need to calculate the number of continuous same elements(say d), then by by combinations formula we get the no of subarrays to be –

    IMPORTANT:

          No of subarrays possible with n elements = ( n * (n+1) / 2 )
          where n is number of CONTINUOUS elements.

          e.g. {1,2,3}

          Total number of subarrays that you can make from this array is
          ( 3 * (3+1) ) / 2 = 6

          (1), (1,2), (1,2,3), (2), (2,3), (3)

          e.g. {1,2,3,4,5}

          Total number of subarrays that you can make from index 1 to 3 is 6.
          From {2,3,4}, you can make 6 subarrays with continuous elements.

    Solution:

        Brute-Force Solution takes O(n^2)
        Better solution that uses above mentioned formula takes O(n)
*/
public class _9NumberOfSubarraysWhoseMinAndMaxElementsAreSame_COMPLICATED {

    public static void main(String[] args) {
        {
            int[] A = {3, 4, 3, 3, 3, 4, 5};

            find(A);//10 - (3), (4), (3), (3,3), (3,3,3), (3), (3,3), (3), (4), (5)

            find_another_way(A);//8
        }

        // another case
        {
            int[] A = {3, 4, 3, 3, 3};

            find(A);//8 - (3), (4), (3), (3,3), (3,3,3), (3), (3,3), (3)

            find_another_way(A);//8
        }

    }

    /*

        'i' is an index for startElement

        3, 4, 3, 3, 3, 4, 5
        i  j

        total number of possible subarrays = 1*(1+1)/2 = 1

        3, 4, 3, 3, 3, 4, 5
           i  j

        total number of possible subarrays = 1*(1+1)/2 = 1

        3, 4, 3, 3, 3, 4, 5
              i  j

        3, 4, 3, 3, 3, 4, 5
              i     j

        total number of possible subarrays = 3*(3+1)/2 = 6

        3, 4, 3, 3, 3, 4, 5
                       i  j

        total number of possible subarrays = 1*(1+1)/2 = 1

        3, 4, 3, 3, 3, 4, 5
                          i  j

        total number of possible subarrays = 1*(1+1)/2 = 1


        TOTAL NUMBER OF SUBARRAYS WITH SAME ELEMENTS = 1+1+6+1+1=10


    */
    private static void find(int[] A) {

        if (A == null || A.length == 0) System.out.println(0);

        int total = 0;

        for (int i = 0; i < A.length; ) {
            int startElement = A[i];

            int totalNumberOfSameStartElements = 1;

            int j = i + 1;

            for (; j < A.length; j++) {
                if (startElement == A[j]) {
                    totalNumberOfSameStartElements++;
                } else {
                    break;
                }
            }

            total += (totalNumberOfSameStartElements * (totalNumberOfSameStartElements + 1)) / 2;

            i = j; // important for O(n) time complexity
        }

        System.out.println(total);
    }

    private static void find_another_way(int[] A) {

        if (A == null || A.length == 0) System.out.println(0);

        int i = 0;
        int j = i + 1;

        int totalNumberOfSubArrays = 0;


        int totalNumberOfSameStartElements = 1;// it is initialized to 1 because when i is on first element and j is on second, min number of same elements will be 1.

        while (j <= A.length) {

            // if there is just one element in the array
            if (j == A.length) {

                int numberOfSubArraysFromIToJ = ((totalNumberOfSameStartElements) * (totalNumberOfSameStartElements + 1)) / 2;
                totalNumberOfSubArrays += numberOfSubArraysFromIToJ;

                break;
            }


            if (A[i] == A[j]) {

                totalNumberOfSameStartElements++;

                j++;
                continue;
            }

            // count total number of subarrays from i to j
            int numberOfSubArraysFromIToJ = ((totalNumberOfSameStartElements) * (totalNumberOfSameStartElements + 1)) / 2;
            totalNumberOfSubArrays += numberOfSubArraysFromIToJ;

            i = j;
            j++;
            totalNumberOfSameStartElements = 1;

        }

        System.out.println(totalNumberOfSubArrays);
    }


}
