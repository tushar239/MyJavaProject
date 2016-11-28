package java8.functionalprograming.functionalprogramminginjavabook.chapter13.readingdata.readers.utils;

import java8.functionalprograming.functionalprogramminginjavabook.chapter12.Tuple;
import java8.functionalprograming.functionalprogramminginjavabook.chapter13.readingdata.vos.Person;
import java8.functionalprograming.functionalprogramminginjavabook.chapter13.readingdata.readers.IReader;
import java8.functionalprograming.functionalprogramminginjavabook.chapter7.Result;

/**
 * @author Tushar Chokshi @ 11/27/16.
 */
public class ReadInput {
    public static Result<Tuple<Person, IReader>> person(IReader in) {

        // comprehension pattern
        Result<Person> personResult =
                in.readInt("Enter your id: ").flatMap(id ->
                        in.readString("Enter your firstname: ").flatMap(fn ->
                                in.readString("Enter your lastname: ").map(ln -> Person.apply(id._1, fn._1, ln._1))));

        return personResult.map(person -> new Tuple(person, in));
    }
}
