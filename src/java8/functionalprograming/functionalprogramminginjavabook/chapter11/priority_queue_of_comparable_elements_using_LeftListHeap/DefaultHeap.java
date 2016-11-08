package java8.functionalprograming.functionalprogramminginjavabook.chapter11.priority_queue_of_comparable_elements_using_LeftListHeap;

import java8.functionalprograming.functionalprogramminginjavabook.chapter7.Result;

/*
Priority Queue using LeftList Heap (pg 333)
-------------------------------------------
Priority queues are structures allowing element retrieval by priority order.

Priority Queue is based on Binary Heap (BinaryHeap.java in algorithms package).
There is Min BinaryHeap and Max BinaryHeap.
BinaryHeap look like a tree, but it is just a reordering of elements in an array. Based on index you can find higher priority the element.
You can find min priority element on the top of Min BinaryHeap, you don't need to search for it like BST.

The biggest use of Priority Queue is to insert elements coming from multiple threads. e.g. in parallel processing of a list elements, there is no guarantee of which thread will finish first.
As soon as a thread finishes, you can insert its output in a priority queue.
For example, if elements 1, 2, 3, 4, 5, 6, 7, and 8 are given to eight threads to be processed in parallel, the consumer will poll the queue to see if element 1 is available. If it is, it will consume it. If not, it will just wait.
Benefit of using Priority Queue to find min/max over any tree is that consumer doesn't need to search for an element in a tree. Normally Red-Black Tree like structure has lowest element at the bottom of left sub tree and highest element at the bottom of right sub tree.

Here, we are trying to implement Priority Queue kind of concept using LeftList Heap.
LeftList Heap is implemented more like a Tree.

LeftList:
"leftist" means that for each element, the left branch rank is >= to the right branch rank.
Here we are implementing LeftList Heap.

Rank:
The rank of an element is the height of the right path (also called the right spine) to an empty element. The leftist property guarantees that the shortest path from any element to an empty element is the right path.

Length:
It is same as size of the heap (# of elements in heap) = # of elements in left heap + # of element in right heap + 1

Important concept of Result's get and getOrThrow methods (pg 338)
-----------------------------------------------------------------
From Book:
As a general rule, you should always remember that calling get, like getOrThrow, could throw an exception if the Result is Empty.
We might either test for emptiness first, or include the code in a try...catch block (second example), but none of these solutions is really functional.
By the way, you should try to never find yourself calling get or getOrThrow.
The get method should only be used inside the Result class.
The best solution for enforcing this would be to make it protected. But it is useful to be able to use it while learning, to show what is happening!

My opinion:
I would say that you should not apply any operation on get, getOrElse, getOrThrow. Instead you should try to use flatMap or map methods as shown DefaultHeap class' merge method.
see get(index) method, diff between mergeDifferentWay_WrongWay and merge methods.

Another Example:
See Toon.java's to see how Toon object is created.


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

    // If you see this code is error prone. it uses Result's get() which can return null also, which can throw NullPointerException.
    // Always avoid to do any operation on result.get(), instead use flatMap or map because they don't evaluate the code inside it if source is Empty.
    public static <A extends Comparable<A>> Heap<A> mergeDifferentWay_WrongWay(Heap<A> first, Heap<A> second) {
        try {
            // operation on get() can be replaced with
            // first.head().flatMap(fhv ->
            //                      second.head().flatMap(shv ->
            //                                            fhv.compareTo(shv) ? ... : ...))
            if (first.head().get().compareTo(second.head().get()) <= 0) {
                return heap(first.head().get(),
                        first.left().get(),
                        mergeDifferentWay_WrongWay(first.right().get(), second));
            }
            return heap(second.head().get(), second.left().get(),
                    mergeDifferentWay_WrongWay(second.right().get(), first));
        } catch (IllegalStateException e) {
            return first.isEmpty() ? second : first;
        }
    }

    // Instead of working on a value of a Result, you can use flatMap and map as shown below.
    // I used flatMap inside flatMap on all the conditions
    public static <A extends Comparable<A>> Heap<A> merge(Heap<A> first, Heap<A> second) {
        // fhv = first->head->value
        // shv = second->head->value
        // flv - first->left->value
        // slv - second->left->value
        // frv - first->right->value
        // srv - second->right->value

        // if first.head() is Result.Empty/Result.Failure then it won't even go inside to evaluate flatMap.
        // flatMap will simply return a default value from getOrElse method
        return first.head().flatMap(
                fhv -> second.head().flatMap(
                        shv -> fhv.compareTo(shv) <= 0
                                ? first.left().flatMap(flv -> first.right().map(frv -> heap(fhv, flv, merge(frv, second))))
                                : second.left().flatMap(slv -> second.right().map(srv -> heap(shv, slv, merge(first, srv))))
                )
        ).getOrElse(first.isEmpty() ? second : first);
    }

    public Heap<A> add(A element) {
        return merge(this, heap(element));
    }

    /*
        Although implemented as a tree, the heap is seen from the user perspective like a priority queue, which means a kind of linked list where the head would always be the smallest element it contains. By analogy, the root element of the tree is called the head, and what remains after having "removed" the head is called the tail.
     */
    @Override
    public Result<Heap<A>> tail() {
        return Result.success(merge(left, right));
    }

    @Override
    public Result<A> get(int index) {
        if (index == 0) {
            return head();
        }

        Result<Heap<A>> tailHeap_Result = tail();

        // This approach is banned. Applying an operation on Result's get, getOrElse, getOrThrow methods is banned because it can result in exception.
        // e.g. below code can throw an exception, if tailHeap_Result.get() returns Result.Empty
        // So, prefer to use flatMap and map methods

        //return tailHeap_Result.get().get(index - 1);

        // better approach
        return tailHeap_Result.flatMap(tailHeap -> tailHeap.get(index - 1));

    }

}
