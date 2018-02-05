package algorithms.crackingcodinginterviewbook._1stringmanipulations;

// pg. 207 from Cracking Coding Interview book
/*
    e.g.
    s1 = waterbottle
    s2 = erbottlewat
    s2 is considered as rotated string of s1

    you are allowed to use isSubstring method just once.

    solution:
    s1s1=s1+s1 = waterbottlewaterbottle
    if(s1s1.contains(s2)) return true;
 */
public class _9StringRotation {
    public static void main(String[] args) {
        System.out.println("Less memory consuming approach");
        String s1 = "waterbottle";
        String s2 = "erbottlewat";
        String s1s1 = s1 + s1;
        System.out.println("s2 is a rotated string of s1: " + (s1.length() == s2.length() && s1s1.contains(s2))); // contains method runs a for loop to find a substring. So it takes some execution time, may be O(n).

        /*
        another approach is more memory consuming
         */
        System.out.println("Another memory consuming approach");

        if ((s1.length() != s2.length())) {
            System.out.println("s2 is a rotated string of s1: " + false);
        } else {

            boolean flag = false;
            for (int i = 0; i < s1.length(); i++) { // execution time O(n)
                if ((s1 = (s1.substring(1) + s1.charAt(0))).equals(s2)) { // max new O(n) String objects will be created
                    flag = true;
                    break;
                }
            }
            if (flag) {
                System.out.println("s2 is a rotated string of s1: " + true);
            } else {
                System.out.println("s2 is a rotated string of s1: " + false);
            }
        }
    }
}
