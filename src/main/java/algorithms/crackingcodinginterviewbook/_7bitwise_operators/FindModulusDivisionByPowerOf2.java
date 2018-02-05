package algorithms.crackingcodinginterviewbook._7bitwise_operators;

/*

Find Modulus (Remainder) of a number by dividing it by a number that is power of 2:

https://www.youtube.com/watch?v=fSjW-wDghTs&index=10&list=PLqM7alHXFySF8B9KqOy6yz4vggu4tiNMP

e.g.
    7 % 4 = 3

    0111 - a=7
  % 0100 - b=4
    ----
    0011 - result=3

Solution:
keep all bits as it is in a before the position of 1 in b
*/
public class FindModulusDivisionByPowerOf2 {

    public static void main(String[] args) {
        int remainder = findModulus(7, 4);
        System.out.println(remainder);//3

        remainder = findModulus(25, 4);
        System.out.println(remainder);//1

        remainder = findModulus(36, 4);
        System.out.println(remainder);//0

    }

    private static int findModulus(int a, int b) {// a = 0111, b = 0100
        // find 2's power from b
        // e.g. 2^2 = 4 is same as log2 4 = 2
        // that is same as log2 b = power
        double power = Math.log(b); // 2

        // 1111 1111 1111 1111 1111 1111 1111 1111 >> 31-2 = 29 = 0000 0000 0000 0000 0000 0000 0000 0011
        int mask = -1 >>> (31-(int) power);

        //      0000 0000 0000 0000 0000 0000 0000 0111 = a = 7
        // &    0000 0000 0000 0000 0000 0000 0000 0011 = mask
        //      ---------------------------------------
        //      0000 0000 0000 0000 0000 0000 0000 0011 = remainder = 3

        int remainder = a & mask;

        return remainder;
    }
}
