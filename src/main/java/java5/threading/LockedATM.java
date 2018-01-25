package java5.threading;

import java.util.concurrent.locks.ReentrantLock;

// p.g. 178 of Cracking Coding Interview book

/*
two ways of creating a thread.
    Thread t = new Thread(runnable)
    OR
    Thread t = new MyThreadClass();

    class MyThreadClass extends Thread {
        @Override
        public void run() {
        ...
        }
    }
As java does not support multiple inheritance. Once you extend Thread class, you can not extend any other class.

 */

@Deprecated // see ThreadAndLocksFundamentals.java
public class LockedATM implements Runnable {
    private ReentrantLock lock;
    private int balance = 100;

    public LockedATM() {
        this.lock = new ReentrantLock();
    }

    public int withdraw(int value) {
        System.out.println("Inside WITHDRAW method: Thread " + Thread.currentThread().getName() + " will try to acquire a lock. Current lock status= " + lock.isLocked());
        //boolean lockApplied = true;
        int temp = balance;
        lock.lock();
        try {
            // after a specific time, lock will be ignored and thread will start accessing the code
            //lockApplied = lock.tryLock(3, TimeUnit.SECONDS);
            //lockApplied = lock.tryLock();// same as tryLock(0, TimeUnit.SECONDS)
            if (lock.isLocked()) {
                System.out.println("Applied a lock in withdraw method by thread " + Thread.currentThread().getName() + " ,hold count=" + lock.getHoldCount() + " , Queue Length: " + lock.getQueueLength());
            } else {
                System.out.println("Accessing a withdraw method block by ignoring locking, hold count=" + lock.getHoldCount() + " , Queue Length: " + lock.getQueueLength());
            }

            //deposit2(value);

            Thread.sleep(1000);
            temp = temp - value;
            Thread.sleep(10000);
            balance = temp;
        } catch (InterruptedException e) {

        }
        if (lock.isLocked()) {// very imp because if lock is unlocked by another method, then it will give IllegalMonitorStateException
            System.out.println("Unlocking a lock in withdraw method: " + Thread.currentThread().getName());
            lock.unlock();
        }
        return temp;
    }
    public int deposit2(int value) {
        System.out.println("Inside DEPOSIT2 method: Thread " + Thread.currentThread().getName() + " will try to acquire a lock. Current lock status=" + lock.isLocked());
        lock.lock();
        if (lock.isLocked()) {
            // holdCount = how many other thread are waiting to acquire a lock because a lock has been put by current thread
            // queueLength = ??? Returns an estimate of the number of threads waiting to acquire a lock
            System.out.println("Applied a lock in deposit2 method by thread " + Thread.currentThread().getName() + " ,hold count=" + lock.getHoldCount() + " , Queue Length: " + lock.getQueueLength());
        } else {
            System.out.println("Accessing deposit2 method block by ignoring locking, hold count=" + lock.getHoldCount() + " , Queue Length: " + lock.getQueueLength());
        }
        return value;
    }
    public int deposit(int value) {
        System.out.println("Inside DEPOSIT method: Thread " + Thread.currentThread().getName() + " will try to acquire a lock. Current lock status=" + lock.isLocked());
        //boolean lockApplied = true;
        //System.out.println("Value:" + value + " ,isHeldByCurrentThread: " + lock.isHeldByCurrentThread());
        lock.lock();
        if (lock.isLocked()) {
            // holdCount = how many other thread are waiting to acquire a lock because a lock has been put by current thread
            // queueLength = ??? Returns an estimate of the number of threads waiting to acquire a lock
            System.out.println("Applied a lock in deposit method by thread " + Thread.currentThread().getName() + " ,hold count=" + lock.getHoldCount() + " , Queue Length: " + lock.getQueueLength());
        } else {
            System.out.println("Accessing deposit method block by ignoring locking, hold count=" + lock.getHoldCount() + " , Queue Length: " + lock.getQueueLength());
        }

        int temp = balance;
        try {
            //Thread.sleep(1000);
            //deposit2(value);

            Thread.sleep(1000);
            temp = temp + value;
            Thread.sleep(10000);
            balance = temp;
        } catch (InterruptedException e) {

        }
        if (lock.isLocked()) {
            System.out.println("Unlocking a lock in deposit method: " + Thread.currentThread().getName());
            lock.unlock();
        }
        return temp;

    }

