package specials_summary;

import specials_summary.dto.Vehicle;
import specials_summary.dto.VehicleIncentivesSummary;

import java.util.List;

public interface VehiclesIncentivesSummaryCreator {
    List<VehicleIncentivesSummary> create(List<Vehicle> vehicles);
}
