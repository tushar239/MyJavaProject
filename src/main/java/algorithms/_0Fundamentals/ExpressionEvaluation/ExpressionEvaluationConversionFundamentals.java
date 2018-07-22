package algorithms._0Fundamentals.ExpressionEvaluation;

/**

 Remember:
     Precedence of operators
        braces                      ( ) { } [ ] etc.
        exponent                    ^
        multiplication or division  * /
        addition or subtraction     + -

 Remember:
    - Infix form may/may not brackets (2+3)+5*6
    - Prefix (++23*56) and Postfix (23+56*+)forms do not have brackets.

    - Evaluation algorithms (except Evaluating Expression Tree in the form of Binary Tree) requires TWO stacks.
        - Value stack
        - Operator (and brackets) stack

    - Conversion algorithms need only one stack

        Infix to Postfix/Prefix requires
        - Operator (and brackets) stack
        - StringBuffer to build an output

        Prefix to Postfix and vice-a-versa requires
        - Operand stack

 Evaluation Algorithms:

     1) Evaluation of Infix Expression:
     https://www.geeksforgeeks.org/expression-evaluation/

     10*2+6+(1+3) = 30
     10*(2+6)+(1+3) = 84

     Use TWO stacks
     - Value stack
     - Operator (and brackets) stack

        for(int i=0; i<chars.size(); i++) {

            char ch = chars[i];

            if(ch is a number) {
                push ch to  ValueStack
            }
            else if(ch is an operator) {
                if(OperatorStack's top element is an Operator) {
                    if(ch's precedence is higher than the precedence of OperatorStack's top element) {
                        push ch to OperatorStack
                    }
                    else {
                        while(OperatorStack's top element's precedence is greater/equal to ch's precedence) {
                            operator = OperatorStack.pop()
                            operand1 = ValueStack.pop() --- if ValueStack is empty, expression is bad. So, throw an exception
                            operand2 = ValueStack.pop() --- if ValueStack is empty, expression is bad. So, throw an exception

                            push (operand1 operator operand2 evaluation) to ValueStack.
                        }
                    }
                } else {
                    push ch to OperatorStack
                }
            } else if(ch is an opening bracket) {

                push ch to OperatorStack

            } else if(ch is an closing bracket) {

                operator = pop top element of OperatorStack (an operator) from OperatorStack.
                opening bracket = pop top element of OperatorStack (an opening bracket) from OperatorStack.

                operand1 = ValueStack.pop() --- if ValueStack is empty, expression is bad. So, throw an exception
                operand2 = ValueStack.pop() --- if ValueStack is empty, expression is bad. So, throw an exception

                push (operand1 operator operand2 evaluation) to ValueStack.

            }
        }

        There will be only one element left in a stack that will form a prefix form
        result = stack.pop()

 2) Evaluation of Postfix Expressions:
        https://www.geeksforgeeks.org/stack-set-4-evaluation-postfix-expression/

     for(int i=0; i<chars.size(); i++) {

         char ch = chars[i];

        if(ch is a number) {

            push ch to  ValueStack

        }
        else if(ch is an operator) {

            operand1 = ValueStack.pop() --- if ValueStack is empty, expression is bad. So, throw an exception
            operand2 = ValueStack.pop() --- if ValueStack is empty, expression is bad. So, throw an exception

            push (operand1 operator operand2 evaluation) to ValueStack.

        }

        There will be only one element left in a stack that will form a prefix form
        result = stack.pop()

     3) Evaluation of Prefix Expressions:
        https://www.geeksforgeeks.org/evaluation-prefix-expressions/

        same as 2). Just evaluate the expression from right to left.

     4) Evaluate BST (expression tree)
     EvaluateExpressionTree.java

 Infix, Prefix, Postfix:

    Watch Infix_Postfix_Prefix.mp4

    Infix : a + (b+c)
    Prefix : +a+bc
    Postfix : abc++

    Remember:
        Prefix and Postfix do not require brackets. That's why they are easier to process by computer.
        Out of Prefix and Postfix, Postfix is easier.

    Conversion Algorithms:

        1) Infix to Postfix conversion:

            infix   = (a + (b + c) * (d + e) - f)
            postfix = abc/de+*+f-

            - Use ONE stack
            and
            - ONE Output StringBuffer(sb)

            - Use stack for brackets and operators

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
               Use stack for Operators.

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
public class ExpressionEvaluationConversionFundamentals {
}
