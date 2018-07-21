package algorithms._2linked_list.geeksforgeeks.medium;

import algorithms.crackingcodinginterviewbook._2linkedlistmanipulation.Node;

/*
    I have created this algorithm because it is used in CheckWhetherLinkedListIsPalindrome.java.
 */
public class _4CreateSubListFromLinkedList {

    public static void main(String[] args) {

        Node head = new Node(1);
        Node second = new Node(2);
        Node third = new Node(3);
        Node fourth = new Node(4);
        Node fifth = new Node(5);

        head.next = second;
        second.next = third;
        third.next = fourth;
        fourth.next = fifth;

        {
            Node subListHead = createSubList(head, third);
            System.out.println(subListHead);
        }
        {
            Node subListHead = createSubList(third);
            System.out.println(subListHead);
        }
    }

    public static Node createSubList(Node startNode, Node endNode) {

        Node subListHead = new Node(startNode.data);

        Node subListRunner = subListHead;

        while (startNode.next != endNode.next) {
            startNode = startNode.next;

            Node node = new Node(startNode.data);
            subListRunner.next = node;

            subListRunner = subListRunner.next;
        }

        return subListHead;
    }

    public static Node createSubList(Node startNode) {

        Node subListHead = new Node(startNode.data);

        Node subListRunner = subListHead;

        while (startNode.next != null) {
            startNode = startNode.next;

            Node node = new Node(startNode.data);
            subListRunner.next = node;

            subListRunner = subListRunner.next;
        }

        return subListHead;
    }
}
