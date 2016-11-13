package jaxb;

/**
 * @author Tushar Chokshi @ 11/2/15.
 */
public class OwnerInformation {
    private String webId;
    private String locale;

    public void setWebId(String webId) {
        this.webId = webId;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getWebId() {
        return webId;
    }

    public String getLocale() {
        return locale;
    }
}
