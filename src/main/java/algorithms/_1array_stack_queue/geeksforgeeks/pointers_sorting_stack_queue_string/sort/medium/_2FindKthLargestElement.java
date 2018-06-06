package algorithms._1array_stack_queue.geeksforgeeks.pointers_sorting_stack_queue_string.sort.medium;

/*
Kth largest element in an unsorted array of distinct elements

Third largest element video
    https://www.youtube.com/watch?v=8pTzWRg_evk
    https://www.geeksforgeeks.org/third-largest-element-array-distinct-elements/
Kth largest element video
    https://www.youtube.com/watch?v=o1vuCrt7uYc



Solution:
    if(k < log n)
        maintaining result[] of 3 elements. compare A[]'s element with result[]'s elements and place it proper position in the result[].
        Runtime complexity= n*k
        if k is close to n, then time complexity of this approach will be O(n^2). In that case, you should choose alternative quick sort approach.
    else
        use quick sort way
        Runtime complexity=n+n/2+n/4.....=O(2n)


*/
public class _2FindKthLargestElement {

    public static void main(String[] args) {
        _2FindKthLargestElement instance = new _2FindKthLargestElement();

        int[] A = {50, 11, -8, 16, -7, 24, -2, 3, 43, 1, 0, 21, 25, 33, -10, 15, 12, 18, 9, 51, 17, 29, -30, 40};
        {
            int k = 3;

            instance.findKthLargestElement(A, k);//43
//            System.out.println(cnt);
        }

        {
            int k = 5;

            instance.findKthLargestElement(A, k);//33
//            System.out.println(count);
        }

    }

    private void findKthLargestElement(int[] A, int k) {
        if (k < Math.log(A.length)) {
            System.out.println("Using extra array approach as k < log n");

            int[] result = new int[k];
            for (int i = 0; i < result.length; i++) {
                result[i] = Integer.MIN_VALUE;
            }
            findKthLargestElement(A, result);
            System.out.println(result[k - 1]);
        } else {
            System.out.println("Using Quick Sort way as k >= log n");

            int thirdLargestElementQuickSortWay = findThirdLargestElementQuickSortWay(A, k, 0, A.length - 1);
            System.out.println(thirdLargestElementQuickSortWay);//11
        }
    }

    //static int cnt=0;
    private void findKthLargestElement(int[] A, int[] result) {
        if (A == null || A.length < result.length) return;

        for (int i = 0; i < A.length; i++) {
//            cnt++;

            if (A[i] <= result[result.length - 1]) {
                continue;
            }

            for (int j = 0; j < result.length; j++) {
//                if(j==0) cnt--;
//                cnt++;
                if (A[i] > result[j]) {
//                    cnt--;

                    int temp = result[j];

                    result[j] = A[i];

                    for (int k = j + 1; k < result.length; k++) {
//                        if(k==j+1) cnt--;
//                        cnt++;
                        int temp1 = result[k];
                        result[k] = temp;
                        temp = temp1;
                    }
                    break;
                }
            }
        }
    }

//    static int count=0;
    private int findThirdLargestElementQuickSortWay(int[] A, int k, int start, int end) {
        if (A == null || A.length < k) return Integer.MIN_VALUE;
        if (start == end) return A[start];

        int pIndex = start;
        int pivot = end;

        for (int i = start; i < end; i++) {
//            count++;
            if (A[pivot] < A[i]) {
                int temp = A[i];
                A[i] = A[pIndex];
                A[pIndex] = temp;
                pIndex++;
            }
        }

        int temp = A[pIndex];
        A[pIndex] = A[pivot];
        A[pivot] = temp;

        if (pIndex == (k - 1)) {// This is the important condition
            return A[pIndex];
        } else if ((k - 1) < pIndex) {
            return findThirdLargestElementQuickSortWay(A, k, start, pIndex - 1);
        }
        return findThirdLargestElementQuickSortWay(A, k, pIndex + 1, end);

    }
}
