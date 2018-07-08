package algorithms._0Fundamentals.Hashing;

/*
    Introduction to Hashing

        https://www.geeksforgeeks.org/hashing-set-1-introduction/
        Watch '_1hashing intro.mp4'

        Collision Handling:
        Since a hash function gets us a small number for a big key, there is possibility that two keys result in same value. The situation where a newly inserted key maps to an already occupied slot in hash table is called collision and must be handled using some collision handling technique. Following are the ways to handle collisions:

        Chaining:           The idea is to make each cell of hash table point to a linked list of records that have same hash function value. Chaining is simple, but requires additional memory outside the table.

                            (IMPORTANT) Separate chaining is ok with array size to store all values to be < number of elements.

                            https://www.geeksforgeeks.org/hashing-set-2-separate-chaining/
                            Watch 'collision avoiding method - separate chaining.mp4'


                            Java uses this technique. From Java 8 onwards, BST is used instead of linked list to store the elements with same hash value.

        Open Addressing:    In open addressing, all elements are stored in the hash table itself. Each table entry contains either a record or NIL. When searching for an element, we one by one examine table slots until the desired element is found or it is clear that the element is not in the table.

                            (IMPORTANT) Open addressing requires array size to store all values to be >= number of elements.

                            https://www.geeksforgeeks.org/hashing-set-3-open-addressing/
                            Watch '_3collision avoiding method - open addressing(linear probing, quadratic probing, double hashing).mp4'

                            There are 3 types of open addressing techniques.

                            - linear probing

                                hash index = (hash(x)+i) % hash table size, where i starts with 0 and increments by 1



                                hash index = (hash(x)+0) % hash table size

                                If this hash index is already full, then use

                                hash index = (hash(x)+1) % hash table size

                                If this hash index is already full, then use

                                hash index = (hash(x)+2) % hash table size

                            - quadratic probing

                                hash index = (hash(x)+i^2) % hash table size, where i starts with 0 and increments by 1



                                hash index = (hash(x)+0) % hash table size

                                If this hash index is already full, then use

                                hash index = (hash(x)+1) % hash table size

                                If this hash index is already full, then use

                                hash index = (hash(x)+4) % hash table size

                                If this hash index is already full, then use

                                hash index = (hash(x)+9) % hash table size


                            - double hashing

                                hash index = (hash(x)+ i * hash(x)) % hash table size, where i starts with 0 and increments by 1



                                hash index = (hash(x)+0) % hash table size

                                If this hash index is already full, then use

                                hash index = (hash(x) + hash(x)) % hash table size

                                If this hash index is already full, then use

                                hash index = (hash(x) + 2 * hash(x)) % hash table size

                                If this hash index is already full, then use

                                hash index = (hash(x) + 3 * hash(x)) % hash table size
*/
public class HashingFundamentals {
}
