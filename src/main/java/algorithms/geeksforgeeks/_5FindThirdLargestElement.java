package algorithms.geeksforgeeks;

/*
Third largest element in an array of distinct elements
https://www.youtube.com/watch?v=8pTzWRg_evk
https://www.geeksforgeeks.org/third-largest-element-array-distinct-elements/


Solution:
maintaining result[] of 3 elements. compare A[]'s element with result[]'s elements and place it proper position in the result[].

Runtime complexity= n*3
*/
public class _5FindThirdLargestElement {

    public static void main(String[] args) {
        _5FindThirdLargestElement instance = new _5FindThirdLargestElement();

        int[] A = {11, -8, 16, -7, 24, -2, 3};
        int[] result = new int[]{Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE};

        instance.findThirdLargestElement(A, result);

        System.out.println(result[2]);//11
    }

    private void findThirdLargestElement(int[] A, int[] result) {
        if (A == null || A.length < 3) return;

        for (int i = 0; i < A.length; i++) {

            for (int j = 0; j < result.length; j++) {

                if (A[i] > result[j]) {

                    int temp = result[j];

                    result[j] = A[i];

                    for (int k = j + 1; k < result.length; k++) {
                        int temp1 = result[k];
                        result[k] = temp;
                        temp = temp1;
                    }
                    break;
                }
            }
        }

    }
}
