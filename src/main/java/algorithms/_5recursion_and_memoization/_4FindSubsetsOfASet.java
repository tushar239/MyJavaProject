package algorithms._5recursion_and_memoization;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

//p.g. 349 of Cracking Coding Interview book
public class _4FindSubsetsOfASet {
    public static void main(String[] args) {
        {
            int A[] = {1, 2, 3};
            List<List<Integer>> outerList = new ArrayList<>(A.length);
            findSubsets(A, 0, A.length - 1, outerList);
            System.out.println(outerList);
        }
        {
            int A[] = {1, 2, 3};
            List<List<Integer>> subsets = findSubsets(A, 0, A.length - 1);
            System.out.println(subsets);
        }
    }
    /*
       To make your life easier for recursion,
           If you are working with Array, always pass start and end. e.g. Sorting.java(mergeSort)
           If you are working with binary tree, always pass root node. e.g. Any tree related algorithm (CreateMirrorImageOfATree.java)
           If you are working with linkedlist, always pass head node.  e.g. ReverseLinkedList.java, CreateMinimalBST.java

      Recursion is a technique of minimizing the problem by 1.
      Here, we are calling findSubsets method recursively starting from 2nd element of an array and its result is merged with 1st element.

      Recursive method's return value is determined by exit condition.
      If exit condition is returning null, make sure you check for null before you removeRootAndMergeLeftAndRight recursive call's result to root element. See CreateMinimalBST.java

      If recursive method is not returning anything, then its result has to be collected in a parameter passed to a method (e.g. outerList here)


    */
    /*
    e.g. A[] = [1,2,3]

    space complexity:
    this is how lists are created at each level

    [[1], [1,2], [1,2,3], [1,3], from 2nd level]    --- 3^2 lists. total elements = 8 + references to lower level lists = 12 = 3^2 + 3
    [[2], [2,3], from 3rd level]                    --- 3^1 lists. total elements = 3 + references to lower level lists = 4 = 3^1 + 1
    [3]                                             --- 3^0 lists. total elements = 1 = 3^0


    n^0 + (n^1+1) + (n^2+2) +.......+(n^n+n) = n^n + (n(n+1)/2) = O(n^n)
     */
    private static List<List<Integer>> findSubsets(final int[] A, int start, int end) {

        List<List<Integer>> subsets = new ArrayList<>(); // expected output

        if (A == null || A.length == 0) return subsets;
        if (start > end) return subsets;

        int one = A[start]; // 1st element
        List<List<Integer>> subSetsFromRemainingArray = findSubsets(A, start + 1, end); // subsets from[2,3]

        List<List<Integer>> subSetsWithOne = new ArrayList<>();
        for (List<Integer> subSet : subSetsFromRemainingArray) {
            List<Integer> s = new ArrayList<>();
            s.add(one);
            s.addAll(subSet);
            subSetsWithOne.add(s);
        }

        subsets.add(Lists.newArrayList(A[start]));// adding [1]
        subsets.addAll(subSetsWithOne); // adding subsets having 1 in subsets of [2,3]
        subsets.addAll(subSetsFromRemainingArray); // adding subsets of [2,3]

        return subsets;
    }

    private static void findSubsets(final int[] A, int start, int end, List outerList) {
        final Integer startElement = A[start];
        if (start == end) {
            List<Integer> subset = new ArrayList<>();
            subset.add(startElement);
            outerList.add(subset);
            return;

        }

        findSubsets(A, ++start, end, outerList);//[   [2],[2,3],[3]   ]


        List<List<Integer>> newSubsets = new ArrayList<>();
        newSubsets.add(new ArrayList<Integer>() {{//newSubstes = [  [1]  ]
            add(startElement);
        }});
        for (int i = 0; i < outerList.size(); i++) {
            List<Integer> subset = (List<Integer>) outerList.get(i);
            // creating a new list from existing lists and adding startElement to it
            //[   [1],[1,2],[1,2,3],[1,3]   ]
            List<Integer> newSubset = new ArrayList<>(subset);
            newSubset.add(startElement);
            newSubsets.add(newSubset);
        }
        //[   [2],[2,3],[3], [1],[1,2],[1,2,3],[1,3]   ]
        outerList.addAll(newSubsets);
    }


}
