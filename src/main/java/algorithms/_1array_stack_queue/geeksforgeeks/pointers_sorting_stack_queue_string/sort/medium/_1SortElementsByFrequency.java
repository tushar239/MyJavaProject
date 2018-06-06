package algorithms._1array_stack_queue.geeksforgeeks.pointers_sorting_stack_queue_string.sort.medium;

import java.util.Arrays;

/*
Sort elements by frequency

https://www.geeksforgeeks.org/sort-elements-by-frequency/

Examples:

Input:  arr[] = {2, 5, 2, 8, 5, 6, 8, 8}
Output: arr[] = {8, 8, 8, 2, 2, 5, 5, 6}

Input: arr[] = {2, 5, 2, 6, -1, 9999999, 5, 8, 8, 8}
Output: arr[] = {8, 8, 8, 2, 2, 5, 5, 6, -1, 9999999}


Solution:

    As there are duplicates, you can either use 3-way quick sort OR BST+In-Order Traversal.

    We will use 3-way quick sort method.

    - Sort array using 3-way quick sort
    - Create count array that has objects containing element and its count
    - Sort this count array according to count inside objects.
    - Travers this count array and print its elements

    You can use BST+In-Order Traversal method also
        https://www.geeksforgeeks.org/sort-elements-by-frequency-set-2/

        In below code, instead of creating an array of ElementCount objects, you need to create a BST of them.
*/
public class _1SortElementsByFrequency {

    public static void main(String[] args) {
        int A[] = {2, 5, 2, 8, 5, 6, 8, 8};
        sort(A);
    }

    private static void sort(int[] A) {
        // sorting elements  (2,2,5,5,6,8,8,8)
        Arrays.sort(A); // 3-way quick sort

        // creating array of objects that contains element and its count
        // final array - [(null,0),(2,2),(null,0),(null,0),(5,2),(6,1),(null,0),(8,3)]
        ElementCount<Integer>[] ecs = new ElementCount[A.length];
        for (int i = 0; i < ecs.length; i++) {
            ecs[i] = new ElementCount(null, 0);// initialized with (null,0)

        }

        int cnt = 0;
        int prevElement = A[0];
        ecs[cnt] = new ElementCount(prevElement, 1);

        for (int i = 1; i < A.length; i++) {
            int element = A[i];

            if (element == prevElement) {
                ecs[cnt].incrementCount();
            } else {
                cnt++;
                ecs[cnt] = new ElementCount(element, 1);
                prevElement = element;
            }
        }

        // sorting elements by its count
        Arrays.sort(ecs);

        // manipulating original array to place elements in it by frequency (count)
        int j = 0;

        for (int i = 0; i < ecs.length; i++) {
            ElementCount<Integer> ec = ecs[i];

            Integer element = ec.element;

            if (element != null) {// important condition because there can be null elements in ElementCount

                for (int c = 0; c < ec.count; c++) {
                    A[j] = element;
                    j++;
                }

            }
        }

        // printing original array that has elements sorted by frequency
        for (int element : A) {
            System.out.print(element + ",");
        }

    }


    private static class ElementCount<I> implements Comparable<ElementCount<I>> {
        private I element;
        private int count;

        public ElementCount(I element, int count) {
            this(element);
            this.count = count;
        }

        public ElementCount(I element) {
            this.element = element;
        }

        public void incrementCount() {
            count++;
        }

        @Override
        public int compareTo(ElementCount<I> o) {
            if (o.count == count) {
                return 0;
            }
            if (count > o.count) {
                return 1;
            }
            return -1;
        }
    }

}
