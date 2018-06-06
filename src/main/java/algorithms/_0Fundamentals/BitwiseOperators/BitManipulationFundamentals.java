package algorithms._0Fundamentals.BitwiseOperators;

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
             You should use >>> only to insert 0s to left, do num >>> n to replace n bits by 0s. It is a better option than >> in this case because it does not retain signed bit.


   In Arithmetic Left shift (<<), signed bit is automatically maintained as a first bit after shifting.

   Basically, if you do arithmetic left or right shift on -ve number, result will be -ve number.
              if you do logical right shift on -ve number, then it is not guaranteed that result will be -ve number.

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


4) Addition and Subtraction

Watch 'Binary Addition and Subtraction.mp4'

In case of addition and subtraction, trick is to thing in decimal.

Addition:

     1 1 1 1
       1 0 1 1 = 11
    +  0 1 1 1 = 7
      --------
     1 0 0 1 0 = 18

    Starting from right:
    1+1=2. 2 in binary is 1 0. So, write 0 and carry over 1.
    1+1+1=3. 3 in binary is 1 1. so write 1 and carry over 1.

    Another trick is remembering the rules
    1+1=0, carry over 1
    1+1+1=0+1=1, carry over 1

Subtraction:

        1   0   1   0   0   - 20
    -   0   0   1   1   0   - 6


    When you see
        0
    -   1
    You need to borrow two 1s(=2) from closest left side 1 and make that closest left = its value-1.


                    2
        1   0   0   0   0   = 16
    -   0   0   1   1   0   = 6
        -----------------
                    1   0  ----- 2-1 = 1


            2
        0   0   0   0   0
    -   0   0   1   1   0
        -----------------
                    1   0


            1   2
        0   0   0   0   0
    -   0   0   1   1   0
        -----------------
        0   1   1   1   0

            1   1   2
        0   0   0   0   0
    -   0   0   1   1   0
        -----------------
        0   1   0   1   0 = 10


    another example


        1   0   1   0   = 10
    -   0   0   1   1   = 3

                    2
        1   0   0   0
    -   0   0   1   1
        -------------
                    1

            2
        0   0   0   0
    -   0   0   1   1
        -------------
                    1

            1   2
        0   0   0   0
    -   0   0   1   1
        -------------
        0   1   1   1   = 7


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
    Diff between | and ^ is:  x | 1s = 1
                              x ^ x = 0 ---- it means that XORing any number with itself results in 0.
                              x ^ ~x = 1 ---- it means that XORing any number with its negated value results in 1.
                              x ^ 1s = ~x

     0011
   ^ 1001
   ------
     1010



  Bit facts and tricks:

      x ^ 0s = x
      x ^ 1s = ~x
      x ^ x = 0s    ----- IMPORTANT: It means that XORing any number with itself results in 0. 7 ^ 7 = 0 and 7 ^ 0 = 7
      x ^ ~x = 1s   ---- Remember

      x & 0s = 0s
      x & 1s = x
      x & x = x
      x & ~x = 0s

      x | 0s = x
      x | 1s = 1s
      x | x = x
      x | ~x = 1s

6) Converting Floating Point Binary to Decimal

    1   1   .   1       1       1
   2^1 2^0  .  1/2^1    1/2^2   1/2^3
   2    1       .5      .25     .125

   3.875

