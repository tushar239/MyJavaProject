package algorithms.crackingcodinginterviewbook._2linkedlistmanipulation;

import java.util.ArrayList;

public class _0ReverseLinkedList {
    public static void main(String[] args) throws CloneNotSupportedException {

        {
            // This algorithm is with O(n) runtime complexity
            SinglyLinkedList linkedList = getSinglyLinkedList();

            System.out.println("Reverse linkedlist in-place");
            System.out.println("Before: " + linkedList.head.toString());
            Node newNode = inPlaceReverseDifferentWayWithBetterRuntimeComplexity(linkedList.head);
            System.out.println("After : " + newNode.toString());
        }
        System.out.println(".............");

        {
            // This algorithm is with O(n^2) runtime complexity
            SinglyLinkedList linkedList = getSinglyLinkedList();

            System.out.println("Reverse linkedlist in-place");
            System.out.println("Before: " + linkedList.toString());
            inPlaceReverseDifferentWay(linkedList, linkedList.head);
            System.out.println("After : " + linkedList.toString());
        }
        /*
        System.out.println(".............");
        // http://www.geeksforgeeks.org/rotate-a-linked-list/
        {
            SinglyLinkedList linkedList = getSinglyLinkedList();

            System.out.println("Reverse linkedlist in-place starting from some position");
            System.out.println("Before: " + linkedList.toString());
            inPlaceReverseDifferentWay(linkedList, 1); // startPosition starts from 0
            System.out.println("After : " + linkedList.toString());
        }
        System.out.println(".............");
        {
            SinglyLinkedList linkedList = getSinglyLinkedList();

            System.out.println("Reverse linkedlist in-place");
            System.out.println("Before: " + linkedList.toString());
            inPlaceReverse(linkedList, linkedList.head);
            System.out.println("After : " + linkedList.toString());
        }
        System.out.println(".............");
        {
            SinglyLinkedList linkedList = getSinglyLinkedList();
            System.out.println("Create new reversed linkedlist from original linkedlist");
            System.out.println("Before: " + linkedList.toString());
            SinglyLinkedList newLinkedList = createNewLinkedListWhileReverse(linkedList.head.clone());
            System.out.println("After : " + newLinkedList.toString());
        }
        System.out.println(".............");
        {
            // http://www.geeksforgeeks.org/swap-nodes-in-a-linked-list-without-swapping-data/
            SinglyLinkedList linkedList = getSinglyLinkedList();

            System.out.println("Swap nodes in linkedlist without swapping data");
            System.out.println("Before: " + linkedList.toString());
            swapNodesWithoutSwappingData(linkedList, 1, 3);
            System.out.println("After : " + linkedList.toString());
        }
        System.out.println(".............");

        // http://www.geeksforgeeks.org/merge-two-sorted-linked-lists-such-that-merged-list-is-in-reverse-order/
        {
            System.out.println("Merge two sorted linked lists such that merged nilList is in reverse order");

                //reverse list1
                //reverse list2
                //pop second nilList's node one by one and deleteRootAndMergeItsLeftAndRight it to first nilList

            SinglyLinkedList linkedList1 = SinglyLinkedList.createLinkedListOfIntegers(new ArrayList<Integer>() {{
                add(0);
                add(3);
                add(4);
                add(6);
                add(9);
            }});
            SinglyLinkedList linkedList2 = SinglyLinkedList.createLinkedListOfIntegers(new ArrayList<Integer>() {{
                add(1);
                add(2);
                add(3);
                add(5);
                add(7);
            }});

            mergeTwoSortedListsAndReverseIt(linkedList1, linkedList2);

            System.out.println("After merging: " + linkedList1.toString());
        }
        // http://www.geeksforgeeks.org/compare-two-strings-represented-as-linked-lists/
        {
            System.out.println("Compare two strings represented as linked lists");
            SinglyLinkedList linkedList1 = SinglyLinkedList.createLinkedListOfChars(new ArrayList<Character>() {{
                add('a');
                add('b');
                add('c');
                add('d');
                add('e');
            }});
            SinglyLinkedList linkedList2 = SinglyLinkedList.createLinkedListOfChars(new ArrayList<Character>() {{
                add('a');
                add('b');
                add('c');
                add('d');
                add('e');
            }});
            System.out.println(compareTwoLikedListsInStringFormat(linkedList1, linkedList2));
        }
        //System.out.println(myRecursiveMethod(10));
    */
    }

    private static SinglyLinkedList getSinglyLinkedList() {
        return SinglyLinkedList.createLinkedListOfIntegers(new ArrayList<Integer>() {{
            add(5);
            add(3);
            add(7);
            add(2);
            add(1);
        }});
    }

