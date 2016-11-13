package refactoring.newstyle;

import java.util.Optional;

/**
 * @author Tushar Chokshi @ 6/24/16.
 */
public class Rental {
    private Movie _movie;
    private int _daysRented;

    public Rental(Movie movie, int daysRented) {
        _movie = movie;
        _daysRented = daysRented;
    }

    public int getDaysRented() {
        return _daysRented;
    }

    public Movie getMovie() {
        return _movie;
    }

    /*
        Step 2 - moved this method from Customer class to here because it was using Rental object thre.
     */
    /*public double getCharge() {
        double result = 0;

        switch (getMovie().getPriceCode()) {
            case Movie.REGULAR:
                result += 2;
                if (getDaysRented() > 2)
                    result += (getDaysRented() - 2) * 1.5;
                break;
            case Movie.NEW_RELEASE:
                result += getDaysRented() * 3;
                break;
            case Movie.CHILDRENS:
                result += 1.5;
                if (getDaysRented() > 3)
                    result += (getDaysRented() - 3) * 1.5;
                break;
        }

        return result;
    }
*/

    /*
        Step 2 - moved this method from Customer class to here because it was using Rental object there.
     */
    public int calculateFrequentRenterPoints(int frequentRenterPoints) {
        // add frequent renter points
        frequentRenterPoints++;
        // add bonus for a two day new release rental
        if ((getMovie().getPriceCode() == Movie.NEW_RELEASE) && getDaysRented() > 1) {
            frequentRenterPoints++;
        }
        return frequentRenterPoints;
    }

    /*
    Step 4 - Replacing the Conditional Logic on Price Code with Polymorphism
    Replacing switch-case with factory method and strategy pattern.
     */
    public double getCharge() {
        return Optional.ofNullable(getMovie()).map(movie -> movie.getCharge(this)).orElse(0d);
    }



}
