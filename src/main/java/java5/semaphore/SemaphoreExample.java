package java5.semaphore;

import java.util.concurrent.Semaphore;

/**
 * @author Tushar Chokshi @ 8/15/16.
 */

/*
final int numberOfPermits = 2;
Semaphore semaphore = new Semaphore(numberOfPermits, true);

This means that the semaphore object has two slots; that is, you can make two calls, one after the other, to semaphore.acquire(). However, if you make a third call without first calling semaphore.release(), your thread will be suspended. To test this result, simply comment out the second call to semaphore.release() in the run() method.
If you try this, execution simply stops, because the JVM is waiting for the semaphore to be released. For reasons like this, semaphores must be used with caution.
*/
public class SemaphoreExample {

    public static void main(String[] args) {
        final int numberOfProcesses = 3;
        final int numberOfPermits = 2;

        Semaphore semaphore = new Semaphore(numberOfPermits, true);
        ProcessExclusion p[] = new ProcessExclusion[numberOfProcesses];

        for (int i = 0; i < numberOfProcesses; i++) {
            p[i] = new ProcessExclusion(semaphore);
            p[i].setThreadId(p[i].hashCode());
            p[i].start();
        }
    }

    static class ProcessExclusion extends Thread {
        private int threadId;
        private Semaphore semaphore;

        public ProcessExclusion(Semaphore semaphore) {
            this.semaphore = semaphore;
        }

        public void setThreadId(int threadId) {
            this.threadId = threadId;
        }

        private int random(int n) {
            return (int) Math.round(n * Math.random());
        }

        private void busyCode() {
            int sleepPeriod = random(5000);
            try {
                sleep(sleepPeriod);
            } catch (InterruptedException e) {
            }
        }

        private void noncriticalCode() {
            busyCode();
        }

        private void criticalCode() {
            busyCode();
        }

        public void run() {
            for (int i = 0; i < 3; i++) {
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + " acquired semaphore lock.");
                    criticalCode();
                    System.out.println(Thread.currentThread().getName() + " releasing semaphore lock.");
                    semaphore.release();
                } catch (InterruptedException e) {
                    System.out.println("Exception " + e.toString());
                }
            }
            for (int i = 0; i < 3; i++) {
                noncriticalCode();
            }
        }
    }

}