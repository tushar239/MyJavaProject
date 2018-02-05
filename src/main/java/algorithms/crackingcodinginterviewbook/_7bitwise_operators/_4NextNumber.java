package algorithms.crackingcodinginterviewbook._7bitwise_operators;

/*
    pg 282 og Cracking Coding Interview book.

    Next Number:
    given a positive integer, print the next smallest and the next largest number that have the same number of 1 bits
    in their binary representation.
*/
public class _4NextNumber {
    public static void main(String[] args) {

        {
            String binaryString = "1101 0110";
            int num = BitwiseManipulationUtil.convertBinaryStringToUnsignedInt(binaryString);

            System.out.println("Original number:                                "+binaryString.replace(" ",""));// 1101 0110

            int nextIncreasedNumberWithSameNumberOf1s = nextIncreasedNumberWithSameNumberOf1s(num);

            String binaryOfNextIncreasedNumberWithSameNumberOf1s = BitwiseManipulationUtil.convertUnsignedIntToBinary(nextIncreasedNumberWithSameNumberOf1s);
            System.out.println("Next Increased number with same number of 1s:   "+binaryOfNextIncreasedNumberWithSameNumberOf1s);// 1101 1001
        }

        System.out.println();

        {
            String binaryString = "1101 1001";
            int num = BitwiseManipulationUtil.convertBinaryStringToUnsignedInt(binaryString);

            System.out.println("Original number:                                "+binaryString.replace(" ",""));// 1101 0101

            int nextDecreasedNumberWithSameNumberOf1s = nextDecreasedNumberWithSameNumberOf1s(num);

            String binaryOfNextDecreasedNumberWithSameNumberOf1s = BitwiseManipulationUtil.convertUnsignedIntToBinary(nextDecreasedNumberWithSameNumberOf1s);
            System.out.println("Next Decreased number with same number of 1s:   "+binaryOfNextDecreasedNumberWithSameNumberOf1s);// 1101 0101

        }


    }

    /*
         e.g.

         128    64  32  16      8   4   2   1
         1      1   0   1       0   1   1   0

         To increase the number, we definitely have to find a 0 from right that has immediate 1 to its right.
         So that we can flip that 0 to 1 and 1 to 0 for minimum increase of number.

         If we fip 8 to 1 and 4 to 0, we will get next minimum increased number by keeping number of 1s same.
    */
    private static int nextIncreasedNumberWithSameNumberOf1s(int num) {// num = 1101 0110

        /*
         find the location of first 0 from right that has 1 at immediate right.
         e.g. in 1101 0110
         index of 0 with 1 at immediate right is 3
         you keep &ing this number with left shifted 1. If result is 0, then it's that bit is 0, otherwise that bit is 1.

              1101 0110 --- 0th bit is 0 because result == 0
            & 0000 0001
              ---------
              0000 0000

              1101 0110 --- 1st bit is 1 because result is != 0. numberOf1s = 1.
            & 0000 0010
              ---------
              0000 0010

              1101 0110 --- 2nd bit is 1 because result is != 0. numberOf1s = 2.
            & 0000 0100
              ---------
              0000 0100

              1101 0110 --- 3rd bit is 0 because result is == 0
            & 0000 1000
              ---------
              0000 0000

              We found 0 with 1 at immediate right to it because numberOf1s > 0;

              index=3 is the location of 0 with 1 at immediate right to it.

        */

        int numberOf1sFromRight = 0;

        int one = 1;

        int locationOf0WithImmediate1AtRight = -1;

        int count = 0;

        while (count < 31) {// you can left shift at the most 0 to 31 = 32 times any int. after that value doesn't change. It will be all 0 bits after 32 left shifts.
            if ((num & one) != 0) {// 1 is found from right

                numberOf1sFromRight++;

            } else { // if 0 is found

                if (numberOf1sFromRight > 0) { // it next bit to the right of 0 is 1
                    locationOf0WithImmediate1AtRight = count;
                    break;
                }

            }
            count++;

            one = one << 1;
        }

        // 0 with immediate 1 at right is not found
        if (locationOf0WithImmediate1AtRight == -1) {
            return -1;
        }

        /*
            flipping 3rd bit with 1

              1101 0110
            | 0000 1000
              ---------
              1101 1110  - finalNumber
        */

        int oneLeftShifted = 1 << locationOf0WithImmediate1AtRight;

        int finalNumber = num | oneLeftShifted;


        /*
            flipping 0th to 2nd bit to 0

              1101 1110 - finalNumber
            & 1111 1000
              ---------
              1101 1000 - finalNumber

              So now we incremented the original number, but number of 1s are lesser than original number.
        */

        int leftShiftedMinusOne = -1 << locationOf0WithImmediate1AtRight;

        finalNumber = finalNumber & leftShiftedMinusOne;


        /*
            To keep number of 1s same, you need to or incremented number with left shifted 1s.
            You need to do this number of 1s that you found (numberOf1sFromRight).

            1101 1000 - finalNumber
          | 0000 0001
            ---------
            1101 1001

         */
        one = 1;

        for (int i = 0; i < numberOf1sFromRight - 1; i++) {
            finalNumber = finalNumber | one;
            one = one << 1;
        }

        return finalNumber;
    }

    /*
         e.g.

         128    64  32  16      8   4   2   1
         1      1   0   1       1   0   0   1

         To decrease the number, we definitely have to find a 1 from right that has immediate 0 to its right.
         e.g. here that index is 3
              flip 3rd index to 0 and to keep number of 1s same in such a way that decreased number is closer to original number, you flip 2nd index to 1.
    */
    private static int nextDecreasedNumberWithSameNumberOf1s(int num) {// num = 1101 1001

        // just rewriting and slightly modifying the code written in nextIncreasedNumberWithSameNumberOf1s(num) method to find the location of 1 with immediate 0.
        int numberOf0sFromRight = 0;

        int one = 1;

        int locationOf1WithImmediate0AtRight = -1;

        int count = 0;

        while (count < 31) {// you can left shift at the most 0 to 31 = 32 times any int. after that value doesn't change. It will be all 0 bits after 32 left shifts.

            if ((num & one) != 0) {// 1 is found from right

                if (numberOf0sFromRight > 0) { // it next bit to the right of 0 is 1
                    locationOf1WithImmediate0AtRight = count;
                    break;
                }

            } else { // if 0 is found

                numberOf0sFromRight++;

            }
            count++;

            one = one << 1;
        }


        // 1 with immediate 0 at right is not found
        if (locationOf1WithImmediate0AtRight == -1) {
            return -1;
        }

        /*
            locationOf1WithImmediate0AtRight = 3

            1101 1001 - original number
          ^ 0000 1000 - XORing 3rd bit with a bit 1
            ---------
            1101 0001 - finalNumber
        */

        int leftShifted1s = 1 << locationOf1WithImmediate0AtRight;

        int finalNumber = num ^ leftShifted1s; // 1101 0001


        /*
            Now just flip 2nd bit to 1 to keep number of 1s same
        */

        leftShifted1s = 1 << (locationOf1WithImmediate0AtRight-1);

        finalNumber = finalNumber | leftShifted1s;

        return finalNumber; // 1101 0101
    }
}
