package algorithms.interviewbit.array.kadane_algorithm;

public class _2FlipElementsInArrayToGetMaximum1s {

    public static void main(String[] args) {
        _2FlipElementsInArrayToGetMaximum1s instance = new _2FlipElementsInArrayToGetMaximum1s();

        //int[] A = {0, 1, 0}; //start index = 0, end index = 0
        //int[] A = {0, 0, 0, 0, 0, 0, 0, 0};// start index = 0, end index = 7
        int[] A = {0, 1, 0, 1, 1, 1, 0, 0/*, 1, 1, 1, 0*/};// start index = 6, end index = 7
        //int[] A = {0, 0, 0, 1, 1, 1, 0, 0};// start index = 0, end index = 2
        //int[] A = {0, 0, 0, 1, 1, 1, 0, 1};// start index = 0, end index = 2
        //int[] A = {0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 1};// start index = 0, end index = 9
        //int[] A = {0, 0, 0, 1, 1, 1, 0, 0, 0, 1};// start index = 0, end index = 2
        //int[] A = {0,1,1};// start index = 0, end index = 0

        int result[] = instance.flip(A);
        if(result.length > 0) {
            System.out.println("start index: " + result[0] + ", end index: " + result[1]);
        } else {
            System.out.println("no 0s found in array");
        }

    }

    private int[] flip(int[] A) {
        if (A == null || A.length == 0) return new int[0];

        int maxSum = 0;
        int sum = 0;

        int startIndex = -1;
        int endIndex = -1;


        for (int i = 0; i < A.length; i++) {
            if (A[i] == 0) {
                startIndex = i;
                endIndex = i;
                break;
            }
        }
        if (startIndex == -1) {// 0 is not found in entire array. All elements in array are 1s.
            return new int[0];
        }


        int finalStartIndex = startIndex;
        int finalEndIndex = endIndex;

        int prevFinalStartIndex = finalStartIndex;
        int prevFinalEndIndex = finalEndIndex;

        for (int i = startIndex; i < A.length; i++) {
            int element = A[i];
            if (element == 0) {
                sum += 1;
            } else {
                sum -= 1;
            }

            prevFinalStartIndex = finalStartIndex;
            prevFinalEndIndex = finalEndIndex;

            if (startIndex == -1) {
                startIndex = i;
            }
            endIndex = i;

            if (sum < 0) {
                // preserving previously found start and end index
                finalStartIndex = prevFinalStartIndex;
                finalEndIndex = prevFinalEndIndex;

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

        int[] result = new int[2];
        result[0] = finalStartIndex;
        result[1] = finalEndIndex;
        return result;
    }

}
