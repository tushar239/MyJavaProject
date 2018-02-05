package algorithms.crackingcodinginterviewbook._8thread_and_locks;

import com.google.common.collect.Lists;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/*

Dining Philosophers:

    There are some people sitting on a table for a dinner. Each person has one chopstick on its left and one on its right.
    So, there is one chopstick between two persons on a table.
    To start the dinner, a person needs to have both chopsticks. Each person will take left chopstick first and then right one.
    After finishing the dinner, person has to put both chopsticks back.

    Guess a situation when all people took their left chopsticks. Now, all will be waiting forever to get the right ones.
    This will create a deadlock.
    First write the code that creates a deadlock, the correct it in such a way that it doesn't create a deadlock.


    This is a very nice problem that gives an opportunity to design objects properly and apply multi-threading and locking concepts.
    It is a good example to use lock.tryLock() instead of just lock.lock().

    Solution:

        If a person can acquire on both left and right chopsticks, then only continue eating process, otherwise release the lock on a chopstick that it acquired a lock on.
        This will release a deadlock.
        Draw a diagram of a table, 4 person and 4 chopsticks between them and simulate above scenario to understand it better.

        Here, there are 3 main entities:
        - Person (Philosopher)
        - Chopstick
        - Table (PhilosopherChopsticksRelationManager)

        Lock is a property of a Chopstick because when a person reserves a chopstick, lock is applied on a chopstick.
        So, it is important to have a ReentrantLock as a property of ChopStick entity.

        PhilosopherChopsticksRelationManager maintains the relationship between a Philosopher and two chopsticks using Map<Philosopher, ChopStick[]>.

        There are helper classes like
        - ChopStickReserver that locks/unlocks a ChopStick.
        - EatingService that has letEat method that lets a philosopher reserve the chopsticks and then eat the food and then unreserve the chopsticks.

 */
public class _3DiningPhilosophers {

    public static void main(String[] args) throws InterruptedException {
        Philosopher p1 = new Philosopher(1);
        Philosopher p2 = new Philosopher(2);
        Philosopher p3 = new Philosopher(3);
        Philosopher p4 = new Philosopher(4);

        ChopStick c1 = new ChopStick(1, new ReentrantLock());
        ChopStick c2 = new ChopStick(2, new ReentrantLock());
        ChopStick c3 = new ChopStick(3, new ReentrantLock());
        ChopStick c4 = new ChopStick(4, new ReentrantLock());

        Map<Philosopher, ChopStick[]> map = new HashMap<>();
        map.put(p1, new ChopStick[]{c1, c2});
        map.put(p2, new ChopStick[]{c2, c3});
        map.put(p3, new ChopStick[]{c3, c4});
        map.put(p4, new ChopStick[]{c4, c1});

        ChopStickReserver chopStickReserver = new ChopStickReserver();
        PhilosopherChopSticksRelationManager philosopherChopSticksRelationManager = new PhilosopherChopSticksRelationManager(map, chopStickReserver);
        EatingService eatingService = new EatingService(philosopherChopSticksRelationManager);

        List<Philosopher> philosophers = Lists.newArrayList(p1, p2, p3, p4);

        makePhilosophersEat(eatingService, philosophers);
    }

