package algorithms.matrix;

/**
 * @author Tushar Chokshi @ 8/23/15.
 */
public class RotateMatrix {
    public static void main(String[] args) {
        int matrixSize = 4;

        int matrix[][] = new int[matrixSize][matrixSize];
        for(int i=0; i < matrixSize; i++) {
            for(int j=0; j < matrixSize; j++) {
                matrix[i][j] = i*j;
            }
        }
        System.out.println("matrix before rotation");
        displayMatrix(matrix, matrixSize);
        //rotate(matrix, matrixSize);

        rotateAsPerBook(matrix, matrixSize);

    }

    private static void rotateAsPerBook(int[][] matrix, int n) {
        for(int layer = 0; layer < n/2; layer++) {
            int first = layer;
            int last = n-1-layer;
            for(int i=first; i<last; i++) {
                int offset = i - first;
                // save top
                int top = matrix[first][i];

                // copy left to top
                matrix[first][i] = matrix [last-offset][first];

                // copy bottom to left
                matrix[last-offset][first] = matrix[last][last-offset];
                // copy right to bottom
                matrix[last][last-offset] = matrix[i][last];
                // copy top to right
                matrix[i][last] = top;

            }
        }
        System.out.println("matrix after rotation");
        displayMatrix(matrix, n);
    }

    private static void rotate(int[][] matrix, int matrixSize) {
        int numberOfLayers = matrixSize/2; // n/2

        for(int layer=0; layer < numberOfLayers; layer++) {
            for(int ele=layer; ele <= matrixSize-1-layer; ele++) {




                int left = matrix[matrixSize-1-layer][layer];
                int top = matrix[layer][ele];
                int right = matrix[matrixSize-1-ele][matrixSize-1-layer];
                int bottom = matrix[matrixSize-1][ele];

                matrix[layer][ele] = left; // copy left to top

                matrix[ele][matrixSize-1] = top; // copy top to right

                matrix[matrixSize-1][ele] = right; // copy right to bottom

                matrix[ele][layer] = bottom;  // copy bottom to left


            }
            System.out.println("matrix after rotation");
            displayMatrix(matrix, matrixSize);
        }
        System.out.println("matrix after rotation");
        displayMatrix(matrix, matrixSize);
    }

    private static void displayMatrix(int[][] matrix, int matrixSize) {
        for(int i=0; i < matrixSize; i++) {
            for(int j=0; j < matrixSize; j++) {
                System.out.print(matrix[i][j] + "   ");
            }
            System.out.println();
        }
    }

}

