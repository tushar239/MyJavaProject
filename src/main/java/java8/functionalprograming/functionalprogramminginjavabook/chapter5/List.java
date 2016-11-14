package java8.functionalprograming.functionalprogramminginjavabook.chapter5;

import java8.functionalprograming.functionalprogramminginjavabook.chapter12.Tuple;
import java8.functionalprograming.functionalprogramminginjavabook.chapter2.Function;
import java8.functionalprograming.functionalprogramminginjavabook.chapter4.TailCall;
import java8.functionalprograming.functionalprogramminginjavabook.chapter7.Result;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static java8.functionalprograming.functionalprogramminginjavabook.chapter4.TailCall.SupplierContainer;
import static java8.functionalprograming.functionalprogramminginjavabook.chapter4.TailCall.ret;
import static java8.functionalprograming.functionalprogramminginjavabook.chapter4.TailCall.sus;

/**
 * @author Tushar Chokshi @ 9/9/16.
 */
/*

There are different types of lists available based on
- Access - Some lists will be accessed from one end only, others will be accessed from both ends. Some will be written from one end and read from the other end. Finally, some lists may allow access to any element using its position in the list; the position is also called its index.
- Ordering - In some lists, the elements will be read in the same order in which they have been inserted. This kind of structure is said to be FIFO (First In, First Out) In others, the order of retrieval will be the inverse of the order of insertion (LIFO, or Last In, First Out). Finally, some lists will allow retrieving the elements in a completely different order.
- Implementation - Access type and ordering are concepts strongly related to the implementation we choose for the list. If we choose to represent the list by linking each element to the next, we will get a completely different result, from the access point of view, from an implementation based on an indexed array. Or if we choose to link each element to the next as well as to the previous one, we will get a list that can be accessed from both ends.

Trading time against memory space, and time against complexity
    Priority Queue : is a data structure that uses Binary Heap algorithm to quickly find out min/max element from an array.
    You can look at BinaryHeap.java in algorithms project.
    It saves a lot of time for searching min/max value. you can search min/max element in an array in O(1) time.
    It takes O(log n) for insertion/deletion.
    It is not in-place data structure. It also requires an auxiliary array.


There are 2 ways to change the data structure.
- In-Place mutation
- Creating a new data structure out of original one (Auxiliary data structure) and mutating that instead of mutating the
original one.

If we mutate the original data structure, then it can’t be shared by multiple threads. Solution is to use auxiliary data structure.
Now you can make an argument that creating an auxiliary data structure might be slow and memory consuming.
But that is not always true. See Figure 5.2 of the book, in which, new elements are added to auxiliary singly linked list and then it is linked
back to original one. This way, you are not consuming a lot of memory and it is faster also.

Mutable List vs Immutable List (Why immutable list?) -

    By now, you know that recursion is a part of Functional programming.
    If you try to make mutable list recursive, you have to pay a cost of memory usage.
    e.g.
    public static Integer sumRecursively(List<Integer> list) {

        if (list == null || list.isEmpty()) return 0;

        Integer ele0 = list.get(0);
        list = list.subList(1, list.size()); // expensive as it creates a new nilList for every recursive call
        return ele0 + sumRecursively(list); // same as head(nilList) + sumRecursive(tail(nilList))
    }

    If you see above code where list is considered mutable, you need to create a sublist every time you recurse a simple sumRecursively method.

    If you use immutable list, this becomes very easy.

    public static Integer sumRecursively(List<Integer> list) {
        if (list == null || list.isEmpty()) return 0;

        Integer ele0 = list.head();
        list = list.tail(); // you are not creating a new list here...
        return ele0 + sumRecursively(list);
    }


    First time when you create an immutable list from an array, you need to create sub array (see listRecursive(A... a) method).
    But after that all subsequent operations on the list will be super efficient as you do not need to create sublist for any recursive operation on the list.

    List<I> is a super class.
        |
     -----------
    |           |
    Nil<I>     Cons<I>
                - A head
                - List<I> tail
Immutability avoids concurrency problems:

    Functional trees like have the advantage of immutability, which allows using them in multithreaded environments without bothering with locks and synchronization.

Folding

    Folding is like traversing a data structure like list and tree and during traversal applying an operation of its elements and creating a new data structure out of it.

    Operation that creates output from the operation on two elements of a list is called 'folding' as you learned in chapter 3.
    It is same as Java 8 Stream's reduce(...) operation.

    The fold operation is not specific to arithmetic computations. You can use a fold to transform a list of characters into a string. In such condition, A and B are two different types: Char and String. But you can also use a fold to transform a list of strings into a single string.

    In any folding operation, you need input list, identity and BiFunction that takes two inputList(one identity and another element from input list)


    What is the difference between foldLeft and foldRight?

        In foldLeft, you apply an operation first on identity and first element of the list and then moving further in the list.

        In foldRight, you move further till the last element of the list using recursion and then applying an operation on identity and last element and then second last element and so on.


 */

// This is a Singly LinkedList

public abstract class List<I> {

    private List() {
    }

    public static final List NIL = new Nil();

    public abstract I head();