    private static void makePhilosophersEat(EatingService eatingService, List<Philosopher> philosophers) throws InterruptedException {
        List<Thread> threads = new LinkedList<>();

        for (Philosopher philosopher : philosophers) {

            Thread t = new Thread() {
                @Override
                public void run() {

                    boolean eaten = false;

                    // keep trying to let philosopher eat
                    // philosopher can eat only if both left and right chopsticks can be reserved for him
                    do {
                        try {
                            eaten = eatingService.letEat(philosopher);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }

                    } while (!eaten);

                    System.out.println("\033[1m" + "..... philosopher " + philosopher.id + " could eat now........" + "\033[0m");
                }
            };

            threads.add(t);
        }

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }
    }

    static class Philosopher {

        private final int id;

        public Philosopher(int id) {
            this.id = id;
        }
    }

    static class ChopStick {
        private int id;
        // lock is a property of a ChopStick because once ChopStick is reserved by a Philosopher, a lock is applied on a ChopStick.
        // You can also replace it with 'boolean reserved' property. But Here, we want to experiment Locking/Unlocking.
        private ReentrantLock lock;

        public ChopStick(int id, ReentrantLock lock) {
            this.id = id;
            this.lock = lock;
        }
    }

    static class PhilosopherChopSticksRelationManager {
        private Map<Philosopher, ChopStick[]> philosopherChopSticks;

        private final ChopStickReserver chopStickReserver;

        public PhilosopherChopSticksRelationManager(Map<Philosopher, ChopStick[]> philosopherChopSticks, ChopStickReserver chopStickReserver) {
            this.philosopherChopSticks = philosopherChopSticks;
            this.chopStickReserver = chopStickReserver;
        }

        public ChopStick[] retrieveChopSticks(Philosopher philosopher) {
            ChopStick[] chopSticks = philosopherChopSticks.get(philosopher);

            ChopStick leftChopStick = chopSticks[0];
            ChopStick rightChopStick = chopSticks[1];

            // Reserve left chopstick for a Philosopher
            boolean leftChopStickReserved = chopStickReserver.reserve(leftChopStick);
            if (leftChopStickReserved) {
                System.out.println(leftChopStick.id + " is reserved for philosopher " + philosopher.id);
            } else {
                System.out.println(leftChopStick.id + " could not be reserved for philosopher " + philosopher.id);
            }

            // Reserve right chopstick for a Philosopher
            boolean rightChopStickReserved = chopStickReserver.reserve(rightChopStick);
            if (rightChopStickReserved) {
                System.out.println(rightChopStick.id + " is reserved for philosopher " + philosopher.id);
            } else {
                System.out.println(rightChopStick.id + " could not be reserved for philosopher " + philosopher.id);
            }

            // If both left and right chopsticks are reserved for a Philosopher, then only return those chopsticks
            // Otherwise, unreserve a reserved chopstick for other philosopher to use it.
            // This philosopher has to wait till he can reserve both left and right chopsticks.
            if (leftChopStickReserved & rightChopStickReserved) {
                return chopSticks;
            } else {
                unreserveChopSticks(chopSticks);
            }

            return null;
        }

        public boolean unreserveChopSticks(ChopStick[] chopSticks) {
            ChopStick leftChopStick = chopSticks[0];
            ChopStick rightChopStick = chopSticks[1];

            boolean leftChopStickUnreserved = chopStickReserver.unReserve(leftChopStick);
            System.out.println("Chopstick " + leftChopStick.id + " is unreserved");

            boolean rightChopStickUnreserved = chopStickReserver.unReserve(rightChopStick);
            System.out.println("Chopstick " + rightChopStick.id + " is unreserved");

            return leftChopStickUnreserved & rightChopStickUnreserved;
        }
    }

    static class ChopStickReserver {

        public boolean reserve(ChopStick chopStick) {
            //chopStick.lock.lock(); // This will cause a deadlock
            //return true;
            return chopStick.lock.tryLock();// if a chopstick cannot be reserved for a philosopher, just return false.
        }

        public boolean unReserve(ChopStick chopStick) {
            // unlocking should be done in finally block. Here, there is no other business logic. So, no need of try-finally block.
            if (chopStick.lock.isHeldByCurrentThread()) {// very important. without this, if some other thread, who did not lock the chopstick, tries to unlock that chopstick, then it will throw IllegalMonitorStateException.
                chopStick.lock.unlock();
                return true;
            }
            return false;
        }

    }

    static class EatingService {
        private PhilosopherChopSticksRelationManager relationManager;

        public EatingService(PhilosopherChopSticksRelationManager relationManager) {
            this.relationManager = relationManager;
        }

        // If both chopsticks can be reserved for a philosopher, this method will finish eating process and return true, otherwise it will return false.
        public boolean letEat(Philosopher philosopher) throws InterruptedException {
            ChopStick[] chopSticks = relationManager.retrieveChopSticks(philosopher);

            if (chopSticks != null && chopSticks.length == 2) {
                Thread.sleep(5_000);//eating process is going on
                return relationManager.unreserveChopSticks(chopSticks);
            }

            return false;
        }
    }


}
