package java8.functionalprograming;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author Tushar Chokshi @ 7/3/16.
 */

/*
  Functional_Programming_V9_MEAP.pdf doesn't have all chapters. It has chapters till 8 only. Other chapters, I read from
  Functional_Programming_V11_MEAP.pdf book.


  From Functional_Programming_V9_MEAP.pdf book
  --------------------------------------------

  Chapter 1 - What is Functional Programming?

      How referential transparency makes programs safer? (pg 6)
          Functional programs are also called Referentially Transparent programs or Pure functions.
          Referential transparency, a term commonly used in functional programming, means that given a function and an input value, you will always receive the same output. That is to say there is no external state used in the function.

          What is Pure Function

          1. Given same input, always provides same output
          2. Execution does not cause observable side-effects.

          1 + 2 = Referential Transparency


           - They are SELF-CONTAINED. It means that it should depend only on its arguments. They should not be doing anything using external objects/variables. They should be working only on the arguments that they have been passed.
           (VERY IMP)It means that they are DETERMINISTIC, which means they will always return the same value for the same argument.

           - They will never throw any kind of exception. They might throw errors such as OOME (Out Of Memory Error) or SOE (Stack Overflow Error), but these errors mean that the program has a bug.
           They can catch an exception, but shouldn't throw it. If they want they can return an error message. They shouldn't log that error message also.

           - They will not mutate their argument or some other external data, causing the caller to find itself with stale data, or concurrent access exceptions.

           - They are not doing anything with database, network, filesystem etc resources. They don't even reserve an input from Console.
           Unless, it is intentional, it should not even write anything to console or logs.
           So, they are not affected because external device is down or simply broken.

           e.g.
           1) Assuming that below method is a functional method.
           public static void add(int a, int b) {
                while (b > 0) {
                    a++;
                    b--;
                }
                System.out.println(a);
            }
            It is working only on its arguments. It is not mutating any external object or variable. a and b are passed by value. So, any changes to them will be local only.
            It is interacting with Console using System.out.println, but that's intentional. so it is ok.
            Pass-By-Value in Java - http://stackoverflow.com/questions/40480/is-java-pass-by-reference-or-pass-by-value


            2) (VERY IMP) Is this Functional Programming?

            public static void doWithList(List<Integer> nilList) {
                nilList.add(1);
            }

            or

            Consumer<List<Integer>> consumer = nilList -> nilList.add(1);
            List<Integer> nilList = new ArrayList<>();
            consumer.accept(nilList);

            As explained above, it is mutating the arg passed to it. See carefully it is changing the value inside the object sent by a caller.
            So, it is not functional-style.

            You could have written

            public static List<Integer> doWithList(List<Integer> nilList) {
                List<Integer> anotherList = new ArrayList<>();
                anotherList.addAll(nilList);
                anotherList.add(1);
                return anotherList;
            }

            3) public static int add(int a, int b) {
                log(String.format("Adding %s and %s", a, b));
                while (b > 0) {
                    a++;
                    b--;
                }
                log(String.format("Returning %s", a));
                return a;
            }
            If you see, this method's primary responsibility is to return a value and not logging something. So, logging (interacting with file system) is unintentional here. So, it should not be done in functional programming.

        - Abstraction  (very powerful feature of functional programming) - pg 14
        Look at the reduce method. It takes as its argument an operation and uses it to reduce a nilList into a single value. Here, the operation has two operands of the same type.
        Except for this, it could be any operation. Consider a nilList of integers. You could write a sumIteratively method to compute the sumIteratively of the elements; you could write a productRecursively method to compute the productRecursively of the elements; or you could write a min or a max methods to compute the minimum or the maximum of the nilList.
        But you could also use the reduce method for all these computations.
        This is abstraction. You abstract the part that is common to all operations in the reduce method, and you pass the variable part (the operation) as an argument.
        This advantage is true for simple constructs such as for a while loops. It is even much more important for complex techniques like parallelization

        - Normal Functional Programming Concepts for a Function:

            - It must not mutate anything outside the function. (example 1 as shown above)
            - No internal mutation may be visible from the outside. (example 2 as shown above)
            - It must not mutate its argument, if it affects caller.  (example 2 as shown above)
            - It must not throw errors or exceptions. It can catch an exception and return an error message, but shouldn't throw an exception or shouldn't log an error message.
            - It must always return a value.
            - When called with the same argument, it must always return the same result.


    Chapter 2 - Using Functions in Java

            Function Composition: pg 20

                Functions are building blocks that can be composed to build other functions.
                e.g. f(x)=x+1 and g(x)=x*2
                composition of two functions
                f(g(x))= (x*2)+1   ---  g(x) is applied first and then f(x)
                g(f(x))= (x+1)*2   ---  f(x) is applied first and g(x)
                results of both can be different based on which function is called first. In Java 8, composition of two functions is represented by andThen, compose methods.

                    andThen(function)  -  f1.andThen(f2) = f1 is called first and its output becomes an input to to f2
                    compose(function)  -  one function's output becomes an input to another function. f1.compose(f2)  = f2 is called first and its output becomes input to f1.


                Disadv of composition:
                    Composition seems very beautiful feature of Functional programming, but it has big disadvantage.
                    In composition, one method calls another and builds the stack. A few composed methods do not make big difference, but think about 1000 composed methods. It may cause Stack OverFlow.

                Function has one another method that returns a Function returning a value same as argument.
                    identity()

            Curried Function and Partially Applied Functions: pg 21, 22

                see tryCurriedFunctionAndPartiallyAppliedFunctions() method

        - Functional Methods (pg 23)

            Example of which method satisfies functional programming concepts:

            public class FunctionalMethods {

              public int percent1 = 5;
              private int percent2 = 9;
              public final int percent3 = 13;

              public int add(int a, int b) { - function because it doesn't mutate the args. It doesn't use anything other than args.
                return a + b;
              }
              public int mult(int a, Integer b) { - function because it mutates the args, but it has no external effect because it is pass by value.
                a = 5;
                b = 2;
                return a * b;
              }
              public int div(int a, int b) { - isn’t a pure function because it’ll throw an exception if the divisor is 0. To make it a function, we could test the second parameter and return a value if it’s null. It would have to be an int, so it would be difficult to find a meaningful value, but this is another problem.
                return a / b;
              }
              public int applyTax1(int a) { - not to be a pure function because its result depends on the value of percent1, which is public and can be modified between two function calls
                return a / 100 * (100 + percent1);
              }
              public int applyTax2(int a) { - We might see it as a function, because the percent2 property is private. But it’s mutable,and it’s mutated by method append2.
                return a / 100 * (100 + percent2);
              }
              public int applyTax3(int a) { - is somewhat special. Given the same argument, the method will always return the same value, because it depends only on its arguments and on the percent3 final property, which can’t be mutated. You might think that applyTax3 isn’t a pure function because the result doesn’t depend only on the method’s arguments.
                return a / 100 * (100 + percent3);
              }
              public List<Integer> append(int i, List<Integer> nilList) { - not functional. it is mutating it input arg, but it is affecting the external state of the passed nilList.
                nilList.add(i);
                return nilList;
              }
              public List<Integer> append2(int i, List<Integer> nilList) {- not functional. it is changing outside variable percent2's value.
                List<Integer> result = new ArrayList<>();
                result.add(i);
                percent2++;
                return result;
              }
            }

            All instance methods can be replaced with static methods by adding an argument of the type of the enclosing class.
            So the applyTax3 method may be rewritten as

            public static int applyTax3(FunctionalMethods x, int a) {
              return a / 100 * 100 + x.percent3;
            }

        - Type Inference pg 38

             Java 7 added a bit of type inference with the diamond syntax:

             List<String> nilList = new ArrayList<>();

             Same thing is possible with lambdas.

             Function<Integer, Integer> triple = x -> x * 3;

             In this example, the type of x is inferred by Java. This isn’t always possible. When Java complains that it isn’t able to infer the type, we have to write it explicitly. Then we must use parentheses:

             Function<Integer, Integer> triple = (Integer x) -> x * 3;

        - Currying (pg 40)
            Function/Method that returns a function is called curried function/method.
            Normally, functional programs should not reserve more than one input args, but if it needs to then it can be converted to curried function.

            Java 8's Lambda simplifies writing curried function.

            Function<Integer, Function<Integer, Integer>> curriedFunction =
                new Function<Integer, Function<Integer, Integer>>() {
                    @Override
                    public Function<Integer, Integer> apply(final Integer i1) {
                        return new Function<Integer, Integer>() {
                            @Override
                            public Integer apply(Integer i2) {
                                return i1 + i2;
                            }
                        };
                    }
                };

            Using Lambda, you can simplify it a lot

            Function<Integer, Function<Integer, Integer>> curriedFunction = i1 -> i2 -> i1+i2;

            See the examples done in code below.

            Partial Function Application or Automatic Currying (pg 45)

            https://blog.informatech.cr/2014/10/19/functional-programming-with-java-8-functions/
            If a function is receiving multiple arguments, we can partially invoke the function providing just a few arguments and receive a partially applied function out of it. This is typically called currying.

            Function<Integer,Function<Integer,Integer>> sumIteratively = x -> y -> x + y;

            Well, you can see sumIteratively is declared in a “currified” way.
            And now we can partially apply it:

            Function<Integer, Integer> plus10 = sumIteratively.apply(10);
            Integer res = plus10.apply(5); //yields 15

        - Higher order function (pg 42)

            Function taking function(s) as arguments and/or returning a function is called higher order function.
            e.g. Consumer's composition method 'andThen'. It takes Consumer as an arg and returns Consumer also.

        - Closures pg 43

            A closure uses variables that are outside of the function scope.
            This is not a problem in traditional procedural programming – you just use the variable.
            But in functional programming, it starts creating problems at runtime.
            Lambda can use outside variables, so it is a closure, but it can access only final variables from outside (just like anonymous class). It cannot even change the value of outside variables because it considers them as final even though they are not declared as final.
            So java 8's lambda is not a pure closure like other functional programming languages like javascript.


            Anonymous class methods cannot access non-final variables of enclosing class/method, and so lambdas.

             class A {

                // This method returns a function (closureFun), but it is not a normal function It is a closure. 'closureFun' uses variable 'a', which is scoped outside of it in its enclosing context. That's why 'closureFun' is a closure function.
                // In Java, closure function cannot use non-final variable from the enclosing context. So, Java's closure is a Partial Closure.
                private Function<Integer, Integer> method() {

                    int a = 10;

                    Function<Integer, Integer> closureFun = new Function<> {
                        @Override
                        public Integer apply(Integer i1) {
                            a = a * 2;  // not allowed because 'a' is a variable of enclosing context and it is not declared final.
                            return a * i1;
                        }
                    }

                    return closureFun; // closureFun is a closure because it uses a variable 'a' that is scoped in its enclosing context.
                }
             }

             method().apply(5);

             Lambda is just a replacement of Anonymous class. So, lambda also needs variable of enclosing class/method to be declared as final, otherwise it cannot use it.
             But, if enclosing class/method's variable is not being modified in lambda, then by default lambda considers it as final. You don't need to declare it final.

             private Function<Integer, Integer> method() {
                int a = 10; // As 'a' is not changed, lambda will consider it as final.
                return (anotherNumber) -> a * anotherNumber; // this lambda is a closure because it uses it enclosing context's variable.
             }

             Pure function should not be using enclosing context's variables, so how to resolve this?
             You can create a Tuple, of multiple input parameters and pass it to function, but it is cumbersome to create a Tuple.

             Function<Tuple<Integer, Integer>, Integer> function = tuple -> tuple._2 * tuple._1;
             int a = 10;
             System.out.println(function.apply(new Tuple<>(a, 12)));

             Better approach:
             - Use BiFunction, BinaryOperator, DoubleBinaryOperator etc that can reserve two input parameters.
             - For more than two input parameters, we can use Currying
                Integer a = 10;
                Function<Integer, Function<Integer, Integer>> function = x -> y -> y * x;
                System.out.println(function.apply(taxRate).apply(12));

          - Recursive Functions (pg 50)

                Some functional programmers even say that recursion is the go-to feature of functional programming, and thus should be avoided as much as possible. Nevertheless, as functional programmers, we must master recursion, even if we decide to eventually avoid it.
                Determining how many recursive steps Java can handle is difficult, because it depends of the size of the data that is pushed on the stack, and also of the state of the stack when the recursive process starts. In general, Java can handle about 5,000 to 6,000 steps.

                A recursive method is simple to define. The method factorialRecursive(int n) can be defined as returning1if it’s 0, andn * factorialRecursive(n – 1)otherwise:

                    public int factorialRecursive(int n) {
                        return n == 0 ? 1 : n * factorialRecursive(n - 1);
                    }

                Recursive factorialRecursive Function

                    Function<Integer, Integer> factorialRecursive = n -> n <= 1 ? n : n * factorialRecursive.apply(n – 1);

                    Now for the tricky part. This code won’t compile because the compiler will complain about an Illegal self reference. What does this mean? Simply that when the compiler reads this code, it’s in the process of defining the factorialRecursive function. During this process, it encounters a call to the factorialRecursive function, which isn’t yet defined.

                    This is same as
                    int x = x + 1;

                    Problem can be solved using
                    int x;
                    {
                        x = x + 1;
                    }

                    public Function<Integer, Integer> factorialRecursive;
                    {
                      factorialRecursive = n -> n <= 1 ? n : n * factorialRecursive.apply(n - 1);
                    }

                    This can also be used for statically defined functions:

                    public static Function<Integer, Integer> factorialRecursive;
                    static {
                      factorialRecursive = n -> n <= 1 ? n : n * factorialRecursive.apply(n - 1);
                    }

          - Identity function (pg 53)

                In future programs, we’ll apply operations to functions, and we’ll need a neutral element, or identity element, for these operations. A neutral element will act as the 0 for addition, or 1 for multiplication, or the empty string for string concatenation.
                The identity function can be added to the definition of our Function class, in the form of a method named identity, returning the identity function:

                static <T> Function<T, T> identity() {
                    return t -> t;
                }

                This is a part of Java 8's Function.

                Example of identity function usage:

                    Function<Double, Double> square = number -> number * number;
                    Function<Double, Double> half = number -> number * 2;

                    Function<Double, Double> square = number -> number * number;
                    Function<Double, Double> half = number -> number * 2;
                    operation(Arrays.asList(10D, 4D, 12D), square);
                    operation(Arrays.asList(10D, 4D, 12D), half);
                    operation(Arrays.asList(10D, 4D, 12D), Function.<Double>identity()); // identity function usage

                    private List<Double> operation(List<Double> numbers, Function<Double, Double> fx) {
                      List<Double> result = new ArrayList<>();

                      for (Double number : numbers) {
                         result.add(fx.apply(number));
                      }

                      return result;
                    }

                    Similar approach is being used by Java's Collector. While creating CollectorImpl in Collectors, it passes identity function, if Characteristics is set as CH_ID.
                    So, Finisher will have no effect.


         - Java 8 Functional Interfaces (pg 54)

            1) java.util.function.Function

            2) java.util.function.Supplier is equivalent to a function with no argument.
                In functional programming, it’s a constant, so it might not look useful at first.
                But it has two specific uses:
                    if not referentially transparent (not a pure function), it can be used to supply variable data, such as time or random numbers. (We won’t use such nonfunctional things!)
                    The second use, much more interesting, is to allow lazy evaluation. We’ll come back to this subject often in the next chapters. (Look at powerful usage of Supplier in Chapter 9's Stream.java and Chapter 3,4's CollectionUtilities.java)
                    e.g.
                    Supplier supplier = () -> ArrayList::new
                    if(...) List nilList = supplier.get()

                    Supplier can be used to instantiate a class based on demand when it is required.


            3) java.util.function.Consumer isn’t at all for functions, but for effects. (Here, it’s not side effect, because the effect is the only result we get with a Consumer, because it doesn’t return anything.)

            4) java.lang.Runnable also can be used for effects that don’t reserve any parameters.

     Chapter 3 - Making Java more functional

         -  Making standard control structures functional (pg 60)
            Abstracting control structures (pg 61)

            EmailValidation.java

                Entire if-else loop can be abstracted out in a Function.

                // From the Java syntax point of view, there’s no obligation for this variable to be final, but it’s necessary if we want to make the testMail method functional.
                // Another solution would be to declare the pattern inside the method, but this would cause it to be compiled for each method call. If the pattern were to change between calls, we ought to make it a second parameter of the method.
                final Pattern emailPattern = Pattern.compile("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$");

                // Although below testMail method seems to be a pure effect because it doesn’t return anything, it mixes data processing with effects.
                // This is something we want to avoid, because it results in code impossible to test.
                void testMail(String email) {
                  if (emailPattern.matcher(email).matches()) {
                    sendVerificationMail(email);
                  } else {
                    logError("email " + email + " is invalid."); // how do you test this line?
                  }
                }

                - Let's abstract out condition using Predicate or Function (This is not the requirement to make testMail functional though). It is just a best practice.

                final Function<String, Boolean> emailChecker = s -> emailPattern.matcher(s).matches();

                void testMail(String email) {
                if (emailChecker.apply(email)) {
                    sendVerificationMail(email);
                  } else {
                    logError("email " + email + " is invalid.");
                  }
                }

                You can actually abstract out the entire if loop using Predicate or Function

                - public class EmailValidation {

                     // You shouldn't log anything in a function.
                     // There are three approaches to replace loggers or any other side-effects in a Function.
                     // 1. you can return a proper object like Result to client and let client log from that object, if it wants. Result interface and its child classes Success and Failure are explained in Chapter 7
                     // 2. you can return an Executable (Java 8 doesn't have it) that takes care of side-effect. It is explained more in detail in Chapter 13.
                     // 3. you can return a Consumer (Java 8 has it) instead of Executable

                     // This function shows Option 1
                    static Function<String, Result> emailChecker = email -> {
                        if (email == null) {
                          return new Result.Failure("email must not be null");
                        } else if (email.length() == 0) {
                          return new Result.Failure("email must not be empty");
                        } else if (emailPattern.matcher(email).matches()) {
                          return new Result.Success();
                        } else {
                          return new Result.Failure("email " + email + " is invalid.");
                        }
                    };

                    private static void logError(String error) {
                        System.err.println("Error message logged: " + error);
                    }
                    private static void sendVerificationMail(String email) {
                        System.out.println("Mail sent to " + email);
                    }

                    // This method has a side-effect of sending email and logging error. How to make it functional?
                    static void validate(String email) {
                        Result result = emailChecker.apply(email);
                        if (result instanceof Result.Success) {
                          sendVerificationMail(s); // side effect
                        } else {
                          logError(((Result.Failure) result).getMessage()); // side effect
                        }
                    }

                    // Solution to make above validate method functional: Using Option 2 (Wrap side-effect by a Supplier)
                    static Executable validate(String email) {
                        Result<Boolean> result = emailChecker.apply(email);
                        return (result instanceof Result.Success)
                            ? () -> sendVerificationMail(s)
                            : () -> logError(((Result.Failure) result).getMessage());
                    }

                    // Solution to make above validate method functional: Using Option 3
                    static Tuple<String, Result<Boolean>> validate(String email) {
                        return new Tuple(email, emailChecker.apply(email));
                    }
                    // +
                    static Consumer processEmailCheckerResult(Tuple<String, Result<Boolean>> result) {
                        return (result._2 instanceof Result.Success)
                            ? (emailCheckerResult) -> sendVerificationMail(result._1)
                            : (emailCheckerResult) -> logError(((Result.Failure) result).getMessage());

                    }
                }


            Java 8 doesn't have below functional interface.
            Without this functional interface, it becomes very difficult to make an imperative method functional without breaking it into two functional methods at least.
            You can see its example of it on pg 65.

            public interface Executable {
                void execute();
            }

            See Chapter 15 also for more examples.

        There is one more challenge.
            How can we replace if...else, switch...case loops?
            You can create Matchers taking Predicate and Supplier/Consumer as arguments and evaluate matchers using for loop.
            See EmailValidation.java. It uses Matcher.java, CompositeMatchers.java.


          - Abstracting Iteration (pg 74)

            In Functional programming, map, reduce, filter etc are finally one type of Function only. They are implicitly using Function ad described below.

            Abstracting an operation on lists with mapping (pg 82))


                List<Double> newList = new ArrayList<>();
                for (Integer value : integerList) {
                  newList.add(value * 1.2);  // This is an example of mapping. you are trying to convert(map) value to value*1.2. so this can be converted to map function
                }


                In Functional programming, everything is about Function. map is also one type of Function. So,

                integerList.stream().map(x -> x * 1.2).collect(Collectors.toList())

                can be written as


                Function<Integer, Double> addTwentyPercent = x -> x * 1.2; // This does a job of mapping.

                List<Double> map(List<Integer> nilList, Function<Integer, Double> f) {
                  List<Double> newList = new ArrayList<>();
                  for (Integer value : nilList) {
                    newList.add(f.apply(value));
                  }
                  return newList;
                }

            Folding and Reduction (pg 79)

                List folding transforms a nilList into a single value by using a specific operation. The resulting value may be of any type. It doesn’t have to be of the same type as the elements of the nilList.
                Folding to a result that is the same type as the elements is a specific case called reducing.

                Folding example - Converting a nilList of characters into string
                    ('a', 'b', 'c') =  (("" + 'a') + 'b') + 'c' = "abc"
                Reduction example - Computing sumIteratively of elements of a nilList (1, 2, 3, 4)
                    (((0 + 1) + 2) + 3) + 4 = 10

                These are the examples of LEFT FOLD/REDUCE because identity element (here 0) is at the left to start accumulating the nilList from the left

                    'a' + ('b' + ('c' + "")) = "abc"
                    1 + (2 + (3 + (4 + 0))) = 10

                These are the examples of RIGHT FOLD/REDUCE because identity element (here 0) is at the right to start accumulating the nilList from the right

                You can consider FOLD RIGHT as RECURSIVE and FOLD LEFT as CORECURSIVE. This is just an analogy. They are not really recursive methods.
                In recursion as well as corecursion, evaluation of one step is dependent on the previous step. But a recursive definition starts with the last step and defines its relationship with the former one. In order to be able to conclude, it also has to define the base step.
                Corecursion, on the other hand, starts from the first step and defines its relationship to the next one. There’s no need for a base step, because it’s also the first step.
                From this, it seems that folding right a nilList is equivalent to folding left the nilList after having reversed the order of the elements.

               This is how you write FOLD/REDUCE operation using Function

                FOLD operation on a list is explained better in Chapter 5.

                public static Integer fold(List<Integer> is,
                                           Integer identity, // identity = 0
                                           Function<Integer, Function<Integer, Integer>> f) {
                  int result = identity;
                  for (Integer i : is) {
                    result = f.apply(result).apply(i);
                  }
                  return result;
                }

                List<Integer> nilList = nilList(1, 2, 3, 4, 5);
                int result = fold(nilList, 0, x -> y -> x + y);

                Using generics, fold left can be written as

                public static <T, U> U foldLeft(List<T> ts,
                                                U identity,
                                                Function<U, Function<T, U>> f) {
                  U result = identity;
                  for (T t : ts) { // start from the first element
                    result = f.apply(result).apply(t);
                  }
                  return result;
                }

                Using generics, fold right an be written as

                public static <T, U> U foldRight(List<T> ts, U identity, Function<U, Function<T, U>> f) {
                  U result = identity;
                  for (int i=ts.size()-1; i>0; i--) {  // start from the last element
                    result = f.apply(result).apply(ts.get(i));
                  }
                  return result;
                }

           Composing mappings and mapping compositions pg 87

               which one is better and when? (VERY IMPORTANT)

               composing mappings
               stream.map(price -> price+tax).map(taxedPrice -> taxedPrice+shippingCost)....
                                or
               mapping compositions (andThen/compose methods of Function.java are called compositions)
               stream.map(
                            (price -> (price+tax)).andThen(taxedPrice_+shippingCost)
                        ).....

               Later one is better for smaller number of mappings. Mostly, it is better to compose the functions instead of mappings.
               But remember, composition call one function's apply method from another function's apply method. So, if you have converted more than 6000 mappers to composed functions, then you may get stack overflow.

               Same concept applies to filters and any other streaming functions. Functional Interface composition(chaining) is better than composing(chaining) streaming methods.
               Predicate, Consumer and Supplier do not have this problem because their methods don't call one within another. So, it is safe to compose predicates for a filter instead of using multiple filters.


           Building corecursive lists pg 88

                Corecursive means that each element can be constructed by applying a function to the previous element, starting from the first one.
                This is what distinguishes corecursive from recursive constructs.
                In recursive constructs, each element is a function of the previous one, starting with the last one.

                Basically, corecursion is recursion accumulator-style (Tail-recursion), building its result on the way forward from the starting case, whereas regular recursion builds its result on the way back from the base case.

                Corecursive lists are easy to construct. Just start from the first element ( int i = 0) and apply the chosen function.

                for (int i = 0; i < limit, i++) { // This is coreursive
                  some processing...
                }
                This is nearly equivalent to the following:
                    nilList(0, 1, 2, 3, 4).forEach(some processing)

                In Java8, this is how you write
                    IntStream.range(0, limit).forEach(i -> some processing)

                Imperative version (non-functional) of range method is

                    public static List<Integer> range(int start, int end) {
                      List<Integer> result = new ArrayList<>();
                      int temp = start;
                      while (temp < end) {
                        result = append(result, temp);
                        temp = temp + 1;
                      }
                      return result;
                    }

                'range' method can call unfold method. 'range' method is same as Stream.java's 'from' method.

                A generic range method working for any type and any condition.

                    public static <T> List<T> unfold(T seed, // In functional programming seed is referred as start
                                                     Function<T, T> f,
                                                     Function<T, Boolean> p) {
                      List<T> result = new ArrayList<>();
                      T temp = seed;
                      while (p.apply(temp)) {
                        result = append(result, temp);
                        temp = f.apply(temp);
                      }
                      return result;
                    }

                 unfold method can be used very effectively for infinite recursive method calls (recursive method that doesn't have exit condition).
                 See Chapter 9's Stream.java's unfold method that is being used by from, repeat, iterate methods.
                 See Chapter 13's TestReader.java that uses unfold method to read inputs from console to create a stream of persons.

                VERY IMPORTANT (pg 90)
                You can easily convert imperative (non-functional method with while/for loop) to a generic method very easily by using Function/Predicate for condition and body of the loop.
                Abstract out the condition and the code in the loop as much as you can and pass those abstracted functions to the method.
                See the examples below.

        Problems with standard types pg 92

            We’ve used standard types such as integers, doubles, and strings to represent business entities such as prices and email addresses. Although this is common practice in imperative programming, it causes problems that should be avoided.

            Rule of Thumb:
            If class has more than one variables of the same type, there is probability of errors. Let's see how?

            public class Product {
                public double price;
                public double weight;

                public double getWeightWithAge(int age) {
                    return price * age;
                }
                public double getPriceForItems(int itemCount) {
                    return weight * itemCount;
                }
            }
            As you see, you have done a mistake in calculating weight and price by using them in wrong methods. Compiler won't complain this because both are double.

            Solution:
            Use Value Types instead of Standard Types.

            public class Product {
                // By doing this we avoided to have variables of the same type in one class.
                // Now price is a type of Price (not double) and Weight is a type of Weight (not double)
                // Both Price and Weight will have a variable called value. So they became Value types now in Product class.
                // As Price calculation based on itemCount should be a responsibility of Price class, we moved that logic there.
                // Similarly, as Weight calculation based on age should be a responsibility of Weight class, we moved that logic there.

                public Price price;
                public Weight weight;

                public Weight getWeightWithAge(int age) {
                    return weight.getWeightWithAge(age);
                }
                public Price getPriceForItems(int itemCount) {
                    return price.getPriceForItems(itemCount);
                }

                class Price {
                    public double value;
                    public Price(double value) {
                        this.value = value;
                    }
                    public Price getPriceForItems(int itemCount) {
                        return new Price(value * itemCount);
                    }
                }
                class Weight {
                    public double value;
                    public Weight(double value) {
                        this.value = value;
                    }
                    public Weight getWeightWithAge(int age) {
                        return new Weight(value * age);
                    }
                }
            }


    Chapter 4 - Recursion and Memoization

        Recursion vs Corecursion:
        Basically, corecursion is recursion accumulator-style, building its result on the way forward from the starting case, whereas regular recursion builds its result on the way back from the base case.

        Tail-Recursion:
        Tail recursion is a special kind of recursion where the recursive call is the very last thing in the function. It's a function that does not do anything at all after recursing.
        Tail-Recursion needs to follow the principle of Corecursion to have the recursive call very last thing in the function.

        public static int factorialRecursive(int n) {
            if(n == 0) return 0;
            if(n == 1) return n;
            return n * factorialRecursive(n-1);  // This is not Tail-Recursion because calling the recursive method is not the last thing to do. Its result needs to be multiplied with something.
        }


        public static int factorialTailRecursive(int n, int result) {
            if(n == 0) return 0;
            if(n == 1) return result;
            return factorialTailRecursive(n - 1, result * n); // This is Tail-Recursive and also Corecursive
        }


        Recursion vs Tail-Recursion: (4.1.1)

            There are stack overflow problems with recursion.

            To make the problem little less serious, you can convert recursion into tail recursion, if possible.
            http://stackoverflow.com/questions/33923/what-is-tail-recursion
            Another example using factorialRecursive algorithm - http://c2.com/cgi/wiki?TailRecursion

                int recsum(x) {
                 if x == 1:
                  return x
                 else
                  return x + recsum(x - 1)
                }

             If you called recsum(5), java will use following recursive calls and store the methods as well as their expression tree evaluations in a stack.
             Expression Tree: https://en.wikipedia.org/wiki/Binary_expression_tree

                recsum(5)
                5 + recsum(4)
                5 + (4 + recsum(3))
                5 + (4 + (3 + recsum(2)))
                5 + (4 + (3 + (2 + recsum(1))))
                5 + (4 + (3 + (2 + 1)))
                15

            By using tail recursion, you can at least avoid using expression tree and so stack for that.

                int running_total = 0;
                int tailrecsum(x, running_total):
                  if x == 0
                    return running_total
                  else:
                    return tailrecsum(x - 1, running_total + x)

            Here's the sequence of events that would occur if you called tailrecsum(5).
            In the tail-recursive case, with each evaluation of the recursive call, the running_total is updated.
            As you see, stack will be used for method calls, but not for expression tree to calculate the result at each level. This reduces the stack usage.

                tailrecsum(5, 0)
                tailrecsum(4, 5)
                tailrecsum(3, 9)
                tailrecsum(2, 12)
                tailrecsum(1, 14)
                tailrecsum(0, 15)
                15

            In traditional recursion,
                the typical model is that you perform your recursive calls first, and then you reserve the return value of the recursive call and calculate the result. In this manner, you don't get the result of your calculation until you have returned from every recursive call.

            In tail recursion,
                you perform your calculations first, and then you execute the recursive call, passing the results of your current step to the next recursive step. This results in the last statement being in the form of "(return (recursive-function params))" (I think that's the syntax for Lisp). Basically, the return value of any given recursive step is the same as the return value of the next recursive call.

            The result of this is that once you are ready to perform your next recursive step, you don't need the current stack frame any more. This allows for some optimization. In fact, with an appropriately written compiler, you should never have a stack overflow snicker with a tail recursive call. Simply reuse the current stack frame for the next recursive step. I'm pretty sure Lisp does this.



            Java 8's Function->compose(...) method uses similar approach, but it doesn't eliminate the recursion completely. So, for large number of recursive calls, it may end up in StackOverflow.

            static <T> Function<T, T> composeAllRecursively(List<Function<T, T>> nilList) {
                Function<T, T> f1 = nilList.get(0); // assuming that nilList size is > 0
                for (int i = 1; i < nilList.size(); i++) {
                    f1 = f1.compose(nilList.get(1));// this is same as f1.apply(f2.apply(f3.apply (...)))) This ends in recursive calls and uses stack. Unfortunately, java uses this form of composition in Function.
                }
                return f1;
            }

       Implementing Recursion in Java (4.1.2, 4.1.3, 4.1.4, 4.1.5, 4.1.6)

            Look at Factorial.java, AddHelper.java, CollectionUtilities.java, List.java, FibonacciExample.java, FoldLeftExample.jaa (VERY IMP) . Look at them in the same sequence for better understanding.
            They has examples of
             - recursion
             - tail recursion  that is not giving much advantage over stack usage due to Java's limitation
             - tail recursion  that is giving advantage of using only one stack frame using Java's Supplier's Lazy Evaluation technique

            FibonacciExample.java is a special one because it calculates result based on two recursive calls. So, You need to handle two results (accumulators) during tail-recursion.
            FoldLeftExample.java, List.java shows functional version of tail-recursion using Java 8.

       Using locally defined functions (4.2.1)

            'this' inside anonymous class vs lambda:
                'this' inside anonymous class refers to that anonymous class' instance. You can access that anonymous class' members using 'this'.
                'this' inside lambda refers to enclosing class' instance. You can access enclosing class' members using 'this'.

            Just like other classes, lambda can also have an inner class in it.
            see 'thisReferenceTest()' method.


       Automatic Memoization (4.4.3):

            Memoization isn’t mainly used for recursive functions. It can be used to speed up any function.
            http://www.geeksforgeeks.org/dynamic-programming-set-1/

            see FibonacciExample.java.
            Memoization helps to save Stack memory. But needs heap memory for a Map which is better than using stack memory.
            Memoization is a solution of Java's Recursion disadvantages, but if you use Java 8's Tail-Recursion, then you don't need Memoization. It uses only one stack frame.

            Normally, you use a Map to remember the result.

            Integer doubleValue(Integer x) {
              return x * 2;
            }

            Memoized form of above method:

            Map<Integer, Integer> cache = new ConcurrentHashMap<>();
            Integer doubleValue(Integer x) {
              if (cache.containsKey(x)) {
                return cache.get(x);
              } else {
                Integer result = x * 2;
                cache.put(x, result) ;
                return result;
              }
            }

            Using Java 8, you can even make it simpler.

            Map<Integer, Integer> cache = new ConcurrentHashMap<>();
            Integer doubleValue(Integer x) {
                return cache.computeIfAbsent(x, x1 -> x1 * 2);
            }

            Any method can be converted to a Function.

            Function<Integer, Integer> f = x -> cache.computeIfAbsent(x, x1 -> x1 * 2);
            f.apply(3);

            You can abstract out the implementation of memoization using Memoizer.java.
            Look at Memoizer.java and MemoizerDemo.java.

            Chapter 8
            has another example of Memoization


    Chapter 5 - Data handling with lists

        List.java

        This chapter shows how to create "Singly LinkedList" that is immutable.

        immutable list vs mutable list (Why immutable list?) :-

            This chapter shows the difference between immutable list vs mutable list in the world of Functional Programming.

            - Recursion is very common in the world of Functional Programming.
            Even though, Java doesn't give any advantage of tail-recursion (by using single stack frame instead of multiple), Chapter 4 showed you how can you use Suppler(lazy evaluation) to make Java use Tail-Recursion effectively.

            See CollectionUtilities.java and List.java :-
            CollectionUtilities.java - If you apply recursion on mutable list, you need to create a new list from original list every time you recurse a method.
            List.java - If you use immutable list, problem of creating a new copy of original list during recursion is gone.
            You can compare sumRecursively methods of both classes for better understanding.

            (VERY IMP)
            First time when you create an immutable list from an array, you need to create sub array (see List.java's listRecursive(A... a) method).
            But after that all subsequent operations on the list will be super efficient as you do not need to create a sublist during recursive method call on the list. (see List.java's sumRecursively(List<A> list) method and compare it with CollectionsUtilities.java's sumRecursion(List<Integer> list) method).

           - Immutability is very important to not to have concurrency problems. You don't need to implement locks to avoid concurrency problems.
           A glance on Concurrency and Immutability
           http://tutorials.jenkov.com/java-concurrency/thread-safety-and-immutability.html
           https://www.infoq.com/articles/dhanji-prasanna-concurrency


        Generic 'fold' method (List.java):-

            public static Integer sumRecursively(List<Integer> list) {
                return list.isEmpty()
                        ? 0
                        : list.head() + sumRecursively(list.tail());
            }

            public static Double productRecursively(List<Double> list) {
                return list.isEmpty()
                        ? 1
                        : list.head() * productRecursively(list.tail());
            }

            Now let’s remove the differences and replace them with a common notation:

            public static Type operation(List<Type> list) {
              return list.isEmpty()
                      ? Identity
                      : list.head() operator operation(list .tail());
            }
            public static Type operation(List<Type> list) {
              return list.isEmpty()
                      ? Identity
                    : list.head() operator operation(list .tail());

            }

            The two operations are actually nearly the same. If we can find a way to abstract the common parts, we will just have to provide the variable information (in bold italic) to implement both operations without repeating ourselves. This common operation is what we call a fold

            These operations do some operation on two elements of the list and produces the result.
            These kind of operations is called 'folding'.

            IMP concept:
            fold method is an abstraction of recursion.
            see
            sumRecursively and sunViaFoldLeft methods,
            reverse and reverseViaFoldLeft methods,
            lastOption_recursive and lastOption_using_foldLeft methods.

            You can write a generic 'fold' operation method using Java 8.
            In any folding operation, you need input list, identity and BiFunction that takes two inputs(one identity and another element from input list)
            See List.java.

            IMP Concept -
            fold method can be evaluated lazily using TailCall functionality. This helps us to use only one stack frame.
            e.g. foldLeftTailRecursiveJava8Style method

            IMP Concept -
            fold method's all output calculations for all recursive calls are delayed till exit condition is met.
            Its output can also be evaluated lazily by making it return Supplier<O> instead of O. For this you need to pass, 'Supplier<O> identity' instead of 'O identity'
            e.g. foldLeftTailRecursive_LazilyEvaluatingTheOutput method. We are passing 'Supplier<O> identity' instead of 'O identity'.


            What is the difference between foldLeft and foldRight?
                In foldLeft, you apply an operation first on identity and first element of the list and then moving further in the list.
                In foldRight, you move further till the last element of the list using recursion and then applying an operation on identity and last element and then second last element and so on.



    Chapter 6 - Dealing with Optional Data

        Alternatives to null references (PG 169)
            NullPointerException is the worst error that you can think of in software. NullPointer
            Totally removing “business” nulls will not allow you to get rid of the NullPointerException. It will only ensure you that null references will only be caused by bugs in the program and not by optional data. The following code is an example of a method returning optional data:

            static Function<List<Double>, Double> mean = xs -> {

              if (xs.isEmpty()) {
                    ???;    // return Double.NaN or throw new MeanOfEmptyListException() or return null;
              } else {
                return xs.foldLeft(0.0, x -> y -> x + y) / xs.length();
              }

            There are 4 options to return a value to indicate that business data (here nilList xs) is empty.
                - return Double.NaN, which is good for Double, but there is no equivalent to it for Integer. So, you cannot use it for generics.
                - throw and exception - Exceptions are generally used for kinds of erroneous results. Here, there is no error. There is a simply no result, and this is because there was in fact no input data! or should we consider calling the function with an empty nilList a bug?
                  Moreover, our function is no longer a pure function. It is no longer referentially transparent, which leads to the numerous problems we have talked about in chapter 2. Among these problems, our function would no longer be composable.
                - Returning null would be the worst possible solution.
                  It would force (ideally!) the caller to test the result for null and act accordingly.
                  It would crash if boxing is used.
                  As with the exception solution, the function would no longer be composable.
                  It would allow propagating the potential problem far away from its origin if the caller was to forget to test for a null result, leading to NullPointerException thrown from anywhere in the code.
               -    if (xs.isEmpty()) {
                        return Option.none();   // This is a better option.
                    } else {
                        Option.some(xs.foldLeft(0.0, x -> y -> x + y) / xs.length());
                    }

                    abstract class Option<A> {
                        private static Option none = new None();

                        public static <A> Option<A> some(A a) {
                            return new Some<>(a);
                        }
                        public static <A> Option<A> none() {
                            return none;
                        }
                    }

                    class Some<A> extends Option<A> {
                        private final A value;

                        private Some(A a) {
                         value = a;
                        }
                    }

                    class None<A> extends Option<A> {
                        private None() {}
                    }

                   Java 8's Optional class is similar to our Option class as mentioned above. Returning Option.none() is like returning a default value instead of returning null or throwing an exception.
                   Java 8's Optional has methods that satisfies this need.

                   public T orElse(T other) {
                        return value != null ? value : other;
                   }

                    public T orElseGet(Supplier<? extends T> other) {
                        return value != null ? value : other.get();
                    }

                    It also provides an option of throwing an exception instead of returning a default value.

                    public T get() {
                        if (value == null) {
                            throw new NoSuchElementException("No value present");
                        }
                        return value;
                    }

                    There is a lot of similarity in the functionality provided by Collection's stream and Optional in Java 8.
                    Considering that an Optional is like a nilList containing at most one element, you can apply the same principles.
                    From a stream of collection, we can use map, flatMap, filter etc methods, similarly Optional can have those methods. Only difference is the return value. In case of Collection's stream, it returns another stream, whereas in Optional's methods return another Optional.
                    Just like List's isEmpty() checks for emptiness, Optional has isPresent() method that checks for null value.

                    Best use of Optional is using it for chain of actions.

                    Person person = null;
                    Optional.ofNullable(person).map(person -> person.getName()).orElse(new Person());

                    This code will never throw a NullPointerException in entire chain of actions. As person is null, ofNullable will return an Optional.EMPTY which is same as our None class.
                    map method will check whether passed Optional is empty, if yes then it will simply return another empty Optional. orElse will check whether passed Optional is empty. If yes, then it will simply return a default value by returning a new Person object.
                    In this entire chain, you don't have to manually check for null input and it will never throw NullPointerException.

                    How to change legacy code?
                    String method(Person person) {
                        if(person == null) { throw some exception; }
                        return person.getName();
                    }
                    change it to
                    Optional<String> method(Optional<Person> person) {
                        return person.map(person -> person.getName());
                    }

                    Function<Double, Double> abs = x -> x > 0 ? x : -x;
                    This function may throw NullPointerException, if input to Function (x) is null.

                    You can simply avoid it by

                    Function<Optional<Double>, Optional<Double>> abs1 = aDouble -> {
                        Double val = aDouble.orElseGet(() -> new Double(0));
                        return Optional.of(val > 0 ? val : -1*val);
                    };


        How do you model the absence of a value? (From "Java8 In Action" book - Chapter 10 (10.1))

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
                private Optional<Car> car;

                public Optional<Car> getCar() {
                    return car;
                }
            }
            // or alternatively
            public class Person {
                private Car car;

                public Optional<Car> getCarAsOptional() {
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
                        .flatMap(p -> p.getCar())
                        .flatMap(c -> c.getInsurance())
                        .map(i -> i.getName())
                        .orElse("Unknown")
                return name;
            }




    Chapter 7 - Handling Errors and Exceptions

        - To make this understand, Result.java and ResultTest.java is created.

        - Java 8's Optional class is similar to Result class with one major difference.
        Both of them have Empty and Success kind of  features, but Optional doesn't have Failure kind of feature that Result class has.
        And it is a very important feature that Java 8 is missing.

        Read Chapter 3 -
            showing different approaches of handing Side-Effects to make a method functional.

        See Chapter 15 -
            AvoidAssertionNullChecksExceptions.java
            ReadXMLFile.java


        (IMP) COMPREHENSION Pattern for Result - Read this concept in Result.java
              COMPREHENSION Pattern for List - Read Chapter 8 (List.java)


    Chapter 8 - Advanced list handling

        - 8.1 The problem with length

            When you need to calculate the length of a list using fold method, then you need to iterate through entire list.

            public <I> static int length(List<I> list) {
              return foldRight(list, 0, listElement -> listSizeIdentity -> 1 + listSizeIdentity);
            }

            If you see here carefully, you are not utilizing a list element. You are just traversing a list and incrementing a value of list size.
            If there are million records, it will reserve so long to calculate the length of a list.
            Instead, you can increment a length by one while inserting a new element in a list.


            There are other operations that can be applied to lists in this way, and among them, several for which the type of the list elements is irrelevant:
            - The hash code of a list can be computed by simply adding the hash codes of its elements. As the hash code is an integer (at least for Java objects!), this operation does not depend upon the object’s type.
            - The string representation of a list, as returned by the toString method, can be computed by composing the toString representation of the list elements. Once again, the actual type of the elements is irrelevant.

            Memoized version of list length:

                private final int length;
                private Cons(A head, List<A> tail) {
                  this.head = head;
                  this.tail = tail;
                  this.length = tail.length() + 1; // Memoizing a length of a list
                }

            One should notice that although adding an element has an increased cost (adding one to the tail length and storing the result), removing an element has zero cost, since the tail length is already memoized.

            Memoization can turn function working in O(n) time (time proportional to the number of elements) into O(1) time (constant time).
            This is a huge benefit, although it has a time cost, making insertion of elements slightly slower.
            Slowing insertion is however generally not a big problem. A much more important problem is the increase in memory space.

            It is up to you to decide if you should memoize some functions of the List class. A few experiments should help to make your decision. Measuring the available memory size just before and after the creation of a list of one million integers shows a very small increase when using memoization.

        - 8.2 Composing List and Result

          This section describes
            - Result<I> headOption()
            - Result<I> lastOption()

          This section describes also functional methods that can convert

              - List<List<I>> to List<I> --- flattenViaFlatMap method (uses flatMap)
              - List<Result<I>> to Result<List<I>> --- flatten_List_of_Result_ViaFlatMap method (uses flattenViaFlatMap)
              - List<Result<I>> to Result<List<I>> --- sequence_using_flatten method (uses flatten_List_of_Result_ViaFlatMap)
              - List<I> to Result<List<O>> --- traverse method (uses sequence_using_flatten)

              flatMap uses fold
              filter uses flatMap (in Chapter 5)
              flatten uses flatMap
              sequence uses flatten
              traverse uses sequence

              These methods are very important. Except flatMap and filter, non of them are provided by Java 8's Stream API.

        - 8.3 Abstracting common usecases

          This section describes
            - zipping the lists (zipWith method)
            - Result<I> getAt(index) ---- Java 8 has skip and findFirst method of Java 8 Stream API, but skip is not functional. it can throw an excetption, where as getAt_ is functional because it wraps exception by Result object.

          - Important Concept: Comprehension pattern for list:
                From Chapter 7, we know that we can use comprehension pattern to create an output from more than one available Result objects.
                You can use it to produce an output from more than one available List objects also.
                But using Comprehension Pattern on two lists are like iterating one list inside another.

                List<Integer> integerList = list(1, 2, 3);
                List<String> stringList = list("a", "b", "c");
                Function<A, Function<B, C>> f = a -> b -> b+"/"+a;

                aList.flatMap(list1Ele -> bList.map(list2Ele -> f.apply(list1Ele).apply(list2Ele))); // comprehension pattern on lists

                O/P: Cons{head=a/3, tail=Cons{head=b/3, tail=Cons{head=c/3, tail=Cons{head=a/2, tail=Cons{head=b/2, tail=Cons{head=c/2, tail=Cons{head=a/1, tail=Cons{head=b/1, tail=Cons{head=c/1, tail=Nil{}}}}}}}}}}


                // pseudo code of comprehension pattern
                List<C> cList = nilList();
                for(a : aList) {
                  for(b : bList) {
                    cList = consList(f.apply(a).apply(b), cList)
                  }
                }
                return cList;


            - Important Concept: when to use fold method and when not to?

                Explicit recursion should be avoided in a method.

                Recursion should be abstracted out using fold method. You can see many example method in List.java any others.
                e.g. sumRecursively and sumViaFoldLeft methods of List.java

                But fold should not be used when you are expecting that final output can be somewhere in between the list because there is no way to stop fold method in between. It iterates entire list.
                e.g. getAt_ and getAt_Using_fold method of List.java.
                If you want to use fold method in this case, then write a new fold method that can stop iterating the list, when result is found (pg 231).

                    public <B> B foldLeft(B identity, B zero, Function<B, Function<A, B>> f) {
                      return foldLeft(identity, zero, this, f).eval();
                    }
                    private <B> B foldLeft(B identity, B zero, List<A> list, Function<B, Function<A, B>> f) {
                      return list.isEmpty() || identity.equals(zero) // this is an extra condition to see whether result is found somewhere in middle of the list.
                          ? identity
                          : foldLeft(f.apply(identity).apply(list.head()), zero, list.tail(), f);
                    }

        - 8.4 Automatic Parallel Processing of list

          Not all operations can be parallelized.
          e.g. Finding the mean of all integers is not something that you can directly parallelize.
          Computing the sum is something that can be easily parallelized, by computing the sums of the sublists, and then computing the sum of the sublist sums.


         What do you need for parallel processing?

         - First of all, you must break the list into sublists. List.java's splitListAt method does that.

         - An ExecutorService to run foldLeft on many subLists in parallel.

         - An additional operation (combiner) allowing you to recompose the results of each parallel computation.

            O parallelFoldLeft(List<I> inputList, O identity, Function<I, Function<O, O>> accumulator, // accumulator is used to foldLeft each subList
                               ExecutorService executorService, // executor service is used to run foldLeft on many subLists in parallel
                               Function <O, Function<O,O>> combiner) // combiner is used to combine the output of foldingLefts of subLists.

            see List.java's parallelFoldLeft

    Chapter 9 - Working with laziness

        This chapter is mainly for understanding the power of laziness using Supplier and how Stream lazily supply the elements of infinite data structure like collection in Java 8.

        9.1 Understanding strictness and laziness
            Laziness is necessary on many occasions. Java does in fact use laziness for constructs like if..then, loops, of try..catch blocks. Without this, a catch block, for example, would be evaluated even in the absence of an exception. Implementing laziness is a must when it comes to providing behavior in case of error, as well as when you need to manipulate infinite data structures.
            Implementing laziness in Java is not fully possible, but we can produce a good approximation using the Supplier class

            public interface Supplier<T> {
              T get();
            }

        9.2 Implementing laziness

            BooleanMethods.java

        9.3 Things you can’t do without laziness

            For Infinite data structure, laziness is must. See Stream.java's repeat, from, iterate methods.

        9.4 Why not use the Java 8 stream? (in this book)

            Java 8 streams were designed with the idea of automatic parallelization in mind. To allow for automatic parallelization, many compromises were made. Many functional methods are missing because they would have made automatic parallelization more difficult. Furhtermore, Java 8 streams are stateful. Once they have been used for some operations, they will have changed their state and are no longer usable. And last, folding Java 8 streams is a strict operation, that causes the evaluation of all elements. For all these reasons, you will define your own streams. After finishing this chapter, you may prefer to use the Java 8 streams. At least, you will fully understand what is missing in the Java 8 implementation.

        9.5 Creating a lazy list data structure

            See Stream.java
            - Memoizing evaluated values
            - Manipulating streams
              reserve, drop, takeWhile, dropWhile etc methods

        9.6 The true essence of laziness

            Stream.java's folding stream examples.

        9.7 Handling infinite streams

            Stream's repeat, from, iterate, fibs, unfold methods

            unfold method - very Important to understand.

        9.8 Avoiding null references and mutable fields

            Stream.java's commented constructors


        Power of laziness using Supplier

            - Look at Stream.java

              if you do
              int from(int i) {
                  return from(i + 1) // infinite loop. It will use multiple stack frames and go into StackOverflow exception.
              }

              once you call Stream.from(1), you cannot move on to next line. It will go in infinite loop that never ends.

              (IMP) Rule of Thumb to stop blowing stack for infinite recursion:
                  If there is no exit condition, then recursive method call will go in infinite loop and which will blow a stack.
                  You can still go in infinite loop by using only one stack frame. Just like how you used TailCall.java, you just need to wrap recursive method call with Supplier and get() on that supplier should happen by a caller of a method (not in the method itself)


              Supplier<Integer> from(int i) {
                  return () -> from(i + 1)
              }

              for(int i=0; i<9; i++) {
                from(0).get(); // you can control to recurse 'from' method only 10 times. Moreover, it will use only 1 stack frame.
              }

              So, you can make a return type as Supplier<O>.

            - Look at Stream.java's Cons.java. It has 'tail' variable wrapped by Supplier.

                private static class Cons<I> extends Stream<I> {
                    private final I head;
                    private final Supplier<Stream<I>> tail;
                    ...
                }

                When do you need to wrap variables with Supplier?
                When you want to evaluate them lazily.
                But when do you need to evaluate them lazily? - Look at from/from_ method

                public static Stream<Integer> from_(int i) {
                    return cons(i, from_(i + 1)); // This will go in in finite loop
                }

                To solve infinite loop problem, as a rule of thumb,
                    - wrap the returned value of a method 'from_' by Supplier
                    Even though this approach works, I would not use this approach because, ideally only recursive method should be wrapped by a Supplier (See Chapter 4's TailCall.java usage)

                    public static Supplier<Stream<Integer>> from_(int i) {
                        return () -> cons(i, from_(i + 1));
                    }

                    - OR   (Better Approach)
                    wrap recursive method call with Supplier (Just like how TailCall.java is used) and get() on that supplier should happen by a caller of a method (not in the method itself)

                    public static Supplier<Stream<Integer>> from(int i) {
                        return cons(i, () -> from(i + 1));
                    }

                    We could have wrapped recursive call by TailCall.getSupplierContainer(from(i + 1)), but TailCall's eval() would call 'from' method indefinitely because there is no exit condition in eval() method.
                    So, instead of using TailCall, you can simply use a Supplier and let caller call 'from' method with some exit condition.

                    Stream<Integer> stream = Stream.from(1);
                    for (int k = 0; k < 5; k++) {
                        System.out.print(stream.head() + ",");
                        stream = stream.tail();
                    }
                    O/P: 1,2,3,4,5,

            - Look at Chapter 13's IO.java's 'forever' methods.

              It has a weird thing

                static <A, B> IO<B> forever1(IO<A> ioa) { // it will blow the stack after a few thousand iterations.
                    IO<B> t = forever1(ioa);
                    return ioa.flatMap(a -> t);
                }

                static <A, B> IO<B> forever2(IO<A> ioa) { // it will blow the stack after a few thousand iterations.
                    Supplier<IO<B>> t = () -> forever2(ioa); // applying a concept of wrapping recursive call by Supplier
                    return ioa.flatMap(a -> t.get()); // you are doing t.get() right in the recursive method, so it is going to be same as forever1 method.
                }

                See the solution in IO.java and then in StackFreeIO.java

            - Look at BooleanMethods.java
              You can make method args as Supplier<I> to evaluate args lazily on demand.

            - Look at Java 8's Stream.java's collect method. It takes Supplier as an input.

                U reduce(U identity,
                        BiFunction<U, ? super T, U> accumulator,
                        BinaryOperator<U> combiner)

                e.g. stream.reduce(new ArrayList(), ..., ...)

                vs

                R collect(Supplier<R> supplier,
                        BiConsumer<R, ? super T> accumulator,
                        BiConsumer<R, R> combiner)

                e.g. stream.collect(() -> new ArrayList(), ..., ...)


                In case of reduce, you use the same identity while reducing entire stream.
                In case of collect, you use different object of identity (supplier.get()) when you use parallelism to reduce a stream.


            - Look at Chapter 4's TailCall class usage.
              It makes the recursive method to use only one stack frame.

            - Look at Chapter 5's List.java
              e.g. foldLeftTailRecursive_LazilyEvaluatingTheOutput method. We are passing 'Supplier<O> identity' instead of 'O identity'.
                Its all output calculation for all recursive calls are delayed till exit condition is met.

        Self created Stream.java vs Java8's Stream.java
            Java 8 streams were designed with the idea of automatic parallelization in mind.
            To allow for automatic parallelization, many compromises were made. Many functional methods are missing because they would have made automatic parallelization more difficult.
            Furthermore, Java 8 streams are stateful. Once they have been used for some operations, they will have changed their state and are no longer usable.
            And last, folding Java 8 streams is a strict operation, that causes the evaluation of all elements.
            For all these reasons, you will define your own streams.
            After finishing this chapter, you may prefer to use the Java 8 streams. At least, you will fully understand what is missing in the Java 8 implementation.



From Functional_Programming_V11_MEAP.pdf book
----------------------------------------------
    Chapter 10 - More Data Handling with Trees

        Tree.java

        This chapter is based on how can you do all tree operations without changing original tree (immutability).
        You create a new tree whenever you need to do any modification like inserting new node, deleting a node etc.


        - You will see a clear advantage of not mutating a tree by comparing
        Immutable Tree, Tree.java's remove method and merge method.
        and
        Mutable Tree, BST.java's deleteNode method and merge method.

        - Immutability is very important to not to have concurrency problems. You don't need to implement locks to avoid concurrency problems.
        A glance on Concurrency and Immutability
        http://tutorials.jenkov.com/java-concurrency/thread-safety-and-immutability.html
        https://www.infoq.com/articles/dhanji-prasanna-concurrency


        DSWAlgorithm.java

        DSW (Day-Stout-Warren) algorithm is to balance imbalanced tree. Tree is already provided to you which is imbalanced.
        In this algorithm, first you make a tree totally imbalanced by creating a linked list from a tree by traversing a tree in-order, and converting linked list back to tree (totally imbalanced tree).
        Using rotation algorithm, rotate a tree to make it Almost Balanced(Balance Factor = Math.abs(height of left subtree-height of right subtree) <= 1).

        Instead of using rotation algorithm, which is a key algorithm for DSW, you can use another technique called 'CreateMinimalBST' from ordered linked list. This is coded in CreateMinimalBST.java.

        DSW algorithm is ok, if tree needs to be balanced few times, but it is not ok if it needs to balanced on each insert/delete of a node. it causes performance issues.
        There re two solutions of real time tree balancing.
        - AVL tree, Red-Black Tree, B-Tree etc are self-balancing (automatically balanced) trees, in which, tree is balanced on insertion/deletion of the node to a tree.
        OR
        - you can say that you will use DSW algorithm, when Balance Factor (Math.abs(height of left subtree - height of right subtree) > some number (may be 10-20 instead of 1).


    Chapter 11 - Solving Real Problems with Advanced Trees

        This chapter describes

        - Red-Black tree (RedBlackTree.java) --- I haven't implemented it though.
        - how can use use a tree for a map instead of an array (MapUsingTree.java)
        - Priority Queue using LeftList Heap (with and without comparable elements) (DefaultHeap.java)
            - For NonComparable elements,
                - you need to provide your own Comparator to DefaultHeap's constructor.
                - For more convenience, Create your own 'static compare(element1, element2, comparator)' method.
        - Result's get,getOrElse,getOrThrow methods should be avoided to use (DefaultHeap.java)


    Chapter 12 - Handling State Mutation In A Functional Way

        This chapter talks about how Random class generates a random number.

        Random class has two constructors
            1. that takes seed from you. It will calculate next number from this seed. If you pass the same seed every time you instantiate Random, it will always return same next number calculated from passed seed.
            2. assigns its own seed based on some long value and System.nanoSecond(). Every time you create Random's instance, seed will be different and so next number from that seed.

        So, if you use Random's instance inside any method using 2nd approach, that method will give you different result each time.
        e.g.
        public interface RNG {
            Tuple<Integer, RNG> nextInt();
        }

         class JavaRNG implements RNG {

            private final Random random;

            private JavaRNG(long seed) {
                this.random = new Random(seed);
            }
            private JavaRNG() {
                this.random = new Random();
            }
            public static RNG rng() {
                return new JavaRNG();
            }
            public static RNG rng(long seed) {
                return new JavaRNG(seed);
            }

            @Override
            public Tuple<Integer, RNG> nextInt() {
                return new Tuple<>(random.nextInt(), this);
            }

         }

         class Generator {
            public static Integer integer(RNG rng) {
                return rng.nextInt();
            }

            public static void main(String[] args) {
                RNG rng = JavaRNG.rng();
                System.out.println(Generator.integer(rng));
            }
         }

         Every time you run this code, Generator.integer(rng) will give you a different result.
         Here, main() is not functional because every time you run it, it will give you different result.
         To make it function, we can do one thing
         Use seed to generate random number and that way Generator.integer(rng) will always get the next int from the seed that you have provided. So, result of it will always be the same.
         RNG rng = JavaRNG.rng(0);

         public static void main(String[] args) {
               RNG rng = JavaRNG.rng(0);
               System.out.println(Generator.integer(rng));
         }

         But if you consider Generator's integer method, it doesn't seem to be functional because every time it calls rng.nextInt(), it changes the stage of rng object.
         Even though, it doesn't seem to be so functional, you can make it somewhat functional, which is accepted ????????????
         If you cannot avoid change of state of input parameter of a function, you should return input parameter also as an output with the actual result of the function.

        class Generator {
            public static Tuple<Integer, RNG> integer(RNG rng) {
                return new Tuple(rng.nextInt(), rng);
            }
            ...
        }

        Now integer method returns Tuple<Integer, RNG> that includes both actual result(next int) and input parameter rng whose state is changed.


        A generic API (pg 358) would be

            After making Generator.integer(rng) method functional, you can replace it with actual Function.
            "Tuple<Integer, RNG> Generator.integer(rng)" method takes rng and returns result with input rng whose state is changed.
            so can we convert it into a Function? - Yes

            Function<RNG, Tuple<Integer, RNG>> run = rng1 -> new Tuple(rng.nextInt(), rng);

            Wouldn’t it be better if we could get rid of the RNG from Function<RNG, Tuple<Integer, RNG>>?
            Is it possible to abstract the RNG handling in such a way that we don’t have to care about it any more?
            Yes
            By inheriting or composing Function<RNG, Tuple<Integer, RNG>>

            Inheritance:
             interface RandomWithInheritance<S, A> extends Function<S, Tuple<A, S>>
             interface Random<A> extends RandomWithInheritance<RNG, A>
             Random<A> extends RandomWithInheritance<RNG, A>

            Composition:
             class RandomWithGenericStateWithComposition<S, A> {
                  protected Function<S, Tuple<A, S>> run; ----------- Function is composed

                  public RandomWithGenericStateWithComposition(Function<S, Tuple<A, S>> run) {
                      this.run = run;
                  }
             }

             class RandomWithComposition<A> extends RandomWithGenericStateWithComposition<RNG, A>

        Generic state handling (pg 364)

             Making RNG also generic in above example.

             - inheritance

                public interface CustomizedRandomWithState<S, A> extends Function<S, Tuple<A, S>>

             - composition

                public class CustomizedRandomWithState<S, A> {
                    public Function<S, Tuple<A, S>> run;

                    public State(Function<S, Tuple<A, S>> run) {
                        this.run = run;
                    }

                }


    Chapter 13 - Functional Input/Output

        13.1 Applying effects in Context (pg 375 Chapter 13)

            Very important concept of functional programming:

            Optional<Integer> intOptional = …

            You don't apply an action on the extracted value (e.g. intOptonal.get().flatMap(….)).
            You don’t extract the value out of the context and apply effect on it.  It may lead you to ERRORS   .
            It’s better to apply effects on the context, so that you have a full control over the result.

            class Optional {
                ...
                 public<U> Optional<U> flatMap(Function<? super T, Optional<U>> mapper) {
                        Objects.requireNonNull(mapper);
                        if (!isPresent())
                            return empty();  — you have full control over the result
                        else {
                            return Objects.requireNonNull(mapper.apply(value));
                        }
                 }
                 ...
            }

            anotherOptionalValue = optionalValue.flatMap(...);
            anotherOptionalValue.map(...) /// you are operating on returned value inside the context Optional only.

            This is very neat and safe. No bad things can happen, no exception can be thrown. This is the beauty of functional programming: a program that will always work, whatever data we use as input.

            See ResultTest.java
            It is a perfect example of a very nice feature provided by Functional Programing - working on the Context (wrapper of the output value) instead of the output value itself.

            - The Result type we developed in chapter 7 is such a computational context, allowing us to use a function that could produce an error in a safe, error-free way.
            - The Option type (Java 8's Optional) from chapter 6 is also a computational context used to safely apply functions that could sometimes (meaning for some arguments) produce no data.
            - The List class we studied in chapters 5 and 8 is a computational context, but rather than dealing with errors, it allows the use of functions working on single elements in the context of a collection of elements. Incidentally, it also deals with the absence of data that is represented by an empty list.

            Side Effect vs Effect (pg 376)

                This section describes the difference between side-effect and effect and how to use functional interface Effect (Java 8's Consumer) instead of Function to output the data.

                We defined pure functions as functions without any observable side effects.
                An effect is anything that can be observed from outside the program.
                The role of a function is to return a value. A side effect is anything observable from the outside of the function beside the returned value. It is called a “side effect” because it comes in addition to returning this value.

                An effect (without “side”) is like a side effect, but being the main (and generally unique) role of a program.
                Functional programming is about writing programs with pure functions (with no side effects) and pure effects handled in a functional way.


                When you are using a Function<I, O> that returns some value. Apart from calculating returned value from input and returning it, if you are doing something else like System.out.println(returned value) or putting returned value in to database, then these kind of effects are observable by outside programs. So, they are not just effects, but they are "side effects".
                To apply an effect, there is a special functional interface available in Java called 'Consumer', which has 'void accept(T t)'. It takes an input, but doesn't return anything.
                'Consumer' should actually be named as 'Effect', but name doesn't matter as far you understand its usage.

                So now you understand, why functional programming has to different interfaces 'Function' and 'Effect (Consumer)'.


                (pg 380)
                In Result.java's

                    Success class has

                        void forEachOrThrow(Effect<V> ef) {
                            forEach(ef);
                        }

                    Empty class has

                        public void forEachOrThrow(Effect<V> ef) {
                            throw exception;
                        }

                    Failure class has

                        public void forEachOrThrow(Effect<T> c) {
                            throw exception;
                        }


                We might want to do something less radical than throwing an exception. For example, we might want to log the exception before going on.
                Logging is not very functional, because logging is generally a side effect. No programs are written with logging as their main goal.
                As you see, applying an effect with a method like forEach is breaking the functional contract. This is not a problem by itself. But when you log, you are suddenly ceasing to be functional. This is in some respect the end of a functional program. After the effect is applied, you are ready to start another new functional program. However, the frontier between imperative and functional programming will not be very clear if your application logs in every method. But as logging is generally a requirement, at least in the Java world, you may want to have a clean way to do it.

                We have no simple way to log the exception in case of a failure. What we need is to transform a failure into a success of its exception. For this, we need a direct access to this exception, which can’t be done from outside the Result context.

                    The Failure implementations just return a Success of the contained exception or of its message:

                        public Result<String> forEachOrFail(Effect<T> c) {
                          return success(exception.getMessage()); // so we are avoiding throwing an exception from the Functional Context (Result class). We let client decide what to do with that exception.
                        }
                        public Result<RuntimeException> forEachOrException(Effect<T> c) {
                          return success(exception);
                        }

                    Success class has

                        public Result<String> forEachOrFail(Effect<T> e) {
                          e.apply(this.value);
                          return empty();
                        }

                    Empty class has

                        public Result<String> forEachOrFail(Effect<T> c) {
                          return empty();
                        }


                    These methods, although not functional, greatly simplify the use of Result values:

                    public class ResultTest {

                      public static void main(String... args) {

                            Result<Integer> ra = Result.success(4);
                            Result<Integer> rb = Result.success(0);

                            Function<Integer, Result<Double>> inverse = x -> x != 0
                                ? Result.success((double) 1 / x)
                                : Result.failure("Division by 0");

                            Result<Double> rt1 = ra.flatMap(inverse);
                            Result<Double> rt2 = rb.flatMap(inverse);

                            System.out.print("Inverse of 4: ");
                            rt1.forEachOrFail(System.out::println).forEach(ResultTest::log);

                            System.out.print("Inverse of 0: ");
                            rt2.forEachOrFail(System.out::println).forEach(ResultTest::log); // client of the functional method forEach/forEachOrFail is deciding to log, not forEach/forEachOrFail itself.
                          }

                          private static void log(String s) {
                            System.out.println(s);
                          }

                    }


                Why logging is dangerous?
                    In functional programming, you will not see much logging. This is because functional programming makes logging mostly useless.
                    Functional programs are built by composing pure functions, meaning functions that always return the same value given the same argument. So there can’t be any surprises.
                    On the other hand, logging is ubiquitous in imperative programming because imperative programs are programs for which you can’t predict the output for a given input. Logging is like saying “I don’t know what the program might produce at this point, so I write it in a log file. If everything goes right, I will not need this log file. But if something goes wrong, I will be able to look at the logs to see what the program state was at this point.” This is nonsense.
                    In functional programing, there is no need for such logs. If all functions at correct, which can generally be proved, we don’t need to know the intermediate states. And furthermore, logging is often made conditional, which means that some logging code will only be executed in very rare and unknown states. This code is often untested. If you have ever seen a Java imperative program that worked fine in INFO mode suddenly break when run in TRACE mode, you know what I mean.


                (pg 382)
                As we saw, outputting data occurs at the end of the program, once the result is computed. This allows most of the program to be written functionally, with all the benefits brought by this paradigm. Only the output part is nonfunctional.
                e.g.
                    stream.map(function).filter(predicate).map(function).forEach(Consumer)

                    map takes function - it should just return the value based on input and should not be creating any side-effect like mutating input arg, outputting to console, db actions, throwing an exception etc
                    filter takes predicate - Predicate is also one type of function (Function<I, Boolean>) that returns Boolean output. predicate anyway should not be creating any side-effect. it is quite logical.
                    Methods like forEach, ifPresent takes Consumer - Consumer is also one type of function (Function<I, Void>) returning no output. So, it is not a pure Function. Consumer is like an effect as described above and its main job is not to return any value, but taking care of effects (side-effects). As taking create of side-effects is a job of consumer, it cannot be called side-effects from he scope of consumer. It is called effect only. And so it is ok for Consumer to be non-functional.

                Important Concept: (How to make a class a class with Functional Context?)
                    Any method of a Functional Context class (e.g. Result) should not create any side-effect.
                    Any side-effect should be handed over to client by letting client pass an Effect (Consumer) to methods.
                    Logging is worst in any functional context. It must not happen.
                    It should not even throw an exception. Exception should also be wrapped with Result class and let client decide what he wants to do with that.
                    see Result's Failure class' forEachOrFail/forEachOrException methods.

                    e.g. Result's Failure class
                    Having this kind of method in functional context makes the context and method non-functional.
                    public void forEachOrThrow(Effect<V> ef) {
                        throw exception;
                    }

                    To make it functional,

                    public Result<RuntimeException> forEachOrException(Effect<V> ef) {
                        return success(exception);
                    }

                    public Result<String> forEachOrFail(Effect<V> c) {
                        return success(exception.getMessage());
                    }


        13.2 Reading Data (Inputting Data) (pg 382)

            Previous section showed how can we output the Data in a functional way.
            This section shows how can we Input the Data in a functional way.

            It shows how can we read data from Console, File etc using unfold method.
            See ConsoleReader.java, FileReader.java and TestReader.java

            See AbstractReader.java
            It shows how to read the data and wrapping it under the context (Result) and return a context instead of returning null or throwing exception.

            public Result<Tuple<String, Input>> readString() {
                try {
                    String s = reader.readLine();  // s is output here
                    return s.length() == 0
                            ? Result.empty()    // Take care of null or empty output
                            : Result.success(new Tuple<>(s, this)); // Wrap the result under context
                } catch (Exception e) {
                    return Result.failure(e);  // Wrap the exception under context
                }
            }

        13.3 Really Functional Input/Output (pg 390)  (VERY IMP)

            This section shows how to create a library that doesn't care about input and output.
            Library produces the return value as some function and client program worries about inputting the parameters to it and outputting its result after running it.

            e.g.
            static void sayHello(String name) {
               System.out.println("Hello, " + name + "!");
            }

            static void sayHello(Effect<String> effect, String name) {
                effect.apply(name);
            }

            sayHello(name -> System.out.println("Hello, " + name + "!"), "Tushar");

            or

            static Supplier<String> sayHello(String name) {
                return () -> System.out.println("Hello, " + name + "!");
            }

            or

            This can be written more as a Library (Functional Context)) method as follows. Client will reserve care of inputting the value, running the function and taking care of the output coming from the function. Client will decide what to do with that output.

            static Function<String, String>  sayHello() {
                return (name) -> "Hello, " + name + "!";
            }

            System.out.println(sayHello().apply("Tushar"));   // This is the best

            Other qualities of Functional Library(Class):  (See IO.java)

            - Parameterized
            - Should be able to have EMPTY instance
            - Combinable - should have an add method
            - should have map/flatMap methods
            - Good to have
                - unit method
                - repeat method
                - forever method  ---- Very interesting method. It shows how to tackle infinite recursive method calls.


    Chapter 14 (Sharing mutable state with actors)
    TBD

    Chapter 15 (Solving Common Problems Functionally)

        How to convert imperative method to a functional method?
        Steps:
        1. ReadXMLFile.java - Break down imperative methods in a small methods returning Result (not taking Result as arg).
        2. ReadXMLFile.java - Make methods as generic as possible (e.g. processFinalResult method. It could have taken List<String> as an arg, but we made it taking List<T>)
        3. ReadXMLFile.java - Use Comprehension pattern to get the result by combining these methods. (This is possible because of step 1)
        4. AvoidAssertionNullChecksExceptions.java - Instead of using assertions, null checks, throwing exceptions etc that can create side-effects in a method, use Supplier to return a side-effect.
        5. Util.java, PropertyReader.java - Avoid type casting to avoid Exceptions.

        15.1 Using assertions to validate

            Important concept: How to make a method functional that has a side-effect?
            How to avoid assertions (validations) of input parameters, null checks and throwing exceptions?

            Instead of throwing an exception from a method, return a Supplier that throws an exception

            See AvoidAssertionNullChecksExceptions.java

        15.2 Reading properties from file

            How to read Properties functionally?

            See PropertyReader.java

        15.3 Converting an imperative program: the XML reader

            How to convert imperative method into testable and functional method?

            See ReadXMLFile.java

           You should use Result/Optional as a
            - returned value of a method
            - constructor argument
            - field variables

            You should not use Result/Optional as a
            - method argument   (IMP)

            Important Concept: Why shouldn't we use Result as method argument?

            If you use it as method argument, you won't be able to use COMPREHENSION pattern.
            See ReadXMLFile.java


    Other Stuff

        Functional Programming Best Practices
            http://www.baeldung.com/java-8-lambda-expressions-tips


        Rule of variable names in Lambda
            http://www.informit.com/articles/article.aspx?p=2303960&seqNum=7
                It is illegal to declare a parameter or a local variable in the lambda that has the same name as a local variable.

                int first = 0;
                Comparator<String> comp = (first, second) -> first.length() - second.length(); // Error: Variable first already defined

                As another consequence of the “same scope” rule, the this keyword in a lambda expression denotes the this parameter of the method that creates the lambda. For example, consider

                public class Application() {
                    public void doWork() {
                        Runnable runner = () -> { ...; System.out.println(this.toString()); ... };
                        ...
                    }
                }


       http://www.drdobbs.com/jvm/lambda-expressions-in-java-8/240166764?pgno=3
            In a lambda expression, you can only reference variables whose value doesn't change. For example, the following is illegal:

            public static void repeatMessage(String text, int count) {
                 Runnable r = () -> {
                    while (count > 0) {
                       count--; // Error: Can't mutate captured variable
                       System.out.println(text);
                       Thread.yield();
                    }
                 };
                 new Thread(r).start();
              }

             There is a reason for this restriction. Mutating variables in a lambda expression is not thread-safe. Consider a sequence of concurrent tasks, each updating a shared counter.

            int matches = 0;
              for (Path p : files)
                 new Thread(() -> { if (p has some property) matches++; }).start(); // Illegal to mutate matches

            If this code were legal, it would be very, very bad. The increment matches++ is not atomic, and there is no way of knowing what would happen if multiple threads execute that increment concurrently.


           List<Path> matches = new ArrayList<>();
           for (Path p : files)
              new Thread(() -> { if (p has some property) matches.add(p); }).start(); // Legal to mutate matches, but unsafe

           Note that the variable matches is effectively final. (An effectively final variable is a variable that is never assigned a new value after it has been initialized.) In our case, matches always refers to the same ArrayList object. However, the object is mutated, and that is not thread-safe. If multiple threads call add, the result is unpredictable.

           Default Method Inheritance rules

               1. Superclasses win. If a superclass provides a concrete method, default methods with the same name and parameter types are simply ignored.
               2. Interfaces clash. If a super interface provides a default method, and another interface supplies a method with the same name and parameter types (default or not), then you must resolve the conflict by overriding that method.

                interface Named {
                     default String getName() { return getClass().getName() + "_" + hashCode(); }
                }

                interface Person {
                     long getId();
                     default String getName() { return "John Q. Public"; }
                }

                class Student implements Person, Named {
                     ...
                }

                The class inherits two inconsistent getName methods provided by the Person and Named interfaces. Rather than choosing one over the other, the Java compiler reports an error and leaves it up to the programmer to resolve the ambiguity.
                Simply provide a getName method in the Student class. In that method, you can choose one of the two conflicting methods, like this:

                class Student implements Person, Named {
                     public String getName() { return Person.super.getName(); }
                     ...
                }

                Now assume that the Named interface does not provide a default implementation for getName:

                interface Named {
                     String getName();
                }
                Can the Student class inherit the default method from the Person interface? This might be reasonable, but the Java designers decided in favor of uniformity. It doesn't matter how two interfaces conflict. If at least one interface provides an implementation, the compiler reports an error, and the programmer must resolve the ambiguity.



 */
