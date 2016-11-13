package java8.functionalprograming.functionalprogramminginjavabook.chapter11.priority_queue_of_comparable_elements_using_LeftListHeap;

import java8.functionalprograming.functionalprogramminginjavabook.chapter7.Result;

public abstract class Heap<A extends Comparable<A>> {
    @SuppressWarnings("rawtypes")
    protected static final Heap EMPTY = new EmptyHeap();

    public abstract Result<A> head();

    protected abstract Result<Heap<A>> left();

    protected abstract Result<Heap<A>> right();

    protected abstract int rank();

    public abstract int length();

    public abstract boolean isEmpty();

    @SuppressWarnings("unchecked")
    public static <A extends Comparable<A>> Heap<A> empty() {
        return EMPTY;
    }

    /*
       Although implemented as a tree, the heap is seen from the user perspective like a priority queue, which means a kind of linked list where the head would always be the smallest element it contains. By analogy, the root element of the tree is called the head, and what remains after having "removed" the head is called the tail.
    */
    public abstract Result<Heap<A>> tail();

    public abstract Result<A> get(int index);
}