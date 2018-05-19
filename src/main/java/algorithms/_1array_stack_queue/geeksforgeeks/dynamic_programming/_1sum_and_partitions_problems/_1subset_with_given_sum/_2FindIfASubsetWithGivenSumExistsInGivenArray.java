package algorithms._1array_stack_queue.geeksforgeeks.dynamic_programming._1sum_and_partitions_problems._1subset_with_given_sum;

import java.util.HashMap;
import java.util.Map;

/*
Find if there exists a subset with the given sum in the given array.

https://www.geeksforgeeks.org/dynamic-programming-subset-sum-problem/

https://www.youtube.com/watch?v=K20Tx8cdwYY

If you see, this problem is NP-Complete when you try to solve in Brute-Force way.
Every element needs to be grouped with all combinations of all other elements.

When this is the situation, algorithm either takes O(n!) or O(2^n)

IMPORTANT:
    Study FindIfASubsetWithGivenSumExistsInGivenArrayWithAConstraintOfContinuousElements.java first
    It describes how to find required exit conditions for recursive method and how to decide whether Dynamic Programming is needed.


What is NP-Complete (Non-Deterministic Polynomial) problem?

    e.g. {1,2,3,4}

    1) O(n!) situation    (include duplicates)

        {1}
        {1,2} {1,3} {1,4}
        {1,2,3} {1,3,4}
        {1,2,3,4}

        {2}
        {1,2} {2,3} {2,4}
        {1,2,3} {2,3,4} {1,2,4}
        {1,2,3,4}

        {3}
        {2,3} {3,4}
        {1,2,3} {2,3,4} {1,3,4}
        {1,2,3,4}

        {4}
        {1,4} {2,4} {3,4}
        {1,2,4} {2,3,4} {1,3,4}
        {1,2,3,4}

    2) O(2^n) situation   (do not include duplicate sets)

    -
        {1}
        {1,2}   {1,3}   {1,4}
        {1,2,3} {1,3,4}
        {1,2,3,4}

        {2}
        {2,3} {2,4}
        {2,3,4}

        {3}
        {3,4}

        {4}

    If you see to create a set of {1,2,3}, you need {1,2} and then you can add 3 to it. So, you can use previously computed results for new result for better optimization.
    This can be achieved using Dynamic Programming.


    - Another O(2^n) situation

    doubling continuous sequence

        {1}
        {1,2}
        {1,2,3}
        {1,2,3,4}

        {2}
        {2,3}
        {2,3,4}

        {3}
        {3,4}

        {4}

    Double this number. It will be close to 2^n.

                            (1,2,3,4)
              (1,2,3)                   (1,2,3)
          (1,2)   (1,2)               (1,2) (1,2)
        (1) (1) (1) (1)              (1) (1)(1) (1)

   2^n nodes. This is the situation for FindIfASubsetWithGivenSumExistsInGiveArray.java algorithm.

   - Fibonacci Series situation

                            (1,2,3,4)
              (1,2,3)                   (1,2)
          (1,2)   (1)               (1)     (1)
        (1)                       (1)

    This is a similar situation as fibonacci series.

                    fib(n)
            fib(n-1)    fib(n-2)

    It will also have ~ 2^n nodes

    -   Longest Increasing SubSequence situation

                            (1,2,3,4,5)
                    (1,2,3,4)         (1,2,3)           (1,2)       (1)
                (1,2,3) (1,2) (1)    (1,2) (1)          (1)
               (1,2) (1) (1)        (1)
               (1)

    It will also have ~ 2^n nodes. LongestIncreasingSubSequenceInArray.java

    3) This is not a NP-Complete situation, but it is important to understand.

     n!
   -------  situation (do not include duplicate sets of specific k number of elements)(This does not occur for NP-Complete problem)
   k!(n-k)!

       When you need to find combinations of k=3 elements in such a way that there are no duplicate combinations, then use above formula.
       e.g. FindTripletWithMaxSum.java


Read this:
    https://www.probabilitycourse.com/chapter2/2_1_3_unordered_without_replacement.php


This problem is same as CoinsChange.java
Here, you have been given sum that you want to achieve from some fixed set of elements.

IMPORTANT:
I could not make this problem work for -ve sum. In CoinsChange.java, you will not have expected sum as -ve because coins cannot make -ve sum.
I could not find a reliable source on internet also that can solve this problem for -ve sum.
So, ASSUMPTION: expected sum is always +ve.


Top-Down Approach
-----------------

    IMPORTANT:
    Use this approach instead of Bottom-Up approach because later one is not feasible, if array is too large because. It will create a big 2-D array in memory.

    Practice Top-Down Approach.
    For Top-Down approach also, you can draw a 2-D matrix as shown above to figure out base conditions and formula.


    Reducing the problem by one element (last element)
    __________________  _____
    | 1,  2,  3,  4, |  | 5 |
    ------------------  -----

    boolean find(A,start,end,expectedSum) {

        // ..... exit conditions .....

        int element = A[end]

        if (element==expectedSum) ||

           (remaining array can get you the expected sum) ||

           ( (element < expectedSum) && (remaining array can get you sum that is expectedSum-element) ) {

                return true;

           }

        return false;
    }

    What exit conditions are needed?
        As you see, two parameters are changing in recursive calls (end index and expected sum).
        So, you need exit conditions for both these parameters.
        Each parameter is changing just once (endIndex is reduced by 1  and  expectedSum is reduced by element value), so you need one exit condition endIndex and one for expectedSum.

        In recursive calls, if you see endIndex is changing multiple times, then you need two exit conditions for endIndex (start==end, end<start)
        e.g. find(A,start,end-1,expectedSum) || find(A,start,end-2,expectedSum)

        If you see just
        e.g. find(A,start,end-2,expectedSum)
        then also you need end==start and end<start conditions because end-2 can go lesser than start also.

    Can you use Dynamic Programming to solve this problem?
        As you see, there are two recursive calls and both of them have different parameters.
        So, there is a possibility that you can use Top-Down Dynamic Programming to memoize the result of recursive calls.



    Recursive call tree:

                                                                  (1,2,3,4,5),sum=12

                                (1,2,3,4),sum=12                                                                             (1,2,3,4),sum=7

        (1,2,3),sum=12                                          (1,2,3),sum=8                           (1,2,3),sum=7                           (1,2,3),sum=3

(1,2),sum=12                (1,2),sum=9                 (1,2),sum=8      (1,2),sum=5             (1,2),sum=7    (1,2),sum=4              (1,2),sum=3    (1,2),sum=0

(1),sum=12  (1),sum=10  (1),sum=9  (1),sum=7       (1),sum=8  (1),sum=6  (1),sum=5  (1),sum=3   (1),sum=7  (1),sum=5
                                   ---------                             ----------             ---------   ----------

You can see overlapping problem. So, you need Dynamic Programming.
This recursive tree is similar to Fibonacci series. It will have O(2^n) nodes. Each node does O(1). So, Brute-Force approach will take O(2^n) time.
You can reduce it to O(n) using Dynamic Programming.

Bottom-Up approach
------------------
    IMPORTANT:
    Do not use Bottom-Up approach because it is not feasible, if array is too large because it will create a big 2-D array in memory.


                    sum
                0   1   2   3   4   5
                ---------------------
       A    0 | T   F   F   F   F   F   --- when array element is 0, except sum=0, no other sums can be achieved
       r    1 | T ------- sum=0 can be achieved with any array element
       r    2 | T
       a    3 | T
       y    4 | T                   * ----- by finding the formula to calculate the result at this position, you will be able to find the formula that you need for your algorithm.
                                            +
                                            you need to consider exist condition based on array element at this position >= sum

        Base conditions: (total 4)

            (1,2) sum=0 and array element=0 are the base conditions

                    if (sum == 0) return true;
                    if (A[start] == 0) return false;

            (3,4) Other base conditions will be based on '*' field's base conditions.

                    if (A[end] == sum) {
                        return true; // you don't need to consider other elements
                    }

                    if(A[end] > 0) {
                        if (A[end] > sum) {
                            return isSubsetSum(A, start, end - 1, sum);// do not consider end element
                        }
                    } else { // to handle -ve numbers
                        if (A[end] < sum) {
                            return isSubsetSum(A, start, end - 1, sum);// do not consider end element
                        }
                    }


            First populate the first row and then first column. This will give you the return value of base conditions 1,2.

        Actual Formula:

            You need to consider below two scenarios. If either of these returns true, then you achieved the sum.

            1) if sum can be achieved without using all previous elements (not including current element)(0,1,2,3), then you are good

                isSubsetSum_BruteForce(A, start, end - 1, sum)

            2) if 1st is not true, then include current element in calculating sum
               To achieve sum=5 by including 4, you need to add 1 to 4.
               4 + 1 = 5
               Find out the value to achieve sum=1 (5-4) using previous elements (0,1,2,3)

                  isSubsetSum_BruteForce(A, start, end - 1, sum - element);

        To find out the elements that make up the sum:

            You need to consider all those conditions that returns true by considering the current element in calculating the sum.
            Base condition 3 and 2nd part of formula are based on current element. So, when either of these return true, that element is participating in making the sum.


    Bottom-Up Approach

        NOTE: This approach is not good for large array, but you can draw 2-D matrix on paper to figure the the formula that you can either with Bottom-Up or Top-Down approach.

        Standard code template for Bottom-Up Dynamic programming that uses 2-D matrix

        - pre-initialize first row of a 2-D matrix
        - pre-initialize first column of 2-D a matrix
        - two for looks (one inside another). one for loop iterates rows and another one iterates cols.

            // start iterating from second row
            for (int row = 1; row < memo.length; row++) {

                // start iterating from second col
                for (int col = 1; col < memo[row].length; col++) {

                    if (element == col) { // base condition 3
                        memo[row][col] = .....;
                    } else if (element > sum) { // base condition 4
                        memo[row][col] = .....;
                    } else {
                        .. fill up the matrix based on some formula ...
                        // you have to make sure that your formula doesn't throw ArrayIndexOutOfBoundException. See below algorithm.
                    }
                }
            }




Extension of this algorithm:

- find elements of array that formed the sum.   ----- isSubsetSum_BruteForce_With_Participating_Elements_Printed method achieves this
- find elements of array that formed the sum with a constraint that you can use only continuous subarray to form the sum - FindIfASubsetWithGivenSumExistsInGivenArrayWithAConstraintOfContinuousElements.java
- find min elements required to form the sum.   ----- isSubsetSum_BruteForce_With_Participating_Elements_Printed method achieves this
- Determine whether a given set can be partitioned into two subsets such that the sum of elements in both subsets is same ----- PartitionASetIntoTwoSubsetsSuchThatSumOfElementsInBothSubsetsAreEqual.java
- Partition a set into two subsets such that the difference of subset sums is minimum ----- PartitionASetIntoTwoSubsetsSuchThatSumOfElementsInBothSubsetsIsMinimal.java

*/
public class _2FindIfASubsetWithGivenSumExistsInGivenArray {

