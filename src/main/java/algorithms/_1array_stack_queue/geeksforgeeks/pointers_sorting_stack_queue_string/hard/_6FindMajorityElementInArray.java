package algorithms._1array_stack_queue.geeksforgeeks.pointers_sorting_stack_queue_string.hard;

/*
Find Majority Element in an Array

https://www.youtube.com/watch?v=qZloazsMZ8k

[4,7,4,4,7,4,4,9,4,3]
result=4

Definition of Majority element:
if number of occurrences of any element > size of array/2, then that element is in majority.

Solution 1:
Create a map of array element and number of time it appears.
Find an element that appears max times.
Then just see whether that max time > arraySize/2

This solution uses extra space (a map).

Solution 2:

4,      7,      4,      4,      7,      4,      4,      9,      4,      3
ele=4
cnt=1
        ele=7
        cnt=1

                ele=4
                cnt=1

                        ele=4
                        cnt=2

                                ele=4
                                cnt=1

                                        ele=4
                                        cnt=2

                                                ele=4
                                                cnt=3
                                                        ele=4
                                                        cnt=3
                                                                ele=4
                                                                cnt=3

                                                                        ele=4
                                                                        cnt=1


here ele (ele of interest) is 4
if cnt==0, then there is no element that can be considered a majority element.

now loop entire array to find out how many times 4 appears. (ans = 6)
6 > A.length/2. So 4 is a majority element
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
        int majorityElement = Integer.MIN_VALUE;
        int count = 0;

        for (int num : A) {
            if (count == 0) {
                majorityElement = num;
                count++;
            } else {
                if (num == majorityElement) {
                    count++;
                } else {
                    count--;
                    if (count == 0) {
                        majorityElement = num;
                        count++;
                    }
                }
            }
        }

        if (count > 0) {
            return majorityElement;
        }
        throw new Exception("there can be more than one element that appears same number of times.");
    }

    /*private static int findMajorityElement(int[] A) throws Exception {
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
    }*/

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
