package java8.functionalprograming.functionalprogramminginjavabook.chapter15.properties;

import java8.functionalprograming.functionalprogramminginjavabook.chapter15.Person;
import java8.functionalprograming.functionalprogramminginjavabook.chapter2.Function;
import java8.functionalprograming.functionalprogramminginjavabook.chapter5and8.List;
import java8.functionalprograming.functionalprogramminginjavabook.chapter7.Result;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.function.Supplier;

/*
Chapter 15 (15.2 Reading properties from file)

This class shows how to improve Properties file loading and reading a property from it.
Use of Optional.
 */

public class PropertyReader {
    private final String propertiesFile;

    public PropertyReader(String propertiesFile) {
        this.propertiesFile = propertiesFile;
    }


    /*
        pg 437 (15.2.3 Producing better error messages)

        This is how Java 8's ClassLoader.getResourceAsStream method is written.
        It returns null in case of exception.
        It is not functional.
        It means that caller of this method has to worry about null check/NullPointerException.

        public InputStream getResourceAsStream(String name) {
          URL url = getResource(name);
          try {
            return url != null ? url.openStream() : null;
          } catch (IOException e) {
            return null;
          }
        }

        An IOException might occur if the file is not found.
        If there is a problem while reading it.
        The file name could be null.
        The getResource method could throw an exception, or return null.

        The minimum that we should do is provide a different message for each case.
     */
    // Using Result
    private Result<Properties> readProperties() {
        try (InputStream inputStream =
                     getClass().getClassLoader().getResourceAsStream(propertiesFile)) {

            Properties properties = new Properties();
            properties.load(inputStream);
            return Result.of(properties);  // --- return Result

        } catch (NullPointerException e) {
            return Result.failure(String.format("File %s not found in classpath", propertiesFile)); // --- instead of throwing an exception, return Result.failure(e)
        } catch (IOException e) {
            return Result.failure(String.format("IOException reading classpath resource %s", propertiesFile));
        } catch (Exception e) {
            return Result.failure(String.format("Exception reading classpath resource %s", propertiesFile), e);
        }
    }

    // Using Optional
    private Supplier<Optional<Properties>> readProperties_Java8() {
        try (InputStream inputStream = getClass().getClassLoader()
                .getResourceAsStream(propertiesFile)) {
            Properties properties = new Properties();
            properties.load(inputStream);
            return () -> Optional.ofNullable(properties);
        } catch (Exception e) {
            return () -> {
                throw new RuntimeException(e);
            }; // --- Instead of throwing an exception from a method, return a Supplier/Consumer that throws an exception
        }
    }

    private Optional<String> getProperty_Java8(String propertyName) {
        Supplier<Optional<Properties>> readPropertiesResult = readProperties_Java8();
        try {
            Optional<Properties> properties = readPropertiesResult.get();
            Optional<String> some_property = properties.map(props -> (String) props.get(propertyName)); // avoid any type of casting - how? see in below code, how NumberFormatExcepiton is avoided.
            return some_property;
        } catch (Exception e) {
            System.out.println("exception thrown");
            return Optional.empty();
        }
    }

/*
    private Optional<String> getProperty_Java8(String propertyName) {
        Supplier<Optional<Properties>> readPropertiesResult = readProperties_Java8(propertiesFile);
        Optional<Properties> properties = readPropertiesResult.get();
        return properties.flatMap(props -> getProperty_Java8(propertyFile, propertyName));
    }
*/


    // pg 436 (15.2.2 Reading properties as strings)
    public static Result<String> getProperty(Properties properties, String name) {
        return Result.of(properties.getProperty(name));
    }

    private Optional<String> getProperty_Java8(Properties properties, String name) {
        return Optional.ofNullable(properties.getProperty(name));
    }


    // pg 439
    // convert properties id,firstName, lastName to a Person object
    // Important: It uses COMPREHENSION technique and avoiding Type Casting
    private Result<Person> getAsPerson(Result<Properties> properties) {
        // pg 438
        // Very Important :
        // how to avoid NumberFormatException in below code?
        Result<Person> person1 = properties.flatMap(props -> getProperty(props, "id"))
                .map(strId -> Integer.parseInt(strId)) // Dangerous --- it can throw NumberFormatException
                // Creating Person object from id, firstName, lastName using COMPREHENSION technique.
                .flatMap(intId -> properties.flatMap(props -> getProperty(props, "firstName"))
                        .flatMap(firstName -> properties.flatMap(props -> getProperty(props, "lastName"))
                                .map(lastName -> Person.getInstance(intId, firstName, lastName))));

        Result<Person> person2 = properties.flatMap(props -> getProperty(props, "id"))
                .flatMap(strId -> Util.getAsInteger(strId)) // solution
                .flatMap(intId -> properties.flatMap(props -> getProperty(props, "firstName"))
                        .flatMap(firstName -> properties.flatMap(props -> getProperty(props, "lastName"))
                                .map(lastName -> Person.getInstance(intId, firstName, lastName))));

        return person2;

    }

    // pg 440 (15.2.4 Reading properties as lists)
    public static <O> Result<List<O>> getAsList(Properties properties, String name, Function<String, O> operation) {
        Result<String> propertyValue = getProperty(properties, name);// assuming that it is a comma separated value

        return propertyValue.map(value -> List.mapArrayToList(value.split(","), operation));
    }


    /*
        pg 441 (15.2.5 Reading enum values)

        One frequent use case consists of reading a property as an enum value. This is a particular case of reading a property as any type. So we can first create a method to convert a property to any type T, taking a function from String to a Result<T>

        Any convert method is just like a map method.
     */
    // book has getAsType method
    private <O> Result<O> map(Result<Properties> properties, String name, Function<String, Result<O>> operation) {
        Result<String> value = properties.flatMap(properties1 -> Util.getAsString(properties1.get(name)));
        return value.flatMap(val -> operation.apply(val));
    }

