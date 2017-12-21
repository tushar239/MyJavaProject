Stack Vs LinkedList

Stack Sorting:
Unlike to LinkedList, Stack extends Vector extends AbstractList.
LinkedList extends AbstractSequentialList extends AbstractList.

Vector and so Stack uses resizable array (just like ArrayList). So retrieval of an element usine index is easy.
LinkedList overrides get(index) method because it doesn't use array internally. It needs to traverse a linked list every time to reach to a specific index (node).

Following link has a special type of stack sorting (as done in below example) and quick sort.
http://stackoverflow.com/questions/24768011/write-a-program-to-sort-a-stack-in-ascending-order

Look at SortStack.java

Stack vs Queue
In java there are many variations of Queue. There are different types of Queue (in java 1.5) that uses array or linked list internally.