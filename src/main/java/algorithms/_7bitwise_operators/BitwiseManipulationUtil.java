package algorithms._7bitwise_operators;

public class BitwiseManipulationUtil {
    public static int convertBinaryStringToUnsignedInt(String binaryString) {
        binaryString = binaryString.replaceAll(" ", "");
        int length = binaryString.length();

        int num = 0;
        int power = 0;
        for (int i = length - 1; i >= 0; i--) {
            String c = "" + binaryString.charAt(i);

            num += Integer.valueOf(c) * (Math.pow(2, power));
            power++;
        }

        return num;
    }

    public static String convertUnsignedIntToBinary(int num) {
        return Integer.toBinaryString(num);
    }
}
