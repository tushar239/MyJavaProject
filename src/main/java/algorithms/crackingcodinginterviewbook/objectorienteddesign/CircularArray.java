package algorithms.crackingcodinginterviewbook.objectorienteddesign;

// p.g. 330 of Cracking Coding Interview
/*
    Implement an Array that can be rotated (right shifted).

    int[] array = {0, 1, 2, 3, 4, 5, 6};
    right shift 2 times, {5, 6, 0, 1, 2, 3, 4};
    array[0] is now 5 instead of 0.

    Solution 1: every time you right shift an array, move all elements.
                It is not so efficient

    Solution 2: in right shift, basically just original start index of an array is moving.
                So, you can just maintain a pointer that keeps moving.

                headIndex=0


                right shift 2 times.
                    headIndex = array.length - 2
                    if(headIndex < 0) {
                        headIndex = headIndex + array.length
                    }

                array.get(index) e.g. get(0)
                    actualIndex = headIndex+index;
                    if(actualIndex>=array.length)
                        actualIndex = array.length-actualIndex

e.g. array=[1,2,3]
right shift 1
virtually array=[3,2,1], headIndex should point to 3.

get(1)
    actualIndex= headIndex(2)+1 = 3, so a[3] will reserve you out of array, so change actualIndex=array.length(3)-actualIndex(3)=0. a[0]=1 is the right result.


 */
public class CircularArray {

    public static void main(String[] args) {
        int[] array = {0, 1, 2, 3, 4, 5, 6};

        MyArray myArray = new MyArray(array);
        myArray.rotate(2);

        System.out.println(myArray.get(0));
        myArray.rotate(1);

        System.out.println(myArray.get(0));
        myArray.rotate(1);
        System.out.println(myArray.get(0));

        myArray.rotate(1);
        System.out.println(myArray.get(0));

        myArray.rotate(1);
        System.out.println(myArray.get(0));

        myArray.rotate(1);
        System.out.println(myArray.get(0));

        myArray.rotate(1);
        System.out.println(myArray.get(0));

        myArray.rotate(1);
        System.out.println(myArray.get(0));


    }

    static class MyArray {


        private final int[] array;
        private int headIndex = 0;

        public MyArray(int[] array) {
            this.array = array;
        }

        private void rotate(int timesRotate) {
            headIndex = headIndex - timesRotate;
            if (headIndex < 0) {
                headIndex = array.length + headIndex;
            }
        }

        private int get(int index) {
            int actualIndex = headIndex + index;
            if (actualIndex >= array.length) {
                actualIndex = actualIndex - array.length;
            }
            return array[actualIndex];
        }
    }
}
