package algorithms._0Fundamentals;

/*

From Cracking Coding Interviews Book
------------------------------------

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

Synchronization

    Two Types of synchronization

        - Method level synchronization
            - instance method level synchronization
            - class method (static method) level synchronization

            See InstanceAndClassMethodLevelSynchronization.java

        - Block level synchronization (object level locking)

            See BlockLevelSynchronization.java

            Wait and Notify can work only with Block-Level synchronization because these methods can be invoked only on a locked object.

            ReentrantLock is a better alternative of Block-Level synchronization (See ReentrantLockExample.java)

Locks

    - ReentrantLock and ReentrantReadWriteLock

        ReentrantLock is a better alternative of Block-Level synchronization

        See ReentrantLockExample.java
            DiningPhilosophers.java

Semaphore

    See CallInOrder.java


(IMPORTANT)
When to use Synchronized block, ReentrantLock and Semaphore?

    Synchronized block - when you don't want multiple threads to access the same code at the same time (e.g. FizzBuzz.java)

    ReentrantLock - When you want to acquire a lock in one method and release in another.
                     When you are ok with a limitations of ReentrantLock
                        - thread that acquires a lock, only that thread can release the lock. If another thread tries to release the lock, java will throw IllegalMonitorStateException.
                        - only one thread can acquire a lock, another thread has to wait till it is released by the same thread.
                     When you want same thread to acquire multiple locks in different methods and don't want another thread to access the methods code until all the locks are released by the same thread.
                     When you want to use tryLock() feature (e.g. DiningPhilosophers.java).
                     When you want to use Fairness feature.
                     When you want to use await/signal of ReentrantLock as an alternative Synchronized Block's wait/notify.
                        Synchronized Block's wait/notify can be used on locked object
                            void method1() {
                                synchronized(obj) {
                                    obj.wait();// lock on obj will be released and some other waiting thread will acquire it. That thread has to notify after finishing its process, so that this thread acquires a lock again.
                                }
                            }
                            void method2() {
                                synchronized(obj) {
                                    obj.notify();
                                    ...... // after notify(), lock will be released only after synchronized block is totally executed.
                                }
                            }

                        Whereas ReentrantLock's wait/signal can be used on condition object taken from ReentrantLock
                            Condition condition = reentrantLock.newCondition()
                            condition.await(), condition.signal()

    Semaphore - When you want to overcome the limitation of ReentrantLock.
                    - you want another thread to be able to release the lock acquired by first thread.
                    - you want specific number of threads(more than one threads) to acquire locks on the same block of code.
                e.g. CallInOrder.java

Dead Lock
---------
    See DeadLockExample.java

*/

import algorithms.crackingcodinginterviewbook._8thread_and_locks._0AbstractThreadAndLockExample;

public class ThreadAndLocksFundamentals extends _0AbstractThreadAndLockExample {

    public static void main(String[] args) {


    }

}
