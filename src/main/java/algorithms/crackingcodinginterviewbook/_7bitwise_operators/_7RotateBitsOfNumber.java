package algorithms.crackingcodinginterviewbook._7bitwise_operators;

/*
Left Rotate/Right Rotate

https://www.youtube.com/watch?v=S2yXCBu3NdQ

e.g. 1110 0101
left rotate it 3 times
result = 0010 1111

You need to preserve bits that will be removed because of rotation and then you need to OR rotated number with preserved bits.

Same concept is for Right Rotation also.
*/
public class _7RotateBitsOfNumber {

    public static void main(String[] args) {
        System.out.println("\033[1m"+"Left Rotation... "+"\033[0m");
        String binaryStringInput = "0000 0110 0110 1001 0101 1010 1110 0101";

        {
            int num = BitwiseManipulationUtil.convertBinaryStringToUnsignedInt(binaryStringInput);

            System.out.println("Input num: "+binaryStringInput);

            int leftRotatedNum = leftRotate(num, 9);
            System.out.println("result: "+BitwiseManipulationUtil.convertUnsignedIntToBinaryWithLeftPadWithZerosAndSpaces(leftRotatedNum));// 1101 0010 1011 0101 1100 1010 0000 1100
        }
        System.out.println("\033[1m"+"Right Rotation... "+"\033[0m");
        {
            int num = BitwiseManipulationUtil.convertBinaryStringToUnsignedInt(binaryStringInput);

            System.out.println("Input num: "+binaryStringInput);

            int rightRotatedNum = rightRotate(num, 9);
            System.out.println("result: "+BitwiseManipulationUtil.convertUnsignedIntToBinaryWithLeftPadWithZerosAndSpaces(rightRotatedNum));// 0111 0010 1000 0011 0011 0100 1010 1101
        }
    }

    private static int leftRotate(int num, int rotation) {
        //System.out.println(BitwiseManipulationUtil.convertUnsignedIntToBinary(num));

        // preserve bits to be rotated
        int bitsToBeRotated = (num >>> (32 - rotation)); // 0000 0111

        System.out.println("bitsToBeRotated: "+BitwiseManipulationUtil.convertUnsignedIntToBinaryWithLeftPadWithZerosAndSpaces(bitsToBeRotated));

        int rotatedNum = num << rotation;

        System.out.println("rotatedNum: "+BitwiseManipulationUtil.convertUnsignedIntToBinaryWithLeftPadWithZerosAndSpaces(rotatedNum));

        int result = rotatedNum | bitsToBeRotated;

        return result;
    }

    private static int rightRotate(int num, int rotation) {
        // preserve bits to be rotated
        int rotatedMinusOne = -1 >>> (32 - rotation);

        System.out.println("rotatedMinusOne: "+BitwiseManipulationUtil.convertUnsignedIntToBinaryWithLeftPadWithZerosAndSpaces(rotatedMinusOne));// 1111 1111 1000 0000 0000 0000 0000 0000

        int bitsToBeRotated = num & rotatedMinusOne;
        bitsToBeRotated = bitsToBeRotated << (32 - rotation);

        System.out.println("bitsToBeRotated: "+ BitwiseManipulationUtil.convertUnsignedIntToBinaryWithLeftPadWithZerosAndSpaces(bitsToBeRotated));// 0111 0010 1000 0000 0000 0000 0000 0000

        int rotatedNum = num >>> rotation;

        System.out.println("rotatedNum: "+BitwiseManipulationUtil.convertUnsignedIntToBinaryWithLeftPadWithZerosAndSpaces(rotatedNum));     // 0000 0000 0000 0011 0011 0100 1010 1101

        int result = rotatedNum | bitsToBeRotated;

        return result;
    }
}
