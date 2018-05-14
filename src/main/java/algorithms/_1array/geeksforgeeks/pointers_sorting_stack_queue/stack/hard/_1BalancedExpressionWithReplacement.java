package algorithms._1array.geeksforgeeks.pointers_sorting_stack_queue.stack.hard;

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


                                    m("XX") ---- for loop of n element
            stack=(, m("(X")         stack={, m(""{X)       stack=[, m("[X")       stack=empty, m(")X")         stack=empty, m("}X")         stack=empty, m("]X")
            .....


      2^n nodes. each node runs a for loop of n elements. So, Time-Complexity=O(n * 2^n)


*/
public class _1BalancedExpressionWithReplacement {

    public static void main(String[] args) {
        //String str = "XX";//true
        //String str = "(X";//true
        //String str = "X)";//true
        //String str = ")X";//true
//        String str = "(X)";//false
//        String str = "(X)X";//true
//        String str = "{(X[X])}";//true
        String str = "[{X}(X)]";//false
        System.out.println(isBalanced(str.toCharArray(), 0, str.toCharArray().length - 1, new Stack<>()));
    }

    private static boolean isBalanced(char[] chars, int start, int end, Stack<Character> stack) {

        for (int i = start; i <= end; i++) {
            char c = chars[i];

            if (c == '(' || c == '{' || c == '[') {

                stack.push(c);

            } else if (c == ')' || c == '}' || c == ']') {

                if (stack.isEmpty()) return false;

                if (!match(stack.pop(), c)) {
                    return false;
                }

            } else { //c == X

                chars[i] = '(';

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

    private static boolean match(char c1, char c2) {
        if ((c2 == ')' && c1 == '(') || (c2 == '}' && c1 == '{') || (c2 == ']' && c1 == '[')) return true;
        return false;
    }
}