    public int depositSynchronizedBlock(int value) {
        System.out.println("Inside DEPOSIT_SYNCHRONIZED method: " + Thread.currentThread().getName());
        int temp = balance;
        synchronized (this) {
            try {
                System.out.println("Lock is applied inside depositSynchronizedBlock method on instance: " + this);
                Thread.sleep(1000);
                temp = temp + value;
                Thread.sleep(10000);
                balance = temp;
            } catch (InterruptedException e) {

            }
        }
        return temp;
    }

    public int withdrawSynchronizedBlock(int value) {
        System.out.println("Inside WITHDRAW_SYNCHRONIZED method: " + Thread.currentThread().getName());
        int temp = balance;
        synchronized (this) {
            System.out.println("Lock is applied inside withdrawSynchronized method on instance: " + this);
            try {
                Thread.sleep(1000);
                temp = temp - value;
                Thread.sleep(10000);
                balance = temp;
            } catch (InterruptedException e) {

            }
        }
        return temp;
    }

    synchronized public int depositSynchronizedMethod(int value) {
        System.out.println("Inside DEPOSIT_SYNCHRONIZED_METHOD method: " + Thread.currentThread().getName());
        int temp = balance;
        try {
            System.out.println("Lock is applied inside depositSynchronizedMethod method on instance: " + this);
            Thread.sleep(1000);
            temp = temp + value;
            Thread.sleep(10000);
            balance = temp;
        } catch (InterruptedException e) {

        }
        return temp;
    }

    synchronized public int withdrawSynchronizedMethod(int value) {
        System.out.println("Inside WITHDRAW_SYNCHRONIZED_METHOD method: " + Thread.currentThread().getName());
        int temp = balance;
        System.out.println("Lock is applied inside withdrawSynchronizedMethod method on instance: " + this);
        try {
            Thread.sleep(1000);
            temp = temp - value;
            Thread.sleep(10000);
            balance = temp;
        } catch (InterruptedException e) {

        }
        return temp;
    }

    synchronized static public void staticSynchronizedM1() {
        try {
            System.out.println("Inside static synchronizedM1");
            Thread.sleep(10000);

        } catch (InterruptedException e) {

        }
    }

    synchronized static public void staticSynchronizedM2() {
        try {
            System.out.println("Inside static synchronizedM2");
            Thread.sleep(10000);

        } catch (InterruptedException e) {

        }
    }


    @Override
    public void run() {
        String currentThread = Thread.currentThread().getName();
        if (currentThread.equals("t1")) {
            deposit(100);
        }
        if (currentThread.equals("t2")) {
            withdraw(1);
        }
        if (currentThread.equals("t1SyncBlock")) {
            depositSynchronizedBlock(100);
        }
        if (currentThread.equals("t2SyncBlock")) {
            withdrawSynchronizedBlock(1);
        }
        if (currentThread.equals("t1SyncMethod")) {
            depositSynchronizedMethod(100);
        }
        if (currentThread.equals("t2SyncMethod")) {
            withdrawSynchronizedMethod(1);
        }
        if (currentThread.equals("t1ClassLevelSync")) {
            staticSynchronizedM1();
        }
        if (currentThread.equals("t2ClassLevelSync")) {
            //depositSynchronizedMethod(100);
            staticSynchronizedM2();
        }
        if (currentThread.equals("t1Deadlock")) {
            deadLockMethod();
        }
        if (currentThread.equals("t2Deadlock")) {
            deadLockMethod();
        }

        if (currentThread.equals("t1WaitAndNotify")) {
            waitAndNotifyM1();
        }
        if (currentThread.equals("t2WaitAndNotify")) {
            waitAndNotifyM2();
        }


    }