    private static int compareTwoLikedListsInStringFormat(SinglyLinkedList linkedList1, SinglyLinkedList linkedList2) {
        Node l1Node = linkedList1.head;
        Node l2Node = linkedList2.head;

        while (true) {
            if (l1Node != null && l2Node == null) {
                return 1;
            } else if (l1Node == null && l2Node != null) {
                return -1;
            } else if (l1Node == null && l2Node == null) {
                return 0;
            } else {
                int compareResult = new Integer(l1Node.data).compareTo(new Integer(l2Node.data));
                if (compareResult == 0) {
                    l1Node = l1Node.next;
                    l2Node = l2Node.next;
                    continue;
                }
                return compareResult;
            }
        }
    }

    protected static void mergeTwoSortedListsAndReverseIt(SinglyLinkedList linkedList1, SinglyLinkedList linkedList2) {
        System.out.println("Before reversal l1 : " + linkedList1.toString());
        System.out.println("Before reversal l2 : " + linkedList2.toString());

        inPlaceReverse(linkedList1, linkedList1.head);
        inPlaceReverse(linkedList2, linkedList2.head);

        System.out.println("After reversal l1 : " + linkedList1.toString());
        System.out.println("After reversal l2 : " + linkedList2.toString());

        Node l1Node = linkedList1.head;
        Node prevL1Node = null;

        Node l2Node = linkedList2.pop(); // pop first node from list2

        while (l2Node != null) {

            if (l2Node.data >= l1Node.data) {// if list2's node >= list1's node, insert list2's node in list1
                // insert l2Node in list1
                if (prevL1Node != null) { // l1Node is not a head node
                    prevL1Node.next = l2Node;
                }
                l2Node.next = l1Node;

                // next traversal should start from the inserted node
                l1Node = l2Node;

                // insertion of previous list2 popped node is successful. So, pop next node and continue.
                l2Node = linkedList2.pop();

            } else { // keep traversing through list1

                prevL1Node = l1Node;
                l1Node = l1Node.next;
            }

        }
    }


    /*
    original linkedlist
    1->2->7->3->5
    you want to reverse it.

    First think whether reversal can be done recursively or not.
    How do you think that? --- Think whether you can reverse a part of partial input data and then do something in between that reversed partial data and remaining data by merging them somehow.
                               If the answer is yes, then you can use recursive method.


    Recursion Technique - Minimize the problem by one. So, instead of thinking to reverse entire linkedlist, think you want to reverse 2->7->3->5, which will be a recursive method call.
                          Assume that 2->7->3->5 will be reversed through recursive method call, so after it is reversed (5->3->7->2) you just need to put 1 at the end of linked nilList.

    Case 1 -
    Node reverse(1, 1->2->7->3->5) {
        Node next = 1.next = 2; // you need to keep this because when you reverse remaining LL, last element of that will be 2

        1.next = null;  // separate first element from remaining list

        Node headOfRemainingReversedLL = reverse(next --- 2->7->3->5) - Assume that this reverse method returns last node after reversing. So, reversed linkedlist is 5->3->7->2. it returns 5.

        next.next = 1;

        return 1; - reverse method expects you to return a lastNode. lastNode became 1 now, so return 1.
    }

    Case 2 -
    In many cases, to minimize the problem by one, you can not use first element always. Sometimes you need to use middle element also.
    When your input is array and if you need use some in between element to minimize the problem by 1, then its better to pass array, start and end positions of array. start and end positions can be used to determine a position of an element that you want to use to minimize the overall problem by one.
    e.g. Merge Sort, Binary Search, creating minimal BST (CreateMinimalBST.java - p.g. 242 of Cracking Coding Interview Book).

    Case 3 -
    In case of CreateMinimalBST.java, you need the result from both left and right side of the node and combine that result with the node.
    In case of BST.java's get() method, you need the result either from left side or right side, whichever is matched first that becomes final result. In this scenario, you need to use a temporary variable 'found***' when the result is matched. It will help you to decide not to execute other side, if one side is matched.
    This is also an example of not combining extracted node (root - that is used for minimizing problem by one) with recursive method calls result.

    */

    /*
    It is is easy to think recursive when you want to reverse a Linked List.

    1---->2---->3

    Reducing the problem by 1.

   head    newHeadNode
    1       3--------->2
    |                  ^
    |                  |
    --------------------

    after recursive call, just do

    head.next.next = head
    head.next = null
    head = newHeadNode

    */
    private static Node inPlaceReverseDifferentWayWithBetterRuntimeComplexity(Node head) {
        if (head == null) return head;

        if (head.next == null) return head;

        Node newHead = inPlaceReverseDifferentWayWithBetterRuntimeComplexity(head.next);

        head.next.next = head;
        head.next = null;

        head = newHead;

        return head;
    }

