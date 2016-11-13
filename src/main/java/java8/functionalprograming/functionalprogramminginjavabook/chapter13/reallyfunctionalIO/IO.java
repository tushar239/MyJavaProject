package java8.functionalprograming.functionalprogramminginjavabook.chapter13.reallyfunctionalIO;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Tushar Chokshi @ 8/28/16.
 */

// Really Functional IO
/*

Library produces the return value as some function and client program worries about inputting the parameters to it and outputting its result after running it.

e.g.
static void sayHello(String name) {
   System.out.println("Hello, " + name + "!");
}

This can be written more as a Library method as follows. Client will take care of inputting the value, running the function and taking care of the output coming from the function. Client will decide what to do with that output.

static Function<String,String>  sayHello() {
    return (name) -> "Hello, " + name + "!";
}

Other qualities of Functional Library:

- Parameterized
- Should be able to have EMPTY instance
- Combinable - should have an add method that
- should have map/flatMap methods
- Good to have
    - unit method
    - repeat method
    - forever method


 */
public interface IO<A> { // parameterized
    A run();

    IO<Nothing> empty = () -> Nothing.instance;

    static <A> IO<A> unit(A a) { // very useful method. It takes a bare value and returns it in the IO context.
        return () -> a;
    }

    // Combining many IOs
    /*
        A method in the IO interface allowing to group two IO instances into one.
        Return a new IO with a run implementation that will first execute the current IO and then the argument IO

        default IO add(IO io) {
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

    default <B> IO<B> map(Function<A, B> f) {  // To transform A to B, DEFAULT method may not need A as an input
        return () -> f.apply(this.run());
    }

    default <B> IO<B> flatMap(Function<A, IO<B>> f) {
        return () -> f.apply(this.run()).run();
    }

    static <A, B, C> IO<C> map2(IO<A> ioa, IO<B> iob,
                                Function<A, Function<B, C>> f) {
        A runA = ioa.run();
        B runB = iob.run();
        C c = f.apply(runA).apply(runB);
        return () -> c;
        //return ioa.flatMap(a -> iob.map(b -> f.apply(a).apply(b)));
    }

    // repeat method is a loop similar to the for indexed loop. This will take the form of a repeat method that takes the number of iterations and the IO to repeat as its parameters.

    //  IO program = IO.repeat(3, sayHello());
    /*static <A> IO<List<A>> repeat(int n, IO<A> io) {
        return Stream.fill(n, () -> io)  // Stream.fill has signature : public static <T> Stream<T> fill(int n, Supplier<T> elem). It returns a Stream of n (lazily evaluated) instances of T
                .foldRight(() -> unit(List.nilList()), ioa -> sioLa -> map2(ioa,
                        sioLa.get(), a -> la -> List.cons(a, la)));
    }*/


    /*
    you might not have noticed that some of the IO methods were using the stack in the same way recursive methods do. The repeat method, for example, will overflow the stack if the number of repetitions is too high.

    In order to experiment blowing the stack, create a forever method taking an IO as its argument and returning a new IO executing the argument in an endless loop.
    Here is the corresponding signature:
     */
    static <A, B> IO<B> forever(IO<A> ioa) {
        Supplier<IO<B>> t = () -> forever(ioa);
        return ioa.flatMap(x -> t.get());
    }

    /*static <A> IO<List<A>> repeat(int n, IO<A> io) {
        Stream<IO<A>> stream = Stream.fill(n, () -> io);
        Function<A, Function<List<A>, List<A>>> f = a -> la -> List.cons(a, la);
        Function<IO<A>, Function<Supplier<IO<List<A>>>, IO<List<A>>>> g =
                ioa -> sioLa -> map2(ioa, sioLa.get(), f);
        Supplier<IO<List<A>>> z = () -> unit(List.nilList());
        return stream.foldRight(z, g);
    }*/

}