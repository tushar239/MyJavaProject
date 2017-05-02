package java8.functionalprograming;

import java.util.HashMap;
import java.util.Map;

/*
    Read Java8_Map_Enhancements.docx
 */
public class Java8MapEnhancementExample {

    public static void main(String[] args) {
        mapEnhancementExamples();
    }

    private static void mapEnhancementExamples() {
        Map<String, String> map = new HashMap<>();
        map.put("1", "one");
        map.put("2", "two");
        map.put("3", "three");

        // you can iterate a map using forEach like collection
        map.forEach((k, v) -> System.out.format("%s's value is %s\n", k, v));
            /*
            1's value is one
            2's value is two
            3's value is three
            */

            /*
            Compute methods are just like replace method with slight variations.
            If you see carefully, replace method doesn’t care whether existing value of a key is null or non-null, but compute methods does care about it. Compute methods will remove the key, if new computed value is null.
             */

        // Replaces the value of a key "1" to "one-new", if current value matches to "one"
        boolean replaced = map.replace("1", "one", "one-new");

        // If key="1" exists with null/non-null value, then only it replaces its value. It doesn't create a new key "1", if it doesn't exist
        final String newValue = map.replace("1", "one-new");

        // replaceAll simply replaces value of all keys based on the output of passed function
        map.replaceAll((k, v) -> v + "-new-value");
        map.forEach((k, v) -> System.out.format("%s's value is %s\n", k, v));

        // compute is a special function that replaces the value of a key with new value calculated by passed function.
        // it returns a new computed value
        // if new value is null then it removes a key from map
        String value = map.compute("1", (k, v) -> null);
        map.forEach((k, v) -> System.out.format("%s's value is %s\n", k, v));

        // if key doesn't exist or value of the key is null, then insert a key with computed value, if computed value is not null.
        value = map.computeIfAbsent("4", (k) -> k + "-new");
        map.forEach((k, v) -> System.out.format("%s's value is %s\n", k, v));

        // if key exist and its value is not null, then replace that value with new computed value. If computed value is null, then it removes the key.
        value = map.computeIfPresent("3", (k, v) -> v + "-new computed value");
        map.forEach((k, v) -> System.out.format("%s's value is %s\n", k, v));

        // add key-value to a map, if key doesn't exist in the map, if key exist then do nothing
        map.putIfAbsent("5", "five");
        map.forEach((k, v) -> System.out.format("%s's value is %s\n", k, v));
    }

}
