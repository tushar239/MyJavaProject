package algorithms.crackingcodinginterviewbook._2linkedlistmanipulation;

import java.util.ArrayList;
import java.util.Stack;

// pg.216 of Cracking Coding Interview book
// Palindrom Strings - if original string is same as reversed string
// Palindrom LinkedLists - if original linkedlist is same as reversed linkedlist

/*
    There are two approaches
    a. Simple but it requires traversal of linkedlist 3 times.
        1. Traverse entire linkedlist and create a string of all nodes data
        2. Reverse a linkedlist in-place
        3. Traverse entire linkedlist (reversed) and create a string of all nodes data
        4. Compare strings created at step 1 and 3. They should match.
    b. Little complex, but requires only one time traversal. But uses extra memory n/2 in the form of stack.
        below example shows this approach
*/
public class _5PalindromeLinkedList {
    public static void main(String[] args) {
        {
            SinglyLinkedList linkedList = SinglyLinkedList.createLinkedListOfIntegers(new ArrayList<Integer>() {{
                add(1);
                add(0);
                add(2);
                add(0);
                add(1);
            }});
            System.out.println("List: " + linkedList.toString());
            System.out.println("Is linkedlist palindrome: " + isPalindrome(linkedList)); // true
            System.out.println("Is linkedlist palindrome: " + isPalindrome_testAgain(linkedList)); // true
        }
        {
            SinglyLinkedList linkedList = SinglyLinkedList.createLinkedListOfIntegers(new ArrayList<Integer>() {{
                add(1);
                add(0);
                add(2);
                add(0);
                add(3);
            }});
            System.out.println("List: " + linkedList.toString());
            System.out.println("Is linkedlist palindrome: " + isPalindrome(linkedList)); // false
            System.out.println("Is linkedlist palindrome: " + isPalindrome_testAgain(linkedList)); // false
        }
        {
            SinglyLinkedList linkedList = SinglyLinkedList.createLinkedListOfIntegers(new ArrayList<Integer>() {{
                add(1);
                add(0);
                add(0);
                add(1);
            }});
            System.out.println("List: " + linkedList.toString());
            System.out.println("Is linkedlist palindrome: " + isPalindrome(linkedList)); // true
            System.out.println("Is linkedlist palindrome: " + isPalindrome_testAgain(linkedList)); // true

        }
        {
            SinglyLinkedList linkedList = SinglyLinkedList.createLinkedListOfIntegers(new ArrayList<Integer>() {{
                add(1);
                add(0);
                add(0);
                add(2);
            }});
            System.out.println("List: " + linkedList.toString());
            System.out.println("Is linkedlist palindrome: " + isPalindrome(linkedList)); // false
            System.out.println("Is linkedlist palindrome: " + isPalindrome_testAgain(linkedList)); // false
        }
    }

    private static boolean isPalindrome(SinglyLinkedList linkedList) {
        if (linkedList == null || linkedList.head == null || linkedList.head.next == null) { // if length of linkedlist is <=1
            return true;
        }
        /*
        e.g. odd number of nodes in linkedlist
        linked linkedlist = 1   0   2   0   1
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

        As b does not point to null node, linkedlist has odd number of nodes. if(b==null) isOdd=false
        just do b = a.next // b points to 0 (after 2)

        1   0   2   0   1   null
                a   b

        keep
        - moving b by one now and
        - popping one node from stack
        - comparing b's data with popped node's data. If both are different then it's not a palindrom.

        e.g. even number of nodes in linkedlist
        linked linkedlist = 1   0   0   1
                      a,b

        1   0   0   1   null
                a       b

        stack =
            | 0 |
            _____
            | 1 |
            _____

        here b==null, so linkedlist has even number of nodes
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

        while (b != null && b.next != null) {
            stack.add(a);
            a = a.next;
            b = b.next.next;
        }
        //System.out.println(stack.toString());
        // at this point, a will point to middle node and b will point either last node or null node (node after last node in linkedlist)

        boolean isOdd = true;
        if (b == null) { // if be points to null node (node after last node) then linkedlist have odd number of nodes. If b points to last node then linkedlist have even number of nodes.
            isOdd = false;
        }

        if (!isOdd) {
            b = a;
        } else {
            b = a.next;
        }

        while (b != null) {
            if (b.data != stack.pop().data) {
                return false;
            }
            b = b.next;
        }
        return true;

    }

    /*
        LinkedList of odd size

            1->0->2->0->1
           slow
           fast

            stack.put(1);

            fast runner goes two steps at a time
            slow runner goes one step at a time
            As you visit slow, put it in stack.

            1->0->2->0->1
                 slow  fast

            stack= | 1 | 0 | 2 |

            as fast.next == null, list is of odd size.

            while(!stack.isEmpty() && slow != null)
                if(!slow.equals(stack.pop()) return false;


        LinkedList of odd size

            1->0->0->1
           slow
           fast

            1->0->0->1->null
                 slow   fast

            stack= | 1 | 0 | 0 |

            as fast == null, list is of even size.

            for even sized list, pop first element of stack.  ----- This step is not there for even sized list

            stack= | 1 | 0 |

            do remaining same as even sized list steps


     */
    private static boolean isPalindrome_testAgain(SinglyLinkedList LL) {
        if (LL.head == null || LL.head.next == null) return true;

        Stack<Node> stack = new Stack<>();

        Node slow = LL.head; // slow runner jumps 1 element at a time
        Node fast = LL.head; // fast runner jumps 2 elements at a time

        stack.add(slow);

        while (fast != null && fast.next != null) {
            fast = fast.next.next;

            slow = slow.next;
            stack.add(slow);
        }

        boolean isEven = false; // is list of even size
        if (fast == null) isEven = true;

        if(isEven) {
            stack.pop();
        }

        while (!stack.isEmpty() && slow != null) {
            Node stackNode = stack.pop();

            if (!slow.equals(stackNode)) return false;

            slow = slow.next;
        }

        return true;
    }
}
