package algorithms._7bitwise_operators;

public class _4NextNumber {
    public static void main(String[] args) {

        int num = BitwiseManipulationUtil.convertBinaryStringToUnsignedInt("1101 0110");
        //System.out.println(num);// 214

        int nextIncreasedNumberWithSameNumberOf1s = nextIncreasedNumberWithSameNumberOf1s(num);
        String binaryOfNextIncreasedNumberWithSameNumberOf1s = BitwiseManipulationUtil.convertUnsignedIntToBinary(nextIncreasedNumberWithSameNumberOf1s);
        System.out.println(binaryOfNextIncreasedNumberWithSameNumberOf1s);// 1101 1001
    }

    private static int nextIncreasedNumberWithSameNumberOf1s(int num) {
/*
         e.g.

         128    64  32  16      8   4   2   1
         1      1   0   1       0   1   1   0

         To increase the number, we definitely have to find a 0 from left that has immediate 1 to right.
         So that we can flip that 0 to 1 and 1 to 0 for minimum increase of number.

         If we fip 8 to 1 and 4 to 0, we will get next minimum increased number by keeping number of 1s same.


*/

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
        int temp = num;

        int numberOf1sFromRight = 0;

        int one = 1;

        int locationOf0WithImmediate1AtRight = -1;

        int count = 0;

        while (count < 31) {// you can left shift at the most 0 to 31 = 32 times any int. after that value doesn't change. It will be all 0 bits after 32 left shifts.
            if ((temp & one) != 0) {// 1 is found from right

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
            return -1; // no 0 with immediate 1 right to it found
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
}
