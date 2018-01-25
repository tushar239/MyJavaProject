package algorithms._8thread_and_locks;

/*
Threads
-------
    - There are two ways to create thread.

        1) Create Runnable's instance and pass it to Thread's constructor and create an instance of Thread.
          Start a Thread using threadInstance.start()

        public class MyRunnable implements Runnable {
            @Override
            public void run() {
                ...
            }
        }

        public class Client {
            MyRunnable runnable = new MyRunnable();

            Thread thread = new Thread(runnable);
            thread.start();
            ....
        }

        2) Create a subclass of Thread itself without a need of Runnable and override a run() method in that subclass.

        public class MyThread extends Thread {
            @Override
            public void run() {
                ...
            }
        }

        public class Client {
            MyThread thread = new MyThread();
            thread.start();
            ....
        }


    We try to avoid 2nd option because by extending a Thread class stops us using multiple inheritance.
        MyThread cannot extend anymore classes, whereas MyRunnable can.
    Also Thread class has a lot many other behaviours that we may not need to use. So, using a subclass of Runnable might be sufficient in most cases.

    - How do you start the thread?

        Thread thread = new Thread(runnable);
        thread.start();
        or
        MyThread thread = new MyThread();
        thread.start();

    - How do you sleep the thread?

      try {
        thread.sleep(milliseconds)
      } catch (InterruptedException e) {
        ...
      }

Synchronization & Locking
-------------------------
Two Types of synchronization

    - Method level synchronization
        - instance-level synchronization

            When you synchronize non-static(instance-level) method(s), that method can't be accessed my more than one thread at the same time, if both threads are trying to access that method for the same instance of the class at the same time.

            If threads use different instances of the class to access synchronized non-static method together at the same time, then they can access it together.
            If one thread is accessing one synchronized non-static method, another thread can access another synchronized non-static method for the same class instance. This is different than class-level (static method) synchronization.

        - class-level synchronization

            static-level(class level) synchronized method(s) can't be accessed by multiple threads.

            REMEMBER:
            If one thread is accessing one synchronized static method, another thread can't access another synchronized static method also. Because it acts like class-level locking.
            This is different than instance-level synchronization.

    - Block level synchronization (object level locking)

        Instead of synchronizing entire method, you can synchronize(lock) a block of the code inside a method.
        This is achieved by locking an access to an object.

Locks

    There are following types of locks:
        - ReentrantLock
        - ReentrantReadWriteLock

    ReentrantLock
    -------------
    Using ReentrantLock's lock() method on an object, thread can acquire a lock on that object.

    It is similar to block-level synchronization with certain major advantages.

        - lock on the object can be obtained inside one method and released inside another method.
        - multiple locks (holds on lock) can be acquired by same thread. But all the holds has to be released before any other thread can acquire a lock.
        - lock.tryLock(...) method
             If lock is not available to acquire, then

                  lock.tryLock() method
                        will return false immediately and will execute the code in method without locking applied. Basically, it will ignore locking applied by another thread.
                  lock.tryLock(time) method
                        will wait for lock to be released by another thread for some time.
                        If lock is released by another thread in that time duration, it will acquire a lock and return true and then execute the code in the method.
                        Otherwise, it will return false and execute the code in method without locking applied. Basically, it will ignore locking applied by another thread.

             Unlike to lock() method, it will not wait forever to acquire a lock.

        - Fairness policy
          new ReentrantLock(true) - fairness policy is set to true
          If multiple threads are waiting to acquire a lock, the longest waiting thread can be given a lock after a lock is released by current thread.

        - Condition
          Just like wait() and notifyAll(), you can use condition.await() to let current thread wait and let other thread acquire a lock. That other thread has to use condition.signal() that will notify waiting thread to acquire a lock again AFTER that other thread unlocks the lock.
          await(time) - if waiting thread is not signaled(notified) by another thread, then after sometime

        - lock.holdCount() method
          This method gives a count of total number of locks acquired by the CURRENT THREAD.
          A thread can hold multiple locks on the same lock object, but it has to release all locks before any other thread can acquire it.


    What is IllegalMonitorStateException?

        IMPORTANT: Lock can be unlocked thread who acquired it.

        If it you try to unlock a lock acquired by some other thread, then you will get this exception.
        So, it is important to check whether a lock is held by current thread before trying to unlock it.

        finally {
            if (lock.isHeldByCurrentThread()) {// do not use lock.isLocked() for unlocking purpose because it returns true, if lock is acquired by ANY thread.
                lock.unlock();
            }
        }

        Remember to unlock a lock inside finally block. If you don't and if method throws some exception before the lock is unlocked, then that lock will never be unlocked.

    ReentrantReadWriteLock
    ----------------------
    ReentrantReadWriteLock is similar to ReentrantLock with one more advantage.
    You can apply read and write locks.

    - Multiple threads can read the data at the same time, as long as there’s no thread is updating the data.
    - Only one thread can update the data at a time, causing other threads (both readers and writers) block until the write lock is released.
    - If a thread attempts to update the data while other threads are reading, the write thread also blocks until the read lock is released.

    lock.readLock().lock()
    lock.readLock().unlock()

    lock.writeLock().lock()
    lock.writeLock().unlock()


*/

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class _0ThreadAndLocksFundamentals {

    public static class MyClass1 {
        public synchronized void method1() {
            System.out.println(Thread.currentThread().getName() + " thread entered method1()");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
            }

        }

        public synchronized void method2() {
            System.out.println(Thread.currentThread().getName() + " thread entered method2()");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
            }
        }

        public static void staticMethod1() {
            System.out.println(Thread.currentThread().getName() + " thread entered staticMethod1()");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
            }
        }

        public static void staticMethod2() {
            System.out.println(Thread.currentThread().getName() + " thread entered staticMethod2()");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
            }
        }
    }

    public static class MyClass2 {
        public MyClass1 cls;

        public MyClass2(MyClass1 cls) {
            this.cls = cls;
        }

        public void methodWithCls1Locking() {
            //System.out.println(Thread.currentThread().getName() + " thread entered methodWithCls1Locking()");

            synchronized (cls) {
                System.out.println(Thread.currentThread().getName() + " has obtained a lock on " + cls.toString() + "instance.");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                }
            }
        }
    }

    public static class MyRunnable1 implements Runnable {
        private MyClass1 cls;

        public MyRunnable1(MyClass1 cls) {
            this.cls = cls;
        }

        @Override
        public void run() {
            cls.method1();  //---- accessing method1
        }
    }

    public static class MyRunnable2 implements Runnable {
        private MyClass1 cls;

        public MyRunnable2(MyClass1 cls) {
            this.cls = cls;
        }

        @Override
        public void run() {
            cls.method2(); //---- accessing method2
        }
    }

    public static class MyRunnable3 implements Runnable {

        public MyRunnable3() {
        }

        @Override
        public void run() {
            MyClass1.staticMethod1(); //---- accessing staticMethod1
        }
    }

    public static class MyRunnable4 implements Runnable {

        public MyRunnable4() {
        }

        @Override
        public void run() {
            MyClass1.staticMethod2(); //---- accessing staticMethod2
        }
    }

    public static void main(String[] args) throws InterruptedException {
       /* System.out.println("\033[1m" + ".....Testing Method-Level synchronization....." + "\033[0m");
        System.out.println("\033[1m" + "Testing synchronized instance-level method calls with threads accessing a synchronized method of class with the same instance of that class." + "\033[0m");
        {
            // Both t1 and t2 CAN NOT access MyClass' method1() at the same time because they are using the same instance of MyClass
            // t2 has to wait till t1 completely comes out of method1() of cls instance
            MyClass1 cls1 = new MyClass1();
            Thread t1 = new Thread(new MyRunnable1(cls1), "t1");
            Thread t2 = new Thread(new MyRunnable1(cls1), "t2");
            t1.start();
            t2.start();

            t1.join(); // main thread cannot move to next step until t1 is completed
            t2.join();
        }

        System.out.println("\033[1m" + "Testing synchronized instance-level method calls with different instances of a class. Two threads accessing the same synchronized method with different instances of a class do not have to wait for each other to come out of that method." + "\033[0m");
        {
            // Both t1 and t2 can access MyClass' method1() at the same time because they are using different instances of MyClass
            MyClass1 cls1 = new MyClass1();
            MyClass1 cls2 = new MyClass1();
            Thread t1 = new Thread(new MyRunnable1(cls1), "t1");
            Thread t2 = new Thread(new MyRunnable1(cls2), "t2");
            t1.start();
            t2.start();

            t1.join();
            t2.join();
        }

        System.out.println("\033[1m" + "Testing synchronized instance-level method calls for two different methods with the same class instance. Even though, two threads are accessing different synchronized non-static methods, other threads have to wait for first one to come out of a method as far as threads are using the same instance of a class." + "\033[0m");
        {
            // t1 can access MyClass' method1() and t2 can access MyClass' method2() at the same time.
            MyClass1 cls1 = new MyClass1();
            Thread t1 = new Thread(new MyRunnable1(cls1), "t1");
            Thread t2 = new Thread(new MyRunnable2(cls1), "t2");
            t1.start();
            t2.start();

            t1.join();
            t2.join();
        }

        System.out.println("\033[1m" + "Testing synchronized class-level method calls. As these are static methods, threads have to wait for each other to come out of a method even though they are accessing same/different synchronized static methods." + "\033[0m");
        {
            // Both t1,t2,t3 CAN NOT access MyClass' staticMethod1()/staticMethod2() at the same time because staticMethod1() is a static method (class-level method).
            // Two threads cannot access the same or different synchronized static methods at the same time.
            // t2 has to wait till t1 completely comes out of method1() of cls instance
            // t2 and t3 have to wait till t1 is completely out of staticMethod1()
            Thread t1 = new Thread(new MyRunnable3(), "t1");//---accessing staticMethod1()
            Thread t2 = new Thread(new MyRunnable3(), "t2");//---accessing staticMethod1()
            Thread t3 = new Thread(new MyRunnable4(), "t3");//---accessing staticMethod2()
            t1.start();
            t2.start();
            t3.start();

            t1.join();
            t2.join();
            t3.join();
        }

        System.out.println("\033[1m" + ".....Testing Block-Level synchronization....." + "\033[0m");
        System.out.println("\033[1m" + "Testing synchronized class-level method calls. Lock is obtained by a thread on MyClass1' object(cls1). If another thread tries obtain the lock on same instance, then it has to wait till first thread completes." + "\033[0m");
        {
            MyClass1 cls1 = new MyClass1();

            MyClass2 cls2 = new MyClass2(cls1);

            // Both t1 and t2 are using the same instance of MyClass1
            Thread t1 = new Thread("t1") {
                @Override
                public void run() {
                    cls2.methodWithCls1Locking();
                }
            };
            Thread t2 = new Thread("t2") {
                @Override
                public void run() {
                    cls2.methodWithCls1Locking();
                }
            };

            t1.start();
            t2.start();

            t1.join();
            t2.join();
        }

        System.out.println("\033[1m" + "Testing synchronized class-level method calls. Lock is obtained by a thread on MyClass1' object(cls1). If another thread tries obtain the lock on different instance, then it doesn't have to wait till first thread completes." + "\033[0m");
        {
            MyClass2 cls21 = new MyClass2(new MyClass1());
            MyClass2 cls22 = new MyClass2(new MyClass1());

            // t1 and t2 are using the different instances of MyClass1
            Thread t1 = new Thread("t1") {
                @Override
                public void run() {
                    cls21.methodWithCls1Locking();
                }
            };
            Thread t2 = new Thread("t2") {
                @Override
                public void run() {
                    cls22.methodWithCls1Locking();
                }
            };

            t1.start();
            t2.start();

            t1.join();
            t2.join();
        }*/

        System.out.println("\033[1m" + ".....Testing Reentrant Lock....." + "\033[0m");
        {

            // ReentrantLock with Fairness Policy set to true
            // If Fairness policy is set to true, then it will make sure that longest waiting thread acquires a lock first.
            ReentrantLock lock = new ReentrantLock(true);
            ReentrantLockExample reentrantLockExample = new ReentrantLockExample(lock);
            Thread firstThread = new Thread("firstThread") {
                @Override
                public void run() {
                    try {
                        reentrantLockExample.firstThread();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            Thread secondThread = new Thread("secondThread") {
                @Override
                public void run() {
                    try {
                        reentrantLockExample.secondThread();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            Thread thirdThread = new Thread("thirdThread") {
                @Override
                public void run() {
                    try {
                        reentrantLockExample.thirdThread();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            Thread fourthThread = new Thread("fourthThread") {
                @Override
                public void run() {
                    try {
                        reentrantLockExample.fourthThread();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };

            firstThread.start();
            secondThread.start();
            thirdThread.start();
            fourthThread.start();

            firstThread.join();
            secondThread.join();
            thirdThread.join();
            fourthThread.join();

        }
    }


    // https://www.youtube.com/watch?v=fjMTaVykOpc
    public static class ReentrantLockExample {
        private final ReentrantLock lock;
        private final Condition condition;

        public ReentrantLockExample(ReentrantLock lock) {
            this.lock = lock;
            this.condition = lock.newCondition();
        }

        //private int count = 0;

        private void increment(String threadName) throws InterruptedException {
            for (long i = 0; i < 5; i++) {
                //count++;
                System.out.println("increment method is executed by " + threadName);
                Thread.sleep(1000);
            }
        }


        private void firstThread() throws InterruptedException {
            System.out.println("firstThread Waiting to acquire lock...Total locks by this thread: " + lock.isHeldByCurrentThread());
            lock.lock();
            System.out.println("firstThread acquired a lock in firstThread()...Total locks by this thread: " + lock.getHoldCount());

            firstThreadSecondLock();

            try {
                increment("firstThread");
            } finally { // IMPORTANT: always unlock in finally block. This is useful, if increment() method throws an exception.
                // IMPORTANT: lock can be unlocked thread who acquired it. If it you try to unlock a lock acquired by some other thread, then you will get 'IllegalMonitorStateException'
                // do not use lock.isLocked() for unlocking purpose because it returns true, if lock is acquired by ANY thread.
                if (lock.isHeldByCurrentThread()) {
                    lock.unlock();
                    System.out.println("firstThread released a lock in firstThread()...Total locks by this thread: " + lock.getHoldCount());
                }
            }
        }

        // Thread that has already acquired a lock can acquire more locks. It doesn't have to wait like other threads.
        // One thread can acquire multiple locks, but it has to release all locks before any other thread can acquire it.
        // But it has to release all the locks that it has acquired before another thread can acquire a lock.
        // lock.getHoldCount() method tells how many locks are acquired by CURRENT thread.
        private void firstThreadSecondLock() {

            lock.lock();

            try {

                System.out.println("firstThread acquired second lock in firstThreadSecondLock()...Total locks held by this thread: " + lock.getHoldCount());

            } finally {
                if (lock.isHeldByCurrentThread()) {
                    lock.unlock();// very important
                    System.out.println("firstThread released second lock in firstThreadSecondLock()...Total locks held by this thread: " + lock.getHoldCount());
                }
            }
        }

        private void secondThread() throws InterruptedException {
            System.out.println("secondThread Waiting to acquire lock in secondThread()...Total locks by this thread: " + lock.getHoldCount());

            lock.lock();

            System.out.println("secondThread acquired a lock in secondThread()...Total locks held by this thread: " + lock.getHoldCount());

            System.out.println("secondThread has put itself for waiting and release a lock in secondThread()");
            condition.await(1, TimeUnit.SECONDS);
            System.out.println("secondThread acquired a lock again in secondThread() either after it is signaled(notified) by other thread or after waiting time is elapsed...Total locks held by this thread: " + lock.getHoldCount());

            try {
                increment("secondThread");
            } finally {
                if (lock.isHeldByCurrentThread()) {
                    lock.unlock();
                    System.out.println("secondThread released a lock...Total locks held by this thread: " + lock.getHoldCount());
                }
            }
        }

        private void thirdThread() throws InterruptedException {
            System.out.println("thirdThread Waiting to acquire lock in thirdThread()...Total locks by this thread: " + lock.isHeldByCurrentThread());

            lock.lock();

            System.out.println("thirdThread acquired a lock in thirdThread()...Total locks held by this thread: " + lock.getHoldCount());

            condition.signal();
            System.out.println("thirdThread signaled(notified) all waiting threads, but waiting thread can acquire a lock only after this thread unlocks it...");

            try {
                increment("thirdThread");
            } finally {
                if (lock.isHeldByCurrentThread()) {
                    lock.unlock();
                    System.out.println("thirdThread released a lock in thirdThread()...Total locks held by this thread: " + lock.getHoldCount());
                }
            }
        }

        private void fourthThread() throws InterruptedException {
            System.out.println("fourthThread Waiting to acquire lock in fourthThread()...Total locks by this thread: " + lock.isHeldByCurrentThread());

            /*
             What is tryLock?
             ----------------
             If lock is not available to acquire, then
                  tryLock() method will return false immediately and will execute the code in method without locking applied. Basically, it will ignore locking applied by another thread.
                  tryLock(time) method will wait for lock to be released by another thread for some time.
                               If lock is released by another thread in that time duration, it will acquire a lock and return true and then execute the code in the method.
                               Otherwise, it will return false and execute the code in method without locking applied. Basically, it will ignore locking applied by another thread.
             Unlike to lock() method, it will not wait forever to acquire a lock.
            */

            // This thread will try to acquire a lock and wait for some time, if it is acquired by some other thread.
            boolean acquiredLock = lock.tryLock(1, TimeUnit.SECONDS);

            System.out.println("Could fourthThread acquire a lock? "+ acquiredLock +"..., Total locks held by this thread: " + lock.getHoldCount());

            try {
                increment("fourthThread");
            } finally {
                if (lock.isHeldByCurrentThread()) {
                    lock.unlock();
                    System.out.println("fourthThread released a lock in fourthThread()...Total locks held by this thread: " + lock.getHoldCount());
                }
            }
        }
    }

}
