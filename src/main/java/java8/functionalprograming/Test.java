package java8.functionalprograming;

/**
 * @author Tushar Chokshi @ 1/6/17.
 */
public class Test {
    private static int i;

    public static void main(String[] args) {

        Float f = new Float(23.43);
        System.out.println(f);
        Boolean b = new Boolean("false");
        System.out.println(b);
        Double d = new Double("12.23d");
        System.out.println(d);

        new Inner() {
            @Override
            public void m() {
                System.out.println(i);
            }
        };

    }

    static class Inner {
        public void m() {
            System.out.println(i);
        }
    }


}
