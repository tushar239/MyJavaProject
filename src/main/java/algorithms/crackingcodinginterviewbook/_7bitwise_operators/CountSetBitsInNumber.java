package algorithms.crackingcodinginterviewbook._7bitwise_operators;

/*

Count number of 1s in a given number

https://www.youtube.com/watch?v=KJnhAUkxAho&index=2&list=PLqM7alHXFySF8B9KqOy6yz4vggu4tiNMP

Solution:
apply a simple rule of anding n with n-1 till n==0
 */
public class CountSetBitsInNumber {

    public static void main(String[] args) {
        int num = BitwiseManipulationUtil.convertBinaryStringToUnsignedInt("1101 0101");

        System.out.println(count(num));

        System.out.println(count_another_way(num));
    }

    // more efficient
    private static int count(int num) {

        int count = 0;
        while (num != 0) {
            //System.out.println("inside-1");
            num = num & (num - 1);// this clears right most set bit (sets right most 1 to 0)
            count++;
        }
        return count;
    }

    private static int count_another_way(int num) {

        int count = 0;
        while (num != 0) {
            //System.out.println("inside-2");
            int temp = num & 1;
            if (temp != 0) count++;
            num = num >>> 1;
        }
        return count;
    }
}
