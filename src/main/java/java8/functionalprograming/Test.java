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
