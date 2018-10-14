package algorithms._1array_stack_queue.geeksforgeeks.pointers_sorting_stack_queue_string.hard._3lesser_greater_elements;

import com.google.common.collect.Lists;

/*
    Replace every element with the greatest element on right side

    https://www.geeksforgeeks.org/replace-every-element-with-the-greatest-on-right-side/

    Given an array of integers, replace every element with the next greatest element (greatest element on the right side) in the array.
    Since there is no element next to the last element, replace it with -1.
    For example, if the array is {16, 17, 4, 3, 5, 2}, then it should be modified to {17, 5, 5, 5, 2, -1}.

    Question for interviewer:
    What to do for last element as there is no greater element to its right?
    Answer: replace last element with -1.


    This is a tricky problem,
    but if you know how to solve FindASortedSubsequenceOfSize3InAnArray.java, then same concept is applied to this algorithm also.
*/
public class _2ReplaceEveryElementWithTheGreatestElementOnRightSide {

    /*

       step 1:

                          i  greatestElementOnRightSoFar
                          |  |
        {16, 17, 4, 3, 5, 7, 2}

        greatest element on right side of 7 is 2. So, replace 7 by 2, but before doing that as 7>2, 7 will become greatest element on right side of 5, so you need to store greatestElementOnRight=7 before you replace 7 by 2.

        - possibleNextGreatestElementOnRightSide = 7

        - replace 7 with 2
          {16, 17, 4, 3, 5, 2, 2}

        - As possibleNextGreatestElementOnRightSide > greatestElementOnRightSoFar
                    7                               >       2

          greatestElementOnRightSoFar = possibleNextGreatestElementOnRightSide (7)


        step 2:
                                   i
            input:  {16, 17, 4, 3, 5, 2, 2} and greatestElementOnRightSoFar=7

            output: {16, 17, 4, 3, 7, 2, 2} and greatestElementOnRightSoFar=7

        step 3:
                                i
            input:  {16, 17, 4, 3, 7, 2, 2} and greatestElementOnRightSoFar=7

            output: {16, 17, 4, 7, 7, 2, 2} and greatestElementOnRightSoFar=7

        step 4:
                             i
            input:  {16, 17, 4, 3, 7, 2, 2} and greatestElementOnRightSoFar=7

            output: {16, 17, 7, 7, 7, 2, 2} and greatestElementOnRightSoFar=7

        step 5:
                         i
            input:  {16, 17, 4, 3, 7, 2, 2} and greatestElementOnRightSoFar=7

            - This step will be similar to step 1 as possibleNextGreatestElementOnRightSide > greatestElementOnRightSoFar
             output: {16, 7, 7, 7, 7, 2, 2} and greatestElementOnRightSoFar=17

        step 6:
                      i
            input:  {16, 17, 4, 3, 7, 2, 2} and greatestElementOnRightSoFar=7

            output: {17, 7, 7, 7, 7, 2, 2} and greatestElementOnRightSoFar=17

    */
    public static void main(String[] args) {
        {
            Integer[] A = {16, 17, 4, 3, 5, 7, 2};// [17, 17, 7, 7, 7, 7, 2, -1]

            replace(A);

            System.out.println(Lists.asList(A[0], A));
        }

        {
            Integer[] A = {16, 17, 4, 3, 5, 7, 2};// [17, 17, 7, 7, 7, 7, 2, -1]

            replace_2(A);

            System.out.println(Lists.asList(A[0], A));
        }
    }

    private static void replace_2(Integer[] A) {
        int greaterElement = A[A.length - 1]; // last element

        for (int i = A.length - 2; i >= 0; i--) { // looping from second last element

            if(A[i] > greaterElement) {
                int temp = A[i];
                A[i] = greaterElement;
                greaterElement = temp;
            } else {
                A[i] = greaterElement;
            }

        }

        A[A.length-1] = -1;

    }

    private static void replace(Integer[] A) {
        int greatestElementOnRightSoFar = A[A.length - 1];

        for (int i = A.length - 2; i >= 0; i--) {

            int possibleNextGreatestElementOnRightSide = A[i];
            A[i] = greatestElementOnRightSoFar;

            if (possibleNextGreatestElementOnRightSide >= greatestElementOnRightSoFar) {
                greatestElementOnRightSoFar = possibleNextGreatestElementOnRightSide;
            }
        }

        A[A.length - 1] = -1;
    }

   }
