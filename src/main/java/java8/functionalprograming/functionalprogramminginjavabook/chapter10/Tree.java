package java8.functionalprograming.functionalprogramminginjavabook.chapter10;


import java8.functionalprograming.functionalprogramminginjavabook.chapter2.Function;
import java8.functionalprograming.functionalprogramminginjavabook.chapter5and8.List;
import java8.functionalprograming.functionalprogramminginjavabook.chapter7.Result;

/*
You can consider DefaultTree as BST.java's TreeNode class in algorithms package.
TreeNode class has
    A data
    TreeNode left
    TreeNode right
DefaultTree class has
    A value
    Tree left
    Tree right
Difference between two of these is
    TreeNode is mutable (you can change its value and left/right children) and it can have null children nodes.
    DefaultTree is immutable and it can have EmptyTree as subtree(s). Every time, you modify it, it returns a new DefaultTree instance.

This class is an example of 'Binary Search Tree'.

Immutability avoids concurrency problems:

    Functional trees like have the advantage of immutability, which allows using them in multithreaded environments without bothering with locks and synchronization.

Folding

    Folding is like traversing a data structure like list and tree and during traversal applying an operation of its elements and creating a new data structure out of it.

    Operation that creates output from the operation on two elements of a list is called 'folding' as you learned in chapter 3.
    It is same as Java 8 Stream's reduce(...) operation.

 */


// This class is a BST (Binary Search Tree) functional way (immutability+using function)
public abstract class Tree<A extends Comparable<A>> { // The Tree class is parameterized and the parameter type must extend Comparable.
    @SuppressWarnings("rawtypes")
    private static Tree EMPTY_TREE = new EmptyTree();

    public abstract A value();

    abstract Tree<A> left();

    abstract boolean hasLeft();

    abstract Tree<A> right();

    abstract boolean hasRight();

    public abstract boolean isEmpty();

    public Tree<A> insert(A insertedValue) {
        return insert(this, insertedValue);
    }

    public Result<A> get(A element) {
        return get(this, element);
    }
    public static <A extends Comparable<A>> Result<A> get(Tree<A> tree, A element) {
        if(tree.isEmpty()) return Result.empty();

        if(tree.value().compareTo(element) == 0) {
            return Result.success(tree.value());
        }

        if(tree.value().compareTo(element) < 0) {
            return get(tree.left(), element);
        }

        if(tree.value().compareTo(element) > 0) {
            return get(tree.right(), element);
        }

        return Result.empty();
    }


    // pg 290
    public static <A extends Comparable<A>> Tree<A> insert(Tree<A> rootTree, A insertedValue) {
        if (rootTree.isEmpty()) { // exit condition
            return new DefaultTree<>(insertedValue, empty(), empty());
        }
        if (insertedValue.compareTo(rootTree.value()) == 0) { // exit condition
            return new DefaultTree<>(insertedValue, rootTree.left(), rootTree.right());
        }

        if (insertedValue.compareTo(rootTree.value()) < 0) {
            Tree<A> newLeft = insert(rootTree.left(), insertedValue); // reducing problem by 1
            return new DefaultTree<>(rootTree.value(), newLeft, rootTree.right()); // connecting the result with root
        }
        if (insertedValue.compareTo(rootTree.value()) > 0) {
            Tree<A> newRight = insert(rootTree.right(), insertedValue); // reducing problem by 1
            return new DefaultTree<>(rootTree.value(), rootTree.left(), newRight); // connecting the result with root
        }
        return Tree.empty(); // never reached
    }

    public Tree<A> remove(A value) {
        return remove(this, value);
    }

    private boolean isLeafTree() {
        return !this.isEmpty() && !this.hasLeft() && !this.hasRight();
    }

    private boolean hasOnlyOneChild() {
        return !this.isEmpty() && ((!this.hasLeft() && this.hasRight()) || (this.hasLeft() && !this.hasRight()));
    }

    private boolean hasTwoChildren() {
        return !this.isEmpty() && (this.hasLeft() && this.hasRight());
    }


