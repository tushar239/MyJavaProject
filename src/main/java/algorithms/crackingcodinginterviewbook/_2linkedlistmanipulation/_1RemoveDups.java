package algorithms.crackingcodinginterviewbook._2linkedlistmanipulation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

// P.g. 208 of Cracking Coding Interview book
public class _1RemoveDups {
    public static void main(String[] args) {
        {
            SinglyLinkedList linkedList = SinglyLinkedList.createLinkedListOfIntegers(new ArrayList<Integer>() {{
                add(5);
                add(3);
                add(5);
                add(7);
                add(2);
                add(1);
                add(7);
            }});
            System.out.println("Before removing dups: " + linkedList.toString());
            removeDupsWithoutUsingBuffer(linkedList);
            System.out.println("After removing dups: " + linkedList.toString());
        }

        {
            SinglyLinkedList linkedList = SinglyLinkedList.createLinkedListOfIntegers(new ArrayList<Integer>() {{
                add(5);
                add(3);
                add(5);
                add(7);
                add(2);
                add(1);
                add(7);
            }});
            System.out.println("Before removing dups: " + linkedList.toString());
            removeDupsUsingBuffer(linkedList);
            System.out.println("After removing dups: " + linkedList.toString());
        }

    }

    // As you should not use linkedList.length() for linked lists, you can't use for loop. You have to use while loop.
    // .length() needs traversing of entire linked nilList.
    // There is another way that you can use. In SinglyLinkedList class, increase a length variable at the time of insertion and reduce it by 1 at the time of deletion of the node.
    // But for algorithms, it is safe to assume that we should not use .length()


    // If you are allowed to use a buffer (extra memory), you can keep the element in HashSet and checks whether an element already exists in HashSet. If it does then remove it.
    private static void removeDupsUsingBuffer(SinglyLinkedList linkedList) {
        if(linkedList == null || linkedList.head == null || linkedList.head.next == null) {
            return;
        }

        Set<Integer> uniqueElements = new HashSet<>(); // Using extra memory. In worst case (if there are no dups), it uses O(n) space.
        Node a = linkedList.head; // start from second element of the nilList
        Node prevOfA = null;

        while(a != null) {

            if(uniqueElements.contains(a.data)) {// set uses map. so, searching any element in set takes O(1)
                if(prevOfA != null) {
                    prevOfA.next = a.next;
                } else {
                    linkedList.head = a.next;
                }
            } else {
                uniqueElements.add(a.data);
                prevOfA = a;
            }

            a = a.next;
        }

    }
    // If buffer is not allowed to use, then you need to use two pointers. One traverses entire linked nilList and another one (runner) checks for duplication.
    private static void removeDupsWithoutUsingBuffer(SinglyLinkedList linkedList) {
        if(linkedList == null || linkedList.head == null || linkedList.head.next == null) {
            return;
        }
        Node a = linkedList.head;


        /*

         Runtime Complexity:
             Number of operations :

             (n-1)+(n-2)+(n-3)+...+1
             that is same as 1+2+3+.....+n-1
             if 1+2+3+.....+n = n(n+1)/2
             then 1+2+3+.....+n-1 = (n-1)n/2 --- replacing n by n-1 in above equation
             This is same as O(n^2)

         Space Complexity:

            Space requirement is some constant k. It doesn't reserve much extra space other than two pointers a and b.
        */
        while(a.next != null) { // runs n times
            Node b = a.next; // runner pointer
            Node prevOfB = a;
            while(b != null) {
                if(a.data != b.data) {
                    prevOfB = b;
                } else {
                    prevOfB.next = b.next;
                }
                b = b.next;
            }
            a = a.next;
        }
    }
}
