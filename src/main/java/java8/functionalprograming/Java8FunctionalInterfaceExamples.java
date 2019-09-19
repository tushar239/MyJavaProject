package java8.functionalprograming;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author Tushar Chokshi @ 5/15/16.
 */
/*

    Java 8 interfaces can have methods with body. This methods can be of two types
         - default  (normally abstract methods are static in interface, but default methods are not)
         - static

    Interface Default Methods:-
        http://www.tutorialspoint.com/java8/java8_default_methods.htm

        http://www.journaldev.com/2752/java-8-interface-changes-static-methods-default-methods-functional-interfaces
        (IMP)
        - Default methods will help us in avoiding utility classes, such as all the Collections class method can be provided in the interfaces itself.
        - Default methods will help us in removing base implementation classes, we can provide default implementation and the implementation classes can chose which one to override.
        - One of the major reason for introducing default methods is to enhance the Collections API in Java 8 to support lambda expressions.
        - If any class in the hierarchy has a method with same signature, then default methods become irrelevant. A default method cannot override a method from java.lang.Object. The reasoning is very simple, it’s because Object is the base class for all the java classes. So even if we have Object class methods defined as default methods in interfaces, it will be useless because Object class method will always be used. That’s why to avoid confusion, we can’t have default methods that are overriding Object class methods.

    Interface Static Methods:-
          http://www.journaldev.com/2752/java-8-interface-changes-static-methods-default-methods-functional-interfaces
          (IMP)

          Static methods are similar to default methods except that we can’t override them in the implementation classes. This feature helps us in avoiding undesired results incase of poor implementation in child classes.

          - Interface static method helps us in providing security by not allowing implementation classes to override them.
          - Interface static methods are part of interface, we can’t use it for implementation class objects.
          - Interface static methods are good for providing utility methods, for example null check, collection sorting etc.
          - We can use static interface methods to remove utility classes such as Collections and move all of it’s static methods to the corresponding interface, that would be easy to find and use.
          - We can’t define static methods for Object class methods, we will get compiler error as “This static method cannot hide the instance method from Object”. This is because it’s not allowed in java, since Object is the base class for all the classes and we can’t have one class level static method and another instance method with same signature.

    Functional Interface:-
        @FunctionalInterface is a marker interface that marks an interface as Functional Interface.
        Conceptually, a functional interface has exactly
            - one abstract method
            - zero or more static methods with body
            - zero or more default methods with body
            - (My observation) you can add equals,hashCode and toString methods also

        It is not mandatory to have @FunctionalInterface annotation to make an interface Functional Interface.
        As far as, an interface has only one abstract method, it is a Functional Interface.

        java.lang.Runnable is an example of a Functional Interface.
        There is ONLY ONE method void run() declared in Runnable interface.
        It can have many 'default' methods that can have body unlike to traditional interfaces.

        We use Anonymous inner classes to instantiate objects of functional interface.
        With Lambda expressions

        Runnable r = () -> System.out.println("hello world");

        Note that instances of functional interfaces can be created with
            lambda expressions
            method references
            constructor references

    Consumer, BiConsumer, DoubleConsumer, ObjDoubleConsumer etc Functional Interfaces:-
        Consumer is an inbuilt functional interface that has one arg method 'void accept(T t)' that returns nothing.
        BiConsumer is an inbuilt functional interface that has two args method 'void accept(T t, B b)' that returns nothing.
        DoubleConsumer has 'void accept(double value)'. Likewise, LongConsumer, IntConsumer etc.
        ObjDoubleConsumer has 'void accept(T t, double value)'. Likewise, ObjLongConsumer, ObjIntConsumer etc.

        Consumer can be chained using
            andThen(Consumer)

    Supplier, DoubleSupplier etc Functional Interfaces:-
        Supplier is an inbuilt functional interface that has zero arg method 'T get()' that returns something that you say.

    Predicate, BiPredicate, DoublePredicate etc Functional Interfaces:-
        Predicate is an inbuilt functional interface that has one arg method 'boolean test(T t)' that returns boolean.
        Predicates can be chained using
            negate() - Returns a predicate that represents the logical negation of this predicate
            and(predicate) - multiple predicates can be combined using and/or
            or(predicate)

    Function, BiFunction, DoubleFunction, ToDoubleFunction, ToDoubleBiFunction, DoubleToIntFunction, ToLongFunction,... etc Functional Interfaces:-
        Function is an inbuilt functional interface that has one arg method 'R apply(T t)' that returns some other value that you provide.

        UnaryOperator, IntUnaryOperator, BinaryOperator etc Functional Interface:- (SPECIAL)
            UnaryOperator is a special Function that outputs the value which is of same type as input.
            It has a special method 'identity' that outputs the value same as input value.

            e.g.
            UnaryOperator<String> i  = (x)-> x.toUpperCase();
            System.out.println(i.apply("java2s.com"));


        BinaryOperator, DoubleBinaryOperator etc Functional Interface:- (SPECIAL)
            It extends Function<T,T,T>
            It is mainly there with min and max functions that compares two args and returns one of them.


        ToIntFunction
            It takes any object as an input and outputs int.

    All Types of inbuilt Functional Interfaces
    http://www.tutorialspoint.com/java8/java8_functional_interfaces.htm

    S           C               P           F
    |           |               |           |
    DS      BiC,DC,ODC       BiP,DP     BiF,DF,...,BinaryOperatorFunction,UnaryOperatorFunction


    Composition:-
        Composition allows applying lambda expressions one after another. There are two methods:
        e.g. Function interface has following methods
        Function compose(Function before) - Function func3 = func1.compose(func2); func3.apply(...) - output of func1 becomes an input to func2.
        Function andThen(Function after) - Function func3 = func1.andThen(func2); func3.apply(...) - func1 is called first and and then func2.

    Identity Function:-
        Function interface has a static method called 'identity' that returns a Function interface.
        Identity Function's output is same as input.
        static <T> Function<T, T> identity() {
            return t -> t;
        }

    forEach method:-
        You can iterate a collection using forEach method of a collection instead a tradition for loop.
        collection.forEach((element) -> {...})
        forEach method takes Consumer functional interface as an argument.

*/

