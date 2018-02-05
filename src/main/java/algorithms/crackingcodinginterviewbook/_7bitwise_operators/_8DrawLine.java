package algorithms.crackingcodinginterviewbook._7bitwise_operators;

/*
Draw a line:

http://www.goldsborough.me/bits/c++/low-level/problems/2015/10/11/23-52-02-bit_manipulation/#drawing-a-line-in-a-monochrome-screen
 */
public class _8DrawLine {

    public static void main(String[] args) {
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
        if (endPixel < startPixel) return;

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
            screen[i] = (byte) 0xFF;// is same as (byte)-1,
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
