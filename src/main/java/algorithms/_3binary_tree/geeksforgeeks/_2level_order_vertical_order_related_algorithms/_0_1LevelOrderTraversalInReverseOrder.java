package algorithms._3binary_tree.geeksforgeeks._2level_order_vertical_order_related_algorithms;

/*
    Reverse Level Order Traversal

    https://www.geeksforgeeks.org/reverse-level-order-traversal/

                    1
           2                    3
    4           5       6               7


    Level Order Traversal:
        O/P: 1,2,3,4,5,6,7
        Use Queue

    Reverse Order Traversal:
        O/P: 4,5,6,7,2,3,1
        Use Queue and Stack


            Queue = 1
            stack = empty

        pop from queue and push it to stack and push popped element's RIGHT and then LEFT children to queue

            Queue = 3,2
            stack = 1

            Queue = 2,7,6
            stack = 1,3

            Queue = 7,6,5,4
            stack = 1,3,2

            Queue = 6,5,4
            stack = 1,3,2,7

            Queue = 5,4
            stack = 1,3,2,7,6

            Queue = 4
            stack = 1,3,2,7,6,5

            Queue = empty
            stack = 1,3,2,7,6,5,4

            pop elements from stack and that will be your Reverse Level Order.
            4,5,6,7,2,3,1

*/
public class _0_1LevelOrderTraversalInReverseOrder {
}
