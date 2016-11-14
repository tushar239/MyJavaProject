package java8.functionalprograming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Set;
import java.util.Spliterator;
import java.util.TreeSet;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author Tushar Chokshi @ 5/15/16.
 */
/*
    http://www.tutorialspoint.com/java8/java8_streams.htm
    http://www.concretepage.com/java/jdk-8/java-8-stream-forEach-foreachordered-peek-skip-toarray-example

    Stream -
        It provides you an access to elements in sequential manner.
        It can do some computation on an element and return the result.
        It never stores the values.

    Source of a Stream -
        Collection
            nilList.stream() --- to convert a collection to a stream
        Array - read Java8InActionBook.java (chapter 5)
            Arrays.stream(array) --- to convert an array to a stream
        I/O resources   - read Java8InActionBook.java (chapter 5)
        Ranges (1 to 10 etc). - read Java8InActionBook.java (chapter 5)
            IntStream.range(0, 9) --- basically to replace a standard for(int i=0; i<=9; i++) kind of loops
            IntStream.closedRange(0, 9) --- 9 inclusive
        from values
            Stream<String> stream = Stream.of("Java 8 ", "Lambdas ", "In ", "Action");
        functions (infinite streams using stream.generate and iterate) - read Java8InActionBook.java (chapter 5)
            Stream.iterate and Stream.generate. These two operations let you create what we call an infinite stream

            Stream.iterate(0, n -> n + 2).limit(10)

            The iterate method takes an initial value, here 0, and a lambda (of type Unary-Operator<T>) to apply successively on each new value produced. Here you return the previous element added with 2 using the lambda n -> n + 2
            Note that this operation produces an infinite stream—the stream doesn’t have an end because values are computed on demand and can be computed forever. We say the stream is unbounded.
            You’re using the limit method to explicitly limit the size of the stream.

            Stream.generate(Math::random).limit(5)

            Similarly to the method iterate, the method generate lets you produce an infinite stream of values computed on demand. But generate doesn’t apply successively a function on each new produced value. It takes a lambda of type Supplier<T> to provide new values.


    With Java 8, Collection interface has two methods to generate a Stream −

        stream() − Returns a sequential stream considering collection as its source.
        parallelStream() − Returns a parallel Stream considering collection as its source.
            When a stream executes in parallel, the Java runtime partitions the stream into multiple substreams.
            Aggregate operations iterate over and process these substreams in parallel and then combine the results.

        What is Parallelism?
        https://blog.logentries.com/2015/10/java-8-introduction-to-parallelism-and-spliterator/

        At a high level, it is a term that refers to programming in a way to exploit multi-core CPU units. That is to say, to take a piece of work (a task) and break it out into separate units (sub-tasks) that can be processed in parallel. Then aggregating the results of all the processed units to complete the original work.
        The savings found in using parallel cores for processing is to try to keep each CPU core busy all the time – which requires reading data as and when is needed without delay. Before embarking on a parallel processing architecture, some cost-benefit analysis is required to be sure that this is the right approach.
        This concept of parallelism is behind the existing fork/join framework found in Java

        What is Spliterator?
        https://blog.logentries.com/2015/10/java-8-introduction-to-parallelism-and-spliterator/

        A new interface added to java.util is the Spliterator, which as the name implies, is a new special kind of Iterator that can traverse a Collection.

        The Spliterator can ‘split’ the Collection, partitioning off some of its elements as another Spliterator.
        This does allow parallel processing of different parts of a Collection but note that the Spliterator itself does not provide the parallel processing behaviour.
        Instead, the Spliterator is there to support parallel traversal of the suitably partitioned parts of a Collection.
        This solves the problem of dividing the data, as held in a Collection such as an ArrayList, into suitably sized sub-units that can be processed in parallel.

        If this Spliterator can be split (partitioned), the method will try to divide the elements in half for a balanced parallel computation.
        This will not always be the case, and where a Spliterator cannot be split, a null is returned.
        The splitting, or partitioning, can continue recursively until a Spliterator returns null. If a different behaviour is required in how the trySplit() method works, then again a custom implementation of the Spliterator interface is required.

        What is lazy evaluation?
        In Java 8, there are intermediate and terminal operations.
        filter, map etc are intermediate operations.
        findFirst, collect etc are terminal (reducer) operations.
        Unless, terminal operation is called, intermediate operations are not going to executed. That's why Java 8 lamdas are evaluated lazily.



    Operations -

        Stream's static methods

            Stream.of(array of values)
                takes an array of values and gives a stream of it.

        Stream supports aggregation operations like
            filter
            map
            distinct
            limit
            reduce
            find
            match and so on.

        Automatic Iterations -
            Stream operations do the iterations internally over the source elements provided, in contrast to Collections where explicit iteration is required.


        Piping (chaining) Operations -
            Normally, Stream operations returns a Stream/Optional/nothing or literals (boolean, int etc). So that other operations can be carried over. This is called Piping.

            Operations that returns Stream (called Intermediate operations)
                - filter
                    useful for replacing if conditions

                - map
                    mapToInt, mapToLong, mapToDouble, mapToObj etc.
                    Converting stream to IntStream using mapToIn helps us to invoke sumIteratively(), min(), max() etc functions. These functions are variant of reduce() only

                    OptionalInt sumIteratively = numbers.stream().mapToInt((n) -> n * n).sumIteratively();

                    Here why sumIteratively it has to return OptionalInt?
                    As there is not initial value like reduce, if there are no elements in numbers, then it should not return 0. It should return OptionalInt(isPresent=false, value=0)

                    Likewise, there are OptionalInt, OptionalDouble, and OptionalLong

                - flatMap
                    flatMapToInt, flatMapToLong, flatMapToDouble, flatMapToObj etc
                    flatMap takes a function argument that returns a stream.
                    It is mainly used to deleteRootAndMergeItsLeftAndRight the values of many streams into one.

                    http://www.mkyong.com/java8/java-8-flatmap-example/

                    Stream<Integer[]>		-> flatMap ->	Stream<Integer>
                    Stream<List<Integer>>	-> flatMap ->	Stream<Integer>

                    { {1,2}, {3,4}, {5,6} } -> flatMap -> {1,2,3,4,5,6}
                    { {'a','b'}, {'c','d'}, {'e','f'} } -> flatMap -> {'a','b','c','d','e','f'}

                    What is the difference between map and flatMap?
                    http://stackoverflow.com/questions/26684562/whats-the-difference-between-map-and-flatmap-methods-in-java-8

                    Stream<R> map(Function<T, R> mapper)
                    Stream<R> flatMap(Function<T, Stream<R>> mapper)

                    Both takes a Function as input. flatMap's Function is useful to make the input flat before applied to map.
                    e.g.

                      List<Integer> together = Stream.of(1,2,3,4) // Stream of an array of integers
                                                    .map(integer -> integer + 1)
                                                    .collect(Collectors.toList()); // O/P: [2,3,4,5]

                      List<Integer> together = Stream.of(asList(1, 2), asList(3, 4)) // Stream of an array of lists
                                                    .flatMap(numbers -> numbers.stream()) // flat each element by converting a nilList to its stream, so that map can be used on it
                                                    .map(integer -> integer + 1)
                                                    .collect(Collectors.toList()); // O/P: [2,3,4,5]

                    In a nutshell, the flatMap method lets you replace each value of a stream with another stream and then concatenates all the generated streams into a single stream.


                - distinct
                - peek
                    just takes Consumer as a parameter
                - skip and limit
                    skip and limit are useful for pagination

            Operations that returns Optional

                Below operations return Optional that avoids client do null check of the result. Optional will have a found value or will return optional of type EMPTY.
                Beauty of Optional is you can invoke any other stream operation (filter, map etc) on Optional object.

                - findAny() and findFirst()
                - min() and max()
                    can be invoked on intStream, longStream etc.
                    Stream can be converted to intStream using nilList.stream().mapToInt(x->x)
                - reduce(...)
                    Reduction :
                        - reduce(initial value/identity/seed, BiFunction accumulator, BinaryOperator combiner)  --- returns value
                        - reduce(initial value/identity/seed, BinaryOperator accumulator) --- returns value
                        - reduce(BinaryOperator accumulator)  --- returns Optional
                            Why does it return an Optional? (IMP)
                            Consider the case when the stream contains no elements. The reduce operation can’t return any result because it doesn’t have an initial value. This is why the result is wrapped in an Optional object to indicate that the result may be absent.

                            int sumIteratively = numbers.stream().map((n) -> n * n).reduce(0, (n1,n2) -> n1+n2); // reduce with initial value returns primitive
                            Optional<Integer> sumIteratively = numbers.stream().map((n) -> n * n).reduce((n1,n2) -> n1+n2); // reduce without initial value returns Optional<Integer>


                        What is accumulator and combiner?
                        Read stream.collect(supplier, accumulator, combiner) as described in collect section.
                        Also read when not to use reduce and why?

                        Collection<Person> persons = new ArrayList<Person>() {{
                            add(new Person(Person.Sex.MALE, 21));
                            add(new Person(Person.Sex.MALE, 22));
                            add(new Person(Person.Sex.MALE, 23));
                            add(new Person(Person.Sex.FEMALE, 20));
                        }};

                        Person newPersonWithAllMalesAges = persons.stream()
                                .filter(p -> p.getGender() == Person.Sex.MALE)
                                .reduce(new Person(Person.Sex.MALE, 0), // initial value/seed/identity
                                        (person1, person2) -> {person1.age += person2.age; return person1;} // accumulator
                                        );

                        System.out.println("New Person with all MALE persons ages: "+newPersonWithAllMalesAges.getAge()); // 66


                        Simple example that does summation of all numbers in a collection.
                            numbers.stream().reduce(0, (x, y) -> x + y);

                        Result of two elements of a stream is calculated and it is combined with third element and that result is combined with the fourth one and so on.
                            reduce(BinaryOperator)
                            e.g. Optional<integer> sumIteratively = numbers.stream().reduce((x, y) -> x + y);

                        You can also set an initial value(identity) of the result. That result is combined with the first element. Its result is combined with second and so on.
                            reduce(initial value, BinaryOperator)
                            e.g. Integer productRecursively = numbers.stream().reduce(1, (x, y) -> x * y);

                        Finding min and max is also made very easy with the help of reduce operation. See the below example.
                            //Min of the stream
                            Optional<Integer> min = numbers.stream().reduce(0, Integer::min); // same as reduce(0, (a, b) -> Integer.min(a, b));
                            //Max of the stream
                            Optional<Integer> max = numbers.stream().reduce(0, Integer::max);


                    When to use Primitive Stream (Numeric Stream) instead of reduce? (IMP)

                        Read Java8InActionBook.java


            Operations returns nothing or literals

                Terminal Operations -
                    Stream operations like collect() that doesn't return a stream is called terminal operation.
                    Terminal operation which is normally present at the end of the pipelining operation to mark the end of the stream.

                    http://java.amitph.com/2014/02/java-8-streams-api-terminal-operations.html

                    Looping:
                        forEach(consumer)
                        forEachOrdered(consumer)

                        Difference between forEach and forEachOrdered
                        http://stackoverflow.com/questions/32797579/foreach-vs-foreachordered-in-java-8-stream

                        forEachOrdered is useful for parallelism:
                        For parallel stream pipelines, forEach does not guarantee to respect the encounter order of the stream, as doing so would sacrifice the benefit of parallelism.
                        forEachOrdered performs an action for each element of this stream, in the encounter order of the stream if the stream has a defined encounter order.

                    Conditional Matching:
                        boolean anyMatch(predicate)
                        boolean allMatch(predicate)
                        boolean noneMatch(predicate)

                    Conditional Finding:
                        Optional findAny()
                        Optional findFirst()

                    collect method:
                        collect(...) --- This is a very special method


            Optional:
                Read chapter 10 from Java8InAction.java


            Collection - MOST IMPORTANT ONE
                 Read chapter 6 from Java8InAction.java

        Map enhancements

            Read Java8_Map_Enhancements.docx



*/
public class Java8SteamsExample {
    public static void main(String[] args) {
        // Map enhancements
        {
            Map<String, String> map = new HashMap<>();
            map.put("1", "one");
            map.put("2", "two");
            map.put("3", "three");

            // you can iterate a map using forEach like collection
            map.forEach((k, v) -> System.out.format("%s's value is %s\n", k, v));
            /*
            1's value is one
            2's value is two
            3's value is three
            */

            // Replace the value of a key "1" to "one-new", if current value matches to "one"
            boolean replaced = map.replace("1", "one", "one-new");

            // If key="1" doesn't exist, then create it, otherwise just replace its value
            final String newValue = map.replace("1", "one-new");

            // replaceAll simply replaces value of all keys based on the output of passed function
            map.replaceAll((k, v) -> v + "-new-value");
            map.forEach((k, v) -> System.out.format("%s's value is %s\n", k, v));

            // compute is a special function that replaces the value of a key with new value calculated by passed function.
            // it returns a new comuted value
            // if new value is null then it removes a key from map
            String value = map.compute("1", (k, v) -> null);
            map.forEach((k, v) -> System.out.format("%s's value is %s\n", k, v));

            value = map.computeIfAbsent("4", (k) -> k + "-new");
            map.forEach((k, v) -> System.out.format("%s's value is %s\n", k, v));

            value = map.computeIfPresent("3", (k, v) -> v + "-new computed value");
            map.forEach((k, v) -> System.out.format("%s's value is %s\n", k, v));

            // add key-value to a map, if key doesn't exist in the map, if key exist then do nothing
            map.putIfAbsent("5", "five");
            map.forEach((k, v) -> System.out.format("%s's value is %s\n", k, v));



        }
        // Stream.of(...)
        {
            Stream.of("a1", "a2", 1)
                    .findFirst()
                    .ifPresent(System.out::println);  // a1
        }
        // converting stream to an array
        {
            System.out.println(Stream.of("a1", "a2", "a3").toArray()); // converts to array of objects
        }

        // Creating a stream of an array
        {
            // Creating a stream of int array
            int[] ints = {0,1,2};
            Stream.of(ints).forEach((i) -> System.out.println(i));

            // Using IntStream instead of normal Stream. IntStream has methods like boxed() that converts IntStream to Stream<Integer>
            final Stream<Integer> integerStream = IntStream.of(ints).boxed();
            integerStream.forEach((i) -> System.out.println(i));



        }
        // Replacing standard range for loops with lambda
        // http://www.journaldev.com/2763/java-8-lambda-expressions-and-functional-interfaces-example-tutorial
        {
            {
                List<Integer> result = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    if (i % 2 == 0) {
                        result.add(i);
                    }
                }
            }
            {
                // Unlike to normal stream, Numeric Stream's collect method doesn't take Collector. You need to pass supplier, accumulator, combiner by yourself.
                // If you see there is actually no difference between both of these approaches because in both of these boxing (converting int to Integer) happens.
                // In first approach, you are explicitly saying box the elements. In second approach, while adding i to nilList, boxing happens implicitly.
                List<Integer> result = new ArrayList<>();
                IntStream.range(0, 10).filter(i -> i % 2 == 0).collect(() -> result, (list, i) -> list.add(i), (list1, list2) -> list1.addAll(list2));
                IntStream.range(0, 10).filter(i -> i % 2 == 0).boxed().collect(Collectors.toCollection(() -> result));
                // If you see, numeric stream's reduce method doesn't have to do boxing. reduce(identity, IntBinaryOperator). IntBinaryOperator accepts ints as input and outputs int.
                IntStream.range(0, 10).filter(i -> i % 2 == 0).reduce(0, (i1, i2) -> i1+i2);
                //IntStream.range(0, 10).noneMatch(predicate)
                //IntStream.range(0, 10).allMatch(predicate)
                //IntStream.range(0, 10).anyMatch(predicate)

            }

        }
        // forEach vs forEachOrdered
        // http://stackoverflow.com/questions/32797579/foreach-vs-foreachordered-in-java-8-stream
        {
            Stream.of("AAA","BBB","CCC").parallel().forEach(s->System.out.println("Output:"+s));
            Stream.of("AAA","BBB","CCC").parallel().forEachOrdered(s->System.out.println("Output:"+s));
            /*
            The second line will always output

            Output:AAA
            Output:BBB
            Output:CCC

            whereas the first one is not guaranteed since the order is not kept.
            forEachOrdered will processes the elements of the stream in the order specified by its source, regardless of whether the stream is sequential or parallel.
             */
        }

