package algorithms.crackingcodinginterviewbook._5recursion_and_dynamic_programming;

import java.util.Arrays;
import java.util.Stack;

/*
Tower of Hanoi:
You have 3 stacks-origin, buffer and destination.
Origin is filled with 5 disks with largest on at the bottom and smallest on on the top in ascending size from top to bottom.
Arrange these disks in the destination stack in the same order.
Conditions:
You cannot reserve out more than one disks at a time. When you reserve out a disk, you need to put it in one of the stacks before you reserve out another.
When you put another disk on top of prev one, they should be stacked as per ascending size.


This algorithm is a classic example of recursion. This algorithm cannot be done easily without recursion. It will be super root_to_leaf_problems_hard to write this algorithm without recursion.

watch 'Towers Of Hanoi Problem.mp4'. This video describes recursion very well.


Time Complexity:

Using Back Substitution strategy.

    TOH(n) = TOH(n-1) + C + TOH(n-1)   C=constant time operation
           = 2TOH(n-1) + C

    Calculate TOH(n-1) by replacing n to n-1 in TOH(n)

        TOH(n-1) = 2TOH(n-2) + C
        TOH(n-2) = 2TOH(n-3) + C

    Now lets's replace TOH(n-1) and then TOH(n-2) and so on... in original formula.

    TOH(n) = 2TOH(n-1) + C
           = 2 (2TOH(n-2) + C) + C
           = 2 (2 (2TOH(n-3) + C) + C) + C
           = 2^3 TOH(n-3) + 4C + 2C + C

    Now replace a constant that is changing at each step. It is 3 here.
           = 2^K TOH(n-K) + 4C + 2C + C

    Now let's replace TOH(n-k) with base condition.
    Our base condition is TOH(0)=do nothing
            n-k=0
            k=n

           = 2^n TOH(0) + 4C + 2C + C

           Ignoring the constants and replacing TOH(0)

           = 2^n


Using Recursive Tree Strategy:

Recursive tree looks ALMOST same as Fibonacci's recursion tree.

                                                    TOH(5)

                        TOH(4)                                                      TOH(4)

            TOH(3)                      TOH(3)                      TOH(3)                          TOH(3)

  TOH(2)            TOH(2)     TOH(2)           TOH(2)    TOH(2)            TOH(2)        TOH(2)            TOH(2)

TOH(1)TOH(1)    TOH(1)TOH(1) TOH(1)TOH(1)   TOH(1)TOH(1)TOH(1)TOH(1)    TOH(1)TOH(1)    TOH(1)TOH(1)    TOH(1)TOH(1)

Total number of nodes in this recursion tree = 2^0 + 2^1 + 2^2 + 2^3 +.....+ 2^n = 2^n+1 - 1 = O(2^n)

in each node of recursion tree, only one constant time operation is happening. So, time complexity is O(2^n)

NOTE:
As you can see all the calls are happening more than once.
TOH(4) is happening 2 times.
TOH(3) is happening 4 times.
TOH(2) is happening 8 times.
TOH(1) is happening 16 times.

Time complexity could be improved, if you could memoize the result of these calls.
You somehow need to memoize the state of origin,buffer and destination stacks for number of disks, which is not so easy.
 */
public class _6TowerOfHenoi {

    public static void main(String[] args) {

        Stack<Integer> origin = new Stack<>();
        Stack<Integer> buffer = new Stack<>();
        Stack<Integer> destination = new Stack<>();

        // 1 is the biggest disk and 5 is the smallest
        int[] disks = {1, 2, 3, 4, 5};

        // initialize origin stack
        for (int disk : disks) {
            origin.push(disk);
        }

        System.out.println("Before...");
        System.out.println("state of origin stack: " + origin.toString());
        System.out.println("state of buffer stack: " + buffer.toString());
        System.out.println("state of destination stack: " + destination.toString());

        TOH(disks, origin, buffer, destination);

        System.out.println("After...");
        System.out.println("state of origin stack: " + origin.toString());
        System.out.println("state of buffer stack: " + buffer.toString());
        System.out.println("state of destination stack: " + destination.toString());
    }

    private static void TOH(int[] disks, Stack<Integer> origin, Stack<Integer> buffer, Stack<Integer> destination) {

        if (disks.length == 0) return;

        // same as reversing a linked list problem
        // reduce the problem by 1
        // reserve out 1 disk from the origin stack
        // arrange remaining disks
        // then think how to merge the 1st disk with remaining ones

        // pop the topmost disk from origin stack
        Integer disk5 = origin.pop();

        // move 4,3,2,1 disks in buffer stack by using destination stack as a buffer(staging) stack
        int[] remainingDisks = Arrays.copyOfRange(disks, 1, disks.length);
        TOH(remainingDisks, origin, destination, buffer);

        // push your disk#5 into destination stack. This disk should be on the top in destination stack. so, it needs to be pushed before others.
        destination.push(disk5);

        // push remaining disks from buffer to destination by using origin stack as a buffer(staging) stack.
        TOH(remainingDisks, buffer, origin, destination);
    }


}
