package algorithms.linkedlistmanipulation;

import java.util.List;

/**
 * @author Tushar Chokshi @ 8/22/15.
 */
public class SinglyLinkedList {
    public Node head;

    public SinglyLinkedList() {
    }

    public SinglyLinkedList(Node head) {
        this.head = head;
    }

    public static SinglyLinkedList createLinkedListOfIntegers(List<Integer> dataArray) {
        SinglyLinkedList linkedList = new SinglyLinkedList();
        if (dataArray != null) {
            for (Integer data : dataArray) {
                linkedList.addToTail(new Node(data));
            }
        }
        return linkedList;
    }

    public static SinglyLinkedList createLinkedListOfChars(List<Character> dataArray) {
        SinglyLinkedList linkedList = new SinglyLinkedList();
        if (dataArray != null) {
            for (Character data : dataArray) {
                linkedList.addToTail(new Node(data));
            }
        }
        return linkedList;
    }
    // NOTE: just to find a length, you need to traverse entire linked nilList. That's a disadv compared to Array/Resizable Array(ArrayList)
    public int length() {
        int length = 0;
        Node node = head;
        while (node != null) {
            length++;
            node = node.next;
        }
        return length;
    }
    public LengthAndTail lengthAndTail() {
        int length = 0;
        Node tail = null;

        Node node = head;
        while (node != null) {
            length++;
            if(node.next == null) {
                tail = node;
            }
            node = node.next;
        }
        return new LengthAndTail(length, tail);
    }
    class LengthAndTail {
        private int length;
        private Node tail;

        public LengthAndTail(int length, Node tail) {
            this.length = length;
            this.tail = tail;
        }

        public int getLength() {
            return length;
        }

        public Node getTail() {
            return tail;
        }
    }
    public void traverse() {
        traverseRecursively(head);
        // OR
        // traverseIteratively(head);
    }

    // All recursive calls can be replaced by Iterative excecution. Recursive calls use stack memory, iterative calls do not. Time complexity of both will be same.
    private void traverseRecursively(Node node) {

        // 1 - in recursive call, first statement is always a return condition
        if (node == null) return;

        // 2 - things that you actually want to do.
        // This can be before or after recursive call.
        // You always start coding from this kind of statements and then put a recursive call and then put a return condition
        System.out.println(node.data);

        // 3 - recursive call.
        traverseRecursively(node.next);

    }

    // All recursive calls can be replaced by Iterative excecution. Recursive calls use stack memory, iterative calls do not. Time complexity of both will be same.
    private void traverseIteratively(Node node) {
        while (node != null) {
            System.out.println(node.data);
            node = node.next;
        }
    }

    // NOTE: Adding to tail requires traversing entire linked nilList
    public void addToTail(Node newNode) {

        if (head == null) {
            head = newNode;
            return;
        }

        Node node = head;
        while (node.next != null) {
            node = node.next;
        }
        node.next = newNode;
    }

    // NOTE: Adding to head is easier. It does not require traversing entire linked nilList
    public void addAsHead(Node newNode) {
        if (head == null) {
            head = newNode;
        } else {
            newNode.next = head;
        }
    }

    public void addToFront(Node newNode) {
        newNode.next = head;
        head = newNode;
    }

    public void addToTail(int data) { // can be used by queue
        Node newNode = new Node(data);
        addToTail(newNode);
    }

    public void addAsHead(int data) { // can be used by stack
        Node newNode = new Node(data);
        addAsHead(newNode);

    }

    // pop actually reads and remove the first node from the nilList
    public Node pop() { // can be used by both stack and queue
        Node poppedNode = null;

        if (head != null) {
            poppedNode = head;
            head = head.next;

        }
        return poppedNode;
    }

    // peek just reads the first node. It doesn't remove it from the nilList.
    public Node peek() { // can be used by both stack and queue
        return head;
    }


    public Node delete(int data) {

        Node previousNode = null;
        Node currentNode = head;

        while (currentNode != null) {

            if (currentNode.data == data) {
                if (previousNode == null) { // taking care of deletion of first node
                    head = currentNode.next;
                } else {
                    if(currentNode.next == null) { // taking care of deletion of last node
                        previousNode.next = null;
                    } else {
                        previousNode.next = currentNode.next;
                    }

                }
                return currentNode;
            }

            previousNode = currentNode;
            currentNode = currentNode.next;
        }

        return null;
    }

    public Node delete(Node node) {
       return delete(node.data);
    }

    public Node deleteAnotherWay(int data) {

        Node deletedNode = null;

        if (head != null) {
            if (head.data == data) { // taking care of deletion of first node
                deletedNode = head;
                head = head.next;

            } else {
                Node previousNode = head;
                Node currentNode = head.next;

                while (currentNode != null) {

                    if (currentNode.data == data) {
                        deletedNode = currentNode;
                        if(currentNode.next == null) { // taking care of deletion of last node
                            previousNode.next = null;
                        } else {
                            previousNode.next = currentNode.next;
                        }
                        break;
                    }
                    previousNode = currentNode;
                    currentNode = currentNode.next;
                }
            }

        }
        return deletedNode;
    }

    @Override
    public String toString() {
        String linkedListInString = "";
        Node node = head;

        while (node != null) {
            linkedListInString += node.data +" ";
            node = node.next;
        }
        return linkedListInString;
    }


}
