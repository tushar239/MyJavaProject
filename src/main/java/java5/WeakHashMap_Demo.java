package java5;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * @author Tushar Chokshi @ 8/12/16.
 */
/*
http://www.tutorialspoint.com/java/java_weakhashmap_class.htm

WeakHashMap is an implementation of the Map interface that stores only weak references to its keys.
Storing only weak references allows a key-value pair to be garbage collected when its key is no longer referenced outside of the WeakHashMap.

It stores the keys as WeakReference objects.

The WeakHashMap functions identically to the HashMap with one very important exception:
if the Java memory manager no longer has a strong reference to the object specified as a key, then the entry in the map will be removed.
 */
public class WeakHashMap_Demo {
    private static Map map;
    public static void main (String args[]) {
        map = new WeakHashMap();
        map.put(new String("Maine"), "Augusta");

        Runnable runner = () -> {
            while (map.containsKey("Maine")) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ignored) {
                }
                System.out.println("Thread waiting");
                System.gc();
            }
        };
        Thread t = new Thread(runner);
        t.start();
        System.out.println("Main thread is waiting");
        try {
            t.join();
        } catch (InterruptedException ignored) {
        }
        System.out.println("Main thread is ending");
    }
}
