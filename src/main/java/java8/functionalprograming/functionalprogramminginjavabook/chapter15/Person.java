package java8.functionalprograming.functionalprogramminginjavabook.chapter15;

import java8.functionalprograming.functionalprogramminginjavabook.chapter15.properties.Util;
import java8.functionalprograming.functionalprogramminginjavabook.chapter7.Result;

import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * @author Tushar Chokshi @ 11/7/16.
 */
public class Person {
    private Integer id;
    private String firstName;
    private String lastName;

    private Person(Integer id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public static Person getInstance(Integer id, String firstName, String lastName) {
        return new Person(id, firstName, lastName);
    }

    public static Person getInstance(Optional<Integer> id, Optional<String> firstName, Optional<String> lastName) {
        return getInstance(id.get(), firstName.get(), lastName.get());
    }

    public static Person getInstance(Result<Integer> id, Result<String> firstName, Result<String> lastName) {
        return getInstance(id.get(), firstName.get(), lastName.get());
    }

    public static Person getInstance(Map<String, Result<String>> personProperties) {
        Result<Integer> id = Util.getAsInteger(personProperties.get("id").get());
        Result<String> firstName = Util.getAsString(personProperties.get("firstName"));
        Result<String> lastName = Util.getAsString(personProperties.get("lastName"));
        return getInstance(id, firstName, lastName);


    }
    public static void main(String[] args) {
        Person person = Person.getInstance(
                assertPositive(-1, "Negative id"),
                assertValidName("Tushar", "Invalid first name:"),
                assertValidName("Chokshi", "Invalid last name:"));

        // better way
        Person person1 = Person.getInstance(assertPositive_(-1, "Negative id").get(),
                assertValidName_("Tushar", "Invalid first name:").get(),
                assertValidName_("Chokshi", "Invalid last name:").get());// this may throw an exception

    }

    private static int assertPositive(int i, String message) {
        if (i < 0) {
            throw new IllegalStateException(message);
        } else {
            return i;
        }
    }

    private static String assertValidName(String name, String message) {
        if (name == null || name.length() == 0
                || name.charAt(0) < 65 || name.charAt(0) > 91) {
            throw new IllegalStateException(message);
        }
        return name;
    }

    private static Supplier<Optional<Integer>> assertPositive_(int i, String message) {
        if (i < 0) {
            return () -> {throw new IllegalStateException(message);};
        } else {
            return () -> Optional.ofNullable(i);
        }
    }

    private static Supplier<Optional<String>> assertValidName_(String name, String message) {
        if (name == null || name.length() == 0
                || name.charAt(0) < 65 || name.charAt(0) > 91) {
            return () -> {throw new IllegalStateException(message);};
        }
        return () -> Optional.ofNullable(name);
    }


    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

}