    public abstract List<I> tail();

    public abstract boolean isEmpty();

    public static <I> List<I> nilList() { // same as list() method of book
        return NIL;
    }

    public static <I> List<I> consList(I head) {
        return new Cons<I>(head, nilList());
    }

    public static <I> List<I> consList(I head, List<I> tail) {
        return new Cons<I>(head, tail);
    }

    // set a new head of a list and return a new list, do not change original list
    public abstract List<I> setHead(I i); // same as cons(A h) method of book

    // replace a head of a list and return a new list, do not change original list
    public abstract List<I> replaceHead(I h);  // same as setHead(A h) of book

    // new list should simply point to the element n of the original list
    public abstract List<I> dropRecursively(int n);

    public abstract List<I> dropTailRecursively(int n, List<I> result);

    public abstract List<I> dropTailRecursivelyJava8Style(int n);

    // drop an element from the list, if it matches passed condition. If first element in the list matches the condition then remove it, otherwise just return the entire list back.
    public abstract List<I> dropWhile(Function<I, Boolean> f);

    public List<I> concat(List<I> list2) {
        return concat(this, list2);
    }

    // concatenate two lists
    // Reduce problem by one method
    public static <I> List<I> concat(List<I> list1, List<I> list2) {
        if ((list1 == null || list1.isEmpty()) && (list2 != null)) return list2;
        return new Cons<>(list1.head(), concat(list1.tail(), list2));
    }

    // Reversing a list
    public List<I> reverse() {
        return reverse(this, list()).eval();
    }

    private TailCall<List<I>> reverse(List<I> list, List<I> acc) {
        return list.isEmpty()
                ? TailCall.getFinalValueContainer(acc)
                : TailCall.getSupplierContainer(() -> reverse(list.tail(), new Cons<>(list.head(), acc)));
    }

    // Reversing a reversed list to bring back original list
    public List<I> doubleReverse() { // same as init() of a book
        return reverse().tail().reverse();
    }


    public static <I> List<List<I>> splitListAtIteratively(List<I> inputList, int i) {
        return splitListAtIteratively(inputList.reverse(), i, nilList());
    }

    private static <I> List<List<I>> splitListAtIteratively(List<I> inputList, int i, List<List<I>> acc) {
        if (i == 0 || inputList.isEmpty()) return acc;

        // if you want to convert this iterative loop into recursion, then see that there are two loops
        // outer while loop can be replaced by recursive call of this method
        // for inner for loop, you need to create another recursive method and its output becomes an input to this method's recursive call
        // this inner for loop is modifying two input parameters (inputList and subList). So, converted recursive method have to return two values (so a Tuple<modified inputList, modified subList>)
        while (!inputList.isEmpty()) {
            List<I> subList = nilList();
            for (int index = 0; index < i && !inputList.isEmpty(); index++) {
                subList = new Cons<I>(inputList.head(), subList);
                inputList = inputList.tail();
            }
            acc = new Cons<>(subList, acc);
        }

        return acc;

    }

    private static <I> List<List<I>> splitListAtRecursiveTailRecursive(List<I> inputList, int i) {
        return splitListAtRecursiveTailRecursive(inputList.reverse(), i, nilList());
    }

    // see how above iterative method is converted to this recursive method
    private static <I> List<List<I>> splitListAtRecursiveTailRecursive(List<I> inputList, int i, List<List<I>> acc) {
        if (i == 0 || inputList.isEmpty()) return acc;

        Tuple<List<I>, List<I>> inputListSubListTuple = listCopy(inputList, i, nilList());
        List<I> newInputList = inputListSubListTuple._1;
        List<I> subList = inputListSubListTuple._2;

        return splitListAtRecursiveTailRecursive(newInputList, i, new Cons<List<I>>(subList, acc));
    }

    private static <I> Tuple<List<I>, List<I>> listCopy(List<I> inputList, int numberOfElementsToCopy, List<I> newList) {
        if (inputList.isEmpty() || numberOfElementsToCopy == 0) return new Tuple<>(inputList, newList);


        return listCopy(inputList.tail(),
                numberOfElementsToCopy - 1,
                new Cons<I>(inputList.head(), newList));

    }

    private static class Nil<I> extends List<I> { // It is private, means it can be instantiated only from super class' method
        private Nil() {
        }

        public I head() {
            throw new IllegalStateException("head called en empty nilList");
        }

        public List<I> tail() {
            throw new IllegalStateException("tail called en empty nilList");
        }

        public boolean isEmpty() {
            return true;
        }

        @Override
        public List<I> replaceHead(I h) {  // same as setHead(A h) of book
            throw new IllegalStateException("replaceHead called on empty list");
        }

        @Override
        public List<I> dropRecursively(int n) {
            return this;
        }

        @Override
        public List<I> dropTailRecursively(int n, List<I> result) {
            return this;
        }

        @Override
        public List<I> dropTailRecursivelyJava8Style(int n) {
            return this;
        }

        @Override
        public List<I> dropWhile(Function<I, Boolean> f) {
            return this;
        }

        public List<I> setHead(I i) { // same as cons(A h) method of book
            return new Cons(i, this);
        }

