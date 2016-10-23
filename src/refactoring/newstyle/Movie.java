package refactoring.newstyle;

/**
 * @author Tushar Chokshi @ 6/24/16.
 */
public abstract class Movie {
    public static final int CHILDRENS = 2;
    public static final int REGULAR = 0;
    public static final int NEW_RELEASE = 1;
    private String _title;


    public Movie(String title) {
        _title = title;
    }

    public abstract int getPriceCode();

    public String getTitle() {
        return _title;
    }

    abstract double getCharge(Rental rental);
}
