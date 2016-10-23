package refactoring.newstyle;

/**
 * @author Tushar Chokshi @ 6/24/16.
 */
public class RegularMovie extends Movie {
    public RegularMovie(String title) {
        super(title);
    }

    @Override
    public int getPriceCode() {
        return Movie.REGULAR;
    }

    @Override
    public double getCharge(Rental rental) {
        double result = 2;
        if (rental.getDaysRented() > 2) {
            result += (rental.getDaysRented() - 2) * 1.5;
        }
        return result;
    }
}