    /*
                11
                |
            _____________
            |           |
            8           14
            |           |
        --------    ---------
        |       |   |       |
        5       10  13      15
        |
     -------
     |      |
     3      6
    If you want to remove 11, then you need to merge subtrees 8 and 14 and create a new tree with (value=8,
                                                                                                   left=merged tree from 8's left(5) and right(10),
                                                                                                   right=14)
    Resulting new tree:
                11
                |
            _____________
            |           |
            5           14
            |           |
        --------    ---------
        |       |   |       |
        3       10  13      15
        |
     --------
     |      |
     Nil    6
    I have created very generic version of merging two trees (merge method).
     */
    public static <A extends Comparable<A>> Tree<A> remove(Tree<A> rootTree, A value) {
        if (rootTree.isEmpty()) return Tree.empty();

        if (value.compareTo(rootTree.value()) == 0) { // exit condition

            return merge(rootTree.left(), rootTree.right()); // merge is called in exit condition only

        }

        if (value.compareTo(rootTree.value()) < 0) {

            Tree<A> newLeftTree = remove(rootTree.left(), value); // this will return merged result of deleted node's left and right subtrees

            return new DefaultTree<A>(rootTree.value(), newLeftTree, rootTree.right()); // merged result has to be combined with root

        } else if (value.compareTo(rootTree.value()) > 0) {

            Tree<A> newRightTree = remove(rootTree.right(), value); // this will return merged result of deleted node's left and right subtrees

            return new DefaultTree<A>(rootTree.value(), rootTree.left(), newRightTree); // merged result has to be combined with root

        }

        return Tree.empty(); // never reached
    }


    // From book's (pg 296)
    // This merge method is replacing complex logic of BST.java's deleteNode()->deleteRootAndMergeItsLeftAndRight() and merge() methods.
    // Look at the advantage of this method over BST.java's deleteNode() and merge() methods.
    // Not able to mutate original tree makes the recursion so simple. You don't need to remember parent node in case of deleteNode logic and you don't need to apply complex logic during merge like BST.java where you need to modify the same tree.
    public static <A extends Comparable<A>> Tree<A> merge(Tree<A> treeOne, Tree<A> treeTwo) {
        if (treeOne.isEmpty()) {
            return treeTwo;
        }
        if (treeTwo.isEmpty()) {
            return treeOne;
        }

        if (treeOne.value().compareTo(treeTwo.value()) == 0) {
            return new DefaultTree<A>(
                    treeOne.value(),
                    //if you do
                    // merge(treeOne.left(), treeOne.right())
                    // merge(treeTwo.left(), treeTwo.right())
                    //it will result in duplicate elements in merged tree, if both trees are exactly same
                    merge(treeOne.left(), treeTwo.left()),
                    merge(treeTwo.right(), treeTwo.right()));
        }

        if (treeOne.value().compareTo(treeTwo.value()) < 0) {
            return new DefaultTree<A>(
                    treeOne.value(),
                    merge(treeOne.left(), treeOne.right()),
                    treeTwo);
        }

        if (treeOne.value().compareTo(treeTwo.value()) > 0) {
            return new DefaultTree<A>(
                    treeTwo.value(),
                    treeOne,
                    merge(treeTwo.left(), treeTwo.right()));
        }

        return empty(); // never reached

    }


    public void preOrderTraversal() {
        preOrderTraversal(this);
    }

    // same as BST.java's preOrderTraversal method
    private static <A extends Comparable<A>> void preOrderTraversal(Tree<A> tree) {
        if (tree.isEmpty()) return; // exit condition

        visitTreeNode(tree); // visit current node
        preOrderTraversal(tree.left()); // go left
        preOrderTraversal(tree.right()); // go right
    }

    public void inOrderTraversal() {
        inOrderTraversal(this);
    }

    // same as BST.java's inOrderTraversal method
    private static <A extends Comparable<A>> void inOrderTraversal(Tree<A> tree) {
        if (tree.isEmpty()) return; // exit condition

        inOrderTraversal(tree.left()); // go left
        visitTreeNode(tree); // visit current node
        inOrderTraversal(tree.right()); // go right
    }

    public void postOrderTraversal() {
        postOrderTraversal(this);
    }

    // same as BST.java's postOrderTraversal method
    private static <A extends Comparable<A>> void postOrderTraversal(Tree<A> tree) {
        if (tree.isEmpty()) return; // exit condition

        postOrderTraversal(tree.left()); // go left
        postOrderTraversal(tree.right()); // go right
        visitTreeNode(tree); // visit current node
    }

