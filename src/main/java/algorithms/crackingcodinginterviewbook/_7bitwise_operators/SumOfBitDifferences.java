package algorithms.crackingcodinginterviewbook._7bitwise_operators;

/*
a=[1,3,6];
find sum of bit differences in all pairs that can be formed from array elements.

https://www.youtube.com/watch?v=cfizo_K7e0o&index=9&list=PLqM7alHXFySF8B9KqOy6yz4vggu4tiNMP

This is simple

int sum = 0;
for(int i=0; i<array.length; i++)
    for(int j=0; j<array.length; j++)
        sum = sum + findDifference(array[i], array[j])

int findDifference(int num1, int num2) {
    xor of two numbers gives a number having 1s at the place where both numbers differ in bits.

        1101 = a
    ^   0110 = b
        ----
        1011

    Now count number of 1s by a technique of anding the number with (number-1) till number=0.
    e.g. Conversion.java
}

 */
public class SumOfBitDifferences {

}
