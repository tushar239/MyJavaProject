package algorithms._1array;

public class ArrayUtils {
    public static void prettyPrintMatrix(int[][] matrix) {
        System.out.print("   ");
        for (int i = 0; i < matrix.length; i++) {
            System.out.print(i + "   ");
        }

        System.out.println();
        System.out.print("   ");
        for (int i = 0; i < matrix.length; i++) {
            System.out.print("---|");
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
}
