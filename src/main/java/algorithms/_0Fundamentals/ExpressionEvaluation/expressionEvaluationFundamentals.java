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
                braces                      ( ) { } [ ] etc.
                exponent                    ^
                multiplication or division  * /
                addition or subtraction     + -


        Conversion Algorithms:

            1) Infix to Postfix conversion:

                infix   = (a + (b + c) * (d + e) - f)
                postfix = abc/de+*+f-

                Use one stack and one output string buffer(sb)
                Parse the expression string

                if(char is operand) {
                    put it in sb
                }
                else if(char is a opening bracket) {
                     put it in sb
                }
                else if(char is a closing bracket) {
                    element = stack.pop();
                    while(element != opening bracket){
                        put element in sb
                    }
                }
                else if(char is an operator) { // you have to check the precedence

                    if(char has higher precedence than stack's top element) {

                        push char to stack

                    } else {
                        while(stack.peek() is an operator and has higher precedence than a char) {
                            push stack.pop() to sb
                        }
                        push char to stack
                    }
                }

            2) Infix to Prefix conversion:
                reverse an expression (while reversing, replace ( with ) and vice-a-versa.
                apply Infix to Postfix
                reverse and expression (while reversing, replace ( with ) and vice-a-versa.

            3) Prefix to Postfix conversion:


            4) Postfix to Prefix conversion:


            5) Prefix to Infix conversion:


            6) Postfix to Infix conversion:

*/
public class expressionEvaluationFundamentals {
}
