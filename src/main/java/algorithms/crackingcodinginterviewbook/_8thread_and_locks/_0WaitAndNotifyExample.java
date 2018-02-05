package algorithms.crackingcodinginterviewbook._8thread_and_locks;

/*
wait()/wait(time) and notify()/notifyAll() can be invoked only on locked object.
So, it works with block-level synchronization only because these methods are invoked on locked object. It cannot be used in method-level synchronization.

There are some limitations with wait and notify strategy that is overcome using ReentrantLock (See ReentrantLock.java)

Wait and Notify example
http://www.programcreek.com/2009/02/notify-and-wait-example/

*/

@SuppressWarnings("Duplicates")
public class _0WaitAndNotifyExample {

    Object lockedObject = new Object();

    public void waitMethod() {
        System.out.println();
        System.out.println();

        System.out.print(Thread.currentThread().getName() + " in waiting to acquire a lock in waitMethod().");

        synchronized (lockedObject) {
            System.out.println();
            System.out.print(Thread.currentThread().getName() + " acquired a lock in waitMethod()");

            for (int i = 0; i < 10; i++) {

                System.out.println(i + " in waitMethod()");

                if (i == 5) {
                    try {
                        System.out.println();
                        System.out.println(Thread.currentThread().getName() + " WAITs (goes to sleep mode and releases the lock). It will wait for another thread to acquire a lock and notify");
                        // it will release the lock on lockedObject and let the thread go in sleep till it is notified by another thread to acquire a lock again.
                        // so that another thread who is waiting to acquire a lock on lockedObject can acquire a lock on it.
                        lockedObject.wait();
                        //lockedObject.wait(5000);// waits till 5 secs for another thread to acquire a lock on lockedObject. If not, then this thread will reacquire a lock and continue the execution.
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void notifyMethod() {
        System.out.println();
        System.out.println();

        System.out.print(Thread.currentThread().getName() + " in waiting to acquire a lock in notifyMethod().");

        synchronized (lockedObject) {
            System.out.println();
            System.out.print(Thread.currentThread().getName() + " acquired a lock in notifyMethod()");

            for (int i = 0; i < 100; i++) {

                if (i == 50) {
                    System.out.println(Thread.currentThread().getName() + " is NOTIFYING waiting thread to acquire a lock again and callSupplier its execution after I releases a lock");
                    /*
                     Sends a signal to any one of the waiting threads to wake up. The choice is arbitrary and occurs at the discretion of the implementation.
                     You have no control over which waiting thread will be awakened. Using ReentrantLock, you can control it by setting Fairness Policy (See ReentrantLockExample.java)

                     But lock won't be released until this block is completely executed. Even though, waiting thread is notified, it has to wait till lock is released.
                     */
                    lockedObject.notify();

                    //An example would be a set of threads waiting for a certain task to finish; once the task has finished, all waiting threads can continue with their business.
                    // In such a case you would use notifyAll() to wake up all waiting threads at the same time.

                    //lockedObject.notifyAll();

                }
                System.out.println(i + " in notifyThread()");
            }

            System.out.println();

        }
    }

    public static void main(String[] args) throws InterruptedException {
        _0WaitAndNotifyExample obj1 = new _0WaitAndNotifyExample();

        Thread thread1 = new Thread("t1") {
            @Override
            public void run() {
                obj1.waitMethod();
            }
        };

        Thread thread2 = new Thread("t2") {
            @Override
            public void run() {
                obj1.notifyMethod();
            }

        };

        thread1.start();
        Thread.sleep(1000);
        thread2.start();

        thread1.join(); // makes current thread to wait till thread1 completes. in this method context, current thread is main thread.
        thread2.join();
    }
}
