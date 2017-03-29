package java8.functionalprograming.functionalprogramminginjavabook.chapter9;

import java8.functionalprograming.functionalprogramminginjavabook.chapter12.Tuple;
import java8.functionalprograming.functionalprogramminginjavabook.chapter2.Function;
import java8.functionalprograming.functionalprogramminginjavabook.chapter4.TailCall;
import java8.functionalprograming.functionalprogramminginjavabook.chapter5and8.List;
import java8.functionalprograming.functionalprogramminginjavabook.chapter7.Result;

import java.util.function.Supplier;

/*
Very important concept.
From this program, you can understand how Stream works in java and how can it supply an element in a sequence from a collection.

Use of supplier enables a Stream to supply an element one by one lazily.
The idea behind laziness is that we can save time by evaluating data only when it is needed.

This Stream.java class vs Java8's Stream.java
    Java 8 streams were designed with the idea of automatic parallelization in mind.
    To allow for automatic parallelization, many compromises were made. Many functional methods are missing because they would have made automatic parallelization more difficult.
    Furhtermore, Java 8 streams are stateful. Once they have been used for some operations, they will have changed their state and are no longer usable.
    And last, folding Java 8 streams is a strict operation, that causes the evaluation of all elements.
    For all these reasons, you will define your own streams.
    After finishing this chapter, you may prefer to use the Java 8 streams. At least, you will fully understand what is missing in the Java 8 implementation.

IMPORTANT:
map, filter, flatMap etc depends on foldRight method. They operate just like foldRight with little bit of variation.
iterate is same same as repeat method.

*/

public abstract class Stream<I> {
    private static Stream EMPTY = new Empty();

    public abstract I head();

    public abstract Stream<I> tail();

    public abstract Boolean isEmpty();

    private Stream() {
    }

    public abstract Result<I> headOption();

    // convert a stream to a List (pg 264)
    public List<I> toList() {
        TailCall<List<I>> listTailCall = toListTailRecursiveJava8Style(this, List.nilList());
        return listTailCall.eval().reverse();
    }

    public static <I> List<I> toList(Stream<I> stream) {
        TailCall<List<I>> listTailCall = toListTailRecursiveJava8Style(stream, List.nilList());
        return listTailCall.eval().reverse();
    }

    private static <I> List<I> toListTailRecursive(Stream<I> stream, List<I> result) {
        if (stream.isEmpty()) return result;

        List newList = List.consList(stream.head(), result);

        return toListTailRecursive(stream.tail(), newList);
    }

    private static <I> TailCall<List<I>> toListTailRecursiveJava8Style(Stream<I> stream, List<I> result) {
        if (stream.isEmpty()) return TailCall.getFinalValueContainer(result);

        List newList = List.consList(stream.head(), result);

        return TailCall.getSupplierContainer(() -> toListTailRecursiveJava8Style(stream.tail(), newList));
    }

    public Stream<I> reverse() {
        return reverse(this, Stream.empty());
    }

    private static <I> Stream<I> reverse(Stream<I> stream, Stream<I> reversedStream) {
        if (stream.isEmpty()) return reversedStream;

        Cons<I> newReversedStream = new Cons<>(stream.head(), reversedStream);

        return reverse(stream.tail(), newReversedStream);
    }

    public static <I> Stream<I> take(Stream<I> stream, int n) {
        return takeTailRecursiveJava8Style(stream, n, Stream.empty()).eval().reverse();
    }

    private static <I> Stream<I> takeTailRecursive(Stream<I> stream, int n, Stream<I> result) {
        if (stream.isEmpty() || n == 0) return result;

        return takeTailRecursive(stream.tail(), n - 1, new Cons<I>(() -> stream.head(), () -> result));
    }

    private static <I> TailCall<Stream<I>> takeTailRecursiveJava8Style(Stream<I> stream, int n, Stream<I> result) {
        if (stream.isEmpty() || n == 0) return TailCall.getFinalValueContainer(result);

        return TailCall.getSupplierContainer(() -> takeTailRecursiveJava8Style(stream.tail(), n - 1, new Cons<I>(() -> stream.head(), () -> result)));
    }

    // return a Stream containing all starting elements as long as a condition is matched
    public static <I> Stream<I> takeWhile(Stream<I> stream, int n, Function<I, Boolean> f) {
        return takeWhileTailRecursive(stream, n, Stream.empty(), f).reverse();
    }

