package algorithms._1array_stack_queue.geeksforgeeks.pointers_sorting_stack_queue_string.segregation_algorithms.hard;

/*
    Sort an array of 0s, 1s and 2s (Dutch National Flag Algorithm)

    https://www.geeksforgeeks.org/sort-an-array-of-0s-1s-and-2s/

    Dutch National Flag Algorithm
    http://users.monash.edu/~lloyd/tildeAlgDS/Sort/Flag/

    three pointers
    put LO at the place where there is first non-0 element from left.
    put HI at the place where there is first non-2 element from right.
    put MID at the place where there is first non-1 element from left after LO.

    while (MID <= HI)

        There can be two possible values of A[MID] = 0 or 2

        If it is 0,

            Exchange it with A[LO].
            After exchange, if A[MID]==1, then find another non-1 for MID
                            else do not change MID
            Find another non-0 for LO.

        If it is 2,

            Exchange it with A[HI].
            After exchange, if A[MID]==1, then find another non-1 for MID
                            else do not change MID
            Find another non-2 for HI.

        Try
        0,  0,  0,  1,  2,  0,  1,  1,  2,  2,  1,  2

        0,  0,  0,  1,  2,  0,  1,  1,  2,  2,  1,  2
                    LO  MID                     HI

        exchange MID and HI
        0,  0,  0,  1,  1,  0,  1,  1,  2,  2,  2,  2
                    LO  MID                     HI

        search non-2 for HI
        MID is now 1, so search non-1 for it. If MID is non-1, then don't change MID's position.

        0,  0,  0,  1,  1,  0,  1,  1,  2,  2,  2,  2
                    LO      MID     HI

        exchange LO and MID
        0,  0,  0,  0,  1,  1,  1,  1,  2,  2,  2,  2
                    LO      MID     HI


        search non-0 for LO
        MID is now 1, so search non-1 for it. If MID is non-1, then don't change MID's position.

        0,  0,  0,  0,  1,  1,  1,  1,  2,  2,  2,  2
                        LO          HI  MID

        MID crossed HI. So, stop here.
*/
public class _1Segregate0s1sAnd2s_DutchNationalFlag_Algorithm {
}
