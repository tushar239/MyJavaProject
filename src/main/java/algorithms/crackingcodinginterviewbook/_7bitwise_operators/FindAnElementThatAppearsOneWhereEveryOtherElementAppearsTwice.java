package algorithms.crackingcodinginterviewbook._7bitwise_operators;

/*

Find an element that appears one where every other element appears twice:

https://www.youtube.com/watch?v=5CRbjH0PmeE&index=16&list=PLqM7alHXFySF8B9KqOy6yz4vggu4tiNMP

7 3 5 5 3 1 0 1 0

answer=7

There are many approaches to solve this problem:
1) maintain hash table with key=number and value=count. This one uses extra memory because of hash table.
2) you know that XORing a number with itself results in 0. So, if you XOR all numbers with each other, you will end up with
   element that appears once ^ 0
   XORing a number with 0 results in that number itself.

*/
public class FindAnElementThatAppearsOneWhereEveryOtherElementAppearsTwice {

    public static void main(String[] args) {
        int result = find(new int[]{7, 3, 5, 5, 3, 1, 0, 1, 0});
        System.out.println(result);// 7
    }

    private static int find(int[] nums) {

        int num = nums[0];

        // 7 ^ (3 ^ 5 ^ 5 ^ 3 ^1 ^ 0 ^ 1 ^0) = 7 ^ 0 = 7
        for (int i = 1; i < nums.length; i++) {
            num = num ^ nums[i];
        }

        return num;
    }
}