    private static <A extends Comparable<A>> void visitTreeNode(Tree<A> tree) {
        System.out.println(tree.toString());
    }

    public boolean member(A value) {
        return member(this, value);
    }
    // pg 291
    public static <A extends Comparable<A>> boolean member(Tree<A> tree, A value) {
        /*if (tree.isEmpty()) return false;
        if (value.compareTo(tree.value()) == 0) return true;

        if (value.compareTo(tree.value()) < 0) {
            return member(tree.left(), value);
        } else if (value.compareTo(tree.value()) > 0) {
            return member(tree.right(), value);
        }*/
        Result<A> aResult = get(tree, value);

        if(aResult instanceof Result.Empty) {
            return false;
        }

        A aNull = null;
        A element = aResult.getOrElse(aNull);
        if(element == null) {
            return false;
        }
        return true;
    }

    // create a tree from a List (Wow... Amazing...)
    // pg 292
    public static <A extends Comparable<A>> Tree<A> tree(List<A> list) {
        return list.foldLeftTailRecursive(list, Tree.<A>empty(), input -> tree -> tree.insert(input));
    }

    // create a tree from array of elements
    // pg 292
    public static <A extends Comparable<A>> Tree<A> tree(A... as) {
        return tree(List.list(as));
    }

    // How many elements are there in a tree
    // pg 292
    public static <A extends Comparable<A>> int size(Tree<A> tree) {
        if (tree.isEmpty()) return 0;
        return size(tree.left()) + size(tree.right()) + 1; // reducing a problem by 1 and concatenating the result with root tree
    }

    // pg 292
    // height of a tree = number of edges from root node to a farthest leaf node. If you return 0, it will give you +1 height.
    public static <A extends Comparable<A>> int height(Tree<A> tree) {
        if (tree.isEmpty())
            return -1; // why -1? height of a tree = number of edges from root node to a farthest leaf node. If you return 0, it will give you +1 height.
        return Math.max(height(tree.left()), height(tree.right())) + 1; // reducing a problem by 1 and concatenating the result with root tree
    }

    public A max() {
        Result<A> max = max(this);
        A aNull = null;
        return max.getOrElse(aNull);
    }

    // pg 293
    public static <A extends Comparable<A>> Result<A> max(Tree<A> tree) {
        if (tree.isEmpty()) return Result.empty();

        if (tree.right().isEmpty()) return Result.success(tree.value());

        return max(tree.right());
    }

    public A min() {
        Result<A> min = min(this);
        A aNull = null;
        return min.getOrElse(aNull);
    }

    // pg 293
    public static <A extends Comparable<A>> Result<A> min(Tree<A> tree) {
        if (tree.isEmpty()) return Result.empty();

        if (tree.left().isEmpty()) return Result.success(tree.value());

        return min(tree.left());
    }

    // pg 301
    /*
        Folding a tree (traversing a tree) is bi-recursive means folding left subtree+folding right subtree+combining their results with the rootTree.
        Isn’t it same as creating two sublists from a list and processing them in parallel and then combining the result?

        Yes, it is the same.

        So as for parallel processing of a list, you need a second function to combine the results of two sublists processing,
        you need second function for combining results from left and right subtrees processing.

        For the difference between foldLeft and foldRight, see pg 303.

        In List foldRight is same as applying foldLeft on reversed List.
        In Tree foldRight is different. In Tree, it is just in what order do you combine the results of left and right subtrees.
     */
    public static <A extends Comparable<A>, B>
    B foldLeft_PreOrder_Using_Two_Functions(Tree<A> rootTree, B identity,
                                            Function<A, Function<B, B>> f,
                                            Function<B, Function<B, B>> g) {
        if (rootTree.isEmpty()) {
            return identity;
        }

        // traversing left subTree
        B leftSubTreeResult = identity;
        if (!rootTree.left().isEmpty()) {
            leftSubTreeResult = foldLeft_PreOrder_Using_Two_Functions(rootTree.left(), identity, f, g);
        }

        // traversing right subTree
        B rightSubTreeResult = identity;
        if (!rootTree.right().isEmpty()) {
            rightSubTreeResult = foldLeft_PreOrder_Using_Two_Functions(rootTree.right(), identity, f, g);
        }

        // PreOrder Traversal
        // combining
        // rootTree result + left subTree result + right subTree result   (folding from left)
        // or
        // rootTree result + right subTree result + left subTree result   (folding from right)
        B rootTreeResult = f.apply(rootTree.value()).apply(identity);
        B leftSubTreeRootTreeCombinedResult = g.apply(rootTreeResult).apply(leftSubTreeResult);
        return g.apply(leftSubTreeRootTreeCombinedResult).apply(rightSubTreeResult);
        // folding from right is
        // B rightSubTreeRootTreeCombinedResult = g.apply(rightSubTreeResult).apply(rootTreeResult);
        // return g.apply(rightSubTreeRootTreeCombinedResult).apply(leftSubTreeResult);

    }

