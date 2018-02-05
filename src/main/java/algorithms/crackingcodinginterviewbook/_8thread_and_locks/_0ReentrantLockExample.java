package algorithms.crackingcodinginterviewbook._8thread_and_locks;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/*

    There are following types of locks:
        - ReentrantLock
        - ReentrantReadWriteLock

    ReentrantLock (better alternative of block-level synchronization)
    -------------
    Using ReentrantLock's lock() method on an object, thread can acquire a lock on that object.

    It is similar to block-level synchronization with certain major advantages.

        - lock on the object can be obtained inside one method and released inside another method.

        - multiple locks (holds on lock) can be acquired by same thread.
          But all the holds(locks) have to be released before any other thread can acquire a lock.

        - lock.tryLock(...) method

             If lock is not available to acquire, then

                  lock.tryLock() method
                        will return false immediately and will execute the code in method without locking applied. Basically, it will ignore locking applied by another thread.
                  lock.tryLock(time) method
                        will wait for lock to be released by another thread for some time.
                        If lock is released by another thread in that time duration, it will acquire a lock and return true and then execute the code in the method.
                        Otherwise, it will return false and execute the code in method without locking applied. Basically, it will ignore locking applied by another thread.

             Unlike to lock() method, it will not wait forever to acquire a lock till lock is released by another thread.

        - Fairness policy (VERY IMPORTANT advantage over wait/notify)

          new ReentrantLock(true) - fairness policy is set to true
          If multiple threads are waiting to acquire a lock, the longest waiting thread can be given a lock after a lock is released by current thread.

        - Condition (wait-notify/notifyAll alternative)

          Just like wait(), you can use condition.await() to let current thread wait and let other thread acquire a lock.
          That other thread has to use condition.signal()/signalAll() (same as notify()/notifyAll()) that will notify waiting thread(s) to acquire a lock again AFTER that other thread unlocks the lock.

          await(time) - It will wait for some time for another thread to acquire a lock. If not, then this thread will reacquire a lock and continue the execution.

        - lock.holdCount() method

          This method gives a count of total number of locks acquired by the CURRENT THREAD.
          A thread can hold multiple locks on the same lock object,
          (IMPORTANT) but it has to release all locks before any other thread can acquire it.


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

// https://www.youtube.com/watch?v=fjMTaVykOpc
public class _0ReentrantLockExample {
    private final ReentrantLock lock;
    private final Condition condition;

    public _0ReentrantLockExample(ReentrantLock lock) {
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

        condition.signal(); // same as notify() method. signalAll() is same as notifyAll() method.
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

        System.out.println("Could fourthThread acquire a lock? " + acquiredLock + "..., Total locks held by this thread: " + lock.getHoldCount());

        try {
            increment("fourthThread");
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
                System.out.println("fourthThread released a lock in fourthThread()...Total locks held by this thread: " + lock.getHoldCount());
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

        System.out.println("\033[1m" + ".....Testing Reentrant Lock....." + "\033[0m");
        {

            // ReentrantLock with Fairness Policy set to true
            // If Fairness policy is set to true, then it will make sure that longest waiting thread acquires a lock first.
            ReentrantLock lock = new ReentrantLock(true);
            _0ReentrantLockExample reentrantLockExample = new _0ReentrantLockExample(lock);
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
}
