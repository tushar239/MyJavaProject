package algorithms._1array_stack_queue.geeksforgeeks.linked_list.medium;

import java.util.LinkedList;

/*
    Hashing with Collision avoidance technique Separate Chaining

    https://www.geeksforgeeks.org/c-program-hashing-chaining/
*/
public class _2ImplementHashingWithSimpleChainingAndSinglyLinkedList {
    private static final int HASHTABLE_SIZE = 5;
    // array of linked lists

    public static void main(String[] args) {
        chaining();
    }

    private static void chaining() {
        LinkedList<String>[] hashTable = new LinkedList[HASHTABLE_SIZE];

        for (int i = 0; i < HASHTABLE_SIZE; i++) {
            hashTable[i] = new LinkedList<>();
        }

        String s1 = "abc";
        String s2 = "def";
        String s3 = "xyz";
        String s4 = "tushar chokshi";
        String s5 = "jkl";
        String s6 = "imd";

        hashTable[getArrayIndex(s1, HASHTABLE_SIZE)].addFirst(s1);
        hashTable[getArrayIndex(s2, HASHTABLE_SIZE)].addFirst(s2);
        hashTable[getArrayIndex(s3, HASHTABLE_SIZE)].addFirst(s3);
        hashTable[getArrayIndex(s4, HASHTABLE_SIZE)].addFirst(s4);
        hashTable[getArrayIndex(s5, HASHTABLE_SIZE)].addFirst(s5);
        hashTable[getArrayIndex(s6, HASHTABLE_SIZE)].addFirst(s6);

        System.out.println("Created hashed hashTable.....");
        System.out.println();

        for (int i = 0; i < HASHTABLE_SIZE; i++) {
            System.out.print(i+"th element: ");
            for (String s : hashTable[i]) {
                System.out.print(s+",");
            }
            System.out.println();
        }

        System.out.println();
        System.out.println("Trying to find a string in hashed hashTable.....");
        System.out.println();

        String foundString = search(s1, hashTable, HASHTABLE_SIZE);
        System.out.println("found string:" + foundString);
    }

    private static String search(String strToSearch, LinkedList<String>[] hashTable, int hashTableSize) {
        int arrayIndex = getArrayIndex(strToSearch, hashTableSize);
        LinkedList<String> list = hashTable[arrayIndex];
        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i);
            if (s.equalsIgnoreCase(strToSearch)) {
                return s;
            }
        }
        return null;
    }

    // IMPORTANT to remember how to find a place in an array
    private static int getArrayIndex(String s, int arraySize) {
        // syntax for separate chaining to find hash index = HASH(X) % array size
        // syntax for open addressing (array size should be >= number of elements to be stored)
        //      linear probing         hash index = HASH(X) + i % array size ,             where i starts with 0 and increments by 1.
        //      quadratic probing      hash index = HASH(X) + i^2 % array size,            where i starts with 0 and increments by 1.
        //      double hashing         hash index = HASH(X) + i * HASH(X) % array size,    where i starts with 0 and increments by 1.
        return s.hashCode() % arraySize;
    }

}