    // http://crunchify.com/how-to-generate-java-deadlock-programmatically-and-how-to-analyze-deadlock/
    // t1 is holding runnableOb1 and t2 is holding runnableOb2. t1 tries to access runnableOb2's sync method and t2 tries to access runnableOb1's sync method.

    /*
    http://www.javaworld.com/article/2075692/java-concurrency/avoid-synchronization-deadlocks.html
         public static Object obj1 = new Object();
         public static Object obj2 = new Object();
          ...
          public void oneMethod() {
            synchronized (obj1) {
              synchronized (obj2) {
                doSomething();
              }
            }
          }
          public void anotherMethod() {
            synchronized (obj2) {
              synchronized (obj1) {
                doSomethingElse();
              }
            }
          }

        This methods would probably create a great deadlock if called by many threads. This is because the objects are locked in different order.
        This is one of the most common reasons of deadlocks, so if you want to avoid them, be sure that the locks are aquired in order.
     */
    private LockedATM anotherLockedATMObj;

    public void setAnotherThread(LockedATM obj) {
        this.anotherLockedATMObj = obj;
    }

    synchronized public void deadLockMethod() {
        System.out.println("Thread entered in deadLockMethod: " + Thread.currentThread().getName());
        try {
            anotherLockedATMObj.deadLockMethod();
            Thread.sleep(5000);
        } catch (InterruptedException e) {

        }
        System.out.println("Leaving deadLockMethod method: " + Thread.currentThread().getName());
    }

    Object lockingObj = new Object();

