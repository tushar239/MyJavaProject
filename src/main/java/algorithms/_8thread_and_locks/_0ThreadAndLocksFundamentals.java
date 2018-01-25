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

            public class MyClass {
                public synchronized void method1() {...}
                public synchronized void method2() {...}

                public static void staticMethod1() {...}
                public static void staticMethod2() {...}
            }

            public class MyRunnable1 implements Runnable {
                private MyClass cls;

                public MyRunnable2(MyClass cls) {
                    this.cls = cls;
                }

                @Override
                public void run() {
                    cls.method1();  ---- accessing method1
                    try {
                        Thread.sleep(5000);
                    } catch(InterruptedException e) {...}
                }
            }

            public class MyRunnable2 implements Runnable {
                private MyClass cls;

                public MyRunnable2(MyClass cls) {
                    this.cls = cls;
                }

                @Override
                public void run() {
                    cls.method2(); ---- accessing method2
                    try {
                        Thread.sleep(5000);
                    } catch(InterruptedException e) {...}
                }
            }

            public class MyRunnable3 implements Runnable {

                public MyRunnable3() {
                }

                @Override
                public void run() {
                    MyClass.staticMethod1(); ---- accessing staticMethod1
                    try {
                        Thread.sleep(5000);
                    } catch(InterruptedException e) {...}
                }
            }

            public class MyRunnable4 implements Runnable {

                public MyRunnable4(MyClass cls) {
                }

                @Override
                public void run() {
                    MyClass.staticMethod2(); ---- accessing staticMethod2
                    try {
                        Thread.sleep(5000);
                    } catch(InterruptedException e) {...}
                }
            }


            // Both t1 and t2 CAN NOT access MyClass' method1() at the same time because they are using the same instance of MyClass
            // t2 has to wait till t1 completely comes out of method1() of cls instance
            MyClass cls1 = new MyClass();
            Thread t1=new Thread(new MyRunnable1(cls1));
            Thread t2=new Thread(new MyRunnable1(cls1));
            t1.start();
            t2.start();

            // Both t1 and t2 can access MyClass' method1() at the same time because they are using different instances of MyClass
            MyClass cls1 = new MyClass();
            MyClass cls2 = new MyClass();
            Thread t1=new Thread(new MyRunnable1(cls1));
            Thread t2=new Thread(new MyRunnable1(cls2));
            t1.start();
            t2.start();

            // t1 can access MyClass' method1() and t2 can access MyClass' method2() at the same time.
            MyClass cls1 = new MyClass();
            Thread t1=new Thread(new MyRunnable1(cls1));
            Thread t2=new Thread(new MyRunnable2(cls1));
            t1.start();
            t2.start();


            // Both t1,t2,t3 CAN NOT access MyClass' staticMethod1()/staticMethod2() at the same time because staticMethod1() is a static method (class-level method).
            // Two threads cannot access the same or different synchronized static methods at the same time.
            // t2 has to wait till t1 completely comes out of method1() of cls instance
            // t2 and t3 have to wait till t1 is completely out of staticMethod1()
            Thread t1=new Thread(new MyRunnable3()); --- accessing staticMethod1()
            Thread t2=new Thread(new MyRunnable3()); --- accessing staticMethod1()
            Thread t3=new Thread(new MyRunnable4()); --- accessing staticMethod2()
            t1.start();
            t2.start();
            t3.start();



        - class-level synchronization

            static-level(class level) synchronized method(s) can't be accessed by multiple threads.

            REMEMBER:
            If one thread is accessing one synchronized static method, another thread can't access another synchronized static method also. Because it acts like class-level locking.
            This is different than instance-level synchronization.

    - Block level synchronization (object level locking)

        Instead of synchronizing entire method, you can synchronize(lock) a block of the code inside a method.
        This is achieved by locking an access to an object.


Locking

*/

public class _0ThreadAndLocksFundamentals {

    public class MyThread extends Thread {
        @Override
        public void run() {
        }

    }

}
