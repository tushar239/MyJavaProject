package algorithms.crackingcodinginterviewbook._4tree_and_graph.tree.baseclasses;

/**
 * @author Tushar Chokshi @ 8/27/15.
 */
public class TreeNode implements Comparable<TreeNode> {
    public Integer data;
    public TreeNode left;
    public TreeNode right;

    // optional parameters - add only if required
    public TreeNode parent; // Interviewer might say that u r allowed to use parent, then only u can use this
    public boolean isRoot;
    public String color;

    public TreeNode(int data) {
        this.data = data;
    }

    public TreeNode(int data, TreeNode left, TreeNode right, TreeNode parent) {
        this(data);
        setLeft(left);
        setRight(right);
        setParent(parent);
    }

    public Integer getData() {
        return data;
    }

    public void setData(Integer data) {
        this.data = data;
    }

    public boolean isLeafNode() {
        return left == null && right == null;
    }

    public boolean amILeftChildOfMyParent() {
        if(hasParent()) {
            return getParent().getLeft() == this;
        }
        return false;
    }

    public boolean amIRightChildOfMyParent() {
        if(hasParent()) {
            return getParent().getRight() == this;
        }
        return false;
    }

    public TreeNode getLeft() {
        return left;
    }

    public void setLeft(TreeNode left) {
        this.left = left;
    }

    public TreeNode getRight() {
        return right;
    }

    public void setRight(TreeNode right) {
        this.right = right;
    }
    public boolean hasLeft() {
        return getLeft() != null;
    }

    public boolean hasRight() {
        return getRight() != null;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isRoot() {
        return isRoot;
    }

    public void setIsRoot(boolean isRoot) {
        this.isRoot = isRoot;
    }

    public boolean hasParent() {
        return parent != null;
    }
    public TreeNode getParent() {
        return parent;
    }

    public void setParent(TreeNode parent) {
        this.parent = parent;
    }

    public boolean hasOnlyOneChild() {
        return (left != null && right == null) || (left == null && right != null) ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TreeNode treeNode = (TreeNode) o;

        return data != null ? data.equals(treeNode.data) : treeNode.data == null;
    }

    @Override
    public int hashCode() {
        return data != null ? data.hashCode() : 0;
    }

    @Override
    public String toString() {
        return data+"";
    }

    @Override
    public int compareTo(TreeNode o) {
        if(this.data == o.data) return 0;
        if(this.data < o.data) return -1;
        return 1;
    }
}
