package algorithms.stackandqueue;

import java.util.Stack;

/*
    pg. 232 of Cracking Coding Interview book

    Create a stack having min function along with push and pop functions.
    Condition is push, pop and min should take O(1) time. It means that to find a min, you can not traverse entire stack.

    Solution:
    push min with an element in the stack. create a wrapper NodeWithMin containing element value and min. Push/Pop NodeWithMin from stack.
*/
public class StackWithMinFunction {

    public static void main(String[] args) {
        int[] elements = {5, 6, 3, 7};

        MyStack stack = new MyStack();

        /*
            stack -
            7,3
            3,3
            6,5
            5,5
        */
        for (int element : elements) {
            stack.myPush(element);
        }
        System.out.println("Stack after pushing all elements: " + stack.toString());

        NodeWithMin item = stack.myPop();
        System.out.println("Popped item: "+ item.toString());
        System.out.println("min in stack:" + stack.min());

        item = stack.myPop();
        System.out.println("Popped item: " + item.toString());
        System.out.println("min in stack:" +stack.min());

        item = stack.myPop();
        System.out.println("Popped item: " + item.toString());
        System.out.println("min in stack:" +stack.min());
    }


    static class NodeWithMin {
        private int value;
        private int min;

        public NodeWithMin(int value, int min) {
            this.value = value;
            this.min = min;
        }

        public int getValue() {
            return value;
        }

        public int getMin() {
            return min;
        }
        @Override
        public String toString() {
            return "{value="+getValue()+", min="+getMin()+"}";
        }
    }

    class Oh<T extends NodeWithMin> {
        NodeWithMin get() {
            return null;
        }
    }
    static class MyStack extends Stack<NodeWithMin> {
        private Integer min = null;


        public NodeWithMin myPush(Integer value) {
            if (size() == 0) {
                this.min = value;
            } else {
                int peekedMin = peek().getMin();
                if (peekedMin > value) {
                    this.min = value;
                } else {
                    this.min = peekedMin;
                }
            }
            return super.push(new NodeWithMin(value, min));
        }


        public NodeWithMin myPop() {
            NodeWithMin item = super.pop();
            if (size() == 0) {
                min = null;
            } else {
                this.min = peek().getMin();
            }
            return item;
        }

        public Integer min() {
            return this.min;
        }

    }

}



