package java8.functionalprograming;

import org.apache.commons.collections4.Predicate;
import org.apache.commons.collections4.SetUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Tushar Chokshi @ 1/6/17.
 */
public class Test {
    private static int i;

    public static void main(String[] args) {

        Float f = new Float(23.43);
        System.out.println(f);
        Boolean b = new Boolean("false");
        System.out.println(b);
        Double d = new Double("12.23d");
        System.out.println(d);

        new Inner() {
            @Override
            public void m() {
                System.out.println(i);
            }
        };


        Set<Long> offerIds = SetUtils.predicatedSet(
                new HashSet<>(),
                new Predicate<Long>() {
                    public boolean evaluate(Long offerId) {
                        return offerId != null;
                    }
                }
        );
        offerIds.add(123L);
        System.out.println(offerIds);

        List<Number> numbers = new ArrayList<>();
        numbers.add(1d);
        numbers.add(new Integer(1));
        numbers.add(new Double(1));


        List<? extends Number> anotherNumbers = numbers;
        System.out.println(anotherNumbers.get(0));
        //anotherNumbers.add(2L); // compilation error - you can read, but cannot add
        // anotherNumbers.add((Number)2L); // compilation error

        //List<Number> thirdNumbers = anotherNumbers; // compilation error


        List<Integer> li = Arrays.asList(1, 2, 3);
        //List< Number> liNumbers = li; // compilation error
        List<? extends Number> liNumbers = li; // works
        List<? extends Integer> anotherLi = Arrays.asList(1, 2, 3);


        wildCardMethod(li);
        wildCardMethod(anotherLi);
        //withoutWildCardMethod(anotherLi);

        List<? super Integer> foo1 = new ArrayList<Integer>();
        foo1.add(1);
        List<? super Integer> foo2 = new ArrayList<Number>();
        foo2.add(new Integer(1));
        List<? super Integer> foo3 = new ArrayList<Object>();
        foo3.add(new Integer(1));


        List<? extends Number> foo4 = anotherLi;

        Object object = foo1.get(0);
        Object object1 = foo2.get(0);
        Object object3 = foo3.get(0);
        System.out.println(object);


        List<? extends Number> foo123 = new ArrayList<Integer>();

        //Number number = foo123.get(0);


        List<Number> foohehe = (List<Number>) foo123;

        I i1 = new SubClass1OfI();

        SomeClass<? extends I> someClass11 = new SomeClass<>(i1);// same as new SomeClass<I>(i1);
        //someClass11.execute(i1); // illegal
        //someClass11.execute(new SubClass1OfI()); // illegal
        SomeClass<? extends I> someClass12 = new SomeClass<>(new SubClass1OfI());// same as new SomeClass<SubClass1OfI>(i1);
        //someClass12.execute(i1);// illegal
        //someClass12.execute(new SubClass1OfI()); // illegal

        SomeClass<? super I> someClass31 = new SomeClass<>(i1);// same as new SomeClass<I>(i1);
        someClass31.execute(i1);
        someClass31.execute(new SubClass1OfI());
        SomeClass<? super I> someClass32 = new SomeClass<>(new SubClass1OfI());// same as new SomeClass<SubClass1OfI>(i1);
        someClass32.execute(i1);
        someClass32.execute(new SubClass1OfI());

        SomeClass<? super I> subClass1OfISomeClass = new SomeClass<>(new SubClass1OfI());


        SomeClass<I> someClass21 = new SomeClass<>(i1);// same as new SomeClass<I>(i1);
        someClass21.execute(i1);
        someClass21.execute(new SubClass1OfI());

        SomeClass<I> someClass22 = new SomeClass<>(new SubClass1OfI());// same as new SomeClass<I>(new SubClass1OfI());
        someClass22.execute(i1);
        someClass22.execute(new SubClass1OfI());
        //SomeClass<I> someClass23 = new SomeClass<SubClass1OfI>(new SubClass1OfI()); // illegal

    }


    public static void wildCardMethod(List<? extends Number> numbers) {
        System.out.println(numbers);
    }

    public static void withoutWildCardMethod(List<Number> numbers) {
        System.out.println(numbers);
    }

    static class Inner {
        public void m() {
            System.out.println(i);
        }
    }


}

class SomeClass<C extends I> {
    private C c;

    public SomeClass(C c) {
        this.c = c;
    }

    public void execute(C c1) {

    }

    public static SomeClass<I> doExecute() { // better
        return new SomeClass<>(new SubClass1OfI());
    }

    public static SomeClass<? extends I> doExecute2() {
        return new SomeClass<>(new SubClass1OfI());
    }

}

interface I {

}

class SubClass1OfI implements I {

}

class SubClass2OfI implements I {

}
