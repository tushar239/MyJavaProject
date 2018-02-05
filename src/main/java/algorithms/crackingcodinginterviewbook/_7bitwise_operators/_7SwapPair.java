package algorithms.crackingcodinginterviewbook._7bitwise_operators;

/*
pg 288 of Cracking Coding Interview book

Pairwise Swap:
Write a program to swap odd an even bits in a n integer with as few instructions as possible
(e.g. bit 0 and 1 are swapped, bit 2 and 3 are swapped, and so on).

Operations of individual pairs of bits would be difficult, but probably not that efficient either.

We can approach this as operating on the odds bits first, and then the even bits.


https://www.youtube.com/watch?v=GWLCF808oVI

IMPORTANT:
This is a bit intelligent algorithm. You need to remember the solution.
*/
public class _7SwapPair {

    public static void main(String[] args) {
        int swapped = swap(BitwiseManipulationUtil.convertBinaryStringToUnsignedInt("1001 1110"));
        System.out.println(BitwiseManipulationUtil.convertUnsignedIntToBinaryWithLeftPadWithZerosAndSpaces(swapped));// 0110 1101
    }

    private static int swap(int num) {
        // Obtain odd bits and shift them to even positions
        int oddBits = num & 0xaaaaaaaa; // 1010=a in hexa
        int shiftedOddBits = oddBits >>> 1;

        // Obtain even bits and shift them to odd positions
        int evenBits = num & 0x55555555;// 0101=5 in hexa
        int shiftedEvenBits = evenBits << 1;

        // combine them to get desired output
        int swappedResult = shiftedOddBits | shiftedEvenBits;
        return swappedResult;
    }

}
