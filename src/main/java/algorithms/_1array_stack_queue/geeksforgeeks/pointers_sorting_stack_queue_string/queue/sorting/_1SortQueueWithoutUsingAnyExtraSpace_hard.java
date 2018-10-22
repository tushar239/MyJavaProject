package algorithms._1array_stack_queue.geeksforgeeks.pointers_sorting_stack_queue_string.queue.sorting;
/*
    Sorting a Queue without extra space     (Sort a Queue in-place)

    https://www.geeksforgeeks.org/sorting-queue-without-extra-space/

    queue = 2 1 4 3


    Solution:

        1) Using two additional queues to sort a queue

            find min element from original queue and put it in another queue
            keep doing this step until original queue is empty.
            Another queue will have sorted elements.

        2)  Use recursion just like SortStackUsingRecursion.java
            e.g. queue = 3 9 2 7 1

            ele = 3
            sort(9,2,7,1) = 1,2,7,9

            separate elements less than 3 in one staging queue and greater than 3 in another staging queue
            stagingQueue1 = 1,2     stagingQueue2 = 7,9

            put stagingQueue1 + 3 + stagingQueue2 in original queue

        3) Without using any extra space

           Basic concept of sorting a queue in-place:

           - find a min from n elements
           - remove and add 0 elements to queue
           - add min element at the end of queue

           - find a min from n-1 elements
           - remove and add 1 element to queue
           - add min element at the end of queue

           - find a min from n-2 elements
           - remove and add 2 element to queue
           - add min element at the end of queue

           and so on...


           while finding a min, you need to keep min element and keep putting others back to queue at the end.

           e.g. Queue=2 1 4 3

           - find min from 2 1 4 3, whichever is not min, put that back in a queue

                min=1, queue= 2 4 3

                now, remove and add the elements from the queue soFarMinElementsFound-1 = 0 times.

                queue=2 4 3

                add min to queue

                queue=2 4 3 1

           - now find min from 2 4 3

               min=2, queue=4 3 1
                            3 1 4
                            1 4 3

                now, remove and add the elements from the queue soFarMinElementsFound-1 = 1 times.

                queue=4 3 1

                add min to queue

                queue=4 3 1 2

           and so on.



          Finding min:

              int findMin(Queue queue, int numberOfElementsToBeCompared) {

                  int min = queue.remove();

                  numberOfElementsToBeCompared--;

                  for(int i=0; i < numberOfElementsToBeCompared; i++) {

                        int removed = queue.remove();

                        if(removed < min) {
                            queue.add(min);
                            min = removed;
                        } else {
                            queue.add(removed);
                        }
                  }

                  return min;
              }


    NOTE: Stack can't be sorted using same approach. To Sort a stack, you have to use additional data structure.
          See 'SortStackUsingRecursion.java'


*/

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class _1SortQueueWithoutUsingAnyExtraSpace_hard {

    public static void main(String[] args) {
        Queue<Integer> queue = new LinkedBlockingQueue<>();
        queue.add(3);
        queue.add(3);
        queue.add(1);
        queue.add(2);
        queue.add(5);
        queue.add(4);
        queue.add(5);

        sort(queue);

        while (!queue.isEmpty()) {
            System.out.print(queue.remove() + ",");
        }
    }

    private static void sort(Queue<Integer> queue) {

        for (int i = 0; i < queue.size(); i++) {
            int min = findMin(queue, queue.size() - i);

            for (int j = 0; j < i; j++) {
                queue.add(queue.remove());
            }

            queue.add(min);// add min at the end of the queue

        }
    }

    private static Integer findMin(Queue<Integer> queue, int numberOfElementsToBeCompared) {

        Integer min = queue.remove();

        numberOfElementsToBeCompared--;

        for (int i = 0; i < numberOfElementsToBeCompared; i++) {

            int removed = queue.remove();

            if (removed < min) {
                queue.add(min);
                min = removed;
            } else {
                queue.add(removed); // put non-min element back to queue
            }
        }

        return min;
    }
}
