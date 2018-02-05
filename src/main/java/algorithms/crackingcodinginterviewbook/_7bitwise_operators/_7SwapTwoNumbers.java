package algorithms.crackingcodinginterviewbook._7bitwise_operators;

/*
Swap two numbers:

https://www.youtube.com/watch?v=46p2OqDHck4

You need to remember this logic.
 */

public class _7SwapTwoNumbers {
    public static void main(String[] args) {
        String binaryNum1 = "0010";
        String binaryNum2 = "0110";
        swap(BitwiseManipulationUtil.convertBinaryStringToUnsignedInt(binaryNum1), BitwiseManipulationUtil.convertBinaryStringToUnsignedInt(binaryNum2));
    }

    private static void swap(int a, int b) {
        a = a ^ b;
        b = a ^ b;
        a = a ^ b;

        System.out.println("a: "+BitwiseManipulationUtil.convertUnsignedIntToBinary(a));
        System.out.println("b: "+BitwiseManipulationUtil.convertUnsignedIntToBinary(b));
    }
}