    public static void main(String[] args) {
        //int A[] = {-3, 1, -2, 4};
        int A[] = {3, 1, 2, 4};
        int start = 0;
        int end = A.length - 1;
        {
            int sum = 5;// I couldn't make this algorithm work for -ve sum. So, assumption is that expected sum is a +ve number.

//            boolean exists = isSubsetSum_BruteForce(A, start, end, sum);
            System.out.print("Elements that form the sum are : ");
            boolean exists = isSubsetSum_BruteForce_With_Participating_Elements_Printed(A, start, end, sum);
            System.out.println();
            System.out.println("can elements form the sum of " + sum + "? " + exists);//true

            boolean existsWithTopDownApproach = isSubsetSum_Dynamic_Programming_Top_Down_Approach(A, start, end, sum, new HashMap<>());
            System.out.println(existsWithTopDownApproach);//true

            boolean existsUsingBottomUpApproach = isSubsetSum_Bottom_Up_Approach(A, sum);
            System.out.println("can elements form the sum of " + sum + "? " + existsUsingBottomUpApproach);//true
        }

        System.out.println();

        {
            int sum = 11;

            //boolean exists = isSubsetSum_BruteForce(A, start, end, sum);
            System.out.print("Elements that form the sum are : ");
            boolean exists = isSubsetSum_BruteForce_With_Participating_Elements_Printed(A, start, end, sum);
            System.out.println();
            System.out.println("can elements form the sum of " + sum + "? " + exists);//false

            boolean existsWithMemoization = isSubsetSum_Dynamic_Programming_Top_Down_Approach(A, start, end, sum, new HashMap<>());
            System.out.println("can elements form the sum of " + sum + "? " + existsWithMemoization);//false

        }

        System.out.println();

        {
            int sum = 9;

            //boolean exists = isSubsetSum_BruteForce(A, start, end, sum);
            System.out.print("Elements that form the sum are : ");
            boolean exists = isSubsetSum_BruteForce_With_Participating_Elements_Printed(A, start, end, sum);
            System.out.println();
            System.out.println("can elements form the sum of " + sum + "? " + exists);//true

            boolean existsWithMemoization = isSubsetSum_Dynamic_Programming_Top_Down_Approach(A, start, end, sum, new HashMap<>());
            System.out.println("can elements form the sum of " + sum + "? " + existsWithMemoization);//true
        }

        System.out.println();

        {
            int sum = 2;

            //boolean exists = isSubsetSum_BruteForce(A, start, end, sum);
            System.out.print("Elements that form the sum are : ");
            boolean exists = isSubsetSum_BruteForce_With_Participating_Elements_Printed(A, start, end, sum);
            System.out.println();
            System.out.println("can elements form the sum of " + sum + "? " + exists);//true

            boolean existsWithMemoization = isSubsetSum_Dynamic_Programming_Top_Down_Approach(A, start, end, sum, new HashMap<>());
            System.out.println("can elements form the sum of " + sum + "? " + existsWithMemoization);//true
        }

        System.out.println();

        {
            int sum = 3;

            //boolean exists = isSubsetSum_BruteForce(A, start, end, sum);
            System.out.print("Elements that form the sum are : ");
            boolean exists = isSubsetSum_BruteForce_With_Participating_Elements_Printed(A, start, end, sum);
            System.out.println();
            System.out.println("can elements form the sum of " + sum + "? " + exists);//true

            boolean existsWithMemoization = isSubsetSum_Dynamic_Programming_Top_Down_Approach(A, start, end, sum, new HashMap<>());
            System.out.println("can elements form the sum of " + sum + "? " + existsWithMemoization);//true
        }
    }

