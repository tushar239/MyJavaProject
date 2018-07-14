package specials_summary;

import specials_summary.dto.Vehicle;
import specials_summary.dto.VehicleIncentivesSummary;

import java.util.LinkedList;
import java.util.List;

public class DefaultVehicleIncentivesSummaryCreator implements VehiclesIncentivesSummaryCreator {
    @Override
    public List<VehicleIncentivesSummary> create(List<Vehicle> vehicles) {
        String[] vehicleCriteriaNames = {"category", "year", "make", "model", "trim"};

        for (Vehicle vehicle : vehicles) {
            String[] vehicleCriteriaValues = {vehicle.getCategory(), vehicle.getYear(), vehicle.getMake(), vehicle.getModel(), vehicle.getTrim()};

            //String groupIds =
        }
        return null;
    }

    private static List<String> printSubSequencesRecursively(String[] strs, int start, int end) {
        if (strs.length == 0) return new LinkedList<>();

        if (start == end) {
            List<String> set = new LinkedList<>();
            set.add(strs[start]);
            return set;
        }

        String first = strs[start];
        List<String> topLevelSubSeqsSet = new LinkedList<>();
        topLevelSubSeqsSet.add(first);

        List<String> subSeqsFromRemainingArray = printSubSequencesRecursively(strs, start + 1, end);
        topLevelSubSeqsSet.addAll(subSeqsFromRemainingArray);

        for (String subseq : subSeqsFromRemainingArray) {
            topLevelSubSeqsSet.add(first + "$"+subseq);
        }

        return topLevelSubSeqsSet;

    }
}
