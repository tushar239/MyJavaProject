package java8.functionalprograming.functionalprogramminginjavabook.chapter11.priority_queue_of_noncomparable_elements_using_LeftListHeap;

import java8.functionalprograming.functionalprogramminginjavabook.chapter7.Result;

import java.util.Comparator;

public abstract class Heap<A> {

    public abstract Result<A> head();

    protected abstract Result<Heap<A>> left();

    protected abstract Result<Heap<A>> right();

    protected abstract int rank();

    public abstract int length();

    public abstract boolean isEmpty();

    protected abstract Result<Comparator<A>> comparator();

    public static <A> Heap<A> empty(Result<Comparator<A>> comparator) {
        return new EmptyHeap(comparator);
    }
    public static <A> Heap<A> empty(Comparator<A> comparator) {
        return empty(Result.success(comparator));
    }
    /*
       Although implemented as a tree, the heap is seen from the user perspective like a priority queue, which means a kind of linked list where the head would always be the smallest element it contains. By analogy, the root element of the tree is called the head, and what remains after having "removed" the head is called the tail.
    */
    public abstract Result<Heap<A>> tail();

    public abstract Result<A> get(int index);
}