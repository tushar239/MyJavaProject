package java8.functionalprograming.functionalprogramminginjavabook.chapter12;

/**
 * @author Tushar Chokshi @ 8/27/16.
 */
public class Tuple<T, U> {
    public final T _1;
    public final U _2;
    public Tuple(T t, U u) {
        this._1 = t;
        this._2 = u;
    } }