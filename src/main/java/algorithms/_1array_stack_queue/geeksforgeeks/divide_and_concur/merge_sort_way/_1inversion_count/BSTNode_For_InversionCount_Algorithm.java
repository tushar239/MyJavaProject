package algorithms._1array_stack_queue.geeksforgeeks.divide_and_concur.merge_sort_way._1inversion_count;

public class BSTNode_For_InversionCount_Algorithm {
    private int value;
    private BSTNode_For_InversionCount_Algorithm leftChild, rightChild;
    private int numberOfChildren;
    private int inversionCount;

    public BSTNode_For_InversionCount_Algorithm(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getNumberOfChildren() {
        return numberOfChildren;
    }

    public void setNumberOfChildren(int numberOfChildren) {
        this.numberOfChildren = numberOfChildren;
    }

    public BSTNode_For_InversionCount_Algorithm getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(BSTNode_For_InversionCount_Algorithm leftChild) {
        this.leftChild = leftChild;
    }

    public BSTNode_For_InversionCount_Algorithm getRightChild() {
        return rightChild;
    }

    public void setRightChild(BSTNode_For_InversionCount_Algorithm rightChild) {
        this.rightChild = rightChild;
    }
    public void incrementNumberOfChildren() {
        numberOfChildren++;
    }

    public int getInversionCount() {
        return inversionCount;
    }

    public void setInversionCount(int inversionCount) {
        this.inversionCount = inversionCount;
    }

    public void incrementInversionCount(int increasedNumber) {
        this.inversionCount += increasedNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BSTNode_For_InversionCount_Algorithm that = (BSTNode_For_InversionCount_Algorithm) o;

        if (value != that.value) return false;
        if (numberOfChildren != that.numberOfChildren) return false;
        if (leftChild != null ? !leftChild.equals(that.leftChild) : that.leftChild != null) return false;
        return rightChild != null ? rightChild.equals(that.rightChild) : that.rightChild == null;
    }

    @Override
    public int hashCode() {
        int result = value;
        result = 31 * result + numberOfChildren;
        result = 31 * result + (leftChild != null ? leftChild.hashCode() : 0);
        result = 31 * result + (rightChild != null ? rightChild.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BinarySearchTreeNode{" +
                "value=" + value +
                ", numberOfChildren=" + numberOfChildren +
                ", leftChild=" + leftChild +
                ", rightChild=" + rightChild +
                '}';
    }
}
