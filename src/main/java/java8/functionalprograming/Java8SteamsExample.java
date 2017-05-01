package java8.functionalprograming;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Set;
import java.util.SortedSet;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

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

    public static List<String> returnSomething() {
        List<String> strs = new ArrayList<>();
        strs.add("a");
        strs.add("b");
        strs.add("c");
        return strs;
    }

    public static List<String> returnSomethingAfterSometime() {
        sleep(1000);
        return returnSomething();
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // Map enhancements
        System.out.println("Map Enhancements Examples ...");
        System.out.println();
        mapEnhancementExamples();
        System.out.println();

        // CompletableFuture
        System.out.println("CompletableFuture Examples ...");
        System.out.println();
        completableFutureExamples();
        System.out.println();

        // Stream.of(...)
        System.out.println("Stream.of example...");
        System.out.println();
        {
            Stream.of("a1", "a2", 1)
                    .findFirst()
                    .ifPresent(System.out::println);  // a1
        }

        System.out.println();

        // converting stream to an array
        System.out.println("Converting stream to array...");
        System.out.println();
        {
            System.out.println(Stream.of("a1", "a2", "a3").toArray()); // converts to array of objects
        }

        System.out.println();

        // Creating a stream of an array
        System.out.println("reating a stream of int array...");
        System.out.println();
        {
            // Creating a stream of int array
            int[] ints = {0, 1, 2};
            Stream.of(ints).forEach((i) -> System.out.println(i));

            // Using IntStream instead of normal Stream. IntStream has methods like boxed() that converts IntStream to Stream<Integer>
            final Stream<Integer> integerStream = IntStream.of(ints).boxed();
            integerStream.forEach((i) -> System.out.println(i));


        }

        System.out.println();

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
                IntStream.range(0, 10).filter(i -> i % 2 == 0).reduce(0, (i1, i2) -> i1 + i2);
                //IntStream.range(0, 10).noneMatch(predicate)
                //IntStream.range(0, 10).allMatch(predicate)
                //IntStream.range(0, 10).anyMatch(predicate)

            }

        }

        System.out.println();

        // forEach vs forEachOrdered
        // http://stackoverflow.com/questions/32797579/foreach-vs-foreachordered-in-java-8-stream
        {
            Stream.of("AAA", "BBB", "CCC").parallel().forEach(s -> System.out.println("Output:" + s));
            Stream.of("AAA", "BBB", "CCC").parallel().forEachOrdered(s -> System.out.println("Output:" + s));
            /*
            The second line will always output

            Output:AAA
            Output:BBB
            Output:CCC

            whereas the first one is not guaranteed since the order is not kept.
            forEachOrdered will processes the elements of the stream in the order specified by its source, regardless of whether the stream is sequential or parallel.
             */
        }

        System.out.println();

        // filter
        {
            List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd", "", "jkl");
            strings.stream().filter((str) -> str.contains("bc")).collect(Collectors.toList());
        }

        System.out.println();

        // map
        {
            List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);

            {
                final ArrayList<Integer> collectedList = numbers.stream().map((n) -> n * n).collect(Collectors.toCollection(() -> new ArrayList<>()));
                System.out.println("Squared Result :" + collectedList);
            }
            {
                // When summation of two numbers are done, it needs to be done on primitives (ints and not Integers).
                // There is a cost of converting Integer to int (Unboxing) for summation and then Boxing again after summation is done.
                // If you see below reduce method, that is what exactly would happen.
                numbers.stream().map((n) -> n * n).reduce(0, (n1, n2) -> n1 + n2);

                // To avoid the cost of Unboxing and Boxing during reduce operation for two numbers n1 and n2, you can just do that once before calling reduce.
                System.out.println("Find sumIteratively: " + numbers.stream().mapToInt((n) -> n * n).sum());
                //is same as
                //System.out.println("Find sumIteratively: "+numbers.stream().mapToInt((n) -> n * n).reduce(0, (n1, n2) -> n1+n2));

                System.out.print("Find min: ");
                numbers.stream().mapToInt((n) -> n * n).min().ifPresent((min) -> System.out.println(min));
            }

        }

        System.out.println();

        // flatMap
        {
            System.out.println("flatMap example");
            Integer[][] data = new Integer[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
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

        // Collectors.groupingBy, mapping
        {
            System.out.println("groupingBy example");

            Department dept11 = new Department();
            dept11.setName("dept11");

            Department dept12 = new Department();
            dept12.setName("dept12");

            Department dept13 = new Department();
            dept13.setName("dept13");

            Employee emp1 = new Employee();
            emp1.setName("emp1");
            emp1.setSalary(100000F);
            emp1.setDepartment(dept11);

            Employee emp2 = new Employee();
            emp2.setName("emp2");
            emp2.setSalary(300000F);
            emp2.setDepartment(dept12);

            Employee emp3 = new Employee();
            emp3.setName("emp1");
            emp3.setSalary(310000F);
            emp3.setDepartment(dept13);

            List<Employee> employees = new ArrayList<>();
            employees.add(emp1);
            employees.add(emp2);
            employees.add(emp3);

            //Collectors.groupingBy((Employee emp) -> emp.getName());

            // Collectors.groupingBy(Function)
            // is same as
            // Collectors.groupingBy(Function, Collectors.toList())
            // is same as
            // Collectors.groupingBy(Function, HashMap::new, Collectors.toList())
            Map<String, List<Employee>> hashMapOfEmpNameVsListOfEmps = employees.stream().collect(Collectors.groupingBy((emp) -> emp.getName()));
            // you can provide your own identity result
            TreeMap<String, List<Employee>> treeMapOfEmpNameVsListOfEmps =
                    employees.stream()
                            .collect(
                                    Collectors.groupingBy(
                                            Employee::getName,
                                            TreeMap::new, // identity result. It has to be a type of Map. Default is HashMap.
                                            Collectors.toList()
                                    )
                            );
            // is same as
            // Collectors.groupingBy(Function, Collector)
            treeMapOfEmpNameVsListOfEmps = employees.stream()
                    .collect(
                            Collectors.groupingBy(
                                    Employee::getName,
                                    TreeMap::new, // identity
                                    Collector.of(
                                            ArrayList::new,
                                            (list, emp) -> list.add(emp),
                                            (list1, list2) -> {
                                                list1.addAll(list2);
                                                return list1;
                                            }
                                    )
                            )
                    );


            System.out.println(treeMapOfEmpNameVsListOfEmps);
            // {
            // emp2=[Employee{name='emp2', salary=300000.0, department=Department{name='dept12', employees=[]}}],
            // emp1=[Employee{name='emp1', salary=100000.0, department=Department{name='dept13', employees=[]}},
            //      Employee{name='emp1', salary=310000.0, department=Department{name='dept11', employees=[]}}]
            // }


            TreeMap<String, List<String>> empNameVsListOfDeptNames = employees.stream().collect(
                    Collectors.groupingBy(
                            (emp) -> emp.getName(),
                            TreeMap::new,
                            Collectors.mapping((emp) -> emp.getDepartment().getName(), Collectors.toList())
                    )
            );
            // is same as
            empNameVsListOfDeptNames = employees.stream()
                    .collect(
                            Collectors.groupingBy(
                                    Employee::getName,
                                    TreeMap::new,
                                    Collector.of(
                                            ArrayList::new,
                                            (list, emp) -> list.add(emp.getDepartment().getName()),
                                            (list1, list2) -> {
                                                list1.addAll(list2);
                                                return list1;
                                            }
                                    )
                            )
                    );

            System.out.println(empNameVsListOfDeptNames);// {emp1=[dept11, dept13], emp2=[dept12]}

            // Creating Map<empName, Map<deptName, Department>>
            TreeMap<String, HashMap<String, Department>> empNameVsMapOfDeptNameAndDept = employees.stream().collect(
                    Collectors.groupingBy(
                            (emp) -> emp.getName(), // T -> K
                            () -> new TreeMap<>(), // Map<K, D> how does it know that TreeMap<> is same as TreeMap<String, Map<String, Department>>
                            Collector.of( // Collector<T, A, D>
                                    () -> new HashMap<>(), // how does it know that HashMap<> is same as HashMap<String, Department>
                                    (map, emp) -> map.put(emp.getDepartment().getName(), emp.getDepartment()),
                                    (map1, map2) -> {
                                        map1.putAll(map2);
                                        return map1;
                                    }
                            )
                    )
            );
            System.out.println(empNameVsMapOfDeptNameAndDept);
            // {
            // emp1={dept11=Department{name='dept11', employees=[]}, dept13=Department{name='dept13', employees=[]}},
            // emp2={dept12=Department{name='dept12', employees=[]}}
            // }

            // (IMP) groupingBy returns Collector, so it means that one groupingBy can take another groupingBy as a second parameter.
            // All the methods of Collectors utility class returns Collector.
            Map<String, Map<String, List<Department>>> empNameVsMapOfDeptNameAndListOfDepts = employees.stream().collect(
                    Collectors.groupingBy(
                            employee -> employee.getName(),
                            Collectors.groupingBy( // groupingBy inside groupingBy to create Map<..., Map<..., List<...>>>
                                    employee -> employee.getDepartment().getName(),
                                    Collectors.mapping( // mapping inside groupingBy
                                            employee -> employee.getDepartment(),
                                            Collectors.toList()
                                    )
                            )
                    )
            );

            System.out.println(empNameVsMapOfDeptNameAndListOfDepts);
            // {
            // emp2={dept12=[Department{name='dept12', employees=[]}]},
            // emp1={dept11=[Department{name='dept11', employees=[]}],
            //       dept13=[Department{name='dept13', employees=[]}]}
            // }
        }

        System.out.println();

        // anyMatch, allmatch
        {
            List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
            final boolean anyMatch = numbers.stream().anyMatch(n -> n > 5);
            System.out.println("anyMatch: " + anyMatch); // anyMatch: true
        }

        System.out.println();

        // distinct, findAny, Optional
        {
            List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
            final Optional<Integer> any = numbers.stream().distinct().findAny();
            any.ifPresent(System.out::println); // 3
        }

        System.out.println();

        // peek - peek takes Consumer functional interface as an argument. peek returns stream itself after applying the action passed as consumer object.
        {
            System.out.println("Testing stream.peek()");
            List<Integer> list = Arrays.asList(10, 11, 12);
            list.stream().peek(i -> System.out.println(i * i)).collect(Collectors.toList());
        }

        System.out.println();

        // skip - skips number of elements and returns a stream
        // skip and limit can be used for pagination
        {
            System.out.println("Testing stream.skip()");
            List<Integer> list = Arrays.asList(10, 11, 12);
            list.stream().skip(1).peek(i -> System.out.println(i * i)).collect(Collectors.toList());
        }

        System.out.println();

        // limit - limits number of elements
        {
            System.out.println("Testing stream.limit()");
            List<Integer> list = Arrays.asList(10, 11, 12);
            List<Integer> collect = list.stream().skip(1).limit(1).peek(i -> System.out.println(i * i)).collect(Collectors.toList());
        }

        System.out.println();

        // reduce, Optional
        {
            System.out.println("Testing stream.reduce(), stream.collect(Collectors.reducing), stream.collect(Collectors.minBy)");
            List<Integer> numbers = Arrays.asList();
            final Optional<Integer> reducedOption = numbers.stream().reduce((x, y) -> x + y);
            if (reducedOption.isPresent()) { // Optional saves a client from null check
                System.out.println(reducedOption.get()); // nothing will be printed
            }
            // OR
            reducedOption.ifPresent(System.out::println);// nothing will be printed

            // In this case reduce and collect will give the same result, but if you try passing collection as identity and try to manipulate it, it will give different result
            numbers = Arrays.asList(1, 10, 11, 2, 3, 12, 15, 4, 5, 6, 7, 13, 14, 8, 9);
            System.out.println(numbers.stream().reduce(1, (x, y) -> x + y)); // 121
            System.out.println(numbers.stream().parallel().reduce(1, (x, y) -> x + y)); // 135 --- wrong

            Integer[] result = numbers.stream()
                    .parallel()
                    .collect(
                            () -> new Integer[]{1}, // choosing an identity value is very important.
                            (identityNumber, n) -> identityNumber[0] = Integer.sum(Integer.parseInt("" + identityNumber[0]), Integer.parseInt("" + n)),
                            (sum1, sum2) -> sum1[0] = Integer.sum(Integer.parseInt("" + sum1[0]), Integer.parseInt("" + sum2[0]))
                    );
            System.out.println(result[0]);// with parallelism 135, sequential 121 (just like reduce)


            System.out.println("Find sum: " + numbers.stream().mapToInt((n) -> n * n).reduce(0, (n1, n2) -> n1 + n2)); // 1240
            // OR
            System.out.println("Find sum: " + numbers.stream().mapToInt((n) -> n * n).sum()); // sum(), min(), max() are variant of reduce() only

            // if numbers is empty, there should not be any min value. In that case it returns OptionalInt with isPresent=false and value=0
            final OptionalInt min = numbers.stream().mapToInt(n -> n).parallel().min();
            System.out.println("Find min using stream.min(): " + min.getAsInt());

            // Optional as many other usages as explained before.
            // see Department class' addEmployees method or Java8StreamWithComplexObject class

            // Collectors.reducing, Collectors.minBy, Collectors.maxBy
            numbers.stream().collect(Collectors.reducing(BinaryOperator.minBy((n1, n2) -> n1.compareTo(n2)))); // It internally creates Collector only, which accepts identity element as Supplier that helps during parallel processing. As you know there is a difference between stream.collect(Collectors.reducing(...) and stream.reduce(...) methods when parallel processing happens.
            // or
            Optional<Integer> result1 = numbers.stream().parallel().collect(Collectors.minBy((n1, n2) -> n1.compareTo(n2)));// internally uses reducing method only.
            System.out.println("Find min using stream.collect(Collectors.minBy): " + result1.get());

            // chaining
            BinaryOperator<Integer> integerBinaryOperator = BinaryOperator.maxBy((Integer n1, Integer n2) -> n1.compareTo(n1));
            Integer result11 = integerBinaryOperator.apply(1, 2);

            Function<Integer, Integer> op1 = (n) -> n * 2;
            Function<Integer, String> op2 = (n) -> String.valueOf(n);
            Function<Integer, String> op3 = op1.andThen(op2);

            numbers.stream().map(op3).collect(Collectors.toList());

        }

        System.out.println();

        // iterate and generate methods for infinite streams
        {
            System.out.println("Testing Stream.iterate method");
            Stream<Integer> stream = Stream.iterate(0, n -> n + 2).limit(10);
            stream.forEach((n) -> System.out.print(n + " "));// 0 2 4 6 8 10 12 14 16 18

            System.out.println();

            System.out.println("Testing Stream.generate method");
            Stream<Double> streamOfRandomNums = Stream.generate(() -> Math.random()).limit(5);
            streamOfRandomNums.forEach((n) -> System.out.print(n + " "));// 0.44385780230094174 0.5167183835872096 0.1302166216099525 0.6071855999084184 0.3232604363190975
        }

        System.out.println();

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
                e1.setSalary(100000F);

                Employee e2 = new Employee();
                e2.setName("Miral");
                e1.setSalary(200000F);

                Employee e3 = new Employee();
                e3.setName("Srikant");
                e1.setSalary(500000F);

                Department d1 = new Department();
                d1.setName("DMG");
                d1.addEmployees(e1, e2, e3, null);
                //System.out.println(d1.getEmployees());

                System.out.println("Testing Collectors' summing* method");
                // System.out.println(d1.getEmployees().stream().collect(Collectors.summingDouble(Employee::getSalary)));
            }

        }

        System.out.println();

        // Custom class experiment
        {
            List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
            MyCollectorClass<List<Integer>, Integer> myCollectorClass = new MyCollectorClass<>(ArrayList::new, numbers);
            System.out.println(myCollectorClass.addSourceListToSupplierList());// [3, 2, 2, 3, 7, 3, 5]
        }

        System.out.println();

        // Spliterator
        // https://blog.logentries.com/2015/10/java-8-introduction-to-parallelism-and-spliterator/
        {
            System.out.println("Testing Spliterator...");

            /*Collection<Consumer<String>> consumers = new ArrayList<>();
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


            final Spliterator<Consumer<String>> consumerSpliterator1 = consumers.firstSplit();
            System.out.println("ConsumerSpliterator1's characteristics: " + consumerSpliterator1.characteristics());
            System.out.println("ConsumerSpliterator1's estimateSize: " + consumerSpliterator1.estimateSize());
            System.out.println("ConsumerSpliterator1's exactSizeIfKnown: " + consumerSpliterator1.getExactSizeIfKnown());*/
            /*
            ConsumerSpliterator1's characteristics: 16464
            ConsumerSpliterator1's estimateSize: 4
            ConsumerSpliterator1's exactSizeIfKnown: 4
             */

           /* Spliterator<Consumer<String>> consumerSpliterator11;
            while ((consumerSpliterator11 = consumerSpliterator1.trySplit()) != null) {
                System.out.println("After splitting a collection...");
                System.out.println("ConsumerSpliterator11's characteristics: " + consumerSpliterator11.characteristics());
                System.out.println("ConsumerSpliterator11's estimateSize: " + consumerSpliterator11.estimateSize());
                System.out.println("ConsumerSpliterator11's exactSizeIfKnown: " + consumerSpliterator11.getExactSizeIfKnown());


                consumerSpliterator11.forEachRemaining(consumer -> consumer.accept("Tushar"));
            }
            consumerSpliterator1.forEachRemaining(consumer -> consumer.accept("Tushar"));*/

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


            Collection<String> names = new ArrayList<>();
            names.add("a");
            names.add("b");
            names.add("c");
            names.add("d");
            names.add("m");
            names.add("n");
            names.add("o");
            names.add("p");
            names.add("q");
            names.add("e");
            names.add("f");
            names.add("g");
            names.add("h");
            names.add("i");
            names.add("j");
            names.add(null);
            names.add("k");
            names.add("l");

            Spliterator<String> firstSplit = names.spliterator();

            /*
                Collection has 17 elements.

                when you say
                    Spliterator<String> firstSplit = names.sortedSetSpliteratorOfUknownSize();
                firstSplit will have all 17 elements

                when you say
                    Spliterator<String> secondSplit = firstSplit.trySplit()
                secondSplit have 0 to 7th elements and firstSplit will have remaining half.

                when you say
                    Spliterator<String> thirdSplit = firstSplit.trySplit()
                thirdSplit will have 8th to 11th elements and firstSplit will have remaining elements.

                when you say
                    Spliterator<String> fourthSplit = firstSplit.trySplit()
                fourthSplit will have 12th to 13th elements and firstSplit will have remaining elements.

                when you say
                    Spliterator<String> fifthSplit = firstSplit.trySplit()
                fifthSplit will have 14th element and firstSplit will have remaining elements.

                when you say
                    Spliterator<String> sixthSplit = firstSplit.trySplit()
                sixthSplit will have 15th element and firstSplit will have remaining elements.


                At this time, firstSplit will have 16th element

             */

            if (firstSplit != null) {

                List<Spliterator<String>> result = new ArrayList<>();

                splitRecursively(firstSplit, firstSplit, result);
                result.add(firstSplit);

                for (int i = 0; i < result.size(); i++) {
                    Spliterator<String> split = result.get(i);
                    System.out.print(i + "th split elements: ");
                    split.forEachRemaining((name) -> System.out.print(name + ","));

                    //System.out.println();
                    //System.out.print("tryAdvance: ");
                    // If there is any element remained, then perform the action on it.
                    // It performs the action only on the next one remaining element.
                    // If there is no element remained, then it returns false.
                    //split.tryAdvance((name) -> System.out.print(name + ","));

                    System.out.println();
                }


                System.out.println("These splits should be splitted further and finally all splits with expected number of elements can be given for parallel processing using ExecutorService. Each thread of ExecutorService will call split.forEachRemaining method");
            }


            SortedSet<String> namesSet = new TreeSet<>((str1, str2) -> str1.compareTo(str2));
            namesSet.add("asfsdf");
            namesSet.add("sadfsdfb");
            namesSet.add("csdf");
            namesSet.add("dsafsd");
            namesSet.add("dsfsdfm");
            namesSet.add("nsdfasdf");
            namesSet.add("odsfasdf");
            namesSet.add("adsfasdp");
            namesSet.add("sadfsaq");
            namesSet.add("dasfase");
            namesSet.add("dsfasfdf");
            namesSet.add("fdasdg");
            namesSet.add("hdfsdfas");
            namesSet.add("ifdafd");
            namesSet.add("jasfd");
            namesSet.add("ksdfasfs");
            namesSet.add("dasfafl");

            System.out.println(namesSet);
            Iterator<String> sourceIterator = namesSet.iterator();
/*
            Stream<String> targetStream = StreamSupport.stream(
                    Spliterators.spliteratorUnknownSize(sourceIterator, Spliterator.IMMUTABLE | Spliterator.NONNULL |
                            Spliterator.DISTINCT | Spliterator.ORDERED | Spliterator.SORTED |
                            Spliterator.SIZED | Spliterator.SUBSIZED), true);
*/


            Spliterator<String> listSpliterator = names.spliterator(); // List has Ordered and Sized elements. Iterators are by default not-sized. HashSet has unordered elements. SortedSet has Ordered and Sorted Elements.
            System.out.println("Characteristics: " + listSpliterator.characteristics());// 16464
            System.out.println(listSpliterator.hasCharacteristics(Spliterator.DISTINCT));// false
            System.out.println(listSpliterator.hasCharacteristics(Spliterator.SORTED));// false
            System.out.println(listSpliterator.hasCharacteristics(Spliterator.ORDERED));// true
            System.out.println(listSpliterator.hasCharacteristics(Spliterator.NONNULL));// false
            System.out.println(listSpliterator.hasCharacteristics(Spliterator.IMMUTABLE));// true
            System.out.println(listSpliterator.hasCharacteristics(Spliterator.SIZED)); // true
            System.out.println(listSpliterator.hasCharacteristics(Spliterator.SUBSIZED)); // true
            System.out.println(listSpliterator.hasCharacteristics(Spliterator.CONCURRENT)); // false


            Spliterator<String> sortedSetSpliterator = namesSet.spliterator();

            System.out.println("Characteristics: " + sortedSetSpliterator.characteristics());// 85
            System.out.println(sortedSetSpliterator.hasCharacteristics(Spliterator.DISTINCT));// true   ---- SortedSet has distinct,sorted,ordered,nonnull,sized values.
            System.out.println(sortedSetSpliterator.hasCharacteristics(Spliterator.SORTED));// true
            System.out.println(sortedSetSpliterator.hasCharacteristics(Spliterator.ORDERED));// true
            System.out.println(sortedSetSpliterator.hasCharacteristics(Spliterator.NONNULL));// true
            System.out.println(sortedSetSpliterator.hasCharacteristics(Spliterator.IMMUTABLE));// false
            System.out.println(sortedSetSpliterator.hasCharacteristics(Spliterator.SIZED)); // true
            System.out.println(sortedSetSpliterator.hasCharacteristics(Spliterator.SUBSIZED)); // false
            System.out.println(sortedSetSpliterator.hasCharacteristics(Spliterator.CONCURRENT)); // false
            /*
            Every Collection and Map classes have method called characteristics(). Each one has different characteristics.

            public int characteristics() {
                return Spliterator.DISTINCT | Spliterator.CONCURRENT | Spliterator.NONNULL;
            }

             */


            // http://www.logicbig.com/how-to/code-snippets/jcode-java-spliterator/
            // you can retrieve a Comparator from Spliterator, if you have provided it while creating a collection. Here while creating SortedSet, you provided a Comparator.
            Comparator<? super String> comparator = sortedSetSpliterator.getComparator();
            System.out.println("comparator: " + comparator);// comparator: java8.functionalprograming.Java8SteamsExample$$Lambda$126/183264084@1c655221

            Stream<String> targetStream = StreamSupport.stream(sortedSetSpliterator, true);

            List<String> result = targetStream.collect(Collectors.toList());
            System.out.println(result);


            // When you create a Spliterator from and Iterator of some collection, you need to tell java its characteristics.

            Iterator<String> sortedSetIterator = namesSet.iterator();
            Spliterator<String> sortedSetSpliteratorOfUknownSize = Spliterators.spliteratorUnknownSize(sortedSetIterator, Spliterator.IMMUTABLE); // immutable and not sized. When size is not provided, estimated size is Long.MAX_VALUE.

            System.out.println("Characteristics: " + sortedSetSpliteratorOfUknownSize.characteristics());// 1024
            System.out.println(sortedSetSpliteratorOfUknownSize.hasCharacteristics(Spliterator.DISTINCT));// false
            System.out.println(sortedSetSpliteratorOfUknownSize.hasCharacteristics(Spliterator.SORTED));// false
            System.out.println(sortedSetSpliteratorOfUknownSize.hasCharacteristics(Spliterator.ORDERED));// false
            System.out.println(sortedSetSpliteratorOfUknownSize.hasCharacteristics(Spliterator.NONNULL));// false
            System.out.println(sortedSetSpliteratorOfUknownSize.hasCharacteristics(Spliterator.IMMUTABLE));// true
            System.out.println(sortedSetSpliteratorOfUknownSize.hasCharacteristics(Spliterator.SIZED)); // false
            System.out.println(sortedSetSpliteratorOfUknownSize.hasCharacteristics(Spliterator.SUBSIZED)); // false
            System.out.println(sortedSetSpliteratorOfUknownSize.hasCharacteristics(Spliterator.CONCURRENT)); // false

            Spliterator<String> sizedSortedSetSpliterator = Spliterators.spliterator(sortedSetIterator, namesSet.size(), Spliterator.IMMUTABLE | Spliterator.ORDERED); // sized, immutable, ordered
            System.out.println("Characteristics: " + sizedSortedSetSpliterator.characteristics());// 17488
            System.out.println(sizedSortedSetSpliterator.hasCharacteristics(Spliterator.DISTINCT));// false
            System.out.println(sizedSortedSetSpliterator.hasCharacteristics(Spliterator.SORTED));// false
            System.out.println(sizedSortedSetSpliterator.hasCharacteristics(Spliterator.ORDERED));// true
            System.out.println(sizedSortedSetSpliterator.hasCharacteristics(Spliterator.NONNULL));// false
            System.out.println(sizedSortedSetSpliterator.hasCharacteristics(Spliterator.IMMUTABLE));// true
            System.out.println(sizedSortedSetSpliterator.hasCharacteristics(Spliterator.SIZED)); // true
            System.out.println(sizedSortedSetSpliterator.hasCharacteristics(Spliterator.SUBSIZED)); // false
            System.out.println(sizedSortedSetSpliterator.hasCharacteristics(Spliterator.CONCURRENT)); // false


        }

    }

    /*
     http://www.deadcoderising.com/java8-writing-asynchronous-code-with-completablefuture/
     http://www.nurkiewicz.com/2013/05/java-8-definitive-guide-to.html

     Unlike to Future, you can use CompletableFuture
     - without setting 'Callable' to it (You don't need Callable anymore)
     - with subsequent processes to be applied on completed value(result of run task), once it is available. It let's you use Reactive design pattern (Once result is available, act on it).
       whenComplete...(...), handle(...), then...(...), exceptionally(...) etc methods are there to react on available result (completed value).

     Instead of waiting for the thread to complete using future.get() method, with CompletableFuture, I can use then...(...) method which accepts a code that will be executed right after the result is available in the CompletableFuture.
     This is called Reactive design, where result is pushed and then you react to it instead of you keep polling the result using future.get() method.

        Future<T>           CompletionStage<T>
            |                       |
            -------------------------
                        |

                CompletableFuture<T>

     Methods of Future

        boolean isDone()
        boolean isCancelled()
        boolean cancel(boolean mayInterruptIfRunning)
        V get() throws InterruptedException, ExecutionException  ----- blocking method. CompletionStage provides many methods that can run without blocking and they react when result (completed value) is available in CompletedFuture.
        V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException

     Some static methods in CompletableFuture:

        -   CompletableFuture<U> supplyAsync(Supplier<U> supplier)  OR  supplyAsync(Supplier<U> supplier, Executor executor)

            Code supplied by a supplier will be execute in thread pool (under separate thread (Runnable))

            This Runnable has access to CompletableFuture where it can put the result using 'completableFuture.completeValue(f.get());'
            Once the result is available in this Runnable, it calls completableFuture.postComplete(). In postComplete(), it will read the the tasks assigned using then...(...) methods.

        -   CompletableFuture<Void> runAsync(Runnable runnable)   OR   runAsync(Runnable runnable, Executor executor)

            If you have an instance of Runnable, you can use CompletableFuture.runAsync(Runnable)

        For both of the above methods:
        (IMP) By default, these methods use ExecutorService of type ForkJoinPool and uses it common thread pool which is shared by all JVM tasks and parallel streams. If you want to pass your own ExecutorService, you can do that too.
        (IMP) If asynchronously executed code throws an exception, it is wrapped by AltResult(exception) and kept it as a result of returned CompletableFuture.

        -   CompletableFuture<Object> anyOf(CompletableFuture<?>... cfs)

            anyOf() on the other hand will wait only for the fastest of the underlying futures.

        -   CompletableFuture<Void> allOf(CompletableFuture<?>... cfs)

            allOf() takes an array of futures and returns a future that completes when all of the underlying futures are completed

     Methods of CompletionStage

          boolean               complete(T value)
          boolean               completeExceptionally(Throwable)

          CompletionStage<T>    whenComplete(BiConsumer<T,Throwable>)                               --- it is called right after result (completed value) is available in the CompletableFuture
          CompletionStage<T>    exceptionally(Function<Throwable,T>)                                --- Do not call get() method on future. If future has an AltResult(Exception) as a result, then get() will throw an exception. It's a blocking method also. So you should try to avoid it. Instead use whenComplete/handle/exceptionally methods. exceptionally method has an effect if the result is an exception.
          CompletionStage<U>    handle(BiFunction<T,Throwable,U>)                                   --- It is very important to understand the difference between whenComplete and handle methods. It is described below

          CompletionStage<U>    thenApply(Function<T,U>)                                            --- f1.thenApply(...) is to set some new result in returned Future (works like a map function). callback provided to thenApply is executed only if f1 has the result completed normally (result is not an exception).
          CompletionStage<U>    thenCompose(Function<T, CompletionStage<U>> fn)                     --- thenApply is like a map (converting one result value to another), thenCompose is like a flatMap.
          CompletionStage<Void> thenAccept(Consumer)                                                --- f1.thenAccept(...) is to modify the result of current Future, use thenAccept that takes Consumer as an argument. If f1 is completed normally (result is not an exception), then only callback provided to thenAccept will be executed.

          CompletionStage<V>    thenCombine(CompletionStage<U> other, BiFunction<T,U,V> fn)         --- f1.thenCombine(f2, ...) If both futures are completed normally (result is not an exception), then only callback provided to thenCombine will be executed.

          CompletionStage<Void> acceptEither   (CompletionStage<T> other, Consumer<T> action)       ---
                                (IMP)
                                In f1.acceptEither(f2,...), as far as f1 is completed normally, action will taken on either f1 or f2 result
                                But if f1 is completed with exception, then even though f2 is completed normally, it won't run an action on f2's result.
                                If f1 is not yet completed (expected to be completed with exception), but if f2 is completed normally, then action will be taken on f2's result.

          CompletionStage<Void> runAfterEither (CompletionStage<?> other, Runnable action)          --- read acceptEither

          CompletionStage<U>    applyToEither  (CompletionStage<T> other, Function<T, U> fn)        --- read acceptEither

          CompletionStage<Void> thenAcceptBoth(CompletionStage<U> other, BiConsumer<T, U> action)   --- f1.thenAcceptBoth(f2, ...) If both f1 and f2 are completed normally (result is not an exception), then run the action.
          CompletionStage<Void> runAfterBoth   (CompletionStage<?> other, Runnable action)          --- read thenAcceptBoth


         methods.
         All these methods are chaining methods, they return a new CompletionStage.
         Most of them has Async version also.

     Lambda passed to future....Async(...) method takes future's result as an input parameter.
     If you use ...Async(...) method, it will create a task in the thread pool.


     */
    @SuppressWarnings("Duplicates")
    protected static void completableFutureExamples() throws InterruptedException, ExecutionException {
        // Example of supplyAsync, complete, whenComplete, thenApply, thenAccept, get methods.
        {
            List<String> incompleteFutureResult = new ArrayList<>();
            incompleteFutureResult.add("xyz");

            CompletableFuture<List<String>> future = CompletableFuture.supplyAsync(() -> returnSomethingAfterSometime());
            // CompletableFuture.complete() can only be called once, subsequent invocations are ignored. But there is a back-door called CompletableFuture.obtrudeValue(...) which overrides previous value of the Future with new one. Use with caution.
            boolean complete = future.complete(Arrays.asList("x", "y", "z")); // complete the task with the provided value, if it is not already completed.
            System.out.println("complete method example result: " + complete);// if true, it means that value that you provided was set, otherwise future was already completed (result was available) with the task that you provided to run.

            Thread.sleep(1000);

            // whenComplete is called when the value of the Future is set using CompletableFuture's completeValue method either because task was finished and its result was set or you set the result using complete method.
            future.whenComplete((list, ex) -> System.out.println("whenComplete method example result: " + list)); // [x, y, z]

            // thenApply(...) method is called once completed value is available and after whenComplete is executed.
            CompletableFuture<List<String>> newFuture = future.thenApply(list -> {
                sleep(1000);
                List<String> newList = new ArrayList<>(list);
                newList.add("newXyz");
                return newList;
            });

            // When you need to map the result to some other result instance (may be of some other type), use thenApply(Function)
            // When you need to just modify the result, use thenAccept(Consumer)
            CompletableFuture<Void> newNewFuture = newFuture.thenAccept(list -> list.add("newNewXyz"));

            // get() is a blocking method.
            System.out.println("thenApply, thenAccept method example result: " + newFuture.get()); // [x, y, z, newXyz, newNewXyz]
        }

        // Example of completedExceptionally
        {
            CompletableFuture<List<String>> future = CompletableFuture.supplyAsync(() -> returnSomethingAfterSometime());

            // future's result will be set to 'new AltResult(exception)'
            boolean completeExceptionally = future.completeExceptionally(new RuntimeException("sorry, completing exceptionally"));
            System.out.println("completeExceptionally method result: " + completeExceptionally);
            System.out.println();

            /*
             future.whenComplete(BiConsumer)
             Internally, newFuture will be created an its uniWhenComplete(future,...) method will be called with current future as a input to it.

             From 'future', result will be retrieved and passed to lambda (list, ex) ->....

             If future's result is AltResult (AltResult contains Exception), which is the case in our below example,
             (IMP) Any changes done to list or ex during the execution of BiConsumer will be ignored. An ex instance wrapped with a new CompletionException instance (new AltResult(new CompletionException(ex))) will be set a newFuture's result.
             (IMP) If you want different behaviour, you need to use future.handle(BiFunction)

             If future's result is not an exception, it's some list,
             then modification to the list is considered and that modified list is set to newFuture.
            */


            CompletableFuture<List<String>> newFuture = future.whenComplete((list, ex) -> {
                if (ex != null) {
                    System.out.println("Exception is not null, error message: " + ex.getMessage());

                    list = new ArrayList<>(); // this has no effect
                    list.add("defaultXyz");
                    //ex = null; // this has no effect
                } else {
                    if (list != null) System.out.println("List is not null: " + list);
                }
            });

            // newFuture will also have same list and ex instances.
            CompletableFuture<List<String>> newNewFuture = newFuture.whenComplete((list, ex) -> {
                if (list != null) { // list will be null only
                    list.add("newNewXyz");
                }
            });


            // newNewFuture will have result set as AltResult(CompletionException(RuntimeException))
            // So, you cannot do get() on it. Use exceptionally(...) to avoid throwing exception because of invoking get().
            //System.out.println("Final Result 2: " + newNewFuture.get());

            // If newNewFuture has Result set as an AltResult(ex), then passed function will be executed and its result will be set as a result of finalFuture
            // If newNewFuture has Result set as List, then passed function will not be executed, and list will be set as a result of finalFuture.
            CompletableFuture<List<String>> finalFuture = newNewFuture.exceptionally(ex -> {
                List<String> defaultList = new ArrayList<>();
                defaultList.add("defaultValue");
                return defaultList;
            });
            System.out.println("exceptionally method example result: " + finalFuture.get());// [defaultValue]
        }

        /*
         Example of handle method

         Difference between whenComplete and handle methods:

         Both returns new CompletableFuture.
         "newFuture = future.whenComplete(BiConsumer)" will not take the changes in current future's result take into the consideration while setting the result of newFuture.
         "newFuture = future.handle(BiFunction)" will set BiFunction's returned value as a result of newFuture.

         newFuture = future.whenComplete(BiConsumer):

             BiConsumer contains list and exception.

             when whenComplete is executed, it sees whether future has AltResult(exception) set as a result.
             If yes,
                 it calls biConsumer.accept(null, ex). In your passed lambda, you can do whatever you want with ex.
                 After that, it calls compareAndSwapObject(this, RESULT, null, r)
                                      compareAndSwapObject(future, current result obj, expected value of current result, modified result)

                 So, even though, in your lambda,
                    If you set exception=null, result value won't be swapped.
                    If not set to null, then it will be wrapped by CompletionException and AltResult(CompletionException) will be set to result of a new future.

             If no,
                then if list is set as a result of the future,
                then modification to the list in your lambda will be considered and that modified list is set to newFuture.


         newFuture = future.handle(BiFunction):

            BiFunction contains list and exception + it is expected to return something.
            That returned value will be set as a result in new CompletableFuture.

         */
        {
            CompletableFuture<List<String>> future = CompletableFuture.supplyAsync(() -> returnSomethingAfterSometime());

            boolean completeExceptionally = future.completeExceptionally(new RuntimeException("sorry, completing exceptionally"));
            //System.out.println("completeExceptionally method result: " + completeExceptionally);

            CompletableFuture<List<String>> newFuture = future.handle((list, ex) -> {
                List<String> newList = new ArrayList<>();
                if (ex != null) {
                    newList.add("default value");
                }
                if (list != null) {
                    newList.addAll(list);
                    newList.add("newXyz");
                }
                return newList;
            });

            newFuture.handle((list, ex) -> {
                if (list != null) {
                    System.out.println("handle method example result: " + list);// [default value]

                }
                if (ex != null) {
                    System.out.println("why ex is not null ???");
                }
                return list;
            });

        }

        /*
            Example of thenCompose method

              thenApply works like a map, thenCompose works like flatMap

              CompletionStage<U> thenApply(Function<T,U>) --- to set something new result in returned Future, use thenApply that takes Function as an argument.
              CompletionStage<Void> thenAccept(Consumer) --- to modify the result of current Future, use thenAccept that takes Consumer as an argument.
              CompletionStage<U> thenCompose(Function<T, CompletionStage<U>> fn) --- thenApply is like a map (converting one result value to another), thenCompose is like a flatMap.

         */
        {
            CompletableFuture<List<String>> future = CompletableFuture.supplyAsync(() -> returnSomething());

           /*
           If you use thenApply (like map function) that returns a Future, you will end up like below
           CompletableFuture<CompletableFuture<List<String>>> newFutureUsingThenApply =
                future.thenApply((resultOfFuture) -> {
                    List<String> anotherList = new ArrayList<>();
                    anotherList.addAll(resultOfFuture1);
                    anotherList.add("x");
                    return CompletableFuture.completedFuture(anotherList);
                });

           So, you need to use thenCompose (like flatMap)

            */

            CompletableFuture<List<String>> newFuture = future.thenCompose((resultOfFuture) -> {
                List<String> anotherList = new ArrayList<>();
                anotherList.addAll(resultOfFuture);
                anotherList.add("x");
                return CompletableFuture.completedFuture(anotherList); // result of this returned future will be set as a result of newFuture
            });
            CompletableFuture<Void> voidCompletableFuture = newFuture.thenAccept(list -> System.out.println("thenCompose method example result: " + list)); // [a, b, c, x]
        }

        /*
            Example of thenCombine method

            CompletableFuture<V> thenCombine(CompletionStage<U> other, BiFunction<T,U,V> fn)
         */
        {
            CompletableFuture<List<String>> future1 = CompletableFuture.supplyAsync(() -> returnSomething());
            CompletableFuture<List<String>> future2 = CompletableFuture.supplyAsync(() -> returnSomething());
            CompletableFuture<List<String>> future3 = CompletableFuture.supplyAsync(() -> {throw new RuntimeException("some error");});

            CompletableFuture<List<String>> newFuture = future1.thenCombine(future2, (future1Result, future2Result) -> {
                List<String> combinedList = new ArrayList<>();
                combinedList.addAll(future1Result);
                combinedList.addAll(future2Result);
                return combinedList;
            });

            newFuture.thenAccept(combinedList -> System.out.println("thenCombine method example result: " + combinedList)); // [a, b, c, a, b, c]

            // if both futures are completed normally, then only callback provided by thenCombine will be executed
            CompletableFuture<List<String>> newFuture1 = future2.thenCombine(future3, (future2Result, future3Result) -> {
                List<String> combinedList = new ArrayList<>();
                combinedList.addAll(future2Result);
                combinedList.addAll(future3Result);
                return combinedList;
            });
            newFuture1.thenAccept(combinedList -> System.out.println("thenCombine method example result: " + combinedList)); // no result
        }

        // Example of acceptEither, thenAcceptBoth, applyToEither
        {
            // acceptEither
            {
                CompletableFuture<List<String>> future1 = CompletableFuture.supplyAsync(() -> returnSomethingAfterSometime());
                CompletableFuture<List<String>> future2 = CompletableFuture.supplyAsync(() -> returnSomething());
                CompletableFuture<List<String>> future3 = CompletableFuture.supplyAsync(() -> {throw new RuntimeException("some error");});
                CompletableFuture<List<String>> future4 = CompletableFuture.supplyAsync(() -> {sleep(1000); throw new RuntimeException("some error");});

                // acceptEither
                future1.acceptEither(future2, future1OrFuture2Result -> System.out.println("acceptEither method example - 1: " + future1OrFuture2Result));// [a, b, c]
                // In f1.acceptEither(f2,...), as far as f1 is completed normally, it will print f1 or f2 result
                // But if f1 is completed with exception, then even though f2 is completed normally, it won't print f2 result.
                future2.acceptEither(future3, future2OrFuture3Result -> System.out.println("acceptEither method example - 2: " + future2OrFuture3Result)); // [a, b, c]
                future3.acceptEither(future2, future3OrFuture2Result -> System.out.println("acceptEither method example - 3: " + future3OrFuture2Result)); // no result
                // when acceptEither is executed on future4, future4's result is not available yet, but future2's result is available. So, action will be taken on future2's result even though later on future4's result is available as an exception.
                future4.acceptEither(future2, future4OrFuture2Result -> System.out.println("acceptEither method example - 4: " + future4OrFuture2Result)); // [a, b, c]
            }

            // runAfterEither
            {
                CompletableFuture<List<String>> future1 = CompletableFuture.supplyAsync(() -> returnSomethingAfterSometime());
                CompletableFuture<List<String>> future2 = CompletableFuture.supplyAsync(() -> returnSomething());
                CompletableFuture<List<String>> future3 = CompletableFuture.supplyAsync(() -> {
                    throw new RuntimeException("some error");
                });
                CompletableFuture<List<String>> future4 = CompletableFuture.supplyAsync(() -> {
                    sleep(1000);
                    throw new RuntimeException("some error");
                });

                future1.runAfterEither(future2, () -> System.out.println("runAfterEither method example - 1: one of the future1 or future2 has result"));// result will be displayed
                future2.runAfterEither(future3, () -> System.out.println("runAfterEither method example - 2: one of the future2 or future3 has result")); // result will be displayed
                future3.runAfterEither(future2, () -> System.out.println("runAfterEither method example - 3: one of the future3 or future2 has result")); // no result
                future4.runAfterEither(future2, () -> System.out.println("runAfterEither method example - 4: one of the future4 or future2 has result")); // result will be displayed
            }

            // thenAcceptBoth
            {
                CompletableFuture<List<String>> future1 = CompletableFuture.supplyAsync(() -> returnSomethingAfterSometime());
                CompletableFuture<List<String>> future2 = CompletableFuture.supplyAsync(() -> returnSomething());

                CompletableFuture<Void> finalFuture = future1.thenAcceptBoth(future2, (future1Result, future2Result) -> future1Result.addAll(future2Result));
                sleep(2000);
                finalFuture.thenAccept(result -> future1.thenAccept(future1Result -> System.out.println("thenAcceptBoth method example: " + future1Result))); // [a, b, c, a, b, c]
            }

            // applyToEither
            {
                CompletableFuture<List<String>> future1 = CompletableFuture.supplyAsync(() -> returnSomethingAfterSometime());
                CompletableFuture<List<String>> future2 = CompletableFuture.supplyAsync(() -> returnSomething());
                CompletableFuture<List<String>> future3 = CompletableFuture.supplyAsync(() -> {throw new RuntimeException("some error");});
                CompletableFuture<List<String>> future4 = CompletableFuture.supplyAsync(() -> {sleep(1000); throw new RuntimeException("some error");});

                future1.applyToEither(future2, (future1OrFuture2Result) -> {
                    List<String> newResult = new ArrayList<>();
                    newResult.addAll(future1OrFuture2Result);
                    newResult.add("x");
                    return newResult;
                }).thenAccept(result -> System.out.println("applyToEither method example - 1: " + result)); // [a ,b ,c, x]

                future2.applyToEither(future3, (future2OrFuture3Result) -> {
                    List<String> newResult = new ArrayList<>();
                    newResult.addAll(future2OrFuture3Result);
                    newResult.add("x");
                    return newResult;
                }).thenAccept(result -> System.out.println("applyToEither method example - 2: " + result)); // [a ,b ,c, x]

                future3.applyToEither(future2, (future3OrFuture2Result) -> {
                    List<String> newResult = new ArrayList<>();
                    newResult.addAll(future3OrFuture2Result);
                    newResult.add("x");
                    return newResult;
                }).thenAccept(result -> System.out.println("applyToEither method example - 3: " + result)); // no result

                future4.applyToEither(future2, (future4OrFuture2Result) -> {
                    List<String> newResult = new ArrayList<>();
                    newResult.addAll(future4OrFuture2Result);
                    newResult.add("x");
                    return newResult;
                }).thenAccept(result -> System.out.println("applyToEither method example - 4: " + result)); // [a ,b ,c, x]
            }

            // anyOf, allOf
            {
                CompletableFuture<List<String>> future1 = CompletableFuture.supplyAsync(() -> returnSomethingAfterSometime());
                CompletableFuture<List<String>> future2 = CompletableFuture.supplyAsync(() -> returnSomething());
                CompletableFuture<List<String>> future3 = CompletableFuture.supplyAsync(() -> {throw new RuntimeException("some error");});
                CompletableFuture<List<String>> future4 = CompletableFuture.supplyAsync(() -> {sleep(1000); throw new RuntimeException("some error");});

                // anyof
                CompletableFuture<Object> anyFuture1 = CompletableFuture.anyOf(future1, future2, future3, future4);
                anyFuture1.whenComplete((result, ex) -> {
                    if(ex != null) {
                        System.out.println("anyOf method example - 1: "+ex.getMessage());
                    } else {
                        if(result != null) {
                            System.out.println("anyOf method example - 1: "+result);// this will be displayed  [a, b, c]
                        }
                    }
                });


                // here future3 will be completed first, but it has an exception set as a result, so anyFuture2 won't have any result set
                CompletableFuture<Object> anyFuture2 = CompletableFuture.anyOf(future3, future1, future2, future4);
                anyFuture2.whenComplete((result, ex) -> {
                    if(ex != null) {
                        System.out.println("anyOf method example - 2: "+ex.getMessage()); // this will be displayed
                    } else {
                        if(result != null) {
                            System.out.println("anyOf method example - 2: "+result);
                        }
                    }
                });
                //anyFuture2.thenAccept(result -> System.out.println("anyOf method example - 2: " + result)); // anyFuture has an exception set as a result, action won't be taken on the result.

                // allOf
                sleep(1000); // let future1's task complete
                CompletableFuture<Void> allFuture1 = CompletableFuture.allOf(future1, future2);
                allFuture1.thenAccept(emptyAltResult -> System.out.println("allOf method example - 1: all futures have results at this point")); // action will be executed as all the futures have normally completed result (non-exception result).

                CompletableFuture<Void> allFuture2 = CompletableFuture.allOf(future1, future2, future3);
                allFuture2.thenAccept(result -> System.out.println("allOf method example - 2: all futures have results at this point")); // As future3 has the result set as exception, action won't be executed.

            }
        }
    }

    protected static void mapEnhancementExamples() {
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

            /*
            Compute methods are just like replace method with slight variations.
            If you see carefully, replace method doesn’t care whether existing value of a key is null or non-null, but compute methods does care about it. Compute methods will remove the key, if new computed value is null.
             */

        // Replaces the value of a key "1" to "one-new", if current value matches to "one"
        boolean replaced = map.replace("1", "one", "one-new");

        // If key="1" exists with null/non-null value, then only it replaces its value. It doesn't create a new key "1", if it doesn't exist
        final String newValue = map.replace("1", "one-new");

        // replaceAll simply replaces value of all keys based on the output of passed function
        map.replaceAll((k, v) -> v + "-new-value");
        map.forEach((k, v) -> System.out.format("%s's value is %s\n", k, v));

        // compute is a special function that replaces the value of a key with new value calculated by passed function.
        // it returns a new computed value
        // if new value is null then it removes a key from map
        String value = map.compute("1", (k, v) -> null);
        map.forEach((k, v) -> System.out.format("%s's value is %s\n", k, v));

        // if key doesn't exist or value of the key is null, then insert a key with computed value, if computed value is not null.
        value = map.computeIfAbsent("4", (k) -> k + "-new");
        map.forEach((k, v) -> System.out.format("%s's value is %s\n", k, v));

        // if key exist and its value is not null, then replace that value with new computed value. If computed value is null, then it removes the key.
        value = map.computeIfPresent("3", (k, v) -> v + "-new computed value");
        map.forEach((k, v) -> System.out.format("%s's value is %s\n", k, v));

        // add key-value to a map, if key doesn't exist in the map, if key exist then do nothing
        map.putIfAbsent("5", "five");
        map.forEach((k, v) -> System.out.format("%s's value is %s\n", k, v));
    }

    protected static void sleep(int milliSeconds) {
        try {
            Thread.sleep(milliSeconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void splitRecursively(Spliterator<String> firstSpliterator, Spliterator<String> newSpliterator, List<Spliterator<String>> result) {
        if (newSpliterator == null) return;

        //newSpliterator.forEachRemaining((name) -> System.out.print(name+","));

        Spliterator<String> anotherSplitter = firstSpliterator.trySplit();
        if (anotherSplitter != null) {
            result.add(anotherSplitter);
        }

        splitRecursively(firstSpliterator, anotherSplitter, result);

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
