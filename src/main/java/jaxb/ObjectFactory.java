package jaxb;

import javax.xml.bind.annotation.XmlRegistry;

/**
 * @author Tushar Chokshi @ 11/2/15.
 */
@XmlRegistry
public class ObjectFactory {
    public OfferPreferencesRules createOfferPreferencesRules() {
        return new OfferPreferencesRules();
    }
}
