package algorithms.crackingcodinginterviewbook._2linkedlistmanipulation;

import java.util.ArrayList;

/**
 * @author Tushar Chokshi @ 12/26/15.
 */
// p.g. 212 of Cracking Coding Interview book.
// partition the linkedList in such a way that all nodes < selected node comes before selected node and
// all nodes greater than selected node comes after selected node
public class _4PartitionLinkedListFromSomeNode {
    public static void main(String[] args) {
        SinglyLinkedList linkedList = SinglyLinkedList.createLinkedListOfIntegers(new ArrayList<Integer>() {{
            add(1);
            add(3);
            add(7);
            add(2); //partition from this value
            add(5);
        }});
        System.out.println("Before partition: " + linkedList.toString());

        System.out.println("Easy way partition.....");
        int data = 2;
        partition(linkedList, data);
        System.out.println("After partition: " + linkedList.toString());

        System.out.println("Quick Sort Way Partition.....");
        partitionQuickSortWay(linkedList, new Node(data));
        System.out.println("After partition: " + linkedList.toString());
    }

    /*
    Good Approach
    -------------
    put selected element in the front of the list.

    5 - 2 - 7 - 3 - 1
      selected

    2 - 5 - 7 - 3 - 1
        a

    Repeat below steps till you reach at the end of the list.
        - compare each element (a) with selected element.
        - If a <= selected element, put it in the front of the list

    Another approach
    ----------------
    Quick Sort : see partitionQuickSortWay method

    Not so good approach
    --------------------

    You can do this way also, but it is very inefficient as the same nodes can be visited twice.

    5 - 2 - 7 - 3 - 1
             selected

    Till you reach 'selected' node, if you find any element > selected, move that node next to selected node.

    2 - 7 - 3 - 5 - 1
         selected

    2 - 3 - 7 - 5 - 1
     selected

    once you cross 'selected', if you find any element < selected, you need that element in the front of the list

    2 - 3 - 7 - 5 - 1
     selected

    If you see, nodes 7 and 5 will be visited twice, which is inefficient.


    Remember:
    ---------
    Design your linkedlist based algorithms in such a way that you don't have to add the nodes in the middle or at the end.
    First approach satisfies this condition.

     */
    private static void partition(SinglyLinkedList linkedList, int data) {
        if (linkedList == null || linkedList.head == null || linkedList.head.next == null) { // size is <=2
            return;
        }

        // e.g. linkedList = 1 3 7 2 5  and passed data=2

        // move pointer a to the node of interest (node having data same as passed data)
        Node a = linkedList.head;
        while (a.data != data) {
            a = a.next;
            if (a == null) {
                return; // passed data doesn't exist in the nilList
            }
        }

        // a points to node having data=2 at this point

        // remove a (2) from its location and put it at the beginning in linked nilList
        linkedList.delete(a);     // linkedList = 1 3 7 5
        linkedList.addToFront(a); // linkedList = 2 1 3 7 5

        // point b to linkedList.head.next (next to a node. a node is head now.)
        Node b = linkedList.head.next; // b = 5

        while (b != null) {
            if (b.data < data) {
                // linkedList = 2   1   3   7   5
                //                  b
                //                  temp
                Node temp = b;

                // linkedList = 2   1   3   7   5
                //                      b
                //                  temp
                b = b.next;

                // remove temp (1) from linked nilList

                // linkedList = 2   3   7   5
                //                  b
                linkedList.delete(temp);

                // add temp (1) as head
                // linkedList = 1   2   3   7   5
                //              head    b
                linkedList.addToFront(temp);
            } else {
                b = b.next;
            }
        }
    }

    /*
    Remember:

    In this way, I have tried to use Quick Sort.
    If you want to sort entire linkedlist, then Quick Sort is not a suggested way.
    Quick Sort is good for indexed array because it constantly keeps exchanging elements based on their indices.
    The same is very root_to_leaf_problems_hard to do with Singly Linked List because you need to keep track of prev node.
    So, if you want to sort entire linked list, then suggested approach is merge sort.
    see SortLinkedList.java


    Another way:
    Think like Quick Sort. In Quick Sort, there is a pivot. Here, your pivot is 'selected element(2)'.
    You put pivot at the end of the list.

    5 - 2 - 7 - 3 - 1

  pIndex
    5 - 7 - 3 - 1 - 2
  start            pivot


  Here, pivot is not > start
  So, just increase start

      pIndex
        5 - 7 - 3 - 1 - 2
            start      pivot

      pIndex
        5 - 7 - 3 - 1 - 2
               start  pivot

      pIndex
        5 - 7 - 3 - 1 - 2
                  start pivot

  Here, pivot > start
  So, exchange start and pIndex and increase both start and pIndex

      pIndex
    1 - 7 - 3 - 5 - 2
                   pivot
                   start

      pIndex
    1 - 7 - 3 - 5 - 2
                   pivot
                   start

    When start reaches at the end of list, exchange pIndex and pivot.

      pIndex
    1 - 2 - 3 - 5 - 7
                   pivot
                   start

In Quick Sort, you sort left of pIndex and right of pIndex recursively.
Here, you don't need to do that.

    */

    private static void partitionQuickSortWay(SinglyLinkedList LL, Node selectedForPivot) {
        if (LL.head == null || LL.head.next == null) return;

        Node a = LL.head;
        Node prevOfA = null;

        // Total Runtime of this while loop + if condition's while loop is O(n)
        while (!a.equals(selectedForPivot) || a == null) {
            prevOfA = a;
            a = a.next;
        }

        if (a != null) { // pivot element found. putting it at the end of list

            Node pivotNode = a;
            prevOfA.next = a.next;// DLink pivot node from list


            while (a.next != null) { // Go at the end of list
                a = a.next;
            }
            a.next = pivotNode; // put pivot node at the end of the list
            a.next.next = null;

            Node pIndexNode = LL.head;
            Node startNode = LL.head;

            Node prevOfStartNode = null;
            Node prevOfPIndexNode = null;

            Node prevOfPivotNode = a;

            while (startNode != null) {
                if (pivotNode.getData() > startNode.getData()) {
                    exchange(LL, startNode, pIndexNode, prevOfStartNode, prevOfPIndexNode);

                    prevOfStartNode = startNode;
                    startNode = startNode.next;

                    prevOfPIndexNode = pIndexNode;
                    pIndexNode = pIndexNode.next;

                } else {
                    prevOfStartNode = startNode;
                    startNode = startNode.next;
                }
            }

            exchange(LL, pIndexNode, pivotNode, prevOfPIndexNode, prevOfPivotNode);


        }

    }

    private static void exchange(SinglyLinkedList LL, Node node1, Node node2, Node prevOfNode1, Node prevOfNode2) {

        Node nextOfNode1 = node1.next;
        Node nextOfNode2 = node2.next;

        if (prevOfNode1 != null) {
            prevOfNode1.next = node2;
            node2.next = nextOfNode1;
        } else {
            LL.head = node2;
            node2.next = nextOfNode1;
        }
        //node1.next = null;


        if (prevOfNode2 != null) {
            prevOfNode2.next = node1;
            node1.next = nextOfNode2;
        } else {
            LL.head = node1;
            node1.next = nextOfNode2;
        }

    }

}
