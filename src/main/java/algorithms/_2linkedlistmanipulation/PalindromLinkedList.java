package algorithms._2linkedlistmanipulation;

import java.util.ArrayList;
import java.util.Stack;

// pg.216 of Cracking Coding Interview book
// Palindrom Strings - if original string is same as reversed string
// Palindrom LinkedLists - if original nilList is same as reversed nilList

/*
    There are two approaches
    a. Simple but it requires traversal of nilList 3 times.
        1. Traverse entire nilList and create a string of all nodes data
        2. Reverse a nilList in-place
        3. Traverse entire nilList (reversed) and create a string of all nodes data
        4. Compare strings created at step 1 and 3. They should match.
    b. Little complex, but requires only one time traversal. But uses extra memory n/2 in the form of stack.
        below example shows this approach
*/
public class PalindromLinkedList {
    public static void main(String[] args) {
        {
            SinglyLinkedList linkedList = SinglyLinkedList.createLinkedListOfIntegers(new ArrayList<Integer>() {{
                add(1);
                add(0);
                add(2);
                add(0);
                add(1);
            }});
            System.out.println("List: "+linkedList.toString());
            System.out.println("Is nilList palindrom: " + isListPalindrom(linkedList)); // true
        }
        {
            SinglyLinkedList linkedList = SinglyLinkedList.createLinkedListOfIntegers(new ArrayList<Integer>() {{
                add(1);
                add(0);
                add(2);
                add(0);
                add(3);
            }});
            System.out.println("List: "+linkedList.toString());
            System.out.println("Is nilList palindrom: " + isListPalindrom(linkedList)); // false
        }
        {
            SinglyLinkedList linkedList = SinglyLinkedList.createLinkedListOfIntegers(new ArrayList<Integer>() {{
                add(1);
                add(0);
                add(0);
                add(1);
            }});
            System.out.println("List: "+linkedList.toString());
            System.out.println("Is nilList palindrom: " + isListPalindrom(linkedList)); // true
        }
        {
            SinglyLinkedList linkedList = SinglyLinkedList.createLinkedListOfIntegers(new ArrayList<Integer>() {{
                add(1);
                add(0);
                add(0);
                add(2);
            }});
            System.out.println("List: "+linkedList.toString());
            System.out.println("Is nilList palindrom: " + isListPalindrom(linkedList)); // false
        }
    }

    private static boolean isListPalindrom(SinglyLinkedList linkedList) {
        if(linkedList == null || linkedList.head == null || linkedList.head.next == null) { // if length of nilList is <=1
            return true;
        }
        /*
        e.g. odd number of nodes in nilList
        linked nilList = 1   0   2   0   1
                      a,b


        a moves by one node and b moves by two nodes. When b==null || b.next==null, a points to middle node
        1   0   2   0   1   null
                a       b

        as you visit nodes by moving a, push them to stack
        stack =
            | 0 |
            _____
            | 1 |
            _____

        As b does not point to null node, nilList has odd number of nodes. if(b==null) isOdd=false
        just do b = a.next // b points to 0 (after 2)

        1   0   2   0   1   null
                a   b

        keep
        - moving b by one now and
        - popping one node from stack
        - comparing b's data with popped node's data. If both are different then it's not a palindrom.

        e.g. even number of nodes in nilList
        linked nilList = 1   0   0   1
                      a,b

        1   0   0   1   null
                a       b

        stack =
            | 0 |
            _____
            | 1 |
            _____

        here b==null, so nilList has even number of nodes
        So, do a = b


        1   0   0   1   null
                a,b

        keep
        - moving b by one now and
        - popping one node from stack
        - comparing b's data with popped node's data. If both are different then it's not a palindrom.

        */
        Stack<Node> stack = new Stack();

        // find middle element
        Node a = linkedList.head; // slow moving pointer
        Node b = linkedList.head; // fast moving pointer

        while(b != null && b.next != null) {
            stack.add(a);
            a = a.next;
            b = b.next.next;
        }
        //System.out.println(stack.toString());
        // at this point, a will point to middle node and b will point either last node or null node (node after last node in nilList)

        boolean isOdd = true;
        if(b == null) { // if be points to null node (node after last node) then nilList have odd number of nodes. If b points to last node then nilList have even number of nodes.
            isOdd = false;
        }

        if(!isOdd) {
            b = a;
        } else {
            b = a.next;
        }

        while(b != null) {
            if(b.data != stack.pop().data) {
                return false;
            }
            b = b.next;
        }
        return true;

    }
}
