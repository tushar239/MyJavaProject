package java8.functionalprograming.functionalprogramminginjavabook.chapter7;

import java8.functionalprograming.functionalprogramminginjavabook.chapter13.Effect;

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
        public String toString() {
            return "Empty()";
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
        public V get() {
            return null;
        }
    }

    public static <V> Result<V> empty() {
        return empty;
    }

    static void testLift() {
        lift1((a) -> String.valueOf(a)).apply("2");
    }

    static <A, B> Function<Result<A>, Result<B>> lift(final Function<A, B> f) {
        return (r) -> r.map(f);
    }

    static Integer lift1(String s, final Function<Integer, String> f) {
        return Integer.valueOf(f.apply(new Integer(s)));
    }

    static Function<String, Integer> lift1(final Function<Integer, String> f) {
        return (s) -> Integer.valueOf(f.apply(new Integer(s)));
    }

    public static <A, B, C> Function<Result<A>, Function<Result<B>,
            Result<C>>> lift2(Function<A, Function<B, C>> f) {
        return ra -> rb -> ra.map(f).flatMap(f1 -> rb.map(f1));
    }
}