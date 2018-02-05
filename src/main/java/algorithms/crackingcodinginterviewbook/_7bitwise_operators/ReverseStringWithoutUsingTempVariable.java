package algorithms.crackingcodinginterviewbook._7bitwise_operators;

/*
https://www.youtube.com/watch?v=Y-UR3ravjRE&index=11&list=PLqM7alHXFySF8B9KqOy6yz4vggu4tiNMP

Solution is same as SwapTwoNumbers.java

s1 = "ab"
result should be s2="ba"

From SwapTwoNumbers.java, we know that to swap two numbers, we do

a = a^b
b = a^b
a = a^b

 */
public class ReverseStringWithoutUsingTempVariable {

    public static void main(String[] args) {
        String reversedString = reverse("abc");
        System.out.println(reversedString);//cba
    }

    private static String reverse(String str) {
        char[] chars = str.toCharArray();
        int start = 0;
        int end = chars.length - 1;

        while (start < end) {
            chars[start] = (char)(chars[start] ^ chars[end]);
            chars[end] = (char)(chars[start] ^ chars[end]);
            chars[start] = (char)(chars[start] ^ chars[end]);

            start++;
            end--;
        }

        return new String(chars);
    }
}