public class FunctionalProgrammingInJavaBook {

    private int a = 0;


    private List<Double> operation(List<Double> numbers, Function<Double, Double> fx) {

        List<Double> result = new ArrayList<>();

        for (Double number : numbers) {
            result.add(fx.apply(number));
        }

        return result;
    }

    private Supplier<Optional<Properties>> readProperties(String configFileName) {
        try (InputStream inputStream = getClass().getClassLoader()
                .getResourceAsStream(configFileName)) {
            Properties properties = new Properties();
            properties.load(inputStream);
            return () -> Optional.ofNullable(properties); // --- return Result
        } catch (Exception e) {
            return () -> {
                throw new RuntimeException(e);
            };
        }
    }

    private Optional<String> readProperty(String propertyFile, String propertyName) {
        Supplier<Optional<Properties>> readPropertiesResult = readProperties(propertyFile);
        try {
            Optional<Properties> properties = readPropertiesResult.get();
            Optional<String> some_property = properties.map(properties1 -> (String) properties1.get(propertyName));

            return some_property;
        } catch (Exception e) {
            System.out.println("exception thrown");
            return Optional.empty();
        }
    }

    public static void main(String[] args) {

        FunctionalProgrammingInJavaBook obj = new FunctionalProgrammingInJavaBook();

        Optional<String> some_name_value = obj.readProperty("some location", "some_name");
        System.out.println(some_name_value.orElseGet(() -> "NONE")); // O/P: NONE


        // Try function currying and partially applied functions
        obj.tryCurriedFunctionAndPartiallyAppliedFunctions();


        System.out.println(FunctionalProgrammingInJavaBook.intRangeWithWhileLoop(0, 10));
        System.out.println(FunctionalProgrammingInJavaBook.genericRange(0, i -> i < 10, i -> i + 1));

        System.out.println(FunctionalProgrammingInJavaBook.intRangeWithForLoop(0, 10));
        System.out.println(FunctionalProgrammingInJavaBook.genericRangeWithForLoop(0, i -> i < 10, i -> i + 1));

        // Use of identity function
        Function<Double, Double> square = number -> number * number;
        Function<Double, Double> half = number -> number * 2;
        obj.operation(Arrays.asList(10D, 4D, 12D), square);
        obj.operation(Arrays.asList(10D, 4D, 12D), half);
        obj.operation(Arrays.asList(10D, 4D, 12D), Function.identity());

    }

