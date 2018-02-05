package algorithms.crackingcodinginterviewbook._7bitwise_operators;

import java.util.ArrayList;
import java.util.List;

public class _3FlipBitToWin {

    public static void main(String[] args) {
/*     Some testing statements

        //System.out.println(Integer.toBinaryString(-15));//1111 1111 1111 1111 1111 1111 1111 0001
        System.out.println(Integer.toUnsignedLong(-1));// 4294967295

        System.out.println(15 | -1);

        System.out.println(Integer.MAX_VALUE);// 2147483647
        System.out.println(Math.pow(2, 31));  // 2.147483648E9
        double some = Math.pow(2, 31) + Math.pow(2, 30) + Math.pow(2, 29);
        System.out.println(some);
        //System.out.println(Integer.valueOf("" + some));
        //System.out.println(Integer.valueOf("" + some) << 1);

        System.out.println(13 ^ 1);
        System.out.println(128 ^ -1);// doing ^ with -ve value or ~0 give -ve value
        System.out.println(128 << 1);// if you left shift a number for more than certain times that reaches a number > 2^30, it will go beyond Integer.MAX_VALUE. So, avoid doing left shifting.
        System.out.println("Anding or Oring with -ve value gives +ve value :" + (128 & -1));// & and | with -ve value or ~0 give +ve value

        System.out.println(Math.pow(2, 31) > Integer.MAX_VALUE);// true. so you cannot left shift a number
        System.out.println(Math.pow(2, 30) > Integer.MAX_VALUE);//false
        System.out.println(Math.pow(2, 30) == Integer.MAX_VALUE);//false

        //System.out.println(127 ^ -129);
*/

        String binaryString = "1011 1011 1111 1101";// 48125

        int num = BitwiseManipulationUtil.convertBinaryStringToUnsignedInt(binaryString);
        System.out.println(num);

/*
        String binary = Integer.toBinaryString(num);
        System.out.println(binary);
*/

        int lengthOfLongestPossibleSequenceOf1sByTurningOne0BitTo1 = findLengthOfLongestSequenceOf1sAfterFlippingExactlyOne0(num);
        System.out.println(lengthOfLongestPossibleSequenceOf1sByTurningOne0BitTo1);
    }

    private static int findLengthOfLongestSequenceOf1sAfterFlippingExactlyOne0(int num) {

        /*
            1110 1111 1111 1101 1111 1111 1111 1101
        ^   1111 1111 1111 1111 1111 1111 1111 1111
            ---------------------------------------
            0001 0000 0000 0010 0000 0000 0000 0010
         */
/*
        int temp = num;

        List<Integer> positionsOfOnes = new ArrayList<>();
        if (temp >= Math.pow(2, 31)) {
            positionsOfOnes.add(31);
        }
        for (int i = 31; i >= 0; i--) {
            temp = temp << 1; // This can give -ve value also. Because this returns signed int.

            // IMPORTANT: you cannot do this because temp is int and doing left shifting (<<) on it multiple times may reserve it beyond max value int. So, to avoid that java considers last bit (31st bit) as a sign bit because 2^31 is beyond the limit of max int.
            // so, comparison of left shifted number with some other number should be avoided.
            // you can still use it for bitwise operations though. Just thinking of it as bits (and not as number)

            // Number beyond 2^30 goes beyond max value of int
            // 1000 0000 0000 0000 0000 0000 0000 0000  is beyond Integer.MAX_VALUE
            // so int value of this bits will -ve, java will consider 31st bit(last bit) as sign bit.

            // This is not the problem with >>> because >>> reduces the number to half. It will not go beyond max/min value of integer.
            // But it has another problem. It can turn -ve value to +ve
            // e.g. -1 >>> 1 will give you 2147483647.

            //So, you can use all bitwise operations as far you not using the result of bitwise operation to compare with some int value.

            if (temp >= Math.pow(2, 31)) {
                positionsOfOnes.add(i);
            }

        }

        System.out.println(positionsOfOnes);
*/

        int xoredNum = num ^ -1;
        System.out.println(Integer.toBinaryString(xoredNum));// 1111 1111 1111 1111 0100 0100 0000 0010
        System.out.println("xoredNum: " + xoredNum);// -48126

        // find the positions of 0s in num
        List<Integer> positionsOfZeros = new ArrayList<>();

        /*int tempNum = num;
        int count = 0;

        for (int i = 0; i < 32; i++) { // as input num is int, number of bits cannot be more than 32

            int prevTempNum = tempNum;
            tempNum = tempNum >>> 1; // IMPORTANT: if num is a -ve number, LRS on -ve number can result in +ve number. This can lead to wrong result.

            //System.out.println(Integer.toBinaryString(tempNum));

            if (prevTempNum == (tempNum * 2)) {
                positionsOfZeros.add(count);
            }
            count++;
        }*/

        // either you need to use 32 for int or you need to use how many bits you need to consider. book uses later approach. I am using better approach.
        for (int i = 0; i < 32; i++) { // as input num is int, number of bits cannot be more than 32

            int endedWithLeftShifted1 = num & (1 << i);

            //System.out.println(Integer.toBinaryString(endedWithLeftShifted1));

            if (endedWithLeftShifted1 == 0) {
                positionsOfZeros.add(i);
            }
        }

        System.out.println("Positions of 0s: " + positionsOfZeros); // [1, 10, 14, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31]


        /*
        Now positions of 0s are 1, 10, 14, 16, ....

        If turn 0 to 1 at pos 10, you need to find the length of 1s from pos=1 to 14
        If turn 0 to 1 at pos 14, you need to find the length of 1s from pos=10 to 16
        So, find the difference between 1 and 14, 10 and 16 and so on and keep the max.
        if max is the difference between 1 and 14, then number of 1s between pos 1 and 14 will be (14-1)-1

         */
        int maxLengthOfSequence = 0;
        for (int i = 0; i < positionsOfZeros.size(); i++) {
            int rightPosOf0 = positionsOfZeros.get(i);

            if ((i + 2) <= positionsOfZeros.size() - 1) {
                int leftPosOf0 = positionsOfZeros.get(i + 2);

                int lengthOfSequence = leftPosOf0 - rightPosOf0 - 1;

                if (lengthOfSequence > maxLengthOfSequence) {
                    maxLengthOfSequence = lengthOfSequence;
                }

            } else {
                break;
            }

        }

        return maxLengthOfSequence;
    }

/*
    private static String convertDecimalToBinaryString(int num) {
        List<String> binary = new ArrayList<>();
        while (true) {
            int remainder = num / 2;

            int quotient = num % 2;

            binary.add("" + quotient);

            if (remainder < 2) {
                binary.add("" + remainder);
                break;
            }

            num = remainder;
        }

        Collections.reverse(binary);

        String str = "";
        for (String s : binary) {
            str += s;
        }

        // adding leading 0s to make the binary string of size 32
//        for (int i = 0; i <= 33 - str.length(); i++) {
//            str = "0" + str;
//        }

        // adding space after 4 bits
//        String finalStr = "";
//        int count = 0;
//        for (int i = 0; i < str.length(); i++) {
//            if (count == 4) {
//                count = 0;
//                finalStr = finalStr + " ";
//            }
//            finalStr = finalStr + str.charAt(i);
//            count++;
//        }

        return str;

    }*/

}
