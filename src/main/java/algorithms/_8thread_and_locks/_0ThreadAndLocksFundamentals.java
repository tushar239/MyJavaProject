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

Synchronization

    Two Types of synchronization

        - Method level synchronization
            - instance method level synchronization
            - class method (static method) level synchronization

            See InstanceAndClassMethodLevelSynchronization.java

        - Block level synchronization (object level locking)

            See BlockLevelSynchronization.java

Locks
    See ReentrantLockExample.java

Dead Lock
---------
    See DeadLockExample.java

*/

public class _0ThreadAndLocksFundamentals extends _0AbstractThreadAndLockExample {

    public static void main(String[] args) {


    }

}
