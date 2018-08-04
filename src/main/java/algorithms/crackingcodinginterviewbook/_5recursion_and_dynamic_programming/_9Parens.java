package algorithms.crackingcodinginterviewbook._5recursion_and_dynamic_programming;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
pg 360 of Cracking Coding Interview book

Parens:
Implement an algorithm to print all valid combinations of n pairs of parentheses.
 */
public class _9Parens {
    public static void main(String[] args) {
        Set<String> strings = parens_my_way(3, "(", ")");
        System.out.println(strings);

        parens_book_way(3);
    }

    /*
    My way:

    This is the simplest way, but not the most efficient way because it creates duplicates and that's why
    I have to use Set instead of List.

    if count = 2

    ( + count 1 o/p + )     = (())
    () + count 1 o/p        = ()()
    count 1 o/p + ()        = ()() --- duplicate
    */
    private static Set<String> parens_my_way(int count, String leftP, String rightP) {
        Set<String> set = new HashSet<>();

        if (count == 0) {
            return set;
        }
        if (count == 1) {
            set.add(leftP + rightP);
            return set;
        }

        Set<String> remainingCountSet = parens_my_way(count - 1, leftP, rightP);

        for (String remaining : remainingCountSet) {
            set.add(leftP + remaining + rightP);
            set.add(leftP + rightP + remaining);
            set.add(remaining + leftP + rightP);
        }
        return set;
    }

    /*
    book way:
    avoids adding duplicates.

    If you draw recursion tree, it takes ((())) first and stores in list.
    Then it starts from (( and adds ()())) to it. It just overwrites the str[] created from first occurrence from certain place based on the value of count in recursive tree.

    NOTE:
    It's very root_to_leaf_problems_hard for me to think in this way. I understood the solution, but I don't think that I can solve it this way by myself.
     */
    private static List<String> parens_book_way(int count) {
        char[] str = new char[count * 2];
        List<String> list = new ArrayList<>();
        addParen(list, count, count, str, 0);
        return list;
    }

    private static void addParen(List<String> list, int leftRem, int rightRem, char[] str, int count) {
        if (leftRem < 0 || rightRem < leftRem) return;
        if (leftRem == 0 && rightRem == 0) {
            String s = String.copyValueOf(str);
            list.add(s);
        } else {
            if(leftRem > 0) {
                str[count] = '(';
                addParen(list, leftRem - 1, rightRem, str, count + 1);
            }

            if(rightRem > leftRem) {
                str[count] = ')';
                addParen(list, leftRem, rightRem - 1, str, count + 1);
            }
        }
    }
}