    public static <A extends Comparable<A>, B>
    B foldLeft_PostOrder_Using_Two_Functions(Tree<A> rootTree, B identity,
                                             Function<A, Function<B, B>> f,
                                             Function<B, Function<B, B>> g) {
        if (rootTree.isEmpty()) {
            return identity;
        }

        // traversing left subTree
        B leftSubTreeResult = identity;
        if (!rootTree.left().isEmpty()) {
            leftSubTreeResult = foldLeft_PostOrder_Using_Two_Functions(rootTree.left(), identity, f, g);
        }

        // traversing right subTree
        B rightSubTreeResult = identity;
        if (!rootTree.right().isEmpty()) {
            rightSubTreeResult = foldLeft_PostOrder_Using_Two_Functions(rootTree.right(), identity, f, g);
        }


        B rootTreeResult = f.apply(rootTree.value()).apply(identity);

        // PostOrder traversal
        // combining
        // left subTree result + right subTree result + rootTree result    (folding from left)
        // or
        // right subTree result + left subTree result + rootTree result    (folding from right)
        B leftAndRightSubTreeCombinedResult = g.apply(leftSubTreeResult).apply(rightSubTreeResult);
        // folding from right is
        // B leftAndRightSubTreeCombinedResult = g.apply(rightSubTreeResult).apply(leftSubTreeResult);
        return g.apply(leftAndRightSubTreeCombinedResult).apply(rootTreeResult);
    }

    public static <A extends Comparable<A>, B>
    B foldLeft_InOrder_Using_Two_Functions(Tree<A> rootTree, B identity,
                                           Function<A, Function<B, B>> f,
                                           Function<B, Function<B, B>> g) {
        if (rootTree.isEmpty()) {
            return identity;
        }

        // traversing left subTree
        B leftSubTreeResult = identity;
        if (!rootTree.left().isEmpty()) {
            leftSubTreeResult = foldLeft_InOrder_Using_Two_Functions(rootTree.left(), identity, f, g);
        }

        // traversing right subTree
        B rightSubTreeResult = identity;
        if (!rootTree.right().isEmpty()) {
            rightSubTreeResult = foldLeft_InOrder_Using_Two_Functions(rootTree.right(), identity, f, g);
        }

        // InOrder traversal
        // combining
        // left subTree result + rootTree result + right subTree result   (folding from left)
        // or
        // right subTree result + rootTree result + left subTree result   (folding from right)
        B rootTreeResult = f.apply(rootTree.value()).apply(identity);
        B leftSubTreeAndRootTreeResult = g.apply(leftSubTreeResult).apply(rootTreeResult);
        return g.apply(leftSubTreeAndRootTreeResult).apply(rightSubTreeResult);

        // folding from right is
        // B rootTreeResult = f.apply(rootTree.value()).apply(identity);
        // B rightSubTreeAndRootTreeResult = g.apply(foldResultOfRightSubTree).apply(rootTreeResult);
        // return g.apply(rightSubTreeAndRootTreeResult).apply(foldResultOfLeftSubTree);
    }


    /*
        pg 303
        Traversal of a tree can be done using one function as shown below.
        So below two functions
        Function<A, Function<B, B>> f,
        Function<B, Function<B, B>> g
        are combined to one
        Function<A, Function<B, Function<B, B>>> f  --- PreOrder traversal
        Function<B, Function<B, Function<A, B>>> f  --- PostOrder traversal
        Function<B, Function<A, Function<B, B>>> f  --- InOrder traversal
     */

