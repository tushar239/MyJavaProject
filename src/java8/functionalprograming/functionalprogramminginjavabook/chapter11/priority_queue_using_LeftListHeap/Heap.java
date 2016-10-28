package java8.functionalprograming.functionalprogramminginjavabook.chapter11.priority_queue_using_LeftListHeap;

import java8.functionalprograming.functionalprogramminginjavabook.chapter7.Result;


/*
TODO: add explanation of Priority Queue and LeftList Heap
 */

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

}