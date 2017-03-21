package java8.functionalprograming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Supplier;

import static java8.functionalprograming.MyStreamReduceCollectApi.MyStream.myCollectMethod;

/**
 * @author Tushar Chokshi @ 3/20/17.
 */
public class MyStreamReduceCollectApi {

    public static void main(String[] args) {

        MyStream<Integer, Integer> myStream =
                new MyStream<>(1,
                        new MyStream<>(2,
                                new MyStream<>(3,
                                        new EmptyStream()
                                )
                        )
                );

        int identity = 0;

        // Major difference between the Java's internal code of Stream's reduce and collect methods are a below
        // reduce method accepts Function to operate on input stream. It means that it accepts some input and gives some output.
        // collect method accepts Consumer to operate on input stream. Consumer doesn't return anything. It means that you need to provide an object to collect method that can hold the output. collect method will just keep updating that object with output on every recursive call.

        Integer finalResult = MyStream.myReduceMethod(myStream, () -> identity, (head, identity1) -> head + identity1);
        System.out.println("Result from reduce method: " + finalResult);


        Result identityResult = new Result<>(identity);
        myCollectMethod(myStream, () -> identityResult, (head, identity1) -> identity1.setRes(head + identity1.getRes()));
        System.out.println("Result from collect method: " + identityResult.getRes());


        List<Long> offerIds = Arrays.asList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L);
        InputPartitioner<List<Long>> listInputPartitioner = new InputPartitioner<>(offerIds, 3);


        // Real Life example of collect method. Here, build method is same as collect.
        Map<Long, List<Criterion<Long>>> identityMap = new HashMap<>();

        Map<Long, List<Criterion<Long>>> buildResult =  new MyResultBuilder().build(listInputPartitioner,
                identityMap,
                // In Java8, Functions are nothing but the Callback methods. Callback method is nothing but you pass the code that gets executed at later point in time.
                new MyAccumulatorCallback<Long, Criterion<Long>>() {
                    @Override
                    public Map<Long, List<Criterion<Long>>> accumulate(List<Long> inputList) {
                        Map<Long, List<Criterion<Long>>> map = new HashMap<>();
                        for (Long input : inputList) {
                            List<Criterion<Long>> criteria = new ArrayList<>();
                            criteria.add(new Criterion<>(input * 10));
                            criteria.add(new Criterion<>(input * 20));
                            criteria.add(new Criterion<>(input * 30));
                            map.put(input, criteria);
                        }
                        return map;
                    }
                },
                new MyCombiner<>()
        );
        System.out.println("Build Result: " + buildResult);

    }

    static class MyStream<I, O> {
        private I head;
        private MyStream<I, O> tail;

        protected MyStream() {
        }

        public MyStream(I head, MyStream<I, O> tail) {
            this.head = head;
            this.tail = tail;
        }

        public boolean isEmpty() {
            return this instanceof EmptyStream;
        }

        public static <I, O> O myReduceMethod(MyStream<I, O> myStream, Supplier<O> identitySupplier, BiFunction<I, O, O> operation) { // same as collect method that has identity and accumulator
            if (myStream.isEmpty()) return identitySupplier.get();

            O identity = identitySupplier.get();

            O newIdentity = operation.apply(myStream.head, identity);

            return myReduceMethod(myStream.tail, () -> newIdentity, operation);
        }

        public static <I, O> void myCollectMethod(MyStream<I, O> myStream, Supplier<Result<O>> identitySupplier, BiConsumer<I, Result<O>> operation) { // same as collect method that has identity and accumulator
            if (myStream.isEmpty()) return;

            Result<O> identity = identitySupplier.get();

            operation.accept(myStream.head, identity);

            myCollectMethod(myStream.tail, () -> identity, operation);
        }
    }

    static class EmptyStream extends MyStream {

        public EmptyStream() {

        }
    }

    static class Result<O> {

        private O res;

        public Result(O res) {
            this.res = res;
        }

        public O getRes() {
            return res;
        }

        public void setRes(O res) {
            this.res = res;
        }
    }

    static class MyResultBuilder<I, O> {

        // When do you decide whether something should be passed as Callback (anonymous class object) or a concrete class object
        // e.g. if combiner operation is same (it always creates the same type of output e.g. map), then I would just write a concrete class and pass its object in this method.
        // accumulator
        public Map<I, List<O>> build(InputPartitioner<List<I>> inputPartitioner, // it has input list and partition size
                                     Map<I, List<O>> identity, // identity
                                     MyAccumulatorCallback<I, O> accumulator, // accumulator works on a partition elements and applies some transformation to them
                                     MyCombiner<I, O> combiner) { // combiner/reducer/aggregator combines the results from two accumulators
            InputPartitioner<List<I>>.PartitionResult<List<I>> partition = inputPartitioner.partition();

            while (partition != null) {
                Map<I, List<O>> accumulatedResult = accumulator.accumulate(partition.getPartition());
                identity = combiner.combine(identity, accumulatedResult);
                partition = inputPartitioner.partition(partition.getNextFrom());
                //return build(inputPartitioner, identity, accumulator, combiner);
            }

            return identity;
        }

    }

    interface MyAccumulatorCallback<I, O> {
        Map<I, List<O>> accumulate(List<I> input);
    }

    static class MyCombiner<I, O> {
        public Map<I, List<O>> combine(Map<I, List<O>> o1, Map<I, List<O>> o2) {
            o1.putAll(o2);
            return o1;
        }
    }

    static class InputPartitioner<T extends List> {

        private T input;
        private int size;

        public InputPartitioner(T input, int size) {
            this.input = input;
            this.size = size;
        }

        public PartitionResult partition() {
            return partition(0);
        }

        public PartitionResult partition(int from) {

            if (from >= input.size()) return null;
            if ((input.size() - from) < size)
                return new PartitionResult(input.subList(from, input.size()), size + from);

            return new PartitionResult(input.subList(from, from + size), size + from);
        }

        class PartitionResult<T extends List> {
            private int nextFrom;
            private T partition;

            public PartitionResult(T partition, int nextFrom) {
                this.nextFrom = nextFrom;
                this.partition = partition;
            }

            public int getNextFrom() {
                return nextFrom;
            }

            public T getPartition() {
                return partition;
            }
        }
    }

    static class Criterion<I> {
        private I input;

        public Criterion(I input) {
            this.input = input;
        }

        public String getOutput() {
            return input.toString() + "'s criterion";
        }

        @Override
        public String toString() {
            return getOutput();
        }
    }

}



