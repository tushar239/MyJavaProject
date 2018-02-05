package algorithms.crackingcodinginterviewbook._2linkedlistmanipulation;

// pg 224 of Cracking Coding Interview book
public class _7DetectIfLinkedListHasALoop {

    static class SinglyLinkedListWithoutToString extends SinglyLinkedList {
        @Override
        public String toString() {
            return "";
        }
    }

    public static void main(String[] args) {
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);
        Node node6 = new Node(6);

        node1.setNext(node2);
        node2.setNext(node3);
        node3.setNext(node4);
        node4.setNext(node5);
        node5.setNext(node6);
        node6.setNext(node3);// creating a loop

        SinglyLinkedList linkedList = new SinglyLinkedList(node1);

        Node collisionNode = detectLoopInLinkedList(linkedList);
        System.out.println("Collision Node: " + (collisionNode != null ? collisionNode.data : null));
    }

    /*
    To summarize, we move FastPointer twice as fast as SlowPointer.
    When SlowPointer enters the loop, after k nodes, FastPointer is k nodes into the linked list.
    This means that FastPointer and SlowPointer are LOOP_SIZE-k nodes away from each other.

    Next, if FastPointer moves two nodes for each node that SlowPointer moves two nodes for each node that SlowPointer moves,
    they move one node closer to each other on each turn. Therefore, they will meet after LOOP_SIZE-k turns.
    Both will be k nodes from the front of the loop.

    Steps of algorithm:
    - Create tow runners(pointers), FastPointer and SlowPointer.
    - Move FastPointer at a rate of 2 steps and SlowPointer at a rate of 1 step.
    - When they collide, move SlowPointer to the Head of LinkedList. Keep FasterPointer where it is.
    - Move SlowPointer and FastPointer at a rate of one step.
    - Return the new collision point.

    It is important to check that there is actually a loop. If FastPointer==null or FastPointer.next==null at any point, then there is no Loop.

     */
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

        // if there is no loop, then this condition will be true.
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
