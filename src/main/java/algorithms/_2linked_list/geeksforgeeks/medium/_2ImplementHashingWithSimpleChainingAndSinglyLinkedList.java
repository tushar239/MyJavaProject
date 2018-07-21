package algorithms._2linked_list.geeksforgeeks.medium;

import java.util.LinkedList;

/*
    Hashtable chaining with Separate Chaining and Singly LinkedList

    https://www.geeksforgeeks.org/c-program-hashing-chaining/


    Hashtable chaining with Separate Chaining and Doubly LinkedList
    https://www.geeksforgeeks.org/hashtables-chaining-with-doubly-linked-lists/

    I personally don't see any benefit of using doubly linkedlist because to search/remove an element you always start from the beginning of the list, you never do that from the middle of the list.
    If there is a need to remove/search an element from the middle of the list, then doubly linkedlist makes sense.


    Question for interviewer:
    Can I use java's inbuilt linkedlist or should I created my own?

*/
public class _2ImplementHashingWithSimpleChainingAndSinglyLinkedList {
    private static final int HASH_TABLE_SIZE = 5;
    // array of linked lists

    public static void main(String[] args) {
        LinkedList<String>[] hashTable = new LinkedList[HASH_TABLE_SIZE];
        for (int i = 0; i < HASH_TABLE_SIZE; i++) {
            hashTable[i] = new LinkedList<>();
        }

        chaining(hashTable);
    }

    private static void chaining(LinkedList<String>[] hashTable) {
        String s1 = "abc";
        String s2 = "def";
        String s3 = "xyz";
        String s4 = "TUSH CHOK";
        String s5 = "jkl";
        String s6 = "imd";

        hashTable[getArrayIndex(s1, HASH_TABLE_SIZE)].addFirst(s1);
        hashTable[getArrayIndex(s2, HASH_TABLE_SIZE)].addFirst(s2);
        hashTable[getArrayIndex(s3, HASH_TABLE_SIZE)].addFirst(s3);
        hashTable[getArrayIndex(s4, HASH_TABLE_SIZE)].addFirst(s4);
        hashTable[getArrayIndex(s5, HASH_TABLE_SIZE)].addFirst(s5);
        hashTable[getArrayIndex(s6, HASH_TABLE_SIZE)].addFirst(s6);

        System.out.println("Created hashed hashTable.....");
        System.out.println();

        for (int i = 0; i < HASH_TABLE_SIZE; i++) {
            System.out.print(i+"th element: ");
            for (String s : hashTable[i]) {
                System.out.print(s+",");
            }
            System.out.println();
        }

        System.out.println();
        System.out.println("Trying to find a string in hashed hashTable.....");
        System.out.println();

        String foundString = search(s1, hashTable, HASH_TABLE_SIZE);
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