        @Override
        public String toString() {
            return "Nil{}";
        }
    }

    public static class Cons<I> extends List<I> {
        private final I head;
        private final List<I> tail;

        public Cons(I head, List<I> tail) { // It is private, means it can be instantiated only from super class' method
            this.head = head;
            this.tail = tail;
        }

        public I head() {
            return head;
        }

        public List<I> tail() {
            return tail;
        }

        public boolean isEmpty() {
            return false;
        }

        @Override
        public List<I> replaceHead(I h) { // same as setHead(A h) of book
            return new Cons<>(h, this.tail());
        }

        public static <I> List<I> replaceHead(List<I> list, I h) { // same as setHead((List<I> list, A h) of book
            return list.replaceHead(h);
        }

        // set a new head of a list and return a new list, do not change original list
        public List<I> setHead(I i) { // same as cons(A h) method of book
            return new Cons<>(i, this);
        }

        public static <I> List<I> setHead(List<I> list, I i) {  // same as cons(List<I> list, A h) method of book
            return new Cons<>(i, list);
        }

        // new list should simply point to the element n of the original list
        public static <I> List<I> dropIteratively(List<I> list, int n) {
            if (list == null || list.isEmpty()) return list;
            if (n <= 0) return list;

            List<I> resultingList = list;
            for (int i = 0; i <= n; i++) {
                resultingList = resultingList.tail();
            }

            return resultingList;
        }

        // Reducing the problem by 1
        // 0 -> 1 -> 2 -> 3 -> 4 -> 5
        // dropRecursively(2)
        // If you reduce the problem by one, you need to call dropRecursively(n-1), which will give you a list 2 -> 3 -> 4 -> 5. Now to get the final result, you need to do list = list.tail(), to get 3 -> 4 -> 5
        // But is this tail-recursive?
        // no because recursion is not the final operation
        @Override
        public List<I> dropRecursively(int n) {
            if (this.isEmpty()) return this;
            if (n < 0) return this;

            List<I> aList = dropRecursively(n - 1);
            return aList.tail();
        }

        // To make above method tail recursive, you need to pass
        @Override
        public List<I> dropTailRecursively(int n, List<I> result) {
            if (result.isEmpty()) return result;
            if (n < 0) return result;

            List<I> result1 = dropTailRecursively(n - 1, result.tail());
            return result1;
        }

        @Override
        public List<I> dropTailRecursivelyJava8Style(int n) {
            return dropTailRecursivelyJava8Style(n, this).eval();
        }

        private TailCall<List<I>> dropTailRecursivelyJava8Style(int n, List<I> result) {
            if (result.isEmpty()) return TailCall.getFinalValueContainer(result);
            if (n < 0) return TailCall.getFinalValueContainer(result);

            SupplierContainer<List<I>> result1 = TailCall.getSupplierContainer(() -> dropTailRecursivelyJava8Style(n - 1, result.tail()));
            return result1;
        }

        @Override
        public List<I> dropWhile(Function<I, Boolean> f) {
            return dropWhile(f, this).eval();
        }

        private TailCall<List<I>> dropWhile(Function<I, Boolean> f, List<I> result) {
            if (result.isEmpty()) return TailCall.getFinalValueContainer(result);
            if (f.apply(result.head())) return TailCall.getSupplierContainer(() -> dropWhile(f, result.tail()));
            return TailCall.getFinalValueContainer(result);
        }

        @Override
        public String toString() {
            return "Cons{" +
                    "head=" + head +
                    ", tail=" + tail +
                    '}';
        }


    }


    // Creating an immutable list from array of elements.
    /*
         0    Con object containing head element A and tail as remaining list
       A  \
           1
         B  |
            2
          C  |
              3
            D  |
              null

        Cons(A)
       A  \
          Cons(B)
         B  |
           Cons(C)
          C  |
             Cons(D)
            D  |
              Nil

       Build a list from end (Nil).
     */
    @SafeVarargs
    public static <I> List<I> list(I... a) {
        List<I> headNode = nilList(); // empty (NIL) nilList
        for (int i = a.length - 1; i >= 0; i--) { // Iterate an array in reverse direction
            Cons<I> newHeadNode = new Cons<>(a[i], headNode);
            headNode = newHeadNode;
        }
        return headNode;
    }

    public static <I> List<I> listRecursive(I... i) {
        if (i == null || i.length == 0) return nilList();
//        if (a.length == 1) return new Cons<>(a[0], nilList());

        return new Cons<>(i[0], listRecursive(Arrays.copyOfRange(i, 1, i.length)));

    }

    public static <I> List<I> listTailRecursive(List<I> result, I... i) {
        if (i == null || i.length == 0) return result;
//        if(a.length == 1) {
//            result = new Cons<I>(a[0], result);
//            return result;
//        }
        result = new Cons<>(i[i.length - 1], result);
        return listTailRecursive(result, Arrays.copyOfRange(i, 0, i.length - 1));
    }


    @SafeVarargs
    public static <I> List<I> listTailRecursionJava8Style(I... is) {
        return listTailRecursionJava8Style(nilList(), is).eval();
    }

