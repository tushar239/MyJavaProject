package specials_summary.dto;

import java.util.LinkedList;
import java.util.List;

public class VehicleIncentivesSummary {

    private List<Summary> summaries;

    private VehicleIncentivesSummary(Vehicle vehicle) {
        summaries = new LinkedList<>();


    }
}
