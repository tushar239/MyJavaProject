package algorithms._1array_stack_queue.geeksforgeeks.xor_missing_and_duplicate_elements_algorithms;

/*
Find Unique pair in an array with pairs of numbers
https://www.geeksforgeeks.org/find-unique-pair-array-pairs-numbers/

Given an array where every element appears twice except a pair (two elements). Find the elements of this unique pair.

Examples:

Input  : 6, 1, 3, 5, 1, 3, 7, 6
Output : 5 7
All elements appear twice except 5 and 7

Input  : 1 3 4 1
Output : 3 4


Solution:
Solution is same as FindTwoMissingNumbersInArray.java
Uses a magic of XOR.

XOR all numbers. you will get result as 5^7 = 2
Take set bit of 2.
Make two sets from array - one that has a set bit and another that doesn't.
XOR these two sets internally. Both these sets will give one-one numbers that is not duplicate.

*/
public class _6FindTwoNumberThatDoesNotHaveDuplicatesInAnArray {
}