    public static <I> List<I> listCopy(List<I> inputList) {
        List<I> newList = nilList();
        if (inputList.isEmpty()) return newList;

        return listCopy(inputList.reverse(), newList);
    }

    private static <I> List<I> listCopy(List<I> inputList, List<I> result) {
        if (inputList.isEmpty()) return result;
        return listCopy(inputList.tail(), new Cons<I>(inputList.head(), result));
    }

    /*
        Be sure, however, not to use this implementation, since it is 10,000 times slower than the imperative one. This is a good example of when not to be blindly functional.
        Note that recursion is not the problem. Recursion using using TailCall is as fast as iteration. The problem here is the copyOfRange method, which is very slow.
     */
    public static <I> TailCall<List<I>> listTailRecursionJava8Style(List<I> acc, I[] is) {
        return is.length == 0
                ? ret(acc)
                : sus(() -> listTailRecursionJava8Style(new Cons<>(is[is.length - 1], acc), Arrays.copyOfRange(is, 0, is.length - 1)));
    }


    public static <I> List<I> replaceHead(List<I> list, I h) {
        if (list == null || list.isEmpty()) {
            throw new IllegalStateException("setHead called on an empty list");
        }
        return new Cons<>(h, list.tail());
    }

    /*
    As described above, this recursive sumRecursively method is very different than recursive sumRecursively method of mutable list in CollectionUtilities.java.
    This recursive sumRecursively method does not create a new list at the time of recursion. And so, importance of immutable list in Functional Programming is more because Recursion in Functional Programming is very normal. To make recursion better, it is better to use immutable list.
     */
    /*public static Integer sumRecursively(List<Integer> list) {
        if (list == null || list.isEmpty()) return 0;

        Integer ele0 = list.head();
        list = list.tail(); // you are not creating a new list here...
        return ele0 + sumRecursively(list);
    }*/
    public static Integer sumRecursively(List<Integer> list) {
        return list.isEmpty()
                ? 0
                : list.head() + sumRecursively(list.tail());
    }

    public static Double productRecursively(List<Double> list) {
        return list.isEmpty()
                ? 1
                : list.head() * productRecursively(list.tail());
    }

    /*
    Operation that creates output from the operation on two elements of a list is called 'folding' as you learned in chapter 3.
    It is same as Java 8 Stream's reduce(...) operation.

    The fold operation is not specific to arithmetic computations. You can use a fold to transform a list of characters into a string. In such condition, A and B are two different types: Char and String. But you can also use a fold to transform a list of strings into a single string.

    In any folding operation, you need input list, identity and BiFunction that takes two inputList(one identity and another element from input list)
    */
    public static <I, O> O foldLeftIteratively(List<I> inputList, O identity, Function<I, Function<O, O>> operation) {
        if (inputList == null || inputList.isEmpty()) return identity;

        O output = identity;
        while (inputList.tail() != NIL) {
            output = operation.apply(inputList.head()).apply(output);
            inputList = inputList.tail();
        }

        return output;
    }

    public static <I> List<I> listFromListUsingFoldLeft(List<I> inputList) {
        List<I> identityOrResult = List.nilList();
        return foldLeftTailRecursiveJava8Style(inputList, identityOrResult, (List<I> identity) -> (I input) -> new Cons<I>(input, identity));

    }

    /*
        One of the most important methods in Functional Programming is foldLeft and foldRight.
        folding is nothing but traversing, but while traversing, you can change/filter the value of visited element and create a same or different data structure with those elements.
     */
    public <O> O foldLeft(O identity, Function<I, Function<O, O>> operation) {
        return foldLeftTailRecursive(this, identity, operation);
    }

    // Using the concept of Reducing a problem by one
    public static <I, O> O foldLeftTailRecursive(List<I> inputList, O identity, Function<I, Function<O, O>> operation) {
        if (inputList == null || inputList.isEmpty()) return identity;

        O output = operation.apply(inputList.head()).apply(identity);
        return foldLeftTailRecursive(inputList.tail(), output, operation); // reducing a problem by 1. Calling recursion on inputList.tail()
    }


    // This is not a Tail-Recursive because calculated output is not passed as an argument to recursive method call.
    // seeing different way - call to recursive method is not the last statement
    public static <I, O> O foldLeft_NonTailRecursive(List<I> inputList, O identity, Function<I, Function<O, O>> operation) {
        if (inputList == null || inputList.isEmpty()) return identity;

        O outputOfRemainingList = foldLeft_NonTailRecursive(inputList.tail(), identity, operation);// reducing a problem by 1. Calling recursion on inputList.tail()
        return operation.apply(inputList.head()).apply(outputOfRemainingList);
    }


    public static <I, O> O foldLeftTailRecursive_LazilyEvaluatingTheOutput(List<I> inputList, Supplier<O> identity, Function<I, Function<Supplier<O>, O>> operation) {
        if (inputList == null || inputList.isEmpty()) return identity.get();

        Supplier<O> output = () -> operation.apply(inputList.head()).apply(identity);
        return foldLeftTailRecursive_LazilyEvaluatingTheOutput(inputList.tail(), output, operation); // reducing a problem by 1. Calling recursion on inputList.tail()
    }

