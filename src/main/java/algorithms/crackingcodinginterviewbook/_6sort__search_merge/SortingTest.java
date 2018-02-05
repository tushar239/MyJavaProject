package algorithms.crackingcodinginterviewbook._6sort__search_merge;

/**
 * @author Tushar Chokshi @ 7/12/15.
 */
public class SortingTest {
    private static int[] a = {42, 23, 4, 16, 8, 15};

    public static void main(String[] args) {

        final int[] sortedArray = insertionSort(a);
        for (int element : sortedArray) {
            System.out.println(element);
        }
    }

    private static int[] insertionSort(int[] a) {

        for (int i = 1; i < a.length; i++) {
            int elementToBeInsertedToSortedSide = a[i];
            int j = i;
            while (j > 0 && a[j - 1] > elementToBeInsertedToSortedSide) {
                a[j] = a[j - 1];
                j = j - 1;
            }
            a[j] = elementToBeInsertedToSortedSide;

            /*for (int element : a) {
                System.out.print(element + " ");
            }
            System.out.println();*/
        }
        return a;
    }
}