    private static <I> Stream<I> takeWhileTailRecursive(Stream<I> stream, int n, Stream<I> result, Function<I, Boolean> f) {
        if (stream.isEmpty() || n == 0) return result;

        return takeWhileTailRecursive(stream.tail(),
                n - 1,
                f.apply(stream.head())
                        ? new Cons<>(stream.head(), result)
                        : result,
                f);
    }


    public static <I> Stream<I> drop(Stream<I> stream, int n) {
        return dropTailRecursive(stream, n, Stream.empty()).reverse();
    }

    private static <I> Stream<I> dropTailRecursive(Stream<I> stream, int n, Stream<I> result) {
        if (stream.isEmpty()) return result;

        Stream<I> newResult = result;

        if (n <= 0) {
            newResult = new Cons<>(stream.head(), result);
        }

        n = n - 1;

        return dropTailRecursive(stream.tail(), n, newResult);
    }

    // returns a stream with the front elements removed as long as they satisfy a condition.
    public static <I> Stream<I> dropWhile(Stream<I> stream,
                                          int n,
                                          Function<I, Boolean> f) {
        return dropWhileTailRecursive(stream, n, Stream.empty(), f).reverse();
    }

    private static <I> Stream<I> dropWhileTailRecursive(Stream<I> stream,
                                                        int n,
                                                        Stream<I> result,
                                                        Function<I, Boolean> f) {
        if (stream.isEmpty()) return result;

        Stream<I> newResult = result;

        if (n <= 0 || !f.apply(stream.head())) {
            newResult = new Cons<>(stream.head(), result);
        }

        n = n - 1;

        return dropTailRecursive(stream.tail(), n, newResult);
    }

    // pg 268
    public static <I> boolean exists(Stream<I> stream,
                                     Function<I, Boolean> existsFunction) {
        if (stream.isEmpty()) return false;

        if (existsFunction.apply(stream.head())) return true;

        return exists(stream.tail(), existsFunction);
    }

    // Below foldLeft and foldRigt methods are same as List's foldLeft and foldRight
    public <O> O foldLeftTailRecursive(O identity,
                                       Function<I, Function<O, O>> operation) {
        return foldLeftTailRecursive(this, identity, operation);
    }

    public static <I, O> O foldLeftTailRecursive(Stream<I> stream,
                                                 O identity,
                                                 Function<I, Function<O, O>> operation) {
        if (stream == null || stream.isEmpty()) return identity;

        O output = operation.apply(stream.head()).apply(identity); // this is what folding means. applying an operation between two elements of the stream. first element is operated with identity and the result of that operation is used to operate with second element and so on.
        return foldLeftTailRecursive(stream.tail(), output, operation);
    }

    public <O> O foldLeft(O identity, Function<I, Function<O, O>> operation) {
        return foldLeftTailRecursive_LazilyEvaluatingTheOutput(
                this,
                () -> identity,
                input -> supplierOfIdentity -> operation.apply(input).apply(supplierOfIdentity.get())
        );
    }

    // Taking care of Laziness in above foldLeft and foldRight methods
    // pg 269
    public static <I, O> O foldLeftTailRecursive_LazilyEvaluatingTheOutput(Stream<I> stream,
                                                                           Supplier<O> identity, // Lazily evaluating output
                                                                           Function<I, Function<Supplier<O>, O>> operation) {
        if (stream == null || stream.isEmpty()) return identity.get();

        Supplier<O> output = () -> operation.apply(stream.head()).apply(identity); // Lazily evaluating output
        return foldLeftTailRecursive_LazilyEvaluatingTheOutput(stream.tail(), output, operation);
    }

    public <O> O foldRight(O identity, Function<I, Function<O, O>> operation) {
        Supplier<O> identitySupplier = () -> identity;
        return foldRightTailRecursive_LazilyEvaluatingTheOutput(identitySupplier, (I input) -> (Supplier<O> identitySupplier1) -> operation.apply(input).apply(identitySupplier1.get()));
    }

    public <O> O foldRightTailRecursive(O identity,
                                        Function<I, Function<O, O>> operation) {
        return foldLeftTailRecursive(this.reverse(), identity, operation);
    }

