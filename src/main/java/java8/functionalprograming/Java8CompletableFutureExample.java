package java8.functionalprograming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


/*
 You can read Java8_CompletableFuture_ForkJoinPool_Contended_annotation.docx, but this class has everything that you need.

 http://www.deadcoderising.com/java8-writing-asynchronous-code-with-completablefuture/
 http://www.nurkiewicz.com/2013/05/java-8-definitive-guide-to.html

 (IMP)
 CompletableFuture is the best example of setting the callbacks that depends on the availability and nature of the result set inside the future object by its related task.
 It is a quality of Reactive Design Pattern.

 Qualities of Reactive Programming Design
 - Elastic
 - Responsive
 - Resilient
 - Message Driven

 Reactive Programming is an extension to Function Programming. Reactive Design starts where Functional Programming stops.

 Qualities of Functional Programming:
 - Immutability
 - Higher-order functions
 - no side-effects
 - chaining (function pipeline)
 - lazy evaluation


 Last two are more important for Reactive Design. But these two cannot be achieved without preceding ones. That's why, we say that Reactive Programming starts where Functional Programming stops.

 When I see reactive programming, all I can see is Dataflow computing.
 You have multiple computational units. Each one has inputs and outputs. If I have one unit available here and it requires two inputs to be available to it to get fired. In Dataflow computing, unit starts it work as soon as inputs are available to it.  When they generate the output, it will create inputs for other units and other units will start computation.
 That is what Lambda is. Lambda is nothing but something that doesn’t have any location. It’s a computational unit that starts running as soon as inputs are made available to it and after the computation is over they die. They wake up again when inputs are available again.




 Unlike to Future, you can use CompletableFuture
 - without setting 'Callable' to it (You don't need Callable anymore)
 - with subsequent processes to be applied on completed value(result of run task), once it is available. It let's you use Reactive design pattern (Once result is available, act on it).
   whenComplete...(...), handle(...), then...(...), exceptionally(...) etc methods are there to react on available result (completed value).

 Instead of waiting for the thread to complete using future.get() method, with CompletableFuture, I can use then...(...) method which accepts a code that will be executed right after the result is available in the CompletableFuture.
 This is called Reactive design, where result is pushed and then you react to it instead of you keep polling the result using future.get() method.

    Future<T>           CompletionStage<T>
        |                       |
        -------------------------
                    |

            CompletableFuture<T>

 Methods of Future

    boolean isDone()
    boolean isCancelled()
    boolean cancel(boolean mayInterruptIfRunning)
    V get() throws InterruptedException, ExecutionException  ----- blocking method. CompletionStage provides many methods that can run without blocking and they react when result (completed value) is available in CompletedFuture.
    V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException

 Some static methods in CompletableFuture:

    -   CompletableFuture<U> supplyAsync(Supplier<U> supplier)  OR  supplyAsync(Supplier<U> supplier, Executor executor)

        Code supplied by a supplier will be execute in thread pool (under separate thread (Runnable))

        This Runnable has access to CompletableFuture where it can put the result using 'completableFuture.completeValue(f.get());'
        Once the result is available in this Runnable, it calls completableFuture.postComplete(). In postComplete(), it will read the the tasks assigned using then...(...) methods.

    -   CompletableFuture<Void> runAsync(Runnable runnable)   OR   runAsync(Runnable runnable, Executor executor)

        If you have an instance of Runnable, you can use CompletableFuture.runAsync(Runnable)

    For both of the above methods:
    (IMP) By default, these methods use ExecutorService of type ForkJoinPool and uses it common thread pool which is shared by all JVM tasks and parallel streams. If you want to pass your own ExecutorService, you can do that too.
    (IMP) If asynchronously executed code throws an exception, it is wrapped by AltResult(exception) and kept it as a result of returned CompletableFuture.

    -   CompletableFuture<Object> anyOf(CompletableFuture<?>... cfs)

        anyOf() on the other hand will wait only for the fastest of the underlying futures.
        If first finished Future has result set as an exception, then other futures won't be evaluated. That exception will be set as a result to returned new future.

    -   CompletableFuture<Void> allOf(CompletableFuture<?>... cfs)

        allOf() takes an array of futures and returns a future that completes when all of the underlying futures are completed.
        If any one of the future has result set as an exception, returned new future will not marked as completed. So, no subsequent callbacks that depends on normal completion of the returned future, will be executed on returned new future.

 Methods of CompletionStage

      boolean               complete(T value)
      boolean               completeExceptionally(Throwable)

      CompletionStage<T>    whenComplete(BiConsumer<T,Throwable>)                               --- it is called right after result (completed value) is available in the CompletableFuture
      CompletionStage<T>    exceptionally(Function<Throwable,T>)                                --- Do not call get() method on future. If future has an AltResult(Exception) as a result, then get() will throw an exception. It's a blocking method also. So you should try to avoid it. Instead use whenComplete/handle/exceptionally methods. exceptionally method has an effect if the result is an exception.
      CompletionStage<U>    handle(BiFunction<T,Throwable,U>)                                   --- It is very important to understand the difference between whenComplete and handle methods. It is described below

      CompletionStage<U>    thenApply(Function<T,U>)                                            --- f1.thenApply(...) is to set some new result in returned Future (works like a map function). callback provided to thenApply is executed only if f1 has the result completed normally (result is not an exception).
      CompletionStage<U>    thenCompose(Function<T, CompletionStage<U>> fn)                     --- thenApply is like a map (converting one result value to another), thenCompose is like a flatMap.
      CompletionStage<Void> thenAccept(Consumer)                                                --- f1.thenAccept(...) is to modify the result of current Future, use thenAccept that takes Consumer as an argument. If f1 is completed normally (result is not an exception), then only callback provided to thenAccept will be executed.

      CompletionStage<V>    thenCombine(CompletionStage<U> other, BiFunction<T,U,V> fn)         --- f1.thenCombine(f2, ...) If both futures are completed normally (result is not an exception), then only callback provided to thenCombine will be executed.

      CompletionStage<Void> acceptEither   (CompletionStage<T> other, Consumer<T> action)       ---
                            (IMP)
                            In f1.acceptEither(f2,...), as far as f1 is completed normally, action will taken on either f1 or f2 result
                            But if f1 is completed with exception, then even though f2 is completed normally, it won't run an action on f2's result.
                            If f1 is not yet completed (expected to be completed with exception), but if f2 is completed normally, then action will be taken on f2's result.

      CompletionStage<Void> runAfterEither (CompletionStage<?> other, Runnable action)          --- read acceptEither

      CompletionStage<U>    applyToEither  (CompletionStage<T> other, Function<T, U> fn)        --- read acceptEither

      CompletionStage<Void> thenAcceptBoth(CompletionStage<U> other, BiConsumer<T, U> action)   --- f1.thenAcceptBoth(f2, ...) If both f1 and f2 are completed normally (result is not an exception), then run the action.
      CompletionStage<Void> runAfterBoth   (CompletionStage<?> other, Runnable action)          --- read thenAcceptBoth


     methods.
     All these methods are chaining methods, they return a new CompletionStage.
     Most of them has Async version also.

 Lambda passed to future....Async(...) method takes future's result as an input parameter.
 If you use ...Async(...) method, it will create a task in the thread pool.
 */
