package algorithms.crackingcodinginterviewbook._6sort__search_merge;

/*
pg 401 of Cracking Coding Interview book

Sorted Search, No Size:
You have a class Listy that has all sorted positive elements. It returns -1, if element at passed index is not available. It doesn’t have size() method. What is the best way to find an index of an element in this data structure?

You can do a linear search in searching all elements one by one in Listy. If this would have been an ideal solution, interviewer will not tell you that all elements in Listy are in sorted order.
When you see sorted array, you should think of binary search.

But Listy doesn't have size() method. Can you find its approx size in O(log n)?
You jump double the index every time and stop when listy.elementAt(index) returns -1.
In worst case, you may end up finding the size that will be double the actual size of Listy, but that's ok because binary search will reduce the size to half immediately.

You are jumping double index
So, for some k in 2^k, you will hit >=n
What is k?
2^k=n
log2 2^k = log2 n
k = log2 n


e.g.
[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17]

This will result in

[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, -1, -1, -1, -1,-1, -1, -1, -1,-1, -1, -1, -1,-1, -1, -1, -1,-1, -1, -1]

and size will be 32


Fundamentals:

    When to use Binary Search?

        Binary Search works best on already sorted array (SortedSearch.java, SparseSearch.java) or matrix (SortedMatrixSearch.java)

        Binary Search can be used on unsorted array to find peaks and valleys (PeakAndValleyInUnOrderedArray.java)

        Remember, Binary Search needs access by index, so it needs an array as an input, it will perform bad on sorted linkedlist.
        You can create an array from sorted linkedlist first and apply binary search on it.

    When to use Binary Search Tree instead of Binary Search?

        Binary Search works best on already sorted array (SortedSearch.java, SparseSearch.java) or matrix (SortedMatrixSearch.java)

        If you want to search an element in unsorted array, you need to sort it first before you can search. This takes at least O(n log n) for sorting and O(log n) for binary search.

        You can do better by searching an element in BST. Inserting elements will reserve O(n) and searching an element will reserve O(log n) provided created BST is close to balanced.
        BST is worse for sorted array. It will created totally unbalanced tree.

    When to use Min/Max-Heap(Priority Queue) Binary Search or BST?

        When you need to search min/max element in O(1), then you use Min/Max-Heap(Priority Queue) because min element is always on the top of of the min-heap and similary max element is always on the top of max-heap.
        Remember, min/max-heap are not trees. It just keeps track of indices in the array to keep track of min/max element.
*/

public class _4SortedSearch {
    public static void main(String[] args) {
        int[] listyElements = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17};

        Listy listy = new Listy(listyElements);

        int index = findIndex(listy, 15);
        System.out.println(index); // 14

        index = findIndex(listy, 19);
        System.out.println(index); // -1
    }

    private static int findIndex(Listy listy, int elementToSearch) {
        if (listy == null) return -1;
        if (listy.elementAt(0) == -1) return -1;

        int i;
        for (i = 1; ; i = i * 2) {
            if (listy.elementAt(i) != -1) continue;
            break;
        }

        // e.g.
        // [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, -1, -1, -1, -1,-1, -1, -1, -1,-1, -1, -1, -1,-1, -1, -1, -1,-1, -1, -1]
        // size will be 32
        int approxSizeOfListy = i;

        // Now, you can do binary search on Listy

        int start = 0;
        int end = approxSizeOfListy;

        return binarySearch(listy, start, end, elementToSearch);
    }

    private static int binarySearch(Listy listy, int start, int end, int elementToSearch) {
        if (start > end) return -1;

        int mid = (start + end) / 2;

        // reducing the scope of search
        if (listy.elementAt(mid) == -1) {
            int newEnd = mid - 1;

            return binarySearch(listy, start, newEnd, elementToSearch);
        }

        // actual binary search starts from here
        if (listy.elementAt(mid) == elementToSearch) {
            return mid;
        }

        if (elementToSearch < listy.elementAt(mid)) {
            return binarySearch(listy, start, mid - 1, elementToSearch);
        }
        return binarySearch(listy, mid + 1, end, elementToSearch);
    }

    static class Listy {
        private int[] elements;

        public Listy(int[] elements) {
            this.elements = elements;
        }

        public int elementAt(int index) {
            if (index > elements.length - 1) return -1; // this is the condition imposed by requirement
            return elements[index];
        }
    }
}
