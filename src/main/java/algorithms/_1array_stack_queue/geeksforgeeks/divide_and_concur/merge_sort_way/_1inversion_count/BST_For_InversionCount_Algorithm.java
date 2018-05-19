package algorithms._1array_stack_queue.geeksforgeeks.divide_and_concur.merge_sort_way._1inversion_count;

public class BST_For_InversionCount_Algorithm {
    private BSTNode_For_InversionCount_Algorithm root;

    public BST_For_InversionCount_Algorithm(int data) {
        this(new BSTNode_For_InversionCount_Algorithm(data));
    }

    public BST_For_InversionCount_Algorithm(BSTNode_For_InversionCount_Algorithm root) {
        this.root = root;
    }

    public BSTNode_For_InversionCount_Algorithm getRoot() {
        return root;
    }

    public BSTNode_For_InversionCount_Algorithm insert(int data) {
        BSTNode_For_InversionCount_Algorithm newNode = new BSTNode_For_InversionCount_Algorithm(data);
        return insert(root, newNode);
    }

    private BSTNode_For_InversionCount_Algorithm insert(BSTNode_For_InversionCount_Algorithm root, BSTNode_For_InversionCount_Algorithm newNode) {
        if (newNode.getValue() < root.getValue()) {
            if (root.getLeftChild() == null) {
                root.setLeftChild(newNode);
                root.incrementNumberOfChildren();
                return newNode;
            }
            root.incrementNumberOfChildren();
            return insert(root.getLeftChild(), newNode);
        } else if (newNode.getValue() > root.getValue()) {

            if (root.getRightChild() == null) {
                newNode.incrementInversionCount(1 + root.getNumberOfChildren());// important: setting inversionCount. There is "1 + " because you need to include root node also.
                root.incrementNumberOfChildren();// important: increment number of children after setting inversion count
                root.setRightChild(newNode);
                return newNode;
            }
            root.incrementNumberOfChildren();
            return insert(root.getRightChild(), newNode);
        }

        newNode.incrementInversionCount(root.getNumberOfChildren());// important to take care of duplicate elements
        return root;
    }

  /*  public int traverseAndGetTotalInversionCount() {
        return traverseAndGetTotalInversionCount(root);
    }

    private int traverseAndGetTotalInversionCount(BinarySearchTreeNode root) {
        if (root == null) return 0;

        return root.getInversionCount() +
                traverseAndGetTotalInversionCount(root.getLeftChild()) +
                traverseAndGetTotalInversionCount(root.getRightChild());
    }*/
}