    public <O> O foldRightTailRecursive_LazilyEvaluatingTheOutput(Supplier<O> identity,
                                                                  Function<I, Function<Supplier<O>, O>> operation) {
        return foldLeftTailRecursive_LazilyEvaluatingTheOutput(this.reverse(), identity, operation);
    }

    /*
        public static <I, O> O foldRightTailRecursive(Stream<I> stream,
                                                      O identity,
                                                      Function<I, Function<O, O>> operation) {
            if (stream == null || stream.isEmpty()) return identity;

            O output = operation.apply(stream.head()).apply(identity);
            return foldRightTailRecursive(stream.tail(), output, operation);
        }
    */


    /*
    public static <I, O> O foldRightTailRecursive_LazilyEvaluatingTheOutput(Stream<I> stream,
                                                                            Supplier<O> identity, // Lazily evaluating output
                                                                            Function<I, Function<Supplier<O>, O>> operation) {
        if (stream == null || stream.isEmpty()) return identity.get();

        Supplier<O> output = () -> operation.apply(stream.head()).apply(identity); // Lazily evaluating output
        return foldRightTailRecursive_LazilyEvaluatingTheOutput(stream.tail(), output, operation);
    }
    */


    // map, filter, flatMap etc depends on foldRight method. They operate just like foldRight with little bit of variation.

    // Create a new stream from input stream, but while doing that map the new stream element to a different value as provided by function.
    // pg 271
    public static <I, O> Stream<O> map(Stream<I> stream, Function<I, O> f) {
        Stream<O> identity = Stream.<O>empty();
        return stream.foldRightTailRecursive(identity,
                streamElement -> identityElement -> cons(f.apply(streamElement), identityElement));
    }

    public <O> Stream<O> map(Function<I, O> f) {
//        return foldRightTailRecursive(Stream.<O>empty(),
//                input -> identity -> cons(() -> f.apply(input), () -> identity));
        return foldLeftTailRecursive(Stream.<O>empty(), input -> identityStream -> cons(f.apply(input), identityStream));
    }


    // map vs flatMap
    // map takes Function<I, O> as a parameter, whereas flatMap takes Function<I, Stream<O>> as a parameter
    public <O> Stream<O> flatMap(Function<I, Stream<O>> f) {
        Stream<O> identity = Stream.<O>empty();
        return foldRightTailRecursive(identity,
                inputStreamElement -> identityElement -> f.apply(inputStreamElement).append(identityElement));
    }

    // pg 271
    public Stream<I> filter(Function<I, Boolean> f) {
        return foldRightTailRecursive(Stream.<I>empty(),
                input -> identity -> f.apply(input) ? cons(input, identity) : identity);
    }

    // appending one stream to another stream. It's like collection.addAll(collection)
    // in foldRight, use identity as passed parameter
    // pg 271
    public Stream<I> append(Stream<I> identity) {
        return foldRightTailRecursive(identity,
                streamElement -> identityElement -> cons(streamElement, identityElement));

    }

    // lazily evaluating the output - you need to pass a Supplier of streamToBeAppendedToCurrentStream
    public Stream<I> append(Supplier<Stream<I>> streamToBeAppendedToCurrentStream) {
        return foldRightTailRecursive_LazilyEvaluatingTheOutput(streamToBeAppendedToCurrentStream,
                streamElement -> identityElement -> cons(streamElement, identityElement));
    }

    // pg 274
    public Result<I> find(Function<I, Boolean> p) {
        return filter(p).headOption();
    }

    /*

      consider from method that gives you the next element from an infinite data structure like collection (here we have used an example of integer numbers).

      if you do
      int from(int i) {
          return from(i + 1)
      }

      once you call Stream.from(1), you cannot move on to next line. It will go in infinite loop that never ends.

      To convert from method in lazily evaluated method, you can replace recursive call to supplier. You can evaluate this method as many times as you want. It won't be by default infinite times.

      Supplier<Integer> from(int i) {
          return () -> from(i + 1)
      }
    */
    /*
    public static Stream<Integer> from_(int i) {
        return cons(i, from_(i + 1)); // This will go in infinite loop
    }
    To solve infinite loop problem, as a rule of thumb, you wrap the returned value of a method 'from_' by Supplier OR wrap a recursive method call by a Supplier.
    But if you do that, it will force you to make 'tail' variable of Cons class a Supplier.

    public static Supplier<Stream<Integer>> from_(int i) {
        return () -> cons(() -> i, from_(i + 1)); // Wrapping entire returned value by a Supplier
    }
    OR
    public static Stream<Integer> from(int i) {
        return cons(() -> i, () -> from(i + 1)); // Wrapping recursive method by a Supplier (Better Approach)
    }

    (IMP) Rule of Thumb to stop blowing stack for infinite recursion:
    If there is no exit condition inside recursive method, you can wrap recursive call with a Supplier (make it lazy call) and let caller decide the exit condition and calling the method again.
    If there is an exit condition inside recursive method, you can make TailCall as the caller of the recursive method. Basic concept still remains same.

    */
    // This method is same as Java 8's Stream.java's range method.
    public static Stream<Integer> from(int i) {
        return cons(i, () -> from(i + 1));
    }

