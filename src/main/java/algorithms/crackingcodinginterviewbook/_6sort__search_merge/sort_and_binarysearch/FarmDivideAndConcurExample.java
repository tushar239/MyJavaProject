package algorithms.crackingcodinginterviewbook._6sort__search_merge.sort_and_binarysearch;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Tushar Chokshi @ 11/23/17.
 *
 * Grokking Algorithms - Chapter 4's Divide and Concur concept of Farm example.
 *
 * Suppose you’re a farmer with a plot of land.
 * You want to divide this farm evenly into square plots. You want the plots to be as big as possible. So none of these will work.
 * How do you figure out the largest square size you can use for a plot of land?
 */
public class FarmDivideAndConcurExample {
    public static void main(String[] args) {
        Farm farm = new Farm(1680, 640);

        Farm smallestPossibleSquareOfFarm = getSmallestPossibleSquareOfFarm(farm);
        System.out.println(smallestPossibleSquareOfFarm);// Farm{length=80, width=80}
    }

    static class Farm {
        int length;
        int width;

        public Farm(int length, int width) {
            this.length = length;
            this.width = width;
        }

        public Farm[] divide() {
            List<Farm> smallerFarms = new LinkedList<>();

            int quotient, remainder = 0;
            if (length == width) {
                return smallerFarms.toArray(new Farm[0]);
            } else if (length > width) {
                quotient = length / width;
                remainder = length % width;

                for (int i = 0; i < quotient; i++) {
                    smallerFarms.add(new Farm(width, width));
                }
                if (remainder > 0) {
                    smallerFarms.add(new Farm(remainder, width));
                }

            } else {
                quotient = width / length;
                remainder = width % length;

                for (int i = 0; i < quotient; i++) {
                    smallerFarms.add(new Farm(length, length));
                }
                if (remainder > 0) {
                    smallerFarms.add(new Farm(length, remainder));
                }

            }

            return smallerFarms.toArray(new Farm[0]);
        }

        @Override
        public String toString() {
            return "Farm{" +
                    "length=" + length +
                    ", width=" + width +
                    '}';
        }
    }

    private static Farm getSmallestPossibleSquareOfFarm(Farm farm) {
        if (farm.length == farm.width) return farm;

        Farm[] smallerFarms = farm.divide();
        for (Farm smallerFarm : smallerFarms) {
            if (smallerFarm.length != smallerFarm.width) {
                return getSmallestPossibleSquareOfFarm(smallerFarm);
            }
        }
        return smallerFarms[0];
    }
}
