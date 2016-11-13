package jaxb;

/**
 * @author Tushar Chokshi @ 11/2/15.
 */
public enum CriteriaEnum {
    MAKE("make"),
    PROVIDER_TYPE("providerType");


    private String make;

    CriteriaEnum(String make) {
        this.make = make;
    }

    public String getMake() {
        return make;
    }
}
