package algorithms.crackingcodinginterviewbook._7bitwise_operators;

/*
Find next sparse number:

Understand what is sparse number - https://www.youtube.com/results?search_query=find+next+sparse+number
Easier Solution                  - https://www.youtube.com/watch?v=lGNq_xMscdo

What is sparse number?
if there are no adjacent 1s, then it is a sparse number.

01010001010101 - sparse
01010001011101 - non-sparse

0 1 0 1 0 0 0 1 0 1 1 1 0 1

There are multiple approaches to find next spars number.

1) find adjacent 1s. If found, add 1 to the number. keep doing it till you get sparse number.
   This is a brute-force approach.

2) you have to find 0 1 1 pattern in the number and turn 0 to 1 and all other bits to its right to 0s.

    0 1 0 1 0 0 0 1 1 0 0 0 0 0
    0 1 0 1 0 0 1 0 0 0 0 0 0 0

    You need to continue this process until you find next sparse number.

    This is better than first approach, but a bit harder to implement.

3) you have to find first '0 0' from right and replaces it with '0 1' and make all other bits 0 after that.

   This is the simplest approach.
*/
public class NextSparseNumber {

    public static void main(String[] args) {

        System.out.println("\033[1m"+"Trying non-sparse number as input. Output should be a next sparse number."+"\033[0m");
        {
            int num = BitwiseManipulationUtil.convertBinaryStringToUnsignedInt("01 0100 0101 1101");// non-sparse number as input
            int nextSparseNumber = findNextSparseNumber(num);

            System.out.println("Input : " + BitwiseManipulationUtil.convertUnsignedIntToBinaryWithLeftPadWithZerosAndSpaces(num));             // 0000 0000 0000 0000 0001 0100 0101 1101
            System.out.println("Output: " + BitwiseManipulationUtil.convertUnsignedIntToBinaryWithLeftPadWithZerosAndSpaces(nextSparseNumber));// 0000 0000 0000 0000 0001 0100 1000 0000
        }

        System.out.println("\033[1m"+"Trying sparse number as input. Output should be same as Input."+"\033[0m");
        {
            int num = BitwiseManipulationUtil.convertBinaryStringToUnsignedInt("0101 0101 0101 0101 0101 0101 0101 0101"); // sparse number as input
            int nextSparseNumber = findNextSparseNumber(num);

            System.out.println("Input : " + BitwiseManipulationUtil.convertUnsignedIntToBinaryWithLeftPadWithZerosAndSpaces(num));             // 0000 0000 0000 0000 0001 0101 0101 0101
            System.out.println("Output: " + BitwiseManipulationUtil.convertUnsignedIntToBinaryWithLeftPadWithZerosAndSpaces(nextSparseNumber));// 0101 0101 0101 0101 0101 0101 0101 0101
        }
    }

    private static int findNextSparseNumber(int num) { // 0000 0000 0000 0000 0001 0100 0101 1101

        int three = 3;
        int one = 1;
        int minusOne = -1;

        boolean two0sFound = false;

        // REMEMBER:
        // Integer.MIN_VALUE = 1000 0000 0000 0000 0000 0000 0000 0000 = 0x80000000 = -2147483648
        // Integer.MAX_VALUE = 0111 1111 1111 1111 1111 1111 1111 1111 = 0x70000000 =  2147483647
        while (!two0sFound && three != Integer.MIN_VALUE) {

            if ((num & three) == 0) { // finding first 00 from right
                // replacing 00 to 01
                //                               | |
                num = num | one;                       //   0000 0000 0000 0000 0001 0100 0101 1101
                // | 0000 0000 0000 0000 0001 0000 1000 0000
                //   ---------------------------------------
                // replacing all bits to 0 after 01    //   0000 0000 0000 0000 0001 0100 1101 1101
                num = num & minusOne;                  // & 1111 1111 1111 1111 1111 1110 0000 0000
                //   ---------------------------------------
                //   0000 0000 0000 0000 0001 0100 1000 0000

                return num;
            }

            three = three << 1;
            one = one << 1;
            minusOne = minusOne << 1;
        }

        return num;
    }

}
