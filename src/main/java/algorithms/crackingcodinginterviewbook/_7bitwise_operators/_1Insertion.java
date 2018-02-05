package algorithms.crackingcodinginterviewbook._7bitwise_operators;

/*
Insertion:

Insert 0s from i to j bits

e.g.

In Java, integer is 32 bits. For simplicity, we are using just 8 bits for example

1011 1110

insert 0s from i=2 to j=4

Result: 1010 0010

 */
public class _1Insertion {

    public static void main(String[] args) {

        int input = 190;
        System.out.println("Input number: "+Integer.toBinaryString(input));// 1011 1110

        int result = insert(190, 2, 4);
        System.out.println(result);
        System.out.println(Integer.toBinaryString(result));
    }
    private static int insert(int num, int i, int j) {
        //System.out.println(Integer.toBinaryString(-1 << 5));

        // -1 = 1111 1111


        int leftMask = -1 << (j+1); // -1 << 5 = 1110 0000
        System.out.println(leftMask);
        System.out.println("left mask: "+Integer.toBinaryString(leftMask));// 1110 0000

        int rightMask = -1 >>> (32 - i); // -1 >>> 8-6(in case of 8 bits number) = 0000 0011
        System.out.println("right mask: "+Integer.toBinaryString(rightMask));// 0000 0011

        int mask = leftMask | rightMask; //  1110 0000 | 0000 0011 = 1110 0011
        System.out.println("mask: "+Integer.toBinaryString(mask));// 1100 0011

        return (num & mask);// 101 000 10 --- from 2 to 4 bits are cleared (replaced by 0s)
    }
}
