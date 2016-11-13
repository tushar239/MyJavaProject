package jaxb;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * @author Tushar Chokshi @ 11/2/15.
 */
public class Test {
    public static void main(String[] args) throws JAXBException {
        {
            OfferPreferencesRules offerPreferencesRules = new OfferPreferencesRules();

            OfferPreferenceRule offerPreferenceRule = new OfferPreferenceRule();
            offerPreferencesRules.addOfferPreferenceRule(offerPreferenceRule);

            OwnerInformation ownerInformation = new OwnerInformation();
            ownerInformation.setWebId("cblt-ms-gmps");
            ownerInformation.setLocale("en_US");
            offerPreferenceRule.setOwner(ownerInformation);

            HierarchyRule hierarchyRule = new HierarchyRule();
            offerPreferenceRule.addMyRule(hierarchyRule);

            AllIncludes allIncludes = new AllIncludes();
            allIncludes.addCriterion(CriteriaEnum.MAKE, "BUICK");
            allIncludes.addCriterion(CriteriaEnum.PROVIDER_TYPE, "TakeOver");
            hierarchyRule.setAllIncludes(allIncludes);


            String result;
            StringWriter sw = new StringWriter();
            JAXBContext context = JAXBContext.newInstance(OfferPreferencesRules.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.marshal(offerPreferencesRules, sw);
            result = sw.toString();
            System.out.println(result);
        }

        {
            StringReader reader = new StringReader("<offerPreferencesRules><offerPreferenceRules><myRules><hierarchyRule><allIncludes><criteria><entry><key>MAKE</key><value>BUICK</value></entry><entry><key>PROVIDER_TYPE</key><value>TakeOver</value></entry></criteria></allIncludes></hierarchyRule></myRules><owner><locale>en_US</locale><webId>cblt-ms-gmps</webId></owner></offerPreferenceRules></offerPreferencesRules>");
            JAXBContext context = JAXBContext.newInstance(OfferPreferencesRules.class);
            Unmarshaller jaxbUnmarshaller = context.createUnmarshaller();
            OfferPreferencesRules offerPreferencesRules = (OfferPreferencesRules) jaxbUnmarshaller.unmarshal(reader);


        }
    }
}
