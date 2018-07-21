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

                Use ONE stack
                and
                ONE Output StringBuffer(sb)

                for(int i = 0; i < chars.size(); i++) { // parse an expression string

                    char ch = chars[i];

                    if(ch is operand) {
                        put it in sb
                    }
                    else if(ch is a opening bracket) {
                         put it in sb
                    }
                    else if(ch is a closing bracket) {
                        element = stack.pop();
                        while(element != opening bracket){
                            put element in sb
                        }
                    }
                    else if(ch is an operator) { // you have to check the precedence

                        if(ch has higher precedence than stack's top element) {

                            push ch to stack

                        } else {
                            while(stack.peek() is an operator and has higher precedence than a ch) {
                                push stack.pop() to sb
                            }
                            push ch to stack
                        }
                    }
                }

            2) Infix to Prefix conversion:

                reverse an expression (while reversing, replace ( with ) and vice-a-versa.
                apply Infix to Postfix
                reverse and expression (while reversing, replace ( with ) and vice-a-versa.

            3) Postfix to Prefix conversion:

                   https://www.geeksforgeeks.org/postfix-prefix-conversion/

                   Use only ONE stack.

                   Postfix  = AB+CD-*
                   Prefix   = *+AB-CD

                    for(int i = 0; i < chars.size(); i++) {

                        char ch = chars[i];

                        if(ch is an operand) {
                            push ch to stack
                        }
                        else if(ch is an operator) {
                            pop two elements (operands) from the stack
                            and
                            push that form to stack
                        }
                    }

                There will be only one element left in a stack that will form a prefix form
                prefix form = stack.pop()

            4) Prefix to Postfix conversion:

                 https://www.geeksforgeeks.org/prefix-postfix-conversion/

                 Prefix   = *+AB-CD
                 Postfix  = AB+CD-*

                 This is very similar to 3). The only difference is that you need to parse a string in reverse order.

                 for(int i = chars.size()-1; i >= 0; i--) {
                    ...

                    else if(ch is an operator) {
                        pop two elements (operands) from the stack and form "operand1 operand2 operator"
                        and
                        push that form to stack
                    }
                 }

            5) Postfix to Infix conversion:

                 https://www.geeksforgeeks.org/postfix-to-infix/

                 Postfix  = AB+CD-*
                 Infix    = ((A+B)*(C-D))

                 Use only ONE stack.

                 for(int i = 0; i < chars.size(); i++) {

                    char ch = chars[i];

                    if(ch is an operand) {
                        push ch to stack
                    }
                    else if(ch is an operator) {
                        pop two elements(operands) from the stack and form "(operand1 operator operand2)"
                        and
                        push that form to stack
                    }
                 }

                 There will be only one element left in a stack that will form a prefix form
                 Infix form = stack.pop()

            6) Prefix to Infix conversion:

                https://www.geeksforgeeks.org/prefix-infix-conversion/

                Prefix   = *+AB-CD
                Infix    = (A+B)*(C-D)

                This is very similar to 5). The only difference is that you need to parse a string in reverse order.

*/
public class expressionEvaluationFundamentals {
}
