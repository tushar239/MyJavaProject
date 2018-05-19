package algorithms._1array_stack_queue.interviewbit.kadane_algorithm;

/*
Flip elements in array to get maximum 1s:

http://edusagar.com/questions/arrays/flip-elements-in-array-to-get-maximum-1s
https://www.geeksforgeeks.org/find-zeroes-to-be-flipped-so-that-number-of-consecutive-1s-is-maximized/

You are given a binary string(i.e. with characters 0 and 1) S consisting of characters S1, S2, …, SN. In a single operation, you can choose two indices L and R such that 1 ≤ L ≤ R ≤ N and flip the characters SL, SL+1, …, SR. By flipping, we mean change character 0 to 1 and vice-versa.

Your aim is to perform ATMOST one operation such that in final string number of 1s is maximised. If you don’t want to perform the operation, return an empty array. Else, return an array consisting of two elements denoting L and R. If there are multiple solutions, return the lexicographically smallest pair of L and R.
Notes: Pair (a, b) is lexicographically smaller than pair (c, d) if a < c or, if a == c and b < d

For example,

  S = 010

Pair of [L, R] | Final string
_______________|_____________
[1 1]          | 110
[1 2]          | 100
[1 3]          | 101
[2 2]          | 000
[2 3]          | 001

We see that two pairs [1, 1] and [1, 3] give same number of 1s in final string. So, we return [1, 1].
Another example,

 If S = 111

No operation can give us more than three 1s in final string. So, we return empty array [].


Solution:
You need to use Kadane's algorithm (See MaxSumContiguousSubarray.java)
*/
public class _2FlipElementsInArrayToGetMaximum1s {

    public static void main(String[] args) {
        _2FlipElementsInArrayToGetMaximum1s instance = new _2FlipElementsInArrayToGetMaximum1s();

        //int[] A = {0, 1, 0}; //start index = 0, end index = 0
        //int[] A = {0, 0, 0, 0, 0, 0, 0, 0};// start index = 0, end index = 7
        int[] A = {0, 1, 0, 1, 1, 1, 0, 0};// start index = 6, end index = 7
        //int[] A = {0, 0, 0, 1, 1, 1, 0, 0};// start index = 0, end index = 2
        //int[] A = {0, 0, 0, 1, 1, 1, 0, 1};// start index = 0, end index = 2
        //int[] A = {0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 1};// start index = 0, end index = 9
        //int[] A = {0, 0, 0, 1, 1, 1, 0, 0, 0, 1};// start index = 0, end index = 2
        //int[] A = {0,1,1};// start index = 0, end index = 0

        int result[] = instance.flip(A);
        if(result.length > 0) {
            System.out.println("start index: " + result[0] + ", end index: " + result[1]);
        } else {
            System.out.println("no 0s found in array");
        }

    }

    private int[] flip(int[] A) {
        if (A == null || A.length == 0) return new int[0];

        int maxSum = 0;
        int sum = 0;

        int startIndex = -1;
        int endIndex = -1;


        for (int i = 0; i < A.length; i++) {
            if (A[i] == 0) {
                startIndex = i;
                endIndex = i;
                break;
            }
        }
        if (startIndex == -1) {// 0 is not found in entire array. All elements in array are 1s.
            return new int[0];
        }


        int finalStartIndex = startIndex;
        int finalEndIndex = endIndex;

        //int prevFinalStartIndex = finalStartIndex;
        //int prevFinalEndIndex = finalEndIndex;

        for (int i = startIndex; i < A.length; i++) {
            int element = A[i];
            // Important:
            // In MaxSumContiguousSubArray.java, you were adding actual element to sum.
            // Here, you need to consider 0 as 1 and 1 as -1 to add it in sum.
            // basically, you increment the sum by 1, if you find element==0
            // otherwise, you decrement the sum by 1.
            if (element == 0) {
                sum += 1;
            } else {
                sum -= 1;
            }

            //prevFinalStartIndex = finalStartIndex;
            //prevFinalEndIndex = finalEndIndex;

            if (startIndex == -1) {
                startIndex = i;
            }
            endIndex = i;

            if (sum < 0) {
                // preserving previously found start and end index
                //finalStartIndex = prevFinalStartIndex;
                //finalEndIndex = prevFinalEndIndex;

                startIndex = -1;//reset
                endIndex = -1;//reset

                sum = 0;// reset
            } else {
                if (sum > maxSum) {
                    maxSum = sum;
                    finalStartIndex = startIndex;
                    finalEndIndex = endIndex;
                }
            }
        }

        int[] result = new int[2];
        result[0] = finalStartIndex;
        result[1] = finalEndIndex;
        return result;
    }

}
