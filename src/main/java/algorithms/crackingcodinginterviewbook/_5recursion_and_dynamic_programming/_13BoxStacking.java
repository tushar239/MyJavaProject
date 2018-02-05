package algorithms.crackingcodinginterviewbook._5recursion_and_dynamic_programming;
/*
pg 367 of Cracking Coding Interview book.

Stack of Boxes:
You have a stack of n boxes with widths w1, heights h1, and depths d1.
The boxes cannot be rotated and can only be stacked on top of one another if each box in the stack is strctly
larger than the box above it in width,height and depth.
Implement a method to compute the height of the tallest possible stack.
The height of a stack is the sum of the heights of each box.


Algorithm Type 1:

    In this algorithm, there is a restriction that says that "all 3 dimensions of a top box should be smaller than a box below it".

    You can solve this problem by sorting the boxes partially. Partially means just reserve one dimension into account (e.g. height).

    If boxes are sorted as per heights in descending order at least,
    e.g.
            L   W   H
    Box1    3   4   5
    Box2    3   5   4
    Box3    5   4   3
    Box4    2   3   2
    Box5    3   2   1

    then you are sure that Box can't go on top of Box2 or Box3 or Box4 or Box5.
    You can blindly consider Box1 as a bottom box during first recursion and remaining boxes on top of it. And then consider Box2 as a bottom box and Box 3 to 5 boxes on top of that.
    If you don't sort by height(or any other dimension), you have to compare each box with all other boxes. Time complexity will be higher.

    This is how this algorithm should be written:


        int maxHeight = 0;

        for(int i=0; i < boxes.length; i++) {
            Box bottomBox = boxes[i]; // Consider Box1 as a bottom box.

            int maxHeightFromASetOfStacksConsideringAParticularBoxAsABottomBox = bottomBox.H; // If no other boxes can go on top of Box1, then there will be only one box in a stack.

            // This for loop will try to create a stacks from Box1 + all other possibilities from remaining boxes.
            // It will keep track of max height.
            for(int j=i+1; j < boxes.length; j++) {

                Check whether you can put Box2 on top of Box1 by checking its L and W (H is already taken care of by sorting all the boxes beforehand by H).
                If you can, then get the max height of the stack created from remaining boxes (Box 2 to 5) and add Box1's height to that. If this total height is bigger than maxHeightFromASetOfStacksConsideringAParticularBoxAsABottomBox, then update maxHeightFromASetOfStacksConsideringAParticularBoxAsABottomBox.

                Then continue doing the same for in between Box1 and Box3, Box1 and Box4, Box1 and Box5.
            }

            if(maxHeightFromASetOfStacksConsideringAParticularBoxAsABottomBox > maxHeight)
                maxHeight = maxHeightFromASetOfStacksConsideringAParticularBoxAsABottomBox;
        }

        return maxHeight;


        This code needs to be recursive because inside inner for loop, you need to use the same code to get the max height from stacks created using remaining boxes.

        int getHeightOfTallestPossibleStack(Box[] boxes, int bottomBoxIndex, int boxesEndIndex) {

            ..... exit conditions ...
            if(bottomBoxIndex > boxesEndIndex) return 0;

            // Considering Box1 as a bottom box
            Box bottomBox1 = boxes[bottomBoxIndex];

            // Finding tallest stack's height by considering Box1 as bottom box in all these stacks
            int maxHeightOfStackFromStacksCreatedUsingBoxes2To5 = 0;

            for(int j=bottomBoxIndex+1; j < boxes.length; j++) {

                if(boxes[j] can be placed over bottomBox1) {

                    int height = maxHeightOfStackFromStacksConsidering_Box1_AsBottomBox + getHeightOfTallestPossibleStack(boxes, j, boxesEndIndex);

                    if(height > maxHeightOfStackFromStacksCreatedUsingBoxes2To5) {
                        maxHeightOfStackFromStacksCreatedUsingBoxes2To5 = height;
                    }
                }

            }

            int maxHeightOfStackFromStacksConsidering_Box1_AsBottomBox = bottomBox.H + maxHeightOfStackFromStacksCreatedUsingBoxes2To5;


            // height of tallest stack consider other boxes as bottom boxes
            int maxHeightFromRemainingCombinationsOfBoxes = getHeightOfTallestPossibleStack(boxes, bottomBoxIndex + 1, boxesEndIndex);

            if(maxHeightFromRemainingCombinationsOfBoxes > maxHeightOfStackFromStacksConsidering_Box1_AsBottomBox) {
                return maxHeightFromRemainingCombinationsOfBoxes;

            return maxHeightOfStackFromStacksConsidering_Box1_AsBottomBox;
        }


Algorithm Type 2:

    Above algorithm says that you cannot rotate the boxes.

    There is another possible algorithm that lets you rotate the boxes.

        Watch
        'Box Stacking Problem Solution Using Dynamic Programming (GeeksforGeeks).mp4'
        Or
        'Box Stacking Dynamic Programming.mp4'

        In that case you need to do one additional step.
        You need to create 3 boxes out of one by rotating their width, height and depth.
        e.g. if there is a box of
        L   W   H
        5   4   3

        You can rotate this box in two ways.
        - length becomes height and width becomes length
        - width becomes height and height becomes width
        you need to create two more boxes from it. so, total 3.

        L   W   H
        5   4   3
        4   3   5
        5   3   4

        So likewise, if you have 4 boxes, you can create 24 boxes from them.

        If the problem has a restriction that "height and width of top box cannot be bigger than a box under it",
        then you need to sort all 24 boxes by their areas (length * width) using quick sort with O(n log n).
        Remember, this sorting does not decide the final stack. Sorting is based on area. There is a possibility that Box1's area can be smaller than Box2, but its width can be bigger than Box1. In this case, even though Box's area is smaller, it cannot be stacked on top of Box2.


*/

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class _13BoxStacking {

    public static void main(String[] args) {
        Box box1 = new Box(3, 4, 7);
        Box box2 = new Box(2, 3, 6);
        Box box3 = new Box(1, 2, 5);
        Box box4 = new Box(1, 2, 5);// there can be a box with the same L, W, H

        Box[] boxes = {box2, box1, box3, box4};

        List<Box> boxesList = Arrays.asList(boxes);
        Collections.sort(boxesList, new BoxHeightComparator());
        boxes = boxesList.toArray(new Box[0]);


        int heightOfTallestStackOfBoxes = getHeightOfTallestPossibleStack_Brute_Force(boxes, 0, boxes.length - 1);
        System.out.println(heightOfTallestStackOfBoxes);
    }

    static class BoxHeightComparator implements Comparator<Box> {

        @Override
        public int compare(Box box1, Box box2) {
            //return box1.H - box2.H;
            return box2.H - box1.H; // we want boxes in descending order
        }

    }

    static class Box {
        private int L, W, H;

        public Box(int l, int w, int h) {
            L = l;
            W = w;
            H = h;
        }

        public int getL() {
            return L;
        }

        public int getW() {
            return W;
        }

        public int getH() {
            return H;
        }

        @Override
        public String toString() {
            return "Box{" +
                    "L=" + L +
                    ", W=" + W +
                    ", H=" + H +
                    '}';
        }

        public boolean canBePlacedOver(Box bottomBox) {
            return (this.L < bottomBox.L && this.W < bottomBox.W && this.H < bottomBox.H);
        }
    }


    /*
                    m([b1,b2,b3,b4,b5])
                            |
                    b1+m([b2,b3,b4,b5]) -------------------------
                            |                                   |
                    b2+m([b3,b4,b5])----------             b2+m([b3,b4,b5])
                            |                |                   |
                    b3+m([b4,b5])----------                b3+m([b4,b5])
                            |             |                      |
                      b4+m([b5])                            b4+m([b5])
                            |                                   |
                         b5+m([])                            b5+m([])

        As you see, method is called with same parameters multiple times.
        You can use Top-Bottom Dynamic Approach to reduce time complexity.

        In memoization array/map, you need to have index/key as parameters that are changing in recursive call. Here, bottomBoxIndex is changing. So, you can have memoization array with box indices.
     */
    private static int getHeightOfTallestPossibleStack_Brute_Force(Box[] boxes, int bottomBoxIndex, int boxesEndIndex) {

        if (boxes == null || boxes.length == 0) return 0;

        if (bottomBoxIndex > boxesEndIndex) return 0;

        // Considering Box1 as a bottom box
        Box bottomBox1 = boxes[bottomBoxIndex];

        // Finding tallest stack's height by considering Box1 as bottom box in all these stacks
        int maxHeightOfStackFromStacksCreatedUsingBoxes2To5 = 0;

        for (int j = bottomBoxIndex + 1; j < boxes.length; j++) {

            if (boxes[j].canBePlacedOver(bottomBox1)) {

                int height = getHeightOfTallestPossibleStack_Brute_Force(boxes, j, boxesEndIndex);

                if (height > maxHeightOfStackFromStacksCreatedUsingBoxes2To5) {
                    maxHeightOfStackFromStacksCreatedUsingBoxes2To5 = height;
                }
            }
        }

        int maxHeightOfStackFromStacksConsidering_Box1_AsBottomBox = bottomBox1.H + maxHeightOfStackFromStacksCreatedUsingBoxes2To5;

        // height of tallest stack consider other boxes as bottom boxes
        int maxHeightFromRemainingCombinationsOfBoxes = getHeightOfTallestPossibleStack_Brute_Force(boxes, bottomBoxIndex + 1, boxesEndIndex);

        // comparing height of tallest stack by considering Box1 as a bottom box and height of tallest stack by considering other boxes as bottom boxes.
        if (maxHeightFromRemainingCombinationsOfBoxes > maxHeightOfStackFromStacksConsidering_Box1_AsBottomBox) {
            return maxHeightFromRemainingCombinationsOfBoxes;
        }

        return maxHeightOfStackFromStacksConsidering_Box1_AsBottomBox;
    }
}