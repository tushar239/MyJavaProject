package algorithms._1array.geeksforgeeks.pointers_sorting_stack.hard;

/*
Find Majority Element in an Array

https://www.youtube.com/watch?v=qZloazsMZ8k

[4,7,4,4,7,4,4,9,4,3]
result=4

Definition of Majority element:
if number of occurrences of any element > size of array/2, then that element is in majority.
*/
public class _6FindMajorityElementInArray {

    public static void main(String[] args) {
        {
            int A[] = {4, 7, 4, 4, 7, 4, 4, 9, 4, 3};
            int majorityElement = 0;
            try {
                majorityElement = findMajorityElement(A);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            System.out.println(majorityElement);// 4
            boolean whetherMajorityElementAppearsMostOfTheTime = findWhetherMajorityElementAppearsMostOfTheTime(A, majorityElement);
            System.out.println(whetherMajorityElementAppearsMostOfTheTime);//true

        }

        {
            int A[] = {4, 4, 4, 3, 3, 5, 6, 7};
            int majorityElement = 0;
            try {
                majorityElement = findMajorityElement(A);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            System.out.println(majorityElement);// instead of 4, it will give 7. But our intention is not to find an element that appears max times. Our intention is to find whether that element is in majority and for that it is not necessary that this algorithm should give an element that appears max times.
            boolean whetherMajorityElementAppearsMostOfTheTime = findWhetherMajorityElementAppearsMostOfTheTime(A, majorityElement);
            System.out.println(whetherMajorityElementAppearsMostOfTheTime);//false

        }

        /*{
            int A[] = {4, 7, 4, 7, 4, 7, 4, 7}; // exception
            int majorityElement = 0;
            try {
                majorityElement = findMajorityElement(A);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            System.out.println(majorityElement);
        }*/

    }

    private static int findMajorityElement(int[] A) throws Exception {
        int majorityElement = 0;
        int count = 0;

        for (int num : A) {
            if (num != majorityElement) {
                if (count == 0) {
                    majorityElement = num;
                    count = 1;
                } else {
                    count--;
                    if (count == 0) {
                        majorityElement = num;
                        count = 1;
                    }
                }
            } else {
                count++;
            }
        }

        if (count > 0) {
            return majorityElement;
        }
        throw new Exception("there can be more than one element that appears same number of times.");
    }

    private static boolean findWhetherMajorityElementAppearsMostOfTheTime(int A[], int majorityElement) {
        int count = 0;
        for (int num : A) {
            if (num == majorityElement) {
                count++;
                if (count > A.length / 2) {
                    return true;
                }
            }
        }
        return false;
    }
}
