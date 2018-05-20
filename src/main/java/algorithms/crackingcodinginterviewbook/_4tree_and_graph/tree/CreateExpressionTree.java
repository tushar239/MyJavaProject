package algorithms.crackingcodinginterviewbook._4tree_and_graph.tree;

import java.util.NoSuchElementException;
import java.util.Stack;

// Creating a tree from postfix expression (e.g. AB*CD/+)
// Evaluating tree pre-order and in-order to get prefix and infix exressions

// http://codereview.stackexchange.com/questions/46894/expression-tree-creation-from-postfix-expression
// http://www.geeksforgeeks.org/expression-tree/

// If you have been given a prefix expression (e.g. +*AB/CD), then you need to evaluate in reverse. first D and then C and then / etc.

// Creating a binary tree from infix expression is very complex if it needs to evaluate parenthesis.
// http://www.seas.gwu.edu/~drum/cs1112/lectures/module10/suppl/index.html
// Without parenthesis, it needs to use two stacks
// you need to maintain two stacks for infix expression (e.g. A*B+C/D)
/*
    Stack 1 is for operands
    Stack 2 is for operators

    push A node to stack1
    push * node to stack2
    push B node to stack1
    Before pushing + node to stack2, check whether stack2 size==1,
        Pop * node from stack2
        Pop A and B nodes from stack1.
        Make A node as left child and B node as right child of * node.
        Push * node to Stack1.
    now push + node to stack2
    push C node to stack1
    Before pushing / node to stack2, check whether stack2 size==1,
        Pop + node from stack2
        Pop * and C nodes from stack1
        Make * node as left child and C node as right child of + node
        Push + node to Stack1.
    push / node to stack2
    push D node to stack1
    As it is the end of prefix string,
        Pop / from stack2.
        Pop D and + nodes from Stack1.
        Make + node a left child and D as right child of / node.
 */


public class CreateExpressionTree {

    private final String postfix;
    private TreeNode root;

    /**
     * Takes in a valid postfix expression and later its used to construct the expression tree.
     * The posfix expression, if invalid, leads to invalid results
     *
     * @param postfix   the postfix expression.
     */
    public CreateExpressionTree(String postfix) {
        if (postfix == null) { throw new NullPointerException("The posfix should not be null"); }
        if (postfix.length() == 0)  { throw new IllegalArgumentException("The postfix should not be empty"); }
        this.postfix = postfix;
    }

    private static class TreeNode {
        char ch;
        TreeNode left;
        TreeNode right;

        TreeNode(char ch, TreeNode left, TreeNode right) {
            this.ch = ch;
            this.left = left;
            this.right = right;
        }
    }


    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }


    /**
     * Constructs an expression binary tree using the postfix expression (e.g. "AB*CD/+")
     */
    public void createExpressionTree() {
        final Stack<TreeNode> stack = new Stack<>();// stack of TreeNodes
        for (int i = 0; i < postfix.length(); i++) {
        //for (int i = prefix.length()-1; i >= 0; i--) { //for prefix expression
            char ch  = postfix.charAt(i);
            if (isOperator(ch)) { // if operator then pop two elements from stack and make them child stack of operator node and push that operator node to stack
                TreeNode rightNode = stack.pop();
                TreeNode leftNode = stack.pop();

                // for prefix expression
                //TreeNode leftNode = stack.pop();
                //TreeNode rightNode = stack.pop();

                stack.push(new TreeNode(ch, leftNode, rightNode));
            } else { // if operand (A,B etc) push to stack without child stack
                stack.add(new TreeNode(ch, null, null));
            }
        }
        root = stack.pop();
    }


    /**
     * Returns the prefix notation
     *
     * @return the prefix notation
     */
    public String prefix() {
        if (root == null) {
            throw new NoSuchElementException("The root is empty, the tree has not yet been constructed.");
        }

        final StringBuilder prefix = new StringBuilder();
        preOrder(root, prefix);
        return prefix.toString();
    }

    private void preOrder(TreeNode node, StringBuilder prefix) {
        if (node != null) {
            prefix.append(node.ch);
            preOrder(node.left, prefix);
            preOrder(node.right, prefix);
        }
    }

    /**
     * Returns the infix expression
     *
     * @return  the string of infix.
     */
    public String infix() {
        if (root == null) {
            throw new NoSuchElementException("The root is empty, the tree has not yet been constructed.");
        }
        final StringBuilder infix = new StringBuilder();
        inOrder(root, infix);
        return infix.toString();
    }

    private void inOrder(TreeNode node, StringBuilder infix) {
        if (node != null) {
            inOrder(node.left, infix);
            infix.append(node.ch);
            inOrder(node.right, infix);
        }
    }


    public static void main(String[] args) {
        CreateExpressionTree expressionTree1 = new CreateExpressionTree("AB*CD/+");
        expressionTree1.createExpressionTree();
        System.out.println("+*AB/CD".equals(expressionTree1.prefix()));// same as +(*(A,B)/(C,D))
        System.out.println("A*B+C/D".equals(expressionTree1.infix()));

        CreateExpressionTree expressionTree2 = new CreateExpressionTree("ABC+*D/");
        expressionTree2.createExpressionTree();
        System.out.println("/*A+BCD".equals(expressionTree2.prefix()));
        System.out.println("A*B+C/D".equals(expressionTree2.infix()));

        CreateExpressionTree expressionTree3 = new CreateExpressionTree("ABCD/+*");
        expressionTree3.createExpressionTree();
        System.out.println("*A+B/CD".equals(expressionTree3.prefix()));
        System.out.println("A*B+C/D".equals(expressionTree3.infix()));

    }
}