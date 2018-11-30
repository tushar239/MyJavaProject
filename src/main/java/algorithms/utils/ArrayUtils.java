package algorithms.utils;

import java.util.function.Supplier;

public class ArrayUtils {

    public static boolean inRange(int[][] matrix, int x, int y) {
        return (x >= 0 && x <= matrix.length - 1) &&
                (y >= 0 && y <= matrix[x].length - 1);
    }

    public static boolean inRange(char[][] matrix, int x, int y) {
        return (x >= 0 && x <= matrix.length - 1) &&
                (y >= 0 && y <= matrix[x].length - 1);
    }

    public static void prettyPrintMatrix(char[][] matrix) {
        System.out.print("   ");
        for (int i = 0; i < matrix.length; i++) {
            System.out.print("  " + i + "  ");
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

    public static void prettyPrintMatrix(boolean[][] matrix) {
        System.out.print("   ");
        for (int i = 0; i < matrix.length; i++) {
            System.out.print("  " + i + "  ");
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

    public static void prettyPrintMatrix(int[][] matrix) {
        System.out.print("   ");
        for (int i = 0; i < matrix.length; i++) {
            System.out.print("  " + i + "  ");
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

    public static void exchange(int[] arr, int i, int j) {
        int element = arr[i];
        arr[i] = arr[j];
        arr[j] = element;
    }

    public static void printArray(String[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
    }

    public static void printArray(char[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
    }

    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
    }

    public static void printArray(int[] arr, Supplier<String> outputSupplier) {
        for (int i = 0; i < arr.length; i++) {
            System.out.println(String.format(outputSupplier.get(), i, arr[i]));
        }
    }

    public static void printArray(String[] arr, Supplier<String> outputSupplier) {
        for (int i = 0; i < arr.length; i++) {
            System.out.println(String.format(outputSupplier.get(), i, arr[i]));
        }
    }

    public static void printArray(char[] arr, Supplier<String> outputSupplier) {
        for (int i = 0; i < arr.length; i++) {
            System.out.println(String.format(outputSupplier.get(), i, arr[i]));
        }
    }

}
