package refactoring.newstyle;

import java.util.Enumeration;
import java.util.Vector;

/**
 * @author Tushar Chokshi @ 6/24/16.
 */
public class Customer {
    private String _name;
    private Vector _rentals = new Vector();

    public Customer(String name) {
        _name = name;
    }

    public void addRental(Rental arg) {
        _rentals.addElement(arg);
    }

    public String getName() {
        return _name;
    }

    public static void main(String[] args) {
        Customer customer = new Customer("Tushar");
        customer.addRental(new Rental(getMovieFromPriceCode("regular", Movie.REGULAR), 3));
        customer.addRental(new Rental(getMovieFromPriceCode("children", Movie.CHILDRENS), 2));
        customer.addRental(new Rental(getMovieFromPriceCode("newRelease", Movie.NEW_RELEASE), 1));
        System.out.println(customer.statement());
    }

    private static Movie getMovieFromPriceCode(String title, int priceCode) {
        if (priceCode == Movie.REGULAR) {
            return new RegularMovie(title);
        } else if (priceCode == Movie.NEW_RELEASE) {
            return new NewReleaseMovie(title);
        } else if (priceCode == Movie.CHILDRENS) {
            return new ChildrenMovie(title);
        }
        return null;
    }

/*   Original method

     public String statement() {
        double totalAmount = 0;
        int frequentRenterPoints = 0;
        Enumeration rentals = _rentals.elements();
        String result = "Rental Record for " + getName() + "\n";
        while (rentals.hasMoreElements()) {
            double thisAmount = 0;
            Rental each = (Rental) rentals.nextElement();
            //determine amounts for each line
            switch (each.getMovie().getPriceCode()) {
                case Movie.REGULAR:
                    thisAmount += 2;
                    if (each.getDaysRented() > 2)
                        thisAmount += (each.getDaysRented() - 2) * 1.5;
                    break;
                case Movie.NEW_RELEASE:
                    thisAmount += each.getDaysRented() * 3;
                    break;
                case Movie.CHILDRENS:
                    thisAmount += 1.5;
                    if (each.getDaysRented() > 3)
                        thisAmount += (each.getDaysRented() - 3) * 1.5;
                    break;
            }
            // add frequent renter points
            frequentRenterPoints++;
            // add bonus for a two day new release rental
            if ((each.getMovie().getPriceCode() == Movie.NEW_RELEASE) && each.getDaysRented() > 1) {
                frequentRenterPoints++;
            }
            //show figures for this rental
            result += "\t" + each.getMovie().getTitle() + "\t" + String.valueOf(thisAmount) + "\n";
            totalAmount += thisAmount;
        }
        //add footer lines
        result += "Amount owed is " + String.valueOf(totalAmount) + "\n";
        result += "You earned " + String.valueOf(frequentRenterPoints) + " frequent renter points";
        return result;
    }
 */

   /*
   Step 1:
    Break big method into smaller methods.

   Step 2:
    Move this smaller methods to the classes where it should belong.
    e.g. both of below methods Moved to Rental class because this method uses Rental object.

   private double getCharge(Rental aRental) {
        double result = 0;

        switch (aRental.getMovie().getPriceCode()) {
            case Movie.REGULAR:
                result += 2;
                if (aRental.getDaysRented() > 2)
                    result += (aRental.getDaysRented() - 2) * 1.5;
                break;
            case Movie.NEW_RELEASE:
                result += aRental.getDaysRented() * 3;
                break;
            case Movie.CHILDRENS:
                result += 1.5;
                if (aRental.getDaysRented() > 3)
                    result += (aRental.getDaysRented() - 3) * 1.5;
                break;
        }

        return result;
    }

    public int calculateFrequentRenterPoints(int frequentRenterPoints, Rental aRental) {
        // add frequent renter points
        frequentRenterPoints++;
        // add bonus for a two day new release rental
        if ((aRental.getMovie().getPriceCode() == Movie.NEW_RELEASE) && getDaysRented() > 1) {
            frequentRenterPoints++;
        }
        return frequentRenterPoints;
    }

    public String statement() {
        double totalAmount = 0;
        int frequentRenterPoints = 0;

        Enumeration rentals = _rentals.elements();
        String result = "Rental Record for " + getName() + "\n";
        while (rentals.hasMoreElements()) {
            double thisAmount = 0;
            Rental each = (Rental) rentals.nextElement();

            //determine amounts for each line
            thisAmount = getCharge(rental);


            // add frequent renter points
            frequentRenterPoints += calculateFrequentRenterPoints(frequentRenterPoints, rental);


            //show figures for this rental
            result += "\t" + each.getMovie().getTitle() + "\t" + String.valueOf(thisAmount) + "\n";
            totalAmount += thisAmount;
        }
        //add footer lines
        result += "Amount owed is " + String.valueOf(totalAmount) + "\n";
        result += "You earned " + String.valueOf(frequentRenterPoints) + " frequent renter points";
        return result;
    }
    */


    /*
    Step 3
    Replace the use temp variables like totalAmount and frequentRenterPoints with methods. So, that you can remove those temp variables.
     */
    public String statement() {
        String result = "Rental Record for " + getName() + "\n";
        Enumeration rentals = _rentals.elements();
        while (rentals.hasMoreElements()) {
            Rental each = (Rental) rentals.nextElement();
            //show figures for this rental
            result += "\t" + each.getMovie().getTitle() + "\t" + String.valueOf(each.getCharge()) + "\n";
        }
        //add footer lines
        result += "Amount owed is " + String.valueOf(getTotalChargeResult(_rentals)) + "\n";
        result += "You earned " + String.valueOf(getFrequentRenterPoints(_rentals)) + " frequent renter points";
        return result;
    }

    private double getTotalChargeResult(Vector _rentals) {
        double result = 0;
        Enumeration rentals = _rentals.elements();
        while (rentals.hasMoreElements()) {
            result += ((Rental) rentals.nextElement()).getCharge();
        }
        return result;
    }

    private int getFrequentRenterPoints(Vector _rentals) {
        int frequentRenterPoints = 0;
        Enumeration rentals = _rentals.elements();
        while (rentals.hasMoreElements()) {
            frequentRenterPoints += ((Rental) rentals.nextElement()).calculateFrequentRenterPoints(frequentRenterPoints);
        }
        return frequentRenterPoints;
    }
}