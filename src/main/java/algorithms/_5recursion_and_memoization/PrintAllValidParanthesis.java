package algorithms._5recursion_and_memoization;

/**
 * @author Tushar Chokshi @ 1/31/16.
 */
// p.g. 360 of Cracking Coding Interview Book
// Very simple - just use reduce problem by 1 technique for recursion
/*
    let's say requirement is to print all possible combinations of 3 paranthesis.
    valid combinations of 2 parenthesis - ()(), (())
    now iterate through these combinations:
        whereever you find '(' in entire string, add () after that. e.g. (()) (), () (())
        and at the end append () to entire string e.g. ()()()
        So, you made 3 combinations from ()() -  (()) (), () (()), ()()()
*/
public class PrintAllValidParanthesis {
}
