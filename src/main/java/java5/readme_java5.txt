What is JDK/JRE/JVM?
https://www.youtube.com/watch?v=eaAqwTdUAAo
JDK has JRE+Development Tools
JRE has JVM+Library Classes
JVM is a interpreter

Developer needs JDK. Client needs only JRE because they just need to run the application.
javac is a compiler that converts java code to Bytecode and creates .class file.
jvm is an interpreter that reads bytecode (.class file) line by line anc converts to Native Machine Code.

Compiler vs Interpreter
http://www.c4learn.com/c-programming/compiler-vs-interpreter/
https://www.youtube.com/watch?v=XjNwyXx2os8
Compiler Takes Entire program as input. Errors are displayed after entire program is checked.
Interpreter Takes Single instruction as input. Errors are displayed for every instruction interpreted (if any).
c,c++ have compilers. Perl has interpreter.
compiled code is faster to run because it doesn't have to converted to machine language everytime you run it.
interpreter is slower because every time you run the program, it converts it into machine language.
Java uses both compiler(javac) and interpreter(jvm). javac creates .class file with bytecode and jvm interpreter reads this bytecode line by line and coverts it to machine language.

What is Stack and Heap?
Stack is used for method calls.
Heap is used to store objects.
Both use a part of RAM. In JVM, you can specify how much max heap size should be used.

Open-close policy
Classes,Modules,Functions should be open for extension and closed for modification.
every class, module, function should have only single responsibility. It means if there is a need to change the code in a class/method, it should be only for one particular functionality.
A class/method should have only one reason to change.


Inheritance vs Composition
Prefer Composition over Inheritance

1) Inheritance actually makes all non-private behaviours automatically available to all child classes. Which is breaking encapsulation weak.
Inheritance is not bad, but whenever you see some special conditions as shown in below points, you should switch over to composition.


2) http://stackoverflow.com/questions/2399544/difference-between-inheritance-and-composition

public class X{
   public void do(){
   }
}
Public Class Y extends X{
   public void work(){
       do();
   }
}

If need arises to add "public int work()" in X, then it will break Y's compatibility.
Or let's say you have "public void work() {...}" in X and Y has overridden it. Suddenly, u change X's work() to "public int work()", then subclass is not going to complain anything but suddenly it will break subclass's functionality because now X x = new Y();x.work() will call X's work() instead of Y's work() and this will happen unknowingly.
If you have such scenario prefer composition.

public class X{
    public void do(){
    }
}

Public Class Y{
    X x=new X();
    public void work(){
        x.do();
    }
}

Now, whatever you do in X will not affect Y.

3) https://www.youtube.com/watch?v=dYUZiJEy0JE
Whenever you see a possibility of a special case e.g.
    Person
      |
    Employee
 |          |       |
Engineer  Genetor   Manager

Till here it is fine, you think like it is a perfect 'is-a' relationship like persion is a employee. employee is a engineer/genetor/manager.
Now in future, there comes a requirement of Employee is a Engineer+Manager. So, to support this hierarchy, you need to created a child class EngManager and copy both Engineer's and Manager's behaviour to it.
Whenever there is a possibility of this kind of special case, you convert is-a(inheritance) to has-a(composition)

Person     has-a              Role
                    |           |           |
                    Engineer    Genetor     Manager

So, now Person can have a Role[].

Whenever you see a chance of growing number of child classes, just go for composition as shown above break deep inheritance.
If you see a deep inheritance, there is a possibility of facing above situation at any point in time. So, refactor it for composition.

4) https://www.youtube.com/watch?v=dYUZiJEy0JE
Whenever you see a parent class having behaviours(abstract behaviour or concrete behaviour) which are actually applicable to separate child classes and not really common for all child classes,
then it's not a real inheritance. Those kind of behaviours should be implemented in a separate class(may be processor) that takes Parent class as an argument.

--------------------------------------------------------------------------------------------------------------------------------------------


