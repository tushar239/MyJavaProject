package algorithms._1array_stack_queue.geeksforgeeks.pointers_sorting_stack_queue_string.medium;

/*
    Find next greater number with same set of digits

    https://www.geeksforgeeks.org/find-next-greater-number-set-digits/
    https://www.youtube.com/watch?v=t_TMt_BFGiQ

    e.g. 539641
    convert this number in array

    int[] A = [5   3   9   6   4   1]

    Starting from right, find a place where descending order breaks. Mark that index as D1

        5   3   9   6   4   1
            D1

    Starting from right, find a number that is greater than A[D1] and mark that index as D2.

        5   3   9   6   4   1
            D1          D2

    Replace A[D1] and A[D2]

        5   4   9   6   3   1
            D1

    Now, sort the right side of D1.

        5   4   1   3   6   9
            D1

    This is the next greater (541369) number with same set of digits.


Important thing to learn here is how to convert a number into an Array/ArrayList.

    int num=32841;

    List<Integer> list = new LinkedList<>();

    while (true) {
        int remainder = num % 10;
        int num = num / 10;

        if(num > 0) {
            list.add(remainder);
        }
        else { ------ important
            list.add(remainder);
            break;
        }
    }

    System.out.println(list); // [1, 4, 8, 2, 3] --- reverse of number


*/
public class _6NextGreaterNumberWithSameSetOfDigits {

}