    public static Supplier<Stream<Integer>> from_(int i) {
        return () -> cons(i, from_(i + 1));
    }


    // pg 275, 275
    // repeat takes an object as its parameter and returning an infinite stream of the same object.
    public static <A> Stream<A> repeat(A a) {
        return cons(a, () -> repeat(a));
    }

    // The iterate method is a generic and more functional form of from method.
    public static <A> Stream<A> iterate(A seed, Function<A, A> f) {
        return cons(seed, () -> iterate(f.apply(seed), f));
    }

    public static <A> Stream<A> iterateUsingUnfold(A seed, Function<A, A> f) {
        return unfold(seed, f, s -> s);
    }

    // pg 276
    // function fibs that generates the infinite stream of Fibonacci numbers: 0, 1, 1, 2, 3, 5, 8, and so on.
    public static Stream<Integer> fibs() {
        Stream<Tuple<Integer, Integer>> stream = iterate(
                new Tuple<>(0, 1),
                x -> new Tuple<>(x._2, x._1 + x._2)
        );

        Stream<Integer> stream1 = stream.map(x -> x._1);

        return stream1;
    }

    /*public static <I, O> Stream<O> unfold_(I sum, Function<I, O> f1, O finalIdentity, Stream<O> identity, Function<I, Stream<O>> f2) {
        O next = f1.apply(sum);
        if(next.equals(finalIdentity)) return identity;
        return unfold_(next, f1, Stream.<O>cons(() -> next, () -> identity), f2);

    }*/

    // pg 276
    // fold method iterates through a stream/list/tree etc and creates an output based on passed startInputIdentity.
    // unfold method is reverse. It creates a stream/list/tree etc from passed startInputIdentity value.
    public static <A, S> Stream<A> unfold(S startInputIdentity, // S means start
                                          Function<S, Result<Tuple<A, S>>> f) {
        //System.out.println(startInputIdentity);
        Result<Tuple<A, S>> fOutput = f.apply(startInputIdentity);
        Result<Stream<A>> output = fOutput.map(tuple -> cons(tuple._1, () -> unfold(tuple._2, f)));
        return output.getOrElse(empty());
    }

    // My own version 1 of unfold method.
    // unfold method is normally used for creating a Stream for infinite input, which will force you to wrap recursive call with Supplier.
    public static <A, S> Stream<A> unfold(S startInputIdentity, // S means start
                                          Function<S, S> nextS,
                                          Function<S, A> f) { // used to create Stream's head by converting S to A

        return Stream.cons(
                f.apply(startInputIdentity),
                () -> unfold(nextS.apply(startInputIdentity), nextS, f)
        );
    }

    // My own version 2 of unfold method, it is easier for me to understand.
    // you can not supply endInputIdentity all the time, if you really want unfold method to be called infinitely.
    // See TestReader.java's unfold method usage.
    public static <A, S> Stream<A> unfold1(S startInputIdentity, // S means start
                                           S endInputIdentity, // last element of the stream. It is used to stop creating more elements in the stream. If this is not there, it will result in infinite stream.
                                           Function<S, S> nextS, // used to get next element for the stream
                                           Function<S, A> f, // used to create Stream's head by converting S to A
                                           Stream<A> outputIdentity) {

        if (startInputIdentity.equals(endInputIdentity))
            return Stream.cons(f.apply(startInputIdentity), outputIdentity); // there is an exit condition, so unfold1 is not going to be called infinitely. So, there is no need to wrap Cons' tail with Supplier.

        return unfold1(
                nextS.apply(startInputIdentity),
                endInputIdentity,
                nextS,
                f,
                Stream.cons(f.apply(startInputIdentity), outputIdentity)
        );
    }