    public void waitAndNotifyM1() {
        System.out.println();
        System.out.println();

        synchronized (lockingObj) {
            System.out.println();
            System.out.print("lockingObj is locked from waitAndNotifyM1() by " + Thread.currentThread().getName());
            for (int i = 0; i < 10; i++) {
                System.out.print(i + " ");
                if (i == 5) {
                    try {
                        System.out.println();
                        System.out.println(Thread.currentThread().getName() + " WAITs for another thread to acquire a lock and notify");
                        // it will release the lock on lockingObj and let the thread go in sleep till it is notified by another thread to acquire a lock again.
                        // so that another thread who is waiting to acquire a lock on lockingObj can acquire a lock on it.
                        lockingObj.wait();
                        //lockingObj.wait(5000);// waits till 5 secs for another thread to acquire a lock on lockingObj. If not, then this thread will reaquire a lock.
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void waitAndNotifyM2() {
        System.out.println();
        System.out.println();

        synchronized (lockingObj) {
            System.out.println();
            System.out.print("lockingObj is locked from waitAndNotifyM2() by " + Thread.currentThread().getName());

            for (int i = 0; i < 10; i++) {
                System.out.print(i + " ");
            }

            System.out.println();
            System.out.println(Thread.currentThread().getName() + " is NOTIFYING waiting thread to acquire a lock again and callSupplier its execution after I releases a lock");
            /*
             Wakes up a single thread that is WAITING on this object's monitor(to acquire a lock on lockingObj).
             If any threads are waiting on this object(lockingObj), one of them is chosen to be awakened.
             The choice is arbitrary and occurs at the discretion of the implementation.

             The awakened threads will not be able to proceed until the current thread relinquishes the lock on this object(lockingObj). It means waiting thread has to wait till synchronized block is completed.
             */
            lockingObj.notify();
            /*
            An example would be a set of threads waiting for a certain task to finish; once the task has finished, all waiting threads can continue with their business. In such a case you would use notifyAll() to wake up all waiting threads at the same time.
             */
            //lockingObj.notifyAll();

        }
    }

    public static void main(String[] args) throws InterruptedException {
        // Two thread sharing the same Runnable instance.
        // when t1 has applied a lock through deposit method, t2 can't access withdraw method code until lock is released by t1.

        {
            LockedATM obj1 = new LockedATM();
            Thread thread1 = new Thread(obj1, "t1");
            Thread thread2 = new Thread(obj1, "t2");

            thread1.start();
            thread2.start();
        }

        /*
        // Using synchronized block. Above code is same as this code
        // when t1SyncBlock has applied a lock through depositSynchronizedBlock method on obj1, t2SyncBlock can't access withdrawSynchronizedBlock method code until lock is released by t1SyncBlock.
        // So, here lock is applied on a specific block of the code for a specific LockedATM instance
        {
            LockedATM obj1 = new LockedATM();
            Thread thread1 = new Thread(obj1, "t1SyncBlock");
            Thread thread2 = new Thread(obj1, "t2SyncBlock");

            thread1.start();
            thread2.start();
        }

        // synchronized methods example
        // when t1SyncMethod has applied a lock on depositSynchronizedMethod  for obj1, t2SyncMethod can't access withdrawSynchronizedMethod until lock is released by t1SyncMethod.
        // So, here lock is applied on a specific methods of a specific LockedATM instance
          /*
            synchronized method is same as
            public int depositSynchronizedMethod(int value) {
                synchronized(this) { // acquiring lock on this object for entire method code
                    ...
                }
            }

            What is REENTRANCE?
            If a thread already holds the lock on a monitor object(here 'this'), it has access to all blocks synchronized on the same monitor object. This is called reentrance.
            The thread can reenter any block of code for which it already holds the lock.
         */
        /*
        {
            LockedATM obj1 = new LockedATM();
            Thread thread1 = new Thread(obj1, "t1SyncMethod");
            Thread thread2 = new Thread(obj1, "t2SyncMethod");

            thread1.start();
            thread2.start();
        }
        */


        // Two thread sharing different Runnable instance.
        // ReentrantLock instances are different for two different threads here. So, t1's lock doesn't stop t2.
        /*{
            LockedATM obj1 = new LockedATM();
            LockedATM obj2 = new LockedATM();
            Thread thread1 = new Thread(obj1, "t1");
            Thread thread2 = new Thread(obj2, "t2");

            thread1.start();
            thread2.start();
        }*/

        // Class level synchronization
        // two threads are using different instances of Runnable. But as synchronizations are applied on static methods, regardless of Runnable instance, it will lock all other synchronized static methods.
        /*{
            LockedATM obj1 = new LockedATM();
            LockedATM obj2 = new LockedATM();

            Thread thread1 = new Thread(obj1, "t1ClassLevelSync");
            Thread thread2 = new Thread(obj2, "t2ClassLevelSync");

            thread1.start();
            thread2.start();
        }*/

        // Wait and Notify example
        // http://www.programcreek.com/2009/02/notify-and-wait-example/
        /*{
            LockedATM obj1 = new LockedATM();
            Thread thread1 = new Thread(obj1, "t1WaitAndNotify");
            Thread thread2 = new Thread(obj1, "t2WaitAndNotify");

            thread1.start();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            thread2.start();
            thread1.join(); // makes current thread to wait till thread1 completes. in this method context, current thread is main thread.
            thread2.join();
        }*/

        // Deadlock example
        // http://crunchify.com/how-to-generate-java-deadlock-programmatically-and-how-to-analyze-deadlock/
        /*{
            LockedATM obj1 = new LockedATM();
            LockedATM obj2 = new LockedATM();
            obj1.setAnotherThread(obj2);
            obj2.setAnotherThread(obj1);

            Thread thread1 = new Thread(obj1, "t1Deadlock");
            Thread thread2 = new Thread(obj2, "t2Deadlock");

            thread1.start();
            thread2.start();
        }*/

    }
}