    public static <A extends Comparable<A>, B>
    B foldLeft_PreOrder_Using_One_Function(Tree<A> rootTree, B identity,
                                           Function<A, Function<B, Function<B, B>>> f) {

        if (rootTree.isEmpty()) {
            return identity;
        }

        // traversing left subTree
        B leftSubTreeResult = identity;
        if (!rootTree.left().isEmpty()) {
            leftSubTreeResult = foldLeft_PreOrder_Using_One_Function(rootTree.left(), identity, f);
        }

        // traversing right subTree
        B rightSubTreeResult = identity;
        if (!rootTree.right().isEmpty()) {
            rightSubTreeResult = foldLeft_PreOrder_Using_One_Function(rootTree.right(), identity, f);
        }

        // PreOrder Traversal
        // combining
        // rootTree result + left subTree result + right subTree result   (folding from left)
        // or
        // rootTree result + right subTree result + left subTree result   (folding from right)
        return f.apply(rootTree.value()).apply(leftSubTreeResult).apply(rightSubTreeResult);

        // folding from right is
        // return f.apply(rootTree.value()).apply(rightSubTreeResult).apply(leftSubTreeResult);
    }

    public static <A extends Comparable<A>, B>
    B foldLeft_PostOrder_Using_One_Function(Tree<A> rootTree, B identity,
                                            Function<B, Function<B, Function<A, B>>> f) {

        if (rootTree.isEmpty()) {
            return identity;
        }

        // traversing left subTree
        B foldResultOfLeftSubTree = identity;
        if (!rootTree.left().isEmpty()) {
            foldResultOfLeftSubTree = foldLeft_PostOrder_Using_One_Function(rootTree.left(), identity, f);
        }

        // traversing right subTree
        B foldResultOfRightSubTree = identity;
        if (!rootTree.right().isEmpty()) {
            foldResultOfRightSubTree = foldLeft_PostOrder_Using_One_Function(rootTree.right(), identity, f);
        }

        // PostOrder traversal
        // combining
        // left subTree result + right subTree result + rootTree result    (folding from left)
        // or
        // right subTree result + left subTree result + rootTree result    (folding from right)
        return f.apply(foldResultOfLeftSubTree).apply(foldResultOfRightSubTree).apply(rootTree.value());


        // folding from right is
        // f.apply(foldResultOfRightSubTree).apply(foldResultOfLeftSubTree).apply(rootTree.value());
    }

    public static <A extends Comparable<A>, B>
    B foldLeft_InOrder_Using_One_Function(Tree<A> rootTree, B identity,
                                          Function<B, Function<A, Function<B, B>>> f) {

        if (rootTree.isEmpty()) {
            return identity;
        }

        // traversing left subTree
        B foldResultOfLeftSubTree = identity;
        if (!rootTree.left().isEmpty()) {
            foldResultOfLeftSubTree = foldLeft_InOrder_Using_One_Function(rootTree.left(), identity, f);
        }

        // traversing right subTree
        B foldResultOfRightSubTree = identity;
        if (!rootTree.right().isEmpty()) {
            foldResultOfRightSubTree = foldLeft_InOrder_Using_One_Function(rootTree.right(), identity, f);
        }

        // InOrder traversal
        // combining
        // left subTree result + rootTree result + right subTree result   (folding from left)
        // or
        // right subTree result + rootTree result + left subTree result   (folding from right)
        return f.apply(foldResultOfLeftSubTree).apply(rootTree.value()).apply(foldResultOfRightSubTree);

        // folding from right is
        // return f.apply(foldResultOfRightSubTree).apply(rootTree.value()).apply(foldResultOfLeftSubTree);
    }

    public static <A extends Comparable<A>, B>
    B foldRight_InOrder_Using_One_Function(Tree<A> rootTree, B identity,
                                          Function<B, Function<A, Function<B, B>>> f) {

        if (rootTree.isEmpty()) {
            return identity;
        }

        // traversing left subTree
        B foldResultOfLeftSubTree = identity;
        if (!rootTree.left().isEmpty()) {
            foldResultOfLeftSubTree = foldRight_InOrder_Using_One_Function(rootTree.left(), identity, f);
        }

        // traversing right subTree
        B foldResultOfRightSubTree = identity;
        if (!rootTree.right().isEmpty()) {
            foldResultOfRightSubTree = foldRight_InOrder_Using_One_Function(rootTree.right(), identity, f);
        }

        // InOrder traversal
        // combining
        // left subTree result + rootTree result + right subTree result   (folding from left)
        // or
        // right subTree result + rootTree result + left subTree result   (folding from right)
        return f.apply(foldResultOfRightSubTree).apply(rootTree.value()).apply(foldResultOfLeftSubTree);
    }

