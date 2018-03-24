package algorithms._1array.geeksforgeeks.dynamic_programming;

import java.util.Map;

/*
Cutting a Rod:
https://www.geeksforgeeks.org/dynamic-programming-set-13-cutting-a-rod/

Given a rod of length n inches and an array of prices that contains prices of all pieces of size smaller than n.
Determine the maximum value obtainable by cutting up the rod and selling the pieces.

For example,

    If length of the rod is 8 and the values of different pieces are given as following, then the maximum obtainable value is 22 (by cutting in two pieces of lengths 2 and 6)


    length   | 1   2   3   4   5   6   7   8
    --------------------------------------------
    price    | 1   5   8   9  10  17  17  20


    And if the prices are as following, then the maximum obtainable value is 24 (by cutting in eight pieces of length 1)

    length   | 1   2   3   4   5   6   7   8
    --------------------------------------------
    price    | 3   5   8   9  10  17  17  20


IMPORTANT:
    This problem a bit different than FindIfASubsetWithGivenSumExistsInGivenArray.java
    Difference is that you can use more than one quantities of the same element to get the max result in this problem.
    So, there is a hard solution for this and an easy solution for this. Read javadoc of findMax_easy_brute_force method.
*/
public class _3RodCuttingProblem {

    public static void main(String[] args) {
        {
            int[] prices = {1, 5, 8, 9, 10, 17, 17, 20};
            int[] pieceLengths = {1, 2, 3, 4, 5, 6, 7, 8};
            int expectedRodLength = 8;

            int max_easier = findMax_easy_brute_force(prices, pieceLengths, 0, pieceLengths.length - 1, expectedRodLength);
            System.out.println(max_easier);

/*
            Map<Integer, Integer> map = new HashMap<>();
            int maxPrice = findMax_with_included_pieces_printed_brute_force(prices, pieceLengths, 0, prices.length - 1, expectedRodLength, map);
            System.out.println(maxPrice);//24
            for (Integer piece : map.keySet()) {
                System.out.println(map.get(piece) + " number of " + piece + " are used to get max price");
            }
*/
        }

        {
            int[] prices = {3, 5, 8, 9, 10, 17, 17, 20};
            int[] pieceLengths = {1, 2, 3, 4, 5, 6, 7, 8};
            int expectedRodLength = 8;

            int max_easier = findMax_easy_brute_force(prices, pieceLengths, 0, pieceLengths.length - 1, expectedRodLength);
            System.out.println(max_easier);//24

          /*  Map<Integer, Integer> map = new HashMap<>();
            int maxPrice = findMax_with_included_pieces_printed_brute_force(prices, pieceLengths, 0, prices.length - 1, expectedRodLength, map);
            System.out.println(maxPrice);//24
            for (Integer piece : map.keySet()) {
                System.out.println(map.get(piece) + " number of " + piece + " are used to get max price");
            }*/

        }

    }

    @SuppressWarnings("Duplicates")
    private static int findMax_hard_brute_force(int[] prices, int[] pieceLengths, int start, int end, int expectedRodLength) {

        // exit condition
        if (expectedRodLength <= 0) return 0;

        // reducing the problem by 1. Selecting last pieceLength and its price.
        int pieceLength = pieceLengths[end];
        int priceOfSelectedPiece = prices[end];

        // exit condition
        if (end == start) {//there is only one possible pieceLength possible of the rod
            if (pieceLength == expectedRodLength) {
                System.out.println("included pieceLength a: " + end + " for " + 1 + " times.");
                return priceOfSelectedPiece;
            }
            if (pieceLength < expectedRodLength) {

                if ((expectedRodLength % pieceLength) != 0) {
                    return 0;
                }

                int num = expectedRodLength / pieceLength;
                System.out.println("included pieceLength b: " + end + " for " + num + " times.");
                return num * priceOfSelectedPiece;
            }
            return 0;
        }

        if (pieceLength == expectedRodLength) {
            return Math.max(prices[end], findMax_hard_brute_force(prices, pieceLengths, start, end - 1, expectedRodLength));
        }

        if (pieceLength > expectedRodLength) {
            return findMax_hard_brute_force(prices, pieceLengths, start, end - 1, expectedRodLength);
        }

        int num = expectedRodLength / pieceLength;

        int max = Integer.MIN_VALUE;

        // price of 0 number of selected pieceLength + price of selecting other pieceLengths to make expectedRodLength
        // price of 1 number of selected pieceLength + price of selecting other pieceLengths to make expectedRodLength - (1 * pieceLength size)
        // price of 2 number of selected pieceLength + price of selecting other pieceLengths to make expectedRodLength - (2 * pieceLength size)
        // price of 3 number of selected pieceLength + price of selecting other pieceLengths to make expectedRodLength - (3 * pieceLength size)
        // ...
        // find max from all these combinations
        for (int i = 0; i <= num; i++) {

            max = Math.max(max,
                    (i * prices[end]) +
                            findMax_hard_brute_force(prices, pieceLengths, start, end - 1, expectedRodLength - (pieceLength * i))
            );
        }

        return max;
    }

