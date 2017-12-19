package algorithms.treeandgraph.tree;

import algorithms.treeandgraph.tree.baseclasses.BST;
import algorithms.treeandgraph.tree.baseclasses.TreeNode;


/*
This is very important algorithm that tells you a few thing about recursion.
- reducing the problem by 1
- when you should decide to pass extra parameter to a method
- how to write an exit condition



Important:
How to know that you need to pass some extra parameters as input to the recursive method?

Example 1: FindMaxFromBinaryTree.java


Example 2: CreateLinkedListForEachLevelOfBinaryTree.java

    You want to create a list of nodes at each level of the tree.

    Map<Level, List> levelOrder(TreeNode root) {
        if(root == null) {
            Map map = new HashMap();
            map.put(0, new ArrayList());
            return map
        }
        ...
    }

    Will hard coded value level=0 in returned map work when you are at leaf.left (null) node?
    It won't. So, it's better to pass level as a parameter.

    Map<Integer, List<Integer>> levelOrder(TreeNode root, int level) {
        if(root == null) {
            return null;
        }

        Map map = new HashMap<>(); // creating a new map on each recursive call ???? doesn't look right.
        map.put(level, new ArrayList<>(){{add(root.data)}})
        ...
    }

    Do you want to return a new Map for each recursive call?
    No. You want to share the same map among all recursive calls.

    void levelOrder(TreeNode root, int level, Map<Integer, List<Integer>> map) {
        if(root == null) {
           return;
        }
        if(map.contains(level) {
            List<Integer> list = map.get(level);
            list.add(root.data);
        } else {
            List<Integer> list = new ArrayList<>();
            list.add(root.data);
            map.put(level, list);
        }
        levelOrder(root.left, ++level, map);
        levelOrder(root.right, level, map);
    }


Example 3: ValidateBST.java

 */
public class _0FindMaxFromBinaryTree {

    public static void main(String[] args) {
        // find max from Binary Tree recursively
        {
            BST bst = BST.createBST();
            System.out.println("Max: " + getMax(bst.root, Integer.MIN_VALUE));

            System.out.println("Max: " + getMax_Improved(bst.root));
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

    So, I decided to use 'max' as method parameter and

    But, I made the code unnecessarily complex by passing the same parameter that I am returning from the method.
    When you see this, you can do better as shown in getMax_Improved
     */
    private static int getMax(TreeNode root, int max) {
        if(root == null) return max;
        if(root.data > max) max=root.data;

        int maxFromLeftSubTree = getMax(root.left, max);
        max = maxFromLeftSubTree > max ? maxFromLeftSubTree : max;

        int maxFromRightSubTree = getMax(root.right, max);
        max = maxFromRightSubTree > max ? maxFromRightSubTree : max;

        return max;
    }

    /*
    In this method, I started thinking reduce the problem by 1 approach instead of thinking about
    which value needs to be shared between recursive calls and how to get away from returning hard coded value
    from exit condition.

    My thought process:
    I don't want to share the parameter 'max' between recursive calls because that's the return value of this method.
    left and right subtrees returns the max values (maxFromLeft and maxFromRight).
    How to use these values with root's data?
     */
    private static Integer getMax_Improved(TreeNode root) {
        if(root == null) return null;

        Integer max = root.data;

        Integer maxFromLeft = getMax_Improved(root.left);
        if(max == null || (max != null && maxFromLeft != null && maxFromLeft > max)) max = maxFromLeft;


        Integer maxFromRight = getMax_Improved(root.right);
        if(max == null || (max != null && maxFromRight != null && maxFromRight > max)) max = maxFromRight;

        return max;
    }
}
