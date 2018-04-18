package algorithms._3stack;

/*

Length of the longest valid substring

https://www.geeksforgeeks.org/length-of-the-longest-valid-substring/

https://www.youtube.com/watch?v=AIhyd8lMpIo


Given a string consisting of opening and closing parenthesis, find length of the longest valid parenthesis substring.

Examples:

Input : ((()
Output : 2
Explanation : ()

Input: )()())
Output : 4
Explanation: ()()

Input:  ()(()))))
Output: 6
Explanation:  ()(())


Solution:
    When opening bracket comes, push its index to stack and when closing bracket comes, calculate the difference between closing bracket index and top element of stack. This will give you a length of substring in between those brackets.
    Keep doing this till you iterate entire string.

*/
public class _0LengthOfLongestValidSubstring {
}