    /*
        Brute-Force approach:

        When you build a recursive tree, it will same as Fibonacci Series recursive tree.
        Time Complexity=O(2^n)

        This solution may try all subsets of given set in worst case. Therefore time complexity of the above solution is exponential. The problem is in-fact NP-Complete (There is no known polynomial time solution for this problem).

        Look at Fibonacci.java

        Using Dynamic Programming, you can reduce time complexity to O(sum * n). Basically, when you draw 2-D matrix, you need to fill up sum*A.length cells.
    */
    @SuppressWarnings("Duplicates")
    private static boolean isSubsetSum_BruteForce(int[] A, int start, int end, int sum) {
        //System.out.println("end="+end+", sum="+sum);

        if (A == null || A.length == 0) {
            if (sum == 0) return true;
            return false;
        }

        if (sum == 0) return true;

        if (start == end) {// just start==end is enough, you don't need start>end condition because recursive calls are reducing index only by 1.
            if (sum == A[end]) return true;
            return false;
        }

        int element = A[end];

      /*  if (element == 0) {
            return false;
        }*/

        if (element == sum) {
            return true;
        }

        boolean withoutEndElement = isSubsetSum_BruteForce(A, start, end - 1, sum);

        if (element > sum) {
            return withoutEndElement;
        }

        // excluding current element || including current element
        return withoutEndElement ||
                isSubsetSum_BruteForce(A, start, end - 1, sum - element);

    }