    // extracting out exit condition as a Function
    // I got this idea from Chapter 3's unfold method
    public static <A, S> Stream<A> unfold2(S startInputIdentity, // S means start
                                           Function<S, Boolean> exitCondition,
                                           Function<S, S> nextS,
                                           Function<S, A> f, // used to create Stream's head by converting S to A
                                           Stream<A> outputIdentity) {

        if (exitCondition.apply(startInputIdentity))
            return Stream.cons(f.apply(startInputIdentity), outputIdentity); // there is an exit condition, so unfold1 is not going to be called infinitely. So, there is no need to wrap Cons' tail with Supplier.

        return unfold2(
                nextS.apply(startInputIdentity),
                exitCondition,
                nextS,
                f,
                Stream.<A>cons(f.apply(startInputIdentity), outputIdentity)
        );
    }

    // pg 276
    public static Stream<Integer> from_Using_Unfold1(int start) {
        //return unfold(start, n1 -> Result.success(new Tuple<>(n1, n1 + 1)));
        // or
        return unfold(start, s -> s + 1, s -> s); // you can say unfold(start, s -> s + 3 1, s -> s * 2) etc
    }

    public static Stream<Integer> from_Using_Unfold1(int start, int end) {
        return unfold1(start, end, s -> s + 1, s -> s, empty()); // you can say unfold(start, end, s -> s + 3,  s -> s * 2, empty()) etc
    }

    public static Stream<Integer> from_Using_Unfold2(int start, int end) {
        return unfold2(start, s -> s == end, s -> s + 1, s -> s, empty()); // you can say unfold2(start, s -> s == end, s -> s + 3, s -> s * 2, empty())
    }

    // pg 277
    public static Stream<Integer> fibs_Using_Unfold() {
        return unfold(new Tuple<>(1, 1),
                x -> Result.success(new Tuple<>(x._1, new Tuple<>(x._2, x._1 + x._2))));
    }

    // pg 393 (Chapter 13)
    public static <I> Stream<I> fill(int n, Supplier<I> elem) {
        return fill(n, elem.get(), Stream.empty());
    }

    public static <I> Stream<I> fill(int n, I elem) {
        return fill(n, elem, Stream.empty());
    }

    private static <I> Stream<I> fill(int n, I elem, Stream<I> identity) {
        if (n == 0) return identity;
        return fill(n - 1, elem, Stream.cons(elem, identity));

    }

    private static class Empty<I> extends Stream<I> {
        @Override
        public Stream<I> tail() {
            throw new IllegalStateException("tail called on empty");
        }

        @Override
        public I head() {
            throw new IllegalStateException("head called on empty");
        }

        @Override
        public Boolean isEmpty() {
            return true;
        }

        @Override
        public Result<I> headOption() {
            return Result.empty();
        }

        @Override
        public String toString() {
            return "Nil{}";
        }

    }

    private static class Cons<I> extends Stream<I> {
        private final Supplier<I> head; // The idea behind laziness is that we can save time by evaluating data only when it is needed.
        private final Supplier<Stream<I>> tail;

        private I h;
        private Stream<I> t;

        private Cons(I h, Stream<I> t) {
            this.h = h;
            // this.h = Result.empty(); // pg 277 - 9.8 Avoiding null references and mutable fields
            this.t = t;

            this.head = () -> h;
            this.tail = () -> t;
        }

        private Cons(I h, Supplier<Stream<I>> tl) {
            this.h = h;
            // this.h = Result.success(h); // pg 277 - 9.8 Avoiding null references and mutable fields
            this.tail = tl;

            this.head = () -> h;
        }

        // Java 8's Stream also maintains head and tail (AbstractPipeline sourceStage  and  AbstractPipeline previousStage). Stream interface has an abstract child class AbstractPipeline.
        @Deprecated // book makes the use of this constructor, but I don't think it is needed.
        private Cons(Supplier<I> hd, Supplier<Stream<I>> tl) {
            this.head = hd;
            this.tail = tl;
            // this.h = Result.empty(); // pg 277 - 9.8 Avoiding null references and mutable fields
        }

