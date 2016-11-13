package java8.functionalprograming;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Tushar Chokshi @ 5/15/16.
 */
/*
    http://www.studytrails.com/java/java8/Java8_Lambdas_FunctionalProgramming.jsp
    http://docs.oracle.com/javase/tutorial/java/javaOO/methodreferences.html

    lambda syntax
        (arguments) -> {expression/body}

        Sometimes, however, a lambda expression does nothing but call an existing method.
        In those cases, it's often clearer to refer to the existing method by name.
 */
public class Java8MethodReferences {
    public static void main(String[] args) {
        List<Integer> ints = Arrays.asList(1,2,3);

        PrintClass<Object> printClass = new PrintClass<>();

        {
            // Calling a constructor as a part of lamda expression
            Supplier<PrintClass> supplier = () ->  new PrintClass();
            supplier.get().printMe("Welcome");

            // Calling existing instance method from lambda expression
            Function<List<Integer>, String> function = (ns) -> printClass.printMe(ns); // calling instance method as a part of lambda expression
            System.out.println(function.apply(ints)); // [1, 2, 3]

            // Calling existing static method from lambda xpression
            Consumer<String> consumer = (str) -> {PrintClass.printStaticText(str);}; // calling static method as a part of lambda expression
            consumer.accept("Welcome"); // Welcome

            // This lambda expression has more than one statements, so it cannot be converted into one liner Method Reference
            Function<List<Integer>, String> function2 = (ns) -> {PrintClass.printStaticText("Welcome");return printClass.printMe(ns);};
            System.out.println(function2.apply(ints)); // Welcome   [1, 2, 3]

            // This lambda express uses an instance method of argument.
            Function<String, String> stringFunction = (str) -> str.toLowerCase();
            System.out.println(stringFunction.apply("WELCOME")); // welcome

            BiFunction<List, Integer, Object> listIntegerFunction = (list,index) -> list.get(index);
            System.out.println(listIntegerFunction.apply(Arrays.asList(new Integer[]{1, 2, 3}), 0));// 1

        }

        // Method Reference style
        // http://www.studytrails.com/java/java8/Java8_Lambdas_FunctionalProgramming.jsp
        {
            // Constructor Reference
            // Limitation of Constructor Reference syntax: you can't use it for constructor with arguments.
            Supplier<PrintClass> supplier = PrintClass::new; // same as () -> new PrintClass()
            System.out.println(supplier.get().printMe("Welcome")); // Welcome

            // Instance Method Reference
            Function<List<Integer>, String> function  = printClass::printMe;
            System.out.println(function.apply(ints)); // [1, 2, 3]

            // Static Method Reference
            Consumer<String> consumer  = PrintClass::printStaticText;
            consumer.accept("Welcome!");// Welcome!

            // Referencing Instance methods using Class Name --- This can be a little confusing, we are callng a non static method but using a class name. Actually the instance of the class is passed when the method is called.
            Function<String, String> stringFunction = String::toLowerCase; // same as (s)->s.toLowerCase();
            System.out.println(stringFunction.apply("WELCOME"));// welcome

            BiFunction<List, Integer, Object> listIntegerFunction = List::get;
            System.out.println(listIntegerFunction.apply(Arrays.asList(new Integer[]{1, 2, 3}), 0));// 1


        }


    }


}
