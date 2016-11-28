package java8.functionalprograming.functionalprogramminginjavabook.chapter13.readingdata;

import java8.functionalprograming.functionalprogramminginjavabook.chapter12.Tuple;
import java8.functionalprograming.functionalprogramminginjavabook.chapter5and8.List;
import java8.functionalprograming.functionalprogramminginjavabook.chapter7.Result;

/**
 * @author Tushar Chokshi @ 11/27/16.
 */
// pg 385
public class ScriptReader implements Input {
    private final List<String> commands;

    public ScriptReader(List<String> commands) {
        super();
        this.commands = commands;
    }

    public ScriptReader(String... commands) {
        super();
        this.commands = List.list(commands);
    }

    public Result<Tuple<String, Input>> readString() {
        return commands.isEmpty() ? Result.failure("Not enough entries in script")
                : Result.success(new Tuple<>(commands.headOption().getOrElse(""),
                new ScriptReader(commands.dropTailRecursivelyJava8Style(1))));

    }

    @Override
    public Result<Tuple<Integer, Input>> readInt() {
        try {
            return commands.isEmpty()
                    ? Result.failure("Not enough entries in script")
                    : Integer.parseInt(commands.headOption().getOrElse("")) >= 0
                    ? Result.success(new Tuple<>(Integer.parseInt(
                    commands.headOption().getOrElse("")),
                    new ScriptReader(commands.dropTailRecursivelyJava8Style(1))))
                    : Result.empty();
        } catch (Exception e) {
            return Result.failure(e);
        }
    }
}