    /*
    You need to remember one important concept when you there is a requirement saying you can use more than one quantity of same element to get the result.
    Here, you can use multiple pieces of same length to make up expectedRodLength.

    int[] pieceLengths = {1, 2, 3, 4, 5, 6, 7, 8};
    int[] prices = {3, 5, 8, 9, 10, 17, 17, 20};
    expectedRodLength=16


            _________________________  _____
            | 1,  2,  3,  4, 5, 6, 7|  | 8 |
            -------------------------  -----

    Here,
          you can use 2 pieces of size 8 to get expected size 16
            OR
          you can use 1 pieces of size 8 to get expected size 16 + remaining array to get remaining expected size (16-8)
            OR
          you can use remaining array to get total expected size 16

    Whichever is maximum out of these 3, you can keep the price of that combination.

    When you see that you can use more than 1 quantities of the same element, your remaining array includes that element.
    So, for second part of 2nd condition, instead of

        for(int i=0; i <= expectedSize/element; i++) // adding same element multiple times
            max = Math.max(max, prices[elementIndex] * i + findMaxPrice(..., end-1, expectedSize - (element * i))

        you can simply write

            max = Math.max(max, prices[elementIndex]     + findMaxPrice(..., end, expectedSize - element)



    Time Complexity is exponential O(2^n) for Brute-Force approach.

    Below method is using Brute Force, but it can easily be converted to Dynamic Programming by memoizing the result of method call for end+expectedRodLength as key.

    Using Dynamic approach, Time Complexity can be reduced to O(priceLengths.length * expectedRodLength). This you can easily see, if you draw 2-D matrix (for bottom-up approach).
    https://www.youtube.com/watch?v=zFe-SX7jzDs

    */
    private static int findMax_easy_brute_force(int[] prices, int[] pieceLengths, int start, int end, int expectedRodLength) {

        // exit condition
        if (expectedRodLength <= 0) return 0;

        // reducing the problem by 1. Selecting last pieceLength and its price.
        int pieceLength = pieceLengths[end];
        int priceOfSelectedPiece = prices[end];

        // exit condition
        // if there is only one possible piece, then you can use 1 or more quantities of that piece, if pieceLength <= expectedRodLength
        if (end == start) {//there is only one possible pieceLength possible of the rod
            if (pieceLength <= expectedRodLength) {
                if ((expectedRodLength % pieceLength) != 0) {
                    return 0;
                }

                int num = expectedRodLength / pieceLength;
                return num * priceOfSelectedPiece;
            }
            return 0;
        }

        if (pieceLength == expectedRodLength) {
            return Math.max(priceOfSelectedPiece, findMax_easy_brute_force(prices, pieceLengths, start, end - 1, expectedRodLength));
        }

        if (pieceLength > expectedRodLength) {
            return findMax_easy_brute_force(prices, pieceLengths, start, end - 1, expectedRodLength);
        }

        // important concept to remember (as mentioned in this method's javadoc) when you can use multiple quantities of the same piece
        return Math.max(priceOfSelectedPiece + findMax_easy_brute_force(prices, pieceLengths, start, end, expectedRodLength - pieceLength),
                findMax_easy_brute_force(prices, pieceLengths, start, end - 1, expectedRodLength));

    }

    // Once you use Dynamic Programming, map will have right numbers. Without Dynamic Programming, values in the map will be overwritten.
    @SuppressWarnings("Duplicates")
    private static int findMax_with_included_pieces_printed_brute_force(int[] prices, int[] pieceLengths, int start, int end, int expectedRodLength, Map<Integer, Integer> map) {

        // exit condition
        if (expectedRodLength <= 0) return 0;

        // reducing the problem by 1. Selecting last pieceLength and its price.
        int pieceLength = pieceLengths[end];
        int priceOfSelectedPiece = prices[end];

        // exit condition
        if (end == start) {//there is only one possible pieceLength possible of the rod
            if (pieceLength <= expectedRodLength) {
                if ((expectedRodLength % pieceLength) != 0) {
                    return 0;
                }

                int num = expectedRodLength / pieceLength;
                map.put(pieceLength, num);
                return num * priceOfSelectedPiece;
            }
            return 0;
        }

        if (pieceLength == expectedRodLength) {
            int maxPriceExcludingThisPiece = findMax_with_included_pieces_printed_brute_force(prices, pieceLengths, start, end - 1, expectedRodLength, map);
            if (priceOfSelectedPiece >= maxPriceExcludingThisPiece) {
                map.put(pieceLength, 1);
                return priceOfSelectedPiece;
            }
            return maxPriceExcludingThisPiece;
        }

        if (pieceLength > expectedRodLength) {
            return findMax_easy_brute_force(prices, pieceLengths, start, end - 1, expectedRodLength);
        }

        int maxPriceIncludingThisPiece = priceOfSelectedPiece + findMax_with_included_pieces_printed_brute_force(prices, pieceLengths, start, end, expectedRodLength - pieceLength, map);
        int maxPriceExcludingThisPiece = findMax_with_included_pieces_printed_brute_force(prices, pieceLengths, start, end - 1, expectedRodLength, map);

        if (maxPriceExcludingThisPiece >= maxPriceIncludingThisPiece) {
            return maxPriceExcludingThisPiece;
        }
        map.put(pieceLength, 1);

        return maxPriceIncludingThisPiece;
    }
}