public class Java8FunctionalInterfaceExamples {

    public static void main(String[] args) {
        Java8FunctionalInterfaceExamples obj = new Java8FunctionalInterfaceExamples();

        // Functional Interface
        obj.functionalInterfaceExample();
        System.out.println("--------");

        // Replacing for loop with forEach to iterate a collection
        obj.iterateCollection();
    }

    private void iterateCollection() {
        List<Integer> ints = Arrays.asList(1, 2, 3, 4, 5);
        // Traditional way
        System.out.print("Traditional Style to iterate a collection: ");
        for (Integer anInt : ints) {
            System.out.print(anInt);
        }
        System.out.println();

        // Java8 Style
        System.out.print("Java8 Style to iterate a collection: ");
        ints.forEach((anInt) -> {
            System.out.print(anInt);
        });
        System.out.println();
    }


    private void functionalInterfaceExample() {

        // Traditional way of creating anonymous classes
        MyInterface mi = new MyInterface() {
            @Override
            public void myMethod() {
                System.out.println("hi 1");
            }

            // default methods can be overridden
            @Override
            public void myAnotherMethod() {
                System.out.println("Inside myAnotherMethod");
            }
            /* static methods cannot be overridden
            @Override
            static void myStaticMethod() {
                System.out.println("Inside myStaticMethod");
            }
            */

        };
        mi.myMethod();
        mi.myAnotherMethod();

        // MyInterface is NOT a Functional Interface, but it has only one method without body, so it automatically becomes a functional interface.
        // It also demonstrates chaining of interfaces.
        MyInterface myInterface = () -> {
            System.out.println("hi 1");
        };
        myInterface.andThen(() -> {
            System.out.println("hi 2");
        }).andThen(() -> {
            System.out.println("hi 3");
        }).myMethod();

        // traditional way to use an anonymous class that implements MyFunctionalInterface.
        MyFunctionalInterface fi = new MyFunctionalInterface() {
            @Override
            public void myMethod() {
                System.out.println("Traditional Java style - inside my method");
            }
        };

        evaluateFunctionalInterface(fi);

        // Java 8 style  (called lambda or functional programming)
        MyFunctionalInterface fi8 = () -> {
            System.out.println("Java8 style - inside my method");
        };
        evaluateFunctionalInterface(fi8);

        // Consumer Interface
        Consumer<Integer> myConsumer = (anInt) -> {
            System.out.println("Consumer Functional Interface Example - " + anInt);
        };
        Consumer<Integer> anotherConsumer = (anInt) -> {
            System.out.println("Another Consumer Functional Interface Example - " + anInt);
        };
        // andThen method returns a Consumer that calls consumers in sequence
        // myConsumer is called first and then anotherConsumer
        Consumer<Integer> consumerWithAndThen = myConsumer.andThen(anotherConsumer);

        takingConsumerInterfaceAsArg(consumerWithAndThen, 1);


        // BiConsumer Interface
        BiConsumer<String, Integer> biConsumer = (String name, Integer age) -> {
            System.out.println("BiConsumer Functional Interface Example - " + name + " " + age);
        };
        takingBiConsumerInterfaceAsArg(biConsumer, "Tushar", 30);

        // Function Interface
        Function<String, String> myFunction = (String outputFromAnotherFunction) -> {
            return "Function Functional Interface Example - Welcome, " + outputFromAnotherFunction;
        };
        Function<String, String> anotherFunction = (String name) -> {
            return "Another Function Functional Interface Example - Hi, " + name;
        };
        // compose method makes sure that output of ond Function becomes an input to another Function
        // output of myFunction becomes an input to anotherFunction
        Function<String, String> functionWithCompose = myFunction.compose(anotherFunction);
        System.out.println(functionWithCompose.apply("Tushar")); // Function Functional Interface Example - Welcome, Another Function Functional Interface Example - Hi, Tushar

        // Identity Function - Function interface has a static method called identity() whose output is same as input.
        Function identityFunction = Function.identity();
        System.out.println(identityFunction.apply("I am an Identity Function"));

        // Predicate interface
        Predicate<Integer> predicate = (anInt) -> {
            return anInt == 1;
        };
        System.out.println("Predicate Functional Interface Example returns " + predicate.test(1));


        // moving if-else to a Function
        {
            int x = 1;

            if(x == 1)
                System.out.println(x);
            else
                System.out.println(-1);


            Function<Integer, Integer> function = (x1) -> {
                if(x1 == 1)
                    return x1;
                else
                    return -1;
            };
            System.out.println(function.apply(x));


        }
    }

    private <T> void takingConsumerInterfaceAsArg(Consumer<T> consumer, T t) {
        consumer.accept(t);
    }

    private <T, B> void takingBiConsumerInterfaceAsArg(BiConsumer<T, B> consumer, T t, B b) {
        consumer.accept(t, b);
    }

    private void evaluateFunctionalInterface(MyFunctionalInterface functionalInterface) {
        functionalInterface.myMethod();
    }

}

