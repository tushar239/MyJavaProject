package algorithms._2linkedlistmanipulation;

// pg 224 of Cracking Coding Interview book
public class DetectIfLinkedListHasALoop {

    static class SinglyLinkedListWithoutToString extends SinglyLinkedList {
        @Override
        public String toString() {
            return "";
        }
    }

    public static void main(String[] args) {
        Node node1 = new Node(3);
        Node node2 = new Node(1);
        Node node3 = new Node(5);
        Node node4 = new Node(9);
        Node node5 = new Node(7);
        Node node6 = new Node(2);
        Node node7 = new Node(0);
        //node7.next = node4; // creating a loop

        SinglyLinkedListWithoutToString linkedList = new SinglyLinkedListWithoutToString();
        linkedList.addToTail(node1);
        linkedList.addToTail(node2);
        linkedList.addToTail(node3);
        linkedList.addToTail(node4);
        linkedList.addToTail(node5);
        linkedList.addToTail(node6);
        linkedList.addToTail(node7);

        node7.next = node5; // creating a loop

        Node collisionNode = detectLoopInLinkedList(linkedList);
        System.out.println("Collision Node: "+ (collisionNode != null?collisionNode.data:null));
    }

    private static Node detectLoopInLinkedList(SinglyLinkedList linkedList) {
        Node slow = linkedList.head;
        Node fast = linkedList.head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                break;
            }
        }
        if (fast == null || fast.next == null) {
            return null;
        }

        slow = linkedList.head;

        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }

        return fast;
    }
}
