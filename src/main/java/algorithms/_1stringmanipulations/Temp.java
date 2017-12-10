package algorithms._1stringmanipulations;

/**
 * @author Tushar Chokshi
 * @date 12/5/17
 */
public class Temp {
    public static void main(String[] args) {
        int[] numbers = new int[]{1,2,3,4,5};

        int count = 0;
        for (int number : numbers) {
            for (int i : numbers) {
                count++;
            }
        }
        System.out.println(count);
        
        count = 0;
        for (int i = 0; i < numbers.length; i++) {
            for (int j = 0; j <= i; j++) {
                count++;
            }
        }
        System.out.println(count);
        
    }
}