    // pg 307
    public static <A extends Comparable<A>, B extends Comparable<B>>
    Tree<B> map(Tree<A> tree, Function<A, B> f) {
        Tree<B> identity = new EmptyTree<B>();
        return foldLeft_PreOrder_Using_One_Function(
                tree,
                identity,
                treeValue -> leftSubTreeResult -> rightSubTreeResult ->
                        new DefaultTree<B>(f.apply(treeValue), leftSubTreeResult, rightSubTreeResult)
        );
    }
    public static <A extends Comparable<A>, B>
    List<B> map_to_list(Tree<A> tree, Function<A, B> f) {
        List<B> identity = List.nilList();
        return foldLeft_PreOrder_Using_One_Function(
                tree,
                identity,
                treeValue -> leftSubTreeResult -> rightSubTreeResult ->
                        List.consList(f.apply(treeValue), leftSubTreeResult).concat(rightSubTreeResult)
        );
    }

    // pg 308, 309 (Rotating Trees - see pg 308 figures to understand how rotation works)
    // Description of right rotation.
    // 1. First, test the left branch for emptiness.
    // 2. If the left branch is empty, just return this since rotating right consists into promoting the left element to root. (We can’t promote an empty tree.)
    // 3. If the left element is not empty, it becomes the root,
    //    so a new Tree is created with left.value as the root.
    //    The left branch of the left element becomes the left branch of the new tree.
    //    For the right branch, we construct a new tree with the original root as the root, the right branch of the original left as the left branch, and the original right as the right branch.

    protected static <A extends Comparable<A>> Tree<A> rotateRight(Tree<A> tree) {
        if (tree.isEmpty() || tree.left().isEmpty()) return tree;
        return new DefaultTree<A>(tree.left().value(),
                tree.left().left(),
                new DefaultTree<A>(tree.value(), tree.left().right(), tree.right()));
    }

    protected static <A extends Comparable<A>> Tree<A> rotateLeft(Tree<A> tree) {
        if (tree.isEmpty() || tree.right().isEmpty()) return tree;
        return new DefaultTree<A>(tree.right().value(),
                new DefaultTree<A>(tree.value(), tree.left(), tree.right().left()),
                tree.right().right());
    }


    // This method just creates a string representation of in-order tree traversal
    public static <A extends Comparable<A>>
    String foldLeft_InOrder(Tree<A> rootTree) {
        String identity = "";
        String result = foldLeft_InOrder_Using_One_Function(rootTree,
                identity,
                leftSubTreeResult -> treeNodeValue -> rightSubTreeResult -> leftSubTreeResult + "," + treeNodeValue + "" + rightSubTreeResult);
        return result;
    }
    public static <A extends Comparable<A>>
    String foldRight_InOrder(Tree<A> rootTree) {
        String identity = "";
        String result = foldRight_InOrder_Using_One_Function(rootTree,
                identity,
                rightSubTreeResult -> treeNodeValue -> leftSubTreeResult -> rightSubTreeResult + "," + treeNodeValue + "" + leftSubTreeResult);
        return result;
    }

    // This method just creates a string representation of pre-order tree traversal
    public static <A extends Comparable<A>>
    String foldLeft_PreOrder(Tree<A> rootTree) {
        String identity = "";
        String result = foldLeft_PreOrder_Using_One_Function(rootTree,
                identity,
                treeValue -> leftSubTreeResult -> rightSubTreeResult -> treeValue + "," + leftSubTreeResult + rightSubTreeResult);
        return result;
    }

    // This method just creates a string representation of post-order tree traversal
    public static <A extends Comparable<A>>
    String foldLeft_PostOrder(Tree<A> rootTree) {
        String identity = "";
        String result = foldLeft_PostOrder_Using_One_Function(rootTree,
                identity,
                leftSubTreeResult -> rightSubTreeResult -> treeNodeValue -> leftSubTreeResult + "" + rightSubTreeResult + "," + treeNodeValue);
        return result;
    }