Java 5 new Features: https://docs.oracle.com/javase/1.5.0/docs/relnotes/features.html
- Generics
- Type Erasure: https://docs.oracle.com/javase/tutorial/java/generics/erasure.html
- Enum
    http://stackoverflow.com/questions/20299074/can-i-make-an-abstract-enum-in-java
    all enums are implicitly final. So, they cannot be inherited

    enum SomeEnum extends ....  //illegal because all enums implicitly extends java.lang.Enum
    enum SomeEnum extends SomeEnum2 // illegal because all enums implicitly extends java.lang.Enum and all enums are implicitly final.
    enum SomeEnum implements SomeInterface  //legal

    enum Planet {
        MERCURY("mercury","red"), SATURN("saturn","blue"), JUPITER("jupiter","yellow");

        String name, color;
        Planet(String name, String color) {
            this.name=name;
            this.color=color
        }
    }

    is same as

    class Planet {
        String name, color;
         Planet(String name, String color) {
            this.name=name;
            this.color=color
         }
    }
    Planet MERCURY = new Planet("mercury","red");
    Planet SATURN = new Planet("saturn","blue");
    Planet JUPITER = new Planet("jupiter","yellow");

- Enhanced for Loop
- Autoboxing/Unboxing - http://javarevisited.blogspot.com/2012/07/auto-boxing-and-unboxing-in-java-be.html
    ArrayList<Integer> intList = new ArrayList<Integer>();
    intList.add(1); //autoboxing - primitive to object
    intList.add(2); //autoboxing

    int number = intList.get(0); // unboxing
- Varargs
- Static Import
- Metadata (Annotations)
- Closeable interface (java.io.closeable)
    https://docs.oracle.com/javase/1.5.0/docs/api/java/io/Closeable.html
    A Closeable is a source or destination of data that can be closed. The close method is invoked to release resources that the object is holding (such as open files).
    FileInputStream fis = null;
    try
    {
        fis = new FileInputStream(file);
    }
    catch (IOException e)
    {
        ... handle error ...
    }
    finally
    {
        if (fis != null)
            fis.close();
    }

    For Java 7 and above try-with-resources should be used:

    try (InputStream in = new FileInputStream(file)) {
      ... code ...
    } catch (IOException e) {
      ... handle error ...
    }

- Autocloseable interface (added in java 1.7) - A resource that must be closed when it is no longer needed.
    The close() method of an Object that implements the AutoCloseable interface is called automatically, when exiting a try-with-resources block is used for a resource.
    http://examples.javacodegeeks.com/core-java/java-autocloseable-interface-example/

- Flushable interface - provides a flush(). The flush method is invoked to write any buffered output to the underlying stream.

