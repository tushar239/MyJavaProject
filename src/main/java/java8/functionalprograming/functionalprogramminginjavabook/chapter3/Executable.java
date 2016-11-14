package java8.functionalprograming.functionalprogramminginjavabook.chapter3;

/*
pg 64
Java 8 doesn't have this functional interface.
Without this functional interface, it becomes very difficult to make an imperative method functional wihtout breaking it into two functional methods at least.
You can see its example of it on pg 65.
 */
public interface Executable {
    void execute();
}
