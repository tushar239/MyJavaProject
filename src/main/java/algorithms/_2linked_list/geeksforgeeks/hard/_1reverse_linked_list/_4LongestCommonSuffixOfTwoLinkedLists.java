package algorithms._2linked_list.geeksforgeeks.hard._1reverse_linked_list;

/*
    Longest common suffix of two linked lists

    https://www.geeksforgeeks.org/longest-common-suffix-two-linked-lists/

    Input : list1 = w -> a -> l -> k -> i -> n -> g
            list2 = l -> i -> s -> t -> e -> n -> i -> n -> g
    Output :i -> n -> g


    Input : list1 = p -> a -> r -> t -> y
            list2 = p -> a -> r -> t -> y -> i -> n -> g
    Output :p -> a -> r -> t -> y
    (This example does not seem to be correct. 'p-a-r-t-y' is not a suffix of both the lists)

    Simple but complicated solution:

        Use auxiliary arrays to store linked lists. Then print longest commons suffix of two arrays.
        This is very complicated solution because for every single element in list1, you may need to traverse entire list2.


    The above solution requires extra space.

    Efficient and easy solution:

    Reverse of both linked lists.
    After reversing, we can easily find length of longest common prefix.
    Reversing again to get the original lists back.
    One important point here is, order of elements. We need to print nodes from n-th to end. We use the above found count and print nodes in required order using two pointer approach.

                     start   end
    reversed list 1 - g - n - i - k - l - a - w

    reversed list 2 - g - n - i - n - e - t - s - i - l

    after finding the positions of common prefix using start and end pointers, reverse the lists again

                   end      start
    w - a - l - k - i - n - g
    l - i - s - t - e - n - i - n - g

    Now print the nodes from end to start.
*/
public class _4LongestCommonSuffixOfTwoLinkedLists {
}
