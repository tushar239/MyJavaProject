    HashTable (from 'Data Structure Self Created Document.doc')
    ---------
    HashTable is nothing but a special type of array in which every element is stored as per its hashvalue.
    Hash value is calculated using hash function.
    For integers, hash value is same as int number.
    For booleans, java has chose two random number 1231(true) and 1237(false).
    For doubles, there is some formula to convert double to long and eventually to some int hash value.
    For Strings, hash value is calculated in following way.
        String str = abcd,
        Hashfunc(str) = 97*31^3 + 98*31^2 + 99*31^1 + 100*31^0

        Why 31? After a lot of research it was decided to choose one prime number.
        Prime number is a number that can be divided by 1 or by that number itself and prime number starts from 2.
        e.g. 2,3,5,7,11,13,17,19,23 etc.


    Question is how to decide array size?
    You need to decide some size 'M' for total 'N' elements to insert in it.

    Approach 1 (Separate Chaining/Open Hashing)
        M = N/5
        hash value = value returned by hash function % M
        e.g. if there are total 15 elements, M = 3 and hash value of 1 is 1%3=1 and value of 4 is 4%3=1. So, both 1 and 4 should be inserted in array[0].
        This is called COLLISION.

        To avoid collision, put elements with same hash value in a linked list.

        array[0] = LinkedList (0)
        array[1] = LinkedList (1->4)
        array[2] = LinkedList (5->8->14)

        This approach is called separate chaining.
        It takes O(1) time for inserting an element, as you can insert an element in front of a linked list.
        It takes O(1) to O(log n) for searching any element.

    Approach 2 (Open Addressing / Closed Hashing)
        In this approach, instead of using a linked list you put an element with same hash value at some other position in the array.
        There are different approaches(syntaxes) to find this another position.
        It means this method requires M (array size) > N (number of elements to be inserted).

        1. Linear Probing
           In this approach, you put an element of same hash value in next available empty cell of an array.
           In this approach insertion/deletion/search will take O(log n) approx.
           So, this is worse than Separate chaining because in Separate chaining, it takes O(1) to insert an element + it requires bigger array size.
           It also creates a problem of CLUSTERING - there can be big gap between blocks of data in an array.

        2. Quadratic Probing
           To avoid the problem of CLUSTERING, use some other syntax to decide hash value
           hash value = ( value returned by hash function + (i^2) ) % M
           where keep i=0 initially, if collision found, increment i by 1.

        3. Double Hashing
           For better approach to avoid CLUSTERING, use better syntax than Quadratic Probing
           hash value = ( value returned by hash function + (R - (value returned by hash function % R)) ) % M
           R is any prime number