    /*
    Runtime complexity:
    while loop starts when you start popping method call from stack.
    list will be traversed 1 + 2 + 3 + 4 + ......+ n-1 times
    1+2+3+.....+n = n(n+1)/2
    So,
    1+2+3+.....+(n-1) = (n-1)n/2   ---- replacing n by n-1 in n(n+1)/2
    That is O(n^2)

    How can you improve it to O(n)?

     */

    private static Node inPlaceReverseDifferentWay(Node head) {
        if (head == null || head.next == null) {
            return head;
        }
        Node headOfRemainingReversedLL = inPlaceReverseDifferentWay(head.next);
        Node runner = headOfRemainingReversedLL;

        while (runner.next != null)
            runner = runner.next; // how can you avoid this to get away from O(n^2) runtime complexity?

        runner.next = head;
        head.next = null;
        return headOfRemainingReversedLL;
    }

    // Improving above approach's O(n^2) to O(n)
    public static Node inPlaceReverseDifferentWay(SinglyLinkedList linkedList, Node head) {
        if (head.next == null) {
            linkedList.head = head;
            return head;
        }
        Node lastNode = inPlaceReverseDifferentWay(linkedList, head.next);
        lastNode.next = head;
        head.next = null;
        return head;
    }


    static void inPlaceReverseDifferentWay(SinglyLinkedList linkedList, int startPosition) {
        if (startPosition < 0) {
            startPosition = 0;
        }

        Node initialHead = linkedList.head;
        Node startingNode = linkedList.head;
        Node prevNode = null;
        int count = 0;
        while (count < startPosition) {
            prevNode = startingNode;
            startingNode = startingNode.next;
            if (startingNode == null) {
                return;
            }
            count++;
        }

        inPlaceReverseDifferentWay(linkedList, startingNode);

        if (prevNode != null) {
            prevNode.next = linkedList.head;
        }

        if (startPosition > 0) {
            linkedList.head = initialHead;
        }

    }


    static void inPlaceReverse(SinglyLinkedList linkedList, Node node) {
        if (node.next == null) {
            linkedList.head = node;
            return;
        }

        Node prevNode = node;
        Node currentNode = node.next;

        inPlaceReverse(linkedList, currentNode);

        currentNode.next = prevNode;
        prevNode.next = null;

    }

    static SinglyLinkedList createNewLinkedListWhileReverse(Node node) throws CloneNotSupportedException {

        SinglyLinkedList newLinkedList = null;

        if (node.next == null) { // exit condition
            newLinkedList = new SinglyLinkedList();
            newLinkedList.head = node;
            return newLinkedList;
        }

        node.next = node.next.clone();

        newLinkedList = createNewLinkedListWhileReverse(node.next); // newLinkedList refers to same object assinged in exit condition

        node.next.next = node;
        node.next = null;

        return newLinkedList;
    }

    public static int myRecursiveMethod(int aVariable) {
        Integer someVariable = 0;
        System.out.println(aVariable);
        aVariable--;
        if (aVariable == 3) {
            someVariable = new Integer(aVariable);
            return someVariable;
        }
        someVariable = myRecursiveMethod(aVariable); // always same as someVariable assigned in exit condition (3)
        return someVariable;

    }

    static void swapNodesWithoutSwappingData(SinglyLinkedList linkedList, int data1, int data2) {
        if (data1 == data2) {
            return;
        }
        Node prevNodeOfData1 = null;
        Node nodeOfData1 = null;

        Node prevNodeOfData2 = null;
        Node nodeOfData2 = null;

        Node node = linkedList.head;
        Node prevNode = null;

        while (node != null) {

            if (node.data == data1) {
                prevNodeOfData1 = prevNode;
                nodeOfData1 = node;
            } else if (node.data == data2) {
                prevNodeOfData2 = prevNode;
                nodeOfData2 = node;
            }
            prevNode = node;
            node = node.next;


        }

        if (nodeOfData1 != null && nodeOfData2 != null) { // if both nodes found
            Node tempPrevNodeOfData2 = prevNodeOfData2;
            Node tempNextNodeOfData2 = nodeOfData2.next;

            prevNodeOfData1.next = nodeOfData2;
            nodeOfData2.next = nodeOfData1.next;

            tempPrevNodeOfData2.next = nodeOfData1;
            nodeOfData1.next = tempNextNodeOfData2;
        }

    }

}
