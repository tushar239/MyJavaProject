package java8.functionalprograming.functionalprogramminginjavabook.chapter11.priority_queue_of_noncomparable_elements_using_LeftListHeap;

import java8.functionalprograming.functionalprogramminginjavabook.chapter7.Result;

import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * @author Tushar Chokshi @ 10/28/16.
 */
public class EmptyHeap<A> extends Heap<A> {
    private final Result<Comparator<A>> comparator;

    protected EmptyHeap(Result<Comparator<A>> comparator) {
        this.comparator = comparator;
    }

    @Override
    protected Result<Comparator<A>> comparator() {
        return this.comparator;
    }

    @Override
    protected int rank() {
        return 0;
    }

    @Override
    public Result<A> head() {
        return Result.failure(new NoSuchElementException(
                "head() called on empty heap"));
    }

    @Override
    public int length() {
        return 0;
    }

    @Override
    protected Result<Heap<A>> left() {
        return Result.success(empty(comparator));
    }

    @Override
    protected Result<Heap<A>> right() {
        return Result.success(empty(comparator));
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public Result<Heap<A>> tail() {
        return Result.failure(new NoSuchElementException("tail() called on empty heap"));
    }

    @Override
    public Result<A> get(int index) {
        return Result.failure(new NoSuchElementException("Index out of range"));
    }
}