7) Conversions

    Watch 'decimal binary octal hexa conversions.mp4'

    Rule:
    There are two method to convert integer decimal to any other type.
        - division (simple)
        - dec to binary to any other type

    To convert decimal floating point value to any other type, process is simple
        You need to multiply value by 2/8/16. Save integer value and keep multiplying with remaining value till remaining value is 0.

    To convert octal/hexa floating point value to decimal,
        you first need to convert it into binary and then binary to decimal.

    To convert dec/oct/hexa to oct/hexa/dec, you first need to convert to binary and then binary to dec/oct/hexa.


    Decimal to Binary Conversion
    ----------------------------

    (25)     =   (?)
        10          2

        2| 25
         | 12 -> 1  ^       =       11001
         | 6  -> 0  |
         | 3  -> 0  |
         | 1  -> 1  |
           |        |
           ----------



    (.25)   =   (?)
        10         2


        Watch 'Floating Point to Binary.mp4'

        0.25 * 2 = 0.5 * 2 = 1.0 (0 remained, so stop)
                    |        |                              = .01
                    v        v
                    0        1


        String str = ".";

        while(n > 0) {

            n = n*2;

            if(n < 1) {
                str += "0";
            } else if (n >= 1) P
                str += "1";
                n = n-1;
            }
        }


    Decimal to Octal Conversion
    ---------------------------

    (25)    =   (?)
       10          8


       Method 1:

            8| 25
             | 3  -> 1  ^       =       31
               |        |
               ----------

            (25)    =   (31)
               10          8


        Method 2:

            You always need to convert into binary first.

            decimal -> binary -> octal

            binary = 11001

            To convert binary to octal, you need to make triplets of bits from right to left
            11  001
            Now represent these binary triplets in decimal
            3   1

            (25)    =   (31)
           10          8



    (.25)   =   (?)
        10         8

        .25 * 8 = 2.0  (0 remained, so stop)
                  |
                  v
                  2

        (.25)   =   (.2)
            10          8

    Decimal to Hexa Conversion
    ---------------------------

    (25)    =   (?)
       10          16

       Method 1:

           16| 25
             | 1  -> 9  ^       =       19
               |        |
               ----------

            (25)    =   (19)
               10          16


        Method 2:

            You always need to convert into binary first.

            decimal -> binary -> hexa

            binary = 11001

            To convert binary to octal, you need to make quadruplets of bits from right to left
            1 1001
            Now represent these binary triplets in decimal
            1   9

            (25)    =   (19)
               10          16



    (.25)   =   (?)
        10         16

        .25 * 16 = 4.0  (0 remained, so stop)
                   |
                   v
                   4

        (.25)   =   (.4)
            10          16


    Octal to Decimal Conversion
    ---------------------------

    (31)    =   (?)
       8          10

        first convert octal to binary

        octal -> binary -> decimal

        Make triplets of binary bits for every octal digit

        3       1
       011     001

       (31)    =   (011001)
           8               2

       (011001) =   (25)
               2        10


    (.2)   =   (?)
        8         10

        (.2)    =   (.010)
            8             2

        (.010)  =   (.25)
             2          10

    Hexa to Decimal Conversion
    ---------------------------

    (19)    =   (?)
       16          10

        first convert hexa to binary

        hexa -> binary -> decimal

        Make quadruplets of binary bits for every octal digit

        1       9
       0001    1001

       (31)    =   (0001 1001)
           8                 2

       (0001 1001) =   (25)
                 2        10


    (.4)   =   (?)
        16        10

        (.4)    =   (.0100)
            8              2

        (.0100)  =   (.25)
              2          10


