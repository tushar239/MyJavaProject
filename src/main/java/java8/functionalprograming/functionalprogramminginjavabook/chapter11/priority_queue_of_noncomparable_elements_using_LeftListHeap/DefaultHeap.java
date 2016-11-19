package java8.functionalprograming.functionalprogramminginjavabook.chapter11.priority_queue_of_noncomparable_elements_using_LeftListHeap;

import java8.functionalprograming.functionalprogramminginjavabook.chapter7.Result;

import java.util.Comparator;

/*
    Priority Queue for NonComparable elements:  pg 340


    To insert elements into a priority queue, we must be able to compare their priority.
    However, priority is not always a property of elements. In other words, not all elements that may be compared implement the Comparable interface.
    Elements that do not implement this interface may still be compared using a Comparator.


    Heap<A> is not same as Heap<A extends Comparable>
    So, if elements of Heap are non-comparable, then
    - You need to pass your own Comparator to Heap's constructor.
    - For more convenience, create your own 'static compare(element1, element2, comparator)' method.

*/

public class DefaultHeap<A> extends Heap<A> {

    private final A head;
    private final Heap<A> left;
    private final Heap<A> right;

    private final int length; // same as size of heap (# of elements in a heap)
    private final int rank; // height of the right heap till empty element

    private final Result<Comparator<A>> comparator;

    private DefaultHeap(int length, int rank, Heap<A> left, A head, Heap<A> right,
                        Result<Comparator<A>> comparator) {// passing a Comparator
        this.length = length;
        this.rank = rank;
        this.head = head;
        this.left = left;
        this.right = right;

        this.comparator = comparator;
    }

    @Override
    protected Result<Comparator<A>> comparator() {
        return this.comparator;
    }

    @Override
    public Result<A> head() {
        return Result.success(this.head);
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
    protected int rank() {
        return this.rank;
    }

    @Override
    public int length() {
        return this.length;
    }


    @Override
    public boolean isEmpty() {
        return false;
    }


    // Creating a heap with single element
    public static <A> Heap<A> heap(A element, Result<Comparator<A>> comparator) {
        return new DefaultHeap(1, 1, empty(comparator), element, empty(comparator), comparator);
    }

    protected static <A> Heap<A> heap(A head, Heap<A> first, Heap<A> second) {
        Result<Comparator<A>> comparator = first.comparator()
                .orElse(() -> second.comparator());
        return first.rank() >= second.rank()
                ? new DefaultHeap<A>(first.length() + second.length() + 1, second.rank() + 1, first, head, second, comparator)
                : new DefaultHeap<A>(first.length() + second.length() + 1, first.rank() + 1, second, head, first, comparator);
    }

    /*
     If you see this code is error prone. it uses Result's get() which can return null also, which can throw NullPointerException.
     Always avoid to do any operation on result.get(), instead use flatMap or map because they don't evaluate the code inside it if source is Empty.
     This is called Comprehension Pattern. Learn it. It is very important in Functional Programming.

     pg 382, 383 of Chapter 13 -

         Note that the comprehension pattern is probably one of the most important patterns in functional programming,
         so you really want to master it. Other languages such as Scala or Haskell have syntactic sugar for it, but Java does not.

             public static Result<Tuple<Person, Input>> person(Input input) {
                return input.readInt("Enter ID:")
                          .flatMap(id -> id._2.readString("Enter first name:")
                          .flatMap(firstName -> firstName._2.readString("Enter last name:")
                          .map(lastName -> new Tuple<>(Person.apply(id._1, firstName._1, lastName._1), lastName._2))));

         pseudocode, to something like

             id in input.readInt("Enter ID:")
             firstName in id._2.readString("Enter first name:")
             lastName in firstName._2.readString("Enter last name:")
             return new Tuple<>(Person.apply(id._1, firstName._1, lastName._1), lastName._2))

         Many programmers know this pattern as
              a.flatMap(b -> flatMap(c -> map(d -> getSomething(a, b, c, d))))
         and they often think it is always a series of flatMap ended with a map.
         This is absolutely not the case. Whether it is map or flatMap depends solely upon the return type.
         It often happens that the last method (here, getSomething) returns a bare value. This is why the pattern ends with a map. But if getSomething were to return a context (here, a Result), the pattern would be:
              a.flatMap(b -> flatMap(c -> flatMap(d -> getSomething(a, b, c, d))))

        REMEMBER:
        WHEN YOU HAVE MORE THAN ONE Result OBJECTS AS INPUTS, THEN YOU CAN USE 'Comprehension' PATTERN TO PRODUCE AN OUTPUT.

        Here, you have Result objects of id,firstName,lastName etc to produce a Person object.


    */
    public static <A extends Comparable<A>> Heap<A> mergeDifferentWay_WrongWay(Heap<A> first, Heap<A> second) {
        Result<Comparator<A>> comparator =
                first.comparator().orElse(second::comparator);

        try {
            // operation on get() can be replaced with
            // first.head().flatMap(fhv ->
            //                      second.head().flatMap(shv ->
            //                                            fhv.compareTo(shv) ? ... : ...))
            if (compare(first.head().get(), second.head().get(), comparator) <= 0) {
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
    public static <A> Heap<A> merge(Heap<A> first, Heap<A> second) {
        Result<Comparator<A>> comparator =
                first.comparator().orElse(second::comparator);

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
                        shv -> compare(fhv, shv, comparator) <= 0
                                ? first.left().flatMap(flv -> first.right().map(frv -> heap(fhv, flv, merge(frv, second))))
                                : second.left().flatMap(slv -> second.right().map(srv -> heap(shv, slv, merge(first, srv))))
                )
        ).getOrElse(first.isEmpty() ? second : first);
    }

    private static <A> int compare(A first, A second, Result<Comparator<A>> comparator) {
        return comparator
                .map(comparator1 -> comparator1.compare(first, second))
                .getOrElse(() -> ((Comparable<A>) first).compareTo(second));
        // This method performs a cast of one of its arguments, but we know we don’t risk a ClassCastException to be thrown because we have insured that no heap could be created without a comparator if the type parameter did not extend Comparable.
    }

    public Heap<A> add(A element) {
        return merge(this, heap(element, comparator));
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
