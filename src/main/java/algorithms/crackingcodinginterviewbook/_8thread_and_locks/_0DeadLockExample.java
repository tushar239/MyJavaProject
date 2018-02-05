package algorithms.crackingcodinginterviewbook._8thread_and_locks;

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
public class _0DeadLockExample extends _0AbstractThreadAndLockExample {
    public SynchronizedMethods cls1 = new SynchronizedMethods();
    public SynchronizedBlockClass cls2 = new SynchronizedBlockClass(cls1);

    public void deadLockFirstThread() throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " waiting to acquire a lock on cls1 object");

        synchronized (cls1) {
            System.out.println(Thread.currentThread().getName() + " acquired a lock on cls1 object");
            Thread.sleep(10_000);
            System.out.println(Thread.currentThread().getName() + " waiting to acquire a lock on cls2 object");
            synchronized (cls2) {
                System.out.println(Thread.currentThread().getName() + " acquired a lock on cls2 object");
                Thread.sleep(10_000);
            }
        }
    }

    public void deadLockSecondThread() throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " waiting to acquire a lock on cls2 object");

        synchronized (cls2) {
            System.out.println(Thread.currentThread().getName() + " acquired a lock on cls2 object");
            Thread.sleep(10_000);
            System.out.println(Thread.currentThread().getName() + " waiting to acquire a lock on cls1 object");
            synchronized (cls1) {
                System.out.println(Thread.currentThread().getName() + " acquired a lock on cls1 object");
                Thread.sleep(10_000);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("\033[1m" + ".....Testing DeadLock....." + "\033[0m");
        {
            _0DeadLockExample deadLockExample = new _0DeadLockExample();

            Thread firstThread = new Thread("firstThread") {
                @Override
                public void run() {
                    try {
                        deadLockExample.deadLockFirstThread();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            Thread secondThread = new Thread("secondThread") {
                @Override
                public void run() {
                    try {
                        deadLockExample.deadLockSecondThread();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };

            firstThread.start();
            secondThread.start();

            firstThread.join();
            secondThread.join();
        }
    }
}
