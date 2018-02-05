package algorithms.crackingcodinginterviewbook._8thread_and_locks;

/*
Block level synchronization (object level locking)

    Instead of synchronizing entire method, you can synchronize(lock) a block of the code inside a method.
    This is achieved by locking an access to an object.
*/
@SuppressWarnings("Duplicates")
public class _0BlockLevelSynchronization extends _0AbstractThreadAndLockExample {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("\033[1m" + ".....Testing Block-Level synchronization....." + "\033[0m");

        System.out.println("\033[1m" + "Lock is obtained by a thread on MyClass1' object(cls1). If another thread tries obtain the lock on different instance, then it doesn't have to wait till first thread completes." + "\033[0m");
        {
            SynchronizedBlockClass cls21 = new SynchronizedBlockClass(new SynchronizedMethods());
            SynchronizedBlockClass cls22 = new SynchronizedBlockClass(new SynchronizedMethods());

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
        }

        System.out.println("\033[1m" + "Lock is obtained by a thread on MyClass1' object(cls1). If another thread tries obtain the lock on same instance, then it has to wait till first thread completes." + "\033[0m");

        {
            SynchronizedMethods cls1 = new SynchronizedMethods();

            SynchronizedBlockClass cls2 = new SynchronizedBlockClass(cls1);

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

    }
}
