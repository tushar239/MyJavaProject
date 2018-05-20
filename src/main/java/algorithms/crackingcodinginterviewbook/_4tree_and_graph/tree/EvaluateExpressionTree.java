package algorithms.crackingcodinginterviewbook._4tree_and_graph.tree;

import algorithms.crackingcodinginterviewbook._4tree_and_graph.tree.baseclasses.BST;
import algorithms.crackingcodinginterviewbook._4tree_and_graph.tree.baseclasses.TreeNode;

import java.util.Stack;

import static algorithms.utils.TreeUtils.printPreety;

/*

http://www.geeksforgeeks.org/expression-tree/
https://www.youtube.com/watch?v=Rl3fc7dJ6Xs

 3 + ((5+9)*2) = 31

 Binary tree for this arithmetic expression will be
           +
         /  \
        3    *
            / \
           +   2
          / \
         5   9

Expression Tree evaluation uses post order traversal.
So, nodes will be traversed in following order
3,5,9,+,2,*,+

3,5,9 will be pushed to stack
when + comes, 9 and 5 will be popped from stack and 5+9=14 will be pushed to stack. At this point stack has 3,14.
when 2 comes, 2 will be pushed to stack. At this point stack has 3,14,2.
when * comes, 2 and 14 will be popped from stack and 2*14=28 will be pushed to stack. At this point stack has 3,28.
when + comes, 28 and 3 will be popped from stack and 28+3=31 will be pushed to stack.

At the end stack will have only one element 31 and that is the result.


 */


public class EvaluateExpressionTree {
    public static void main(String[] args) {
        {
            BST bst = new BST();
            {
                TreeNode root = new TreeNode('+');
                bst.put(Integer.valueOf('+'));

                {
                    TreeNode left = new TreeNode(3);
                    TreeNode right = new TreeNode('*');
                    bst.root.left = left;
                    bst.root.right = right;
                }
                {
                    TreeNode left = new TreeNode('+');
                    TreeNode right = new TreeNode(2);
                    bst.root.right.left = left;
                    bst.root.right.right = right;
                }
                {
                    TreeNode left = new TreeNode(5);
                    TreeNode right = new TreeNode(9);
                    bst.root.right.left.left = left;
                    bst.root.right.left.right = right;
                }
            }
            System.out.println("Created Arithmetic Expression Tree");
            printPreety(bst.root);

            Stack<Integer> stack = new Stack<>();
            evaluateExpressionTree(stack, bst.root);
            if (stack.size() > 0) { // at this point stack size will be exactly 1
                System.out.println("Evaluated Value: " + stack.pop());
            }
        }
        /*{
            String expression="3+((5+9)*2)";
            Stack<Integer> stack = new Stack<>();
            char[] chars = expression.toCharArray();
            for (Integer aChar : chars) {
                if(aChar != ')') {
                    stack.push(aChar);
                } else {

                    List<Character> poppedElements = new ArrayList<>();
                    int poppedElement;
                    while((poppedElement=stack.pop()) != '(') {
                        Integer oneNumber=poppedElement;
                        Integer operator=stack.pop();
                        Integer anotherNumber=stack.pop();

                        if(operator == '+') {
                            stack.push(oneNumber + anotherNumber);
                        }
                        else if(operator == '*') {
                            stack.push(oneNumber * anotherNumber);
                        }
                        else if(operator == '/') {
                            stack.push(oneNumber / anotherNumber);
                        }
                        else if(operator == '-') {
                            stack.push(oneNumber - anotherNumber);
                        }

                        if(poppedElement != '(') {

                        }
                    }
                    while((poppedElement=stack.pop()) != '(') {

                        if(isOperator(poppedElement)) {
                            Integer anotherNumber = stack.pop();

                        } else {
                            char oneNumber = poppedElement;
                        }
                    }

                }
            }
        }*/
    }

    public static void evaluateExpressionTree(Stack<Integer> stack, TreeNode node) { // pass root node
        if (node == null) return;

        evaluateExpressionTree(stack, node.left);
        evaluateExpressionTree(stack, node.right);

        int d = node.data;
        if (isOperator(d)) { // node is an operator, pop two elements froms stack and do the operation
            Integer t1 = stack.pop();
            Integer t2 = stack.pop();

            int evaluatedValue = 0;

            if(d == '+') {
                evaluatedValue = t1 + t2;
            }
            else if(d == '*') {
                evaluatedValue = t1 * t2;
            }
            else if(d == '/') {
                evaluatedValue = t1 / t2;
            }
            else if(d == '-') {
                evaluatedValue = t1 - t2;
            }

            stack.push(evaluatedValue); // push evaluated value to stack

        } else { // node is an operand, push it to stack
            stack.push(d);// keep pushing operands to stack
        }

    }

    private static boolean isOperator(int d) {
        return (d == '+' || d == '-' || d == '*' || d == '/');
    }
}
