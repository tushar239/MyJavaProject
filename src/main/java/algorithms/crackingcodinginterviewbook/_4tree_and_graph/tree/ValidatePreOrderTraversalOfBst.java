package algorithms.crackingcodinginterviewbook._4tree_and_graph.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// https://github.com/xieqilu/Qilu-leetcode/blob/master/B207.ValidatePreOrderTraversalBST.cs
/*
    Is given array a valid pre-order traversed array of a BST?
    A = [40, 30, 35, 80, 100]
          40
       |     |
      30     80
         |      |
         35     100

    reserve root=40
         start from 30 till element < 40  and make an array L out of it.
         Stop filling L when element > 40 comes
         At this point start putting elements in array R if element > 40.

         While filling up L, if element > 40, then it won't be in L
         Similarly, if there is an element < 40 after 80, then it won't be in R

         At this point L and R are created.
         If L.size+R.size < A.size-1, then it means that while filling up L we encountered an element > 40 and/or while filling up R we encountered an element < 40 and so we skipped that element.
         In this case, array can not be a valid preorder traversed tree result.

         If L.size+R.size == A.size-1, then do this recursively for L and R.



   If you want to validate post-order traversed array
   A = [35,30,100,80,40]
   In this case, you start from last element of array.
   Take root=40
   L=[80,100] and R=[30,35] and do the same thing as we did for pre-order traversed array above.


  Time complexity
   validate(n) = O(n) + validate(n/2) + validate(n/2)
                = O(n) + 2validate(n/2)
                = O(n log n)

              This is same as Master's theorem T(n)= aT(n/b) + O(n^d)
                                                   = O(n^d log n)
              Same theorem applied to Merge Sort Time Complexity. See 'Sorting Algorithm Worksheet.xlsx'


 */

public class ValidatePreOrderTraversalOfBst {
    public static void main(String[] args) {
        {
            Integer[] A = {40, 30, 35, 80, 100};
            List<Integer> elementsList = Arrays.asList(A);
            System.out.println(validatePreOrderTraversal(elementsList));// true
        }
        {
            Integer[] A = {40, 30, 35, 80, 22, 100};
            List<Integer> elementsList = Arrays.asList(A);
            System.out.println(validatePreOrderTraversal(elementsList));// false
        }
    }

    private static boolean validatePreOrderTraversal(List<Integer> A) {
        if (A.size() == 0 || A.size() == 1) return true;

        int root = A.get(0);
        int i = 1;

        List<Integer> L = new ArrayList<>();
        while (i < A.size() && A.get(i) <= root) { // max O(n)
            L.add(A.get(i));
            i++;
        }

        List<Integer> R = new ArrayList<>();
        while (i < A.size() &&  A.get(i) > root) { // if above while loop is O(n), then this is 0. So both while loops combined O(n).
            R.add(A.get(i));
            i++;
        }

        return (L.size() + R.size() == (A.size() - 1))
                && validatePreOrderTraversal(L) && validatePreOrderTraversal(R);

    }
}
