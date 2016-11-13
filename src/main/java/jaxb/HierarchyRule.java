package jaxb;

import javax.xml.bind.annotation.XmlElement;

/**
 * @author Tushar Chokshi @ 11/2/15.
 */
public class HierarchyRule extends MyRule {

    private AllIncludes allIncludes;

    private AnyIncludes anyIncludes;

    @XmlElement(required = false, nillable = true)
    public AllIncludes getAllIncludes() {
        return allIncludes;
    }

    public void setAllIncludes(AllIncludes allIncludes) {
        this.allIncludes = allIncludes;
    }

    @XmlElement(required = false, nillable = true)
    public AnyIncludes getAnyIncludes() {
        return anyIncludes;
    }

    public void setAnyIncludes(AnyIncludes anyIncludes) {
        this.anyIncludes = anyIncludes;
    }
}
