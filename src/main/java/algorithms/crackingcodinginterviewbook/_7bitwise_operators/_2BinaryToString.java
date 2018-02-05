package algorithms.crackingcodinginterviewbook._7bitwise_operators;

/*
Binary to String:
Given a real number between 0 and 1(e.g. 0.72) that is passed in as a double,
print the binary representation.
If the number cannot be represented accurately in binary with at most 32 characters, print "ERROR".

Learn conversions first from BitManipulationFundamentals.java

*/
public class _2BinaryToString {

    public static void main(String[] args) {
        String binaryString = printBinary(0.72);
        System.out.println(binaryString); // ERROR

        binaryString = printBinary(0.25);
        System.out.println(binaryString); // 0.01

    }

    private static String printBinary(double num) {
        if (num >= 1 || num <= 0) {
            return "ERROR";
        }

        // This is a simple logic to convert floating point decimal to binary. See BitManipulationFundamentals.java.
        StringBuilder sb = new StringBuilder();
        sb.append(".");

        while (num > 0) {

            if (sb.length() >= 32) {
                return "ERROR";
            }

            num = num * 2;

            if (num >= 1) {
                sb.append("1");
                num = num - 1;
                continue;
            }
            sb.append("0");
        }

        return sb.toString();
    }
}
