package java8.functionalprograming.functionalprogramminginjavabook.chapter7;

import static java8.functionalprograming.functionalprogramminginjavabook.chapter7.Result.success;

/**
 * @author Tushar Chokshi @ 11/7/16.
 */
// pg 201
// The Toon class using Result.Empty for optional data
public class Toon {
    private final String firstName;
    private final String lastName;
    private final Result<String> email;

    Toon(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = Result.empty();
    }

    Toon(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = success(email);
    }

    public static void main(String[] args) {
        MyMap<String, Toon> toons = new MyMap<String, Toon>()
                .put("Mickey", new Toon("Mickey", "Mouse", "mickey@disney.com"))
                .put("Minnie", new Toon("Minnie", "Mouse"))
                .put("Donald", new Toon("Donald", "Duck", "donald@disney.com"));
        Result<String> result =
                getName()
                        .flatMap((name) -> toons.get(name))
                        .flatMap((toon) -> toon.getMail());
        System.out.println(result);

        // pg 213
        // Create Toon object from provided Result objects of firstName, lastName and mail.
        /* one way
         But we are reaching the limits of abstraction. We could have to call methods or
        constructors with more than three arguments.
        */
        /*
        Function<String, Function<String, Function<String, Toon>>> createPerson =
                (String x) -> (String y) -> (String z) -> new Toon(x, y, z);
        Result<Toon> toon2 = Result.lift3(createPerson)
                .apply(getFirstName())
                .apply(getLastName())
                .apply(getMail());
        */

        /*
         In such a case, we may use the following pattern:
         This pattern has two advantages:
          You can use any number of arguments.
          You don’t need to define a function.
         This pattern is sometimes called “comprehension”.

         Pseudo code
            for {
                firstName in getFirstName(),
                lastName in getLastName(),
                mail in getMain()
            } return new Toon(firstName, lastName, mail)
        */
        Result<Toon> toon = getFirstName()
                .flatMap(firstName -> getLastName()
                        .flatMap(lastName -> getMail()
                                .map(mail -> new Toon(firstName, lastName, mail))));

    }

    public static Result<String> getName() {
        return success("Mickey Mouse");
        //return Result.failure(new IOException("Input error"));
        //return Result.success("Minnie");
        //return Result.success("Goofy");
    }

    static Result<String> getFirstName() {
        return success("Mickey");
    }

    static Result<String> getLastName() {
        return success("Mouse");
    }

    static Result<String> getMail() {
        return success("mickey@disney.com");
    }

}