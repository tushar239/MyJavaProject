package algorithms._1array_stack_queue.geeksforgeeks.pointers_sorting_stack_queue_string.stack.medium;

import algorithms.utils.ArrayUtils;

/*
    Implement two stacks in an array

    https://www.geeksforgeeks.org/implement-two-stacks-in-an-array/


    Create a data structure twoStacks that represents two stacks. Implementation of twoStacks should use only one array, i.e., both stacks should use the same array for storing elements. Following functions must be supported by twoStacks.

    push1(int x) –> pushes x to first stack
    push2(int x) –> pushes x to second stack

    pop1() –> pops an element from first stack and return the popped element
    pop2() –> pops an element from second stack and return the popped element

    Implementation of twoStack should be space efficient.


    Solution:

    There are three solutions:

    1) you just keep adding an element to an array with a object that has an element and stack number.
    Every time, you need to pop an element, you iterate an array from the end and find an element for particular stack
    and remove that element from a stack and move all elements after that elements to one step back.

    this solution is space efficient, but time complexity will be high.

    2) divide an array into half.
    start first stack from 0th place and second stack from array.size()/2 place.

*/
public class _4TwoStacksUsingOneArray {

    private int[] arr;
    private int stack1Pointer;
    private int stack2Pointer;

    public _4TwoStacksUsingOneArray(int sizeOfArray) {
        this.arr = new int[sizeOfArray];
        this.stack1Pointer = -1;
        this.stack2Pointer = sizeOfArray;
    }

    public void push1(int x) throws Exception {
        if (stack2Pointer == stack1Pointer + 1) {
            throw new Exception("array is full. Unless element(s) are popped from stack(s), more element(s) can't be added.");
        }
        arr[++stack1Pointer] = x;
    }

    public void push2(int x) throws Exception {
        if (stack2Pointer == stack1Pointer + 1) {
            throw new Exception("array is full. Unless element(s) are popped from stack(s), more element(s) can't be added.");
        }
        arr[--stack2Pointer] = x;
    }

    public int pop1() throws Exception {
        if (stack1Pointer == -1) {
            throw new Exception("stack1 is empty.");
        }
        int poppedElement = arr[stack1Pointer];
        stack1Pointer--;
        return poppedElement;
    }

    public int pop2() throws Exception {
        if (stack2Pointer == -1) {
            throw new Exception("stack2 is empty.");
        }
        int poppedElement = arr[stack2Pointer];
        stack2Pointer--;
        return poppedElement;
    }

    public void print() {
        ArrayUtils.printArray(arr);
    }

    public static void main(String[] args) {
        _4TwoStacksUsingOneArray obj = new _4TwoStacksUsingOneArray(10);

        pushToStack1(obj, 1);
        pushToStack2(obj, 100);

        pushToStack1(obj, 2);
        pushToStack2(obj, 200);

        pushToStack1(obj, 3);
        pushToStack2(obj, 300);

        pushToStack1(obj, 4);
        pushToStack2(obj, 400);

        pushToStack1(obj, 5);
        pushToStack2(obj, 500);

        obj.print();
        System.out.println();

        pushToStack1(obj, 6);
        pushToStack2(obj, 600);

        obj.print();
        System.out.println();

        popFromStack1(obj);
        popFromStack2(obj);

        obj.print();
        System.out.println();

        pushToStack1(obj, 6);
        pushToStack1(obj, 7);


        obj.print();
    }

    private static void popFromStack1(_4TwoStacksUsingOneArray obj) {
        try {
            System.out.println("Element popped from Stack1: " + obj.pop1());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void popFromStack2(_4TwoStacksUsingOneArray obj) {
        try {
            System.out.println("Element popped from Stack2: " + obj.pop2());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void pushToStack1(_4TwoStacksUsingOneArray obj, int element) {
        try {
            obj.push1(element);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void pushToStack2(_4TwoStacksUsingOneArray obj, int element) {
        try {
            obj.push2(element);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
