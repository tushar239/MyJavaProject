package algorithms._1array_stack_queue.geeksforgeeks.pointers_sorting_stack_queue_string.stack.hard;

import java.util.Stack;

/*
    Balanced expression with replacement

    https://www.geeksforgeeks.org/balanced-expression-replacement/

    Given a string that contains only the following => ‘{‘, ‘}’, ‘(‘, ‘)’, ‘[’, ‘]’. At some places there is ‘X’ in place of any bracket. Determine whether by replacing all ‘X’s with appropriate bracket, is it possible to make a valid bracket sequence.

    Examples:

        Input : S = "{(X[X])}"
        Output : Balanced
        The balanced expression after
        replacing X with suitable bracket is:
        {([[]])}.

        Input : [{X}(X)]
        Output : Not balanced
        No substitution of X with any bracket
        results in a balanced expression.


    Solution:

    - If there is opening bracket, push it to stack.
    - If there is closing bracket, see whether top element in stack is a related opening bracket.
      If no, then expression is not balanced.
    - If there is X, X can be either (,{,[ or ),},].
      You need to evaluate all possibilities, whichever possibility returns true, keep that.
      e.g. So, you can change X with ( and recursively call isBalanced method
                                     { ....
                                     [ ....
                                     ) ....
                                     } ....
                                     ] ....
      There should be OR between these recursions.

      Important:
                Before recursing isBalanced, in certain cases, we need to push element to stack.
                so, we need to make sure that we pop from the stack after recursion is over to bring back the stack in initial condition, so that it can be used by another recursion.


                                    m(arr,start,end)
                              m(arr,start+1,end)    m(arr,start+1,end)
                            ....


    This problem is similar to Fibonacci series. It will have 2^n nodes.
    There is a for loop/recursion inside exit condition(s), so each node does O(n) time consuming task.
    So, Time-Complexity=O(n * 2^n).

    Read README_Memorize_These_Points.docx to understand how time complexity is calculated.

    IMPORTANT:
    I could not this algorithm work, but this algorithm is a best example of O(n * 2^n) time complexity.

    Even though, it takes exponential time, it cannot be optimized using Dynamic Programming.
    Here, there are two varying variables (index and stack) which are varying, but it is not possible to add a stack in key because to use a stack in a key, you need to iterate through entire stack.
    So, this problem cannot be optimized using Dynamic Programming. (This is my guess)
*/

// NOT WORKING......
public class _1BalancedExpressionWithReplacement {

    public static void main(String[] args) {
//        String str = "XX";//true
//        String str = "(X";//true
//        String str = "X)";//true
//        String str = ")X";//false
//        String str = "(X)";//false
//        String str = "(X)X";//true
//        String str = "XXX";//false
        String str = "{(X[X])}";//true
//        String str = "{(X}";
//        String str = "[{X}(X)]";//false
        System.out.println(isBalanced_another(str.toCharArray(), 0, str.toCharArray().length - 1, new Stack<>()));
//        System.out.println(cnt);
    }

    private static boolean isBalanced_another(char[] chars, int start, int end, Stack<Character> stack) {

        if(start > end) {
            return stack.isEmpty();
        }
        /*if (start == end) {
            if (!stack.isEmpty()) {
                return match(stack.pop(), chars[start]);
            }
            return false;
        }*/

        char c = chars[start];


        if (c == '(' || c == '{' || c == '[') {
            stack.push(c);

            return isBalanced_another(chars, start + 1, end, stack);

        }

        if (c == ')' || c == '}' || c == ']') {

            if (stack.isEmpty()) return false;

            if (!isMatching_a(stack.pop(), c)) {
                return false;
            }

            return isBalanced_another(chars, start + 1, end, stack);
        }

        // if c == 'X'

        // Assuming 'X' can be replaced with (, { or [
        stack.push(c);

        boolean resultFromRemainingElements = isBalanced_another(chars, start + 1, end, stack);

        if(resultFromRemainingElements) {
            return true;
        }

        if(stack.isEmpty()) { // important
            return false;
        }

        stack.pop(); // removing pushed X

        // Assuming 'X' can be replaced with ), } or ]
        if(stack.isEmpty()) return false;

        if(isMatching_b(stack.pop(), c)) {
            resultFromRemainingElements = isBalanced_another(chars, start + 1, end, stack);
            return resultFromRemainingElements;
        }
        return false;

    }


    static int cnt = 0;

    private static boolean isBalanced(char[] chars, int start, int end, Stack<Character> stack) {

        for (int i = start; i <= end; i++) {
            cnt++;
            char c = chars[i];

            if (c == '(' || c == '{' || c == '[') {

                stack.push(c);

            } else if (c == ')' || c == '}' || c == ']') {

                if (stack.isEmpty()) return false;

                if (!match(stack.pop(), c)) {
                    return false;
                }

            } else { //c == X

                chars[i] = '('; // if you don't want to change input array, you can do stack.push('(') and then boolean possibilityOne = isBalanced(chars, i+1, end, stack);

                boolean possibilityOne = isBalanced(chars, i, end, stack);

                if (!possibilityOne) {

                    if (!stack.isEmpty()) stack.pop();

                    chars[i] = '{';

                    boolean possibilityTwo = isBalanced(chars, i, end, stack);

                    if (!possibilityTwo) {

                        if (!stack.isEmpty()) stack.pop();

                        chars[i] = '[';

                        boolean possibilityThree = isBalanced(chars, i, end, stack);

                        if (!possibilityThree) {

                            if (!stack.isEmpty()) stack.pop();

                            chars[i] = ')';

                            boolean possibilityFour = isBalanced(chars, i, end, stack);

                            if (!possibilityFour) {

                                chars[i] = '}';

                                boolean possibilityFive = isBalanced(chars, i, end, stack);

                                if (!possibilityFive) {

                                    chars[i] = ']';

                                    boolean possibilitySix = isBalanced(chars, i, end, stack);

                                    return possibilitySix;
                                } else {
                                    return true;
                                }

                            } else {
                                return true;
                            }
                        } else {
                            return true;
                        }
                    } else {
                        return true;
                    }
                } else {
                    return true;
                }

            }
        }

        return stack.isEmpty();
    }

    private static boolean isMatching_a(char a, char b) {
        if (
                (a == '{' && b == '}') ||
                        (a == '[' && b == ']') ||
                        (a == '(' && b == ')') ||
                        a == 'X'
                )
            return true;
        return false;
    }

    private static boolean isMatching_b(char a, char b) {
        if (
                (a == '{' && b == '}') ||
                        (a == '[' && b == ']') ||
                        (a == '(' && b == ')') ||
                        b == 'X'
                )
            return true;
        return false;
    }

    private static boolean match(char c1, char c2) {


        if (((c1 == '(' || c1 == 'X') && (c2 == ')' || c2 == 'X')) ||
                ((c1 == '{' || c1 == 'X') && (c2 == '}' || c2 == 'X')) ||
                ((c1 == '[' || c1 == 'X') && (c2 == ']' || c2 == 'X')))
            return true;
        return false;
    }
}
