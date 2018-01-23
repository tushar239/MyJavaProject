package algorithms._7bitwise_operators;

/*

Find Modulus (Remainder) of a number by dividing it by a number that is power of 2:

https://www.youtube.com/watch?v=fSjW-wDghTs&index=10&list=PLqM7alHXFySF8B9KqOy6yz4vggu4tiNMP

e.g.
    0111 - a=7
  % 0100 - b=4
    ----
    0011 - result=3

Solution:
keep all bits as it is in a before the position of 1 in b
*/
public class FindModulusDivisionByPowerOf2 {

    public static void main(String[] args) {
        int result = findModulus(7, 4);
        System.out.println(result);//3

        result = findModulus(25, 4);
        System.out.println(result);//1

        result = findModulus(36, 4);
        System.out.println(result);//0

    }

    private static int findModulus(int a, int b) {
        // find 2's power from b
        // e.g. 2^2 = 4 is same as log2 4 = 2
        // that is same as log2 b = power
        double power = Math.log(b);

        int mask = -1 >>> (31-(int) power);

        int result = a & mask;

        return result;
    }
}
