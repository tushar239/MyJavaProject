package algorithms.utils;

public class ArrayUtils {
    public static void prettyPrintMatrix(int[][] matrix) {
        System.out.print("   ");
        for (int i = 0; i < matrix.length; i++) {
            System.out.print("  "+i+"  ");
        }

        System.out.println();
        System.out.print("   ");
        for (int i = 0; i < matrix.length; i++) {
            System.out.print("----|");
        }

        System.out.println();

        for (int from = 0; from < matrix.length; from++) {
            for (int to = 0; to <= from; to++) {

                System.out.print(from + "|" + " ");
                break;

            }
            for (int to = 0; to < matrix[from].length; to++) {
                System.out.print(matrix[from][to] + "  " + "|");
            }
            System.out.println();
            System.out.println();
        }
    }

    public static <T> void exchange(Comparable<T>[] comparables, int i, int j) {
        Comparable<T> comparable = comparables[i];
        comparables[i] = comparables[j];
        comparables[j] = comparable;
    }

    public static  void exchange(int[] arr, int i, int j) {
        int element = arr[i];
        arr[i] = arr[j];
        arr[j] = element;
    }

}
