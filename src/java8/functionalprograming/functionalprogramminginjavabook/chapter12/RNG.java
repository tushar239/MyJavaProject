package java8.functionalprograming.functionalprogramminginjavabook.chapter12;

/**
 * @author Tushar Chokshi @ 8/27/16.
 */
public interface RNG {
    Tuple<Integer, RNG> nextInt();
}
