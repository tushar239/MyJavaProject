package refactoring.newstyle;

/**
 * @author Tushar Chokshi @ 6/24/16.
 */
public class ChildrenMovie extends Movie {
    public ChildrenMovie(String title) {
        super(title);
    }

    @Override
    public int getPriceCode() {
        return Movie.CHILDRENS;
    }

    @Override
    public double getCharge(Rental rental) {
        double result = 1.5;
        if (rental.getDaysRented() > 3) {
            result += (rental.getDaysRented() - 3) * 1.5;
        }
        return result;
    }

}
