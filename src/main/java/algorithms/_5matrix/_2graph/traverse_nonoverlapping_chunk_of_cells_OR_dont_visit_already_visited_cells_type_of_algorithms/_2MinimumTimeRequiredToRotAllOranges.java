package algorithms._5matrix._2graph.traverse_nonoverlapping_chunk_of_cells_OR_dont_visit_already_visited_cells_type_of_algorithms;

/*
    Minimum time required to rot all oranges

    https://www.geeksforgeeks.org/minimum-time-required-so-that-all-oranges-become-rotten/

    Given a matrix of dimension m*n where each cell in the matrix can have values 0, 1 or 2 which has the following meaning:

    0: Empty cell
    1: Cells have fresh oranges
    2: Cells have rotten oranges

    So we have to determine what is the minimum time required so that all the oranges become rotten. A rotten orange at index [i,j] can rot other fresh orange at indexes [i-1,j], [i+1,j], [i,j-1], [i,j+1] (up, down, left and right). If it is impossible to rot every orange then simply return -1.

    Examples:

    Input:  arr[][C] = { {2, 1, 0, 2, 1},
                         {1, 0, 1, 2, 1},
                         {1, 0, 0, 2, 1}};
    Output:
    All oranges can become rotten in 2 time frames.


    Input:  arr[][C] = { {2, 1, 0, 2, 1},
                         {0, 0, 1, 2, 1},
                         {1, 0, 0, 2, 1}};
    Output:
    All oranges cannot be rotten.




    let's take this example:

    { {2, 1, 0, 2, 1},
      {1, 0, 1, 2, 1},
      {1, 0, 0, 2, 1}};

    First attempt:
      Find first rotten orange. It is at (0,0).
      At once, you can rot (0,1),(1,0),(2,0)

    { {2, 2, 0, 2, 1},
      {2, 0, 1, 2, 1},
      {2, 0, 0, 2, 1}};

     Second attempt:
        You need to find another rotten orange, but not from the once those were rotten (VISITED) during first attempt.
        Let's say you found (0,3).
        With these you can rote

    { {2, 2, 0, 2, 2},
      {2, 0, 2, 2, 2},
      {2, 0, 0, 2, 2}};

      All oranges are rotten in two attempts.


    IMPORTANT:
     If you see, there is a condition that says that choose a rotten orange that is not already rotten (VISITED) by previous attempts.
     So, you need to keep a list of visited oranges. This can be the perfect fit for BFS.
     If you don't use BFS, you have to modify the elements original matrix (from fresh-1 to rotten-2)

     Solution using BFS:
     Find any rotten UNVISITED orange (e.g. (0,0)) and put it in queue and visited list.
     Dequeue an orange and rot all fresh oranges around it (up,down,left,right) and put both fresh and rotten oranges in queue and visited list.
     Keep doing this process until queue is empty. When queue is empty, you one attempt will be over.

     Repeat above steps till all oranges(cells) are visited.

*/

public class _2MinimumTimeRequiredToRotAllOranges {
}