    /*
        Brute-Force approach (just like above method) + printing elements that makes the sum

        To find out the elements that participate in making the sum,
        you need to consider return conditions that are returning true by including an element.
    */

    private static boolean isSubsetSum_BruteForce_With_Participating_Elements_Printed(int[] A, int start, int end, int sum) {
        //System.out.println("end="+end+", sum="+sum);

        if (A == null || A.length == 0) {
            if (sum == 0) return true;
            return false;
        }

        if (sum == 0) return true;

        if (start == end) {// just start==end is enough, you don't need start>end condition because recursive calls are reducing index only by 1.
            if (sum == A[end]) {
                System.out.print(end + ",");
                return true;
            }
            return false;
        }

        int element = A[end];

        /*if (element == 0) {
            return false;
        }*/

        if (element == sum) { // this element will participate in making the sum
            System.out.print(end + ",");
            return true;
        }

        boolean withoutEndElement = isSubsetSum_BruteForce_With_Participating_Elements_Printed(A, start, end - 1, sum);
        if (element > sum) {
            return withoutEndElement;
        }

        boolean excludingCurrentElement = withoutEndElement;
        if (excludingCurrentElement) {
            return excludingCurrentElement;
        }
        boolean includingCurrentElement = isSubsetSum_BruteForce_With_Participating_Elements_Printed(A, start, end - 1, sum - element);
        if (includingCurrentElement) {// this element will participate in making the sum
            System.out.print(end);
        }
        return includingCurrentElement;

    }

