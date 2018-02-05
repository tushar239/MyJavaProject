package algorithms.crackingcodinginterviewbook._3stackandqueue;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

// p.g. 233 of Cracking Coding Interview book
/*
Define a stack of specific size. If that stack becomes full, create another one in the set of stacks and when it is empty remove it from the stack.
Client should not know that he is operating on multiple stacks.

As a FOLLOW UP:
implement a method popAt(int stackIndex)

Solution:
You create a class StacksSet and client should push and pop from this class.
StacksSet should maintain a pile of stacks (ArrayList of stacks)

ArrayList
---------------------------------------------------------
|    stack 1    |     stack 2      |      stack 3       |
---------------------------------------------------------
  |  3  |             |  6  |               |     |
  |  2  |             |  5  |               |     |
  |  1  |             |  4  |               |  7  |
  -------             -------               -------

pop()  =  7

------------------------------------
|    stack 1    |     stack 2      |
------------------------------------
  |  3  |             |  6  |
  |  2  |             |  5  |
  |  1  |             |  4  |
  -------             -------

pop()  =  6

------------------------------------
|    stack 1    |     stack 2      |
------------------------------------
  |  3  |             |     |
  |  2  |             |  5  |
  |  1  |             |  4  |
  -------             -------


push(6)

------------------------------------
|    stack 1    |     stack 2      |
------------------------------------
  |  3  |             |  6  |
  |  2  |             |  5  |
  |  1  |             |  4  |
  -------             -------

push(7)

---------------------------------------------------------
|    stack 1    |     stack 2      |      stack 3       |
---------------------------------------------------------
  |  3  |             |  6  |               |     |
  |  2  |             |  5  |               |     |
  |  1  |             |  4  |               |  7  |
  -------             -------               -------


for the special case, popAt(stack#2) = 6:  after popping, shifting of elements from next stacks should happen


------------------------------------
|    stack 1    |     stack 2      |
------------------------------------
  |  3  |             |  7  |
  |  2  |             |  5  |
  |  1  |             |  4  |
  -------             -------



 */
public class _3SetOfStacks {

    public static void main(String[] args) {
        StacksSet stacksSet = new StacksSet();
        try {
            System.out.println("Pushing element: " + stacksSet.push(1));
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            System.out.println("Pushing element: " + stacksSet.push(2));
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            System.out.println("Pushing element: " + stacksSet.push(3));
        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println("Total stacks in a set: "+stacksSet.size());

        try {
            System.out.println("Pushing element: " + stacksSet.push(4));
        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println("Total stacks in a set: "+stacksSet.size());

        try {
            System.out.println("Popping element: " + stacksSet.pop());
            stacksSet.pop();
        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println("Total stacks in a set: "+stacksSet.size());

        try {
            System.out.println("Pushing element: " + stacksSet.push(4));
        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            System.out.println("Pushing element: " + stacksSet.push(5));
        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println("Total stacks in a set: "+stacksSet.size());
        System.out.println(stacksSet.toString());

        System.out.println();
        System.out.println("Testing popAt(stackIndex) ..........");

        try {
            System.out.println("Popping element from stack: " + stacksSet.popAt(0));
        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println("Total stacks in a set: "+stacksSet.size());
        System.out.println(stacksSet.toString());

        try {
            System.out.println("Popping element from stack: " + stacksSet.popAt(0));
        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println("Total stacks in a set: "+stacksSet.size());
        System.out.println(stacksSet.toString());
    }
    static class StacksSet {
        private static final int STACK_CAPACITY=3;
        private List<MyStack> stacks = new ArrayList<>();


        @Override
        public String toString() {
            String str = "";
            for (MyStack stack : stacks) {
                str += stack.toString()+"\n";
            }
            return str;
        }

        public Integer push(Integer element) throws Exception {
            MyStack availableStack = getAvailableStack();
            if(availableStack == null) {
                stacks.add(new MyStack(STACK_CAPACITY));
                availableStack = getAvailableStack();
            }
            else if(availableStack.isFull()) {
                stacks.add(new MyStack(STACK_CAPACITY));
                availableStack = getAvailableStack();
            }
            return availableStack.myPush(element);
        }

        public Integer pop() throws Exception {
            MyStack availableStack = getAvailableStack();
            if(availableStack == null) {
                return null;
            }
            Integer poppedElement = availableStack.myPop();

            if(availableStack.isEmpty()) {
                stacks.remove(availableStack);
            }

            return poppedElement;
        }

        // This is a bit complex. This method allows you to pop an element from a specific stack.
        // After popping, you need to shift other stack elements.
        public Integer popAt(int stackIndex) throws Exception {
            if(stacks.size() < stackIndex + 1) {
                return null;
            }
            MyStack myStack = stacks.get(stackIndex);
            Integer poppedElement = myStack.myPop();

            shiftStacksElements(stackIndex);

            return poppedElement;
        }
        private void shiftStacksElements(int stackToBeFilledIndex) {
            MyStack stackToBeFilled = stacks.get(stackToBeFilledIndex);
            if(stackToBeFilled.isEmpty()) {
                stacks.remove(stackToBeFilledIndex);
            } else {
                int shiftingStackIndex = stackToBeFilledIndex + 1;

                if(isAvailable(shiftingStackIndex)) {
                    MyStack stack = stacks.get(shiftingStackIndex);
                    while(!stackToBeFilled.isFull()) {
                        stackToBeFilled.push(stack.popFromBottom());
                    }

                    shiftStacksElements(shiftingStackIndex);
                }
            }
        }
        public boolean isAvailable(int stackIndex) {
            if(stacks.size() >= (stackIndex + 1)) {
                return true;
            }
            return false;
        }
        public MyStack getAvailableStack() {
            if(stacks.size() == 0) {
                return null;
            }
            return stacks.get(stacks.size() - 1);
        }

        public int size() {
            return stacks.size();
        }

        public boolean isEmpty() {
            return stacks.size() == 0;
        }
        /*public boolean isFull() {
            return stacks.size() ==0 || stacks.get(stacks.size() - 1).isFull();
        }
        public boolean isEmpty() {
            return stacks.size() ==0 || stacks.get(stacks.size() - 1).isEmpty();
        }*/

    }
    static class MyStack extends Stack<Integer> {
        private int capacity;
        public MyStack(int capacity) {
            this.capacity = capacity;
        }

        public int getCapacity() {
            return capacity;
        }
        public boolean isFull() {
            return size() == capacity;
        }
        public boolean isEmpty() {
            return  size() == 0;
        }

        public Integer myPush(Integer element) throws Exception {
            if(isFull()) {
                throw new Exception("Stack is full");
            }
            return super.push(element);
        }

        public Integer myPop() throws Exception {
            if(isEmpty()) {
                throw new Exception("Stack is empty");
            }
            return super.pop();
        }

        public Integer popFromBottom() {
            int bottomIndex = size() - 1;
            Integer element = super.get(bottomIndex);
            super.removeElementAt(bottomIndex);
            return element;
        }
    }
}
