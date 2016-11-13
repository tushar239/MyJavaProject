package java8.functionalprograming.functionalprogramminginjavabook.chapter11.mapusingtree;

import java8.functionalprograming.functionalprogramminginjavabook.chapter7.Result;

/**
 * @author Tushar Chokshi @ 10/25/16.
 */

/*
pg. 330 (Using Map with noncomparable keys)
In our below example, key (K) is comparable, but what if it noncomparable?

class MapEntry<K, V> implements Comparable<MapEntry<K, V>>
{
...
public int compareTo(MapEntry<K, V> that) {
  int thisHashCode = this.hashCode();
  int thatHashCode = that.hashCode();
  return thisHashCode < thatHashCode
      ? -1
      : thisHashCode > thatHashCode
          ?1
          : 0;
}
...

What is collision in java map that uses array to store MapEntries?
If hash function returns same hashCode for two different keys, then in Java, map needs to insert two keys at the same array location. This is called collision in Java Map.
In this situation, before Java 8, Java used to create a linked list at that array location and used to keep both keys in it.
In Java 8, linked list is replaced by BST (Binary Search Tree) for faster search.

In our example of a MapUsingTree, we are using tree in place of an array. In this case each node of a tree can contain a list of Tuple<K,V>.
Here, in our example, if two different keys have same hashCode, then there will be collision. There is no hash function involved here.
In this situation you need to insert a MapEntry in to Tree<MapEntry<Integer, List<Tuple<K, V>>>>. So, each node in a tree can contain a linked list.

*/
public class MapEntry<K extends Comparable<K>, V> implements Comparable<MapEntry<K, V>> {

    public final K key;
    public final Result<V> value;

    private MapEntry(K key, Result<V> value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof MapEntry && this.key.equals(((MapEntry) o).key);
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }

    public static <K extends Comparable<K>, V> MapEntry<K, V>
    mapEntry(K key, V value) {
        return new MapEntry<>(key, Result.success(value)); // look at this, we are wrapping the value with Result.
    }

    public static <K extends Comparable<K>, V> MapEntry<K, V>
    mapEntry(K key) {
        return new MapEntry<>(key, Result.empty()); // look at this. instead of putting null as value, we are putting empty Result.
    }

    public K getKey() {
        return key;
    }

    public Result<V> getValue() {
        return value;
    }

    @Override
    public int compareTo(MapEntry<K, V> me) { // Two MapEntries are compared by key
        return this.key.compareTo(me.key);
    }

    @Override
    public String toString() {
        return String.format("MapEntry(%s, %s)", key, value);
    }
}