8) Memorize these points:

   - Diff between OR(|) and XOR(^) is:

        1 | 1  = 1 in OR
        1 ^ 1  = 0 in XOR ----- It means that XORing any number with itself results in 0.
        1 ^ -1 = 1 in XOR ----- It means that XORing any number with its negated value results in 1.

        7 ^ 7  = 0 --- IMPORTANT
        7 ^ ~7 = all 1s
        7 ^ 0s  = 7
        7 ^ 1s  = ~7

        e.g. FindAnElementThatAppearsOneWhereEveryOtherElementAppearsTwice.java

   - XOR of two numbers gives differentiating bits

            1111 1101   - a
          ^ 0110 0101   - b
            ---------
            1001 1000 --- this will give you differentiating  bits

        e.g. Conversion.java, ReverseStringWithoutUsingTempVariable.java

   - XOR can be used to swap two numbers in an array/string (string is char array only)

     A = [1,2,3,4,5]

     to replace start and end elements

     A[start] = A[start] ^ A[end] = 1 ^ 5 = 4
     A[end]   = A[start] ^ A[end] = 4 ^ 5 = 1
     A[start] = A[start] ^ A[end] = 4 ^ 1 = 5

     This method can be used, if you are not allowed to use a temp variable to swap two numbers in an array.

   - Other interesting XOR algorithms
        FindMissingNumberInArray.java
        FindANumberOccurringOddNumberOfTimes.java

   - a+b = a^b  + a&b

     EqualSumAndXor.java

     So, a^b = a+b - a&b

    So, sometimes, XORing one number with some other number gives either summation or subtraction of those numbers.
        e.g.
        24^16=40 (24+16)
        24^32=8  (32-24)

   - REMEMBER bits in min and max values of Integer

     Integer.MIN_VALUE = 1000 0000 0000 0000 0000 0000 0000 0000 = 0x80000000 = -2147483648
     Integer.MAX_VALUE = 0111 1111 1111 1111 1111 1111 1111 1111 = 0x70000000 =  2147483647

   - To insert n 0s to left, do num >>> n
     To insert n 0s to right, do num << n

     e.g.
     1111 1111 >>> 4 = 0000 1111
     1111 1111 <<  4 = 1111 0000

   - 1111 1111 (All 1s)
     All 1s is same as -1 or ~0

   -128 64  32  16      8   4   2   1  = -1 = ~0
     1  1   1   1       1   1   1   1

     0000 0000 0000 0000 0000 0000 0000 0001 = +1
     if you reserve 2's complement to get -1, you will get
     1111 1111 1111 1111 1111 1111 1111 1111 = -1  (All 1s is same as -1 or ~0 or 0xFFFFFFFF)

     You will need to use All 1s in many algorithms. So REMEMBER it.

   - For some operations like get,set,clear,update bits, you need to remember that
     all these operations require some shifting operation on +1 or -1.

     For these operations you need to either <</>>>/~ operations +1 or -1
     and do &,|,^ operation with input number.

   - ALS/LS - Arithmetic Left Shift (<<)
     ARS    - Arithmetic right shift (>>) --- you seldom use it for algorithms
     LRS    - Logical right shift (>>>) --- used only for placing all 0s for first n bits  num >>> n will replace first n bits with 0s when it is right shifting bits n times.

     ARS of more than bits in -ve number result in -1 or ~0, not 0   ---  IMPORTANT
     e.g. -15 >> 1000 = -1 or ~0 (not 0)
          -15 in binary is 2's complement of 15 = 1111 0001
          1111 0001 >> 1000 = 1111 1111 = all 1s is same as -1 or ~0

     LRS on -ve number gives some +ve number. This will give you some number.   ---  IMPORTANT
     e.g. -15 >>> 1 = 2147483640

     LRS or ALS more than bits in any number result in 0
     e.g. 15 >>> 1000 = 0, -15 >>> 1000 = 0
          15 << 1000 = 0, -15 << 1000 = 0

   - All bitwise operations (except >>>) on +ve number can lead to -ve number.
     In Java, int has 32 bits, but if you turn all 32 bits on (1111 1111 1111 1111 1111 1111 1111 1111 = -1),
     it will become -ve value instead of +ve 2^31 value. Java takes 1st bit as sign bit because 2^31 goes beyond Integer.MAX_VALUE.
     So, java cannot handle it. Till 2^30 + 2^29 + .....+2^0, value is below Integer.MAX_VALUE.

     This problem does not exist with just one operation and that LRS (>>>) because it reduces the value by dividing the value in half.
     It's downside is that it can change -ve value to +ve
     e.g. 1111 1111 1111 1111 1111 1111 1111 1111 >>> 1 = 0111 1111 1111 1111 1111 1111 1111 1111
          This changed -1 to 2147483647

     VERY IMPORTANT:
     So, you can use all bitwise operations as far you are not using the result of bitwise operation to compare with some int value.

    e.g. FlipBitToWin.java

   - n & (n-1) clears the right most bit (flips right most 1 to 0)

        1101    - 13
    &   1100    - 12
        ----
        1100    - 12, right most bit is cleared
    &   1011    - 11
        ----
        1000    - 8, right most bit is cleared
    &   0111    - 7
        ----
        0000    - 0, right most bit is cleared


    - Approaches to find out 1s (set bits) in a number

        Approach 1: (Best approach)

            keep doing n = n & (n-1) till you get n==0;

            For me, it is the best approach because it avoids using shift operators.

            int x = 11;
            int count = 0;

            while(x != 0) {
                count++;
                x = x & (x-1);
            }

            System.out.println("number of 1s: "+count);

        Approach 2: (Good approach)

            In this approach, you keep the mask=1 always. You don't change the mask. You change the original number.

            int x = 11;
            int count = 0;

            while(x != 0) {
                if((x & 1) != 0) count++;
                x = x >>> 1;
            }

            System.out.println("number of 1s: "+count);

        Approach 3: (worst approach)

            In this approach, you change the mask(one) and do not touch original number.

            This approach has to iterate 32 times always for any int number. So, it is not a good approach.

            int count = 0;
            int one = 1;

            for(int i=0; i < 32; i++) {
                one = one << i;
                if((x & one) != 0) count++;
            }

            System.out.println("number of 1s: "+count);

   - what does (n & (n-1) == 0) indicates?
     See Debugger.java

   - n & ~(n-1)
     it will give the number with right most set bit

      0 1 1 0 = 6
    & 1 0 1 0 = 10
      --------
      0 0 1 0 = number with right most set bit

      See FindTwoMissingNumbersInArray.java, FindTwoNumberThatDoesNotHaveDuplicatesInAnArray.java

   - Practice all below operations (get, set, clear, update bits) properly. They are very important operations for all algorithms.
 */