        /*  pg 277 - 9.8 Avoiding null references and mutable fields
        public Tuple<I, Stream<I>> head() {
            I a = h.getOrElse(head.get());
            return h.isEmpty()
                    ? new Tuple<>(a, new Cons<>(a, tail))
                    : new Tuple<>(a, this);
        }
        */
        @Override
        public I head() {
            //return head.get();
            if (h == null) {
                h = head.get(); // memoizing evaluated values
            }
            return h;
        }

        @Override
        public Stream<I> tail() {
            //return tail.get();
            if (t == null) {
                t = tail.get(); // memoizing evaluated values
            }
            return t;
        }

        @Override
        public Boolean isEmpty() {
            return false;
        }

        @Override
        public Result<I> headOption() {
            return Result.success(head());
        }

        @Override
        public String toString() {
            return "Cons{" +
                    "head=" + head.get() +
                    ", tail=" + tail.get() +
                    '}';
        }
    }

    static <I> Stream<I> cons(Supplier<I> hd, Supplier<Stream<I>> tl) {
        return new Cons<>(hd, tl);
    }

    static <I> Stream<I> cons(I h, Supplier<Stream<I>> tl) {
        return new Cons<>(h, tl);
    }

    static <I> Stream<I> cons(I h, Stream<I> t) {
        return new Cons<>(h, t);
    }

    @SuppressWarnings("unchecked")
    public static <I> Stream<I> empty() {
        return EMPTY;
    }


