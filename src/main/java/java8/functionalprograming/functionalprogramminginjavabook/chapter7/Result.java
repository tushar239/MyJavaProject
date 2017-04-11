package java8.functionalprograming.functionalprogramminginjavabook.chapter7;


import java8.functionalprograming.functionalprogramminginjavabook.chapter13.outputtingdata.Effect;

import java.io.Serializable;
import java.util.function.Function;
import java.util.function.Supplier;

/*
        Result
          |
      ------------
      |          |
    Success     Empty
       value      |
                Failure
                    exception

Result class is more like Java's Optional class except it has subclasses Success and Failure to handle certain features in a better way.

Optional class is capable of returning Optional.Empty or Optional containing value, which is same as Result's Empty and Success classes respectively.
Result also has Failure class, Optional class doesn't have this capability at this point in time.

e.g.

    public Result<Tuple<String, Input>> readString() {
        try {
            String s = reader.readLine();
            return s.length() == 0
                    ? Result.empty()
                    : Result.success(new Tuple<>(s, this));
        } catch (Exception e) {
            return Result.failure(e);
        }
    }

    public Optional<?> readString() {
        try {
            String s = reader.readLine();
            return s.length() == 0
                    ? Optional.empty()                   // same as Result.empty
                    : Optional.of(new Tuple<>(s, this)); // same as Result.success(...)
        } catch (Exception e) {
            return Optional.of(e); // Compile-Time Error: Optional provides support of Empty and Success, but not for failure.
        }
    }

    Result's forEach(Effect) is similar to Optional's ifPresent(Consumer) method.
    Result has forEachOrThrow(Effect) and forEachOrException(Effect) methods. Similar methods are not there in Optional.

    Important concepts of Result/Optional (COMPREHENSION PATTERN)
    -------------------------------------------------------------
    - avoid using get and getOrThrow methods using flatMap/map (from Chapter 11 (pg 338) DefaultHeap.java)

        From Book:
        As a general rule, you should always remember that calling get or getOrThrow, could throw an exception if the Result is Empty.
        We might either test for emptiness first, or include the code in a try...catch block (second example), but none of these solutions is really functional.
        By the way, you should try to never find yourself calling get or getOrThrow.
        The get method should only be used inside the Result class.
        The best solution for enforcing this would be to make it protected. But it is useful to be able to use it while learning, to show what is happening!

        My opinion:
        I would say that you should not apply any operation on get, getOrElse, getOrThrow.
        Instead you should try to use flatMap or map methods as shown in Result's lift2 method, DefaultHeap class' merge method.
        see DefaultHeap.java's get(index) method, diff between mergeDifferentWay_WrongWay and merge methods.


        As a general rule, you should always remember not to use result.get() or getOrThrow() when using that value as an input to other methods.
        Instead use comprehension pattern as shown below because it ensures calling getSomething(...) method only if all required inputs to that method are not-null.

        This is called Comprehension Pattern. Learn it. It is very important in Functional Programming.

        Many programmers know this pattern as
           O output = ra.flatMap(a ->
                                    rb.flatMap(b ->
                                                rc.map(c -> getSomething(a, b, c))));

           Result<O> output = ra.flatMap(a ->
                                              rb.flatMap(b ->
                                                            rc.flatMap(c -> getSomething(a, b, c))));
        You could do
            getSomething(ra.get(), rb.get(), rc.get()) which can potentially pass nulls to getSomething method, if you want to avoid that use comprehension pattern.

        REMEMBER:
        WHEN YOU HAVE MORE THAN ONE Optional/Result objects AS INPUTS TO SOME OTHER METHOD/FUNCTION, then you can use 'COMPREHENSION' pattern to call that method/function.

    - avoid null checks using flatMap, map methods (from Chapter 7 (pg 213))
        See Toon.java's to see how Toon object is created.


    Important concept of how to make a method functional that has a side-effect?
    ----------------------------------------------------------------------------

    Chapter 15's example (pg 430, 431)
    Java suggests one of the below options to avoid division by 0 error.

    double inverse(int x) {  // using assertion. assertion can be disabled at runtime by using 'java -da ...' option.
      assert x != 0; // if x == 0, then it will throw AssertionException, which is a side-effect.
      return 1.0 / x;
    }

    double inverse(int x) {
      if (x != 0) throw new IllegalArgumentException("div. By 0"); // by adding null check. throwing an exception is a side-effect that makes a method non-functional.
      return 1.0 / x;
    }

    How will you make above method functional?
    Functional method should not create a side effect like throwing an exception.
    Using Result class, you can wrap an exception easily. Using Optional, it is a bit tough.

    // Using Result
    Result<Double> inverse(int x) {
        return x == 0
            ? Result.failure("div. By 0")
            : Result.success(1.0 / x);
    }

    // Using Optional
    Supplier<Optional<Double>> inverse(int x) {
        if (x == 0)
            return () -> {throw new RuntimeException("can not divide by 0");};
        return () -> Optional.<Double>ofNullable(1.0 / x);
    }



 */
