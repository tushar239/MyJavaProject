package algorithms._0Fundamentals;

/*

There are multiple types of matrix problems

1) Layered approach

   You need to do processing on cells in layers

    There are predefined steps for Layered approach.

    - Find total number of layers
    - loop through number of layers
        find rows and cols involved in a layer
        find total number of swaps possible in a particular layer
        Do the swaps


   e.g. MakeRowsAsColsAndViceAVersa.java
        RotateMatrix.java

2) When to use Graph traversal approach?

   When to use BFS?

   a) You need find shortest distance between two cells
      e.g. ShortestDistanceBetweenTwoCellsInMatrixOrGrid.java

   b) You need to find path between two cells
      e.g. FindWhetherThereIsAPathBetweenTwoCellsOfTheMatrix.java

   c) You need to Find/Process chunk of cells provided that these chunks should not be overlapping or in other words visited cells should not be visited again.
      e.g. FindNumberOfIslands.java
           MinimumTimeRequiredToRotAllOranges.java

      CAUTION:
      If you need to find/process chunks of cells that can be overlapping, then you cannot use BFS. You have to use Recursion+Dynamic Programming.

3) When to use Dynamic Programming?

   If you need to find/process chunks of cells that can be overlapping, then you CANNOT use BFS. You have to use Recursion+Dynamic Programming.

   e.g.
        Thoroughly understand
            MinCostPathInMatrix.java
            MinCostPathInMatrix_different_traversal_direction.java
            MinOddCostPathInMatrix.java --- very important to understand when to find values for each cell of top row

    GoldMineProblem.java, UniquePathsInAGridWithObstacles.java are very important to understand how to determine exit conditions and how to find the resulting path from memoized table.

    LargestSquareOf1s.java is a very important algorithm from the complexity perspective.

4) VERY IMPORTANT: Exit conditions for Recursive programs

    Range checking condition is mandatory

        if (!inRange(matrix, startCellX, startCellY)) {
            return ......;
        }

    If recursion is increasing/decreasing row/col, then you need to think about exit conditions for
        last row/first row  last col/first col.
        It may certainly happen that last cell of the row or col may require a special exit contion.

        e.g.
        GoldMineProblem.java
        UniquePathsInAGridWithObstacles.java

5) IMPORTANT: Relation between Traversal Directions and initiating cell of traversal

    Based on the traversal directions, you will choose whether to recurse by changing

        startCell value (e.g. MinCostPathInMatrix.java, MinCostPathInMatrix_different_traversal_direction.java, MinOddCostPathInMatrix.java)
        or
        endCell value  (e.g. MaxPathSumInMatrix.java)

    If you choose to recurse by changing

        - startCell's value,
              your top-down approach's memoization table will have final result in the startCell.

              When you convert this kind of top-down approach to bottom-up, you will start initializing the endCell (from top-down's exit condition this will be very clear)
              and then last row and/or last col, if needed.

              e.g. MinCostPathInMatrix.java, MinCostPathInMatrix_different_traversal_direction.java
                    MinOddCostPathInMatrix.java

        - endCell's value,
              your top-down approach's memoization table will have final result in the endCell.

              When you convert this kind of top-down approach to bottom-up, you will start initializing the startCell (from top-down's exit condition this will be very clear)
              and then and/or start row and/or start col

              e.g. MaxPathSumInMatrix.java


*/


public class MatrixFundamentals {
}
