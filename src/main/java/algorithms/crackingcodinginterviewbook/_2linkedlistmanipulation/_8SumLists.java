package algorithms.crackingcodinginterviewbook._2linkedlistmanipulation;

import java.util.ArrayList;

// p.g. 214 of Cracking Coding Interview book
// sumIteratively two linked lists data
public class _8SumLists {
    public static void main(String[] args) {
        SinglyLinkedList linkedList1 = SinglyLinkedList.createLinkedListOfIntegers(new ArrayList<Integer>() {{
            add(7);
            add(1);
            add(6);
        }});
        SinglyLinkedList linkedList2 = SinglyLinkedList.createLinkedListOfIntegers(new ArrayList<Integer>() {{
            add(5);
            add(1);
            add(5);
        }});

        SinglyLinkedList linkedListWithSums = reverseAndSum_1(linkedList1, linkedList2);
        System.out.println(linkedListWithSums);
        String sum = "";
        while (linkedListWithSums.head != null) {
            sum += linkedListWithSums.pop().data;
        }
        System.out.println("Sum:" + sum);
    }

    /*
        7->1->6
        +
        5->1->5
        -------
     1->2->3->1

        you add 6 and 5=11. keep 1 and and pass 1 to next step.
        add 1 and 1 and passed 1 from first step=3
        add 7 and 2=9

        So to achieve this, you need to
        - reverse both the lists LL1 and LL2
        - instantiate sumList that will contain nodes as sum of LL1 and LL2 nodes
        - do below steps till both lists are reached at the end
            - start adding elements of both lists
            - Calculate Remainder and Quotient
                if(sum > 10) remainder = sum % 10, quotient = sum / 10; ----- don't do this, if sum < 10
                else remainder=0

                if(remainder > 0) sum = remainder;

            - add sum to head of sumList
        - if (quotient > 0) add quotient to head of sumList


        sumList = SinglyLinkedList{head=Node{data=1, next=Node{data=1, next=Node{data=3, next=Node{data=1, next=null}}}}}
        finalSum=1231

     */
    private static SinglyLinkedList reverseAndSum_1(SinglyLinkedList linkedList1, SinglyLinkedList linkedList2) {
        _0ReverseLinkedList.inPlaceReverseDifferentWay(linkedList1, linkedList1.head);
        _0ReverseLinkedList.inPlaceReverseDifferentWay(linkedList2, linkedList2.head);
        //return Integer.valueOf(sum(linkedList1.head, linkedList2.head, ""));
        SinglyLinkedList linkedListOfSums = sum_1(linkedList1, linkedList2);
        return linkedListOfSums;
    }

    private static SinglyLinkedList sum_1(SinglyLinkedList LL1, SinglyLinkedList LL2) {
        Node a = LL1.head;
        Node b = LL2.head;

        SinglyLinkedList LLOfSums = new SinglyLinkedList();

        int remainder = 0;
        int quotient = 0;

        while (a != null && b != null) {
            int sum = 0;
            if (a != null) sum += a.data;
            if (b != null) sum += b.data;
            sum += remainder;

            // you need to remember how to calculate a Remainder and what to do if Remainder > 0
            if (sum > 10) {// if sum < 10, then remainder will be same as sum (e.g. 9 % 10 = 9). We don't want that. We want 12 % 10 = 2.
                remainder = sum % 10;
                quotient = sum / 10; // 12 / 10 = 1
            }
            else remainder = 0;

            if (remainder > 0) {
                sum = remainder;
            }

            LLOfSums.addAsHead(sum);

            if (a != null) a = a.next;
            if (b != null) b = b.next;
        }

        if (remainder > 0) LLOfSums.addAsHead(quotient);

        return LLOfSums;
    }
}
