package algorithms._1array_stack_queue.geeksforgeeks.pointers_sorting_stack_queue_string.stack.medium;

/*
    Design a stack with operations on middle element

    https://www.geeksforgeeks.org/design-a-stack-with-find-middle-operation/

    How to implement a stack which will support following operations in O(1) time complexity?

    1) push() which adds an element to the top of stack.
    2) pop() which removes an element from top of stack.
    3) findMiddle() which will return middle element of the stack.
    4) deleteMiddle() which will delete the middle element.

    Push and pop are standard stack operations.

    Solution:
        You can implement stack from fixed size array or linked list. Here, you don't want to fix the size, so let's use a linked list.
        For O(1) operation on middle element, you need to keep a pointer on middle element all the time.
        When a new element is added, if size of linked list becomes odd, you move a middle element pointer by 1. Don't do anything when size becomes even.
        When an element is removed, if size of linked list becomes even, you move a middle element pointer back by 1 element. Don't do anything when size becomes odd.
        This way, you have a pointer pointing to middle element all the time and you can do any operation on middle element in O(1).

    Important:
        This algorithm teaches you that
        - To implement a stack from linked list, you need a doubly linked list.
        - To implement a queue from linked list, you can use singly linked list.

*/
public class _7DesignStackWithOperationsOnMiddleElement {

    public static void main(String[] args) {
        System.out.println("Test findMiddleElement()");
        {
            MyStack<Integer> myStack = new MyStack<>();

            myStack.push(1);
            System.out.println(myStack.findMiddleElement());//1

            myStack.push(2);
            System.out.println(myStack.findMiddleElement());//1

            myStack.push(3);
            System.out.println(myStack.findMiddleElement());//2

            myStack.push(4);
            System.out.println(myStack.findMiddleElement());//2

            myStack.pop();
            System.out.println(myStack.findMiddleElement());//2

            myStack.pop();
            System.out.println(myStack.findMiddleElement());//1

            myStack.pop();
            System.out.println(myStack.findMiddleElement());//1

            myStack.pop();
            System.out.println(myStack.findMiddleElement());//null
        }

        System.out.println("Test removeMiddleElement()");
        {

            MyStack<Integer> myStack = new MyStack<>();

            myStack.push(1);
            myStack.push(2);
            myStack.push(3);
            myStack.push(4);
            myStack.push(5);
            System.out.println(myStack.findMiddleElement());//3

            myStack.removeMiddleElement();
            System.out.println(myStack.findMiddleElement());//2

            myStack.removeMiddleElement();
            System.out.println(myStack.findMiddleElement());//2

            myStack.removeMiddleElement();
            System.out.println(myStack.findMiddleElement());//1

            myStack.removeMiddleElement();
            System.out.println(myStack.findMiddleElement());//1

            myStack.removeMiddleElement();
            System.out.println(myStack.findMiddleElement());//null

        }
    }

    static class MyStackNode<I> {
        private I item;
        private MyStackNode<I> next;
        private MyStackNode<I> prev;

        public MyStackNode(I item) {
            this.item = item;
        }

        public I getItem() {
            return item;
        }

        public MyStackNode<I> getPrev() {
            return prev;
        }

        public void setPrev(MyStackNode<I> prev) {
            this.prev = prev;
        }

        public MyStackNode<I> getNext() {
            return next;
        }

        public void setNext(MyStackNode<I> next) {
            this.next = next;
        }
    }

    static class MyStack<I> {
        private MyStackNode<I> top;
        private int size;
        private MyStackNode<I> middleNode;

        public void push(I item) {
            MyStackNode<I> newNode = new MyStackNode<>(item);
            if (top == null) {
                top = newNode;
                size++;
                middleNode = top;
            } else {
                newNode.prev = top;
                top.next = newNode;
                top = newNode;

                size++;

                if (size % 2 == 1) {
                    middleNode = middleNode.next;
                }
            }
        }

        public I pop() {
            MyStackNode<I> poppedNode = top;

            if (poppedNode != null) {

                top = poppedNode.prev;

                if (top != null) {
                    top.next = null;
                }
                poppedNode.prev = null;

                size--;


                if (size % 2 == 0) {
                    middleNode = middleNode.prev;
                }

                return poppedNode.item;
            }
            return null;
        }

        public I findMiddleElement() {
            if (middleNode == null) {
                return null;
            }
            return middleNode.item;
        }

        public void removeMiddleElement() {
            if (middleNode == null) {
                return;
            }

            MyStackNode<I> nodeToBeRemoved = this.middleNode;

            if (nodeToBeRemoved.next != null) {
                nodeToBeRemoved.next.prev = nodeToBeRemoved.prev;
            }
            if (nodeToBeRemoved.prev != null) {
                nodeToBeRemoved.prev.next = nodeToBeRemoved.next;
            }
            size--;

            if (size % 2 == 0) {
                middleNode = middleNode.prev;
            }

        }

    }
}