    public static <I, O> O foldLeftTailRecursiveJava8Style(List<I> inputList, O identity, Function<O, Function<I, O>> operation) {
        TailCall<O> output = foldLeftTailRecursiveJava8Style_(inputList, identity, operation);
        return output.eval();
    }

    private static <I, O> TailCall<O> foldLeftTailRecursiveJava8Style_(List<I> inputList, O identity, Function<O, Function<I, O>> operation) {
        if (inputList == null || inputList.isEmpty()) return TailCall.getFinalValueContainer(identity);

        O output = operation.apply(identity).apply(inputList.head());
        return TailCall.getSupplierContainer(() -> foldLeftTailRecursiveJava8Style_(inputList.tail(), output, operation)); // reducing a problem by 1. Calling recursion on list.tail()
    }

    public int length() {
        return foldLeftTailRecursiveJava8Style(this, 0, x -> y -> x + 1);
    }


    // foldRight method from book
    public static <I, O> O foldRight(List<I> inputList, O identity, Function<I, Function<O, O>> f) {
        return foldRight_(identity, inputList.reverse(), identity, f).eval();
    }

    // foldRight_ method from book
    private static <I, O> TailCall<O> foldRight_(O acc, List<I> inputList,
                                                 O identity,
                                                 Function<I, Function<O, O>> f) {
        return inputList.isEmpty()
                ? TailCall.getFinalValueContainer(acc)
                : TailCall.getSupplierContainer(() -> foldRight_(f.apply(inputList.head()).apply(acc), inputList.tail(), identity, f));
    }

    private static <I> List<I> listFromListUsingFoldRight(List<I> inputList) {
        List<I> identityOrResult = List.nilList();// in case of Tail-Recursion, you pass a Result (that is nothing but the identity)
        return foldRightRecursive(inputList, identityOrResult, (I input) -> (List<I> identity) -> new Cons<I>(input, identity));
    }

    // My foldRight implementation (not from book) - My foldRight is not not Tail-Recursive
    /*
    What is the difference between foldLeft and foldRight?

    In foldLeft, you start from the left(start) of the inputList. So you apply an operation first on identityOrResult and first element of the list and then moving further in the list.
    In foldRight, you need to start from right(end) of the inputList. So you need to go to the end of the inputList using recursion and then then apply an operation on identityOrResult and last element and then second last element and so on.

    */
    // This is not Tail-Recursive because you are not calculating the final output every time you put a recursive method call in the stack.
    // To get final output, you need to pop entire stack.
    private static <I, O> O foldRightRecursive(List<I> inputList, O identityOrResult, Function<I, Function<O, O>> operation) {
        if (inputList == null || inputList.isEmpty()) return identityOrResult;

        O output = foldRightRecursive(inputList.tail(), identityOrResult, operation);
        return operation.apply(inputList.head()).apply(output);
    }

    // Write foldRight in terms of foldLeft.
    public static <I, O> O foldRightViaFoldLeft(List<I> list, O identity, Function<I, Function<O, O>> f) {
        return list.reverse().foldLeftTailRecursive(list, identity, listElement -> resultFromRemainingListElements -> f.apply(listElement).apply(resultFromRemainingListElements));
    }

    // Write foldLeft in terms of foldRight.
    public static <I, O> O foldLeftViaFoldRight(List<I> list, O identity, Function<I, Function<O, O>> f) {
        return List.foldRightRecursive(list.reverse(), identity, listElement -> resultFromRemainingListElements -> f.apply(listElement).apply(resultFromRemainingListElements));
    }


    public static <I, O> Function<I, O> mapAnElement(Function<I, O> f) {
        return input -> f.apply(input);
    }

    public static <I, O> O mapAnElement(I input, Function<I, O> f) {
        return f.apply(input);
    }

    // convert List<I> to List<O> using map function
    public static <I, O> List<O> mapListIteratively(List<I> inputList, Function<I, O> f) {
        if (inputList.isEmpty()) return List.<O>nilList();

        inputList = inputList.reverse();

        List<O> emptyList = List.nilList();
        List<O> initial = new Cons<O>(f.apply(inputList.head()), emptyList);

        inputList = inputList.tail();

        while (!inputList.isEmpty()) { // same as inputList is not Nil list
            I head = inputList.head();
            inputList = inputList.tail();
            O convertedHead = f.apply(head);
            initial = new Cons<O>(convertedHead, initial);

        }
        return initial;

    }

    public <O> List<O> map(Function<I, O> operation) {
        return mapListTailRecursively(this, operation);
    }


    public static <I, O> List<O> mapArrayToList(I[] inputArray, Function<I, O> operation) {
        List<O> result = nilList(); // identityList
        for (I input : inputArray) {
            result = new Cons<>(operation.apply(input), result);
        }
        return result;
    }

    // java8 style
    public static <I, O> java.util.List<O> mapArrayToList_java8_style(I[] inputArray, java.util.function.Function<I, O> operation) {
        return Arrays.stream(inputArray).map(operation).collect(Collectors.toList());
    }