    // 'this' inside lambda vs anonymous class
    private void thisReferenceTest() {

        Function<Integer, Integer> tempLambda = i -> {
            //static int n = 0; // you cannot have static members inside lambda just like inner classes.
            int n = 0;
            class Add { // You can define a class also inside lambda, just like class inside anonymous class.
            }

            return (i + n) * this.a; // you cannot say this.n because 'this' refers to enclosing class' instance in case of lambda
        };

        Function<Integer, Integer> tempAnonymousClass = new Function<Integer, Integer>() {
            //static int n = 0; // you cannot have static members inside inner classes.
            int n = 0;

            class Add {

            }

            @Override
            public Integer apply(Integer i) {
                return (i + this.n) * FunctionalProgrammingInJavaBook.this.a; // you can use this.n because 'this' refers to inner class' reference here. To access enclosing class' reference you need to use EnclosingClass.this.member
            }
        };

    }

    // VERY IMPORTANT
    // You can easily convert imperative (non-functional method with while/for loop) to a generic method very easily by using Function for condition and body of the loop.
    // Abstract out the condition and the code in the loop as much as you can and pass those abstracted functions to the method.
    public static List<Integer> intRangeWithWhileLoop(int start, int end) {
        List<Integer> result = new ArrayList<>();
        int temp = start;
        while (temp < end) {
            result.add(temp);
            temp = temp + 1;
        }
        return result;
    }

