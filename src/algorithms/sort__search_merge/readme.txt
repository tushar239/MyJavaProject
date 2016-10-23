Search strategies

    It is very important to choose right data structure to put your data in, so that you can retrieve it later on the way you need.

    - data is coming from many streams (queues), you need to merge all these data and then find min/max and then next min/max and so on from it.
        use Binary Heap in this case.
    - you have stream of data coming in unsorted form. you need to create a datastructure to store all these data and later you should be able find any element with its rank.
         use BST and its in-order traversal (e.g. FindARankOfAnElementComingFromStream.java)
    - you have SORTED array and you need to find an element from it
        use Binary Search. Binary Search is the best for sorted array (e.g. SearchInRotatedArray.java, SearchInSizeLessArray.java, SparseSearch.java, _3sumAlgorithm.java
    - Another alternative of searching any element effectively is Hashing (HastMap/HashTable)
        There are different ways of implementing HashTable.
        Information about these different ways can be found in "objectorienteddesign/readme.txt"

    If there are streams of data coming and if you need to store them in some data structure in such a way that
    you can retrieve them in ordered form without running any sorting algorithm, then use binary tree.
    keep putting incoming data in binary tree and later just run in-order traversal, which will give you data from binary tree in ordered (sorted) form.

    If the requirement is to find min/max and then next min/max and so on, then put these data in Binary Heap. Binary Heap inserts data in O(log n) time and removes data in O(log n) time.
    Heap Sort also uses Binary Heap that takes O(nlog n) time.


Reorder array based on some criteria
    Create HashMap<Criteria, List<elements> elementsMatchingCriteria>.
    Use criteria as key
    Then merge all values in one array. All elements having same criteria will be together.
    e.g. GroupAnagrams.java

Merge tow sorted arrays
   MergeTwoSortedArrays.java

Sort contents of big file
   Use External Sort (e.g.SortBigFile.java)


