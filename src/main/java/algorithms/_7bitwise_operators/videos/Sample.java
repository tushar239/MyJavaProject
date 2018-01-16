package algorithms._7bitwise_operators.videos;

/*

1) There are two kinds of integers in binary world:
- Unsigned
- Signed

Unsigned numbers are always +ve numbers.
Signed number can be +ve or -ve. If first bit is 0, then it is +ve. If first bit is 1, then it is -ve

2)
If someone asks you, how to write -15 in binary?
You should write 15 and do 2's complement on it.

If someone asks you to convert binary -15 to binary 15, how will you do?
You should do 2's complement on binary -15.

To convert positive to negative and vice-a-versa, you need to use 2's complement method.

128 64  32  16      8   4   2   1
0   0   0   0       1   1   1   1   = 15

How to know it's a +ve number?
If first bit is 0 in signed number, then it's a +ve number


To get -15,

    0   0   0   0       1   1   1   1   = 15
    1   1   1   1       0   0   0   0   - this is 1's complement (inversing all bits)
   +                                1   - this is 2's complement (adding 1 to inversed bits)
    ---------------------------------
    1   1   1   1       0   0   0   1   = -15

How to know, it's -15?
first bit is signed bit in case of signed integers. consider first bit value as a -ve value.

128 64  32  16      8   4   2   1
1   1   1   1       0   0   0   1

So, -128 + 64 + 32 + 16 + 1 = -15


Similarly, to convert -15 to 15, you can apply 2's complement on -15

    1   1   1   1       0   0   0   1   = -15
    0   0   0   0       1   1   1   0   - 1's complement (inversing all bits)
   +                                1   - this is 2's complement (adding 1 to inversed bits)
    ---------------------------------
    0   0   0   0       1   1   1   1   = 15


3) Shift Left and Shift Right

   Shift Left is multiplication e.g. 32 << 1 = 32 * 2^1 = 64
                                     32 << 2 = 32 * 2^2 = 128
   Shift Right is division  e.g. 32 >> 1 = 32 / 2^1 = 16
                                 32 >> 2 = 32 / 2^2 = 8
                                 -32 >> 2 = -16
                                 -32 >>> 2 = some number. It will give you the right result, but it doesn't make sense to use >>> for signed number.

   Remember:
       number << n = number * 2^n
       number >> n = number / 2^n

   There are Arithmetic (<<) Left Shifts. It is also called just Left Shift.
             Arithmetic (>>) + Logical (>>>) Right Shifts

             Arithmetic Right Shift (>>) is for taking sign into consideration.
             If you use Logical Right Shift (>>>) on signed number, then it will give you the right result, but it doesn't make sense to do so.


   In Arithmetic Left shift (<<), signed bit is automatically maintained as a first bit after shifting.
   e.g.

       1    0   1   1       0   1   0   1   =  -75
            <<1
    1  0    1   1   0       1   0   1   0   = first bit is -256. -256+64+32+8+2 = -150


        1    0   1   1       0   1   0   1   =  unsigned 181
            <<1
    1   0    1   1   0       1   0   1   0   =  362


    In Arithmetic Right shift (>>), you have to maintain the sign bit as a first bit after shifting.

    1    0   1   1       0   1   0   1   =  -75
    |
    |    >>1
    |
    | <-------- after right shifting, you need to maintain the sign bit
    v
    1    1   0   1       1   0   1   0   = -38


    Logical Right Shift (>>>)

    1    0   1   1       0   1   0   1   =  -75
        >>>1
    0    1   0   1       1   0   1   0   = 170 in 8 bits representation.

    In 32 bits representation,

    1111 1111 1111 1111 1111 1111 1111 0001 = -75
        >>>1
    0111 1111 1111 1111 1111 1111 1111 1000 = 2147483640

    So, it's not a good idea to use >>> on signed integers.

4) Addition and Subtraction

In case of addition and subtraction, trick is to thing in decimal.

Addition:

     1 1 1 1
       1 0 1 1 - 11
    +  0 1 1 1 - 7
      --------
     1 0 0 1 0 - 18

    Starting from right:
    1+1=2. 2 in binary is 1 0. So, write 0 and carry over 1.
    1+1+1=3. 3 in binary is 1 1. so write 1 and carry over 1.

    Another trick is remembering the rules
    1+1=0, carry over 1
    1+1+1=0+1=1, carry over 1

Subtraction:

        1   0   1   0   0   - 20
    -   0   0   1   1   0   - 6


    When you find
        0
    -   1
    You need to borrow two 1s(=2) from closest left 1 and make closest left to 0.


                    2
        1   0   0   0   0   = 20
    -   0   0   1   1   0   = 6
        -----------------
                    1   0  ----- (1+1)-1 = 2-1 = 1


            2
        0   0   0   0   0   = 20
    -   0   0   1   1   0   = 6
        -----------------
                    1   0


            1   2
        0   0   0   0   0   = 20
    -   0   0   1   1   0   = 6
        -----------------
        0   1   1   1   0   =  14


5) & (AND), | (OR), ^ (XOR), ~ (NOT)

     0011
   & 1001
   ------
     0001

     0011
   | 1001
   ------
     1011

  Remember:
    Diff between | and ^ is,  1 | 1 = 1  and  1 ^ 1 = 0

     0011
   ^ 1001
   ------
     1010



  Bit facts and tricks:

      x ^ 0s = x
      x ^ 1s = ~x
      x ^ x = 0s
      x ^ ~x = 1s

      x & 0s = 0s
      x & 1s = x
      x & x = x
      x & ~x = 0s

      x | 0s = x
      x | 1s = 1s
      x | x = x
      x | ~x = 1s


6) For some operations like get,set,clear,update bits, you need to remember that
   all these operations require +1 or -1.

   For these operations you need to either left shift/right shift/negate +1 or -1 and do & or | withe input number.

   Remember: 0000 0000 0000 0000 0000 0000 0000 0001 = +1
             if you take 2's complement to get -1, you will get
             1111 1111 1111 1111 1111 1111 1111 1111 = -1  (All 1s is same as -1)

 */
