package java8.functionalprograming.functionalprogramminginjavabook.chapter13.reallyfunctionalIO.stackfreeIO;

import java8.functionalprograming.functionalprogramminginjavabook.chapter13.reallyfunctionalIO.Nothing;
import java8.functionalprograming.functionalprogramminginjavabook.chapter2.Function;
import java8.functionalprograming.functionalprogramminginjavabook.chapter4.TailCall;

import java.util.function.Supplier;

import static java8.functionalprograming.functionalprogramminginjavabook.chapter4.TailCall.sus;

/**
 * @author Tushar Chokshi @ 11/29/16.
 */

/*
 pg 396-400
 IO.java's forever method will go in infinite loop. StackFreeIO.java is an improved version of IO to handle this situation.
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
        return run_(io).eval();
    }

    // The method returns a TailCall that will be evaluated by the caller method.
    private TailCall<A> run_(StackFreeIO<A> io) {

        if (io.isReturn()) {

            return TailCall.getFinalValueContainer(((Return<A>) io).value); // If the received IO is a Return, the computation is over.

        } else if (io.isSuspend()) {

            return TailCall.getFinalValueContainer(((Suspend<A>) io).resume.get()); // If the received IO is a Suspend, the contained effect is executed before returning the resume value.

        } else {

            Continue<A, A> ct = (Continue<A, A>) io; // If the received IO is a Continue, the contained sub IO is read.

            StackFreeIO<A> sub = ct.sub;

            Function<A, StackFreeIO<A>> f = ct.f;

            if (sub.isReturn()) { // If sub is a Return, the method is called recursively, with the result of applying the enclosed function to it.

                return sus(() -> run_(f.apply(((Return<A>) sub).value)));

            } else if (sub.isSuspend()) { // If sub is a Suspend, the enclosed function is applied to it, possibly producing the corresponding effect.

                return TailCall.getSupplierContainer(() -> run_(f.apply(((Suspend<A>) sub).resume.get())));

            } else {

                Continue<A, A> ct2 = (Continue<A, A>) sub; // If sub is a continue, the IO it contains is extracted (sub2) and it is flatMapped with sub, thus creating the chaining.
                StackFreeIO<A> sub2 = ct2.sub;
                Function<A, StackFreeIO<A>> f2 = ct2.f;
                return TailCall.getSupplierContainer(() -> run_(sub2.flatMap(x -> f2.apply(x).flatMap(f))));

            }

        }
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
        return ioa.flatMap(a -> t.get());
    }

}
