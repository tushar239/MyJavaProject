package algorithms.crackingcodinginterviewbook.objectorienteddesign;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

// http://www.careercup.com/question?id=17230678
/*
    Basically LRU cache is nothing but a queue, especially a concurrent queue.

    Put the elements in cache(queue) till max cache size is reached. After that poll an element (Least recently used element will be first in the queue) to make a space for a new element.
    When you access an element from cache(queue), remove that element from queue and put it back at the end of the queue because you accessed that element so it becomes most recently used.

    Now, queue.remove(...) does not returned you a removed element. So, you may need to implement your custom Queue class with customized remove method
    or
    use a hashmap like below.
 */

public class LRUCache<Key, Value> {

    private final int maxSize;
    private ConcurrentHashMap<Key, Value> map;
    private ConcurrentLinkedQueue<Key> queue;

    public LRUCache(final int maxSize) { // you need some max size of cache for implementing LRU
        this.maxSize = maxSize;
        map = new ConcurrentHashMap<Key, Value>(maxSize);
        queue = new ConcurrentLinkedQueue<Key>();
    }

    /**
     * @param key - may not be null!
     * @param value - may not be null!
     */
    public void put(final Key key, final Value value) {
        if (map.containsKey(key)) {
            queue.remove(key); // remove the key from the FIFO queue
        }

        while (queue.size() >= maxSize) {
            Key oldestKey = queue.poll();
            if (null != oldestKey) {
                map.remove(oldestKey);
            }
        }
        queue.add(key);
        map.put(key, value);
    }

    /**
     * @param key - may not be null!
     * @return the value associated to the given key or null
     */
    public Value get(final Key key) {
        return map.get(key);
    }
}