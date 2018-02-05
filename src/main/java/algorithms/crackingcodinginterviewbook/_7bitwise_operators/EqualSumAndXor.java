package algorithms.crackingcodinginterviewbook._7bitwise_operators;

/*
Equal Sum and XOR:
Find all integers that gives same result when you sum them or xor them with a given number.

https://www.youtube.com/watch?v=zhu605v9KOI&list=PLqM7alHXFySF8B9KqOy6yz4vggu4tiNMP

e.g. 12^0 = 12+0
     12^1 = 12+1
     12^2 = 12+2
     12^3 = 12+3

One approach is to iterate from 0 to 12. This will reserve O(n).

Better approach:
we know that  a+b = a^b + a&b
For a+b = a^b, a&b has to be 0

when a&b be 0?
when
- b is all 0s
    or
- b has 0s where a has 1s


a = 12 = 1100

possible values of b:
0000
0011, 0010, 0001

total possibilities of b = 2^count of 0s in a

how to know count of 0s in a?

approach 1:

    temp=a
    numberOf1s=0

    while(temp>0)
        temp = temp & (temp-1) --- remember n & (n-1) clears the rightmost bit (flips rightmost 1 to 0)
        numberOf1s++

    numberOf0s =  32-numberOf1s

approach 2:

    temp=a
    numberOf0s=0
    one=1

    loop 32 times ------ not efficient
        if(temp & one == 0) numberOf0s++
        one <<= 1

approach 3:

    temp=a
    numberOf0s=0

    while(temp != 0) --- efficient
        if(temp & 1 == 0) numberOf0s++
        temp >>>= 1

 */
public class EqualSumAndXor {
}
