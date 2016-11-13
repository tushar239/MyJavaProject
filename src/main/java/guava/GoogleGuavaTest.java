package guava;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


// Google Guava API example - http://www.javacodegeeks.com/2012/10/google-guava-v07-examples.html
// Guava comes with a special utility called Collection2 that has interfaces like Function and Predicate
// These interfaces lets you modify the collection or determine true/false without client iterating through the colletion
// There are also utility methods like transform, Splitter, Joiner etc
public class GoogleGuavaTest {
    public static void main(String[] args) {
        testNullable(null, null);

        List<Employee> employees = new ArrayList<>();
        Employee emp1 = new Employee();
        emp1.setName("Tushar");
        employees.add(emp1);

        Employee emp2 = new Employee();
        emp2.setName("hi");
        employees.add(emp2);

        employees.add(null);

        Collection<Employee> filteredEmployees = Collections2.filter(employees, isValidEmployee);
        System.out.println(filteredEmployees);
    }

    // @Nullable and @NotNull are used from javax.annotations (JSR305) library by Guava.
    // They actually have no significance.
    // If a field is marked @NotNull, it is there to tell client that there is actually no null check
    // in the code, so please do not send null, otherwise expect some unwanted behaviour like exception, if you send null.
    private static void testNullable(/*@Nullable*/ String name, /*@NotNull*/ String address) {
        System.out.println(name);
        System.out.println(address);
    }

    protected final static Predicate isValidEmployee = new Predicate<Employee>() {
        @Override
        public boolean apply(/*@Nullable*/ Employee employee) {
            return "Tushar".equals(employee.getName());
        }
    };
}
