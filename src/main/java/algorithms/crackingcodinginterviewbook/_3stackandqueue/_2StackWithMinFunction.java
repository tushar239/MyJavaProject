package algorithms.crackingcodinginterviewbook._3stackandqueue;

import java.util.Stack;

/*
    pg. 232 of Cracking Coding Interview book

    Create a stack having min function along with push and pop functions.
    Condition is push, pop and min should reserve O(1) time. It means that to find a min, you can not traverse entire stack.

    Remember:
    If you are thinking of maintaining 'min' variable in your MyStack class by updating its value on each push by comparing existing min value and pushed value,
    then it worn't work because on pop(), you cannot maintain 'min' value.

    Solution:
    Push min with an element in the stack.
    Create a wrapper NodeWithMin containing element value and min.
    Push/Pop NodeWithMin from stack.
    Before pushing next element, peek an element (NodeWithMin) from stack and check its min. If its min < next element's min, then use its min in next element's NodeWithMin, otherwise use next element as min value for its NodeWithMin.
*/
public class _2StackWithMinFunction {

    public static void main(String[] args) {
        int[] elements = {5, 6, 3, 7};

        MyStack stack = new MyStack();

        /*
            stack -
            7, min=3
            3, min=3
            6, min=5
            5, min=5
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
            if (isEmpty()) {
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



