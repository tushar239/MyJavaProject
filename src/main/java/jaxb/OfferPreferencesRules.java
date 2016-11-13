package jaxb;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Tushar Chokshi @ 11/2/15.
 */
@XmlRootElement
public class OfferPreferencesRules {
    private List<OfferPreferenceRule> offerPreferenceRules = new ArrayList<>();

    public List<OfferPreferenceRule> getOfferPreferenceRules() {
        return offerPreferenceRules;
    }

    public void setOfferPreferenceRules(List<OfferPreferenceRule> rules) {
        this.offerPreferenceRules = offerPreferenceRules;
    }

    public void addOfferPreferenceRule(OfferPreferenceRule offerPreferenceRule) {
        if(offerPreferenceRule != null) {
            offerPreferenceRules.add(offerPreferenceRule);
        }
    }
}