public class Sample {
    public static void main(String[] args) {
        int result = repeatedArithmeticRightShift(-15, 1000);
        System.out.println(result);//-1
        result = repeatedLogicalRightShift(-15, 1000);
        System.out.println(result);//0

        // Integer is represented as 4 bytes (32 bits)
        System.out.println(Integer.toBinaryString(-15));// 1111 1111 1111 1111 1111 1111 1111 0001
        result = repeatedLogicalRightShift(-15, 1);
        System.out.println(result);//2147483640
        System.out.println(Integer.toBinaryString(result));// 0111 1111 1111 1111 1111 1111 1111 1000

        result = repeatedLeftShift(-75, 1);
        System.out.println(result);//-150
        result = repeatedLeftShift(181, 1);
        System.out.println(result);//362
        System.out.println(Integer.toBinaryString(result));// 101101010


        {
            int input = -70;
            System.out.println(Integer.toBinaryString(input));// 1111 1111 1111 1111 1111 1111 1011 1010

            result = clearBits_I_Through_0(input, 3);
            System.out.println(result);// -80
            System.out.println(Integer.toBinaryString(result)); // 1111 1111 1111 1111 1111 1111 1011 0000

            result = clearBits_I_Through_0_book_way(input, 3);
            System.out.println(result);// -80
            System.out.println(Integer.toBinaryString(result));// 1111 1111 1111 1111 1111 1111 1011 0000

            result = updateBit_my_way(input, 2, true);
            System.out.println(result);// -66
            System.out.println(Integer.toBinaryString(result));// 1111 1111 1111 1111 1111 1111 1011 1110

            result = updateBit_my_way(input, 3, false);
            System.out.println(result);// -78
            System.out.println(Integer.toBinaryString(result));// 1111 1111 1111 1111 1111 1111 1011 0010

            result = updateBit_book_way(input, 2, true);
            System.out.println(result);// -66
            System.out.println(Integer.toBinaryString(result));// 1111 1111 1111 1111 1111 1111 1011 1110

            result = updateBit_book_way(input, 3, false);
            System.out.println(result);// -78
            System.out.println(Integer.toBinaryString(result));// 1111 1111 1111 1111 1111 1111 1011 0010
        }
    }

    private static int updateBit_book_way(int input, int i, boolean bitIs1) {
        int value = bitIs1 ? 1 :0;
        int mask = ~(1 << i);
        return (input & mask) | (value << i);
    }

    private static int repeatedArithmeticRightShift(int x, int count) {
        for (int i = 0; i < count; i++) {
            x >>= 1; // arithmetic right shift
        }
        return x;
    }

    private static int repeatedLogicalRightShift(int x, int count) {
        for (int i = 0; i < count; i++) {
            x >>>= 1; // logical right shift
        }
        return x;
    }


    private static int repeatedLeftShift(int x, int count) {
        for (int i = 0; i < count; i++) {
            x <<= 1; // logical right shift
        }
        return x;
    }

    private static int clearBits_I_Through_0(int num, int i) {
        int mask = -1 << (i + 1);
        //System.out.println("mask:"+mask);
        return num & mask;
    }

    private static int clearBits_I_Through_0_book_way(int num, int i) {
        int mask = ~(-1 >>> (31 - i));
        //System.out.println("mask:"+mask);
        return num & mask;
    }

    private static int updateBit_my_way(int num, int i, boolean bitIs1) {
        int value = bitIs1 ? 1 : 0;

        if (value == 1) {
            int mask = 1 << i;
            return num | mask;
        }
        int mask = ~(1 << i);
        return num & mask;
    }
}