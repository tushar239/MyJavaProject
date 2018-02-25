package algorithms.interviewbit.array.kadane_algorithm;

public class _2FlipElementsInArrayToGetMaximum1s {

    public static void main(String[] args) {
        _2FlipElementsInArrayToGetMaximum1s instance = new _2FlipElementsInArrayToGetMaximum1s();

        //int[] A = {0, 1, 0}; //start index = 0, end index = 0
        //int[] A = {0, 0, 0, 0, 0, 0, 0, 0};// start index = 0, end index = 7
        //int[] A = {0, 1, 0, 1, 1, 1, 0, 0/*, 1, 1, 1, 0*/};// start index = 6, end index = 7
        //int[] A = {0, 0, 0, 1, 1, 1, 0, 0};// start index = 0, end index = 2
        //int[] A = {0, 0, 0, 1, 1, 1, 0, 1};// start index = 0, end index = 2
        //int[] A = {0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 1};// start index = 0, end index = 9
        int[] A = {0, 0, 0, 1, 1, 1, 0, 0, 0, 1};// start index = 0, end index = 2
        int[] result = new int[2];

        instance.flip(A, result);
        System.out.println("start index: " + result[0] + ", end index: " + result[1]);
    }

    private void flip(int[] A, int[] result) {
        if (A == null || A.length == 0) return;

        int maxSum = 0;
        int sum = 0;

        int startIndex = -1;
        int endIndex = -1;


        for (int i = 0; i < A.length; i++) {
            if (A[i] >= 0) {
                startIndex = i;
                endIndex = i;
                break;
            }
        }
        if (startIndex == -1) {// 0 is not found in entire array. All elements in array are 1s.
            return;
        }

        int prevStartIndex = startIndex;
        int prevEndIndex = endIndex;

        int finalStartIndex = startIndex;
        int finalEndIndex = endIndex;


        for (int i = startIndex; i < A.length; i++) {
            int element = A[i];
            if (element == 0) {
                sum += 1;
            } else {
                sum -= 1;
            }

            prevStartIndex = startIndex;
            prevEndIndex = endIndex;

            if (startIndex == -1) {
                startIndex = i;
            }
            endIndex = i;

            if (sum < 0) {
                // preserving previously found start and end index
                finalStartIndex = prevStartIndex;
                finalEndIndex = prevEndIndex;

                startIndex = -1;//reset
                endIndex = -1;//reset

                sum = 0;// reset
            } else {
                if (sum > maxSum) {
                    maxSum = sum;
                    finalStartIndex = startIndex;
                    finalEndIndex = endIndex;
                }
            }


        }

        result[0] = finalStartIndex;
        result[1] = finalEndIndex;
    }

}
