package java8.functionalprograming.functionalprogramminginjavabook.chapter11.priority_queue_using_LeftListHeap;

import java8.functionalprograming.functionalprogramminginjavabook.chapter7.Result;

/*
pg 333

Here, we are trying to implement Priority Queue kind of concept using LeftList Heap.
Priority Queue is based on Binary Heap (BinaryHeap.java in algorithms package).
There is Min BinaryHeap and Max BinaryHeap.
BinaryHeap look like a tree, but it is just a reordering of elements in an array. Based on index you can find higher priority the element.
You can find min priority element on the top of Min BinaryHeap, you don't need to search for it like BST.

The biggest use of Priority Queue is to insert elements coming from multiple threads. e.g. in parallel processing of a list elements, there is no guarantee of which thread will finish first.
As soon as a thread finishes, you can insert its output in a priority queue.
For example, if elements 1, 2, 3, 4, 5, 6, 7, and 8 are given to eight threads to be processed in parallel, the consumer will poll the queue to see if element 1 is available. If it is, it will consume it. If not, it will just wait.
Benefit of using Priority Queue to find min/max over any tree is that consumer doesn't need to search for an element in a tree. Normally Red-Black Tree like structure has lowest element at the bottom of left sub tree and highest element at the bottom of right sub tree.


LeftList:
"leftist" means that for each element, the left branch rank is >= to the right branch rank.
Here we are implementing LeftList Heap.

Rank:
The rank of an element is the height of the right path (also called the right spine) to an empty element. The leftist property guarantees that the shortest path from any element to an empty element is the right path.

Length:
It is same as size of the heap (# of elements in heap) = # of elements in left heap + # of element in right heap + 1

 */

public class DefaultHeap<A extends Comparable<A>> extends Heap<A> {

    private final int length; // same as size of heap (# of elements in a heap)
    private final int rank; // height of the right heap till empty element
    private final A head;
    private final Heap<A> left;
    private final Heap<A> right;

    private DefaultHeap(int length, int rank, Heap<A> left, A head, Heap<A> right) {
        this.length = length;
        this.rank = rank;
        this.head = head;
        this.left = left;
        this.right = right;
    }

    @Override
    protected int rank() {
        return this.rank;
    }

    @Override
    public Result<A> head() {
        return Result.success(this.head);
    }

    @Override
    public int length() {
        return this.length;
    }

    @Override
    protected Result<Heap<A>> left() {
        return Result.success(this.left);
    }

    @Override
    protected Result<Heap<A>> right() {
        return Result.success(this.right);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }


    // Creating a heap with single element
    public static <A extends Comparable<A>> Heap<A> heap(A element) {
        return new DefaultHeap(1, 1, empty(), element, empty());
    }

    protected static <A extends Comparable<A>> Heap<A> heap(A head,
                                                            Heap<A> first, Heap<A> second) {
        return first.rank() >= second.rank()
                ? new DefaultHeap(first.length() + second.length() + 1, second.rank() + 1, first, head, second)
                : new DefaultHeap(first.length() + second.length() + 1, first.rank() + 1, second, head, first);
    }

    public static <A extends Comparable<A>> Heap<A> merge(Heap<A> first,
                                                          Heap<A> second) {
        return first.head().flatMap(
                fh -> second.head().flatMap(
                        sh -> fh.compareTo(sh) <= 0
                                ? first.left().flatMap(
                                fl -> first.right().map(
                                        fr -> heap(fh, fl, merge(fr, second))))
                                : second.left().flatMap(
                                sl -> second.right().map(
                                        sr -> heap(sh, sl, merge(first, sr))))))
                .getOrElse(first.isEmpty() ? second : first);
    }
    
}
