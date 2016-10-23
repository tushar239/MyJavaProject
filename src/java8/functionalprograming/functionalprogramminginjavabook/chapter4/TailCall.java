package java8.functionalprograming.functionalprogramminginjavabook.chapter4;

import java.util.function.Supplier;

/**
 * @author Tushar Chokshi @ 9/1/16.
 */
public abstract class TailCall<T> {

    public abstract TailCall<T> callSupplier();

    public abstract T eval();

    public abstract boolean isInstanceOfSupplierContainer();

    public static <T> FinalValueContainer<T> getFinalValueContainer(T t) {
        return new FinalValueContainer<>(t);
    }

    public static <T> SupplierContainer<T> getSupplierContainer(Supplier<TailCall<T>> t) {
        return new SupplierContainer<>(t);
    }

    public static class FinalValueContainer<T> extends TailCall<T> {
        private final T value;

        public FinalValueContainer(T value) {
            this.value = value;
        }

        @Override
        public T eval() {
            return value;
        }

        public T getValue() {
            return value;
        }

        @Override
        public boolean isInstanceOfSupplierContainer() {
            return false;
        }

        @Override
        public TailCall<T> callSupplier() {
            throw new IllegalStateException("Return has no callSupplier");
        }
    }

    public static class SupplierContainer<T> extends TailCall<T> {
        private final Supplier<TailCall<T>> value;

        public SupplierContainer(Supplier<TailCall<T>> value) {
            this.value = value;
        }

        @Override
        public T eval() {
            TailCall<T> tailRec = this;
            while (tailRec.isInstanceOfSupplierContainer()) {
                tailRec = tailRec.callSupplier(); // do supplier.get()
            }
            return tailRec.eval(); // get the value from FinalValueContainer
        }

        @Override
        public boolean isInstanceOfSupplierContainer() {
            return true;
        }

        @Override
        public TailCall<T> callSupplier() {
            return value.get();
        }
    }

    public static <T> FinalValueContainer<T> ret(T finalValue) {
        return new FinalValueContainer<>(finalValue);
    }

    public static <T> SupplierContainer<T> sus(Supplier<TailCall<T>> s) {
        return new SupplierContainer<>(s);
    }

}
