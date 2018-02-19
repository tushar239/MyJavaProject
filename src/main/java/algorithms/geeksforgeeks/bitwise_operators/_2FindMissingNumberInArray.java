package algorithms.geeksforgeeks.bitwise_operators;

/*
Find missing number in an array(using summation and XOR operation)

e.g.
[3,2,1,5]
missing number is 4

Assumption:
there is no duplicate element in an array.

Solution:
1) sum of all numbers in an array
   sum all 1 to n numbers --- method is n(n+1)/2.
   and then subtract one from another. result will give you a missing number.

   1+2+3+4+5 = 5(5+1)/2 = 15
   3+2+1+5 = 11

   15-11=4

2) xor all numbers in an array
   xor all 1 to n numbers
   and then xor above two results. result will give you a missing number.

   3^2^1^5  ^  1^2^3^4^5   =   4
 */
public class _2FindMissingNumberInArray {
}
