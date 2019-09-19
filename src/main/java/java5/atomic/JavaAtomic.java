package java5.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/*
    http://www.journaldev.com/1095/java-atomic-operations-atomicinteger-example

    AtomicInteger has
        private volatile int value;

    When same variable is used between two threads, any change in the value will not be saved in the thread's local cache.
    It will directly be modified in shared memory because value is declared as volatile.
*/
public class JavaAtomic {
    public void method(int a, int b) {

    }
    public static void main(String[] args) throws InterruptedException {
        // no issue with this approach because T3 and T4 are using two different instances of Runnable (ProcessingThread).
        // So, they will not share instance variables of Runnable
        {
            ProcessingThread forT3 = new ProcessingThread();
            Thread t3 = new Thread(forT3, "t3");
            ProcessingThread forT4 = new ProcessingThread();
            Thread t4 = new Thread(forT4, "t4");
            t3.start();
            t4.start();
            //System.out.println(Thread.currentThread().getName());
            t3.join(); // makes current thread to wait till t3 completes. in this method context, current thread is main thread.
            t4.join();
            System.out.println(Thread.currentThread().getName());
            System.out.println("Processing count for T3=" + forT3.getCount()); // 4
            System.out.println("Processing count for T4=" + forT4.getCount()); // 4

        }
        // Issue will happen with this approach because T1 and T2 are using the same instance of Runnable (ProcessingThread).
        // So, they will share the same instance of instance variables. As instance variables(e.g. count here) are not synchronized, one thread may not see the latest value updated by another thread.
        // To make the instance variables synchronized, use AtomicInteger. AtomicInteger/AtomicLong/..../AtomicReference are synchronized. Update from one thread is seen by other thread.
        // Basically, Atomic* makes the value Volatile. So, values are not cached in Thread's own heap memory. Whenever thread needs a variable, it is retrieved from main heap memory.
        // Here ProcessingThread is stored in main heap memory. When Thread starts, it caches the object of ProcessingThread to its own heap memory.
        // If any other thread tries to read the same object, jvm let that thread read first from its own cache and then pushes the changes done by first thread to main memory and main memory to second thread's heap memory. This takes a while to reflect latest value in second thread.
        // If a variable is defined volatile (same as Atomic*), threads won't cache the object in its own heap memory. It will always read from main memory and write to main memory.
        {
            ProcessingThread pt = new ProcessingThread();// Same Runnable instance is shared by two threads.
            Thread t1 = new Thread(pt, "t1");
            Thread t2 = new Thread(pt, "t2");
            t1.start();
            t2.start();
            t1.join();
            t2.join();
            System.out.println("Processing count=" + pt.getCount()); // may not be 8
            System.out.println("Processing count=" + pt.getAtomicCount()); // 8
        }
    }
}

class  ProcessingThread implements Runnable {
    private Integer count = new Integer(0); // count is not synchronized
    private AtomicInteger atomicCount = new AtomicInteger(0);// atomicCount is synchronized

    @Override
    public void run() {

        for (int i = 1; i < 5; i++) {
            //System.out.println(Thread.currentThread().getName());
            processSomething(i);
            count = getCount()+1;
            atomicCount.incrementAndGet();
        }
    }


    public int getCount() {
        //return this.count.get();
        return count;
    }
    public Integer getAtomicCount() {
        return atomicCount.get();
    }


    private void processSomething(int i) {
        try {
            Thread.sleep(i * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
