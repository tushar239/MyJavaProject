package algorithms._0Fundamentals.Recursion_and_Dynamic_Programming;

/*
Converting normal recursive method to tail-recursive may not be simple all the time.
For Algorithms related interviews, it may not be so important learning tail-recursion.

Tail recursion doesn't do any computation with previous element.
It stores the result in a variable and pass it as a parameter during recursive call.
When last method call is kept in the stack, it already has final result in it all method calls return with that return. It doesn't do anymore computation.
This approach is very useful in functional programming where Recursion is inherent and java has problem with size of stack. In Functional programming, if you use tail-recursion, then using some trick, you can get away from using so many stack slots. you can using only one stack slot for entire recursion

For number as an input, you need to memorize how
    Fibonacci.java and
    TripleSteps.java
are coded in Tail-Recursive way.

For tree as an input, you need to memorize how
    CreateMinimalBST.java
    PathsWithSum.java
are coded in Tail-Recursive Way.

 */
public class TailRecursionFundamentals {
}
