package java8.functionalprograming.functionalprogramminginjavabook.chapter7;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Tushar Chokshi @ 11/7/16.
 */
// pg 200, 201
// The MyMap class using the new Result.Empty class for optional data.
public class MyMap<T, U> {
    private final ConcurrentMap<T, U> map = new ConcurrentHashMap<>();

    public static <T, U> MyMap<T, U> empty() {
        return new MyMap<>();
    }

    public static <T, U> MyMap<T, U> add(MyMap<T, U> m, T t, U u) {
        m.map.put(t, u);
        return m;
    }

    public Result<U> get(final T t) {
        return this.map.containsKey(t)
                ? Result.success(this.map.get(t))
                : Result.empty();
    }

    public MyMap<T, U> put(T t, U u) {
        return add(this, t, u);
    }

    public MyMap<T, U> removeKey(T t) {
        this.map.remove(t);
        return this;
    }

}