- ProcessBuilder - The new ProcessBuilder class provides a more convenient way to invoke subprocesses than does Runtime.exec. In particular, ProcessBuilder makes it easy to start a subprocess with a modified process environment (that is, one based on the parent's process environment, but with a few changes).
- Formatter (java.util.Formatter)
    C's printf style formatting
    https://docs.oracle.com/javase/1.5.0/docs/api/java/util/Formatter.html
- Scanner (java.util.Scanner) - it is not from java.io package, but it can be used to read a file content line by line using regex delimiter.
    It is just like Regex, but little different.
    https://docs.oracle.com/javase/1.5.0/docs/api/java/util/Scanner.html

    In Regex, you can provide a specific substring to find (matcher.find()) from a string and it can find that substring from all possible places in that string, if you do while(mathcer.find()).

    In Scanner, instead of providing a specific substring to find, you can provide a delimiter regex. It will find all substrings for a string delimited by delimiter regex.
    On the top of that, you have a api method to convert retrieved substring to int/long etc.

    String input = "1 fish 2 fish red fish blue fish";
    Scanner s = new Scanner(input).useDelimiter("\\s*fish\\s*");
    System.out.println(s.nextInt());
    System.out.println(s.nextInt());
    System.out.println(s.next());
    System.out.println(s.next());
    s.close();

    You can also read a file using Scanner.
    http://javarevisited.blogspot.com/2012/07/read-file-line-by-line-java-example-scanner.html
    One way to read a file line by line
        fis = new FileInputStream("C:/sample.txt");
        reader = new BufferedReader(new InputStreamReader(fis));
        String line = reader.readLine();
        while(line != null){
            System.out.println(line);
            line = reader.readLine();
        }

    Another way (using Scanner)

        FileInputStream fis = new FileInputStream("C:/sample.txt");
        Scanner scanner = new Scanner(fis);// you can use delimiter also.
        while(scanner.hasNextLine()){
            System.out.println(scanner.nextLine());
        }



- util.concurrent.* packages - java.uti.concurrent, java.util.concurrent.atomic, java.util.concurrent.lock packages






------------------------------------------------------------------------------------------------------------------

Generics-
    https://docs.oracle.com/javase/tutorial/java/generics/index.html

    Type Parameter and Type Argument Terminology:
    the T in Foo<T> is a type parameter and the String in Foo<String> f is a type argument

    class Foo<T> { // T is a type parameter
        public <T> void get(T t) {// T is a Type parameter
            ...
        }
        public <K,V> void get(Boo<K,V> boo) {// K and V are Type parameters
                ...
        }
    }

    Foo<String> foo = new Foo<>(); // <String> is a type argument
    foo.get(str);
    foo.get(new Boo<String, Integer>()); <String, Integer> are type arguments


    Bounded Type Parameters:

    T extends Something   ---- here Something is Upper Bound
    T super Something     ---- here Something is Lower Bound

    class A {}
    interface B {}
    interface C {}
    class D {}

    class Foo<T extends A> { // This is how you provided bounded type parameter at class level
     public <T extends A> void get(T t) {// This is how you provided bounded type parameter at method level
            ...
     }
    }

    class Foo<T extends A & B & C> { // you can also provided multiple

    }

    class Foo<T extends A & D & B & C> { // not allowed - you cannot have more than one classes A & D

    }

    class Foo<T extends B & A & C> { // not allowed - class has to come before all interfaces A has to come before B and C.
    }


    class Foo<T super Integer> { // it means Integer or its super types (Number, Object) are allowed to pass as type arguments.

    }


    Wild Cards:

    Wild Card as Type Parameter and Type Argument :

        wild cards and super can't be used with type parameters at Class or Method level, but they can be used as type arguments.

        // class Foo<? extends A> { // wildcard as a parameter is not allowed. you need to use <T extends A>. Reason is in code of this class, you need to refer variable type as A instead of its subclass type and it limits the access of subclass members.
        // class Foo<T super A> { // super is not allowed as a parameter
        class Foo<T extends A> { // allowed
            public <T extends A> void get(T t) { // get(? extends T t) not allowed

            }

            public void get(Boo<? extends B> boo) {// allowed because ? is used as class Boo's type argument

            }
            OR
            // public void get(Boo<J extends B> boo)    // not allowed
            public <J extends B> void get(Boo<J> boo) { // allowed

            }
            // public void get(Boo<T> boo)  // allowed
            // public <T> void get(Boo<T> boo)  // Don't do this - T is a generic parameter of class also. So, no need to put <T> for method. If you put it then code inside this method using 'boo' will get confused whether it should consider T of class or T of method and it will give compile time error.

            // public <E> void get(E e)
            // is same as
            // public <E extends Object> void get(E e)

        }
        Foo<? extends A> foo = new Foo<>(); // allowed because wild card is used as a type argument, BUT you should not use it (see below List<? extends Number> example). You should either use Foo<? super B>(assuming B extends A) or Foo<A>. Later is better.
        Foo<? super A> foo = new Foo<>(); // allowed because super is used as a type argument


    Difference between List<Number> and List<? extends Number> and List<? super Integer>:

        - List<Number> vs List<? extends Number> vs List<? super Integer>

         List<Number>           - You can add and read elements.

                                  But you can't assign like List<Number> = List<Integer>


         List<? extends Number> - You cannot add elements. You can only read.

                                  But you can assign List<? extends Number> = List<Number> or List<Integer> or List<? extends Integer>

                                  Legal assignments

                                  List<? extends Number> foo1 = new ArrayList<Number>();
                                  List<? extends Number> foo2 = new ArrayList<Integer>();
                                  List<? extends Number> foo3 = new ArrayList<Double>();
                                  List<? extends Number> foo4 = foo1; // allowed

                                  foo2.get(0); // legal  --- reading is legal
                                  foo2.add(1); // illegal  --- writing is illegal


         List<? super Integer> -  A list of any class that is super class of Integer can be be assigned to List<? super Integer>.

                                    Legal assignments

                                    List<? super Integer> foo1 = new ArrayList<Integer>();
                                    List<? super Integer> foo2 = new ArrayList<Number>();
                                    List<? super Integer> foo3 = new ArrayList<Object>();

                                    You can both read and write the elements, but while reading the only return type can be Object because foo1,foo2,foo3 can contain any Integer/Number/Object.

                                    Object obj1 = foo1.get(0); // reading is legal, but only return type can be Object
                                    Object obj2 = foo2.get(0);
                                    Object obj3 = foo3.get(0);



        Examples:
            1)
                List<? extends Number>  numList = new ArrayList<>(); // you can read the elements from this list but cannot write it because you can write Integer or Double anything to this list, which is not appropriate.
                numList.add(1); // compile-time error


                List<? extends Integer> intList = new ArrayList<>();

                List<? extends Number>  numList = intList;  // OK.
                Number num = numList.get(0); // OK   reading is ok
                numList.add(1); // compile-error  because you can add both Integer and Double, it eliminates the purpose of generics, so it should not be allowed.

                List<? super Integer>  numList = new ArrayList<>(); // you can both read and write to this list because you can write Number or Integer, which is fine
                numList.add(1); // OK
                numList.add((Number) new Integer(1)); // compile-error
                numList.add((Object) new SomeClass()); // compile-error
                Object o = numList.get(0); // Number o=... or Integer o=.... cannot be allowed
                It means that List<? super Integer> acts same as List<Integer> because you can write only Integer and not its super class Number or Object.


                List<B> lb = new ArrayList<>();
                List<A> la = lb;   // compile-time error


            2)

                List<Integer> li = Arrays.asList(1, 2, 3);

                wildCardMethod(li);
                withoutWildCardMethod(li);

                public static void wildCardMethod(List<? extends Number> numbers) {
                    System.out.println(numbers);
                }

                public static void withoutWildCardMethod(List<Number> numbers) {
                    System.out.println(numbers);
                }


