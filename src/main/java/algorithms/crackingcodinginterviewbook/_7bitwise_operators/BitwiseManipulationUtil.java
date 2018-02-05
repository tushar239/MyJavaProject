package algorithms.crackingcodinginterviewbook._7bitwise_operators;

import org.apache.commons.lang.StringUtils;

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

    public static String convertUnsignedByteToBinary(byte num) {
        return Integer.toBinaryString(Byte.toUnsignedInt(num));
    }

    public static String convertUnsignedIntToBinary(int num) {
        return Integer.toBinaryString(num);
    }

    public static String convertUnsignedIntToBinaryWithLeftPadWithZeros(int num) {
        return StringUtils.leftPad(convertUnsignedIntToBinary(num), 32, '0');
    }

    public static String convertUnsignedByteToBinaryWithLeftPadWithZeros(byte num) {
        return StringUtils.leftPad(convertUnsignedIntToBinary(num), 8, '0');
    }

    public static String convertUnsignedIntToBinaryWithLeftPadWithZerosAndSpaces(int num) {
        return insertPeriodically(convertUnsignedIntToBinaryWithLeftPadWithZeros(num), " ", 4);
    }

    // taken from https://stackoverflow.com/questions/537174/putting-char-into-a-java-string-for-each-n-characters
    private static String insertPeriodically(
            String text, String insert, int period) {
        StringBuilder builder = new StringBuilder(
                text.length() + insert.length() * (text.length() / period) + 1);

        int index = 0;
        String prefix = "";
        while (index < text.length()) {
            // Don't put the insert in the very first iteration.
            // This is easier than appending it *after* each substring
            builder.append(prefix);
            prefix = insert;
            builder.append(text.substring(index,
                    Math.min(index + period, text.length())));
            index += period;
        }
        return builder.toString();
    }

}