        // filter
        {
            List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd", "", "jkl");
            strings.stream().filter((str) -> str.contains("bc")).collect(Collectors.toList());
        }

        // map
        {
            List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);

            {
                final ArrayList<Integer> collectedList = numbers.stream().map((n) -> n * n).collect(Collectors.toCollection(() -> new ArrayList<>()));
                System.out.println("Squared Result :" + collectedList);
            }
            {
                System.out.println("Find sumIteratively: "+numbers.stream().mapToInt((n) -> n * n).sum());

                System.out.print("Find min: ");
                numbers.stream().mapToInt((n) -> n * n).min().ifPresent((min) -> System.out.println(min));
            }

        }
        // flatMap
        {
            System.out.println("flatMap example");
            Integer[][] data = new Integer[][]{{1,2,3}, {4,5,6}, {7,8,9}};
            Stream<Integer[]> streamFromArray = Arrays.stream(data);
            final Stream<Integer> stream = streamFromArray.flatMap(arr -> Arrays.stream(arr)); // this will create one stream from many streams
            System.out.println(stream.collect(Collectors.toList())); // [1, 2, 3, 4, 5, 6, 7, 8, 9]


            // From Java 8 In Action (Java_8_in_Action.pdf) book pg 125
            String[] words = {"Hello", "World"};
            Stream<String> streamOfwords = Arrays.stream(words);

            final Stream<String[]> stream1 = streamOfwords.map(word -> word.split(""));// [{"H","e","l","l","o"}, {"w","o","r","l","d"}]
            //final Stream<Stream<String>> streamStream = stream1.map(wordArray -> Arrays.stream(wordArray)); // you don't want Stream<Stream<String>>. You want String<String>
            final Stream<String> stringStream = stream1.flatMap(wordArray -> Arrays.stream(wordArray)); // a stream of ["H","e","l","l","o","w","o","r","l","d"]

            final List<String> collect = stringStream.collect(Collectors.toList()); // ["H","e","l","l","o","w","o","r","l","d"]
            System.out.println(collect);

        }

        /*{
            // not working
            List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
            final Map<Integer, List<Integer>> collectedGroup = numbers.stream().collect(Collectors.groupingBy(List::get));
            System.out.println(collectedGroup);
        }*/

        // anyMatch, allmatch
        {
            List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
            final boolean anyMatch = numbers.stream().anyMatch(n -> n > 5);
            System.out.println("anyMatch: " + anyMatch); // anyMatch: true
        }

        // distinct, findAny, Optional
        {
            List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
            final Optional<Integer> any = numbers.stream().distinct().findAny();
            any.ifPresent(System.out::println); // 3
        }
        // peek - peek takes Consumer functional interface as an argument. peek returns stream itself after applying the action passed as consumer object.
        {
            System.out.println("Testing stream.peek()");
            List<Integer> list = Arrays.asList(10,11,12);
            list.stream().peek(i->System.out.println(i*i)).collect(Collectors.toList());
        }
        // skip - skips number of elements and returns a stream
        // skip and limit can be used for pagination
        {
            System.out.println("Testing stream.skip()");
            List<Integer> list = Arrays.asList(10,11,12);
            list.stream().skip(1).peek(i->System.out.println(i*i)).collect(Collectors.toList());
        }
        // limit - limits number of elements
        {
            System.out.println("Testing stream.limit()");
            List<Integer> list = Arrays.asList(10,11,12);
            List<Integer> collect = list.stream().skip(1).limit(1).peek(i -> System.out.println(i * i)).collect(Collectors.toList());
        }
        // reduce, Optional
        {
            List<Integer> numbers = Arrays.asList();
            final Optional<Integer> reducedOption = numbers.stream().reduce((x, y) -> x + y);
            if (reducedOption.isPresent()) { // Optional saves a client from null check
                System.out.println(reducedOption.get()); // nothing will be printed
            }
            // OR
            reducedOption.ifPresent(System.out::println);// nothing will be printed

            numbers = Arrays.asList(1, 2, 3);
            System.out.println(numbers.stream().reduce(1, (x, y) -> x + y)); // 7


            System.out.println("Find sumIteratively: "+numbers.stream().mapToInt((n) -> n * n).reduce(0, (n1, n2) -> n1 + n2));
            // OR
            System.out.println("Find sumIteratively: " + numbers.stream().mapToInt((n) -> n * n).sum()); // sumIteratively(), min(), max() are variant of reduce() only

            // if numbers is empty, there should not be any min value. In that case it returns OptionalInt with isPresent=false and value=0
            final OptionalInt min = numbers.stream().mapToInt(n -> n).min();

            // Optional as many other usages as explained before.
            // see Department class' addEmployees method or Java8StreamWithComplexObject class
        }
        // collect
        {
            System.out.println("Testing collect(supplier that returns an object, BiConsumer that takes input as a supplier's output and an element of a nilList, BiConsumer that takes input as two lists created by parallel streams)");
            List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
            {
                final Stream<Integer> integerStream = numbers.stream().map(n -> n * n);

                List collector = integerStream
                        .collect(
                                ArrayList::new, // same as () -> new ArrayList<>(),
                                List::add, // same as //(nilList,b) -> nilList.add(b)
                                List::addAll // same as (list1, list2) -> {return list1.addAll(list2);}
                        );
                System.out.println("Without using Collectors utility class: " + collector);
                //collector = integerStream.collect(Collectors.toList());// Exception in thread "main" java.lang.IllegalStateException: stream has already been operated upon or closed
            }
            {
                final Stream<Integer> integerStream = numbers.stream().map(n -> n * n);
                // Above utility of collecting the stream elements into a nilList is given by Collectors class' static method toList()
                List collector = integerStream.collect(Collectors.toList());
                System.out.println("Using Collectors utility class to collect stream elements into a nilList: " + collector);
            }
            {
                final Stream<Integer> integerStream = numbers.stream().map(n -> n * n);
                Set<Integer> set = integerStream.collect(Collectors.toCollection(() -> new TreeSet<>()));// same as TreeSet::new
                System.out.println("Using Collectors utility class to collect steam elements into a TreeSet");
            }
            {
                Employee e1 = new Employee();
                e1.setName("Tushar");
                e1.setSalary(100000);

                Employee e2 = new Employee();
                e2.setName("Miral");
                e1.setSalary(200000);

                Employee e3 = new Employee();
                e3.setName("Srikant");
                e1.setSalary(500000);

                Department d1 = new Department();
                d1.setName("DMG");
                d1.addEmployees(e1, e2, e3, null);
                //System.out.println(d1.getEmployees());

                System.out.println("Testing Collectors' summing* method");
                System.out.println(d1.getEmployees().stream().collect(Collectors.summingDouble(Employee::getSalary)));
            }

        }
        // Custom class experiment
        {
            List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
            MyCollectorClass<List<Integer>, Integer> myCollectorClass = new MyCollectorClass<>(ArrayList::new, numbers);
            System.out.println(myCollectorClass.addSourceListToSupplierList());// [3, 2, 2, 3, 7, 3, 5]
        }

        // Spliterator
        {
            Collection<Consumer<String>> consumers = new ArrayList<>();
            consumers.add((String name) -> {
                System.out.println(name);
            });
            consumers.add((String name) -> {
                System.out.println("hi, "+name);
            });
            consumers.add((String name) -> {
                System.out.println("how r u?, "+name);
            });
            consumers.add((String name) -> {
                System.out.println("where r u?, "+name);
            });


            final Spliterator<Consumer<String>> consumerSpliterator1 = consumers.spliterator();
            System.out.println("ConsumerSpliterator1's characteristics: " + consumerSpliterator1.characteristics());
            System.out.println("ConsumerSpliterator1's estimateSize: " + consumerSpliterator1.estimateSize());
            System.out.println("ConsumerSpliterator1's exactSizeIfKnown: " + consumerSpliterator1.getExactSizeIfKnown());
            /*
            ConsumerSpliterator1's characteristics: 16464
            ConsumerSpliterator1's estimateSize: 4
            ConsumerSpliterator1's exactSizeIfKnown: 4
             */

            Spliterator<Consumer<String>> consumerSpliterator11;
            while ((consumerSpliterator11 = consumerSpliterator1.trySplit()) != null) {
                System.out.println("After splitting a collection...");
                System.out.println("ConsumerSpliterator11's characteristics: " + consumerSpliterator11.characteristics());
                System.out.println("ConsumerSpliterator11's estimateSize: " + consumerSpliterator11.estimateSize());
                System.out.println("ConsumerSpliterator11's exactSizeIfKnown: " + consumerSpliterator11.getExactSizeIfKnown());
                consumerSpliterator11.forEachRemaining(consumer -> consumer.accept("Tushar"));
            }
            consumerSpliterator1.forEachRemaining(consumer -> consumer.accept("Tushar"));
            /*
            If you just do consumerSpliterator1.trySplit() without while loop,
            It should create 2 partitions of equal size
            one with 2 elements and another with 2 elements. One of them will be the original one consumerSpliterator1.

            If you use while loop, it will try to keep partitioning the sub-partitions also until trySplit() returns null (cannot partition anymore)
            You can read 'https://blog.logentries.com/2015/10/java-8-introduction-to-parallelism-and-spliterator/'

            After splitting a collection...
            ConsumerSpliterator11's characteristics: 16464
            ConsumerSpliterator11's estimateSize: 2
            ConsumerSpliterator11's exactSizeIfKnown: 2
            Tushar
            hi, Tushar

            After splitting a collection...
            ConsumerSpliterator11's characteristics: 16464
            ConsumerSpliterator11's estimateSize: 1
            ConsumerSpliterator11's exactSizeIfKnown: 1
            how r u?, Tushar
            where r u?, Tushar   --- from initial partition which is left with just one element
             */


        }
    }


    static class MyCollectorClass<A extends List, T> {
        Supplier<A> supplier;
        BiConsumer<A, T> accumulator;
        //Function<A, A> combiner;
        List<T> sourceList;

        MyCollectorClass(Supplier<A> supplier, List<T> sourceList) {
            this.supplier = supplier;
            this.sourceList = sourceList;
        }

/*
        MyCollectorClass(Supplier<A> supplier,
                         BiConsumer<A, T> accumulator,
                         List<T> sourceList
                         ) {
            this.supplier = supplier;
            this.accumulator = accumulator;
            this.sourceList = sourceList;

        }
*/

        public A addSourceListToSupplierList() {
            A a = supplier.get();
            BiFunction<A, T, A> function = (A, T) -> {
                A.add(T);
                return A;
            };
            sourceList.forEach(n -> function.apply(a, n));
            return a;
        }

    }
}