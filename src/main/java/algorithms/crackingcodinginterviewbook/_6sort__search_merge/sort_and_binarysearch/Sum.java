package algorithms.crackingcodinginterviewbook._6sort__search_merge.sort_and_binarysearch;

/**
 * @author Tushar Chokshi @ 11/23/17.
 *         <p>
 *         There are two ways of doing recursion
 *         - return the result from the method
 *         - pass the result parameter to the method ( I think it is called memoization )
 */
public class Sum {
    public static void main(String[] args) {
        int[] a = {2, 3, 4};
        System.out.println(sum(a));
    }

    private static int sum(int[] a) {
        if (a == null || a.length == 0) {
            return 0;
        }
        //return sum(a, 0, a.length - 1);
        // or
        Result result = new Result();
        sum(a, 0, a.length - 1, result);
        return result.getTotal();
    }


    // This is a typical example of reducing the problem by 1
    private static int sum(int[] a, int start, int end) {
        if (start == end) return a[start];
        return a[start] + sum(a, ++start, end);
    }

    // result is memoized
    private static void sum(int[] a, int start, int end, Result result) {
        if (start == end) {
            result.add(a[start]);
            return;
        }
        result.add(a[start]);
        start++;
        sum(a, start, end, result);
    }

    // recursive method is converted into iterative method
    private static int sumIteratively(int[] a) {
        if (a == null || a.length == 0) {
            return 0;
        }
        int start = 0; // parameters passed to recursive method are initialized this way in iterative approach
        int end = a.length - 1;

        int total = 0; // recursive method call is replaced by a variable 'total'.

        while (!(start > end)) { // exit condition of recursive method should become exit condition of while loop
            total = total + a[start];
            start++;
        }

        return total;
    }

    static class Result {
        int total;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public void add(int number) {
            total += number;
        }
    }

}