    private static class EmptyTree<A extends Comparable<A>> extends Tree<A> { // represents an empty tree
        @Override
        public A value() {
            throw new IllegalStateException("value() called on empty");
        }

        @Override
        Tree<A> left() {
            throw new IllegalStateException("left() called on empty");
        }

        @Override
        boolean hasLeft() {
            return false;
        }

        @Override
        Tree<A> right() {
            throw new IllegalStateException("right() called on empty");
        }

        @Override
        boolean hasRight() {
            return false;
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public String toString() {
            return "Empty";
        }
    }

    public static class DefaultTree<A extends Comparable<A>> extends Tree<A> { // represents a non empty tree
        private A value = null;
        private Tree<A> left = null;
        private Tree<A> right = null;

        public DefaultTree(A value, Tree<A> left, Tree<A> right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }



        @Override
        public A value() {
            return value;
        }

        @Override
        Tree<A> left() {
            return left;
        }

        @Override
        boolean hasLeft() {
            return !left().isEmpty();
        }

        @Override
        Tree<A> right() {
            return right;
        }

        @Override
        boolean hasRight() {
            return !right().isEmpty();
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public String toString() {
            //return String.format("(DefaultTree: %s %s %s)", left, value, right);
            return String.format("(DefaultTree: %s)", value);
        }

    }

    @SuppressWarnings("unchecked")
    public static <A extends Comparable<A>> Tree<A> empty() {
        return EMPTY_TREE;
    }


