package algorithms._1array.geeksforgeeks.dynamic_programming;

/*
Find if there exists a subset with the given sum in the given array.

https://www.youtube.com/watch?v=K20Tx8cdwYY

If you see this problem is NP-Complete, if you try to solve in Brute-Force way.
Every element needs to be grouped with all combinations of all other elements.

When this is the situation, algorithm either takes O(n!) or O(2^n)

e.g. {1,2,3,4}

O(2^n) situation   (do not include duplicates)

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

O(n!) situation    (include duplicates)

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

    If you see to create a set of {1,2,3}, you need {1,2} and then you can add 3 to it. So, you can use previously computed results for new result for better optimization.
    This can be achieved using Dynamic Programming.


This problem is same as CoinsChange.java
Here, you have been given sum that you want to achieve from some fixed set of elements.

Standard code template for Bottom-Up Dynamic programming that uses 2-D matrix

- pre-initialize first row of a 2-D matrix
- pre-initialize first column of 2-D a matrix
- two for looks (one inside another). one for loop iterates rows and another one iterates cols.

    // start iterating from second row
    for (int row = 1; row < memo.length; row++) {
        // start iterating from second col
        for (int col = 1; col < memo[row].length; col++) {

            ... fill up the matrix based on some formula ...

        }
    }


Extension of this algorithm:
- find elements of array that formed the sum.
- find min elements required to form the sum.
- Partition a set into two subsets such that the difference of subset sums is minimum

*/
public class _1FindIfASubsetWithGivenSumExistsInGivenArray {
}
