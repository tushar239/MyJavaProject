package algorithms._1array_stack_queue.geeksforgeeks.pointers_sorting_stack_queue.queue;

/*
    Implement Queue using Stacks

    https://www.geeksforgeeks.org/queue-using-stacks/

    This problem is similar to 'ImplementStackUsingQueues.java'.

    Solution:

    You can use two stacks (S1, S2) to make them work like a queue.
    When new element needs to be added, push it to S2 (empty stack) and then pop all elements from S1 and push them to S2.
    and then exchange S1 with S2 and vice-versa.

*/
public class _1ImplementQueueUsingStacks {
}
