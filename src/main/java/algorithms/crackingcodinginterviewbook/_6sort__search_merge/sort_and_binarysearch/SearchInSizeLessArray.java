package algorithms.crackingcodinginterviewbook._6sort__search_merge.sort_and_binarysearch;

// p.g. 401 of Cracking Coding Interview Book
/*
You have sorted array, but you cannot use the array.length to find its size.
You have custom class called Listy that has only one method elementAt(index) with original array as its property. You need to find an element from Listy.
elementAt(index) can return -1, if element is not found at that index, otherwise it returns found element.
So, you can assume that array has all positive numbers.

Solution:
    Whenever there is a sorted array mentioned, you should think of binary search to find an element in it.
    Binary search requires to find middle element in array and that requires to know the length of an array, but here you don't have method array.length

    One solution is first you find the length of array by iterating entire array that takes O(n) execution time, but interviewer won't ask such a simple algorithm.
    To reduce execution time, it's better to do this way

    foundLengthOfArray=0;
    index=1
    while(listy.elementAt(index) != -1 && elementToFind > listy.elementAt(index)) {

        index = index * 2; // increase index exponentially 1,2,4,8,16,...
    }

    foundLengthOfArray = index;

    binarySearch(listy.array, foundLengthOfArray/2, foundLengthOfArray);

 */
public class SearchInSizeLessArray {

    public static void main(String[] args) {
        int[] a = new int[] {1,2,3,4,5,6,7};
        Listy listy = new Listy(a);
        //int elementToFind = 9;// false
        //int elementToFind = 5;// true
        //int elementToFind = 7;// true
        int elementToFind = 1;// true

        int foundLengthOfArray=0;
        int index=1;

        while(listy.elementAt(index) != -1 && elementToFind > listy.elementAt(index)) {

            index = index * 2; // increase index exponentially 1,2,4,8,16,...
        }

        foundLengthOfArray = index;
        System.out.println(binarySearchAlgorithmRecursive(listy, foundLengthOfArray / 2, foundLengthOfArray, elementToFind));
    }

    static class Listy {
        private int[] a;

        public Listy(int[] a) {
            this.a = a;
        }

        public int elementAt(int index) {
            if(a.length-1 < index) return -1;
            return a[index];
        }
    }

    public static boolean binarySearchAlgorithmRecursive(Listy listy, int start, int end, int numberToSearch) {
        if (end < start) {
            return false;
        }

        int middle = (start + end) == 0 ? 0 : (start + end) / 2;
        if (listy.elementAt(middle) == numberToSearch) {
            return true;
        } else if (numberToSearch < listy.elementAt(middle)) {
            return binarySearchAlgorithmRecursive(listy, start, (middle - 1), numberToSearch);
        }
        return binarySearchAlgorithmRecursive(listy, middle + 1, end, numberToSearch);

    }
}
