package algorithms.crackingcodinginterviewbook._3stackandqueue;

import java.util.Stack;

/*

Sorting a stack is relatively easy compared to array or linked nilList.
For sorting a array, you have many sorting algorithms.
For sorting a linkedlist, you have to use deleteRootAndMergeItsLeftAndRight sort. you can not use quick sort because quick sort requires to use index and linkedlist is very root_to_leaf_problems_hard to work with index.
You can sort Stack also just like array using deleteRootAndMergeItsLeftAndRight/quick sort, but there is a easier method available.
For Sorting a stack, you don't need any special sorting algorithm. you just need to use stack's pop and push api.

IMP:
Unlike to LinkedList, Stack extends Vector extends AbstractList.
So, stack element can be accessed using index, stack.get(index). So, stack can be sorted using quick sort or deleteRootAndMergeItsLeftAndRight sort just like normal array.
Following link has a special type of stack sorting (as done in below example) and quick sort.
http://stackoverflow.com/questions/24768011/write-a-program-to-sort-a-stack-in-ascending-order



This solution has execution time O(n^2) and space requirement O(n). To reduce execution time (O(n log n)), you can convert stack1 to linkedlist and run deleteRootAndMergeItsLeftAndRight sort on linkedlist and then put sorted linkedlist elements back to original stack.
OR
You can create a priority queue. pop elements from stack and put in priority queue. priority queue will provide min element every time you remove an element from it. Remove an element from priority queue and push it to stack again.

you need two stacks.
step 1

     stack 1=
     8
     12
     1
     3
     7
     10

     stack2=
     empty

step 2
pop 8 from stack1 and push to stack2

    stack 1=
     12
     1
     3
     7
     10

     stack2=
     8

step 3
pop 12 from stack1, compare it with stack2's top element(peek()). As 12>8, push 12 to stack2
    stack 1=
     1
     3
     7
     10

     stack2=
     12
     8

step 4
pop 1 from stack1, compare it with stack2's top element 12 (peek() 12). As 1<12, pop 12 from stack2 and push it to stack1.
Now stack2 has 8. Do the same thing in between 1 and 8.
push 1 to stack2.


    pop 1 from stack1, pop 12 from stack2. put 12 back to stack 1. Do the same with 8.
    push 1 to stack2.
    stack 1=
     8
     12
     3
     7
     10

     stack2=
     1

step 4,5

    stack 1=
     3
     7
     10

     stack2=
     12
     8
     1

step 6

    stack 1=
     8
     12
     7
     10

     stack2=
     3
     1

step 7,8

    stack 1=
     7
     10

     stack2=
     12
     8
     3
     1

step 9
    stack 1=
      8
     12
     10

     stack2=
     7
     3
     1

step 10,11
    stack 1=
     10

    stack2=
     12
     8
     7
     3
     1

step 12
    stack 1=
     12

    stack2=
     10
     8
     7
     3
     1

step 13
    stack 1=

    stack2=
     12
     10
     7
     3
     1

stack 2 is sorted
 */
public class _5SortStack {
    public static void main(String[] args) {
        int[] array = {8, 12, 1, 3, 7, 10};

        Stack<Integer> stack1 = new Stack<>();
        for (int element : array) {
            stack1.push(element);
        }

        System.out.println("UnSorted stack: " + stack1);

        Stack<Integer> stack2 = new Stack<>();

//        sort(stack1, stack2);
//        System.out.println("Sorted stack: " + stack2);

        sort_test(stack1, stack2);
        System.out.println("Sorted stack: " + stack2);

    }

    // execution time is O(n^2). You need extra stack (stack2) so space requirement is O(n)
    private static void sort(Stack<Integer> stack1, Stack<Integer> stack2) {
        while (!stack1.isEmpty()) {// execution time is O(n)
            Integer stack1Element = stack1.pop();
            Integer peekedStack2Element;
            while (!stack2.isEmpty() && ((peekedStack2Element = stack2.peek()) > stack1Element)) { // execution time worst case (when stack 1 has sorted elements) = o(n) for each out loop element
                stack1.push(stack2.pop());
            }
            stack2.push(stack1Element);

        }
    }

    private static void sort_test(Stack<Integer> stack1, Stack<Integer> stack2) {
        while (!stack1.isEmpty()) {
            Integer poppedFromStack1 = stack1.pop();

            while (!stack2.isEmpty()) {
                if (stack2.peek() > poppedFromStack1) {
                    stack1.push(stack2.pop());
                } else {
                    break;
                }
            }
            stack2.push(poppedFromStack1);
        }
    }

}
