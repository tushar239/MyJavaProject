package specials_summary.dto;

import java.util.Objects;

public class Vehicle {
    private String category;
    private String make;
    private String model;
    private String year;
    private String trim;
    private double cashSavings;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getTrim() {
        return trim;
    }

    public void setTrim(String trim) {
        this.trim = trim;
    }

    public double getCashSavings() {
        return cashSavings;
    }

    public void setCashSavings(double cashSavings) {
        this.cashSavings = cashSavings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return Double.compare(vehicle.cashSavings, cashSavings) == 0 &&
                Objects.equals(category, vehicle.category) &&
                Objects.equals(make, vehicle.make) &&
                Objects.equals(model, vehicle.model) &&
                Objects.equals(year, vehicle.year) &&
                Objects.equals(trim, vehicle.trim);
    }

    @Override
    public int hashCode() {

        return Objects.hash(category, make, model, year, trim, cashSavings);
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "category='" + category + '\'' +
                ", make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", year='" + year + '\'' +
                ", trim='" + trim + '\'' +
                ", cashSavings=" + cashSavings +
                '}';
    }
}
