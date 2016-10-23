package java8.functionalprograming.functionalprogramminginjavabook.chapter12;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;

/**
 * @author Tushar Chokshi @ 8/27/16.
 */
public class Generator {
    public static Tuple<Integer, RNG> integer(RNG rng) {
        return rng.nextInt();
    }

    // returning a random positive integer lower than a value passed as a parameter, but greater than or equal to 0.
    public static Tuple<Integer, RNG> integer(RNG rng, int limit) {
        Tuple<Integer, RNG> random = rng.nextInt();
        return new Tuple<>(Math.abs(random._1 % limit), random._2);
    }


    // Random class has two constructors
    // 1. that takes seed from you. It will calculate next number from this seed. If you pass the same seed every time you instantiate Random, it will always return same next number calculated from passed seed.
    // 2. assigns its own seed based on some long value and System.nanoSecond(). Every time you create Random's instance, seed will be different and so next number from that seed.

    // Generator's integer method is fully functional because it returns the same output for the same input every time.
    // Passing seed to Random using JavaRNG.rng(0) tells Random to get next integer from passed seed.

    // Instead of using JavaRNG.rng(0), if you use JavaRNG.rng() that initializes the state of Random based on some long value and current nanotime, you will not get consistent result all the time.
    // In that case Generator's integer method becomes non-functional.
    @Test
    public void testInteger() throws Exception {
        RNG rng = JavaRNG.rng(0);
        Tuple<Integer, RNG> t1 = Generator.integer(rng);
        assertEquals(Integer.valueOf(-1155484576), t1._1);

        Tuple<Integer, RNG> t2 = Generator.integer(t1._2);
        assertEquals(Integer.valueOf(-723955400), t2._1);

        Tuple<Integer, RNG> t3 = Generator.integer(t2._2);
        assertEquals(Integer.valueOf(1033096058), t3._1);


        // Generic API for Functional way
        {
            Function<RNG, Tuple<Integer, RNG>> function = rng1 -> Generator.integer(rng1);
            function.apply(rng);
        }

        // you can
        {
            Random<Integer> random = (rng1) -> rng1.nextInt();
            Tuple<Integer, RNG> tuple = random.apply(rng);
        }
        {
            Random<Integer> randomA = (rng1) -> rng1.nextInt();

            Function<Random<Integer>, Random<Integer>> function = ranA -> {
                Tuple<Integer, RNG> fromRanA = ranA.apply(rng);
                return (rng2) -> new Tuple<>(fromRanA._1, rng2);
            };
            Random<Integer> randomB = Random.map(randomA, function);
            randomB.apply(rng);
        }

    }

    @Test
    public void testRandomWithComposition() {
        RandomWithComposition<Point> randomA = new RandomWithComposition<>((rng) -> {
            Tuple<Integer, RNG> tuple = rng.nextInt();
            return new Tuple(new Point(tuple._1, tuple._1 + 1, tuple._1 + 2), rng);
        });

        {
            RNG rng = JavaRNG.rng(0);

            RandomWithComposition<Point> randomB = RandomWithComposition.map(randomA, randA -> {
                Function<RNG, Tuple<Point, RNG>> randARun = randA.run;
                return new RandomWithComposition<>(randARun);
            });
            Function<RNG, Tuple<Point, RNG>> run = randomB.run;
            Tuple<Point, RNG> tuple = run.apply(rng);
            System.out.println(tuple._1);

        }
    }

    public interface RandomWithInheritance<S, A> extends Function<S, Tuple<A, S>> {


    }


    public interface Random<A> extends RandomWithInheritance<RNG, A> {

        static <A> Random<A> unit(A a) { // unit method is a very useful method. It takes a bare value and returns it in the IO context.
            return rng -> new Tuple<>(a, rng);
        }


        // From RandomA, get RandomB (Transform RandomA to RandomB)
        static <A, B> Random<B> map(Random<A> randomA, Function<Random<A>, Random<B>> function) {
            return (rng) -> {
                Random<B> randomB = function.apply(randomA);
                return randomB.apply(rng);
            };
        }

        static <A, B> B map(RNG rng, Random<A> randomA, Function<Random<A>, Random<B>> function) {
            Random<B> randomB = function.apply(randomA);
            return randomB.apply(rng)._1;
        }

        static <A, B> Random<B> map(A a, Function<A, B> function) {
            return (rng) -> {
                B b = function.apply(a);
                return new Tuple<>(b, rng);
            };
        }

        static <A, B> Random<B> map3(Random<A> a, Function<A, B> function) {
            return (rng) -> {
                B b = function.apply(a.apply(rng)._1);
                return new Tuple<>(b, rng);
            };
        }

        // Composing state operations
        // From RandomA and RandomB, get RandomC (Transform RandomA and RandomB to RandomC)
        static <A, B, C> Random<C> map2(Random<A> ra, Random<B> rb,
                                        Function<A, Function<B, C>> f) {

            return (rng) -> {
                A a = ra.apply(rng)._1;
                B b = rb.apply(rng)._1;
                C c = f.apply(a).apply(b);
                return new Tuple<>(c, rng);
            };

        }

        static <A> Random<List<A>> sequence(List<Random<A>> rs) {
            return (rng) -> {
                List<A> listOfAs = new ArrayList<>();
                for (Random<A> r : rs) {
                    listOfAs.add(r.apply(rng)._1);
                }

                return new Tuple<>(listOfAs, rng);
            };
        }
    }


    public static class RandomWithGenericStateWithComposition<S, A> {
        protected Function<S, Tuple<A, S>> run;

        public RandomWithGenericStateWithComposition(Function<S, Tuple<A, S>> run) {
            this.run = run;
        }
    }

    public static class RandomWithComposition<A> extends RandomWithGenericStateWithComposition<RNG, A> {

        public RandomWithComposition(Function<RNG, Tuple<A, RNG>> run) {
            super(run);
        }

        static <A> RandomWithComposition<A> unit(A a) {
            return new RandomWithComposition<>((rng) -> new Tuple<>(a, rng));
        }

        static <A, B> RandomWithComposition<B> map(RandomWithComposition<A> randomA, Function<RandomWithComposition<A>, RandomWithComposition<B>> function) {
            return function.apply(randomA);
        }

        static <A, B> B map(RNG rng, RandomWithComposition<A> randomA, Function<RandomWithComposition<A>, RandomWithComposition<B>> function) {
            RandomWithComposition<B> randomWithCompositionB = function.apply(randomA);
            return randomWithCompositionB.run.apply(rng)._1;
        }
    }


    public static class Point {
        public final int x;
        public final int y;
        public final int z;

        public Point(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        @Override
        public String toString() {
            return String.format("Point(%s, %s, %s)", x, y, z);
        }
    }



}