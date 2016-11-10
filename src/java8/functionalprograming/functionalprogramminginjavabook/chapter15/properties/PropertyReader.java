package java8.functionalprograming.functionalprogramminginjavabook.chapter15.properties;

import java8.functionalprograming.functionalprogramminginjavabook.chapter7.Result;
import java8.functionalprograming.functionalprogramminginjavabook.temp.Person;

import java.io.IOException;
import java.io.InputStream;
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
        pg 437

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
            }; // --- Instead of throwing an exception from a method, return a Supplier that throws an exception
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


    private Result<String> getProperty(Properties properties, String name) {
        return Result.of(properties.getProperty(name));
    }

    private Optional<String> getProperty_Java8(Properties properties, String name) {
        return Optional.ofNullable(properties.getProperty(name));
    }

    private Result<Integer> getAsInteger(String str) {
        try {
            return Result.success(Integer.parseInt(str));
        } catch (NumberFormatException nfe) {
            return Result.failure("Non-number string cannot be converted to number");
        }
    }

    public static void main(String[] args) {
        PropertyReader propertyReader = new PropertyReader("java8/functionalprograming/functionalprogramminginjavabook/chapter15/properties/config.properties");

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
            // Very Important :
            // how to avoid NumberFormatException in below code?
            properties.flatMap(props -> propertyReader.getProperty(props, "id"))
                    .map(strId -> Integer.parseInt(strId)) // Dangerous --- it can throw NumberFormatException
                    // Creating Person object from id, firstName, lastName using COMPREHENSION technique.
                    .flatMap(intId -> properties.flatMap(props -> propertyReader.getProperty(props, "firstName"))
                            .flatMap(firstName -> properties.flatMap(props -> propertyReader.getProperty(props, "lastName"))
                                    .map(lastName -> new Person(intId, firstName, lastName))));



            properties.flatMap(props -> propertyReader.getProperty(props, "id"))
                    .flatMap(strId -> propertyReader.getAsInteger(strId)) // solution
                    .flatMap(intId -> properties.flatMap(props -> propertyReader.getProperty(props, "firstName"))
                            .flatMap(firstName -> properties.flatMap(props -> propertyReader.getProperty(props, "lastName"))
                                    .map(lastName -> new Person(intId, firstName, lastName))));

        }

    }

}
