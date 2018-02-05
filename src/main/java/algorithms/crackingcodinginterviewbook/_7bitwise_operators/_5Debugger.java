package algorithms.crackingcodinginterviewbook._7bitwise_operators;

/*
p.g. 286 of Cracking Coding Interview book.

Debugger:
Explain what the following code does:
((n & (n-1)) == 0)

1000 = n = 8
0111 = n-1 = 7

n and n-1 will never have 1s at the same locations.

n & (n-1) will be 0 only in one situation:
when n==0

So, ((n & (n-1)) == 0) condition checks wither n==0 or n is some value of 2's power.

 */
public class _5Debugger {
    public static void main(String[] args) {
        int n = 0;
        System.out.println(n & (n - 1));//0
    }
}