Concurrency Utilities -
    http://tutorials.jenkov.com/java-util-concurrent/index.html
    java.util.concurrent,

        CopyOnWriteArrayList, CopyOnWriteArraySet and CocurrentHashMap
            CopyOnWriteArrayList
                https://techvivek.wordpress.com/2009/08/29/difference-between-arraylist-and-copyonwritearraylist/
                ArrayList is not thread safe so any simultaneous thread can access and modify the content of list simultaneously.Here lies the dangerous, ConcurrentModificationException. When one thread is Iterating over an ArrayList and any other thread(which may be the same thread)  modify the content of list and when we again call next() on the iterator ,we get this exception. Which means that no thread can modify the ArrayList while an Iterator is iterating over this.
                CopyOnWriteArrayList collection cause the underlying array to be replaced with a copy of itself before the contents of the array are changed. Any active iterators will continue to see the unmodified array, so there is no need for locks

                It is best suited for applications in which set sizes generally stay small, read-only operations vastly outnumber mutative operations, and you need to prevent interference among threads during traversal.

                See CopyOnWriteTest.java

            ConcurrentMap
                http://tutorials.jenkov.com/java-util-concurrent/concurrentmap.html
                ConcurrentHashMap does not lock the Map while you are reading from it.
                Additionally, ConcurrentHashMap does not lock the entire Map when writing to it. It only locks the part of the Map that is being written to, internally.

                Another difference is that ConcurrentHashMap does not throw ConcurrentModificationException if the ConcurrentHashMap is changed while being iterated.

        ConcurrentNavigableMap
            http://tutorials.jenkov.com/java-util-concurrent/concurrentnavigablemap.html
            ???

        CountDownLatch
            http://tutorials.jenkov.com/java-util-concurrent/countdownlatch.html

            You are starting many threads together, but you can actually make them wait at any execution point using latch.await()
            Once the latch's value becomes 0, then only those threads will start moving ahead. see the example
            http://javarevisited.blogspot.com/2012/07/countdownlatch-example-in-java.html

        CyclicBarrier
            http://tutorials.jenkov.com/java-util-concurrent/cyclicbarrier.html
            ???

        Exchanger
            http://tutorials.jenkov.com/java-util-concurrent/exchanger.html

            Two threads can exchange the objects using Exchanger.
            https://examples.javacodegeeks.com/core-java/util/concurrent/exchanger/java-util-concurrent-exchanger-example/

            The Exchanger Class provides a sort of rendezvous point for two threads, where the threads can exchange their respective Objects with the other thread.
            Whenever a thread arrives at the exchange point, it must wait for the other thread to arrive. When the other pairing thread arrives the two threads proceed to exchange their objects.

        Semaphore
            http://tutorials.jenkov.com/java-util-concurrent/semaphore.html

            Semaphore is also like a Lock(ReentrantLock) with a small improvement.
            Lock allows only one thread to access a block of code, whereas Semaphore allows more than 1 threads to acquire locks on the same block of code.
            Here, 3 threads can enter the block of the code and others have to wait until one of them releases lock.
            You can also set fairness=true/false just like Lock(ReentrantLock).

            Semaphore semaphore = new Semaphore(3, <fairness>);

            //critical section
            semaphore.acquire();

            ...

            semaphore.release();

            Example:
            http://www.informit.com/articles/article.aspx?p=1339471&seqNum=2
            SemaphoreExample.java

        CyclicBarrier
            http://tutorials.jenkov.com/java-util-concurrent/cyclicbarrier.html
            ???

        Fork and Join
            http://tutorials.jenkov.com/java-util-concurrent/java-fork-and-join-forkjoinpool.html
            ???

        Queue interface
                https://docs.oracle.com/javase/7/docs/api/java/util/Queue.html
                Sub-Interfaces - BlockingDeque<E>, BlockingQueue<E>, Deque<E>, TransferQueue<E>

        BlockingQueue -
            The producing thread will keep producing new objects and insert them into the queue, until the queue reaches some upper bound on what it can contain. It's limit, in other words. If the blocking queue reaches its upper limit, the producing thread is blocked while trying to insert the new object. It remains blocked until a consuming thread takes an object out of the queue.
            The consuming thread keeps taking objects out of the blocking queue, and processes them. If the consuming thread tries to take an object out of an empty queue, the consuming thread is blocked until a producing thread puts an object into the queue.

            BlockingQueue interface extended by
                ArrayBlockingQueue - ArrayBlockingQueue is a bounded, blocking queue that stores the elements internally in an array. That it is bounded means that it cannot store unlimited amounts of elements. There is an upper bound on the number of elements it can store at the same time.
                LinkedBlockingQueue - The LinkedBlockingQueue keeps the elements internally in a linked structure (linked nodes). This linked structure can optionally have an upper bound if desired. If no upper bound is specified, Integer.MAX_VALUE is used as the upper bound.
                DelayQueue - Once an element is put in queue, it needs to wait for certain time as specified before it can be dequeued.
                PriorityBlockingQueue - It is unbounded queue just like LinkedBlockingQueue.
                                On the top of that, it can also maintain a priority(order) of elements while dequeuing elements. Objects put in this queue mush implement Comparabale interface.
                                The elements thus order themselves according to whatever priority you decide in your Comparable implementation.
                                If no comparator is specified when a PriorityQueue is constructed, then the default comparator for the type of data stored in the queue is used. The default comparator will order the queue in ascending order
                                IT USES HEAP SORT while inserting elements.
                    http://kodejava.org/how-do-i-use-priorityblockingqueue-class/
                SynchronousQueue - one element can be there in a queue at a time. The SynchronousQueue is a queue that can only contain a single element internally. A thread inserting an element into the queue is blocked until another thread takes that element from the queue. Likewise, if a thread tries to take an element and no element is currently present, that thread is blocked until a thread insert an element into the queue.

        Executor, ExecutorService, ThreadPoolExecutor, ScheduledExecutorService
            https://docs.oracle.com/javase/1.5.0/docs/api/java/util/concurrent/Executor.html
            ExecutorService can run thread using predefined thread pool. you dont have to worry about thread pooling.
            It has execute(runnable) and submit(callable) methods to run runnables and callables
            submit() can be used for Callable or Runnable. It returns Future.

        Future, Callable - FutureCallableExample.java

    java.util.concurrent.atomic

        A small toolkit of classes that support lock-free thread-safe programming on single variables.
        AtomicBoolean
        AtomicInteger  --- See JavaAtomic.java
        AtomicIntegerArray
        AtomicLong
        AtomicLongArray
        AtomicReference
        AtomicStampedReference
        AtomicIntegerArray
        AtomicLongArray
        AtomicReferenceArray

        It's a very powerful concept. Read JavaAtomic.java.
        It stores value in volatile field. It means that all threads accessing that value always has to read and write the value in main heap memory.
        Each thread caches objects from main heap memory to its own memory. Any read/write happens to its own memory. When any other thread tries to access the same object, it first reads from its own memory and then jvm pushes the first thread's changes to main heap memory and other threads' memory.
        In this situation, thread might not be able to read the newest value.

        If you define a value as volatile, thread cannot cache it in its own memory and it always has to go to main memory. You should do it only if it is required because it is a time consuming process.


    java.util.concurrent.locks

        Lock and ReadWriteLock interfaces
            - ReentrantLock
              see below example
            - ReadWriteReentrantLock - ReadWriteLock is an advanced thread lock mechanism. It allows multiple threads to read a certain resource, but only one to write it, at a time. Read-write locks provide increased level of concurrency and typically results in better performance if the frequency of read operations is more than write operations.
              see ReadWriteLockDemo.java

        http://tutorials.jenkov.com/java-util-concurrent/lock.html
        Lock interface and its concrete class ReentrantLock
        It is a synchronized block with more capabilities. (IMP)

        SIMILARITY:
            Just like synchronized block, it also applies a lock for an instance on a particular block of the code.

        Main DIFFERENCES between Locks and Synchronized Blocks

         - when you use synchronized method/bock with wait and notify. waiting thread will relinquish its lock for other thread to acquire.
            Unless you have provided a priority to threads, any random thread will be picked up and given lock.
            This is called UNFAIRness.
            Lock provides a facility of FAIRness

            The constructor of ReentrantLock class accepts an optional fairness parameter. When set true, locks favor granting access to the longest-waiting thread. Otherwise this lock does not guarantee any particular access order.

            https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/locks/ReentrantLock.html

            e.g.
            class X {
               private final ReentrantLock lock = new ReentrantLock(<<by default fairness=false>>);
               // ...

               public void m() {
                 .....
                 lock.lock();  // block until condition holds
                 try {
                   // ... method body
                 } finally {
                   lock.unlock()
                 }
                 .....
               }
             }


            Due to UNFAIRness, problem of STARVATION may occur
            http://tutorials.jenkov.com/java-concurrency/starvation-and-fairness.html

            The following three common causes can lead to starvation of threads in Java:

            1) Threads with high priority swallow all CPU time from threads with lower priority.
            2) Threads are blocked indefinitely waiting to enter a synchronized block, because other threads are constantly allowed access before it.
            3) Threads waiting on an object (called wait() on it) remain waiting indefinitely because other threads are constantly awakened instead of it.

            Solution:
            Using Lock Instead of Synchronization. Lock will allow to synchronize smaller chunk of important code just like synchronized block and also it will allow you set fairness parameter, if needed.

         - synchronized block has to start and end in the same method. lock can be locked in one method and unlocked in another method.

            class X {
                private final Lock lock = new ReentrantLock();
                public void m() {
                    lock.lock();
                    // ... method body
                    m1();
                }
                public void m1() {
                    //... method body
                    lock.unlock();
                }

                public void m() {
                    synchronized(this) { -- synchronized block has to start and end in the same method.
                        // ... method body
                    }
                }
            }
         - You can share a lock between multiple methods (e.g. LockATM.java)
             class X {
                private final Lock lock = new ReentrantLock();

                public void m1() {
                    lock.lock();
                    //... method body
                    lock.unlock();
                }
                public void m2() {
                    lock.lock();
                    //... method body
                    lock.unlock();
                }

                // m1 and m2 are same as m3 and m4
                public void m3() {
                    synchronized(this) {
                        // ... method body
                    }
                }
                public void m4() {
                    synchronized(this) {
                        // ... method body
                    }
                }

             }
             If one of the two threads sharing the same instance of X (and so the same instance of lock) enters m1 and another one is trying to enter m2,
             then another one has to wait until first one unlocks.

         - You cannot pass any parameters to the entry of a synchronized block. Thus, having a timeout trying to get access to a synchronized block is not possible.
            public void m() {
                lock.tryLock(10, TimeUnit.SECONDS);
                // ... method body
                lock.unlock();
            }

        Lock Methods

         The Lock interface has the following primary methods:

         lock() - locks the Lock instance if possible. If the Lock instance is already locked, the thread calling lock() is blocked until the Lock is unlocked.
         lockInterruptibly() - locks the Lock unless the thread calling the method has been interrupted. Additionally, if a thread is blocked waiting to lock the Lock via this method, and it is interrupted, it exits this method calls.
         tryLock() - attempts to lock the Lock instance immediately. It returns true if the locking succeeds, false if Lock is already locked. This method never blocks.
         tryLock(long timeout, TimeUnit timeUnit) - works like the tryLock() method, except it waits up the given timeout before giving up trying to lock the Lock.
         unlock() - unlocks the Lock instance. Typically, a Lock implementation will only allow the thread that has locked the Lock to call this method. Other threads calling this method may result in an unchecked exception (RuntimeException).



                class X {
                    private final Lock lock = new ReentrantLock();
                    // ...

                    public void m() {
                        lock.lock();  // block until condition holds
                        try {
                            // ... method body
                        } finally {
                            lock.unlock()
                        }
                    }
                }


        ReadWrite Lock -
        http://examples.javacodegeeks.com/core-java/util/concurrent/locks-concurrent/readwritelock/java-readwritelock-example/

        see ReadWriteLockDemo.java

        The ReadWriteLock offers two main methods
            Lock readLock()
            Lock writeLock()
            As the name suggests, the readLock() method is to acquire read-Lock and writeLock is called for acquiring the write-Lock.

        ReadWriteLock is implemented by ReentrantReadWriteLock Class
            ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
            Lock readLock = readWriteLock.readLock();
            Lock writeLock = readWriteLock.writeLock();

        Threads can acquire multiple read Locks, but only a single Thread can acquire mutually-exclusive write Lock .Other threads requesting readLocks have to wait till the write Lock is released.


Timer and TimerTask - They are there since java 1.3
    http://javarevisited.blogspot.com/2013/02/what-is-timer-and-timertask-in-java-example-tutorial.html
    It is for scheduling a task


Volatile -
    It has to do with CPU cache and Main Heap Memory
    http://tutorials.jenkov.com/java-concurrency/volatile.html
    Read JavaAtomic.java

How to make a class immutable?
    https://docs.oracle.com/javase/tutorial/essential/concurrency/imstrat.html
    1) Don't provide "setter" methods — methods that modify fields or objects referred to by fields.
    2) Make all fields final and private.
    3) Don't allow subclasses to override methods. The simplest way to do this is to declare the class as final. A more sophisticated approach is to make the constructor private and construct instances in factory methods.
    4) If the instance fields include references to mutable objects, don't allow those objects to be changed:
            Don't provide methods that modify the mutable objects.
            Don't share references to the mutable objects. Never store references to external, mutable objects passed to the constructor; if necessary, create copies, and store references to the copies. Similarly, create copies of your internal mutable objects when necessary to avoid returning the originals in your methods.