    /*
        With Dynamic Programming Top-Down approach

        When you use Dynamic Approach, Time Complexity reduces from O(2^n) to O(n), just like Fibonacci.java
    */
    @SuppressWarnings("Duplicates")
    public static boolean isSubsetSum_Dynamic_Programming_Top_Down_Approach(int[] A, int start, int end, int sum, Map<String, Boolean> memo) {

        String key = end + "," + sum;
        if (memo.containsKey(key)) {
            return memo.get(key);
        }

        //System.out.println(key);

        if (A == null || A.length == 0) {
            if (sum == 0) return true;
            return false;
        }

        if (sum == 0) return true;

        if (start == end) {// just start==end is enough, you don't need start>end condition because recursive calls are reducing index only by 1.
            if (sum == A[end]) return true;
            return false;
        }

        int element = A[end];

       /* if (element == 0) {
            return false;
        }*/

        if (element == sum) {
            return true;
        }


        boolean withoutEndElement = isSubsetSum_Dynamic_Programming_Top_Down_Approach(A, start, end - 1, sum, memo);
        memo.put((end - 1) + "," + sum, withoutEndElement);


        if (element > sum) {
            return withoutEndElement;
        }

        boolean withoutEndElementAndExcludingThatElementFromSum = isSubsetSum_Dynamic_Programming_Top_Down_Approach(A, start, end - 1, sum - element, memo);

        memo.put((end - 1) + "," + (sum - element), withoutEndElementAndExcludingThatElementFromSum);

        return withoutEndElement || withoutEndElementAndExcludingThatElementFromSum;
    }

    private static boolean isSubsetSum_Bottom_Up_Approach(int[] A, int sum) {
        if (A == null || A.length == 0) return false;
        if (sum == 0) return true;

        boolean[][] memo = new boolean[A.length + 1][Math.abs(sum) + 1];// sum can be -ve, so using Math.abs(sum)

        for (int col = 0; col < memo[0].length; col++) {
            memo[0][col] = false;
        }
        for (int row = 0; row < memo.length; row++) {
            memo[row][0] = true;
        }

        for (int row = 1; row < memo.length; row++) {

            int element = A[row - 1];

            for (int col = 1; col < memo[row].length; col++) {

                if (element == col) {
                    memo[row][col] = true;
                } else if (element > sum) {
                    memo[row][col] = memo[row - 1][col];
                } else {
                    //try {
                    memo[row][col] = memo[row - 1][col] ||
                            (((sum - element) <= sum) ? memo[row - 1][sum - element] : false);// this condition is necessary because if element is a -ve number, sum-element will be more than sum and it will throw ArrayIndexOutOfBoundException.
                    /*}catch (Exception e) {
                        System.out.println(e);
                        System.out.println(element+" : "+(row-1) +" : "+col+" : "+(sum-element));
                    }*/
                }
            }
        }

        return memo[memo.length - 1][memo[0].length - 1];
    }
}
