package algorithms.crackingcodinginterviewbook._7bitwise_operators;

/*
    pg 287 of Cracking Coding Interview book

    Conversion:
    Write a function to determine the number of bits you would need to flip to convert integer A to integer B.
*/
public class _6Conversion {


    public static void main(String[] args) {
        int a = BitwiseManipulationUtil.convertBinaryStringToUnsignedInt("1111 1101");
        int b = BitwiseManipulationUtil.convertBinaryStringToUnsignedInt("0110 0101");
        System.out.println("Approach - 1");
        {
            int swapsRequired = bitSwapRequired_approach_1(a, b);
            System.out.println(swapsRequired);
        }
        System.out.println("Approach - 2 - Better approach");
        {
            int swapsRequired = bitSwapRequired_approach_2(a, b);
            System.out.println(swapsRequired);
        }


    }

    private static int bitSwapRequired_approach_1(int a, int b) {
        int numberOfSwapRequired = 0;

        int count = 0;
        while (count < 32) { // this loop will go 32 times. There can be better approach.

            int leftShifted = 1 << count;

            //System.out.println(leftShifted);

            int aa = a & leftShifted;
            int bb = b & leftShifted;

            //System.out.println(a);
            //System.out.println(b);

            if ((aa == 0 && bb == 0) || (aa != 0 && bb != 0)) {
                count++;
                continue;
            }
            numberOfSwapRequired++;
            count++;
        }

        return numberOfSwapRequired;
    }

    private static int bitSwapRequired_approach_2(int a, int b) {

        /*
            1111 1101
          ^ 0110 0101
            ---------
            1001 1000 --- this will give you differentiating  bits
         */
        int xoredValue = a ^ b;

        // you now either count number of 1s by doing left shifting of 1 and doing end with xored value counting the number of time result is != 0
        // or can do something to clear all bits of xored value till value becomes 0
        int count = 0;
        while (xoredValue != 0) {
            xoredValue = xoredValue & (xoredValue - 1); // Remember: n & (n-1) clears the right most bit (flips rightmost 1 to 0).
            count++;
        }

        return count;
    }
}
