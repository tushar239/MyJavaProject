package algorithms.crackingcodinginterviewbook._5recursion_and_dynamic_programming;

import java.util.ArrayList;
import java.util.List;

/*

0/1 KnapSack Problem:
You are given a KnapSack that can hold fixed weight W.
There are n items with some weight and profit.
Unlike to CoinsChange problem, you can put 0 or 1 count of any item in the KnapSack.
You need to choose the items in your KnapSack in such a way that you get max profit.


If you could have put more than 1 count of the same item in a backpack, formula for the algorithm could be a bit different.
It will be described below.


This is a very important example to understand Dynamic Programming.
Watch
0-1 Knapsack Problem (Brute-Force and Top-Bottom Dynamic Programming).mp4
0-1 Knapsack Problem (Bottom-Up Dynamic Programming) - Part 1.mp4
0-1 Knapsack Problem (Bottom-Up Dynamic Programming) - Part 2.mp4


Remember:
In the matrix, items should be in the sorted order of weight. This is the primary condition for any Bottom-Up approach.
Both dimensions must have sorted values to form the matrix.

You need to have one additional row and col (0th row and 0th col).
It is easy to fill up 0th row most of the time, but to fill up 0th col (which will be the base condition of the code), you sometimes need to wait till you reach to some point in filling up the matrix (See CoinsChange.java).
In your Bottom-Up code, you can root_to_leaf_problems_hard code the values for these additional row and col.
Value of additional col becomes a base condition for Brute-Force code.


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

As you the value in the cell depends on the value in other cells. If you are using recursion (Brute-Force), you will end up calculating the value for same item and knapsack holding weight multiple times.
And so, it becomes a perfect candidate for Dynamic Programming.


To me personally, it is a lot easier to think knapsack kind of problems with Bottom-Up Dynamic approach directly, rather than Brute-Force(followed by Top-Down Dynamic Approach).
*/
public class _0KnapSack {

    public static void main(String[] args) {
        Item item1 = new Item(1, 1);
        Item item2 = new Item(2, 32);
        Item item3 = new Item(1, 33);
        Item item4 = new Item(4, 34);

        // keeping items in sorted order by profit descending
        Item[] items = {item4, item3, item2, item1};

        //int knapSackWeight = 5;
        int knapSackWeight = 4;

        List<Item> itemsInKnapSackWithMaxProfit = greedy_way(items, 0, items.length - 1, knapSackWeight);
        System.out.println("Items in knapsack will possible highest profit using greedy algorithm: " + itemsInKnapSackWithMaxProfit);

        int totalProfit = 0;
        for (Item item : itemsInKnapSackWithMaxProfit) {
            totalProfit += item.profit;
        }
        System.out.println("Total Profit: " + totalProfit);

    }


    /*
    As this is a greedy algorithm where it needs to pick the item with highest profit first.
    So, items should be sorted by profit descending before you start a greedy algorithm.


    As you see Greedy Algorithms are very easy to write, but they may not provide the most optimal result.
    As you see an ideal result will be picking up item 1,2,3 for knapsack weight holding capacity=4. That will give max profit of 66, but greedy algorithm picked up item4 only that gave max profit of 34.

    In certain cases, it may give most optimal result also. e.g. if you change knapsack weight holding capacity=5, above example will give max possible profit.
     */
    private static List<Item> greedy_way(Item[] sortedByProfitItems, int startIndex, int endIndex, int knapSackWeight) {
        List<Item> knapSackItems = new ArrayList<>();

        if (sortedByProfitItems == null || sortedByProfitItems.length == 0) {
            return knapSackItems;
        }

        if (startIndex > endIndex) {
            return knapSackItems;
        }

        if (knapSackWeight <= 0) {
            return knapSackItems;
        }

        Item firstItem = sortedByProfitItems[startIndex];

        if (firstItem.weight > knapSackWeight) {
            List<Item> knapSackItemsFromRemainingItems = greedy_way(sortedByProfitItems, startIndex + 1, endIndex, knapSackWeight);
            knapSackItems.addAll(knapSackItemsFromRemainingItems);
            return knapSackItems;
        } else if (firstItem.weight == knapSackWeight) {
            knapSackItems.add(firstItem);
            return knapSackItems;
        } else {
            knapSackItems.add(firstItem);
            int remainingWeightInKnapSack = knapSackWeight - firstItem.weight;
            List<Item> knapSackItemsFromRemainingItems = greedy_way(sortedByProfitItems, startIndex + 1, endIndex, remainingWeightInKnapSack);
            knapSackItems.addAll(knapSackItemsFromRemainingItems);
            return knapSackItems;
        }
    }

    static class Item {
        private int weight;
        private int profit;

        public Item(int weight, int profit) {
            this.weight = weight;
            this.profit = profit;
        }

        public int getWeight() {
            return weight;
        }

        public int getProfit() {
            return profit;
        }

        @Override
        public String toString() {
            return "Item{" +
                    "weight=" + weight +
                    ", profit=" + profit +
                    '}';
        }
    }
}