    public static <I, O> List<O> flatMapArrayToList(I[] inputArray, Function<I, List<O>> operation) {
        List<O> result = nilList(); // identityList
        for (I input : inputArray) {
            result = List.concat(result, operation.apply(input));
        }
        return result;
    }


    // e.g. commaSeparatedStringInput = id:3,firstName:Tushar,lastName:Chokshi
    // This method returns
    // Result object of List[{id=3}, {firstName=Tushar}, {lastName=Chokshi}]
    public static Result<List<Map<String, Result<String>>>> mapCommaSeparatedStringToListOfMap(String commaSeparatedStringInput) {
        String[] splits = commaSeparatedStringInput.split(","); // splits[0] = id:3, splits[1] = firstName:Tushar, splits[2] = lastName:Chokshi


        return Result.success(
                mapArrayToList(splits, split1 -> {
                    Map<String, Result<String>> myMap = new HashMap();
                    String[] colonSeparatedSplits = split1.split(":");

                    String key = colonSeparatedSplits[0];
                    Result<String> value = Result.empty();

                    if (colonSeparatedSplits.length == 2) {
                        value = Result.success(colonSeparatedSplits[1]);
                    }
                    myMap.put(key, value);

                    return myMap;

                }));
    }


    // same as map function of a book (pg 156)
    public static <I, O> List<O> mapListTailRecursively(List<I> inputList, Function<I, O> operation) {
        List<O> identityList = List.nilList();
        return foldRightRecursive(inputList, identityList, (I input) -> (List<O> identityList1) -> new Cons<O>(operation.apply(input), identityList1));
        // or
        //return foldRight(inputList, List.<O>list(), (A a) -> (List<O> b) -> new Cons<O>(operation.apply(a),b));
    }

    // This is a map method that converts List<I> to List<List<O>>
/*
    public static <I, O> List<List<O>> map(List<I> inputList, Function<I, List<O>> f) {
        List<List<O>> identityList = new Cons<>(nilList(), new Cons<>(nilList(),nilList()));
        return foldRightRecursive(inputList, identityList, (List<List<O>> identityList1) -> (I input) -> new Cons<>(f.apply(input), identityList1));
    }
*/

    public <O> List<O> flatMap(Function<I, List<O>> operation) {
        return flatMap(this, operation);
    }

    // applying to each element of a List<I> to a function that converts from I to List<O> (pg 157)
    // flatMap is nothing but using a function that takes I and generates List<O> instead of O.
    // and then concatenating this List<O> with main List
    // By doing this, you are not creating List<List<O>>, but just List<O>
    // To test it, you can pass List<List<I>> as an inputList. map will return you List<List<O>>, flatMap can return you List<O>
    public static <I, O> List<O> flatMap(List<I> inputList, Function<I, List<O>> operation) {
        List<O> identityList = List.<O>nilList();
        return foldRightRecursive(inputList, identityList, (I input) -> (List<O> identityList1) -> List.concat(identityList1, operation.apply(input)));
        // or
        //return foldRight(inputList, identityList, input -> identityList1 -> concat(identityList1, operation.apply(input)));
    }

    // method for flattening a list of lists into a list containing all elements of each contained list.
    public static <I> List<I> flatten(List<List<I>> listOfInputLists) {
        List<I> identityList = List.nilList();
        return foldRightRecursive(listOfInputLists, identityList, (List<I> identityList1) -> (List<I> input) -> List.concat(identityList1, input));
        // or
        // return foldRight(listOfInputLists, identity, inputList -> identityList -> List.concat(identityList, inputList));
    }

    // filter that removes from a list the elements that do not satisfy a given predicate. (pg 157)
    public static <I> List<I> filter(List<I> inputList, Function<I, Boolean> operation) {
        List<I> identityList = List.nilList(); // new empty list
        return foldRightRecursive(inputList, identityList, (I input) -> (List<I> identityList1) -> operation.apply(input) ? new Cons<I>(input, identityList1) : identityList1);
    }
    /* Too hard - I didn't try to understand (pg 158)
    public static <I> List<I> filterViaFlatMap(List<I> list,
                                               Function<I, Boolean> p) {
        return list.flatMap((A a) -> p.apply(a) ? List.list(a) : List.<I>list());
    }

    public static <I> List<I> flattenViaFlatMap(List<List<I>> list) {
        return list.flatMap(x -> x);
    }
    */


