package algorithms.crackingcodinginterviewbook._3stackandqueue;

import java.util.Arrays;

//p.g. 227 of Cracking Coding Interview book
/*
Three in one: Describe how you could use a single array to implement three stacks.


Array Index      0          4  5        9 10        14
                ---------------------------------------
                |  Stack 1   |  Stack 2   |  Stack 3  |
                ---------------------------------------

You need to have a Fixed Size of each Stack (e.g each stack can have max 5 elements).

You need to have methods
- getFirstIndexOfStack(int stackNo)
- getLastIndexOfStack(int stackNo)

These methods will be useful to shift array elements when you push and pop the elements.

As it is a stack, you always need first index of a stack to pop and push.
*/
public class _1ArrayForMultipleStacks {
    private static final int SIZE_OF_EACH_STACK=5;
    private static final int NO_OF_STACKS=3;

    public static void main(String[] args) {
        ArrayOfStacks arrayOfStacks = new ArrayOfStacks();
        try {
            arrayOfStacks.pop(1);
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            arrayOfStacks.push(0, 1);
            arrayOfStacks.push(0, 2);
            arrayOfStacks.push(0, 3);
            arrayOfStacks.push(0, 4);
            arrayOfStacks.push(0, 5);
            arrayOfStacks.push(0, 6);// This will throw an error - stack# 0 is full
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            arrayOfStacks.push(1, 11);
            arrayOfStacks.push(2, 21);
        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println("ArrayOfStacks after push: "+arrayOfStacks.toString());
        try {
            System.out.println("Pop from 0th stack: " + arrayOfStacks.pop(0));
            System.out.println("Pop from 0th stack: "+arrayOfStacks.pop(0));
            System.out.println("Pop from 0th stack: "+arrayOfStacks.pop(0));
            System.out.println("Pop from 1st stack: "+arrayOfStacks.pop(1));
            System.out.println("Pop from 1st stack: "+arrayOfStacks.pop(1));// This will throw an error - stack# is empty
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("ArrayOfStacks after pop: "+arrayOfStacks.toString());
    }

    static class ArrayOfStacks {
        Integer[] array = new Integer[NO_OF_STACKS*SIZE_OF_EACH_STACK];

        public ArrayOfStacks() {

        }
        public void push(int stackNumber, Integer item) throws Exception {
            if(stackNumber > NO_OF_STACKS) {
                throw new Exception("stack# "+stackNumber+ " does not exist");
            }
            if(isStackFull(stackNumber)) {
                throw new Exception("stack# "+stackNumber+ " is full");
            }

            int startIndex = getStartIndexOfStack(stackNumber);
            int endIndex = getEndIndexOfStack(stackNumber);

            for (int i=startIndex; i<=endIndex; i++) {
                if(array[i] == null) {
                    array[i] = item;
                    break;
                }
            }
        }
        public Integer pop(int stackNumber) throws Exception {
            if(stackNumber > NO_OF_STACKS) {
                throw new Exception("stack# "+stackNumber+ " does not exist");
            }
            if(isStackEmpty(stackNumber)) {
                throw new Exception("stack# "+stackNumber+ " is empty");
            }
            Integer poppedItem = null;
            for(int i=getEndIndexOfStack(stackNumber); i>=getStartIndexOfStack(stackNumber); i--) {
                if(array[i] != null) {
                    poppedItem = array[i];
                    array[i] = null;
                    break;
                }
            }
            return poppedItem;
        }
        public boolean isStackFull(int stackNumber) {
            return (this.array[getEndIndexOfStack(stackNumber)] != null);
        }
        public boolean isStackEmpty(int stackNumber) {
            return (this.array[getStartIndexOfStack(stackNumber)] == null);
        }
        public int getStartIndexOfStack(int stackNumber) {
            return SIZE_OF_EACH_STACK * stackNumber;
        }
        public int getEndIndexOfStack(int stackNumber) {
            return getStartIndexOfStack(stackNumber) + (SIZE_OF_EACH_STACK - 1);
        }
        @Override
        public String toString() {
            return Arrays.asList(array).toString();
        }

    }

}

