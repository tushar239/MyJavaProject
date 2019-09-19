package java8.functionalprograming;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/*

Java 8 In Action (java8_in_action.pdf) book
-------------------------------------------

Qualities of Functional Programming:
 - Immutability
 - Higher-order functions
 - no side-effects
 - chaining (function pipeline)
 - lazy evaluation


Chapter 1

Chapter 2

Chapter 3

Chapter 4

Chapter 5

    List<Dish> vegetarianDishes = new ArrayList<>();
    for(Dish d: menu){
        if(d.isVegetarian()){ vegetarianDishes.add(d);}
    }

    vs

    List<Dish> vegetarianDishes = menu.stream() .filter(Dish::isVegetarian) .collect(toList());

    This different way of working with data is useful because you let the Streams API manage how to process the data.
    As a consequence, the Streams API can work out several optimizations behind the scenes.
    In addition, using internal iteration, the Streams API can decide to run your code in parallel.
    Using external iteration, this isn’t possible because you’re committed to a single-threaded step-by-step sequential iteration.


    5.2 Filtering and slicing:

        Filtering a stream with a predicate:
            Use of filter(predicate)

        Filtering unique elements:
            Use of distinct()

        Truncating a stream:
            Use of limit(n), skip(n)
            Use of collect(Collectors.toList())

        Mapping:
            Use of map(Function<I,O>)
            Use of flatMap(Function<I,Stream<O>>)  - pg 124
                flatMap has the effect of replacing each generated stream by the contents of that stream

            String[] words = {"Hello", "World"};
            Stream<String> streamOfwords = Arrays.stream(words);

            final Stream<String[]> stream1 = streamOfwords.map(word -> word.split(""));// [["H","e","l","l","o"], ["w","o","r","l","d"]]

            //final Stream<Stream<String>> streamOfStreams = stream1.map(strArray -> Arrays.stream(strArray)); // you don't want Two-Level Streams (Stream<Stream<String>>). You want String<String>
            final Stream<String> streamOfStrings = stream1.flatMap(str -> Arrays.stream(str)); // flatMap has the effect of replacing each generated stream by the contents of that stream

            final List<String> collect = streamOfStrings.collect(Collectors.toList()); // ["H","e","l","l","o","w","o","r","l","d"]
            System.out.println(collect);

            In a nutshell, the flatMap method lets you replace each value of a stream with another stream and then concatenates all the generated streams into a single stream.

    5.3 Finding and matching

        - Checking to see if a predicate matches at least one element
        Use of anyMatch(predicate)

        if(menu.stream().anyMatch(Dish::isVegetarian)) {...}

        - Checking to see if a predicate matches all/none elements
        Use of allMatch(predicate), noneMatch(predicate)

        if(menu.stream().allMatch(Dish::isVegetarian)) {...}
        if(menu.stream().noneMatch(Dish::isVegetarian)) {...}

        These three operations, anyMatch, allMatch, and noneMatch, make use of what we call short-circuiting, a stream version of the familiar Java short-circuiting && and || operators.
        You need only find out that one expression is false to deduce that the whole expression will return false, no matter how long the expression is; there’s no need to evaluate the entire expression. This is what short-circuiting refers to.

        - Finding an element
        Use of findAny()/findFirst() ---- returns Optional

        Optional<Dish> dish = menu.stream().filter(Dish::isVegetarian).findAny();

    5.4 Reducing  (Folding)

        you have seen

        boolean     allMatch, anyMatch, noneMatch
        void        forEach
        Optional    findAny, findFirst
        Collection  collect

        Here you will see the use of reduce(initial value, BinaryOperator)
        int sumIteratively = numbers.stream().reduce(0, (a, b) -> a + b);

        - reduce(initial value/seed/identity, BinaryOperator accumulator, BinaryOperator combiner) --- returns value
        - reduce(initial value/seed/identity, BinaryOperator accumulator) --- returns value --- same as reduce(identity,accumulator,accumulator) accumulator function is used for combiner also
        - reduce(BinaryOperator accumulator)  --- returns Optional --- same as reduce(Optional.empty(),accumulator,accumulator)
          Why does it return an Optional?
            Consider the case when the stream contains no elements. The reduce operation can’t return any result because it doesn’t have an initial value. This is why the result is wrapped in an Optional object to indicate that the result may be absent.

            If initial value is not passed by us, it takes the first value retrieved from stream as initial value.

            boolean foundAny = false;
            T result = null;
            for (T element : this stream) {
                if (!foundAny) { // only for first element
                    foundAny = true;
                    result = element; // initial value
                }
                else
                    result = accumulator.apply(result, element);
            }
            return foundAny ? Optional.of(result) : Optional.empty();


        (IMP)
         reduce should never mutate the parameter passed to it. It should just do some operation on parameters and return a new object. If you try to mutate parameter, it can give you wrong result or exception during parallel processing.
         For mutating the parameter, you should use collect.


        sumIteratively(), min(), max() are variant of reduce() only. They can be used on IntStream/LongStream etc, not on Stream directly.

        numbers.stream().map((n) -> n * n).reduce(0, (n1,n2) -> n1+n2);  // or reduce(0, Integer::sum);
        // same as
        numbers.stream().map((n) -> n * n).reduce(0, (n1,n2) -> Integer.sum(n1, n2));
        // same as
        numbers.stream().map((n) -> n * n).sum()

        sumIteratively(), min(), max() etc use reduce() only internally.


        Similarly, min and max can be done.

        numbers.stream().map((n) -> n * n).reduce(Math::min) // this will return OptionalInt as you are not passing identity/seed to reduce method


       Interesting things to know about reduce (IMP)
        https://www.youtube.com/watch?v=oWlWEKNM5Aw

        - initial value passed to reduce is very important.

        numbers.stream()        .map((n) -> n * n).reduce(100, (n1,n2) -> n1+n2);
        numbers.parallelStream().map((n) -> n * n).reduce(100, (n1,n2) -> n1+n2);

        Sequential and Parallel operations will give different results, if initial value is not chosen carefully. Your code should be written in such a way that whatever works for Sequential should also work for Parallel processing.
        Parallel processing works on ForkAndJoin algorithm of Java 7. It divides the input to many threads and each thread runs together using same initial value. So, think like thread 1 is adding values to 100, thread 2 is adding values to 100 and so on, eventually when threads are combined, result is created by adding many 100s instead of just one.

        Integer[] intArray = new Integer[] {1,2,3,4,5,6,7};
        Integer result = Arrays.stream(intArray).reduce(100, (n1, n2) -> n1 + n2);    // using sequential processing
        System.out.println(result); // 128
        result = Arrays.stream(intArray).parallel().reduce(100, (n1, n2) -> n1 + n2); // using parallel processing
        System.out.println(result);// 728


        - Read 'collect Vs. reduce' section(6.2.4) of Chapter 6.


        stateless vs. stateful stream operations (IMP)

            Intermediate Operations
                stateless (map and filter) - like map and filter reserve each element from the input stream and produce zero or one result in the output stream. These operations are thus in general stateless: they don’t have an internal state
                stateful (sorted, distinct, skip, limit) - By contrast, some operations such as sorted or distinct seem at first to behave like filter or map—all reserve a stream and produce another stream (an intermediate operation), but there’s a crucial difference. Both sorting and removing duplicates from a stream require knowing the previous history to do their job. For example, sorting requires all the elements to be buffered before a single item can be added to the output stream; the storage requirement of the operation is unbounded. This can be problematic if the data stream is large or infinite.
                    sorted, distinct requires unbounded state.
                    skip and limit requires bounded state.

                    Try to avoid stateful operations ON parallel stream, why? (IMP)
                    https://jaxenter.com/java-performance-tutorial-how-fast-are-the-java-8-streams-118830.html
                    e.g. distinct() operation.
                        It is an intermediate operation that eliminates duplicates from the input sequence, i.e., it returns an output sequence with distinct elements.
                        In order to decide whether the next element is a duplicate or not the operation must compare to all elements it has already encountered.
                        For this purpose it maintains some sort of data structure as its state. If you call distinct() on a parallel stream its state will be accessed concurrently by multiple worker threads, which requires some form of coordination or synchronisation, which adds overhead, which slows down parallel execution, up to the extent that parallel execution may be significantly slower than sequential execution.

                        Suggestion: (IMP)
                        Even though these operations give correct results in parallel processing, it is advisable not to use them ON parallel stream.
                        numbers.parallelStream().distinct()... ---- try to avoid
                        numbers.stream().distinct().parallelStream().... ---- good



            Terminal Operations
                Operations like reduce, sumIteratively, and max need to have internal state to accumulate the result. In this case the internal state is small. In our example it consisted of an int or double.
                The internal state is of bounded size no matter how many elements are in the stream being processed.

                if you see Stream's reduce, it uses ReduceOps's makeRef method that keeps a state.

            from 'https://www.youtube.com/watch?v=H7VbRz9aj7c'
                how to decide whether to keep state in any stream operation provided by Java 8. 'collect Vs. reduce' section(6.2.4) of Chapter 6 can help you to understand as it explained below.

                If stream operation expects
                    Function as an argument, then it means that you should use the parameters of that function and should not mutate them and create a new object out of that and return that from a Function.
                    Consumer as an argument, then as Consumer doesn't return anything back, it is ok to mutate the parameter passed to Consumer.
                    But....
                      Stream has only 3 operations that reserve Consumer as parameter (IMP)
                      - forEach
                      - forEachOrdered
                      - peek
                      JavaDoc specifically says that if you mutate the parameter passed to Consumer parameter of these operations, then you are responsible to maintain its state between different threads in case of parallel processing, java doesn't do that internally.
                      So, bottom line is try not to mutate the parameters passed to any lambda expression in Java 8. If you need to do that then better to use 'collect' operation that handles the mutation of parameter passed to accumulator properly between multiple threads in case of parallel processing.

                forEach vs forEachOrdered

                    It makes a difference in parallel processing. During parallel processing work is divided to many threads (processors).
                    Each thread will execute forEach at its own time. e.g. "AAA" is given to thread-1, "BBB" is given to thread-2 and "CCC" is given to thread-3
                    order of threads finishing their work is thread-2,1,3. In that case O/P: BBB,AAA,CCC

                    If forEachOrdered is used, then it hurts the performance of parallelizm, but it always returns AAA,BBB,CCC

                    http://stackoverflow.com/questions/32797579/foreach-vs-foreachordered-in-java-8-stream
                    Stream.of("AAA", "BBB", "CCC").parallel().forEach(s -> System.out.println("Output:" + s));
                    Stream.of("AAA", "BBB", "CCC").parallel().forEachOrdered(s -> System.out.println("Output:" + s));

                    The second line will always output

                    Output:AAA
                    Output:BBB
                    Output:CCC

                    whereas the first one is not guaranteed since the order is not kept.
                    forEachOrdered will processes the elements of the stream in the order specified by its source, regardless of whether the stream is sequential or parallel.

    5.6 Numeric Streams

        When to use Primitive Stream (Numeric Stream) instead of reduce? (IMP)

            int calories = menu.stream().map(Dish::getCalories).reduce(0, Integer::sumIteratively);
            The problem with this code is that there’s an insidious BOXING cost. Dish.getCalories return Integer. Integer.sumIteratively(int, int) expects ints as parameters. So, behind the scenes each Integer needs to be unboxed to a primitive before performing the summation.
            Accumulator has to do this behind the scene
            (Integer i1, Integer i2) -> {int sumIteratively = Integer.valueOf(i1) + Integer.valueOf(i2); return Integer form of sumIteratively}

            Java 8 introduces three primitive specialized stream interfaces to tackle this issue, IntStream, DoubleStream, and LongStream, that respectively specialize the elements of a stream to be int, long, and double—and thereby avoid hidden boxing costs.

            int calories = menu.stream().mapToInt(Dish::getCalories).sumIteratively();

            Converting back to Stream<T>

            IntStream s = menu.stream().mapToInt(Dish::getCalories);
            Stream<Integer> boxed = s.boxed();

            sumIteratively(), min(), max() are variant of reduce() only, but it has a benefit over reduce()

            I tried below example. Difference between doing boxing and unboxing in reduce is huge compared to doing unboxing just once for every element in mapToInt.
            mapToInt converts Integer to int just once, but reduce has to convert convert n1 and n2 from Integer to int for doing summation and result has to be converted back to Integer, so there is multiple boxing+unboxing. So, cost is more.

                List<Integer> numbers = new ArrayList<>();
                int i = 0;
                while (i < 100000) {
                    numbers.add(i++);
                }
                {
                    long start = System.nanoTime();
                    Integer reduce = numbers.stream().map((n) -> n * n).reduce(0, (n1, n2) -> n1 + n2);
                    long end = System.nanoTime();
                    System.out.println("Time taken by reduce: " + (end - start) + " nanoseconds");// Time taken by reduce: 78040813 nanoseconds
                }

                {
                    long start = System.nanoTime();
                    int sumIteratively = numbers.stream().mapToInt((n) -> n * n).sumIteratively();
                    long end = System.nanoTime();
                    System.out.println("Time taken by reduce: " + (end - start) + " nanoseconds");// Time taken by reduce: 7868144 nanoseconds
                }

        Default values: OptionalInt:

            int sumIteratively = numbers.stream().map((n) -> n * n).reduce(0, (n1,n2) -> n1+n2); // reduce with initial value returns primitive
            Optional<Integer> sumIteratively = numbers.stream().map((n) -> n * n).reduce((n1,n2) -> n1+n2); // reduce without initial value returns Optional<Integer>
            // OR
            // sumIteratively(), min(), max() are variant of reduce() only, but it has a benefit
            // during reduce(initial value, BinaryOperator), there’s an insidious boxing cost. Behind the scenes each Integer needs to be unboxed to a primitive before performing the summation because BinaryOperator is a Function that operates only on Wrappers and not Premitivies.
            // When you use Numeric Streams (IntStream/LongStream etc), this boxing cost is not there.
            OptionalInt minNumber = numbers.stream().mapToInt((n) -> n * n).min(); // min() is same as reduce(Math::min)

            Here why min() t has to return OptionalInt?
            Internally, it is same as reduce(Math::min) that doesn't use initial value and so it return Optional. Similarly, min/max return OptionalInt(isPresent=false, value=0)

            Likewise, there are OptionalInt, OptionalDouble, and OptionalLong

        Numeric ranges:

            IntStream.range(0,10).filter(.....)
            This is like for(int i=0; i<10; i++)

            IntStream.rangeClosed(0,10).filter(.....)
            This is like for(int i=0; i<=10; i++)

    5.7. Building streams

         1) Creating empty stream
         Stream<String> emptyStream = Stream.empty();

         2) Building stream from values
         Stream<String> stream = Stream.of("Java 8 ", "Lambdas ", "In ", "Action");

         3) Creating stream from array. e.g. you can convert an array of primitive ints into an IntStream
         int[] numbers = {1,2,3}
         int sumResult = Arrays.stream(numbers).sum()

         4) Creating stream from a file
         Java’s NIO API (non-blocking I/O), which is used for I/O operations such as processing a file, has been updated to reserve advantage of the Streams API.
         Many static methods in java.nio.file.Files return a stream. For example, a useful method is Files.lines, which returns a stream of lines as strings from a given file.

         Stream<String> lines = Files.lines(Paths.get("./MyJavaProject/src/java8/data.txt"), Charset.defaultCharset());
         //Stream<String[]> stream = lines.map(line -> line.split(" "));
         Stream<String> stream = lines.flatMap(line -> Arrays.stream(line.split(" ")));
         long uniqueWordCount = stream.distinct().count();

         5) Creating stream from collection
         List<Integer> list = new ArrayList();
         list.add(1);list.add(2);list.add(3);

         Stream<Integer> stream = list.stream();

         6) Creating stream from Iterator

         List<Integer> list = new ArrayList();
         list.add(1);list.add(2);list.add(3);

         Iterator<Integer> iterator = list.iterator();

         From Iterator, you need to create spliterator and using spliterator, you can create a stream.

         Spliterator<Integer> spliterator = Spliterators.spliteratorUnknownSize(sourceIterator, Spliterator.IMMUTABLE | Spliterator.NONNULL | Spliterator.DISTINCT | Spliterator.ORDERED | Spliterator.SORTED |Spliterator.SIZED | Spliterator.SUBSIZED);

         Stream<Integer> targetStream = StreamSupport.stream(spliterator, true);



         Streams from functions: creating infinite streams!

            Stream.iterate and Stream.generate. These two operations let you create what we call an infinite stream

            Stream.iterate(0, n -> n + 2).limit(10)

                The iterate method takes an initial value, here 0, and a lambda (of type Unary-Operator<T>) to apply successively on each new value produced. Here you return the previous element added with 2 using the lambda n -> n + 2
                Note that this operation produces an infinite stream—the stream doesn’t have an end because values are computed on demand and can be computed forever. We say the stream is unbounded.
                You’re using the limit method to explicitly limit the size of the stream.

            intStream.range(0, 10).map(n -> n+2)

                Using range is better than iterate because iterate works on objects, so if you use iterate over ints, there will a cost of unboxing for n+2 like operations and then converting back to Integer(boxing) for creating Stream<Integer>
                Use iterate for real objects(not wrappers of primitives).


            IntStream ones = IntStream.generate(() -> 1);   This will repeatedly generate 1.
            Stream.generate(Math::random).limit(5)

                Similarly to the method iterate, the method generate lets you produce an infinite stream of values computed on demand. But generate doesn’t apply successively a function on each new produced value. It takes a lambda of type Supplier<T> to provide new values.




Chapter 6   (Collecting data with streams)
    collect is a terminal method of a stream.

    There are many syntaxes of collect method.

        - collection.stream().collect(Collector)    --- This is the most row form. All other syntaxes get converted into this form eventually.
          You can read about Collector in detail in section 6.5

        - collection.stream().collect(supplier, accumulator and combiner)

        - collection.stream().collect(Collectors utility method).

    Collector is an interface. It has following methods

        Supplier<A> supplier()
        BiConsumer<A, T> accumulator()
        BinaryOperator<A> combiner()
        Function<A, R> finisher()
        Set<Characteristics> characteristics()

        Collector<T, A, R> of(Supplier<A> supplier,
                              BiConsumer<A, T> accumulator,
                              BinaryOperator<A> combiner,
                              Function<A, R> finisher,
                              Characteristics... characteristics)

        List collector = integerStream.collect(Collectors.toList());

        is same as

        List collector = integerStream.collect(
                                    ArrayList::new, // same as () -> new ArrayList<>()  --- identity list
                                    List::add, // same as //(identityList,b) -> nilList.add(b)
                                    List::addAll // same as (lists from thread1, list2 from thread2) -> {return list1.addAll(list2);}
                            );

        Internally, it is converted to
        List collector = integerStream.collect(
                                                Collectors.new CollectorImpl<>( --- you cannot instantiate CollectorImpl. This formation happens internally in Java.
                                                                ArrayList::new,
                                                                List::add,
                                                                (left, right) -> { left.addAll(right); return left; },
                                                                Collector.Characteristics.IDENTITY_FINISH --- This will create result in a Finisher that just returns the output same as input. I don't see any API that let's you pass Finisher. May be it is just for Java's internal use.
                                                             )
                                              );


    Collectors is a utility class having many static factory methods returning Collector.

    There are some Predefined collectors that do following
    - Reducing and summarizing stream elements to a single value
    - Grouping elements
    - Partitioning elements

    6.2. Reducing and summarizing

    They can be used every time you want to combine all the items in the stream into a single result

    long howManyDishes =  menu.stream().collect(Collectors.counting());

    You can write this far more directly as

    long howManyDishes = menu.stream().count();

    but the counting collector can be especially useful when used in combination with other collectors, as we demonstrate later.

    6.2.1. Finding maximum and minimum in a stream of values

    Collectors.reducing(BinaryOperator) or (identity, Function mapper, BinaryOperator op)
        It internally creates Collector only, which accepts identity element as Supplier that helps during parallel processing. As you know there is a difference between stream.collect(Collectors.reducing(...) and stream.reduce(...) methods when parallel processing happens.
    Collectors.counting()
        It uses Collectors.reducing(0L, e -> 1L, Long::sum) internally
    Collectors.minBy
        It uses Collectors.reducing(BinaryOperator.minBy(comparator)) internally
    Collectors.maxBy
        It uses Collectors.reducing(BinaryOperator.maxBy(comparator)) internally

    Optional<Dish> mostCalorieDish = menu.stream().collect(Collectors.maxBy(dishCaloriesComparator));

    6.2.2. Summarization

    Collectors.summingInt  --- same as stream.mapToInt(...).sum(), which is same as stream.mapToInt(...).reduce(0, Integer::sum).
    Collectors.summingLong
    Collectors.summingDouble

    Collectors.averagingInt
    Collectors.averagingLong
    Collectors.averagingDouble

    Collectors.comparingInt
    Collectors.comparingLong
    Collectors.comparingDouble

    int totalCalories = menu.stream().collect(Collectors.summingInt(Dish::getCalories));

    See Collectors.reducing in below section. It is a generalization of all above methods.


    Collectors.summerizingInt - It takes ToIntFunction as a parameter that can convert passed object to int. It returns a Collector that has a Finisher IntSummaryStatistics. This Finisher just summerizes everything like sumIteratively, min, max, count etc.

    Collectors.toList() // If you want Collectors to create a new nilList to collect elements.
    Collectors.toCollection(() -> nilList) // if you want to use external nilList to collect elements. Try to avoid using this approach because it can create problems when you use Parallel stream when you try to mutate the same nilList shared between multiple threads. To understand it more, read Chapter 7's 7.1.3 section. Use first approach (toList()) which will create a new nilList for each thread and these lists from all threads will be combined in one at the end.
    Collectors.toSet()
    Collectors.toCollection(() -> set)

    6.2.3. Joining Strings

    Collectors.joining() ---  --- (IMP) use 'Collectors.joining' instead of '.reduce("", (s1,s2) -> s1+s2)' because Collectors.joining uses StringBuilder to concatenate strings, where as reduce method doesn't.
    Collectors.joining(delimiter)

    String shortMenu = menu.stream().map(Dish::getName).collect(Collectors.joining());
    String shortMenu = menu.stream().map(Dish::getName).collect(joining(", "));

    6.2.4. Generalized summarization with reduction (IMP)

    Collectors.reducing(initial value/seed/identity, BinaryOperator)

        All collectors that we have discussed so far are only convenient specializations of a reduction process that can be defined using the reducing factory method.
        The Collectors.reducing factory method is a generalization of all of them.
        Collectors.reducing is almost same as stream.reduce. Only difference is that it can not reserve initial value/seed/identity as a parameter like stream.reduce.

        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);

        numbers.stream().mapToInt(n -> n).sumIteratively();
        //or
        numbers.stream().collect(Collectors.summingInt(n -> n));
        //or
        numbers.stream().collect(Collectors.reducing((n1, n2) -> n1+n2));

        numbers.stream().mapToInt(n -> n).max();
        //or
        numbers.stream().collect(Collectors.maxBy((n1, n2) -> n1.compareTo(n2)));
        //or
        numbers.stream().collect(Collectors.reducing(BinaryOperator.maxBy((n1, n2) -> n1.compareTo(n2))));
        //or
        numbers.stream().collect(Collectors.reducing((n1, n2) -> n1.compareTo(n2) >=0 ? n1:n2 ));


    Collect vs. reduce (IMP)

        As you see, both reduce and collect provides almost the same functionality. Both provides map-reduce kind of functionality. accumulator does mapping, combiner does reducing.

        Both of them can reserve
        - initial value/seed/identity (collect accepts '()->identity'. reduce accepts 'identity')
        - accumulator
        - combiner (Where combiner is not passed, accumulator function is used as combiner function also)
        - finisher (collect has it, reduce doesn't have it. But even for collect, we normally do not pass it. I don't see any .collect API that accepts Finisher from outside java's internal methods.)


        collect(Supplier supplierOfIdentity,  --- supplier that creates an initial object/seed/identity
                BiConsumer accumulator, --- accumulates stream's element into initial object. This happens for all elements one by one.
                BiConsumer combiner); ---  it is mainly for parallel streams to combine the result of two parallel streams into one. combiner will reserve return values of accumulators of two parallel streams and combine them in one.

        reduce(initial value/seed/identity,
               BiFunction accumulator,
               BinaryOperator combiner);

    from 'https://www.youtube.com/watch?v=oWlWEKNM5Aw', 'https://www.youtube.com/watch?v=H7VbRz9aj7c'

        - there is a overhead, if you use reduce for concatenating strings
        strings.stream().reduce("", (s1,s2) -> s1+s2);

        when you do s1+s2+s3+...+sn in java, from Java 5 onwards, compiler will do 'new StringBuilder().append(s1).append(s2)....' instead of creating new objects for every two string concatenation.
        If there are just two strings need to be concatenated s1+s2, then this optimization is not used. This improvement will be done in Java 9, but for now we have live with this limitation.
        above reduce operation does exactly s1+s2 and so compiler doesn't use optimization. So be careful while using reduce for string concatenation.

        Use strings.stream().collect(Collectors.joining()) instead of reduce
        Collectors.joining() returns Collector<CharSequence, ?, String>, it does optimized way of concatenating CharSequence.
        It uses StringJoiner that needs CharSequence and uses StringBuilder to concatenate CharSequences

        how can i write Collectors.joining() by myself?
        String s = strings.stream()
                          .collect(StringBuilder::new,
                                    (StringBuilder sb1, String s1) -> sb1.append(s1),
                                    (StringBuilder sb1, StringBuilder sb2) -> sb1.append(sb2)
                                  );

        More elegantly using method references
        String s = strings.stream()
                          .collect(StringBuilder::new,
                                    StringBuilder::append,
                                    StringBuilder::append
                                  );
        - Rule of Thumb:
        reduce's accumulator should never mutate the parameter that it is passed. If you want to do that use collect.

        reduce should never mutate the parameter passed to it. It should just do some operation on parameters and return a new object. If you try to mutate parameter, it can give you wrong result or exception during parallel processing.
        For mutating the parameter, you should use collect.

        WHY ????? (IMP)
        If you see the signature of reduce and collect, you can easily figure out the answer
        reduce takes 'BiFunction accumulator'. Function returns something (in case of reduce, it should return a new value accumulated with elements)
        collect takes 'BiConsumer accumulator'. Consumer doesn't return anything (it means that in case of collect, it should mutate passed parameter to accumulate the elements)

        Let's take a deeper look
        Assume that you are using parallel processing for above string concatenation example.
        reduce uses the same StringBuilder object between all parallel threads. StringBuilder is not thread-safe. Many threads can append the values to it at the same time, when that happens it can corrupt the StringBuilder.
        Moreover, stringBuilder.append returns the same StringBuilder object. When during Join time of threads, combiner will try append the same StringBuilder object to itself and that will result in the wrong result.

        In case of collect, this problem doesn't occur because first argument is a Supplier that returns a new object and each thread will call supplier and use different StringBuilder objects for accumulation. Combiner will get different StringBuilder objects to combine.

        To avoid this problem in reduce, in accumulator, you can create a new StringBuilder and append passed parameters to it and return this new StringBuilder. This will work, but it will create a log many unnecessary StringBuilder objects that need to garbage collected quickly, which is not so efficient.

        See "MyStreamReduceCollectApi.java" to see how reduce and collect methods work.

    Choosing the best solution for your situation

        Java 8 functional programming often provides multiple ways to perform the same operation.
        Our suggestion is to explore the largest number of solutions possible for the problem at hand, but always choose the most specialized one that’s general enough to solve it.
        This is often the best decision for both readability and performance reasons.
        As mentioned in Chapter 5's 5.7 section-
        For instance, to calculate the total calories in our menu, we’d prefer the last solution (using IntStream) because it’s the most concise and likely also the most readable one.
        At the same time, it’s also the one that performs best, because IntStream lets us avoid all the auto-unboxing operations, or implicit conversions from Integer to int, that are useless in this case.

    6.3. Grouping

    Collectors.groupingBy(Function, Collector)

    Map<key, value> map = stream.collect(Collectors.groupingBy(Function, Collector))
    Function is to decide key and Collector is to decide value of the returned map.

    (IMP) groupingBy returns Collector, so it means that one groupingBy can reserve another groupingBy as a second parameter.
    All the methods of Collectors utility class returns Collector.
    This helps to create multilevel grouping as mentioned in below section.

    Most Raw Syntax:

        Map<key, value> result =
        Collectors.groupingBy(Function, ---  to decide key of Map
                             Supplier, ---  supplier of identity map (default is () -> new HashMap())
                             Collector) ---  for collecting result as map's value (default is Collectors.toList()). (IMP) it can reserve any Collector instance returned by any utility method of Collectors class.


        (IMP) See example in MyStreamReduceCollectGroupingByMappingEtcApi.java

    Other Syntaxes:

        Collectors.groupingBy(Function)
            internally uses
        Collectors.groupingBy(Function, HashMap::new, Collectors.toList())

        Collectors.groupingBy(Function, mapping(Function, Collector)) --- mapping returns a Collector

            people.stream().collect(Collectors.groupingBy(Person::getCity))
            creates a Map<city, List<Person>>

            people.stream().collect(Collectors.groupingBy(Person::getCity, Collectors.mapping(Person::getLastName, toSet())));
            It creates a Map<city, Set<lastname>>


    How collector is executed by a stream.collect(Collector) method?

        All utility methods of Collectors returns a Collector that will have a Supplier, Accumulator, Combiner, Finisher

        stream.collect(Collector) method executes this collector


            Till stream ends {
                O identityResult = collector.supplier().get();
                collector.getAccumulator().apply(streamElement, identityResult); --- accumulator collects stream element into identityResult
            }

           See example in MyStreamReduceCollectGroupingByMappingEtcApi.java

    6.3.1. Multilevel grouping

    Collectors.collectingAndThen(Collector downstream, Function finisher)

        Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList)
        It is same as Collectors.toList() + do something on returned nilList.

        Another example
        nilList.stream()
            .collect(
                Collectors.collectingAndThen(
                    Collectors.maxBy(Collectors.comparingInt(Person:getAge())), --- This method returns a Collector<Person, ?, Optional<Person>>
                    Optional::get  --- This function is combined with finisher part of above return Collector<Person, ?, Optional<Person>>
                )
            )

    Collectors.mapping(Function mapper, Collector downstream)

        Before calling Collector's accumulator, it transforms the accumulator's input using mapper function.

        List<Integer> collectingAge2 = persons.stream()
                                              .collect(ArrayList<Integer>::new,
                                                        (list, person) -> list.add(person.getAge()), // it is adding person's age
                                                        (list1, list2) -> list1.addAll(list2));
        // is same as
        List<Integer> collectingAge1 = persons.stream()
                                              .collect(Collectors.mapping(person -> person.getAge(), Collectors.toList()));


    Map<Dish.Type, List<Dish>> dishesByType = menu.stream()
                                            .collect(
                                                Collectors.groupingBy(   --- first level grouping
                                                    Dish::getType,
                                                    Collectors.groupingBy( --- second level grouping
                                                        dish -> {return dish.caloryLevel();}
                                                    )
                                                )
                                             );
    The result of this two-level grouping is a two-level Map like the following:
    {MEAT={DIET=[chicken], NORMAL=[beef], FAT=[pork]}, FISH={DIET=[prawns], NORMAL=[salmon]}, Core Java for the Impatient.pdfOTHER={DIET=[rice, seasonal fruit], NORMAL=[french fries, pizza]}}


    Example:

    Person person1 = new Person(Person.Sex.MALE, 21);
    Person person2 = new Person(Person.Sex.MALE, 21);
    Person person3 = new Person(Person.Sex.MALE, 22);
    Person person4 = new Person(Person.Sex.MALE, 22);
    Person person5 = new Person(Person.Sex.FEMALE, 30);
    Person person6 = new Person(Person.Sex.FEMALE, 30);
    Person person7 = new Person(Person.Sex.FEMALE, 32);

    List<Person> persons = new ArrayList<Person>() {{
        add(person1);
        add(person2);
        add(person3);
        add(person4);
        add(person5);
        add(person6);
        add(person7);
    }};
    Map<Person.Sex, List<Person>> groupByGender = persons.stream()
                                                         .collect(Collectors.groupingBy(Person::getGender)); //same as Collectors.groupingBy(Person::getGender, Collectors.toList())

    Map<Person.Sex, Long> groupByKeyGenderAndValueTotalPersons =
                    persons.stream()
                    .collect(Collectors.groupingBy(
                                           Person::getGender,
                                           Collectors.counting()
                                       )
                     );

    Map<Person.Sex, Optional<Person>> groupByKeyGenderAndValueFilterByMaxAge1 =
                    persons.stream()
                    .collect(
                            Collectors.groupingBy(p1 -> p1.getGender(),
                                                  Collectors.maxBy((p1, p2) -> p1.getAge().compareTo(p2.getAge()))
                            )
                    );

    Map<Person.Sex, Optional<Person>> groupByKeyGenderAndValueByMaxAge2 =
                    persons.stream()
                    .collect(
                            Collectors.groupingBy(p1 -> p1.getGender(),
                                                  Collectors.maxBy(Comparator.comparingInt(Person::getAge))
                            )
                    );
    Map<Person.Sex, List<Person>> groupByKeyGenderAndValueByUnmodifiableListOfPersons =
                    persons.stream()
                    .collect(
                            Collectors.groupingBy(p1 -> p1.getGender(),
                                                  Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList) // performs an additional finishing transformation.
                            )
                    );


    6.4. Partitioning

    Partitioning is a special type of groupingBy. It creates a map of key type Boolean (true/false)

    Collectors.partitioningBy(Predicate, Collector)

    persons.stream().collect(Collectors.partitioningBy(Person::isMale));  - This is same as Collectors.partitioningBy(Person::isMale, Collectors.toList())
    This will create
    Map<Boolean, List<Person>> = {true=[male person1, male person2, male person3], false=[female person4]}

    Just like groupingBy, you can pass any Collector as a second parameter (groupingBy, maxBy, collectingAndThen etc).


    6.5. The Collector interface

        It's a kind of map-reduce functionality. accumulator does mapping, combiner does reducing.

        public interface Collector<T, A, R> { --- T = Input Stream Element Type, A = Supplier's and Accumulator's Output, R = Finisher's Output
            Supplier<A> supplier();
            BiConsumer<A, T> accumulator();
            Function<A, R> finisher();
            BinaryOperator<A> combiner();
            Set<Characteristics> characteristics();
        }

        In this listing, the following definitions apply:

          T is the generic type of the items in the stream to be collected.
          A is the type of the accumulator, the object on which the partial result will be accumulated during the collection process.
          R is the type of the object (typically, but not always, the collection) resulting from the collect operation.


        Collector's implementation class is CollectorImpl that takes following parameters

            - Supplier supplier - supplier that creates an initial object/seed/identity

                Making a new result container: the supplier method:

                The supplier method has to return a Supplier of an empty result—a parameterless function that when invoked creates an instance of an empty accumulator used during the collection process.

                public Supplier<List<T>> supplier() {
                    return () -> new ArrayList<T>();  // same as 'return ArrayList::new;'
                }

            - BiConsumer accumulator - accumulates stream's element into initial object. This happens for all elements one by one.

                Adding an element to a result container: the accumulator method

                public BiConsumer<List<T>, T> accumulator() {
                    return (nilList, item) -> nilList.add(item); // same as 'return List::add;'
                }


            - BiConsumer combiner - it is mainly for parallel streams to combine the result of two parallel streams into one. combiner will reserve return values of accumulators of two parallel streams and combine them in one.
                                    See how combiner works in Parallel stream processing on pg 191 diagram of the book.

                Merging two result containers: the combiner method

                Defines how the accumulators resulting from the reduction of different subparts of the stream are combined when the subparts are processed in parallel.

                public BinaryOperator<List<T>> combiner() {
                    return (list1, list2) -> {
                                list1.addAll(list2);
                                return list1;
                           }
                }


            - Finisher finisher - finisher can transform the result giving by accumulator to some other type. If Characteristics is set to IDENTITY_FINISH then it is assumed that no transformation is required, so finisher won't be called.

                Applying the final transformation to the result container: the finisher method

                The finisher method has to return a function that’s invoked at the end of the accumulation process, after having completely traversed the stream, in order to transform the accumulator object into the final result of the whole collection operation.
                Often, as in the case of the ToListCollector, the accumulator object already coincides with the final expected result.
                As a consequence, there’s no need to perform a transformation, so the finisher method just has to return the identity function:

                public Function<List<T>, List<T>> finisher() {
                    return Function.identity();
                }

            - Characterstics -

                IDENTITY_FINISH - Finisher's responsibility is to transform the result from accumulator to some other type. This indicates that the intermediate type A is the same as returned type R. So there is no need to call finisher() method.
                             If Characteristics has IDENTITY_FINISH, then finisher is not called.

                CONCURRENT - Indicates that this collector is concurrent, meaning that the result container can support the accumulator function being called concurrently with the same result container from multiple threads.
                             It means that during parallel processing, accumulator of different threads can share the same result container (object given by Supplier) assuming that result container will not throw ConcurrentModificationException, if accessed concurrently by multiple threads.
                             It is programmer's responsibility to provide such kind of result container from Supplier e.g. ConcurrentMap.

                            Java 8 api (https://docs.oracle.com/javase/8/docs/api/java/util/stream/Collector.Characteristics.html#CONCURRENT) says that
                                If a CONCURRENT collector is not also UNORDERED, then it should only be evaluated concurrently if applied to an unordered data source.
                                It means that CONCURRENT attribute is respected,
                                    if CONCURRENT + UNORDERED + source of elements is ORDERED/UNORDERED (e.g. doesn't matter whether it is List or Set)
                                    if CONCURRENT + ORDERED, then source of elements should be UNORDERED collection (e.g. Set)
                                                             otherwise CONCURRENT is ignored


                UNORDERED - Means that the order of elements is not important. This information can be used to optimize processing. Collector can start combining results from different threads without worrying about combining the threads in sequence to preserve the order of elements in final combined result.


                See Stream's collect(Collector)->ReferencePipeline's collect(Collector) method to see how these parameters are used.

                    If stream is parallel
                        Collector's Characteristics has CONCURRENT,
                            stream is unordered and Collector's Characteristics has UNORDERED,
                                get Supplier's object
                                call accumulator
                    else
                        if(stream is parallel) ... do something ...
                        else ... do something ...



        Simple example of using supplier, accumulator and combiner works in general
            https://docs.oracle.com/javase/tutorial/collections/streams/reduction.html

                    Collection<Person> persons = new ArrayList<Person>() {{
                            add(new Person(Person.Sex.MALE, 21));
                            add(new Person(Person.Sex.MALE, 22));
                            add(new Person(Person.Sex.MALE, 23));
                            add(new Person(Person.Sex.FEMALE, 20));
                        }};

                        Averager averageCollect = persons.stream()
                                .filter(p -> p.getGender() == Person.Sex.MALE)
                                .map(Person::getAge)
                                //  supplier(initial value), accumulator (Consumer), combiner/reducer (Consumer)
                                .collect(Averager::new, Averager::accept, Averager::combine);

                        System.out.println("Average age of male members: " +
                                averageCollect.average());
                    }

                    static class Averager implements IntConsumer {
                        private int total = 0;
                        private int count = 0;

                        public double average() {
                            return count > 0 ? ((double) total) / count : 0;
                        }

                        public void accept(int i) {
                            total += i;
                            count++;
                        }

                        public void combine(Averager other) {
                            total += other.total;
                            count += other.count;
                        }
                    }

                    static class Person {
                        private Sex gender;
                        private int age;

                        public Person(Sex gender, int age) { this.gender = gender; this.age = age;}

                        public Sex getGender() {return gender;}
                        public int getAge() {return age;}

                        enum Sex {
                            MALE, FEMALE;
                        }
                    }

        Custom collector
            ToListCollector -

            For instance, you could implement a ToListCollector<T> class that gathers all the elements of a Stream<T> into a List<T> having the following signature

            public class ToListCollector<T> implements Collector<T, List<T>, List<T>>

            From pg 193 of the book
            public class ToListCollector<T> implements Collector<T, List<T>, List<T>> {

                @Override
                public Supplier<List<T>> supplier() {
                    return new ArrayList<>();
                }

                @Override
                public BiConsumer<List<T>, T> accumulator() {
                    return (list, element) -> list.add(element);
                }

                @Override
                public BinaryOperator<List<T>> combiner() {
                    return (list1, list2) -> { list1.addAll(list2); return list1;};
                }

                @Override
                public Function<List<T>, List<T>> finisher() {
                    return Function.identity();
                }

                @Override
                public Set<Characteristics> characteristics() {
                    return Collections.unmodifiableSet(EnumSet.of);
                }

            }

            List<Dish> dishes = menuStream.collect(new ToListCollector<Dish>());
            is same as
            List<Dish> dishes = menuStream.collect(toList());

            PrimeNumbersCollector
                pg 199 of the book.


Chapter 7 (Parallel data processing and performance)

    Parallel streams work under the hood by employing the fork/join framework introduced in Java 7

    A parallel stream is a stream that splits its elements into multiple chunks, processing each chunk with a different thread. Thus, you can automatically partition the workload of a given operation on all the cores of your multicore processor and keep all of them equally busy.

    public static long iterativeSum(long n) {
        long result = 0;
        for (long i = 1L; i <= n; i++) { result += i;}
        return result;
    }

    This operation seems to be a good candidate to leverage parallelization, especially for large values of n. But where do you start? Do you synchronize on the result variable? How many threads do you use? Who does the generation of numbers? Who adds them up?
    Answer is parallel streams.

    You can keep changing from parallel to sequential and vice-versa like below, but....
        stream.parallel() .filter(...).sequential().map(...).parallel().reduce();
    But the last call to parallel or sequential wins and affects the pipeline globally. In this example, the pipeline will be executed in parallel because that’s the last call in the pipeline.

    Configuring the thread pool used by parallel streams

        Looking at the stream’s parallel method, you may wonder where the threads used by the parallel stream come from, how many there are, and how you can customize the process.

        Parallel streams internally use the default ForkJoinPool, which by default has as many threads as you have processors, as returned by Runtime.getRuntime().availableProcessors().

        But you can change the size of this pool using the system property java.util.concurrent.ForkJoinPool.common.parallelism, as in the following example:

        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "12");

        This is a global setting, so it will affect all the parallel streams in your code. Conversely, it currently isn’t possible to specify this value for a single parallel stream. In general, having the size of the ForkJoinPool equal to the number of processors on your machine is a meaningful default, and we strongly suggest that you not modify it unless you have a very good reason for doing so.

    7.1.2. Measuring stream performance

        // what is the difference between
        int sumIteratively=0;
        for(int i=0; i < 10; i++) {
            sumIteratively += i;
        }
        // and
        Stream.iterate(1, i -> i+1).limit(10)
                .parallel()
                .reduce(0, (i1, i2) -> i1 + i2);
        // and
        IntStream.range(0, 10).parallel()
                .reduce(0, (i1, i2) -> i1+i2);

        - first approach is sequential. So, it would reserve longer to execute compared to parallel processing.
        - second approach has two problems and so it will reserve longer time compared to even first approach, even through it is converted to parallel stream. iterate takes parameters that has to go through boxing process(converting passed int to Integer).
          iterate is difficult to divide into independent chunks to execute in parallel.
        - third approach is perfect because it is parallel processing + both range and reduce can accept primitives (they don't have to do boxing). so it is faster than previous approaches.

    7.1.3. Using parallel streams correctly

            (IMP) When parallel processing gives worse performance compare to sequential processing?
                - It gives wrong result when object being passed to threads are mutated
                - When you need to maintain the order of the result (e.g. stream.parallel().forEachOrdered(...) / .findFirst(). To improve the performance, you can either use findAny or parallel().unordered().filter(...).findFirst().
                - When you try to parallel processing on stateful operations (sorted/distinct/skip/limit)
                - When amount of data is small
                - For better parallelization, use ArrayList instead of LinkedList. ArrayList can be splitted in between threads faster than LinkedList.


            public static long sideEffectSum(long n) {
                Accumulator accumulator = new Accumulator();
                LongStream.rangeClosed(1, n).forEach(accumulator::add); // sequential
                return accumulator.total;
            }
            public class Accumulator {
                public long total = 0;
                public void add(long value) { total += value; }
            }

            If you change above code to use parallel stream LongStream.rangeClosed(1, n).parallel().forEach(accumulator::add);,
            it will create problem. Each time to you run the code, it will give you different result.

            Result: 5959989000692
            Result: 7425264100768
            Result: 6827235020033
            Result: 7192970417739
            Result: 6714157975331
            Result: 7497810541907
            Result: 6435348440385
            Result: 6999349840672
            Result: 7435914379978
            Result: 7715125932481

            True result is 50000005000000.

            Why???
            When you use parallel stream, it spawns threads and each thread copies the external objects into its own cache (here Accumulator object). Read and write happens in cached object and after write happens from one thread, jvm will try to push modified object into main memory and all other threads memory, but it takes some time to do this process. Before this push happens, other threads might have already changed their cached objects. This will end up in wrong result.
            That's why it says that you should try to avoid mutating external objects. If you want, create a local object inside the function and return it and afterwards deleteRootAndMergeItsLeftAndRight returned object with your original object somehow.


            As we already demonstrated in this section, a parallel stream isn’t always faster than the corresponding sequential version.
            How to avoid lower performing parallellization?

            - (IMP) Watch out for boxing. Automatic boxing and unboxing operations can dramatically hurt performance. Java 8 includes primitive streams (IntStream, LongStream, and DoubleStream) to avoid such operations, so use them when possible.

            - (IMP) Some operations naturally perform worse on a parallel stream than on a sequential stream.
            In particular, operations such as sorted/distinct/skip/limit(all stateful operations) and findFirst that rely on the order of the elements are expensive in a parallel stream. For example, findAny will perform better than findFirst because it isn’t constrained to operate in the encounter order. You can always turn an ordered stream into an unordered stream by invoking the method unordered on it. So, for instance, if you need N elements of your stream and you’re not necessarily interested in the first N ones, calling limit on an unordered parallel stream may execute more efficiently than on a stream with an encounter order (for example, when the source is a List).

                e.g.
                IntStream.range(0, n).parallel().filter(...).findFirst();
                parallel() will create bunch of threads and split the elements to those threads.
                filter will run in every thread.
                At the end, each thread will submit its result to findFirst.
                If stream is ordered, findFirst's job becomes more expensive because it literally has to wait for the first thread to finish and check its result before going to next.
                So, to improve the performance, you can either use findAny or parallel().unordered().filter(...).findFirst() in which whichever thread finishes first will be observed for its result.

            - Consider the total computational cost of the pipeline of operations performed by the stream. With N being the number of elements to be processed and Q the approximate cost of processing one of these elements through the stream pipeline, the productRecursively of N*Q gives a rough qualitative estimation of this cost. A higher value for Q implies a better chance of good performance when using a parallel stream.

            - (IMP) For a small amount of data, choosing a parallel stream is almost never a winning decision. The advantages of processing in parallel only a few elements aren’t enough to compensate for the additional cost introduced by the parallelization process.

            - (IMP) Take into account how well the data structure underlying the stream decomposes. For instance, an ArrayList can be split much more efficiently than a LinkedList, because the first can be evenly divided without traversing it, as it’s necessary to do with the second. Also, the primitive streams created with the range factory method can be decomposed quickly.

                        Source              Decomposability
                        ------              ---------------
                        ArrayList           Excellent
                        LinkedList          Poor
                        IntStream.range     Excellent
                        Stream.iterate      Poor
                        HashSet             Good
                        TreeSet             Good

            Using ArrayList instead of LinkedList with Parallelization is better.

                For splitting the list(array) elements into smaller chunks is easier. You can see ArrayList's spliterator() method that returns ArrayListSpliterator.
                ArrayListSpliterator can simply chunk the elements for different Spliterators using a list/array index.

                It is not that easy to do for LinkedList as to chuck a LinkedList, its LLSpliterator need to travers the linked list nodes.

            Using IntStream.range instead of Stream.iterate is better with Parallelization.

                Stream<T> iterate(final T seed, final UnaryOperator<T> f)
                IntStream range(int startInclusive, int endExclusive)

                Using range is better than iterate because iterate works on objects, so if you use iterate over ints, there will a cost of unboxing for n+2 like operations and then converting back to Integer(boxing) for creating Stream<Integer>
                Use iterate for real objects(not wrappers of primitives).


            - (IMP) Consider whether a terminal operation has a cheap or expensive deleteRootAndMergeItsLeftAndRight step (for example, the combiner method in a Collector). If this is expensive, then the cost caused by the combination of the partial results generated by each substream can outweigh the performance benefits of a parallel stream.

            - (IMP) Parallelism should not be used for undbounded stateful operations like stream.distinct, stream.sorted

    7.2. The fork/join framework  (pg 214)

        Parallelization uses Java 7's ForkAndJoin (Divide and Concur) algorithm.

        It’s an implementation of the ExecutorService interface, which distributes those subtasks to worker threads in a thread pool, called ForkJoinPool.

        https://zeroturnaround.com/rebellabs/fixedthreadpool-cachedthreadpool-or-forkjoinpool-picking-correct-java-executors-for-background-tasks/
        As described in above link, ExecutorService had different flavors of ThreadPools.
        - FixedThreadPool
        - CachedThreadPool
        - ForkAndJoinPool

        Exectutors is a utility class to provide you an instance of ExecutorService

            public static ExecutorService newFixedThreadPool(int nThreads) {
                return new ThreadPoolExecutor(nThreads, nThreads, --- you can provide the max limit of threads. In most cases, you will be using this kind of Thread Pool.
                                              0L, TimeUnit.MILLISECONDS,
                                              new LinkedBlockingQueue<Runnable>());
            }

            public static ExecutorService newCachedThreadPool() {
                return new ThreadPoolExecutor(0, Integer.MAX_VALUE, ----- max limit of threads is very big. So, it should not be used for long running threads. It is good for short running tasks.
                                              60L, TimeUnit.SECONDS,
                                              new SynchronousQueue<Runnable>());
            }

            public static ExecutorService newWorkStealingPool() { --- It's a ForkAndJoinPool which is based on Work Stealing algorithm.
                return new ForkJoinPool
                    (Runtime.getRuntime().availableProcessors(),
                     ForkJoinPool.defaultForkJoinWorkerThreadFactory,
                     null, true);
            }

             public static ExecutorService newWorkStealingPool(int parallelism) {
                    return new ForkJoinPool
                        (parallelism,
                         ForkJoinPool.defaultForkJoinWorkerThreadFactory,
                         null, true);
             }

        ForkAndJoinPool has a method common() that returns you a common pool, which is shared by all JVM processes. So, you should not use it.


        ForkAndJoin Strategy & ForkJoinPool

            Java's Parallelism is based on ForkAndJoin strategy + ForkJoinPool(uses Work Stealing Algorithm).

            ForkAndJoin strategy - Forking subtasks from tasks and merging the results from subtasks to complete the task.
            ForkJoinPool - It's an implementation of the ExecutorService interface, which distributes subtasks of tasks to worker threads in a thread pool, called ForkAndJoinPool.
                           It is based on Work Stealing Algorithm.
                           When you submit a task using asynchronously using CompletableFuture, by default it uses ForkJoinPool's Common Pool.
                           Common Pool is nothing but the pool of threads (available processors) shared by all JVM tasks. Sometimes, it's better to use another variation of ExecutorService that provides fixedThreadPool because using Common Pool for your tasks are shared by JVM tasks.

            What is work stealing algorithm?
                Work stealing is a scheduling strategy where worker threads that have finished their own tasks can steal pending tasks from other threads. In parallel execution, tasks are divided among multiple processors/cores. When a core has no work, it should be assigned a task from another processor's overloaded queue rather than being idle.
                In Work Stealing Algorithm, each thread in the pool is assigned its doubly linked list queue. When one thread finishes its task and if its queue is empty, it steals the task from some other thread's queue.
                This work stealing is required because in ForkAndJoin strategy, parent task is not completed until all its subtasks are completed and joined.

        As described in the book,
        - Task is created and that task is subdivided into smaller sub-tasks till a single sub-task can be executed sequentially.
        - This sub-task is put in ForkAndJoinPool for the execution.
        - Then the result of these smaller sub-tasks are joined.

        if (task is small enough or no longer divisible) {

            compute task sequentially

        } else {

            split task in two subtasks
            call this method recursively possibly further splitting each subtask
            wait for the completion of all subtasks ------- major problem
            combine the results of each subtask

        }

        There is one major problem that can arise in ForkAndJoin strategy. If one sub-task takes longer to execute then other sub-task has to wait for its result to join with first sub-task. This becomes a cyclic effect because then many other sub-tasks will be waiting join also.

        Read Work-Stealing algorithm on pg 220.

        But forking a quite large number of fine-grained tasks is in general a winning choice. This is because ideally you want to partition the workload of a parallelized task in such a way that each subtask takes exactly the same amount of time, keeping all the cores of your CPU equally busy.
        Unfortunately, especially in cases closer to real-world scenarios than the straightforward example we presented here, the time taken by each subtask can dramatically vary either due to the use of an inefficient partition strategy or because of unpredictable causes like slow access to the disk or the need to coordinate the execution with external services.
        The fork/join framework works around this problem with a technique called work stealing.

        Spliterator:
            Internally, Java uses Spliterator to split the collection/map etc into chunks and based on your choice, it executes them in sequence or parallel.
            You can also create a Stream with your own Spliterator with using StreamSupport.stream(spliterator, parallel=true/false) api.
            Spliterator can directly be retrieved from a collection or from it iterator.

            See Java8StreamExample.java
            to understand Spliterator, its Characteristics and its usage for Parallelism



Chapter 8

Chapter 9 (Default Methods)
    An interface can now contain method signatures for which an implementing class doesn’t provide an implementation. So who implements them? The missing method bodies are given as part of the interface (hence default implementations) rather than in the implementing class.

    (IMP) Difference between a method in abstract class and a default method in interface.
    Interface cannot have instance variables, so default methods cannot have any logic related to instance variables.

    interface MyInterface {
        void do();
        default void method(...) {... implementations...}
    }

    default methods can have implementation in it. Note that functional interface as above can have one and only one abstract method and many default methods.
    e.g. Collection interface has a few default methods now

         default boolean removeIf(Predicate<? super E> filter)
         default Spliterator<E> spliterator()
         default Stream<E> stream()
         default Stream<E> parallelStream()

         List interface has a few default methods now

         default void replaceAll(UnaryOperator<E> operator)
         default void sort(Comparator<? super E> c)
         default Spliterator<E> spliterator()


    Abstract classes vs. interfaces in Java 8

    - They both can contain abstract methods and methods with a body.
    - First, a class can extend only from one abstract class, but a class can implement multiple interfaces.
    - Second, an abstract class can enforce a common state through instance variables (fields). An interface can’t have instance variables.

    9.3. Usage patterns for default methods
        - You can avoid boilerplate code in classes that implements interface.
        e.g.
        interface Iterator<T> {
            boolean hasNext();
            T next();

            default void remove() {
                throw new UnsupportedOperationException();
            }
        }

        Any class implementing the Iterator interface doesn’t need to declare an empty remove method anymore to ignore it, because it now has a default implementation.
        You can avoid boilerplate code.

        - Multiple Inheritance

        Multiple inheritance from more than one classes is still not available in Java, but it has been made a bit easier using default methods in interface.

        But now abstract class' importance is a bit reduced (not totally gone yet).
        Abstract class is mainly used for two purposes.
         - to share the instance variables
         - to share some common behaviour
         between multiple child classes.

        Abstract class is still useful for the first usage, but for the second type of usage, you can use interface with default methods.
        So, if you don't need first usage, then you can simply avoid creating abstract class and child classes can inherit from multiple interfaces.

        9.4.2. Most specific default-providing interface wins

        // Rule 1:
        // Classes always win. A method declaration in the class or a superclass takes priority over any default method declaration.
        G g = new G();
        g.hello(); // hello from class D

        // Rule 2:
        // Otherwise, sub-interfaces win: the method with the same signature in the most specific default-providing interface is selected.
        // Here C is more specific than A
        F f = new F();
        f.hello(); // hello from interface C

        // Rule 3:
        // Finally, if the choice is still ambiguous, the class inheriting from multiple interfaces has to explicitly select which default method implementation to use by overriding it and calling the desired method explicitly.
        E e = new E();
        e.hello(); // hello from interface A

        // Rule 4: Diamond problem
        // J implements H and I. H and I extends A. So basically both H and I's default overridden hello() from A.
        // In this case, J's default overridden method is also A's hello()
        J j = new J();
        j.hello(); // hello from interface A

        // Rule 5:
        // Little twist in Rule 3
        // L implements H and K. Both H and K extends A. K has its own hello() and H has default overridden hello() from A.
        // So, in this case closest related hello() will be called, which is H's hello()
        L l = new L();
        l.hello(); //hello from H

       interface A {
            default void hello() {
                System.out.println("hello from interface A");
            }
        }

        interface B {
            default void hello() {
                System.out.println("hello from interface B");
            }
        }

        interface C extends A {
            default void hello() {
                System.out.println("hello from interface c");
            }
        }

        static class D {
            public void hello() {
                System.out.println("hello from class D");
            }
        }

        // Rule 1
        static class G extends D implements A, C {
            public G() { }
        }

        // Rule 2
        static class F implements A, C {
            public F() { }
        }

        // Rule 3
        static class E implements A, B {
            public E() { }

            // if both implemented interfaces have same method hello(), then java doesn't know which one to use as a default overridden method
            // so it will force you to override child class' own hello() and do as below to call particular interface specific default method hello()
            @Override
            public void hello() {
                A.super.hello();
            }
        }

        // Rule 4
        interface H extends A { }

        interface I extends A { }

        static class J implements I, H { }


        interface K extends A {
            default void hello() {
                System.out.println("hello from H");
            }
        }

        // Rule 5
        static class L implements H, K { }

        - Abstract classes versus interfaces in Java 8
            https://dzone.com/articles/interface-default-methods-java

            Abstract class can define constructor. They are more structured and can have a state associated with them.

            Java 8's interface can have default methods.
            Default method can be implemented only in the terms of invoking other interface methods, with no reference to a particular implementation's state.

        - Difference Between Default Method and Regular Method
            https://dzone.com/articles/interface-default-methods-java

            Default Method is different from the regular method in the sense that default method comes with default modifier.
            Additionally, methods in classes can use and modify method arguments as well as the fields of their class but default method on the other hand, can only access its arguments as interfaces do not have any state.

            In summary, Default methods enable to add new functionality to existing interfaces without breaking older implementation of these interfaces.

            When we extend an interface that contains a default method, we can perform following,

            - Not override the default method and will inherit the default method.
            - Override the default method similar to other methods we override in subclass..
            - Redeclare default method as abstract, which force subclass to override it.

Chapter 10 (Using Optional as better alternative)

    10.1. How do you model the absence of a value?

    public class Person {
        private Car car;

        public Car getCar() {
            return car;
        }
    }

    public class Car {
        private Insurance insurance;

        public Insurance getInsurance() {
            return insurance;
        }
    }

    public class Insurance {
        private String name;

        public String getName() {
            return name;
        }
    }

    public String getCarInsuranceName(Person person) {

        if(person != null) {
            Car car = person.getCar();
            if(car != null) {
                Insurance insurance = car.getInsurance();
                if(insurance != null) {
                    return insurance.getName();  // Assuming that name can't be null, so no null check added
                }
            }
        }
        return "Unknown";
    }

    Now, adding null checks makes to code very root_to_leaf_problems_hard to read.

    Groovy gives following option to check for nulls.

    def carInsuranceName = person?.car?.insurance?.name

    But Java doesn't have this facility, but Java 8 provides Optional.

    You can rewrite above code as below

    public class Person {
        private Optional<Car> car; --- anti-pattern

        public Optional<Car> getCar() {
            return car;
        }
    }
    // or alternatively
    public class Person {
        private Car car;

        public Car getCar() {
            return car;
        }

        public Optional<Car> getCarAsOptional() {   ------- this is how you can add a new method in legacy code and start using Optional as shown below
            return Optional.ofNullable(car);
        }
   }


    public class Car {
        private Optional<Insurance> insurance;

        public Optional<Insurance> getInsurance() {
            return insurance;
        }
    }

    public class Insurance {
        private String name;

        public String getName() {
            return name;
        }
    }

    public String getCarInsuranceName(Person person) {
        String name =
                Optional.ofNullable(person)
                .flatMap(p -> p.getCar())  // why flatMap and not map? see section 10.3.3 of this chapter.
                .flatMap(c -> c.getInsurance())
                .map(i -> i.getName())
                .orElse("Unknown")
        return name;
    }

    10.3.1. Creating Optional objects

        - Empty optional

        As mentioned earlier, you can get hold of an empty optional object using the static factory method Optional.empty:

        Optional<Car> optCar = Optional.empty();

        - Optional from a non-null value

        You can also create an optional from a non-null value with the static factory method Optional.of:

        Optional<Car> optCar = Optional.of(car);

        If car were null, a NullPointerException would be immediately thrown (rather than getting a latent error once you try to access properties of the car).

        - Optional from null

        Finally, by using the static factory method Optional.ofNullable, you can create an Optional object that may hold a null value:

        Optional<Car> optCar = Optional.ofNullable(car);

        If car were null, the resulting Optional object would be empty.

    10.3.3. Chaining Optional objects with flatMap

        Optional.ofNullable(person)
                .map(p -> p.getCar())  --- p.getCar() returns Optional<Car>. So map will return Optional<Optional<Car>>. You cannot call getInsurance on Optional<Optional<Car>>.
                .map(c -> c.getInsurance())
                .map(i -> i.getName())
                .orElse("Unknown")

        Optional<Optional<Car>> =
                Optional.ofNullable(person)
                .map(p -> p.getCar())

        It results in two level Optional just like two level stream problem (Stream<Stream<String>>). Look at Chapter 5's section 5.2.
        To resolve, two level Stream problem, we used flatMap. flatMap has the effect of replacing each generated stream by the contents of that stream
        Just like that to resolve two level Optional problem, you can use flatMap.

        String name =
                Optional.ofNullable(person)
                .flatMap(p -> p.getCar())
                .flatMap(c -> c.getInsurance())
                .map(i -> i.getName())
                .orElse("Unknown")

    Table 10.1. The methods of the Optional class

        From Java 8 in Action book and other sources

            One thing which may go wrong the the matching and finding elements is that there might be a case when no element is returned by them.
            In such a case these methods simply return Null. This may be error-prone to the client codes and the client program needs to put a Null check.
            Java 8 comes up with a special class which helps solving this problem.
            The Optional class represents whether an object is assigned or unassigned (Null).

            - The methods like findAny, findFirst, and reduce without identity returns value wrapped inside Optional.
            - methods of numeric streams (IntStream, LongStream, DoubleStream etc) have sumIteratively(), min(), max() etc methods that can return primitive variant of Optional like OptionalInt, OptionalLong, OptionalDouble etc.
            - We can call all of the stream like operations on Optional as well, e.g. optionalObj.filter(...), optionalObj.map(...) etc.


            http://www.oracle.com/technetwork/articles/java/java8-optional-2175753.html

               - java.util.Optional<T> that is inspired from the ideas of Haskell and Scala. It is a class that encapsulates an optional value.

               - The purpose of Optional is NOT to replace every single null reference in your codebase but rather to help design better APIs in which—just by reading the signature of a method—users can tell whether to expect an optional value.
               In addition, Optional forces you to actively unwrap an Optional to deal with the absence of a value; as a result, you protect your code against unintended null pointer exceptions.

               - Optional's methods:

                   1) ofNullable(obj)

                   Optional objOptional = Optional.ofNullable(object)

                   Optional.ofNullable(obj) returns Optional that holds a value of an object (it doesn't throw NullPointerException if object is null)

                   objOptional.get() // returns Optional.EMPTY or object.

                   2) of(obj)

                   Unlike to ofNullable, this method throw NullPointerException if obj is null.


                   3) Aggregation operations: filter, map, flatMap etc

                   Stream has these methods as well as Optional has these methods.
                   Stream's methods can be applied to array/nilList, but Optional's these methods can be applied to a single object also.

                   e.g.
                    Optional<USB> maybeUSB = ...;
                    maybeUSB.filter(usb -> "3.0".equals(usb.getVersion())
                            .ifPresent(() -> System.out.println("ok"));

                   4) Terminal operations : get(), isPresent(), orElse(supplier returning default value), orElseGet(supplier returning default value), orElseThrow(supplier throwing an exception)

                   - get()
                        returns the value passed to Optional.

                   - isPresent()

                        You can replace null check of an object
                        if(object != null)
                        by
                        if(Optional.ofNullable(object).isPresent())

                        However, this is not the recommended use of Optional (it's not much of an improvement over nested null checks), and there are more idiomatic alternatives, which we explore below.

                   - orElse(default value),  orElseGet(supplier returning default value),  orElseThrow(supplier throwing an exception)

                       orElse return the value if present, otherwise returns a default value.
                       if Optional.ofNullable(object) returns Optional.EMPTY then you can provide a default value using orElse method
                       or
                       you can raise an exception using orElseThrow.

Chapter 11

Chapter 12

Chapter 13

        - What is Pure or Side-Effect Free method?

        A method, which modifies neither the state of its enclosing class nor the state of any other objects and returns its entire results using return, is called pure or side-effect free.

        In a nutshell, a side effect is an action that’s not totally enclosed within the function itself. Here are some examples:

            - Modifying a data structure in place, including assigning to any field, apart from initialization inside a constructor (for example, setter methods)
            - Throwing an exception
            - Doing I/O operations such as writing to a file

        Another way to look at this idea of no side effects is to consider immutable objects.
        An immutable object is an object that can’t change its state after it’s instantiated so it can’t be affected by the actions of a function.
        This means that once immutable objects are instantiated, they can never go into an unexpected state.
        You can share them without having to copy them, and they’re thread-safe because they can’t be modified.

        Advantage of Side-Effect Free method:
            The idea of no side effects might appear as a pretty severe restriction, and you may doubt whether real systems can be built in this way.
            We hope to convince you of this by the end of the chapter.
            (IMP) The good news is that components of systems that embrace this idea can use multicore parallelism without using locking, because the methods can no longer interfere with each other.



        - To be regarded as functional, your function or method should call only those side-effecting library functions for which you can hide their nonfunctional behavior (that is, ensuring that any mutation they make on data structures is hidden from your caller, perhaps by copying first and by catching any exceptions they might raise).

        - No mutating structure visible to callers, no I/O, no exceptions

        - What is Referential Transparency?

        A function consistently produces the same result given the same input, no matter where and when it’s invoked.

        The restrictions on “no visible side-effects” (no mutating structure visible to callers, no I/O, no exceptions) encode the concept of referential transparency.
        A function is referentially transparent if it always returns the same result value when called with the same argument value.
        The method String.replace is referentially transparent because "raoul".replace('r', 'R') will always produce the same result (the method replace returns a new String with all lowercase 'r' replaced with uppercase 'R') rather than updating its this object so it can be considered a function.

        It also explains why we don’t regard Random.nextInt as functional.

        In Java using a Scanner object to get the input from a user’s keyboard violates referential transparency because calling the method nextLine may produce a different result at each call.

        (IMP)
        Suppose a function or method has no side effects, except for it incrementing a field just after entry and decrementing it just before exit.
        From the point of view of a program consisting of a single thread, this method has no visible side effects and can be regarded as functional style.
        On the other hand, if another thread could inspect the field—or worse could call the method concurrently—it wouldn’t be functional. You could hide this issue by wrapping the body of this method with a lock, and this would again enable you to argue that the method is functional. But in doing so you would have lost the ability to execute two calls to the method in parallel using two cores on your multicore processor.
        Your side effect may not be visible to a program, but it’s visible to the programmer in terms of slower execution!

        - object-oriented view: everything is an object and programs operate by updating fields and calling methods that update their associated object.
        At the other end of the spectrum lies the referentially transparent functional-programming style of no (visible) mutation.

        (VERY IMP - pg 375) Pure functional programming languages typically don’t include iterative constructs like while and for loops. Why?
        Because such constructs are often a hidden invitation to use mutation. For example, the condition in a while loop needs to be updated; otherwise the loop would execute zero or an infinite number of times.

        Iterator<Apple> it = apples.iterator();
        while (it.hasNext()) {
            Apple apple = it.next(); // 'it' is outside the loop, but loop has to update it to make the condition work properly. This is against functional-style programming.
            // ...
        }

        The guidance when writing Java 8 is that you can often replace iteration with streams to avoid mutation.

        Recursive way is also bad because it uses lots of stack frames and eventually turns into StackOverflow.
        But, Tail-Recursion with CoRecursion is a lot better because it uses only 1 stack frame. Read the book (pg 377) for better understanding.
        But Java's compiler doesn't do this kind of optimizations when you use Tail-Recursion, Scala and Groovy compilers do.
        So, for Java it doesn't make sense to use any kind of Recursions.



Chapter 14

    Qualities of Functional-Style Programming:

    - first-class functions

    functional-style programming to mean that the behavior of functions and methods should be like that of mathematical-style functions—no side effects.
    Functional-language programmers often use the phrase with more generality to mean that functions may be used like other values: passed as arguments, returned as results, and stored in data structures.
    Such functions that may be used like other values are referred to as first-class functions.
    This is exactly what Java 8 adds over previous versions of Java: you may use any method as a function value, using the :: operator to create a method reference, and lambda expressions (for example, (int x) -> x + 1) to directly express function values.

    In Java 8 it’s perfectly valid to store the method Integer.parseInt in a variable by using a method reference as follows:

    Function<String, Integer> strToInt = Integer::parseInt;

    - Higher-Order functions

    higher-order functions do one of the following
        - Take one or more functions as parameter
        - Return a function as result

    - Currying (IMP)

            When should Currying be used?

            static double converter(double x, double f, double b) {
                return x * f + b;
            }

            e.g. in this method, f and b remains same and based on changing x, you want to calculate the converted value.
            It becomes tedious to pass f and b (non changing values) again and again to converter method and sometimes you can do mistake.

            converter(10, 5, 6);
            converter(20, 5, 6);
            converter(30, 5, 6);

            Here you should use currying.

            static Function<Double> converter(double f, double b) {
                return (x) -> x * f + b;
            }

            Function<Double> reusable = converter(5, 6);
            reusable.apply(10);
            reusable.apply(20);
            reusable.apply(30);

            Function<Double> anotherReusable = converter(6, 7);
            resuable.apply(10);
            reusable.apply(20);
            reusable.apply(30);


            Converting a method to curried method

                private static int method(Integer i) {
                    return i*3;
                }

                just take input parameter out and make it like below

                private static Function<Integer, Integer> method() {
                    return (i) -> i*3;
                }


            (IMP) As curried method returns function, it helps to do chaining. You can chain another Function to returned Function.
            (IMP) Read COMPREHENSION pattern also. It will help you to learn how to take input parameter out and make a method curried method.

    - Functional Data Structure/Immutable Data Structure

      e.g. when you add a new node to a linked list, you don't add (mutate) to an existing linked list. You create a new one with head as a new node and current linked list a tail node.
      This brings the advantage of immutability that is required for no-side effect

      The functional-style approach to this problem is to ban such side-effecting methods. If you need a data structure to represent the result of a computation, you should make a new one and not mutate an existing data structure as done previously.
      This is often best practice in standard object-oriented programming too.

    - Lazy Evaluation

      Java 8 streams are often described as lazy. They’re lazy in one particular aspect: a stream behaves like a black box that can generate values on request. When you apply a sequence of operations to a stream, these are merely saved up. Only when you apply a terminal operation to

    - Chaining

        Function has compose, andThen etc. You can chain one function with another

    - Composition

Chapter 15




My Important Observations From Functional Programming In Java Book
------------------------------------------------------------------


    Static methods with body in interface
    -------------------------------------
    Just like default methods, you can have static methods also interface that includes body.
    e.g. BinaryOperator interface has a few static methods with body in it.


    Thinking functionally:  (VERY IMP)
    ----------------------

        1) Thought process:
        There is some value on which you want to apply some EFFECT.
        Method that you can create is:


        interface IO<V> {

            V run();

            // If it is a non-static method and if it is possible to retrieve V from enclosing context, then you don't need to supply V to the method.
            default void accept(Consumer<V> consumer) {
                consumer.apply(this.run());
            }

            // If it is a static method, for transforming V to U, you need to supply V somehow to the method.
            static <V> void acceptStatic(V v, Consumer<V> consumer) {
                consumer.apply(v);
            }
            or

            static <V> Consumer<V> acceptStatic() { // very useful method. It takes a bare value and returns it in the IO context. It is like getInstance() method.
                return (v) -> System.out.println(a);

            }

        }

        IO.acceptStatic("Some Value", v -> System.out.println(v))
        io.accept(v -> System.out.println(v));

        2) Thought process:
        There is some input value and you want to TRANSFORM input value to something.

        Two ways to write methods that takes Function as parameter.
         1. With a Function, you also pass input parameter (and identity if required) and evaluate the function with that input inside a method.
         2. Just pass a Function to a method and return another Function from a method and evaluate returned Function from outside of method by passing input to it.
        2nd approach helps to create a chain and helps to call defer to call actual function later when you have input value available.

        Method that you can create is:

            Approach 1:
                interface IO<V> {
                    V run();

                    // If it is a non-static method and if it is possible to retrieve V from enclosing context, then you don't need to supply V to the method.
                    default <U> U map(Function<V, U> functionConvertingVToU) {
                        return functionConvertingVToU.apply(this.run());
                    }

                    // If it is a static method, for transforming V to U, you need to supply V somehow to the method.
                    static <V, U> U mapStatic1(V v, Function<V, U> functionConvertingVToU) {// For transforming V to U, you need to input V or some object from which you can retrieve V.
                        return functionConvertingVToU.apply(v);
                    }
                    // If it is a static method, for transforming V to U, you need to supply V somehow to the method.
                    static <V, U> U mapStatic2(SomeClassProvidingV someClassObj, Function<V, U> functionConvertingVToU) {
                        return functionConvertingVToU.apply(someClassObj.getV());
                    }

                }

            Approach 2 (Currying):
                f(3, 4, 5) = 3 * 4 + 5 = 17
                we may apply the arguments 3, 4, 5 at the same time and get:

                But we may also apply only 3 and get:
                f(3, y, z) = g(y, z) = 3 * y + z

                We have now a new function g, taking only two arguments. We can curry again this function, applying 4 to y:
                g(4, z) = h(z) = 3 * 4 + z

                This is Currying.

                From Chapter 14:

                            When should Currying be used?

                            static double converter(double x, double f, double b) {
                                return x * f + b;
                            }

                            e.g. in this method, f and b remains same and based on changing x, you want to calculate the converted value.
                            It becomes tedious to pass f and b (non changing values) again and again to converter method and sometimes you can do mistake.

                            converter(10, 5, 6);
                            converter(20, 5, 6);
                            converter(30, 5, 6);

                            Here you should use currying.

                            static Function<Double> converter(double f, double b) {
                                return (x) -> x * f + b;
                            }

                            Function<Double> reusable = converter(5, 6);
                            resuable.apply(10);
                            reusable.apply(20);
                            reusable.apply(30);

                            Function<Double> anotherReusable = converter(6, 7);
                            resuable.apply(10);
                            reusable.apply(20);
                            reusable.apply(30);

                Converting approach 1 to better approach (approach 2).
                This approach is also called "CURRYING". Currying forces you to evaluate the function Partially.

                Function<V, U> map(Function<V, U> functionConvertingVToU) {
                    return (v) -> functionConvertingVToU.apply(v);
                }
                map is a function that returns a Function. so, map is called a curried function.

                Here, map is a "PARTIALLY APPLIED FUNCTION" because functionConvertingVToU needs V as an input, but it is not available it because we are not passing it to map.
                In this situation, functionConvertingVToU cannot be evaluated fully. So, map becomes Partially Applied Function.
                This forces us to return a Function from map method that takes required input parameters to evaluate functionConvertingVToU in future.

                Function<String, Integer> functionConvertingVToU = (s) -> Integer.valueOf(s);
                Function<String, Integer> partialResult = map(functionConvertingVToU);

                String v = "1"
                partialResult.apply(v);
                v = "2"
                partialResult.apply(v);
                v = "3"
                partialResult.apply(v);

                (IMP) Why Currying is Important?
                Answer :
                - Code Reuse
                - You can use partialResult for different v values.
                - You can defer the execution of actual function at later point in time when you have an input value available.


            Example 1
            ---------
            // approach 1
            public static <T, U> U foldLeft(List<T> ts,
                                            U identity,
                                            BiFunction<U, T, U> f) { // approach 1
                U result = identity;
                for (T t : ts) {
                    result = f.apply(result, t);
                }
                return result;
            }

            // approach 2
            public static <T, U> BiFunction<U, T, U> foldLeft(BiFunction<U, T, U> f) {
                return (u, t) -> f.apply(u, t);
            }


            // approach 1
            List<Integer> items = Lists.newArrayList(1, 2, 3);
            {
                foldLeft(items, 0, (a, b) -> a + b);
            }

            // approach 2
            {
                Integer result = items.get(0);
                for (Integer item : items) {
                    foldLeft((Integer a, Integer b) -> a + b).apply(result, item);
                }
            }




            Example 2
            ---------

            List<Integer> items = Lists.newArrayList(1, 2, 3);
            // approach 1
            {
                Integer result = items.get(0);
                for (Integer item : items) {
                    result = minBy(Comparator.<Integer>naturalOrder()).apply(result, item);
                }
                System.out.println(result);
            }
            // approach 2
            {
                System.out.println(minBy(items, Comparator.<Integer>naturalOrder()));
            }


            // approach 1
            private static <T> T minBy(List<T> items, Comparator<? super T> comparator) {
                T result = null;
                if(items.size() > 0) {
                    result = items.get(0);
                }

                for (T item : items) {
                    result = comparator.compare(result, item) > 0 ? item : result;

                }
                return result;
            }
            // approach 2
            private static <T> BinaryOperator<T> minBy(Comparator<? super T> comparator) {
                return (a, b) -> comparator.compare(a, b) > 0 ? a : b;
            }
            // same as
            private static <T> Function<T, Function<T,T>> minBy(Comparator<? super T> comparator) {
                return a -> b -> comparator.compare(a, b) > 0 ? a : b;
            }

    Chaining(Composing), Currying
    ------------------------------

        Chaining:

            In my opinion, chaining is something like below

            class Stream {
                Stream map(...);  // method returning same or another instance of the its containing class, so that another method can be called on the same instance
                Stream filter(...);
                ...
            }
            Stream stream = list.stream().flatMap(...).filter(...)...


        Currying:

            A method/function returning a Function is called curried method/function

            e.g.
            Function<I, Function<I,O>> --- function returning a function

            Function<T, U> memoize(Function<T, U> function); --- functional method returning a function

            From Chapter 14
            When should Currying be used?

                static double converter(double x, double f, double b) {
                    return x * f + b;
                }

                e.g. in this method, f and b remains same and based on changing x, you want to calculate the converted value.
                It becomes tedious to pass f and b (non changing values) again and again to converter method and sometimes you can do mistake.

                converter(10, 5, 6);
                converter(20, 5, 6);
                converter(30, 5, 6);

                Here you should use currying.

                static Function<Double> converter(double f, double b) {
                    return (x) -> x * f + b;
                }

                Function<Double> reusable = converter(5, 6);
                resuable.apply(10);
                reusable.apply(20);
                reusable.apply(30);

                Function<Double> anotherReusable = converter(6, 7);
                resuable.apply(10);
                reusable.apply(20);
                reusable.apply(30);


        Combination of both Chaining + Currying:

            interface Function<T, R> {

                default <V> Function<V, R> compose(Function<? super V, ? extends T> before) { // this method is returning a Function, so it is curried. But it is returning an instance of its containing class, so it is chaining also.
                    Objects.requireNonNull(before);
                    return (V v) -> apply(before.apply(v));
                }

            }

    Trick to use multiple parameters to find the result even though method is accepting a function that takes only one parameter
    ----------------------------------------------------------------------------------------------------------------------------
    e.g. below method takes a Function that takes only one input parameter
    Function<T, U> method(Function<T, U> function) {
        return (t) -> function.apply(t);
    }

    Function<Integer, Integer> function = method((t) -> t*10);

    What if you want to pass 10 also as input to this method?

    you have three choices:

    Approach 1: Modify method code --- Not so good idea !!!

        Function<T, Function<M, U>> method(Function<T, Function<M, U>> function) {
            return (t, m) -> function.apply(t).apply(m);
        }


    Approach 2: Use Tuple as input that contains all the parameters that you need. --- Better idea

        Function<Tuple, Integer> function = method((tuple) -> tuple.t * tuple.multiplicationFactor);

        Tuple tuple = new Tuple(1, 10);
        Integer result = function.apply(tuple);

    Approach 3: No need to create Tuple. Use below approach. It is a bit complicated way, so try to avoid using it, but still understand it.

        Function<Integer, Function<Integer, Integer>> function = method(t -> method(multiplicationFactor -> t * multiplicationFactor))
        Integer result = function.apply(1).apply(10);


    Closure
    -------
    See Chapter 2 of FunctionalProgrammingInJavaBook.java

    Converting Imperative code to Recursive code
    ---------------------------------------------
    See
    - FactorialExample.java's factorialImperative1 and factorialTailRecursive1 methods   ---- this is simple example having only one loop inside imperative code
    - List.java's splitListAtIteratively and splitListAtRecursiveTailRecursive methods   ---- this is complex example of having multiple loops inside imperative code

    Converting Imperative code to Functional Code
    ---------------------------------------------
        You can easily convert imperative (non-functional method with while/for loop) to a generic method very easily by using Function for condition and body of the loop.
        Abstract out the condition and the code in the loop as much as you can and pass those abstracted functions to the method.

        - Imperative approach

            List<Integer> intRangeWithWhileLoop(int start, int end) {
                List<Integer> result = new ArrayList<>();  // can be passed as Supplier
                int temp = start;                          // can be passed as Identity (Seed)
                while (temp < end) {                       // can be converted into Predicate
                    result.add(temp);
                    temp = temp + 1;                       // can be converted to Function that transforms temp to temp+1
                }
                return result;
            }

        - Functional approach  (Converting above imperative method to functional method)

            int start = 0;
            int end = 10
            Supplier<List<Integer>> supplier = () -> ArrayList::new;
            Predicate<Integer> predicate = (t) -> t < end;
            Function<Integer, Integer> function = (t) -> t+1;

            // approach 1
            List<Integer> result = functionFormOfIntRangeWithWhileLoop(start, supplier, predicate, function);

            List<T> functionFormOfIntRangeWithWhileLoop(T identity,
                                                        Supplier<List<T>> supplier,
                                                        Predicate<T> predicate,
                                                        Function<T, T> function) {
                List<T> result = supplier.get();
                T temp = identity;
                while(predicate.test(temp)) {
                    result.add(temp);
                    temp = function.apply(temp);
                }
                return result;
            }

            // Above approach is the most simplest functional approach
            // Now slowly make it more complex by not passing input parameters directly to method, passing them from caller of the method using Returned Function.

            // approach 2.1
            List<Integer> result = functionFormOfIntRangeWithWhileLoop(supplier, predicate, function).apply(start);

            Function<T, List<T>> functionFormOfIntRangeWithWhileLoop(
                                                        Supplier<List<T>> supplier,
                                                        Predicate<T> predicate,
                                                        Function<T, T> function) {
                return (identity) -> {
                    List<T> result = supplier.get();
                    T temp = identity;
                    while(predicate.test(temp)) {
                        result.add(temp);
                        temp = function.apply(temp);
                    }
                }
            }

            // approach 2.2
            List<Integer> result = functionFormOfIntRangeWithWhileLoop(predicate, function).apply(start).apply(supplier);

            Function<T,
                    Function<Supplier<List<T>>,
                             List<T>>> functionFormOfIntRangeWithWhileLoop(
                                                        Predicate<T> predicate,
                                                        Function<T, T> function) {
                return (identity) -> (supplier) -> {
                    List<T> result = supplier.get();
                    T temp = identity;
                    while(predicate.test(temp)) {
                        result.add(temp);
                        temp = function.apply(temp);
                    }
                }
            }

            // approach 2.3
            List<Integer> result = functionFormOfIntRangeWithWhileLoop(supplier, predicate, function).apply(start).apply(supplier).apply(predicate);

            Function<T,
                    Function<Supplier<List<T>>,
                            Function<Predicate<T>,
                                    List<T>>>> functionFormOfIntRangeWithWhileLoop(
                                                        Function<T, T> function) {
                return (identity) -> (supplier) -> (predicate) -> {
                            List<T> result = supplier.get();
                            T temp = identity;
                            while(predicate.test(temp)) {
                                result.add(temp);
                                temp = function.apply(temp);
                            }
                        }
            }


    Use of Functions.identity()
    ---------------------------
    http://www.java2s.com/Tutorials/Java/java.util.function/Function/1080__Function.identity.htm

    The following code shows how to use Function.identity() to not modify values.

    public class Main {
       public static void main(String[] args) {
          Function<Double, Double> square = number -> number * number;
          Function<Double, Double> half = number -> number * 2;

          List<Double> numbers = Arrays.asList(10D, 4D, 12D);
          // you can use identity to not modify them
          System.out.println(mapIt(numbers, Function.<Double>identity())); // ------ Using Function.identity()
       }

       private static List<Double> mapIt(List<Double> numbers, Function<Double, Double> augmenter) {
          List<Double> result = new ArrayList<>();

          for (Double number : numbers) {
             result.add(augmenter.apply(number));
          }

          return result;
       }
    }

    Making methods functional
    -------------------------
    Read "Abstract out control structures conditions" from Chapter 3 (pg 67) from FunctionalProgrammingInJavaBook.java

        There are two approaches to replace loggers or any other side-effects in a Function.
            1. you can return a proper object like Result to client and let client log from that object, if it wants. Result interface and its child classes Success and Failure are explained in Chapter 7
            2. you can return an Executable (Java 8 doesn't have it) that takes care of side-effect. It is explained more in detail in Chapter 13.
            3. you can return a Consumer (Java 8 has it) instead of Executable

    Read Chapter 15 in FunctionalProgrammingInJavaBook.java also step-by-step approach of converting imperative method to a functional method.

    Read Chapter 12 from FunctionalProgrammingInJavaBook.java

        Considering that it is very root_to_leaf_problems_hard for a method not modify input parameter (it is impossible to avoid it because it modifying it indirectly) as shown in Chapter 12's Generator and Random class example, then
        return input parameter also with the output from that method to make that method functional.

        Every time you run Generator.integer(rng) method, it will change the state of rng, so it is not a functional method.
        How can you make it functional?
        By including input parameter rng (whose state is changing) also along with actual output as a returned value. - Tuple<Integer, RNG>

    Read Chapter 13 (13.3) from FunctionalProgrammingInJavaBook.java

        It shows different approaches to make a method functional.


    Replacing if...else, switch...case
    ----------------------------------

    You can create Matchers taking Predicate and Supplier/Consumer as arguments and evaluate matchers using for loop.

    Read "Abstract out control structures conditions" from Chapter 3 from FunctionalProgrammingInJavaBook.java

    How to avoid side-effects from functional method/class?
    -------------------------------------------------------

    Any class that follow below principles of not creating side-effects from its methods can be considered as functional class.

    1. Use of Optional

        public void method(...) {
            throw new RuntimeException(...);
        }

        To make above method functional,

        public Optional<RuntimeException> method(...) {
            return Optional.of(exception);
        }

        public Optional<String> method(...) {
            return Optional.of(exception.getMessage());
        }

        Or you can return a Consumer/Supplier that throws/supplies an exception.

        public Consumer<RuntimeException> method(...) { ----- IMP
            return (something) -> throw new RuntimeException(something);
        }

        public Supplier<RuntimeException> method(...) { ----- IMP
            return () -> new RuntimeException(...);
        }

        - Returning null from a method is also harmful because you are forcing client to do a null check, which is bad.

        public Person method(String name) {
          if(name.equals(...)) return new Person(name);
          return null;
        }

        This can improved as

        public Optional<Person> method(String name) {
          if(name.equals(...)) return Optional.of(new Person(name));
          return Optional.empty();
        }

        See PropertyReader.java


    2. Use of Supplier (IMP)

        To make your method functional, your method should not create a side-effect. Side effect should be wrapped by a Supplier and method should return a Supplier and let caller create a side effect.

        // This method has a side-effect of sending email and logging error. How to make it functional?
        static void validate(String email) {
            if (...some condition...) {
              sendVerificationMail(email); // side effect
            } else {
              logError("email could not be sent to "+email); // side effect
            }
        }

        static Supplier<String> validate(String email) {
            return (...some condition...)
                ? () -> sendVerificationMail(email) // overcoming side-effect from this method by wrapping a side-effect with a Consumer
                : () -> logError("email could not be sent to "+email); // overcoming side-effect from this method by wrapping a side-effect with a Consumer

        }


        static void sayHello(String name) {
           System.out.println("Hello, " + name + "!");
        }

        This method can be made functional as below

        static Supplier<String> sayHello(String name) {
            return () -> System.out.println("Hello, " + name + "!");
        }

    3. Use of Consumer

        static void sayHello(String name) {
           System.out.println("Hello, " + name + "!");
        }

        static Consumer<String> sayHello() {
           return (name) -> System.out.println("Hello, " + name + "!");
        }


        Another Example:
            EmailValidation.java - See how Matcher's result wrapped by a Consumer.

    3. Use of Function

        static void sayHello(String name) {
           System.out.println("Hello, " + name + "!");
        }

        This method can be made functional as below

        static Function<String, String>  sayHello() {
            return (name) -> "Hello, " + name + "!";
        }

    Why logging inside a Function is non-functional?
        Why logging is dangerous?
        In functional programming, you will not see much logging. This is because functional programming makes logging mostly useless.
        Functional programs are built by composing pure functions, meaning functions that always return the same value given the same argument. So there can’t be any surprises.
        On the other hand, logging is ubiquitous in imperative programming because imperative programs are programs for which you can’t predict the output for a given input. Logging is like saying “I don’t know what the program might produce at this point, so I write it in a log file. If everything goes right, I will not need this log file. But if something goes wrong, I will be able to look at the logs to see what the program state was at this point.” This is nonsense.
        In functional programing, there is no need for such logs. If all functions at correct, which can generally be proved, we don’t need to know the intermediate states. And furthermore, logging is often made conditional, which means that some logging code will only be executed in very rare and unknown states. This code is often untested. If you have ever seen a Java imperative program that worked fine in INFO mode suddenly break when run in TRACE mode, you know what I mean.

        Other qualities of Functional Library(Class):  (See IO.java)

        - Parameterized
        - Should be able to have EMPTY instance
        - Combinable - should have an add method (same as Java 8 Function's andThen method)
        - should have map/flatMap methods
        - Good to have
            - unit method
            - repeat method
            - forever method  ---- Very interesting method. It shows how to tackle infinite recursive method calls.


    Read Chapter 13 from FunctionalProgrammingInJavaBook.java



    FoldLeft and FoldRight method of a list and important Tail-Recursion concept
    ----------------------------------------------------------------------------

    What is the difference between foldLeft and foldRight?

    In foldLeft, you start from the left(start) of the inputList. So you apply an operation first on identity and first element of the list and then moving further in the list.
    In foldRight, you need to start from right(end) of the inputList. So you need to go to the end of the inputList using recursion and then then apply an operation on identity and last element and then second last element and so on.

    // Using the concept of Reducing a problem by one
    // This is Tail-Recursive because by the time you put last method call in a stack, you already have a final output. Ideally, you don't need to pop entire stack to get final output. Scala uses only one stack-frame for Tail-Recursion, Java doesn't have that feature yet.
    public static <I, O> O foldLeftTailRecursive(List<I> inputList, O identity, Function<I, Function<O, O>> operation) {
        if (inputList == null || inputList.isEmpty()) return identity;

        O output = operation.apply(inputList.head()).apply(identity);
        return foldLeftTailRecursive(inputList.tail(), output, operation); // reducing a problem by 1. Calling recursion on inputList.tail()
    }

    // Going all the way to the end of the list using recursion and then applying operation between last element of the list and output of recursion
    // This is not Tail-Recursive because you are not calculating the final output every time you put a recursive method call in the stack.
    // To get final output, you need to pop entire stack.
    private static <I, O> O foldRightRecursive(List<I> inputList, O identity, Function<I, Function<O, O>> operation) {
        if (inputList == null || inputList.isEmpty()) return identity;

        O output = foldRightTailRecursive(inputList.tail(), identity, operation);
        return operation.apply(inputList.head()).apply(output);
    }

    Power of Laziness (Supplier)
    ----------------------------
    Read Chapter 9 of FunctionalProgrammingInJavaBook.java

    1)
        Supplier<Integer> from(int i) {
            return from(i+1); ----- infinite loop
        }

        Any recursion can be converted to lazy call using a Supplier

        Supplier<Integer> from(int i) {
            return () -> from(i + 1); ---- no recursion. Every call is returned back to client and client decides to make another call.
        }

        int i=0;
        while(i < 10) {
            Supplier<Integer> res = from(i++);
            System.out.println(res.get());
        }


        To fulfill the advantage of laziness, sometimes you may have to make class variable also lazy.

        class Cons extends Stream<I> {
            private Cons(I head, Supplier<Stream<I>> tail) {
                this.head = head;
                this.tail = tail;
            }
        }

        public static Stream<Integer> from(int i) {
            return new Cons(i, () -> from(i + 1)); // Wrapping recursive method by a Supplier (Better Approach)
        }

    2)
        TailCall<Integer> from(int i) {
            if(i == 9) return TailCall.getFinalValueContainer(i);
            return TailCall.getSupplierContainer(() -> from(i+1);
        }

        When there is an exit condition inside recursive method, you can wrap lazy call to recursive method with TailCall.
        You cannot do it without exit condition because of the way TailCall is implemented. It will always get SupplierContainer back from recursive method and TailCall doesn't have exit condition.

    (IMP) Rule of Thumb to stop blowing stack for infinite recursion:
    If there is no exit condition inside recursive method, you can wrap recursive call with a Supplier (make it lazy call) and let caller decide the exit condition and calling the method again.
    If there is an exit condition inside recursive method, you can make TailCall as the caller of the recursive method. Basic concept still remains same.

    3) Making method parameters lazy
       Look at Collectors' utility methods that creates a Collector for you.
       It uses a Supplier to send the identity result.
       Stream's collect method uses this identitySupplier.get() for each thread it spawns for parallelism. For each thread, a new identity element is used to aggregate its result.
       This is different than Stream's reduce method that doesn't use supplier for identity result.

    unfold method
    -------------
    Read Chapter 9 of FunctionalProgrammingInJavaBook.java

    Usage of foldRight (VERY IMP)
    -----------------
    All functional methods like map, flatMap, flattens, filter etc uses foldRight method to convert List<I> into List<O>

    map vs flatMap vs flattens
    --------------------------
    Look at List.java, Stream.java, Result.java(Java8's Optional.java)

    In List.java
        - map is a method whose Function takes I and converts it into O. So, for List<I>, it gives List<O>

        whereas

        - flatMap is a method whose Function takes I and generates List<O> instead of O.
        - And then concatenates each List<O> with main List<O>. This will give you List<O> instead of List<List<O>>

        whereas

        - flattens is a method that converts List<List<I>> into List<I>, concatenating many lists into one.

    Similar thing is in Stream.java and Result.java(Java8's Optional.java)




    List.java's map and flatMap

        public static <I, O> List<O> map(List<I> inputList, Function<I, O> f) {
            List<O> identityList = List.nilList();
            return foldRightRecursive(inputList,
                                      identityList,
                                      (List<O> identityList1) -> (I input) -> new Cons<O>(f.apply(input),identityList1));
        }

        public static <I, O> List<O> flatMap(List<I> inputList, Function<I, List<O>> f) {
            List<O> identityList = List.<O>nilList();
            return foldRightRecursive(inputList,
                                      identityList,
                                      (List<O> identityList1) -> (I input) -> List.concat(identityList1, f.apply(input)));
        }

        public static <I> List<I> flatten(List<List<I>> listOfInputLists) {
            List<I> identityList = List.nilList();
            return foldRightRecursive(listOfInputLists,
                                      identityList,
                                      (List<I> identityList1) -> (List<I> input) -> List.concat(identityList1, input));
        }


    Stream.java's map and flatMap

        public static <I,O> Stream<O> map(Stream<I> inputStream, Function<I, O> f) {
            Stream<O> identity = Stream.<O>empty();
            return foldRightTailRecursive(inputStream,
                                          identity,
                                          inputStreamElement -> identityStream1 -> cons(() -> f.apply(inputStreamElement), () -> identityStream1));
        }
        public <O> Stream<O> flatMap(Stream<I> inputStream, Function<I, Stream<O>> f) {
            Stream<O> identityStream = Stream.<O>empty();
            return foldRightTailRecursive(inputStream,
                                          identityStream,
                                          inputStreamElement -> identityStream1 -> f.apply(inputStreamElement).append(identityStream1));
        }

    Result.java's map and flatMap

        public static <V,U> Result<U> map(Result<V> result, Function<V, U> f) {
            return new Success<>(f.apply(result.value));
        }
        public static <V,U> Result<U> flatMap(Result<V> result, Function<V, Result<U>> f) {
            return f.apply(result.value);
        }

    Mutable vs Immutable List and Tree
    ----------------------------------
    See Chapter 5 notes in FunctionalProgrammingInJavaBook.java for List and Chapter 10 for Tree.
    List has a clear advantage of memory saving.
    Tree has a clear advantage of simplifying recursion logic.
    Immutability is very important to avoid concurrency problems also.

    COMPREHENSION Pattern
    ---------------------
    For Result/Optional - See Chapter 7 of FunctionalProgrammingInJavaBook.java
        It can be used if you need generate an output by using more than one Result objects.

    For List - See Chapter 8 of FunctionalProgrammingInJavaBook.java
        It can be used if you need generate an output by using more than one List objects.
        It is same as iterating one list inside another.


        Comprehension pattern for Optional

            When you have 2 or more Optional objects as input, you can use comprehension pattern

            private Function<Optional<A>, Function<Optional<B>, Optional<C>>> func(Function<A, Function<B, C>> augmenter) {

                //One way
                // it is not so good because you have to use oa.get() and ob.get(). you should try to avoid using get(), ifPresent etc methods on Optional.
                //return oa -> ob -> Optional.of(augmenter.apply(oa.get()).apply(ob.get()));


                // COMPREHENSION pattern. It is better because it avoids using optional.get()
                return oa -> ob ->
                oa.flatMap(a -> ob.map(b -> augmenter.apply(a).apply(b)));
            }


        Comprehension pattern for List

            When you have 2 or more Collection objects as input, you can use comprehension pattern

            private Function<List<A>, Function<List<B>, List<C>>> func(Function<A, Function<B, C>> augmenter) {

                //One way
                return la -> lb -> {
                    List<C> listC = new ArrayList<>();
                    for (A a : la) {
                        for (B b : lb) {
                            listC.add(augmenter.apply(a).apply(b));
                        }
                    }
                    return listC;
                };

                // COMPREHENSION pattern
                return (la) -> (lb) ->
                        la.stream()
                                .flatMap(a -> lb.stream()
                                        .map(b -> augmenter.apply(a).apply(b)))
                                .collect(Collectors.toList());
            }

    when to use fold method and when not to?
    ----------------------------------------
    Read Chapter 8 from FunctionalProgrammingInJavaBook.java


    Important Concept: Why shouldn't we use Result/Optional as method argument?
    ---------------------------------------------------------------------------
    See Chapter 15 of FunctionalProgrammingInJavaBook.java (ReadXMLFile.java)


	Optional<String> decoratorMethod_1(String str) {
		...
		return Optional.ofNullable(newStr);
	}

	Optional<String> decoratorMethod_2_1(Optional<String> str) {
		...
		return Optional.ofNullable(newStr);
	}

	Optional<String> decoratorMethod_2_2(String str) {
		...
		return Optional.ofNullable(newStr);
	}

	Approach 1
		Optional<String> newStr = decoratorMethod_1("abc");
		newStr = decoratorMethod_2_1(newStr); // In this approach, you can't use Optional's method chaining, so you can't reserve the advantage of Comprehension pattern, if you need it.

	Approach 2
		Optional<String> newStr = decoratorMethod_1("abc");
		newStr = newStr.flatMap(s -> decoratorMethod_2_2(s)); // As decoratorMethod_2_2 method is not taking Optional parameter, you can reserve the advantage of method chaining of Optional. You can ever reserve the advantage of Comprehension pattern, if you need it.

    How to avoid exception that can be raised due to type casting
    -------------------------------------------------------------
    See Chapter 15 of FunctionalProgrammingInJavaBook.java  (PropertyReader.java)
    Java 8's Optional doesn't have Result.failure(...) that can wrap the exception in it in case of casting error. So, in Java 8, you can return Optional.empty() in case of casting error, but cannot wrap an exception.

    Data Structure with Comparable and NonComparable elements
    ---------------------------------------------------------
    See DefaultHeap.java - There are two version of it, one with comparable elements and another with NonComparable elements.
                           If elements are NonComparable, then
                           - you need to provide your own Comparator.
                           - For more convenience, Create your own 'static compare(element1, element2, comparator)' method.


    How to write a method that can convert from one type to another?
    ----------------------------------------------------------------
    Any convert method is just like a map method.

    See Chapter 15 of FunctionalProgrammingInJavaBook.java  (PropertyReader.java's map and mapToEnum methods)

    Map enhancements
    ----------------
    See Java8MapEnhancementExample.java

    CompletableFuture
    -----------------
    see Java8CompletableFutureExample.java

    Date And Time Enhancements
    --------------------------
    see Java8DateAndTimeAPIEnhancementExample.java

    Optional Anti-Patterns
    ----------------------
    https://dzone.com/articles/optional-anti-patterns

    Anti-Pattern #1: Optional Types in Object Fields

        public class Car {
          private List<Wheel> wheels;
          private Optional<Engine> engine;  --- Optional is not Serializable. So do not keep it as a property
        }
        you can do

        public class Car {
          private List<Wheel> wheels;
          private Engine engine;

          public Optional<Engine> getOptionalEngine() {
            return Optional.ofNullable(engine);
          }
        }

    Anti-Pattern #2: Collections of Optionals

        private List<Optional<Wheel>> wheels;

        There is no need to have List of Optionals that might or might not have a value. We can have a smaller or even empty List of concrete objects instead.

    Anti-Pattern #3: Optional as a Method Argument

        Long calculate(List<Optional<Long>> data)

        Now let’s use this method:

        List<Long> data = Arrays.asList(1L, 2L);
        calculate(Optional.of(data));


        Optional wraps objects with another level of abstraction, which, in that case, is simply extra boilerplate code. On the other hand, we have a cleaner solution without Optional.

        List<Long> data = Arrays.asList(1L, 2L);
        calculate(data);

        it is not good to have Optional as method params because it make is hard to overload a method.
        Long calculate(Optional<Long> data, Optional<Long> data2)

    Anti-Pattern #4: Trying to Serialize Optionals

        Optionals were not designed to be serialized. Object serialization depends on object identity. If we reserve a look at the Javadocs, we can see that Optional was designed to be value-based:

        "This is a value-based class; use of identity-sensitive operations (including reference equality (==), identity hash code, or synchronization) on instances of Optional may have unpredictable results and should be avoided"

 */
