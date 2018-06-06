package algorithms._1array_stack_queue.geeksforgeeks.pointers_sorting_stack_queue_string.easy;

/*
Evaluate an array expression with numbers, + and –

https://www.geeksforgeeks.org/evaluate-an-array-expression-with-numbers-and/

Very Simple algorithm

Input : arr[] = {"3", "+", "4", "-", "7", "+", "13"}
The value of expression 3+4-7+13 is 13.

Input : arr[] = {"3", "+", "4", "-", "7", "+"}
The value of expression 3+4-7 is 0.
 */
public class _2EvaluateArrayExpressionWithNumbersPlusAndMinus {

    public static void main(String[] args) {
        {
            String A[] = {"3", "+", "4", "-", "7", "+", "13"};
            int total = evaluate(A);
            System.out.println(total);//13

        }
        {
            String A[] = {"3", "+", "4", "-", "7", "+"};
            int total = evaluate(A);
            System.out.println(total);//0

        }
    }

    private static int evaluate(String[] A) {

        if (A == null || A.length < 3) return 0;

        int total = 0;

        int num1 = Integer.parseInt(A[0]);
        int num2 = Integer.parseInt(A[2]);

        if (A[1].equals("+")) {
            total = num1 + num2;
        } else if (A[1].equals("-")) {
            total = num1 - num2;
        }

        for (int i = 3; i < A.length - 1; i = i + 2) {
            int num = Integer.parseInt(A[i + 1]);
            if (A[i].equals("+")) {
                total += num;
            } else if (A[i].equals("-")) {
                total -= num;
            }

        }
        return total;
    }

}
