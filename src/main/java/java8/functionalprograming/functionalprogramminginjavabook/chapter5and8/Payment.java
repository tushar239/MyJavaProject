package java8.functionalprograming.functionalprogramminginjavabook.chapter5and8;

/**
 * @author Tushar Chokshi @ 11/22/16.
 */
public class Payment {
    public final String name;
    public final int amount;

    public Payment(String name, int amount) {
        this.name = name;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "name='" + name + '\'' +
                ", amount=" + amount +
                '}';
    }
}
