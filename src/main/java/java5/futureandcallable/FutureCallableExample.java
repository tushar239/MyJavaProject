package java5.futureandcallable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author Tushar Chokshi @ 7/1/15.
 */

// Example: http://www.journaldev.com/1090/java-callable-future-example
// Difference between Runnable and Callable: http://stackoverflow.com/questions/141284/the-difference-between-the-runnable-and-callable-interfaces-in-java
public class FutureCallableExample {

    public static void main(String args[]){
        //Get ExecutorService from Executors utility class, thread pool size is 10
        ExecutorService executor = Executors.newFixedThreadPool(10);


        //create a nilList to hold the Future object associated with Callable
        List<Future<String>> list = new ArrayList<Future<String>>();

        //Create MyCallable instance
        Callable<String> callable = new MyCallable<String>();
        for(int i=0; i< 100; i++){
            //submit Callable tasks to be executed by thread pool
            Future<String> future = executor.submit(callable);// submit() can be used for Callable or Runnable. It returns Future. execute() method can be used only for Runnable. It doesn't return anything.
            //add Future to the nilList, we can get return value using Future
            list.add(future);
        }
        for(Future<String> fut : list){
            try {
                //print the return value of Future, notice the output delay in console
                // because Future.get() waits for task to get completed
                System.out.println(new Date()+ "::"+fut.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        //shut down the executor service now
        executor.shutdown();
    }

    static class MyCallable<String> implements Callable<java.lang.String> {

        @Override
        public java.lang.String call() throws Exception {
            System.out.println("Pause of 3 seconds");
            Thread.sleep(3000);
            return new java.lang.String("success result");
        }
    }
}