public class Java8InActionBook {


    public static void main(String[] args) throws IOException {

        defaultMethodInheritanceExample();


        Function<Integer, Integer> fun = (x) -> x * 2;
        Function<Integer, Integer> andThen = fun.andThen((x) -> x + 2);
        Integer result = andThen.apply(3);

        System.out.println(result);

        Function<Integer, Integer> composedFun = (x) -> x + 2;
        Function<Integer, Integer> finalFunc = composedFun.compose(fun);

        result = finalFunc.apply(3);
        System.out.println(result);

        curried().apply(3);

        Employee employee = new Employee();
        Department department = new Department();
        department.setName("dept");
        //employee.setDepartment(department);

        String deptName = Optional.ofNullable(employee)
                .map(emp -> emp.getDepartment())
                .map(dept -> dept.getName())
                .orElse("Unknown");

        System.out.println(deptName);

        List<Integer> ints = new LinkedList<>();
        Integer any = ints.stream().findAny().orElse(null);
        System.out.println(any);
    }

    private static int nonCurried(Integer i) {
        return i * 3;
    }

    private static Function<Integer, Integer> curried() {
        return (i) -> i * 3;
    }


    protected static void defaultMethodInheritanceExample() {
        // Rule 1:
        // Classes always win. A method declaration in the class or a superclass takes priority over any default method declaration.
        G g = new G();
        g.hello(); // hello from class D

        // Rule 2:
        // Otherwise, sub-interfaces win: the method with the same signature in the most specific default-providing interface is selected.
        // Here C is more specific than A
        F f = new F();
        f.hello(); // hello from interface C

        // Rule 3:
        // Finally, if the choice is still ambiguous, the class inheriting from multiple interfaces has to explicitly select which default method implementation to use by overriding it and calling the desired method explicitly.
        E e = new E();
        e.hello(); // hello from interface A

        // Rule 4: Diamond problem
        // J implements H and I. H and I extends A. So basically both H and I's default overridden hello() from A.
        // In this case, J's default overridden method is also A's hello()
        J j = new J();
        j.hello(); // hello from interface A

        // Rule 5:
        // Little twist in Rule 3
        // L implements H and K. Both H and K extends A. K has its own hello() and H has default overridden hello() from A.
        // So, in this case closest related hello() will be called, which is H's hello()
        L l = new L();
        l.hello(); //hello from H
    }

    interface A {
        default void hello() {
            System.out.println("hello from interface A");
        }
    }

    interface B {
        default void hello() {
            System.out.println("hello from interface B");
        }
    }

    interface C extends A {
        default void hello() {
            System.out.println("hello from interface c");
        }
    }

    static class D {

        public void hello() {
            System.out.println("hello from class D");
        }
    }

    static class E implements A, B {
        public E() {

        }

        // if both implemented interfaces have same method hello(), then java doesn't know which one to use as a default overridden method
        // so it will force you to override child class' own hello() and do as below to call particular interface specific default method hello()
        @Override
        public void hello() {
            A.super.hello();
        }

    }

    // In this case A and C both has hello() and C extends A. It will use more specific hello() as default overridden method, which is C here.
    static class F implements A, C {
        public F() {

        }
    }

    static class G extends D implements A, C {
        public G() {

        }
    }

    interface H extends A {
    }

    interface I extends A {
    }

    static class J implements I, H {
    }

    interface K extends A {
        default void hello() {
            System.out.println("hello from H");
        }
    }

    static class L implements H, K {
    }


}
