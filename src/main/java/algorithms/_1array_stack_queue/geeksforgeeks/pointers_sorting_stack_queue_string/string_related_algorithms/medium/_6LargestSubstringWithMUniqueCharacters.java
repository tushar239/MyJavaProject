package algorithms._1array_stack_queue.geeksforgeeks.pointers_sorting_stack_queue_string.string_related_algorithms.medium;

/*
    Longest Substring With M Unique Characters

    https://www.youtube.com/watch?v=8AQra0p_HmI

    k a r a p p a

    if m=2, then longest substring with 2 unique chars is 'appa'. 'a' and 'p' are those unique characters.
    if m=3, then longest substring with 2 unique chars is 'arappa'. 'a','p' and 'r' are those unique characters.


    Brute-Force solution:

        It takes O(n^2).

        create aux array of 256 chars.

        traverse a string and keep updating this array with count for visited character and also keep track of total number of unique chars so far.
        Once total number of unique characters > limit, stop there. you got one possible substring with m unique characters.

        e.g. k a r a p p a    and    m=2

        -
         startOfSubstring
            |
            k   a   r   a   p   p   a
            |
            i-> i->i

            visited[k]=1, totalUniqueChars=1
            visited[a]=1, totalUniqueChars=2
            visited[r]=1, totalUniqueChars=3

            at this point totalUniqueChars > m. So, so far your longest substring with m=2 is "ka"

        -    reset visited array to 0s.

        -    startOfSubstring
                |
            k   a   r   a   p   p   a
                |
                i-> i-> i-> i

            at this point totalUniqueChars > m. So, so far your longest substring with m=2 is "ara"

        -    reset visited array to 0s.

        -       startOfSubstring
                    |
            k   a   r   a   p   p   a
                    |
                    i-> i-> i

            at this point totalUniqueChars > m. So, so far your longest substring with m=2 is still "ara"


        and so on.

        This will take O(n^2).



    Better Approach:

        It takes O(n).

        It is almost same as Brute-Force, just do not reset 'visited' array to 0s when you move 'startOfSubstring' to next char. Instead, just reduce the count of prev 'startOfSubstring' char and 'totalUniqueChars'

        -
        startOfSubstring
            |
            k   a   r   a   p   p   a
            |
            i-> i->i

            visited[k]=1, totalUniqueChars=1
            visited[a]=1, totalUniqueChars=2
            visited[r]=1, totalUniqueChars=3

            at this point totalUniqueChars > m. So, so far your longest substring with m=2 is "ka".


        -
            visited[k]=0 --- reduce count by 1
            visited[a]=1
            visited[r]=1

            totalUniqueChars=2

        -
            startOfSubstring
                |
            k   a   r   a   p   p   a
                    |
                    i

        -
            startOfSubstring
                |
            k   a   r   a   p   p   a
                        |
                        i

            visited[k]=0
            visited[a]=2
            visited[r]=1


            totalUniqueChars=2

        -
            startOfSubstring
                |
            k   a   r   a   p   p   a
                            |
                            i

            visited[k]=0
            visited[a]=2
            visited[r]=1
            visited[p]=1


            totalUniqueChars=3

        -
            visited[k]=0
            visited[a]=1  --- reduce count by 1
            visited[r]=1
            visited[p]=1


            after reducing the count of 'a' by 1, its count is not yet 0, it means that totalUniqueChars is still 3.

                    startOfSubstring
                        |
            k   a   r   a   p   p   a
                            |
                            i
            visited[k]=0
            visited[a]=1
            visited[r]=0  --- reduce count by 1
            visited[p]=1


            totalUniqueChars=2

        -
                    startOfSubstring
                        |
            k   a   r   a   p   p   a
                                |
                                i
            visited[k]=0
            visited[a]=1
            visited[r]=0
            visited[p]=2

            totalUniqueChars=2

        -
                    startOfSubstring
                        |
            k   a   r   a   p   p   a
                                    |
                                    i
            visited[k]=0
            visited[a]=2
            visited[r]=0
            visited[p]=2

            totalUniqueChars=2

            your longest substring with m=2 is "appa".

*/
public class _6LargestSubstringWithMUniqueCharacters {
}