    // book has getAsEnum method
    private <O extends Enum<O>> Result<O> mapToEnum(Result<Properties> properties, String name, Class<O> enumClass) {

        Result<O> enumT;
        try {
            enumT = Result.success(Enum.valueOf(enumClass, name));
        } catch (Exception e) {
            enumT = Result.failure("not found");
        }

        final Result<O> finalEnum = enumT;
        return map(properties, name, propertyValue -> finalEnum);
    }



    public static Result<List<Integer>> getAsIntegerList(Properties properties, String name) {
        return getAsList(properties, name, (s) -> Integer.parseInt(s));
    }

    public static Result<List<Double>> getAsDoubleList(Properties properties, String name) {
        return getAsList(properties, name, (s) -> Double.parseDouble(s));
    }

    public static Result<List<Boolean>> getAsBooleanList(Properties properties, String name) {
        return getAsList(properties, name, (s) -> Boolean.parseBoolean(s));
    }

    public Result<Person> getAsPerson(Result<Properties> properties, String personPropertyName) {
        Result<String> personPropertyValue = properties.flatMap(props -> Util.getAsString(props.getProperty(personPropertyName)));

        //final map that is passed to Person.apply
        //{id:3,firstName:Tushar,lastName:Chokshi}
        Map<String, Result<String>> individualProperties = new HashMap<>();

        return getAsPerson(
                individualProperties,// {} empty at beginning
                personPropertyValue, // id:3,firstName:Tushar,lastName:Chokshi
                individualProps -> personPropVal ->
                        personPropVal
                                .flatMap(propValue -> List.mapCommaSeparatedStringToListOfMap(propValue)) // Result object of List[{id:3}, {firstName:Tushar}, {lastName:Chokshi}]
                                .map(listOfPersonProperties -> listOfPersonProperties.map(
                                        headOfList -> {// Map<String, String>
                                            Map<String, Result<String>> stringResultMap = getStringResultMap(headOfList);
                                            individualProps.putAll(stringResultMap);
                                            return individualProps;
                                        }
                                ))
                                .map(listOfIndividualProps -> listOfIndividualProps.head()) // List containing one Map {id:3, firstName:Tushar, lastName:Chokshi}
                                .map(individualProps1 -> Person.getInstance(individualProps1))); // individualProps1 = {id:3, firstName:Tushar, lastName:Chokshi}

    }

    // pg 442 (15.2.6 Reading properties of arbitrary types)
    // convert a property (person=id:3,firstName:Tushar,lastName:Chokshi) to a Person object
    // 'person' property is a one string.
    // you need a function that converts a String to Result<Person>
    private Result<Person> getAsPerson(
            Map<String, Result<String>> individualProperties,
            Result<String> personPropertyValue,
            Function<Map<String, Result<String>>, Function<Result<String>, Result<Person>>> operation) {

        return operation.apply(individualProperties).apply(personPropertyValue);
    }

    // or
    private Function<Map<String, Result<String>>, Function<Result<String>, Result<Person>>> getAsPerson(
            Function<Map<String, Result<String>>, Function<Result<String>, Result<Person>>> operation) {

        return individualProperties -> personPropertyValue ->
                operation.apply(individualProperties).apply(personPropertyValue);
    }


    protected Map<String, Result<String>> getStringResultMap(Map<String, Result<String>> headOfList) {
        Map<String, Result<String>> individualProps = new HashMap<>();

        Object[] keys = headOfList.keySet().toArray();
        String key = (String) keys[0];

        Object[] values = headOfList.values().toArray();
        Result<String> value = (Result<String>) values[0];

        individualProps.put(key, value);
        return individualProps;
    }


    public static void main(String[] args) {
        PropertyReader propertyReader = new PropertyReader("config.properties");

        Optional<String> some_name_value = propertyReader.getProperty_Java8("host");
        System.out.println(some_name_value.orElseGet(() -> "NONE")); // O/P: acme.org


        Result<Properties> properties = propertyReader.readProperties();
        {
            Properties props = properties.get();

            propertyReader.getProperty(props, "host")
                    .forEachOrFail((x) -> System.out.println(x));// O/P: acme.org
            //.forEach((x) -> System.out.println(x));
            propertyReader.getProperty(props, "name")
                    .forEachOrFail((x) -> System.out.println(x))
                    .forEach((x) -> System.out.println(x));// space

            propertyReader.getProperty(props, "year")
                    .forEachOrFail((x) -> System.out.println(x))
                    .forEach((x) -> System.out.println(x)); // Null value
        }

        {
            // Very Important :
            // Above code is doing properties.get(). As a rule of thumb, we should always try to avoid that using flatMap or map methods.
            // This will avoid NullPointerException, if props is null.
            properties.flatMap(props -> propertyReader.getProperty(props, "host"))
                    .forEachOrFail((x) -> System.out.println(x));// O/P: acme.org
        }

        {
            Result<Person> personResult = propertyReader.getAsPerson(properties);
            System.out.println("Person: " + personResult.getOrElse(Person.getInstance(0, "NOT FOUND", "NOT FOUND")));// Person: Person{id=1, firstName='Tushar', lastName='Chokshi'}
        }
        {
            Result<TypeEnum> type = propertyReader.mapToEnum(properties, "SERIAL", TypeEnum.class);
            type.forEach(typeEnum -> System.out.println("enum found: " + typeEnum.name()));// SERIAL
        }
        {
            System.out.println(propertyReader.getAsPerson(properties, "person")); // Success(Person{id=3, firstName='Tushar', lastName='Chokshi'})
        }

    }

}