    public static void testGenericRange1() {
        Integer start = 0;
        Integer end = 10;
        Supplier<List<Integer>> supplier = ArrayList::new;
        Predicate<Integer> predicate = (t) -> t < end;
        Function<Integer, Integer> function = (a) -> a + 1;

        genericRange1(start, supplier, predicate, function);
    }

    public static void testGenericRange2() {
        Integer start = 0;
        Integer end = 10;
        //Supplier<List<Integer>> supplier = ArrayList::new;
        List<Integer> result = new ArrayList<>();
        Predicate<Integer> predicate = (t) -> t < end;
        Function<Integer, Integer> function = (a) -> {
            result.add(a);
            return a + 1;
        };

        genericRange2(start, predicate, function);
    }

    private static <T> void genericRange2(T start, Predicate<T> predicate, Function<T, T> function) {
        T temp = start;
        while (predicate.test(temp)) {
            temp = function.apply(temp);
        }
    }

    private static <T> List<T> genericRange1(T start, Supplier<List<T>> supplier, Predicate<T> predicate, Function<T, T> function) {
        List<T> result = supplier.get();
        T temp = start;
        while (predicate.test(temp)) {
            result.add(temp);
            temp = function.apply(temp);
        }
        return result;
    }


    private static <T> List<T> genericRange(T start, Function<T, Boolean> condition, Function<T, T> f) {
        List<T> result = new ArrayList<>();
        T temp = start;

        while (condition.apply(temp)) {
            result.add(temp);
            temp = f.apply(temp);
        }
        return result;
    }

