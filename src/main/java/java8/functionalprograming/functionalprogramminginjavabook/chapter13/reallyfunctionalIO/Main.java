package java8.functionalprograming.functionalprogramminginjavabook.chapter13.reallyfunctionalIO;

import java8.functionalprograming.functionalprogramminginjavabook.chapter5and8.List;

/**
 * @author Tushar Chokshi @ 8/28/16.
 */
public class Main {
    public static void main(String... args) {
        System.out.println("Testing IO's map, flatMap etc method by taking input from console...");
        {
            //IO<Nothing> script = sayHello();
            //script.run();
        }
        System.out.println();

        System.out.println("Testing IO's repeat method...");
        {
            IO<Nothing> io = () -> {
                System.out.println("hi");
                return Nothing.instance;
            };
            IO<List<Nothing>> repeat = IO.repeat_(5, io);
        }
        System.out.println();

        System.out.println("Testing StackFreeIO..."); // IO's forever method will go in infinite loop. StackFreeIO is an improved version of IO to handle this situation.
        {

            // commented because this is and endless loop
            //TailCall<IO<Object>> program3 = IO.foreverStackFree(IO.unit("Hi again!"));
            //program3.eval();

            // commented because this is and endless loop
            //StackFreeIO program4 = StackFreeIO.forever(StackFreeIO.unit("Hi again!"));
            //program4.run();
        }
    }

    private static IO<Nothing> sayHello() {
        // returns IO<Nothing> whose run() method will print 'Enter your name:' in console
        IO<Nothing> nothing = Console.printLine("Enter your name: ");
        // returns IO<String> - it will be an input taken from console (e.g. Tushar)
        IO<String> inputNameFromConsole = nothing.flatMap(nothing1 -> Console.readLine(nothing1));
        // returns IO<String> after calling buildMessage("Tushar")
        IO<String> builtMessage = inputNameFromConsole.map(name -> buildMessage(name));
        // Converting String to a formatted String ("Tushar" to "Hello, Tushar!"). map will return a String
        IO<Nothing> nothingIO = builtMessage.flatMap(builtMessage1 -> Console.printLine(builtMessage1));

        return nothingIO;
    }

    private static String buildMessage(String name) {
        return String.format("Hello, %s!", name);
    }

}