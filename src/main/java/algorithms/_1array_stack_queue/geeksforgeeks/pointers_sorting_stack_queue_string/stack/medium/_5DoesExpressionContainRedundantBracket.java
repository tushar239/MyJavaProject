package algorithms._1array_stack_queue.geeksforgeeks.pointers_sorting_stack_queue_string.stack.medium;

import java.util.Stack;

/*
    Expression contains redundant bracket or not

    https://www.geeksforgeeks.org/expression-contains-redundant-bracket-not/

    Given a string of balanced expression, find if it contains a redundant parenthesis or not. A set of parenthesis are redundant if same sub-expression is surrounded by unnecessary or multiple brackets. Print ‘Yes’ if redundant else ‘No’.

    Note: Expression may contain ‘+‘, ‘*‘, ‘–‘ and ‘/‘ operators. Given expression is valid and there are no white spaces present.


    Solution:
    ((a+b)) has redundant brackets
    (b) is also has a redundant brackets. ----- This is a trick

    You don't push letters to stack. Just push (, +, -, *, / only to stack.
*/
public class _5DoesExpressionContainRedundantBracket {

    public static void main(String[] args) {
        {
            String s = "((a+b))";// it has redundant bracket

            boolean result = doesExpressionContainRedundantBracket(s);
            System.out.println(result);
        }
        {
            String s = "(a+((b)+c))"; // (b) has redundant bracket

            boolean result = doesExpressionContainRedundantBracket(s);
            System.out.println(result);
        }
        {
            String s = "(a+(b+c))"; // it doesn't have redundant bracket

            boolean result = doesExpressionContainRedundantBracket(s);
            System.out.println(result);
        }

    }
    private static boolean doesExpressionContainRedundantBracket(String s) {

        char[] chars = s.toCharArray();

        Stack<Character> stack = new Stack<>();

        for (Character c : chars) {
            if(c == ')') {
                if(!stack.isEmpty() && stack.peek() == '(') {
                    return true;
                }
                while(!stack.isEmpty()) {
                    Character peekedElement = stack.peek();
                    if(peekedElement == '(') {
                        stack.pop();
                        break;
                    } else {
                        stack.pop();
                    }
                }

            } else if(c == '(' || c == '+' || c == '-' || c == '*' || c == '/'){
                stack.push(c);
            } else {
                // don't push letters or numbers to stack.
            }
        }
        return false;
    }
}
