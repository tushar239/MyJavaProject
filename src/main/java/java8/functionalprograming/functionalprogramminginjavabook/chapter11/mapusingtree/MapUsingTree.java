package java8.functionalprograming.functionalprogramminginjavabook.chapter11.mapusingtree;

import java8.functionalprograming.functionalprogramminginjavabook.chapter10.Tree;
import java8.functionalprograming.functionalprogramminginjavabook.chapter2.Function;
import java8.functionalprograming.functionalprogramminginjavabook.chapter5and8.List;
import java8.functionalprograming.functionalprogramminginjavabook.chapter7.Result;

import static java8.functionalprograming.functionalprogramminginjavabook.chapter11.mapusingtree.MapEntry.mapEntry;

// pg 327
public class MapUsingTree<K extends Comparable<K>, V> {
    // In reality, java 8 Map still works like previous Java, but when there is a collision to store multiple MapEntries in the same array location, it uses BST (Binary Search Tree) for faster search instead of linked list.
    // You can watch this video to understand, how Java Map works - https://www.youtube.com/watch?v=c3RVW3KGIIE

    // Unlike to Java, this class is using a tree to store MapEntry (key-value pair) instead of array.
    // So, as described on pg 330 of the book, to store multiple MapEntries at the same treenode, we need a list on each tree node 'Tree<MapEntry<Integer, List<Tuple<K, V>>>>'

    protected final Tree<MapEntry<K, V>> delegate;

    private MapUsingTree() {
        this.delegate = Tree.empty();
    }

    private MapUsingTree(Tree<MapEntry<K, V>> delegate) {
        this.delegate = delegate;
    }

    public MapUsingTree<K, V> add(K key, V value) {
        return new MapUsingTree<>(delegate.insert(mapEntry(key, value)));
    }

    public boolean contains(K key) {
        return delegate.member(mapEntry(key));
    }

    public MapUsingTree<K, V> remove(K key) {
        return new MapUsingTree<>(delegate.remove(mapEntry(key)));
    }

    public MapEntry<K, V> max() {
        return delegate.max();
    }

    public MapEntry<K, V> min() {
        return delegate.min();
    }

    public Result<MapEntry<K, V>> get(K key) {
        return delegate.get(mapEntry(key));
    }

    public boolean isEmpty() {
        return delegate.isEmpty();
    }

    public static <K extends Comparable<K>, V> MapUsingTree<K, V> empty() {
        return new MapUsingTree<>();
    }

    // pg 328
    public <B> B foldLeft_PreOrder(B identity, Function<MapEntry<K, V>, Function<B, Function<B, B>>> f) {
        return Tree.foldLeft_PreOrder_Using_One_Function(delegate, identity, f);
    }

    // pg 329
    public <B> B foldLeft_InOrder(B identity, Function<B, Function<MapEntry<K, V>, Function<B, B>>> f) {
        return Tree.foldLeft_InOrder_Using_One_Function(delegate, identity, f);
    }

    // pg 329
    public <B> B foldLeft_ReverseOrder(B identity, Function<B, Function<MapEntry<K, V>, Function<B, B>>> f) {
        return Tree.foldRight_InOrder_Using_One_Function(delegate, identity, f);
    }

    // pg 329
    public List<V> values() {
        V nullV = null;
        return Tree.map_to_list(delegate, mapEntry -> mapEntry.getValue().getOrElse(nullV));
    }


}

