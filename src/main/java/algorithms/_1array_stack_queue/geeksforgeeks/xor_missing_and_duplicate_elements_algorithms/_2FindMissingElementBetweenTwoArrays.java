package algorithms._1array_stack_queue.geeksforgeeks.xor_missing_and_duplicate_elements_algorithms;

/*
There are two arrays. Second one has all elements except one. You need to find that one missing element.

e.g.
A=[1,2,3,4,5,6]
B=[1,3,4,5,6]
Result=2

https://www.youtube.com/watch?v=zgh9WIgzOTg

Assumption:
There are no duplicate elements in the same array.

Solution:
Remember the XOR rule:
XORing an element with itself results in 0. and XORing 0 with a number results in that number.

XOR all elements of A and B. Result will be a missing number.

1^2^3^4^5^6  ^  1^3^4^5^6 = 2
*/
public class _2FindMissingElementBetweenTwoArrays {
}