public class BitManipulationFundamentals {
    public static void main(String[] args) {

        {
            int x = 11;
            int count = 0;

            while (x != 0) {
                count++;
                x = x & (x - 1);
            }

            System.out.println(count);
        }
        {
            int x = 11;
            int one = 1;
            int count = 0;

            for (int i = 0; i < 32; i++) {
                one = one << i;
                if ((x & one) != 0) count++;
            }

            System.out.println(count);
        }

        {
            int x = 11;
            int count = 0;

            while (x != 0) {
                if ((x & 1) != 0) count++;
                x = x >>> 1;
            }
            System.out.println(count);
        }

        System.out.println("\033[1m" + ".............. Right Shifts .............." + "\033[0m");
        System.out.println();
        {
            int input = -15;
            // Integer is represented as 4 bytes (32 bits) in java
            System.out.println("Input: " + Integer.toBinaryString(input));// 1111 1111 1111 1111 1111 1111 1111 0001

            System.out.println("\033[1m" + "ARS more than number of bits on -ve number gives -1" + "\033[0m");
            int result = repeatedArithmeticRightShift(input, 1000);
            System.out.println(result);//-1

            System.out.println("\033[1m" + "LRS on -ve number" + "\033[0m");
            result = repeatedLogicalRightShift(input, 1);
            System.out.println(result);//2147483640
            System.out.println(Integer.toBinaryString(result));// 0111 1111 1111 1111 1111 1111 1111 1000

            System.out.println("\033[1m" + "LRS more than number of bits on ANY number gives 0" + "\033[0m");
            result = repeatedLogicalRightShift(input, 1000);
            System.out.println(result);//0
            System.out.println(Integer.toBinaryString(result));//0

        }

        System.out.println();
        System.out.println("\033[1m" + ".............. Left Shifts .............." + "\033[0m");
        System.out.println();
        {
            int input = -75;
            // Integer is represented as 4 bytes (32 bits)
            System.out.println("Input: " + Integer.toBinaryString(input));// 1111 1111 1111 1111 1111 1111 1011 0101

            System.out.println("\033[1m" + "Left Shift - There is just Left Shift (same as Logical). There is no Arithmetic and Logical in it. " + "\033[0m");
            int result = repeatedLeftShift(input, 1);
            System.out.println(result);//-150
            System.out.println(Integer.toBinaryString(result));//1111 1111 1111 1111 1111 1111 0110 1010

            result = repeatedLeftShift(181, 1);
            System.out.println(result);//362
            System.out.println(Integer.toBinaryString(result));// 1 0110 1010

            System.out.println("\033[1m" + "Left Shift More Than 32 bits " + "\033[0m");
            result = repeatedLeftShift(input, 33);
            System.out.println(result);// 0
            System.out.println(Integer.toBinaryString(result));// 0000 0000 0000 0000 0000 0000 0000 0000

        }

        System.out.println();
        System.out.println("\033[1m" + ".............. Clearing bits .............." + "\033[0m");
        System.out.println();
        {
            int input = -70;
            System.out.println(Integer.toBinaryString(input));// 1111 1111 1111 1111 1111 1111 1011 1010

            System.out.println("\033[1m" + "Clear Most Significant Bit Through I My Way" + "\033[0m");
            int result = clearBits_MSB_Through_I_my_way(input, 3);
            System.out.println(result);// 2
            System.out.println(Integer.toBinaryString(result)); // 0000 0000 0000 0000 0000 0000 0000 0010

            System.out.println("\033[1m" + "Clear Most Significant Bit Through I Book Way" + "\033[0m");
            result = clearBits_MSB_Through_I_book_way(input, 3);
            System.out.println(result); // 2
            System.out.println(Integer.toBinaryString(result)); // 0000 0000 0000 0000 0000 0000 0000 0010

            System.out.println("\033[1m" + "Clear I Through 0 bits My Way" + "\033[0m");
            result = clearBits_I_Through_0(input, 3);
            System.out.println(result);// -80
            System.out.println(Integer.toBinaryString(result)); // 1111 1111 1111 1111 1111 1111 1011 0000

            System.out.println("\033[1m" + "Clear I Through 0 bits Book Way" + "\033[0m");
            result = clearBits_I_Through_0_book_way(input, 3);
            System.out.println(result);// -80
            System.out.println(Integer.toBinaryString(result));// 1111 1111 1111 1111 1111 1111 1011 0000

            System.out.println("\033[1m" + "Clear right most bit 1 " + "\033[0m");
            result = clearBits_RightMost_Bit_1(input);
            System.out.println(result); // 1111 1111 1111 1111 1111 1111 1011 1000

        }

        System.out.println();
        System.out.println("\033[1m" + ".............. Updating bits .............." + "\033[0m");
        System.out.println();
        {
            int input = -70;
            System.out.println("Input: " + Integer.toBinaryString(input));// 1111 1111 1111 1111 1111 1111 1011 1010

            System.out.println("\033[1m" + "Update a Bit My Way" + "\033[0m");
            int result = updateBit_my_way(input, 2, true);
            System.out.println(result);// -66
            System.out.println(Integer.toBinaryString(result));// 1111 1111 1111 1111 1111 1111 1011 1110

            result = updateBit_my_way(input, 3, false);
            System.out.println(result);// -78
            System.out.println(Integer.toBinaryString(result));// 1111 1111 1111 1111 1111 1111 1011 0010

            System.out.println("\033[1m" + "Update a Bit Book Way" + "\033[0m");
            result = updateBit_book_way(input, 2, true);
            System.out.println(result);// -66
            System.out.println(Integer.toBinaryString(result));// 1111 1111 1111 1111 1111 1111 1011 1110

            result = updateBit_book_way(input, 3, false);
            System.out.println(result);// -78
            System.out.println(Integer.toBinaryString(result));// 1111 1111 1111 1111 1111 1111 1011 0010
        }
    }

