package algorithms._5recursion_and_dynamic_programming;

/*
Find a length of Longest Common Subsequence between two strings:

Str1 = a b c d e f g
       -   - - -   -
Str2 = p q a r c d e g f
           -   - - -   -

O/P: a c d e f


Watch 'Longest Common Subsequence Dynamic Programming.mp4'

This problem can be solved using Bottom-Up Approach Dynamic Approach.

        a   b   c   d   e   f   g

    0   0   0   0   0   0   0   0
        |
        v
p   0-> max

q   0

a   0

r   0       \
             \
              v
c   0           +1

d   0

e   0

g   0

f   0


If letters of row and col do not match, then you need to take max of prev row or prev col's value
otherwise, you need to take a value from diagonal cell and add 1 to it.
To find the longest common subsequence string, you need to keep track of cells for which you derive value from diagonal cell.
*/
public class LongestCommonSubsequence {
}