    // pg. 250
    /*
        Parallel processing of a list. Imitating list.stream().parallel()..... of Java 8
     */
    public static <I, O> O parallelFoldLeft(List<I> inputList, O identity, Function<I, Function<O, O>> accumulator,
                                            ExecutorService executorService,
                                            Function<O, Function<O, O>> combiner) {

        // split an inputList into 3 sublists
        List<List<I>> subLists = splitListAtRecursiveTailRecursive(inputList, 3);

        // converting each subList (List<I>>) into Future<O> using list.foldLeft(identity, accumulator)
        List<Future<O>> futureList = subLists.map(subList -> {
                    Callable<O> callable = () -> subList.foldLeft(identity, accumulator);
                    Future<O> future = executorService.submit(callable);
                    return future;
                }
        );


        // Each Future is an output for each subList.
        // Retrieve results from these futures
        List<O> results = futureList.map((Future<O> future) -> {
                    try {
                        O o = future.get();
                        return o;
                    } catch (InterruptedException | ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                }
        );


        // combine the ouptuts for all subLists using combiner
        return results.foldLeft(identity, combiner);
        // if you want to return a Result, you can "return Result.success(results.foldLeft(identity, combiner))"
        // and wrap entire code by try-catch, catch returning "return Result.failure(e)"
    }

    public static void main(String[] args) {
        {
            List<Integer> ex1 = nilList();
            List<Integer> ex2 = list(1);
        }

        // creating a new list
        System.out.println("Creating a new list ...");
        {
            System.out.println("Imperative approach: " + list(1, 2)); //Cons{head=1, tail=Cons{head=2, tail=Nil{}}}

            System.out.println("Recursive approach: " + listRecursive(1, 2));// Cons{head=1, tail=Cons{head=2, tail=Nil{}}}

            System.out.println("Tail-Recursive approach: " + listTailRecursive(nilList(), 1, 2));//Cons{head=1, tail=Cons{head=2, tail=Nil{}}}

            List<Integer> result = listTailRecursionJava8Style(nilList(), new Integer[]{1, 2}).eval();
            System.out.println("Tail-Recursive using Java8 style approach: " + result); // Cons{head=1, tail=Cons{head=2, tail=Nil{}}}

            List<Integer> newList = listCopy(list(1, 2));
            System.out.println("New List from List: " + newList);
        }
        System.out.println();

        // set a new head in the list
        System.out.println("Set a new head in the list...");
        {
            List<Integer> inputList = list(1, 2);
            List<Integer> result = inputList.setHead(3);
            System.out.println("Result after setting new head: " + result); // Cons{head=3, tail=Cons{head=1, tail=Cons{head=2, tail=Nil{}}}}
        }
        System.out.println();

        // drop a sublist till n'th index
        System.out.println("Drop a sublist till n'th index...");
        {
            List<Integer> inputList = list(0, 1, 2, 3, 4, 5);
            System.out.println("Drop list from inputList recursively: " + inputList.dropRecursively(2)); // Cons{head=3, tail=Cons{head=4, tail=Cons{head=5, tail=Nil{}}}}
            System.out.println("Drop list from inputList tail-recursively: " + inputList.dropTailRecursively(2, inputList)); // Cons{head=3, tail=Cons{head=4, tail=Cons{head=5, tail=Nil{}}}}
            System.out.println("Drop list from inputList tail-recursively Java 8 style: " + inputList.dropTailRecursivelyJava8Style(2)); // Cons{head=3, tail=Cons{head=4, tail=Cons{head=5, tail=Nil{}}}}

        }
        System.out.println();

        // dropWhile head matches the condition
        System.out.println("Drop while condition matches...");
        {
            List<Integer> inputList = list(0, 1, 2, 3, 4, 5);
            System.out.println("Drop while condition matches: " + inputList.dropWhile((head) -> head == 2)); // Cons{head=5, tail=Cons{head=4, tail=Cons{head=3, tail=Cons{head=2, tail=Cons{head=1, tail=Cons{head=0, tail=Nil{}}}}}}}
        }
        System.out.println();

        // Reverse a list
        System.out.println("Reverse a list...");
        {
            List<Integer> inputList = list(0, 1, 2, 3, 4, 5);
            System.out.println("Reverse a list tail-recursively Java 8 style: " + inputList.reverse()); // Reverse a list tail-recursively Java 8 style: Cons{head=5, tail=Cons{head=4, tail=Cons{head=3, tail=Cons{head=2, tail=Cons{head=1, tail=Cons{head=0, tail=Nil{}}}}}}}
            System.out.println("Double reverse: " + inputList.doubleReverse()); // Cons{head=0, tail=Cons{head=1, tail=Cons{head=2, tail=Cons{head=3, tail=Cons{head=4, tail=Nil{}}}}}}
        }
        System.out.println();

        // sumRecursively of immutable list
        System.out.println("Sum of immutable list...");
        {
            List<Integer> inputList = list(1, 2, 3, 4, 5);
            System.out.println("Sum of immutable list recursively: " + List.<Integer>sumRecursively(inputList));// 15
        }
        System.out.println();

        // Folding Left example
        System.out.println("Folding a list from left example...");
        {
            List<Integer> inputList = list(1, 2, 3, 4, 5);
            System.out.println("sum of list elements: " + List.foldLeftTailRecursiveJava8Style(inputList, 0, inputListElement -> resultFromRemainingListElements -> inputListElement + resultFromRemainingListElements)); // 15
            System.out.println("product of list elements: " + List.foldLeftTailRecursiveJava8Style(inputList, 1, a -> b -> a * b)); // 120

            List<Integer> identity = List.nilList();
            System.out.println("creating a new List from input list: " + List.foldLeftTailRecursive(inputList, identity, inputListElement -> resultFromRemainingListElements -> resultFromRemainingListElements.setHead(inputListElement)));// Cons{head=5, tail=Cons{head=4, tail=Cons{head=3, tail=Cons{head=2, tail=Cons{head=1, tail=Nil{}}}}}}

            System.out.println("Finding a length of a list: " + inputList.length()); // 5
        }
        System.out.println();

        System.out.println("Folding a list from right example...");
        {
            List<Integer> inputList = list(1, 2, 3, 4, 5);
            List<Integer> identity = List.nilList();

            // "o -> i -> o.setHead(i)" is same as "new Cons(i, o)"
            //System.out.println("creating a new List from input list: " + List.foldRightRecursive(inputList, identity, x -> y -> x.setHead(y))); // Cons{head=1, tail=Cons{head=2, tail=Cons{head=3, tail=Cons{head=4, tail=Cons{head=5, tail=Nil{}}}}}}
            System.out.println("creating a new List from input list: " + List.listFromListUsingFoldRight(inputList)); // Cons{head=1, tail=Cons{head=2, tail=Cons{head=3, tail=Cons{head=4, tail=Cons{head=5, tail=Nil{}}}}}}

        }
        System.out.println();

        System.out.println("Mapping List of elements to some other type of elements...");
        {
            List<Integer> inputList = list(1, 2, 3, 4, 5);
            System.out.println("Mapping Iteratively: " + List.mapListIteratively(inputList, inputListElement -> inputListElement + "hi")); // Cons{head=1hi, tail=Cons{head=2hi, tail=Cons{head=3hi, tail=Cons{head=4hi, tail=Cons{head=5hi, tail=Nil{}}}}}}
            System.out.println("Mapping using foldRight: " + List.mapListTailRecursively(inputList, inputListElement -> inputListElement + "hi"));       // Cons{head=1hi, tail=Cons{head=2hi, tail=Cons{head=3hi, tail=Cons{head=4hi, tail=Cons{head=5hi, tail=Nil{}}}}}}

        }
        System.out.println();

        System.out.println("Difference between map and flatMap...");
        {
            List<List<Integer>> input = list(list(1, 2, 3, 4, 5), list(6, 7));

            // output from Map is List<List<O>>
            List<List<String>> outputFromMap = input.map(subList -> subList.map(subListElement -> subListElement + "hi"));
            System.out.println(outputFromMap);// Cons{head=Cons{head=1hi, tail=Cons{head=2hi, tail=Cons{head=3hi, tail=Cons{head=4hi, tail=Cons{head=5hi, tail=Nil{}}}}}}, tail=Cons{head=Cons{head=6hi, tail=Cons{head=7hi, tail=Nil{}}}, tail=Nil{}}}

            // output from Map is List<O>
            List<String> outputFromFlatMap = input.flatMap(subList -> subList.map(subListElement -> subListElement + "hi"));
            System.out.println(outputFromFlatMap);// Cons{head=6hi, tail=Cons{head=7hi, tail=Cons{head=1hi, tail=Cons{head=2hi, tail=Cons{head=3hi, tail=Cons{head=4hi, tail=Cons{head=5hi, tail=Nil{}}}}}}}}
        }

        System.out.println();

        System.out.println("Splitting list...");
        {
            List<Integer> inputList = list(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);


            List<List<Integer>> listList = splitListAtIteratively(inputList, 3);
            System.out.println("length of list of lists: " + listList.length()); // 4
            System.out.println("list of lists: " + listList); // Cons{head=Cons{head=1, tail=Nil{}}, tail=Cons{head=Cons{head=2, tail=Cons{head=3, tail=Cons{head=4, tail=Nil{}}}}, tail=Cons{head=Cons{head=5, tail=Cons{head=6, tail=Cons{head=7, tail=Nil{}}}}, tail=Cons{head=Cons{head=8, tail=Cons{head=9, tail=Cons{head=10, tail=Nil{}}}}, tail=Nil{}}}}}

            List<List<Integer>> listList1 = splitListAtRecursiveTailRecursive(inputList, 3);
            System.out.println("length of list of lists: " + listList1.length()); // 4
            System.out.println("list of lists: " + listList1);// Cons{head=Cons{head=1, tail=Nil{}}, tail=Cons{head=Cons{head=2, tail=Cons{head=3, tail=Cons{head=4, tail=Nil{}}}}, tail=Cons{head=Cons{head=5, tail=Cons{head=6, tail=Cons{head=7, tail=Nil{}}}}, tail=Cons{head=Cons{head=8, tail=Cons{head=9, tail=Cons{head=10, tail=Nil{}}}}, tail=Nil{}}}}}

        }
        System.out.println();

        System.out.println("Parallel processing of a list...");
        {
            List<Integer> inputList = list(1, 2, 3, 4, 5, 6);
            Function<Integer, Function<String, String>> accumulator = i1 -> s -> s + i1;

            Function<String, Function<String, String>> combiner = s1 -> s2 -> s1 + s2;

            String output = parallelFoldLeft(inputList, "", accumulator, Executors.newFixedThreadPool(1), combiner);
            System.out.println(output);
        }

    }
}