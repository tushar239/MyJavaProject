package algorithms.crackingcodinginterviewbook._5recursion_and_dynamic_programming;

/*
https://www.youtube.com/watch?v=PpgmvNjqeUk
https://www.geeksforgeeks.org/dynamic-programming-set-8-matrix-chain-multiplication/


I couldn't understand and implement this algorithm correctly........

 */
public class FindMinCostOfMatriciesMultiplication {
    public static void main(String[] args) {
        int P[] = {2,3,6,4,5};
        //int P[] = {10,20,30,40,5,7};
        {
            int minCost = minCost(P, 0, P.length - 1);
            System.out.println("\033[1m" + "Min Cost: " + "\033[0m" + minCost);

        }
        {
            int n = P.length;

            System.out.println("Minimum number of multiplications is "+
                    MatrixChainOrder(P, 1, n-1));
        }
    }

    private static int minCost(int P[], int start, int end) {
        if(start > end) return 0;

        if(start == end) return 0;

        if(end == (start+1)) return 0;

        if(end == (start + 2)) {
            return P[start + 2] * P[start + 1] * P[start];
        }
        /*if(end == (start + 3)) {
            int mid = (start+end)/2;
            return minCost(P, start, mid) +minCost(P, mid+1, end);
        }*/

        int min1 = (P[start] * P[start+1] * P[end]) + minCost(P, start+1, end);

        int min2 = minCost(P, start, end-1) + (P[start] * P[end-1] * P[end]);

        int finalMin = min1;
        if(min2 < finalMin) {
            finalMin = min2;
        }

        int mid = (start+end)/2;
        int min3 = minCost(P, start, mid) + minCost(P, mid+1, end) + (P[start] * P[mid] * P[end]);

        if(min3 < finalMin) {
            finalMin = min3;
        }

        /*if(min1 < min2) {
            //System.out.println("( " + "P[" + start +"]"+ "   (P["+(end-(start+2)) +"] " + "P[" + (end-(start+1)) +"] " + "P[" + (end -(start+0)) + "])" +" )");
            return min1;
        }
        //System.out.println(" (" + "(P["+(end-(start+2)) +"] " + "P[" + (end-(start+1)) +"] " + "P[" + (end -(start+0)) + "])" + " P[" + start +"]"+" )");
        return min2;*/

        return finalMin;
    }
    // I don't understand this code, but to verify my code's result, I have put this code here
    // https://www.geeksforgeeks.org/dynamic-programming-set-8-matrix-chain-multiplication/
    static int MatrixChainOrder(int p[], int i, int j)
    {
        if (i == j)
            return 0;

        int min = Integer.MAX_VALUE;

        // place parenthesis at different places between first
        // and last matrix, recursively calculate count of
        // multiplications for each parenthesis placement and
        // return the minimum count
        for (int k=i; k<j; k++)
        {
            int count = MatrixChainOrder(p, i, k) +
                    MatrixChainOrder(p, k+1, j) +
                    p[i-1]*p[k]*p[j];

            if (count < min)
                min = count;
        }

        // Return minimum count
        return min;
    }
}
