package algorithms.crackingcodinginterviewbook._8thread_and_locks;

import java.util.concurrent.Semaphore;

/*
pg. 456 of Cracking Coding Interview book

Call In Order:
    Suppose we have the following code:
    public class Foo {
        public Foo() { ... }
        public void first() { ... }
        public void second() { ... }
        public void third() { ... }
    }
    The same instance of Foo will be passed to three different threads.
    ThreadA will call first(), ThreadB will call second() and ThreadC will call third() methods.
    You have to make sure that first() is called before second() and second() is called before third().

This algorithm is a good example of Semaphore usage.

Lock vs Semaphore:

        Semaphore is also like a ReentrantLock with a some improvements.

        ReentrantLock allows only one thread to access a block of code, whereas Semaphore allows more than 1 threads to acquire locks on the same block of code.
        Here, 3 threads can enter the block of the code and others have to wait until one of them releases lock.
            +
        The thread that acquires a lock on some ReentrantLock object, the same thread can release it. If some other thread tries to release it, java will throw IllegalMonitorStateException.
        That is why lock.unlock() has to be surrounded by 'if(lock.isHeldByCurrentThread())'
        Whereas, one thread can acquire a lock on semaphore object and some other thread can release it.

        You can also set fairness=true/false just like ReentrantLock.

        Semaphore semaphore = new Semaphore(3, <fairness true/false>);
*/
public class _5CallInOrder {

    public static void main(String[] args) throws InterruptedException {
        Foo foo = new Foo();

        Thread t1 = new Thread() {
            @Override
            public void run() {
                foo.first();
            }
        };

        Thread t2 = new Thread() {
            @Override
            public void run() {
                try {
                    foo.second();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread t3 = new Thread() {
            @Override
            public void run() {
                try {
                    foo.third();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        t3.start();
        t2.start();
        t1.start();

        t3.join();
        t2.join();
        t1.join();
    }

    static class Foo {

        private Semaphore s2, s3;

        public Foo() throws InterruptedException {
            s2 = new Semaphore(1);
            s3 = new Semaphore(1);

            // main thread acquires a lock for second() and third()
            // As per above configuration, only one thread can acquire a lock on s2 and s3.
            // So when second() will try to acquire a lock on s2, it has to wait till first() is executed.
            // Similarly, when third() will try to acquire a lock on s3, it has to wait till second() is executed.
            // Here, we could not use ReentrantLock because locks acquired by main thread need to be released by some other thread.
            s2.acquire();
            s3.acquire();
        }

        public void first() {
            System.out.println("first() called");
            s2.release();
        }

        public void second() throws InterruptedException {
            s2.acquire();
            System.out.println("second() called");
            //...
            s2.release();
            s3.release();
        }

        public void third() throws InterruptedException {
            s3.acquire();
            System.out.println("third() called");
            //...
            s3.release();
        }
    }
}
