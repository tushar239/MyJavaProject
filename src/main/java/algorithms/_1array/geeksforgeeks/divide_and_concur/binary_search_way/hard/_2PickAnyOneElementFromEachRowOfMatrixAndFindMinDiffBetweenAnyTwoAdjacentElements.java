package algorithms._1array.geeksforgeeks.divide_and_concur.binary_search_way.hard;

/*
    Minimum difference between adjacent elements of array which contain elements from each row of a matrix

    https://www.geeksforgeeks.org/minimum-difference-adjacent-elements-array-contain-elements-row-matrix/

    3   4   13
    2   12  15
    -1  0   5

    Find one element from each row in such a way that difference between any two adjacent elements is minimum.

    Brute-Force Approach:
    compare every element of first row with every element of second row and find the differences and keep the minimum one.
    then do the same between second and third row
    and so on.

    e.g.

    3       4       13
    |\
    | \
    |  \
    |   \
    |    \
    2      12       15

    2 < 3 < 12.
    2 and 12 are closest to 3. take their differences and keep min one (min(3-2,12-3)).
    so, keep 3-2=1


    3       4       13
           /|
          / |
         /  |
        /   |
       /    |
    2      12       15

    min(4-2, 12-4) = 4-2 = 2


    3       4       13
                    /|
                   / |
                  /  |
                 /   |
                /    |
    2      12       15

    min(13-12, 15-13) = 13-12 = 1


    min of all (1,2,1) = 1
    So, min difference between any one element from first row and any one element from second rows is 1.


    Similarly, do the same between second and third row and so on.
    Take the min of all.


    Better Approach:

    Looking at Brute-Force approach, you realize that you need closest >= element and <= element from second row for the first row element.

    Can you do better by not comparing every single element of second row?
    yes, by using binary search on second row.
    Using binary search, find closest >= element in second row for first row element.
    Element just before this closest >= element will be closest <= element.

    Important concept of when can you use Binary Search type divide-and-concur:

    When there are many possibilities, but you need to find one of them, then you can use Binary Search type divide-and-concur concept.
    There can be many >= elements in second row for a first row element, but you need to find just one. So, you can use Binary Search type divide-and-concur concept.


*/
public class _2PickAnyOneElementFromEachRowOfMatrixAndFindMinDiffBetweenAnyTwoAdjacentElements {
}
