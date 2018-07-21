package algorithms._0Fundamentals.ExpressionEvaluation;

/**

    Evaluate BST (expression tree)
        EvaluateExpressionTree.java

    Infix, Prefix, Postfix:
        Watch Infix_Postfix_Prefix.mp4

        Infix : a + (b+c)
        Prefix : +a+bc
        Postfix : abc++

        Prefix and PostFix do not require brackets

        Remember:
        Postfix is easy to evaluate for a computer.

        Remember:
        Precedence of operators
            braces                      ( ) { } [ ]etc
            exponent                    ^
            multiplication or division  * /
            addition or subtraction     + -


        Conversion Algorithms:

            1) Infix to Postfix conversion:

                https://www.youtube.com/watch?v=vXPL6UavUeA

                Use one stack and one output string buffer(sb)
                Parse the expression string

                if(char is a operand) {
                    put it in sb
                }
                else if(char is an operator)

                    if(char has higher precedence than stack's top element) {

                        push char to stack

                    } else {
                        while(stack.peek() has higher precedence than char) {
                            push stack.pop() to sb
                        }
                        push char to stack
                    }
                }


            2) Infix to Prefix conversion:
                reverse an expression
                apply Infix to Postfix
                reverse and expression

            3) Prefix to Postfix conversion:


            4) Postfix to Prefix conversion:


            5) Prefix to Infix conversion:


            6) Postfix to Infix conversion:

*/
public class expressionEvaluationFundamentals {
}