    // ........ Bits Shifting operations ........................
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

    // ........ Clearing bits operations ........................

    private static int clearBits_RightMost_Bit_1(int input) {
        return input & (input - 1);
    }

    private static int clearBits_MSB_Through_I_my_way(int num, int i) {
        int mask = -1 >>> (32 - (i - 1)); // -1 is same as ~0. so, you can use ~0 also. As in java, integer is 32 bits, i considered 32 number for logical right shift.
        //System.out.println("mask:"+mask);
        return num & mask;
    }

    private static int clearBits_MSB_Through_I_book_way(int num, int i) {
        int mask = (1 << i) - 1; // -1 is same as ~0. so, you can use ~0 also. As in java, integer is 32 bits, i considered 32 number for logical right shift.
        //System.out.println("mask:"+mask);
        return num & mask;
    }


    private static int clearBits_I_Through_0(int num, int i) {
        int mask = -1 << (i + 1); // -1 is same as ~0. so, you can use ~0 also.
        //System.out.println("mask:"+mask);
        return num & mask;
    }

    private static int clearBits_I_Through_0_book_way(int num, int i) {
        int mask = ~(-1 >>> (31 - i));
        //System.out.println("mask:"+mask);
        return num & mask;
    }

    // ........ Updating bits operations ........................
    private static int updateBit_my_way(int num, int i, boolean bitIs1) {
        int value = bitIs1 ? 1 : 0;

        if (value == 1) {
            int mask = 1 << i;
            return num | mask;
        }
        int mask = ~(1 << i);
        return num & mask;
    }

    private static int updateBit_book_way(int input, int i, boolean bitIs1) {
        int value = bitIs1 ? 1 : 0;
        int mask = ~(1 << i);
        return (input & mask) | (value << i);
    }
}
