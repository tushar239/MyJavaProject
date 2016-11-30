package java8.functionalprograming.functionalprogramminginjavabook.chapter13.reallyfunctionalIO.stackfreeIO;

import java8.functionalprograming.functionalprogramminginjavabook.chapter13.reallyfunctionalIO.Nothing;
import java8.functionalprograming.functionalprogramminginjavabook.chapter2.Function;
import java8.functionalprograming.functionalprogramminginjavabook.chapter4.TailCall;

import java.util.function.Supplier;

/**
 * @author Tushar Chokshi @ 11/29/16.
 */

/*
 pg 396-400
 IO.java's forever() method will go in infinite loop, which will blow a stack.
 StackFreeIO.java is an improved version of IO to use only one stack frame for infinitely running recursive method.
*/
public abstract class StackFreeIO<A> {

    protected abstract boolean isReturn();

    protected abstract boolean isSuspend();

    private static StackFreeIO<Nothing> EMPTY = new Suspend<>(() -> Nothing.instance);

    public static StackFreeIO<Nothing> empty() {
        return EMPTY;
    }

    public A run() {
        return run(this);
    }

    public A run(StackFreeIO<A> io) {
        //return run_(io).eval();

        //TailCall<A> aTailCall = run__(io);
        //return aTailCall.eval();

        return run___(io).eval();
    }

    // The method returns a TailCall that will be evaluated by the caller method.
    private static <A> TailCall<A> run_(StackFreeIO<A> io) {

        if (io.isReturn()) {

            return TailCall.getFinalValueContainer(((Return<A>) io).value); // If the received IO is a Return, the computation is over.

        } else if (io.isSuspend()) {

            return TailCall.getFinalValueContainer(((Suspend<A>) io).resume.get()); // If the received IO is a Suspend, the contained effect is executed before returning the resume value.

        } else { // Continue is a wrapper of another Continue/Return/Suspend

            Continue<A, A> ct = (Continue<A, A>) io; // If the received IO is a Continue, the contained sub IO is read.

            StackFreeIO<A> sub = ct.sub;

            Function<A, StackFreeIO<A>> f = ct.f;

            if (sub.isReturn()) { // If sub is a Return, the method is called recursively, with the result of applying the enclosed function to it.
                A a = ((Return<A>) sub).value;
                return TailCall.getSupplierContainer(() -> {
                    StackFreeIO<A> apply = f.apply(a);
                    return run_(apply);
                });

            } else if (sub.isSuspend()) { // If sub is a Suspend, the enclosed function is applied to it, possibly producing the corresponding effect.
                A a = ((Suspend<A>) sub).resume.get();// "Hi Again!"

                return TailCall.getSupplierContainer(() -> {
                    StackFreeIO<A> apply = f.apply(a);
                    return run_(apply);
                });

            } else {

                Continue<A, A> ct2 = (Continue<A, A>) sub; // If sub is a continue, the IO it contains is extracted (sub2) and it is flatMapped with sub, thus creating the chaining.
                StackFreeIO<A> sub2 = ct2.sub;
                Function<A, StackFreeIO<A>> f2 = ct2.f;
                return TailCall.getSupplierContainer(() -> run_(sub2.flatMap(x -> f2.apply(x).flatMap(f))));

            }

        }
    }


    private static <A> TailCall<A> run___(StackFreeIO<A> io) {
        Continue<A, A> ct = (Continue<A, A>) io; // If the received IO is a Continue, the contained sub IO is read.

        StackFreeIO<A> sub = ct.sub;

        Function<A, StackFreeIO<A>> f = ct.f; // a -> t.get()


        // below two statements are same as IO's flatMap
        A a = ((Suspend<A>) sub).resume.get();// "Hi Again!"
        System.out.println(a);
        StackFreeIO<A> anotherStackFreeIO = f.apply(a);// will return Continue

        return TailCall.getSupplierContainer(() -> run___(anotherStackFreeIO));

    }

    public <B> StackFreeIO<B> map(Function<A, B> f) {
        return flatMap(f.andThen(a -> new Return<>(a)));
    }

    @SuppressWarnings("unchecked")
    public <B> StackFreeIO<B> flatMap(Function<A, StackFreeIO<B>> f) {
        return (StackFreeIO<B>) new Continue<>(this, f);
    }

    public static <A> StackFreeIO<A> unit(A a) {
        return new Suspend<>(() -> a);
    }

    public static <A, B> StackFreeIO<B> forever(StackFreeIO<A> ioa) {
        Supplier<StackFreeIO<B>> t = () -> forever(ioa);
        return ioa.flatMap(a -> t.get()); // as per rule, if you wrap recursive call with a Supplier, then you should call supplier.get() from the caller of the method, but improved flatMap method will allow you to use supplier.get() from inside the recursive method.
    }

}
