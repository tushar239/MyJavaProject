package java8.functionalprograming.functionalprogramminginjavabook.chapter13.reallyfunctionalIO;

import java8.functionalprograming.functionalprogramminginjavabook.chapter4.TailCall;
import java8.functionalprograming.functionalprogramminginjavabook.chapter5and8.List;
import java8.functionalprograming.functionalprogramminginjavabook.chapter9.Stream;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Tushar Chokshi @ 8/28/16.
 */

// pg 386 Really Functional IO
//
/*

Library produces the return value as some function and client program worries about inputting the parameters to it and outputting its result after running it.

e.g.
    static void sayHello(String name) {
       System.out.println("Hello, " + name + "!");
    }

    static void sayHello(Effect<String> effect, String name) {
        effect.apply(name);
    }

    sayHello(name -> System.out.println("Hello, " + name + "!"), "Tushar");

    or

    static Supplier<String> sayHello(String name) {
        return () -> System.out.println("Hello, " + name + "!");
    }

    or

    This can be written more as a Library (Functional Context)) method as follows. Client will reserve care of inputting the value, running the function and taking care of the output coming from the function. Client will decide what to do with that output.

    static Function<String, String>  sayHello() {
        return (name) -> "Hello, " + name + "!";
    }

    System.out.println(sayHello().apply("Tushar"));   // This is the best


Other qualities of Functional Library (Functional Context):

    - Parameterized
    - Should be able to have EMPTY instance
    - Combinable - should have an add method (it's like andThen method of Java 8's Function)
    - should have map/flatMap methods
    - Good to have
        - unit method
        - repeat method
        - forever method

    These methods should create a context for computations (return a new IO instance for further processing).
    As you can see, the IO interface creates a context for computations, just like Option, Result, List, Stream etc.

    Read a book (pg 387 for more understanding of below properties)

 */

// It looks same as Supplier interface
public interface IO<A> { // parameterized
    A run();

    IO<Nothing> empty = () -> Nothing.instance;

    // pg 390
    static <A> IO<A> unit(A a) { // very useful method. It takes a bare value and returns it in the IO context. It is like getInstance() method.
        return () -> a;
    }

    // Combining many IOs
    /*
        A method in the IO interface allowing to group two IO instances into one.
        Return a new IO with a run implementation that will first execute the current IO and then the argument IO

        default IO add(IO io) {  // same as andThen method of Java 8's Function
          return () -> {
            this.run();
            io.run();
          };
        }


        This helps to do something like this

        IO instruction1 = println("Hello, ");
        IO instruction2 = println("Tushar");
        IO instruction3 = println("!\n");

        IO script = instruction1.add(instruction2).add(instruction3);
        script.run();

        List<IO> instructions = List.nilList(
            println("Hello, "),
            println(name),
            println("!\n")
        );

        IO program = instructions.foldLeft(IO.empty(), io -> io::add); // same as reduce method
        IO program = instructions.foldRight(IO.empty(), io -> io::add); // same as reduce method starting from right
    */
    // pg 388
    default IO<A> add(IO<A> io) {

        return () -> {
            A runMe = this.run();
            A runAnother = io.run();
            return runMe != null ? runMe : runAnother;
        };

    }

    static <A, B> IO<B> mapStatic(A a, Function<A, B> f) {// To transform A to B, STATIC method needs A as an input
        return () -> f.apply(a);
    }

    // pg 390
    default <B> IO<B> map(Function<A, B> f) {  // To transform A to B, DEFAULT method may not need A as an input
        return () -> {
            A a = this.run();
            return f.apply(a);
        };
    }

    // pg 391
    default <B> IO<B> flatMap(Function<A, IO<B>> f) {
        //return () -> f.apply(this.run()).run(); // book has this code. Not sure why.
        A a1 = this.run();
        return f.apply(a1);
    }

    // above method is same as this method
    static <A, B> IO<B> flatMap(IO<A> a, Function<A, IO<B>> f) {
        A a1 = a.run();
        return f.apply(a1);
    }

    // pg 392
    // a loop similar to the for loop. This will reserve the form of a repeat method that takes the number of iterations and the IO to repeat as its parameters.
    static <A> IO<List<A>> repeat(int n, IO<A> io) {
        return repeat(n, io, List.nilList());
    }