public abstract class Result<V> implements Serializable {
    private Result() {
    }

    // Method bind allows handling Effects
    public abstract void bind(Effect<V> success, Effect<String> failure);

    public static <V> Result<V> failure(String message) {
        return new Failure<>(message);
    }

    public static <V> Result<V> failure(Exception e) {
        return new Failure<V>(e);
    }

    public static <V> Result<V> failure(String message, Exception e) {
        return new Failure<>(message, e);
    }

    public static <V> Result<V> failure(RuntimeException e) {
        return new Failure<V>(e);
    }

    public static <V> Result<V> success(V value) {
        return new Success<>(value);
    }

    public abstract V get();

    public abstract V getOrElse(final V defaultValue);

    public abstract V getOrElse(final Supplier<V> defaultValue);

    public abstract <U> Result<U> map(Function<V, U> f);

    public abstract <U> Result<U> flatMap(Function<V, Result<U>> f);

    public Result<V> orElse(Supplier<Result<V>> defaultValue) {
        return map(x -> this).getOrElse(defaultValue);
    }

    // Similar to Optional's ifPresent(Consumer consumer) method
    public abstract void forEach(Effect<V> ef);

    // Optional doesn't have this kind of method at present
    abstract void forEachOrThrow(Effect<V> ef);

    // Optional doesn't have this kind of method at present
    abstract Result<RuntimeException> forEachOrException(Effect<V> ef);

    // Optional doesn't have this kind of method at present
    public abstract Result<String> forEachOrFail(Effect<V> c);

    public abstract <U> Result<U> mapFailure(String s);

    private static Result empty = new Empty();


    // Java 8 doesn't have this facility yet. Java 8's Optional can handle Empty and Success scenario, but not Failure scenario.
    private static class Failure<V> extends Empty<V> {
        private final RuntimeException exception;

        private Failure(String message) {
            super();
            this.exception = new IllegalStateException(message);
        }

        private Failure(RuntimeException e) {
            super();
            this.exception = e;
        }

        private Failure(Exception e) {
            super();
            this.exception = new IllegalStateException(e.getMessage(), e);
        }

        private Failure(String message, Exception e) {
            super();
            this.exception = new IllegalStateException(message, e);
        }

        @Override
        public String toString() {
            return String.format("Failure(%s)", exception.getMessage());
        }

        @Override
        public void bind(Effect<V> success, Effect<String> failure) {
            failure.apply(exception.getMessage());
        }

        @Override
        public V get() {
            throw exception;
        }

        @Override
        public V getOrElse(V defaultValue) {
            return defaultValue;
        }

        @Override
        public V getOrElse(Supplier<V> defaultValue) {
            return defaultValue.get();
        }

        @Override
        public <U> Result<U> map(Function<V, U> f) {
            return failure(exception);
        }