    public static List<Integer> intRangeWithForLoop(int start, int end) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < end; i++) {
            result.add(i);
        }
        return result;
    }

    public static <T> List<T> genericRangeWithForLoop(T start, Function<T, Boolean> condition, Function<T, T> f) {
        List<T> result = new ArrayList<>();
        for (T i = start; condition.apply(i); i = f.apply(i)) {
            result.add(i);
        }
        return result;
    }


    // This is a full function because to evaluate 'f', you have all input parameters available
    public static Integer full(Integer x, Integer y, BiFunction<Integer, Integer, Integer> f) {
        return f.apply(x, y);
    }

    // This is a partial function because to evaluate 'f', you do not have all input parameters available.
    // You have only one parameter available, so you can evaluate this method only partially
    // This is how you write partial function.
    public static Function<Integer, Integer> partial(Integer x, BiFunction<Integer, Integer, Integer> f) {
        return (y) -> f.apply(x, y);
    }

    private void tryCurriedFunctionAndPartiallyAppliedFunctions() {

        /*
        FUNCTIONS OF SEVERAL ARGUMENTS:
        There is no such thing like function can reserve several arguments. Function takes only one argument.
        In this example, we have broken down a function taking two arguments into two functions. Java 8 Function works exactly like that.

        f(x,y) = x + (y * 2)
        f(x)(y)= x + (y * 2)
        f(1)(y) = g(y) = 1 + (y * 2)
        g(2) = 1 + (2 * 2)= 5

        It means that f(x) is returning g(y) and g(y) works on both x and y. Return value of g(y) becomes a return value of f(x).
         */

        System.out.println("Trying Partially Applied Function...");
        {
            Integer i1= 5;
            // partially applied function
            Function<Integer, Integer> partial = partial(i1, (input1, input2) -> input1 + input2);

            Integer i2= 7;
            Integer finalResult = partial.apply(i2);

            // fully applied function
            full(i1, i2, (input1, input2) -> input1 + input2);
        }

        System.out.println("Trying function currying...");
        {
            BiFunction<Integer, Integer, Integer> biFunction = (x, y) -> x + y;
            System.out.println(biFunction.apply(1, 2));

            // is same as below curried form. Internally BiFunction converts it into curried form only.

            /*

            f(3, 4, 5) = 3 * 4 + 5 = 17
            we may apply the arguments 3, 4, 5 at the same time and get:

            But we may also apply only 3 and get:
            f(3, y, z) = g(y, z) = 3 * y + z

            We have now a new function g, taking only two arguments. We can curry again this function, applying 4 to y:
            g(4, z) = h(z) = 3 * 4 + z

            Currying allows to turn a function that expects two arguments into a function that expects only one, and that function returns a function that expects the second argument.
            Creating basically a chain of functions.

            Why Currying is important?
            --------------------------
            Answer: code reuse

            In below example, you can reuse f1 for different values of i2.

            */
            Function<Integer,
                    Function<Integer, Integer>> curriedFunction =
                    new Function<Integer, Function<Integer, Integer>>() {

                        @Override
                        public Function<Integer, Integer> apply(final Integer i1) {
                            // x = x * 2   ----- this is not allowed because you are using x in inner Function. So, x has to be final.
                            return new Function<Integer, Integer>() {
                                @Override
                                public Integer apply(Integer i2) {
                                    return i1 + i2;   // variables used in lambda expression have to be final
                                }
                            };
                        }
                    };

            // Evaluating curried function using partially applied functions
            // what does it mean?
            // Let's say, when you need to use 'curriedFunction', you have only i1 available. you don't have i2 available.
            // Later on, you will have i2 available.
            // so for now you apply 'curriedFunction' with partial input (only i1)
            int i1 = 1;
            Function<Integer, Integer> f1 = curriedFunction.apply(i1);

            int i2 = 2;
            Integer result = f1.apply(i2);
            System.out.println("Result of evaluated curried function: " + result); //3

        }
        // is same as
        {
            // i1
            Function<Integer,
                    // i2
                    Function<Integer,
                            Integer>>
                    // apply method of last function can access i1 and i2. here that apply method is doing i1+i2
                    curriedFunction = i1 -> i2 -> i1 + i2;// i1 -> i2 -> i1+i2;

            // Evaluating curried function using partially applied functions
            int i1 = 1, i2 = 2;
            Function<Integer, Integer> f1 = curriedFunction.apply(i1);
            Integer result = f1.apply(i2);
            System.out.println("Result of evaluated curried function: " + result); //3

        }

        // Function Currying and Partially Applied Functions
        // Curried function can be evaluated using partially applied functions
        // https://dzone.com/articles/partially-applied-functions-in-java
        {

            // Evaluating curried function using partially applied functions
            {
                {
                    // curried function
                    // i1
                    Function<Integer,
                            // i2
                            Function<Integer,
                                    //bof
                                    Function<BinaryOperator<Integer>,
                                            Integer>>>
                            // apply method of last function can access i1, i2 and bof. here that apply method is doing 'bof.apply(i1,i2)'
                            someComputation = i1 -> i2 -> bof -> bof.apply(i1, i2);

                    // Evaluating this curried function using partially applied functions
                    int i1 = 10, i2 = 20;
                    BinaryOperator<Integer> bof = (i11, i22) -> i11 + i22;

                    Function<Integer, Function<BinaryOperator<Integer>, Integer>> firstFunction = someComputation.apply(i1);
                    Function<BinaryOperator<Integer>, Integer> secondFunction = firstFunction.apply(i2);
                    final Integer finalResult = secondFunction.apply(bof);
                    System.out.println("Result of evaluated curried function: " + finalResult); // 30
                }

                // above curried functions can be written in this way also (imperative programming - without lambda)
                {
                    Function<Integer, Function<Integer, Function<BinaryOperator<Integer>, Integer>>> someComputation = new Function<Integer, Function<Integer, Function<BinaryOperator<Integer>, Integer>>>() {
                        @Override
                        public Function<Integer, Function<BinaryOperator<Integer>, Integer>> apply(final Integer i1) {
                            return new Function<Integer, Function<BinaryOperator<Integer>, Integer>>() {
                                @Override
                                public Function<BinaryOperator<Integer>, Integer> apply(final Integer i2) {
                                    return new Function<BinaryOperator<Integer>, Integer>() {
                                        @Override
                                        public Integer apply(BinaryOperator<Integer> binaryOperator) {
                                            return binaryOperator.apply(i1, i2);
                                        }
                                    };
                                }
                            };
                        }
                    };

                    // Evaluating this curried function using partially applied functions
                    int i1 = 10, i2 = 20;
                    BinaryOperator<Integer> bof = (i11, i22) -> i11 + i22;

                    Function<Integer, Function<BinaryOperator<Integer>, Integer>> firstFunction = someComputation.apply(i1);
                    Function<BinaryOperator<Integer>, Integer> secondFunction = firstFunction.apply(i2);
                    final Integer finalResult = secondFunction.apply(bof);
                    System.out.println("Result of evaluated curried function: " + finalResult); // 30
                }
            }

        }

        // another example of function currying and partially applied function
        // from Functional Programming in Java book (p.g. 36).
        {
            // This is very confusing to understand
            {
                //f1
                Function<Function<Integer, Integer>,
                        //f2
                        Function<Function<Integer, Integer>,
                                //i1
                                Function<Integer,
                                        // this function's apply method will be able to use f1,f2,i1
                                        Integer>>>
                        compose =
                        f1 -> f2 -> i1 ->
                                // returned value of last Function's apply method
                                (f1.apply(f2.apply(i1)));

                // Evaluating this curried function using partially applied functions
                int i1 = 10;
                Function<Integer, Integer> f2 = intValue -> intValue * intValue;// 100
                Function<Integer, Integer> f1 = f2Result -> i1 + f2Result; // 110
                System.out.println("Result of evaluated curried function: " + compose.apply(f1).apply(f2).apply(i1)); // 110

            }
            {
                // above curried functions can be written in this way also (imperative programming - without lambda)
                Function<Function<Integer, Integer>,
                        Function<Function<Integer, Integer>,
                                Function<Integer, Integer>>> compose =
                        new Function<Function<Integer, Integer>, Function<Function<Integer, Integer>, Function<Integer, Integer>>>() {
                            @Override
                            public Function<Function<Integer, Integer>, Function<Integer, Integer>> apply(final Function<Integer, Integer> f1) {
                                return new Function<Function<Integer, Integer>, Function<Integer, Integer>>() {
                                    @Override
                                    public Function<Integer, Integer> apply(final Function<Integer, Integer> f2) {
                                        return new Function<Integer, Integer>() {
                                            @Override
                                            public Integer apply(Integer i1) {
                                                return f1.apply(f2.apply(i1));
                                            }
                                        };
                                    }
                                };
                            }
                        };

                // Evaluating this curried function using partially applied functions
                int i1 = 10;
                Function<Integer, Integer> f2 = intValue -> intValue * intValue;// 100
                Function<Integer, Integer> f1 = f2Result -> i1 + f2Result; // 110
                System.out.println("Result of evaluated curried function: " + compose.apply(f1).apply(f2).apply(i1)); // 110

            }


        }
        // Function's andThen operation
        // one function converts T to U and another converts U to V, then you can chain them using andThen.
        // output is a Function<T, V>
        {
            Function<Integer, String> intToString = a -> "" + a;
            Function<String, Double> strToDouble = s -> Double.valueOf(s);
            Function<Integer, Double> integerToStringToDoubleFunction = intToString.andThen(strToDouble);
            integerToStringToDoubleFunction.apply(1);
        }
        // Function's compose operation
        // one function converts V to U and another converts U to T, then you can chain them using andThen.
        // output is a Function<V, T>
        {
            Function<Double, Integer> intToString = d -> new Integer("" + d);
            Function<String, Double> strToDouble = s -> Double.valueOf(s);
            Function<String, Integer> stringIntegerFunction = intToString.compose(strToDouble);
            stringIntegerFunction.apply("1.0");

        }

    }
}
