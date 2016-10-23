package java8.functionalprograming.functionalprogramminginjavabook.chapter12;

import java.util.Random;

/**
 * @author Tushar Chokshi @ 8/27/16.
 */
public class JavaRNG implements RNG {
    private final Random random;

    private JavaRNG(long seed) {
        this.random = new Random(seed);
    }

    private JavaRNG() {
        this.random = new Random();
    }

    @Override
    public Tuple<Integer, RNG> nextInt() {
        return new Tuple<>(random.nextInt(), this);
    }

    public static RNG rng(long seed) {
        return new JavaRNG(seed);
    }

    public static RNG rng() {
        return new JavaRNG();
    }

}