        @Override
        public <U> Result<U> flatMap(Function<V, Result<U>> f) {
            return failure(exception);
        }

        @Override
        public void forEachOrThrow(Effect<V> ef) {
            throw exception;
        }

        @Override
        public Result<RuntimeException> forEachOrException(Effect<V> ef) {
            return success(exception);
        }

        @Override
        public Result<String> forEachOrFail(Effect<V> c) {
            return success(exception.getMessage());
        }

        // pg 205, 206
        @Override
        public Result<V> mapFailure(String s) {
            return failure(new IllegalStateException(s, exception));
        }
    }

    private static class Success<V> extends Result<V> {
        private final V value;

        private Success(V value) {
            super();
            this.value = value;
        }


        @Override
        public void bind(Effect<V> success, Effect<String> failure) {
            success.apply(value);
        }

        @Override
        public String toString() {
            return String.format("Success(%s)", value.toString());
        }

        @Override
        public V getOrElse(V defaultValue) {
            return value;
        }

        @Override
        public V getOrElse(Supplier<V> defaultValue) {
            return value;
        }

        @Override
        public <U> Result<U> map(Function<V, U> f) {
            return new Success<>(f.apply(value));
        }

        @Override
        public <U> Result<U> flatMap(Function<V, Result<U>> f) {
            return f.apply(value);
        }

        @Override
        public void forEach(Effect<V> ef) {
            ef.apply(value);
        }

        @Override
        void forEachOrThrow(Effect<V> ef) {
            forEach(ef);
        }

        @Override
        public Result<RuntimeException> forEachOrException(Effect<V> ef) {
            forEach(ef);
            return empty();
        }

        @Override
        public Result<String> forEachOrFail(Effect<V> e) {
            e.apply(this.value);
            return empty();
        }

        @Override
        public V get() {
            return value;
        }

        public Result<V> mapFailure(String s) {
            return this;
        }
    }

    public static class Empty<V> extends Result<V> {
        public Empty() {
            super();
        }

        @Override
        public void bind(Effect<V> success, Effect<String> failure) {

        }

        @Override
        public V getOrElse(final V defaultValue) {
            return defaultValue;
        }

        @Override
        public <U> Result<U> map(Function<V, U> f) {
            return empty();
        }

        @Override
        public <U> Result<U> flatMap(Function<V, Result<U>> f) {
            return empty();
        }

        @Override
        public V getOrElse(Supplier<V> defaultValue) {
            return defaultValue.get();
        }

        @Override
        public void forEach(Effect<V> ef) {
            // Do nothing
        }

        @Override
        void forEachOrThrow(Effect<V> ef) {
            // Do nothing
        }

        @Override
        public Result<RuntimeException> forEachOrException(Effect<V> ef) {
            return empty();
        }

        @Override
        public Result<String> forEachOrFail(Effect<V> c) {
            return empty();
        }

        @Override
        public Result<V> mapFailure(String s) {
            return this;
        }

        @Override
        public V get() {
            return null;
        }

        @Override
        public String toString() {
            return "Empty()";
        }
    }

    public static <V> Result<V> empty() {
        return empty;
    }

    static void testLift() {
        Integer result = lift1((a) -> String.valueOf(a)).apply("2");
        System.out.println("Result of lift(): " + result);
    }

    // pg 211
    /* Use cases for Result are more or less the same as for Option.
    In the previous chapter, you defined a lift method for composing Options by transforming a Function<A,B> into Function<Option<A>, Option<B>>.
    You can do the same for Result.
    */
    static <A, B> Function<Result<A>, Result<B>> lift(final Function<A, B> f) {
        return (r) -> r.map(f);
    }

    static Function<String, Integer> lift1(final Function<Integer, String> f) {
        return (s) -> Integer.valueOf(f.apply(new Integer(s)));
    }

    // same as
    static Integer lift1(String s, final Function<Integer, String> f) {
        return Integer.valueOf(f.apply(new Integer(s)));
    }

