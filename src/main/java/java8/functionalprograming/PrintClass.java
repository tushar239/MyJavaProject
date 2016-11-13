package java8.functionalprograming;

/**
 * @author Tushar Chokshi @ 5/15/16.
 */
public class PrintClass<T> {
    public String printMe(T t) {
        return t.toString();
    }

    public static void printStaticText(String str) {
        System.out.println(str);
    }
}
