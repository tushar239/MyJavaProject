package algorithms._1array_stack_queue.geeksforgeeks.pointers_sorting_stack_queue_string.string_related_algorithms.hard;

/*
    Length of the longest substring without repeating characters

    https://www.geeksforgeeks.org/length-of-the-longest-substring-without-repeating-characters/


    This algorithm has no relation with LCS, LIS, LPS etc algorithms.
    This algorithm is called LNRCS (Longest Non-Repeating Character Substring)


    https://www.youtube.com/watch?v=8FDMiWQijpo

        i ->i
    0   1   2   3   4   5   6   7   8   9   10  11  12

    G   E   E   K   S   F   O   R   G   E   E   K   S
    |
startOfSubstring

    visited[G]=0
    visited[E]=1

    right now, i is is at index=2
    you found that E has already visited and its last visited index >= start.
    so, i-startOfSubstring becomes max length of NRCS so far.
    and your startOfSubstring moves to index=2

    visited[E]=2

                i-> i-> i-> i-> i-> i-> i
    0   1   2   3   4   5   6   7   8   9   10  11  12

    G   E   E   K   S   F   O   R   G   E   E   K   S
            |
       startOfSubstring


    visited[K]=3
    visited[S]=4
    visited[F]=5
    visited[O]=6
    visited[R]=7

    G is already visited at index 0, but that 0 < startOfSubstring. So, G is not repeating in current sustring.

    visited[G]=8

    E is already visited at index 1. 1 == startOfSubstring. So, your current substring should end at index=8.
    Max NRCS value is updated to i-startOfSubstring = 9-2 = 7
    and new substring starts from 9

                                            i
    0   1   2   3   4   5   6   7   8   9   10  11  12

    G   E   E   K   S   F   O   R   G   E   E   K   S
                                        |
                                    startOfSubstring


    and so on.
*/
public class _1LNRCS_NonRepeatingLongestSubstring {



}
