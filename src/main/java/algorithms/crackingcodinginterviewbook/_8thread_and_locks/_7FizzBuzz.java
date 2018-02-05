package algorithms.crackingcodinginterviewbook._8thread_and_locks;

import java.util.function.Predicate;

/*
p.g. 458 of Cracking Coding Algorithms book

FizzBuzz:
    You are told to print the numbers from 1 to n.
    However, when number is divisible by 3, print 'Fizz'.
    When it is divisible by 5, print 'Buzz'.
    When it is divisible by 3 and 5, print 'FizzBuzz'.
    Implement a multithreaded version of FizzBuzz with four threads. One check for divisibility of 3 and prints 'Fizz'.
    Another thread is responsible for divisibility of 5 and prints 'Buzz'.
    Third one is responsible for divisibility of 3 and 5 and prints 'FizzBuzz'.
    Fourth one does the numbers.
*/
public class _7FizzBuzz {

    private static Object objectToLock = new Object();
    private static int n = 100;
    private static int count = 1;

    private static _7FizzBuzz fizzBuzz = new _7FizzBuzz();

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread() {
            @Override
            public void run() {
                while (count <= n) {
                    fizzBuzz.print((cnt) -> (cnt % 3) == 0, "Fizz", n);
                }
            }
        };
        Thread t2 = new Thread() {
            @Override
            public void run() {
                while (count <= n) {
                    fizzBuzz.print((cnt) -> (cnt % 5) == 0, "Buzz", n);
                }
            }
        };

        Thread t3 = new Thread() {
            @Override
            public void run() {
                while (count <= n) {
                    fizzBuzz.print((cnt) -> (((cnt % 3) == 0) && ((cnt % 5) == 0)), "FizzBuzz", n);
                }
            }
        };

        Thread t4 = new Thread() {
            @Override
            public void run() {
                while (count <= n) {
                    fizzBuzz.print((cnt) -> (((cnt % 3) != 0) && ((cnt % 5) != 0)), "", n);
                }
            }
        };

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        t1.join();
        t2.join();
        t3.join();
        t4.join();
    }

    private void print(Predicate<Integer> condition, String strToPrint, int n) {
        synchronized (objectToLock) {
            if (count <= n) {// when one thread is doing this comparison, another thread might increment the count. So, this block of the code has to be synchronized.
                if (condition.test(count)) {
                    System.out.println(count + ":" + strToPrint);
                    count++;
                }
            }
        }
    }
}
