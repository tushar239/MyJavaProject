package algorithms._1array_stack_queue.geeksforgeeks.dynamic_programming._1sum_and_partitions_problems._1subset_with_given_sum;

/*
Find if there exists a subset with the given sum in the given array.
Constraint: To calculate the sum, you can use only continuous subarray.

This is a self created problem as an extension of FindIfASubsetWithGivenSumExistsInGivenArray.java

A[] = {1,2,3,4,5}  expectedSum=6

Because there is a condition in the problem, solution of this problem will slightly change from FindIfASubsetWithGivenSumExistsInGivenArray.java.

Reducing the problem by one element (last element)
__________________  _____
| 1,  2,  3,  4, |  | 5 |
------------------  -----

if (element==expectedSum) ||
   (sum of continuous elements from selected element till last element==expectedSum) ||
   (recursive call for remaining array==expectedSum)


Recursive Tree Structure:
                            start,   end,   expectedSum
                    find(A, 0,       4,     6)
                            |
        end=5, sum=5,   find(A,0,3,6)
                            |
        end=4, sum=4+5  find(A,0,2,6)
                            |
        end=3, sum=3+4+5 find(A,0,1,6)
                            |
        end=2, sum=2+3+4+5 find(A,0,1,6)
                            |
        end=1, sum=1+2+3 = 6 (result found)

Takes O(n^2).

You don't see any need of memoizing the result of recursive call, why?
Because recursion is happening only once per method call. If you see two or more recursive calls, there is definitely a chance of Top-Down Dynamic approach.

boolean find(A, start, end, expectedSum) {

    // how to decide what should be the exit conditions?
    // You have to see what parameters are changing in the recursive call.
    // You need exit condition(s) for all of those parameters
    // Here, there is just one recursive call and end index is changing, so you need one exit condition for end index
    // If there were two recursive calls ane in one recursive call end changes to end-1 and in another recursive call end-2, then you need two exit conditions for end index.
    // if(end==0) and if(end<0)
    if(end == start) {
        if(A[end] == expectedSum) return true;

        return true or false based on taking sum from end to A.length-1;
    }

    int element = A[end];

    if(element == expectedSum) return true;

    int sum = 0
    for (int i = end; i <= A.length - 1; i++) {
        sum += A[i];
        if(sum == expectedSum) return true;
    }

    return find(A, start, end-1, expectedSum); // As you see here, 'end' is changing in recursive call. So, that's an indication that 'end' needs an exit condition.

}

What exit conditions are needed?
    As you see, two parameters are changing in recursive calls (end index and expected sum).
    So, you need exit conditions for both these parameters.
    Each parameter is changing just once (endIndex is reduced by 1  and  expectedSum is reduced by element value), so you need one or more exit condition(s) for endIndex and one or more exit conditions for expectedSum.

    In recursive calls, if you see endIndex is changing multiple times, then you need minimum two exit conditions for endIndex (start==end, end<start)
    e.g. find(A,start,end-1,expectedSum) || find(A,start,end-2,expectedSum)

    If you see just
    e.g. find(A,start,end-2,expectedSum)
    then also you need end==start and end<start conditions because end-2 can go lesser than start also.

Can you use Dynamic Programming to solve this problem?
    As you see, there is just one recursive call. So, you don't need Dynamic Programming.
    If you see more than one recursive calls (like FindIfASubsetWithGivenSumExistsInGivenArray.java) and they are having different parameters, then you may need Dynamic Programming.



Now, how to find elements that forms the expectedSum?

boolean find(A, start, end, expectedSum) {

    if(end == start) {
        if(A[end] == expectedSum)  {
            System.out.println(end + " index is participating in forming expectedSum");
            return true;
        }
        return true or false based on taking sum from end to A.length-1;
    }

    int element = A[end];

    if(element == expectedSum) {
        System.out.println(end + " index is participating in forming expectedSum");
        return true;
    }

    int sum = 0
    for (int i = end; i <= A.length - 1; i++) {
        sum += A[i];
        if(sum == expectedSum) {
            System.out.println(end +" to "+i+ " indices are participating in forming expectedSum");
            return true;
        }
    }

    return find(A, start, end-1, expectedSum);

}


*/
public class _1FindIfASubsetWithGivenSumExistsInGivenArrayWithAConstraintOfContinuousElements {

    public static void main(String[] args) {
        int A[] = {1, 2, 3, 4, 5};
        int expectedSum = 6;

        int start = 0;
        int end = A.length - 1;
        boolean result = find(A, start, end, expectedSum);
        System.out.println(result);

    }

    private static boolean find(int[] A, int start, int end, int expectedSum) {

        if (end == start) {
            if (A[end] == expectedSum) {
                System.out.println(end + " index is participating in forming expectedSum");
                return true;
            }
            return foundExpectedSum(A, end, A.length-1, expectedSum);// same as "return false;"
        }

        int element = A[end];

        if (element == expectedSum) {
            System.out.println(end + " index is participating in forming expectedSum");
            return true;
        }

        boolean found = foundExpectedSum(A, end, A.length-1, expectedSum);
        if (found) return true;

        return find(A, start, end - 1, expectedSum);

    }

    private static boolean foundExpectedSum(int[] A, int from, int to, int expectedSum) {
        int sum = 0;
        for (int i = from; i <= to; i++) {
            sum += A[i];
            if (sum == expectedSum) {
                System.out.println(from + " to " + i + " indices are participating in forming expectedSum");
                return true;
            }
        }
        return false;
    }
}
