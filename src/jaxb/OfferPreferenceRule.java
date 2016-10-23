package jaxb;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Tushar Chokshi @ 11/2/15.
 */
public class OfferPreferenceRule {
    private OwnerInformation owner;

    private List<MyRule> myRules;

    public OwnerInformation getOwner() {
        return owner;
    }

    public void setOwner(OwnerInformation owner) {
        this.owner = owner;
    }

    @XmlElementWrapper
    @XmlElements({
            @XmlElement(name="hierarchyRule", type=HierarchyRule.class)
    })
    public List<MyRule> getMyRules() {
        return myRules;
    }


    public void addMyRule(MyRule myRule) {
        if(myRule != null) {
            if(myRules == null) {
                myRules = new ArrayList<>();
            }
            myRules.add(myRule);
        }
    }
}
