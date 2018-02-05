package algorithms.crackingcodinginterviewbook._2linkedlistmanipulation;

/**
 * @author Tushar Chokshi @ 8/22/15.
 */
public class Node implements Cloneable {
    public int data;
    public Node next;

    public Node(int data) {
        this.data = data;
    }

    public Node(Node b) {
        this.data = b.data;
        this.next = b.next;
    }

    public Node() {

    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    @Override
    public Node clone() throws CloneNotSupportedException {
        return (Node)super.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        return data == node.data;
    }

    @Override
    public int hashCode() {
        return data;
    }

    @Override
    public String toString() {
        return "Node{" +
                "data=" + data +
                ", next=" + next +
                '}';
    }
}
