package refactoring.newstyle;

/**
 * @author Tushar Chokshi @ 6/24/16.
 */
public class NewReleaseMovie extends Movie {
    public NewReleaseMovie(String title) {
        super(title);
    }

    @Override
    public int getPriceCode() {
        return Movie.NEW_RELEASE;
    }
    @Override
    public double getCharge(Rental rental) {
        return rental.getDaysRented() * 3;
    }
}
