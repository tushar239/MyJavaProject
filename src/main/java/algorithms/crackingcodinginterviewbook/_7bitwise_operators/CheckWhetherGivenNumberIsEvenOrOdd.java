package algorithms.crackingcodinginterviewbook._7bitwise_operators;

/*
Check whether given number is even or odd:

https://www.youtube.com/watch?v=9j8X3dsBBNA&index=14&list=PLqM7alHXFySF8B9KqOy6yz4vggu4tiNMP

There are two approaches
1) if(number % 2 > 0) then it is an odd number
2) if(number & 1 != 0) then it an odd number
*/
public class CheckWhetherGivenNumberIsEvenOrOdd {

    public static void main(String[] args) {
        System.out.println(isEven(15)); // false
        System.out.println(isEven(10)); // true
    }

    private static boolean isEven(int num) {
        return (num & 1) == 0;
    }
}
