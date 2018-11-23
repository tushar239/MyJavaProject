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

2) Graph traversal approach

   When to use BFS?

   a) You need find shortest distance between two cells
      e.g. ShortestDistanceBetweenTwoCellsInMatrixOrGrid.java

   b) You need to find path between two cells
      e.g. FindWhetherThereIsAPathBetweenTwoCellsOfTheMatrix.java

   c) Find/Process chunk of cells provided that these chunks should not be overlapping or in other words visited cells should not be visited again.
      e.g. FindNumberOfIslands.java
           MinimumTimeRequiredToRotAllOranges.java

      CAUTION:
      If you need to find/process chunks of cells that can be overlapping, then you cannot use BFS. You have to use Recursion+Dynamic Programming.

3) Dynamic Programming

   If you need to find/process chunks of cells that can be overlapping, then you cannot use BFS. You have to use Recursion+Dynamic Programming.

   e.g. GoldMineProblem.java
        LargestSquareOf1s.java




*/


public class MatrixFundamentals {
}
