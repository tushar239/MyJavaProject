package algorithms.crackingcodinginterviewbook._7bitwise_operators;

/*
Find Nth Magic Number:

What is Nth magic number?
A magic number is defined as a number which can be expressed as a power of 5 or sum of unique powers of 5.
First few magic numbers are 5, 25, 30(5 + 25), 125, 130(125 + 5), ….


https://www.youtube.com/watch?v=Rmjqr6U4k50&index=4&list=PLqM7alHXFySF8B9KqOy6yz4vggu4tiNMP
https://www.geeksforgeeks.org/find-nth-magic-number/

001 = 5^1       = 5
010 = 5^2       = 25
011 = 5^1 + 5^2 = 5+25 = 30
100 = 5^3       = 125
101 = 5^1 + 5^3 = 5+125 = 130
110 = 5^2 + 5^3 = 25+125 = 150
111 = 5^1 + 5^2 + 5^3 = 5+25+125 = 155

*/
public class FindNthMagicNumber {

    public static void main(String[] args) {
        // find 7th magic number
        int magicNumber = find(7);
        System.out.println(magicNumber);// 155
    }

    private static int find(int n) {
        int i = 1;

        int sum = 0;

        while (n != 0) {
            if ((n & 1) != 0) {
                sum += Math.pow(5, i);
            }
            i++;
            n = n >>> 1;
        }

        return sum;
    }
}