    public static <A, B, C> Function<Result<A>, Function<Result<B>, Result<C>>>
    lift2(Function<A, Function<B, C>> f) {

        // One approach - bad because it using .get method. Using the result of .get* method as an input to another method is a bad approach.
        //return ra -> rb -> Result.of(f.apply(ra.get()).apply(rb.get()));

        // Second approach (Comprehension Pattern) - very good. you have more than one Result objects as inputs to produce some output. So, you can use comprehension pattern.
        //return ra -> rb -> ra.flatMap(a -> rb.map(b -> f.apply(a).apply(b)));

        // or
        return ra -> rb -> ra.map(f).flatMap(f1 -> rb.map(f1));
        /*
        return ra -> rb -> {
            Result<Function<B, C>> one = ra.map(f);
            Result<C> two = one.flatMap(funTakingB -> rb.map(funTakingB));
            return two;
        };
        */
    }

    public static <A, B, C, D>
    Function<Result<A>,
            Function<Result<B>,
                    Function<Result<C>, Result<D>>
                    >
            >
    lift3(Function<A, Function<B, Function<C, D>>> f) {
        // this is a simple method
        return ra -> rb -> rc -> ra.flatMap(a -> rb.flatMap(b -> rc.map(c -> f.apply(a).apply(b).apply(c))));// using comprehension pattern
        // or
        //return ra -> rb -> rc -> ra.map(f).flatMap((f1) -> rb.map(f1)).flatMap((Function<Function<C, D>, Result<D>>) (f2) -> rc.map(f2));
    }

    // pg 212
    /*
    map2 method, taking as its arguments
        an Result<A>,
        an Result<B>
        and ra function from A to B to C

    and returning an Result<C>.
    */
    public <A, B, C> Result<C> map2(Result<A> ra,
                                    Result<B> rb,
                                    Function<A, Function<B, C>> f) {

        // Very Simple:
        // If you have more than one Result objects as input, you can simply use Comprehension Pattern
        //return ra.flatMap(a -> rb.flatMap(b -> Result.of(f.apply(a).apply(b))));
        // or
        //return ra.flatMap(a -> rb.map(b -> f.apply(a).apply(b)));
        // or
        return lift2(f).apply(ra).apply(rb);
    }

    // pg 202, 203
    public Result<V> filter(Function<V, Boolean> p) {
        return p.apply(get()) ? this : failure("Condition not matched");
/*
        return flatMap(x -> p.apply(x)
                ? this
                : failure("Condition not matched"));
*/
    }

    public Result<V> filter(Function<V, Boolean> p, String message) {
        return p.apply(get()) ? this : failure(message);
/*
        return flatMap(x -> p.apply(x)
                ? this
                : failure(message));
*/
    }

    // pg 203
    public boolean exists(Function<V, Boolean> p) {
        return map(p).getOrElse(false);
    }

    public static <T> Result<T> of(T value) {
        return value != null
                ? success(value)
                : Result.failure("Null value");
    }

    public static <T> Result<T> of(T value, String message) {
        return value != null
                ? success(value)
                : failure(message);
    }

    // pg 206, 207
    public static <T> Result<T> of(Function<T, Boolean> predicate, T value) {
        try {
            return predicate.apply(value)
                    ? success(value)
                    : empty();
        } catch (Exception e) {
            String errMessage =
                    String.format("Exception while evaluating predicate: %s", value);
            return Result.failure(new IllegalStateException(errMessage, e));
        }
    }

    public static <T> Result<T> of(Function<T, Boolean> predicate,
                                   T value, String message) {
        try {
            return predicate.apply(value)
                    ? Result.success(value)
                    : Result.failure(String.format(message, value));
        } catch (Exception e) {
            String errMessage =
                    String.format("Exception while evaluating predicate: %s",
                            String.format(message, value));
            return Result.failure(new IllegalStateException(errMessage, e));
        }
    }
}