package algorithms.crackingcodinginterviewbook._8thread_and_locks;

/*
  Method level synchronization

    - instance-level synchronization

        When you synchronize non-static(instance-level) method(s), that method can't be accessed my more than one thread at the same time, if both threads are trying to access that method for the same instance of the class at the same time.

        If threads use different instances of the class to access synchronized non-static method together at the same time, then they can access it together.
        If one thread is accessing one synchronized non-static method, another thread can access another synchronized non-static method for the same class instance. This is different than class-level (static method) synchronization.

    - class-level synchronization

        static-level(class level) synchronized method(s) can't be accessed by multiple threads.

        REMEMBER:
        If one thread is accessing one synchronized static method, another thread can't access another synchronized static method also. Because it acts like class-level locking.
        This is different than instance-level synchronization.

*/
public class _0InstanceAndClassMethodLevelSynchronization extends _0AbstractThreadAndLockExample {
    public static class MyRunnable1 implements Runnable {
        private SynchronizedMethods cls;

        public MyRunnable1(SynchronizedMethods cls) {
            this.cls = cls;
        }

        @Override
        public void run() {
            cls.method1();  //---- accessing method1
        }
    }

    public static class MyRunnable2 implements Runnable {
        private SynchronizedMethods cls;

        public MyRunnable2(SynchronizedMethods cls) {
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
            SynchronizedMethods.staticMethod1(); //---- accessing staticMethod1
        }
    }

    public static class MyRunnable4 implements Runnable {

        public MyRunnable4() {
        }

        @Override
        public void run() {
            SynchronizedMethods.staticMethod2(); //---- accessing staticMethod2
        }
    }

    public static void main(String[] args) throws InterruptedException {


        System.out.println("\033[1m" + ".....Testing Instance Method-Level synchronization....." + "\033[0m");

        System.out.println("\033[1m" + "Testing threads accessing a synchronized method of class with the same instance of that class." + "\033[0m");
        {
            // Both t1 and t2 CAN NOT access MyClass' method1() at the same time because they are using the same instance of MyClass
            // t2 has to wait till t1 completely comes out of method1() of cls instance
            SynchronizedMethods cls1 = new SynchronizedMethods();
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
            SynchronizedMethods cls1 = new SynchronizedMethods();
            SynchronizedMethods cls2 = new SynchronizedMethods();
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
            SynchronizedMethods cls1 = new SynchronizedMethods();
            Thread t1 = new Thread(new MyRunnable1(cls1), "t1");
            Thread t2 = new Thread(new MyRunnable2(cls1), "t2");
            t1.start();
            t2.start();

            t1.join();
            t2.join();
        }

        System.out.println("\033[1m" + ".....Testing Class Method (Static Method )-Level synchronization....." + "\033[0m");
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

    }
}
