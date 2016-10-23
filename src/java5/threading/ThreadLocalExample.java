package java5.threading;

// http://tutorials.jenkov.com/java-concurrency/threadlocal.html
public class ThreadLocalExample {


    public static class MyRunnable implements Runnable {

        // ThreadLocal maintains a map where key is a thread and value is something
        // from a running thread, whenever you do threadLocal.get(), it will actually look for a key that is same as current thread and return its value
        // Even though, MyRunnable instance is shared by multiple threads, inside MyRunnable, you can get a specific value related to specific thread.
        private ThreadLocal<Integer> threadLocal = new ThreadLocal<>();

        @Override
        public void run() {
            int value = (int) (Math.random() * 100D);
            threadLocal.set(value);// will set a value against current thread

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }

            System.out.println(threadLocal.get());// will get a value of current thread
        }
    }


    public static void main(String[] args) throws InterruptedException {
        MyRunnable sharedRunnableInstance = new MyRunnable();

        Thread thread1 = new Thread(sharedRunnableInstance);
        Thread thread2 = new Thread(sharedRunnableInstance);

        thread1.start();
        thread2.start();

        thread1.join(); //current thread (main) will wait for thread 1 to terminate
        thread2.join(); //current thread (main) will wait for thread 2 to terminate
    }

}
