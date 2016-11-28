package java8.functionalprograming.functionalprogramminginjavabook.chapter13.outputtingdata;

/**
 * @author Tushar Chokshi @ 8/28/16.
 */

/*
As an effect is generally applied to a value, a pure effect can be modeled as a special kind of function, returning no value.

Note that this is equivalent to the Consumer interface of Java.

 */
public interface Effect<T> {
    void apply(T t);
}