    public static void main(String[] args) {
        {
            System.out.println("Testing from method...");
            Stream<Integer> stream = Stream.from(1);
            for (int i = 0; i < 5; i++) {
                System.out.print(stream.head() + ",");
                stream = stream.tail();
            }
            // O/P: 1,2,3,4,5,
        }
        System.out.println();

        {
            System.out.println("Convert Stream to a List...");
            Stream<Integer> stream = new Cons<>(1,
                    new Cons<>(2,
                            new Cons<>(3,
                                    new Empty<>())));
            System.out.println(Stream.toList(stream)); // Cons{head=1, tail=Cons{head=2, tail=Cons{head=3, tail=Nil{}}}}

        }
        System.out.println();

        {
            System.out.println("Take first n elements from a Stream...");
            Stream<Integer> stream = new Cons<>(1,
                    new Cons<>(2,
                            new Cons<>(3,
                                    new Empty<>())));
            System.out.println(Stream.take(stream, 2).toList()); // Cons{head=1, tail=Cons{head=2, tail=Nil{}}}
        }
        System.out.println();

        { // pg 274 of the book
            System.out.println("Take first n elements from an INFINITE Stream... (Same as Java 8's Stream.iterate(0, n -> n + 1).limit(10))...");
            Stream<Integer> infiniteStream = Stream.from(0);
            System.out.println(Stream.take(infiniteStream, 10).toList()); // Cons{head=0, tail=Cons{head=1, tail=Cons{head=2, tail=Cons{head=3, tail=Cons{head=4, tail=Cons{head=5, tail=Cons{head=6, tail=Cons{head=7, tail=Cons{head=8, tail=Cons{head=9, tail=Nil{}}}}}}}}}}}
        }
        System.out.println();

        {
            System.out.println("Drop first n elements from a Stream...");
            Stream<Integer> stream = new Cons<>(1,
                    new Cons<>(2,
                            new Cons<>(3,
                                    new Empty<>())));
            System.out.println(Stream.drop(stream, 1).toList()); // Cons{head=2, tail=Cons{head=3, tail=Nil{}}}
        }


        System.out.println("Folding a stream from left example...");
        {

            Stream<Integer> stream = new Cons<>(1,
                    new Cons<>(2,
                            new Cons<>(3,
                                    new Empty<>())));
            {
                Integer identity = 0;
                System.out.println(Stream.foldLeftTailRecursive(stream,
                        identity,
                        streamElement -> identityElement -> streamElement + identityElement)); // 6
            }
            {
                Stream<Integer> identity = Stream.empty();
                System.out.println(Stream.foldLeftTailRecursive(stream,
                        identity,
                        streamElement -> identityElement -> new Cons<>(streamElement, identityElement))); // 6
            }

        }

        System.out.println("Folding a stream from left example (evaluating output lazily)...");
        {
            Stream<Integer> stream = new Cons<>(1,
                    new Cons<>(2,
                            new Cons<>(3,
                                    new Empty<>())));
            Supplier<Integer> identity = () -> 0;
            System.out.println(Stream.foldLeftTailRecursive_LazilyEvaluatingTheOutput(stream, identity, streamEle -> identity1 -> streamEle + identity1.get())); // 6

        }
        System.out.println();

        System.out.println("While folding a stream, fold it with mapped function...");
        {
            Stream<Integer> stream = new Cons<>(1,
                    new Cons<>(2,
                            new Cons<>(3,
                                    new Empty<>())));
            System.out.println(stream.map(i -> i * 2)); // Cons{head=2, tail=Cons{head=4, tail=Cons{head=6, tail=Nil{}}}}
        }
        System.out.println();

        System.out.println("While folding a stream, filter its elements based on passed predicate...");
        {
            Stream<Integer> stream = new Cons<>(1,
                    new Cons<>(2,
                            new Cons<>(3,
                                    new Cons<>(4,
                                            new Empty<>()))));
            System.out.println(stream.filter(i -> i % 2 == 0));// Cons{head=2, tail=Cons{head=4, tail=Nil{}}}
        }
        System.out.println();

        System.out.println("repeat and from and iterate...");
        {
            System.out.println("repeat...");
            // creating infinite stream of 1s
            Stream<Integer> stream1 = repeat(1);
            for (int i = 0; i < 9; i++) {
                System.out.print(stream1.head() + ",");// 1,1,1,1,1,1,1,1,1,
                stream1 = stream1.tail();
            }
            System.out.println();

            System.out.println("from...");
            Stream<Integer> stream2 = from(1);
            for (int i = 0; i < 9; i++) {
                System.out.print(stream2.head() + ",");// 1,2,3,4,5,6,7,8,9,
                stream2 = stream2.tail();
            }
            System.out.println();

            System.out.println("from_ diff way...");
            Stream<Integer> stream22 = from_(1).get();
            for (int i = 0; i < 9; i++) {
                System.out.print(stream22.head() + ",");// 1,2,3,4,5,6,7,8,9,
                stream22 = stream22.tail();
            }
            System.out.println();

            System.out.println("iterate same as repeat...");
            Stream<Integer> stream3 = iterate(1, input -> input);
            for (int i = 0; i < 9; i++) {
                System.out.print(stream3.head() + ",");// 1,1,1,1,1,1,1,1,1,
                stream3 = stream3.tail();
            }
            System.out.println();

            System.out.println("iterate same as from...");
            Stream<Integer> stream4 = iterate(1, input -> input + 1);
            for (int i = 0; i < 9; i++) {
                System.out.print(stream4.head() + ",");// 1,2,3,4,5,6,7,8,9,
                stream4 = stream4.tail();
            }

            System.out.println("iterate using unfold...");
            Stream<Integer> stream5 = iterateUsingUnfold(1, input -> input + 1);
            for (int i = 0; i < 9; i++) {
                System.out.print(stream5.head() + ",");// 1,2,3,4,5,6,7,8,9,
                stream5 = stream5.tail();
            }

        }
        System.out.println();


        /* commented because it will go in infinite loop
        System.out.println("fibonacci series using iterate...");
        {
            fibs();
        }*/
        System.out.println("from using unfold...");
        {
            System.out.println("one way...");
            Stream<Integer> output = from_Using_Unfold1(1); // Cons(() -> 1, () -> unfold(2, f))
            Integer head;
            while ((head = output.head()) < 10) {
                System.out.print(head + ",");// 1,2,3,4,5,6,7,8,9,
                output = output.tail();
            }
            System.out.println();

            System.out.println("second way...");
            output = from_Using_Unfold1(1, 9);
            System.out.println(output); // Cons{head=9, tail=Cons{head=8, tail=Cons{head=7, tail=Cons{head=6, tail=Cons{head=5, tail=Cons{head=4, tail=Cons{head=3, tail=Cons{head=2, tail=Cons{head=1, tail=Nil{}}}}}}}}}}

            System.out.println("third way...");
            output = from_Using_Unfold2(1, 9);
            System.out.println(output);// Cons{head=9, tail=Cons{head=8, tail=Cons{head=7, tail=Cons{head=6, tail=Cons{head=5, tail=Cons{head=4, tail=Cons{head=3, tail=Cons{head=2, tail=Cons{head=1, tail=Nil{}}}}}}}}}}

        }
    }

}