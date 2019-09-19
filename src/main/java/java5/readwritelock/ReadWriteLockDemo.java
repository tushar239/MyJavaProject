package java5.readwritelock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author Tushar Chokshi @ 8/11/16.
 */

// http://www.sourcetricks.com/2014/09/java-reentrantreadwritelock.html#.V6zgHpMrJHQ
/*

ReentrantLock - read about it from readme_java5.txt

In below code, lock is a ReentrantReadWriteLock object.

If write-lock is applied on lock object by one thread, no other thread can enter in the code surrounded by the same lock object anywhere in the code (same thread/different thread).

Once write-lock is released, any other thread can request for another lock.
Let's say another thread requested for read-lock on the lock object.
Once the read-lock is acquired by one thread, all other threads requesting for read-locks can get their own read-locks and access the code, but a thread requesting for write-lock has to wait get the lock.


A read-write lock provides greater level of concurrency than a mutual exclusion lock when working with shared data.
Read-write locks allows simultaneous read only operations by multiple threads whereas a write operation can be performed by only one thread.
(IMP) Read-write locks provide increased level of concurrency and typically results in better performance if the frequency of read operations is more than write operations.

*/
public class ReadWriteLockDemo {

    // Shared resources to be guarded
    private static int count = 0;


    public static void main(String[] args) {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();// shared resource between all threads. Threads will set the lock on this object.

        Thread producer = new Thread(new ProducerTask(lock));
        Thread reader = new Thread(new ReadTask(lock));
        Thread anotherReader = new Thread(new AnotherReadTask(lock));

        producer.start();
        reader.start();
        anotherReader.start();
    }

    private static class ProducerTask implements Runnable {

        // Producer thread. Counts up the resource.
        // Reader task
        // Reader task
        private ReentrantReadWriteLock lock = null;

        ProducerTask(ReentrantReadWriteLock lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            System.out.println("Producer requesting lock");
            lock.writeLock().lock(); // requesting write lock
            System.out.println("Producer gets lock");
            for (int i = 0; i < 5; i++) {
                count = count + 1;
                System.out.println("Counting up");
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Producer releases lock");
            lock.writeLock().unlock();
        }

    }

    private static class AnotherReadTask implements Runnable {

        private ReentrantReadWriteLock lock = null;

        AnotherReadTask(ReentrantReadWriteLock lock) {
            this.lock = lock;
        }
        @Override
        public void run() {
            System.out.println("Another Reader requesting lock");
            lock.readLock().lock(); // request read lock
            System.out.println("Another Reader gets lock");
            System.out.println("Current count = " + count);
            try {
                Thread.sleep(7000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Another Reader releases lock");
            lock.readLock().unlock();
        }

    }

    private static class ReadTask implements Runnable {

        private ReentrantReadWriteLock lock = null;

        ReadTask(ReentrantReadWriteLock lock) {
            this.lock = lock;
        }
        @Override
        public void run() {
            System.out.println("Reader requesting lock");
            lock.readLock().lock();
            System.out.println("Reader gets lock");
            System.out.println("Current count = " + count);
            try {
                Thread.sleep(7000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Reader releases lock");
            lock.readLock().unlock();
        }

    }
}
