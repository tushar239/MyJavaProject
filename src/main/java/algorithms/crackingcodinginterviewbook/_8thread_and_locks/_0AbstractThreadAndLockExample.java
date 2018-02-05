package algorithms.crackingcodinginterviewbook._8thread_and_locks;

public class _0AbstractThreadAndLockExample {

    public static class SynchronizedMethods {

        // Synchronized Instance-Level (non-static) methods
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

        // Synchronized Class-Level (static) methods
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

    public static class SynchronizedBlockClass {
        public SynchronizedMethods cls;

        public SynchronizedBlockClass(SynchronizedMethods cls) {
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


}
