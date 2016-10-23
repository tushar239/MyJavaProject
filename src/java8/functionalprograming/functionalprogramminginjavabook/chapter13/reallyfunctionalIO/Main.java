package java8.functionalprograming.functionalprogramminginjavabook.chapter13.reallyfunctionalIO;

/**
 * @author Tushar Chokshi @ 8/28/16.
 */
public class Main {
    public static void main(String... args) {
        IO<Nothing> script = sayHello();
        script.run();

       /* IO program = program(buildMessage,
                "Enter the names of the persons to welcome:");
        program.run();*/

/*
        IO program1 = IO.repeat(3, sayHello());
        program1.run();
*/

        IO program3 = IO.forever(IO.unit("Hi again!"));
        program3.run();
    }

    private static IO<Nothing> sayHello() {
        return Console.printLine("Enter your name: ")
                .flatMap(Console::readLine)
                .map(Main::buildMessage)
                .flatMap(Console::printLine);
    }

    private static String buildMessage(String name) {
        return String.format("Hello, %s!", name);
    }
    /*public static IO<Nothing> program(Function<String, IO<Boolean>> f,
                                      String title) {
        return IO.sequence(
                Console.printLine(title),
                IO.doWhile(Console.readLine(), f),
                Console.printLine("bye!")
        );
    }*/

   /* private static IO<Nothing> sayHello() {
        return Console.printLine("Enter your name: ")
                .flatMap(Console::readLine)
                .map(Main::buildMessage)
                .flatMap(Console::printLine);
    }

    private static Function<String, IO<Boolean>> buildMessage =
            name -> IO.when(name.length() != 0,
                    () -> IO.unit(String.format("Hello, %s!", name))
                            .flatMap(Console::printLine));*/

}