package algorithms.crackingcodinginterviewbook._1stringmanipulations;

/*
    CCI book pg 201

    Rotate Matrix:
    Given an image represented by an N x N matrix, where each pixel in the image of 4 bytes, write a method to rotate the image by 90 degrees.
    Do it in-place.

    Matrix:

    00   01   02    03
    10   11   12    13
    20   21   22    23
    30   31   32    33

    Expected O/P:

    33   32   31   30
    23   22   21   20
    13   12   11   10
    03   02   01   00


    Solution:

    This is a complex algorithm.

    This algorithm is based on RotateMatrix.java.


 */
public class _7ImageRotation {

    public static void main(String[] args) {
        {
            //int pixelSize = 4;// in an image, one pixel is of size 4
            // image of 1 pixel
        /*String[][] image = new String[][]{

                new String[]{"00", "01", "02", "03"},
                new String[]{"10", "11", "12", "13"},
                new String[]{"20", "21", "22", "23"},
                new String[]{"30", "31", "32", "33"}
        };*/

            int pixelSize = 6;// in an image, one pixel is of size 6
            // image of 4 pixels
            String[][] image = new String[][]{

                    new String[]{"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "010", "011"},
                    new String[]{"10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "110", "111"},
                    new String[]{"20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "210", "211"},
                    new String[]{"30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "310", "311"},
                    new String[]{"40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "410", "411"},
                    new String[]{"50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "510", "511"},

                    new String[]{"60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "610", "611"},
                    new String[]{"70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "710", "711"},
                    new String[]{"80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "810", "811"},
                    new String[]{"90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "910", "911"},
                    new String[]{"100", "101", "102", "103", "104", "105", "106", "107", "108", "109", "1010", "1011"},
                    new String[]{"110", "111", "112", "113", "114", "115", "116", "117", "118", "119", "1110", "1111"}

            };

            int arrayType = 2; //2D array(matrix)

            System.out.println("Before Rotation...");
            print(image, pixelSize);

            System.out.println("better........");
            rotate(image, pixelSize, arrayType);

            System.out.println("After final rotation");
            print(image, pixelSize);

        }

    }

    private static void print(String[][] image, int pixelSize) {
        System.out.println();

        for (int i = 0; i < image.length; i++) {
            for (int j = 0; j < image[i].length; j++) {
                System.out.print(image[i][j] + "   ");
                if (j == pixelSize - 1) {
                    System.out.print("     ");
                }

            }
            if (i == pixelSize - 1) {
                System.out.println();
            }
            System.out.println();

        }
        System.out.println();
    }

    private static void rotate(String[][] image, int pixelSize, int arrayType) {
        int totalNumberOfPixelsInOneRowOfImage = image.length / pixelSize;
        int totalNumberOfPixelsInOneColOfImage = image[0].length / pixelSize;


        for (int pixelsInRow = 0; pixelsInRow < totalNumberOfPixelsInOneRowOfImage; pixelsInRow++) {

            int startRow = 0;
            int startCol = (pixelSize * pixelsInRow);

            rotateOnePixel(image, pixelSize, arrayType, startRow, startCol);
        }

        for (int pixelsInCol = 0; pixelsInCol < totalNumberOfPixelsInOneColOfImage; pixelsInCol++) {

            int startRow = (pixelSize * pixelsInCol);
            int startCol = 0;

            rotateOnePixel(image, pixelSize, arrayType, startRow, startCol);
        }

        print(image, pixelSize);
    }

    private static void rotateOnePixel(String[][] image, int pixelSize, int arrayType, int startRow, int startCol) {
        int endRow = startRow + (pixelSize - 1);
        int endCol = startCol + (pixelSize - 1);


        int totalLayersInPixel = (pixelSize - arrayType);

        for (int layer = 0; layer < totalLayersInPixel; layer++) {

            int finalPixelSize = pixelSize - (2 * layer);

            for (int j = 0; j < finalPixelSize - 1; j++) {// temp variables should be exchanged pixelSize-1 times

                int finalStartRow = startRow + layer;
                int finalStartCol = startCol + layer;
                int finalEndRow = endRow - layer;
                int finalEndCol = endCol - layer;

                try {
                    String topLeft = image[finalStartRow][finalStartCol + j];
                    String topRight = image[finalStartRow + j][finalEndCol];
                    String bottomRight = image[finalEndRow][finalEndCol - j];
                    String bottomLeft = image[finalEndRow - j][finalStartCol];

                    image[finalStartRow][finalStartCol + j] = bottomLeft; // topLeft=bottomLeft
                    image[finalStartRow + j][finalEndCol] = topLeft; // topRight=topLeft
                    image[finalEndRow][finalEndCol - j] = topRight; // bottomRight=topRight
                    image[finalEndRow - j][finalStartCol] = bottomRight; // bottomLeft=bottomRight
                } catch (Exception e) {
                    System.out.println(finalStartRow);
                    System.out.println(finalStartCol);
                    System.out.println(finalEndRow);
                    System.out.println(finalEndCol);
                    throw e;
                }
            }

            System.out.println("after layer: " + layer);
            print(image, pixelSize);
        }
    }
}
