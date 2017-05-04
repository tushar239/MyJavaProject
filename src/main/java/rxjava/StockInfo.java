package rxjava;

/**
 * @author Tushar Chokshi @ 5/3/17.
 */
public class StockInfo {

    private String ticker;
    private double price;

    public StockInfo(String ticker, double price) {
        this.ticker = ticker;
        this.price = price;
    }

    public String getTicker() {
        return ticker;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "StockInfo{" +
                "ticker='" + ticker + '\'' +
                ", price=" + price +
                '}';
    }
}
