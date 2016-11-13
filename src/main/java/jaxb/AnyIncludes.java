package jaxb;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Tushar Chokshi @ 11/2/15.
 */

public class AnyIncludes {
    private Map<CriteriaEnum, String> criteria;

    public Map<CriteriaEnum, String> getCriteria() {
        return criteria;
    }

    public void addCriterion(CriteriaEnum criterion, String value) {
        if(criterion != null) {
            if(criteria == null) {
                criteria = new HashMap<>();
            }
            criteria.put(criterion, value);
        }

    }
}