    public static void main(String[] args) {
        Tree<Integer> tree = Tree.empty();
        tree = tree.insert(tree, 11);
        tree = tree.insert(tree, 8);
        tree = tree.insert(tree, 14);
        tree = tree.insert(tree, 5);
        tree = tree.insert(tree, 10);
        tree = tree.insert(tree, 13);
        tree = tree.insert(tree, 15);
        tree = tree.insert(tree, 3);
        tree = tree.insert(tree, 6);

        /*
                11
                |
            _____________
            |           |
            8           14
            |           |
        --------    ---------
        |       |   |       |
        5       10  13      15
        |
     -------
     |      |
     3      6
         */

        System.out.println("Pre-Order Traversal...");
        tree.preOrderTraversal();
        System.out.println("In-Order Traversal...");
        tree.inOrderTraversal();
        System.out.println("Post-Order Traversal...");
        tree.postOrderTraversal();

        System.out.println("Is 3 a member of a tree?: " + Tree.member(tree, 3)); // true
        System.out.println("Is 8 a member of a tree?: " + Tree.member(tree, 51)); // false

        System.out.println("Size of a Tree: " + Tree.size(tree)); // 9
        System.out.println("Height of a Tree: " + Tree.height(tree)); // 3

        System.out.println("Max from all elements: " + Tree.max(tree).getOrElse(-1)); // 15
        System.out.println("Min from all elements: " + Tree.min(tree).getOrElse(-1)); // 3

        System.out.println("Removing an element: ");
        Tree<Integer> remove = remove(tree, 8);
        remove.preOrderTraversal();

        {
            System.out.println("Merging two Trees: ");
            Tree<Integer> tree2 = Tree.empty();
            tree2 = tree2.insert(tree, 11);
            tree2 = tree2.insert(tree, 8);
            tree2 = tree2.insert(tree, 14);
            tree2 = tree2.insert(tree, 5);
            tree2 = tree2.insert(tree, 10);
            tree2 = tree2.insert(tree, 13);
            tree2 = tree2.insert(tree, 15);
            tree2 = tree2.insert(tree, 3);
            tree2 = tree2.insert(tree, 6);

            Tree<Integer> mergedTree = merge(tree, tree2);// hi, how r u?
            mergedTree.preOrderTraversal();

//            System.out.println("another way:");
//
//            Tree<Integer> merge = merge(tree, tree2);
//            merge.preOrderTraversal();

        }

        System.out.println();
        System.out.println("Folding Left a tree using TWO functions.....");
        {
            System.out.println("PreOrder Traversal: ");
/*
            List<Object> result = foldLeft_PreOrder_Using_Two_Functions(tree,
                    List.nilList(),
                    treeNodeValue -> identityList -> new List.Cons(treeNodeValue, identityList),
                    list_From_LeftSubTreeResult -> list_From_RightSubTreeResult -> List.concat(list1, list2)
            );
*/
            String result = foldLeft_PreOrder_Using_Two_Functions(tree,
                    "",
                    treeNodeValue -> identity -> treeNodeValue + "," + identity,
                    str_From_LeftSubTreeResult -> str_From_RightSubTreeResult -> str_From_LeftSubTreeResult + str_From_RightSubTreeResult
            );

            System.out.println(result.toString());// 3,5,6,8,10,11,13,14,15
        }
        {
            System.out.println("PostOrder Traversal:  ");
            /*List<Object> result = foldLeft_PostOrder_Using_Two_Functions(tree,
                    List.nilList(),
                    treeNodeValue -> identityList -> new List.Cons(treeNodeValue, identityList),
                    list_From_LeftSubTreeResult -> list_From_RightSubTreeResult -> List.concat(list_From_LeftSubTreeResult, list_From_RightSubTreeResult)
            );*/
            String result = foldLeft_PostOrder_Using_Two_Functions(tree,
                    "",
                    treeNodeValue -> identity -> treeNodeValue + "," + identity,
                    str_From_LeftSubTreeResult -> str_From_RightSubTreeResult -> str_From_LeftSubTreeResult + str_From_RightSubTreeResult
            );
            System.out.println(result.toString()); // 3,6,5,10,8,13,15,14,11
        }
        {
            System.out.println("InOrder Traversal:  ");
/*
            List<Object> result = foldLeft_InOrder_Using_Two_Functions(tree,
                    List.nilList(),
                    treeNodeValue -> identityList -> new List.Cons(treeNodeValue, identityList),
                    list_From_LeftSubTreeResult -> list_From_RightSubTreeResult -> List.concat(list_From_LeftSubTreeResult, list_From_RightSubTreeResult)
            );
*/
            String result = foldLeft_InOrder_Using_Two_Functions(tree,
                    "",
                    treeNodeValue -> identity -> treeNodeValue + "," + identity,
                    str1 -> str2 -> str1 + str2
            );

            System.out.println(result.toString());// 11,8,5,3,6,10,14,13,15
        }

        System.out.println();
        System.out.println("Folding Left a tree using ONE function.....");
        {
            System.out.println("PreOrder Traversal: ");
            String identity = "";
            String result = foldLeft_PreOrder_Using_One_Function(tree,
                    identity,
                    treeNodeValue -> leftSubTreeResult -> rightSubTreeResult -> treeNodeValue + "," + leftSubTreeResult + rightSubTreeResult

            );
            System.out.println(result.toString()); // 11,8,5,3,6,10,14,13,15
        }
        {
            System.out.println("PostOrder Traversal: ");
            String identity = "";
            String result = foldLeft_PostOrder_Using_One_Function(tree,
                    identity,
                    leftSubTreeResult -> rightSubTreeResult -> treeNodeValue -> leftSubTreeResult + "" + rightSubTreeResult + "," + treeNodeValue

            );
            System.out.println(result.toString()); // 3,6,5,10,8,13,15,14,11
        }
        {
            System.out.println("InOrder Traversal: ");
            System.out.println("folding from left");
            String identity = "";
            String result = foldLeft_InOrder_Using_One_Function(tree,
                    identity,
                    leftSubTreeResult -> treeNodeValue -> rightSubTreeResult -> leftSubTreeResult + "," + treeNodeValue + "" + rightSubTreeResult

            );
            System.out.println(result.toString());  // 3,5,6,8,10,11,13,14,15,

            System.out.println("folding from right");
            String foldRightResult = foldRight_InOrder(tree);
            System.out.println(foldRightResult);// 15,14,13,11,10,8,6,5,3
        }

        System.out.println();
        System.out.println("map function...");
        {

            Tree<Integer> mappedTree = map(tree, treeElement -> treeElement * 2);
            String result = foldLeft_PreOrder_Using_One_Function(
                    mappedTree,
                    "",
                    treeValue -> leftSubTreeResult -> rightSubTreeResult -> treeValue + "," + leftSubTreeResult + rightSubTreeResult);
            System.out.println(result); // 22,16,10,6,12,20,28,26,30,

        }
    }

}