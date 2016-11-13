1)

    SortLinkedList.java
    PalindromLinkedList.java
    DetectIfLinkedListHasALoop.java
are using pointers a and b. One moves slow and another moves fast.
Difference is b either starts from head.next or head.

To partition (divide) a list into two halves

         Node a = headOriginal;
         Node b = headOriginal.next; // IMP
         while ((b != null) && (b.next != null))
         {
             headOriginal = headOriginal.next;
             b = (b.next).next;
         }
         b = headOriginal.next; // IMP
         headOriginal.next = null;  // IMP

In DetectIfLinkedListHasALoop.java and PalindromLinkedList.java uses
         Node a = linkedList.head;
         Node b = linkedList.head;


2) if node's prevNode == null means node is a head node
   if node's next node == null means node is a tail node
   You have to keep these conditions in mind

3) As an extra space (like PalindromLinkedList.java, RemoveDups.java), you can use stack/set.