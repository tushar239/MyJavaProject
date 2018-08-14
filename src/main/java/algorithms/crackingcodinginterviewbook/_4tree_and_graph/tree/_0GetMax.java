package algorithms.crackingcodinginterviewbook._4tree_and_graph.tree;

import algorithms.crackingcodinginterviewbook._4tree_and_graph.tree.baseclasses.BST;
import algorithms.crackingcodinginterviewbook._4tree_and_graph.tree.baseclasses.TreeNode;

public class _0GetMax {

    public static void main(String[] args) {
        // find max from Binary Tree recursively
        {
            BST bst = BST.createBST();
            System.out.println("Max: " + getMax_TailRecursion(bst.root, null));

            System.out.println("Max: " + getMax(bst.root));

            getMax_(bst.root);
            System.out.println("Max: " + max);
        }
    }

    // find max from Binary Tree
    /*
    In this method, I started writing exit condition first

    if(root == null) return ???

    I was struggling to figure out what should be the return value.

    Idea that came to mind is can I get away from that problem by passing 'max' as method parameter?
    Answer was 'yes'.

    Next question that I asked myself was 'does method parameter 'max' need to be shared between recursive calls?'
    Answer was 'yes' because value of 'max' changes as we recurse.

    So, I decided to use 'max' as method parameter

    REMEMBER:
    When you pass a result of a method as a parameter, you are moving towards Tail-Recursion approach that is a bit complex.
    Try to solve the problem without Tail-Recursion first.
    If you are trying to pass a variable to a method that is not an output of the method, then it's ok. It's not a tail-recursion.
    e.g. PathsWithSum.java's countPathsWithSum(TreeNode root, int expectedSum, int currentSum) method
    where currentSum is passed as method parameter.

    See getMax to see non-tail-recursive approach. I would do this first for simplicity of logic thinking.
     */
    private static int getMax_TailRecursion(TreeNode root, Integer max) {
        if(root == null) return max;
        if(max == null || root.data > max) {max=root.data;}

        getMax_TailRecursion(root.left, max);
        return getMax_TailRecursion(root.right, max);
    }

    /*
    In this method, I started thinking reduce the problem by 1 approach instead of thinking about
    which value needs to be shared between recursive calls and how to get away from returning root_to_leaf_problems_hard coded value
    from exit condition.

    My thought process:
    I don't want to share the parameter 'max' between recursive calls because that's the return value of this method.
    left and right subtrees returns the max values (maxFromLeft and maxFromRight).
    How to use these values with root's data?
     */
    private static Integer getMax(TreeNode root) {
        if(root == null) return null;

        Integer maxOfRoot = root.data;

        Integer maxFromLeft = getMax(root.left);
        if(maxOfRoot == null || (maxOfRoot != null && maxFromLeft != null && maxFromLeft > maxOfRoot)) maxOfRoot = maxFromLeft;

        Integer maxFromRight = getMax(root.right);
        if(maxOfRoot == null || (maxOfRoot != null && maxFromRight != null && maxFromRight > maxOfRoot)) maxOfRoot = maxFromRight;

        return maxOfRoot;
    }


    /*
        When not to pass return value as a method parameter?

            - If your return value is a literal (Integer, Long, String etc). If you pass it as a method parameter, then caller of the method will not see the final result. It will see the same value that it passed as method parameter.
            e.g. ConvertBinaryTreeToBstWithoutChangingItsSpatialStructure.java
                 GetMax.java --- in this case, solution 3 makes more sense, but it depends on the requirement.

                    int max = 1;
                    method(max);
                    System.out.println(max); --- O/P:1

                    void method(int max) { max = 5; }

                    To avoid this,

                    Solution 1:

                        int max = 1;
                        max = method(max);
                        System.out.println(max); --- O/P:5

                        int method(int max) { max = 5; return max;}

                    Solution 2:

                        Result result = new Result(); result.setMax(1);
                        method(result);
                        System.out.println(result.getMax()); --- O/P:5

                        void method(Result result) { result.setMax(5);}

                    Solution 3:

                        int max = method(max);
                        System.out.println(max); --- O/P:5

                        int method() { max = 5; return max;}

    */

    private static Integer max = Integer.MIN_VALUE;

    private static void getMax_(TreeNode root) {
        if(root == null) return;

        if(max < root.data) {
            max = root.data;
        }
        getMax_(root.left);

        getMax_(root.right);

    }
}
