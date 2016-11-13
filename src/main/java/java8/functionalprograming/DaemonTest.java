package java8.functionalprograming;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

/**
 * @author Tushar Chokshi @ 8/9/16.
 */
public class DaemonTest {
    static ThreadLocal<Person> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) throws InterruptedException {
        List<WorkerThread> workerThreadList = new CopyOnWriteArrayList<>();

        IntStream.range(0, 9).forEach(i -> {
            WorkerThread wt = new WorkerThread();
            wt.start();
            workerThreadList.add(wt);
        });
        Thread.sleep(1000);
        System.out.println("here");
        workerThreadList.stream().forEach(workerThread -> {
            Person person = workerThread.get();
            if(person != null) {
                System.out.println(person.getName());
            }
        });
    }

    static class Person {
        String name;

        public Person(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
    static class WorkerThread extends Thread {
        public WorkerThread() {

        }

        public void run() {
            Person person = new Person("Tushar from "+Thread.currentThread().getId());
            System.out.println("adding person to "+Thread.currentThread().getId());
            threadLocal.set(person);
            System.out.println(get().getName());

        }
        public Person get() {
            return threadLocal.get();
        }
    }

}

