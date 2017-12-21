package algorithms.recursion_and_memoization;

import java.util.ArrayList;
import java.util.List;

//p.g. 349 of Cracking Coding Interview book
public class FindSubsetsOfASet {
    public static void main(String[] args) {
        int A[] = {1, 2, 3};
        List<List<Integer>> outerList = new ArrayList<>(A.length);
        findSubsets(A, 0, A.length - 1, outerList);
        System.out.println(outerList);
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
            // creating a new nilList from existing lists and adding startElement to it
            //[   [1],[1,2],[1,2,3],[1,3]   ]
            List<Integer> newSubset = new ArrayList<>(subset);
            newSubset.add(startElement);
            newSubsets.add(newSubset);
        }
        //[   [2],[2,3],[3], [1],[1,2],[1,2,3],[1,3]   ]
        outerList.addAll(newSubsets);
    }

}
