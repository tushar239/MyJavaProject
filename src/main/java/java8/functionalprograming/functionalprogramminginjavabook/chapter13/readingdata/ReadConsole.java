package java8.functionalprograming.functionalprogramminginjavabook.chapter13.readingdata;

import java8.functionalprograming.functionalprogramminginjavabook.chapter12.Tuple;
import java8.functionalprograming.functionalprogramminginjavabook.chapter7.Result;

/**
 * @author Tushar Chokshi @ 11/27/16.
 */
public class ReadConsole {
    public static Result<Tuple<Person, Input>> person(Input in) {

        // comprehension pattern
        Result<Person> personResult =
                in.readInt("Enter your id: ").flatMap(id ->
                        in.readString("Enter your firstname: ").flatMap(fn ->
                                in.readString("Enter your lastname: ").map(ln -> Person.apply(id._1, fn._1, ln._1))));

        return personResult.map(person -> new Tuple(person, in));
    }
}
