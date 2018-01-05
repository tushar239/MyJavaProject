package algorithms._5recursion_and_dynamic_programming;

/*
    This is a very important example to understand Dynamic Programming.
    Watch
    0-1 Knapsack Problem (Brute-Force and Top-Bottom Dynamic Programming).mp4
    0-1 Knapsack Problem (Bottom-Up Dynamic Programming) - Part 1.mp4
    0-1 Knapsack Problem (Bottom-Up Dynamic Programming) - Part 2.mp4

                        weight that your knapsack can hold
value    weight     0       1       2       3           4       5       6       7

0           0       0       0       0       0           0       0       0       0

1           1       0

4           3       0                       |           <-----
                                            v                |
5           4       0                                 max(5, | )

7           5       0                                            <--             <---------
                                                                   |                      |
                                                                   |-------------------   |
4           2       0                                                          max(4+ |,  | )
^                                                                                  |
|                                                                                  |
------------------------------------------------------------------------------------

You need to put value of an item in the cells by considering their weights.
You start filling up the values from cell(0,0), but first think how can you determine the value of last cell(item with weight=2, knapsack holding weight=7).

If (item weight > weight that knapsack can hold) then you can't put that item in the knapsack. You just need to use the value of a right on the top of it cell(prev row, knapsack holding weight=7).
It means that you need to fill up your knapsack with remaining prev items.

If (item weight == weight that knapsack can hold) then you have two options based on what provides the max value.
Either you put only that item in knapsack OR use all prev items to fill up your knapsack.

If (item weight < weight that knapsack can hold) then you have two options based on what provides the max value.
Either you put current item + prev items for remaining weight after putting current item OR use all prev items to fill up your knapsack.

    Here, item weight (2) > weight that knapsack can hold (7)
    So value of last cell = max (value of last item +
                                 cell(item with weight 5, remaining weight in knapsack (7-2=5) with prev items),
                                 cell(item with weight 5, knapsack holding weight 7)
                            )

    Why can't you put multiple quantities of the same item, for the last condition?
    That's the condition imposed by the problem.
    If we could put multiple quantities of the same item, then
    value of last cell = max (value of last item +
                              cell(item with weight 5, remaining weight in knapsack (7-2=5) with prev+current items that is value from a cell(item with weight 5, remaining weight in knapsack 5)),

                              value of last item +
                              cell(item with weight 5, remaining weight in knapsack (7-2=5) with prev items),

                              cell(item with weight 5, knapsack holding weight 7)
                            )

*/
public class _0KnapSack {
}
