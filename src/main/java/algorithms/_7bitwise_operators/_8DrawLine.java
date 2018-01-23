package algorithms._7bitwise_operators;

public class _8DrawLine {
    private static void draw_line(char screen[],
                                  int width,
                                  int x_1,
                                  int x_2,
                                  int y) {
        if (x_1 == x_2) return;

        // First byte of the row
        int first_byte = width * y;

        // The byte at which we'll start the line
        int lower_byte = first_byte + (x_1 / 8);

        // The byte at which we'll end the line
        int upper_byte = first_byte + (x_2 / 8);

        // The bit in the lower byte
        x_1 %= 8;

        // The bit in the upper byte
        x_2 %= 8;

        // Special case when the bits are in the same byte
        if (lower_byte == upper_byte) {
            // Create a mask with the appropriate number of bits
            // and shift them into place. Note that x_2 is exclusive.
            int i = ((1 << (x_2 - x_1)) - 1) << (8 - x_2);
            screen[lower_byte] = (char) i;
        } else {
            screen[lower_byte] = (char) (0xFF >> x_1);

            screen[upper_byte] = (char) (0xFF << (8 - x_2));

            for (++lower_byte; lower_byte < upper_byte; ++lower_byte) {
                screen[lower_byte] = 0xFF;
            }
        }

    }

    public static void main(String[] args) {

        //System.out.println(1/8);//0

        /*char screen[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        draw_line(screen, 4, 1, 3, 3);

        // Representation
        for (int i = 0; i < 16; ++i) {
            if (i % 4 == 0) System.out.println();

            System.out.print(screen[i]);

            if ((i + 1) % 4 != 0) System.out.print("|");
        }*/


        // screen with
        // 4 rows (0 to 3)
        // each row having 8 bytes. so width of a screen is 64 bits (64 pixels)
        byte[] screen =
                {
                        0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0
                };

        // draw a horizontal line in 3rd row from pixel 10 to pixel 20
        drawLine(screen, 64, 10, 20, 0);

        for (int i = 0; i < screen.length; i++) {
            //System.out.print(BitwiseManipulationUtil.convertUnsignedByteToBinaryWithLeftPadWithZeros(screen[i]) + ",");
            System.out.print(screen[i] + ",");
            if ((i + 1) % 8 == 0) {
                System.out.println();
            }
        }

    }

    // width = 64
// row = 3
// startPixel = 10
// endPixel = 20
    private static void drawLine(byte[] screen, int width, int startPixel, int endPixel, int row) {
        if(endPixel < startPixel) return;

        int widthOfScreenInBytes = width / 8; // number of bytes in one row. e.g. if width=64 bits/pixels, then number of bytes in one row=64/8=8. It means that first 8 elements of screen array will be in row 0, second 8 elements will be in row 1 and so on

        int start_byte_of_row = widthOfScreenInBytes * row; // 8 * 3 = 24. 24th element in screen[] is the first byte of the row in which we need to draw a line.

        int start_byte_of_line = start_byte_of_row + (startPixel / 8); // 25th element of screen[]
        int end_byte_of_line = start_byte_of_row + (endPixel / 8); // 26th element of screen[]

        //System.out.println(0xFF);// 0xFF is same as 0x000000FF
        /*
            Below for loop will modify 25th to 26th elements of screen[] as follows
            0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,
            0,-1,-1,0,0,0,0,0,
        */
        if (end_byte_of_line < start_byte_of_line) return;

        for (int i = start_byte_of_line; i <= end_byte_of_line; i++) {
            screen[i] = (byte) 0xFF;// is same as (byte)-1
        }

        //System.out.println(BitwiseManipulationUtil.convertUnsignedIntToBinary(0xFF));// 1111 1111
        //System.out.println(BitwiseManipulationUtil.convertUnsignedIntToBinary((0xFF << 4) & 0xFF));

        /*
            Below for loop will modify 25th to 26th elements of screen[] as follows
            00000000,00000000,00000000,00000000,00000000,00000000,00000000,00000000,00000000,
            00000000,00000000,00000000,00000000,00000000,00000000,00000000,00000000,00000000,
            00000000,00000000,00000000,00000000,00000000,00000000,00000000,00000000,00000000,
            00000000,00111111,11110000,00000000,00000000,00000000,00000000,00000000,00000000  --- 3rd row
                       |         |
                      10th     20th
                      pixel    pixel
        */

        byte start_byte_of_line_mask = (byte) (0xFF >>> (startPixel % 8));
        //System.out.println(BitwiseManipulationUtil.convertUnsignedByteToBinary(start_byte_of_line_mask));//11110000

        screen[start_byte_of_line] = (byte) ((screen[start_byte_of_line]) & start_byte_of_line_mask);

        byte end_byte_of_line_mask = (byte) ((0xFF << (endPixel % 8)));
        //System.out.println(BitwiseManipulationUtil.convertUnsignedByteToBinary(screen[end_byte_of_line]));//11111111


        screen[end_byte_of_line] = (byte) (screen[end_byte_of_line] & end_byte_of_line_mask);
    }

}
