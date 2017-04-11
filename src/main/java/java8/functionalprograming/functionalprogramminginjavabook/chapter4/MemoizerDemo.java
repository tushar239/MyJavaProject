package java8.functionalprograming.functionalprogramminginjavabook.chapter4;

import java.util.function.Function;

import static java8.functionalprograming.functionalprogramminginjavabook.chapter4.Memoizer.memoize;

/**
 * @author Tushar Chokshi @ 9/5/16.
 */
public class MemoizerDemo {
    private static Integer longCalculation(Integer x) {
        try {
            Thread.sleep(1_000);
        } catch (InterruptedException ignored) {
        }
        return x * 2;
    }

    private static Function<Integer, Integer> f = MemoizerDemo::longCalculation;
    private static Function<Integer, Integer> g = memoize(f);

    public static void main(String[] args) {
        {
            long startTime = System.currentTimeMillis();
            Integer result1 = g.apply(1); // here it will memoize(cache) the value
            long time1 = System.currentTimeMillis() - startTime;

            System.out.println(result1); //2
            System.out.println(time1);//1003

            startTime = System.currentTimeMillis();
            Integer result2 = g.apply(1); // retrieving memoized(cached) value
            long time2 = System.currentTimeMillis() - startTime;

            System.out.println(result2); //2
            System.out.println(time2); //0
        }

        // Functions returning functions returning functions ... returning a result
        {
            Function<Integer, Function<Integer, Integer>> mhc =
                    memoize(x ->
                            memoize(y -> x + y));
            Integer curriedFunctionResult = mhc.apply(1).apply(2);



            Function<Integer, Integer> function = Memoizer.memoize(x -> x*10);

            Function<Integer, Function<Integer, Integer>> memoize = Memoizer.memoize((Integer x) -> Memoizer.memoize((Integer y) -> x + y));

            Integer result = function.apply(1);// 10
            // or
            result = Memoizer.memoize(1, x -> x * 10); // 10

            Function<Integer, Integer> function1 = Memoizer.memoize(1, x -> Memoizer.memoize(y -> x * y));
            result = function1.apply(10); //10


        }
        
        // A memoized function of a Tuple3
        {
            Function<Tuple3<Integer, Integer, Integer>, Integer> ft =
                    x -> longCalculation(x._1)
                            + longCalculation(x._2)
                            - longCalculation(x._3);
            Function<Tuple3<Integer, Integer, Integer>, Integer> ftm =
                    memoize(ft);

            long startTime = System.currentTimeMillis();
            Integer result1 = ftm.apply(new Tuple3<>(2, 3, 4)); // here it will memoize(cache) the value
            long time1 = System.currentTimeMillis() - startTime;

            System.out.println(result1); // 2
            System.out.println(time1); // 3008

            startTime = System.currentTimeMillis();
            Integer result2 = ftm.apply(new Tuple3<>(2, 3, 4)); // retrieving memoized(cached) value
            long time2 = System.currentTimeMillis() - startTime;

            System.out.println(result2); // 2
            System.out.println(time2); // 0
        }

    }
}