public class Java8CompletableFutureExample {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        completableFutureExamples();
    }

    public static List<String> returnSomething() {
        List<String> strs = new ArrayList<>();
        strs.add("a");
        strs.add("b");
        strs.add("c");
        return strs;
    }

    public static List<String> returnSomethingAfterSometime() {
        sleep(1000);
        return returnSomething();
    }

    protected static void sleep(int milliSeconds) {
        try {
            Thread.sleep(milliSeconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @SuppressWarnings("Duplicates")
    private static void completableFutureExamples() throws InterruptedException, ExecutionException {
        // Example of supplyAsync, complete, whenComplete, thenApply, thenAccept, get methods.
        {
            List<String> incompleteFutureResult = new ArrayList<>();
            incompleteFutureResult.add("xyz");

            CompletableFuture<List<String>> future = CompletableFuture.supplyAsync(() -> returnSomethingAfterSometime());
            // CompletableFuture.complete() can only be called once, subsequent invocations are ignored. But there is a back-door called CompletableFuture.obtrudeValue(...) which overrides previous value of the Future with new one. Use with caution.
            boolean complete = future.complete(Arrays.asList("x", "y", "z")); // complete the task with the provided value, if it is not already completed.
            System.out.println("complete method example result: " + complete);// if true, it means that value that you provided was set, otherwise future was already completed (result was available) with the task that you provided to run.

            Thread.sleep(1000);

            // whenComplete is called when the value of the Future is set using CompletableFuture's completeValue method either because task was finished and its result was set or you set the result using complete method.
            future.whenComplete((list, ex) -> System.out.println("whenComplete method example result: " + list)); // [x, y, z]

            // thenApply(...) method is called once completed value is available and after whenComplete is executed.
            CompletableFuture<List<String>> newFuture = future.thenApply(list -> {
                sleep(1000);
                List<String> newList = new ArrayList<>(list);
                newList.add("newXyz");
                return newList;
            });

            // When you need to map the result to some other result instance (may be of some other type), use thenApply(Function)
            // When you need to just modify the result, use thenAccept(Consumer)
            CompletableFuture<Void> newNewFuture = newFuture.thenAccept(list -> list.add("newNewXyz"));

            // get() is a blocking method.
            System.out.println("thenApply, thenAccept method example result: " + newFuture.get()); // [x, y, z, newXyz, newNewXyz]
        }

        // Example of completedExceptionally
        {
            CompletableFuture<List<String>> future = CompletableFuture.supplyAsync(() -> returnSomethingAfterSometime());

            // future's result will be set to 'new AltResult(exception)'
            boolean completeExceptionally = future.completeExceptionally(new RuntimeException("sorry, completing exceptionally"));
            System.out.println("completeExceptionally method result: " + completeExceptionally);
            System.out.println();

            /*
             future.whenComplete(BiConsumer)
             Internally, newFuture will be created an its uniWhenComplete(future,...) method will be called with current future as a input to it.

             From 'future', result will be retrieved and passed to lambda (list, ex) ->....

             If future's result is AltResult (AltResult contains Exception), which is the case in our below example,
             (IMP) Any changes done to list or ex during the execution of BiConsumer will be ignored. An ex instance wrapped with a new CompletionException instance (new AltResult(new CompletionException(ex))) will be set a newFuture's result.
             (IMP) If you want different behaviour, you need to use future.handle(BiFunction)

             If future's result is not an exception, it's some list,
             then modification to the list is considered and that modified list is set to newFuture.
            */


            CompletableFuture<List<String>> newFuture = future.whenComplete((list, ex) -> {
                if (ex != null) {
                    System.out.println("Exception is not null, error message: " + ex.getMessage());

                    list = new ArrayList<>(); // this has no effect
                    list.add("defaultXyz");
                    //ex = null; // this has no effect
                } else {
                    if (list != null) System.out.println("List is not null: " + list);
                }
            });

            // newFuture will also have same list and ex instances.
            CompletableFuture<List<String>> newNewFuture = newFuture.whenComplete((list, ex) -> {
                if (list != null) { // list will be null only
                    list.add("newNewXyz");
                }
            });


            // newNewFuture will have result set as AltResult(CompletionException(RuntimeException))
            // So, you cannot do get() on it. Use exceptionally(...) to avoid throwing exception because of invoking get().
            //System.out.println("Final Result 2: " + newNewFuture.get());

            // If newNewFuture has Result set as an AltResult(ex), then passed function will be executed and its result will be set as a result of finalFuture
            // If newNewFuture has Result set as List, then passed function will not be executed, and list will be set as a result of finalFuture.
            CompletableFuture<List<String>> finalFuture = newNewFuture.exceptionally(ex -> {
                List<String> defaultList = new ArrayList<>();
                defaultList.add("defaultValue");
                return defaultList;
            });
            System.out.println("exceptionally method example result: " + finalFuture.get());// [defaultValue]
        }

        /*
         Example of handle method

         Difference between whenComplete and handle methods:

         Both returns new CompletableFuture.
         "newFuture = future.whenComplete(BiConsumer)" will not reserve the changes in current future's result reserve into the consideration while setting the result of newFuture.
         "newFuture = future.handle(BiFunction)" will set BiFunction's returned value as a result of newFuture.

         newFuture = future.whenComplete(BiConsumer):

             BiConsumer contains list and exception.

             when whenComplete is executed, it sees whether future has AltResult(exception) set as a result.
             If yes,
                 it calls biConsumer.accept(null, ex). In your passed lambda, you can do whatever you want with ex.
                 After that, it calls compareAndSwapObject(this, RESULT, null, r)
                                      compareAndSwapObject(future, current result obj, expected value of current result, modified result)

                 So, even though, in your lambda,
                    If you set exception=null, result value won't be swapped.
                    If not set to null, then it will be wrapped by CompletionException and AltResult(CompletionException) will be set to result of a new future.

             If no,
                then if list is set as a result of the future,
                then modification to the list in your lambda will be considered and that modified list is set to newFuture.


         newFuture = future.handle(BiFunction):

            BiFunction contains list and exception + it is expected to return something.
            That returned value will be set as a result in new CompletableFuture.

         */
        {
            CompletableFuture<List<String>> future = CompletableFuture.supplyAsync(() -> returnSomethingAfterSometime());

            boolean completeExceptionally = future.completeExceptionally(new RuntimeException("sorry, completing exceptionally"));
            //System.out.println("completeExceptionally method result: " + completeExceptionally);

            CompletableFuture<List<String>> newFuture = future.handle((list, ex) -> {
                List<String> newList = new ArrayList<>();
                if (ex != null) {
                    newList.add("default value");
                }
                if (list != null) {
                    newList.addAll(list);
                    newList.add("newXyz");
                }
                return newList;
            });

            newFuture.handle((list, ex) -> {
                if (list != null) {
                    System.out.println("handle method example result: " + list);// [default value]

                }
                if (ex != null) {
                    System.out.println("why ex is not null ???");
                }
                return list;
            });

        }

        /*
            Example of thenCompose method

              thenApply works like a map, thenCompose works like flatMap

              CompletionStage<U> thenApply(Function<T,U>) --- to set something new result in returned Future, use thenApply that takes Function as an argument.
              CompletionStage<Void> thenAccept(Consumer) --- to modify the result of current Future, use thenAccept that takes Consumer as an argument.
              CompletionStage<U> thenCompose(Function<T, CompletionStage<U>> fn) --- thenApply is like a map (converting one result value to another), thenCompose is like a flatMap.

         */
        {
            CompletableFuture<List<String>> future = CompletableFuture.supplyAsync(() -> returnSomething());

           /*
           If you use thenApply (like map function) that returns a Future, you will end up like below
           CompletableFuture<CompletableFuture<List<String>>> newFutureUsingThenApply =
                future.thenApply((resultOfFuture) -> {
                    List<String> anotherList = new ArrayList<>();
                    anotherList.addAll(resultOfFuture1);
                    anotherList.add("x");
                    return CompletableFuture.completedFuture(anotherList);
                });

           So, you need to use thenCompose (like flatMap)

            */

            CompletableFuture<List<String>> newFuture = future.thenCompose((resultOfFuture) -> {
                List<String> anotherList = new ArrayList<>();
                anotherList.addAll(resultOfFuture);
                anotherList.add("x");
                return CompletableFuture.completedFuture(anotherList); // result of this returned future will be set as a result of newFuture
            });
            CompletableFuture<Void> voidCompletableFuture = newFuture.thenAccept(list -> System.out.println("thenCompose method example result: " + list)); // [a, b, c, x]
        }

        /*
            Example of thenCombine method

            CompletableFuture<V> thenCombine(CompletionStage<U> other, BiFunction<T,U,V> fn)
         */
        {
            CompletableFuture<List<String>> future1 = CompletableFuture.supplyAsync(() -> returnSomething());
            CompletableFuture<List<String>> future2 = CompletableFuture.supplyAsync(() -> returnSomething());
            CompletableFuture<List<String>> future3 = CompletableFuture.supplyAsync(() -> {throw new RuntimeException("some error");});

            CompletableFuture<List<String>> newFuture = future1.thenCombine(future2, (future1Result, future2Result) -> {
                List<String> combinedList = new ArrayList<>();
                combinedList.addAll(future1Result);
                combinedList.addAll(future2Result);
                return combinedList;
            });

            newFuture.thenAccept(combinedList -> System.out.println("thenCombine method example result: " + combinedList)); // [a, b, c, a, b, c]

            // if both futures are completed normally, then only callback provided by thenCombine will be executed
            CompletableFuture<List<String>> newFuture1 = future2.thenCombine(future3, (future2Result, future3Result) -> {
                List<String> combinedList = new ArrayList<>();
                combinedList.addAll(future2Result);
                combinedList.addAll(future3Result);
                return combinedList;
            });
            newFuture1.thenAccept(combinedList -> System.out.println("thenCombine method example result: " + combinedList)); // no result
        }

        // Example of acceptEither, thenAcceptBoth, applyToEither
        {
            // acceptEither
            {
                CompletableFuture<List<String>> future1 = CompletableFuture.supplyAsync(() -> returnSomethingAfterSometime());
                CompletableFuture<List<String>> future2 = CompletableFuture.supplyAsync(() -> returnSomething());
                CompletableFuture<List<String>> future3 = CompletableFuture.supplyAsync(() -> {throw new RuntimeException("some error");});
                CompletableFuture<List<String>> future4 = CompletableFuture.supplyAsync(() -> {sleep(1000); throw new RuntimeException("some error");});

                // acceptEither
                future1.acceptEither(future2, future1OrFuture2Result -> System.out.println("acceptEither method example - 1: " + future1OrFuture2Result));// [a, b, c]
                // In f1.acceptEither(f2,...), as far as f1 is completed normally, it will print f1 or f2 result
                // But if f1 is completed with exception, then even though f2 is completed normally, it won't print f2 result.
                future2.acceptEither(future3, future2OrFuture3Result -> System.out.println("acceptEither method example - 2: " + future2OrFuture3Result)); // [a, b, c]
                future3.acceptEither(future2, future3OrFuture2Result -> System.out.println("acceptEither method example - 3: " + future3OrFuture2Result)); // no result
                // when acceptEither is executed on future4, future4's result is not available yet, but future2's result is available. So, action will be taken on future2's result even though later on future4's result is available as an exception.
                future4.acceptEither(future2, future4OrFuture2Result -> System.out.println("acceptEither method example - 4: " + future4OrFuture2Result)); // [a, b, c]
            }

            // runAfterEither
            {
                CompletableFuture<List<String>> future1 = CompletableFuture.supplyAsync(() -> returnSomethingAfterSometime());
                CompletableFuture<List<String>> future2 = CompletableFuture.supplyAsync(() -> returnSomething());
                CompletableFuture<List<String>> future3 = CompletableFuture.supplyAsync(() -> {
                    throw new RuntimeException("some error");
                });
                CompletableFuture<List<String>> future4 = CompletableFuture.supplyAsync(() -> {
                    sleep(1000);
                    throw new RuntimeException("some error");
                });

                future1.runAfterEither(future2, () -> System.out.println("runAfterEither method example - 1: one of the future1 or future2 has result"));// result will be displayed
                future2.runAfterEither(future3, () -> System.out.println("runAfterEither method example - 2: one of the future2 or future3 has result")); // result will be displayed
                future3.runAfterEither(future2, () -> System.out.println("runAfterEither method example - 3: one of the future3 or future2 has result")); // no result
                future4.runAfterEither(future2, () -> System.out.println("runAfterEither method example - 4: one of the future4 or future2 has result")); // result will be displayed
            }

            // thenAcceptBoth
            {
                CompletableFuture<List<String>> future1 = CompletableFuture.supplyAsync(() -> returnSomethingAfterSometime());
                CompletableFuture<List<String>> future2 = CompletableFuture.supplyAsync(() -> returnSomething());

                CompletableFuture<Void> finalFuture = future1.thenAcceptBoth(future2, (future1Result, future2Result) -> future1Result.addAll(future2Result));
                sleep(2000);
                finalFuture.thenAccept(result -> future1.thenAccept(future1Result -> System.out.println("thenAcceptBoth method example: " + future1Result))); // [a, b, c, a, b, c]
            }

            // applyToEither
            {
                CompletableFuture<List<String>> future1 = CompletableFuture.supplyAsync(() -> returnSomethingAfterSometime());
                CompletableFuture<List<String>> future2 = CompletableFuture.supplyAsync(() -> returnSomething());
                CompletableFuture<List<String>> future3 = CompletableFuture.supplyAsync(() -> {throw new RuntimeException("some error");});
                CompletableFuture<List<String>> future4 = CompletableFuture.supplyAsync(() -> {sleep(1000); throw new RuntimeException("some error");});

                future1.applyToEither(future2, (future1OrFuture2Result) -> {
                    List<String> newResult = new ArrayList<>();
                    newResult.addAll(future1OrFuture2Result);
                    newResult.add("x");
                    return newResult;
                }).thenAccept(result -> System.out.println("applyToEither method example - 1: " + result)); // [a ,b ,c, x]

                future2.applyToEither(future3, (future2OrFuture3Result) -> {
                    List<String> newResult = new ArrayList<>();
                    newResult.addAll(future2OrFuture3Result);
                    newResult.add("x");
                    return newResult;
                }).thenAccept(result -> System.out.println("applyToEither method example - 2: " + result)); // [a ,b ,c, x]

                future3.applyToEither(future2, (future3OrFuture2Result) -> {
                    List<String> newResult = new ArrayList<>();
                    newResult.addAll(future3OrFuture2Result);
                    newResult.add("x");
                    return newResult;
                }).thenAccept(result -> System.out.println("applyToEither method example - 3: " + result)); // no result

                future4.applyToEither(future2, (future4OrFuture2Result) -> {
                    List<String> newResult = new ArrayList<>();
                    newResult.addAll(future4OrFuture2Result);
                    newResult.add("x");
                    return newResult;
                }).thenAccept(result -> System.out.println("applyToEither method example - 4: " + result)); // [a ,b ,c, x]
            }

            // anyOf, allOf
            {
                CompletableFuture<List<String>> future1 = CompletableFuture.supplyAsync(() -> returnSomethingAfterSometime());
                CompletableFuture<List<String>> future2 = CompletableFuture.supplyAsync(() -> returnSomething());
                CompletableFuture<List<String>> future3 = CompletableFuture.supplyAsync(() -> {throw new RuntimeException("some error");});
                CompletableFuture<List<String>> future4 = CompletableFuture.supplyAsync(() -> {sleep(1000); throw new RuntimeException("some error");});

                // anyof
                CompletableFuture<Object> anyFuture1 = CompletableFuture.anyOf(future1, future2, future3, future4);
                anyFuture1.whenComplete((result, ex) -> {
                    if(ex != null) {
                        System.out.println("anyOf method example - 1: "+ex.getMessage());
                    } else {
                        if(result != null) {
                            System.out.println("anyOf method example - 1: "+result);// this will be displayed  [a, b, c]
                        }
                    }
                });

                // here future3 will be completed first, but it has an exception set as a result, so anyFuture2 won't have any result set
                CompletableFuture<Object> anyFuture2 = CompletableFuture.anyOf(future3, future1, future2, future4);
                anyFuture2.whenComplete((result, ex) -> {
                    if(ex != null) {
                        System.out.println("anyOf method example - 2: "+ex.getMessage()); // this will be displayed
                    } else {
                        if(result != null) {
                            System.out.println("anyOf method example - 2: "+result);
                        }
                    }
                });

                // allOf
                sleep(1000); // let future1's task complete
                CompletableFuture<Void> allFuture1 = CompletableFuture.allOf(future1, future2);
                allFuture1.thenAccept(emptyAltResult -> System.out.println("allOf method example - 1: all futures have results at this point")); // action will be executed as all the futures have normally completed result (non-exception result).

                CompletableFuture<Void> allFuture2 = CompletableFuture.allOf(future1, future2, future3);
                allFuture2.thenAccept(result -> System.out.println("allOf method example - 2: all futures have results at this point")); // As future3 has the result set as exception, action won't be executed.

            }
        }
    }
}
