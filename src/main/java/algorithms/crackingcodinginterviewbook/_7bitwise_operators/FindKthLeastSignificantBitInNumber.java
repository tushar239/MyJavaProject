package algorithms.crackingcodinginterviewbook._7bitwise_operators;

/*
https://www.youtube.com/watch?v=tB7Qp4copus&index=15&list=PLqM7alHXFySF8B9KqOy6yz4vggu4tiNMP

input = 1100 0100
find a bit at index k=2
result=1

Solution
int result = input & (1 << (k-1))
if(result == 0) then kth bit is 0 otherwise it is 1.

*/
public class FindKthLeastSignificantBitInNumber {
}