    static <A> IO<List<A>> repeat(int n, IO<A> io, List<A> identity) {
        if (n == 0) return () -> identity;
        return repeat(n - 1, io, List.consList(io.run(), identity));
    }

    // from book. It is doing same as above method created by me
    static <A> IO<List<A>> repeat_(int n, IO<A> io) {
        return Stream
                .fill(n, io) // Stream<IO<A>>
                .foldRight(unit(List.nilList()), // identity = IO<List<A>>
                                ioaFromStream -> // IO<A>
                                identityIOOfListOfA -> // identity
                                map2(
                                    ioaFromStream, // ioaFromStream
                                    identityIOOfListOfA, // identity
                                    ioaFromStream_ -> identityIOOfListOfA_ -> List.consList(ioaFromStream_, identityIOOfListOfA_) // creating a IO<List<A>>
                                )
                );
    }

    // pg 393
    static <A, B, C> IO<C> map2(IO<A> ioa, IO<B> iob,
                                Function<A, Function<B, C>> f) {
        return ioa.flatMap(a -> iob.flatMap(b -> (() -> f.apply(a).apply(b))));

        /* above comprehension pattern is same as
        A runA = ioa.run();
        B runB = iob.run();
        C c = f.apply(runA).apply(runB);
        return () -> c;*/
    }

    /*
    you might not have noticed that some of the IO methods were using the stack in the same way recursive methods do.
    The repeat() method, for example, will overflow the stack if the number of repetitions is too high.

    Below method 'forever()' is another example.
    It is not a useful method, but it uses flatMap just to show you how can it blow the stack.
    Without flatMap usage, it is very easy to fix the problem.

    static <A, B> Supplier<IO<B>> forever1(IO<A> ioa) {
        return () -> forever1(ioa);
    }

    IO<A> io = ...
    for(int i=o; i<5; i++) { // caller of forever1 can control the exit condition.
        io = io.forever1().get();
    }

    To see how to make below forever1() method stack free,
    see foreverStackFree() method and StackFreeIO.java's forever() method.
    */
    static <A, B> IO<B> forever1(IO<A> ioa) { // it will blow the stack after a few thousand iterations.
        IO<B> t = forever1(ioa);
        return ioa.flatMap(a -> t);
    }


    static <A, B> IO<B> forever2(IO<A> ioa) { // it will blow the stack after a few thousand iterations.
        Supplier<IO<B>> t = () -> forever2(ioa); // applying a concept same as Stream's from method. Provided that there is no exit condition, to stop infinite loop you need to wrap recursive method call with Supplier and get() on that supplier should happen by a caller of a method (not in the method itself)
        return ioa.flatMap(a -> t.get()); // you are doing t.get() right in the recursive method, so it is going to be same as forever1 method.
    }

    static <A, B> Supplier<IO<B>> forever3(IO<A> ioa) { // it will also blow the stack after a few thousand iterations.
        Supplier<Supplier<IO<B>>> t = () -> forever3(ioa);
        return () -> ioa.flatMap(a -> t.get().get());
    }


    // compare it with forever1, forever2 methods, which can blow the stack
    // Unlike to forever2 method, here we are avoiding t.get() inside the recursive method. We putting t.get() responsibility on TailCall.eval(). So, indirectly avoiding recursive loop.
    // But to make it work, I had to modify a signature of basic flatMap method, which is not good.
    // Inside IO.java,
    // IO<B> flatMap(Function<A, IO<B>>) makes sense
    // TailCall<IO<B>> flatMap(Function<A, TailCall<IO<B>>>) does not make sense
    // So final solution is in StackFreeIO.java
    static <A, B> TailCall<IO<B>> foreverStackFree(IO<A> ioa) {
        TailCall.SupplierContainer<IO<B>> iobSupplier = TailCall.getSupplierContainer(() -> foreverStackFree(ioa));
        return ioa.flatMap_(a -> iobSupplier);
    }
    default <B> TailCall<IO<B>> flatMap_(Function<A, TailCall<IO<B>>> f) {
        A a1 = this.run();
        System.out.println(a1);
        return TailCall.getSupplierContainer(() -> f.apply(a1));
    }



}